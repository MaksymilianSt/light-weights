import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environment';
import {AuthenticationResponse} from '../models/authentication-response.model';
import {RegisterRequest} from '../models/register-request.model';
import {AuthenticationRequest} from '../models/authentication-request.model';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly API_URL = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {
  }

  authenticate(payload: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.API_URL}/authenticate`, payload);
  }

  register(payload: RegisterRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.API_URL}/register`, payload);
  }
}
