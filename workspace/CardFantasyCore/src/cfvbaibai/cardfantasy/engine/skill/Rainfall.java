package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class Rainfall {
    private static class Heal {
        public CardInfo healee;
        public int healHP;
        public Heal(CardInfo healee, int healHP) {
            this.healee = healee;
            this.healHP = healHP;
        }
    }
    
    public static void healCards(SkillResolver resolver, EntityInfo healer, Skill skill, HealType type, List<CardInfo> healeeCandidates) throws HeroDieSignal {
        List<Heal> heals = new ArrayList<Heal>();
        for (CardInfo healee : healeeCandidates) {
            int healHP = 0;
            if (type == HealType.Percentage) { // 月神的护佑, 月神的触碰
                healHP = healee.getMaxHP() * skill.getImpact3() / 100;
            } else {
                healHP = skill.getImpact();
            }
            if (healHP + healee.getHP() > healee.getMaxHP()) {
                healHP = healee.getMaxHP() - healee.getHP();
            }
            if (healHP == 0) {
                continue;
            }
            heals.add(new Heal(healee, healHP));
        }
        List<CardInfo> healees = new ArrayList<CardInfo>();
        for (Heal heal : heals) {
            healees.add(heal.healee);
        }
        resolver.getStage().getUI().useSkill(healer, healees, skill, true);
        for (Heal heal : heals) {
            OnAttackBlockingResult result = resolver.resolveHealBlockingSkills(healer, heal.healee, skill);
            if (!result.isAttackable()) {
                continue;
            }
            resolver.getStage().getUI().healCard(healer, heal.healee, skill, heal.healHP);
            resolver.applyDamage(healer, heal.healee, skill, -heal.healHP);
        }
    }

    public static void apply(Skill cardSkill, SkillResolver resolver, EntityInfo healer) throws HeroDieSignal {
        if (healer == null) {
            return;
        }

        Field field = healer.getOwner().getField();
        healCards(resolver, healer, cardSkill, HealType.Exact, field.getAliveCards());
    }
}

enum HealType {
    Percentage,
    Exact,
}