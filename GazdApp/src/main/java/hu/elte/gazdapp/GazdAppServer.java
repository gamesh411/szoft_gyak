package hu.elte.gazdapp;

import javax.swing.SwingUtilities;

public class GazdAppServer {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        //createAndShowGUI();
		    }
		});
	}

}
