import {Injectable} from '@angular/core';
import {Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {TrainingPlanListModel} from './models/training-plan-list-model';
import {PlanService} from '../plan-service';


@Injectable({providedIn: 'root'})
export class PlansResolver implements Resolve<TrainingPlanListModel[]> {
  constructor(private planService: PlanService) {
  }

  resolve(): Observable<TrainingPlanListModel[]> {
    return this.planService.getPlanPreviews();
  }
}
