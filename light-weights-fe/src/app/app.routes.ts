import {Routes} from '@angular/router';
import {AuthComponent} from './auth/auth.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AuthGuard} from './auth.guard';
import {PlansResolver} from './plans/resolvers/plans.resolver';
import {PlanResolver} from './plans/resolvers/plan.resolver';
import {TrainingResolver} from './plans/resolvers/training.resolver';
import {BlockResolver} from './plans/resolvers/block.resolver';

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
          import('./plans/components/training-plan-list/training-plan-list.component').then(
            (m) => m.TrainingPlanListComponent
          ),
        resolve: {
          plans: PlansResolver
        }
      },
      {
        path: 'plans/new',
        loadComponent: () =>
          import('./plans/components/training-plan/training-plan.component').then(
            (m) => m.TrainingPlanComponent
          ),
        resolve: {
          trainingPlan: PlanResolver
        }
      },
      {
        path: 'plans/:planId',
        loadComponent: () =>
          import('./plans/components/training-plan/training-plan.component').then(
            (m) => m.TrainingPlanComponent
          ),
        resolve: {
          trainingPlan: PlanResolver
        }
      },
      {
        path: 'plans/:planId/blocks/:blockId/trainings/new',
        loadComponent: () =>
          import('./plans/components/training/training.component').then(
            (m) => m.TrainingComponent
          ),
        resolve: {
          training: TrainingResolver,
          block: BlockResolver
        }
      },
      {
        path: 'plans/:planId/blocks/:blockId/trainings/:trainingId',
        loadComponent: () =>
          import('./plans/components/training/training.component').then(
            (m) => m.TrainingComponent
          ),
        resolve: {
          training: TrainingResolver,
          block: BlockResolver
        }
      },
    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];
