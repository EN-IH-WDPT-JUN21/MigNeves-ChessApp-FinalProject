export interface iGameCreated {
    id?: number,
    whitePassword?: string,
    blackPassword?: string,
    whiteOwner: boolean
}

export class GameCreated {
    id!: number;
    whitePassword!: string;
    blackPassword!: string;
    whiteOwner!: boolean;

    constructor(rawData: any) {
        Object.assign(this, rawData);
    }
}