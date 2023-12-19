package main.java.com.example.s.engine;

import java.net.Socket;

public class Engine extends Thread
{
    protected final Socket player1;
    protected final Socket player2;   // w grze z botem player dwa to będzie bot

    public Engine(final Socket player1, final Socket player2)
    {
        this.player1 = player1;
        this.player2 = player2;
    }
    
    @Override
    public void run()
    {
        System.out.println("player1 vs player2 " + player1 + player2);
    }
}
