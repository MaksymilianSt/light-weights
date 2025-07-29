import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';
import {TrainingPreview} from '../models/training-preview.model';

export class BlockValidator {

  public static endDateAfterStartDateValidator(): ValidatorFn {
    return (group: AbstractControl): ValidationErrors | null => {
      const start = group.get('start')?.value;
      const end = group.get('end')?.value;

      if (!start || !end) {
        return null;
      }
      const startDate = new Date(start);
      const endDate = new Date(end);

      return endDate > startDate ? null : {endDateAfterStartDate: true};
    }
  }

  public static startDateAfterTrainingValidator(blockTrainings: TrainingPreview[]): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const start = control.value;

      if (!start || !blockTrainings) {
        return null;
      }

      return blockTrainings.find(training => training.date < start)
        ? {startDateAfterTraining: true}
        : null;
    }
  }

  public static endDateBeforeTrainingValidator(blockTrainings: TrainingPreview[]): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const end = control.value;

      if (!end || !blockTrainings) {
        return null;
      }

      return blockTrainings.find(training => training.date > end)
        ? {endDateBeforeTraining: true}
        : null;
    }
  }
}
