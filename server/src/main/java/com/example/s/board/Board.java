package com.example.s.board;

import com.example.s.board.pawns.BlackPawn;
import com.example.s.board.pawns.BorderPawn;
import com.example.s.board.pawns.Pawn;
import com.example.s.board.pawns.PawnFactory;
import com.example.s.board.pawns.WhitePawn;
import com.example.s.logger.MyLogger;

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
    private String lastUpdate;

    public Board(final int rows, final int columns)
    {
        this.rows = rows;
        this.columns = columns;
        this.board = new Pawn[rows][columns];
    }

    public void putPawn(final Pawn pawn) //to musi zwracac string z update dla klienta
    {
        this.board[pawn.getRow()][pawn.getColumn()] = pawn;
        this.lastUpdate = pawn.toString();

        pawn.setNeighbours(this.getActualneighbours(pawn));

        for (Pawn neighbour : pawn.getNeighbours()) 
        {   
            if (neighbour != null)
            {
                neighbour.setNeighbours(this.getActualneighbours(neighbour));                
            }

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
            this.lastUpdate += ";" + pawn.toStringClearMessage();
            MyLogger.logger.info("Removing pawn: " + pawn.toString());
            MyLogger.logger.info("Last update: " + this.lastUpdate);

            for (Pawn neighbour : pawn.getNeighbours()) 
            {   
                if (neighbour != null)
                {
                    neighbour.setNeighbours(this.getActualneighbours(neighbour));                
                }
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

    public boolean isPositionAllowed(final int row, final int column, String color)
    {
        // first check if position is free
        if (board[row][column] != null)
        {
            return false;
        }


        // then check if pawn is not surrounded
        PawnFactory pawnFactory = new PawnFactory();                                //
        Pawn testPawn = pawnFactory.producePawn(color, row, column);                // Creating test pawn
        this.board[testPawn.getRow()][testPawn.getColumn()] = testPawn;             //
        
        testPawn.setNeighbours(this.getActualneighbours(testPawn));                 //
        for (Pawn neighbour : testPawn.getNeighbours())                             //
        {                                                                           // Setting neighbours for test pawn
            if (neighbour != null)                                                  // Updating neighbours their neighbours
            {                                                                       //
                neighbour.setNeighbours(this.getActualneighbours(neighbour));       //       
            }                                                                       //
        }                                                                           //




        if(!checkIsNotSurrounded(testPawn))                                                         
        {        
            Boolean returnFlag = false;

            for (Pawn neighbour : testPawn.getNeighbours())                                         //
            {                                                                                       //
                if (neighbour != null)                                                              //
                {                                                                                   //
                    if (!neighbour.getColor().equals(testPawn.getColor()))                          //
                    {                                                                               //
                        if (!checkIsNotSurrounded(neighbour))                                       //
                        {                                                                           //  If move is suicide
                            returnFlag = true;                                                      //  but there is a possibility 
                        }                                                                           //  of shortness of breath
                    }                                                                               //
                }
    
            }


            this.board[testPawn.getRow()][testPawn.getColumn()] = null;                     //
            for (Pawn neighbour : testPawn.getNeighbours())                                 //
            {                                                                               //
                if (neighbour != null)                                                      // If move is suicide move
                {                                                                           //
                    neighbour.setNeighbours(this.getActualneighbours(neighbour));           //
                }                                                                           //
            }                                                                               //
            return returnFlag;                                                              //
        }                                                                                   //
        
        

        return true;
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
        MyLogger.logger.info("Getting neighbours for pawn: " + pawn.toString());

        int row = pawn.getRow();
        int column = pawn.getColumn();

        Pawn[] neighbours = new Pawn[4];
        if(row == 0)
        {
            neighbours[0] = new BorderPawn(); //TODO: tu nie null tylko trzeba dac ten Mackowy pionek, którego jeszcze nie ma
        }
        else
        {
            neighbours[0] = board[row-1][column];
        }

        if(row == rows-1)
        {
            neighbours[2] = new BorderPawn(); //TODO: tu nie null tylko trzeba dac ten Mackowy pionek, którego jeszcze nie ma
        }
        else
        {
            neighbours[2] = board[row+1][column];
        }

        if(column == 0)
        {
            neighbours[3] = new BorderPawn(); //TODO: tu nie null tylko trzeba dac ten Mackowy pionek, którego jeszcze nie ma
        }
        else
        {
            neighbours[3] = board[row][column-1];
        }
        if(column == columns - 1)
        {
            neighbours[1] = new BorderPawn(); //TODO: tu nie null tylko trzeba dac ten Mackowy pionek, którego jeszcze nie ma
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
                    if (!checkIsNotSurrounded(neighbour))
                    {
                        MyLogger.logger.info("Neighbour is surrounded");
                        removeRecursion(neighbour);
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
            if (neighbour == null)
            {
                MyLogger.logger.info("Neighbour is null");
                continue;
            }

            if (neighbour.getColor().equals(pawn.getColor()) && neighbour.getChecked() == false)
            {
                MyLogger.logger.info("Neighbour is the same color");
                removeRecursion(neighbour);
 
            }
            removePawn(pawn);
        }

    }
    public boolean checkIsNotSurrounded(Pawn pawn)
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
            if (neighbour == null)
            {
                continue;
            }
            if (neighbour.getColor().equals(pawn.getColor()) && neighbour.getChecked() == false ) 
            {
                isNeighboursSurrounded = isNeighboursSurrounded || checkIsNotSurrounded(neighbour);
            }
        }

        pawn.setIsChecked(false);
        return isNeighboursSurrounded;
    }

    public String getLastUpdate()
    {
        return this.lastUpdate;
    }
}
