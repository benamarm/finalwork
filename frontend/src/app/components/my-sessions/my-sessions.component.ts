import {Component, ComponentFactoryResolver, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {User} from '../../models/User';
import {Session} from '../../models/Session';
import {APIService} from '../../services/api.service';
import {ActivatedRoute} from '@angular/router';
import {SessionDetailsComponent} from '../session-details/session-details.component';

@Component({
    selector: 'app-my-sessions',
    templateUrl: './my-sessions.component.html',
    styleUrls: ['./my-sessions.component.css']
})
export class MySessionsComponent implements OnInit {

    user = new User();
    sessions: Session[];
    noSessions: string;
    @ViewChild('sessionDetails', {read: ViewContainerRef}) entry: ViewContainerRef;

    constructor(private API: APIService, private route: ActivatedRoute, private resolver: ComponentFactoryResolver) {
    }

    ngOnInit() {
        if (!localStorage.getItem('userToken')) {
            window.location.href = '/userPortal';
        }
        this.API.getUser(localStorage.getItem('userId')).subscribe(user => {
                this.user = user;
                this.API.getSessions(user.username).subscribe(sessions => {
                    this.sessions = this.filterDesc('startTime', sessions);
                    if (sessions.length === 0) {
                        this.noSessions = '<td colspan="3">Geen sessies.</td>';
                    }
                });
            });
    }

    onLogOut() {
        localStorage.removeItem('userToken');
        localStorage.removeItem('userId');
        window.location.href = '/userPortal';
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
