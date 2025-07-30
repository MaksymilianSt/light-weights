import {TrainingExerciseExecution} from './training-exercise-execution.model';

export interface TrainingExecution {
  id: number | null;
  notes: string | null;
  startDate: string | null;
  finishDate: string | null;
  referencedTrainingName: string;
  trainingExerciseExecutions: TrainingExerciseExecution[];
}
