import { EndResult } from './../enums/end-result.enums';
import { Move } from './move.models';

export interface iGame {
    id: number,
    result: EndResult,
    startDate: Date,
    moves: Move[],
    moveToAdd: Move,
    colorWhite: boolean,
    password: string
}

export class Game {
    id!: number;
    result!: EndResult;
    startDate!: Date;
    moves!: Move[];
    moveToAdd!: Move;
    colorWhite!: boolean;
    password!: string | null;

    constructor(rawData: any) {
        Object.assign(this, rawData);
    }
}