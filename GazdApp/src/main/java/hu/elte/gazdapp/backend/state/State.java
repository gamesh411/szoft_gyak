/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.state;

import hu.elte.gazdapp.backend.domain.Player;
import java.io.Serializable;

/**
 *
 * @author MetaPC
 */
public interface State extends Serializable {
    
    void turn();
    boolean canStep(Player player,int diceNumber);
    
}
