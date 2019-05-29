import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { APIService } from '../../services/api.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: User[];

  constructor(private API: APIService, private router: Router) { }

  ngOnInit() {

    if (!localStorage.getItem('adminToken')) {
      this.router.navigate(['coachPortal']);
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
    this.router.navigate(['coachPortal']);
  }

}
