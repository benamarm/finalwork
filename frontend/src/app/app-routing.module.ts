import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {UsersComponent} from './components/users/users.component';
import {UserPortalComponent} from './components/user-portal/user-portal.component';
import {UserDetailsComponent} from './components/user-details/user-details.component';
import {CoachPortalComponent} from './components/coach-portal/coach-portal.component';
import {MySessionsComponent} from './components/my-sessions/my-sessions.component';

const appRoutes: Routes = [
  {path: 'coachPortal', component: CoachPortalComponent},
  {path: 'userPortal', component: UserPortalComponent},
  {path: 'users', component: UsersComponent},
  {path: 'userDetails/:id', component: UserDetailsComponent},
  {path: 'mySessions', component: MySessionsComponent},
  {path: '', redirectTo: '/coachPortal', pathMatch: 'full'},
  {path: '**', component: CoachPortalComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
