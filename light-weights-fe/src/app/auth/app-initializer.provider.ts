import { APP_INITIALIZER, Provider } from '@angular/core';
import { AuthService } from './services/auth.service';

export function initApp(authService: AuthService) {
  return () => authService.fetchCurrentUser().toPromise();
}

export const AppInitializerProvider: Provider = {
  provide: APP_INITIALIZER,
  useFactory: initApp,
  deps: [AuthService],
  multi: true
};
