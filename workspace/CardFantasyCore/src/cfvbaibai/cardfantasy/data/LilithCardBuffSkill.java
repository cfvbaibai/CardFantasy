package cfvbaibai.cardfantasy.data;

import cfvbaibai.cardfantasy.engine.CardInfo;

public class LilithCardBuffSkill extends BuffSkill {
    private String applyToCard;
    public LilithCardBuffSkill(SkillType type, int level, String applyToCard) {
        super(type, level);
        this.applyToCard = applyToCard;
    }

    @Override
    public boolean canApplyTo(CardInfo card) {
        return this.applyToCard.equalsIgnoreCase(card.getName());
    }
}