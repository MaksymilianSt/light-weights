import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable, of} from 'rxjs';
import {PlanService} from '../services/plan-service';
import {TrainingPlan} from '../models/training-plan.model';


@Injectable({providedIn: 'root'})
export class PlanResolver implements Resolve<TrainingPlan | null> {
  constructor(private planService: PlanService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TrainingPlan | null> {
    const planId = route.paramMap.get('planId');

    return planId != null
              ? this.planService.getPlanById(Number(planId))
              : of(null)

  }
}
