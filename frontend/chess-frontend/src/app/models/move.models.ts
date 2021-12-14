import { EndResult } from './../enums/end-result.enums';
import { Piece } from './../enums/piece.enums';

export interface iMove {
    id?: number,
    gameId: number,
    move: string,
    piece: Piece,
    capture: boolean,
    inCheck: boolean,
    result: EndResult,
    fen: string
}

export class Move {
    id!: number;
    gameId!: number;
    move!: string;
    piece!: Piece;
    capture!: boolean;
    inCheck!: boolean;
    result!: EndResult;
    fen!: string;

    constructor(rawData: any) {
        Object.assign(this, rawData);
    }
}