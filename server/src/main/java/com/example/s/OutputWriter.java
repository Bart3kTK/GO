package com.example.s;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class OutputWriter 
{
    private PrintWriter out;

    public OutputWriter(Socket destination)
    {
        try {
            this.out = new PrintWriter(destination.getOutputStream(), true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void close()
    {
        out.close();
    }

    public void wariteOutput(String outputMessage)
    {
        out.println(outputMessage + "\n");
    }
}
