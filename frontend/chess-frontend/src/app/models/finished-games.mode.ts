import { SimplifiedGame } from './simplified-game-models';
export interface iFinishedGames {
    games?: SimplifiedGame[],
    pages?: number
}

export class FinishedGames {
    games!: SimplifiedGame[];
    pages!: number;

    constructor(rawData: any) {
        Object.assign(this, rawData);
    }
}