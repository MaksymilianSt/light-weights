import {PlanOpinion} from './plan-opinion.model';

export interface TrainingPlanPublicationPreview {
  id: number,
  name: string,
  category: string,
  difficultyLvl: string,
  goal: string,
  publishedAt: string
  authorEmail: string
  downloads: string
  start: string
  end: string
  opinions: PlanOpinion[];
}
