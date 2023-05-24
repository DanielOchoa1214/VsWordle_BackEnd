package edu.eci.arsw.wordle.services;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Palabra;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.LobbiesInterface;
import edu.eci.arsw.wordle.persistence.exceptions.PalabrasException;
import edu.eci.arsw.wordle.services.PalabraServices;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;


public class PalabrasServicesTest {

    @Mock
    private LobbiesInterface lobbiesMock;

    @InjectMocks
    private PalabraServices palabraServices;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenWordIsCorrectThenProveWordReturnsTrue(){
        // ARRANGE
        Lobby lobby = new Lobby();
        String firstWord = lobby.getPalabra(0).getText();
        lobby.addPlayer(new Player("Yo"));
        // ACT
        boolean wordTaken = palabraServices.proveWord(firstWord, 0, "Yo", lobby);
        // ASSERT
        assertTrue(wordTaken);
    }

    @Test
    public void whenWordIsIncorrectThenProveWordReturnsTrue(){
        // ARRANGE
        Lobby lobby = new Lobby();
        String firstWord = "Putito";
        lobby.addPlayer(new Player("Yo"));
        // ACT
        boolean wordTaken = palabraServices.proveWord(firstWord, 0, "Yo", lobby);
        // ASSERT
        assertFalse(wordTaken);
    }

    @Test(expected = PalabrasException.class)
    public void whenIndexOutOfBoundsThenGetWordTrowsControlledException() throws PalabrasException {
        // ARRANGE
        Lobby lobby = new Lobby();
        // ACT
        palabraServices.getWord(10, lobby);
        // ASSERT
    }

    @Test
    public void whenValidIndexThenGetWordReturnsWord() throws PalabrasException {
        // ARRANGE
        Lobby lobby = new Lobby();
        String palabra = lobby.getPalabra(0).getText();
        // ACT
        String servicePalabra = palabraServices.getWord(0, lobby);
        // ASSERT
        assertEquals(palabra, servicePalabra);
    }

    @Test
    public void whenGettingWordsReturnsListOfWords() throws PalabrasException {
        // ARRANGE
        Lobby lobby = new Lobby();
        List<Palabra> palabras = lobby.getPalabras();
        // ACT
        List<Palabra> servicePalabras = palabraServices.getWords(lobby);
        // ASSERT
        assertEquals(palabras, servicePalabras);
    }

}
