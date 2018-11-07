/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;

/**
 *
 * @author endrefulop
 */
public class CostAction implements GameAction {

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
