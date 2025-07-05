import {Component, OnInit} from '@angular/core';
import {TrainingPlan} from './models/training-preview';
import {ActivatedRoute} from '@angular/router';
import {AutosizeModule} from 'ngx-autosize';
import {FormsModule} from '@angular/forms';
import {BlockComponent} from './block/block.component';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-training-plan',
  standalone: true,
  imports: [AutosizeModule, FormsModule, BlockComponent, NgForOf, NgIf],
  templateUrl: './training-plan.component.html',
  styleUrl: './training-plan.component.css'
})
export class TrainingPlanComponent implements OnInit {
  plan: TrainingPlan | null = null;
  editMode: boolean = false;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.plan = this.route.snapshot.data['trainingPlan'];
  }

}
