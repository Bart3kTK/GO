package com.example.s.pawns;

public class BlackPawn extends Pawn
{
   public BlackPawn()
   {
        color = "Black";
   }

   public BlackPawn(final int row, final int column)
   {
        color = "Black";
        this.row = row;
        this.column = column;
   }
}
