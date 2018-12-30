package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;

public class Player extends EntityInfo {
    private PlayerInfo playerInfo;
    private Deck deck;
    private Hand hand;
    private Grave grave;
    private BeforeDeath beforeDeath;
    private Field field;
    private OutField outField;
    private RuneBox runeBox;
    private List<SkillUseInfo> cardBuffs;
    private int hp;
    private List<CardInfo> primaryCards;
    private List<CardInfo>  productCards;
    private List<SkillUseInfo>  counterAttackHero;//背水系列技能
    private List<SkillUseInfo>  impregnableDefenseHero;//铁壁系列技能
    
    public Player(PlayerInfo playerInfo, StageInfo stage) {
        this.playerInfo = playerInfo;
        this.primaryCards = prepareCards();
        this.deck = new Deck(primaryCards);
        this.hand = new Hand(stage.getRule());
        this.grave = new Grave();
        this.beforeDeath = new BeforeDeath();
        this.field = new Field(this);
        this.outField = new OutField();
        this.runeBox = new RuneBox(this, playerInfo.getRunes());
        this.hp = playerInfo.getMaxHP();
        this.cardBuffs = new ArrayList<SkillUseInfo>();
        this.productCards = new ArrayList<CardInfo>();
        this.counterAttackHero = new ArrayList<SkillUseInfo>();
        this.impregnableDefenseHero = new ArrayList<SkillUseInfo>();
        for (Skill cardBuff : playerInfo.getCardBuffs()) {
            this.cardBuffs.add(new SkillUseInfo(this, cardBuff));
        }
    }
    
    /**
     * This method does not return summoned minion cards.
     * @return
     */
    public List<CardInfo> getAllPrimaryCards() {
        return new ArrayList<CardInfo>(primaryCards);
    }

    public int getMaxCost() {
        return this.playerInfo.getMaxCost();
    }
    
    public int getCardSlot() {
        return this.playerInfo.getCardSlot();
    }
    
    public int getRuneSlot() {
        return this.playerInfo.getRuneSlot();
    }
    
    public PlayerInfo getPlayerInfo() {
        return this.playerInfo;
    }
    
    public RuneBox getRuneBox() {
        return this.runeBox;
    }
    
    public Hand getHand() {
        return this.hand;
    }
    
    public Deck getDeck() {
        return this.deck;
    }
    
    public Grave getGrave() {
        return this.grave;
    }

    public BeforeDeath getBeforeDeath() {
        return this.beforeDeath;
    }
    
    public Field getField() {
        return this.field;
    }
    
    public OutField getOutField() {
        return this.outField;
    }
    
    public int getHP() {
        return this.hp;
    }
    
    public int getMaxHP() {
        return this.playerInfo.getMaxHP();
    }

    public String getId() {
        return this.getPlayerInfo().getId();
    }

    public void setHP(int hp) {
        this.hp = hp;
        if (this.hp <= 0) {
            // throw new HeroDieSignal(this);
            // Now the game will not stop if hero is killed in the middle of the round.
            // It is possible that hero is cured even after he's killed (e.g. 叹惋之歌)
            // Hero HP is only checked when the round ends.
            this.hp = 0;
        }
    }
    
    private List<CardInfo> prepareCards() {
        Collection <Card> cards = this.getPlayerInfo().getCards();
        List<CardInfo> cardInfos = new ArrayList<CardInfo>();
        for (Card card : cards) {
            cardInfos.add(new CardInfo(card, this));
        }
        return cardInfos;
    }

    public String getShortDesc() {
        return String.format("<%s>", this.playerInfo.getId());
    }

    public RuneInfo getActiveRuneOf(RuneData runeData) {
        RuneInfo rune = this.getRuneBox().getRuneOf(runeData);
        if (rune == null) {
            return null;
        } else if (rune.isActivated()) {
            return rune;
        } else {
            return null;
        }
    }

    public int getLevel() {
        return this.getPlayerInfo().getLevel();
    }

    @Override
    public CardStatus getStatus() {
        return new CardStatus();
    }

    @Override
    public Player getOwner() {
        return this;
    }

    public List<SkillUseInfo> getCardBuffs() {
         return new ArrayList<SkillUseInfo>(this.cardBuffs);
    }

    public List<CardInfo> getProductCards() {
        return productCards;
    }

    public void addProductCards(CardInfo productCard) {
        this.productCards.add(productCard);
    }

    public List<SkillUseInfo> getCounterAttackHero() {
        return this.counterAttackHero;
    }

    public void addCounterAttackHero(SkillUseInfo skillUseInfo) {
        for(SkillUseInfo addSkillUserInfo:this.counterAttackHero)
        {
            if(addSkillUserInfo == skillUseInfo)
            {
                throw new CardFantasyRuntimeException("skillUseInfo is reused");
            }
        }
        this.counterAttackHero.add(skillUseInfo);
    }

    public void removeCounterAttackHero(SkillUseInfo skillUseInfo) {
        this.counterAttackHero.remove(skillUseInfo);
    }

    public List<SkillUseInfo> getImpregnableDefenseHero() {
        return this.impregnableDefenseHero;
    }

    public void addImpregnableDefenseHero(SkillUseInfo skillUseInfo) {
        for(SkillUseInfo addSkillUserInfo:this.impregnableDefenseHero)
        {
            if(addSkillUserInfo == skillUseInfo)
            {
                throw new CardFantasyRuntimeException("skillUseInfo is reused");
            }
        }
        this.impregnableDefenseHero.add(skillUseInfo);
    }

    public void removeImpregnableDefenseHero(SkillUseInfo skillUseInfo) {
        this.impregnableDefenseHero.remove(skillUseInfo);
    }
}
