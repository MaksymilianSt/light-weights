import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {PlanService} from '../plan-service';
import {TrainingPlan} from './models/training-preview';


@Injectable({providedIn: 'root'})
export class PlanResolver implements Resolve<TrainingPlan> {
  constructor(private planService: PlanService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TrainingPlan> {
    const planId = Number(route.paramMap.get('id')) //TODO: handle /new
    return this.planService.getPlanById(planId);
  }
}
