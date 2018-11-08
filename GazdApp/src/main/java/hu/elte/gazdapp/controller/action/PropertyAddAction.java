
package hu.elte.gazdapp.controller.action;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.backend.domain.Player;
import hu.elte.gazdapp.backend.domain.component.Property;
import java.util.List;

public class PropertyAddAction implements GameAction {

    private Board board;
    private List<Property> properties;

    public PropertyAddAction(Board board,List<Property> properties) {
        this.board = board;
        this.properties = properties;
    }

    @Override
    public void execute(){
        Player p = board.getCurrentPlayer();
        properties.forEach(p::addProperty);
    }

}
