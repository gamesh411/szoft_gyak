/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller.action;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author endrefulop
 */
public interface GameAction extends Serializable {

    void execute() throws RemoteException;
}
