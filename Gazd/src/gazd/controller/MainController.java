/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller;

import gazd.controller.action.NextPlayerGameAction;
import gazd.controller.action.StepAction;
import gazd.backend.*;
import gazd.frontend.GuiManager;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author MetaPC
 */
public class MainController {

    private Board board;
    private GuiManager gui;

    public MainController(GuiManager gui) {
        this.gui = gui;
    }

    public void newGame() {
        board = new Board();
    }

    public void start() {
        board.start();
        gui.update();
    }

    public void onRoll() {
        board.queueLateAction(new StepAction(board, gui));
        board.doTurn();
        gui.update();
    }

    public List<Player> getPlayers() {
        return board.getPlayers();
    }

    public Player getCurrentPlayer() {
        return board.getCurrentPlayer();
    }

    public void endRound() {
        board.queueLateAction(new NextPlayerGameAction(board, gui));
        board.doTurn();
        gui.update();
    }

    public void addPlayer(String playerName, Piece color) {
        board.addPlayer(new Player(playerName, color));
    }

}
