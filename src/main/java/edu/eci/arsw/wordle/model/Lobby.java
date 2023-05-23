package edu.eci.arsw.wordle.model;

import edu.eci.arsw.wordle.persistence.exceptions.LobbyException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Document(collection = "Lobbies")
public class Lobby implements Serializable {
    private static final long serialVersionUID = 80085L;
    private static final String ARCHIVO_PALABRAS = "wordlist_spanish_fil.txt";
    private final int MAX_ROUNDS = 10;
    private static List<Palabra> wordList = null;
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    private final AtomicBoolean isFinished = new AtomicBoolean(false);
    private List<Player> playerList;
    private List<Palabra> palabraList;
    // @Id
    private String id;
    private Player host = null;
    private static SecureRandom random = new SecureRandom();

    public Lobby() {
        try {
            setWordList();
        }catch (Exception ignored) {}
        this.id = generateIdLobby();
        this.playerList = new ArrayList<>();
        this.palabraList = lobbyWords(MAX_ROUNDS);
    }

    public Lobby(String id){
        this();
        this.id = id;
    }

    private static LobbyException setWordList() throws LobbyException {
        if(wordList == null) {
            wordList = new ArrayList<>();
            BufferedReader br = null;
            try {
                File file = new File(ARCHIVO_PALABRAS);
                br = new BufferedReader(new FileReader(file));

                String palabra;
                while((palabra = br.readLine()) != null) {
                    wordList.add(new Palabra(palabra));
                }
                br.close();
            } catch (IOException e ) {
                throw new LobbyException(LobbyException.DEFAULT);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ignored) {
                    }
                }
            }
            Collections.shuffle(wordList, random);
        }
        throw new LobbyException(LobbyException.DEFAULT);
    }

    private List<Palabra> lobbyWords(int rounds) {
        List<Palabra> lobbyWords = new ArrayList<>();
        for(int i = 0; i<rounds; i++) {
            int wordIndex = random.nextInt(wordList.size());
            lobbyWords.add(wordList.get(wordIndex));
        }
        return lobbyWords;
    }

    private void setClosed() {
        if(playerList.size() >= 5) {
            isClosed.set(true);
        }
    }

    private List<String> playersNicknames() {
        List<String> nicknames = new ArrayList<>();
        for (Player player: playerList) {
            nicknames.add(player.getNickname());
        }
        return nicknames;
    }

    private String generateIdLobby() {
        return RandomStringUtils.randomAlphabetic(4).toLowerCase();
    }

    public boolean nicknameExists(Player player) {
        return playersNicknames().contains(player.getNickname());
    }

    public boolean addPlayer(Player player) {
        synchronized (palabraList) {
            if(!isClosed.get()) {
                setHost(player);
                playerList.add(player);
                setClosed();
                return true;
            }
        }
        return false;
    }

    public void removePlayer(Player player){
        synchronized (playerList) {
            playerList.remove(player);
            if(player.equals(host)) {
                try {
                    host = playerList.get(0);
                } catch (IndexOutOfBoundsException e) {
                    resetLobby();
                    throw e;
                }
            }
        }
    }

    public Palabra getPalabra(int round) {
        return palabraList.get(round);
    }

    public List<Palabra> getPalabras() {
        return palabraList;
    }

    public Player getPlayer(String nickname) {
        for(Player player: playerList) {
            if(player.getNickname().equals(nickname)) {
                return player;
            }
        }
        return null;
    }

    public List<Player> getPlayers() {
        return playerList;
    }

    public List<String> getMissingPlayers(String host) {
        List<String> allNicknames = playersNicknames();
        allNicknames.remove(host);
        return allNicknames;
    }

    public boolean startGame() {
        isClosed.set(true);
        return isClosed.get();
    }

    public Player lobbyWinner() {
        Player playerWinner = new Player();
        //no haya empezado la partida
        for (Player player: playerList) {
            if (player.getRoundsWon() > playerWinner.getRoundsWon()){
                playerWinner = player;
            }
        }
        return playerWinner;
    }

    public String toString() {
        return "id = " + id + ", palabras: " + palabraList + ", host: " + host;
    }

    public String getId() {
        return id;
    }

    public void resetLobby() {
        host = null;
        isClosed.set(false);
        isFinished.set(false);
        palabraList = lobbyWords(10);
        playerList = new ArrayList<>();
    }

    public AtomicBoolean getIsFinished() {
        return isFinished;
    }

    public AtomicBoolean getIsClosed() {
        return isClosed;
    }

    public void setHost(Player host) {
        if(this.host == null) {
            this.host = host;
        }
    }

    public Player getHost() {
        return host;
    }

    public List<Player> statistics() {
        List<Player> copy = new ArrayList<>(playerList);
        copy.sort(Comparator.comparing(Player::getRoundsWon).reversed());
        return copy;
    }

    public void setId(String newId){
        this.id = newId;
    }

    public boolean equals(Object o){
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;
        return this.equals((Lobby) o);
    }

    private boolean equals(Lobby lobby){
        return this.id.equals(lobby.id);
    }
}