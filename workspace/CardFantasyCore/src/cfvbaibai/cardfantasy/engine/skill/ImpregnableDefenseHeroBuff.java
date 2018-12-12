package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

public final class ImpregnableDefenseHeroBuff {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        Player atacter = card.getOwner();
        int coefficient =atacter.getCoefficient()*impact/100;
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(card, card, skill,true);
        atacter.setCoefficient(coefficient);
    }
    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        Player atacter = card.getOwner();
        int coefficient=100;

        if(impact==0)
        {
             coefficient =100;//暂时hack一下铁壁10

        }
        else{
            coefficient = atacter.getCoefficient()*100/impact;
        }
        if(coefficient>100)
        {
            coefficient=100;//hack一下某些情况下铁壁非正常移除。
        }
        atacter.setCoefficient(coefficient);
    }
    public static void removeSkill(CardInfo card, SkillResolver resolver) {
        if (card.containsAllSkill(SkillType.铁壁)||card.containsAllSkill(SkillType.驱虎吞狼)||card.containsAllSkill(SkillType.金汤)
                ||card.containsAllSkill(SkillType.铁壁方阵) ||card.containsAllSkill(SkillType.光之守护)||card.containsAllSkill(SkillType.聚能立场)
                ||card.containsAllSkill(SkillType.魔神加护) || card.containsAllSkill(SkillType.护主)|| card.containsAllSkill(SkillType.据守)
                ||card.containsAllSkill(SkillType.龙之守护) || card.containsAllSkill(SkillType.不动如山)) {
            for (SkillUseInfo defenderskill : card.getAllNormalSkills()) {
                if (defenderskill.getType() == SkillType.铁壁||defenderskill.getType() == SkillType.金汤
                        ||defenderskill.getType() == SkillType.光之守护||defenderskill.getType() == SkillType.铁壁方阵
                        ||defenderskill.getType() == SkillType.聚能立场|| defenderskill.getType() == SkillType.护主
                        ||defenderskill.getType() == SkillType.龙之守护|| defenderskill.getType() == SkillType.不动如山) {
                    ImpregnableDefenseHeroBuff.remove(resolver, defenderskill, card);
                }
                else if (defenderskill.getType() == SkillType.驱虎吞狼 || defenderskill.getType() == SkillType.魔神加护 || defenderskill.getType() == SkillType.据守)
                {
                    ImpregnableDefenseHeroBuff.remove(resolver, defenderskill.getAttachedUseInfo2(), card);
                }
            }
        }
    }
}
