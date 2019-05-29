import {Component, Input, OnInit} from '@angular/core';
import {Session} from '../../models/Session';
import {SessionStep} from '../../models/SessionStep';
import {APIService} from '../../services/api.service';
import * as CanvasJS from '../../canvasjs.min.js';

@Component({
    selector: 'app-session-details',
    templateUrl: './session-details.component.html',
    styleUrls: ['./session-details.component.css']
})
export class SessionDetailsComponent implements OnInit {

    @Input() session: Session;
    sessionSteps: SessionStep[];
    feedback: string;
    feedbackSaved: string;

    constructor(private API: APIService) {
    }

    ngOnInit() {
        this.feedback = this.session.feedback;
        if (!this.isCoach() && !this.session.feedbackSeen) {
            this.API.feedbackSeen(this.session.id).subscribe();
        }
        this.API.getSessionSteps(this.session.id).subscribe(sessionSteps => {
            this.sessionSteps = this.filterSeqNum(sessionSteps);

            const chart = new CanvasJS.Chart('chartContainer', {
                animationEnabled: true,
                axisX: {
                    labelFormatter(e) {
                        return '';
                    }
                },
                axisY: {
                    title: 'Toonhoogte (in Hz)',
                    maximum: this.session.maxFreq,
                    minimum: this.session.minFreq
                },
                data: [{
                    type: 'scatter',
                    toolTipContent: '<span style="color:blue "><b>Minimum te treffen toon:</b></span> {y} Hz',
                    name: 'Maximum & minimum te treffen toon',
                    color: 'blue',
                    showInLegend: true,
                    dataPoints: this.freqToArray('minFreq')
                },
                    {
                        type: 'scatter',
                        toolTipContent: '<span style="color:blue "><b>Maximum te treffen toon:</b></span> {y} Hz',
                        color: 'blue',
                        dataPoints: this.freqToArray('maxFreq')
                    },
                    {
                        type: 'scatter',
                        toolTipContent: '<span style="color:red "><b>{name}:</b></span> {y} Hz',
                        name: 'Getroffen toon',
                        color: 'red',
                        showInLegend: true,
                        dataPoints: this.freqToArray('hitFreq')
                    }]
            });

            chart.render();
        });

        this.API.getAudio(this.session.id).subscribe(data => {
            const blob = new Blob([data], {type: 'audio/wav'});
            const url = URL.createObjectURL(blob);
            const audio = document.getElementById('audio') as HTMLAudioElement;
            const source = document.getElementById('source') as HTMLSourceElement;
            source.src = url;
            audio.load();
        });
    }

    onSaveFeedback() {
        this.session.feedback = this.feedback;
        this.API.saveFeedback(this.session).subscribe(data => {
            this.feedbackSaved = 'Feedback succesvol bewaard.';
        });
    }

    filterSeqNum(array: SessionStep[]) {
        return array.sort((a, b) => a.seqNum > b.seqNum ? 1 : a.seqNum === b.seqNum ? 0 : -1);
    }

    freqToArray(prop: string): object[] {
        const array = [];
        for (const step of this.sessionSteps) {
            array.push({x: step.seqNum, y: step[prop]});
        }
        return array;
    }

    isCoach() {
        return localStorage.getItem('adminToken');
    }

}
