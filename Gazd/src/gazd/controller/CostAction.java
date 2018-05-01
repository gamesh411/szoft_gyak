/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller;

import gazd.backend.IGameAction;
import gazd.backend.Board;
import gazd.frontend.GuiManager;

/**
 *
 * @author endrefulop
 */
public class CostAction implements IGameAction {

    private Board board;
    private int cost;

    public CostAction(Board board, int cost) {
        this.board = board;
        this.cost = cost;
    }

    @Override
    public void execute() {
        board.getCurrentPlayer().spendMoney(cost);
    }

}
