package com.example.xd.pawns;

public class WhitePawn extends Pawn
{
   public WhitePawn()
   {
        color = "White";
   }

   public WhitePawn(final int row, final int column)
   {
        color = "White";
        this.row = row;
        this.column = column;
   }
}
