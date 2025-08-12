import {Routes} from '@angular/router';
import {AuthComponent} from './auth/auth.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AuthGuard} from './auth.guard';
import {PlansResolver} from './plans/resolvers/plans.resolver';
import {PlanResolver} from './plans/resolvers/plan.resolver';
import {TrainingResolver} from './plans/resolvers/training.resolver';
import {BlockResolver} from './plans/resolvers/block.resolver';
import {TrainingExecutionResolver} from './execution/resolvers/training-execution.resolver';
import {TrainingExecutionPreviewResolver} from './execution/resolvers/training-execution-preview.resolver';
import {PlanPublicationResolver} from './plan-publications/resolvers/plan-publication.resolver';
import {PlanPublicationPreviewResolver} from './plan-publications/resolvers/plan-publication-preview.resolver';

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
      {
        path: 'executions',
        loadComponent: () =>
          import('./execution/components/training-execution-list/training-execution-list.component').then(
            (m) => m.TrainingExecutionListComponent
          ),
        resolve: {
          trainingExecutions: TrainingExecutionPreviewResolver,
        }
      },
      {
        path: 'executions/:executionId',
        loadComponent: () =>
          import('./execution/components/training-execution/training-execution.component').then(
            (m) => m.TrainingExecutionComponent
          ),
        resolve: {
          trainingExecution: TrainingExecutionResolver,
        }
      },
      {
        path: 'plan-publications',
        loadComponent: () =>
          import('./plan-publications/components/training-plan-publication-list/training-plan-publication-list.component').then(
            (m) => m.TrainingPlanPublicationListComponent
          ),
        resolve: {
          trainingPlanPublications: PlanPublicationPreviewResolver,
        }
      },
      {
        path: 'plan-publications/:planPublicationId',
        loadComponent: () =>
          import('./plan-publications/components/training-plan-publication/training-plan-publication.component').then(
            (m) => m.TrainingPlanPublicationComponent
          ),
        resolve: {
          trainingPlanPublication: PlanPublicationResolver,
        }
      },
    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];
