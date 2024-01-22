package com.example.s.board.pawns;

public class BlackPawn extends Pawn
{
   public BlackPawn()
   {
        color = "black";
   }

   public BlackPawn(final int row, final int column)
   {
        color = "black";
        this.row = row;
        this.column = column;
   }
}
