/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend;

import gazd.backend.Piece;
import gazd.backend.Player;
import gazd.controller.MainController;
import gazd.frontend.windows.InitGameScreen;
import gazd.frontend.windows.StartScreen;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ComboBoxModel;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public final class GuiManager {

    private StartScreen screen;
    private InitGameScreen initGameScreen;
    private MainController control;

    public void start() {
        screen = new StartScreen(this);
        screen.pack();
        screen.setVisible(true);
    }

    public void newGame() {
        control = new MainController(this);
        control.newGame();
        initGameScreen = new InitGameScreen(this);
        initGameScreen.pack();
        initGameScreen.setVisible(true);
    }

    public void doRoll() {
        control.onRoll();
        screen.repaint();
    }

    public List<Player> getPlayers() {
        return control == null ? new LinkedList<>() : control.getPlayers();
    }

    public Player getCurrentPlayer() {
        return control == null ? null : control.getCurrentPlayer();
    }

    //TODO: Some consistent update implementation across the gui.
    public void update() {
        screen.update();
    }

    public void endRound() {
        control.endRound();

    }

    public Piece[] getColors() {
        //Is it okay, or should we return with MainControllers getColors method?
        return Piece.values();
    }

    public void addPlayer(String playerName, Piece color) {
        control.addPlayer(playerName, color);
    }

    public void startGame() {
        control.start();
        screen.repaint();
    }

}
