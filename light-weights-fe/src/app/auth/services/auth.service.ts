import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, catchError, filter, map, Observable, take, throwError} from 'rxjs';
import {environment} from '../../../../environment';
import {AuthenticationResponse} from '../models/authentication-response.model';
import {RegisterRequest} from '../models/register-request.model';
import {AuthenticationRequest} from '../models/authentication-request.model';
import {User} from '../models/user-model';
import {Router} from '@angular/router';
import {JwtPayload} from '../models/jwt-payload-model';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public static readonly API_URL = `${environment.apiUrl}/auth`;
  private readonly _local_storage_user_key = 'user';

  private currentUserSubject: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);
  private logoutTimer: any;

  constructor(private http: HttpClient, private router: Router) {
    this.autoLogin();
  }

  authenticate(payload: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http
      .post<AuthenticationResponse>(`${AuthService.API_URL}/authenticate`, payload)
      .pipe(
        map((resp: AuthenticationResponse) => this.handleAuthResponse(resp))
      );
  }

  register(payload: RegisterRequest): Observable<AuthenticationResponse> {
    return this.http
      .post<AuthenticationResponse>(`${AuthService.API_URL}/register`, payload)
      .pipe(
        map((resp: AuthenticationResponse) => this.handleAuthResponse(resp))
      );
  }

  refreshToken(): Observable<AuthenticationResponse> {
    const storedUser = localStorage.getItem(this._local_storage_user_key);
    if (!storedUser) return throwError(() => new Error('No user stored'));

    const user: User = JSON.parse(storedUser);
    if(!user){
      this.logout();
      return throwError(() => new Error('Invalid user'));
    }

    const refreshToken = this.parseJwt(user.refresh_token)
    if( !refreshToken || (refreshToken.exp * 1000) < Date.now()) {
      this.logout();
      return throwError(() => new Error('Refresh token expired'));
    }

    if (this.isRefreshing) {
      return this.refreshTokenSubject.pipe(
        filter(token => token != null),
        take(1),
        map(token =>
          this.handleAuthResponse({
            accessToken: token!,
            refreshToken: user.refresh_token
          } as AuthenticationResponse)
        )
      );
    } else {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      return this.http.post<AuthenticationResponse>(
        `${AuthService.API_URL}/refresh`,
        user.refresh_token
      ).pipe(
        map((resp: AuthenticationResponse) => {
          this.isRefreshing = false;
          this.refreshTokenSubject.next(resp.accessToken);
          return this.handleAuthResponse(resp);
        }),
        catchError(err => {
          this.isRefreshing = false;
          this.logout();
          return throwError(() => err);
        })
      );
    }
  }

  logout(): void {
    const user = this.getCurrentUser();
    if (user?.refresh_token) {
      this.http.post(`${AuthService.API_URL}/logout`, user.refresh_token ).subscribe({
        next: () => console.log('Refresh token invalidated'),
        error: (err) => console.error('Failed to invalidate refresh token', err)
      });
    }

    localStorage.removeItem(this._local_storage_user_key);
    this.currentUserSubject.next(null);
    this.router.navigate(['auth']);
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return !!this.currentUserSubject.value;
  }

  private handleAuthResponse(resp: AuthenticationResponse): AuthenticationResponse {
    const parsedTokenPayload = this.parseJwt(resp.accessToken);
    const user: User = {
      email: parsedTokenPayload.sub,
      roles: parsedTokenPayload['roles'],
      access_token: resp.accessToken,
      refresh_token: resp.refreshToken
    }
    localStorage.setItem(this._local_storage_user_key, JSON.stringify(user));
    this.currentUserSubject.next(user);
    this.setAutoLogout(this.parseJwt(resp.refreshToken).exp)

    return resp;
  }

  private autoLogin() {
    const storedUser = localStorage.getItem(this._local_storage_user_key);

    if (storedUser) {
      const user: User = JSON.parse(storedUser);
      const expDate = this.parseJwt(user.refresh_token).exp;

      this.currentUserSubject.next(user);
      this.setAutoLogout(expDate);
    }
  }

  private setAutoLogout(expirationTime: number): void {
    if (this.logoutTimer) {
      clearTimeout(this.logoutTimer);
    }

    const currentTime = Date.now();
    const tokenValidityTime = expirationTime * 1000 - currentTime;

    if (tokenValidityTime <= 0) {
      this.logout();
      return;
    }

    this.logoutTimer = setTimeout(() => {
      this.logout();
    }, tokenValidityTime);
  }

  private parseJwt(token: string): JwtPayload {
    const tokenPayload = token.split('.')[1];
    return JSON.parse(atob(tokenPayload));
  }
}
