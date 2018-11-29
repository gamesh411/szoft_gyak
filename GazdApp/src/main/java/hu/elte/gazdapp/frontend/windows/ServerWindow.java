package hu.elte.gazdapp.frontend.windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.frontend.ServerGuiManager;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Action;

public class ServerWindow {
	
	ServerGuiManager mgr;

	private JFrame frame;
	private JTextField txtExpectedPlayers;
	private final Action action = new MainAction();
	private JTextPane txtpnServerLog;
	private JButton btnMain;
	private JLabel lblState;

	/**
	 * Create the application.
	 */
	public ServerWindow(ServerGuiManager mgr) {
		this.mgr = mgr;
		initialize();
		this.frame.pack();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblExpectedPlayers = new JLabel("Expected Players");
		
		txtExpectedPlayers = new JTextField();
		txtExpectedPlayers.setText("2");
		txtExpectedPlayers.setColumns(10);
		
		JLabel lblStatus = new JLabel("Status");
		
		lblState = new JLabel("Not running");
		
		btnMain = new JButton("Start");
		btnMain.setAction(action);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblExpectedPlayers)
								.addComponent(lblStatus))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblState)
								.addComponent(txtExpectedPlayers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(btnMain, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblExpectedPlayers)
								.addComponent(txtExpectedPlayers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblStatus)
								.addComponent(lblState)))
						.addComponent(btnMain, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		txtpnServerLog = new JTextPane();
		scrollPane.setViewportView(txtpnServerLog);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public void showMessage(String message, String title) {
		JOptionPane.showMessageDialog(this.frame, message, title, JOptionPane.PLAIN_MESSAGE);

	}
	
	public void logMessage(String message) {
		getServerLog().setText(
			txtpnServerLog.getText() +
			message +
		    "\n"
		);
	}
	
	private enum ServerState {
		NOT_RUNNING,
		WAITING_FOR_PLAYERS,
		RUNNING
	}
	
	private class MainAction extends AbstractAction {
		private ServerState state = ServerState.NOT_RUNNING;
		private int maxPlayers;
		private int currentPlayers;
		
		private boolean gameInProgress;
	    private Thread updateThread;
	    
	    private Registry registry;
	    
	    private MulticastSocket communicationSocket;
	    private InetAddress group;
	    private Board remoteBoard;
	    
	    private Timer waitingForPlayerTimer;
		
		public MainAction() {
			putValue(NAME, "Start");
			putValue(SHORT_DESCRIPTION, "Controls the server process");
		}
		
		public void actionPerformed(ActionEvent e) {
			switch (state) {
			case NOT_RUNNING:
				tryStartGame();
				break;
			case WAITING_FOR_PLAYERS:
				stopGame();
				break;
			case RUNNING:
				stopGame();
				break;
			}
		}
		
		private void tryStartGame() {
			Integer expectedPlayers;
			
			try {
				expectedPlayers = Integer.parseInt(txtExpectedPlayers.getText());
			} catch (NumberFormatException e) {
				showMessage("Játékosok száma egész szám kell, hogy legyen!", "Játékosok száma");
				return;
			}
			
			if (expectedPlayers < 1 || expectedPlayers > 4) {
				showMessage("Játékosok száma 1 és 4 közötti szám kell, hogy legyen!", "Játékosok száma");
				return;
			}
			
			maxPlayers = expectedPlayers;
			currentPlayers = 0;
			
			logMessage("Starting game with " + expectedPlayers + " players!");
			getMainButton().setText("Stop");
			
			try {
                            System.setProperty("java.rmi.server.hostname", "25.16.83.237");  // this is the hamachi ip server address
                            registry = LocateRegistry.createRegistry(1099);
				remoteBoard = new Board();
				remoteBoard.start();
	            registry.rebind("rmiServer", remoteBoard);
			} catch (RemoteException e) {
				logMessage(e.getMessage());
			}
			
			waitingForPlayerTimer = new Timer();
			waitingForPlayerTimer.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run() {
					Integer numPlayersSoFar;
					try {
						numPlayersSoFar = remoteBoard.getPlayers().size();
					} catch (RemoteException e) {
						e.printStackTrace();
						return;
					}
					
					currentPlayers = numPlayersSoFar;
					
					if (currentPlayers == maxPlayers) {
						state = ServerState.RUNNING;
						this.cancel();
					}
					
					updateStatusLabel();
					
				}
				
			}, 200, 200);
			
			state = ServerState.WAITING_FOR_PLAYERS;
			
			updateStatusLabel();
            
		}
		
		private void stopGame() {
			logMessage("Stopping game...");
			getMainButton().setText("Start");
			try {
				registry.unbind("rmiServer");
			} catch (RemoteException | NotBoundException e) {
				logMessage(e.getMessage());
			}
			waitingForPlayerTimer.cancel();
			currentPlayers = 0;
			state = ServerState.NOT_RUNNING;
			
			updateStatusLabel();
			
			logMessage("Game stopped!");
		}
		
		private void updateStatusLabel() {
			switch (state) {
			case NOT_RUNNING:
				getStateLabel().setText("Not running");
				break;
			case WAITING_FOR_PLAYERS:
				getStateLabel().setText("Waiting for players (" + currentPlayers + "/" + maxPlayers + ")");
				break;
			case RUNNING:
				getStateLabel().setText("Running");
				break;
			}
			
		}
		
		
	}
	public JButton getMainButton() {
		return btnMain;
	}
	public JLabel getStateLabel() {
		return lblState;
	}
	public JTextField getExpectedPlayersTextBox() {
		return txtExpectedPlayers;
	}
	public JTextPane getServerLog() {
		return txtpnServerLog;
	}
}
