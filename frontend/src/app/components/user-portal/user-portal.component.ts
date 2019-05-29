import {Component, OnInit} from '@angular/core';
import {APIService} from '../../services/api.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-user-portal',
    templateUrl: './user-portal.component.html',
    styleUrls: ['./user-portal.component.css']
})
export class UserPortalComponent implements OnInit {
    username: string;
    password: string;
    errorMessage: string;

    constructor(private API: APIService, private router: Router) {
    }

    ngOnInit() {
        if (localStorage.getItem('adminToken')) {
            this.router.navigate(['users']);
        }
        if (localStorage.getItem('userToken')) {
            this.router.navigate(['mySessions']);
        }
    }

    onSubmit() {

        if (!this.username) {
            this.errorMessage = 'Gebruikersnaam is leeg.';
            return;
        }

        if (!this.password) {
            this.errorMessage = 'Wachtwoord is leeg.';
            return;
        }

        const loginRequest = {
            username: this.username,
            password: this.password
        };

        this.API.userLogin(loginRequest).subscribe(user => {
            localStorage.setItem('userToken', user.token);
            localStorage.setItem('userId', user.username);
            this.router.navigate(['mySessions']);
        }, e => {
            this.errorMessage = 'Gebruikersnaam of wachtwoord onjuist.';
        });
    }

}
