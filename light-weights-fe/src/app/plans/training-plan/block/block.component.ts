import {Component, Input} from '@angular/core';
import {TrainingBlock} from '../models/training-plan';
import {FormsModule} from '@angular/forms';
import {DatePipe, DecimalPipe, NgForOf, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-block',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    DatePipe,
    NgIf,
    RouterLink,
    DecimalPipe
  ],
  templateUrl: './block.component.html',
  styleUrl: './block.component.css'
})
export class BlockComponent {
  @Input() block: TrainingBlock | null = null;
  @Input() editMode: boolean = false;


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

  calculateTrainingsCompletion() {
    const completedTrainings = this.block!.trainings
      .filter(training => training.done)
      .length;
    const allTrainingsSize = this.block!.trainings.length;

    return (completedTrainings / allTrainingsSize) * 100;
  }
}
