import {Component, OnInit, ViewChild, ViewContainerRef, ComponentFactory, ComponentRef, ComponentFactoryResolver} from '@angular/core';
import {APIService} from '../../services/api.service';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../../models/User';
import {Session} from '../../models/Session';
import {SessionDetailsComponent} from '../session-details/session-details.component';
import {FreqChange} from '../../models/FreqChange';

@Component({
    selector: 'app-user-details',
    templateUrl: './user-details.component.html',
    styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

    user = new User();
    note: string;
    noteSaved: string;
    freqChanges: FreqChange[];
    sessions: Session[];
    noSessions: string;
    @ViewChild('sessionDetails', {read: ViewContainerRef}) entry: ViewContainerRef;

    constructor(private API: APIService, private route: ActivatedRoute, private resolver: ComponentFactoryResolver, private router: Router) {
    }

    ngOnInit() {
        if (!localStorage.getItem('adminToken')) {
            this.router.navigate(['coachPortal']);
            return;
        }

        this.API.getUser(this.route.snapshot.params['id']).subscribe(user => {
                this.user = user;
                this.note = user.note;

                this.API.getFreqChanges(user.username).subscribe(freqChanges => {
                    this.freqChanges = this.filterDesc('changeDate', freqChanges);
                });

                this.API.getSessions(user.username).subscribe(sessions => {
                    this.sessions = this.filterDesc('startTime', sessions);
                    if (sessions.length === 0) {
                        this.noSessions = '<td colspan="3">Geen sessies.</td>';
                    }
                });
            },
            error => {
                this.router.navigate(['users']);
            });

    }

    onBack() {
        this.router.navigate(['users']);
    }

    onLogOut() {
        localStorage.removeItem('adminToken');
        this.router.navigate(['coachPortal']);
    }

    onSaveNote() {
        this.user.note = this.note;
        this.API.saveNote(this.user).subscribe(data => {
            this.noteSaved = 'Notities succesvol bewaard.';
        });
    }

    filterDesc<T>(prop: string, array: T[]) {
        return array.sort((a, b) => a[prop] < b[prop] ? 1 : a[prop] === b[prop] ? 0 : -1);
    }

    showSessionDetails(session) {
        this.entry.clear();
        const factory = this.resolver.resolveComponentFactory(SessionDetailsComponent);
        const componentRef = this.entry.createComponent(factory);
        componentRef.instance.session = session;
    }

}
