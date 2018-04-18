/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend;

import gazd.backend.Player;
import gazd.controller.MainController;
import gazd.frontend.layouts.StartScreen;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.crypto.spec.PSource;
import javax.swing.JFrame;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public final class GuiManager {
    
    private StartScreen screen;
    private MainController control;
    
    public void start() {
        screen = new StartScreen(this);
        screen.pack();
        screen.setVisible(true);
    }
    
    public void newGame() {
        control = new MainController();
        control.newGame(this);
    }
    
    public void doRoll() {
        control.onRoll();
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

}