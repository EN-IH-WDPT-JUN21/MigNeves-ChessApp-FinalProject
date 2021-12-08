import { EndResult } from "../enums/end-result.enums";
import { GameType } from "../enums/game-type.enums";

export interface iSimplifiedGame {
    id: number,
    gameType: GameType,
    numberMoves: number,
    result: EndResult,
    startDate: Date
}

export class SimplifiedGame {
    id!: number;
    gameType!: GameType;
    numberMoves!: number;
    result!: EndResult;
    startDate!: Date;

    constructor(rawData: any) {
        Object.assign(this, rawData);
    }
} 