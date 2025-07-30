import {Component, Input, OnInit} from '@angular/core';
import {TrainingExerciseExecution} from '../../../models/training-exercise-execution.model';
import {AutosizeModule} from 'ngx-autosize';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {TrainingSetExecution} from '../../../models/training-set-execution.model';

@Component({
  selector: 'app-execution-exercise',
  imports: [
    AutosizeModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './execution-exercise.component.html',
  standalone: true,
  styleUrl: './execution-exercise.component.css'
})
export class ExecutionExerciseComponent  implements OnInit{
  @Input() exercise!: TrainingExerciseExecution;
  @Input() editMode = false;

  exerciseExecutionForm!: FormGroup;

  constructor(private fb: FormBuilder,) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.exerciseExecutionForm = this.fb.group({
      id: [this.exercise!.id],
      name: [this.exercise!.name],
      notes: [this.exercise!.notes],
      sequence: [this.exercise!.sequence],
      done: [this.exercise!.done],
      sets: this.fb.array(this.exercise!.trainingSetExecutions.map(set => this.createSetGroup(set)))
    });
  }

  createSetGroup(set: TrainingSetExecution): FormGroup {
    return this.fb.group({
      id: [set.id],
      sequence: [set.sequence],
      repetitions: [set.repetitions, [Validators.required, Validators.min(1)]],
      weight: [set.weight, [Validators.required, Validators.min(1), Validators.max(9999)]],
      tempo: [set.tempo, Validators.maxLength(7)],
      rpe: [set.rpe, [Validators.required, Validators.min(1), Validators.max(10)]],
      executedAt: [set.executedAt]
    });
  }

  get sets(): FormArray {
    return this.exerciseExecutionForm.get('sets') as FormArray;
  }
}

