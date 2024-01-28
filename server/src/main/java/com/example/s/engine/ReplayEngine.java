package com.example.s.engine;

import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;

import com.example.s.DatabaseManager;
import com.example.s.board.Board;
import com.example.s.board.pawns.BlackPawn;
import com.example.s.board.pawns.PawnFactory;
import com.example.s.board.pawns.WhitePawn;
import com.example.s.logger.MyLogger;
import com.example.s.players.IPlayer;

public class ReplayEngine extends Thread implements IEngine
{
    private Board gameBoard;
    private PawnFactory pawnFactory = new PawnFactory();
    protected final IPlayer player1;
    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private ArrayList<String> game = new ArrayList<String>();
    private int moveCounter = -1;
    


    public ReplayEngine(final IPlayer player1)
    {
        this.player1 = player1;
    }

    /*
     *  Method implements whole turn and its logic
     */


     /* Kody aby nie zmieniac kodu w Player.java
      * 1 0 - right button clicked
      * 0 1 - left button clicked
      * 2 {String of int} - ok button clicked
      */
    @Override
    public void run()
    {
        MyLogger.logger.log(Level.INFO, "Replay engine started");
        sendListOfGames();
        while (true)
            {
                player1.writeOutput("done");   // tells client that now is his turn
                player1.loadInput();  // loads input from client
                int[] playerRequest = player1.moveRequest();
                System.out.println("przeczytałem, pozdro");
                if (playerRequest[0] == 2)
                {
                    game = databaseManager.getGameSave(playerRequest[1]);
                    player1.writeOutput("prepare;" + game.get(0).split("\n").length);
                }
                if (playerRequest[0] == 1 && moveCounter < game.size() - 1)
                {
                    moveCounter++;
                    player1.writeOutput("board;" + game.get(moveCounter).replace("\n", "/"));
                }
                if (playerRequest[0] == 0 && moveCounter > 0)
                {
                    moveCounter--;
                    player1.writeOutput("board;" + game.get(moveCounter).replace("\n", "/"));
                    
                }

                // if (gameBoard.isPositionAllowed(playerRequest[0], playerRequest[1], color)) // to trzeba uzupełnić  // już nie trzeba
                // {
                //     gameBoard.putPawn( pawnFactory.producePawn(color, playerRequest[0], playerRequest[1]) );
                //     String messageToClient = gameBoard.getLastUpdate();
                //     MyLogger.logger.log(Level.INFO, "mess to client: " + messageToClient);

                //     // if move beats opponent's pawns then increment count of collected pawns for player and inform players
                //     if (messageToClient.contains("clear"))
                //     {
                //         String[] messageContent = messageToClient.split(";");
                //         currentPlayer.incrementCollectedPawnsCount(messageContent.length - 1);
                //         MyLogger.logger.log(Level.INFO, "collected pawns: " + currentPlayer.getCollectedPawnsCount());  
                //         currentPlayer.writeOutput(color + " " + Integer.toString(currentPlayer.getCollectedPawnsCount()));
                //         opponentPlayer.writeOutput(color + " " + Integer.toString(currentPlayer.getCollectedPawnsCount()));
                //     }

                //     currentPlayer.writeOutput(messageToClient);
                //     opponentPlayer.writeOutput(messageToClient);
                //     MyLogger.logger.info("Pawn placed");
                //     currentPlayer.writeOutput("server _");
                //     break;
                // }
                // else
                // {
                //     System.out.println("Weź jeszcze raz połóż ten żeton, tylko tym razem legalnie");
                //     currentPlayer.writeOutput("server Incorrect_position_try_again!");
                //     // player1.writeOutput("incorrect position");
                // }
                //sprawdzam czy sąsiedzi są uduszeni
            }
    }

    private void sendListOfGames() {
        String listOfGames = "data;";
        for (String game[] : databaseManager.getGameList()) {
            listOfGames += "Data: "+ game[1] + " Nr: " + game[0] + ";";
        }
        player1.writeOutput(listOfGames);
    }
    
}

// TODO: Dołożyć działanieklasy main w serverze i przetestować połączenie dwóch graczy
// TODO: Przed wiekszym rozbudowaniem logiki stworzyć już jakieś połączenie Clienta i servera, tak by mie ć pewność, że wszystko działa
// TODO: mieć dobry humor
// elo