import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {TrainingExerciseExecution} from '../../../models/training-exercise-execution.model';
import {AutosizeModule} from 'ngx-autosize';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {TrainingSetExecution} from '../../../models/training-set-execution.model';
import {debounceTime, interval, Subscription} from 'rxjs';
import {TrainingExerciseExecutionService} from '../../../services/training-exercise-execution-service';
import {ActivatedRoute} from '@angular/router';
import {TimeUtils} from '../../../../utils/time-utils';

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
export class ExecutionExerciseComponent implements OnInit, OnDestroy {
  @Input() exercise!: TrainingExerciseExecution;
  @Input() editMode = false;

  exerciseExecutionForm!: FormGroup;
  formChangesSub?: Subscription;
  afterSave: boolean = false;

  exerciseTime: string = '00:00';
  _exerciseTimerSub?: Subscription;

  avgBreakTime: string = '00:00';
  _avgBreakTimeSub?: Subscription;

  constructor(
    private fb: FormBuilder,
    private executionExerciseService: TrainingExerciseExecutionService,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.initForm();
    this.setUpTimes()
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

  private createSetGroup(set: TrainingSetExecution): FormGroup {
    return this.fb.group({
      id: [set.id],
      sequence: [set.sequence, [Validators.required, Validators.min(1)]],
      repetitions: [set.repetitions, [Validators.required, Validators.min(1)]],
      weight: [set.weight, [Validators.required, Validators.min(1), Validators.max(9999)]],
      tempo: [set.tempo, Validators.maxLength(7)],
      rpe: [set.rpe, [Validators.required, Validators.min(1), Validators.max(10)]],
      done: [set.executedAt],
    });
  }

  get sets(): FormArray {
    return this.exerciseExecutionForm.get('sets') as FormArray;
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
      trainingSetExecutions: value.sets.map((formSet: { done: boolean; id: number }) => ({
        ...formSet,
        executedAt: formSet.done
          ? (
            this.exercise.trainingSetExecutions.find(set => set.id == formSet.id)?.executedAt
              ?? new Date()
            )
          : null
      })) as TrainingSetExecution[]
    } as TrainingExerciseExecution;

    this.executionExerciseService.updateTrainingExerciseExecution(executionId, toUpdate).subscribe(updatedExecution => {
      this.exercise = updatedExecution;
      this.initForm();
      this.setAfterSaveIcon();
      this.setUpTimes()
    })
  }

  private setAfterSaveIcon() {
    this.afterSave = true;
    setTimeout(() => {
      this.afterSave = false;
    }, 2000)
  }


  private setUpTimes(){
    this.setUpExerciseTime();
    this.setUpAvgBreakTime();
  }

  private setUpExerciseTime() {
    const firstSetTime = this.getSetFirstExecutedDate()?.toISOString();

    if (this.exercise.done || !this.editMode) {
      const lastSetTime = this.getSetLastExecutedDate()?.toISOString();

      this.exerciseTime = TimeUtils.calculateElapsedTime(firstSetTime!, lastSetTime!)!;
      this._exerciseTimerSub?.unsubscribe();
    } else {
      this.startExerciseTimer(firstSetTime!, null)
    }
  }

  private startExerciseTimer(start: string, end: string | null ) {
    this.exerciseTimerSub = interval(1000).subscribe(() => {
      this.exerciseTime = TimeUtils.calculateElapsedTime(start, end?? TimeUtils.getCurrentIsoDate())!;
    })
  }

  set exerciseTimerSub(sub: Subscription) {
    if (this._exerciseTimerSub) {
      this._exerciseTimerSub.unsubscribe();
    }
    this._exerciseTimerSub = sub;
  }

  private setUpAvgBreakTime() {
    if (this.exercise.done || !this.editMode) {
      this.avgBreakTime = TimeUtils.getAverageBreakTime(this.exercise.trainingSetExecutions) ?? '00:00';
      this._avgBreakTimeSub?.unsubscribe();
    } else {
      this.startAvgBreakTimer();
    }
  }

  private startAvgBreakTimer(){
    this.avgBreakTimeSub = interval(1000).subscribe(() => {
      this.avgBreakTime = TimeUtils.getAverageBreakTime(this.exercise.trainingSetExecutions)?? '00:00';
    })
  }

  set avgBreakTimeSub(sub: Subscription) {
    if (this._avgBreakTimeSub) {
      this._avgBreakTimeSub.unsubscribe();
    }
    this._avgBreakTimeSub = sub;
  }


  calculateCurrentBreakTime(){
    const lastExecutionTime = this.getSetLastExecutedDate()?.toISOString()!;
    return TimeUtils.calculateElapsedTime(lastExecutionTime, TimeUtils.getCurrentIsoDate())?? '00:00';
  }

  calculateSetBreakTime(setControl:  AbstractControl){
    const setId = setControl.get('id')?.value;
    const set = this.exercise.trainingSetExecutions.find(set => set.id === setId);

    if(!set || !set!.executedAt ){
      return '00:00'
    }

    const previousSets = this.exercise.trainingSetExecutions
      .filter(s => s.executedAt && new Date(s.executedAt) < new Date(set!.executedAt!));

    if (!previousSets.length) {
      return '00:00';
    }

    const closestPrevExecutedAt = new Date(Math.max(...previousSets.map(s => new Date(s.executedAt!).getTime())));
    return TimeUtils.calculateElapsedTime(closestPrevExecutedAt.toISOString(), set.executedAt);
  }

  setBreakTimeColor(setGroup: AbstractControl): string {
    const breakTime = TimeUtils.parseTimeStringToSeconds(this.calculateSetBreakTime(setGroup));
    const avg = TimeUtils.parseTimeStringToSeconds(this.avgBreakTime);

    if(!setGroup.get('done')?.value || breakTime == 0) {
      return '';
    }
    if (breakTime < avg * 0.8) {
      return 'break-before-avg';
    }
    if (breakTime > avg * 1.2) {
      return 'break-after-avg';
    }
    return '';
  }


  private getSetFirstExecutedDate(): Date | null {
    const validDates = this.exercise.trainingSetExecutions
      .map(set => set.executedAt)
      .filter(Boolean)
      .map(date => new Date(date!));

    return validDates.length
      ? new Date(Math.min(...validDates.map(d => d.getTime())))
      : null;
  }

  private getSetLastExecutedDate(): Date | null {
    const validDates = this.exercise.trainingSetExecutions
      .map(set => set.executedAt)
      .filter(Boolean)
      .map(date => new Date(date!));

    return validDates.length
      ? new Date(Math.max(...validDates.map(d => d.getTime())))
      : null;
  }


  removeAutoSaveSubscription() {
    this.formChangesSub?.unsubscribe();
    this._exerciseTimerSub?.unsubscribe();
    this._avgBreakTimeSub?.unsubscribe();
  }

  private removeSubscriptions() {
    this.formChangesSub?.unsubscribe();
    this._exerciseTimerSub?.unsubscribe();
    this._avgBreakTimeSub?.unsubscribe();
  }

  ngOnDestroy(): void {
    this.removeSubscriptions();
  }
}
