package com.example.s;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientInputReader 
{
    private BufferedReader inputReader;

    public ClientInputReader(final Socket source) 
    {
        try {
            this.inputReader = new BufferedReader(new InputStreamReader(source.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String readInput()
    {
        try {
            return inputReader.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    
}
