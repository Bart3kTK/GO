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
    public Boolean isPassed();
    public String introduce();
}
