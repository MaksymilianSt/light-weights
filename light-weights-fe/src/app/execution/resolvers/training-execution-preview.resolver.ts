import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {TrainingExecutionService} from '../services/training-execution-service';
import {TrainingExecutionPreview} from '../models/training-execution-preview.model';

@Injectable({providedIn: 'root'})
export class TrainingExecutionPreviewResolver implements Resolve<TrainingExecutionPreview> {
  constructor(
    private trainingExecutionService: TrainingExecutionService
  ) {}

  resolve(route: ActivatedRouteSnapshot): Observable<TrainingExecutionPreview> {
    return this.trainingExecutionService.getTrainingExecutions();
  }
}
