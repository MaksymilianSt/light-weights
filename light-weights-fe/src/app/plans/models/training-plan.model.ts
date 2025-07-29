import {TrainingBlock} from './training-block.model';

export interface TrainingPlan {
  id: number;
  name: string;
  description: string;
  category: string;
  difficultyLvl: string;
  goal: string;
  blocks: TrainingBlock[];

  createdAt: string;
  updatedAt: string;
}
