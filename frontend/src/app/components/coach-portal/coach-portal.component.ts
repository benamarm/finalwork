import {Component, OnInit} from '@angular/core';
import {APIService} from '../../services/api.service';

@Component({
    selector: 'app-coach-portal',
    templateUrl: './coach-portal.component.html',
    styleUrls: ['./coach-portal.component.css']
})
export class CoachPortalComponent implements OnInit {
    coachToken: string;
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

        if (!this.coachToken) {
            this.errorMessage = 'Coachtoken is leeg.';
            return;
        }

        const coachToken = {
            token: this.coachToken
        };

        this.API.coachLogin(coachToken).subscribe(token => {
            if (token) {
                localStorage.setItem('adminToken', token);
                window.location.href = '/users';
            } else {
                this.errorMessage = 'Ingegeven token is onbestaand.';
            }
        });
    }

}
