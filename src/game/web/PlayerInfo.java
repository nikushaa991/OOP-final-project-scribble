package game.web;

import game.classes.Game;

import javax.servlet.http.HttpSession;

public class PlayerInfo {
    private final HttpSession sess;
    private final int id;
    private final Game game;

    public PlayerInfo(HttpSession sess, int id, Game game) {
        this.sess = sess;
        this.id = id;
        this.game = game;
    }

    public HttpSession getSess() {
        return sess;
    }

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }
}
