import {TrainingSetExecution} from './training-set-execution.model';

export interface TrainingExerciseExecution {
  id: number;
  name: string | null;
  notes: string | null;
  done: boolean;
  sequence: number;
  trainingSetExecutions: TrainingSetExecution[];
}
