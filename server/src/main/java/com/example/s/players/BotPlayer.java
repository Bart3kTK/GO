package com.example.s.players;

import java.util.Random;

/*
 * @brief class represents bot player that means
 *        the opponent is computer
 */
public class BotPlayer extends AbstractPlayer
{
    private int[] playerInput = new int[2];
    private Random random = new Random();
    private final int boardRowsCount;
    private final int boardColumnsCount;

    public BotPlayer(final int boardRowsCount, final int boardColumnsCount)
    {
        this.boardRowsCount = boardRowsCount;
        this.boardColumnsCount = boardColumnsCount;
    }

    @Override
    public int[] moveRequest() 
    {
        return playerInput;
    }

    @Override
    public void loadInput() 
    {
        int row = random.nextInt(this.boardRowsCount);
        int column = random.nextInt(this.boardColumnsCount);
        this.playerInput[0] = row;
        this.playerInput[1] = column;
    }

    @Override
    public void writeOutput(String outputMessage) 
    {
        System.out.println("message recived by bot: " + outputMessage);
    }

    @Override
    public Boolean getIsPassed() 
    {
        return false;
    }
    @Override
    public Boolean getIsSurrendered()
    {
        return false;
    }

    @Override
    public void setIsPassed(Boolean isPassed) 
    {
        
    }

    @Override
    public void disconnect()
    {

    }

    @Override
    public String getRawInput() 
    {
        return Integer.toString(playerInput[0]) + " " + Integer.toString(playerInput[1]);
    }

    @Override
    public Boolean isConnected() 
    {
        return true;
    }
}
