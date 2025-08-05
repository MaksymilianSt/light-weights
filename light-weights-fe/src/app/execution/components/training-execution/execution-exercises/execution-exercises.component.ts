import {Component, Input, OnInit} from '@angular/core';
import {TrainingExerciseExecution} from '../../../models/training-exercise-execution.model';
import {ExecutionExerciseComponent} from '../execution-exercise/execution-exercise.component';
import {DecimalPipe, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-execution-exercises',
    imports: [
        ExecutionExerciseComponent,
        NgForOf,
        DecimalPipe,
        NgIf
    ],
  templateUrl: './execution-exercises.component.html',
  standalone: true,
  styleUrl: './execution-exercises.component.css'
})
export class ExecutionExercisesComponent implements OnInit{
  @Input() exercises!: TrainingExerciseExecution[];
  @Input() editMode = false;


  ngOnInit(): void {
  }

  updateExercise(exercise: TrainingExerciseExecution): void {
    const index = this.exercises.findIndex(e => e.id === exercise.id);
    if (index !== -1) {
      this.exercises[index] = exercise;
    }
  }

  calculateExercisesCompletion() {
    const completedExercises = this.exercises
      .filter(exercise => exercise.done)
      .length;
    const allExercisesSize = this.exercises.length;

    return (completedExercises / allExercisesSize) * 100;
  }
}
