import {Component, Input} from '@angular/core';
import {TrainingPlanPublicationPreview} from '../../../models/training-plan-publication-preview.model';
import {DatePipe} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-plan-publication-preview',
  imports: [
    DatePipe,
    RouterLink
  ],
  templateUrl: './plan-publication-preview.component.html',
  standalone: true,
  styleUrl: './plan-publication-preview.component.css'
})
export class PlanPublicationPreviewComponent {
  @Input() planPublication!: TrainingPlanPublicationPreview;

  calculateWeeks(start: string | Date, end: string | Date): number | '?' {
    const startDate = new Date(start);
    const endDate = new Date(end);

    if (isNaN(startDate.getTime()) || isNaN(endDate.getTime())) {
      return '?';
    }

    const diffInMs = endDate.getTime() - startDate.getTime();
    const oneWeekInMs = 7 * 24 * 60 * 60 * 1000;

    return Math.ceil(diffInMs / oneWeekInMs);
  }
}
