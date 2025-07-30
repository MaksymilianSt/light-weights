import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environment';
import {TrainingExerciseExecution} from '../models/training-exercise-execution.model';

@Injectable({
  providedIn: 'root',
})
export class TrainingExerciseExecutionService {
  private readonly BASE_URL = `${environment.apiUrl}/executions`;

  constructor(private http: HttpClient) {
  }

  updateTrainingExerciseExecution(executionId: number, executionExercise: TrainingExerciseExecution): Observable<TrainingExerciseExecution> {
    return this.http.put<TrainingExerciseExecution>(
      `${this.BASE_URL}/${executionId}/exercises/${executionExercise.id}`,
      executionExercise
    )
  }
}
