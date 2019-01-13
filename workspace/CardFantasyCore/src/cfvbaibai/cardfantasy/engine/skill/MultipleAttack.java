package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public final class MultipleAttack {
    public static void apply(SkillResolver resolver, SkillUseInfo attackSkillUseInfo, CardInfo attacker, Player defender, Skill attackSkill, Boolean firstSkill)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        if (attackSkill != null) {
            return;
        }
        if (attacker.isDead()) {
            return;
        }
        if (!firstSkill) {
            return;
        }
        Skill skill = attackSkillUseInfo.getSkill();
        int impact2 = skill.getImpact2();
        int damage = attacker.getCurrentAT();
        GameUI ui = resolver.getStage().getUI();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        for (int count = 0; count < impact2; count++) {
            if (attacker.isDead()) {
                break;
            }
            List<CardInfo> cardList = defender.getField().getAliveCards();
            Field opField = defender.getField();
            if (cardList.size() > 0) {
                List<CardInfo> victims = random.pickRandom(cardList, 1, true, null);
                for (CardInfo card : victims) {
                    //   ui.useSkill(attacker,card,skill,true);
                    resolver.attackCard(attacker, card, attackSkillUseInfo, damage, false);
                    if (attacker.isDead()) {
                        break;
                    }
                    for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
                        if (skillUseInfo.getType() == SkillType.横扫 ||
                                skillUseInfo.getType() == SkillType.地裂劲 ||
                                skillUseInfo.getType() == SkillType.秘纹领域 ||
                                skillUseInfo.getType() == SkillType.三千世界 ||
                                skillUseInfo.getType() == SkillType.大小通吃 ||
                                skillUseInfo.getType() == SkillType.魔龙之怒 ||
                                skillUseInfo.getType() == SkillType.兽人之血 ||
                                skillUseInfo.getType() == SkillType.英勇打击 ||
                                skillUseInfo.getType() == SkillType.鬼彻 ||
                                skillUseInfo.getType() == SkillType.灵击 ||
                                skillUseInfo.getType() == SkillType.死亡践踏 ||
                                skillUseInfo.getType() == SkillType.毒杀) {
                            List<CardInfo> sweepDefenders = new ArrayList<CardInfo>();
                            int i = attacker.getPosition();
                            if (i > 0 && opField.getCard(i - 1) != null) {
                                sweepDefenders.add(opField.getCard(i - 1));
                            }
                            if (opField.getCard(i + 1) != null) {
                                sweepDefenders.add(opField.getCard(i + 1));
                            }

                            for (CardInfo sweepDefender : sweepDefenders) {
                                if (!sweepDefender.isAlive()) {
                                    continue;
                                }
                                ui.useSkill(attacker, sweepDefender, skillUseInfo.getSkill(), true);
                                resolver.attackCard(attacker, sweepDefender, skillUseInfo, damage, false);
                                // Physical attack cannot proceed if attacker is killed by counter attack skills.
                                if (attacker.isDead()) {
                                    break;
                                }
                            }
                            break;
                        }
                        else if (skillUseInfo.getType() == SkillType.一文字 || skillUseInfo.getType() == SkillType.页游横扫千军
                                || skillUseInfo.getType() == SkillType.横扫千军 || skillUseInfo.getType() == SkillType.纷乱雪月花
                                || skillUseInfo.getType() == SkillType.醉生梦死) {
                            for (CardInfo sweepDefender : opField.getAliveCards()) {
                                if(!sweepDefender.isAlive())
                                {
                                    continue;
                                }
                                ui.useSkill(attacker, sweepDefender, skillUseInfo.getSkill(), true);
                                resolver.attackCard(attacker, sweepDefender, skillUseInfo, damage,false);
                                if (attacker.isDead()) {
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                // ui.useSkill(attacker,defender,skill,true);
                resolver.attackHero(attacker, defender, skill, damage);
            }

        }

    }

    public static void applyMultiple(SkillResolver resolver, SkillUseInfo attackSkillUseInfo, CardInfo attacker, Player defender, Skill attackSkill)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        if (attackSkill != null) {
            return;
        }
        if (attacker.isDead()) {
            return;
        }
        Skill skill = attackSkillUseInfo.getSkill();
        int damage = attacker.getCurrentAT();
        GameUI ui = resolver.getStage().getUI();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        Field opField = defender.getField();
        int rate = skill.getImpact();
        if (random.roll100(rate)) {
            List<CardInfo> cardList = defender.getField().getAliveCards();
            if (cardList.size() > 0) {
                List<CardInfo> victims = random.pickRandom(cardList, 1, true, null);
                for (CardInfo card : victims) {
                    //   ui.useSkill(attacker,card,skill,true);
                    resolver.attackCard(attacker, card, attackSkillUseInfo, damage, false);

                    if (attacker.isDead()) {
                        break;
                    }
                    for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
                        if (skillUseInfo.getType() == SkillType.横扫 ||
                                skillUseInfo.getType() == SkillType.地裂劲 ||
                                skillUseInfo.getType() == SkillType.秘纹领域 ||
                                skillUseInfo.getType() == SkillType.三千世界 ||
                                skillUseInfo.getType() == SkillType.大小通吃 ||
                                skillUseInfo.getType() == SkillType.魔龙之怒 ||
                                skillUseInfo.getType() == SkillType.兽人之血 ||
                                skillUseInfo.getType() == SkillType.英勇打击 ||
                                skillUseInfo.getType() == SkillType.鬼彻 ||
                                skillUseInfo.getType() == SkillType.灵击 ||
                                skillUseInfo.getType() == SkillType.死亡践踏 ||
                                skillUseInfo.getType() == SkillType.毒杀) {
                            List<CardInfo> sweepDefenders = new ArrayList<CardInfo>();
                            int i = attacker.getPosition();
                            if (i > 0 && opField.getCard(i - 1) != null) {
                                sweepDefenders.add(opField.getCard(i - 1));
                            }
                            if (opField.getCard(i + 1) != null) {
                                sweepDefenders.add(opField.getCard(i + 1));
                            }

                            for (CardInfo sweepDefender : sweepDefenders) {
                                if (!sweepDefender.isAlive()) {
                                    continue;
                                }
                                ui.useSkill(attacker, sweepDefender, skillUseInfo.getSkill(), true);
                                resolver.attackCard(attacker, sweepDefender, skillUseInfo, damage, false);
                                // Physical attack cannot proceed if attacker is killed by counter attack skills.
                                if (attacker.isDead()) {
                                    break;
                                }
                            }
                            break;
                        }
                        else if (skillUseInfo.getType() == SkillType.一文字 || skillUseInfo.getType() == SkillType.页游横扫千军
                                || skillUseInfo.getType() == SkillType.横扫千军 || skillUseInfo.getType() == SkillType.纷乱雪月花
                                || skillUseInfo.getType() == SkillType.醉生梦死) {
                            for (CardInfo sweepDefender : opField.getAliveCards()) {
                                if(!sweepDefender.isAlive())
                                {
                                    continue;
                                }
                                ui.useSkill(attacker, sweepDefender, skillUseInfo.getSkill(), true);
                                resolver.attackCard(attacker, sweepDefender, skillUseInfo, damage,false);
                                if (attacker.isDead()) {
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                // ui.useSkill(attacker,defender,skill,true);
                resolver.attackHero(attacker, defender, skill, damage);
            }
        }
    }

    //只攻击一下，并且有判定卡牌
    public static void applyFirst(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, Player defender, Skill attackSkill, Boolean firstSkill)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        if (attackSkill != null) {
            return;
        }
        if (attacker.isDead()) {
            return;
        }
        if (!firstSkill) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int damage = attacker.getCurrentAT();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = defender.getField().getCardsWithLowestHP(1);
        if (victims.size() > 0) {
            for (CardInfo card : victims) {
                ui.useSkill(attacker, card, skill, true);
                resolver.attackCard(attacker, card, skillUseInfo, damage, false);
            }
        } else {
            ui.useSkill(attacker, defender, skill, true);
            resolver.attackHero(attacker, defender, skill, damage);
        }

    }
}
