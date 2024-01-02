package com.example.s.engine;

import java.net.Socket;

<<<<<<< HEAD
import com.example.s.board.Board;
import com.example.s.board.pawns.BlackPawn;
import com.example.s.board.pawns.WhitePawn;
import com.example.s.players.IPlayer;

public class Engine extends Thread
{
    private Board gameBoard;
    protected final IPlayer player1;
    protected final IPlayer player2;   // w grze z botem player dwa to będzie bot

    public Engine(final IPlayer player1, final IPlayer player2, Board gameBoard)
=======
import com.example.s.player.IPlayer;

public class Engine extends Thread
{
    protected final IPlayer player1;
    protected final IPlayer player2;

    public Engine(final IPlayer player1, final IPlayer player2)
>>>>>>> main
    {
        this.player1 = player1;
        this.player2 = player2;
        this.gameBoard = gameBoard;
    }
    
    @Override
    public void run()
    {
        System.out.println(player1.introduce() + " and " + player2.introduce() + " started game");
        System.out.println("biali zaczynają (bo są lepsi)");
        while (true)
        {
            while (true)
            {
                player1.loadInput();
                if(player1.isPassed())  // ta metoda będzie sprawdzaź czy input to pass, jęsli nie to zapisuje input i daje false
                {
                    System.out.println("spasował :(");
                    break;
                }
                int[] player1Request = player1.moveRequest();
                if (gameBoard.isPositionFree(player1Request[0], player1Request[1])) // to trzeba uzupełnić  // już nie trzeba
                {
                    gameBoard.putPawn(player1Request[0], player1Request[1], new WhitePawn());
                    System.out.println("weź poinformuj gracza o tym że pionek się zmienił");
                    break;
                }
                else
                {
                    System.out.println("Weź jeszcze raz połóż ten żeton, tylko tym razem legalnie");
                }
            }

            while (true)
            {
                player2.loadInput();
                if(player2.isPassed())
                {
                    System.out.println("spasował :(");
                    break;
                }
                int[] player2Request = player2.moveRequest();
                if (gameBoard.isPositionFree(player2Request[0], player2Request[1])) // to trzeba uzupełnić
                {
                    gameBoard.putPawn(player2Request[0], player2Request[1], new BlackPawn());
                    System.out.println("weź poinformuj gracza o tym że pionek się zmienił");
                    break;
                }
                else
                {
                    System.out.println("Weź jeszcze raz połóż ten żeton, tylko tym razem legalnie");
                }
            }
        }
    }
}

// TODO: Dołożyć działanieklasy main w serverze i przetestować połączenie dwóch graczy
// TODO: Przed wiekszym rozbudowaniem logiki stworzyć już jakieś połączenie Clienta i servera, tak by mie ć pewność, że wszystko działa
// TODO: mieć dobry humor
// elo