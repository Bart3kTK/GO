package com.example.s.board.pawns;

public class NullPawn extends Pawn
{
   public NullPawn()
   {
        color = "null";
   }

   public NullPawn(final int row, final int column)
   {
        color = "null";
        this.row = row;
        this.column = column;
   }
}
