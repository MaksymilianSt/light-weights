import {Component, OnInit} from '@angular/core';
import {TrainingPlan} from '../../models/training-preview.model';
import {ActivatedRoute, Router} from '@angular/router';
import {AutosizeModule} from 'ngx-autosize';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {BlockComponent} from './block/block.component';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {BlocksComponent} from './blocks/blocks.component';
import {PlanService} from '../../services/plan-service';

@Component({
  selector: 'app-training-plan',
  standalone: true,
  imports: [
    AutosizeModule,
    FormsModule,
    BlockComponent,
    NgForOf,
    NgIf,
    BlocksComponent,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './training-plan.component.html',
  styleUrl: './training-plan.component.css'
})
export class TrainingPlanComponent implements OnInit {
  plan: TrainingPlan | null = null;
  categories: string[] = [];
  difficultyLvls: string[] = [];
  editMode: boolean = false;
  createMode: boolean = false;
  planForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private planService: PlanService
  ) {
  }

  ngOnInit(): void {
    this.planService.getCategories().subscribe(categories => this.categories = categories);
    this.planService.getDifficultyLvls().subscribe(lvls => this.difficultyLvls = lvls);

    this.plan = this.route.snapshot.data['trainingPlan'];
    this.editMode = this.route.snapshot.queryParamMap.get('edit') === 'true';

    if (this.plan === null) {
      this.createMode = true;
      this.editMode = true;
    }

    this.planForm = this.fb.group({
      name: [this.plan?.name ?? '', Validators.required],
      description: [this.plan?.description ?? ''],
      category: [this.plan?.category ?? '', Validators.required],
      goal: [this.plan?.goal ?? ''],
      difficultyLvl: [this.plan?.difficultyLvl, Validators.required],
    });
  }

  onSave() {
    const formValue = this.planForm.value;

    const toSave = {
      name: formValue.name,
      description: formValue.description,
      category: formValue.category,
      goal: formValue.goal,
      difficultyLvl: formValue.difficultyLvl,
    } as TrainingPlan;

    if (this.createMode) {
      this.planService.createPlan(toSave).subscribe((created) => {
        this.router.navigate(['plans', created.id]);
      });
    } else {
      this.planService.updatePlan(this.plan!.id, toSave).subscribe((updated) => {
        this.editMode = false;
      })
    }
  }

}
