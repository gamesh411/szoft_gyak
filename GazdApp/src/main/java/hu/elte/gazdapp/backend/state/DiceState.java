/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.state;

import hu.elte.gazdapp.backend.domain.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author MetaPC
 */
public class DiceState implements State {

    private final List<Integer> diceNumbers;
    private boolean canStep = false;
    
    public DiceState(Integer... diceNumber) {
        this.diceNumbers = new ArrayList<>(Arrays.asList(diceNumber));
    }

    @Override
    public void turn() {
    }

    @Override
    public boolean canStep(Player player,int diceNumber) {
        if(canStep) return true;
        canStep = diceNumbers.contains(diceNumber);
        return canStep;
    }
    
    
}
