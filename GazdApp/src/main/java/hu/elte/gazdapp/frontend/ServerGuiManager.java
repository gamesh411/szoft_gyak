package hu.elte.gazdapp.frontend;

import hu.elte.gazdapp.frontend.windows.StartNewGameWindow;
import hu.elte.gazdapp.backend.domain.component.Piece;
import hu.elte.gazdapp.backend.domain.Player;
import hu.elte.gazdapp.backend.domain.component.Property;
import hu.elte.gazdapp.controller.MainController;
import hu.elte.gazdapp.frontend.util.ScreenConstants;
import hu.elte.gazdapp.frontend.windows.StartNewGameWindow;
import hu.elte.gazdapp.frontend.windows.PurchaseWindow;
import hu.elte.gazdapp.frontend.windows.ServerWindow;
import hu.elte.gazdapp.frontend.windows.GameWindow;
import hu.elte.gazdapp.frontend.windows.PropertyWindow;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.UIManager;

public class ServerGuiManager {
	    private ServerWindow screen;
	    private MainController control;

	    private final int TEXT_SIZE = ScreenConstants.FONT_SIZE.getValue();

	    public void start() {
	        setUIFont(new javax.swing.plaf.FontUIResource("Dialog", Font.ITALIC, TEXT_SIZE));
	        screen = new ServerWindow(this);
	    }

	    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
	        java.util.Enumeration keys = UIManager.getDefaults().keys();
	        while (keys.hasMoreElements()) {
	            Object key = keys.nextElement();
	            Object value = UIManager.get(key);
	            if (value instanceof javax.swing.plaf.FontUIResource) {
	                UIManager.put(key, f);
	            }
	        }
	    }

}
