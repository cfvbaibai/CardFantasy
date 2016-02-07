package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.StringBufferEx;

public class Board {
    private List<Player> players;
    private int round;
    
    public Board() {
        this.players = new ArrayList<Player>();
    }
    
    public Player getPlayer(int number) {
        return players.get(number);
    }
    
    public int addPlayer(Player player) {
        int playerNumber = this.players.size();
        players.add(player);
        return playerNumber;
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
            for (CardInfo card : hand.toList()) {
                allHandCards.add(card);
            }
        }
        return allHandCards;
    }

    public List<Player> getPlayers() {
        return new ArrayList<Player>(this.players);
    }

    public String getBoardInText() {
        StringBufferEx sb = new StringBufferEx();
        sb.appendLine("-----------------------------------------------------------------------------");
        Player player = this.getPlayer(0);
        sb.appendLineFormat("玩家: %s - HP: %d", player.getId(), player.getHP());
        showGrave(sb, player.getGrave());
        showDeck(sb, player.getDeck());
        showHand(sb, player.getHand());
        sb.appendLine("");
        showRune(sb, player.getRuneBox());
        showField(sb, player.getField());
        sb.appendLine("");

        player = this.getPlayer(1);
        sb.appendLine("");
        showField(sb, player.getField());
        showRune(sb, player.getRuneBox());
        sb.appendLine("");
        showHand(sb, player.getHand());
        showDeck(sb, player.getDeck());
        showGrave(sb, player.getGrave());
        sb.appendLineFormat("玩家: %s - HP: %d", player.getId(), player.getHP());
        sb.appendLine("-----------------------------------------------------------------------------");
        return sb.toString();
    }
    
    private void showGrave(StringBufferEx sb, Grave grave) {
        sb.append("墓地: ");
        for (CardInfo card : grave.toList()) {
            sb.append(String.format("%s (等级=%d, 攻击=%d, HP=%d), ",
                    card.getUniqueName(), card.getLevel(), card.getInitAT(), card.getMaxHP()));
        }
        sb.appendLine("");
    }

    private void showDeck(StringBufferEx sb, Deck deck) {
        sb.append("牌堆: ");
        for (CardInfo card : deck.toList()) {
            sb.append(String.format("%s (等级=%d, 攻击=%d, HP=%d), ",
                    card.getUniqueName(), card.getLevel(), card.getInitAT(), card.getMaxHP()));
        }
        sb.appendLine("");
    }

    private void showHand(StringBufferEx sb, Hand hand) {
        sb.append("手牌: ");
        for (CardInfo card : hand.toList()) {
            sb.append(String.format("%s (等级=%d, 攻击=%d, HP=%d, 等待=%d), ",
                    card.getUniqueName(), card.getLevel(), card.getInitAT(), card.getMaxHP(), card.getSummonDelay()));
        }
        sb.appendLine("");
    }

    private void showField(StringBufferEx sb, Field field) {
        int i = 0;
        List<CardInfo> cards = field.getAliveCards();
        for (CardInfo card : cards) {
            sb.append(String.format("[%d] %s (等级=%d, 攻击=%d/%d, HP=%d/%d/%d, 状态=%s, 效果=%s)\r\n", i, card.getUniqueName(),
                    card.getLevel(), card.getCurrentAT(), card.getInitAT(), card.getHP(), card.getMaxHP(),
                    card.getRawMaxHP(), card.getStatus().getShortDesc(), card.getEffectsDesc()));
            ++i;
        }
        if (cards.size() > 0) {
            sb.deleteLastChars(2);
        }
        sb.appendLine("");
    }
    
    private void showRune(StringBufferEx sb, RuneBox runeBox) {
        sb.append("符文 : ");
        for (RuneInfo rune : runeBox.getRunes()) {
            sb.append(rune.getShortDesc());
            sb.append(", ");
        }
        sb.appendLine("");
    }

    public void validate() {
        List<CardInfo> cards = new ArrayList<CardInfo>();
        for (Player player : this.getPlayers()) {
            cards.addAll(player.getField().getAliveCards());
            cards.addAll(player.getHand().getCards());
            cards.addAll(player.getGrave().getCards());
            cards.addAll(player.getDeck().getCards());
            cards.addAll(player.getOutField().getCards());
        }
        if (cards.size() <= 1) {
            return;
        }
        cards.sort(new Comparator<CardInfo>() {
            @Override
            public int compare(CardInfo o1, CardInfo o2) {
                return o1.getUniqueName().compareTo(o2.getUniqueName());
            }
        });
        
        for (int i = 0; i < cards.size() - 1; ++i) {
            if (cards.get(i).getUniqueName().equals(cards.get(i + 1).getUniqueName())) {
                throw new CardFantasyRuntimeException(
                        "无效的场上状态，请去贴吧报告BUG! 多次出现卡牌：" + cards.get(i).getUniqueName());
            }
        }
    }
}
