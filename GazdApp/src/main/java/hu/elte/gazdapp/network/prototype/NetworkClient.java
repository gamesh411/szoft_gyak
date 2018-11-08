/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.network.prototype;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author enf
 */
public class NetworkClient implements Runnable, AutoCloseable {

	ObjectSocketConnection serverConnection;
	Socket server;

	public NetworkClient() throws IOException {
		logInfo("Connecting to localhost:9090...");
		server = new Socket("localhost", 9090);
		logInfo("Connected!");
		serverConnection = new ObjectSocketConnection(server, true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Random rnd = new Random();
				switch (rnd.nextInt(3)) {
				case 0:
					negotiateRoll();
					break;
				case 1:
					negotiateBuy();
					break;
				case 2:
					negotiatePass();
					break;
				}
				Thread.sleep(1000 + rnd.nextInt(1000));
			} catch (InterruptedException ex) {
				Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
				break;
			} catch (IOException ex) {
				Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
				break;
			}
		}
	}

	@Override
	public void close() throws Exception {
		serverConnection.getInputStream().close();
		serverConnection.getOutputStream().close();
		if (!serverConnection.getSocket().isClosed())
			serverConnection.getSocket().close();
	}

	private void negotiateRoll() throws IOException {
		logInfo("Saying 'roll' to server...");
		serverConnection.getOutputStream().writeUTF("roll");
		serverConnection.getOutputStream().flush();
		String response = serverConnection.getInputStream().readUTF();
		logInfo("Server said: '" + response + "'");
	}

	private void negotiateBuy() throws IOException {
		logInfo("Saying 'buy' to server...");
		serverConnection.getOutputStream().writeUTF("buy house");
		serverConnection.getOutputStream().flush();
		String response = serverConnection.getInputStream().readUTF();
		logInfo("Server said: '" + response + "'");
	}

	private void negotiatePass() throws IOException {
		logInfo("Saying 'pass' to server...");
		serverConnection.getOutputStream().writeUTF("pass");
		serverConnection.getOutputStream().flush();
		String response = serverConnection.getInputStream().readUTF();
		logInfo("Server said: '" + response + "'");
	}

	public void logInfo(String msg) {
		Logger.getLogger(NetworkClient.class.getName()).log(Level.INFO, msg);
	}

}
