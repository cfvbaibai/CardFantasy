package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.RuneActivationType;
import cfvbaibai.cardfantasy.data.RuneActivator;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.engine.feature.AllDelay;
import cfvbaibai.cardfantasy.engine.feature.AllSpeed;
import cfvbaibai.cardfantasy.engine.feature.Arouse;
import cfvbaibai.cardfantasy.engine.feature.AttackUp;
import cfvbaibai.cardfantasy.engine.feature.BackStab;
import cfvbaibai.cardfantasy.engine.feature.Block;
import cfvbaibai.cardfantasy.engine.feature.BloodDrain;
import cfvbaibai.cardfantasy.engine.feature.BloodPaint;
import cfvbaibai.cardfantasy.engine.feature.BloodThirsty;
import cfvbaibai.cardfantasy.engine.feature.Burning;
import cfvbaibai.cardfantasy.engine.feature.BurningFlame;
import cfvbaibai.cardfantasy.engine.feature.ChainAttack;
import cfvbaibai.cardfantasy.engine.feature.Confusion;
import cfvbaibai.cardfantasy.engine.feature.CounterAttack;
import cfvbaibai.cardfantasy.engine.feature.CounterBite;
import cfvbaibai.cardfantasy.engine.feature.CounterMagic;
import cfvbaibai.cardfantasy.engine.feature.CounterSummon;
import cfvbaibai.cardfantasy.engine.feature.CriticalAttack;
import cfvbaibai.cardfantasy.engine.feature.Curse;
import cfvbaibai.cardfantasy.engine.feature.Destroy;
import cfvbaibai.cardfantasy.engine.feature.Disease;
import cfvbaibai.cardfantasy.engine.feature.Dodge;
import cfvbaibai.cardfantasy.engine.feature.EnergyArmor;
import cfvbaibai.cardfantasy.engine.feature.EnergyDrain;
import cfvbaibai.cardfantasy.engine.feature.Enprison;
import cfvbaibai.cardfantasy.engine.feature.Escape;
import cfvbaibai.cardfantasy.engine.feature.Explode;
import cfvbaibai.cardfantasy.engine.feature.FireMagic;
import cfvbaibai.cardfantasy.engine.feature.Guard;
import cfvbaibai.cardfantasy.engine.feature.Heal;
import cfvbaibai.cardfantasy.engine.feature.HealingMist;
import cfvbaibai.cardfantasy.engine.feature.HeavenWrath;
import cfvbaibai.cardfantasy.engine.feature.HeroKiller;
import cfvbaibai.cardfantasy.engine.feature.HolyFire;
import cfvbaibai.cardfantasy.engine.feature.HolyGuard;
import cfvbaibai.cardfantasy.engine.feature.IceArmor;
import cfvbaibai.cardfantasy.engine.feature.IceMagic;
import cfvbaibai.cardfantasy.engine.feature.Immobility;
import cfvbaibai.cardfantasy.engine.feature.Immue;
import cfvbaibai.cardfantasy.engine.feature.LegionBuff;
import cfvbaibai.cardfantasy.engine.feature.LighteningMagic;
import cfvbaibai.cardfantasy.engine.feature.MagicShield;
import cfvbaibai.cardfantasy.engine.feature.ManaErode;
import cfvbaibai.cardfantasy.engine.feature.NoEffect;
import cfvbaibai.cardfantasy.engine.feature.OneDelay;
import cfvbaibai.cardfantasy.engine.feature.Overdraw;
import cfvbaibai.cardfantasy.engine.feature.Penetration;
import cfvbaibai.cardfantasy.engine.feature.Plague;
import cfvbaibai.cardfantasy.engine.feature.PoisonMagic;
import cfvbaibai.cardfantasy.engine.feature.Pray;
import cfvbaibai.cardfantasy.engine.feature.Purify;
import cfvbaibai.cardfantasy.engine.feature.Pursuit;
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


public class SkillResolver {
    private StageInfo stage;

    public SkillResolver(StageInfo stage) {
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
        for (SkillUseInfo feature : attacker.getNormalUsableFeatures()) {
            if (attacker.isDead()) {
                continue;
            }
            if (feature.getType() == SkillType.透支) {
                Overdraw.apply(this, feature, attacker);
            }
        }
        for (SkillUseInfo feature : attacker.getNormalUsableFeatures()) {
            if (attacker.isDead()) {
                continue;
            }
            if (feature.getType() == SkillType.未知) {
                // JUST A PLACEHOLDER
            } else if (feature.getType() == SkillType.关小黑屋) {
                Enprison.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == SkillType.吐槽) {
                TsukomiFeature.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == SkillType.火球) {
                FireMagic.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.火墙) {
                FireMagic.apply(feature.getFeature(), this, attacker, defender, 3);
            } else if (feature.getType() == SkillType.烈焰风暴) {
                FireMagic.apply(feature.getFeature(), this, attacker, defender, -1);
            } else if (feature.getType() == SkillType.落雷) {
                LighteningMagic.apply(feature, this, attacker, defender, 1, 50);
            } else if (feature.getType() == SkillType.连环闪电) {
                LighteningMagic.apply(feature, this, attacker, defender, 3, 40);
            } else if (feature.getType() == SkillType.雷暴) {
                LighteningMagic.apply(feature, this, attacker, defender, -1, 35);
            } else if (feature.getType() == SkillType.冰弹) {
                IceMagic.apply(feature, this, attacker, defender, 1, 45, 0);
            } else if (feature.getType() == SkillType.霜冻新星) {
                IceMagic.apply(feature, this, attacker, defender, 3, 35, 0);
            } else if (feature.getType() == SkillType.暴风雪) {
                IceMagic.apply(feature, this, attacker, defender, -1, 30, 0);
            } else if (feature.getType() == SkillType.寒霜冲击) {
                IceMagic.apply(feature, this, attacker, defender, -1, 50, 45 * defender.getField().getAliveCards().size());
            } else if (feature.getType() == SkillType.毒液) {
                PoisonMagic.apply(feature, this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.毒雾) {
                PoisonMagic.apply(feature, this, attacker, defender, 3);
            } else if (feature.getType() == SkillType.毒云) {
                PoisonMagic.apply(feature, this, attacker, defender, -1);
            } else if (feature.getType() == SkillType.陷阱) {
                TrapFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.狙击) {
                SnipeFeature.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.魔神之刃) {
                SnipeFeature.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.治疗) {
                Heal.apply(feature.getFeature(), this, attacker);
            } else if (feature.getType() == SkillType.甘霖) {
                RainfallFeature.apply(feature.getFeature(), this, attacker);
            } else if (feature.getType() == SkillType.治疗之雾) {
                HealingMist.apply(feature.getFeature(), this, attacker);
            } else if (feature.getType() == SkillType.祈祷) {
                Pray.apply(feature.getFeature(), this, attacker);
            } else if (feature.getType() == SkillType.复活) {
                ReviveFeature.apply(this, feature, attacker);
            } else if (feature.getType() == SkillType.背刺) {
                BackStab.apply(this, feature, attacker);
            } else if (feature.getType() == SkillType.群体削弱) {
                WeakenAllFeature.apply(this, feature, attacker, defender);
            } else if (feature.getType() == SkillType.回魂) {
                ResurrectionFeature.apply(this, feature, attacker);
            } else if (feature.getType() == SkillType.二重狙击) {
                SnipeFeature.apply(feature.getFeature(), this, attacker, defender, 2);
            } else if (feature.getType() == SkillType.迷魂) {
                Confusion.apply(feature, this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.烈火焚神) {
                BurningFlame.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.诅咒) {
                Curse.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == SkillType.魔神之咒) {
                Curse.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == SkillType.摧毁) {
                Destroy.apply(this, feature.getFeature(), attacker, defender, 1);
            } else if (feature.getType() == SkillType.瘟疫) {
                Plague.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.血炼) {
                BloodPaint.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.鲜血盛宴) {
                BloodPaint.apply(feature.getFeature(), this, attacker, defender, -1);
            } else if (feature.getType() == SkillType.天谴) {
                HeavenWrath.apply(this, feature.getFeature(), attacker, defender);
            } else if (feature.getType() == SkillType.封印) {
                SealFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.圣炎) {
                HolyFire.apply(feature.getFeature(), this, attacker, defender);
            } else if (feature.getType() == SkillType.法力侵蚀) {
                ManaErode.apply(feature.getFeature(), this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.趁胜追击) {
                WinningPursuitFeature.apply(this, feature, attacker, defender);
            } else if (feature.getType() == SkillType.复仇) {
                RevengeFeature.apply(this, feature, attacker);
            } else if (feature.getType() == SkillType.振奋) {
                Arouse.apply(this, feature, attacker);
            } else if (feature.getType() == SkillType.全体阻碍){
                AllDelay.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.全体加速){
                AllSpeed.apply(feature, this, attacker);
            } else if (feature.getType() == SkillType.阻碍) {
                OneDelay.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.加速) {
                SpeedUpFeature.apply(feature, this, attacker);
            } else if (feature.getType() == SkillType.净化) {
                Purify.apply(feature, this, attacker);
            } else if (feature.getType() == SkillType.虚弱) {
                SoftenFeature.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.召唤王国战士) {
                SummonFeature.apply(this, feature, attacker, "圣骑士", "魔剑士");
            } else if (feature.getType() == SkillType.召唤噩梦护卫) {
                SummonFeature.apply(this, feature, attacker, "时光女神", "金属巨龙");
            } else if (feature.getType() == SkillType.召唤邪龙护卫) {
                SummonFeature.apply(this, feature, attacker, "亡灵守护神", "光明之龙");
            } else if (feature.getType() == SkillType.召唤花仙子) {
                SummonFeature.apply(this, feature, attacker, "花仙子", "花仙子");
            } else if (feature.getType() == SkillType.召唤火焰乌鸦) {
                SummonFeature.apply(this, feature, attacker, "火焰乌鸦");
            } else if (feature.getType() == SkillType.召唤人马巡逻者) {
                SummonFeature.apply(this, feature, attacker, "人马巡逻者", "人马巡逻者");
            } else if (feature.getType() == SkillType.召唤女神侍者) {
                SummonFeature.apply(this, feature, attacker, "女神侍者", "女神侍者");
            } else if (feature.getType() == SkillType.召唤树人守护者) {
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
            for (SkillUseInfo feature : defender.getNormalUsableFeatures()) {
                if (feature.getType() == SkillType.反击) {
                    CounterAttack.apply(feature.getFeature(), this, attacker, defender, result.getDamage());
                } else if (feature.getType() == SkillType.盾刺) {
                    SpikeFeature.apply(feature.getFeature(), this, attacker, defender, attackFeature, result.getDamage());
                } else if (feature.getType() == SkillType.魔神之甲) {
                    SpikeFeature.apply(feature.getFeature(), this, attacker, defender, attackFeature, result.getDamage());
                } else if (feature.getType() == SkillType.燃烧) {
                    Burning.apply(feature, this, attacker, defender);
                } else if (feature.getType() == SkillType.邪灵汲取) {
                    EnergyDrain.apply(feature, this, attacker, defender, result, damagedResult);
                } else if (feature.getType() == SkillType.被插出五星) {
                    CounterSummon.apply(this, defender, feature.getFeature(), 5);
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
                    CounterAttack.apply(rune.getFeature(), this, attacker, defender, result.getDamage());
                }
            }
            if (!defender.isDead()) {
                for (SkillUseInfo feature : defender.getNormalUsableFeatures()) {
                    if (feature.getType() == SkillType.狂热) {
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
                for (SkillUseInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == SkillType.闪避) {
                        result.setAttackable(!Dodge.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, result.getDamage()));
                        if (!result.isAttackable()) {
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.轻灵);
                    if (rune != null && !defender.isWeak()) {
                        result.setAttackable(!Dodge.apply(rune.getFeature(), this, cardAttacker, defender,
                                result.getDamage()));
                        if (!result.isAttackable()) {
                            return result;
                        }
                    }
                }
                for (SkillUseInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == SkillType.王国之盾) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.HELL));
                    }
                    if (blockFeature.getType() == SkillType.森林之盾) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.SAVAGE));
                    }
                    if (blockFeature.getType() == SkillType.蛮荒之盾) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.KINGDOM));
                    }
                    if (blockFeature.getType() == SkillType.地狱之盾) {
                        result.setDamage(RacialShieldFeature.apply(blockFeature.getFeature(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.FOREST));
                    }
                }
                for (SkillUseInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == SkillType.格挡) {
                        result.setDamage(Block.apply(blockFeature.getFeature(), this, cardAttacker, defender,
                                defender, result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                for (SkillUseInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == SkillType.冰甲) {
                        result.setDamage(IceArmor.apply(blockFeature.getFeature(), this, cardAttacker, defender,
                                result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.冰封);
                    if (rune != null && !defender.isWeak()) {
                        result.setDamage(IceArmor.apply(rune.getFeature(), this, cardAttacker, defender,
                                result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.岩壁);
                    if (rune != null && !defender.isWeak()) {
                        result.setDamage(Block.apply(rune.getFeature(), this, cardAttacker, defender, rune,
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
                for (SkillUseInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == SkillType.法力反射) {
                        if (CounterMagic.isFeatureBlocked(this, blockFeature.getFeature(), attackFeature,
                                attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getRuneBox().getRuneOf(RuneData.石林);
                    if (rune != null && rune.isActivated() && !defender.isWeak()) {
                        if (CounterMagic.isFeatureBlocked(this, rune.getFeature(), attackFeature, attacker,
                                defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }

                for (SkillUseInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == SkillType.免疫) {
                        if (Immue.isFeatureBlocked(this, blockFeature.getFeature(), attackFeature, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockFeature.getType() == SkillType.无效) {
                        if (NoEffect.isFeatureBlocked(this, blockFeature.getFeature(), attackFeature, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockFeature.getType() == SkillType.脱困) {
                        if (Escape.isFeatureEscaped(this, blockFeature.getFeature(), attackFeature, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockFeature.getType() == SkillType.不动) {
                        if (Immobility.isFeatureBlocked(this, blockFeature.getFeature(), attackFeature, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.鬼步);
                    if (rune != null && !defender.isWeak()) {
                        if (Escape.isFeatureEscaped(this, rune.getFeature(), attackFeature, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                for (SkillUseInfo blockFeature : defender.getNormalUsableFeatures()) {
                    if (blockFeature.getType() == SkillType.魔甲) {
                        result.setDamage(MagicShield.apply(this, blockFeature.getFeature(), attacker, defender,
                                attackFeature, result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.炎甲);
                    if (rune != null && !defender.isWeak()) {
                        result.setDamage(MagicShield.apply(this, rune.getFeature(), attacker, defender,
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
        for (SkillUseInfo deadCardFeature : deadCard.getUsableDeathFeatures()) {
            if (deadCardFeature.getType() == SkillType.烈焰风暴) {
                FireMagic.apply(deadCardFeature.getFeature(), this, deadCard, killerCard.getOwner(), -1);
            } else if (deadCardFeature.getType() == SkillType.雷暴) {
                LighteningMagic.apply(deadCardFeature, this, deadCard, killerCard.getOwner(), -1, 35);
            } else if (deadCardFeature.getType() == SkillType.暴风雪) {
                IceMagic.apply(deadCardFeature, this, deadCard, killerCard.getOwner(), -1, 30, 0);
            } else if (deadCardFeature.getType() == SkillType.毒云) {
                PoisonMagic.apply(deadCardFeature, this, deadCard, killerCard.getOwner(), -1);
            } else if (deadCardFeature.getType() == SkillType.瘟疫) {
                Plague.apply(deadCardFeature, this, deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == SkillType.治疗) {
                Heal.apply(deadCardFeature.getFeature(), this, deadCard.getOwner());
            } else if (deadCardFeature.getType() == SkillType.甘霖) {
                RainfallFeature.apply(deadCardFeature.getFeature(), this, deadCard.getOwner());
            } else if (deadCardFeature.getType() == SkillType.祈祷) {
                Pray.apply(deadCardFeature.getFeature(), this, deadCard);
            } else if (deadCardFeature.getType() == SkillType.诅咒) {
                Curse.apply(this, deadCardFeature.getFeature(), deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == SkillType.群体削弱) {
                WeakenAllFeature.apply(this, deadCardFeature, deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == SkillType.烈火焚神) {
                BurningFlame.apply(deadCardFeature, this, deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == SkillType.陷阱) {
                TrapFeature.apply(deadCardFeature, this, deadCard, killerCard.getOwner());
            } else if (deadCardFeature.getType() == SkillType.复活) {
                ReviveFeature.apply(this, deadCardFeature, deadCard);
            } else if (deadCardFeature.getType() == SkillType.摧毁) {
                Destroy.apply(this, deadCardFeature.getFeature(), deadCard, killerCard.getOwner(), 1);
            } else if (deadCardFeature.getType() == SkillType.传送) {
                TransportFeature.apply(this, deadCardFeature.getFeature(), deadCard, killerCard.getOwner());
            } 
        }
        for (SkillUseInfo deadCardFeature : deadCard.getAllUsableFeatures()) {
            if (deadCardFeature.getType() == SkillType.自爆) {
                Explode.apply(this, deadCardFeature.getFeature(), killerCard, deadCard);
            }
        }
        {
            RuneInfo rune = deadCard.getOwner().getActiveRuneOf(RuneData.爆裂);
            if (rune != null && !deadCard.isWeak()) {
                Explode.apply(this, rune.getFeature(), killerCard, deadCard);
            }
        }
        boolean reincarnated = false;
        for (SkillUseInfo deadCardFeature : deadCard.getAllUsableFeatures()) {
            if (deadCardFeature.getType() == SkillType.转生) {
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
        for (SkillUseInfo feature : attacker.getNormalUsableFeatures()) {
            if (!attacker.isDead()) {
                if (feature.getType() == SkillType.吸血) {
                    BloodDrain.apply(feature.getFeature(), this, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (!attacker.isDead()) {
            RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.赤谷);
            if (rune != null && !attacker.isWeak()) {
                BloodDrain.apply(rune.getFeature(), this, attacker, defender, normalAttackDamage);
            }
        }
    }
    
    public void resolveExtraAttackFeature(CardInfo attacker, CardInfo defender, Player defenderHero, Skill attackFeature,
            int normalAttackDamage) throws HeroDieSignal {

        for (SkillUseInfo feature : attacker.getNormalUsableFeatures()) {
            if (!attacker.isDead()) {
                if (feature.getType() == SkillType.穿刺) {
                    Penetration.apply(feature.getFeature(), this, attacker, defenderHero, normalAttackDamage);
                } else if (feature.getType() == SkillType.削弱) {
                    WeakenFeature.apply(this, feature, attacker, defender, normalAttackDamage);
                } else if (feature.getType() == SkillType.裂伤) {
                    WoundFeature.apply(this, feature, attackFeature, attacker, defender, normalAttackDamage);
                } else if (feature.getType() == SkillType.嗜血) {
                    BloodThirsty.apply(this, feature, attacker, normalAttackDamage);
                } else if (feature.getType() == SkillType.连锁攻击) {
                    ChainAttack.apply(this, feature, attacker, defender, attackFeature);
                } else if (feature.getType() == SkillType.疾病) {
                    Disease.apply(feature, this, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (!attacker.isDead()) {
            RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.洞察);
            if (rune != null && !attacker.isWeak()) {
                BloodThirsty.apply(this, rune.getFeatureInfo(), attacker, normalAttackDamage);
            }
        }
    }

    public void resolvePreAttackHeroFeature(CardInfo attacker, Player defenderPlayer) {
        for (SkillUseInfo feature : attacker.getNormalUsableFeatures()) {
           if (feature.getType() == SkillType.英雄杀手) {
               HeroKiller.apply(this, feature, attacker, defenderPlayer);
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
        for (SkillUseInfo feature : attacker.getNormalUsableFeatures()) {
            if (prior) {
                if (feature.getType() == SkillType.送还) {
                    ReturnFeature.apply(this, feature.getFeature(), attacker, defender);
                }
            } else {
                if (feature.getType() == SkillType.圣光) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.HELL);
                } else if (feature.getType() == SkillType.要害) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.SAVAGE);
                } else if (feature.getType() == SkillType.暗杀) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.KINGDOM);
                } else if (feature.getType() == SkillType.污染) {
                    RacialAttackFeature.apply(this, feature, attacker, defender, Race.FOREST);
                } else if (feature.getType() == SkillType.暴击) {
                    CriticalAttack.apply(this, feature, attacker, defender);
                } else if (feature.getType() == SkillType.穷追猛打) {
                    Pursuit.apply(this, feature, attacker, defender);
                } else if (feature.getType() == SkillType.战意) {
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
                    CriticalAttack.apply(this, rune.getFeatureInfo(), attacker, defender);
                }
            }
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.扬旗);
                if (rune != null && !attacker.isWeak()) {
                    Pursuit.apply(this, rune.getFeatureInfo(), attacker, defender);
                }
            }
        }
    }

    public void removeTempEffects(CardInfo card) {
        if (card == null) {
            return;
        }
        for (SkillEffect effect : card.getEffects()) {
            SkillType type = effect.getCause().getType();
            if (type == SkillType.圣光 || type == SkillType.要害 || type == SkillType.暗杀 || type == SkillType.污染) {
                RacialAttackFeature.remove(this, effect.getCause(), card);
            } else if (type == SkillType.暴击) {
                CriticalAttack.remove(this, effect.getCause(), card);
            } else if (type == SkillType.穷追猛打) {
                Pursuit.remove(this, effect.getCause(), card);
            } else if (type == SkillType.背刺) {
                BackStab.remove(this, effect.getCause(), card);
            } else if (type == SkillType.战意) {
                WarthFeature.remove(this, effect.getCause(), card);
            } else if (type == SkillType.趁胜追击) {
                WinningPursuitFeature.remove(this, effect.getCause(), card);
            } else if (type == SkillType.复仇) {
                RevengeFeature.remove(this, effect.getCause(), card);
            } else if (type == SkillType.振奋) {
                Arouse.remove(this, effect.getCause(), card);
            } else if (type == SkillType.英雄杀手) {
                HeroKiller.remove(this, effect.getCause(), card);
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
            for (SkillUseInfo defenderFeature : defender.getNormalUsableFeatures()) {
                if (defenderFeature.getType() == SkillType.守护) {
                    remainingDamage = Guard.apply(defenderFeature.getFeature(), cardFeature, this, attacker, defender, remainingDamage);
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
        for (SkillUseInfo cardFeature : card.getNormalUsableFeatures()) {
            if (cardFeature.getType() == SkillType.回春) {
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

    public OnDamagedResult attackCard(CardInfo attacker, CardInfo defender, SkillUseInfo skillUseInfo) throws HeroDieSignal {
        return attackCard(attacker, defender, skillUseInfo, attacker.getCurrentAT());
    }

    public OnDamagedResult attackCard(CardInfo attacker, CardInfo defender, SkillUseInfo skillUseInfo, int damage) throws HeroDieSignal {
        Skill skill = skillUseInfo == null ? null : skillUseInfo.getFeature();
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
        LegionBuff.apply(this, card);
        for (CardInfo fieldCard : myField.getAliveCards()) {
            for (SkillUseInfo feature : fieldCard.getNormalUsableFeatures()) {
                if (feature.getType() == SkillType.王国之力) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.KINGDOM, SkillEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == SkillType.王国守护) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.KINGDOM, SkillEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == SkillType.森林之力) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.FOREST, SkillEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == SkillType.森林守护) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.FOREST, SkillEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == SkillType.蛮荒之力) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.SAVAGE, SkillEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == SkillType.蛮荒守护) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.SAVAGE, SkillEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == SkillType.地狱之力) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.HELL, SkillEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == SkillType.地狱守护) {
                    RaceBuffFeature.apply(this, feature, fieldCard, Race.HELL, SkillEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == SkillType.本源之力) {
                    RaceBuffFeature.apply(this, feature, fieldCard, null, SkillEffectType.ATTACK_CHANGE);
                } else if (feature.getType() == SkillType.本源守护) {
                    RaceBuffFeature.apply(this, feature, fieldCard, null, SkillEffectType.MAXHP_CHANGE);
                } else if (feature.getType() == SkillType.神圣守护) {
                    HolyGuard.apply(this, feature, fieldCard);
                } 
            }
        }
        for (SkillUseInfo feature : card.getNormalUsableFeatures()) {
            if (feature.getType() == SkillType.献祭) {
                SacrificeFeature.apply(this, feature, card, reviver);
            } else if (feature.getType() == SkillType.反噬) {
                CounterBite.apply(feature, this, card);
            }
        }
        for (SkillUseInfo feature : card.getUsableSummonFeatures()) {
            if (feature.getType() == SkillType.烈焰风暴) {
                FireMagic.apply(feature.getFeature(), this, card, opField.getOwner(), -1);
            } else if (feature.getType() == SkillType.雷暴) {
                LighteningMagic.apply(feature, this, card, opField.getOwner(), -1, 35);
            } else if (feature.getType() == SkillType.暴风雪) {
                IceMagic.apply(feature, this, card, opField.getOwner(), -1, 30, 0);
            } else if (feature.getType() == SkillType.毒云) {
                PoisonMagic.apply(feature, this, card, opField.getOwner(), -1);
            } else if (feature.getType() == SkillType.瘟疫) {
                Plague.apply(feature, this, card, opField.getOwner());
            } else if (feature.getType() == SkillType.治疗) {
                Heal.apply(feature.getFeature(), this, card);
            } else if (feature.getType() == SkillType.甘霖) {
                RainfallFeature.apply(feature.getFeature(), this, card);
            } else if (feature.getType() == SkillType.祈祷) {
                Pray.apply(feature.getFeature(), this, card);
            } else if (feature.getType() == SkillType.诅咒) {
                Curse.apply(this, feature.getFeature(), card, opField.getOwner());
            } else if (feature.getType() == SkillType.群体削弱) {
                WeakenAllFeature.apply(this, feature, card, opField.getOwner());
            } else if (feature.getType() == SkillType.烈火焚神) {
                BurningFlame.apply(feature, this, card, opField.getOwner());
            } else if (feature.getType() == SkillType.陷阱) {
                TrapFeature.apply(feature, this, card, opField.getOwner());
            } else if (feature.getType() == SkillType.送还) {
                ReturnFeature.apply(this, feature.getFeature(), card, opField.getCard(card.getPosition()));
            } else if (feature.getType() == SkillType.摧毁) {
                Destroy.apply(this, feature.getFeature(), card, opField.getOwner(), 1);
            } else if (feature.getType() == SkillType.传送) {
                TransportFeature.apply(this, feature.getFeature(), card, opField.getOwner());
            } else if (feature.getType() == SkillType.复活) {
                ReviveFeature.apply(this, feature, card);
            } else if (feature.getType() == SkillType.关小黑屋) {
                Enprison.apply(this, feature.getFeature(), card, opField.getOwner());
            } else if (feature.getType() == SkillType.净化 || feature.getType() == SkillType.神性祈求){
                Purify.apply(feature, this, card);
            }
        }
    }

    public void resolveLeaveFeature(CardInfo card, Skill cardFeature) {
        for (SkillUseInfo deadCardFeature : card.getNormalUsableFeatures()) {
            if (deadCardFeature.getType() == SkillType.王国之力) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.KINGDOM);
            } else if (deadCardFeature.getType() == SkillType.王国守护) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.KINGDOM);
            } else if (deadCardFeature.getType() == SkillType.森林之力) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.FOREST);
            } else if (deadCardFeature.getType() == SkillType.森林守护) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.FOREST);
            } else if (deadCardFeature.getType() == SkillType.蛮荒之力) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.SAVAGE);
            } else if (deadCardFeature.getType() == SkillType.蛮荒守护) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.SAVAGE);
            } else if (deadCardFeature.getType() == SkillType.地狱之力) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.HELL);
            } else if (deadCardFeature.getType() == SkillType.地狱守护) {
                RaceBuffFeature.remove(this, deadCardFeature, card, Race.HELL);
            } else if (deadCardFeature.getType() == SkillType.本源之力) {
                RaceBuffFeature.remove(this, deadCardFeature, card, null);
            } else if (deadCardFeature.getType() == SkillType.本源守护) {
                RaceBuffFeature.remove(this, deadCardFeature, card, null);
            } else if (deadCardFeature.getType() == SkillType.神圣守护) {
                HolyGuard.remove(this, deadCardFeature, card);
            } else if (deadCardFeature.getType() == SkillType.军团王国之力
                    || deadCardFeature.getType() == SkillType.军团森林之力
                    || deadCardFeature.getType() == SkillType.军团蛮荒之力
                    || deadCardFeature.getType() == SkillType.军团地狱之力) {
                LegionBuff.remove(this, deadCardFeature, card);
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

    public BlockStatusResult resolveBlockStatusFeature(EntityInfo attacker, CardInfo victim, SkillUseInfo feature,
            CardStatusItem item) {
        boolean blocked = false;
        for (SkillUseInfo blockFeature : victim.getNormalUsableFeatures()) {
            if (blockFeature.getType() == SkillType.脱困) {
                blocked = Escape.isStatusEscaped(blockFeature.getFeature(), this, item, victim);
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
        for (SkillUseInfo attackerFeature : attacker.getAllUsableFeatures()) {
            if (attackerFeature.getType() == SkillType.弱点攻击) {
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
                for (SkillEffect effect : card.getEffects()) {
                    if (effect.getCause().equals(rune.getFeatureInfo())) {
                        if (rune.getFeature().getType().containsTag(SkillTag.永久)) {
                            continue;
                        }
                        if (effect.getType() == SkillEffectType.ATTACK_CHANGE) {
                            stage.getUI().loseAdjustATEffect(card, effect);
                        } else if (effect.getType() == SkillEffectType.MAXHP_CHANGE) {
                            stage.getUI().loseAdjustHPEffect(card, effect);
                        } else if (effect.getType() == SkillEffectType.SKILL_USED) {
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
                PoisonMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.沼泽)) {
                PoisonMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.岩晶)) {
                EnergyArmor.apply(this, rune.getFeatureInfo(), rune, 1);
            } else if (rune.is(RuneData.毒砂)) {
                PoisonMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.深渊)) {
                PoisonMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.陨星)) {
                Plague.apply(rune.getFeatureInfo(), this, rune, defenderHero);
            } else if (rune.is(RuneData.死域)) {
                PoisonMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, -1);
            } else if (rune.is(RuneData.霜冻)) {
                IceMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 45, 0);
            } else if (rune.is(RuneData.寒潮)) {
                IceMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 35, 0);
            } else if (rune.is(RuneData.冰锥)) {
                IceMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 45, 0);
            } else if (rune.is(RuneData.暴雨)) {
                WeakenAllFeature.apply(this, rune.getFeatureInfo(), rune, defenderHero);
            } else if (rune.is(RuneData.清泉)) {
                RainfallFeature.apply(rune.getFeature(), this, rune);
            } else if (rune.is(RuneData.雪崩)) {
                IceMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 35, 0);
            } else if (rune.is(RuneData.圣泉)) {
                Pray.apply(rune.getFeature(), this, rune);
            } else if (rune.is(RuneData.永冻)) {
                IceMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, -1, 30, 0);
            } else if (rune.is(RuneData.闪电)) {
                LighteningMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 50);
            } else if (rune.is(RuneData.雷云)) {
                LighteningMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 40);
            } else if (rune.is(RuneData.霹雳)) {
                LighteningMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 1, 50);
            } else if (rune.is(RuneData.飞羽)) {
                SnipeFeature.apply(rune.getFeature(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.飓风)) {
                LighteningMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, 3, 40);
            } else if (rune.is(RuneData.春风)) {
                EnergyArmor.apply(this, rune.getFeatureInfo(), rune, -1);
            } else if (rune.is(RuneData.雷狱)) {
                LighteningMagic.apply(rune.getFeatureInfo(), this, rune, defenderHero, -1, 35);
            } else if (rune.is(RuneData.火拳)) {
                FireMagic.apply(rune.getFeature(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.热浪)) {
                FireMagic.apply(rune.getFeature(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.流火)) {
                FireMagic.apply(rune.getFeature(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.红莲)) {
                Heal.apply(rune.getFeature(), this, rune);
            } else if (rune.is(RuneData.冥火)) {
                BurningFlame.apply(rune.getFeatureInfo(), this, rune, defenderHero);
            } else if (rune.is(RuneData.淬炼)) {
                AttackUp.apply(this, rune.getFeatureInfo(), rune, -1);
            } else if (rune.is(RuneData.焚天)) {
                FireMagic.apply(rune.getFeature(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.灼魂)) {
                HeavenWrath.apply(this, rune.getFeature(), rune, defenderHero);
            } else if (rune.is(RuneData.灭世)) {
                FireMagic.apply(rune.getFeature(), this, rune, defenderHero, -1);
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
