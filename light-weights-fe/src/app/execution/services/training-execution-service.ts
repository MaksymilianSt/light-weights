import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environment';
import {TrainingExecution} from '../models/training-execution.model';
import {TrainingExecutionPreview} from '../models/training-execution-preview.model';

@Injectable({
  providedIn: 'root',
})
export class TrainingExecutionService {
  private readonly BASE_URL = `${environment.apiUrl}/executions`;

  constructor(private http: HttpClient) {
  }

  getTrainingExecutions(): Observable<TrainingExecutionPreview> {
    return this.http.get<TrainingExecutionPreview>(
      `${this.BASE_URL}`
    )
  }

  getTrainingExecutionById(executionId: number): Observable<TrainingExecution> {
    return this.http.get<TrainingExecution>(
      `${this.BASE_URL}/${executionId}`
    )
  }

  finishTrainingExecutionById(executionId: number): Observable<TrainingExecution> {
    return this.http.patch<TrainingExecution>(
      `${this.BASE_URL}/${executionId}`, {}
    );
  }

  createTrainingExecution(trainingId: number): Observable<TrainingExecution> {
    return this.http.post<TrainingExecution>(
      `${this.BASE_URL}?trainingId=${trainingId}`, {}
    )
  }

  updateTrainingExecution(execution: TrainingExecution): Observable<TrainingExecution> {
    return this.http.put<TrainingExecution>(
      `${this.BASE_URL}/${execution.id}`,
        execution
    )
  }

  deleteTrainingExecutionById(executionId: number): Observable<void> {
    return this.http.delete<void>(
      `${this.BASE_URL}/${executionId}`
    )
  }

}
