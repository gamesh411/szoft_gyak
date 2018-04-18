/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend;

import gazd.controller.CostAction;

/**
 *
 * @author MetaPC
 */
public class StandardIField implements IField {
    
    Board board;
    int cost;

    public StandardIField(Board board, int cost) {
        this.board = board;
        this.cost = cost;
    }
    

    @Override
    public IGameAction onPlayerArrived(Player player) {
        return new CostAction(board, cost);
    }
    
}
