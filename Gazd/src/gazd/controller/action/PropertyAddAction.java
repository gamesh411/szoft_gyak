
package gazd.controller.action;

import gazd.backend.IGameAction;
import gazd.backend.Board;
import gazd.backend.Player;
import gazd.backend.Property;
import java.util.List;

public class PropertyAddAction implements IGameAction {

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
