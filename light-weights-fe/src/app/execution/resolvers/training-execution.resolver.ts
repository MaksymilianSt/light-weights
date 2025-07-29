import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {TrainingExecution} from '../models/training-execution.model';
import {TrainingExecutionService} from '../services/training-execution-service';

@Injectable({providedIn: 'root'})
export class TrainingExecutionResolver implements Resolve<TrainingExecution> {
  constructor(
    private trainingExecutionService: TrainingExecutionService
  ) {}

  resolve(route: ActivatedRouteSnapshot): Observable<TrainingExecution> {
    const executionId = Number(route.paramMap.get('executionId'))

    return this.trainingExecutionService.getTrainingExecutionById(executionId);
  }
}
