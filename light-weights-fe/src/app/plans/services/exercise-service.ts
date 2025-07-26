import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environment';
import {Exercise} from '../models/exercise.model';

@Injectable({
  providedIn: 'root',
})
export class ExerciseService {
  private readonly API_URL = `${environment.apiUrl}`;

  constructor(private http: HttpClient) {
  }

  createExercise(planId: number, blockId: number, trainingId: number, exercise: Exercise): Observable<Exercise> {
    return this.http.post<Exercise>(
      `${this.API_URL}/plans/${planId}/blocks/${blockId}/trainings/${trainingId}/exercises`,
      exercise
    );
  }

  updateExercise(planId: number, blockId: number, trainingId: number, exercise: Exercise): Observable<Exercise> {
    return this.http.put<Exercise>(
      `${this.API_URL}/plans/${planId}/blocks/${blockId}/trainings/${trainingId}/exercises/${exercise.id}`,
      exercise
    );
  }

  deleteExerciseById(planId: number, blockId: number, trainingId: number, exercisegId: number): Observable<void> {
    return this.http.delete<void>(
      `${this.API_URL}/plans/${planId}/blocks/${blockId}/trainings/${trainingId}/exercises/${exercisegId}`
    )
  }
}
