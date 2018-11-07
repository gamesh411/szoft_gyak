/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.backend.state.SkipState;

/**
 *
 * @author MetaPC
 */
public class SkipStateAction implements GameAction {

    int skipTurns;
    Board board;
    int cost;

    public SkipStateAction(Board board,int skipTurns, int cost) {
        this.skipTurns = skipTurns;
        this.board = board;
        this.cost = cost;
    }
    
    
    @Override
    public void execute() {
        board.getCurrentPlayer().setState(new SkipState(skipTurns));
        board.queueImmediateAction(new CostAction(board, cost));
    }
    
}
