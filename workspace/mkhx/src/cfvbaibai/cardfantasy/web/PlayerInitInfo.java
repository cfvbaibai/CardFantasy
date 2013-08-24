package cfvbaibai.cardfantasy.web;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class PlayerInitInfo {
    private String id;
    private int level;
    private int maxHP;
    private int hp;
    private int number;
    private List<CardInitInfo> deckInitInfos;
    private List<RuneInitInfo> runeInitInfos;
    
    public PlayerInitInfo(Player player, int playerNumber) {
        this.id = player.getId();
        this.level = player.getLevel();
        this.maxHP = player.getMaxHP();
        this.hp = player.getMaxHP();
        this.number = playerNumber;
        this.deckInitInfos = new ArrayList<CardInitInfo>();
        for (CardInfo card : player.getDeck().toList()) {
            this.deckInitInfos.add(new CardInitInfo(card));
        }
        this.runeInitInfos = new ArrayList<RuneInitInfo>();
        for (RuneInfo rune : player.getRuneBox().getRunes()) {
            this.runeInitInfos.add(new RuneInitInfo(rune));
        }
    }
    public String getId() {
        return id;
    }
    public int getLevel() {
        return level;
    }
    public int getMaxHP() {
        return maxHP;
    }
    public int getHP() {
        return hp;
    }
    public int getNumber() {
        return number;
    }
    public void addInitCard(CardInitInfo cardInitInfo) {
        this.deckInitInfos.add(cardInitInfo);
    }
    public List<CardInitInfo> getDeckInitInfos() {
        return new ArrayList<CardInitInfo>(deckInitInfos);
    }
    public void addInitRune(RuneInitInfo runeInitInfo) {
        this.runeInitInfos.add(runeInitInfo);
    }
    public List<RuneInitInfo> getRuneInitInfos() {
        return new ArrayList<RuneInitInfo>(runeInitInfos);
    }
}
