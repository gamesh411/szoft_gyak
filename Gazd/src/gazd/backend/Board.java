/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend;


import gazd.backend.state.SkipState;
import gazd.controller.action.CostAction;
import gazd.controller.action.DiceStateAction;
import gazd.controller.action.DrawCardAction;
import gazd.controller.action.InterestAction;
import gazd.controller.action.MoveAction;
import gazd.controller.action.RelativeMoveAction;
import gazd.controller.action.ShowMessageGameAction;
import gazd.controller.action.SkipStateAction;
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
import javax.swing.JOptionPane;

/**
 *
 * @author mmeta
 */
public class Board {

    private List<Player> players;
    private Player currentPlayer;
    private final Field[] fields;

    private final List<Card> cards;
    private final int BOARDSIZE = 42;

    private final Deque<IGameAction> actionQueue;

    public Board() {
        players = new ArrayList<>();
        fields = fieldsFactory();
        cards = cardsFactory();
        actionQueue = new ArrayDeque<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void start() {
        currentPlayer = players.get(0);
    }

    public boolean queueLateAction(IGameAction action) {
        return actionQueue.offerLast(action);
    }

    public boolean queueImmediateAction(IGameAction action) {
        return actionQueue.offerFirst(action);
    }

    public void doTurn() {
        while (!actionQueue.isEmpty()) {
            actionQueue.poll().execute();
        }
    }

    public void step() {

        Random rand = new Random();
        int dice = rand.nextInt(6) + 1;
        int newPosition = (currentPlayer.getPosition() + dice) % BOARDSIZE;
        currentPlayer.getState().turn();
        if(currentPlayer.getState().canStep(currentPlayer, dice)) stepOn(newPosition);

    }

    public void stepOn(int newPosition) {
        final int START = 0;
        if(currentPlayer.getPosition()> newPosition && newPosition!=START) queueImmediateAction(new CostAction(this, -2000));
        currentPlayer.setPosition(newPosition);
        queueImmediateAction(fields[newPosition].getAction());
    }

    public void nextPlayer() {
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
        
        if (!currentPlayer.canStep() && currentPlayer.getState() instanceof SkipState) {
            currentPlayer.decSkip();
            nextPlayer();
        }
    }

    public void drawCard() {
        queueImmediateAction(cards.get(0).getAction());
        queueLateAction(new ShowMessageGameAction(currentPlayer.getPosition() + " mező: " + cards.get(0).getMessage()));
        Collections.rotate(cards, 1);
    }

    private Field[] fieldsFactory() {
        IGameAction[] fields = new IGameAction[BOARDSIZE];
        IntStream.range(0, BOARDSIZE).forEach(i -> fields[i] = new CostAction(this, 0));
        fields[0] = new CostAction(this, -2000);
        fields[1] = new CostAction(this, 100);
        fields[2] = new InterestAction(this,7);
        fields[3] = new DrawCardAction(this);
        fields[4] = new CostAction(this, 100);
        fields[8] = new CostAction(this, 20);
        fields[9] = new CostAction(this, 500);
        fields[10] = new DrawCardAction(this);
        fields[13] = new SkipStateAction(this, 2,300);
        fields[14] = new CostAction(this, 100);
        fields[15] = new CostAction(this, 20);
        fields[16] = new DrawCardAction(this);
        fields[17] = new CostAction(this, 20);
        fields[18] = new CostAction(this, 50);
        fields[20] = new CostAction(this, 100);
        fields[21] = new DiceStateAction(this,1,6);
        fields[22] = new CostAction(this, 20);
        fields[24] = new CostAction(this, 50);
        fields[28] = new CostAction(this, 300);
        fields[31] = new CostAction(this, 20);
        fields[32] = new DrawCardAction(this);
        fields[34] = new SkipStateAction(this, 3,400);
        fields[36] = new CostAction(this, 20);
        fields[37] = new DrawCardAction(this);
        fields[38] = new CostAction(this, 100);
        fields[40] = new CostAction(this, 20);
        fields[41] = new CostAction(this, 200);
        Field[] f = Arrays.stream(fields).map(s -> new Field(s)).toArray(Field[]::new);
        
        f[11].setProperties(new HashSet<>(Arrays.asList(Property.KITCHEN,Property.LIVING)));
        f[19].setProperties(new HashSet<>(Arrays.asList(Property.HOUSE)));
        f[33].setProperties(new HashSet<>(Arrays.asList(Property.HOUSEHOLD)));
        f[35].setProperties(new HashSet<>(Arrays.asList(Property.CAR)));
        f[39].setProperties(new HashSet<>(Arrays.asList(Property.HOUSE)));
        return f;
    }

    private List<Card> cardsFactory() {
        List<Card> cards = new LinkedList<>();
        cards.add(new Card(new MoveAction(this,null,33), "KERAVILL-ban vásárlhatsz. Lépj a 33-as mezőre"));
        cards.add(new Card(new MoveAction(this,null,0), "Lépj a START mezőre."));
        cards.add(new Card(new CostAction(this,-400), "TOTO-n 400 eurót nyertél."));
        cards.add(new Card(new CostAction(this,-10000), "LOTTÓ-n 10.000 eurót nyertél."));
        cards.add(new Card(new MoveAction(this,null,34), "Lépj a 34-es mezőre."));
        cards.add(new Card(new MoveAction(this,null,11), "Lépj a 11-es mezőre."));
        cards.add(new Card(new InterestAction(this, 7), "OTP Bank 7% kamatot fizet."));
        cards.add(new Card(new DrawCardAction(this),"Húzz még egy szerencsekártyát."));
        cards.add(new Card(new MoveAction(this,null,38), "Hajókirándulás, lépj a 38-es mezőre."));
        cards.add(new Card(new CostAction(this,-2500), "Újításért 2500 eurót fizet az OPT Bank."));
        cards.add(new Card(new CostAction(this,50), "Fizess elő a HETEK hetilapra, 50 euró"));
        cards.add(new Card(new CostAction(this,20), "Elromlott a videód, fizess 20 eurót."));
        cards.add(new Card(new InterestAction(this, 15), "OTP Bank 15% kamatot fizet."));
        cards.add(new Card(new CostAction(this,-400), "Jó munkádért 400 euró jutalomban részesülsz."));
        cards.add(new Card(new CostAction(this,-6000), "TOTO-n 6000 eurót nyertél."));
        cards.add(new Card(new MoveAction(this,null,31), "Lépj a 31-es mezőre."));
        cards.add(new Card(new MoveAction(this,null,30), "Lépj a 30-es mezőre."));
        cards.add(new Card(new CostAction(this,-800), "LOTTÓ-n 800 eurót nyertél."));
        cards.add(new Card(new CostAction(this,-1000), "Jó munkádért 1000 euró jutalomban részesülsz."));
        cards.add(new Card(new MoveAction(this,null,9), "Lépj a 9-es mezőre."));
        cards.add(new Card(new MoveAction(this,null,26), "Lépj a 26-es mezőre.")); 
        cards.add(new Card(new CostAction(this,70), "Fizess elő a Népszabadság hetilapra, 70 euró.")); 
        cards.add(new Card(new RelativeMoveAction(this, null, 1), "Lépj előre 1 mezőt.")); 
        cards.add(new Card(new RelativeMoveAction(this, null, 2), "Lépj előre 2 mezőt.")); 
        cards.add(new Card(new RelativeMoveAction(this, null, -3), "Lépj vissza 3 mezőt."));
        cards.add(new Card(new RelativeMoveAction(this, null, -3), "Lépj vissza 3 mezőt."));
        Collections.shuffle(cards);
        return cards;
    }
    
    public Field[] getFields() {
        return fields;
    }

    public void checkGame() {
        if(currentPlayer.getProperties().size()== Property.values().length){
            JOptionPane.showMessageDialog(null, "Győztes: "+currentPlayer.getName());
            queueImmediateAction(() -> System.exit(0));
        }
        
    }

}
