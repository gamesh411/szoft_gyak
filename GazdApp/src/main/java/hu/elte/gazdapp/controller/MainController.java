/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller;

import hu.elte.gazdapp.backend.domain.component.Property;
import hu.elte.gazdapp.backend.domain.component.Piece;
import hu.elte.gazdapp.backend.domain.Player;
import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.controller.action.NextPlayerGameAction;
import hu.elte.gazdapp.controller.action.StepAction;
import hu.elte.gazdapp.controller.action.CostAction;
import hu.elte.gazdapp.controller.action.GameAction;
import hu.elte.gazdapp.controller.action.MoveAction;
import hu.elte.gazdapp.controller.action.ShowMessageGameAction;
import hu.elte.gazdapp.frontend.GuiManager;
import java.util.List;
import java.util.Set;

/**
 *
 * @author MetaPC
 */
public class MainController {

    private Board board;
    private GuiManager gui;
    public final static int REPAY_AMOUNT = 5000;
    public final static int LOAN = 20000;
    public static final int OWN_RESOURCE = 15000; 

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

    public void buySelectedItem(Property selectedItem) {
        GameAction purchase = new CostAction(board, selectedItem.getPrice());
        board.queueImmediateAction(purchase);
        board.doTurn();
        board.getCurrentPlayer().addProperty(selectedItem);
        gui.update();
    }

    public Set<Property> getFieldItems() {
        return board.getPropertiesOfField(board.getCurrentPlayersPosition());
    }

    public void checkGame() {
        board.checkGame();
    }

    public boolean isAnyPurchasableItem() {
        Set<Property> p = getFieldItems();
        if(p.contains(Property.HOUSEHOLD) || p.contains(Property.KITCHEN) || p.contains(Property.LIVING)){
            //Law of Demeter
            return board.getCurrentPlayer().getProperties().contains(Property.HOUSE);
        }
        return !p.isEmpty();
    }
    
    public void hackMove(int n){
        board.queueImmediateAction(new MoveAction(board, gui, n));
        board.doTurn();
        gui.update();
    }

    public boolean canLoan() {
        int pos = board.getCurrentPlayersPosition();
        Player p = board.getCurrentPlayer();
        return (pos == 19 || pos == 39) && !p.getProperties().contains(Property.HOUSE) && p.getMoney() >= 15000;
    }

    public boolean canRepay() {
        return board.getCurrentPlayer().getDebt() > 0 && board.getCurrentPlayer().getMoney() >= REPAY_AMOUNT;
    }

    public void takeLoan() {
        GameAction purchase = new CostAction(board, OWN_RESOURCE);
        board.queueImmediateAction(purchase);
        board.doTurn();
        board.getCurrentPlayer().addProperty(Property.HOUSE);
        board.getCurrentPlayer().setDebt(LOAN);
        gui.update();
    }

    public void repay(int sum) {
        Player p = board.getCurrentPlayer();
        GameAction purchase = new CostAction(board, sum);
        board.queueImmediateAction(purchase);
        p.setDebt(p.getDebt()-sum);
        if(p.getDebt()==0){
            board.queueLateAction(new ShowMessageGameAction("Hitel sikeresn visszafizetve!"));
        }
        board.doTurn();
        gui.update();
        board.checkGame();
    }
}
