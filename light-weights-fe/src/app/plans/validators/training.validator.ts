import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';
import {TrainingBlock} from '../models/training-plan.model';

export class TrainingValidator {

  public static trainingDateInBlockRange(block: TrainingBlock): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const trainingDate = control.value;

      return block.start <= trainingDate && trainingDate <= block.end
        ? null
        : {endDateAfterStartDate: true};
    }
  }

}
