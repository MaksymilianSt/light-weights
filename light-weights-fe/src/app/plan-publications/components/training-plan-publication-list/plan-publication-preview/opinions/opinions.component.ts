import {Component, EventEmitter, Input, Output} from '@angular/core';
import {PlanOpinion} from '../../../../models/plan-opinion.model';
import {DatePipe, NgClass, NgForOf} from '@angular/common';
import {FormsModule, NgForm} from '@angular/forms';
import {PlanPublicationOpinionService} from '../../../../services/plan-publication-opinion-service';

@Component({
  selector: 'app-opinions',
  imports: [
    NgForOf,
    DatePipe,
    FormsModule,
    NgClass
  ],
  templateUrl: './opinions.component.html',
  standalone: true,
  styleUrl: './opinions.component.css'
})
export class OpinionsComponent {
  @Input() opinions!: PlanOpinion[];
  @Input() planPublicationId: number = 0;
  @Output() addNewOpinion: EventEmitter<PlanOpinion> = new EventEmitter();
  newOpinion?: string;

  constructor(
    private opinionService: PlanPublicationOpinionService
  ) {}

  onNewOpinion(form: NgForm) {
    if (form.valid) {
      this.opinionService.createOpinion(this.planPublicationId, this.newOpinion!).subscribe(opinion => {
        this.addNewOpinion.emit(opinion);
        this.newOpinion = '';
      })
    }
  }

}
