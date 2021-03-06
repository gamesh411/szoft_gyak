/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.domain;

import hu.elte.gazdapp.backend.domain.component.Card;
import hu.elte.gazdapp.backend.domain.component.Piece;
import hu.elte.gazdapp.backend.domain.component.Property;
import hu.elte.gazdapp.backend.state.SkipState;
import hu.elte.gazdapp.controller.action.CostAction;
import hu.elte.gazdapp.controller.action.DiceStateAction;
import hu.elte.gazdapp.controller.action.DrawCardAction;
import hu.elte.gazdapp.controller.action.InterestAction;
import hu.elte.gazdapp.controller.action.MoveAction;
import hu.elte.gazdapp.controller.action.PropertyAddAction;
import hu.elte.gazdapp.controller.action.RelativeMoveAction;
import hu.elte.gazdapp.controller.action.ShowMessageGameAction;
import hu.elte.gazdapp.controller.action.SkipStateAction;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import hu.elte.gazdapp.controller.action.GameAction;
import hu.elte.gazdapp.controller.action.InsuranceCheckAction;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

/**
 *
 * @author mmeta
 */
public class Board extends UnicastRemoteObject implements BoardInterface, Serializable{

    private List<PlayerInterface> players;
    private PlayerInterface currentPlayer;
    private final Field[] fields;

    private final List<Card> cards;
    private final int BOARDSIZE = 42;

    private final Deque<GameAction> actionQueue;

    private final FieldFactory fieldFactory = new FieldFactory();
    private final CardFactory cardFactory = new CardFactory();
    private String message;

    public Board() throws RemoteException {
        players = new ArrayList<>();
        fields = fieldFactory.createFields();
        cards = cardFactory.createCards();
        actionQueue = new ArrayDeque<>();
        message = "";
    }
    
    @Override
    public void setMessage(String message) throws RemoteException {
        this.message = message;
    }
    
    public String getMessage() throws RemoteException {
        return message;
    }
    
    @Override
    public void addPlayer(String name, Piece piece) throws RemoteException {
        players.add(new Player(name, piece));
    }

    @Override
    public List<PlayerInterface> getPlayers() throws RemoteException {
        return players;
    }
    
    public void addPlayer(PlayerInterface player) throws RemoteException{
        players.add(player);
    }
    
    @Override
    public PlayerInterface getCurrentPlayer() throws RemoteException {
        return currentPlayer;
    }

    @Override
    public void setCurrentPlayer(PlayerInterface currentPlayer) throws RemoteException {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void start() throws RemoteException {
        currentPlayer = players.isEmpty() ? null : players.get(0);
    }

    @Override
    public boolean queueLateAction(GameAction action) throws RemoteException {
        return actionQueue.offerLast(action);
    }

    @Override
    public boolean queueImmediateAction(GameAction action) throws RemoteException {
        return actionQueue.offerFirst(action);
    }

    @Override
    public void doTurn() throws RemoteException {
        while (!actionQueue.isEmpty()){
            actionQueue.poll().execute();
        }
    }

    @Override
    public void step() throws RemoteException {
        Random rand = new Random();
        int dice = rand.nextInt(6) + 1;
        int newPosition = (currentPlayer.getPosition() + dice) % BOARDSIZE;
        currentPlayer.getState().turn();
        if (currentPlayer.canStepWithRolledValue(dice)) {
            stepOn(newPosition);
        }
    }

    @Override
    public void stepOn(int newPosition) throws RemoteException {
        final int START = 0;
        if (currentPlayer.getPosition() > newPosition && newPosition != START) {
            queueImmediateAction(new CostAction(this, -2000));
        }
        currentPlayer.setPosition(newPosition);
        queueImmediateAction(fields[newPosition].getAction());
    }

    @Override
    public void nextPlayer() throws RemoteException {
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());

        if (!currentPlayer.canStep() && currentPlayer.getState() instanceof SkipState) {
            currentPlayer.decSkip();
            nextPlayer();
        }
    }

    @Override
    public void drawCard() throws RemoteException {
        queueImmediateAction(cards.get(0).getAction());
        queueLateAction(new ShowMessageGameAction(currentPlayer.getPosition() + " mező: " + cards.get(0).getMessage(), this));
        Collections.rotate(cards, 1);
    }

    @Override
    public Field[] getFields() throws RemoteException {
        return fields;
    }

    @Override
    public void checkGame() throws RemoteException {
        Set<Property> properties = currentPlayer.getProperties();
        if (properties.size() == (properties.contains(Property.INSURANCE) ? Property.values().length :Property.values().length-1) &&
                currentPlayer.getDebt() == 0) {
            queueImmediateAction(new ShowMessageGameAction("Győztes: " + currentPlayer.getName(), this));
            queueImmediateAction(() -> System.exit(0));
        }
    }

    @Override
    public int getCurrentPlayersPosition() throws RemoteException {
        return currentPlayer.getPosition();
    }

    private final class CardFactory implements Serializable {

        private List<Card> cards = new LinkedList<>();

        private CardFactory() {
        }

        private List<Card> createCards() {

            cards.add(new Card(new MoveAction(Board.this, null, 33), "KERAVILL-ban vásárlhatsz. Lépj a 33-as mezőre"));
            cards.add(new Card(new MoveAction(Board.this, null, 0), "Lépj a START mezőre."));
            cards.add(new Card(new CostAction(Board.this, -400), "TOTO-n 400 eurót nyertél."));
            cards.add(new Card(new CostAction(Board.this, -10000), "LOTTÓ-n 10.000 eurót nyertél."));
            cards.add(new Card(new MoveAction(Board.this, null, 34), "Lépj a 34-es mezőre."));
            cards.add(new Card(new MoveAction(Board.this, null, 11), "Lépj a 11-es mezőre."));
            cards.add(new Card(new InterestAction(Board.this, 7), "OTP Bank 7% kamatot fizet."));
            cards.add(new Card(new DrawCardAction(Board.this), "Húzz még egy szerencsekártyát."));
            cards.add(new Card(new MoveAction(Board.this, null, 38), "Hajókirándulás, lépj a 38-es mezőre."));
            cards.add(new Card(new CostAction(Board.this, -2500), "Újításért 2500 eurót fizet az OPT Bank."));
            cards.add(new Card(new CostAction(Board.this, 50), "Fizess elő a HETEK hetilapra, 50 euró"));
            cards.add(new Card(new CostAction(Board.this, 20), "Elromlott a videód, fizess 20 eurót."));
            cards.add(new Card(new InterestAction(Board.this, 15), "OTP Bank 15% kamatot fizet."));
            cards.add(new Card(new CostAction(Board.this, -400), "Jó munkádért 400 euró jutalomban részesülsz."));
            cards.add(new Card(new CostAction(Board.this, -6000), "TOTO-n 6000 eurót nyertél."));
            cards.add(new Card(new MoveAction(Board.this, null, 31), "Lépj a 31-es mezőre."));
            cards.add(new Card(new MoveAction(Board.this, null, 30), "Lépj a 30-es mezőre."));
            cards.add(new Card(new CostAction(Board.this, -800), "LOTTÓ-n 800 eurót nyertél."));
            cards.add(new Card(new CostAction(Board.this, -1000), "Jó munkádért 1000 euró jutalomban részesülsz."));
            cards.add(new Card(new MoveAction(Board.this, null, 9), "Lépj a 9-es mezőre."));
            cards.add(new Card(new MoveAction(Board.this, null, 26), "Lépj a 26-es mezőre."));
            cards.add(new Card(new CostAction(Board.this, 70), "Fizess elő a Népszabadság hetilapra, 70 euró."));
            cards.add(new Card(new RelativeMoveAction(Board.this, null, 1), "Lépj előre 1 mezőt."));
            cards.add(new Card(new RelativeMoveAction(Board.this, null, 2), "Lépj előre 2 mezőt."));
            cards.add(new Card(new RelativeMoveAction(Board.this, null, -3), "Lépj vissza 3 mezőt."));
            cards.add(new Card(new RelativeMoveAction(Board.this, null, -3), "Lépj vissza 3 mezőt."));
            cards.add(new Card(new PropertyAddAction(Board.this, Property.HOUSEHOLD),
                    "Ajándékul háztarrtási bútorokat kaptál ajándékba."));
            cards.add(new Card(new PropertyAddAction(Board.this, Property.KITCHEN),
                    "Ajándékul konyhai bútort kaptál ajándékba."));
            cards.add(new Card(new PropertyAddAction(Board.this, Property.LIVING),
                    "Ajándékul szobabútort kaptál ajándékba."));

            Collections.shuffle(cards);
            return cards;
        }
    }

    private final class FieldFactory implements Serializable {
        
        private GameAction[] fields = new GameAction[BOARDSIZE];
        
        private FieldFactory(){};

        private Field[] createFields() {          
            IntStream.range(0, BOARDSIZE).forEach(i -> fields[i] = new CostAction(Board.this, 0));
            fields[0] = new CostAction(Board.this, -2000);
            fields[1] = new CostAction(Board.this, 100);
            fields[2] = new InterestAction(Board.this, 7);
            fields[3] = new DrawCardAction(Board.this);
            fields[4] = new CostAction(Board.this, 100);
            fields[6] = new InsuranceCheckAction(Board.this);
            fields[8] = new CostAction(Board.this, 20);
            fields[9] = new CostAction(Board.this, 500);
            fields[10] = new DrawCardAction(Board.this);
            fields[13] = new SkipStateAction(Board.this, 2, 300);
            fields[14] = new CostAction(Board.this, 100);
            fields[15] = new CostAction(Board.this, 20);
            fields[16] = new DrawCardAction(Board.this);
            fields[17] = new CostAction(Board.this, 20);
            fields[18] = new CostAction(Board.this, 50);
            fields[20] = new CostAction(Board.this, 100);
            fields[21] = new DiceStateAction(Board.this, 1, 6);
            fields[22] = new CostAction(Board.this, 20);
            fields[24] = new CostAction(Board.this, 50);
            fields[28] = new CostAction(Board.this, 300);
            fields[31] = new CostAction(Board.this, 20);
            fields[32] = new DrawCardAction(Board.this);
            fields[34] = new SkipStateAction(Board.this, 3, 400);
            fields[36] = new CostAction(Board.this, 20);
            fields[37] = new DrawCardAction(Board.this);
            fields[38] = new CostAction(Board.this, 100);
            fields[40] = new CostAction(Board.this, 20);
            fields[41] = new CostAction(Board.this, 200);
            Field[] f = Arrays.stream(fields).map(Field::new).toArray(Field[]::new);

            f[6].setProperties(new HashSet<>(Arrays.asList(Property.INSURANCE)));
            f[11].setProperties(new HashSet<>(Arrays.asList(Property.KITCHEN, Property.LIVING)));
            f[19].setProperties(new HashSet<>(Arrays.asList(Property.HOUSE)));
            f[33].setProperties(new HashSet<>(Arrays.asList(Property.HOUSEHOLD)));
            f[35].setProperties(new HashSet<>(Arrays.asList(Property.CAR)));
            f[39].setProperties(new HashSet<>(Arrays.asList(Property.HOUSE)));
            return f;
        }
    }

}
