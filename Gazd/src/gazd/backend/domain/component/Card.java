/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend.domain.component;

import gazd.controller.action.GameAction;

/**
 *
 * @author MetaPC
 */
public class Card {
    
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
