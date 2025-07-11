import {Exercise} from './exercise.model';

export interface Training {
  id: number;
  name: string;
  description: string;
  date: string;
  executionId: number | null;
  blockName: string;
  exercises: Exercise[];
}
