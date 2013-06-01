package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private List<Player> players;
    private int round;
    
    public Board() {
        this.players = new ArrayList<Player>();
    }
    
    public Player getPlayer(int number) {
        return players.get(number);
    }
    
    public void addPlayer(Player player) {
        players.add(player);
    }
    
    public int getPlayerCount() {
        return this.players.size();
    }
    
    public int getRound() {
        return round;
    }
    
    public void setRound(int round) {
        this.round = round;
    }

    public Collection<CardInfo> getAllHandCards() {
        Collection <CardInfo> allHandCards = new LinkedList<CardInfo>();
        for (Player player : players) {
            Hand hand = player.getHand();
            for (CardInfo card : hand) {
                allHandCards.add(card);
            }
        }
        return allHandCards;
    }
}
