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
public class NetworkServerMain {
	public static void main(String[] args) {
		try (NetworkServer server = new NetworkServer(2);) {
			server.run();
		} catch (IOException ex) {
			Logger.getLogger(NetworkServerMain.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
