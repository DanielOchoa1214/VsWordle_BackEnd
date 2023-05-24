package edu.eci.arsw.wordle.services;


import edu.eci.arsw.wordle.model.Palabra;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class PalabraTest {

    @Test
    public void testGetText() {
        Palabra palabra = new Palabra("Hola");
        assertEquals("Hola", palabra.getText());
    }

    @Test
    public void testIsTaken() {
        Palabra palabra = new Palabra("Hola");
        assertFalse(palabra.isTaken());
    }

    @Test
    public void testSetTaken() {
        Palabra palabra = new Palabra("Hola");
        palabra.setTaken(true);
        assertTrue(palabra.isTaken());
    }

    @Test
    public void testEquals() {
        Palabra palabra1 = new Palabra("Hola");
        Palabra palabra2 = new Palabra("Hola");
        Palabra palabra3 = new Palabra("Adiós");

        assertTrue(palabra1.equals(palabra2));
        assertFalse(palabra1.equals(palabra3));
    }


}

