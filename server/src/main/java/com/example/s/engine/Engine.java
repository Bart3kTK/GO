package com.example.s.engine;

import java.net.Socket;
import java.util.logging.Level;

import com.example.s.board.Board;
import com.example.s.board.pawns.BlackPawn;
import com.example.s.board.pawns.PawnFactory;
import com.example.s.board.pawns.WhitePawn;
import com.example.s.logger.MyLogger;
import com.example.s.players.IPlayer;

public class Engine extends Thread
{
    private Board gameBoard;
    private PawnFactory pawnFactory = new PawnFactory();
    protected final IPlayer player1;
    protected final IPlayer player2;   // w grze z botem player dwa to będzie bot


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
                        for (String information : messageContent) 
                        {
                            currentPlayer.incrementCollectedPawnsCount();   
                        }
                        currentPlayer.writeOutput(Integer.toString(currentPlayer.getCollectedPawnsCount()));
                        opponentPlayer.writeOutput(Integer.toString(currentPlayer.getCollectedPawnsCount()));
                    }

                    currentPlayer.writeOutput(messageToClient);
                    opponentPlayer.writeOutput(messageToClient);
                    MyLogger.logger.info("Pawn placed");
                    break;
                }
                else
                {
                    System.out.println("Weź jeszcze raz połóż ten żeton, tylko tym razem legalnie");
                    currentPlayer.writeOutput("display");
                    currentPlayer.writeOutput("Illegal move! Try again");
                    // player1.writeOutput("incorrect position");
                }
            }
    }
    
    @Override
    public void run()
    {
        System.out.println(player1.introduce() + " and " + player2.introduce() + " started game");
        System.out.println("biali zaczynają (bo są lepsi)");
        while (true)
        {
           turn(player1, player2, "white");

           turn(player2, player1, "black");
        }
    }
}

// TODO: Dołożyć działanieklasy main w serverze i przetestować połączenie dwóch graczy
// TODO: Przed wiekszym rozbudowaniem logiki stworzyć już jakieś połączenie Clienta i servera, tak by mie ć pewność, że wszystko działa
// TODO: mieć dobry humor
// elo