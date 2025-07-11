import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TrainingPlanListModel} from '../models/training-plan-list.model';
import {environment} from '../../../../environment';
import {TrainingPlan} from '../models/training-preview.model';

@Injectable({
  providedIn: 'root',
})
export class PlanService {
  private readonly API_URL = `${environment.apiUrl}`;

  constructor(private http: HttpClient) {
  }

  getPlanPreviews(): Observable<TrainingPlanListModel[]> {
    return this.http.get<TrainingPlanListModel[]>(`${this.API_URL}/plans`)
  }

  getPlanById(id: number): Observable<TrainingPlan> {
    return this.http.get<TrainingPlan>(`${this.API_URL}/plans/${id}`)
  }
}
