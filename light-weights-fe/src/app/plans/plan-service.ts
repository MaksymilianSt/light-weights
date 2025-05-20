import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TrainingPlanListModel} from './training-plan-list/models/training-plan-list-model';
import {environment} from '../../../environment';

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
}
