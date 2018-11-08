/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.network.prototype;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author enf
 */
public class NetworkServer implements Runnable, AutoCloseable {
	private int currentTurn;
	private int expectedPlayers;
	private ServerSocket serverSocket;
	private List<ObjectSocketConnection> clientConnections;

	public NetworkServer(int expectedPlayers) throws IOException {
		this.expectedPlayers = expectedPlayers;
		currentTurn = 0;
		serverSocket = new ServerSocket(9090);
		clientConnections = new ArrayList<>();
	}

	public void acceptClient() throws IOException {
		logInfo("Accepting client...");
		Socket client = serverSocket.accept();
		logInfo("Client accepted!");
		clientConnections.add(new ObjectSocketConnection(client, false));
	}

	public void startGame() {
		logInfo("Starting server...");
		while (clientConnections.size() < expectedPlayers) {
			logInfo("Waiting for players...");

			try {
				acceptClient();
				logInfo("Client connected!");
			} catch (SocketTimeoutException e) {
				System.out.println("Still looking for players...");
			} catch (IOException ex) {
				Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		logInfo("Enough players connected!");
	}

	private void handleTurns() {
		while (true) {
			try {
				logInfo("Starting turn " + currentTurn + "...");
				for (int currentPlayer = 0; currentPlayer < clientConnections.size(); currentPlayer++) {
					logInfo("Handling player " + currentPlayer + "...");
					handlePlayer(currentPlayer);
					logInfo("Done handling player " + currentPlayer + "...");
				}
				currentTurn++;
				logInfo("Done handling turn " + currentTurn + "...");
			} catch (IOException ex) {
				Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
				break;
			}
		}
	}

	@Override
	public void run() {
		startGame();
		handleTurns();
	}

	@Override
	public void close() throws Exception {
		clientConnections.forEach(conn -> {
			try {
				conn.getInputStream().close();
				conn.getOutputStream().close();
				if (!conn.getSocket().isClosed())
					conn.getSocket().close();
			} catch (IOException ex) {
				Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		if (!serverSocket.isClosed())
			serverSocket.close();
	}

	public void logInfo(String msg) {
		Logger.getLogger(NetworkServer.class.getName()).log(Level.INFO, msg);
	}

	private void handlePlayer(int currentPlayer) throws IOException {
		ObjectSocketConnection connection = clientConnections.get(currentPlayer);
		String intent = connection.getInputStream().readUTF();

		logInfo("Player " + currentPlayer + " wants to '" + intent + "'.");
		
		Random rnd = new Random();

		if (intent.equalsIgnoreCase("roll")) {
			logInfo("Player " + currentPlayer + " wants to roll, and move.");
			int roll = rnd.nextInt(6) + 1;
			connection.getOutputStream().writeUTF("You rolled " + roll + ", move accordingly!");
			connection.getOutputStream().flush();
		} else if (intent.startsWith("buy")) {
			String purchaseTarget = intent.substring(4);
			logInfo("Player " + currentPlayer + " wants to buy '" + purchaseTarget + "'.");
			if (rnd.nextBoolean()) {
				connection.getOutputStream().writeUTF("Success! You bought '" + purchaseTarget + "'!");
			} else {
				connection.getOutputStream().writeUTF("Failure! You can not buy '" + purchaseTarget + "'!");
			}
			connection.getOutputStream().flush();
		} else if (intent.equalsIgnoreCase("pass")) {
			logInfo("Player " + currentPlayer + " does not want to do anyting...");
			connection.getOutputStream().writeUTF("You passed!");
			connection.getOutputStream().flush();
		} else {
			logInfo("Player " + currentPlayer + " wants something, that is not possible. Passing.");
			connection.getOutputStream().writeUTF("You passed!");
			connection.getOutputStream().flush();
		}

	}
}
