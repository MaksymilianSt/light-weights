import {Component, Input} from '@angular/core';
import {TrainingBlock} from '../../../models/training-plan.model';
import {BlockComponent} from '../block/block.component';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-blocks',
  imports: [
    BlockComponent,
    NgForOf
  ],
  templateUrl: './blocks.component.html',
  standalone: true,
  styleUrl: './blocks.component.css'
})
export class BlocksComponent {
  @Input() blocks: TrainingBlock[] | null = null;
  @Input() editMode: boolean = false;
}
