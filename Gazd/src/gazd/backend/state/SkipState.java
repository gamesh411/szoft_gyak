/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend.state;

import gazd.backend.Player;

/**
 *
 * @author MetaPC
 */
public class SkipState implements State {

    private int skipTurns;

    public SkipState(int skipTurns) {
        this.skipTurns = skipTurns;
    }

    @Override
    public void turn() {
        skipTurns--;
        System.out.println("gazd.backend.SkipState.turn()");
    }

    @Override
    public boolean canStep(Player player,int diceNumber) {
        return skipTurns < 0;
    }
    
    
}
