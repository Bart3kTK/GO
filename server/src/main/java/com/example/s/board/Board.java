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

        pawn.setNeighbours(this.getActualneighbours(row, column));
        //TODO: PROBLEM TRZEBA Z MACKIEM OBADAC JAK SKONCZY OGLADAC WYKLAD
        //TOOD: tu trzeba ustawic ze sąsiadom ze ten pionek mieszka obok nich
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


    /*
     * @brief this method is used to set actual neighbours for pawn on board
     */
    public void setNeighbours(int row, int column)
    {
        
        if (this.isPositionFree(row, column))
        {
            Pawn pawn = board[row][column];
            pawn.setNeighbours(getActualneighbours(row, column));
        }
    }





    // [ ][a][ ][ ]          Pawn[0] = a
    // [b][x][c][ ]          Pawn[1] = b
    // [ ][d][ ][ ]          Pawn[2] = c
    // [ ][ ][ ][ ]          Pawn[3] = d

    /*
     * @brief method returns actual neighbours for pawn on board
     */
    public Pawn[] getActualneighbours(int row, int column)
    {
        Pawn[] neighbours = new Pawn[4];
        if(row == 0)
        {
            neighbours[0] = null; //TODO: tu nie null tylko trzeba dac ten Mackowy pionek, którego jeszcze nie ma
        }
        else
        {
            neighbours[0] = board[row-1][column];
        }

        if(row == rows-1)
        {
            neighbours[3] = null; //TODO: tu nie null tylko trzeba dac ten Mackowy pionek, którego jeszcze nie ma
        }
        else
        {
            neighbours[3] = board[row+1][column];
        }

        if(column == 0)
        {
            neighbours[1] = null; //TODO: tu nie null tylko trzeba dac ten Mackowy pionek, którego jeszcze nie ma
        }
        else
        {
            neighbours[1] = board[row][column-1];
        }
        if(column == columns - 1)
        {
            neighbours[2] = null; //TODO: tu nie null tylko trzeba dac ten Mackowy pionek, którego jeszcze nie ma
        }
        else
        {
            neighbours[2] = board[row][column+1];
        }
        return neighbours;
    }

}
