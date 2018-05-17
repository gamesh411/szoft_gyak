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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
    	Player newPlayer = new Player(playerName, color);
    	
    	// For demo purposes randomly leave out one property.
    	List<Property> allProperties = new ArrayList<>();
    	allProperties.addAll(Arrays.asList(Property.values()));
    	
    	Collections.shuffle(allProperties);
    	allProperties = allProperties.subList(0, allProperties.size() - 1);
    	
    	allProperties.stream().forEach(prop -> newPlayer.addProperty(prop));

        board.addPlayer(newPlayer);
    }

    public void buySelectedItem(Property selectedItem) {
        board.getCurrentPlayer().addProperty(selectedItem);
    }

    public Set<Property> getFieldItems() {
        return board.getFields()[board.getCurrentPlayer().getPosition()].getProperties();
    }

    public void checkGame() {
        board.checkGame();
    }


}
