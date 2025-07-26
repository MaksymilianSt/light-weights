import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Exercise} from '../../../models/exercise.model';
import {Set} from '../../../models/set.model';
import {DatePipe, NgClass, NgForOf, NgIf} from '@angular/common';
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {AutosizeModule} from 'ngx-autosize';
import {ExerciseService} from '../../../services/exercise-service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-exercise',
  imports: [
    DatePipe,
    FormsModule,
    NgForOf,
    AutosizeModule,
    NgClass,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './exercise.component.html',
  standalone: true,
  styleUrl: './exercise.component.css'
})
export class ExerciseComponent implements OnInit {
  @Input() exercise: Exercise | null = null;
  @Input() editMode: boolean = false;
  @Input() createMode: boolean = false;
  @Input() nextSequence!: number;

  @Output() deleteExerciseFromList = new EventEmitter<number>();
  @Output() setReadMode = new EventEmitter();
  @Output() addExerciseToList = new EventEmitter<Exercise>();
  @Output() updateExerciseList = new EventEmitter<Exercise>();

  exerciseForm!: FormGroup;

  message: string | null = null;


  constructor(
    private fb: FormBuilder,
    private exerciseService: ExerciseService,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.exercise = this.exercise ?? {
      name: '',
      description: '',
      sequence: this.nextSequence,
      sets: [] as Set[],
    } as Exercise;

    this.initForm();
  }

  initForm() {
    this.exerciseForm = this.fb.group({
      id: [this.exercise!.id],
      name: [this.exercise!.name, Validators.required],
      description: [this.exercise!.description],
      sequence: [this.exercise!.sequence, [Validators.required, Validators.min(0)]],
      sets: this.fb.array(this.exercise!.sets.map(set => this.createSetGroup(set)))
    });
  }

  createSetGroup(set: Set): FormGroup {
    return this.fb.group({
      id: [set.id],
      sequence: [set.sequence, [Validators.required, Validators.min(0)]],
      repetitions: [set.repetitions, [Validators.required, Validators.min(1)]],
      weight: [set.weight, [Validators.required, Validators.min(1), Validators.max(9999)]],
      tempo: [set.tempo, Validators.maxLength(7)]
    });
  }

  get sets(): FormArray {
    return this.exerciseForm.get('sets') as FormArray;
  }

  addSet(): void {
    this.sets.push(this.createSetGroup({
      id: 0,
      sequence: this.sets.length + 1,
      repetitions: 0,
      weight: 0,
      tempo: ''
    }));
  }

  copySet(set: Set) {
    this.sets.push(this.createSetGroup({
      id: 0,
      sequence: this.sets.length + 1,
      repetitions: set.repetitions,
      weight: set.weight,
      tempo: set.tempo
    }));
  }

  removeSet(index: number): void {
    this.sets.removeAt(index);
  }

  deleteExercise() {
    const planId = this.route.snapshot.params['planId'];
    const blockId = this.route.snapshot.params['blockId'];
    const trainingId = this.route.snapshot.params['trainingId'];

    this.exerciseService.deleteExerciseById(planId, blockId, trainingId, this.exercise!.id).subscribe(() => {
      this.deleteExerciseFromList.emit(this.exercise!.id);
    })
  }

  onSave() {
    const planId = this.route.snapshot.params['planId'];
    const blockId = this.route.snapshot.params['blockId'];
    const trainingId = this.route.snapshot.params['trainingId'];

    const toSave = {
      id: this.exerciseForm.value.id,
      name: this.exerciseForm.value.name,
      description: this.exerciseForm.value.description,
      sequence: this.exerciseForm.value.sequence,
      sets: this.exerciseForm.value.sets.map((set: any) => ({
        id: set.id,
        sequence: set.sequence,
        repetitions: set.repetitions,
        weight: set.weight,
        tempo: set.tempo
      } as Set))
    } as Exercise;

    if (this.createMode) {
      this.createExercise(planId, trainingId, trainingId, toSave);
    } else {
      this.updateExercise(planId, blockId, trainingId, toSave);
    }
  }

  private createExercise(planId: number, blockId: number, trainingId: number, toCreate: Exercise) {
    this.exerciseService.createExercise(planId, blockId, trainingId, toCreate).subscribe(created => {
      this.exercise = created;
      this.setReadMode.emit();
      this.initForm();
      this.addExerciseToList.emit(this.exercise);
    })
  }

  private updateExercise(planId: number, blockId: number, trainingId: number, toUpdate: Exercise) {
    this.exerciseService.updateExercise(planId, blockId, trainingId, toUpdate).subscribe(updated => {
      this.exercise = updated;
      this.initForm();
      this.updateExerciseList.emit(this.exercise);
      this.setReadMode.emit();
      this.addMessage("Changes successfully applied");
    })
  }

  private addMessage(message: string): void {
    this.message = message;

    const twoSeconds = 1000 * 2;
    setTimeout(() => {
      this.message = null;
    }, twoSeconds);
  }

}
