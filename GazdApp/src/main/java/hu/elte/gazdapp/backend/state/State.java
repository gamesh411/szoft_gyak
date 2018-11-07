/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.state;

import hu.elte.gazdapp.backend.domain.Player;

/**
 *
 * @author MetaPC
 */
public interface State {
    
    void turn();
    boolean canStep(Player player,int diceNumber);
    
}
