import { EndResult } from "../enums/end-result.enums";

export interface iSimplifiedGame {
    id: number,
    numberMoves: number,
    result: EndResult,
    startDate: Date,
    fen: string,
    owner: boolean
}

export class SimplifiedGame {
    id!: number;
    numberMoves!: number;
    result!: EndResult;
    startDate!: Date;
    fen!: string;
    owner!: boolean;

    constructor(rawData: any) {
        Object.assign(this, rawData);
    }
} 