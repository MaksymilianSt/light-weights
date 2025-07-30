import {Component, OnInit} from '@angular/core';
import {TrainingExecution} from '../../models/training-execution.model';
import {ActivatedRoute} from '@angular/router';
import {AutosizeModule} from 'ngx-autosize';
import {ExercisesComponent} from '../../../plans/components/training/exercises/exercises.component';
import {DatePipe, NgIf, TitleCasePipe} from '@angular/common';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ExecutionExercisesComponent} from './execution-exercises/execution-exercises.component';

@Component({
  selector: 'app-training-execution',
  imports: [
    AutosizeModule,
    ExercisesComponent,
    NgIf,
    ReactiveFormsModule,
    DatePipe,
    TitleCasePipe,
    ExecutionExercisesComponent
  ],
  templateUrl: './training-execution.component.html',
  standalone: true,
  styleUrl: './training-execution.component.css'
})
export class TrainingExecutionComponent implements OnInit{
  execution!: TrainingExecution;
  executionForm!: FormGroup;

  editMode: boolean = false;

  constructor(private route: ActivatedRoute, private fb: FormBuilder) {}


  ngOnInit(): void {
      this.execution = this.route.snapshot.data['trainingExecution'];
      this.editMode = this.execution.finishDate === null;
      this.initForm();
  }

  initForm(): void {
    this.executionForm = this.fb.group({
        notes: [this.execution.notes],
      },
    );
  }

}
