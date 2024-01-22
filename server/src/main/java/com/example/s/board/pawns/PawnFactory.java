package com.example.s.board.pawns;

public class PawnFactory {
    public Pawn producePawn(String color, int row, int column) {
        if (color == "white") {
            return new WhitePawn(row, column);
        } else {
            return new BlackPawn(row, column);
        }
    }
}
