
package gazd.controller.action;

import gazd.backend.domain.Board;
import gazd.backend.domain.Player;
import gazd.backend.domain.component.Property;
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
