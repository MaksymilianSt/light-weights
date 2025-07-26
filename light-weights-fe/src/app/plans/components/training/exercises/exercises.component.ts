import {Component, Input, QueryList, ViewChildren} from '@angular/core';
import {Exercise} from '../../../models/exercise.model';
import {NgForOf, NgIf} from '@angular/common';
import {ExerciseComponent} from '../exercise/exercise.component';

@Component({
  selector: 'app-exercises',
  imports: [
    NgForOf,
    ExerciseComponent,
    NgIf
  ],
  templateUrl: './exercises.component.html',
  standalone: true,
  styleUrl: './exercises.component.css'
})
export class ExercisesComponent {
  @ViewChildren(ExerciseComponent) exerciseComponents!: QueryList<ExerciseComponent>;

  @Input() exercises: Exercise[] | null = null;
  @Input() editMode: boolean = false;
  @Input() newExercise: boolean = false;

  message: string | null = null;

  toggleMode() {
    if (this.editMode) {
      this.setReadMode();
    } else {
      this.editMode = true;
    }
  }

  setReadMode() {
    this.editMode = false;
    this.newExercise = false;
    this.cleanUnsavedChanges()
  }

  addExercise(newExercise: Exercise) {
    this.exercises = [...this.exercises!, newExercise]
      .sort((e1, e2) => e1.sequence - e2.sequence);
    this.addMessage("New exercise created successfully")
  }

  updateExercises(updated: Exercise) {
    if (!this.exercises) return;

    const index = this.exercises.findIndex(ex => ex.id === updated.id);
    if (index !== -1) {
      this.exercises[index] = updated;
    }
    this.exercises = [...this.exercises].sort((e1, e2) => e1.sequence - e2.sequence);
    this.addMessage("Changes applied successfully")
  }

  deleteExercise(exerciseId: number) {
    this.exercises = this.exercises!
      .filter(exercise => exercise.id !== exerciseId);
    this.setReadMode();
    this.addMessage("Exercise deleted successfully.");
  }

  getLastSequence(): number {
    if (!this.exercises || this.exercises.length === 0) {
      return 0;
    }
    return Math.max(...this.exercises.map(exercise => exercise.sequence));
  }

  private addMessage(message: string): void {
    this.message = message;

    const twoSeconds = 1000 * 2;
    setTimeout(() => {
      this.message = null;
    }, twoSeconds);
  }

  private cleanUnsavedChanges() {
    this.exerciseComponents.forEach(component => component.initForm());
  }

}
