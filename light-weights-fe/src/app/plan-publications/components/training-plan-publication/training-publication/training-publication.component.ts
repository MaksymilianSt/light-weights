import {Component, Input} from '@angular/core';
import {Training} from '../../../../plans/models/training.model';
import {AutosizeModule} from 'ngx-autosize';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DatePipe, NgForOf} from '@angular/common';
import {ExercisePublicationComponent} from './exercise-publication/exercise-publication.component';
import {PlanScale} from '../../../models/plan-scale.model';

@Component({
  selector: 'app-training-publication',
  imports: [
    AutosizeModule,
    FormsModule,
    ReactiveFormsModule,
    NgForOf,
    ExercisePublicationComponent,
    DatePipe
  ],
  templateUrl: './training-publication.component.html',
  standalone: true,
  styleUrl: './training-publication.component.css'
})
export class TrainingPublicationComponent {
  @Input() training!: Training;
  @Input() scale!: PlanScale;

  getTrainingDate(): string {
    const date = new Date(this.training.date);
    date.setDate(date.getDate() + (this.scale.dayOffSet ?? 0));

    return date.toString();
  }

}
