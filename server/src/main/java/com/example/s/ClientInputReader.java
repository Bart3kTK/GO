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
        System.out.println(source);
        try {
            this.inputReader = new BufferedReader(new InputStreamReader(source.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void close()
    {
        try 
        {
            inputReader.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public String readInput()
    {
        try 
        {
            String input = null;
            input = inputReader.readLine();
            System.out.println(input);
            if (input == null)
            {
                return "exit";
            }
            else
            {
                return input;
            }
        } 
        catch (IOException e) 
        {
            System.out.println("output reader is closed");
            return null;
        }
    }
    
}
