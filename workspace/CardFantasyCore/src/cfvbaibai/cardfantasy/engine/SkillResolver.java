package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.RuneActivationType;
import cfvbaibai.cardfantasy.data.RuneActivator;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
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
import cfvbaibai.cardfantasy.engine.feature.EarthShield;
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
import cfvbaibai.cardfantasy.engine.feature.HolyShield;
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
import cfvbaibai.cardfantasy.engine.feature.RacialAttackFeature;
import cfvbaibai.cardfantasy.engine.feature.RacialBuff;
import cfvbaibai.cardfantasy.engine.feature.RacialShield;
import cfvbaibai.cardfantasy.engine.feature.Rainfall;
import cfvbaibai.cardfantasy.engine.feature.Reincarnation;
import cfvbaibai.cardfantasy.engine.feature.Rejuvenate;
import cfvbaibai.cardfantasy.engine.feature.Resurrection;
import cfvbaibai.cardfantasy.engine.feature.Return;
import cfvbaibai.cardfantasy.engine.feature.Revenge;
import cfvbaibai.cardfantasy.engine.feature.Revive;
import cfvbaibai.cardfantasy.engine.feature.Sacrifice;
import cfvbaibai.cardfantasy.engine.feature.Seal;
import cfvbaibai.cardfantasy.engine.feature.Snipe;
import cfvbaibai.cardfantasy.engine.feature.Soften;
import cfvbaibai.cardfantasy.engine.feature.SpeedUp;
import cfvbaibai.cardfantasy.engine.feature.Spike;
import cfvbaibai.cardfantasy.engine.feature.Summon;
import cfvbaibai.cardfantasy.engine.feature.Transport;
import cfvbaibai.cardfantasy.engine.feature.Trap;
import cfvbaibai.cardfantasy.engine.feature.Tsukomi;
import cfvbaibai.cardfantasy.engine.feature.WeakPointAttack;
import cfvbaibai.cardfantasy.engine.feature.Weaken;
import cfvbaibai.cardfantasy.engine.feature.WeakenAll;
import cfvbaibai.cardfantasy.engine.feature.WinningPursuit;
import cfvbaibai.cardfantasy.engine.feature.Wound;
import cfvbaibai.cardfantasy.engine.feature.Wrath;
import cfvbaibai.cardfantasy.engine.feature.Zealot;


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

    public void resolvePreAttackSkills(Player attacker, Player defender) throws HeroDieSignal {
        List<CardInfo> cards = attacker.getField().toList();
        for (CardInfo card : cards) {
            if (card.isFullyControlled()) { continue; }
            for (SkillUseInfo skillUseInfo : card.getNormalUsableSkills()) {
                if (skillUseInfo.getType() == SkillType.神性祈求) {
                    Purify.apply(skillUseInfo, this, card);
                }
            }
        }
    }

    public void resolvePreAttackFeature(CardInfo attacker, Player defender) throws HeroDieSignal {
        for (SkillUseInfo feature : attacker.getNormalUsableSkills()) {
            if (attacker.isDead()) {
                continue;
            }
            if (feature.getType() == SkillType.透支) {
                Overdraw.apply(this, feature, attacker);
            }
        }
        for (SkillUseInfo feature : attacker.getNormalUsableSkills()) {
            if (attacker.isDead()) {
                continue;
            }
            if (feature.getType() == SkillType.未知) {
                // JUST A PLACEHOLDER
            } else if (feature.getType() == SkillType.关小黑屋) {
                Enprison.apply(this, feature.getSkill(), attacker, defender);
            } else if (feature.getType() == SkillType.吐槽) {
                Tsukomi.apply(this, feature.getSkill(), attacker, defender);
            } else if (feature.getType() == SkillType.火球) {
                FireMagic.apply(feature.getSkill(), this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.火墙) {
                FireMagic.apply(feature.getSkill(), this, attacker, defender, 3);
            } else if (feature.getType() == SkillType.烈焰风暴) {
                FireMagic.apply(feature.getSkill(), this, attacker, defender, -1);
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
                Trap.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.狙击) {
                Snipe.apply(feature.getSkill(), this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.魔神之刃) {
                Snipe.apply(feature.getSkill(), this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.治疗) {
                Heal.apply(feature.getSkill(), this, attacker);
            } else if (feature.getType() == SkillType.甘霖) {
                Rainfall.apply(feature.getSkill(), this, attacker);
            } else if (feature.getType() == SkillType.治疗之雾) {
                HealingMist.apply(feature.getSkill(), this, attacker);
            } else if (feature.getType() == SkillType.祈祷) {
                Pray.apply(feature.getSkill(), this, attacker);
            } else if (feature.getType() == SkillType.复活) {
                Revive.apply(this, feature, attacker);
            } else if (feature.getType() == SkillType.背刺) {
                BackStab.apply(this, feature, attacker);
            } else if (feature.getType() == SkillType.群体削弱) {
                WeakenAll.apply(this, feature, attacker, defender);
            } else if (feature.getType() == SkillType.回魂) {
                Resurrection.apply(this, feature, attacker);
            } else if (feature.getType() == SkillType.二重狙击) {
                Snipe.apply(feature.getSkill(), this, attacker, defender, 2);
            } else if (feature.getType() == SkillType.迷魂) {
                Confusion.apply(feature, this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.烈火焚神) {
                BurningFlame.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.诅咒) {
                Curse.apply(this, feature.getSkill(), attacker, defender);
            } else if (feature.getType() == SkillType.魔神之咒) {
                Curse.apply(this, feature.getSkill(), attacker, defender);
            } else if (feature.getType() == SkillType.摧毁) {
                Destroy.apply(this, feature.getSkill(), attacker, defender, 1);
            } else if (feature.getType() == SkillType.瘟疫) {
                Plague.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.血炼) {
                BloodPaint.apply(feature.getSkill(), this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.鲜血盛宴) {
                BloodPaint.apply(feature.getSkill(), this, attacker, defender, -1);
            } else if (feature.getType() == SkillType.天谴) {
                HeavenWrath.apply(this, feature.getSkill(), attacker, defender);
            } else if (feature.getType() == SkillType.封印) {
                Seal.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.圣炎) {
                HolyFire.apply(feature.getSkill(), this, attacker, defender);
            } else if (feature.getType() == SkillType.法力侵蚀) {
                ManaErode.apply(feature.getSkill(), this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.趁胜追击) {
                WinningPursuit.apply(this, feature, attacker, defender);
            } else if (feature.getType() == SkillType.复仇) {
                Revenge.apply(this, feature, attacker);
            } else if (feature.getType() == SkillType.振奋) {
                Arouse.apply(this, feature, attacker);
            } else if (feature.getType() == SkillType.全体阻碍){
                AllDelay.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.全体加速){
                AllSpeed.apply(feature, this, attacker);
            } else if (feature.getType() == SkillType.阻碍) {
                OneDelay.apply(feature, this, attacker, defender);
            } else if (feature.getType() == SkillType.加速) {
                SpeedUp.apply(feature, this, attacker);
            } else if (feature.getType() == SkillType.净化) {
                Purify.apply(feature, this, attacker);
            } else if (feature.getType() == SkillType.虚弱) {
                Soften.apply(feature, this, attacker, defender, 1);
            } else if (feature.getType() == SkillType.战争怒吼) {
                Soften.apply(feature, this, attacker, defender, -1);
            } else if (feature.getType() == SkillType.召唤王国战士) {
                Summon.apply(this, feature, attacker, "圣骑士", "魔剑士");
            } else if (feature.getType() == SkillType.召唤噩梦护卫) {
                Summon.apply(this, feature, attacker, "时光女神", "金属巨龙");
            } else if (feature.getType() == SkillType.召唤邪龙护卫) {
                Summon.apply(this, feature, attacker, "亡灵守护神", "光明之龙");
            } else if (feature.getType() == SkillType.召唤复仇护卫) {
                Summon.apply(this, feature, attacker, "雷兽", "末日预言师");
            } else if (feature.getType() == SkillType.召唤花仙子) {
                Summon.apply(this, feature, attacker, "花仙子", "花仙子");
            } else if (feature.getType() == SkillType.召唤火焰乌鸦) {
                Summon.apply(this, feature, attacker, "火焰乌鸦");
            } else if (feature.getType() == SkillType.召唤人马巡逻者) {
                Summon.apply(this, feature, attacker, "人马巡逻者", "人马巡逻者");
            } else if (feature.getType() == SkillType.召唤女神侍者) {
                Summon.apply(this, feature, attacker, "女神侍者", "女神侍者");
            } else if (feature.getType() == SkillType.召唤树人守护者) {
                Summon.apply(this, feature, attacker, "霜雪树人", "树人祭司");
            }
        }
        RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.飞岩);
        if (rune != null && !attacker.isWeak()) {
            Snipe.apply(rune.getSkill(), this, attacker, defender, 1);
        }
    }

    public void resolvePostAttackFeature(CardInfo attacker, Player defender) {

    }

    public void resolveCounterAttackFeature(CardInfo attacker, CardInfo defender, Skill attackSkill,
            OnAttackBlockingResult result, OnDamagedResult damagedResult) throws HeroDieSignal {
        if (isPhysicalAttackFeature(attackSkill)) {
            for (SkillUseInfo skillUseInfo : defender.getNormalUsableSkills()) {
                if (skillUseInfo.getType() == SkillType.反击) {
                    CounterAttack.apply(skillUseInfo.getSkill(), this, attacker, defender, result.getDamage());
                } else if (skillUseInfo.getType() == SkillType.盾刺) {
                    Spike.apply(skillUseInfo.getSkill(), this, attacker, defender, attackSkill, result.getDamage());
                } else if (skillUseInfo.getType() == SkillType.魔神之甲) {
                    Spike.apply(skillUseInfo.getSkill(), this, attacker, defender, attackSkill, result.getDamage());
                } else if (skillUseInfo.getType() == SkillType.燃烧) {
                    Burning.apply(skillUseInfo, this, attacker, defender);
                } else if (skillUseInfo.getType() == SkillType.邪灵汲取) {
                    EnergyDrain.apply(skillUseInfo, this, attacker, defender, result, damagedResult);
                } else if (skillUseInfo.getType() == SkillType.被插出五星) {
                    CounterSummon.apply(this, defender, skillUseInfo.getSkill(), 5);
                } else if (skillUseInfo.getType() == SkillType.大地之盾) {
                    EarthShield.apply(skillUseInfo, this, attacker, defender);
                }
            }
            {
                RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.雷盾);
                if (rune != null && !defender.isWeak()) {
                    Spike.apply(rune.getSkill(), this, attacker, defender, attackSkill, result.getDamage());
                }
            }
            {
                RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.漩涡);
                if (rune != null && !defender.isWeak()) {
                    CounterAttack.apply(rune.getSkill(), this, attacker, defender, result.getDamage());
                }
            }
            if (!defender.isDead()) {
                for (SkillUseInfo feature : defender.getNormalUsableSkills()) {
                    if (feature.getType() == SkillType.狂热) {
                        Zealot.apply(feature, this, attacker, defender, result);
                    }
                }
                RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.怒涛);
                if (rune != null && !defender.isWeak()) {
                    Zealot.apply(rune.getSkillUseInfo(), this, attacker, defender, result);
                }
            }
        }
    }

    public OnAttackBlockingResult resolveHealBlockingFeature(EntityInfo healer, CardInfo healee, Skill cardSkill) {
        OnAttackBlockingResult result = new OnAttackBlockingResult(true, cardSkill.getImpact());
        if (healee.getStatus().containsStatus(CardStatusType.裂伤)) {
            stage.getUI().healBlocked(healer, healee, cardSkill, null);
            result.setAttackable(false);
        }
        return result;
    }

    /**
     * Resolve attack blocking skills.
     * @param attacker The one that uses the skill to be blocked.
     * @param defender The one that uses the skill to block the attacker.
     * @param attackSkill The skill that uses to attack the defender.
     * @param damage
     * @return
     * @throws HeroDieSignal
     */
    public OnAttackBlockingResult resolveAttackBlockingFeature(EntityInfo attacker, CardInfo defender,
            Skill attackSkill, int damage) throws HeroDieSignal {
        OnAttackBlockingResult result = new OnAttackBlockingResult(true, 0);
        CardStatus status = attacker.getStatus();
        if (isPhysicalAttackFeature(attackSkill)) {
            // Physical attack could be blocked by Dodge or 麻痹, 冰冻, 锁定, 迷惑, 复活 status.
            CardInfo cardAttacker = (CardInfo) attacker;
            result.setDamage(damage);
            if (status.containsStatus(CardStatusType.冰冻) || status.containsStatus(CardStatusType.麻痹)
                    || status.containsStatus(CardStatusType.锁定) || status.containsStatus(CardStatusType.复活)) {
                stage.getUI().attackBlocked(cardAttacker, defender, attackSkill, null);
                result.setAttackable(false);
            } else {
                for (SkillUseInfo blockSkillUseInfo : defender.getNormalUsableSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.闪避) {
                        result.setAttackable(!Dodge.apply(blockSkillUseInfo.getSkill(), this, cardAttacker, defender, result.getDamage()));
                        if (!result.isAttackable()) {
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.轻灵);
                    if (rune != null && !defender.isWeak()) {
                        result.setAttackable(!Dodge.apply(rune.getSkill(), this, cardAttacker, defender, result.getDamage()));
                        if (!result.isAttackable()) {
                            return result;
                        }
                    }
                }
                {
                    for (SkillUseInfo blockSkillUseInfo : defender.getNormalUsableSkills()) {
                        if (blockSkillUseInfo.getType() == SkillType.圣盾) {
                            result.setAttackable(HolyShield.apply(blockSkillUseInfo, this, cardAttacker, defender));
                            if (!result.isAttackable()) {
                                return result;
                            }
                        }
                    }
                }
                for (SkillUseInfo blockSkillUseInfo : defender.getNormalUsableSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.王国之盾) {
                        result.setDamage(RacialShield.apply(blockSkillUseInfo.getSkill(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.HELL));
                    }
                    if (blockSkillUseInfo.getType() == SkillType.森林之盾) {
                        result.setDamage(RacialShield.apply(blockSkillUseInfo.getSkill(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.SAVAGE));
                    }
                    if (blockSkillUseInfo.getType() == SkillType.蛮荒之盾) {
                        result.setDamage(RacialShield.apply(blockSkillUseInfo.getSkill(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.KINGDOM));
                    }
                    if (blockSkillUseInfo.getType() == SkillType.地狱之盾) {
                        result.setDamage(RacialShield.apply(blockSkillUseInfo.getSkill(), this, cardAttacker,
                                defender, defender, result.getDamage(), Race.FOREST));
                    }
                }
                for (SkillUseInfo blockSkillUseInfo : defender.getNormalUsableSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.格挡) {
                        result.setDamage(Block.apply(blockSkillUseInfo.getSkill(), this, cardAttacker, defender,
                                defender, result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                for (SkillUseInfo blockSkillUseInfo : defender.getNormalUsableSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.冰甲) {
                        result.setDamage(IceArmor.apply(blockSkillUseInfo.getSkill(), this, cardAttacker, defender,
                                result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.冰封);
                    if (rune != null && !defender.isWeak()) {
                        result.setDamage(IceArmor.apply(rune.getSkill(), this, cardAttacker, defender,
                                result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.岩壁);
                    if (rune != null && !defender.isWeak()) {
                        result.setDamage(Block.apply(rune.getSkill(), this, cardAttacker, defender, rune,
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
            if (!attackSkill.isDeathSkill() && isAttackerDisabled) {
                stage.getUI().attackBlocked(attacker, defender, attackSkill, null);
                result.setAttackable(false);
            } else {
                for (SkillUseInfo blockSkillUseInfo : defender.getNormalUsableSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.法力反射) {
                        if (CounterMagic.isFeatureBlocked(this, blockSkillUseInfo.getSkill(), attackSkill,
                                attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getRuneBox().getRuneOf(RuneData.石林);
                    if (rune != null && rune.isActivated() && !defender.isWeak()) {
                        if (CounterMagic.isFeatureBlocked(this, rune.getSkill(), attackSkill, attacker,
                                defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }

                for (SkillUseInfo blockSkillUseInfo : defender.getNormalUsableSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.免疫) {
                        if (Immue.isFeatureBlocked(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockSkillUseInfo.getType() == SkillType.无效) {
                        if (NoEffect.isFeatureBlocked(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockSkillUseInfo.getType() == SkillType.脱困) {
                        if (Escape.isFeatureEscaped(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockSkillUseInfo.getType() == SkillType.不动) {
                        if (Immobility.isFeatureBlocked(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.鬼步);
                    if (rune != null && !defender.isWeak()) {
                        if (Escape.isFeatureEscaped(this, rune.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                for (SkillUseInfo blockSkillUseInfo : defender.getNormalUsableSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.魔甲) {
                        result.setDamage(MagicShield.apply(this, blockSkillUseInfo.getSkill(), attacker, defender,
                                attackSkill, result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.炎甲);
                    if (rune != null && !defender.isWeak()) {
                        result.setDamage(MagicShield.apply(this, rune.getSkill(), attacker, defender,
                                attackSkill, result.getDamage()));
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
     * @param cardSkill
     * @return Whether the dead card is revived.
     * @throws HeroDieSignal
     */
    public void resolveDeathFeature(EntityInfo killerCard, CardInfo deadCard, Skill cardSkill) throws HeroDieSignal {
        if (deadCard.hasDeadOnce()) {
            return;
        }
        deadCard.setDeadOnce(true);
        resolveLeaveFeature(deadCard, cardSkill);
        for (SkillUseInfo deadCardSkillUseInfo : deadCard.getUsableDeathSkills()) {
            if (deadCardSkillUseInfo.getType() == SkillType.烈焰风暴) {
                FireMagic.apply(deadCardSkillUseInfo.getSkill(), this, deadCard, killerCard.getOwner(), -1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.雷暴) {
                LighteningMagic.apply(deadCardSkillUseInfo, this, deadCard, killerCard.getOwner(), -1, 35);
            } else if (deadCardSkillUseInfo.getType() == SkillType.暴风雪) {
                IceMagic.apply(deadCardSkillUseInfo, this, deadCard, killerCard.getOwner(), -1, 30, 0);
            } else if (deadCardSkillUseInfo.getType() == SkillType.毒云) {
                PoisonMagic.apply(deadCardSkillUseInfo, this, deadCard, killerCard.getOwner(), -1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.瘟疫) {
                Plague.apply(deadCardSkillUseInfo, this, deadCard, killerCard.getOwner());
            } else if (deadCardSkillUseInfo.getType() == SkillType.治疗) {
                Heal.apply(deadCardSkillUseInfo.getSkill(), this, deadCard.getOwner());
            } else if (deadCardSkillUseInfo.getType() == SkillType.甘霖) {
                Rainfall.apply(deadCardSkillUseInfo.getSkill(), this, deadCard.getOwner());
            } else if (deadCardSkillUseInfo.getType() == SkillType.祈祷) {
                Pray.apply(deadCardSkillUseInfo.getSkill(), this, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.诅咒) {
                Curse.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, killerCard.getOwner());
            } else if (deadCardSkillUseInfo.getType() == SkillType.群体削弱) {
                WeakenAll.apply(this, deadCardSkillUseInfo, deadCard, killerCard.getOwner());
            } else if (deadCardSkillUseInfo.getType() == SkillType.烈火焚神) {
                BurningFlame.apply(deadCardSkillUseInfo, this, deadCard, killerCard.getOwner());
            } else if (deadCardSkillUseInfo.getType() == SkillType.陷阱) {
                Trap.apply(deadCardSkillUseInfo, this, deadCard, killerCard.getOwner());
            } else if (deadCardSkillUseInfo.getType() == SkillType.复活) {
                Revive.apply(this, deadCardSkillUseInfo, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.摧毁) {
                Destroy.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, killerCard.getOwner(), 1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.传送) {
                Transport.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, killerCard.getOwner());
            } 
        }
        for (SkillUseInfo deadCardSkillUseInfo : deadCard.getAllUsableSkills()) {
            if (deadCardSkillUseInfo.getType() == SkillType.自爆) {
                Explode.apply(this, deadCardSkillUseInfo.getSkill(), killerCard, deadCard);
            }
        }
        {
            RuneInfo rune = deadCard.getOwner().getActiveRuneOf(RuneData.爆裂);
            if (rune != null && !deadCard.isWeak()) {
                Explode.apply(this, rune.getSkill(), killerCard, deadCard);
            }
        }
        boolean reincarnated = false;
        for (SkillUseInfo deadCardSkillUseInfo : deadCard.getAllUsableSkills()) {
            if (deadCardSkillUseInfo.getType() == SkillType.转生) {
                if (Reincarnation.apply(this, deadCardSkillUseInfo.getSkill(), deadCard)) {
                    reincarnated = true;
                    break;
                }
            }
        }
        if (!reincarnated) {
            RuneInfo rune = deadCard.getOwner().getActiveRuneOf(RuneData.秽土);
            if (rune != null && !deadCard.isWeak()) {
                Reincarnation.apply(this, rune.getSkill(), deadCard);
            }
        }
    }

    public void resolvePostAttackFeature(CardInfo attacker, CardInfo defender, Player defenderHero, Skill attackSkill,
            int normalAttackDamage) throws HeroDieSignal {
        for (SkillUseInfo skillUseInfo : attacker.getNormalUsableSkills()) {
            if (!attacker.isDead()) {
                if (skillUseInfo.getType() == SkillType.吸血) {
                    BloodDrain.apply(skillUseInfo.getSkill(), this, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (!attacker.isDead()) {
            RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.赤谷);
            if (rune != null && !attacker.isWeak()) {
                BloodDrain.apply(rune.getSkill(), this, attacker, defender, normalAttackDamage);
            }
        }
    }
    
    public void resolveExtraAttackFeature(CardInfo attacker, CardInfo defender, Player defenderHero, Skill attackSkill,
            int normalAttackDamage) throws HeroDieSignal {

        for (SkillUseInfo skillUseInfo : attacker.getNormalUsableSkills()) {
            if (!attacker.isDead()) {
                if (skillUseInfo.getType() == SkillType.穿刺) {
                    Penetration.apply(skillUseInfo.getSkill(), this, attacker, defenderHero, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.削弱) {
                    Weaken.apply(this, skillUseInfo, attacker, defender, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.裂伤) {
                    Wound.apply(this, skillUseInfo, attackSkill, attacker, defender, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.嗜血) {
                    BloodThirsty.apply(this, skillUseInfo, attacker, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.连锁攻击) {
                    ChainAttack.apply(this, skillUseInfo, attacker, defender, attackSkill);
                } else if (skillUseInfo.getType() == SkillType.疾病) {
                    Disease.apply(skillUseInfo, this, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (!attacker.isDead()) {
            RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.洞察);
            if (rune != null && !attacker.isWeak()) {
                BloodThirsty.apply(this, rune.getSkillUseInfo(), attacker, normalAttackDamage);
            }
        }
    }

    public void resolvePreAttackHeroFeature(CardInfo attacker, Player defenderPlayer) {
        for (SkillUseInfo feature : attacker.getNormalUsableSkills()) {
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
        for (SkillUseInfo feature : attacker.getNormalUsableSkills()) {
            if (prior) {
                if (feature.getType() == SkillType.送还) {
                    Return.apply(this, feature.getSkill(), attacker, defender);
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
                    Wrath.apply(this, feature, attacker, defender);
                }
            }
        }
        if (!prior) {
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.绝杀);
                if (rune != null && !attacker.isWeak()) {
                    Wrath.apply(this, rune.getSkillUseInfo(), attacker, defender);
                }
            }
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.寒伤);
                if (rune != null && !attacker.isWeak()) {
                    CriticalAttack.apply(this, rune.getSkillUseInfo(), attacker, defender);
                }
            }
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.扬旗);
                if (rune != null && !attacker.isWeak()) {
                    Pursuit.apply(this, rune.getSkillUseInfo(), attacker, defender);
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
                Wrath.remove(this, effect.getCause(), card);
            } else if (type == SkillType.趁胜追击) {
                WinningPursuit.remove(this, effect.getCause(), card);
            } else if (type == SkillType.复仇) {
                Revenge.remove(this, effect.getCause(), card);
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
            for (SkillUseInfo defenderFeature : defender.getNormalUsableSkills()) {
                if (defenderFeature.getType() == SkillType.守护) {
                    remainingDamage = Guard.apply(defenderFeature.getSkill(), cardFeature, this, attacker, defender, remainingDamage);
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
        for (SkillUseInfo cardFeature : card.getNormalUsableSkills()) {
            if (cardFeature.getType() == SkillType.回春) {
                Rejuvenate.apply(cardFeature.getSkill(), this, card);
            }
        }
        {
            RuneInfo rune = card.getOwner().getActiveRuneOf(RuneData.复苏);
            if (rune != null && !card.isWeak()) {
                Rejuvenate.apply(rune.getSkill(), this, card);
            }
        }
    }

    public OnDamagedResult attackCard(CardInfo attacker, CardInfo defender, SkillUseInfo skillUseInfo) throws HeroDieSignal {
        return attackCard(attacker, defender, skillUseInfo, attacker.getCurrentAT());
    }

    public OnDamagedResult attackCard(CardInfo attacker, CardInfo defender, SkillUseInfo skillUseInfo, int damage) throws HeroDieSignal {
        Skill skill = skillUseInfo == null ? null : skillUseInfo.getSkill();
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
    // It is only set when the summoning skill performer is revived by another card.
    public void resolveSummoningFeature(CardInfo card, Field myField, Field opField, CardInfo reviver) throws HeroDieSignal {
        LegionBuff.apply(this, card);
        for (CardInfo fieldCard : myField.getAliveCards()) {
            for (SkillUseInfo skillUseInfo : fieldCard.getNormalUsableSkills()) {
                if (skillUseInfo.getType() == SkillType.王国之力) {
                    RacialBuff.apply(this, skillUseInfo, fieldCard, Race.KINGDOM, SkillEffectType.ATTACK_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.王国守护) {
                    RacialBuff.apply(this, skillUseInfo, fieldCard, Race.KINGDOM, SkillEffectType.MAXHP_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.森林之力) {
                    RacialBuff.apply(this, skillUseInfo, fieldCard, Race.FOREST, SkillEffectType.ATTACK_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.森林守护) {
                    RacialBuff.apply(this, skillUseInfo, fieldCard, Race.FOREST, SkillEffectType.MAXHP_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.蛮荒之力) {
                    RacialBuff.apply(this, skillUseInfo, fieldCard, Race.SAVAGE, SkillEffectType.ATTACK_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.蛮荒守护) {
                    RacialBuff.apply(this, skillUseInfo, fieldCard, Race.SAVAGE, SkillEffectType.MAXHP_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.地狱之力) {
                    RacialBuff.apply(this, skillUseInfo, fieldCard, Race.HELL, SkillEffectType.ATTACK_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.地狱守护) {
                    RacialBuff.apply(this, skillUseInfo, fieldCard, Race.HELL, SkillEffectType.MAXHP_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.本源之力) {
                    RacialBuff.apply(this, skillUseInfo, fieldCard, null, SkillEffectType.ATTACK_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.本源守护) {
                    RacialBuff.apply(this, skillUseInfo, fieldCard, null, SkillEffectType.MAXHP_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.神圣守护) {
                    HolyGuard.apply(this, skillUseInfo, fieldCard);
                } 
            }
        }
        for (SkillUseInfo skillUseInfo : card.getNormalUsableSkills()) {
            if (skillUseInfo.getType() == SkillType.献祭) {
                Sacrifice.apply(this, skillUseInfo, card, reviver);
            } else if (skillUseInfo.getType() == SkillType.反噬) {
                CounterBite.apply(skillUseInfo, this, card);
            }
        }
        for (SkillUseInfo skillUseInfo : card.getUsableSummonSkills()) {
            if (skillUseInfo.getType() == SkillType.烈焰风暴) {
                FireMagic.apply(skillUseInfo.getSkill(), this, card, opField.getOwner(), -1);
            } else if (skillUseInfo.getType() == SkillType.雷暴) {
                LighteningMagic.apply(skillUseInfo, this, card, opField.getOwner(), -1, 35);
            } else if (skillUseInfo.getType() == SkillType.暴风雪) {
                IceMagic.apply(skillUseInfo, this, card, opField.getOwner(), -1, 30, 0);
            } else if (skillUseInfo.getType() == SkillType.毒云) {
                PoisonMagic.apply(skillUseInfo, this, card, opField.getOwner(), -1);
            } else if (skillUseInfo.getType() == SkillType.瘟疫) {
                Plague.apply(skillUseInfo, this, card, opField.getOwner());
            } else if (skillUseInfo.getType() == SkillType.治疗) {
                Heal.apply(skillUseInfo.getSkill(), this, card);
            } else if (skillUseInfo.getType() == SkillType.甘霖) {
                Rainfall.apply(skillUseInfo.getSkill(), this, card);
            } else if (skillUseInfo.getType() == SkillType.祈祷) {
                Pray.apply(skillUseInfo.getSkill(), this, card);
            } else if (skillUseInfo.getType() == SkillType.诅咒) {
                Curse.apply(this, skillUseInfo.getSkill(), card, opField.getOwner());
            } else if (skillUseInfo.getType() == SkillType.群体削弱) {
                WeakenAll.apply(this, skillUseInfo, card, opField.getOwner());
            } else if (skillUseInfo.getType() == SkillType.烈火焚神) {
                BurningFlame.apply(skillUseInfo, this, card, opField.getOwner());
            } else if (skillUseInfo.getType() == SkillType.陷阱) {
                Trap.apply(skillUseInfo, this, card, opField.getOwner());
            } else if (skillUseInfo.getType() == SkillType.送还) {
                Return.apply(this, skillUseInfo.getSkill(), card, opField.getCard(card.getPosition()));
            } else if (skillUseInfo.getType() == SkillType.摧毁) {
                Destroy.apply(this, skillUseInfo.getSkill(), card, opField.getOwner(), 1);
            } else if (skillUseInfo.getType() == SkillType.传送) {
                Transport.apply(this, skillUseInfo.getSkill(), card, opField.getOwner());
            } else if (skillUseInfo.getType() == SkillType.复活) {
                Revive.apply(this, skillUseInfo, card);
            } else if (skillUseInfo.getType() == SkillType.关小黑屋) {
                Enprison.apply(this, skillUseInfo.getSkill(), card, opField.getOwner());
            } else if (skillUseInfo.getType() == SkillType.净化){
                Purify.apply(skillUseInfo, this, card);
            } else if (skillUseInfo.getType() == SkillType.战争怒吼) {
                Soften.apply(skillUseInfo, this, card, opField.getOwner(), -1);
            }
        }
    }

    public void resolveLeaveFeature(CardInfo card, Skill cardSkill) {
        for (SkillUseInfo deadCardSkillUseInfo : card.getNormalUsableSkills()) {
            if (deadCardSkillUseInfo.getType() == SkillType.王国之力) {
                RacialBuff.remove(this, deadCardSkillUseInfo, card, Race.KINGDOM);
            } else if (deadCardSkillUseInfo.getType() == SkillType.王国守护) {
                RacialBuff.remove(this, deadCardSkillUseInfo, card, Race.KINGDOM);
            } else if (deadCardSkillUseInfo.getType() == SkillType.森林之力) {
                RacialBuff.remove(this, deadCardSkillUseInfo, card, Race.FOREST);
            } else if (deadCardSkillUseInfo.getType() == SkillType.森林守护) {
                RacialBuff.remove(this, deadCardSkillUseInfo, card, Race.FOREST);
            } else if (deadCardSkillUseInfo.getType() == SkillType.蛮荒之力) {
                RacialBuff.remove(this, deadCardSkillUseInfo, card, Race.SAVAGE);
            } else if (deadCardSkillUseInfo.getType() == SkillType.蛮荒守护) {
                RacialBuff.remove(this, deadCardSkillUseInfo, card, Race.SAVAGE);
            } else if (deadCardSkillUseInfo.getType() == SkillType.地狱之力) {
                RacialBuff.remove(this, deadCardSkillUseInfo, card, Race.HELL);
            } else if (deadCardSkillUseInfo.getType() == SkillType.地狱守护) {
                RacialBuff.remove(this, deadCardSkillUseInfo, card, Race.HELL);
            } else if (deadCardSkillUseInfo.getType() == SkillType.本源之力) {
                RacialBuff.remove(this, deadCardSkillUseInfo, card, null);
            } else if (deadCardSkillUseInfo.getType() == SkillType.本源守护) {
                RacialBuff.remove(this, deadCardSkillUseInfo, card, null);
            } else if (deadCardSkillUseInfo.getType() == SkillType.神圣守护) {
                HolyGuard.remove(this, deadCardSkillUseInfo, card);
            } else if (deadCardSkillUseInfo.getType() == SkillType.军团王国之力
                    || deadCardSkillUseInfo.getType() == SkillType.军团森林之力
                    || deadCardSkillUseInfo.getType() == SkillType.军团蛮荒之力
                    || deadCardSkillUseInfo.getType() == SkillType.军团地狱之力) {
                LegionBuff.remove(this, deadCardSkillUseInfo, card);
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
                this.resolveDeathFeature(item.getCause().getOwner(), card, item.getCause().getSkill());
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
        for (SkillUseInfo blockFeature : victim.getNormalUsableSkills()) {
            if (blockFeature.getType() == SkillType.脱困) {
                blocked = Escape.isStatusEscaped(blockFeature.getSkill(), this, item, victim);
            }
        }
        return new BlockStatusResult(blocked);
    }

    public void summonCard(Player player, CardInfo card, CardInfo reviver) throws HeroDieSignal {
        List<CardInfo> cards = new ArrayList<CardInfo>();
        cards.add(card);
        summonCards(player, cards, reviver);
    }

    public void summonCards(Player player, List<CardInfo> cards, CardInfo reviver) throws HeroDieSignal {
        for (CardInfo card : cards) {
            card.reset();
            this.stage.getUI().summonCard(player, card);
            player.getField().addCard(card);
            player.getHand().removeCard(card);
        }
        for (CardInfo card : cards) {
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
    }

    /**
     * 
     * @param cardFeature
     * @param attacker
     * @param defender
     * @return Whether block is disabled
     */
    public boolean resolveCounterBlockFeature(Skill cardFeature, CardInfo attacker, CardInfo defender) {
        for (SkillUseInfo attackerFeature : attacker.getAllUsableSkills()) {
            if (attackerFeature.getType() == SkillType.弱点攻击) {
                return WeakPointAttack.isBlockFeatureDisabled(this, attackerFeature.getSkill(), cardFeature,
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
                    if (effect.getCause().equals(rune.getSkillUseInfo())) {
                        if (rune.getSkill().getType().containsTag(SkillTag.永久)) {
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
                PoisonMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.沼泽)) {
                PoisonMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.岩晶)) {
                EnergyArmor.apply(this, rune.getSkillUseInfo(), rune, 1);
            } else if (rune.is(RuneData.毒砂)) {
                PoisonMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.深渊)) {
                PoisonMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.陨星)) {
                Plague.apply(rune.getSkillUseInfo(), this, rune, defenderHero);
            } else if (rune.is(RuneData.死域)) {
                PoisonMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, -1);
            } else if (rune.is(RuneData.霜冻)) {
                IceMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 1, 45, 0);
            } else if (rune.is(RuneData.寒潮)) {
                IceMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 3, 35, 0);
            } else if (rune.is(RuneData.冰锥)) {
                IceMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 1, 45, 0);
            } else if (rune.is(RuneData.暴雨)) {
                WeakenAll.apply(this, rune.getSkillUseInfo(), rune, defenderHero);
            } else if (rune.is(RuneData.清泉)) {
                Rainfall.apply(rune.getSkill(), this, rune);
            } else if (rune.is(RuneData.雪崩)) {
                IceMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 3, 35, 0);
            } else if (rune.is(RuneData.圣泉)) {
                Pray.apply(rune.getSkill(), this, rune);
            } else if (rune.is(RuneData.永冻)) {
                IceMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, -1, 30, 0);
            } else if (rune.is(RuneData.闪电)) {
                LighteningMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 1, 50);
            } else if (rune.is(RuneData.雷云)) {
                LighteningMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 3, 40);
            } else if (rune.is(RuneData.霹雳)) {
                LighteningMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 1, 50);
            } else if (rune.is(RuneData.飞羽)) {
                Snipe.apply(rune.getSkill(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.飓风)) {
                LighteningMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, 3, 40);
            } else if (rune.is(RuneData.春风)) {
                EnergyArmor.apply(this, rune.getSkillUseInfo(), rune, -1);
            } else if (rune.is(RuneData.雷狱)) {
                LighteningMagic.apply(rune.getSkillUseInfo(), this, rune, defenderHero, -1, 35);
            } else if (rune.is(RuneData.火拳)) {
                FireMagic.apply(rune.getSkill(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.热浪)) {
                FireMagic.apply(rune.getSkill(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.流火)) {
                FireMagic.apply(rune.getSkill(), this, rune, defenderHero, 1);
            } else if (rune.is(RuneData.红莲)) {
                Heal.apply(rune.getSkill(), this, rune);
            } else if (rune.is(RuneData.冥火)) {
                BurningFlame.apply(rune.getSkillUseInfo(), this, rune, defenderHero);
            } else if (rune.is(RuneData.淬炼)) {
                AttackUp.apply(this, rune.getSkillUseInfo(), rune, -1);
            } else if (rune.is(RuneData.焚天)) {
                FireMagic.apply(rune.getSkill(), this, rune, defenderHero, 3);
            } else if (rune.is(RuneData.灼魂)) {
                HeavenWrath.apply(this, rune.getSkill(), rune, defenderHero);
            } else if (rune.is(RuneData.灭世)) {
                FireMagic.apply(rune.getSkill(), this, rune, defenderHero, -1);
            }
        }
    }

    public void removeOneRoundEffects(Player activePlayer) {
    }
}
