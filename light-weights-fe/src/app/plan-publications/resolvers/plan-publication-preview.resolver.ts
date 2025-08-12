import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {PlanPublicationService} from '../services/plan-publication-service';
import {TrainingPlanPublicationPreview} from '../models/training-plan-publication-preview.model';


@Injectable({providedIn: 'root'})
export class PlanPublicationPreviewResolver implements Resolve<TrainingPlanPublicationPreview[]> {
  constructor(private planPublicationService: PlanPublicationService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TrainingPlanPublicationPreview[]> {
    return this.planPublicationService.getPlanPublicationPreviews();
  }
}
