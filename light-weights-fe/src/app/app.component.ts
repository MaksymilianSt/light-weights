import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AuthComponent} from './auth/auth.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, DashboardComponent, AuthComponent], //todo:remove DashboardComponent, AuthComponent l8er
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'light-weights-fe';
}
