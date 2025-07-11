import {Component, HostListener, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {TrainingPlanListModel} from '../../models/training-plan-list.model';
import {NgForOf, NgIf} from '@angular/common';
import {ActivatedRoute, RouterLink} from '@angular/router';

@Component({
  selector: 'app-training-plan-list',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    RouterLink,
    NgIf
  ],
  templateUrl: './training-plan-list.component.html',
  styleUrl: './training-plan-list.component.css'
})
export class TrainingPlanListComponent implements OnInit {
  private planList: TrainingPlanListModel[] = [];
  filteredPlanList: TrainingPlanListModel[] = [];
  mobile: boolean = this.isMobile();
  searchText: string | null = null;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.fetchPlans();
  }

  filterPlans() {
    if (!this.searchText) {
      this.filteredPlanList = this.planList;
      return;
    }
    const lowerSearch = this.searchText.toLowerCase();

    this.filteredPlanList = this.planList.filter(plan =>
      plan.name.toLowerCase().includes(lowerSearch) ||
      plan.category.toLowerCase().includes(lowerSearch) ||
      plan.difficultyLvl.toLowerCase().includes(lowerSearch)
    );
  }

  private fetchPlans() {
    this.planList = this.route.snapshot.data['plans'];
    this.filteredPlanList = this.planList;
  }

  @HostListener('window:resize')
  private onResize() {
    this.mobile = this.isMobile();
  }

  private isMobile(): boolean {
    return window.innerWidth <= 768
  }
}
