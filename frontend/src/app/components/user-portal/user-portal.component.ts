import {Component, OnInit} from '@angular/core';
import {APIService} from '../../services/api.service';

@Component({
    selector: 'app-user-portal',
    templateUrl: './user-portal.component.html',
    styleUrls: ['./user-portal.component.css']
})
export class UserPortalComponent implements OnInit {
    username: string;
    password: string;
    errorMessage: string;

    constructor(private API: APIService) {
    }

    ngOnInit() {
        if (localStorage.getItem('adminToken')) {
            window.location.href = '/users';
        }
        if (localStorage.getItem('userToken')) {
            window.location.href = '/mySessions';
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
            window.location.href = '/mySessions';
        }, e => {
            this.errorMessage = 'Gebruikersnaam of wachtwoord onjuist.';
        });
    }

}
