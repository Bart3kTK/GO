package com.example.s.engine;

import java.net.Socket;

import com.example.s.player.IPlayer;

public class Engine extends Thread
{
    protected final IPlayer player1;
    protected final IPlayer player2;

    public Engine(final IPlayer player1, final IPlayer player2)
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
