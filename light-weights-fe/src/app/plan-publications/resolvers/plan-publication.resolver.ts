import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {TrainingPlanPublication} from '../models/training-plan-publication.model';
import {PlanPublicationService} from '../services/plan-publication-service';


@Injectable({providedIn: 'root'})
export class PlanPublicationResolver implements Resolve<TrainingPlanPublication> {
  constructor(private planPublicationService: PlanPublicationService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TrainingPlanPublication> {
    const planPublicationId = route.paramMap.get('planPublicationId');
    return this.planPublicationService.getPlanPublicationById(Number(planPublicationId));
  }
}
