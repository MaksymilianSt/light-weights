import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable, of} from 'rxjs';
import {TrainingService} from '../services/training-service';
import {Training} from '../models/training.model';


@Injectable({providedIn: 'root'})
export class TrainingResolver implements Resolve<Training | null> {
  constructor(private trainingService: TrainingService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Training | null> {

    const planId = Number(route.paramMap.get('planId'))
    const blockId = Number(route.paramMap.get('blockId'))
    const trainingId = route.paramMap.get('trainingId')

    return trainingId != null
      ? this.trainingService.getTrainingById(planId, blockId, Number(trainingId))
      : of(null);

  }
}
