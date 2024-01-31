package com.example.s.players;


import static org.junit.Assert.assertEquals;
import org.junit.Test;
public class PlayerTest {


    @Test
    public void testPlayerBot() {
        // Create a new PlayerBot
        BotPlayer playerBot = new BotPlayer(10, 10);

        // Test that the name is set correctly
        System.out.println(playerBot.introduce());
        assertEquals("hi, I'm Wściekły bot", playerBot.introduce());

    }
}