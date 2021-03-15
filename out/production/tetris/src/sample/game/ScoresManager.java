package sample.game;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoresManager {

    private static final String FILE_NAME = "scores.bin";
    private final List<Player> players;

    public ScoresManager() {
        players = new ArrayList<>();
        loadPlayers();
    }

    public void loadPlayers() {
        List<Player> loaded = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            loaded = (List<Player>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No old scores");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        players.addAll(loaded);
    }

    public void savePlayer(Player player) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            players.add(player);
            Collections.sort(players);
            out.writeObject(players);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Player> getPlayers() {
        return players;
    }
}
