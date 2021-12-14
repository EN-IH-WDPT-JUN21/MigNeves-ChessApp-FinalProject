package com.ironhack.drawservice.service;

import com.ironhack.drawservice.dto.MoveDTO;
import com.ironhack.drawservice.enums.EndResult;
import com.ironhack.drawservice.enums.Piece;
import com.ironhack.drawservice.proxy.MovesProxy;
import com.ironhack.drawservice.utils.MoveServiceMock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrawService {

    @Value("${running.test}")
    private boolean test;
    private final MovesProxy movesProxy;
    private final MoveServiceMock moveServiceMock;

    public DrawService(MovesProxy movesProxy, MoveServiceMock moveServiceMock) {
        this.movesProxy = movesProxy;
        this.moveServiceMock = moveServiceMock;
    }

    // Method to check whether it is a draw
    public MoveDTO checkDraw(MoveDTO moveDTO) {

        // Check all draw conditions
        if (checkInsufficientMaterial(moveDTO.getFen()) || checkRepetition(moveDTO) || checkFiftyMoveRule(moveDTO)) {
            moveDTO.setResult(EndResult.DRAW);
        }
        return moveDTO;
    }

    // Draw by insufficient material -> both players only have the king and at most one minor piece (bishop or knight)
    private boolean checkInsufficientMaterial(String fen) {
        fen = fen.split(" ")[0];

        // Check if pawns, queens and rooks exist
        if (fen.toLowerCase().contains("p") || fen.toLowerCase().contains("q") || fen.toLowerCase().contains("r")) {
            return false;
        }

        // Count number of bishops and knights for each color
        int whiteMinorPiecesCount = 0;
        int blackMinorPiecesCount = 0;
        for (int i = 0; i < fen.length(); i++) {
            if (fen.charAt(i) == 'b' || fen.charAt(i) == 'n') {
                blackMinorPiecesCount++;
            } else if (fen.charAt(i) == 'B' || fen.charAt(i) == 'N') {
                whiteMinorPiecesCount++;
            }
        }
        return whiteMinorPiecesCount <= 1 && blackMinorPiecesCount <= 1;
    }

    // Condition where the same exact position if repeated 3 times is a draw (castling rights and en passant matter)
    private boolean checkRepetition(MoveDTO moveDTO) {

        List<MoveDTO> moves;
        if (!test) {
            moves = movesProxy.getMovesFromGame(moveDTO.getGameId());
        } else {
            moves = moveServiceMock.getMockMoves();
        }
        moves.add(moveDTO);

        // Map to save position in fen notation and number of times it occurred
        Map<String, Integer> repeatedPositions = new HashMap();

        repeatedPositions.put(moveDTO.getFen(), 1);

        // Start from the last move and check if position, en passant possibilities and castling rights repeats 3 times
        for (int move = moves.size() - 1; move >= 0; move--) {
            String[] fenParts = moves.get(move).getFen().split(" ");
            String simplifiedFen;
            // Reduce fen notation to the three important parts
            if (fenParts.length == 6) {
                simplifiedFen = fenParts[0] + " " + fenParts[2] + " " + fenParts[3];
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid FEN");
            }
            Integer repeatTimes = repeatedPositions.get(simplifiedFen);

            // Check if it's the third time it repeats
            if (repeatTimes == null) {
                repeatedPositions.put(simplifiedFen, 1);
            } else {
                if (repeatTimes == 2) {
                    System.out.println("Repeated position: " + simplifiedFen);
                    return true;
                } else {
                    repeatedPositions.put(simplifiedFen, repeatTimes + 1);
                }
            }
        }
        return false;
    }

    // Fifty move rule -> if 50 moves (player white and player black both move) have passed without any capture or
    // pawn move it is a draw
    public boolean checkFiftyMoveRule(MoveDTO moveDTO) {
        List<MoveDTO> moves;
        if (!test) {
            moves = movesProxy.getMovesFromGame(moveDTO.getGameId());
        } else {
            moves = moveServiceMock.getMockMoves();
        }
        moves.add(moveDTO);

        int beginningMove = moves.size() % 2 == 0 ? moves.size() - 1 : moves.size() - 2;
        int moveCount = 0;

        // Count moves until last capture or pawn move
        for (int i = beginningMove; i >= 0; i -= 2) {
            if (moves.get(i).getCapture() || moves.get(i).getPiece().equals(Piece.BLACK_PAWN) ||
            moves.get(i-1).getCapture() || moves.get(i-1).getPiece().equals(Piece.WHITE_PAWN)) {
                System.out.println(moveCount + " moves");
                return moveCount >= 50;
            }
            moveCount++;
        }
        System.out.println(moveCount + " moves");
        return moveCount >= 50;
    }
}
