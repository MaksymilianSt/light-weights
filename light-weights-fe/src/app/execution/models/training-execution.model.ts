import {TrainingExerciseExecution} from './training-exercise-execution.model';

export interface TrainingExecution {
  id: number;
  notes: string | null;
  startDate: string;
  finishDate: string | null;
  referencedTrainingName: string;
  trainingExerciseExecutions: TrainingExerciseExecution[];
}
