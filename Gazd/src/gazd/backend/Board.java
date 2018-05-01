/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend;

import gazd.controller.CostAction;
import gazd.controller.DrawCardAction;
import gazd.controller.ShowMessageGameAction;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *
 * @author mmeta
 */
public class Board {

    private List<Player> players;
    private Player currentPlayer;
    private final IGameAction[] fields;
    private final List<IGameAction> cards;
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
        stepOn(newPosition);

    }

    public void stepOn(int newPosition) {
        currentPlayer.setPosition(newPosition);
        //queueImmediateAction(fields[newPosition].onPlayerArrived(currentPlayer));
        queueImmediateAction(fields[newPosition]);
    }

    public void nextPlayer() {
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
    }

    public void drawCard() {
        queueLateAction(cards.get(0));
        Collections.rotate(cards, 1);
    }

    private IGameAction[] fieldsFactory() {
        IGameAction[] fields = new IGameAction[BOARDSIZE];
        IntStream.range(0, BOARDSIZE).forEach(i -> fields[i] = new CostAction(this, 0));
        fields[1] = new CostAction(this, 100);
        fields[3] = new DrawCardAction(this);
        fields[4] = new CostAction(this, 100);
        fields[8] = new CostAction(this, 20);
        fields[9] = new CostAction(this, 500);
        fields[10] = new DrawCardAction(this);
        fields[14] = new CostAction(this, 100);
        fields[15] = new CostAction(this, 20);
        fields[16] = new DrawCardAction(this);
        fields[17] = new CostAction(this, 20);
        fields[18] = new CostAction(this, 50);
        fields[20] = new CostAction(this, 100);
        fields[22] = new CostAction(this, 20);
        fields[24] = new CostAction(this, 50);
        fields[28] = new CostAction(this, 300);
        fields[31] = new CostAction(this, 20);
        fields[32] = new DrawCardAction(this);
        fields[36] = new CostAction(this, 20);
        fields[37] = new DrawCardAction(this);
        fields[38] = new CostAction(this, 100);
        fields[40] = new CostAction(this, 20);
        fields[41] = new CostAction(this, 200);

        return fields;
    }

    private List<IGameAction> cardsFactory() {
        List<IGameAction> cards = new LinkedList<>();
        IntStream.range(0, BOARDSIZE).forEach(i -> cards.add(new ShowMessageGameAction("Kartya")));
        return cards;
    }

}
