/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller;

import gazd.backend.domain.component.Property;
import gazd.backend.domain.component.Piece;
import gazd.backend.domain.Player;
import gazd.backend.domain.Board;
import gazd.controller.action.NextPlayerGameAction;
import gazd.controller.action.StepAction;
import gazd.controller.action.CostAction;
import gazd.controller.action.GameAction;
import gazd.controller.action.MoveAction;
import gazd.controller.action.ShowMessageGameAction;
import gazd.frontend.GuiManager;
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
        return board.getFields()[board.getCurrentPlayersPosition()].getProperties();
    }

    public void checkGame() {
        board.checkGame();
    }

    public boolean isAnyPurchasableItem() {
        return !board.getFields()[board.getCurrentPlayersPosition()].getProperties().isEmpty();
    }
    
    public void hackMove(int n){
        board.queueImmediateAction(new MoveAction(board, gui, n));
        board.doTurn();
        gui.update();
    }

    public boolean canLoan() {
        int pos = board.getCurrentPlayersPosition();
        return (pos == 19 || pos == 39) && !board.getCurrentPlayer().getProperties().contains(Property.HOUSE);
    }

    public boolean canRepay() {
        return board.getCurrentPlayer().getDebt() > 0 && board.getCurrentPlayer().getMoney() >= REPAY_AMOUNT;
    }

    public void takeLoan() {
        board.getCurrentPlayer().addProperty(Property.HOUSE);
        board.getCurrentPlayer().setDebt(Property.HOUSE.getPrice());
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
