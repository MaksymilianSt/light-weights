import {Component, OnInit} from '@angular/core';
import {TrainingExecution} from '../../models/training-execution.model';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-training-execution',
  imports: [],
  templateUrl: './training-execution.component.html',
  standalone: true,
  styleUrl: './training-execution.component.css'
})
export class TrainingExecutionComponent implements OnInit{
  execution!: TrainingExecution;

  constructor(private route: ActivatedRoute,) {}


  ngOnInit(): void {
      this.execution = this.route.snapshot.data['trainingExecution'];
  }



}
