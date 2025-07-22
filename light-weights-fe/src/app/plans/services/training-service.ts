import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environment';
import {Training} from '../models/training.model';

@Injectable({
  providedIn: 'root',
})
export class TrainingService {
  private readonly API_URL = `${environment.apiUrl}`;

  constructor(private http: HttpClient) {
  }

  getTrainingById(planId: number, blockId: number, trainingId: number): Observable<Training> {
    return this.http.get<Training>(
      `${this.API_URL}/plans/${planId}/blocks/${blockId}/trainings/${trainingId}`
    )
  }

  createTraining(planId: number, blockId: number, training: Training): Observable<Training> {
    return this.http.post<Training>(
      `${this.API_URL}/plans/${planId}/blocks/${blockId}/trainings`,
      training
    );
  }

  updateTraining(planId: number, blockId: number, training: Training): Observable<Training> {
    return this.http.put<Training>(
      `${this.API_URL}/plans/${planId}/blocks/${blockId}/trainings/${training.id}`,
      training
    );
  }

  deleteTrainingById(planId: number, blockId: number, trainingId: number): Observable<void> {
    return this.http.delete<void>(
      `${this.API_URL}/plans/${planId}/blocks/${blockId}/trainings/${trainingId}`
    )
  }
}
