package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.RuneActivationType;
import cfvbaibai.cardfantasy.data.RuneActivator;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.engine.feature.AllDelayFeature;
import cfvbaibai.cardfantasy.engine.feature.AllSpeedUpFeature;
import cfvbaibai.cardfantasy.engine.feature.ArouseFeature;
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
import cfvbaibai.cardfantasy.engine.feature.CounterSummonFeature;
import cfvbaibai.cardfantasy.engine.feature.CriticalAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.CurseFeature;
import cfvbaibai.cardfantasy.engine.feature.DestroyFeature;
import cfvbaibai.cardfantasy.engine.feature.DiseaseFeature;
import cfvbaibai.cardfantasy.engine.feature.DodgeFeature;
import cfvbaibai.cardfantasy.engine.feature.EnergyArmorFeature;
import cfvbaibai.cardfantasy.engine.feature.EnergyDrainFeature;
import cfvbaibai.cardfantasy.engine.feature.EnprisonFeature;
import cfvbaibai.cardfantasy.engine.feature.EscapeFeature;
import cfvbaibai.cardfantasy.engine.feature.ExplodeFeature;
import cfvbaibai.cardfantasy.engine.feature.FireMagicFeature;
import cfvbaibai.cardfantasy.engine.feature.GuardFeature;
import cfvbaibai.cardfantasy.engine.feature.HealFeature;
import cfvbaibai.cardfantasy.engine.feature.HealingMistFeature;
import cfvbaibai.cardfantasy.engine.feature.HeavenWrathFeature;
import cfvbaibai.cardfantasy.engine.feature.HeroKillerFeature;
import cfvbaibai.cardfantasy.engine.feature.HolyFireFeature;
import cfvbaibai.cardfantasy.engine.feature.HolyGuardFeature;
import cfvbaibai.cardfantasy.engine.feature.IceArmorFeature;
import cfvbaibai.cardfantasy.engine.feature.IceMagicFeature;
import cfvbaibai.cardfantasy.engine.feature.ImmobilityFeature;
import cfvbaibai.cardfantasy.engine.feature.ImmueFeature;
import cfvbaibai.cardfantasy.engine.feature.LegionBuffFeature;
import cfvbaibai.cardfantasy.engine.feature.LighteningMagicFeature;
import cfvbaibai.cardfantasy.engine.feature.MagicShieldFeature;
import cfvbaibai.cardfantasy.engine.feature.ManaErodeFeature;
import cfvbaibai.cardfantasy.engine.feature.NoEffectFeature;
import cfvbaibai.cardfantasy.engine.feature.OneDelayFeature;
import cfvbaibai.cardfantasy.engine.feature.OverdrawFeature;
import cfvbaibai.cardfantasy.engine.feature.PenetrationFeature;
import cfvbaibai.cardfantasy.engine.feature.PlagueFeature;
import cfvbaibai.cardfantasy.engine.feature.PoisonMagicFeature;
import cfvbaibai.cardfantasy.engine.feature.PrayFeature;
import cfvbaibai.cardfantasy.engine.feature.PurifyFeature;
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
import cfvbaibai.cardfantasy.engine.feature.SoftenFeature;
import cfvbaibai.cardfantasy.engine.feature.SpeedUpFeature;
import cfvbaibai.cardfantasy.engine.feature.SpikeFeature;
import cfvbaibai.cardfantasy.engine.feature.SummonFeature;
import cfvbaibai.cardfantasy.engine.feature.TransportFeature;
import cfvbaibai.cardfantasy.engine.feature.TrapFeature;
import cfvbaibai.cardfantasy.engine.feature.TsukomiFeature;
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
    
    private boolean isPhysicalAttackFeature(Skill skill) {
        return skill == null || skill.getType().containsTag(SkillTag.物理攻击);
    }
    
    public void removeStatus(CardInfo card, CardStatusType statusType) {
        if (card == null) {
            return;
        }
        if (card.removeStatus(statusType)) {
            this.stage.getUI().removeCardStatus(card, statusType);
        }
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
            if (feature.getType() == FeatureType.透支) {
                OverdrawFeature.apply(this, feature, attacker);
            }
        }
        for (FeatureInfo feature : attacker.getNormalUsableFeatures()) {
            if (attacker.isDead()) {
                continue;
            }
            if (feature.getType() == FeatureType.未知) {
                // JUST A PLACEHOLDER
            } else if (feature.getType() == FeatureType.关小黑屋) {
                EnprisonFeature.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == FeatureType.吐槽) {
                TsukomiFeature.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == FeatureType.火球) {
                FireMagicFeature.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.火墙) {
                FireMagicFeature.apply(feature.getFeature(), this, attacker, defender, 3);
            } else if (feature.getType() == FeatureType.烈焰风暴) {
                FireMagicFeature.apply(feature.getFeature(), this, attacker, defender, -1);
            } else if (feature.getType() == FeatureType.落雷) {
                LighteningMagicFeature.apply(feature, this, attacker, defender, 1, 50);
            } else if (feature.getType() == FeatureType.连环闪电) {
                LighteningMagicFeature.apply(feature, this, attacker, defender, 3, 40);
            } else if (feature.getType() == FeatureType.雷暴) {
                LighteningMagicFeature.apply(feature, this, attacker, defender, -1, 35);
            } else if (feature.getType() == FeatureType.冰弹) {
                IceMagicFeature.apply(feature, this, attacker, defender, 1, 45, 0);
            } else if (feature.getType() == FeatureType.霜冻新星) {
                IceMagicFeature.apply(feature, this, attacker, defender, 3, 35, 0);
            } else if (feature.getType() == FeatureType.暴风雪) {
                IceMagicFeature.apply(feature, this, attacker, defender, -1, 30, 0);
            } else if (feature.getType() == FeatureType.寒霜冲击) {
                IceMagicFeature.apply(feature, this, attacker, defender, -1, 50, 45 * defender.getField().getAliveCards().size());
            } else if (feature.getType() == FeatureType.毒液) {
                PoisonMagicFeature.apply(feature, this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.毒雾) {
                PoisonMagicFeature.apply(feature, this, attacker, defender, 3);
            } else if (feature.getType() == FeatureType.毒云) {
                PoisonMagicFeature.apply(feature, this, attacker, defender, -1);
            } else if (feature.getType() == FeatureType.陷阱) {
                TrapFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.狙击) {
                SnipeFeature.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.魔神之刃) {
                SnipeFeature.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.治疗) {
                HealFeature.apply(feature.getFeature(), this, attacker);
            } else if (feature.getType() == FeatureType.甘霖) {
                RainfallFeature.apply(feature.getFeature(), this, attacker);
            } else if (feature.getType() == FeatureType.治疗之雾) {
                HealingMistFeature.apply(feature.getFeature(), this, attacker);
            } else if (feature.getType() == FeatureType.祈祷) {
                PrayFeature.apply(feature.getFeature(), this, attacker);
            } else if (feature.getType() == FeatureType.复活) {
                ReviveFeature.apply(this, feature, attacker);
            } else if (feature.getType() == FeatureType.背刺) {
                BackStabFeature.apply(this, feature, attacker);
            } else if (feature.getType() == FeatureType.群体削弱) {
                WeakenAllFeature.apply(this, feature, attacker, defender);
            } else if (feature.getType() == FeatureType.回魂) {
                ResurrectionFeature.apply(this, feature, attacker);
            } else if (feature.getType() == FeatureType.二重狙击) {
                SnipeFeature.apply(feature.getFeature(), this, attacker, defender, 2);
            } else if (feature.getType() == FeatureType.迷魂) {
                ConfusionFeature.apply(feature, this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.烈火焚神) {
                BurningFlameFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.诅咒) {
                CurseFeature.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == FeatureType.魔神之咒) {
                CurseFeature.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == FeatureType.摧毁) {
                DestroyFeature.apply(this, feature.getFeature(), attacker, defender, 1);
            } else if (feature.getType() == FeatureType.瘟疫) {
                PlagueFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.血炼) {
                BloodPaintFeature.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.鲜血盛宴) {
                BloodPaintFeature.apply(feature.getFeature(), this, attacker, defender, -1);
            } else if (feature.getType() == FeatureType.天谴) {
                HeavenWrathFeature.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == FeatureType.封印) {
                SealFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.圣炎) {
                HolyFireFeature.apply(feature.getFeature(), this, attacker, defender);
            } else if (feature.getType() == FeatureType.法力侵蚀) {
                ManaErodeFeature.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == FeatureType.趁胜追击) {
                WinningPursuitFeature.apply(this, feature, attacker, defender);
            } else if (feature.getType() == FeatureType.复仇) {
                RevengeFeature.apply(this, feature, attacker);
            } else if (feature.getType() == FeatureType.振奋) {
                ArouseFeature.apply(this, feature, attacker);
            } else if (feature.getType() == FeatureType.全体阻碍){
                AllDelayFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.全体加速){
                AllSpeedUpFeature.apply(feature, this, attacker);
            } else if (feature.getType() == FeatureType.阻碍) {
                OneDelayFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.加速) {
                SpeedUpFeature.apply(feature, this, attacker);
            } else if (feature.getType() == FeatureType.净化) {
                PurifyFeature.apply(feature, this, attacker);
            } else if (feature.getType() == FeatureType.虚弱) {
                SoftenFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == FeatureType.召唤王国战士) {
                SummonFeature.apply(this, feature, attacker, "圣骑士", "魔剑士");
            } else if (feature.getType() == FeatureType.召唤噩梦护卫) {
                SummonFeature.apply(this, feature, attacker, "时光女神", "金属巨龙");
            } else if (feature.getType() == FeatureType.召唤邪龙护卫) {
                SummonFeature.apply(this, feature, attacker, "亡灵守护神", "光明之龙");
            } else if (feature.getType() == FeatureType.召唤花仙子) {
                SummonFeature.apply(this, feature, attacker, "花仙子", "花仙子");
            } else if (feature.getType() == FeatureType.召唤火焰乌鸦) {
                SummonFeature.apply(this, feature, attacker, "火焰乌鸦");
            } else if (feature.getType() == FeatureType.召唤人马巡逻者) {
                SummonFeature.apply(this, feature, attacker, "人马巡逻者", "人马巡逻者");
            } else if (feature.getType() == FeatureType.召唤女神侍者) {
                SummonFeature.apply(this, feature, attacker, "女神侍者", "女神侍者");
            } else if (feature.getType() == FeatureType.召唤树人守护者) {
                SummonFeature.apply(this, feature, attacker, "霜雪树人", "树人祭司");
            }
        }
        RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.飞岩);
        if (rune != null && !attacker.isWeak()) {
            SnipeFeature.apply(rune.getFeature(), this, attacker, defender, 1);
        }
    }

    public void resolvePostAttackFeature(CardInfo attacker, Player defender) {

    }

    public void resolveCounterAttackFeature(CardInfo attacker, CardInfo defender, Skill attackFeature,
            OnAttackBlockingResult result, OnDamagedResult damagedResult) throws HeroDieSignal {
        if (isPhysicalAttackFeature(attackFeature)) {
            for (FeatureInfo feature : defender.getNormalUsableFeatures()) {
                if (feature.getType() == FeatureType.反击) {
                    CounterAttackFeature.apply(feature.getFeature(), this, attacker, defender, result.getDamage());
                } else if (feature.getType() == FeatureType.盾刺) {
                    SpikeFeature.apply(feature.getFeature(), this, attacker, defender, attackFeature, result.getDamage());
                } else if (feature.getType() == FeatureType.魔神之甲) {
                    SpikeFeature.apply(feature.getFeature(), this, attacker, defender, attackFeature, result.getDamage());
                } else if (feature.getType() == FeatureType.燃烧) {
                    BurningFeature.apply(feature, this, attacker, defender);
                } else if (feature.getType() == FeatureType.邪灵汲取) {
                    EnergyDrainFeature.apply(feature, this, attacker, defender, result, damagedResult);
                } else if (feature.getType() == FeatureType.被插出五星) {
                    CounterSummonFeature.apply(this, defender, feature.getFeature(), 5);
                }
            }
            {
                RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.雷盾);
                if (rune != null && !defender.isWeak()) {
                    SpikeFeature.apply(rune.getFeature(), this, attacker, defender, attackFeature, result.getDamage());
                }
            }
            {
                RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.漩涡);
                if (rune != null && !defender.isWeak()) {
                    CounterAttackFeature.apply(rune.getFeature(), this, attacker, defender, result.getDamage());
                }
            }
            if (!defender.isDead()) {
                for (FeatureInfo feature : defender.getNormalUsableFeatures()) {
                    if (feature.getType() == FeatureType.狂热) {
                        ZealotFeature.apply(feature, this, attacker, defender, result);
                    }
                }
                RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.怒涛);
                if (rune != null && !defender.isWeak()) {
                    ZealotFeature.apply(rune.getFeatureInfo(), this, attacker, defender, result);
                }
            }
        }
    }

    public OnAttackBlockingResult resolveHealBlockingFeature(EntityInfo healer, CardInfo healee, Skill cardFeature) {
        OnAttackBlockingResult result = new OnAttackBlockingResult(true, cardFeature.getImpact());
        if (healee.getStatus().containsStatus(CardStatusType.裂伤)) {
            stage.getUI().healBlocked(healer, healee, cardFeature, null);
            result.setAttackable(false);
        }
        return result;
    }

    public OnAttackBlockingResult resolveAttackBlockingFeature(EntityInfo attacker, CardInfo defender,
            Skill attackFeature, int damage) throws HeroDieSignal {
        OnAttackBlockingResult result = new OnAttackBlockingResult(true, 0);
        CardStatus status = attacker.getStatus();
        if (isPhysicalAttackFeature(attackFeature)) {
            // Physical attack could be blocked by Dodge or 麻痹, 冰冻, 锁定, 迷惑, 复活 status.
            CardInfo cardAttacker = (CardInfo) attacker;
            result.setDamage(damage);
            if (status.containsStatus(CardStatusType.冰冻) || status.containsStatus(CardStatusType.麻痹)
                    || status.containsStatus(CardStatusType.锁定) || status.containsStatus(CardStatusType.复活)) {
                stage.getUI().attackBlocked(cardAttacker, defender, attackFeature, null);
                result.setAttackable(false);
            } else {
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.闪避) {
                        result.setAttackable(!DodgeFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, result.getDamage()));
                        if (!result.isAttackable()) {
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.轻灵);
                    if (rune != null && !defender.isWeak()) {
                        result.setAttackable(!DodgeFeature.apply(rune.getFeature(), this, cardAttacker, defender,
                                result.getDamage()));
                        if (!result.isAttackable()) {
                            return result;
                        }
                    }
                }
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.王国之盾) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.HELL));
                    }
                    if (blockFeature.getType() == FeatureType.森林之盾) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.SAVAGE));
                    }
                    if (blockFeature.getType() == FeatureType.蛮荒之盾) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.KINGDOM));
                    }
                    if (blockFeature.getType() == FeatureType.地狱之盾) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.FOREST));
                    }
                }
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.格挡) {
                        result.setDamage(BlockFeature.apply(blockFeature.getFeature(), this, cardAttacker, defender,
                                defender, result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.冰甲) {
                        result.setDamage(IceArmorFeature.apply(blockFeature.getFeature(), this, cardAttacker, defender,
                                result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.冰封);
                    if (rune != null && !defender.isWeak()) {
                        result.setDamage(IceArmorFeature.apply(rune.getFeature(), this, cardAttacker, defender,
                                result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.岩壁);
                    if (rune != null && !defender.isWeak()) {
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
            boolean isAttackerDisabled = status.containsStatus(CardStatusType.冰冻)
                    || status.containsStatus(CardStatusType.锁定)
                    || status.containsStatus(CardStatusType.复活);
            if (!attackFeature.isDeathFeature() && isAttackerDisabled) {
                stage.getUI().attackBlocked(attacker, defender, attackFeature, null);
                result.setAttackable(false);
            } else {
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.法力反射) {
                        if (CounterMagicFeature.isFeatureBlocked(this, blockFeature.getFeature(), attackFeature,
                                attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getRuneBox().getRuneOf(RuneData.石林);
                    if (rune != null && rune.isActivated() && !defender.isWeak()) {
                        if (CounterMagicFeature.isFeatureBlocked(this, rune.getFeature(), attackFeature, attacker,
                                defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }

                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.免疫) {
                        if (ImmueFeature.isFeatureBlocked(this, blockFeature.getFeature(), attackFeature, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockFeature.getType() == FeatureType.无效) {
                        if (NoEffectFeature.isFeatureBlocked(this, blockFeature.getFeature(), attackFeature, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockFeature.getType() == FeatureType.脱困) {
                        if (EscapeFeature.isFeatureEscaped(this, blockFeature.getFeature(), attackFeature, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockFeature.getType() == FeatureType.不动) {
                        if (ImmobilityFeature.isFeatureBlocked(this, blockFeature.getFeature(), attackFeature, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.鬼步);
                    if (rune != null && !defender.isWeak()) {
                        if (EscapeFeature.isFeatureEscaped(this, rune.getFeature(), attackFeature, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                for (FeatureInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == FeatureType.魔甲) {
                        result.setDamage(MagicShieldFeature.apply(this, blockFeature.getFeature(), attacker, defender,
                                attackFeature, result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.炎甲);
                    if (rune != null && !defender.isWeak()) {
                        result.setDamage(MagicShieldFeature.apply(this, rune.getFeature(), attacker, defender,
                                attackFeature, result.getDamage()));
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
    public void resolveDeathFeature(EntityInfo killerCard, CardInfo deadCard, Skill cardFeature) throws HeroDieSignal {
        if (deadCard.hasDeadOnce()) {
            return;
        }
        deadCard.setDeadOnce(true);
        resolveLeaveFeature(deadCard, cardFeature);
        for (FeatureInfo deadCardFeature : deadCard.getUsableDeathFeatures()) {
            if (deadCardFeature.getType() == FeatureType.烈焰风暴) {
                FireMagicFeature.apply(deadCardFeature.getFeature(), this, deadCard, killerCard.getOwner(), -1);
            } else if (deadCardFeature.getType() == FeatureType.雷暴) {
                LighteningMagicFeature.apply(deadCardFeature, this, deadCard, killerCard.getOwner(), -1, 35);
            } else if (deadCardFeature.getType() == FeatureType.暴风雪) {
                IceMagicFeature.apply(deadCardFeature, this, deadCard, killerCard.getOwner(), -1, 30, 0);
            } else if (deadCardFeature.getType() == FeatureType.毒云) {
                PoisonMagicFeature.apply(deadCardFeature, this, deadCard, killerCard.getOwner(), -1);
            } else if (deadCardFeature.getType() == FeatureType.瘟疫) {
                PlagueFeature.apply(deadCardFeature, this, deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == FeatureType.治疗) {
                HealFeature.apply(deadCardFeature.getFeature(), this, deadCard.getOwner());
            } else if (deadCardFeature.getType() == FeatureType.甘霖) {
                RainfallFeature.apply(deadCardFeature.getFeature(), this, deadCard.getOwner());
            } else if (deadCardFeature.getType() == FeatureType.祈祷) {
                PrayFeature.apply(deadCardFeature.getFeature(), this, deadCard);
            } else if (deadCardFeature.getType() == FeatureType.诅咒) {
                CurseFeature.apply(this, deadCardFeature.getFeature(), deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == FeatureType.群体削弱) {
                WeakenAllFeature.apply(this, deadCardFeature, deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == FeatureType.烈火焚神) {
                BurningFlameFeature.apply(deadCardFeature, this, deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == FeatureType.陷阱) {
                TrapFeature.apply(deadCardFeature, this, deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == FeatureType.复活) {
                ReviveFeature.apply(this, deadCardFeature, deadCard);
            } else if (deadCardFeature.getType() == FeatureType.摧毁) {
                DestroyFeature.apply(this, deadCardFeature.getFeature(), deadCard, killerCard.getOwner(), 1);
            } else if (deadCardFeature.getType() == FeatureType.传送) {
                TransportFeature.apply(this, deadCardFeature.getFeature(), deadCard, killerCard.getOwner());
            } 
        }
        for (FeatureInfo deadCardFeature : deadCard.getAllUsableFeatures()) {
            if (deadCardFeature.getType() == FeatureType.自爆) {
                ExplodeFeature.apply(this, deadCardFeature.getFeature(), killerCard, deadCard);
            }
        }
        {
            RuneInfo rune = deadCard.getOwner().getActiveRuneOf(RuneData.爆裂);
            if (rune != null && !deadCard.isWeak()) {
                ExplodeFeature.apply(this, rune.getFeature(), killerCard, deadCard);
            }
        }
        boolean reincarnated = false;
        for (FeatureInfo deadCardFeature : deadCard.getAllUsableFeatures()) {
            if (deadCardFeature.getType() == FeatureType.转生) {
                if (ReincarnationFeature.apply(this, deadCardFeature.getFeature(), deadCard)) {
                    reincarnated = true;
                    break;
                }
            }
        }
        if (!reincarnated) {
            RuneInfo rune = deadCard.getOwner().getActiveRuneOf(RuneData.秽土);
            if (rune != null && !deadCard.isWeak()) {
                ReincarnationFeature.apply(this, rune.getFeature(), deadCard);
            }
        }
    }

    public void resolvePostAttackFeature(CardInfo attacker, CardInfo defender, Player defenderHero, Skill attackFeature,
            int normalAttackDamage) throws HeroDieSignal {
        for (FeatureInfo feature : attacker.getNormalUsableFeatures()) {
            if (!attacker.isDead()) {
                if (feature.getType() == FeatureType.吸血) {
                    BloodDrainFeature.apply(feature.getFeature(), this, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (!attacker.isDead()) {
            RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.赤谷);
            if (rune != null && !attacker.isWeak()) {
                BloodDrainFeature.apply(rune.getFeature(), this, attacker, defender, normalAttackDamage);
            }
        }
    }
    
    public void resolveExtraAttackFeature(CardInfo attacker, CardInfo defender, Player defenderHero, Skill attackFeature,
            int normalAttackDamage) throws HeroDieSignal {

        for (FeatureInfo feature : attacker.getNormalUsableFeatures()) {
            if (!attacker.isDead()) {
                if (feature.getType() == FeatureType.穿刺) {
                    PenetrationFeature.apply(feature.getFeature(), this, attacker, defenderHero, normalAttackDamage);
                } else if (feature.getType() == FeatureType.削弱) {
                    WeakenFeature.apply(this, feature, attacker, defender, normalAttackDamage);
                } else if (feature.getType() == FeatureType.裂伤) {
                    WoundFeature.apply(this, feature, attackFeature, attacker, defender, normalAttackDamage);
                } else if (feature.getType() == FeatureType.嗜血) {
                    BloodThirstyFeature.apply(this, feature, attacker, normalAttackDamage);
                } else if (feature.getType() == FeatureType.连锁攻击) {
                    ChainAttackFeature.apply(this, feature, attacker, defender, attackFeature);
                } else if (feature.getType() == FeatureType.疾病) {
                    DiseaseFeature.apply(feature, this, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (!attacker.isDead()) {
            RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.洞察);
            if (rune != null && !attacker.isWeak()) {
                BloodThirstyFeature.apply(this, rune.getFeatureInfo(), attacker, normalAttackDamage);
            }
        }
    }

    public void resolvePreAttackHeroFeature(CardInfo attacker, Player defenderPlayer) {
        for (FeatureInfo feature : attacker.getNormalUsableFeatures()) {
           if (feature.getType() == FeatureType.英雄杀手) {
               HeroKillerFeature.apply(this, feature, attacker, defenderPlayer);
           }
        }
    }
    
    /**
     * 
     * @param attacker
     * @param defender
     * @param prior if TRUE, this is resolved before pre-attack features are resolved.
     *              Currently, only RETURN falls in this case.
     * @throws HeroDieSignal
     */
    public void resolvePreAttackCardFeature(CardInfo attacker, CardInfo defender, boolean prior) throws HeroDieSignal {
        for (FeatureInfo feature : attacker.getNormalUsableFeatures()) {
            if (prior) {
                if (feature.getType() == FeatureType.送还) {
                    ReturnFeature.apply(this, feature.getFeature(), attacker, defender);
                }
            } else {
                if (feature.getType() == FeatureType.圣光) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.HELL);
                } else if (feature.getType() == FeatureType.要害) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.SAVAGE);
                } else if (feature.getType() == FeatureType.暗杀) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.KINGDOM);
                } else if (feature.getType() == FeatureType.污染) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.FOREST);
                } else if (feature.getType() == FeatureType.暴击) {
                    CriticalAttackFeature.apply(this, feature, attacker, defender);
                } else if (feature.getType() == FeatureType.穷追猛打) {
                    PursuitFeature.apply(this, feature, attacker, defender);
                } else if (feature.getType() == FeatureType.战意) {
                    WarthFeature.apply(this, feature, attacker, defender);
                }
            }
        }
        if (!prior) {
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.绝杀);
                if (rune != null && !attacker.isWeak()) {
                    WarthFeature.apply(this, rune.getFeatureInfo(), attacker, defender);
                }
            }
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.寒伤);
                if (rune != null && !attacker.isWeak()) {
                    CriticalAttackFeature.apply(this, rune.getFeatureInfo(), attacker, defender);
                }
            }
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.扬旗);
                if (rune != null && !attacker.isWeak()) {
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
            if (type == FeatureType.圣光 || type == FeatureType.要害 || type == FeatureType.暗杀 || type == FeatureType.污染) {
                RacialAttackFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.暴击) {
                CriticalAttackFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.穷追猛打) {
                PursuitFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.背刺) {
                BackStabFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.战意) {
                WarthFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.趁胜追击) {
                WinningPursuitFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.复仇) {
                RevengeFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.振奋) {
                ArouseFeature.remove(this, effect.getCause(), card);
            } else if (type == FeatureType.英雄杀手) {
                HeroKillerFeature.remove(this, effect.getCause(), card);
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
            // 由于技能多重触发可能造成cardDead被多次调用
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
                if (!card.getStatus().containsStatus(CardStatusType.召唤)) {
                    // 被召唤的卡牌不进入墓地，而是直接死亡
                    owner.getGrave().addCard(card);
                }
                break;
            }
        }
    }

    public void attackHero(EntityInfo attacker, Player defenderPlayer, Skill cardFeature, int damage)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        try {
            if (isPhysicalAttackFeature(cardFeature) && attacker.getStatus().containsStatus(CardStatusType.麻痹)) {
                return;
            }
            stage.getUI().useSkillToHero(attacker, defenderPlayer, cardFeature);
            if (damage > defenderPlayer.getHP()) {
                damage = defenderPlayer.getHP();
            }
            if (damage >= 0) {
                int remainingDamage = this.resolveAttackHeroBlockingFeatures(attacker, defenderPlayer, cardFeature, damage);
                if (remainingDamage > 0) {
                    stage.getUI().attackHero(attacker, defenderPlayer, cardFeature, remainingDamage);
                    defenderPlayer.setHP(defenderPlayer.getHP() - remainingDamage);
                }
            } else {
                stage.getUI().healHero(attacker, defenderPlayer, cardFeature, -damage);
                defenderPlayer.setHP(defenderPlayer.getHP() - damage);
            }
        } finally {
            if (attacker instanceof CardInfo) {
                CardInfo attackerCard = (CardInfo)attacker;
                if (attackerCard.removeStatus(CardStatusType.麻痹)) {
                    this.stage.getUI().removeCardStatus(attackerCard, CardStatusType.麻痹);
                }
            }
        }
    }

    private int resolveAttackHeroBlockingFeatures(EntityInfo attacker, Player defenderPlayer, Skill cardFeature,
            int damage) throws HeroDieSignal {
        int remainingDamage = damage;
        for (CardInfo defender : defenderPlayer.getField().getAliveCards()) {
            if (defender == null) {
                continue;
            }
            for (FeatureInfo defenderFeature : defender.getNormalUsableFeatures()) {
                if (defenderFeature.getType() == FeatureType.守护) {
                    remainingDamage = GuardFeature.apply(defenderFeature.getFeature(), cardFeature, this, attacker, defender, remainingDamage);
                    if (remainingDamage == 0) {
                        return 0;
                    }
                }
            }
        }
        return remainingDamage;
    }

    public void resolveCardRoundEndingFeature(CardInfo card) {
        if (card == null) {
            return;
        }
        CardStatus status = card.getStatus();
        if (status.containsStatus(CardStatusType.锁定)) {
            return;
        }
        for (FeatureInfo cardFeature : card.getNormalUsableFeatures()) {
            if (cardFeature.getType() == FeatureType.回春) {
                RejuvenateFeature.apply(cardFeature.getFeature(), this, card);
            }
        }
        {
            RuneInfo rune = card.getOwner().getActiveRuneOf(RuneData.复苏);
            if (rune != null && !card.isWeak()) {
                RejuvenateFeature.apply(rune.getFeature(), this, card);
            }
        }
    }

    public OnDamagedResult attackCard(CardInfo attacker, CardInfo defender, FeatureInfo featureInfo) throws HeroDieSignal {
        return attackCard(attacker, defender, featureInfo, attacker.getCurrentAT());
    }

    public OnDamagedResult attackCard(CardInfo attacker, CardInfo defender, FeatureInfo featureInfo, int damage) throws HeroDieSignal {
        Skill skill = featureInfo == null ? null : featureInfo.getFeature();
        boolean bingo = !attacker.getStatus().containsStatus(CardStatusType.麻痹);
        this.stage.getUI().useSkill(attacker, defender, null, bingo);

        OnAttackBlockingResult blockingResult = stage.getResolver().resolveAttackBlockingFeature(
                attacker, defender, skill, damage);
        if (!blockingResult.isAttackable()) {
            return null;
        }
        this.stage.getUI().attackCard(attacker, defender, skill, blockingResult.getDamage());
        OnDamagedResult damagedResult = stage.getResolver().applyDamage(defender, blockingResult.getDamage());

        resolvePostAttackFeature(attacker, defender, defender.getOwner(), skill, damagedResult.actualDamage);
        if (damagedResult.cardDead) {
            stage.getResolver().resolveDeathFeature(attacker, defender, skill);
        }
        resolveExtraAttackFeature(attacker, defender, defender.getOwner(), skill, damagedResult.actualDamage);
        resolveCounterAttackFeature(attacker, defender, skill, blockingResult, damagedResult);

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

    // reviver: for most of the cases, it should be null.
    // It is only set when the summoning feature performer is revived by another card.
    public void resolveSummoningFeature(CardInfo card, Field myField, Field opField, CardInfo reviver) throws HeroDieSignal {
        LegionBuffFeature.apply(this, card);
        for (CardInfo fieldCard : myField.getAliveCards()) {
            for (FeatureInfo feature : fieldCard.getNormalUsableFeatures()) {
                if (feature.getType() == FeatureType.王国之力) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.KINGDOM, FeatureEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == FeatureType.王国守护) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.KINGDOM, FeatureEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == FeatureType.森林之力) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.FOREST, FeatureEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == FeatureType.森林守护) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.FOREST, FeatureEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == FeatureType.蛮荒之力) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.SAVAGE, FeatureEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == FeatureType.蛮荒守护) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.SAVAGE, FeatureEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == FeatureType.地狱之力) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.HELL, FeatureEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == FeatureType.地狱守护) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.HELL, FeatureEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == FeatureType.本源之力) {
                    RaceBuffFeature.apply(this, feature, fieldCard, null, FeatureEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == FeatureType.本源守护) {
                    RaceBuffFeature.apply(this, feature, fieldCard, null, FeatureEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == FeatureType.神圣守护) {
                    HolyGuardFeature.apply(this, feature, fieldCard);
                } 
            }
        }
        for (FeatureInfo feature : card.getNormalUsableFeatures()) {
            if (feature.getType() == FeatureType.献祭) {
                SacrificeFeature.apply(this, feature, card, reviver);
            } else if (feature.getType() == FeatureType.反噬) {
                CounterBiteFeature.apply(feature, this, card);
            }
        }
        for (FeatureInfo feature : card.getUsableSummonFeatures()) {
            if (feature.getType() == FeatureType.烈焰风暴) {
                FireMagicFeature.apply(feature.getFeature(), this, card, opField.getOwner(), -1);
            } else if (feature.getType() == FeatureType.雷暴) {
                LighteningMagicFeature.apply(feature, this, card, opField.getOwner(), -1, 35);
            } else if (feature.getType() == FeatureType.暴风雪) {
                IceMagicFeature.apply(feature, this, card, opField.getOwner(), -1, 30, 0);
            } else if (feature.getType() == FeatureType.毒云) {
                PoisonMagicFeature.apply(feature, this, card, opField.getOwner(), -1);
            } else if (feature.getType() == FeatureType.瘟疫) {
                PlagueFeature.apply(feature, this, card, opField.getOwner());
            } else if (feature.getType() == FeatureType.治疗) {
                HealFeature.apply(feature.getFeature(), this, card);
            } else if (feature.getType() == FeatureType.甘霖) {
                RainfallFeature.apply(feature.getFeature(), this, card);
            } else if (feature.getType() == FeatureType.祈祷) {
                PrayFeature.apply(feature.getFeature(), this, card);
            } else if (feature.getType() == FeatureType.诅咒) {
                CurseFeature.apply(this, feature.getFeature(), card, opField.getOwner());
            } else if (feature.getType() == FeatureType.群体削弱) {
                WeakenAllFeature.apply(this, feature, card, opField.getOwner());
            } else if (feature.getType() == FeatureType.烈火焚神) {
                BurningFlameFeature.apply(feature, this, card, opField.getOwner());
            } else if (feature.getType() == FeatureType.陷阱) {
                TrapFeature.apply(feature, this, card, opField.getOwner());
            } else if (feature.getType() == FeatureType.送还) {
                ReturnFeature.apply(this, feature.getFeature(), card, opField.getCard(card.getPosition()));
            } else if (feature.getType() == FeatureType.摧毁) {
                DestroyFeature.apply(this, feature.getFeature(), card, opField.getOwner(), 1);
            } else if (feature.getType() == FeatureType.传送) {
                TransportFeature.apply(this, feature.getFeature(), card, opField.getOwner());
            } else if (feature.getType() == FeatureType.复活) {
                ReviveFeature.apply(this, feature, card);
            } else if (feature.getType() == FeatureType.关小黑屋) {
                EnprisonFeature.apply(this, feature.getFeature(), card, opField.getOwner());
            } else if (feature.getType() == FeatureType.净化 || feature.getType() == FeatureType.神性祈求){
                PurifyFeature.apply(feature, this, card);
            }
        }
    }

    public void resolveLeaveFeature(CardInfo card, Skill cardFeature) {
        for (FeatureInfo deadCardFeature : card.getNormalUsableFeatures()) {
            if (deadCardFeature.getType() == FeatureType.王国之力) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.KINGDOM);
            } else if (deadCardFeature.getType() == FeatureType.王国守护) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.KINGDOM);
            } else if (deadCardFeature.getType() == FeatureType.森林之力) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.FOREST);
            } else if (deadCardFeature.getType() == FeatureType.森林守护) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.FOREST);
            } else if (deadCardFeature.getType() == FeatureType.蛮荒之力) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.SAVAGE);
            } else if (deadCardFeature.getType() == FeatureType.蛮荒守护) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.SAVAGE);
            } else if (deadCardFeature.getType() == FeatureType.地狱之力) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.HELL);
            } else if (deadCardFeature.getType() == FeatureType.地狱守护) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.HELL);
            } else if (deadCardFeature.getType() == FeatureType.本源之力) {
                RaceBuffFeature.remove(this, deadCardFeature, card, null);
            } else if (deadCardFeature.getType() == FeatureType.本源守护) {
                RaceBuffFeature.remove(this, deadCardFeature, card, null);
            } else if (deadCardFeature.getType() == FeatureType.神圣守护) {
                HolyGuardFeature.remove(this, deadCardFeature, card);
            } else if (deadCardFeature.getType() == FeatureType.军团王国之力
                    || deadCardFeature.getType() == FeatureType.军团森林之力
                    || deadCardFeature.getType() == FeatureType.军团蛮荒之力
                    || deadCardFeature.getType() == FeatureType.军团地狱之力) {
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
            if (blockFeature.getType() == FeatureType.脱困) {
                blocked = EscapeFeature.isStatusEscaped(blockFeature.getFeature(), this, item, victim);
            }
        }
        return new BlockStatusResult(blocked);
    }

    public void summonCard(Player player, CardInfo card, CardInfo reviver) throws HeroDieSignal {
        card.reset();
        //card.setFirstRound(true);
        this.stage.getUI().summonCard(player, card);
        player.getField().addCard(card);
        if (this.stage.getPlayerCount() != 2) {
            throw new CardFantasyRuntimeException("There are " + this.stage.getPlayerCount()
                    + " player(s) in the stage, but expect 2");
        }
        for (Player other : stage.getPlayers()) {
            if (other != player) {
                stage.getResolver().resolveSummoningFeature(card, player.getField(), other.getField(), reviver);
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
    public boolean resolveCounterBlockFeature(Skill cardFeature, CardInfo attacker, CardInfo defender) {
        for (FeatureInfo attackerFeature : attacker.getAllUsableFeatures()) {
            if (attackerFeature.getType() == FeatureType.弱点攻击) {
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

            // Special logic for 永冻 & 春风 & 清泉 & 冰封 & 灼魂.
            if (rune.is(RuneData.清泉)) {
                if (player.getField().size() == 0) {
                    shouldActivate = false;
                } else {
                    boolean anyCardWounded = false;
                    for (CardInfo card : player.getField().toList()) {
                        if (card.isWounded()) {
                            anyCardWounded = true;
                            break;
                        }
                    }
                    if (!anyCardWounded) {
                        shouldActivate = false;
                    }
                }
            } else if (rune.is(RuneData.永冻) || rune.is(RuneData.灼魂)) {
                if (enemy.getField().toList().isEmpty()) {
                    shouldActivate = false;
                }
            } else if (rune.is(RuneData.春风) || rune.is(RuneData.冰封)) {
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
            if (!rune.isActivated()) {
                continue;
            }
            stage.getUI().deactivateRune(rune);
            rune.deactivate();
            for (CardInfo card : player.getField().getAliveCards()) {
                for (FeatureEffect effect : card.getEffects()) {
                    if (effect.getCause().equals(rune.getFeatureInfo())) {
                        if (rune.getFeature().getType().containsTag(SkillTag.永久)) {
                            continue;
                        }
                        if (effect.getType() == FeatureEffectType.ATTACK_CHANGE) {
                            stage.getUI().loseAdjustATEffect(card, effect);
                        } else if (effect.getType() == FeatureEffectType.MAXHP_CHANGE) {
                            stage.getUI().loseAdjustHPEffect(card, effect);
                        } else if (effect.getType() == FeatureEffectType.SKILL_USED) {
                            // DO NOTHING..
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
            if (rune.is(RuneData.荒芜)) {
                PoisonMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.沼泽)) {
                PoisonMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.岩晶)) {
                EnergyArmorFeature.apply(this, rune.getFeatureInfo(), rune, 1);
            } else if (rune.is(RuneData.毒砂)) {
                PoisonMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.深渊)) {
                PoisonMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.陨星)) {
                PlagueFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero);
            } else if (rune.is(RuneData.死域)) {
                PoisonMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, -1);
            } else if (rune.is(RuneData.霜冻)) {
                IceMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 45, 0);
            } else if (rune.is(RuneData.寒潮)) {
                IceMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 35, 0);
            } else if (rune.is(RuneData.冰锥)) {
                IceMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 45, 0);
            } else if (rune.is(RuneData.暴雨)) {
                WeakenAllFeature.apply(this, rune.getFeatureInfo(), rune, defenderHero);
            } else if (rune.is(RuneData.清泉)) {
                RainfallFeature.apply(rune.getFeature(), this, rune);
            } else if (rune.is(RuneData.雪崩)) {
                IceMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 35, 0);
            } else if (rune.is(RuneData.圣泉)) {
                PrayFeature.apply(rune.getFeature(), this, rune);
            } else if (rune.is(RuneData.永冻)) {
                IceMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, -1, 30, 0);
            } else if (rune.is(RuneData.闪电)) {
                LighteningMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 50);
            } else if (rune.is(RuneData.雷云)) {
                LighteningMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 40);
            } else if (rune.is(RuneData.霹雳)) {
                LighteningMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 50);
            } else if (rune.is(RuneData.飞羽)) {
                SnipeFeature.apply(rune.getFeature(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.飓风)) {
                LighteningMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 40);
            } else if (rune.is(RuneData.春风)) {
                EnergyArmorFeature.apply(this, rune.getFeatureInfo(), rune, -1);
            } else if (rune.is(RuneData.雷狱)) {
                LighteningMagicFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero, -1, 35);
            } else if (rune.is(RuneData.火拳)) {
                FireMagicFeature.apply(rune.getFeature(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.热浪)) {
                FireMagicFeature.apply(rune.getFeature(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.流火)) {
                FireMagicFeature.apply(rune.getFeature(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.红莲)) {
                HealFeature.apply(rune.getFeature(), this, rune);
            } else if (rune.is(RuneData.冥火)) {
                BurningFlameFeature.apply(rune.getFeatureInfo(), this, rune, defenderHero);
            } else if (rune.is(RuneData.淬炼)) {
                AttackUpFeature.apply(this, rune.getFeatureInfo(), rune, -1);
            } else if (rune.is(RuneData.焚天)) {
                FireMagicFeature.apply(rune.getFeature(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.灼魂)) {
                HeavenWrathFeature.apply(this, rune.getFeature(), rune, defenderHero);
            } else if (rune.is(RuneData.灭世)) {
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
