import {Component, EventEmitter, Input, Output} from '@angular/core';
import {PlanOpinion} from '../../../../models/plan-opinion.model';
import {DatePipe, NgClass, NgForOf, NgIf} from '@angular/common';
import {FormsModule, NgForm} from '@angular/forms';
import {PlanPublicationOpinionService} from '../../../../services/plan-publication-opinion-service';
import {AuthService} from '../../../../../auth/services/auth.service';

@Component({
  selector: 'app-opinions',
    imports: [
        NgForOf,
        DatePipe,
        FormsModule,
        NgClass,
        NgIf
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
    private opinionService: PlanPublicationOpinionService,
    private authService: AuthService,
  ) {}

  onNewOpinion(form: NgForm) {
    if (form.valid) {
      this.opinionService.createOpinion(this.planPublicationId, this.newOpinion!).subscribe(opinion => {
        this.addNewOpinion.emit(opinion);
        this.newOpinion = '';
      })
    }
  }

  hasRemoveAccess(): boolean {
    return this.authService.isAdmin();
  }

  deleteOpinion(opinion: PlanOpinion) {
    if (this.hasRemoveAccess()) {
      if (confirm(`Are you sure you want to delete ${opinion.userEmail}'s opinion with content: "${opinion.content}"?`)) {
        this.opinionService.deleteOpinion(this.planPublicationId, opinion.id).subscribe(() => {
          const index = this.opinions.findIndex(item => item.id === opinion.id);
          if (index !== -1) {
            this.opinions.splice(index, 1);
          }
        })
      }
    }
  }

}
