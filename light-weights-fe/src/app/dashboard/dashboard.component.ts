import { Component } from '@angular/core';
import {UserMenuComponent} from './user-menu/user-menu.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    UserMenuComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

}
