import {Routes} from '@angular/router';
import {AuthComponent} from './auth/auth.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AuthGuard} from './auth.guard';
import {PlansResolver} from './plans/training-plan-list/plans.resolver';
import {PlanResolver} from './plans/training-plan/plan.resolver';

export const routes: Routes = [
  {
    path: 'auth',
    component: AuthComponent
  },
  {
    path: '',
    canActivate: [AuthGuard],
    component: DashboardComponent,
    children: [
      {
        path: '',
        redirectTo: 'plans',
        pathMatch: 'full'
      },
      {
        path: 'plans',
        loadComponent: () =>
          import('./plans/training-plan-list/training-plan-list.component').then(
            (m) => m.TrainingPlanListComponent
          ),
        resolve: {
          plans: PlansResolver
        }
      },
      {
        path: 'plans/:id',
        loadComponent: () =>
          import('./plans/training-plan/training-plan.component').then(
            (m) => m.TrainingPlanComponent
          ),
        resolve: {
          trainingPlan: PlanResolver
        }
      },
    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];
