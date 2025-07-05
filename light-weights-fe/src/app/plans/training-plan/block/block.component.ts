import {Component, Input} from '@angular/core';
import {TrainingBlock} from '../models/training-plan';
import {FormsModule} from '@angular/forms';
import {DatePipe, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-block',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    DatePipe,
    NgIf
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
      return  '?';
    }

    const diffInMs = endDate.getTime() - startDate.getTime();
    const oneWeekInMs = 7 * 24 * 60 * 60 * 1000;

    return Math.ceil(diffInMs / oneWeekInMs);
  }

}
