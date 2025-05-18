import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, catchError, map, Observable} from 'rxjs';
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
  private readonly API_URL = `${environment.apiUrl}/auth`;
  private readonly _local_storage_user_key = 'user';

  private currentUserSubject: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  private logoutTimer: any;

  constructor(private http: HttpClient, private router: Router) {
    this.autoLogin();
  }

  authenticate(payload: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http
      .post<AuthenticationResponse>(`${this.API_URL}/authenticate`, payload)
      .pipe(
        map((resp: AuthenticationResponse) => this.handleAuthResponse(resp))
      );
  }

  register(payload: RegisterRequest): Observable<AuthenticationResponse> {
    return this.http
      .post<AuthenticationResponse>(`${this.API_URL}/register`, payload)
      .pipe(
        map((resp: AuthenticationResponse) => this.handleAuthResponse(resp))
      );
  }

  logout(): void {
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
    const parsedTokenPayload = this.parseJwt(resp.token);
    const user: User = {
      email: parsedTokenPayload.sub,
      token: resp.token
    }
    localStorage.setItem(this._local_storage_user_key, JSON.stringify(user));
    this.currentUserSubject.next(user);
    this.setAutoLogout(parsedTokenPayload.exp)

    return resp;
  }

  private autoLogin() {
    const storedUser = localStorage.getItem(this._local_storage_user_key);

    if (storedUser) {
      const user: User = JSON.parse(storedUser);
      const expDate = this.parseJwt(user.token).exp;

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
