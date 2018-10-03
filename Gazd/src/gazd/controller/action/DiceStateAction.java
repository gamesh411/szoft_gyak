/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.controller.action;

import gazd.backend.Board;
import gazd.backend.state.DiceState;
import gazd.backend.IGameAction;

/**
 *
 * @author MetaPC
 */
public class DiceStateAction implements IGameAction {

    Integer[] diceNumbers;
    Board board;

    public DiceStateAction(Board board, Integer... diceNumbers) {
        this.diceNumbers = diceNumbers;
        this.board = board;
    }

    @Override
    public void execute() {
        board.getCurrentPlayer().setState(new DiceState(diceNumbers));

    }

}
