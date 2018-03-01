package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class AlchemyFailure {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, Skill cardSkill, CardInfo card) throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(card, card, cardSkill, true);
        //处理炼金失败触发涅盘的问题，但是可能会导致魔族之血出问题。
//        if(card.containsUsableSkill(SkillType.不屈))
//        {
//            card.setBasicHP(0);
//           if(Unbending.apply(skillUseInfo, resolver, card))
//           {
//               return ;
//           }
//        };
        ui.attackCard(card, card, cardSkill, 99999);
        resolver.resolveDeathSkills(card, card, cardSkill,  resolver.applyDamage(card, card, cardSkill,99999));
    }
}
