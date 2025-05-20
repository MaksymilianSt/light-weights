export interface TrainingPlanListModel {
  id: number;
  name: string;
  description: string;
  category: string;
  difficultyLvl: string;

  createdAt: Date  | string;
  updatedAt?: Date | string;
}
