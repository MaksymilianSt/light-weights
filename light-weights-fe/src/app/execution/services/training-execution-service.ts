import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environment';
import {TrainingExecution} from '../models/training-execution.model';

@Injectable({
  providedIn: 'root',
})
export class TrainingExecutionService {
  private readonly BASE_URL = `${environment.apiUrl}/executions`;

  constructor(private http: HttpClient) {
  }

  getTrainingExecutionById(executionId: number): Observable<TrainingExecution> {
    return this.http.get<TrainingExecution>(
      `${this.BASE_URL}/${executionId}`
    )
  }

  createTrainingExecution(trainingId: number): Observable<TrainingExecution> {
    return this.http.post<TrainingExecution>(
      `${this.BASE_URL}?trainingId=${trainingId}`, {}
    )
  }

}
