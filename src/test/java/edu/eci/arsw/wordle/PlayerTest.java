package edu.eci.arsw.wordle;
import edu.eci.arsw.wordle.model.Player;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testAddRoundWon() {
        Player player = new Player("Daniela");
        player.addRoundWon();
        assertEquals(1, player.getRoundsWon());
    }

    @Test
    public void testGetNickname() {
        Player player = new Player("Daniela");
        assertEquals("Daniela", player.getNickname());
    }

    @Test
    public void testGetRoundsWon() {
        Player player = new Player("Daniela");
        assertEquals(0, player.getRoundsWon());
    }

    @Test
    public void testAddWrongLetter() {
        Player player = new Player("Daniela");
        player.addWrongLetter();
        assertEquals(1, player.getWrongLetters());
    }

    @Test
    public void testAddCorrectLetter() {
        Player player = new Player("Daniela");
        player.addCorrectLetter();
        assertEquals(1, player.getCorrectLetters());
    }

    @Test
    public void testGetCorrectLetters() {
        Player player = new Player("Daniela");
        assertEquals(0, player.getCorrectLetters());
    }

    @Test
    public void testGetWrongLetters() {
        Player player = new Player("Daniela");
        assertEquals(0, player.getWrongLetters());
    }

    @Test
    public void testToString() {
        Player player = new Player("Daniela");
        player.addRoundWon();
        String expected = "{Nickname: Daniela Puntos: 1}";
        assertEquals(expected, player.toString());
    }

    @Test
    public void testEquals() {
        Player player1 = new Player("Daniela");
        Player player2 = new Player("Daniela");
        Player player3 = new Player("Wilmer");

        assertTrue(player1.equals(player2));
        assertFalse(player1.equals(player3));
    }
}
