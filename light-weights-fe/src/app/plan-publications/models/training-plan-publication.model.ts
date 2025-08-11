import {Training} from '../../plans/models/training.model';

export interface TrainingPlanPublication {
  id: number;
  name: string;
  description: string;
  category: string;
  difficultyLvl: string;
  goal: string;
  publishedAt: string;
  authorEmail: string;

  trainings: Training[];
}
