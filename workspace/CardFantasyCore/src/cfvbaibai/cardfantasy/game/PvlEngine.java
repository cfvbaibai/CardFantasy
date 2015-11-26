package cfvbaibai.cardfantasy.game;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.PlayerCardBuffSkill;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.BattleEngine;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.Rule;

public class PvlEngine extends GameEngine {
    private int timeout;
    
    public PvlEngine(GameUI ui, Rule rule) {
        this(ui, rule, 50);
    }
    
    public PvlEngine(GameUI ui, Rule rule, int timeout) {
        super(ui, rule);
        this.timeout = timeout;
    }
    
    public static List<Skill> getCardBuffs(int cardAtBuff, int cardHpBuff) {
        List<Skill> result = new ArrayList<Skill>();
        result.add(new PlayerCardBuffSkill(SkillType.原始攻击调整, cardAtBuff));
        result.add(new PlayerCardBuffSkill(SkillType.原始体力调整, cardHpBuff));
        return result;
    }

    public PvlGameResult rushBoss(PlayerInfo lilith, int initialHP, PlayerInfo player) {
        int battleCount = 0;
        List<CardInfo> survivors = null;
        while (true) {
            BattleEngine engine = this.createBattleEngine();
            engine.registerPlayers(lilith, player);
            if (survivors != null) {
                engine.importSurvivers(0, survivors);
            }
            if (battleCount == 0) {
                Player lilithPlayer = engine.getStage().getPlayers().get(0);
                CardInfo lilithCard = lilithPlayer.getDeck().toList().get(0);
                lilithCard.setRemainingHP(initialHP);
            }
            GameResult result = engine.playGame();
            ++battleCount;
            if (battleCount > this.timeout) {
                throw new PvlGameTimeoutException();
            }
            if (result.getWinner().getId().equals(player.getId())) {
                return new PvlGameResult(battleCount, initialHP);
            }
            survivors = engine.exportSurvivers(0);
        }
    }
    
    public PvlGameResult clearGuards(PlayerInfo lilith, PlayerInfo player, int targetLilithAliveCardCount) {
        int battleCount = 0;
        List<CardInfo> survivors = null;
        int maxLilithHp = getLilithCardHp(lilith);

        while (true) {
            BattleEngine engine = this.createBattleEngine();
            engine.registerPlayers(lilith, player);
            if (survivors != null) {
                engine.importSurvivers(0, survivors);
            }
            GameResult result = engine.playGame();
            ++battleCount;
            if (battleCount > this.timeout) {
                throw new PvlGameTimeoutException();
            }
            survivors = engine.exportSurvivers(0);
            if (result.getWinner().getId().equals(player.getId())) {
                return getClearGuardsResult(battleCount, result.getLoser(), maxLilithHp);
            }
            Player lilithPlayer = result.getWinner();
            /*
            if (isLilithKilled(lilithPlayer)) {
                return getClearGuardsResult(battleCount, lilithPlayer);
            }*/
            int lilithAliveCardCount = 0;
            lilithAliveCardCount += lilithPlayer.getDeck().size();
            lilithAliveCardCount += lilithPlayer.getHand().size();
            lilithAliveCardCount += lilithPlayer.getField().getAliveCards().size();
            if (lilithAliveCardCount <= targetLilithAliveCardCount) {
                return getClearGuardsResult(battleCount, lilithPlayer, maxLilithHp);
            }
        }
    }

    private PvlGameResult getClearGuardsResult(int battleCount, Player lilith, int maxLilithHp) {
        //int cardNumber = 0;
        for (CardInfo card : lilith.getField().getAliveCards()) {
            //cardNumber++;
            if (card.getRace() == Race.BOSS) {
                return new PvlGameResult(battleCount, card.getEternalWound());
            }
        }
        for (CardInfo card : lilith.getGrave().toList()) {
            if (card.getRace() == Race.BOSS) {
                return new PvlGameResult(battleCount, card.getRawMaxHP());
            }
        }
        for (CardInfo card : lilith.getOutField().toList()) {
            if (card.getRace() == Race.BOSS) {
                return new PvlGameResult(battleCount, card.getRawMaxHP());
            }
        }
        for (CardInfo card : lilith.getDeck().toList()) {
            //cardNumber++;
            if (card.getRace() == Race.BOSS) {
                return new PvlGameResult(battleCount, card.getEternalWound());
            }
        }
        for (CardInfo card : lilith.getHand().toList()) {
            //cardNumber++;
            if (card.getRace() == Race.BOSS) {
                return new PvlGameResult(battleCount, card.getEternalWound());
            }
        }
        /*
        if (cardNumber > 0) //如果剩余下来卡牌不是莉莉丝
        {
        */
            return new PvlGameResult(battleCount, maxLilithHp);
        /*
        }
            throw new CardFantasyRuntimeException("Should not reach here.");
        */
    }
/*
    private boolean isLilithKilled(Player lilith) {
        for (CardInfo card : lilith.getGrave().toList()) {
            if (card.getRace() == Race.BOSS) {
                return true;
            }
        }
        for (CardInfo card : lilith.getOutField().toList()) {
            if (card.getRace() == Race.BOSS) {
                return true;
            }
        }
        return false;
    }
*/
    private int getLilithCardHp(PlayerInfo lilith) {
        Card lilithCard = null;
        for (Card card : lilith.getCards()) {
            if (card.getRace() == Race.BOSS) {
                lilithCard = card;
                break;
            }
        }

        if (lilithCard == null) {
            throw new CardFantasyRuntimeException("Could not find lilith card.");
        }

        int maxHp = lilithCard.getMaxHP();
        return maxHp;
    }
}
