package hu.elte.gazdapp;

import javax.swing.SwingUtilities;

import hu.elte.gazdapp.frontend.ServerGuiManager;

public class GazdAppServer {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        (new ServerGuiManager()).start();
		    }
		});
	}

}
