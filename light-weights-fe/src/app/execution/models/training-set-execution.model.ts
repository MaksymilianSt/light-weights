export interface TrainingSetExecution {
  id: number;
  repetitions: number;
  weight: number;
  tempo: string | null;
  rpe: number | null;
  sequence: number;
  executedAt: string | null;
}
