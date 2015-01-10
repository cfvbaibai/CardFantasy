package cfvbaibai.cardfantasy.game;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.data.TrivialSkill;

public class SkillBuilder {
    public static List<Skill> buildLegionBuffs(int kingdomBuffLevel, int forestBuffLevel, int savageBuffLevel, int hellBuffLevel) {
        List<Skill> result = new ArrayList<Skill>();
        result.add(new TrivialSkill(SkillType.军团王国之力, kingdomBuffLevel));
        result.add(new TrivialSkill(SkillType.军团森林之力, forestBuffLevel));
        result.add(new TrivialSkill(SkillType.军团蛮荒之力, savageBuffLevel));
        result.add(new TrivialSkill(SkillType.军团地狱之力, hellBuffLevel));
        return result;
    }
}
