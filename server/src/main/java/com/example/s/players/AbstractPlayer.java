package com.example.s.players;

public abstract class AbstractPlayer implements IPlayer
{
    private final String nickname = "Wściekły bot";
    private int collectedPawnsCount = 0;

    public int getCollectedPawnsCount()
    {
        return collectedPawnsCount;
    }

    public void incrementCollectedPawnsCount(int newBeatedPawnsNumber)
    {
        collectedPawnsCount += newBeatedPawnsNumber;
    }

    public String introduce() 
    {
        return "hi, I'm " + nickname;
    }
}
