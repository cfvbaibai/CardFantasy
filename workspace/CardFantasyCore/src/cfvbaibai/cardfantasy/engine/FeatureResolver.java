package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.engine.feature.BlockFeature;
import cfvbaibai.cardfantasy.engine.feature.ChainLighteningFeature;
import cfvbaibai.cardfantasy.engine.feature.HolyLightFeature;
import cfvbaibai.cardfantasy.engine.feature.PenetrationFeature;
import cfvbaibai.cardfantasy.engine.feature.SnipeFeature;
import cfvbaibai.cardfantasy.engine.feature.TrapFeature;

public class FeatureResolver {
    private Board board;
    private StageInfo stage;

    public FeatureResolver(Board board, StageInfo stage) {
        this.board = board;
        this.stage = stage;
    }
    
    public StageInfo getStage() {
        return this.stage;
    }
    
    public void resolvePreAttackFeature(CardInfo attacker, Player defender) {
        for (Feature feature : attacker.getUsableFeatures()) {
            if (feature.getType() == FeatureType.ChainLightening) {
                ChainLighteningFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.Trap) {
                TrapFeature.apply(feature, this, attacker, defender);
            }
        }
    }
    
    public void resolvePostAttackFeature(CardInfo attacker, Player defender) {
        for (Feature feature : attacker.getUsableFeaturesOf(FeatureType.Snipe)) {
            SnipeFeature.apply(feature, this, attacker, defender);
        }
    }
    
    public void resolveCounterAttackFeature(CardInfo attacker, CardInfo defender, Feature feature) {
        
    }

    public OnDamagedResult applyDamage(CardInfo card, int damage) {
        int originalHP = card.getHP();
        card.setHP(card.getHP() - damage);
        OnDamagedResult result = new OnDamagedResult();
        if (card.getHP() <= 0) {
            result.cardDead = true;
            result.actualDamage = originalHP;
            cardDead(card);
        } else {
            result.actualDamage = damage;
        }
        return result;
    }
    
    public void cardDead(CardInfo deadCard) {
        this.stage.getUI().cardDead(deadCard);
        Player owner = deadCard.getOwner();
        Field field = owner.getField();
        // Set field position to null
        for (int i = 0; i < field.size(); ++i) {
            CardInfo card = field.getCard(i);
            if (deadCard == card) {
                field.expelCard(i);
                owner.getGrave().addCard(card);
                break;
            }
        }
    }

    public void attackHero(CardInfo attacker, Player defenderPlayer, Feature feature, int damage) throws HeroDieSignal {
        stage.getUI().attackHero(attacker, defenderPlayer, feature, damage);
        defenderPlayer.setLife(defenderPlayer.getLife() - damage);
    }
    
    public OnAttackBlockingResult resolveAttackBlockingFeature(CardInfo attacker, CardInfo defender, Feature feature) {
        OnAttackBlockingResult result = new OnAttackBlockingResult(true, feature == null ? attacker.getAT() : feature.getImpact());
        if (feature == null) {
            // Normal attack could be blocked by Dodge or PARALYZED, FROZEN, TRAPPED status.
            CardStatus status = attacker.getStatus();
            if (status.containsStatus(CardStatusType.FROZEN) ||
                    status.containsStatus(CardStatusType.PARALYZED) ||
                    status.containsStatus(CardStatusType.TRAPPED)) {
                stage.getUI().attackBlocked(attacker, defender, feature, null);
                result.attackable = false;
                result.damage = 0;
            } else {
                for (Feature blockFeature : defender.getUsableFeaturesOf(FeatureType.Block)) {
                    result.damage = BlockFeature.apply(blockFeature, this, attacker, defender, result.damage);
                }
            }
        } else {
            CardStatus status = attacker.getStatus();
            if (status.containsStatus(CardStatusType.FROZEN) || status.containsStatus(CardStatusType.TRAPPED)) {
                stage.getUI().attackBlocked(attacker, defender, feature, null);
                result.attackable = false;
                result.damage = 0;
            } else {
                for (Feature blockFeature : defender.getUsableFeaturesOf(FeatureType.MagicShield)) {
                    result.damage = BlockFeature.apply(blockFeature, this, attacker, defender, result.damage);
                }
            }
        }
        return result;
    }

    public void resolveDyingFeature(CardInfo attacker, CardInfo card, Feature feature) {
        // TODO Auto-generated method stub
        return;
    }

    public void resolveExtraAttackFeature(CardInfo attacker, CardInfo defender, Player defenderHero, int normalAttackDamage) throws HeroDieSignal {
        for (Feature feature : attacker.getUsableFeaturesOf(FeatureType.Penetration)) {
            PenetrationFeature.apply(feature, this, attacker, defenderHero, normalAttackDamage);
        }
    }

    public void resolvePreAttackCardFeature(CardInfo attacker, CardInfo defender) {
        for (Feature feature : attacker.getUsableFeaturesOf(FeatureType.HolyLight)) {
            HolyLightFeature.apply(feature, this, attacker, defender);
        }
    }

    public void removeEffects(CardInfo card, FeatureType ... causes) {
        if (card == null) {
            return;
        }
        for (FeatureType cause : causes) {
            card.removeEffectsCausedBy(cause);
        }
    }
}
