package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.NonSerializable;
import cfvbaibai.cardfantasy.data.*;

public class IndentureInfo extends EntityInfo {
    @NonSerializable
    private Player owner;
    private Indenture indenture;
    @NonSerializable
    private CardInfo cardInfo;
    private int effectNumber;
    private SkillUseInfo skillUseInfo;

    public IndentureInfo(Indenture indenture, Player owner) {
        CardSkill skill = new CardSkill(SkillType.召唤契约,0,0,false,false,false,false);
        this.indenture = indenture;
        this.owner = owner;
        this.cardInfo = new CardInfo(indenture.getCard(), owner);
        this.effectNumber = indenture.getEffectNumber();
        this.skillUseInfo = new SkillUseInfo(this,skill);
    }
    
    public Player getOwner() {
        return this.owner;
    }

    public CardInfo getCardInfo() {
        return this.cardInfo;
    }
    
    public IndentureActivator getIndentureActivator() {
        return this.indenture.getIndentureActivator();
    }

    public int getEffectNumber() {
        return this.effectNumber;
    }

    public String getShortDesc() {
//        return String.format("【%s%d-%s%d-%s%d】", this.indenture.getName(), this.indenture.getLevel(), this.cardInfo.getName(),
//                this.cardInfo.getLevel(), this.cardInfo.getExtraSkill().getName(),this.cardInfo.getExtraSkill().getLevel());
        return String.format("【%s%d-%s%d】", this.indenture.getName(), this.indenture.getLevel(), this.cardInfo.getName(),
                this.cardInfo.getLevel());
    }

    public boolean is(IndentureData data) {
        return this.indenture.is(data);
    }

    @Override
    public CardStatus getStatus() {
        return new CardStatus();
    }

    public String getName() {
        return this.indenture.getName();
    }

    public SkillUseInfo getSkillUseInfo() {
        return skillUseInfo;
    }
}
