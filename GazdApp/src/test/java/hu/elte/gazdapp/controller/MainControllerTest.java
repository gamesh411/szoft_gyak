/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller;

import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.backend.domain.Player;
import hu.elte.gazdapp.backend.domain.component.Piece;
import hu.elte.gazdapp.backend.domain.component.Property;
import hu.elte.gazdapp.controller.action.CostAction;
import hu.elte.gazdapp.controller.action.GameAction;
import hu.elte.gazdapp.controller.action.MoveAction;
import hu.elte.gazdapp.controller.action.NextPlayerGameAction;
import hu.elte.gazdapp.controller.action.ShowMessageGameAction;
import hu.elte.gazdapp.controller.action.StepAction;

import hu.elte.gazdapp.frontend.GuiManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.FieldSetter;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public class MainControllerTest {
    
    private MainController underTest;
    
    @Mock 
    private GuiManager gui;
    
    @Mock
    private Board board;
    
    public MainControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws NoSuchFieldException {
        MockitoAnnotations.initMocks(this);
        underTest = new MainController(gui);
        FieldSetter.setField(underTest, underTest.getClass().getDeclaredField("board"), board);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of start method, of class MainController.
     */
    @Test
    public void testStart() {
        //GIVEN
        //WHEN
        underTest.start();
        //THEN
        BDDMockito.verify(board).start();
        BDDMockito.verify(gui).update();
    }

    /**
     * Test of onRoll method, of class MainController.
     */
    @Test
    public void testOnRoll() {
        //GIVEN
        //WHEN
        underTest.onRoll();
        //THEN
        InOrder inorder = BDDMockito.inOrder(board, gui);
        ArgumentCaptor<StepAction> content = ArgumentCaptor.forClass(StepAction.class);
        inorder.verify(board).queueLateAction(content.capture());
        inorder.verify(board).doTurn();
        inorder.verify(gui).update();
    }

    /**
     * Test of getPlayers method, of class MainController.
     */
    @Test
    public void testGetPlayers() {
        //GIVEN
        List<Player> players = new ArrayList<>();
        BDDMockito.given(board.getPlayers()).willReturn(players);
        //WHEN
        List<Player> result = underTest.getPlayers();
        //THEN
        BDDMockito.verify(board).getPlayers();
        Assert.assertNotNull(result);
    }

    /**
     * Test of getCurrentPlayer method, of class MainController.
     */
    @Test
    public void testGetCurrentPlayer() {
        //GIVEN
        Player player = new Player("currPlayer", Piece.RED);
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        //WHEN
        Player result = underTest.getCurrentPlayer();
        //THEN
        BDDMockito.verify(board).getCurrentPlayer();
        Assert.assertNotNull(result);

    }

    /**
     * Test of endRound method, of class MainController.
     */
    @Test
    public void testEndRound() {
        //GIVEN
        //WHEN
        underTest.endRound();
        //THEN
        InOrder inorder = BDDMockito.inOrder(board, gui);
        ArgumentCaptor<NextPlayerGameAction> content = ArgumentCaptor.forClass(NextPlayerGameAction.class);
        inorder.verify(board).queueLateAction(content.capture());
        inorder.verify(board).doTurn();
        inorder.verify(gui).update();

    }

    /**
     * Test of addPlayer method, of class MainController.
     */
    @Test
    public void testAddPlayer() {
        //GIVEN
        //WHEN
        underTest.addPlayer("testName", Piece.BLUE);
        //THEN
        BDDMockito.verify(board).addPlayer(BDDMockito.any(Player.class));
    }

    /**
     * Test of buySelectedItem method, of class MainController.
     */
    @Test
    public void testBuySelectedItem() {
        //GIVEN
        Player player = new Player("currentPlayer", Piece.RED);
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        //WHEN
        underTest.buySelectedItem(Property.KITCHEN);
        //THEN
        InOrder inorder = BDDMockito.inOrder(board, gui);
        ArgumentCaptor<GameAction> content = ArgumentCaptor.forClass(CostAction.class);
        inorder.verify(board).queueImmediateAction(content.capture());
        inorder.verify(board).doTurn();
        
        inorder.verify(board).getCurrentPlayer();
        inorder.verify(gui).update();
        
        Assert.assertTrue(player.getProperties().contains(Property.KITCHEN));
    }

    /**
     * Test of getFieldItems method, of class MainController.
     */
    @Test
    public void testGetFieldItems() {
        //GIVEN
        int pos = 19;
        Set<Property> properties = new HashSet<>(Arrays.asList(Property.HOUSE));
        BDDMockito.given(board.getPropertiesOfField(BDDMockito.anyInt())).willReturn(properties);
        BDDMockito.given(board.getCurrentPlayersPosition()).willReturn(pos);
        //WHEN
        Set<Property> result = underTest.getFieldItems();
        //THEN
        BDDMockito.verify(board).getPropertiesOfField(BDDMockito.anyInt());
        BDDMockito.verify(board).getCurrentPlayersPosition();
        Assert.assertEquals(properties, result);
    }

    /**
     * Test of checkGame method, of class MainController.
     */
    @Test
    public void testCheckGame() {
        //GIVEN
        //WHEN
        underTest.checkGame();
        //THEN
        BDDMockito.verify(board).checkGame();
    }

//    /**
//     * Test of isAnyPurchasableItem method, of class MainController.
//     */
//    @Test
//    public void testIsAnyPurchasableItem() {
//        System.out.println("isAnyPurchasableItem");
//        MainController instance = null;
//        boolean expResult = false;
//        boolean result = instance.isAnyPurchasableItem();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
    /**
     * Test of hackMove method, of class MainController.
     */
    @Test
    public void testHackMove() {
        //GIVEN
        //WHEN
        underTest.hackMove(0);
        //THEN
        InOrder inorder = BDDMockito.inOrder(board, gui);
        ArgumentCaptor<MoveAction> content = ArgumentCaptor.forClass(MoveAction.class);
        inorder.verify(board).queueImmediateAction(content.capture());
        inorder.verify(board).doTurn();
        inorder.verify(gui).update();
    }

    /**
     * Test of canLoan method, of class MainController.
     */
    @Test
    public void testCanLoanWithCorrectInformations() {
        //GIVEN
        Player player = new Player("currentPlayer", Piece.RED);
        int pos = 19;
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        BDDMockito.given(board.getCurrentPlayersPosition()).willReturn(pos);
        //WHEN
        boolean result = underTest.canLoan();
        //THEN
        BDDMockito.verify(board).getCurrentPlayersPosition();
        BDDMockito.verify(board).getCurrentPlayer();
        Assert.assertTrue(result);
    }
    
    /**
     * Test of canLoan method, of class MainController.
     */
    @Test
    public void testCanLoanWithIncorrectPosition() {
        //GIVEN
        Player player = new Player("currentPlayer", Piece.RED);
        int pos = 20;
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        BDDMockito.given(board.getCurrentPlayersPosition()).willReturn(pos);
        //WHEN
        boolean result = underTest.canLoan();
        //THEN
        BDDMockito.verify(board).getCurrentPlayersPosition();
        BDDMockito.verify(board).getCurrentPlayer();
        Assert.assertTrue(!result);
    }
    
    /**
     * Test of canLoan method, of class MainController.
     */
    @Test
    public void testCanLoanWithPlayerWithHouse() {
        //GIVEN
        Player player = new Player("currentPlayer", Piece.RED);
        player.addProperty(Property.HOUSE);
        int pos = 19;
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        BDDMockito.given(board.getCurrentPlayersPosition()).willReturn(pos);
        //WHEN
        boolean result = underTest.canLoan();
        //THEN
        BDDMockito.verify(board).getCurrentPlayersPosition();
        BDDMockito.verify(board).getCurrentPlayer();
        Assert.assertTrue(!result);
    }
    
    /**
     * Test of canLoan method, of class MainController.
     */
    @Test
    public void testCanLoanWithPlayerWithoutEnoughMoney() {
        //GIVEN
        Player player = new Player("currentPlayer", Piece.RED);
        player.setMoney(10000);
        int pos = 19;
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        BDDMockito.given(board.getCurrentPlayersPosition()).willReturn(pos);
        //WHEN
        boolean result = underTest.canLoan();
        //THEN
        BDDMockito.verify(board).getCurrentPlayersPosition();
        BDDMockito.verify(board).getCurrentPlayer();
        Assert.assertTrue(!result);
    }

    /**
     * Test of canRepay method, of class MainController.
     */
    @Test
    public void testCanRepayWithCorrectInformations() {
        //GIVEN
        Player player = new Player("currentPlayer", Piece.RED);
        player.setDebt(10);
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        //WHEN
        boolean result = underTest.canRepay();
        //THEN
        BDDMockito.verify(board, Mockito.atLeastOnce()).getCurrentPlayer();
        Assert.assertTrue(result);
    }
    
    /**
     * Test of canRepay method, of class MainController.
     */
    @Test
    public void testCanRepayWithNoDebt() {
        //GIVEN
        Player player = new Player("currentPlayer", Piece.RED);
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        //WHEN
        boolean result = underTest.canRepay();
        //THEN
        BDDMockito.verify(board, Mockito.atLeastOnce()).getCurrentPlayer();
        Assert.assertTrue(!result);
    }
    
    /**
     * Test of canRepay method, of class MainController.
     */
    @Test
    public void testCanRepayWithLessMoneyThenRepayAmount() {
        //GIVEN
        Player player = new Player("currentPlayer", Piece.RED);
        player.setDebt(10);
        player.setMoney(9);
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        //WHEN
        boolean result = underTest.canRepay();
        //THEN
        BDDMockito.verify(board, Mockito.atLeastOnce()).getCurrentPlayer();
        Assert.assertTrue(!result);
    }

    /**
     * Test of takeLoan method, of class MainController.
     */
    @Test
    public void testTakeLoan() {
        //GIVEN
        Player player = new Player("currentPlayer", Piece.RED);
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        //WHEN
        underTest.takeLoan();
        //THEN
        InOrder inorder = BDDMockito.inOrder(board, gui);
        ArgumentCaptor<GameAction> content = ArgumentCaptor.forClass(CostAction.class);
        inorder.verify(board).queueImmediateAction(content.capture());
        inorder.verify(board).doTurn(); 
        inorder.verify(board, BDDMockito.times(2)).getCurrentPlayer();
        inorder.verify(gui).update();
        
        Assert.assertTrue(player.getProperties().contains(Property.HOUSE));
        Assert.assertEquals(20000, player.getDebt());
    }

    /**
     * Test of repay method, of class MainController.
     */
    @Test
    public void testRepayAllDebt() {
        //GIVEN
        Player player = new Player("currentPlayer", Piece.RED);
        player.setDebt(10);
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        //WHEN
        underTest.repay(10);
        //THEN
        InOrder inorder = BDDMockito.inOrder(board, gui);
        ArgumentCaptor<GameAction> content = ArgumentCaptor.forClass(CostAction.class);
        ArgumentCaptor<ShowMessageGameAction> messageCaptor = ArgumentCaptor.forClass(ShowMessageGameAction.class);
        
        inorder.verify(board).queueImmediateAction(content.capture());
        inorder.verify(board).queueLateAction(messageCaptor.capture());
        inorder.verify(board).doTurn();
        inorder.verify(gui).update();
        inorder.verify(board).checkGame();
        
        Assert.assertEquals(0, player.getDebt());
    }
    
    /**
     * Test of repay method, of class MainController.
     */
    @Test
    public void testRepayDebt() {
        //GIVEN
        Player player = new Player("currentPlayer", Piece.RED);
        player.setDebt(11);
        BDDMockito.given(board.getCurrentPlayer()).willReturn(player);
        //WHEN
        underTest.repay(10);
        //THEN
        InOrder inorder = BDDMockito.inOrder(board, gui);
        ArgumentCaptor<GameAction> content = ArgumentCaptor.forClass(CostAction.class);
        
        inorder.verify(board).queueImmediateAction(content.capture());
        inorder.verify(board).doTurn();
        inorder.verify(gui).update();
        inorder.verify(board).checkGame();
        
        Assert.assertEquals(1, player.getDebt());
    }
    
}
