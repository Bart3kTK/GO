package com.example.s.players;

import java.io.IOException;
import java.net.Socket;

import com.example.s.ClientInputReader;
import com.example.s.OutputWriter;

public class Player extends AbstractPlayer
{
    private final Socket socket;
    private final ClientInputReader reader;
    private final OutputWriter writer;

    private String playerInput;

    private Boolean isPassed = false;
    private Boolean isSurrendered = false;
    private Boolean wantToContinue = false;

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
        if (playerInput.equals("pass"))
        {
            this.isPassed = true;
        }
        else
        {
            this.isPassed = false;
        }
        if(playerInput.equals("surrender"))
        {
            this.isSurrendered = true;
        }

    }

    @Override
    public String getRawInput()
    {
        return this.playerInput;
    }

    @Override
    public int[] moveRequest() 
    {
        String[] stringPosition = this.playerInput.split(" ");
        int[] intPosition = new int[2];

        try
        {
            intPosition[0] = Integer.parseInt(stringPosition[0]);
            intPosition[1] = Integer.parseInt(stringPosition[1]);
        }
        catch (NumberFormatException e)
        {
            intPosition[0] = -1;
            intPosition[1] = -1;
        }

        return intPosition;
    }

    @Override
    public Boolean getIsPassed() 
    {
        return isPassed;
    }
    @Override
    public Boolean getIsSurrendered()
    {
        return isSurrendered;
    }

    @Override
    public void setIsPassed(Boolean isPassed) 
    {
        this.isPassed = isPassed;
    }

    @Override
    public void writeOutput(String outputMessage) {
        writer.wariteOutput(outputMessage);
    }

    @Override
    public Boolean isConnected() 
    {
        if (playerInput == null)
        {
            return socket.isConnected();
        }
        return socket.isConnected()  && !(playerInput.equals("exit"));
    }

    @Override
    public void disconnect()
    {
        try 
        {
            reader.close();
            writer.close();
            socket.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
}
