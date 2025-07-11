import {TrainingPreview} from './training-block.model';

export interface TrainingBlock {
  id: number;
  name: string;
  description: string;
  start: string;
  end: string;
  trainings: TrainingPreview[];
}
