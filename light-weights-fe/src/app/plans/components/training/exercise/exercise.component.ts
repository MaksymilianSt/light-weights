import {Component, Input} from '@angular/core';
import {Exercise} from '../../../models/exercise.model';
import {DatePipe, DecimalPipe, NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {AutosizeModule} from 'ngx-autosize';

@Component({
  selector: 'app-exercise',
  imports: [
    DatePipe,
    FormsModule,
    NgForOf,
    AutosizeModule
  ],
  templateUrl: './exercise.component.html',
  standalone: true,
  styleUrl: './exercise.component.css'
})
export class ExerciseComponent {
  @Input() exercise: Exercise | null = null;
  @Input() editMode: boolean = false;
}
