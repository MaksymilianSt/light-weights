import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Training} from '../../models/training.model';
import {AutosizeModule} from 'ngx-autosize';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {DatePipe, NgClass, NgIf} from '@angular/common';
import {ExercisesComponent} from './exercises/exercises.component';
import {TrainingService} from '../../services/training-service';
import {TrainingBlock} from '../../models/training-block.model';
import {TrainingValidator} from '../../validators/training.validator';

@Component({
  selector: 'app-training',
  imports: [
    AutosizeModule,
    FormsModule,
    NgIf,
    DatePipe,
    ExercisesComponent,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './training.component.html',
  standalone: true,
  styleUrl: './training.component.css'
})
export class TrainingComponent implements OnInit {
  training!: Training;
  block!: TrainingBlock;

  editMode: boolean = false
  createMode: boolean = false
  message: string | null = null;

  trainingForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private trainingService: TrainingService
  ) {
  }

  ngOnInit(): void {
    this.block = this.route.snapshot.data['block'];
    this.training = this.route.snapshot.data['training'];

    this.createMode = this.training == null;
    this.editMode = this.training == null || this.route.snapshot.queryParamMap.get('edit') === 'true';

    this.initForm();
  }

  initForm() {
    this.training = this.training ?? {
      name: '',
      date: this.block.start,
      description: '',
    } as Training;


    this.trainingForm = this.fb.group({
        name: [this.training.name ?? '', Validators.required],
        date: [this.training.date, [Validators.required, TrainingValidator.trainingDateInBlockRange(this.block)]],
        description: [this.training.description ?? ''],
      },
    );
  }

  onSave() {
    const planId = this.route.snapshot.params['planId'];
    const blockId = this.route.snapshot.params['blockId'];

    if (this.createMode) {
      this.createTraining(planId, blockId);
    } else {
      this.updateTraining(planId, blockId);
    }
  }

  createTraining(planId: number, blockId: number): void {
    const formValue = this.trainingForm.value;
    const toCreate = {
      id: this.training!.id,
      name: formValue.name,
      date: formValue.date,
      description: formValue.description,
    } as Training;

    this.trainingService.createTraining(planId, blockId, toCreate).subscribe(created => {
      this.router.navigate(['..', created.id], {relativeTo: this.route})
    })
  }

  updateTraining(planId: number, blockId: number) {
    const formValue = this.trainingForm.value;
    const toUpdate = {
      id: this.training!.id,
      name: formValue.name,
      date: formValue.date,
      description: formValue.description,
    } as Training;

    this.trainingService.updateTraining(planId, blockId, toUpdate).subscribe(updated => {
      this.training = updated;
      this.editMode = false;
      this.initForm();
      this.addMessage('Changes successfully applied');
    })
  }

  backToPlan() {
    this.router.navigate(['../../../..'], {relativeTo: this.route});
  }


  toggleMode() {
    if (this.editMode) {
      this.initForm();
    }
    this.editMode = !this.editMode;
  }

  deleteTraining() {
    if (confirm('Are you sure you want to delete this training?')) {
      const planId = this.route.snapshot.params['planId'];
      const blockId = this.route.snapshot.params['blockId'];
      const trainingId = this.training.id

      this.trainingService.deleteTrainingById(planId, blockId, trainingId).subscribe(() => {
        this.backToPlan();
      })
    }
  }

  private addMessage(message: string): void {
    this.message = message;

    const twoSeconds = 1000 * 2;
    setTimeout(() => {
      this.message = null;
    }, twoSeconds);
  }

}
