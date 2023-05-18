package edu.eci.arsw.wordle.model;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {
    private static final long serialVersionUID = 80085L;
    private String nickname;
    private int roundsWon = 0;
    private int wrongLetters;
    private int correctLetters;

    public Player(String nickname){
        this.nickname = nickname;
    }

    public Player(){}

    public void addRoundWon(){
        roundsWon++;
    }

    public String getNickname() {
        return nickname;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public void addWrongLetter() {wrongLetters++;}

    public void addCorrectLetter() {correctLetters++;}

    public int getCorrectLetters() {
        return correctLetters;
    }

    public int getWrongLetters() {
        return wrongLetters;
    }

    @Override
    public String toString() {
        return "{Nickname: " + nickname + " Puntos: " + roundsWon + "}";
    }

    @Override
    public boolean equals(Object obj) {
        return this.equals((Player) obj);
    }

    private boolean equals(Player player){
        return this.nickname.equals(player.nickname);
    }
}
