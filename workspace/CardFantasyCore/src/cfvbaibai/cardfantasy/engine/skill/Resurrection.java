package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class Resurrection {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo resurrector) {
        if (resurrector == null) {
            throw new CardFantasyRuntimeException("resurrector should not be null");
        }
        Skill skill = skillUseInfo.getSkill();
        // Grave is a stack, find the last-in card and revive it.
        int resurrectionCount = skill.getImpact();
        Player player = resurrector.getOwner();
        List<CardInfo> exclusions = null;
        List<CardInfo> deadCards = player.getGrave().toList();
        if (skill.isDeathSkill()) {
            exclusions = new ArrayList<CardInfo>();
            exclusions.add(resurrector);
            if(resurrectionCount==deadCards.size())
            {
                resurrectionCount=resurrectionCount-1;
            }
        }
        GameUI ui = resolver.getStage().getUI();
        if (SoulSeal.soulSealed(resolver, resurrector)) {
            return;
        }
        for (int i=0;i<=deadCards.size()-1&&i<=resurrectionCount-1;i++) {
            if(deadCards.get(i)==resurrector)
            {
                resurrectionCount=resurrectionCount+1;
                continue;
            }
            ui.useSkill(resurrector, deadCards.get(i), skill, true);
            ui.cardToDeck(player, deadCards.get(i));
            player.getGrave().removeCard(deadCards.get(i));
            if (player.getDeck().size() > 0) {
       //     int position = Randomizer.getRandomizer().next(0, player.getDeck().size());
         // 回魂是有顺序的。
                int position = 0;
                player.getDeck().insertCardToPosition(deadCards.get(i), position);
            } else {
                player.getDeck().addCard(deadCards.get(i));
            }
        }
    }
}
