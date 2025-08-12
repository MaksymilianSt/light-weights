import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TrainingPlanPublicationPreview} from '../../models/training-plan-publication-preview.model';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {PlanPublicationPreviewComponent} from './plan-publication-preview/plan-publication-preview.component';

@Component({
  selector: 'app-training-plan-publication-list',
  imports: [
    NgForOf,
    DatePipe,
    FormsModule,
    NgIf,
    PlanPublicationPreviewComponent
  ],
  templateUrl: './training-plan-publication-list.component.html',
  standalone: true,
  styleUrl: './training-plan-publication-list.component.css'
})
export class TrainingPlanPublicationListComponent implements OnInit {
  planPublications!: TrainingPlanPublicationPreview[];
  filteredPlanPublications!: TrainingPlanPublicationPreview[];
  searchText: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.planPublications = this.route.snapshot.data['trainingPlanPublications'];
    this.filteredPlanPublications = this.planPublications;
  }

  filterPlans() {
    if (!this.searchText) {
      this.filteredPlanPublications = this.planPublications;
      return;
    }
    const lowerSearch = this.searchText.toLowerCase();

    this.filteredPlanPublications = this.planPublications.filter(plan =>
      plan.name.toLowerCase().includes(lowerSearch) ||
      plan.category.toLowerCase().includes(lowerSearch) ||
      plan.difficultyLvl.toLowerCase().includes(lowerSearch)
    );
  }

}
