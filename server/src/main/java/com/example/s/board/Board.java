package com.example.s.board;

import java.util.ArrayList;

import com.example.s.board.pawns.BlackPawn;
import com.example.s.board.pawns.BorderPawn;
import com.example.s.board.pawns.NullPawn;
import com.example.s.board.pawns.Pawn;
import com.example.s.board.pawns.PawnFactory;
import com.example.s.board.pawns.WhitePawn;
import com.example.s.logger.MyLogger;
//TODO:zmeinaic ----- na 'null' w bazie
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
    private String lastBoardState;
    private String lastlastBoardState;

    public Board(final int rows, final int columns)
    {
        this.rows = rows;
        this.columns = columns;
        this.board = new Pawn[rows][columns];
        
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < columns; j++) 
            {
                this.board[i][j] = new NullPawn(i, j);
            }
        }

        for (Pawn[] row : board) 
        {
            for (Pawn pawn : row) 
            {
                pawn.setNeighbours(this.getActualneighbours(pawn));
            }
        }
    }

    public void putPawn(final Pawn pawn)
    {
        this.board[pawn.getRow()][pawn.getColumn()] = pawn;
        this.lastUpdate = pawn.toString();

        pawn.setNeighbours(this.getActualneighbours(pawn));

        for (Pawn neighbour : pawn.getNeighbours()) 
        {   
            neighbour.setNeighbours(this.getActualneighbours(neighbour));                
        }

        doPossibleUpdates(pawn);

        this.lastlastBoardState = lastBoardState;
        this.lastBoardState = this.toString();
        
    }


    public void removePawn(final Pawn pawn)
    {
        if (!pawn.getColor().equals("null") && !pawn.getColor().equals("UNDEFINED"))
        {
            Pawn nullPawn = new NullPawn(pawn.getRow(), pawn.getColumn());
            this.board[pawn.getRow()][pawn.getColumn()] = nullPawn;
            nullPawn.setNeighbours(this.getActualneighbours(nullPawn));

            this.lastUpdate += ";" + pawn.toStringClearMessage();
            MyLogger.logger.info("Removing pawn: " + pawn.toString());
            MyLogger.logger.info("Last update: " + this.lastUpdate);

            for (Pawn neighbour : pawn.getNeighbours()) 
            {   
                if (!neighbour.getColor().equals("UNDIFINED"))
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
                if (pawn.getColor().equals("null"))
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
        if (!board[row][column].getColor().equals("null"))
        {
            return false;
        }


        // then check if pawn is not surrounded
        PawnFactory pawnFactory = new PawnFactory();                                //
        Pawn testPawn = pawnFactory.producePawn(color, row, column);                // Creating test pawn
        this.board[testPawn.getRow()][testPawn.getColumn()] = testPawn;             //
        
        testPawn.setNeighbours(this.getActualneighbours(testPawn));
        for (Pawn neighbour : testPawn.getNeighbours())                             //
        {       
                if(!neighbour.getColor().equals("UNDEFINED"))
                {
                    neighbour.setNeighbours(this.getActualneighbours(neighbour));
                }                     
        }                                                                           //




        if(!checkIsNotSurrounded(testPawn))                                                         
        {  
            Boolean returnFlag = false;

            for (Pawn neighbour : testPawn.getNeighbours())                                         //
            {                                                                                                                                                               
                if (!neighbour.getColor().equals(testPawn.getColor()) && !neighbour.getColor().equals("null" )&& !neighbour.getColor().equals("UNDEFINED"))                          //
                {                                                                               //
                    if (!checkIsNotSurrounded(neighbour))                                       //
                    {   
                        board[neighbour.getRow()][neighbour.getColumn()] = pawnFactory.producePawn("null", neighbour.getRow(), neighbour.getColumn());
                        MyLogger.logger.info(lastlastBoardState);
                        MyLogger.logger.info(lastBoardState);
                        if (!this.toString().equals(this.lastlastBoardState))
                        {
                            returnFlag = true;
                        }     
                        board[neighbour.getRow()][neighbour.getColumn()] = neighbour;           //  If move is suicide
                    }                                                                           //  but there is a possibility 
                                                                                                //  of shortness of breath
                }                                                                               //
    
            }

            Pawn nullPawn = new NullPawn(testPawn.getRow(), testPawn.getColumn());
            this.board[testPawn.getRow()][testPawn.getColumn()] = nullPawn;  
            nullPawn.setNeighbours(this.getActualneighbours(nullPawn));
            
            for (Pawn neighbour : testPawn.getNeighbours())                                 //
            {                                                                             //
                if (!neighbour.getColor().equals("UNDEFINED"))                                                      // If move is suicide move
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
            if (!neighbour.getColor().equals(pawn.getColor()) && !neighbour.getColor().equals("null") && !neighbour.getColor().equals("UNDEFINED"))
            {
                if (!checkIsNotSurrounded(neighbour))
                {
                    MyLogger.logger.info("Neighbour is surrounded");
                    removeRecursion(neighbour);
                }
            }
        }
    }
    public void removeRecursion(Pawn pawn)
    {
        pawn.setIsChecked(true);

        for (Pawn neighbour : pawn.getNeighbours())
        {
            if (neighbour.getColor().equals("null") || neighbour.getColor().equals("UNDEFINED"))
            {
                MyLogger.logger.info("Neighbour is null");
                continue;
            }

            if (neighbour.getColor().equals(pawn.getColor()) && neighbour.getChecked() == false)
            {
                MyLogger.logger.info("Neighbour is the same color");
                removeRecursion(neighbour);
 
            }
        }

        removePawn(pawn);

    }
    public boolean checkIsNotSurrounded(Pawn pawn)
    {
        pawn.setIsChecked(true);

        Boolean isNeighboursSurrounded = false;

        for (Pawn neighbour : pawn.getNeighbours())
        {
            if (neighbour.getColor().equals("null"))
            {
                pawn.setIsChecked(false);
                return true;
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
    
    
    public boolean isTerritory(Pawn pawn, String teamColor)
    {
        if (pawn.getChecked() == true || !pawn.getColor().equals("null"))
        {
            return false;
        }
        boolean isAvaible = true;
        pawn.setIsChecked(true);
        for (Pawn neighbour : pawn.getNeighbours())
        {
            if (!neighbour.getColor().equals(teamColor) && !neighbour.getColor().equals("null") && !neighbour.getColor().equals("UNDEFINED"))
            {
                isAvaible = false;
                continue;
            }
            else if (neighbour.getColor().equals(teamColor) || neighbour.getColor().equals("UNDEFINED") || neighbour.getChecked() == true)
            {
                continue;
            }
            else if (neighbour.getColor().equals("null"))
            {
                boolean wait = isTerritory(neighbour, teamColor);
                isAvaible = isAvaible && wait;
                
            }
        }
        return isAvaible;
    }
    public int countTerritory(Pawn pawn)
    {
        int territory = 1;
        pawn.setIsChecked(true);
        for (Pawn neighbour : pawn.getNeighbours())
        {
            if (neighbour.getColor().equals("null") && neighbour.getChecked() == false)
            {
                territory += countTerritory(neighbour);
            }
        }
        return territory;
    }
    public void clearChecked()
    {
        for (Pawn[] row : board) 
        {
            for (Pawn pawn : row) 
            {
                pawn.setIsChecked(false);
            }
        }
    }
    public ArrayList<Pawn> mainTerritoryPawns(String teamColor)
    {
        ArrayList<Pawn> mainTerritoryPawns = new ArrayList<Pawn>();

        for (Pawn[] row : board) 
        {
            for (Pawn pawn : row) 
            {
                if (pawn.getColor().equals("null") && pawn.getChecked() == false)
                {
                    if (isTerritory(pawn, teamColor))
                    {
                        mainTerritoryPawns.add(pawn);
                        MyLogger.logger.info("Territory pawn: " + pawn.toString());
                    }
                } 
            }
        }
        clearChecked();
        return mainTerritoryPawns;
    }
    public int countTerritoryPoints(String teamColor)
    {
        int territoryPoints = 0;
        ArrayList<Pawn> mainTerritoryPawns = mainTerritoryPawns(teamColor);
        for (Pawn pawn : mainTerritoryPawns)
        {
            territoryPoints += countTerritory(pawn);
        }
        clearChecked();
        return territoryPoints;

}

}