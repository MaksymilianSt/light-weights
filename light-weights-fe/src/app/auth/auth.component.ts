import {Component} from '@angular/core';
import {NgClass, NgIf} from '@angular/common';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {RegistrationValidator} from './validators/registration.validator';
import {AuthService} from './services/auth.service';
import {AuthenticationRequest} from './models/authentication-request.model';
import {RegisterRequest} from './models/register-request.model';
import {ValidationErrorResponse} from './models/validation-error-response.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [
    NgIf, ReactiveFormsModule, NgClass
  ],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {
  loginMode: boolean = true;
  loading: boolean = false;
  errorMessage: string | null = null;

  authForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.authForm = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]],
      confirmPassword: [''],
      nickname: [''],
    });
  }

  toggleLoading() {
    this.loading = !this.loading;
  }

  onSubmit() {
    this.toggleLoading();

    if (this.loginMode) {
      this.onAuthenticate();
    } else {
      this.onRegister();
    }
  }

  private onAuthenticate() {
    const loginPayload: AuthenticationRequest = {
      email: this.authForm.get('email')?.value,
      password: this.authForm.get('password')?.value,
    };

    this.authService.authenticate(loginPayload).subscribe(resp => {
      this.errorMessage = null;
      this.router.navigate(['']);
      this.toggleLoading();
    }, error => {
      this.errorMessage = error.error;
      this.toggleLoading();
    });
  }

  private onRegister() {
    const registerPayload: RegisterRequest = {
      email: this.authForm.get('email')?.value,
      nickname: this.authForm.get('nickname')?.value,
      password: this.authForm.get('password')?.value,
    };

    this.authService.register(registerPayload).subscribe(resp => {
      this.router.navigate(['']);
      this.toggleLoading()
    }, error => {
      this.handleRegistrationErrors(error);
      this.toggleLoading();
    });
  }

  private handleRegistrationErrors(error: any) {
    const validationErr = error.error as ValidationErrorResponse;

    if (validationErr?.errors) {
      for (const field in validationErr.errors) {
        const control = this.authForm.get(field);
        if (control) {
          const messages = validationErr.errors[field].join(', ');

          control.setErrors({
            backend: messages
          });
        }
      }
    } else {
      this.errorMessage = error?.message || 'Something went wrong';
    }
  }

  toggleAuthMode() {
    this.errorMessage = null;
    this.loginMode = !this.loginMode;
    this.setupValidators();
  }

  private setupValidators() {
    if (!this.loginMode) {
      this.setupRegistrationValidators();
    } else {
      this.setupLoginValidators();
    }
    this.authForm.get('email')?.updateValueAndValidity();
    this.authForm.get('nickname')?.updateValueAndValidity();
    this.authForm.get('confirmPassword')?.updateValueAndValidity();
    this.authForm.get('password')?.updateValueAndValidity();
    this.authForm.updateValueAndValidity();
  }

  private setupLoginValidators() {
    this.authForm.get('nickname')?.clearValidators();
    this.authForm.get('confirmPassword')?.clearValidators();
    this.authForm.get('password')?.clearValidators();
    this.authForm.clearValidators();

    this.authForm.get('password')?.setValidators([Validators.required]);
    this.authForm.get('email')?.setValidators([Validators.required]);
  }

  private setupRegistrationValidators() {
    this.authForm.get('email')?.setValidators([Validators.required, Validators.email]);
    this.authForm.get('nickname')?.setValidators([Validators.required]);
    this.authForm.get('password')?.setValidators([
      RegistrationValidator.strongPassword(),
      Validators.required
    ]);
    this.authForm.get('confirmPassword')?.setValidators([Validators.required,]);

    this.authForm.setValidators([
      RegistrationValidator.matchPasswords('password', 'confirmPassword')
    ]);
  }
}
