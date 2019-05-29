import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { APIService } from '../../services/api.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: User[];

  constructor(private API: APIService) { }

  ngOnInit() {

    if (!localStorage.getItem('adminToken')) {
      window.location.href = '/coachPortal';
      return;
    }

    this.API.getAllUsers().subscribe(users => {

      for (const u of users) {
        this.API.getLatestSession(u.username).subscribe(lastPlayed => {
          u.lastPlayed = lastPlayed;
        });
      }
      this.users = users;
    });
  }

  onLogOut() {
    localStorage.removeItem('adminToken');
    window.location.href = '/coachPortal';
  }

}
