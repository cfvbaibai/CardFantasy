package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Polymorph {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attackCard, Player defenderHero,int victimCount,int effectNumber) throws HeroDieSignal {

        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> victims = random.pickRandom(defenderHero.getField().toList(), victimCount, true, null);
        if (victims.size() == 0) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(attackCard, victims, skill, true);
        CardStatusItem statusItem = CardStatusItem.sheep(skillUseInfo);
        CardStatusItem statusItemSlience = CardStatusItem.slience(skillUseInfo);
        statusItem.setEffectNumber(effectNumber);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingSkills(attackCard, victim, skill, 1).isAttackable()) {
                continue;
            }
            if(effectNumber>0)
            {
                if(!victim.getStatus().getStatusOf(CardStatusType.变羊).isEmpty()){
                    continue;
                }
            }
            ui.addCardStatus(attackCard, victim, skill, statusItem);
            victim.addStatus(statusItem);
            ui.addCardStatus(attackCard, victim, skill, statusItemSlience);
            victim.addStatus(statusItemSlience);
            int impactAdd = victim.getInitAT()-skill.getImpact();
            resolver.getStage().getUI().adjustAT(attackCard, victim, -impactAdd, skill);
            victim.addCoefficientEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, -impactAdd, false));
            int impactHpAdd = victim.getHP()-skill.getImpact2();
            resolver.getStage().getUI().adjustHP(attackCard, victim, -impactHpAdd, skill);
            victim.addCoefficientEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, -impactHpAdd, false));
        }
    }

    public static void explode(SkillResolver resolver, CardInfo deadCard, OnDamagedResult onDamagedResult) throws HeroDieSignal {
        if (!onDamagedResult.cardDead) {
            // Card could be unbending and not dead. In that case, death mark does not explode.
            return;
        }
        int position = deadCard.getPosition();
        List<CardInfo> adjacentCards = resolver.getAdjacentCards(deadCard.getOwner().getField(), position);
        List<CardInfo> victims = new ArrayList<CardInfo>();
        for (CardInfo card : adjacentCards) {
            if (card == null || card.isDead()) {
                continue;
            }
            victims.add(card);
        }
        GameUI ui = resolver.getStage().getUI();
        List<CardStatusItem> statusItems = deadCard.getStatus().getStatusOf(CardStatusType.死印);
        for (CardStatusItem statusItem : statusItems) {
            SkillUseInfo skillUseInfo = statusItem.getCause();
            Skill skill = skillUseInfo.getSkill();
            EntityInfo killer = skillUseInfo.getOwner();
            ui.useSkill(deadCard, victims, skill, true);
            int damage = skill.getImpact();
            for (CardInfo victim : victims) {
                OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(killer, victim, skill, damage);
                if (!result.isAttackable()) {
                    continue;
                }
                damage = result.getDamage();
                ui.attackCard(killer, victim, skill, damage);
                resolver.resolveDeathSkills(killer, victim, skill, resolver.applyDamage(deadCard, victim, skill, damage));
            }
        }
    }
}
