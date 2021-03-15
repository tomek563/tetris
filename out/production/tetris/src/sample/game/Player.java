package sample.game;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable, Comparable<Player> {

    public static final long serialVersionUID = 1L;

    private String name;
    private int scores;

    public void addScore(int rows) {
        scores += rows * 10;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScores() {
        return scores;
    }

    @Override
    public String toString() {
        return String.format("%s - %d points", name, scores);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return scores == player.scores &&
                Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, scores);
    }

    @Override
    public int compareTo(Player o) {
        return o.scores - scores;
    }
}
