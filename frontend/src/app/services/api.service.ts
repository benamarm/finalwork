import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../models/User';
import {Session} from '../models/Session';
import {FreqChange} from '../models/FreqChange';
import {SessionStep} from '../models/SessionStep';


@Injectable({
    providedIn: 'root'
})
export class APIService {

    url = 'http://193.190.238.44:8080/finalworkapi/web';

    constructor(private http: HttpClient) {
    }

    getAllUsers(): Observable<User[]> {
        return this.http.get<User[]>(this.url + '/users', {
            headers: new HttpHeaders({adminToken: localStorage.getItem('adminToken')})
        });
    }

    getUser(username: string): Observable<User> {
        return this.http.get<User>(this.url + '/users/' + username, this.getMultiHeaders());
    }

    getLatestSession(username: string): Observable<string> {
        return this.http.get<string>(this.url + '/latestSession/' + username, {
            headers: new HttpHeaders({adminToken: localStorage.getItem('adminToken')}),
            responseType: 'text' as 'json'
        });
    }

    coachLogin(token: object): Observable<string> {
        return this.http.post<string>(this.url + '/login', token, {responseType: 'text' as 'json'});
    }

    userLogin(loginRequest: object): Observable<User> {
        return this.http.post<User>('http://193.190.238.44:8080/finalworkapi/app/login', loginRequest, {});
    }

    getFreqChanges(userId: string): Observable<FreqChange[]> {
        return this.http.get<FreqChange[]>(this.url + '/freqChanges/' + userId, {
            headers: new HttpHeaders({adminToken: localStorage.getItem('adminToken')})
        });
    }

    getSessions(userId: string): Observable<Session[]> {
        return this.http.get<Session[]>(this.url + '/sessions/' + userId, this.getMultiHeaders());
    }

    getSessionSteps(sessionId: number): Observable<SessionStep[]> {
        return this.http.get<SessionStep[]>(this.url + '/sessionSteps/' + sessionId, this.getMultiHeaders());
    }

    saveNote(user: User) {
        return this.http.post(this.url + '/editNote', user, {
            headers: new HttpHeaders({adminToken: localStorage.getItem('adminToken')})
        });
    }

    saveFeedback(session: Session) {
        return this.http.post(this.url + '/editFeedback', session, {
            headers: new HttpHeaders({adminToken: localStorage.getItem('adminToken')})
        });
    }

    feedbackSeen(sessionid: number) {
        return this.http.get(this.url + '/feedbackSeen/' + sessionid, {
            headers: new HttpHeaders({userToken: localStorage.getItem('userToken')})
        });
    }

    getAudio(sessionId: number): Observable<ArrayBuffer> {
        return this.http.get<ArrayBuffer>(this.url + '/audio/' + sessionId, this.getMultiHeadersArray());
    }

    getMultiHeaders(): object {
        if (localStorage.getItem('adminToken')) {
            return {
                headers: new HttpHeaders({adminToken: localStorage.getItem('adminToken')})
            };
        } else {
            return {
                headers: new HttpHeaders({userToken: localStorage.getItem('userToken')})
            };
        }
    }

    getMultiHeadersArray(): object {
        if (localStorage.getItem('adminToken')) {
            return {
                headers: new HttpHeaders({adminToken: localStorage.getItem('adminToken')}), responseType: 'arraybuffer' as 'json'
            };
        } else {
            return {
                headers: new HttpHeaders({userToken: localStorage.getItem('userToken')}), responseType: 'arraybuffer' as 'json'
            };
        }
    }

}
