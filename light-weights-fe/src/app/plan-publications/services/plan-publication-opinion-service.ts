import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environment';
import {PlanOpinion} from '../models/plan-opinion.model';

@Injectable({
  providedIn: 'root',
})
export class PlanPublicationOpinionService {
  private readonly URL = `${environment.apiUrl}/plan-publication`;

  constructor(private http: HttpClient) {
  }

  createOpinion(planPublicationId: number, opinionContent: string): Observable<PlanOpinion> {
    return this.http.post<PlanOpinion>(
      `${this.URL}/${planPublicationId}/opinions`,
      opinionContent
    );
  }

}
