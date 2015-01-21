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
import cfvbaibai.cardfantasy.engine.skill.Agile;
import cfvbaibai.cardfantasy.engine.skill.AllDelay;
import cfvbaibai.cardfantasy.engine.skill.AllSpeedUp;
import cfvbaibai.cardfantasy.engine.skill.Arouse;
import cfvbaibai.cardfantasy.engine.skill.AttackUp;
import cfvbaibai.cardfantasy.engine.skill.BackStab;
import cfvbaibai.cardfantasy.engine.skill.BasicAtBuff;
import cfvbaibai.cardfantasy.engine.skill.BasicHpBuff;
import cfvbaibai.cardfantasy.engine.skill.Bless;
import cfvbaibai.cardfantasy.engine.skill.Block;
import cfvbaibai.cardfantasy.engine.skill.BloodDrain;
import cfvbaibai.cardfantasy.engine.skill.BloodPaint;
import cfvbaibai.cardfantasy.engine.skill.BloodThirsty;
import cfvbaibai.cardfantasy.engine.skill.BraveFight;
import cfvbaibai.cardfantasy.engine.skill.Burning;
import cfvbaibai.cardfantasy.engine.skill.BurningFlame;
import cfvbaibai.cardfantasy.engine.skill.ChainAttack;
import cfvbaibai.cardfantasy.engine.skill.Confusion;
import cfvbaibai.cardfantasy.engine.skill.CounterAttack;
import cfvbaibai.cardfantasy.engine.skill.CounterBite;
import cfvbaibai.cardfantasy.engine.skill.CounterMagic;
import cfvbaibai.cardfantasy.engine.skill.CounterSummon;
import cfvbaibai.cardfantasy.engine.skill.CriticalAttack;
import cfvbaibai.cardfantasy.engine.skill.Curse;
import cfvbaibai.cardfantasy.engine.skill.DeathMark;
import cfvbaibai.cardfantasy.engine.skill.Destroy;
import cfvbaibai.cardfantasy.engine.skill.Disease;
import cfvbaibai.cardfantasy.engine.skill.Dodge;
import cfvbaibai.cardfantasy.engine.skill.EarthShield;
import cfvbaibai.cardfantasy.engine.skill.EnergyArmor;
import cfvbaibai.cardfantasy.engine.skill.EnergyDrain;
import cfvbaibai.cardfantasy.engine.skill.Enprison;
import cfvbaibai.cardfantasy.engine.skill.Escape;
import cfvbaibai.cardfantasy.engine.skill.Explode;
import cfvbaibai.cardfantasy.engine.skill.FireMagic;
import cfvbaibai.cardfantasy.engine.skill.Guard;
import cfvbaibai.cardfantasy.engine.skill.Heal;
import cfvbaibai.cardfantasy.engine.skill.HealingMist;
import cfvbaibai.cardfantasy.engine.skill.HeavenWrath;
import cfvbaibai.cardfantasy.engine.skill.HeroKiller;
import cfvbaibai.cardfantasy.engine.skill.HolyFire;
import cfvbaibai.cardfantasy.engine.skill.HolyGuard;
import cfvbaibai.cardfantasy.engine.skill.HolyShield;
import cfvbaibai.cardfantasy.engine.skill.IceArmor;
import cfvbaibai.cardfantasy.engine.skill.IceMagic;
import cfvbaibai.cardfantasy.engine.skill.Immobility;
import cfvbaibai.cardfantasy.engine.skill.Immue;
import cfvbaibai.cardfantasy.engine.skill.LegionBuff;
import cfvbaibai.cardfantasy.engine.skill.LighteningMagic;
import cfvbaibai.cardfantasy.engine.skill.MagicShield;
import cfvbaibai.cardfantasy.engine.skill.ManaErode;
import cfvbaibai.cardfantasy.engine.skill.NoEffect;
import cfvbaibai.cardfantasy.engine.skill.OneDelay;
import cfvbaibai.cardfantasy.engine.skill.Overdraw;
import cfvbaibai.cardfantasy.engine.skill.Penetration;
import cfvbaibai.cardfantasy.engine.skill.Plague;
import cfvbaibai.cardfantasy.engine.skill.PoisonMagic;
import cfvbaibai.cardfantasy.engine.skill.Pray;
import cfvbaibai.cardfantasy.engine.skill.Purify;
import cfvbaibai.cardfantasy.engine.skill.Pursuit;
import cfvbaibai.cardfantasy.engine.skill.RaceChange;
import cfvbaibai.cardfantasy.engine.skill.RacialAttackSkill;
import cfvbaibai.cardfantasy.engine.skill.RacialBuff;
import cfvbaibai.cardfantasy.engine.skill.RacialShield;
import cfvbaibai.cardfantasy.engine.skill.Rainfall;
import cfvbaibai.cardfantasy.engine.skill.Reincarnation;
import cfvbaibai.cardfantasy.engine.skill.Rejuvenate;
import cfvbaibai.cardfantasy.engine.skill.Resurrection;
import cfvbaibai.cardfantasy.engine.skill.Return;
import cfvbaibai.cardfantasy.engine.skill.Revenge;
import cfvbaibai.cardfantasy.engine.skill.Revive;
import cfvbaibai.cardfantasy.engine.skill.Sacrifice;
import cfvbaibai.cardfantasy.engine.skill.Seal;
import cfvbaibai.cardfantasy.engine.skill.Snipe;
import cfvbaibai.cardfantasy.engine.skill.Soften;
import cfvbaibai.cardfantasy.engine.skill.SpeedUp;
import cfvbaibai.cardfantasy.engine.skill.Spike;
import cfvbaibai.cardfantasy.engine.skill.Summon;
import cfvbaibai.cardfantasy.engine.skill.TimeBack;
import cfvbaibai.cardfantasy.engine.skill.Transport;
import cfvbaibai.cardfantasy.engine.skill.Trap;
import cfvbaibai.cardfantasy.engine.skill.Tsukomi;
import cfvbaibai.cardfantasy.engine.skill.Unbending;
import cfvbaibai.cardfantasy.engine.skill.WeakPointAttack;
import cfvbaibai.cardfantasy.engine.skill.Weaken;
import cfvbaibai.cardfantasy.engine.skill.WeakenAll;
import cfvbaibai.cardfantasy.engine.skill.WinningPursuit;
import cfvbaibai.cardfantasy.engine.skill.Wound;
import cfvbaibai.cardfantasy.engine.skill.Wrath;
import cfvbaibai.cardfantasy.engine.skill.Zealot;


public class SkillResolver {
    private StageInfo stage;

    public SkillResolver(StageInfo stage) {
        this.stage = stage;
    }

    public StageInfo getStage() {
        return this.stage;
    }

    private boolean isPhysicalAttackSkill(Skill skill) {
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
        if (position > 0) {
            CardInfo leftSide = field.getCard(position - 1);
            if (leftSide != null) {
                cards.add(leftSide);
            }
        }
        if (card != null) {
            cards.add(card);
        }
        CardInfo rightSide = field.getCard(position + 1);
        if (rightSide != null) {
            cards.add(rightSide);
        }
        return cards;
    }

    public void resolvePreAttackSkills(Player attacker, Player defender) throws HeroDieSignal {
        List<CardInfo> cards = attacker.getField().getAliveCards();
        for (CardInfo card : cards) {
            for (SkillUseInfo skillUseInfo : card.getNormalUsableSkills()) {
                if (skillUseInfo.getType() == SkillType.神性祈求) {
                    Purify.apply(skillUseInfo, this, card);
                }
            }
        }
    }

    public void resolvePreAttackSkills(CardInfo attacker, Player defender) throws HeroDieSignal {
        for (SkillUseInfo skillUseInfo : attacker.getNormalUsableSkills()) {
            if (attacker.isDead()) {
                continue;
            }
            if (skillUseInfo.getType() == SkillType.透支) {
                Overdraw.apply(this, skillUseInfo, attacker);
            }
        }
        for (SkillUseInfo skillUseInfo : attacker.getNormalUsableSkills()) {
            if (attacker.isDead()) {
                continue;
            }
            if (skillUseInfo.getType() == SkillType.未知) {
                // JUST A PLACEHOLDER
            } else if (skillUseInfo.getType() == SkillType.关小黑屋) {
                Enprison.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.吐槽) {
                Tsukomi.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.火球) {
                FireMagic.apply(skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.火墙) {
                FireMagic.apply(skillUseInfo.getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.烈焰风暴) {
                FireMagic.apply(skillUseInfo.getSkill(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.落雷) {
                LighteningMagic.apply(skillUseInfo, this, attacker, defender, 1, 50);
            } else if (skillUseInfo.getType() == SkillType.连环闪电) {
                LighteningMagic.apply(skillUseInfo, this, attacker, defender, 3, 40);
            } else if (skillUseInfo.getType() == SkillType.雷暴) {
                LighteningMagic.apply(skillUseInfo, this, attacker, defender, -1, 35);
            } else if (skillUseInfo.getType() == SkillType.冰弹) {
                IceMagic.apply(skillUseInfo, this, attacker, defender, 1, 45, 0);
            } else if (skillUseInfo.getType() == SkillType.霜冻新星) {
                IceMagic.apply(skillUseInfo, this, attacker, defender, 3, 35, 0);
            } else if (skillUseInfo.getType() == SkillType.暴风雪) {
                IceMagic.apply(skillUseInfo, this, attacker, defender, -1, 30, 0);
            } else if (skillUseInfo.getType() == SkillType.寒霜冲击) {
                IceMagic.apply(skillUseInfo, this, attacker, defender, -1, 50, 45 * defender.getField().getAliveCards().size());
            } else if (skillUseInfo.getType() == SkillType.毒液) {
                PoisonMagic.apply(skillUseInfo, this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.毒雾) {
                PoisonMagic.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.毒云) {
                PoisonMagic.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.陷阱) {
                Trap.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.狙击) {
                Snipe.apply(skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.魔神之刃) {
                Snipe.apply(skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.治疗) {
                Heal.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.甘霖) {
                Rainfall.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.治疗之雾) {
                HealingMist.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.祈祷) {
                Pray.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.复活) {
                Revive.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.背刺) {
                BackStab.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.群体削弱) {
                WeakenAll.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.回魂) {
                Resurrection.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.二重狙击) {
                Snipe.apply(skillUseInfo.getSkill(), this, attacker, defender, 2);
            } else if (skillUseInfo.getType() == SkillType.迷魂) {
                Confusion.apply(skillUseInfo, this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.烈火焚神) {
                BurningFlame.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.诅咒) {
                Curse.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.魔神之咒) {
                Curse.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.摧毁) {
                Destroy.apply(this, skillUseInfo.getSkill(), attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.瘟疫) {
                Plague.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.血炼) {
                BloodPaint.apply(skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.鲜血盛宴) {
                BloodPaint.apply(skillUseInfo.getSkill(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.天谴) {
                HeavenWrath.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.封印) {
                Seal.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.圣炎) {
                HolyFire.apply(skillUseInfo.getSkill(), this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.法力侵蚀) {
                ManaErode.apply(skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.趁胜追击) {
                WinningPursuit.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.复仇) {
                Revenge.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.奋战) {
                BraveFight.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.振奋) {
                Arouse.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.全体阻碍){
                AllDelay.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.全体加速){
                AllSpeedUp.apply(skillUseInfo, this, attacker);
            } else if (skillUseInfo.getType() == SkillType.阻碍) {
                OneDelay.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.加速) {
                SpeedUp.apply(skillUseInfo, this, attacker);
            } else if (skillUseInfo.getType() == SkillType.净化) {
                Purify.apply(skillUseInfo, this, attacker);
            } else if (skillUseInfo.getType() == SkillType.虚弱) {
                Soften.apply(skillUseInfo, this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.圣光洗礼 || skillUseInfo.getType() == SkillType.森林沐浴 ||
                       skillUseInfo.getType() == SkillType.蛮荒威压 || skillUseInfo.getType() == SkillType.地狱同化) {
                RaceChange.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.战争怒吼) {
                Soften.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.召唤王国战士) {
                Summon.apply(this, skillUseInfo, attacker, "圣骑士", "魔剑士");
            } else if (skillUseInfo.getType() == SkillType.召唤噩梦护卫) {
                Summon.apply(this, skillUseInfo, attacker, "时光女神", "金属巨龙");
            } else if (skillUseInfo.getType() == SkillType.召唤邪龙护卫) {
                Summon.apply(this, skillUseInfo, attacker, "亡灵守护神", "光明之龙");
            } else if (skillUseInfo.getType() == SkillType.召唤复仇护卫) {
                Summon.apply(this, skillUseInfo, attacker, "雷兽", "末日预言师");
            } else if (skillUseInfo.getType() == SkillType.召唤花仙子) {
                Summon.apply(this, skillUseInfo, attacker, "花仙子", "花仙子");
            } else if (skillUseInfo.getType() == SkillType.召唤火焰乌鸦) {
                Summon.apply(this, skillUseInfo, attacker, "火焰乌鸦");
            } else if (skillUseInfo.getType() == SkillType.召唤人马巡逻者) {
                Summon.apply(this, skillUseInfo, attacker, "人马巡逻者", "人马巡逻者");
            } else if (skillUseInfo.getType() == SkillType.召唤女神侍者) {
                Summon.apply(this, skillUseInfo, attacker, "女神侍者", "女神侍者");
            } else if (skillUseInfo.getType() == SkillType.召唤树人守护者) {
                Summon.apply(this, skillUseInfo, attacker, "霜雪树人", "树人祭司");
            }
        }
        RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.飞岩);
        if (rune != null && !attacker.justRevived()) {
            Snipe.apply(rune.getSkill(), this, attacker, defender, 1);
        }
    }

    public void resolvePostAttackSkills(CardInfo attacker, Player defender) {

    }

    public void resolveCounterAttackSkills(CardInfo attacker, CardInfo defender, Skill attackSkill,
            OnAttackBlockingResult result, OnDamagedResult damagedResult) throws HeroDieSignal {
        if (isPhysicalAttackSkill(attackSkill) && damagedResult.actualDamage > 0) {
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
                if (rune != null && !defender.justRevived()) {
                    Spike.apply(rune.getSkill(), this, attacker, defender, attackSkill, result.getDamage());
                }
            }
            {
                RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.漩涡);
                if (rune != null && !defender.justRevived()) {
                    CounterAttack.apply(rune.getSkill(), this, attacker, defender, result.getDamage());
                }
            }
            if (!defender.isDead()) {
                for (SkillUseInfo skillUseInfo : defender.getNormalUsableSkills()) {
                    if (skillUseInfo.getType() == SkillType.狂热) {
                        Zealot.apply(skillUseInfo, this, attacker, defender, result);
                    }
                }
                RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.怒涛);
                if (rune != null && !defender.justRevived()) {
                    Zealot.apply(rune.getSkillUseInfo(), this, attacker, defender, result);
                }
            }
        }
    }

    public OnAttackBlockingResult resolveHealBlockingSkills(EntityInfo healer, CardInfo healee, Skill cardSkill) {
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
    public OnAttackBlockingResult resolveAttackBlockingSkills(EntityInfo attacker, CardInfo defender,
            Skill attackSkill, int damage) throws HeroDieSignal {
        OnAttackBlockingResult result = new OnAttackBlockingResult(true, 0);
        CardStatus status = attacker.getStatus();
        Unbending.isSkillEscaped(this, attacker, attackSkill, defender, result);
        if (!result.isAttackable()) {
            return result;
        }
        if (isPhysicalAttackSkill(attackSkill)) {
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
                    if (rune != null && !defender.justRevived()) {
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
                    if (rune != null && !defender.justRevived()) {
                        result.setDamage(IceArmor.apply(rune.getSkill(), this, cardAttacker, defender,
                                result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.岩壁);
                    if (rune != null && !defender.justRevived()) {
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
            if (!attackSkill.isDeathSkill() && isAttackerDisabled && attackSkill.getType() != SkillType.邪灵汲取) {
                // BUGBUG: Why we need go here? Hack 邪灵汲取 here temporarily.
                stage.getUI().attackBlocked(attacker, defender, attackSkill, null);
                result.setAttackable(false);
            } else {
                for (SkillUseInfo blockSkillUseInfo : defender.getNormalUsableSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.法力反射) {
                        if (CounterMagic.isSkillBlocked(this, blockSkillUseInfo.getSkill(), attackSkill,
                                attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getRuneBox().getRuneOf(RuneData.石林);
                    if (rune != null && rune.isActivated() && !defender.justRevived()) {
                        if (CounterMagic.isSkillBlocked(this, rune.getSkill(), attackSkill, attacker,
                                defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }

                for (SkillUseInfo blockSkillUseInfo : defender.getNormalUsableSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.免疫) {
                        if (Immue.isSkillBlocked(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockSkillUseInfo.getType() == SkillType.无效) {
                        if (NoEffect.isSkillBlocked(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockSkillUseInfo.getType() == SkillType.脱困) {
                        if (Escape.isSkillEscaped(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    } else if (blockSkillUseInfo.getType() == SkillType.不动) {
                        if (Immobility.isSkillBlocked(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.鬼步);
                    if (rune != null && !defender.justRevived()) {
                        if (Escape.isSkillEscaped(this, rune.getSkill(), attackSkill, attacker, defender)) {
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
                    if (rune != null && !defender.justRevived()) {
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
    public void resolveDeathSkills(EntityInfo killerCard, CardInfo deadCard, Skill cardSkill, OnDamagedResult result) throws HeroDieSignal {
        if (deadCard.hasDeadOnce()) {
            return;
        }
        // Two scenarios where death skill should be resolved:
        // 1. cardDead
        // 2. unbending triggered
        if (!result.cardDead && !result.unbending) {
            return;
        }
        if (result.cardDead && !result.unbending) {
            deadCard.setDeadOnce(true);
        }
        // HACKHACK: Cannot find better way to handle 不屈
        if (!deadCard.getStatus().containsStatus(CardStatusType.不屈)) {
            resolveLeaveSkills(deadCard);
        }
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
            } else if (deadCardSkillUseInfo.getType() == SkillType.回魂) {
                Resurrection.apply(this, deadCardSkillUseInfo, deadCard);
            }
        }
        for (SkillUseInfo deadCardSkillUseInfo : deadCard.getAllUsableSkills()) {
            if (deadCardSkillUseInfo.getType() == SkillType.自爆) {
                Explode.apply(this, deadCardSkillUseInfo.getSkill(), killerCard, deadCard);
            }
        }
        {
            RuneInfo rune = deadCard.getOwner().getActiveRuneOf(RuneData.爆裂);
            if (rune != null && !deadCard.justRevived()) {
                Explode.apply(this, rune.getSkill(), killerCard, deadCard);
            }
        }
        if (deadCard.getStatus().containsStatus(CardStatusType.死印)) {
            DeathMark.explode(this, deadCard, result);
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
            if (rune != null && !deadCard.justRevived()) {
                Reincarnation.apply(this, rune.getSkill(), deadCard);
            }
        }
    }

    public void resolvePostAttackSkills(CardInfo attacker, CardInfo defender, Player defenderHero, Skill attackSkill,
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
            if (rune != null && !attacker.justRevived()) {
                BloodDrain.apply(rune.getSkill(), this, attacker, defender, normalAttackDamage);
            }
        }
    }
    
    public void resolveExtraAttackSkills(CardInfo attacker, CardInfo defender, Player defenderHero, Skill attackSkill, OnDamagedResult damageResult) throws HeroDieSignal {
        int normalAttackDamage = damageResult.actualDamage;
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
                    ChainAttack.apply(this, skillUseInfo, attacker, defender, attackSkill, damageResult.originalDamage);
                } else if (skillUseInfo.getType() == SkillType.疾病) {
                    Disease.apply(skillUseInfo, this, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (!attacker.isDead()) {
            RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.洞察);
            if (rune != null && !attacker.justRevived()) {
                BloodThirsty.apply(this, rune.getSkillUseInfo(), attacker, normalAttackDamage);
            }
        }
    }

    public void resolvePreAttackHeroSkills(CardInfo attacker, Player defenderPlayer) {
        for (SkillUseInfo skillUseInfo : attacker.getNormalUsableSkills()) {
           if (skillUseInfo.getType() == SkillType.英雄杀手) {
               HeroKiller.apply(this, skillUseInfo, attacker, defenderPlayer);
           }
        }
    }
    
    /**
     * 
     * @param attacker
     * @param defender
     * @param prior if TRUE, this is resolved before pre-attack skills are resolved.
     *              Currently, only RETURN falls in this case.
     * @throws HeroDieSignal
     */
    public void resolvePreAttackCardSkills(CardInfo attacker, CardInfo defender, boolean prior) throws HeroDieSignal {
        for (SkillUseInfo skillUseInfo : attacker.getNormalUsableSkills()) {
            if (prior) {
                if (skillUseInfo.getType() == SkillType.送还) {
                    Return.apply(this, skillUseInfo.getSkill(), attacker, defender);
                }
            } else {
                if (skillUseInfo.getType() == SkillType.圣光) {
                    RacialAttackSkill.apply(this, skillUseInfo, attacker, defender, Race.HELL);
                } else if (skillUseInfo.getType() == SkillType.要害) {
                    RacialAttackSkill.apply(this, skillUseInfo, attacker, defender, Race.SAVAGE);
                } else if (skillUseInfo.getType() == SkillType.暗杀) {
                    RacialAttackSkill.apply(this, skillUseInfo, attacker, defender, Race.KINGDOM);
                } else if (skillUseInfo.getType() == SkillType.污染) {
                    RacialAttackSkill.apply(this, skillUseInfo, attacker, defender, Race.FOREST);
                } else if (skillUseInfo.getType() == SkillType.暴击) {
                    CriticalAttack.apply(this, skillUseInfo, attacker, defender);
                } else if (skillUseInfo.getType() == SkillType.穷追猛打) {
                    Pursuit.apply(this, skillUseInfo, attacker, defender);
                } else if (skillUseInfo.getType() == SkillType.战意) {
                    Wrath.apply(this, skillUseInfo, attacker, defender);
                } else if (skillUseInfo.getType() == SkillType.死亡印记) {
                    DeathMark.apply(this, skillUseInfo, attacker, defender);
                }
            }
        }
        if (!prior) {
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.绝杀);
                if (rune != null && !attacker.justRevived()) {
                    Wrath.apply(this, rune.getSkillUseInfo(), attacker, defender);
                }
            }
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.寒伤);
                if (rune != null && !attacker.justRevived()) {
                    CriticalAttack.apply(this, rune.getSkillUseInfo(), attacker, defender);
                }
            }
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.扬旗);
                if (rune != null && !attacker.justRevived()) {
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
                RacialAttackSkill.remove(this, effect.getCause(), card);
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
            } else if (type == SkillType.奋战) {
                BraveFight.remove(this, effect.getCause(), card);
            } else if (type == SkillType.振奋) {
                Arouse.remove(this, effect.getCause(), card);
            } else if (type == SkillType.英雄杀手) {
                HeroKiller.remove(this, effect.getCause(), card);
            }
        }
    }

    public OnDamagedResult applyDamage(CardInfo card, Skill skill, int damage) {
        List<CardStatusItem> unbendingStatusItems = card.getStatus().getStatusOf(CardStatusType.不屈);
        if (!unbendingStatusItems.isEmpty()) {
            if (skill != null && skill.getType() == SkillType.吸血) {
                // 不屈状态下可以吸血
            } else {
                this.getStage().getUI().unbend(card, unbendingStatusItems.get(0));
                damage = 0;
            }
        }
        int actualDamage = card.applyDamage(damage);
        OnDamagedResult result = new OnDamagedResult();
        result.originalDamage = damage;
        result.actualDamage = actualDamage;
        if (card.getHP() <= 0) {
            result.cardDead = true;
            for (SkillUseInfo skillUseInfo : card.getNormalUsableSkills()) {
                if (skillUseInfo.getType() == SkillType.不屈) {
                    // BUGBUG: The original game does not set cardDead to false
                    // result.cardDead = false
                    result.unbending = Unbending.apply(skillUseInfo, this, card);
                 }
            }
            if (!result.unbending) {
                cardDead(card);
            } else {
                result.cardDead = false;
            }
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
        deadCard.getPosition(); // Save cached position
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

    public void attackHero(EntityInfo attacker, Player defenderPlayer, Skill cardSkill, int damage)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        try {
            if (isPhysicalAttackSkill(cardSkill) && attacker.getStatus().containsStatus(CardStatusType.麻痹)) {
                return;
            }
            stage.getUI().useSkillToHero(attacker, defenderPlayer, cardSkill);
            if (damage > defenderPlayer.getHP()) {
                damage = defenderPlayer.getHP();
            }
            if (damage >= 0) {
                int remainingDamage = this.resolveAttackHeroBlockingSkills(attacker, defenderPlayer, cardSkill, damage);
                if (remainingDamage > 0) {
                    stage.getUI().attackHero(attacker, defenderPlayer, cardSkill, remainingDamage);
                    defenderPlayer.setHP(defenderPlayer.getHP() - remainingDamage);
                }
            } else {
                if (defenderPlayer.getHP() - damage > defenderPlayer.getMaxHP()) {
                    damage = defenderPlayer.getHP() - defenderPlayer.getMaxHP();
                }
                stage.getUI().healHero(attacker, defenderPlayer, cardSkill, -damage);
                defenderPlayer.setHP(defenderPlayer.getHP() - damage);
            }
            if (defenderPlayer.getHP() > defenderPlayer.getMaxHP()) {
                throw new CardFantasyRuntimeException("Hero MaxHP < HP");
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

    private int resolveAttackHeroBlockingSkills(EntityInfo attacker, Player defenderPlayer, Skill cardSkill,
            int damage) throws HeroDieSignal {
        int remainingDamage = damage;
        for (CardInfo defender : defenderPlayer.getField().getAliveCards()) {
            if (defender == null) {
                continue;
            }
            for (SkillUseInfo defenderSkill : defender.getNormalUsableSkills()) {
                if (defenderSkill.getType() == SkillType.守护) {
                    remainingDamage = Guard.apply(defenderSkill.getSkill(), cardSkill, this, attacker, defender, remainingDamage);
                    if (remainingDamage == 0) {
                        return 0;
                    }
                }
            }
        }
        return remainingDamage;
    }

    public void resolveCardRoundEndingSkills(CardInfo card) {
        if (card == null) {
            return;
        }
        CardStatus status = card.getStatus();
        if (status.containsStatus(CardStatusType.锁定)) {
            return;
        }
        for (SkillUseInfo cardSkillUseInfo : card.getNormalUsableSkills()) {
            if (cardSkillUseInfo.getType() == SkillType.回春) {
                Rejuvenate.apply(cardSkillUseInfo.getSkill(), this, card);
            }
        }
        {
            RuneInfo rune = card.getOwner().getActiveRuneOf(RuneData.复苏);
            if (rune != null && !card.justRevived()) {
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

        OnAttackBlockingResult blockingResult = stage.getResolver().resolveAttackBlockingSkills(
                attacker, defender, skill, damage);
        if (!blockingResult.isAttackable()) {
            return null;
        }
        this.stage.getUI().attackCard(attacker, defender, skill, blockingResult.getDamage());
        OnDamagedResult damagedResult = stage.getResolver().applyDamage(defender, skill, blockingResult.getDamage());

        resolvePostAttackSkills(attacker, defender, defender.getOwner(), skill, damagedResult.actualDamage);
        stage.getResolver().resolveDeathSkills(attacker, defender, skill, damagedResult);

        resolveExtraAttackSkills(attacker, defender, defender.getOwner(), skill, damagedResult);
        resolveCounterAttackSkills(attacker, defender, skill, blockingResult, damagedResult);

        return damagedResult;
    }

    public CardInfo pickHealee(EntityInfo healer) {
        Field field = healer.getOwner().getField();
        CardInfo healee = null;
        for (CardInfo card : field.getAliveCards()) {
            if (card.getStatus().containsStatus(CardStatusType.不屈)) {
                continue;
            }
            if (healee == null || card.getLostHP() > healee.getLostHP()) {
                healee = card;
            }
        }
        return healee;
    }

    public void resolveRacialBuffSkills(CardInfo card, Field myField) {

    }

    public void resolveEnteringSkills(CardInfo card, Field myField, Field opField, CardInfo reviver) throws HeroDieSignal {

    }

    // reviver: for most of the cases, it should be null.
    // It is only set when the summoning skill performer is revived by another card.
    public void resolveSummoningSkills(List<CardInfo> summonedCards, Field myField, Field opField, CardInfo reviver) throws HeroDieSignal {
        Player player = myField.getOwner();
        for (SkillUseInfo skillUseInfo : player.getCardBuffs()) {
            for (CardInfo card : summonedCards) {
                if (skillUseInfo.getType() == SkillType.军团王国之力) {
                    LegionBuff.apply(this, card, skillUseInfo, Race.KINGDOM);
                } else if (skillUseInfo.getType() == SkillType.军团森林之力) {
                    LegionBuff.apply(this, card, skillUseInfo, Race.FOREST);
                } else if (skillUseInfo.getType() == SkillType.军团蛮荒之力) {
                    LegionBuff.apply(this, card, skillUseInfo, Race.SAVAGE);
                } else if (skillUseInfo.getType() == SkillType.军团地狱之力) {
                    LegionBuff.apply(this, card, skillUseInfo, Race.HELL);
                } else if (skillUseInfo.getType() == SkillType.原始体力调整) {
                    BasicHpBuff.apply(this, skillUseInfo, card);
                } else if (skillUseInfo.getType() == SkillType.原始攻击调整) {
                    BasicAtBuff.apply(this, skillUseInfo, card);
                }
            }
        }

        for (CardInfo summonedCard : summonedCards) {
            if (!summonedCard.isDead()) {
                summonedCard.applySurvivalStatus();
            }
        }

        // Racial buff
        for (CardInfo fieldCard : myField.getAliveCards()) {
            // 主动种族BUFF
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

        // Standard summoning skills
        for (CardInfo card : summonedCards) {
            if (myField.getCard(card.getPosition()) == null) {
                // Killed or returned by other summoning skills 
                continue;
            }
            for (SkillUseInfo skillUseInfo : card.getAllUsableSkills()) {
                if (skillUseInfo.getSkill().isSummonSkill()) {
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
                    } else if (skillUseInfo.getType() == SkillType.关小黑屋) {
                        Enprison.apply(this, skillUseInfo.getSkill(), card, opField.getOwner());
                    } else if (skillUseInfo.getType() == SkillType.净化){
                        Purify.apply(skillUseInfo, this, card);
                    } else if (skillUseInfo.getType() == SkillType.战争怒吼) {
                        Soften.apply(skillUseInfo, this, card, opField.getOwner(), -1);
                    } else if (skillUseInfo.getType() == SkillType.阻碍) {
                        OneDelay.apply(skillUseInfo, this, card, opField.getOwner());
                    } else if (skillUseInfo.getType() == SkillType.全体阻碍) {
                        AllDelay.apply(skillUseInfo, this, card, opField.getOwner());
                    } else if (skillUseInfo.getType() == SkillType.烈焰风暴) {
                        FireMagic.apply(skillUseInfo.getSkill(), this, card, opField.getOwner(), -1);
                    } else if (
                        skillUseInfo.getType() == SkillType.圣光洗礼 || skillUseInfo.getType() == SkillType.森林沐浴 ||
                        skillUseInfo.getType() == SkillType.蛮荒威压 || skillUseInfo.getType() == SkillType.地狱同化) {
                        RaceChange.apply(this, skillUseInfo, card, opField.getOwner());
                    } 
                }
                else if (!skillUseInfo.getSkill().isDeathSkill() && skillUseInfo.getType() == SkillType.反噬) {
                    CounterBite.apply(skillUseInfo, this, card);
                }
            }
        }

        for (CardInfo card : summonedCards) {
            if (myField.getCard(card.getPosition()) == null) {
                // Killed or returned by other summoning skills 
                continue;
            }
            for (SkillUseInfo skillUseInfo : card.getAllUsableSkills()) {
                if (skillUseInfo.getType() == SkillType.时光倒流) {
                    TimeBack.apply(skillUseInfo, this, myField.getOwner(), opField.getOwner());
                } else if (skillUseInfo.getType() == SkillType.献祭) {
                    Sacrifice.apply(this, skillUseInfo, card, reviver);
                } else if (skillUseInfo.getType() == SkillType.复活 && skillUseInfo.getSkill().isSummonSkill()) {
                    Revive.apply(this, skillUseInfo, card);
                }
            }
        }
    }

    public void resolveLeaveSkills(CardInfo card) {
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

            OnDamagedResult result = this.applyDamage(card, item.getCause().getSkill(), item.getEffect());
            this.resolveDeathSkills(item.getCause().getOwner(), card, item.getCause().getSkill(), result);
            if (result.cardDead) {
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

    public BlockStatusResult resolveBlockStatusSkills(EntityInfo attacker, CardInfo victim, SkillUseInfo skillUseInfo, CardStatusItem item) {
        if (Unbending.isStatusEscaped(this, item, victim)) {
            return new BlockStatusResult(true);
        }
        for (RuneInfo rune : victim.getOwner().getRuneBox().getRunes()) {
            if (!rune.isActivated()) {
                continue;
            }
            if (rune.is(RuneData.鬼步)) {
                if (Escape.isStatusEscaped(rune.getSkill(), this, item, victim)) {
                    return new BlockStatusResult(true);
                }
            }
        }
        for (SkillUseInfo blockSkillUseInfo : victim.getNormalUsableSkills()) {
            if (blockSkillUseInfo.getType() == SkillType.脱困) {
                if (Escape.isStatusEscaped(blockSkillUseInfo.getSkill(), this, item, victim)) {
                    return new BlockStatusResult(true);
                }
            }
        }
        return new BlockStatusResult(false);
    }

    public void summonCard(Player player, CardInfo card, CardInfo reviver) throws HeroDieSignal {
        List<CardInfo> cards = new ArrayList<CardInfo>();
        cards.add(card);
        summonCards(player, cards, reviver);
    }

    /**
     * 1. Process racial buff skills
     * 2. Process summoning skills
     * @param player
     * @param cards
     * @param reviver
     * @throws HeroDieSignal
     */
    public void summonCards(Player player, List<CardInfo> cards, CardInfo reviver) throws HeroDieSignal {
        for (CardInfo card : cards) {
            card.reset();
            this.stage.getUI().summonCard(player, card);
            player.getField().addCard(card);
            player.getHand().removeCard(card);
        }
        if (this.stage.getPlayerCount() != 2) {
            throw new CardFantasyRuntimeException("There are " + this.stage.getPlayerCount()
                    + " player(s) in the stage, but expect 2");
        }
        Player enemy = null;
        for (Player other : stage.getPlayers()) {
            if (other != player) {
                enemy = other;
            }
        }

        this.resolveSummoningSkills(cards, player.getField(), enemy.getField(), reviver);
    }

    /**
     * 
     * @param cardSkill
     * @param attacker
     * @param defender
     * @return Whether block is disabled
     */
    public boolean resolveCounterBlockSkill(Skill cardSkill, CardInfo attacker, CardInfo defender) {
        for (SkillUseInfo attackerSkillUseInfo : attacker.getNormalUsableSkills()) {
            if (attackerSkillUseInfo.getType() == SkillType.弱点攻击) {
                return WeakPointAttack.isBlockSkillDisabled(this, attackerSkillUseInfo.getSkill(), cardSkill,
                        attacker, defender);
            }
        }
        return false;
    }
    
    public boolean resolverCounterAttackBlockSkill(Skill counterAttackSkill, CardInfo attacker, CardInfo counterAttacker) {
        for (SkillUseInfo skillUseInfo : attacker.getNormalUsableSkills()) {
            if (skillUseInfo.getType() == SkillType.灵巧) {
                return Agile.isCounterAttackSkillDisabled(this, skillUseInfo.getSkill(), counterAttackSkill, attacker, counterAttacker);
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
            } else if (activator.getType() == RuneActivationType.FieldDiff) {
                shouldActivate = enemy.getField().getAliveCards().size() - player.getField().getAliveCards().size() > activator.getThreshold();
            }
            
            if (!shouldActivate) {
                continue;
            }

            // Special logic for 永冻 & 春风 & 清泉 & 冰封 & 灼魂.
            if (rune.is(RuneData.清泉)) {
                if (player.getField().getAliveCards().isEmpty()) {
                    shouldActivate = false;
                } else {
                    boolean anyCardWounded = false;
                    for (CardInfo card : player.getField().getAliveCards()) {
                        if (card.isWounded()) {
                            anyCardWounded = true;
                            break;
                        }
                    }
                    if (!anyCardWounded) {
                        shouldActivate = false;
                    }
                }
            } else if (rune.is(RuneData.春风) || rune.is(RuneData.冰封)) {
                if (player.getField().getAliveCards().isEmpty()) {
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
                            throw new CardFantasyRuntimeException("Invalid skill effect type " + effect.getType());
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
            } else if (rune.is(RuneData.玄石)) {
                AllSpeedUp.apply(rune.getSkillUseInfo(), this, rune);
            } else if (rune.is(RuneData.龙吟)) {
                Bless.apply(rune.getSkillUseInfo().getSkill(), this, rune);
            } else if (rune.is(RuneData.神祈)) {
                Purify.apply(rune.getSkillUseInfo(), this, rune);
            }
        }
    }

    public void removeOneRoundEffects(Player activePlayer) {
        for (CardInfo card : activePlayer.getField().getAliveCards()) {
            this.removeStatus(card, CardStatusType.不屈);
        }
    }
}
