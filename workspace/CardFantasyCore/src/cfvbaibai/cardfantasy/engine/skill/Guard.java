package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnDamagedResult;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class Guard {
    public static int apply(Skill guardSkill, Skill attackSkill, SkillResolver resolver, EntityInfo attacker, CardInfo guardian,
            int damage) throws HeroDieSignal {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("Attacker cannot be null");
        }
        if (attackSkill != null && attackSkill.getType().containsTag(SkillTag.抗守护)) {
            return damage;
        }
        resolver.getStage().getUI().useSkill(guardian, attacker, guardSkill, true);
        if (damage > guardian.getOwner().getHP()) {
            damage = guardian.getOwner().getHP();
        }
        int sdamage = damage;
        if(guardSkill.getType()== SkillType.诲人不倦)
        {
            sdamage = damage*30/100;
        }
        else if(guardSkill.getType()== SkillType.心转之术)
        {
            sdamage = damage*10/100;
        }
        else if(guardSkill.getType()== SkillType.固守)
        {
            sdamage = damage*50/100;
        }
        //调整守护，但是不影响旧技能
        int impact = guardSkill.getImpact();
        if(guardSkill.getType() == SkillType.守护)
        {
            if(impact == 0)
            {
                sdamage = damage;
            }
            else{
                sdamage = damage*impact/100;
            }
        }
        int remainingDamage = 0;
        if (sdamage > guardian.getHP()) {
            remainingDamage = damage - guardian.getHP();
            damage = guardian.getHP();
        }
        else{
            remainingDamage = damage -sdamage;
            damage = sdamage;
        }
        resolver.getStage().getUI().attackCard(attacker, guardian, guardSkill, damage);
        OnDamagedResult result = resolver.applyDamage(attacker, guardian, guardSkill, damage);
        resolver.resolveDeathSkills(attacker, guardian, guardSkill, result);
        return remainingDamage;
    }
}
