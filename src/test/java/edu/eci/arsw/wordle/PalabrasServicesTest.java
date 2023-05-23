package edu.eci.arsw.wordle;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Palabra;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.LobbiesInterface;
import edu.eci.arsw.wordle.persistence.exceptions.PalabrasException;
import edu.eci.arsw.wordle.services.PalabraServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PalabrasServicesTest {

    @Mock
    private LobbiesInterface lobbiesMock;

    private PalabraServices palabraServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

}
