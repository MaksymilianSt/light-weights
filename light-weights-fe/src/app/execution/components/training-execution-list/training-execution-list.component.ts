import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {DatePipe, NgClass, NgForOf, NgIf} from '@angular/common';
import {TrainingExecutionPreview} from '../../models/training-execution-preview.model';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-training-execution-list',
  imports: [
    RouterLink,
    NgIf,
    NgForOf,
    DatePipe,
    NgClass,
    FormsModule
  ],
  templateUrl: './training-execution-list.component.html',
  standalone: true,
  styleUrl: './training-execution-list.component.css'
})
export class TrainingExecutionListComponent implements OnInit {
  executions!: TrainingExecutionPreview[];
  filteredExecutions!: TrainingExecutionPreview[];

  mobile: boolean = this.isMobile();
  searchText: string | null = null;

  constructor(private route: ActivatedRoute,) {
  }

  ngOnInit(): void {
    this.executions = this.route.snapshot.data['trainingExecutions'];
    this.filteredExecutions = this.executions;
  }

  filterExecutions() {
    if (!this.searchText) {
      this.filteredExecutions = this.executions;
      return;
    }
    const lowerSearch = this.searchText.toLowerCase();

    this.filteredExecutions = this.executions.filter(execution =>
      execution.trainingName.toLowerCase().includes(lowerSearch) ||
      execution.planName.toLowerCase().includes(lowerSearch) ||
      execution.startDate.toLowerCase().includes(lowerSearch) ||
      execution.completionPercentage.toString().includes(lowerSearch)
    );
  }

  private isMobile(): boolean {
    return window.innerWidth <= 768
  }
}
