
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.backend.domain.BoardInterface;
import hu.elte.gazdapp.backend.domain.Player;
import hu.elte.gazdapp.backend.domain.component.Property;
import java.rmi.RemoteException;

public class PropertyAddAction implements GameAction {

    private BoardInterface board;
    private Property property;

    public PropertyAddAction(BoardInterface board,Property property) {
        this.board = board;
        this.property = property;
    }

    @Override
    public void execute() throws RemoteException{
        Player p = board.getCurrentPlayer();
        if((property == Property.HOUSEHOLD || property == Property.KITCHEN || property == Property.LIVING) && !p.getProperties().contains(Property.HOUSE) ){
            board.queueLateAction(new ShowMessageGameAction("Nincs lakásod, így bútor árát kaptad meg", board));
            p.spendMoney(-property.getPrice());
        }else{
           p.addProperty(property); 
        }
        
    }

}
