import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../auth/services/auth.service';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-user-menu',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './user-menu.component.html',
  styleUrl: './user-menu.component.css'
})
export class UserMenuComponent implements OnInit {
  email: string = '';

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(user => {
      if (user) {
        this.email = user.email;
      }
    })
  }

  logout(): void {
    this.authService.logout();
  }

}
