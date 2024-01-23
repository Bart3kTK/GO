package com.example.s.logger;

import java.io.IOException;
import java.util.logging.*;

public class MyLogger 
{
    public static final Logger logger = Logger.getGlobal();
    public static void loggerConfig()
    {
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        logger.addHandler(ch);

        logger.setUseParentHandlers(false);

        try
        {
            FileHandler fh = new FileHandler("./src/main/java/com/example/s/logs/log.txt");
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        }
        catch (IOException | SecurityException e)
        {

        }

        logger.setLevel(Level.ALL);
    }
}
