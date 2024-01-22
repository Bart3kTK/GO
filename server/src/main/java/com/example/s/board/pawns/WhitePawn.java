package com.example.s.board.pawns;

public class WhitePawn extends Pawn
{
   public WhitePawn()
   {
        color = "white";
   }

   public WhitePawn(final int row, final int column)
   {
        color = "white";
        this.row = row;
        this.column = column;
   }
}
