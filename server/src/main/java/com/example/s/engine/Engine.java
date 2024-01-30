package com.example.s.engine;

import java.net.Socket;
import java.util.logging.Level;

import org.h2.store.Data;

import com.example.s.DatabaseManager;
import com.example.s.board.Board;
import com.example.s.board.pawns.BlackPawn;
import com.example.s.board.pawns.PawnFactory;
import com.example.s.board.pawns.WhitePawn;
import com.example.s.logger.MyLogger;
import com.example.s.players.IPlayer;

public class Engine extends Thread implements IEngine
{
    private Board gameBoard;
    private PawnFactory pawnFactory = new PawnFactory();
    protected final IPlayer player1;
    protected final IPlayer player2;   // bot is also instance of IPlayer
    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private Boolean isGameWorking = true;
    private int gameID;


    public Engine(final IPlayer player1, final IPlayer player2, Board gameBoard)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.gameBoard = gameBoard;
    }

    /*
     *  Method implements whole turn and its logic
     */
    private void turn(IPlayer currentPlayer, IPlayer opponentPlayer, String color)
    {
        while (true)
            {
                currentPlayer.writeOutput("done");   // tells client that now is his turn
                currentPlayer.loadInput();  // loads input from client
                if(currentPlayer.getIsPassed())  // ta metoda będzie sprawdzaź czy input to pass, jęsli nie to zapisuje input i daje false
                {
                    MyLogger.logger.log(Level.INFO, "Player passed");
                    break;
                }

                /*
                 * check is player still connected
                 * if it is not, server closes sockets both of players
                 */
                else if (currentPlayer.isConnected() == false)
                {
                    currentPlayer.disconnect();
                    opponentPlayer.writeOutput("server opponent_disconnected");
                    opponentPlayer.disconnect();
                    isGameWorking = false;
                    break;
                }

                int[] playerRequest = currentPlayer.moveRequest();
                System.out.println("przeczytałem, pozdro");
                if (gameBoard.isPositionAllowed(playerRequest[0], playerRequest[1], color)) // to trzeba uzupełnić  // już nie trzeba
                {
                    gameBoard.putPawn( pawnFactory.producePawn(color, playerRequest[0], playerRequest[1]) );
                    String messageToClient = gameBoard.getLastUpdate();
                    MyLogger.logger.log(Level.INFO, "mess to client: " + messageToClient);

                    // if move beats opponent's pawns then increment count of collected pawns for player and inform players
                    if (messageToClient.contains("clear"))
                    {
                        String[] messageContent = messageToClient.split(";");
                        currentPlayer.incrementCollectedPawnsCount(messageContent.length - 1);
                        MyLogger.logger.log(Level.INFO, "collected pawns: " + currentPlayer.getCollectedPawnsCount());  
                        currentPlayer.writeOutput(color + " " + Integer.toString(currentPlayer.getCollectedPawnsCount()));
                        opponentPlayer.writeOutput(color + " " + Integer.toString(currentPlayer.getCollectedPawnsCount()));
                    }

                    currentPlayer.writeOutput(gameBoard.countTerritoryPoints("white") + " " + gameBoard.countTerritoryPoints("black") + " territory");
                    opponentPlayer.writeOutput(gameBoard.countTerritoryPoints("white") + " " + gameBoard.countTerritoryPoints("black")+ " territory");
                    currentPlayer.writeOutput(messageToClient);
                    opponentPlayer.writeOutput(messageToClient);
                    MyLogger.logger.info("Pawn placed");
                    currentPlayer.writeOutput("server _");
                    databaseManager.addBoardString(gameBoard.toString(), gameID);
                    break;
                }
                else
                {
                    MyLogger.logger.info("Pawn placed incorrect");
                    currentPlayer.writeOutput("server Incorrect_position_try_again!");
                    // player1.writeOutput("incorrect position");
                }
            }
    }
    
    @Override
    public void run()
    {
        System.out.println("new game started!");
        gameID = databaseManager.addNewGame();
        while (isGameWorking)
        {
           turn(player1, player2, "white");

           if (!isGameWorking)
           {
            break;
           }

           turn(player2, player1, "black");
        }
    }

}