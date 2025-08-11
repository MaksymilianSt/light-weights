import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environment';
import {TrainingPlanPublication} from '../models/training-plan-publication.model';
import {TrainingPlan} from '../../plans/models/training-plan.model';
import {PlanScale} from '../models/plan-scale.model';

@Injectable({
  providedIn: 'root',
})
export class PlanPublicationService {
  private readonly URL = `${environment.apiUrl}/plan-publication`;

  constructor(private http: HttpClient) {
  }

  // getPlanPublicationPreviews(): Observable<TrainingPlanListModel[]> {
  //   return this.http.get<TrainingPlanListModel[]>(`${this.API_URL}/plans`)
  // }

  getPlanPublicationById(id: number): Observable<TrainingPlanPublication> {
    return this.http.get<TrainingPlanPublication>(`${this.URL}/${id}`)
  }

  publishPlan(planId: number): Observable<TrainingPlanPublication> {
    return this.http.post<TrainingPlanPublication>(
      `${this.URL}/publish?planId=${planId}`,
      null
    );
  }

  downloadPlan(planPublicationId: number, scale: PlanScale): Observable<TrainingPlan> {
    return this.http.post<TrainingPlan>(
      `${this.URL}/download?publicationId=${planPublicationId}`,
      scale
    );
  }

  deletePlanPublication(id: Number): Observable<any> {
    return this.http.delete(`${this.URL}/${id}`)
  }

}
