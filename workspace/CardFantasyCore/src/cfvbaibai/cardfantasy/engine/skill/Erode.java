package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class Erode {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Player defender, Skill summonSkill,Boolean onceSkill) throws HeroDieSignal {
        if (card.hasUsed(skillUseInfo)) {
            return;
        }
        if(card.isSilent())
        {
            return;
        }
        if(onceSkill) {
            card.setUsed(skillUseInfo);
        }
        Skill skill = skillUseInfo.getSkill();
        GameUI ui = resolver.getStage().getUI();
        Randomizer random = resolver.getStage().getRandomizer();

        List<CardInfo> selectCard =new ArrayList<>();
        for(CardInfo fieldCard:defender.getField().getAliveCards())
        {
            if(fieldCard!=null)
            {
                selectCard.add(fieldCard);
            }
        }
        for(CardInfo beforeCard:defender.getBeforeDeath().toList())
        {
            if(beforeCard!=null)
            {
                selectCard.add(beforeCard);
            }
        }

        List<CardInfo> candidates = random.pickRandom(selectCard, 1, true, null);

        ui.useSkill(card, candidates, skill, true);
        if (candidates.isEmpty()) {
            return;
        }
        CardInfo oblation = candidates.get(0);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(card, oblation, skill, 1);
        if (!result.isAttackable()) {
            return;
        }
        int magicEchoSkillResult = resolver.resolveMagicEchoSkill(card, oblation, skill);
        if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
            if(card.isDead())
            {
                if (magicEchoSkillResult == 1) {
                    return;
                }
            }
            else{
                OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(oblation, card, skill, 1);
                if (!result2.isAttackable()) {
                    if (magicEchoSkillResult == 1) {
                        return;
                    }
                }
                else{
                    int adjHP = skill.getImpact() *oblation .getMaxHP() / 100;
                    int adjAT = skill.getImpact() * oblation.getLevel0AT() / 100;//修改为原始攻击力加成
                    ui.adjustHP(oblation, oblation, adjHP, skill);
                    ui.adjustAT(oblation, oblation, adjAT, skill);
                    oblation.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, adjHP, true));
                    oblation.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));

                    ui.killCard(oblation, card, skill);
                    resolver.killCard(oblation, card, skill);
                }
            }
            if (magicEchoSkillResult == 1) {
                return;
            }
        }
        int adjHP = skill.getImpact() * card.getMaxHP() / 100;
        int adjAT = skill.getImpact() * card.getLevel0AT() / 100;//修改为原始攻击力加成
        ui.adjustHP(card, card, adjHP, skill);
        ui.adjustAT(card, card, adjAT, skill);
        card.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, adjHP, true));
        card.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));

        if(defender.getBeforeDeath().contains(oblation))
        {
           return;
        }
        ui.killCard(card, oblation, skill);
        resolver.killCard(card, oblation, skill);
    }
}
