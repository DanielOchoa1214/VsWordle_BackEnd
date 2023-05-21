package edu.eci.arsw.wordle.services;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Palabra;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.LobbiesInterface;
import edu.eci.arsw.wordle.persistence.exceptions.PalabrasException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PalabrasServicesTest {

    @Mock
    private LobbiesInterface lobbies;

    private PalabraServices palabraServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        palabraServices = new PalabraServices();
    }



    @Test
    public void testGetPalabra() throws PalabrasException {
        int round = 1;
        Lobby lobby = mock(Lobby.class);
        Palabra palabraObj = mock(Palabra.class);

        when(lobby.getPalabra(round)).thenReturn(palabraObj);
        when(palabraObj.getText()).thenReturn("example");

        String result = palabraServices.getWord(round, lobby);

        assertEquals("example", result);
    }

    @Test
    public void testGetPalabra_InvalidRound() {
        int round = 1;
        Lobby lobby = mock(Lobby.class);

        when(lobby.getPalabra(round)).thenThrow(new IndexOutOfBoundsException());

        assertThrows(PalabrasException.class, () -> palabraServices.getWord(round, lobby));
    }




}
