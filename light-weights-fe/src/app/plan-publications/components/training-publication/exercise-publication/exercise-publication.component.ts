import {Component, Input} from '@angular/core';
import {Exercise} from '../../../../plans/models/exercise.model';
import {NgForOf, NgIf} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PlanScale} from '../../../models/plan-scale.model';

@Component({
  selector: 'app-exercise-publication',
  imports: [
    NgForOf,
    FormsModule,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './exercise-publication.component.html',
  standalone: true,
  styleUrl: './exercise-publication.component.css'
})
export class ExercisePublicationComponent {
  @Input() exercise!: Exercise;
  @Input() scale!: PlanScale;
  protected readonly Math = Math;
}
