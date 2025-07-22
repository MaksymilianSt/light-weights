import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environment';
import {TrainingBlock} from '../models/training-plan.model';

@Injectable({
  providedIn: 'root',
})
export class BlockService {
  private readonly API_URL = `${environment.apiUrl}`;

  constructor(private http: HttpClient) {
  }

  getBlockById(planId: number, blockId: number): Observable<TrainingBlock> {
    return this.http.get<TrainingBlock>(`${this.API_URL}/plans/${planId}/blocks/${blockId}`);
  }

  createBlock(planId: number, block: TrainingBlock): Observable<TrainingBlock> {
    return this.http.post<TrainingBlock>(
      `${this.API_URL}/plans/${planId}/blocks`,
      block
    );
  }

  updateBlock(planId: number, block: TrainingBlock): Observable<TrainingBlock> {
    return this.http.put<TrainingBlock>(
      `${this.API_URL}/plans/${planId}/blocks/${block.id}`,
      block
    );
  }

  deleteBlock(planId: number, blockId: number): Observable<any> {
    return this.http.delete(`${this.API_URL}/plans/${planId}/blocks/${blockId}`)
  }

}
