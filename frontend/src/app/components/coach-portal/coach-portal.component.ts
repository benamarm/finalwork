import {Component, OnInit} from '@angular/core';
import {APIService} from '../../services/api.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-coach-portal',
    templateUrl: './coach-portal.component.html',
    styleUrls: ['./coach-portal.component.css']
})
export class CoachPortalComponent implements OnInit {
    coachToken: string;
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
                this.router.navigate(['users']);
            } else {
                this.errorMessage = 'Ingegeven token is onbestaand.';
            }
        });
    }

}
