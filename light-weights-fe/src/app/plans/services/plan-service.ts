import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TrainingPlanListModel} from '../models/training-plan-list.model';
import {environment} from '../../../../environment';
import {TrainingPlan} from '../models/training-plan.model';

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

  createPlan(plan: TrainingPlan): Observable<TrainingPlan> {
    return this.http.post<TrainingPlan>(
      `${this.API_URL}/plans`,
      plan
    );
  }

  updatePlan(planId: number, plan: TrainingPlan): Observable<TrainingPlan> {
    return this.http.put<TrainingPlan>(
      `${this.API_URL}/plans/${planId}`,
          plan
    );
  }

  deletePlan(planId: Number): Observable<any> {
    return this.http.delete(`${this.API_URL}/plans/${planId}`)
  }

  getCategories(): Observable<string[]> {
    return this.http.get<string[]>(`${this.API_URL}/plans/categories`)
  }

  getDifficultyLvls(): Observable<string[]> {
    return this.http.get<string[]>(`${this.API_URL}/plans/difficulty-lvls`)
  }
}
