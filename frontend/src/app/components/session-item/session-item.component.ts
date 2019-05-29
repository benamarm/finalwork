import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Session} from '../../models/Session';

@Component({
    selector: '[app-session-item]',
    templateUrl: './session-item.component.html',
    styleUrls: ['./session-item.component.css']
})
export class SessionItemComponent implements OnInit {

    @Input() session: Session;
    @Output() someEvent = new EventEmitter();

    constructor() {
    }

    ngOnInit() {
    }

    callParent() {
        this.someEvent.next(this.session);
    }

    isUser() {
        return localStorage.getItem('userToken');
    }

}
