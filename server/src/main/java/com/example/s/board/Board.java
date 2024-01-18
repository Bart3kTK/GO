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

    public void putPawn(final Pawn pawn) //to musi zwracac string z update dla klienta
    {
        this.board[pawn.getRow()][pawn.getColumn()] = pawn;

        pawn.setNeighbours(this.getActualneighbours(pawn));

        for (Pawn neighbour : pawn.getNeighbours()) 
        {   
            neighbour.setNeighbours(this.getActualneighbours(neighbour));
        }

        doPossibleUpdates(pawn);
        
    }

    // public void putPawn(final int row, final int column, final String color)
    // {
    //     Pawn pawn;
    //     if (color.equals("white"))
    //     {
    //         pawn = new WhitePawn();
    //         this.board[row][column] = pawn;
    //     }
    //     else
    //     {                                                                   /// to trzeba zmienic na jekgiegoś buildra czy coś
    //         pawn = new BlackPawn();
    //         this.board[row][column] = pawn;
    //     }

    //     pawn.setNeighbours(this.getActualneighbours(pawn));

    //     for (Pawn neighbour : pawn.getNeighbours()) 
    //     {   
    //         neighbour.setNeighbours(this.getActualneighbours(neighbour));
    //     }
    // }

    public void removePawn(final Pawn pawn)
    {
        if (pawn != null)
        {
            this.board[pawn.getRow()][pawn.getColumn()] = null;

            for (Pawn neighbour : pawn.getNeighbours()) 
            {   
                neighbour.setNeighbours(this.getActualneighbours(neighbour));
            }  
        }
        
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


    // [ ][a][ ][ ]          Pawn[0] = a
    // [d][x][b][ ]          Pawn[1] = b
    // [ ][c][ ][ ]          Pawn[2] = c
    // [ ][ ][ ][ ]          Pawn[3] = d

    /*
     * @brief method returns actual neighbours for pawn on board
     */
    public Pawn[] getActualneighbours(Pawn pawn)
    {
        int row = pawn.getRow();
        int column = pawn.getColumn();

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
            neighbours[2] = null; //TODO: tu nie null tylko trzeba dac ten Mackowy pionek, którego jeszcze nie ma
        }
        else
        {
            neighbours[2] = board[row+1][column];
        }

        if(column == 0)
        {
            neighbours[3] = null; //TODO: tu nie null tylko trzeba dac ten Mackowy pionek, którego jeszcze nie ma
        }
        else
        {
            neighbours[3] = board[row][column-1];
        }
        if(column == columns - 1)
        {
            neighbours[1] = null; //TODO: tu nie null tylko trzeba dac ten Mackowy pionek, którego jeszcze nie ma
        }
        else
        {
            neighbours[1] = board[row][column+1];
        }
        return neighbours;
    }

    public void doPossibleUpdates(Pawn pawn)
    {
        for (Pawn neighbour : pawn.getNeighbours()) 
        {   
            if (neighbour != null)
            {
                if (!neighbour.getColor().equals(pawn.getColor()))
                {
                    if (checkIsSurrounded(neighbour))
                    {
                        removeRecursion(pawn);
                    }
                }
            }
        }
    }

    public void removeRecursion(Pawn pawn)
    {
        pawn.setIsChecked(true);

        for (Pawn neighbour : pawn.getNeighbours())
        {
            if (neighbour.getColor().equals(pawn.getColor()) && neighbour.getChecked() == false)
            {
                removeRecursion(neighbour);
            }
        }

    }
    public boolean checkIsSurrounded(Pawn pawn)
    {
        pawn.setIsChecked(true);
        Boolean isNeighboursSurrounded = false;

        for (Pawn neighbour : pawn.getNeighbours())
        {
            if (neighbour == null)
            {
                pawn.setIsChecked(false);
                return true;
            }
        }

        for (Pawn neighbour : pawn.getNeighbours())
        {
            if (neighbour.getColor().equals(pawn.getColor()) && neighbour.getChecked() == false)
            {
                isNeighboursSurrounded = isNeighboursSurrounded || checkIsSurrounded(neighbour);
            }
        }

        pawn.setIsChecked(false);
        return isNeighboursSurrounded;
    }
}
