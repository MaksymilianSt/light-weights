import {Component, Input} from '@angular/core';
import {Exercise} from '../../../models/exercise.model';
import {NgForOf} from '@angular/common';
import {ExerciseComponent} from '../exercise/exercise.component';

@Component({
  selector: 'app-exercises',
  imports: [
    NgForOf,
    ExerciseComponent
  ],
  templateUrl: './exercises.component.html',
  standalone: true,
  styleUrl: './exercises.component.css'
})
export class ExercisesComponent {
  @Input() exercises: Exercise[] | null = null;
  @Input() editMode: boolean = false;
}
