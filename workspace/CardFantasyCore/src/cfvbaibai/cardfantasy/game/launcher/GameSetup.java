package cfvbaibai.cardfantasy.game.launcher;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.PlayerCardBuffSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.game.SkillBuilder;
import cfvbaibai.cardfantasy.game.VictoryCondition;

public class GameSetup {
    private String name1;
    private String name2;
    private String deck1;
    private String deck2;
    private int heroLv1;
    private int heroLv2;
    private int heroHpBuff1;
    private int heroHpBuff2;
    private int firstAttack;
    private int deckOrder;
    private VictoryCondition victoryCondition1;
    private List<Skill> buffs1;
    private List<Skill> buffs2;
    private int gameCount;
    private GameUI ui;
    private GameType gameType;

    private GameSetup(GameType gameType, String deck1, String deck2, int heroLv1, int heroLv2,
            int heroHpBuff1, int heroHpBuff2, List<Skill> buffs1, List<Skill> buffs2,
            int firstAttack, int deckOrder, VictoryCondition victoryCondition1,
            int gameCount, GameUI ui) {
        this.deck1 = deck1;
        this.deck2 = deck2;
        this.heroLv1 = heroLv1;
        this.heroLv2 = heroLv2;
        this.heroHpBuff1 = heroHpBuff1;
        this.heroHpBuff2 = heroHpBuff2;
        this.firstAttack = firstAttack;
        this.deckOrder = deckOrder;
        this.victoryCondition1 = victoryCondition1;
        this.buffs1 = buffs1 == null ? new ArrayList<Skill>() : new ArrayList<Skill>(buffs1);
        this.buffs2 = buffs2 == null ? new ArrayList<Skill>() : new ArrayList<Skill>(buffs2);
        this.gameCount = gameCount;
        this.ui = ui;
        this.gameType = gameType;
        switch (gameType) {
        case Arena:
            this.name1 = "玩家1";
            this.name2 = "玩家2";
            break;
        case Boss:
            this.name1 = "BOSS";
            this.name2 = "玩家2";
            break;
        case Map:
            break;
        case Lilith:
            break;
        default:
            throw new CardFantasyRuntimeException("Invalid game type: " + gameType);
        }
    }
    
    /**
     * 
     * @param playerDeck
     * @param bossName
     * @param heroLv
     * @param kingdomBuff
     * @param forestBuff
     * @param savageBuff
     * @param hellBuff
     * @param guardType
     * @param gameCount
     * @param ui
     * @return
     */
    public static GameSetup setupBossGame(
        String playerDeck, String bossName, int heroLv, 
        int kingdomBuff, int forestBuff, int savageBuff, int hellBuff,
        int guardType, int gameCount, GameUI ui) {
        List<Skill> legionBuffs = SkillBuilder.buildLegionBuffs(kingdomBuff, forestBuff, savageBuff, hellBuff);
        String bossDeck = bossName + getBossGuards(guardType);
        return new GameSetup(GameType.Boss, bossDeck, playerDeck, 999999, heroLv, 100, 100, null, legionBuffs,
                0 /* firstAttack */, 0 /* deckOrder */, VictoryCondition.parse("Any"), gameCount, ui);
    }

    private static CardData getBossGuard(String[] bossGuardCandidates) {
        Randomizer random = Randomizer.getRandomizer();
        int guardNameIndex = random.next(0, bossGuardCandidates.length);
        String guardName = bossGuardCandidates[guardNameIndex];
        CardData guard = CardDataStore.loadDefault().getCard(guardName);
        if (guard == null) {
            throw new CardFantasyRuntimeException("Invalid guard name " + guardName);
        }
        return guard;
    }

    private static String getBossGuards(int guardType) {
        if (guardType == 0) {
            return "";
        }
        List<CardData> bossGuards = new ArrayList<CardData>();
        if (guardType == 1) {
            while (true) {
                bossGuards.clear();
                int totalCost = 0;
                for (int i = 0; i < 9; ++i) {
                    CardData bossGuard = getBossGuard(CardDataStore.weakBossGuardians);
                    bossGuards.add(bossGuard);
                    totalCost += bossGuard.getBaseCost();
                }
                if (totalCost <= 101) {
                    break;
                }
            }
        }
        else if (guardType == 2) {
            while (true) {
                bossGuards.clear();
                int totalCost = 0;
                for (int i = 0; i < 3; ++i) {
                    CardData fiveStarBossGuard = getBossGuard(CardDataStore.fiveStarBossGuardians);
                    bossGuards.add(fiveStarBossGuard);
                    totalCost += fiveStarBossGuard.getBaseCost();
                }
                for (int i = 3; i < 9; ++i) {
                    CardData bossGuard = getBossGuard(CardDataStore.weakBossGuardians);
                    bossGuards.add(bossGuard);
                    totalCost += bossGuard.getBaseCost();
                }
                if (totalCost <= 141) {
                    break;
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 9; ++i) {
            CardData bossGuard = bossGuards.get(i);
            sb.append("," + bossGuard.getName());
        }
        return sb.toString();
    }

    public static GameSetup setupArenaGame(
            String deck1, String deck2, int heroLv1, int heroLv2,
            int p1CardAtBuff, int p1CardHpBuff, int p1HeroHpBuff,
            int p2CardAtBuff, int p2CardHpBuff, int p2HeroHpBuff,
            int firstAttack, int deckOrder, VictoryCondition victoryCondition1,
            int gameCount, GameUI ui) {
        List<Skill> p1CardBuffs = new ArrayList<Skill>();
        if (p1CardAtBuff != 100) {
            p1CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始攻击调整, p1CardAtBuff - 100));
        }
        if (p1CardHpBuff != 100) {
            p1CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始体力调整, p1CardHpBuff - 100));
        }
        List<Skill> p2CardBuffs = new ArrayList<Skill>();
        if (p2CardAtBuff != 100) {
            p2CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始攻击调整, p2CardAtBuff - 100));
        }
        if (p2CardHpBuff != 100) {
            p2CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始体力调整, p2CardHpBuff - 100));
        }
        return new GameSetup(GameType.Arena, deck1, deck2, heroLv1, heroLv2, p1HeroHpBuff, p2HeroHpBuff,
                p1CardBuffs, p2CardBuffs, firstAttack, deckOrder, victoryCondition1, gameCount, ui);
    }
    public boolean isBossBattle() {
        return this.gameType == GameType.Boss;
    }
    public String getDeck1() {
        return deck1;
    }
    public String getDeck2() {
        return deck2;
    }
    public int getHeroLv1() {
        return heroLv1;
    }
    public int getHeroLv2() {
        return heroLv2;
    }
    public int getHeroHpBuff1() {
        return heroHpBuff1;
    }
    public int getHeroHpBuff2() {
        return heroHpBuff2;
    }
    public int getFirstAttack() {
        return firstAttack;
    }
    public int getDeckOrder() {
        return deckOrder;
    }
    public VictoryCondition getVictoryCondition1() {
        return victoryCondition1;
    }
    public int getGameCount() {
        return gameCount;
    }
    public GameUI getUi() {
        return ui;
    }
    public String getName1() {
        return name1;
    }
    public String getName2() {
        return name2;
    }
    public GameType getGameType() {
        return gameType;
    }
    public List<Skill> getBuffs1() {
        return buffs1;
    }
    public List<Skill> getBuffs2() {
        return buffs2;
    }
}
