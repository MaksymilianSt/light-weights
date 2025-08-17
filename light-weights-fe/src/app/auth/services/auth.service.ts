import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, catchError, map, Observable, of, switchMap, tap} from 'rxjs';
import {environment} from '../../../../environment';
import {AuthenticationResponse} from '../models/authentication-response.model';
import {RegisterRequest} from '../models/register-request.model';
import {AuthenticationRequest} from '../models/authentication-request.model';
import {User} from '../models/user-model';
import {Router} from '@angular/router';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly AUTH_URL = `${environment.apiUrl}/auth`;
  private readonly USER_URL = `${environment.apiUrl}/user`;

  private currentUserSubject: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();


  constructor(private http: HttpClient, private router: Router) {
  }

  authenticate(payload: AuthenticationRequest): Observable<User | null> {
    return this.http
      .post<AuthenticationResponse>(`${this.AUTH_URL}/authenticate`, payload)
      .pipe(
        switchMap((resp: AuthenticationResponse) => this.fetchCurrentUser())
      );
  }

  register(payload: RegisterRequest): Observable<User | null> {
    return this.http
      .post<AuthenticationResponse>(`${this.AUTH_URL}/register`, payload)
      .pipe(
        switchMap((resp: AuthenticationResponse) => this.fetchCurrentUser())
      );
  }

  logout(): void {
    this.currentUserSubject.next(null);
    this.router.navigate(['auth']);
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return !!this.currentUserSubject.value;
  }

  fetchCurrentUser(): Observable<User | null> {
    return this.http.get<User>(`${this.USER_URL}/me`, {withCredentials: true}).pipe(
      tap(user => this.currentUserSubject.next(user)),
      catchError(error => {
        this.currentUserSubject.next(null);
        return of(null);
      })
    );
  }

}
