/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.domain;

import hu.elte.gazdapp.backend.domain.component.Piece;
import hu.elte.gazdapp.controller.action.GameAction;
import hu.elte.gazdapp.controller.action.MoveAction;
import hu.elte.gazdapp.controller.action.RelativeMoveAction;
import hu.elte.gazdapp.frontend.GuiManager;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author efulop
 */
public class BoardTest {

    public BoardTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addPlayer method, of class Board.
     */
    @Test
    public void testAddPlayer() throws RemoteException {
        PlayerInterface player = new Player("Player1", Piece.RED);
        BoardInterface board = new Board();
        board.addPlayer(player);
        assertTrue("Playerlist should include the player added", board.getPlayers().contains(player));
    }

    /**
     * Test of getPlayers method, of class Board.
     */
    @Test
    public void testGetPlayers() throws RemoteException {
        BoardInterface board = new Board();
        PlayerInterface player1 = new Player("Jill", Piece.RED);
        PlayerInterface player2 = new Player("Joe", Piece.BLUE);
        
        List<PlayerInterface> expResult = Arrays.asList(player1, player2);
        board.addPlayer(player1);
        board.addPlayer(player2);
        List<PlayerInterface> result = board.getPlayers();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of getCurrentPlayer, and setCurrentPlayer method, of class Board.
     */
    @Test
    public void testGetAndSetCurrentPlayer() throws RemoteException {
        BoardInterface board = new Board();

        PlayerInterface player1 = new Player("Wendy", Piece.GREEN);
        PlayerInterface player2 = new Player("Ray", Piece.YELLOW);
        board.addPlayer(player1);
        board.addPlayer(player2);

        board.setCurrentPlayer(player2);

        PlayerInterface currentPlayer = board.getCurrentPlayer();
        assertEquals("Current player should be Ray", "Ray", currentPlayer.getName());
        assertSame("Current player should not be copied", player2, board.getCurrentPlayer());
        assertTrue("Current player should be same as one of the pointed contained elements", board.getPlayers().contains(board.getCurrentPlayer()));
    }

    /**
     * Test of start method, of class Board.
     */
    @Test
    public void testStart() throws RemoteException {
        BoardInterface board = new Board();
        board.addPlayer(new Player("Peter", Piece.RED));
        board.addPlayer(new Player("Jane", Piece.BLUE));
        board.start();

        assertEquals("The first player is the initial", "Peter", board.getCurrentPlayer().getName());
    }

    /**
     * Test of queuing and executing of actions.
     */
    @Test
    public void testActionHandling() throws RemoteException {
        GuiManager gui = new GuiManager();
        BoardInterface board = new Board();
        GameAction moveAction = new MoveAction(board, gui, 12);
        GameAction relativeMoveAction = new RelativeMoveAction(board, gui, 3);
        Player currentPlayer = new Player("Sam", Piece.RED);
        board.addPlayer(currentPlayer);
        board.setCurrentPlayer(currentPlayer);

        board.queueLateAction(relativeMoveAction);
        board.queueImmediateAction(moveAction);

        board.doTurn();
        PlayerInterface player = board.getPlayers().stream().filter(p -> {
            boolean match = false;
            try{
               match = p.getName().equals("Sam");
            }catch(Exception e){   
            }
            return match;
        }).findFirst().get();

        assertEquals("Position should be the sum of the absolute and relative move, absolute move should happen before relative", 15, player.getPosition());
    }

    /**
     * Test of step method, of class Board.
     */
    @Test
    public void testStep() throws RemoteException {
        BoardInterface board = new Board();
        Player currentPlayer = new Player("Zoe", Piece.GREEN);
        board.addPlayer(currentPlayer);
        board.setCurrentPlayer(currentPlayer);
        board.step();

        assertTrue("Player moved by a random amount between 1 and 6", board.getCurrentPlayersPosition() > 0);
    }

    /**
     * Test of stepOn method, of class Board.
     */
    @Test
    public void testStepOn() throws RemoteException {
        int newPosition = 15;
        BoardInterface instance = new Board();
        Player currentPlayer = new Player("Jan", Piece.YELLOW);
        instance.addPlayer(currentPlayer);
        instance.setCurrentPlayer(currentPlayer);
        instance.stepOn(newPosition);

        assertTrue(instance.getCurrentPlayersPosition() == 15);
    }

    /**
     * Test of nextPlayer method, of class Board.
     */
    @Test
    public void testNextPlayer() throws RemoteException {
        BoardInterface instance = new Board();
        Player currentPlayer = new Player("Manfred", Piece.PURPLE);
        Player nextPlayer = new Player("Laszlo", Piece.RED);
        instance.addPlayer(currentPlayer);
        instance.addPlayer(nextPlayer);
        instance.setCurrentPlayer(currentPlayer);
        instance.nextPlayer();

        assertSame(instance.getCurrentPlayer(), nextPlayer);
    }
}
