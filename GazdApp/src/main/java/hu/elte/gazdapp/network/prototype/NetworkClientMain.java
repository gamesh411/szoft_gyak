/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.network.prototype;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author enf
 */
public class NetworkClientMain {
	public static void main(String[] argv) {
		try (NetworkClient client = new NetworkClient();) {
			client.run();
		} catch (IOException ex) {
			Logger.getLogger(NetworkClientMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
