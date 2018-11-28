/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.domain.component;

import hu.elte.gazdapp.controller.action.GameAction;
import java.io.Serializable;

/**
 *
 * @author MetaPC
 */
public class Card implements Serializable {
    
    private GameAction action;
    private String message;

    public Card(GameAction action, String message) {
        this.action = action;
        this.message = message;
    }

    public GameAction getAction() {
        return action;
    }

    public void setAction(GameAction action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
