import { Component } from '@angular/core';
import {UserMenuComponent} from './user-menu/user-menu.component';
import {RouterLink, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    UserMenuComponent,
    RouterOutlet,
    RouterLink
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

}
