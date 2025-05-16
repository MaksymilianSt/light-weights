import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';

export class RegistrationValidator {
  static strongPassword(): ValidatorFn {
    const strongPasswordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-=+{};:,<.>]).{8,}$/;

    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (!value) return null;

      return strongPasswordRegex.test(value) ? null : {weakPassword: true};
    };
  }

  static matchPasswords(passwordField: string, confirmField: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.get(passwordField)?.value;
      const confirm = control.get(confirmField)?.value;

      if (password !== confirm) {
        control.get(confirmField)?.setErrors({mismatch: true});
        return {mismatch: true};
      }

      return null;
    };
  }
}

