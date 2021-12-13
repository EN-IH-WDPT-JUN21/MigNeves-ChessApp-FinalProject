import { GameType } from './../enums/game-type.enums';

export interface iGameCreated {
    id?: number,
    gameType: GameType,
    whitePassword?: string,
    blackPassword?: string,
    whiteOwner: boolean
}

export class GameCreated {
    id!: number;
    gameType!: GameType;
    whitePassword!: string;
    blackPassword!: string;
    whiteOwner!: boolean;

    constructor(rawData: any) {
        Object.assign(this, rawData);
    }
}