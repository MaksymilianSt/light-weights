import {Component, Input, OnInit} from '@angular/core';
import {TrainingExerciseExecution} from '../../../models/training-exercise-execution.model';
import {AutosizeModule} from 'ngx-autosize';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {TrainingSetExecution} from '../../../models/training-set-execution.model';
import {debounceTime, Subscription} from 'rxjs';
import {TrainingExerciseExecutionService} from '../../../services/training-exercise-execution-service';
import {ActivatedRoute} from '@angular/router';

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
export class ExecutionExerciseComponent implements OnInit {
  @Input() exercise!: TrainingExerciseExecution;
  @Input() editMode = false;

  exerciseExecutionForm!: FormGroup;
  formChangesSub?: Subscription;
  afterSave: boolean = false;

  constructor(
    private fb: FormBuilder,
    private executionExerciseService: TrainingExerciseExecutionService,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    this.exerciseExecutionForm = this.fb.group({
      id: [this.exercise!.id],
      name: [this.exercise!.name],
      notes: [this.exercise!.notes],
      sequence: [this.exercise!.sequence],
      done: [this.exercise!.done],
      sets: this.fb.array(this.exercise!.trainingSetExecutions.map(set => this.createSetGroup(set)))
    });

    if (this.editMode) {
      this.setAutoSave();
    }
  }

  private setAutoSave() {
    this.formChangesSub = this.exerciseExecutionForm.valueChanges
      .pipe(debounceTime(3500))
      .subscribe(value => {
        this.updateExercise(value);
      });
  }

  private updateExercise(value: any) {
    const executionId = this.route.snapshot.params['executionId'];
    const toUpdate: TrainingExerciseExecution = {
      id: value.id,
      notes: value.notes,
      sequence: value.sequence,
      done: value.done,
      trainingSetExecutions: value.sets as TrainingSetExecution[]
    } as TrainingExerciseExecution;

    this.executionExerciseService.updateTrainingExerciseExecution(executionId, toUpdate).subscribe(updatedExecution => {
      this.exercise = updatedExecution;
      this.initForm();
      this.setAfterSaveIcon();
    })
  }

  private setAfterSaveIcon() {
    this.afterSave = true;
    setTimeout(() => {
      this.afterSave = false;
    }, 2000)
  }

  private createSetGroup(set: TrainingSetExecution): FormGroup {
    return this.fb.group({
      id: [set.id],
      sequence: [set.sequence, [Validators.required, Validators.min(1)]],
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

  removeAutoSaveSubscription() {
    this.formChangesSub!.unsubscribe();
  }
}

