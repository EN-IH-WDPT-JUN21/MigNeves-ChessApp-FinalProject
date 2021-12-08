import { EndResult } from './../enums/end-result.enums';
import { GameType } from './../enums/game-type.enums';
import { Move } from './move.models';

export interface iGame {
    id: number,
    gameType: GameType,
    result: EndResult,
    startDate: Date,
    moves: Move[],
    moveToAdd: Move,
    colorWhite: boolean,
    password: string
    fen: string
}

export class Game {
    id!: number;
    gameType!: GameType;
    result!: EndResult;
    startDate!: Date;
    moves!: Move[];
    moveToAdd!: Move;
    colorWhite!: boolean;
    password!: string | null;
    fen!: string;

    constructor(rawData: any) {
        Object.assign(this, rawData);
    }
}