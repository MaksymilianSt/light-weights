import {Component, OnDestroy, OnInit, QueryList, ViewChildren} from '@angular/core';
import {TrainingExecution} from '../../models/training-execution.model';
import {ActivatedRoute, Router} from '@angular/router';
import {AutosizeModule} from 'ngx-autosize';
import {ExercisesComponent} from '../../../plans/components/training/exercises/exercises.component';
import {DatePipe, NgIf, TitleCasePipe} from '@angular/common';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ExecutionExercisesComponent} from './execution-exercises/execution-exercises.component';
import {debounceTime, interval, Subscription} from 'rxjs';
import {TrainingExecutionService} from '../../services/training-execution-service';
import {ExecutionExerciseComponent} from './execution-exercise/execution-exercise.component';
import {TimeUtils} from '../../../utils/time-utils';

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
export class TrainingExecutionComponent implements OnInit, OnDestroy {
  @ViewChildren(ExecutionExerciseComponent) exerciseComponents!: QueryList<ExecutionExerciseComponent>;
  execution!: TrainingExecution;
  executionForm!: FormGroup;
  formChangesSub?: Subscription;
  editMode: boolean = false;
  trainingTime: string = '00:00:00';
  trainingTimerSub?: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private executionService: TrainingExecutionService,
  ) {
  }

  ngOnInit(): void {
    this.execution = this.route.snapshot.data['trainingExecution'];
    this.editMode = this.execution.finishDate === null;
    this.initForm();
    this.setAutoSave();

    if(this.editMode){
      this.startTrainingTimer();
    }else {
      this.trainingTime = TimeUtils.calculateElapsedTime(this.execution.startDate, this.execution.finishDate!);
    }
  }

  private setAutoSave() {
    this.formChangesSub = this.executionForm.get('notes')?.valueChanges
      .pipe(debounceTime(3500))
      .subscribe(value => {
        const toUpdate: TrainingExecution = {...this.execution, notes: value};

        this.executionService.updateTrainingExecution(toUpdate).subscribe(updatedExecution => {
          this.updateExecution(toUpdate);
        });
      });
  }

  private updateExecution(updatedExecution: TrainingExecution) {
    this.execution.notes = updatedExecution.notes;
  }

  private initForm(): void {
    this.executionForm = this.fb.group({
        notes: [this.execution.notes],
      },
    );
  }

  finishExecution() {
    if (confirm('Are you sure you want to finish this training execution? Changes will not be possible after this operation')) {
      this.executionService.finishTrainingExecutionById(this.execution.id).subscribe(finishedExecution => {
        this.removeAllSubscriptions();
        this.execution = finishedExecution;
        this.editMode = false;
      })
    }
  }

  deleteExecution() {
    if (confirm('Are you sure you want to delete this training execution?')) {
      this.executionService.deleteTrainingExecutionById(this.execution.id!).subscribe(() => {
        this.removeAllSubscriptions();
        this.router.navigate(['executions']);
      })
    }
  }

  private startTrainingTimer(){
    this.trainingTimerSub = interval(1000).subscribe(() => {
      this.trainingTime = TimeUtils.calculateElapsedTime(this.execution.startDate, TimeUtils.getCurrentIsoDate());
    })
  }

  private removeAllSubscriptions(): void {
    this.removeAutoSaveSubscriptions();
    this.removeTimerSubscriptions();
  }

  private removeAutoSaveSubscriptions() {
    this.formChangesSub?.unsubscribe();
    this.exerciseComponents.forEach(component => component.removeAutoSaveSubscription())
  }

  private removeTimerSubscriptions() {
    this.trainingTimerSub?.unsubscribe();
  }

  ngOnDestroy(): void {
    this.removeAllSubscriptions();
  }
}
