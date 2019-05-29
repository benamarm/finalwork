export class Session {
    id: number;
    minFreq: number;
    maxFreq: number;
    score: number;
    startTime: string;
    feedback?: string;
    feedbackSeen: boolean;
}
