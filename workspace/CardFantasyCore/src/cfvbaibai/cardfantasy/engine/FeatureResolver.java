package cfvbaibai.cardfantasy.engine;

import java.util.List;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.engine.feature.BlockFeature;
import cfvbaibai.cardfantasy.engine.feature.ChainLighteningFeature;
import cfvbaibai.cardfantasy.engine.feature.CounterAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.CriticalAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.DodgeFeature;
import cfvbaibai.cardfantasy.engine.feature.FireballFeature;
import cfvbaibai.cardfantasy.engine.feature.GuardFeature;
import cfvbaibai.cardfantasy.engine.feature.HealFeature;
import cfvbaibai.cardfantasy.engine.feature.HolyLightFeature;
import cfvbaibai.cardfantasy.engine.feature.IceBoltFeature;
import cfvbaibai.cardfantasy.engine.feature.KingdomPowerFeature;
import cfvbaibai.cardfantasy.engine.feature.PenetrationFeature;
import cfvbaibai.cardfantasy.engine.feature.PrayFeature;
import cfvbaibai.cardfantasy.engine.feature.RainfallFeature;
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

    public void resolvePreAttackFeature(CardInfo attacker, Player defender) throws HeroDieSignal {
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
            } else if (feature.getType() == FeatureType.ÖÎÁÆ) {
                HealFeature.apply(feature, this, attacker);
            } else if (feature.getType() == FeatureType.¸ÊÁØ) {
                RainfallFeature.apply(feature, this, attacker);
            } else if (feature.getType() == FeatureType.Æíµ») {
                PrayFeature.apply(feature, this, attacker);
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
                for (Feature blockFeature : defender.getUsableFeaturesOf(FeatureType.ÉÁ±Ü)) {
                    if (!result.attackable) {
                        continue;
                    }
                    if (blockFeature.getType() == FeatureType.ÉÁ±Ü) {
                        result.attackable = !DodgeFeature.apply(blockFeature, this, attacker, defender, result.damage);
                    }
                }
                for (Feature blockFeature : defender.getUsableFeatures()) {
                    if (!result.attackable) {
                        continue;
                    }
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

    public void resolveDeathFeature(CardInfo attacker, CardInfo defender, Feature feature) {
        for (FeatureInfo deadCardFeature : defender.getUsableFeatures()) {
            if (deadCardFeature.getType() == FeatureType.Íõ¹úÖ®Á¦) {
                KingdomPowerFeature.remove(this, deadCardFeature, defender);
            }
        }
        return;
    }

    public void resolveExtraAttackFeature(CardInfo attacker, CardInfo defender, Player defenderHero,
            int normalAttackDamage) throws HeroDieSignal {
        if (attacker != null) {
            for (FeatureInfo feature : attacker.getUsableFeatures()) {
                if (feature.getType() == FeatureType.´©´Ì) {
                    PenetrationFeature.apply(feature, this, attacker, defenderHero, normalAttackDamage);
                } else if (feature.getType() == FeatureType.Ï÷Èõ) {
                    WeakenFeature.apply(this, feature, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (defender != null) {
            for (FeatureInfo feature : defender.getUsableFeatures()) {
                if (feature.getType() == FeatureType.¿ñÈÈ) {
                    ZealotFeature.apply(feature, this, attacker, defender, normalAttackDamage);
                }
            }
        }
    }

    public void resolvePreAttackCardFeature(CardInfo attacker, CardInfo defender) {
        for (FeatureInfo feature : attacker.getUsableFeatures()) {
            if (feature.getType() == FeatureType.Ê¥¹â) {
                HolyLightFeature.apply(this, feature, attacker, defender);
            } else if (feature.getType() == FeatureType.±©»÷) {
                CriticalAttackFeature.apply(this, feature, attacker, defender);
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
        if (attacker == null) {
            return;
        }
        stage.getUI().useSkillToHero(attacker, defenderPlayer, feature);
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
            List<FeatureEffect> effects = card.getEffectsCausedBy(cause);
            if (effects == null) {
                continue;
            }
            for (FeatureEffect effect : effects) {
                if (cause == FeatureType.Ê¥¹â) {
                    HolyLightFeature.remove(this, effect.getCause(), card);
                } else if (cause == FeatureType.±©»÷) {
                    CriticalAttackFeature.remove(this, effect.getCause(), card);
                }
            }
        }
    }

    public void resolveCardRoundEndingFeature(CardInfo card) {
        if (card == null) {
            return;
        }
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
            stage.getResolver().resolveDeathFeature(attacker, defender, null);
        }
        return damagedResult.actualDamage;
    }

    public CardInfo pickHealee(CardInfo healer) {
        Field field = healer.getOwner().getField();
        CardInfo healee = null;
        for (CardInfo card : field) {
            if (healee == null || card.getHP() < healee.getHP()) {
                healee = card;
            }
        }
        return healee;
    }

    public void resolveSummoningFeature(Field field) {
        for (CardInfo card : field) {
            for (FeatureInfo feature : card.getUsableFeatures()) {
                if (feature.getType() == FeatureType.Íõ¹úÖ®Á¦) {
                    KingdomPowerFeature.apply(this, feature, card);
                }
            }
        }
    }
}
