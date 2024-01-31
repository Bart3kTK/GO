package com.example.s.engine;
import java.util.logging.Level;
import com.example.s.DatabaseManager;
import com.example.s.board.Board;
import com.example.s.board.pawns.PawnFactory;
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
    private Boolean isGamePaused = false;
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

                if (currentPlayer.getIsPassed())  // ta metoda będzie sprawdzaź czy input to pass, jęsli nie to zapisuje input i daje false
                {
                    MyLogger.logger.log(Level.INFO, "Player passed");
                    if (opponentPlayer.getIsPassed())
                    {
                        MyLogger.logger.info("game stops"); 
                        currentPlayer.writeOutput("pause");
                        opponentPlayer.writeOutput("pause");
                        isGamePaused = true;
                    }
                    break;
                }
                if(currentPlayer.getIsSurrendered())
                {
                    currentPlayer.writeOutput("server " +color+"_surrendered\nServer_closed");
                    opponentPlayer.writeOutput("server " +color+"_surrendered\nServer_closed"); 
                    isGameWorking = false;
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    player1.disconnect();
                    player2.disconnect();
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

    private String handlePause(IPlayer firstPlayer, IPlayer secondPlayer)
    {
        String playerTurn = "nobody";

        player1.loadInput();
        String player1Input = player1.getRawInput();
        if (player1Input.equals("continue"))
        {
            playerTurn = "secondPlayer";
        }
        else if (player1Input.equals("end"))
        {
            playerTurn = "firstPlayer";
        }

        player2.loadInput();
        String players2Input = player2.getRawInput();
        if (players2Input.equals("continue"))
        {
            return playerTurn;
        }
        else if (players2Input.equals("end"))
        {
            if (playerTurn.equals("firstPlayer"))
            {
                return "nobody";
            }
            return "secondPlayer";
        }

        return "nobody";

    }
    
    @Override
    public void run()
    {
        System.out.println("new game started!");

        String playersTurn = "firstPlayer";

        gameID = databaseManager.addNewGame();
        while (isGameWorking)
        {
            if (isGamePaused)
            {
                if (playersTurn.equals("firstPlayer"))
                {
                    playersTurn = handlePause(player2, player1);
                }
                else if (playersTurn.equals("secondPlayer"))
                {
                    playersTurn = handlePause(player1, player2);
                }
                isGamePaused = false;
                continue;
            }

            if (playersTurn.equals("firstPlayer"))
            {
                player1.writeOutput("server " + playersTurn+"_turn");
                player2.writeOutput("server " + playersTurn+"_turn");
                
                turn(player1, player2, "white");
                playersTurn = "secondPlayer";
            }
            else if (playersTurn.equals("secondPlayer"))
            {
                player1.writeOutput("server " + playersTurn+"_turn");
                player2.writeOutput("server " + playersTurn+"_turn");
                turn(player2, player1, "black");
                playersTurn = "firstPlayer";
            }
            else if (playersTurn.equals("nobody"))
            {
                player1.writeOutput("server END_OF_THE_GAME");
                player2.writeOutput("server END_OF_THE_GAME");

                player1.disconnect();
                player2.disconnect();
                break;
            }
        }
    }

}