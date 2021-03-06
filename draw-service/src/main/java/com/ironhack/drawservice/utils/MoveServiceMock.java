package com.ironhack.drawservice.utils;

import com.ironhack.drawservice.dto.MoveDTO;
import com.ironhack.drawservice.enums.EndResult;
import com.ironhack.drawservice.enums.Piece;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MoveServiceMock {
    public List<MoveDTO> getMockMoves() {
        List<MoveDTO> moves = new ArrayList<>();
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/8/8/8/8/4B3/4K1N1 b - - 1 1"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/4b3/8/8/8/8/4B3/4K1N1 w - - 2 2"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/4b3/8/8/8/5N2/4B3/4K3 b - - 3 2"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/5n2/8/8/5N2/4B3/4K3 w - - 4 3"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/5n2/8/8/5N2/8/4KB2 b - - 5 3"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/5n2/8/8/5N2/8/4KB2 w - - 6 4"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/5n2/8/8/8/3N4/4KB2 b - - 7 4"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/8/8/8/3N4/4KB2 w - - 8 5"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/8/8/8/3N2B1/4K3 b - - 9 5"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/8/8/8/3N2B1/4K3 w - - 10 6"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/8/8/5N2/6B1/4K3 b - - 11 6"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/5n2/8/8/5N2/6B1/4K3 w - - 12 7"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/5n2/8/3N4/8/6B1/4K3 b - - 13 7"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/3n4/3N4/8/6B1/4K3 w - - 14 8"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/1N1n4/8/8/6B1/4K3 b - - 15 8"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/8/8/6B1/4K3 w - - 16 9"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/8/5B2/8/4K3 b - - 17 9"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n3b2/1N6/8/5B2/8/4K3 w - - 18 10"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n3b2/1N6/4B3/8/8/4K3 b - - 19 10"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N2b3/4B3/8/8/4K3 w - - 20 11"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N1Bb3/8/8/8/4K3 b - - 21 11"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N1B4/3b4/8/8/4K3 w - - 22 12"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1nB5/1N6/3b4/8/8/4K3 b - - 23 12"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1nB5/1N6/3b4/8/8/4K3 w - - 24 13"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/3b4/8/8/4K3 b - - 25 13"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/8/2b5/8/4K3 w - - 26 14"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/8/2b5/8/3K4 b - - 27 14"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/8/8/1b6/3K4 w - - 28 15"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/Bn6/1N6/8/8/1b6/3K4 b - - 29 15"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/Bn6/1N6/8/b7/8/3K4 w - - 30 16"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/8/b7/8/3K4 b - - 31 16"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/1b6/8/8/3K4 w - - 32 17"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1nB5/1N6/1b6/8/8/3K4 b - - 33 17"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1nB5/1N6/8/2b5/8/3K4 w - - 34 18"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N1B4/8/2b5/8/3K4 b - - 35 18"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N1B4/8/8/3b4/3K4 w - - 36 19"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/4B3/8/3b4/3K4 b - - 37 19"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/4B3/8/8/3Kb3 w - - 38 20"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N3B2/8/8/8/3Kb3 b - - 39 20"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N3B2/8/8/5b2/3K4 w - - 40 21"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n2B3/1N6/8/8/5b2/3K4 b - - 41 21"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n2B3/1N6/8/8/8/3K2b1 w - - 42 22"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/8/8/8/3K2b1 b - - 43 22"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/8/8/7b/3K4 w - - 44 23"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/8/8/7b/3K4 b - - 45 23"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/8/6b1/8/3K4 w - - 46 24"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/8/6b1/8/3K4 b - - 47 24"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N6/5b2/8/8/3K4 w - - 48 25"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1nB5/1N6/5b2/8/8/3K4 b - - 49 25"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1nB5/1N4b1/8/8/8/3K4 w - - 50 26"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1n6/1N4b1/8/8/8/3K4 b - - 51 26"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/1N1n2b1/8/8/8/3K4 w - - 52 27"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/3n2b1/3N4/8/8/3K4 b - - 53 27"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/6b1/3N1n2/8/8/3K4 w - - 54 28"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/6b1/5n2/5N2/8/3K4 b - - 55 28"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/6b1/8/3n1N2/8/3K4 w - - 56 29"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/6b1/8/3n4/3N4/3K4 b - - 57 29"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/6b1/1n6/8/3N4/3K4 w - - 58 30"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/6b1/1nN5/8/8/3K4 b - - 59 30"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/2n5/6b1/2N5/8/8/3K4 w - - 60 31"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/2nN4/6b1/8/8/8/3K4 b - - 61 31"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/3N4/4n1b1/8/8/8/3K4 w - - 62 32"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/4n1b1/4N3/8/8/3K4 b - - 63 32"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/6b1/4N3/8/8/3K4 w - - 64 33"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/6b1/8/2N5/8/3K4 b - - 65 33"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/7n/6b1/8/2N5/8/3K4 w - - 66 34"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/7n/6b1/N7/8/8/3K4 b - - 67 34"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/5nb1/N7/8/8/3K4 w - - 68 35"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2nb1/8/8/8/3K4 b - - 69 35"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/5b2/2N2n2/8/8/8/3K4 w - - 70 36"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/2B2b2/2N2n2/8/8/8/3K4 b - - 71 36"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/2B5/2N1bn2/8/8/8/3K4 w - - 72 37"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/1BN1bn2/8/8/8/3K4 b - - 73 37"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/3b4/1BN2n2/8/8/8/3K4 w - - 74 38"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/3b4/2N2n2/B7/8/8/3K4 b - - 75 38"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/B7/8/8/3K4 w - - 76 39"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/1B6/8/3K4 b - - 77 39"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1b6/2N2n2/8/1B6/8/3K4 w - - 78 40"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/1b6/2N2n2/2B5/8/8/3K4 b - - 79 40"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/b1N2n2/2B5/8/8/3K4 w - - 80 41"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/b1NB1n2/8/8/8/3K4 b - - 81 41"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2NB1n2/1b6/8/8/3K4 w - - 82 42"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/1b6/8/B7/3K4 b - - 83 42"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/b7/B7/3K4 w - - 84 43"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/b7/8/1B1K4 b - - 85 43"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/8/1b6/1B1K4 w - - 86 44"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/8/1bB5/3K4 b - - 87 44"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/2b5/2B5/3K4 w - - 88 45"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/2bB4/8/3K4 b - - 89 45"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/3b4/3B4/8/3K4 w - - 90 46"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/3bB3/8/8/3K4 b - - 91 46"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/4B3/4b3/8/3K4 w - - 92 47"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/4bB2/8/3K4 b - - 93 47"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/5B2/5b2/3K4 w - - 94 48"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/8/5bB1/3K4 b - - 95 48"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/6b1/6B1/3K4 w - - 96 49"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/8/6bB/8/3K4 b - - 97 49"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/5b2/7B/8/3K4 w - - 98 50"));
        moves.add(new MoveDTO(null, null, null, Piece.BLACK_BISHOP, false, null, null,"/8/2N2n2/5bB1/8/8/3K4 b - - 99 50"));
        return moves;
    }
}