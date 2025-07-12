import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {Training} from '../../models/training.model';
import {AutosizeModule} from 'ngx-autosize';
import {BlockComponent} from '../training-plan/block/block.component';
import {FormsModule} from '@angular/forms';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {ExercisesComponent} from './exercises/exercises.component';

@Component({
  selector: 'app-training',
  imports: [
    AutosizeModule,
    FormsModule,
    NgIf,
    DatePipe,
    ExercisesComponent
  ],
  templateUrl: './training.component.html',
  standalone: true,
  styleUrl: './training.component.css'
})
export class TrainingComponent implements OnInit {
  training!: Training;
  editMode: boolean = false

  constructor(private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.training = this.route.snapshot.data['training'];
  }

  backToPlan() {
    this.router.navigate(['../../../..'], { relativeTo: this.route });
  }
}
