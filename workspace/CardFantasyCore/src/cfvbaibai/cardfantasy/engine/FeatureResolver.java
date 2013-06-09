package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.engine.feature.BlockFeature;
import cfvbaibai.cardfantasy.engine.feature.BurningFeature;
import cfvbaibai.cardfantasy.engine.feature.ChainLighteningFeature;
import cfvbaibai.cardfantasy.engine.feature.CounterAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.CounterMagicFeature;
import cfvbaibai.cardfantasy.engine.feature.CriticalAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.DodgeFeature;
import cfvbaibai.cardfantasy.engine.feature.FireStormFeature;
import cfvbaibai.cardfantasy.engine.feature.FireballFeature;
import cfvbaibai.cardfantasy.engine.feature.FirewallFeature;
import cfvbaibai.cardfantasy.engine.feature.GuardFeature;
import cfvbaibai.cardfantasy.engine.feature.HealFeature;
import cfvbaibai.cardfantasy.engine.feature.HolyLightFeature;
import cfvbaibai.cardfantasy.engine.feature.IceBoltFeature;
import cfvbaibai.cardfantasy.engine.feature.MagicShieldFeature;
import cfvbaibai.cardfantasy.engine.feature.PenetrationFeature;
import cfvbaibai.cardfantasy.engine.feature.PrayFeature;
import cfvbaibai.cardfantasy.engine.feature.RaceBuffFeature;
import cfvbaibai.cardfantasy.engine.feature.RainfallFeature;
import cfvbaibai.cardfantasy.engine.feature.RejuvenateFeature;
import cfvbaibai.cardfantasy.engine.feature.ResurrectFeature;
import cfvbaibai.cardfantasy.engine.feature.SnipeFeature;
import cfvbaibai.cardfantasy.engine.feature.SpikeFeature;
import cfvbaibai.cardfantasy.engine.feature.ThunderStormFeature;
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

    public List<CardInfo> getAdjacentCards(CardInfo card) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card is null!");
        }
        List<CardInfo> cards = new ArrayList<CardInfo>();
        cards.add(card);
        int i = card.getPosition();
        if (i > 0) {
            CardInfo leftSide = card.getOwner().getField().getCard(i - 1);
            if (leftSide != null) {
                cards.add(leftSide);
            }
        }
        CardInfo rightSide = card.getOwner().getField().getCard(i + 1);
        if (rightSide != null) {
            cards.add(rightSide);
        }
        return cards;
    }

    public void resolvePreAttackFeature(CardInfo attacker, Player defender) throws HeroDieSignal {
        for (FeatureInfo feature : attacker.getUsableFeatures()) {
            if (attacker.isDead()) {
                continue;
            }
            if (feature.getType() == FeatureType.连环闪电) {
                ChainLighteningFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.陷阱) {
                TrapFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.狙击) {
                SnipeFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.火球) {
                FireballFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.冰弹) {
                IceBoltFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.治疗) {
                HealFeature.apply(feature, this, attacker);
            } else if (feature.getType() == FeatureType.甘霖) {
                RainfallFeature.apply(feature, this, attacker);
            } else if (feature.getType() == FeatureType.祈祷) {
                PrayFeature.apply(feature, this, attacker);
            } else if (feature.getType() == FeatureType.火墙) {
                FirewallFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.烈焰风暴) {
                FireStormFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.雷暴) {
                ThunderStormFeature.apply(feature, this, attacker, defender);
            }
        }
    }

    public void resolvePostAttackFeature(CardInfo attacker, Player defender) {

    }

    public void resolveCounterAttackFeature(CardInfo attacker, CardInfo defender, Feature attackFeature) {
        if (attackFeature == null) {
            for (FeatureInfo feature : defender.getUsableFeatures()) {
                if (feature.getType() == FeatureType.反击) {
                    CounterAttackFeature.apply(feature, this, attacker, defender);
                } else if (feature.getType() == FeatureType.盾刺) {
                    SpikeFeature.apply(feature, this, attacker, defender);
                } else if (feature.getType() == FeatureType.燃烧) {
                    BurningFeature.apply(feature, this, attacker, defender);
                }
            }
        }
    }

    public OnAttackBlockingResult resolveAttackBlockingFeature(CardInfo attacker, CardInfo defender, Feature feature) {
        OnAttackBlockingResult result = new OnAttackBlockingResult(true, feature == null ? attacker.getAT()
                : feature.getImpact());
        if (feature == null) {
            // Normal attack could be blocked by Dodge or 麻痹, 冰冻,
            // 锁定 status.
            CardStatus status = attacker.getStatus();
            if (status.containsStatus(CardStatusType.冰冻) || status.containsStatus(CardStatusType.麻痹)
                    || status.containsStatus(CardStatusType.锁定)) {
                stage.getUI().attackBlocked(attacker, defender, feature, null);
                result.setAttackable(false);
            } else {
                for (Feature blockFeature : defender.getUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.闪避) {
                        if (!result.isAttackable()) {
                            continue;
                        }

                        result.setAttackable(!DodgeFeature.apply(blockFeature, this, attacker, defender,
                                result.getDamage()));
                    }
                }
                for (Feature blockFeature : defender.getUsableFeatures()) {
                    if (!result.isAttackable()) {
                        continue;
                    }
                    if (blockFeature.getType() == FeatureType.格挡) {
                        result.setDamage(BlockFeature.apply(blockFeature, this, attacker, defender, result.getDamage()));
                    }
                }
            }
        } else {
            CardStatus status = attacker.getStatus();
            if (status.containsStatus(CardStatusType.冰冻) || status.containsStatus(CardStatusType.锁定)) {
                stage.getUI().attackBlocked(attacker, defender, feature, null);
                result.setAttackable(false);
            } else {
                for (Feature blockFeature : defender.getUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.法力反射 && feature.getType().containsTag(FeatureTag.魔法)) {
                        CounterMagicFeature.apply(blockFeature, this, attacker, defender);
                        result.setAttackable(false);
                    }
                }
                for (Feature blockFeature : defender.getUsableFeatures()) {
                    if (!result.isAttackable()) {
                        continue;
                    }
                    if (blockFeature.getType() == FeatureType.魔甲 && feature.getType().containsTag(FeatureTag.魔法)) {
                        result.setDamage(MagicShieldFeature.apply(this, blockFeature, attacker, defender,
                                result.getDamage()));
                    }
                }
            }
        }
        return result;
    }

    public void resolveDeathFeature(CardInfo attacker, CardInfo defender, Feature feature) {
        for (FeatureInfo deadCardFeature : defender.getUsableFeatures()) {
            if (deadCardFeature.getType() == FeatureType.王国之力) {
                RaceBuffFeature.remove(this, deadCardFeature, defender, Race.王国);
            } else if (deadCardFeature.getType() == FeatureType.王国守护) {
                RaceBuffFeature.remove(this, deadCardFeature, defender, Race.王国);
            }
        }
        for (FeatureInfo deadCardFeature : defender.getUsableDeathFeatures()) {
            if (deadCardFeature.getType() == FeatureType.转生) {
                ResurrectFeature.apply(this, deadCardFeature, defender);
            }
        }
    }

    public void resolveExtraAttackFeature(CardInfo attacker, CardInfo defender, Player defenderHero,
            int normalAttackDamage) throws HeroDieSignal {
        if (attacker != null) {
            for (FeatureInfo feature : attacker.getUsableFeatures()) {
                if (feature.getType() == FeatureType.穿刺) {
                    PenetrationFeature.apply(feature, this, attacker, defenderHero, normalAttackDamage);
                } else if (feature.getType() == FeatureType.削弱) {
                    WeakenFeature.apply(this, feature, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (defender != null) {
            for (FeatureInfo feature : defender.getUsableFeatures()) {
                if (feature.getType() == FeatureType.狂热) {
                    ZealotFeature.apply(feature, this, attacker, defender, normalAttackDamage);
                }
            }
        }
    }

    public void resolvePreAttackCardFeature(CardInfo attacker, CardInfo defender) {
        for (FeatureInfo feature : attacker.getUsableFeatures()) {
            if (feature.getType() == FeatureType.圣光) {
                HolyLightFeature.apply(this, feature, attacker, defender);
            } else if (feature.getType() == FeatureType.暴击) {
                CriticalAttackFeature.apply(this, feature, attacker, defender);
            }
        }
    }

    public OnDamagedResult applyDamage(CardInfo card, int damage) {
        int originalHP = card.getHP();
        card.applyDamage(damage);
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
                // Grave is a stack.
                owner.getGrave().insertCard(card, 0);
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
        for (CardInfo defender : defenderPlayer.getField().getAliveCards()) {
            if (defender == null) {
                continue;
            }
            for (Feature defenderFeature : defender.getUsableFeatures()) {
                if (defenderFeature.getType() == FeatureType.守护) {
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
                if (cause == FeatureType.圣光) {
                    HolyLightFeature.remove(this, effect.getCause(), card);
                } else if (cause == FeatureType.暴击) {
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
            if (feature.getType() == FeatureType.回春) {
                RejuvenateFeature.apply(feature, this, card);
            }
        }
    }

    public int attackCard(CardInfo attacker, CardInfo defender) {
        this.stage.getUI().useSkill(attacker, defender, null);
        OnAttackBlockingResult blockingResult = stage.getResolver().resolveAttackBlockingFeature(attacker, defender,
                null);
        if (!blockingResult.isAttackable()) {
            return -1;
        }
        this.stage.getUI().attackCard(attacker, defender, null, blockingResult.getDamage());
        OnDamagedResult damagedResult = stage.getResolver().applyDamage(defender, blockingResult.getDamage());
        if (damagedResult.cardDead) {
            stage.getResolver().resolveDeathFeature(attacker, defender, null);
        }
        return damagedResult.actualDamage;
    }

    public CardInfo pickHealee(CardInfo healer) {
        Field field = healer.getOwner().getField();
        CardInfo healee = null;
        for (CardInfo card : field.getAliveCards()) {
            if (healee == null || card.getHP() < healee.getHP()) {
                healee = card;
            }
        }
        return healee;
    }

    public void resolveSummoningFeature(CardInfo card, Field myField, Field opField) {
        for (FeatureInfo feature : card.getUsableSummonFeatures()) {
            if (feature.getType() == FeatureType.陷阱) {
                TrapFeature.apply(feature, this, card, opField.getOwner());
            } else if (feature.getType() == FeatureType.烈焰风暴) {
                FireStormFeature.apply(feature, this, card, opField.getOwner());
            } else if (feature.getType() == FeatureType.雷暴) {
                ThunderStormFeature.apply(feature, this, card, opField.getOwner());
            }
        }
        for (CardInfo fieldCard : myField.getAliveCards()) {
            for (FeatureInfo feature : fieldCard.getUsableFeatures()) {
                if (feature.getType() == FeatureType.王国之力) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.王国, FeatureEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == FeatureType.王国守护) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.王国, FeatureEffectType.MAXHP_CHANGE);
                }
            }
        }
    }

    public void resolveDebuff(CardInfo card, CardStatusType debuffType) {
        if (card == null) {
            return;
        }
        List<CardStatusItem> items = card.getStatus().getStatusOf(debuffType);
        for (CardStatusItem item : items) {
            this.stage.getUI().debuffDamage(card, item, item.getEffect());
            this.applyDamage(card, item.getEffect());
        }
    }
}
