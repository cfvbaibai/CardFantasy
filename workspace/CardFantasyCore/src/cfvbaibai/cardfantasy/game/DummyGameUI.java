package cfvbaibai.cardfantasy.game;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Phase;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class DummyGameUI extends GameUI {

    @Override
    public void stageCreated() {
        
    }
    
    @Override
    public void gameStarted() {
        // TODO Auto-generated method stub

    }

    @Override
    public void playerAdded(Player player, int playerNumber) {
        // TODO Auto-generated method stub

    }

    @Override
    public void errorHappened(CardFantasyRuntimeException e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void phaseChanged(Player player, Phase previous, Phase current) {
        // TODO Auto-generated method stub

    }

    @Override
    public void playerChanged(Player previousPlayer, Player nextPlayer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void gameEnded(GameResult result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void cardDrawed(Player drawer, CardInfo card) {
        // TODO Auto-generated method stub

    }

    @Override
    public void cantDrawDeckEmpty(Player drawer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void cantDrawHandFull(Player drawer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void summonCard(Player player, CardInfo card) {
        // TODO Auto-generated method stub

    }

    @Override
    public void roundStarted(Player player, int round) {
        // TODO Auto-generated method stub

    }

    @Override
    public void roundEnded(Player player, int round) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attackCard(EntityInfo attacker, CardInfo defender, Skill cardFeature, int damage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void cardDead(CardInfo deadCard) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attackHero(EntityInfo attacker, Player hero, Skill cardFeature, int damage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void useSkill(EntityInfo caster, List<? extends EntityInfo> targets, Skill skill, boolean bingo) {
        // TODO Auto-generated method stub

    }

    @Override
    public void protect(EntityInfo protector, EntityInfo attacker, EntityInfo protectee, Skill attackFeature,
            Skill protectFeature) {
        // TODO Auto-generated method stub

    }

    @Override
    public void useSkillToHero(EntityInfo caster, Player targetHero, Skill skill) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addCardStatus(EntityInfo attacker, CardInfo victim, Skill cardFeature, CardStatusItem item) {
        // TODO Auto-generated method stub

    }

    @Override
    public void battleBegins() {
        // TODO Auto-generated method stub

    }

    @Override
    public void attackBlocked(EntityInfo attacker, CardInfo defender, Skill atFeature, Skill dfFeature) {
        // TODO Auto-generated method stub

    }

    @Override
    public void adjustAT(EntityInfo source, CardInfo target, int adjAT, Skill cardFeature) {
        // TODO Auto-generated method stub

    }

    @Override
    public void adjustHP(EntityInfo source, List<? extends CardInfo> targets, int adjHP, Skill cardFeature) {
        // TODO Auto-generated method stub

    }

    @Override
    public void blockDamage(EntityInfo protector, EntityInfo attacker, EntityInfo defender, Skill cardFeature,
            int originalDamage, int actualDamage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void healBlocked(EntityInfo healer, CardInfo healee, Skill cardFeature, Skill blockerFeature) {
        // TODO Auto-generated method stub

    }

    @Override
    public void debuffDamage(CardInfo card, CardStatusItem item, int effect) {
        // TODO Auto-generated method stub

    }

    @Override
    public void cannotAction(CardInfo card) {
        // TODO Auto-generated method stub

    }

    @Override
    public void recoverAT(CardInfo card, SkillType cause, int recoveredAT) {
        // TODO Auto-generated method stub

    }

    @Override
    public void healCard(EntityInfo healer, CardInfo healee, Skill cardFeature, int healHP) {
        // TODO Auto-generated method stub

    }

    @Override
    public void healHero(EntityInfo healer, Player healee, Skill cardFeature, int healHP) {
        // TODO Auto-generated method stub

    }

    @Override
    public void loseAdjustATEffect(CardInfo ally, SkillEffect effect) {
        // TODO Auto-generated method stub

    }

    @Override
    public void loseAdjustHPEffect(CardInfo ally, SkillEffect effect) {
        // TODO Auto-generated method stub

    }

    @Override
    public void cardToDeck(Player player, CardInfo card) {
        // TODO Auto-generated method stub

    }

    @Override
    public void cardToHand(Player player, CardInfo card) {
        // TODO Auto-generated method stub

    }

    @Override
    public void blockStatus(EntityInfo attacker, EntityInfo defender, Skill cardFeature, CardStatusItem item) {
        // TODO Auto-generated method stub

    }

    @Override
    public void blockFeature(EntityInfo attacker, EntityInfo defender, Skill cardFeature, Skill attackFeature) {
        // TODO Auto-generated method stub

    }

    @Override
    public void returnCard(CardInfo attacker, CardInfo defender, Skill cardFeature) {
        // TODO Auto-generated method stub

    }

    @Override
    public void cardToGrave(Player player, CardInfo card) {
        // TODO Auto-generated method stub

    }

    @Override
    public void disableBlock(CardInfo attacker, CardInfo defender, Skill attackFeature, Skill blockFeature) {
        // TODO Auto-generated method stub

    }

    @Override
    public void confused(CardInfo card) {
        // TODO Auto-generated method stub

    }

    @Override
    public void roll100(int dice, int rate) {
        // TODO Auto-generated method stub

    }

    @Override
    public void useSkill(EntityInfo caster, Skill skill, boolean bingo) {
        // TODO Auto-generated method stub

    }

    @Override
    public void killCard(EntityInfo attacker, CardInfo victim, Skill cardFeature) {
        // TODO Auto-generated method stub

    }

    @Override
    public void activateRune(RuneInfo rune) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deactivateRune(RuneInfo rune) {
        // TODO Auto-generated method stub

    }

    @Override
    public void compactField(Field field) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showMessage(String string) {
        // TODO Auto-generated method stub

    }

    @Override
    public void cardActionBegins(CardInfo card) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void cardActionEnds(CardInfo card) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void cardToOutField(Player player, CardInfo card) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mapStageResult(PveGameResult result) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeCardStatus(CardInfo card, CardStatusType type) {
        // TODO Auto-generated method stub
        
    }

	@Override
	public void increaseSummonDelay(CardInfo card, int offset) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void softened(CardInfo card) {
        // TODO Auto-generated method stub
        
    }

}
