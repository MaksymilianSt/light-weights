import {Set} from './set.model';

export interface Exercise {
  id: number;
  name: string;
  description: string;
  sequence: number;
  sets: Set[];
}
