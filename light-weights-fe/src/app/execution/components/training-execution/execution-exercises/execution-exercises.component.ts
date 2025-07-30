import {Component, Input, OnInit} from '@angular/core';
import {TrainingExerciseExecution} from '../../../models/training-exercise-execution.model';
import {ExecutionExerciseComponent} from '../execution-exercise/execution-exercise.component';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-execution-exercises',
  imports: [
    ExecutionExerciseComponent,
    NgForOf
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
}
