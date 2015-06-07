package cfvbaibai.cardfantasy.web.animation;

import java.util.ArrayList;
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
import cfvbaibai.cardfantasy.game.PveGameResult;

public class StructuredRecordGameUI extends GameUI {

    private static String toName(Skill skill) {
        if (skill == null) {
            return "普通攻击";
        } else {
            return skill.getType().name();
        }
    }

    private BattleRecord record;
    public StructuredRecordGameUI() {
        this.record = new BattleRecord();
    }
    
    public BattleRecord getRecord() {
        return this.record;
    }
    
    private PlayerRuntimeInfo toPlayer(Player healee) {
        return new PlayerRuntimeInfo(healee, getPlayerNumber(healee));
    }

    @Override
    public void stageCreated() {
        record.addEvent("stageCreated");
    }
    
    @Override
    public void gameStarted() {
        record.addEvent("gameStarted");
    }

    @Override
    public void playerAdded(Player player, int playerNumber) {
        PlayerInitInfo pii = new PlayerInitInfo(player, playerNumber);
        record.addEvent("playerAdded", pii);
    }

    @Override
    public void errorHappened(CardFantasyRuntimeException e) {
        record.addEvent("errorHappened", e.getMessage());
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
        PlayerRuntimeInfo winner = toPlayer(result.getWinner());
        PlayerRuntimeInfo loser = toPlayer(result.getLoser());
        record.addEvent("gameEnded", winner, loser, result.getCause(), result.getDamageToBoss());
    }

    @Override
    public void cardDrawed(Player drawer, CardInfo card) {
        record.addEvent("cardDrawed", drawer.getId(), card.getId(), card.getUniqueName(), card.getSummonDelay());
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
        this.record.addEvent("summonCard", player.getId(), new CardInitInfo(card));
    }

    @Override
    public void roundStarted(Player player, int round) {
        this.record.addEvent("roundStarted", round, player.getId());
    }

    @Override
    public void roundEnded(Player player, int round) {
        this.record.addEvent("roundEnded", round, player.getId());
    }

    @Override
    public void attackCard(EntityInfo attacker, CardInfo defender, Skill cardSkill, int damage) {
        int currentHP = defender.getHP() - damage;
        if (currentHP < 0) { currentHP = 0; }
        String skillName = toName(cardSkill);
        this.record.addEvent("attackCard", new EntityRuntimeInfo(attacker), new EntityRuntimeInfo(defender),
                skillName, damage, currentHP, defender.getMaxHP()); 
    }

    @Override
    public void cardDead(CardInfo deadCard) {
        this.record.addEvent("cardDead", deadCard.getOwner().getId(), new CardRuntimeInfo(deadCard));
    }

    @Override
    public void attackHero(EntityInfo attacker, Player hero, Skill cardSkill, int damage) {
        this.record.addEvent("attackHero", new EntityRuntimeInfo(attacker),
                toPlayer(hero), damage, toName(cardSkill));
    }

    @Override
    public void protect(EntityInfo protector, EntityInfo attacker, EntityInfo protectee, Skill attackSkill,
            Skill protectSkill) {
        this.record.addEvent("protect", new EntityRuntimeInfo(protector),
                new EntityRuntimeInfo(attacker), new EntityRuntimeInfo(protectee),
                toName(attackSkill), toName(protectSkill));
    }

    @Override
    public void addCardStatus(EntityInfo attacker, CardInfo victim, Skill cardSkill, CardStatusItem item) {
        this.record.addEvent("addCardStatus", new EntityRuntimeInfo(attacker), new EntityRuntimeInfo(victim),
                toName(cardSkill), item.getType().name(), item.getType().getAbbrev());
    }
    
    @Override
    public void removeCardStatus(CardInfo card, CardStatusType type) {
        this.record.addEvent("removeCardStatus", new EntityRuntimeInfo(card), type.getAbbrev());
    }

    @Override
    public void battleBegins() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void attackBlocked(EntityInfo attacker, CardInfo defender, Skill atSkill, Skill dfSkill) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void adjustAT(EntityInfo source, CardInfo target, int adjAT, Skill cardSkill) {
        this.record.addEvent("adjustAT", new EntityRuntimeInfo(source), new EntityRuntimeInfo(target),
                adjAT, target.getCurrentAT() + adjAT, cardSkill.getType().name());
    }

    public void adjustHP(EntityInfo source, List<? extends CardInfo> targets, int adjHP, Skill cardSkill) {
        for (CardInfo target : targets) {
            this.record.addEvent("adjustHP", new EntityRuntimeInfo(source), new EntityRuntimeInfo(target),
                adjHP, target.getHP() + adjHP, cardSkill.getType().name(), target.getMaxHP() + adjHP);
        }
    }

    @Override
    public void blockDamage(EntityInfo protector, EntityInfo attacker, EntityInfo defender, Skill cardSkill,
            int originalDamage, int actualDamage) {
        this.record.addEvent("blockDamage", new EntityRuntimeInfo(protector), new EntityRuntimeInfo(attacker),
                new EntityRuntimeInfo(defender), toName(cardSkill), originalDamage, actualDamage);
    }

    @Override
    public void healBlocked(EntityInfo healer, CardInfo healee, Skill cardSkill, Skill blockerSkill) {
        this.record.addEvent("healBlocked", new EntityRuntimeInfo(healer), new EntityRuntimeInfo(healee),
                toName(cardSkill), toName(blockerSkill));
    }

    @Override
    public void debuffDamage(CardInfo card, CardStatusItem item, int effect) {
        int currentHP = card.getHP() - effect;
        if (currentHP < 0) { currentHP = 0; }
        this.record.addEvent("debuffDamage", new EntityRuntimeInfo(card),
                item.getType().name(), effect, currentHP, card.getMaxHP());
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
    public void healCard(EntityInfo healer, CardInfo healee, Skill cardSkill, int healHP) {
        if (healee.getHP() == healee.getMaxHP()) {
            return;
        }
        int currentHP = healee.getHP() + healHP;
        if (currentHP > healee.getMaxHP()) {
            currentHP = healee.getMaxHP();
        }
        this.record.addEvent("healCard", new EntityRuntimeInfo(healer), new EntityRuntimeInfo(healee),
                toName(cardSkill), healHP, currentHP, healee.getMaxHP()); 
    }

    @Override
    public void healHero(EntityInfo healer, Player healee, Skill cardSkill, int healHP) {
        int currentHP = healee.getHP() + healHP;
        if (currentHP > healee.getMaxHP()) {
            currentHP = healee.getMaxHP();
        }
        this.record.addEvent("healHero", new EntityRuntimeInfo(healer), toPlayer(healee),
                toName(cardSkill), healHP, currentHP); 
    }

    @Override
    public void loseAdjustATEffect(CardInfo card, SkillEffect effect) {
        this.record.addEvent("lostAdjAT", new EntityRuntimeInfo(card), new EntityRuntimeInfo(card),
                effect.getValue(), card.getCurrentAT() - effect.getValue(), effect.getCause().getType().name());
    }

    @Override
    public void loseAdjustHPEffect(CardInfo card, SkillEffect effect) {
        int currentHP = card.getHP() > card.getMaxHP() - effect.getValue() ?
                card.getMaxHP() - effect.getValue() : card.getHP();
        this.record.addEvent("lostAdjHP", new EntityRuntimeInfo(card), new EntityRuntimeInfo(card),
                card.getHP() - currentHP, currentHP, effect.getCause().getType().name(), card.getMaxHP() - effect.getValue());
    }

    @Override
    public void cardToDeck(Player player, CardInfo card) {
        this.record.addEvent("cardToDeck", player.getId(), new EntityRuntimeInfo(card));
    }

    @Override
    public void cardToHand(Player player, CardInfo card) {
        this.record.addEvent("cardToHand", toPlayer(player),
                new EntityRuntimeInfo(card), card.getId(), card.getSummonSpeed());
    }
    
    @Override
    public void cardToOutField(Player player, CardInfo card) {
        this.record.addEvent("cardToOutField", toPlayer(player),
                new EntityRuntimeInfo(card), card.getId());
    }

    @Override
    public void blockStatus(EntityInfo attacker, EntityInfo defender, Skill cardSkill, CardStatusItem item) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void blockSkill(EntityInfo attacker, EntityInfo defender, Skill cardSkill, Skill attackSkill) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void returnCard(CardInfo attacker, CardInfo defender, Skill cardSkill) {
        this.record.addEvent("returnCard", new EntityRuntimeInfo(attacker), new EntityRuntimeInfo(defender));
    }

    @Override
    public void cardToGrave(Player player, CardInfo card) {
        this.record.addEvent("cardToGrave", player.getId(), new EntityRuntimeInfo(card));
    }

    @Override
    public void disableBlock(CardInfo attacker, CardInfo defender, Skill attackSkill, Skill blockSkill) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void confused(CardInfo card) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void softened(CardInfo card) {
        // TODO
    }

    @Override
    public void roll100(int dice, int rate) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void useSkill(EntityInfo caster, Skill skill, boolean bingo) {
        if (!bingo) { return; }
        List<EntityRuntimeInfo> defenders = new ArrayList<EntityRuntimeInfo>();
        defenders.add(new EntityRuntimeInfo(caster));
        this.record.addEvent("useSkill", new EntityRuntimeInfo(caster), toName(skill), defenders);
    }

    @Override
    public void useSkill(EntityInfo caster, List<? extends EntityInfo> targets, Skill skill, boolean bingo) {
        if (!bingo) { return; }
        String skillName = toName(skill);
        BattleEvent event = new BattleEvent("useSkill", new EntityRuntimeInfo(caster), skillName);
        EntityRuntimeInfo[] defenders = new EntityRuntimeInfo[targets.size()]; 
        for (int i = 0; i < targets.size(); ++i) {
            EntityInfo entityInfo = targets.get(i);
            defenders[i] = new EntityRuntimeInfo(entityInfo);
        }
        event.addData(defenders);
        this.record.addEvent(event);
    }

    @Override
    public void useSkillToHero(EntityInfo caster, Player targetHero, Skill skill) {
        this.record.addEvent("useSkillToHero", new EntityRuntimeInfo(caster), toName(skill), toPlayer(targetHero));
    }

    @Override
    public void killCard(EntityInfo attacker, CardInfo victim, Skill cardSkill) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void activateRune(RuneInfo rune) {
        boolean isFirstActivation = rune.getEnergy() == rune.getMaxEnergy();
        RuneInitInfo rii = new RuneInitInfo(rune);
        rii.setEnergy(rii.getEnergy() - 1);
        this.record.addEvent("activateRune", toPlayer(rune.getOwner()), rii, isFirstActivation);
    }

    @Override
    public void deactivateRune(RuneInfo rune) {
        boolean isFinalDeactivation = rune.getEnergy() == 0;
        RuneInitInfo rii = new RuneInitInfo(rune);
        rii.setEnergy(rii.getEnergy() - 1);
        this.record.addEvent("deactivateRune", toPlayer(rune.getOwner()), rii, isFinalDeactivation);
    }
    
    @Override
    public void updateRuneEnergy(RuneInfo rune) {
        RuneInitInfo rii = new RuneInitInfo(rune);
        this.record.addEvent("updateRuneEnergy", toPlayer(rune.getOwner()), rii);
    }

    @Override
    public void compactField(Field field) {
        this.record.addEvent("compactField", field.getOwner().getId());
    }

    @Override
    public void showMessage(String string) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void cardActionBegins(CardInfo card) {
        this.record.addEvent("cardActionBegins", new EntityRuntimeInfo(card));
    }

    @Override
    public void cardActionEnds(CardInfo card) {
        this.record.addEvent("cardActionEnds", new EntityRuntimeInfo(card));
    }

    @Override
    public void mapStageResult(PveGameResult result) {
        this.record.addEvent("mapBattleResult", result.getDescription());
    }

    @Override
    public void increaseSummonDelay(CardInfo card, int offset) {
        this.record.addEvent("increaseSummonDelay", new EntityRuntimeInfo(card), offset);
    }

    @Override
    public void unbend(CardInfo card, CardStatusItem statusItem) {
        this.useSkill(card, statusItem.getCause().getSkill(), true);
    }
}
