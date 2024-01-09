package com.example.s.board;

import com.example.s.board.pawns.BlackPawn;
import com.example.s.board.pawns.Pawn;
import com.example.s.board.pawns.WhitePawn;

/*
 *  @brief class wich represents board and holds pawns in 2D array
 *          null in array represents free space on board
 *          otherwise pawn is stored on particular position
 * 
 *      TODOs:
 *      - add saving to database (by prototype pattern)
 */
public class Board 
{
    private final int rows;
    private final int columns;
    private final Pawn board[][];

    public Board(final int rows, final int columns)
    {
        this.rows = rows;
        this.columns = columns;
        this.board = new Pawn[rows][columns];
    }

    public void putPawn(final int row, final int column, final Pawn pawn)
    {
        this.board[row][column] = pawn;
    }

    public void putPawn(final int row, final int column, final String color)
    {
        if (color.equals("white"))
        {
            this.board[row][column] = new WhitePawn();
        }
        else
        {
            this.board[row][column] = new BlackPawn();
        }
    }

    public void removePawn(final int row, final int column)
    {
        this.board[row][column] = null;
    }

    /*
     *   clear string representation for board
     */
    public String toString()
    {
        String textRepresentation = "";
        for (Pawn[] row : board) 
        {
            for (Pawn pawn : row) 
            {
                if (pawn == null)
                {
                    textRepresentation += "----- ";
                    continue;
                }
                textRepresentation += pawn.getColor() + " ";
            }
            textRepresentation += "\n";
        }

        return textRepresentation;
    }

    public boolean isPositionFree(final int row, final int column)
    {
        if (board[row][column] == null)
        {
            return true;
        }
        return false;
    }

    public Pawn[][] getboard()
    {
        return this.board;
    }

}
