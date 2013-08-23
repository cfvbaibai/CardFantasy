package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.RuneActivationType;
import cfvbaibai.cardfantasy.data.RuneActivator;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.engine.feature.AttackUpFeature;
import cfvbaibai.cardfantasy.engine.feature.BackStabFeature;
import cfvbaibai.cardfantasy.engine.feature.BlockFeature;
import cfvbaibai.cardfantasy.engine.feature.BloodDrainFeature;
import cfvbaibai.cardfantasy.engine.feature.BloodPaintFeature;
import cfvbaibai.cardfantasy.engine.feature.BloodThirstyFeature;
import cfvbaibai.cardfantasy.engine.feature.BurningFeature;
import cfvbaibai.cardfantasy.engine.feature.BurningFlameFeature;
import cfvbaibai.cardfantasy.engine.feature.ChainAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.ConfusionFeature;
import cfvbaibai.cardfantasy.engine.feature.CounterAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.CounterBiteFeature;
import cfvbaibai.cardfantasy.engine.feature.CounterMagicFeature;
import cfvbaibai.cardfantasy.engine.feature.CriticalAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.CurseFeature;
import cfvbaibai.cardfantasy.engine.feature.DestroyFeature;
import cfvbaibai.cardfantasy.engine.feature.DiseaseFeature;
import cfvbaibai.cardfantasy.engine.feature.DodgeFeature;
import cfvbaibai.cardfantasy.engine.feature.EnergyArmorFeature;
import cfvbaibai.cardfantasy.engine.feature.EscapeFeature;
import cfvbaibai.cardfantasy.engine.feature.ExplodeFeature;
import cfvbaibai.cardfantasy.engine.feature.FireMagicFeature;
import cfvbaibai.cardfantasy.engine.feature.GuardFeature;
import cfvbaibai.cardfantasy.engine.feature.HealFeature;
import cfvbaibai.cardfantasy.engine.feature.HeavenWrathFeature;
import cfvbaibai.cardfantasy.engine.feature.HolyGuardFeature;
import cfvbaibai.cardfantasy.engine.feature.IceArmorFeature;
import cfvbaibai.cardfantasy.engine.feature.IceMagicFeature;
import cfvbaibai.cardfantasy.engine.feature.ImmobilityFeature;
import cfvbaibai.cardfantasy.engine.feature.ImmueFeature;
import cfvbaibai.cardfantasy.engine.feature.LegionBuffFeature;
import cfvbaibai.cardfantasy.engine.feature.LighteningMagicFeature;
import cfvbaibai.cardfantasy.engine.feature.MagicShieldFeature;
import cfvbaibai.cardfantasy.engine.feature.OverdrawFeature;
import cfvbaibai.cardfantasy.engine.feature.PenetrationFeature;
import cfvbaibai.cardfantasy.engine.feature.PlagueFeature;
import cfvbaibai.cardfantasy.engine.feature.PoisonMagicFeature;
import cfvbaibai.cardfantasy.engine.feature.PrayFeature;
import cfvbaibai.cardfantasy.engine.feature.PursuitFeature;
import cfvbaibai.cardfantasy.engine.feature.RaceBuffFeature;
import cfvbaibai.cardfantasy.engine.feature.RacialAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.RacialShieldFeature;
import cfvbaibai.cardfantasy.engine.feature.RainfallFeature;
import cfvbaibai.cardfantasy.engine.feature.ReincarnationFeature;
import cfvbaibai.cardfantasy.engine.feature.RejuvenateFeature;
import cfvbaibai.cardfantasy.engine.feature.ResurrectionFeature;
import cfvbaibai.cardfantasy.engine.feature.ReturnFeature;
import cfvbaibai.cardfantasy.engine.feature.RevengeFeature;
import cfvbaibai.cardfantasy.engine.feature.ReviveFeature;
import cfvbaibai.cardfantasy.engine.feature.SacrificeFeature;
import cfvbaibai.cardfantasy.engine.feature.SealFeature;
import cfvbaibai.cardfantasy.engine.feature.SnipeFeature;
import cfvbaibai.cardfantasy.engine.feature.SpikeFeature;
import cfvbaibai.cardfantasy.engine.feature.TransportFeature;
import cfvbaibai.cardfantasy.engine.feature.TrapFeature;
import cfvbaibai.cardfantasy.engine.feature.WarthFeature;
import cfvbaibai.cardfantasy.engine.feature.WeakPointAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.WeakenAllFeature;
import cfvbaibai.cardfantasy.engine.feature.WeakenFeature;
import cfvbaibai.cardfantasy.engine.feature.WinningPursuitFeature;
import cfvbaibai.cardfantasy.engine.feature.WoundFeature;
import cfvbaibai.cardfantasy.engine.feature.ZealotFeature;

public class FeatureResolver {
    private StageInfo stage;

    public FeatureResolver(StageInfo stage) {
        this.stage = stage;
    }

    public StageInfo getStage() {
        return this.stage;
    }

    public List<CardInfo> getAdjacentCards(Field field, int position) {
        List<CardInfo> cards = new ArrayList<CardInfo>();
        CardInfo card = field.getCard(position);
        if (card != null) {
            cards.add(card);
        }
        if (position > 0) {
            CardInfo leftSide = field.getCard(position - 1);
            if (leftSide != null) {
                cards.add(leftSide);
            }
        }
        CardInfo rightSide = field.getCard(position + 1);
        if (rightSide != null) {
            cards.add(rightSide);
        }
        return cards;
    }

    public void resolvePreAttackFeature(CardInfo attacker, Player defender) throws HeroDieSignal {
        for (FeatureInfo feature : attacker.getNormalUsableFeatures()) {
            if (attacker.isDead()) {
                continue;
            }
            if (feature.getType() == FeatureType.Í¸Ö§) {
                OverdrawFeature.apply(this, feature, attacker);
            }
        }
        for (FeatureInfo feature : attacker.getNormalUsableFeatures()) {
            if (attacker.isDead()) {
                continue;
            }
            if (feature.getType() == FeatureType.Î´Öª) {
                // JUST A PLACEHOLDER
            } else if (feature.getType() == FeatureType.»ğÇò) {
                FireMagicFeature.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.»ğÇ½) {
                FireMagicFeature.apply(feature.getFeature(), this, attacker, defender, 3);
            } else if (feature.getType() == FeatureType.ÁÒÑæ·ç±©) {
                FireMagicFeature.apply(feature.getFeature(), this, attacker, defender, -1);
            } else if (feature.getType() == FeatureType.ÂäÀ×) {
                LighteningMagicFeature.apply(feature, this, attacker, defender, 1, 50);
            } else if (feature.getType() == FeatureType.Á¬»·ÉÁµç) {
                LighteningMagicFeature.apply(feature, this, attacker, defender, 3, 40);
            } else if (feature.getType() == FeatureType.À×±©) {
                LighteningMagicFeature.apply(feature, this, attacker, defender, -1, 35);
            } else if (feature.getType() == FeatureType.±ùµ¯) {
                IceMagicFeature.apply(feature, this, attacker, defender, 1, 45);
            } else if (feature.getType() == FeatureType.Ëª¶³ĞÂĞÇ) {
                IceMagicFeature.apply(feature, this, attacker, defender, 3, 35);
            } else if (feature.getType() == FeatureType.±©·çÑ©) {
                IceMagicFeature.apply(feature, this, attacker, defender, -1, 30);
            } else if (feature.getType() == FeatureType.¶¾Òº) {
                PoisonMagicFeature.apply(feature, this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.¶¾Îí) {
                PoisonMagicFeature.apply(feature, this, attacker, defender, 3);
            } else if (feature.getType() == FeatureType.¶¾ÔÆ) {
                PoisonMagicFeature.apply(feature, this, attacker, defender, -1);
            } else if (feature.getType() == FeatureType.ÏİÚå) {
                TrapFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.¾Ñ»÷) {
                SnipeFeature.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.Ä§ÉñÖ®ÈĞ) {
                SnipeFeature.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.ÖÎÁÆ) {
                HealFeature.apply(feature.getFeature(), this, attacker);
            } else if (feature.getType() == FeatureType.¸ÊÁØ) {
                RainfallFeature.apply(feature.getFeature(), this, attacker);
            } else if (feature.getType() == FeatureType.Æíµ») {
                PrayFeature.apply(feature.getFeature(), this, attacker);
            } else if (feature.getType() == FeatureType.¸´»î) {
                ReviveFeature.apply(this, feature, attacker);
            } else if (feature.getType() == FeatureType.±³´Ì) {
                BackStabFeature.apply(this, feature, attacker);
            } else if (feature.getType() == FeatureType.ÈºÌåÏ÷Èõ) {
                WeakenAllFeature.apply(this, feature, attacker, defender);
            } else if (feature.getType() == FeatureType.»Ø»ê) {
                ResurrectionFeature.apply(this, feature, attacker);
            } else if (feature.getType() == FeatureType.¶şÖØ¾Ñ»÷) {
                SnipeFeature.apply(feature.getFeature(), this, attacker, defender, 2);
            } else if (feature.getType() == FeatureType.ÃÔ»ê) {
                ConfusionFeature.apply(feature, this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.ÁÒ»ğ·ÙÉñ) {
                BurningFlameFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.×çÖä) {
                CurseFeature.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == FeatureType.Ä§ÉñÖ®Öä) {
                CurseFeature.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == FeatureType.´İ»Ù) {
                DestroyFeature.apply(this, feature.getFeature(), attacker, defender, 1);
            } else if (feature.getType() == FeatureType.ÎÁÒß) {
                PlagueFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.ÑªÁ¶) {
                BloodPaintFeature.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.ÌìÇ´) {
                HeavenWrathFeature.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == FeatureType.·âÓ¡) {
                SealFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.ÉñÊ¥ÊØ»¤) {
                HolyGuardFeature.apply(this, feature, attacker);
            }
        }
        RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.·ÉÑÒ);
        if (rune != null) {
            SnipeFeature.apply(rune.getFeature(), this, attacker, defender, 1);
        }
    }

    public void resolvePostAttackFeature(CardInfo attacker, Player defender) {

    }

    public void resolveCounterAttackFeature(CardInfo attacker, CardInfo defender, Feature attackFeature,
            OnAttackBlockingResult result) throws HeroDieSignal {
        if (attackFeature == null) {
            for (FeatureInfo feature : defender.getNormalUsableFeatures()) {
                if (feature.getType() == FeatureType.·´»÷) {
                    CounterAttackFeature.apply(feature.getFeature(), this, attacker, defender, result.getDamage());
                } else if (feature.getType() == FeatureType.¶Ü´Ì) {
                    SpikeFeature.apply(feature.getFeature(), this, attacker, defender, result.getDamage());
                } else if (feature.getType() == FeatureType.Ä§ÉñÖ®¼×) {
                    SpikeFeature.apply(feature.getFeature(), this, attacker, defender, result.getDamage());
                } else if (feature.getType() == FeatureType.È¼ÉÕ) {
                    BurningFeature.apply(feature, this, attacker, defender);
                }
            }
            {
                RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.À×¶Ü);
                if (rune != null) {
                    SpikeFeature.apply(rune.getFeature(), this, attacker, defender, result.getDamage());
                }
            }
            {
                RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.äöÎĞ);
                if (rune != null) {
                    CounterAttackFeature.apply(rune.getFeature(), this, attacker, defender, result.getDamage());
                }
            }
            if (!defender.isDead()) {
                for (FeatureInfo feature : defender.getNormalUsableFeatures()) {
                    if (feature.getType() == FeatureType.¿ñÈÈ) {
                        ZealotFeature.apply(feature, this, attacker, defender, result);
                    }
                }
                RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.Å­ÌÎ);
                if (rune != null) {
                    ZealotFeature.apply(rune.getFeatureInfo(), this, attacker, defender, result);
                }
            }
        }
    }

    public OnAttackBlockingResult resolveHealBlockingFeature(EntityInfo healer, CardInfo healee, Feature cardFeature) {
        OnAttackBlockingResult result = new OnAttackBlockingResult(true, cardFeature.getImpact());
        if (healee.getStatus().containsStatus(CardStatusType.ÁÑÉË)) {
            stage.getUI().healBlocked(healer, healee, cardFeature, null);
            result.setAttackable(false);
        }
        return result;
    }

    public OnAttackBlockingResult resolveAttackBlockingFeature(EntityInfo attacker, CardInfo defender,
            Feature cardFeature, int damage) throws HeroDieSignal {
        OnAttackBlockingResult result = new OnAttackBlockingResult(true, 0);
        CardStatus status = attacker.getStatus();
        if (cardFeature == null) {
            // Normal attack could be blocked by Dodge or Âé±Ô, ±ù¶³,
            // Ëø¶¨ status.
            CardInfo cardAttacker = (CardInfo) attacker;
            result.setDamage(damage);
            if (status.containsStatus(CardStatusType.±ù¶³) || status.containsStatus(CardStatusType.Âé±Ô)
                    || status.containsStatus(CardStatusType.Ëø¶¨) || status.containsStatus(CardStatusType.ĞéÈõ)) {
                stage.getUI().attackBlocked(cardAttacker, defender, cardFeature, null);
                result.setAttackable(false);
            } else {
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.ÉÁ±Ü) {
                        result.setAttackable(!DodgeFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, result.getDamage()));
                        if (!result.isAttackable()) {
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.ÇáÁé);
                    if (rune != null) {
                        result.setAttackable(!DodgeFeature.apply(rune.getFeature(), this, cardAttacker, defender,
                                result.getDamage()));
                        if (!result.isAttackable()) {
                            return result;
                        }
                    }
                }
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.Íõ¹úÖ®¶Ü) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.µØÓü));
                    }
                    if (blockFeature.getType() == FeatureType.É­ÁÖÖ®¶Ü) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.Âù»Ä));
                    }
                    if (blockFeature.getType() == FeatureType.Âù»ÄÖ®¶Ü) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.Íõ¹ú));
                    }
                    if (blockFeature.getType() == FeatureType.µØÓüÖ®¶Ü) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.É­ÁÖ));
                    }
                }
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.¸ñµ²) {
                        result.setDamage(BlockFeature.apply(blockFeature.getFeature(), this, cardAttacker, defender,
                                defender, result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.±ù¼×) {
                        result.setDamage(IceArmorFeature.apply(blockFeature.getFeature(), this, cardAttacker, defender,
                                result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.±ù·â);
                    if (rune != null) {
                        result.setDamage(IceArmorFeature.apply(rune.getFeature(), this, cardAttacker, defender,
                                result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.ÑÒ±Ú);
                    if (rune != null) {
                        result.setDamage(BlockFeature.apply(rune.getFeature(), this, cardAttacker, defender, rune,
                                result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
            }
        } else {
            result.setDamage(damage);
            if (status.containsStatus(CardStatusType.±ù¶³) || status.containsStatus(CardStatusType.Ëø¶¨)
                    || status.containsStatus(CardStatusType.ĞéÈõ)) {
                stage.getUI().attackBlocked(attacker, defender, cardFeature, null);
                result.setAttackable(false);
            } else {
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.·¨Á¦·´Éä) {
                        if (CounterMagicFeature.isFeatureBlocked(this, blockFeature.getFeature(), cardFeature,
                                attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getRuneBox().getRuneOf(RuneData.Ê¯ÁÖ);
                    if (rune != null && rune.isActivated()) {
                        if (CounterMagicFeature.isFeatureBlocked(this, rune.getFeature(), cardFeature, attacker,
                                defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }

                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.ÃâÒß) {
                        if (ImmueFeature.isFeatureBlocked(this, blockFeature.getFeature(), cardFeature, attacker,
                                defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockFeature.getType() == FeatureType.ÍÑÀ§) {
                        if (EscapeFeature.isFeatureEscaped(this, blockFeature.getFeature(), cardFeature, attacker,
                                defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockFeature.getType() == FeatureType.²»¶¯) {
                        if (ImmobilityFeature.isFeatureBlocked(this, blockFeature.getFeature(), cardFeature, attacker,
                                defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.Ä§¼×) {
                        result.setDamage(MagicShieldFeature.apply(this, blockFeature.getFeature(), attacker, defender,
                                cardFeature, result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.Ñ×¼×);
                    if (rune != null) {
                        result.setDamage(MagicShieldFeature.apply(this, rune.getFeature(), attacker, defender,
                                cardFeature, result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 
     * @param killerCard
     * @param deadCard
     * @param cardFeature
     * @return Whether the dead card is revived.
     * @throws HeroDieSignal
     */
    public void resolveDeathFeature(EntityInfo killerCard, CardInfo deadCard, Feature cardFeature) throws HeroDieSignal {
        if (deadCard.hasDeadOnce()) {
            return;
        }
        deadCard.setDeadOnce(true);
        resolveLeaveFeature(deadCard, cardFeature);
        for (FeatureInfo deadCardFeature : deadCard.getUsableDeathFeatures()) {
            if (deadCardFeature.getType() == FeatureType.ÈºÌåÏ÷Èõ) {
                WeakenAllFeature.apply(this, deadCardFeature, deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == FeatureType.ÁÒ»ğ·ÙÉñ) {
                BurningFlameFeature.apply(deadCardFeature, this, deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == FeatureType.¸´»î) {
                ReviveFeature.apply(this, deadCardFeature, deadCard);
            } else if (deadCardFeature.getType() == FeatureType.×çÖä) {
                CurseFeature.apply(this, deadCardFeature.getFeature(), deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == FeatureType.ÁÒÑæ·ç±©) {
                FireMagicFeature.apply(deadCardFeature.getFeature(), this, deadCard, killerCard.getOwner(), -1);
            } else if (deadCardFeature.getType() == FeatureType.´İ»Ù) {
                DestroyFeature.apply(this, deadCardFeature.getFeature(), deadCard, killerCard.getOwner(), 1);
            } else if (deadCardFeature.getType() == FeatureType.´«ËÍ) {
                TransportFeature.apply(this, deadCardFeature.getFeature(), deadCard, killerCard.getOwner());
            }
        }
        for (FeatureInfo deadCardFeature : deadCard.getAllUsableFeatures()) {
            if (deadCardFeature.getType() == FeatureType.×Ô±¬) {
                ExplodeFeature.apply(this, deadCardFeature.getFeature(), killerCard, deadCard);
            }
        }
        {
            RuneInfo rune = deadCard.getOwner().getActiveRuneOf(RuneData.±¬ÁÑ);
            if (rune != null) {
                ExplodeFeature.apply(this, rune.getFeature(), killerCard, deadCard);
            }
        }
        boolean reincarnated = false;
        for (FeatureInfo deadCardFeature : deadCard.getAllUsableFeatures()) {
            if (deadCardFeature.getType() == FeatureType.×ªÉú) {
                if (ReincarnationFeature.apply(this, deadCardFeature.getFeature(), deadCard)) {
                    reincarnated = true;
                    break;
                }
            }
        }
        if (!reincarnated) {
            RuneInfo rune = deadCard.getOwner().getActiveRuneOf(RuneData.»àÍÁ);
            if (rune != null) {
                ReincarnationFeature.apply(this, rune.getFeature(), deadCard);
            }
        }
    }

    public void resolveExtraAttackFeature(CardInfo attacker, CardInfo defender, Player defenderHero,
            int normalAttackDamage) throws HeroDieSignal {

        for (FeatureInfo feature : attacker.getNormalUsableFeatures()) {
            if (!attacker.isDead()) {
                if (feature.getType() == FeatureType.´©´Ì) {
                    PenetrationFeature.apply(feature.getFeature(), this, attacker, defenderHero, normalAttackDamage);
                } else if (feature.getType() == FeatureType.Ï÷Èõ) {
                    WeakenFeature.apply(this, feature, attacker, defender, normalAttackDamage);
                } else if (feature.getType() == FeatureType.ÁÑÉË) {
                    WoundFeature.apply(this, feature, attacker, defender, normalAttackDamage);
                } else if (feature.getType() == FeatureType.ÊÈÑª) {
                    BloodThirstyFeature.apply(this, feature, attacker, normalAttackDamage);
                } else if (feature.getType() == FeatureType.Á¬Ëø¹¥»÷) {
                    ChainAttackFeature.apply(this, feature, attacker, defender);
                } else if (feature.getType() == FeatureType.¼²²¡) {
                    DiseaseFeature.apply(feature, this, attacker, defender, normalAttackDamage);
                } else if (feature.getType() == FeatureType.ÎüÑª) {
                    BloodDrainFeature.apply(feature.getFeature(), this, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (!attacker.isDead()) {
            RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.³à¹È);
            if (rune != null) {
                BloodDrainFeature.apply(rune.getFeature(), this, attacker, defender, normalAttackDamage);
            }
        }
        if (!attacker.isDead()) {
            RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.¶´²ì);
            if (rune != null) {
                BloodThirstyFeature.apply(this, rune.getFeatureInfo(), attacker, normalAttackDamage);
            }
        }
    }

    public void resolvePreAttackCardFeature(CardInfo attacker, CardInfo defender, boolean prior) throws HeroDieSignal {
        for (FeatureInfo feature : attacker.getNormalUsableFeatures()) {
            if (prior) {
                if (feature.getType() == FeatureType.ËÍ»¹) {
                    ReturnFeature.apply(this, feature.getFeature(), attacker, defender);
                }
            } else {
                if (feature.getType() == FeatureType.Ê¥¹â) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.µØÓü);
                } else if (feature.getType() == FeatureType.Òªº¦) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.Âù»Ä);
                } else if (feature.getType() == FeatureType.°µÉ±) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.Íõ¹ú);
                } else if (feature.getType() == FeatureType.ÎÛÈ¾) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.É­ÁÖ);
                } else if (feature.getType() == FeatureType.±©»÷) {
                    CriticalAttackFeature.apply(this, feature, attacker, defender);
                } else if (feature.getType() == FeatureType.Çî×·ÃÍ´ò) {
                    PursuitFeature.apply(this, feature, attacker, defender);
                } else if (feature.getType() == FeatureType.Õ½Òâ) {
                    WarthFeature.apply(this, feature, attacker, defender);
                } else if (feature.getType() == FeatureType.³ÃÊ¤×·»÷) {
                    WinningPursuitFeature.apply(this, feature, attacker, defender);
                } else if (feature.getType() == FeatureType.¸´³ğ) {
                    RevengeFeature.apply(this, feature, attacker, defender);
                }
            }
        }
        if (!prior) {
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.¾øÉ±);
                if (rune != null) {
                    WarthFeature.apply(this, rune.getFeatureInfo(), attacker, defender);
                }
            }
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.º®ÉË);
                if (rune != null) {
                    CriticalAttackFeature.apply(this, rune.getFeatureInfo(), attacker, defender);
                }
            }
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.ÑïÆì);
                if (rune != null) {
                    PursuitFeature.apply(this, rune.getFeatureInfo(), attacker, defender);
                }
            }
        }
    }

    public void removeTempEffects(CardInfo card) {
        if (card == null) {
            return;
        }
        for (FeatureEffect effect : card.getEffects()) {
            FeatureType type = effect.getCause().getType();
            if (type == FeatureType.Ê¥¹â || type == FeatureType.Òªº¦ || type == FeatureType.°µÉ± || type == FeatureType.ÎÛÈ¾) {
                RacialAttackFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.±©»÷) {
                CriticalAttackFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.Çî×·ÃÍ´ò) {
                PursuitFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.±³´Ì) {
                BackStabFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.Õ½Òâ) {
                WarthFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.³ÃÊ¤×·»÷) {
                WinningPursuitFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.¸´³ğ) {
                RevengeFeature.remove(this, effect.getCause(), card);
            }
        }
    }

    public OnDamagedResult applyDamage(CardInfo card, int damage) {
        int actualDamage = card.applyDamage(damage);
        OnDamagedResult result = new OnDamagedResult();
        result.originalDamage = damage;
        result.actualDamage = actualDamage;
        if (card.getHP() <= 0) {
            result.cardDead = true;
            cardDead(card);
        }
        return result;
    }

    public void cardDead(CardInfo deadCard) {
        if (deadCard.hasDeadOnce()) {
            return;
        }
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

    public void attackHero(EntityInfo attacker, Player defenderPlayer, Feature cardFeature, int damage)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        stage.getUI().useSkillToHero(attacker, defenderPlayer, cardFeature);
        if (damage > 0) {
            if (!this.resolveAttackHeroBlockingFeatures(attacker, defenderPlayer, cardFeature, damage)) {
                stage.getUI().attackHero(attacker, defenderPlayer, cardFeature, damage);
            }
        } else {
            stage.getUI().healHero(attacker, defenderPlayer, cardFeature, -damage);
        }
        defenderPlayer.setHP(defenderPlayer.getHP() - damage);
    }

    private boolean resolveAttackHeroBlockingFeatures(EntityInfo attacker, Player defenderPlayer, Feature cardFeature,
            int damage) throws HeroDieSignal {
        for (CardInfo defender : defenderPlayer.getField().getAliveCards()) {
            if (defender == null) {
                continue;
            }
            for (FeatureInfo defenderFeature : defender.getNormalUsableFeatures()) {
                if (defenderFeature.getType() == FeatureType.ÊØ»¤) {
                    GuardFeature.apply(defenderFeature.getFeature(), this, attacker, defender, damage);
                    return true;
                }
            }
        }
        return false;
    }

    public void resolveCardRoundEndingFeature(CardInfo card) {
        if (card == null) {
            return;
        }
        CardStatus status = card.getStatus();
        if (status.containsStatus(CardStatusType.Ëø¶¨)) {
            return;
        }
        for (FeatureInfo cardFeature : card.getNormalUsableFeatures()) {
            if (cardFeature.getType() == FeatureType.»Ø´º) {
                RejuvenateFeature.apply(cardFeature.getFeature(), this, card);
            }
        }
        {
            RuneInfo rune = card.getOwner().getActiveRuneOf(RuneData.¸´ËÕ);
            if (rune != null) {
                RejuvenateFeature.apply(rune.getFeature(), this, card);
            }
        }
    }

    public OnDamagedResult attackCard(CardInfo attacker, CardInfo defender) throws HeroDieSignal {
        return attackCard(attacker, defender, attacker.getAT());
    }

    public OnDamagedResult attackCard(CardInfo attacker, CardInfo defender, int damage) throws HeroDieSignal {
        boolean bingo = !attacker.getStatus().containsStatus(CardStatusType.Âé±Ô);
        this.stage.getUI().useSkill(attacker, defender, null, bingo);

        OnAttackBlockingResult blockingResult = stage.getResolver().resolveAttackBlockingFeature(attacker, defender,
                null, damage);
        if (!blockingResult.isAttackable()) {
            return null;
        }
        this.stage.getUI().attackCard(attacker, defender, null, blockingResult.getDamage());
        OnDamagedResult damagedResult = stage.getResolver().applyDamage(defender, blockingResult.getDamage());
        if (damagedResult.cardDead) {
            stage.getResolver().resolveDeathFeature(attacker, defender, null);
        }

        resolveExtraAttackFeature(attacker, defender, defender.getOwner(), damagedResult.actualDamage);
        resolveCounterAttackFeature(attacker, defender, null, blockingResult);

        return damagedResult;
    }

    public CardInfo pickHealee(EntityInfo healer) {
        Field field = healer.getOwner().getField();
        CardInfo healee = null;
        for (CardInfo card : field.getAliveCards()) {
            if (healee == null || card.getLostHP() > healee.getLostHP()) {
                healee = card;
            }
        }
        return healee;
    }

    public void resolveSummoningFeature(CardInfo card, Field myField, Field opField) throws HeroDieSignal {
        for (FeatureInfo feature : card.getUsableSummonFeatures()) {
            if (feature.getType() == FeatureType.ÏİÚå) {
                TrapFeature.apply(feature, this, card, opField.getOwner());
            } else if (feature.getType() == FeatureType.ÁÒÑæ·ç±©) {
                FireMagicFeature.apply(feature.getFeature(), this, card, opField.getOwner(), -1);
            } else if (feature.getType() == FeatureType.À×±©) {
                LighteningMagicFeature.apply(feature, this, card, opField.getOwner(), -1, 35);
            } else if (feature.getType() == FeatureType.±©·çÑ©) {
                IceMagicFeature.apply(feature, this, card, opField.getOwner(), -1, 30);
            } else if (feature.getType() == FeatureType.ËÍ»¹) {
                ReturnFeature.apply(this, feature.getFeature(), card, opField.getCard(card.getPosition()));
            } else if (feature.getType() == FeatureType.ÈºÌåÏ÷Èõ) {
                WeakenAllFeature.apply(this, feature, card, opField.getOwner());
            } else if (feature.getType() == FeatureType.´«ËÍ) {
                TransportFeature.apply(this, feature.getFeature(), card, opField.getOwner());
            } else if (feature.getType() == FeatureType.¸ÊÁØ) {
                RainfallFeature.apply(feature.getFeature(), this, card);
            } else if (feature.getType() == FeatureType.×çÖä) {
                CurseFeature.apply(this, feature.getFeature(), card, opField.getOwner());
            } else if (feature.getType() == FeatureType.´İ»Ù) {
                DestroyFeature.apply(this, feature.getFeature(), card, opField.getOwner(), 1);
            } else if (feature.getType() == FeatureType.ÎÁÒß) {
                PlagueFeature.apply(feature, this, card, opField.getOwner());
            }
        }
        for (CardInfo fieldCard : myField.getAliveCards()) {
            for (FeatureInfo feature : fieldCard.getNormalUsableFeatures()) {
                if (feature.getType() == FeatureType.Íõ¹úÖ®Á¦) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.Íõ¹ú, FeatureEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == FeatureType.Íõ¹úÊØ»¤) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.Íõ¹ú, FeatureEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == FeatureType.É­ÁÖÖ®Á¦) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.É­ÁÖ, FeatureEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == FeatureType.É­ÁÖÊØ»¤) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.É­ÁÖ, FeatureEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == FeatureType.Âù»ÄÖ®Á¦) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.Âù»Ä, FeatureEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == FeatureType.Âù»ÄÊØ»¤) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.Âù»Ä, FeatureEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == FeatureType.µØÓüÖ®Á¦) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.µØÓü, FeatureEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == FeatureType.µØÓüÊØ»¤) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.µØÓü, FeatureEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == FeatureType.±¾Ô´Ö®Á¦) {
                    RaceBuffFeature.apply(this, feature, fieldCard, null, FeatureEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == FeatureType.±¾Ô´ÊØ»¤) {
                    RaceBuffFeature.apply(this, feature, fieldCard, null, FeatureEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == FeatureType.·´ÊÉ) {
                    CounterBiteFeature.apply(feature.getFeature(), this, fieldCard);
                } else if (feature.getType() == FeatureType.Ï×¼À) {
                    SacrificeFeature.apply(this, feature, fieldCard);
                }
            }
        }
        LegionBuffFeature.apply(this, card);
    }

    public void resolveLeaveFeature(CardInfo card, Feature cardFeature) {
        for (FeatureInfo deadCardFeature : card.getNormalUsableFeatures()) {
            if (deadCardFeature.getType() == FeatureType.Íõ¹úÖ®Á¦) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.Íõ¹ú);
            } else if (deadCardFeature.getType() == FeatureType.Íõ¹úÊØ»¤) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.Íõ¹ú);
            } else if (deadCardFeature.getType() == FeatureType.É­ÁÖÖ®Á¦) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.É­ÁÖ);
            } else if (deadCardFeature.getType() == FeatureType.É­ÁÖÊØ»¤) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.É­ÁÖ);
            } else if (deadCardFeature.getType() == FeatureType.Âù»ÄÖ®Á¦) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.Âù»Ä);
            } else if (deadCardFeature.getType() == FeatureType.Âù»ÄÊØ»¤) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.Âù»Ä);
            } else if (deadCardFeature.getType() == FeatureType.µØÓüÖ®Á¦) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.µØÓü);
            } else if (deadCardFeature.getType() == FeatureType.µØÓüÊØ»¤) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.µØÓü);
            } else if (deadCardFeature.getType() == FeatureType.±¾Ô´Ö®Á¦) {
                RaceBuffFeature.remove(this, deadCardFeature, card, null);
            } else if (deadCardFeature.getType() == FeatureType.±¾Ô´ÊØ»¤) {
                RaceBuffFeature.remove(this, deadCardFeature, card, null);
            } else if (deadCardFeature.getType() == FeatureType.ÉñÊ¥ÊØ»¤) {
                HolyGuardFeature.remove(this, deadCardFeature, card);
            } else if (deadCardFeature.getType() == FeatureType.¾üÍÅÍõ¹úÖ®Á¦
                    || deadCardFeature.getType() == FeatureType.¾üÍÅÉ­ÁÖÖ®Á¦
                    || deadCardFeature.getType() == FeatureType.¾üÍÅÂù»ÄÖ®Á¦
                    || deadCardFeature.getType() == FeatureType.¾üÍÅµØÓüÖ®Á¦) {
                LegionBuffFeature.remove(this, deadCardFeature, card);
            }
        }
    }

    public void resolveDebuff(CardInfo card, CardStatusType debuffType) throws HeroDieSignal {
        if (card == null) {
            return;
        }
        List<CardStatusItem> items = card.getStatus().getStatusOf(debuffType);
        for (CardStatusItem item : items) {
            this.stage.getUI().debuffDamage(card, item, item.getEffect());

            if (this.applyDamage(card, item.getEffect()).cardDead) {
                this.resolveDeathFeature(item.getCause().getOwner(), card, item.getCause().getFeature());
                break;
            }
        }
    }

    public static class BlockStatusResult {
        private boolean blocked;

        public boolean isBlocked() {
            return blocked;
        }

        public BlockStatusResult(boolean blocked) {
            this.blocked = blocked;
        }
    }

    public BlockStatusResult resolveBlockStatusFeature(EntityInfo attacker, CardInfo victim, FeatureInfo feature,
            CardStatusItem item) {
        boolean blocked = false;
        for (FeatureInfo blockFeature : victim.getNormalUsableFeatures()) {
            if (blockFeature.getType() == FeatureType.ÍÑÀ§) {
                blocked = EscapeFeature.isStatusEscaped(blockFeature.getFeature(), this, item, victim);
            }
        }
        return new BlockStatusResult(blocked);
    }

    public void summonCard(Player player, CardInfo card) throws HeroDieSignal {
        card.reset();
        card.setFirstRound(true);
        this.stage.getUI().summonCard(player, card);
        player.getField().addCard(card);
        if (this.stage.getPlayerCount() != 2) {
            throw new CardFantasyRuntimeException("There are " + this.stage.getPlayerCount()
                    + " player(s) in the stage, but expect 2");
        }
        for (Player other : stage.getPlayers()) {
            if (other != player) {
                stage.getResolver().resolveSummoningFeature(card, player.getField(), other.getField());
            }
        }
    }

    /**
     * 
     * @param cardFeature
     * @param attacker
     * @param defender
     * @return Whether block is disabled
     */
    public boolean resolveCounterBlockFeature(Feature cardFeature, CardInfo attacker, CardInfo defender) {
        for (FeatureInfo attackerFeature : attacker.getAllUsableFeatures()) {
            if (attackerFeature.getType() == FeatureType.Èõµã¹¥»÷) {
                return WeakPointAttackFeature.isBlockFeatureDisabled(this, attackerFeature.getFeature(), cardFeature,
                        attacker, defender);
            }
        }
        return false;
    }

    public void activateRunes(Player player, Player enemy) {
        for (RuneInfo rune : player.getRuneBox().getRunes()) {
            if (rune.getEnergy() <= 0) {
                continue;
            }
            boolean shouldActivate = false;
            RuneActivator activator = rune.getActivator();
            if (activator.getType() == RuneActivationType.HeroHP) {
                if (player.getHP() < activator.getThreshold() * player.getMaxHP() / 100) {
                    shouldActivate = true;
                }
            } else if (activator.getType() == RuneActivationType.Field) {
                Player playerToCheck = activator.shouldCheckEnemy() ? enemy : player;
                int activatorCardCount = 0;
                if (activator.getRace() == null) {
                    activatorCardCount = playerToCheck.getField().getAliveCards().size();
                } else {
                    for (CardInfo card : playerToCheck.getField().getAliveCards()) {
                        if (card.getRace() == activator.getRace()) {
                            ++activatorCardCount;
                        }
                    }
                }
                if (activatorCardCount > activator.getThreshold()) {
                    shouldActivate = true;
                }
            } else if (activator.getType() == RuneActivationType.Grave) {
                Player playerToCheck = activator.shouldCheckEnemy() ? enemy : player;
                int activatorCardCount = 0;
                if (activator.getRace() == null) {
                    activatorCardCount = playerToCheck.getGrave().size();
                } else {
                    for (CardInfo card : playerToCheck.getGrave().toList()) {
                        if (card.getRace() == activator.getRace()) {
                            ++activatorCardCount;
                        }
                    }
                }
                if (activatorCardCount > activator.getThreshold()) {
                    shouldActivate = true;
                }
            } else if (activator.getType() == RuneActivationType.Hand) {
                Player playerToCheck = activator.shouldCheckEnemy() ? enemy : player;
                int activatorCardCount = 0;
                if (activator.getRace() == null) {
                    activatorCardCount = playerToCheck.getHand().size();
                } else {
                    for (CardInfo card : playerToCheck.getHand().toList()) {
                        if (card.getRace() == activator.getRace()) {
                            ++activatorCardCount;
                        }
                    }
                }
                if (activatorCardCount > activator.getThreshold()) {
                    shouldActivate = true;
                }
            } else if (activator.getType() == RuneActivationType.Deck) {
                Player playerToCheck = activator.shouldCheckEnemy() ? enemy : player;
                int activatorCardCount = 0;
                if (activator.getRace() == null) {
                    activatorCardCount = playerToCheck.getDeck().size();
                } else {
                    for (CardInfo card : playerToCheck.getDeck().toList()) {
                        if (card.getRace() == activator.getRace()) {
                            ++activatorCardCount;
                        }
                    }
                }
                if (activatorCardCount < activator.getThreshold()) {
                    shouldActivate = true;
                }
            } else if (activator.getType() == RuneActivationType.Round) {
                if (stage.getRound() > activator.getThreshold()) {
                    shouldActivate = true;
                }
            }
            
            if (!shouldActivate) {
                continue;
            }
            
            
            // Special logic for ÓÀ¶³ & ´º·ç & ÇåÈª.
            if (rune.is(RuneData.ÇåÈª)) {
                for (CardInfo card : player.getField().toList()) {
                    if (card.isWounded()) {
                        break;
                    }
                }
                shouldActivate = false;
            } else if (rune.is(RuneData.ÓÀ¶³)) {
                if (enemy.getField().toList().isEmpty()) {
                    shouldActivate = false;
                }
            } else if (rune.is(RuneData.´º·ç)) {
                if (player.getField().size() == 0) {
                    shouldActivate = false;
                }
            }

            if (shouldActivate) {
                this.stage.getUI().activateRune(rune);
                rune.activate();
            }
        }
    }

    public void deactivateRunes(Player player) {
        for (RuneInfo rune : player.getRuneBox().getRunes()) {
            rune.deactivate();
            for (CardInfo card : player.getField().getAliveCards()) {
                for (FeatureEffect effect : card.getEffects()) {
                    if (effect.getCause().equals(rune.getFeatureInfo())) {
                        if (rune.getFeature().getType().containsTag(FeatureTag.ÓÀ¾Ã)) {
                            continue;
                        }
                        if (effect.getType() == FeatureEffectType.ATTACK_CHANGE) {
                            stage.getUI().loseAdjustATEffect(card, effect);
                        } else if (effect.getType() == FeatureEffectType.MAXHP_CHANGE) {
                            stage.getUI().loseAdjustHPEffect(card, effect);
                        } else {
                            throw new CardFantasyRuntimeException("Invalid feature effect type " + effect.getType());
                        }
                        card.removeEffect(effect);
                    }
                }
            }
        }
    }

    public void resolvePreAttackRune(Player attackerHero, Player defenderHero) throws HeroDieSignal {
        for (RuneInfo rune : attackerHero.getRuneBox().getRunes()) {
            if (!rune.isActivated()) {
                continue;
            }
            if (rune.is(RuneData.»ÄÎß)) {
                PoisonMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.ÕÓÔó)) {
                PoisonMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.ÑÒ¾§)) {
                EnergyArmorFeature.apply(this, rune.getFeatureInfo(), rune, 1);
            } else if (rune.is(RuneData.¶¾É°)) {
                PoisonMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.ÉîÔ¨)) {
                PoisonMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.ÔÉĞÇ)) {
                PlagueFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero);
            } else if (rune.is(RuneData.ËÀÓò)) {
                PoisonMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, -1);
            } else if (rune.is(RuneData.Ëª¶³)) {
                IceMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 45);
            } else if (rune.is(RuneData.º®³±)) {
                IceMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 35);
            } else if (rune.is(RuneData.±ù×¶)) {
                IceMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 45);
            } else if (rune.is(RuneData.±©Óê)) {
                WeakenAllFeature.apply(this, rune.getFeatureInfo(), rune, defenderHero);
            } else if (rune.is(RuneData.ÇåÈª)) {
                RainfallFeature.apply(rune.getFeature(), this, rune);
            } else if (rune.is(RuneData.Ñ©±À)) {
                IceMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 35);
            } else if (rune.is(RuneData.Ê¥Èª)) {
                PrayFeature.apply(rune.getFeature(), this, rune);
            } else if (rune.is(RuneData.ÓÀ¶³)) {
                IceMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, -1, 30);
            } else if (rune.is(RuneData.ÉÁµç)) {
                LighteningMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 50);
            } else if (rune.is(RuneData.À×ÔÆ)) {
                LighteningMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 40);
            } else if (rune.is(RuneData.Åùö¨)) {
                LighteningMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 50);
            } else if (rune.is(RuneData.·ÉÓğ)) {
                SnipeFeature.apply(rune.getFeature(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.ì«·ç)) {
                LighteningMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 40);
            } else if (rune.is(RuneData.´º·ç)) {
                EnergyArmorFeature.apply(this, rune.getFeatureInfo(), rune, -1);
            } else if (rune.is(RuneData.À×Óü)) {
                LighteningMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, -1, 35);
            } else if (rune.is(RuneData.»ğÈ­)) {
                FireMagicFeature.apply(rune.getFeature(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.ÈÈÀË)) {
                FireMagicFeature.apply(rune.getFeature(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.Á÷»ğ)) {
                FireMagicFeature.apply(rune.getFeature(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.ºìÁ«)) {
                HealFeature.apply(rune.getFeature(), this, rune);
            } else if (rune.is(RuneData.Ú¤»ğ)) {
                BurningFlameFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero);
            } else if (rune.is(RuneData.´ãÁ¶)) {
                AttackUpFeature.apply(this, rune.getFeatureInfo(), rune, -1);
            } else if (rune.is(RuneData.·ÙÌì)) {
                FireMagicFeature.apply(rune.getFeature(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.×Æ»ê)) {
                HeavenWrathFeature.apply(this, rune.getFeature(), rune, defenderHero);
            } else if (rune.is(RuneData.ÃğÊÀ)) {
                FireMagicFeature.apply(rune.getFeature(), this, rune, defenderHero, -1);
            }
        }
    }

    public void removeOneRoundEffects(Player activePlayer) {
        /*
        for (CardInfo card : activePlayer.getField().toList()) {
            for (FeatureInfo featureInfo : card.getNormalUsableFeatures()) {
            }
        }
        */
    }
}
