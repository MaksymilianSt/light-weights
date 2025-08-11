import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TrainingPlanPublication} from '../../models/training-plan-publication.model';
import {AutosizeModule} from 'ngx-autosize';
import {BlocksComponent} from '../../../plans/components/training-plan/blocks/blocks.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {PlanScale} from '../../models/plan-scale.model';
import {PlanPublicationService} from '../../services/plan-publication-service';
import {TrainingPublicationComponent} from '../training-publication/training-publication.component';
import {AuthService} from '../../../auth/services/auth.service';
import {User} from '../../../auth/models/user-model';

@Component({
  selector: 'app-training-plan-publication',
  imports: [
    AutosizeModule,
    BlocksComponent,
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    DatePipe,
    TrainingPublicationComponent
  ],
  templateUrl: './training-plan-publication.component.html',
  standalone: true,
  styleUrl: './training-plan-publication.component.css'
})
export class TrainingPlanPublicationComponent implements OnInit {
  planPublication!: TrainingPlanPublication;
  scale!: PlanScale;
  user: User | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private planPublicationService: PlanPublicationService,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
    this.planPublication = this.route.snapshot.data['trainingPlanPublication'];
    this.scale = {
      weightScale: 1.0,
      planStartDate: new Date().toISOString().split('T')[0]
    };
    this.scale.dayOffSet = this.calculateDayOffSet();
  }

  onDownloadPlan() {
    this.planPublicationService.downloadPlan(this.planPublication.id, this.scale).subscribe(downloadedPlan => {
      this.router.navigate(['/plans', downloadedPlan.id]);
    })
  }

  onDelete() {
    if (confirm('Are you sure you want to delete this plan publication?')) {
      this.planPublicationService.deletePlanPublication(this.planPublication.id).subscribe(() => {
        this.router.navigate(['/..']);
      })
    }
  }

  protected calculateDayOffSet(): number {
    const startDay = new Date(this.scale.planStartDate);
    if (!this.planPublication?.trainings?.length) {
      return 0;
    }

    const firstDateStr = this.planPublication.trainings.reduce((min, training) =>
        new Date(training.date) < new Date(min) ? training.date : min,
      this.planPublication.trainings[0].date
    );

    const firstDate = new Date(firstDateStr);
    const diffMs = startDay.setHours(0, 0, 0, 0) - firstDate.setHours(0, 0, 0, 0);

    return Math.floor(diffMs / (1000 * 60 * 60 * 24));
  }

}
