/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.network.prototype;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author enf
 */
public class ObjectSocketConnection {
	private final Socket socket;
	private final ObjectInputStream inputStream;
	private final ObjectOutputStream outputStream;

	public ObjectSocketConnection(Socket socket, boolean shouldInitiate) throws IOException {
		this.socket = socket;
		if (shouldInitiate) {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
		} else {
			inputStream = new ObjectInputStream(socket.getInputStream());
			outputStream = new ObjectOutputStream(socket.getOutputStream());
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectInputStream getInputStream() {
		return inputStream;
	}

	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}
}
