/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller;

import hu.elte.gazdapp.backend.domain.Board;

import hu.elte.gazdapp.frontend.GuiManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.BDDMockito;
import org.mockito.Mock;
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
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        underTest = new MainController(gui);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of start method, of class MainController.
     */
    @Test
    public void testStart() throws NoSuchFieldException {
        //GIVEN
        FieldSetter.setField(underTest, underTest.getClass().getDeclaredField("board"), board);
        //WHEN
        underTest.newGame();
        //THEN
        //BDDMockito.verify(board).start();
        //BDDMockito.verify(gui).update();
    }

//    /**
//     * Test of onRoll method, of class MainController.
//     */
//    @Test
//    public void testOnRoll() {
//        System.out.println("onRoll");
//        MainController instance = null;
//        instance.onRoll();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPlayers method, of class MainController.
//     */
//    @Test
//    public void testGetPlayers() {
//        System.out.println("getPlayers");
//        MainController instance = null;
//        List<Player> expResult = null;
//        List<Player> result = instance.getPlayers();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCurrentPlayer method, of class MainController.
//     */
//    @Test
//    public void testGetCurrentPlayer() {
//        System.out.println("getCurrentPlayer");
//        MainController instance = null;
//        Player expResult = null;
//        Player result = instance.getCurrentPlayer();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of endRound method, of class MainController.
//     */
//    @Test
//    public void testEndRound() {
//        System.out.println("endRound");
//        MainController instance = null;
//        instance.endRound();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addPlayer method, of class MainController.
//     */
//    @Test
//    public void testAddPlayer() {
//        System.out.println("addPlayer");
//        String playerName = "";
//        Piece color = null;
//        MainController instance = null;
//        instance.addPlayer(playerName, color);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of buySelectedItem method, of class MainController.
//     */
//    @Test
//    public void testBuySelectedItem() {
//        System.out.println("buySelectedItem");
//        Property selectedItem = null;
//        MainController instance = null;
//        instance.buySelectedItem(selectedItem);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFieldItems method, of class MainController.
//     */
//    @Test
//    public void testGetFieldItems() {
//        System.out.println("getFieldItems");
//        MainController instance = null;
//        Set<Property> expResult = null;
//        Set<Property> result = instance.getFieldItems();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of checkGame method, of class MainController.
//     */
//    @Test
//    public void testCheckGame() {
//        System.out.println("checkGame");
//        MainController instance = null;
//        instance.checkGame();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
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
//    /**
//     * Test of hackMove method, of class MainController.
//     */
//    @Test
//    public void testHackMove() {
//        System.out.println("hackMove");
//        int n = 0;
//        MainController instance = null;
//        instance.hackMove(n);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of canLoan method, of class MainController.
//     */
//    @Test
//    public void testCanLoan() {
//        System.out.println("canLoan");
//        MainController instance = null;
//        boolean expResult = false;
//        boolean result = instance.canLoan();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of canRepay method, of class MainController.
//     */
//    @Test
//    public void testCanRepay() {
//        System.out.println("canRepay");
//        MainController instance = null;
//        boolean expResult = false;
//        boolean result = instance.canRepay();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of takeLoan method, of class MainController.
//     */
//    @Test
//    public void testTakeLoan() {
//        System.out.println("takeLoan");
//        MainController instance = null;
//        instance.takeLoan();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of repay method, of class MainController.
//     */
//    @Test
//    public void testRepay() {
//        System.out.println("repay");
//        int sum = 0;
//        MainController instance = null;
//        instance.repay(sum);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
