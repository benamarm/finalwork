import {BrowserModule} from '@angular/platform-browser';
import {NgModule, LOCALE_ID} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import localeNl from '@angular/common/locales/nl';
import {registerLocaleData} from '@angular/common';

registerLocaleData(localeNl);

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {UsersComponent} from './components/users/users.component';
import {UserItemComponent} from './components/user-item/user-item.component';
import {UserPortalComponent} from './components/user-portal/user-portal.component';
import {UserDetailsComponent} from './components/user-details/user-details.component';
import {CoachPortalComponent} from './components/coach-portal/coach-portal.component';
import {MySessionsComponent} from './components/my-sessions/my-sessions.component';
import { SessionItemComponent } from './components/session-item/session-item.component';
import { FreqChangeItemComponent } from './components/freq-change-item/freq-change-item.component';
import { SessionDetailsComponent } from './components/session-details/session-details.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule, MatTooltipModule} from '@angular/material';


@NgModule({
    declarations: [
        AppComponent,
        UsersComponent,
        UserItemComponent,
        UserPortalComponent,
        UserDetailsComponent,
        CoachPortalComponent,
        MySessionsComponent,
        SessionItemComponent,
        FreqChangeItemComponent,
        SessionDetailsComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        MatIconModule,
        MatTooltipModule
    ],
    providers: [
        {provide: LOCALE_ID, useValue: 'nl-NL'}
    ],
    bootstrap: [AppComponent],
    entryComponents: [SessionDetailsComponent]
})
export class AppModule {
}
