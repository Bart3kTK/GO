package com.example.s.players;

public interface IPlayer 
{
    /* 
     *   this method recives move request from InputReader class wich
     *   takes it from Client
     *   in case of Bot Player, he creates move request on his own
     *  
    */
    public int[] moveRequest();
    public void loadInput();
    public void writeOutput(String outputMessage);

    // control isPassed flags
    public Boolean getIsPassed();
    public void setIsPassed(Boolean isPassed);

    // basiclly says "hi, i'm + nickname"
    public String introduce();

    // checks whether player is connected
    public Boolean isConnected();

    // disconnects player from server
    public void disconnect();

    // methods to track number of beated opponents
    public int getCollectedPawnsCount();
    public void incrementCollectedPawnsCount(int newBeatedPawnsNumber);
}
