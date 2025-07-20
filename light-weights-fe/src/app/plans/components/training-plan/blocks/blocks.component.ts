import {Component, Input, QueryList, ViewChildren} from '@angular/core';
import {TrainingBlock} from '../../../models/training-plan.model';
import {BlockComponent} from '../block/block.component';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-blocks',
  imports: [
    BlockComponent,
    NgForOf,
    NgIf
  ],
  templateUrl: './blocks.component.html',
  standalone: true,
  styleUrl: './blocks.component.css'
})
export class BlocksComponent {
  @ViewChildren(BlockComponent) blockComponents!: QueryList<BlockComponent>;
  @Input() blocks!: TrainingBlock[];

  editMode: boolean = false;
  newBlock: boolean = false;

  setReadMode() {
    this.editMode = false;
    this.blockComponents.forEach(component => component.initForm());
  }

  updateBlockList(newBlock: TrainingBlock) {
    this.blocks = [newBlock, ...this.blocks]
      .sort((a, b) => a.start < b.start ? -1 : 1);
    this.newBlock = false;
  }

  deleteBlock(blockId: number) {
    this.blocks = this.blocks.filter(block => block.id !== blockId);
    this.editMode = false;
  }

  toggleMode() {
    if (this.editMode) {
      this.setReadMode();
      this.newBlock = false;
    } else {
      this.editMode = true;
    }
  }

}
