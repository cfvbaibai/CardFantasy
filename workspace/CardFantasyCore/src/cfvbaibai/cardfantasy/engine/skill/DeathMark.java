package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.OnDamagedResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class DeathMark {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo caster, Player defenderHero) throws HeroDieSignal {
        CardInfo victim = defenderHero.getField().getCard(caster.getPosition());
        if (victim == null) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(caster, victim, skill, true);
        CardStatusItem statusItem = CardStatusItem.deathMark(skillUseInfo);
        if (!resolver.resolveAttackBlockingSkills(caster, victim, skill, 1).isAttackable()) {
            return;
        }
        ui.addCardStatus(caster, victim, skill, statusItem);
        victim.addStatus(statusItem);
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
