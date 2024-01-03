package com.example.s.players;

import java.io.IOException;
import java.net.Socket;

import com.example.s.ClientInputReader;
import com.example.s.OutputWriter;

public class Player implements IPlayer
{
    private final Socket socket;
    private final String nickname = "Wściekły bot";
    private final ClientInputReader reader;
    private final OutputWriter writer;

    private String playerInput;

    public Player(final Socket socket, ClientInputReader clientInputReader) throws IOException
    {
        this.socket = socket;
        this.reader = clientInputReader;
        this.writer = new OutputWriter(socket);
    }

    @Override
    public void loadInput() 
    {
        this.playerInput = reader.readInput();
    }

    @Override
    public int[] moveRequest() 
    {
        loadInput();
        String[] stringPosition = this.playerInput.split(" ");
        int[] intPosition = new int[2];

        intPosition[0] = Integer.parseInt(stringPosition[0]);
        intPosition[1] = Integer.parseInt(stringPosition[1]);

        return intPosition;
    }

    @Override
    public Boolean isPassed() {
        loadInput();
        if (playerInput.equals("pass"))
        {
            return true;
        }
        return false;
    }

    @Override
    public String introduce() {
        return "hi, I'm " + nickname;
    }

    @Override
    public void writeOutput(String outputMessage) {
        writer.wariteOutput(outputMessage);
    }
    
}
