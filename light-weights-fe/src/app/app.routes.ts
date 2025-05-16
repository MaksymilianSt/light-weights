import { Routes } from '@angular/router';
import {AuthComponent} from './auth/auth.component';
import {DashboardComponent} from './dashboard/dashboard.component';

export const routes: Routes = [
  {
    path: 'auth',
    component: AuthComponent
  },
  {
    path: '',
    component: DashboardComponent,
    children: [

    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];
