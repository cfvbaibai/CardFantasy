package cfvbaibai.cardfantasy.engine;

import java.util.List;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.engine.feature.BlockFeature;
import cfvbaibai.cardfantasy.engine.feature.ChainLighteningFeature;
import cfvbaibai.cardfantasy.engine.feature.CounterAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.CriticalAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.FireballFeature;
import cfvbaibai.cardfantasy.engine.feature.GuardFeature;
import cfvbaibai.cardfantasy.engine.feature.HolyLightFeature;
import cfvbaibai.cardfantasy.engine.feature.IceBoltFeature;
import cfvbaibai.cardfantasy.engine.feature.PenetrationFeature;
import cfvbaibai.cardfantasy.engine.feature.RejuvenateFeature;
import cfvbaibai.cardfantasy.engine.feature.SnipeFeature;
import cfvbaibai.cardfantasy.engine.feature.TrapFeature;
import cfvbaibai.cardfantasy.engine.feature.WeakenFeature;
import cfvbaibai.cardfantasy.engine.feature.ZealotFeature;

public class FeatureResolver {
    private StageInfo stage;

    public FeatureResolver(StageInfo stage) {
        this.stage = stage;
    }

    public StageInfo getStage() {
        return this.stage;
    }

    public void resolvePreAttackFeature(CardInfo attacker, Player defender) {
        for (Feature feature : attacker.getUsableFeatures()) {
            if (feature.getType() == FeatureType.Á¬»·ÉÁµç) {
                ChainLighteningFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.ÏÝÚå) {
                TrapFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.¾Ñ»÷) {
                SnipeFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.»ðÇò) {
                FireballFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.±ùµ¯) {
                IceBoltFeature.apply(feature, this, attacker, defender);
            }
        }
    }

    public void resolvePostAttackFeature(CardInfo attacker, Player defender) {

    }

    public void resolveCounterAttackFeature(CardInfo attacker, CardInfo defender, Feature attackFeature) {
        if (attackFeature == null) {
            for (Feature feature : defender.getUsableFeatures()) {
                if (feature.getType() == FeatureType.·´»÷) {
                    CounterAttackFeature.apply(feature, this, attacker, defender);
                }
            }
        }
    }

    public OnAttackBlockingResult resolveAttackBlockingFeature(CardInfo attacker, CardInfo defender, Feature feature) {
        OnAttackBlockingResult result = new OnAttackBlockingResult(true, feature == null ? attacker.getAT()
                : feature.getImpact());
        if (feature == null) {
            // Normal attack could be blocked by Dodge or Âé±Ô, ±ù¶³,
            // Ëø¶¨ status.
            CardStatus status = attacker.getStatus();
            if (status.containsStatus(CardStatusType.±ù¶³) || status.containsStatus(CardStatusType.Âé±Ô)
                    || status.containsStatus(CardStatusType.Ëø¶¨)) {
                stage.getUI().attackBlocked(attacker, defender, feature, null);
                result.attackable = false;
                result.damage = 0;
            } else {
                for (Feature blockFeature : defender.getUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.¸ñµ²) {
                        result.damage = BlockFeature.apply(blockFeature, this, attacker, defender, result.damage);
                    }
                }
            }
        } else {
            CardStatus status = attacker.getStatus();
            if (status.containsStatus(CardStatusType.±ù¶³) || status.containsStatus(CardStatusType.Ëø¶¨)) {
                stage.getUI().attackBlocked(attacker, defender, feature, null);
                result.attackable = false;
                result.damage = 0;
            } else {
                for (Feature blockFeature : defender.getUsableFeaturesOf(FeatureType.Ä§¼×)) {
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

    public void resolveExtraAttackFeature(CardInfo attacker, CardInfo defender, Player defenderHero,
            int normalAttackDamage) throws HeroDieSignal {
        if (attacker != null) {
            for (Feature feature : attacker.getUsableFeatures()) {
                if (feature.getType() == FeatureType.´©´Ì) {
                    PenetrationFeature.apply(feature, this, attacker, defenderHero, normalAttackDamage);
                } else if (feature.getType() == FeatureType.Ï÷Èõ) {
                    WeakenFeature.apply(feature, this, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (defender != null) {
            for (Feature feature : defender.getUsableFeatures()) {
                if (feature.getType() == FeatureType.¿ñÈÈ) {
                    ZealotFeature.apply(feature, this, attacker, defender, normalAttackDamage);
                }
            }
        }
    }

    public void resolvePreAttackCardFeature(CardInfo attacker, CardInfo defender) {
        for (Feature feature : attacker.getUsableFeatures()) {
            if (feature.getType() == FeatureType.Ê¥¹â) {
                HolyLightFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.±©»÷) {
                CriticalAttackFeature.apply(feature, this, attacker, defender);
            }
        }
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
        stage.getUI().useSkillToHero(attacker, defenderPlayer, null);
        if (!this.resolveAttackHeroBlockingFeatures(attacker, defenderPlayer, feature, damage)) {
            stage.getUI().attackHero(attacker, defenderPlayer, feature, damage);
            defenderPlayer.setLife(defenderPlayer.getLife() - damage);
        }
    }

    private boolean resolveAttackHeroBlockingFeatures(CardInfo attacker, Player defenderPlayer, Feature feature,
            int damage) {
        for (CardInfo defender : defenderPlayer.getField()) {
            if (defender == null) {
                continue;
            }
            for (Feature defenderFeature : defender.getUsableFeatures()) {
                if (defenderFeature.getType() == FeatureType.ÊØ»¤) {
                    GuardFeature.apply(defenderFeature, this, attacker, defender, damage);
                    return true;
                }
            }
        }
        return false;
    }

    public void removeEffects(CardInfo card, FeatureType... causes) {
        if (card == null) {
            return;
        }
        for (FeatureType cause : causes) {
            List<FeatureEffect> effects = card.getEffectsCauseBy(cause);
            if (effects == null) {
                continue;
            }
            for (FeatureEffect effect : effects) {
                if (cause == FeatureType.Ê¥¹â) {
                    HolyLightFeature.remove(this, effect, card);
                } else if (cause == FeatureType.±©»÷) {
                    CriticalAttackFeature.remove(this, effect, card);
                }
            }
        }
    }

    public void resolveCardRoundEndingFeature(CardInfo card) {
        for (Feature feature : card.getUsableFeatures()) {
            if (feature.getType() == FeatureType.»Ø´º) {
                RejuvenateFeature.apply(feature, this, card);
            }
        }
    }
    
    public int attackCard(CardInfo attacker, CardInfo defender) {
        this.stage.getUI().useSkill(attacker, defender, null);
        OnAttackBlockingResult blockingResult = stage.getResolver().resolveAttackBlockingFeature(attacker, defender,
                null);
        if (!blockingResult.attackable) {
            return -1;
        }
        this.stage.getUI().attackCard(attacker, defender, null, blockingResult.damage);
        OnDamagedResult damagedResult = stage.getResolver().applyDamage(defender, blockingResult.damage);
        if (damagedResult.cardDead) {
            stage.getResolver().resolveDyingFeature(attacker, defender, null);
        }
        return damagedResult.actualDamage;
    }
}
