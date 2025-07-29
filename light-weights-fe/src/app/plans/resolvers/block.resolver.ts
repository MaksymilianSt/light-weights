import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {BlockService} from '../services/block-service';
import {TrainingBlock} from '../models/training-block.model';


@Injectable({providedIn: 'root'})
export class BlockResolver implements Resolve<TrainingBlock> {

  constructor(private blockService: BlockService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TrainingBlock> {

    const planId = Number(route.paramMap.get('planId'))
    const blockId = Number(route.paramMap.get('blockId'))

    return this.blockService.getBlockById(planId, blockId)
  }
}
