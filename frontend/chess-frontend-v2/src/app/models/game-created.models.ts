import { GameType } from './../enums/game-type.enums';

export interface iGameCreated {
    id?: number,
    gameType: GameType,
    whitePassword?: string,
    blackPassword?: string
}

export class GameCreated {
    id!: number;
    gameType!: GameType;
    whitePassword!: string;
    blackPassword!: string;

    constructor(rawData: any) {
        Object.assign(this, rawData);
    }
}