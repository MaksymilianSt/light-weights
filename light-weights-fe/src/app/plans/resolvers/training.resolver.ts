import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {TrainingService} from '../services/training-service';
import {Training} from '../models/training.model';


@Injectable({providedIn: 'root'})
export class TrainingResolver implements Resolve<Training> {
  constructor(private trainingService: TrainingService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Training> {
    //TODO: handle /new
    const planId = Number(route.paramMap.get('planId'))
    const blockId = Number(route.paramMap.get('blockId'))
    const trainingId = Number(route.paramMap.get('trainingId'))

    return this.trainingService.getTrainingById(planId, blockId, trainingId);
  }
}
