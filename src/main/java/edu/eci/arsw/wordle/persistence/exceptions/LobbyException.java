package edu.eci.arsw.wordle.persistence.exceptions;

public class LobbyException extends Exception{

    public static String IS_CLOSED = "El lobby ya inicio, te dejaron por fuera D:";
    public static String PLAYER_EXISTS = "El jugador ya existe, se mas original";
    public static String PLAYER_DOESNT_EXISTS = "El jugador no existe";
    public static String IS_NOT_FINISHED = "El lobby no ha terminado de jugar";
    public static String LOBBY_NOT_FOUND = "El lobby no se encontro";
    public static String EMPTY_NICK = "Tu nickname no puede estar vacio :(";
    public static String NOT_HOST = "No se proporciono un host para el lobby";
    public static String DEFAULT = "Ohhh no, un error a ocurrido";

    public LobbyException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
