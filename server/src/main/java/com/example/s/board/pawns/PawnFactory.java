package com.example.s.board.pawns;

public class PawnFactory {
    public Pawn producePawn(String color, int row, int column) {
        if (color == "white") {
            return new WhitePawn(row, column);
        }
        else if(color == "black") {
            return new BlackPawn(row, column);
        }
        else {
            return new NullPawn(row, column);
        }
    }
}
