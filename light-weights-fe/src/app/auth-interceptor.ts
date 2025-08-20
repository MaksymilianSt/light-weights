import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, switchMap, throwError} from 'rxjs';
import {AuthService} from './auth/services/auth.service';
import {Router} from '@angular/router';
import {AuthenticationResponse} from './auth/models/authentication-response.model';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const currentUser = this.authService.getCurrentUser();
    const authToken = currentUser?.access_token;
    const isRefreshRequest = req.url.includes(AuthService.API_URL);

    const clonedRequest = authToken && !isRefreshRequest
      ? req.clone({setHeaders: {Authorization: `Bearer ${authToken}`}})
      : req;

    return next.handle(clonedRequest).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401 && !isRefreshRequest) {
          return this.authService.refreshToken().pipe(
            switchMap((refreshResp: AuthenticationResponse) => {
              const retryRequest = req.clone({
                setHeaders: {Authorization: `Bearer ${refreshResp.accessToken}`}
              });
              return next.handle(retryRequest);
            })
          );
        }
        return throwError(() => error);
      })
    );
  }
}
