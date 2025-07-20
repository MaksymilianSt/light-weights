import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TrainingBlock} from '../../../models/training-plan.model';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';
import {DatePipe, DecimalPipe, NgClass, NgForOf, NgIf} from '@angular/common';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {BlockService} from '../../../services/block-service';

@Component({
  selector: 'app-block',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    DatePipe,
    NgIf,
    RouterLink,
    DecimalPipe,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './block.component.html',
  styleUrl: './block.component.css'
})
export class BlockComponent implements OnInit {

  @Output() setReadMode = new EventEmitter();
  @Output() updateBlockList = new EventEmitter<TrainingBlock>();
  @Output() deleteBlockFromList = new EventEmitter<number>();

  @Input() block: TrainingBlock | null = null;
  @Input() editMode: boolean = false;
  @Input() createMode: boolean = false;

  blockForm!: FormGroup;
  message: string | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private blockService: BlockService,
  ) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.block = this.block ?? {
      name: '',
      start: new Date().toISOString().split('T')[0],
      end: new Date(Date.now() + 7 * 24 * 60 * 60 * 1000).toISOString().split('T')[0],
      description: '',
    } as TrainingBlock;


    this.blockForm = this.fb.group({
        name: [this.block.name ?? '', Validators.required],
        start: [this.block.start, Validators.required],
        end: [this.block.end, Validators.required],
        description: [this.block.description ?? ''],
      },
      {validators: this.endDateAfterStartDateValidator}
    );
  }

  onSave(): void {
    if (this.createMode) {
      this.createBlock()
    } else {
      this.updateBlock()
    }
    this.setReadMode.emit();

  }

  createBlock() {
    const planId = this.route.snapshot.params['planId'];

    const formValue = this.blockForm.value;
    const toCreate = {
      name: formValue.name,
      description: formValue.description,
      start: formValue.start,
      end: formValue.end
    } as TrainingBlock;
    this.blockService.createBlock(planId, toCreate).subscribe(created => {
      this.addMessage('Changes successfully applied');
      this.block = created;
      this.createMode = false;
      this.initForm();
      this.updateBlockList.emit(created);
    })

  }

  updateBlock() {
    const planId = this.route.snapshot.params['planId'];

    const formValue = this.blockForm.value;
    const toUpdate = {
      id: this.block!.id,
      name: formValue.name,
      description: formValue.description,
      start: formValue.start,
      end: formValue.end
    } as TrainingBlock;

    this.blockService.updateBlock(planId, toUpdate).subscribe((updated) => {
      this.addMessage('Changes successfully applied');
      this.block = updated;
      this.initForm();
    })

  }

  deleteBlock() {
    if (confirm('Are you sure you want to delete this block?')) {
      const planId = this.route.snapshot.params['planId'];
      this.blockService.deleteBlock(planId, this.block!.id).subscribe(() => {
        this.deleteBlockFromList.emit(this.block?.id);
      })
    }
  }

  calculateWeeks(start: string | Date, end: string | Date): number | '?' {
    const startDate = new Date(start);
    const endDate = new Date(end);

    if (isNaN(startDate.getTime()) || isNaN(endDate.getTime())) {
      return '?';
    }

    const diffInMs = endDate.getTime() - startDate.getTime();
    const oneWeekInMs = 7 * 24 * 60 * 60 * 1000;

    return Math.ceil(diffInMs / oneWeekInMs);
  }

  calculateTrainingsCompletion() {
    const completedTrainings = this.block!.trainings
      .filter(training => training.done)
      .length;
    const allTrainingsSize = this.block!.trainings.length;

    return (completedTrainings / allTrainingsSize) * 100;
  }


  private addMessage(message: string): void {
    this.message = message;

    const twoSeconds = 1000 * 2;
    setTimeout(() => {
      this.message = null;
    }, twoSeconds);
  }

  private endDateAfterStartDateValidator: ValidatorFn = (group: AbstractControl): ValidationErrors | null => {
    const start = group.get('start')?.value;
    const end = group.get('end')?.value;

    if (!start || !end) {
      return null;
    }
    const startDate = new Date(start);
    const endDate = new Date(end);

    return endDate > startDate ? null : {endDateAfterStartDate: true};
  }

}
