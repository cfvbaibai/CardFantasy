package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.*;
import cfvbaibai.cardfantasy.engine.skill.*;


public class SkillResolver {
    private StageInfo stage;

    public SkillResolver(StageInfo stage) {
        this.stage = stage;
    }

    public StageInfo getStage() {
        return this.stage;
    }

    public boolean isPhysicalAttackSkill(Skill skill) {
        return skill == null || skill.getType().containsTag(SkillTag.物理攻击);
    }

    public boolean isMagicalSkill(Skill skill) {
        return skill != null && skill.getType().containsTag(SkillTag.魔法);
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
        List<CardInfo> cards = this.getCardsOnSides(field, position);
        CardInfo card = field.getCard(position);
        if (card != null) {
            cards.add(card);
        }
        return cards;
    }

    public List<CardInfo> getCardsOnSides(Field field, int position) {
        List<CardInfo> cards = new ArrayList<CardInfo>();
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

    public List<CardInfo> getFrontCards(Field field, int position) {
        List<CardInfo> cards = this.getCardsOnFront(field, position);
        //前置卡不包含本身
//        CardInfo card = field.getCard(position);
//        if (card != null) {
//            cards.add(card);
//        }
        return cards;
    }

    public List<CardInfo> getCardsOnFront(Field field, int position) {
        List<CardInfo> cards = new ArrayList<CardInfo>();
        CardInfo frontCard = null;
        if (position > 0) {
            for (int i = 0; i < position; i++) {
                frontCard = field.getCard(i);
                if (frontCard != null) {
                    cards.add(frontCard);
                }
            }
        }
        return cards;
    }

    public void resolvePreAttackSkills(Player attacker, Player defender) throws HeroDieSignal {
        List<CardInfo> cards = attacker.getField().getAliveCards();
        for (CardInfo card : cards) {
            for (SkillUseInfo skillUseInfo : card.getUsableNormalSkills()) {
                if (skillUseInfo.getType() == SkillType.神性祈求) {
                    Purify.apply(skillUseInfo, this, card, -1);
                } else if (skillUseInfo.getType() == SkillType.净魂领域) {
                    Purify.apply(skillUseInfo, this, card, -2);
                }
                if (skillUseInfo.getType() == SkillType.无刀取) {
                    HolyShield.resetApply(skillUseInfo, this, card);
                }
            }
        }
    }

    public void resolvePreAttackSkills(CardInfo attacker, Player defender,int status) throws HeroDieSignal {
        for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
            if (attacker.isDead()) {
                continue;
            }
            if (skillUseInfo.getType() == SkillType.透支 || skillUseInfo.getType() == SkillType.过载) {
                Overdraw.apply(this, skillUseInfo, attacker);
            }
        }
        for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
            if (attacker.isDead()) {
                continue;
            }
            if (skillUseInfo.getType() == SkillType.未知) {
                // JUST A PLACEHOLDER
            } else if (skillUseInfo.getType() == SkillType.送还 || skillUseInfo.getType() == SkillType.突袭) {
                Return.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.LETITGO) {
                Return.apply(this, skillUseInfo.getSkill().getAttachedSkill1(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.沉默 ||
                    skillUseInfo.getType() == SkillType.觉醒沉默 && attacker.isAwaken(skillUseInfo, Race.KINGDOM) ||
                    skillUseInfo.getType() == SkillType.觉醒沉默A && attacker.isAwaken(skillUseInfo, Race.FOREST)) {
                Silence.apply(this, skillUseInfo, attacker, defender, false, false);
            } else if (skillUseInfo.getType() == SkillType.全体沉默) {
                Silence.apply(this, skillUseInfo, attacker, defender, true, false);
            } else if (skillUseInfo.getType() == SkillType.死亡印记 || skillUseInfo.getType() == SkillType.武形印记) {
                DeathMark.apply(this, skillUseInfo, attacker, defender);
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
            } else if (skillUseInfo.getType() == SkillType.雷神降临 ||
                    skillUseInfo.getType() == SkillType.觉醒雷神降临 && attacker.isAwaken(skillUseInfo, Race.HELL)) {
                LighteningMagic.apply(skillUseInfo, this, attacker, defender, -1, 75);
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
            } else if (skillUseInfo.getType() == SkillType.治疗) {
                Heal.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.甘霖) {
                Rainfall.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.月神的护佑 || skillUseInfo.getType() == SkillType.月之守护|| skillUseInfo.getType() == SkillType.月之守望) {
                LunaBless.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.月神的触碰) {
                LunaTouch.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.治疗之雾) {
                HealingMist.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.祈祷) {
                Pray.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.复活) {
                Revive.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.新生) {
                NewBorn.apply(this, skillUseInfo, attacker, 1);
            } else if (skillUseInfo.getType() == SkillType.夺魂) {
                SoulControl.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.鬼才) {
                SoulControl.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.背刺) {
                BackStab.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.群体削弱) {
                WeakenAll.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.回魂) {
                Resurrection.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.祈愿) {
                Supplication.apply(this, skillUseInfo, attacker,defender);
            } else if (skillUseInfo.getType() == SkillType.号角) {
                Horn.apply(skillUseInfo, this, attacker);
            } else if (skillUseInfo.getType() == SkillType.觉醒风之祈愿) {
                if(skillUseInfo.getOwner().getOwner().getHand().isFull())
                {
                    Horn.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker);
                }
                if(!skillUseInfo.getOwner().getOwner().getHand().isFull())
                {
                    Supplication.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker,defender);
                }
            } else if (skillUseInfo.getType() == SkillType.归魂) {
                RegressionSoul.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.太平要术) {
                RegressionSoul.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker);
                LunaBless.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.狙击) {
                Snipe.apply(skillUseInfo,skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.魔神之刃) {
                Snipe.apply(skillUseInfo,skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.武形秘箭 || skillUseInfo.getType() == SkillType.骤雨) {
                Snipe.apply(skillUseInfo,skillUseInfo.getSkill(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.二重狙击) {
                Snipe.apply(skillUseInfo,skillUseInfo.getSkill(), this, attacker, defender, 2);
            } else if (skillUseInfo.getType() == SkillType.神箭三重奏) {
                Snipe.apply(skillUseInfo,skillUseInfo.getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.寒莹触碰) {
                Snipe.apply(skillUseInfo,skillUseInfo.getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.武形神箭) {
                Snipe.apply(skillUseInfo,skillUseInfo.getSkill(), this, attacker, defender, -1);
                Snipe.apply(skillUseInfo,skillUseInfo.getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.穿云箭) {
                Snipe.apply(skillUseInfo,skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.绯弹) {
                Snipe.apply(skillUseInfo,skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.左轮射击) {
                Snipe.apply(skillUseInfo,skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.百步穿杨) {
                Snipe.apply(skillUseInfo,skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, -1);
                Snipe.apply(skillUseInfo,skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.枪林弹雨) {
                Snipe.apply(skillUseInfo,skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, -1);
                Snipe.apply(skillUseInfo,skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.迷魂) {
                Confusion.apply(skillUseInfo, this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.混乱领域) {
                Confusion.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.国色) {
                Confusion.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.魅惑之舞) {
                Confusion.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.无我境界) {
                Confusion.apply(skillUseInfo, this, attacker, defender, 3);
                Insane.apply(skillUseInfo, this, attacker, defender, 1, 100);
            } else if (skillUseInfo.getType() == SkillType.烈火焚神 || skillUseInfo.getType() == SkillType.天火) {
                BurningFlame.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.诅咒) {
                Curse.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.魔神之咒) {
                Curse.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.摧毁) {
                Destroy.apply(this, skillUseInfo.getSkill(), attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.炼金失败 || skillUseInfo.getType() == SkillType.凤凰涅盘) {
                AlchemyFailure.apply(this,skillUseInfo, skillUseInfo.getSkill(), attacker);
            } else if (skillUseInfo.getType() == SkillType.瘟疫) {
                Plague.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.凋零真言) {
                WitheringWord.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.血炼) {
                BloodPaint.apply(skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.鲜血盛宴 || skillUseInfo.getType() == SkillType.歃血魔咒 ||
                    skillUseInfo.getType() == SkillType.猎杀之夜) {
                BloodPaint.apply(skillUseInfo.getSkill(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.天谴 || skillUseInfo.getType() == SkillType.末世术 || skillUseInfo.getType() == SkillType.以逸待劳) {
                HeavenWrath.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.封印) {
                Seal.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.圣炎) {
                HolyFire.apply(skillUseInfo.getSkill(), this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.法力侵蚀 || skillUseInfo.getType() == SkillType.灵王的轰击 ||
                    skillUseInfo.getType() == SkillType.觉醒灵王的轰击 && attacker.isAwaken(skillUseInfo, Race.FOREST)) {
                ManaErode.apply(skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.破魔手) {
                ManaErode.apply(skillUseInfo.getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.法力风暴 || skillUseInfo.getType() == SkillType.魔法毁灭) {
                ManaErode.apply(skillUseInfo.getSkill(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.趁胜追击) {
                WinningPursuit.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.复仇) {
                Revenge.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.奋战) {
                BraveFight.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.樱魂) {
                BraveFight.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker);
            } else if (skillUseInfo.getType() == SkillType.振奋 || skillUseInfo.getType() == SkillType.会心一击) {
                Arouse.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.全体阻碍) {
                AllDelay.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.全体加速) {
                AllSpeedUp.apply(skillUseInfo, this, attacker);
            } else if (skillUseInfo.getType() == SkillType.阻碍) {
                OneDelay.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.加速) {
                SpeedUp.apply(skillUseInfo, this, attacker);
            } else if (skillUseInfo.getType() == SkillType.净化) {
                Purify.apply(skillUseInfo, this, attacker, -1);
            } else if (skillUseInfo.getType() == SkillType.虚弱) {
                Soften.apply(skillUseInfo, this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.圣光洗礼 || skillUseInfo.getType() == SkillType.森林沐浴 ||
                    skillUseInfo.getType() == SkillType.蛮荒威压 || skillUseInfo.getType() == SkillType.地狱同化) {
                RaceChange.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.战争怒吼 ) {
                Soften.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.镜像) {
                // 镜像召唤的单位可以被连锁攻击
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 1, attacker.getName());
            } else if (skillUseInfo.getType() == SkillType.虚梦) {
                //镜像召唤的单位可以被连锁攻击
                Summon.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, SummonType.Normal, 1, attacker.getName());
            } else if (skillUseInfo.getType() == SkillType.召唤王国战士) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "圣骑士", "魔剑士");
            } else if (skillUseInfo.getType() == SkillType.召唤骷髅战士) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "骷髅战士", "骷髅战士");
            } else if (skillUseInfo.getType() == SkillType.召唤噩梦护卫) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "时光女神", "金属巨龙");
            } else if (skillUseInfo.getType() == SkillType.召唤邪龙护卫) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "亡灵守护神", "光明之龙");
            } else if (skillUseInfo.getType() == SkillType.召唤复仇护卫) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "雷兽", "末日预言师");
            } else if (skillUseInfo.getType() == SkillType.召唤花仙子) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "花仙子", "花仙子");
            } else if (skillUseInfo.getType() == SkillType.召唤火焰乌鸦) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "火焰乌鸦", "火焰乌鸦");
            } else if (skillUseInfo.getType() == SkillType.召唤人马巡逻者) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "人马巡逻者", "人马巡逻者");
            } else if (skillUseInfo.getType() == SkillType.召唤女神侍者) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "女神侍者", "女神侍者");
            } else if (skillUseInfo.getType() == SkillType.召唤树人守护者) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "霜雪树人", "树人祭司");
            } else if (skillUseInfo.getType() == SkillType.召唤炎魔) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 1, "炎魔");
            } else if (skillUseInfo.getType() == SkillType.双子之身) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 1, "双子座·幻影");
            } else if (skillUseInfo.getType() == SkillType.召唤北海神兽) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "北海神兽", "北海神兽");
            } else if (skillUseInfo.getType() == SkillType.召唤梦境女神) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "梦境女神", "梦境女神");
            } else if (skillUseInfo.getType() == SkillType.酋长号令) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "战意斗神", "战意斗神");
            } else if (skillUseInfo.getType() == SkillType.召唤花族守卫) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "黄金金属巨龙", "处女座");
            } else if (skillUseInfo.getType() == SkillType.召唤花族侍卫) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "时光女神", "雷雕之魂");
            } else if (skillUseInfo.getType() == SkillType.七十二变) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "齐天美猴王", "齐天美猴王");
            } else if (skillUseInfo.getType() == SkillType.仙子召唤) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "蝶语仙子", "蝶语仙子");
            } else if (skillUseInfo.getType() == SkillType.召唤炮灰) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "炮灰", "炮灰");
            } else if (skillUseInfo.getType() == SkillType.英灵降临) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "圣剑持有者", "银河圣剑使", "精灵游骑兵", "爱神", "蝗虫公爵", "战场女武神", "龙角将军", "断罪之镰");
            } else if (skillUseInfo.getType() == SkillType.寒霜召唤) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "陨星魔法使", "怒雪咆哮", "圣诞老人", "寒霜冰灵使", "白羊座", "霜狼酋长", "雪月花", "梦魇猎手·霜");
            } else if (skillUseInfo.getType() == SkillType.无尽梦魇) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "梦魇猎手·岚", "梦魇猎手·霜", "梦魇猎手·胧");
            } else if (skillUseInfo.getType() == SkillType.百鬼夜行) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "大江三鬼·银", "大江三鬼·红", "大江三鬼·金");
            } else if (skillUseInfo.getType() == SkillType.武形降临) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "武形火焰尊者", "武形神射尊者", "武形破拳尊者", "武形剑圣", "武形斗圣");
            } else if (skillUseInfo.getType() == SkillType.爱之召唤) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 2,
                        "爱之使者", "森林丘比特", "占卜少女", "爱神");
            } else if (skillUseInfo.getType() == SkillType.召唤伍长) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 1, "巅峰伍长");
            } else if (skillUseInfo.getType() == SkillType.召唤兵长) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 1,  "巅峰兵长");
            } else if (skillUseInfo.getType() == SkillType.突击军势) {
                Summon.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, SummonType.Normal, 1, "巅峰伍长");
                Summon.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, SummonType.Normal, 1,  "巅峰兵长");
            } else if (skillUseInfo.getType() == SkillType.连营) {
                Summon.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, SummonType.Normal, 2, "炮灰", "炮灰");
                MagicMark.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.魔力法阵) {
                MagicMark.apply(this, skillUseInfo, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.魔力印记) {
                MagicMark.apply(this, skillUseInfo, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.东风) {
                MagicMark.apply(this, skillUseInfo, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.致盲) {
                Blind.apply(this, skillUseInfo, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.闪光弹) {
                Blind.apply(this, skillUseInfo, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.动能追加) {
                EnergyIncrement.apply(skillUseInfo, this, attacker);
            } else if (skillUseInfo.getType() == SkillType.祈福 ||
                    skillUseInfo.getType() == SkillType.真理导言) {
                Bless.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.闭月) {
                Bless.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.修罗地火攻 || skillUseInfo.getType() == SkillType.火攻) {
                SuraFire.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.火烧连营) {
                ContinuousFire.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.化学风暴) {
                ChemicalRage.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.精神狂乱) {
                Insane.apply(skillUseInfo, this, attacker, defender, 1, 100);
            } else if (skillUseInfo.getType() == SkillType.离间) {
                Insane.apply(skillUseInfo, this, attacker, defender, 3, 100);
            } else if (skillUseInfo.getType() == SkillType.精神污染) {
                Insane.apply(skillUseInfo, this, attacker, defender, 3, 0);
            } else if (skillUseInfo.getType() == SkillType.无尽华尔兹) {
                Insane.apply(skillUseInfo, this, attacker, defender, -1, 100);
            } else if (skillUseInfo.getType() == SkillType.圣洁魅惑) {
                Insane.apply(skillUseInfo, this, attacker, defender, 3, 50);
            } else if (skillUseInfo.getType() == SkillType.天怒) {
                FireMagic.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, -1);
                BurningFlame.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.业火) {
                HellFire.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.魔龙吐息) {
                HellFire.apply(skillUseInfo, this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.传送) {
                Transport.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.灵魂消散) {
                SoulCrash.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.全体裂伤) {
                Wound.applyToAll(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.凋零陷阱) {
                WitheringWord.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender);
                Trap.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.觉醒白虎守护) {
                if (attacker.getOwner().getHP() >= attacker.getOwner().getMaxHP() * 0.7) {
                    LunaBless.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker);
                } else {
                    Bless.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker);
                }
            } else if (skillUseInfo.getType() == SkillType.觉醒星之意志) {
                if (defender.getField().getAliveCards().size() >= 5) {
                    SoulCrash.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender);
                }
                if (defender.getField().getAliveCards().size() < 5) {
                    ManaErode.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 1);
                }
            } else if (skillUseInfo.getType() == SkillType.觉醒狼顾) {
                if (defender.getField().getAliveCards().size() >= 5) {
                    LighteningMagic.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender, -1, 75);
                }
                if (defender.getField().getAliveCards().size() < 5) {
                    ThunderStrike.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender, 3);
                }
            } else if (skillUseInfo.getType() == SkillType.觉醒原素之舞) {
                if (defender.getField().getAliveCards().size() >= 5) {
                    ThunderStrike.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender, -1);
                }
                if (defender.getField().getAliveCards().size() < 5) {
                    Snipe.apply(skillUseInfo,skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 3);
                }
            } else if (skillUseInfo.getType() == SkillType.原素共鸣) {
                ResonantElements.apply(this,skillUseInfo,attacker,"原素曜灵");
            } else if (skillUseInfo.getType() == SkillType.地裂) {
                GiantEarthquakesLandslides.apply(this, skillUseInfo.getSkill(), attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.觉醒天崩地裂) {
                GiantEarthquakesLandslides.apply(this, skillUseInfo.getAttachedUseInfo1().getSkill(), attacker, defender, 3);
                ManaErode.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.寒冰触碰) {
                IceTouch.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.雷霆一击) {
                ThunderStrike.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.雷公助我) {
                ThunderStrike.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.薜荔之怒) {
                ThunderStrike.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.雷霆之怒) {
                ThunderStrike.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.赤之魔枪) {
                RedGun.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.雷切) {
                ThunderStrike.apply(skillUseInfo, this, attacker, defender, 3);
            }
        }
        if((attacker.containsAllSkill(SkillType.连续魔法) || attacker.containsAllSkill(SkillType.黄天当立))&&!attacker.isDead()&&status==0)
        {
            for(SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills())
            {
                if (skillUseInfo.getType() == SkillType.连续魔法 || skillUseInfo.getType() == SkillType.黄天当立) {
                    ContinuousMagic.apply(this,skillUseInfo,attacker,defender);break;
                }
            }
        }
        if (!attacker.isDead() && !attacker.isSilent() && !attacker.justRevived()) {
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.飞岩);
                if (rune != null) {
                    Snipe.apply(rune.getSkillUseInfo(),rune.getSkill(), this, attacker, defender, 1);
                }
            }
        }
    }

    public void resolvePostAttackSkills(CardInfo attacker, Player defender) {

    }

    public void resolveCounterAttackSkills(CardInfo attacker, CardInfo defender, Skill attackSkill,
                                           OnAttackBlockingResult result, OnDamagedResult damagedResult) throws HeroDieSignal {
        if (isPhysicalAttackSkill(attackSkill) && damagedResult.actualDamage > 0) {
            for (SkillUseInfo skillUseInfo : defender.getUsableNormalSkills()) {
                if (skillUseInfo.getType() == SkillType.反击) {
                    CounterAttack.apply(skillUseInfo.getSkill(), this, attacker, defender, result.getDamage());
                } else if (skillUseInfo.getType() == SkillType.盾刺) {
                    Spike.apply(skillUseInfo.getSkill(), this, attacker, defender, attackSkill, result.getDamage());
                } else if (skillUseInfo.getType() == SkillType.荆棘术 || skillUseInfo.getType() == SkillType.刚烈) {
                    Spike.apply(skillUseInfo.getSkill(), this, attacker, defender, attackSkill, result.getDamage());
                } else if (skillUseInfo.getType() == SkillType.大地之盾 || skillUseInfo.getType() == SkillType.寒冰之盾) {
                    EarthShield.apply(skillUseInfo, this, attacker, defender);
                } else if (skillUseInfo.getType() == SkillType.物理反弹 || skillUseInfo.getType() == SkillType.武形破剑击 || skillUseInfo.getType() == SkillType.反击屏障) {
                    PhysicalReflection.apply(skillUseInfo.getSkill(), this, attacker, defender, damagedResult.actualDamage);
                } else if (skillUseInfo.getType() == SkillType.一闪) {
                    EarthShield.apply(skillUseInfo, this, attacker, defender);
                    PhysicalReflection.apply(skillUseInfo.getSkill(), this, attacker, defender, damagedResult.actualDamage);
                } else if (skillUseInfo.getType() == SkillType.魔神之甲) {
                    Spike.apply(skillUseInfo.getSkill(), this, attacker, defender, attackSkill, result.getDamage());
                } else if (skillUseInfo.getType() == SkillType.燃烧) {
                    Burning.apply(skillUseInfo, this, attacker, defender);
                } else if (skillUseInfo.getType() == SkillType.邪灵汲取) {
                    EnergyDrain.apply(skillUseInfo, this, attacker, defender, result, damagedResult);
                } else if (skillUseInfo.getType() == SkillType.恶灵汲取) {
                    LifeDrain.apply(skillUseInfo, this, attacker, defender, result, damagedResult);
                } else if (skillUseInfo.getType() == SkillType.不灭原核) {
                    EnergyDrain.apply(skillUseInfo, this, attacker, defender, result, damagedResult);
                } else if (skillUseInfo.getType() == SkillType.被插出五星) {
                    CounterSummon.apply(this, defender, skillUseInfo.getSkill(), 5);
                } else if (skillUseInfo.getType() == SkillType.反射装甲) {
                    ReflectionArmor.apply(skillUseInfo.getSkill(), this, attacker, defender, attackSkill, damagedResult.actualDamage);
                } else if (skillUseInfo.getType() == SkillType.LETITGO) {
                    ReflectionArmor.apply(skillUseInfo.getSkill().getAttachedSkill2(), this, attacker, defender, attackSkill, damagedResult.actualDamage);
                }
            }
            if (!defender.isSilent() && !defender.justRevived()) {
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.雷盾);
                    if (rune != null) {
                        Spike.apply(rune.getSkill(), this, attacker, defender, attackSkill, result.getDamage());
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.漩涡);
                    if (rune != null) {
                        CounterAttack.apply(rune.getSkill(), this, attacker, defender, result.getDamage());
                    }
                }
                if (!defender.isDead()) {
                    for (SkillUseInfo skillUseInfo : defender.getUsableNormalSkills()) {
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
            for (SkillUseInfo skillUseInfo : defender.getUsableNormalSkills()) {
                if (skillUseInfo.getType() == SkillType.逃跑 || skillUseInfo.getType() == SkillType.强链原核) {
                    Flee.apply(skillUseInfo.getSkill(), this, attacker, defender, damagedResult.actualDamage);
                }
            }
        }
    }

    public OnAttackBlockingResult resolveHealBlockingSkills(EntityInfo healer, CardInfo healee, Skill cardSkill) {
        OnAttackBlockingResult result = new OnAttackBlockingResult(true, cardSkill.getImpact());
        if (healee.getStatus().containsStatus(CardStatusType.裂伤) ||
                healee.getStatus().containsStatus(CardStatusType.不屈)) {
            stage.getUI().healBlocked(healer, healee, cardSkill, null);
            result.setAttackable(false);
        }
        return result;
    }

    /**
     * Resolve attack blocking skills.
     *
     * @param attacker    The one that uses the skill to be blocked.
     * @param defender    The one that uses the skill to block the attacker.
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
                return result;
            } else {
                for (SkillUseInfo blockSkillUseInfo : defender.getUsableNormalSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.闪避 || blockSkillUseInfo.getType() == SkillType.龙胆) {
                        result.setAttackable(!Dodge.apply(blockSkillUseInfo.getSkill(), this, cardAttacker, defender, result.getDamage()));
                        if (!result.isAttackable()) {
                            return result;
                        }
                    }
                }
                if (!defender.isSilent()) {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.轻灵);
                    if (rune != null && !defender.justRevived()) {
                        result.setAttackable(!Dodge.apply(rune.getSkill(), this, cardAttacker, defender, result.getDamage()));
                        if (!result.isAttackable()) {
                            return result;
                        }
                    }
                }
                List<CardStatusItem> blindItems = attacker.getStatus().getStatusOf(CardStatusType.致盲);
                if (!blindItems.isEmpty()) {
                    Skill skill = Blind.getDodgeSkill(blindItems);
                    result.setAttackable(!Dodge.apply(skill, this, cardAttacker, defender, result.getDamage()));
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                {
                    for (SkillUseInfo blockSkillUseInfo : defender.getUsableNormalSkills()) {
                        if (blockSkillUseInfo.getType() == SkillType.圣盾) {
                            if (resolveStopBlockSkill(blockSkillUseInfo.getSkill(), cardAttacker, defender)) {
                                result.setAttackable(true);
                            } else
                                result.setAttackable(HolyShield.apply(blockSkillUseInfo, this, cardAttacker, defender));
                            if (!result.isAttackable()) {
                                return result;
                            }
                        }
                    }
                    for (SkillUseInfo blockSkillUseInfo : defender.getUsableNormalSkills()) {
                        if (blockSkillUseInfo.getType() == SkillType.无刀取) {
                            if (resolveStopBlockSkill(blockSkillUseInfo.getSkill(), cardAttacker, defender)) {
                                result.setAttackable(true);
                            } else
                                result.setAttackable(HolyShield.apply(blockSkillUseInfo, this, cardAttacker, defender));
                            if (!result.isAttackable()) {
                                return result;
                            }
                        }
                    }
                }
                resolveShieldBlockingSkills(cardAttacker, defender, true, result);
                if (!result.isAttackable()) {
                    return result;
                }

                for (SkillUseInfo blockSkillUseInfo : defender.getUsableNormalSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.冰甲 ||
                            blockSkillUseInfo.getType() == SkillType.魔龙之血 ||
                            blockSkillUseInfo.getType() == SkillType.冰神附体 ||
                            blockSkillUseInfo.getType() == SkillType.神魔之甲 ||
                            blockSkillUseInfo.getType() == SkillType.寒冰之盾) {
                        result.setDamage(IceArmor.apply(blockSkillUseInfo.getSkill(), this, cardAttacker, defender,
                                result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                if (!defender.isSilent()) {
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
            }

            for (SkillUseInfo blockSkillUseInfo : defender.getUsableNormalSkills()) {
                if (blockSkillUseInfo.getType() == SkillType.骑士守护 || blockSkillUseInfo.getType() == SkillType.骑士荣耀) {
                    result.setDamage(KnightGuardian.apply(this, blockSkillUseInfo.getSkill(), attacker, defender,
                            attackSkill, result.getDamage()));
                }
            }

            for (SkillUseInfo skillUseInfo : defender.getAllUsableSkills()) {
                if (skillUseInfo.getType() == SkillType.生命链接) {
                    List<CardInfo> victims = this.getAdjacentCards(defender.getOwner().getField(), defender.getPosition());
                    if (victims.size() <= 1) {
                        break;
                    }
                    this.getStage().getUI().useSkill(defender, victims, skillUseInfo.getSkill(), true);
                    result.setDamage(result.getDamage() / victims.size());
                    for (CardInfo victim : victims) {
                        this.getStage().getUI().attackCard(defender, victim, skillUseInfo.getSkill(), result.getDamage());
                        OnDamagedResult lifeChainResult = this.applyDamage(attacker, victim, skillUseInfo.getSkill(), result.getDamage());
                        if (lifeChainResult.cardDead) {
                            this.resolveDeathSkills(attacker, victim, attackSkill, lifeChainResult);
                        }
                        if (attacker instanceof CardInfo) {
                            this.resolvePostAttackSkills((CardInfo) attacker, victim, victim.getOwner(), attackSkill, lifeChainResult.actualDamage);
                        }
                    }
                    result.setAttackable(false);
                    return result;
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
                if (CounterMagic.apply(this, attackSkill, attacker, defender)) {
                    result.setAttackable(false);
                    return result;
                }

                if (NoEffect.isSkillBlocked(this, attackSkill, attacker, defender)) {
                    result.setAttackable(false);
                    return result;
                }
                if (NoWhenDemon.isSkillBlocked(this, attackSkill, attacker, defender)) {
                    result.setAttackable(false);
                    return result;
                }

                for (SkillUseInfo blockSkillUseInfo : defender.getUsableNormalSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.免疫 || blockSkillUseInfo.getType() == SkillType.结界立场) {
                        if (Immue.isSkillBlocked(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                    // 神威既包含脱困又包含不动，还有技能既包含不动又抗沉默的，所以需要将if分开
                    if (blockSkillUseInfo.getType() == SkillType.脱困 ||
                            blockSkillUseInfo.getType() == SkillType.神威 ||
                            blockSkillUseInfo.getType() == SkillType.村正 ||
                            blockSkillUseInfo.getType() == SkillType.月之守望 ||
                            blockSkillUseInfo.getType() == SkillType.冰神附体 ||
                            blockSkillUseInfo.getType() == SkillType.以逸待劳 ||
                            blockSkillUseInfo.getType() == SkillType.不灭原核 ||
                            blockSkillUseInfo.getType() == SkillType.黄天当立 ||
                            blockSkillUseInfo.getType() == SkillType.神之守护) {
                        if (Escape.isSkillEscaped(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                    if (blockSkillUseInfo.getType().containsTag(SkillTag.不动)) {
                        if (Immobility.isSkillBlocked(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                    if (blockSkillUseInfo.getType().containsTag(SkillTag.抗沉默)) {
                        if (attackSkill.getType().containsTag(SkillTag.沉默)) {
                            this.getStage().getUI().useSkill(defender, blockSkillUseInfo.getSkill(), true);
                            this.getStage().getUI().blockSkill(attacker, defender, blockSkillUseInfo.getSkill(), attackSkill);
                            result.setAttackable(false);
                            return result;
                        }
                    }
                }
                if (!defender.isSilent()) {
                    {
                        RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.鬼步);
                        if (rune != null && !defender.justRevived()) {
                            if (Escape.isSkillEscaped(this, rune.getSkill(), attackSkill, attacker, defender)) {
                                result.setAttackable(false);
                                return result;
                            }
                        }
                    }
                    {
                        RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.磐石);
                        if (rune != null && !defender.justRevived()) {
                            if (Immobility.isSkillBlocked(this, rune.getSkill(), attackSkill, attacker, defender)) {
                                result.setAttackable(false);
                                return result;
                            }
                        }
                    }
                }
                if (isMagicalSkill(attackSkill) && damage > 0) {
                    // 治疗法术不受魔法印记影响
                    List<CardStatusItem> magicMarkStatusItems = defender.getStatus().getStatusOf(CardStatusType.魔印);
                    int maxDamage = 0;
                    for (CardStatusItem item : magicMarkStatusItems) {
                        Skill magicMarkSkill = item.getCause().getSkill();
                        this.getStage().getUI().useSkill(defender, magicMarkSkill, true);
                        int extraDamage = damage * magicMarkSkill.getImpact() / 100;
                        if(extraDamage >maxDamage)
                        {
                            maxDamage = extraDamage;
                        }
                    }
                    result.setDamage(damage+maxDamage);
                }
                for (SkillUseInfo blockSkillUseInfo : defender.getUsableNormalSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.魔甲 ||
                            blockSkillUseInfo.getType() == SkillType.神魔之甲) {
                        result.setDamage(MagicShield.apply(this, blockSkillUseInfo.getSkill(), attacker, defender,
                                attackSkill, result.getDamage()));
                    } else if (blockSkillUseInfo.getType() == SkillType.骑士守护 || blockSkillUseInfo.getType() == SkillType.骑士荣耀) {
                        result.setDamage(KnightGuardian.apply(this, blockSkillUseInfo.getSkill(), attacker, defender,
                                attackSkill, result.getDamage()));
                    }  if (blockSkillUseInfo.getType() == SkillType.魔法装甲) {
                        result.setDamage(MagicArmor.apply(this, blockSkillUseInfo.getSkill(), attacker, defender,
                                attackSkill, result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                if (!defender.isSilent()) {
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

    public void resolveShieldBlockingSkills(CardInfo cardAttacker, CardInfo defender, boolean includeBlocking,
                                            OnAttackBlockingResult result) throws HeroDieSignal {
        if (!defender.isSilent() && includeBlocking) {
            RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.止水);
            if (rune != null && !defender.justRevived()) {
                result.setDamage(WaterArmor.apply(rune.getSkill(), this, cardAttacker, defender, result.getDamage()));
            }
        }
        for (SkillUseInfo blockSkillUseInfo : defender.getAllUsableSkillsInvalidSilence()) {
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
            if (includeBlocking) {
                if (blockSkillUseInfo.getType() == SkillType.格挡 ||
                        blockSkillUseInfo.getType() == SkillType.魔龙之血 ||
                        blockSkillUseInfo.getType() == SkillType.钢铁之肤) {
                    result.setDamage(Block.apply(blockSkillUseInfo.getSkill(), this, cardAttacker, defender,
                            defender, result.getDamage()));
                }
                if (blockSkillUseInfo.getType() == SkillType.金属装甲 || blockSkillUseInfo.getType() == SkillType.酒池肉林) {
                    result.setDamage(PhysicalArmor.apply(blockSkillUseInfo.getSkill(), this, cardAttacker, defender,
                            result.getDamage()));
                }
                if (blockSkillUseInfo.getType() == SkillType.水流护甲 || blockSkillUseInfo.getType() == SkillType.真夏通雨) {
                    result.setDamage(WaterArmor.apply(blockSkillUseInfo.getSkill(), this, cardAttacker, defender, result.getDamage()));
                }
            }
        }
    }

    /**
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

        Player opponent = this.getStage().getOpponent(deadCard.getOwner());
        for (SkillUseInfo deadCardSkillUseInfo : deadCard.getUsableDeathSkills()) {
            if (deadCardSkillUseInfo.getType() == SkillType.烈焰风暴) {
                FireMagic.apply(deadCardSkillUseInfo.getSkill(), this, deadCard, opponent, -1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.雷暴) {
                LighteningMagic.apply(deadCardSkillUseInfo, this, deadCard, opponent, -1, 35);
            } else if (deadCardSkillUseInfo.getType() == SkillType.暴风雪) {
                IceMagic.apply(deadCardSkillUseInfo, this, deadCard, opponent, -1, 30, 0);
            } else if (deadCardSkillUseInfo.getType() == SkillType.毒云) {
                PoisonMagic.apply(deadCardSkillUseInfo, this, deadCard, opponent, -1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.瘟疫) {
                Plague.apply(deadCardSkillUseInfo, this, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.凋零真言) {
                Plague.apply(deadCardSkillUseInfo, this, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.治疗) {
                Heal.apply(deadCardSkillUseInfo.getSkill(), this, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.甘霖) {
                Rainfall.apply(deadCardSkillUseInfo.getSkill(), this, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.月神的护佑) {
                LunaBless.apply(deadCardSkillUseInfo.getSkill(), this, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.祈祷) {
                Pray.apply(deadCardSkillUseInfo.getSkill(), this, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.诅咒) {
                Curse.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.群体削弱) {
                WeakenAll.apply(this, deadCardSkillUseInfo, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.烈火焚神) {
                BurningFlame.apply(deadCardSkillUseInfo, this, deadCard, opponent, -1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.陷阱) {
                Trap.apply(deadCardSkillUseInfo, this, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.复活) {
                Revive.apply(this, deadCardSkillUseInfo, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.摧毁) {
                Destroy.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, opponent, 1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.圣炎) {
                HolyFire.apply(deadCardSkillUseInfo.getSkill(), this, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.传送) {
                Transport.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.回魂) {
                Resurrection.apply(this, deadCardSkillUseInfo, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.归魂) {
                RegressionSoul.apply(this, deadCardSkillUseInfo, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.召唤炎魔) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, "炎魔");
            } else if (deadCardSkillUseInfo.getType() == SkillType.全体阻碍) {
                AllDelay.apply(deadCardSkillUseInfo, this, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.全体加速) {
                AllSpeedUp.apply(deadCardSkillUseInfo, this, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.战争怒吼) {
                Soften.apply(deadCardSkillUseInfo, this, deadCard, opponent, -1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.时间溯行) {
                TimeTravel.apply(deadCardSkillUseInfo, this, deadCard.getOwner(), opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.魔法毁灭) {
                ManaErode.apply(deadCardSkillUseInfo.getSkill(), this, deadCard.getOwner(), opponent, -1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.逆鳞) {
                Snipe.apply(deadCardSkillUseInfo,deadCardSkillUseInfo.getSkill().getAttachedSkill1(), this, deadCard, opponent, -1);
                Snipe.apply(deadCardSkillUseInfo,deadCardSkillUseInfo.getSkill().getAttachedSkill2(), this, deadCard, opponent, 3);
            } else if (deadCardSkillUseInfo.getType() == SkillType.万兽奔腾) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Random, 2,
                        "凤凰", "浮云青鸟", "九头妖蛇", "雷兽", "羽翼化蛇", "神谕火狐",
                        "齐天美猴王", "羽蛇神", "月蚀兽", "逐月恶狼", "逐日凶狼", "月之神兽", "山地兽");
            } else if (deadCardSkillUseInfo.getType() == SkillType.武形降临) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Random, 1,
                        "武形火焰尊者", "武形神射尊者", "武形破拳尊者", "武形剑圣", "武形斗圣");
            } else if (deadCardSkillUseInfo.getType() == SkillType.镜像) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, deadCard.getName());
            }
        }
        if (!result.soulCrushed) {
            // 被扼杀的卡牌无法转生
            boolean reincarnated = false;
            for (SkillUseInfo deadCardSkillUseInfo : deadCard.getAllUsableSkills()) {
                if (deadCardSkillUseInfo.getType() == SkillType.转生 ||
                        deadCardSkillUseInfo.getType() == SkillType.武形秘仪 ||
                        deadCardSkillUseInfo.getType() == SkillType.花族秘术 ||
                        deadCardSkillUseInfo.getType() == SkillType.洪荒之术 ||
                        deadCardSkillUseInfo.getType() == SkillType.六道轮回 ||
                        deadCardSkillUseInfo.getType() == SkillType.武形秘术 ||
                        deadCardSkillUseInfo.getType() == SkillType.武形秘法 ||
                        deadCardSkillUseInfo.getType() == SkillType.涅盘 ||
                        deadCardSkillUseInfo.getType() == SkillType.凤凰涅盘 ||
                        deadCardSkillUseInfo.getType() == SkillType.武侯) {
                    if (Reincarnation.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, result.unbending)) {
                        reincarnated = true;
                        break;
                    }
                }
            }
            if (!reincarnated && !deadCard.isSilent()) {
                RuneInfo rune = deadCard.getOwner().getActiveRuneOf(RuneData.秽土);
                if (rune != null && !deadCard.justRevived()) {
                    Reincarnation.apply(this, rune.getSkill(), deadCard, result.unbending);
                }
            }
        }
        for (SkillUseInfo deadCardSkillUseInfo : deadCard.getAllUsableSkills()) {
            // IMPORTANT: Unbending card cannot trigger 自爆
            if (deadCardSkillUseInfo.getType() == SkillType.自爆 && !result.unbending) {
                Explode.apply(this, deadCardSkillUseInfo.getSkill(), killerCard, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.燕返 || deadCardSkillUseInfo.getType() == SkillType.上层精灵的挽歌) {
                TsubameGaeshi.apply(deadCardSkillUseInfo,deadCardSkillUseInfo.getSkill(), this, opponent, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.格式化) {
                SoulCrash.apply(deadCardSkillUseInfo, this, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.九转秘术 ) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, "九命猫神·幻影");
            } else if (deadCardSkillUseInfo.getType() == SkillType.九转禁术 ) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1,deadCard.getName());
            } else if (deadCardSkillUseInfo.getType() == SkillType.我还会回来的) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, "大毒汁之王-5");
            } else if (deadCardSkillUseInfo.getType() == SkillType.蛮荒我还会回来的) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, "蛮荒大毒汁之王-5");
            } else if (deadCardSkillUseInfo.getType() == SkillType.召唤玫瑰剑士) {
                Summon.apply(this, deadCardSkillUseInfo.getAttachedUseInfo2(), deadCard, SummonType.Normal, 1,
                        "玫瑰甜心");
            } else if (deadCardSkillUseInfo.getType() == SkillType.白帝托孤) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Random, 1,
                        "三国英魂卧龙","三国英魂汉升","三国英魂子龙","三国英魂孟起");
            } else if (deadCardSkillUseInfo.getType() == SkillType.铁壁) {
                ImpregnableDefenseHeroBuff.remove(this, deadCardSkillUseInfo, deadCard);
            }
        }
        if (!deadCard.isSilent()) {
            RuneInfo rune = deadCard.getOwner().getActiveRuneOf(RuneData.爆裂);
            // IMPORTANT: Unbending card cannot trigger 爆裂
            if (rune != null && !deadCard.justRevived() && !result.unbending) {
                Explode.apply(this, rune.getSkill(), killerCard, deadCard);
            }
            rune = deadCard.getOwner().getActiveRuneOf(RuneData.背水);
            // IMPORTANT: Unbending card cannot trigger 背水
            if (rune != null && !deadCard.justRevived() ) {
                TsubameGaeshi.apply(null,rune.getSkill(), this, opponent, deadCard);
            }
        }
        if (deadCard.getStatus().containsStatus(CardStatusType.死印)) {
            DeathMark.explode(this, deadCard, result);
        }
        // HACKHACK: Cannot find better way to handle 不屈/
        //改变不屈的去掉buff位置，为GiveSideSkill做的处理
        if (!deadCard.getStatus().containsStatus(CardStatusType.不屈)) {
            for (SkillUseInfo deadCardSkillUseInfo : deadCard.getAllUsableSkills()) {
                if (deadCardSkillUseInfo.getSkill().getGiveSkill() == 1) {
                    deadCard.removeSkill((CardSkill) deadCardSkillUseInfo.getSkill());
                }
            }
            resolveLeaveSkills(deadCard);
        }
    }

    public void resolvePostAttackSkills(CardInfo attacker, CardInfo defender, Player defenderHero, Skill attackSkill,
                                        int normalAttackDamage) throws HeroDieSignal {
        for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
            if (!attacker.isDead()) {
                if (skillUseInfo.getType() == SkillType.吸血 ||
                        skillUseInfo.getType() == SkillType.蛇吻 ||
                        skillUseInfo.getType() == SkillType.鬼彻 ||
                        skillUseInfo.getType() == SkillType.武圣 ||
                        skillUseInfo.getType() == SkillType.狂暴 ||
                        skillUseInfo.getType() == SkillType.村正) {
                    BloodDrain.apply(skillUseInfo.getSkill(), this, attacker, defender, normalAttackDamage);
                }
                if (skillUseInfo.getType() == SkillType.樱魂) {
                    BloodDrain.apply(skillUseInfo.getSkill().getAttachedSkill1(), this, attacker, defender, normalAttackDamage);
                }
            }
        }
        if (!attacker.isDead() && !attacker.isSilent()) {
            RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.赤谷);
            if (rune != null && !attacker.justRevived()) {
                BloodDrain.apply(rune.getSkill(), this, attacker, defender, normalAttackDamage);
            }
        }
    }

    public void resolveExtraAttackSkills(CardInfo attacker, CardInfo defender, Player defenderHero, Skill attackSkill, OnDamagedResult damageResult) throws HeroDieSignal {
        int normalAttackDamage = damageResult.actualDamage;
        for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
            if (!attacker.isDead()) {
                if (skillUseInfo.getType() == SkillType.穿刺) {
                    Penetration.apply(skillUseInfo.getSkill(), this, attacker, defenderHero, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.精准打击 || skillUseInfo.getType() == SkillType.精准射击) {
                    Penetration.apply(skillUseInfo.getSkill(), this, attacker, defenderHero, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.削弱) {
                    Weaken.apply(this, skillUseInfo, attacker, defender, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.裂伤) {
                    Wound.apply(this, skillUseInfo, attackSkill, attacker, defender, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.嗜血 || skillUseInfo.getType() == SkillType.亮银) {
                    BloodThirsty.apply(this, skillUseInfo, attacker, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.连锁攻击) {
                    ChainAttack.apply(this, skillUseInfo, attacker, defender, attackSkill, damageResult.originalDamage);
                } else if (skillUseInfo.getType() == SkillType.疾病) {
                    Disease.apply(skillUseInfo, this, attacker, defender, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.贪吃) {
                    BloodThirsty.apply(this, skillUseInfo, attacker, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.毒刃 || skillUseInfo.getType() == SkillType.毒杀) {
                    PosionBlade.apply(this, skillUseInfo, attacker, defender, normalAttackDamage);
                }
            }
            if (skillUseInfo.getType() == SkillType.武形天火击) {
                if (!defender.isDead() && defender.getStatus().containsStatus(CardStatusType.燃烧)) {
                    Destroy.apply(this, skillUseInfo.getSkill(), attacker, defender);
                }
            }
        }
        if (!attacker.isDead() && !attacker.isSilent()) {
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.洞察);
                if (rune != null && !attacker.justRevived()) {
                    BloodThirsty.apply(this, rune.getSkillUseInfo(), attacker, normalAttackDamage);
                }
            }
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.狂战);
                if (rune != null && !attacker.justRevived()) {
                    Penetration.apply(rune.getSkillUseInfo().getSkill(), this, attacker, defenderHero, normalAttackDamage);
                }
            }
        }
    }

    public void resolvePreAttackHeroSkills(CardInfo attacker, Player defenderPlayer) throws HeroDieSignal {
        for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
            if (skillUseInfo.getType() == SkillType.英雄杀手) {
                HeroKiller.apply(this, skillUseInfo, attacker, defenderPlayer);
            } else if (skillUseInfo.getType() == SkillType.凯撒之击) {
                CaeserAttack.apply(this, skillUseInfo, attacker, defenderPlayer);
            } else if (skillUseInfo.getType() == SkillType.厨具召唤) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defenderPlayer, 1, 500);
            } else if (skillUseInfo.getType() == SkillType.神兵召唤 ||
                    skillUseInfo.getType() == SkillType.神兵降临 ||
                    skillUseInfo.getType() == SkillType.觉醒神兵召唤 && attacker.isAwaken(skillUseInfo, Race.SAVAGE) ||
                    skillUseInfo.getType() == SkillType.阿拉希血统) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defenderPlayer, 500, 1700);
            } else if (skillUseInfo.getType() == SkillType.圣器召唤 || skillUseInfo.getType() == SkillType.突袭) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defenderPlayer, 300, 1300);
            }
        }
    }

    /**
     * @param attacker
     * @param defender
     * @throws HeroDieSignal
     */
    public void resolvePreAttackCardSkills(CardInfo attacker, CardInfo defender) throws HeroDieSignal {
        for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
            if (skillUseInfo.getType() == SkillType.圣光) {
                RacialAttackSkill.apply(this, skillUseInfo, attacker, defender, Race.HELL);
            } else if (skillUseInfo.getType() == SkillType.要害) {
                RacialAttackSkill.apply(this, skillUseInfo, attacker, defender, Race.SAVAGE);
            } else if (skillUseInfo.getType() == SkillType.暗杀) {
                RacialAttackSkill.apply(this, skillUseInfo, attacker, defender, Race.KINGDOM);
            } else if (skillUseInfo.getType() == SkillType.污染) {
                RacialAttackSkill.apply(this, skillUseInfo, attacker, defender, Race.FOREST);
            } else if (skillUseInfo.getType() == SkillType.暴击 || skillUseInfo.getType() == SkillType.鹰眼) {
                CriticalAttack.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.神兵召唤 ||
                    skillUseInfo.getType() == SkillType.神兵降临 ||
                    skillUseInfo.getType() == SkillType.觉醒神兵召唤 && attacker.isAwaken(skillUseInfo, Race.SAVAGE) ||
                    skillUseInfo.getType() == SkillType.阿拉希血统) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defender, 500, 1700);
            } else if (skillUseInfo.getType() == SkillType.厨具召唤) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defender, 1, 500);
            } else if (skillUseInfo.getType() == SkillType.圣器召唤) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defender, 300, 1300);
            } else if (skillUseInfo.getType() == SkillType.穷追猛打) {
                Pursuit.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.战意 || skillUseInfo.getType() == SkillType.鬼王之怒) {
                Wrath.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.凯撒之击) {
                CaeserAttack.apply(this, skillUseInfo, attacker, defender);
            }
        }
        if (!attacker.isSilent()) {
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
            {
                if (!attacker.justRevived()) {
                    List<CardInfo> cards = attacker.getOwner().getField().getAliveCards();
                    for (CardInfo card : cards) {
                        for (SkillUseInfo skillUseInfo : card.getUsableNormalSkills()) {
                            if (skillUseInfo.getType() == SkillType.群体追击) {
                                Pursuit.apply(this, skillUseInfo, attacker, defender);
                            }
                        }
                    }
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
            } else if (type == SkillType.暴击 || type == SkillType.鹰眼) {
                CriticalAttack.remove(this, effect.getCause(), card);
            } else if (type == SkillType.穷追猛打) {
                Pursuit.remove(this, effect.getCause(), card);
            } else if (type == SkillType.背刺) {
                BackStab.remove(this, effect.getCause(), card);
            } else if (type == SkillType.战意 || type == SkillType.鬼王之怒) {
                Wrath.remove(this, effect.getCause(), card);
            } else if (type == SkillType.趁胜追击) {
                WinningPursuit.remove(this, effect.getCause(), card);
            } else if (type == SkillType.复仇) {
                Revenge.remove(this, effect.getCause(), card);
            } else if (type == SkillType.奋战) {
                BraveFight.remove(this, effect.getCause(), card);
            } else if (type == SkillType.樱魂) {
                BraveFight.remove(this, effect.getCause().getAttachedUseInfo2(), card);
            } else if (type == SkillType.振奋 || type == SkillType.会心一击) {
                Arouse.remove(this, effect.getCause(), card);
            } else if (type == SkillType.英雄杀手) {
                HeroKiller.remove(this, effect.getCause(), card);
            } else if (type == SkillType.凯撒之击) {
                CaeserAttack.remove(this, effect.getCause(), card);
            } else if (type == SkillType.神兵召唤 || type == SkillType.神兵降临 || type == SkillType.厨具召唤 || type == SkillType.觉醒神兵召唤 ||
                    type == SkillType.圣器召唤 || type == SkillType.阿拉希血统) {
                WeaponSummon.remove(this, effect.getCause(), card);
            }
        }
    }

    public OnDamagedResult applyDamage(EntityInfo attacker, CardInfo defender, Skill skill, int damage) throws HeroDieSignal {
        OnDamagedResult result = new OnDamagedResult();
        List<CardStatusItem> unbendingStatusItems = defender.getStatus().getStatusOf(CardStatusType.不屈);
        if (!unbendingStatusItems.isEmpty()) {
            if (skill != null && (skill.getType() == SkillType.吸血 || skill.getType() == SkillType.蛇吻 || skill.getType() == SkillType.鬼彻
                    || skill.getType() == SkillType.武圣 || skill.getType() == SkillType.村正 || skill.getType() == SkillType.樱魂 || skill.getType() == SkillType.狂暴)) {
                // 不屈状态下可以吸血
            } else {
                this.getStage().getUI().unbend(defender, unbendingStatusItems.get(0));
                damage = 0;
            }
        }
        if (defender.containsUsableSkill(SkillType.魔族之血)) {
            for (SkillUseInfo skillUseInfo : defender.getUsableNormalSkills()) {
                if (skillUseInfo.getType() == SkillType.魔族之血) {
                    damage = (damage - skillUseInfo.getSkill().getImpact()) > 0 ? (damage - skillUseInfo.getSkill().getImpact()) : 0;
                    break;
                }
            }
        }
        int actualDamage = defender.applyDamage(damage);
        result.originalDamage += damage;
        result.actualDamage += actualDamage;


        if (defender.getHP() <= 0) {
            result.cardDead = true;
            for (SkillUseInfo skillUseInfo : defender.getUsableNormalSkills()) {
                if (skillUseInfo.getType() == SkillType.不屈 ||
                        skillUseInfo.getType() == SkillType.鬼王之怒 ||
                        skillUseInfo.getType() == SkillType.空城 ||
                        skillUseInfo.getType() == SkillType.武形秘法 ||
                        skillUseInfo.getType() == SkillType.坚毅) {
                    // BUGBUG: The original game does not set cardDead to false
                    // result.cardDead = false
                    result.unbending = Unbending.apply(skillUseInfo, this, defender);
                }
            }
            if (!result.unbending) {
                DeadType deadType = cardDead(attacker, skill, defender);
                if (deadType == DeadType.SoulCrushed) {
                    result.soulCrushed = true;
                }
            } else {
                result.cardDead = false;
            }
        }

        if (result.cardDead) {

        }
        return result;
    }

    public DeadType cardDead(EntityInfo attacker, Skill killingSkill, CardInfo deadCard) {
        if (deadCard.hasDeadOnce()) {
            // 由于技能多重触发可能造成cardDead被多次调用
            return DeadType.AlreadyDead;
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
                if (card.getStatus().containsStatus(CardStatusType.召唤)) {
                    // 被召唤的卡牌不进入墓地，而是直接死亡
                    return DeadType.PhantomDiminished;
                }
                card.restoreOwner();
                if (attacker instanceof CardInfo && this.isPhysicalAttackSkill(killingSkill)) {
                    CardInfo attackCard = (CardInfo) attacker;
                    if (deadCard.getRace() != Race.BOSS && deadCard.getRace() != Race.DEMON) {
                        for (SkillUseInfo skillUseInfo : attackCard.getUsableNormalSkills()) {
                            if (skillUseInfo.getType() == SkillType.扼杀 || skillUseInfo.getType() == SkillType.无双 || skillUseInfo.getType() == SkillType.双斩) {
                                this.getStage().getUI().useSkill(attacker, skillUseInfo.getSkill(), true);
                                owner.getOutField().addCard(card);
                                return DeadType.SoulCrushed;
                            }
                        }
                    }
                }
                card.getOwner().getGrave().addCard(card);
                break;
            }
        }
        return DeadType.Normal;
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
            if (damage >= 0) {
                int remainingDamage = this.resolveAttackHeroBlockingSkills(attacker, defenderPlayer, cardSkill, damage);
                if (remainingDamage > 0) {
                    remainingDamage = remainingDamage * defenderPlayer.getCoefficient() / 100;
                    if (remainingDamage > defenderPlayer.getHP()) {
                        remainingDamage = defenderPlayer.getHP();
                    }
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
                CardInfo attackerCard = (CardInfo) attacker;
                this.removeStatus(attackerCard, CardStatusType.麻痹);
            }
        }
    }

    private int resolveAttackHeroBlockingSkills(EntityInfo attacker, Player defenderPlayer, Skill cardSkill,
                                                int damage) throws HeroDieSignal {
        int remainingDamage = damage;
        for (CardInfo defender : defenderPlayer.getField().getAliveCards()) {
            if (defender == null || defender.isDead()) {
                continue;
            }
            for (SkillUseInfo defenderSkill : defender.getUsableNormalSkills()) {
                if (defenderSkill.getType().containsTag(SkillTag.守护)) {
                    remainingDamage = Guard.apply(defenderSkill.getSkill(), cardSkill, this, attacker, defender, remainingDamage);
                    if (remainingDamage == 0) {
                        return 0;
                    }
                }
            }
        }
        return remainingDamage;
    }

    public void resolveCardRoundEndingSkills(CardInfo card) throws HeroDieSignal {
        if (card == null) {
            return;
        }
        CardStatus status = card.getStatus();
        if (status.containsStatus(CardStatusType.锁定)) {
            return;
        }
        for (SkillUseInfo cardSkillUseInfo : card.getUsableNormalSkills()) {
            if (cardSkillUseInfo.getType() == SkillType.回春 ||
                    cardSkillUseInfo.getType() == SkillType.月恩术 ||
                    cardSkillUseInfo.getType() == SkillType.圣母回声 ||
                    cardSkillUseInfo.getType() == SkillType.圣母咏叹调) {
                Rejuvenate.apply(cardSkillUseInfo.getSkill(), this, card);
            } else if (cardSkillUseInfo.getType() == SkillType.闭月) {
                Rejuvenate.apply(cardSkillUseInfo.getAttachedUseInfo2().getSkill(), this, card);
            } else if (cardSkillUseInfo.getType() == SkillType.圣母吟咏) {
                PercentGetHp.apply(cardSkillUseInfo.getSkill(), this, card);
            }
        }
        if (!card.isSilent()) {
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
            if(!(attacker.containsUsableSkill(SkillType.炼金失败)|| attacker.containsUsableSkill(SkillType.凤凰涅盘))){
                this.removeStatus(attacker, CardStatusType.不屈);
            }
            return null;
        }
        if (skill == null) {
            for (SkillUseInfo cardSkillUseInfo : attacker.getAllUsableSkills()) {
                if (cardSkillUseInfo.getType() == SkillType.斩杀 || cardSkillUseInfo.getType() == SkillType.送葬之刃
                        || cardSkillUseInfo.getType() == SkillType.无双 || cardSkillUseInfo.getType() == SkillType.双斩) {
                    SuddenKill.apply(this, cardSkillUseInfo, attacker, defender, blockingResult);
                }
            }
        }
        this.stage.getUI().attackCard(attacker, defender, skill, blockingResult.getDamage());
        OnDamagedResult damagedResult = stage.getResolver().applyDamage(attacker, defender, skill, blockingResult.getDamage());
        if(!(attacker.containsUsableSkill(SkillType.炼金失败)|| attacker.containsUsableSkill(SkillType.凤凰涅盘))){
            this.removeStatus(attacker, CardStatusType.不屈);
        }

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

    public void resolveFirstClassSummoningSkills(CardInfo card, Player player, Player enemy, boolean isMinion) throws HeroDieSignal {
        // 召唤物不享受加成
        if (!isMinion) {
            for (SkillUseInfo skillUseInfo : card.getOriginalOwner().getCardBuffs()) {
                Skill skill = skillUseInfo.getSkill();
                if (skill instanceof BuffSkill) {
                    if (!((BuffSkill) skill).canApplyTo(card)) {
                        continue;
                    }
                }
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

        if (!card.isDead()) {
            card.applySurvivalStatus();
        }

        // Racial buff
        for (CardInfo fieldCard : player.getField().getAliveCards()) {
            // 主动种族BUFF
            for (SkillUseInfo skillUseInfo : fieldCard.getUsableNormalSkills()) {
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
                } else if (skillUseInfo.getType() == SkillType.根源之力) {
                    TogetherBuff.apply(this, skillUseInfo, fieldCard, null);
                } else if (skillUseInfo.getType() == SkillType.生命符文) {
                    CoefficientBuff.apply(this, skillUseInfo, fieldCard,card, null, SkillEffectType.MAXHP_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.战歌之鼓) {
                    CoefficientBuff.apply(this, skillUseInfo, fieldCard,card, null, SkillEffectType.ATTACK_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.神圣守护) {
                    HolyGuard.apply(this, skillUseInfo, fieldCard);
                } else if (skillUseInfo.getType() == SkillType.坚壁) {
                    CoefficientThreeBuff.apply(this, skillUseInfo, fieldCard ,card,null, SkillEffectType.MAXHP_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.剑域) {
                    CoefficientThreeBuff.apply(this, skillUseInfo, fieldCard ,card,null, SkillEffectType.ATTACK_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.西凉铁骑) {
                    GiveSideSkill.apply(this, skillUseInfo, fieldCard, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.袈裟斩) {
                    GiveSideSkill.apply(this, skillUseInfo, fieldCard, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.致命打击) {
                    GiveSideSkill.apply(this, skillUseInfo, fieldCard, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.爱心料理) {
                    GiveSideSkill.apply(this, skillUseInfo, fieldCard, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.仁德之君) {
                    GiveSideSkill.apply(this, skillUseInfo, fieldCard, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.剑舞) {
                    GiveSideSkill.apply(this, skillUseInfo, fieldCard, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.王国同调) {
                    Synchrome.apply(this, skillUseInfo, fieldCard, card, Race.KINGDOM);
                } else if (skillUseInfo.getType() == SkillType.森林同调) {
                    Synchrome.apply(this, skillUseInfo, fieldCard, card, Race.FOREST);
                } else if (skillUseInfo.getType() == SkillType.蛮荒同调) {
                    Synchrome.apply(this, skillUseInfo, fieldCard, card, Race.SAVAGE);
                } else if (skillUseInfo.getType() == SkillType.地狱同调) {
                    Synchrome.apply(this, skillUseInfo, fieldCard, card, Race.HELL);
                }
            }
        }

        // 降临系技能（除了降临复活之外）
        int position = card.getPosition();
        if (position < 0 || player.getField().getCard(position) == null) {
            // Killed or returned by other summoning skills
            return;
        }
        for (SkillUseInfo skillUseInfo : card.getAllUsableSkills()) {
            if (skillUseInfo.getSkill().isSummonSkill()) {
                if (skillUseInfo.getType() == SkillType.烈焰风暴) {
                    FireMagic.apply(skillUseInfo.getSkill(), this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.雷暴) {
                    LighteningMagic.apply(skillUseInfo, this, card, enemy, -1, 35);
                } else if (skillUseInfo.getType() == SkillType.暴风雪) {
                    IceMagic.apply(skillUseInfo, this, card, enemy, -1, 30, 0);
                } else if (skillUseInfo.getType() == SkillType.寒霜冲击) {
                    IceMagic.apply(skillUseInfo, this, card, enemy, -1, 50, 45 * enemy.getField().getAliveCards().size());
                } else if (skillUseInfo.getType() == SkillType.圣炎) {
                    HolyFire.apply(skillUseInfo.getSkill(), this, card, enemy);
                }  else if (skillUseInfo.getType() == SkillType.寒冰触碰) {
                    IceTouch.apply(skillUseInfo, this, card, enemy, 3);
                } else if (skillUseInfo.getType() == SkillType.法力风暴 || skillUseInfo.getType() == SkillType.魔法毁灭) {
                    ManaErode.apply(skillUseInfo.getSkill(), this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.毒云) {
                    PoisonMagic.apply(skillUseInfo, this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.瘟疫) {
                    Plague.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.凋零真言) {
                    WitheringWord.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.治疗) {
                    Heal.apply(skillUseInfo.getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.甘霖) {
                    Rainfall.apply(skillUseInfo.getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.月神的护佑 || skillUseInfo.getType() == SkillType.月之守护|| skillUseInfo.getType() == SkillType.月之守望) {
                    LunaBless.apply(skillUseInfo.getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.月神的触碰) {
                    LunaTouch.apply(skillUseInfo.getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.祈祷) {
                    Pray.apply(skillUseInfo.getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.魔力法阵) {
                    MagicMark.apply(this, skillUseInfo, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.诅咒) {
                    Curse.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.群体削弱) {
                    WeakenAll.apply(this, skillUseInfo, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.烈火焚神) {
                    BurningFlame.apply(skillUseInfo, this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.陷阱) {
                    Trap.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.送还) {
                    Return.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.精神污染) {
                    Insane.apply(skillUseInfo, this, card, enemy, 3, 0);
                } else if (skillUseInfo.getType() == SkillType.摧毁) {
                    Destroy.apply(this, skillUseInfo.getSkill(), card, enemy, 1);
                } else if (skillUseInfo.getType() == SkillType.传送 || skillUseInfo.getType() == SkillType.代表月亮消灭你) {
                    Transport.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.归魂) {
                    RegressionSoul.apply(this, skillUseInfo, card);
                } else if (skillUseInfo.getType() == SkillType.号角) {
                    Horn.apply(skillUseInfo, this, card);
                } else if (skillUseInfo.getType() == SkillType.封印) {
                    Seal.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.业火) {
                    HellFire.apply(skillUseInfo, this, card, enemy, 3);
                } else if (skillUseInfo.getType() == SkillType.关小黑屋) {
                    Enprison.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.净化) {
                    Purify.apply(skillUseInfo, this, card, -1);
                } else if (skillUseInfo.getType() == SkillType.战争怒吼|| skillUseInfo.getType() == SkillType.常夏日光) {
                    Soften.apply(skillUseInfo, this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.阻碍) {
                    OneDelay.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.全体阻碍) {
                    AllDelay.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.烈焰风暴) {
                    FireMagic.apply(skillUseInfo.getSkill(), this, card, enemy, -1);
                } else if (
                        skillUseInfo.getType() == SkillType.圣光洗礼 || skillUseInfo.getType() == SkillType.森林沐浴 ||
                                skillUseInfo.getType() == SkillType.蛮荒威压 || skillUseInfo.getType() == SkillType.地狱同化) {
                    RaceChange.apply(this, skillUseInfo, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.全体加速) {
                    AllSpeedUp.apply(skillUseInfo, this, card);
                } else if (skillUseInfo.getType() == SkillType.沉默) {
                    Silence.apply(this, skillUseInfo, card, enemy, false, false);
                } else if (skillUseInfo.getType() == SkillType.回魂) {
                    Resurrection.apply(this, skillUseInfo, card);
                } else if (skillUseInfo.getType() == SkillType.全体沉默) {
                    // 降临全体沉默全场只能发动一次，全领域沉默可以无限发动
                    Silence.apply(this, skillUseInfo, card, enemy, true, true);
                } else if (skillUseInfo.getType() == SkillType.魅惑之舞) {
                    Confusion.apply(skillUseInfo, this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.无限全体沉默) {
                    Silence.apply(this, skillUseInfo, card, enemy, true, false);
                } else if (!isMinion && skillUseInfo.getType() == SkillType.镜像) {
                    // 镜像召唤的单位可以被连锁攻击
                    Summon.apply(this, skillUseInfo, card, SummonType.Normal, 1, card.getName());
                } else if (skillUseInfo.getType() == SkillType.仙子召唤) {
                    Summon.apply(this, skillUseInfo, card, SummonType.Normal, 2, "蝶语仙子", "蝶语仙子");
                } else if (skillUseInfo.getType() == SkillType.星之所在) {
                    Summon.apply(this, skillUseInfo, card, SummonType.RandomSummoning, 2,
                            "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座",
                            "天秤座", "射手座", "天蝎座", "摩羯座", "水瓶座", "双鱼座");
                } else if (skillUseInfo.getType() == SkillType.灵龙轰咆) {
                    Summon.apply(this, skillUseInfo, card, SummonType.RandomSummoning, 2,
                            "光明之龙", "金属巨龙", "黄金金属巨龙", "元素灵龙", "暴怒霸龙", "毁灭之龙", "幽灵巨龙",
                            "水晶巨龙", "毒雾羽龙", "黄金毒龙",  "地魔龙", "邪狱魔龙", "混沌之龙");
                } else if (skillUseInfo.getType() == SkillType.万兽奔腾) {
                    Summon.apply(this, skillUseInfo, card, SummonType.RandomSummoning, 2,
                            "麒麟兽", "凤凰", "浮云青鸟", "九头妖蛇", "雷兽", "羽翼化蛇", "神谕火狐",
                            "齐天美猴王", "羽蛇神", "月蚀兽", "逐月恶狼", "逐日凶狼", "月之神兽", "山地兽");
                }
            } else if (!skillUseInfo.getSkill().isDeathSkill()) {
                if (skillUseInfo.getType() == SkillType.反噬) {
                    CounterBite.apply(skillUseInfo, this, card);
                } else if (skillUseInfo.getType() == SkillType.星云锁链) {
                    NebulaChain.apply(skillUseInfo, this, card);
                } else if (skillUseInfo.getType() == SkillType.咆哮) {
                    Destroy.apply(this, skillUseInfo.getSkill(), card, enemy, 1);
                    Transport.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.虚梦) {
                    Transport.apply(this, skillUseInfo.getAttachedUseInfo2().getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.上层精灵的挽歌) {
                    Resurrection.apply(this, skillUseInfo, card);
                } else if (!isMinion && skillUseInfo.getType() == SkillType.镜魔) {
                    // 镜像召唤的单位可以被连锁攻击
                    Summon.apply(this, skillUseInfo.getAttachedUseInfo2(), card, SummonType.Normal, 1, card.getName());
                } else if (skillUseInfo.getType() == SkillType.全领域沉默) {
                    Silence.apply(this, skillUseInfo, card, enemy, true, false);
                } else if (skillUseInfo.getType() == SkillType.召唤玫瑰剑士) {
                    Summon.apply(this, skillUseInfo.getAttachedUseInfo1(), card, SummonType.Normal, 1,
                            "花舞剑士");
                } else if (skillUseInfo.getType() == SkillType.桃园结义) {
                    Summon.apply(this, skillUseInfo, card, SummonType.Normal, 2,
                            "三国英魂云长","三国英魂翼德");
                } else if (skillUseInfo.getType() == SkillType.灵龟羁绊) {
                    Summon.apply(this, skillUseInfo, card, SummonType.Normal, 1,
                            "巨岛龟幼崽");
                } else if (skillUseInfo.getType() == SkillType.舌战群儒) {
                    Insane.apply(skillUseInfo, this, card, enemy, -1, 70);
                } else if (skillUseInfo.getType() == SkillType.合纵连横) {
                    GiantEarthquakesLandslides.apply(this, skillUseInfo.getSkill(), card, enemy, 1);
                } else if (skillUseInfo.getType() == SkillType.铁壁) {
                    ImpregnableDefenseHeroBuff.apply(this, skillUseInfo, card);
                }
            }
        }
    }

    // reviver: for most of the cases, it should be null.
    // It is only set when the summoning skill performer is revived by another card.
    public void resolveSecondClassSummoningSkills(List<CardInfo> summonedCards, Field myField, Field opField, Skill summonSkill, boolean isSummoning) throws HeroDieSignal {
        if (summonSkill != null && summonSkill.getType() == SkillType.星云锁链) {
            // 木盒的特殊BUG，星云锁链召唤的卡无法发动第二阶降临技能
            return;
        }
        for (CardInfo card : summonedCards) {
            int position = card.getPosition();
            if (position < 0 || myField.getCard(position) == null) {
                // Killed or returned by other summoning skills
                continue;
            }
            for (SkillUseInfo skillUseInfo : card.getAllUsableSkills()) {
                if (skillUseInfo.getType() == SkillType.时光倒流) {
                    TimeBack.apply(skillUseInfo, this, myField.getOwner(), opField.getOwner());
                } else if (skillUseInfo.getType() == SkillType.献祭) {
                    Sacrifice.apply(this, skillUseInfo, card, summonSkill);
                } else if (skillUseInfo.getType() == SkillType.侵蚀) {
                    Erode.apply(this, skillUseInfo, card, opField.getOwner(), summonSkill);
                } else if (skillUseInfo.getType() == SkillType.鬼才) {
                    Erode.apply(this, skillUseInfo.getAttachedUseInfo1(), card, opField.getOwner(), summonSkill);
                } else if (skillUseInfo.getType() == SkillType.复活 && skillUseInfo.getSkill().isSummonSkill() && isSummoning) {
                    Revive.apply(this, skillUseInfo, card);
                } else if (skillUseInfo.getType() == SkillType.灵魂献祭 && skillUseInfo.getSkill().isSummonSkill() && isSummoning) {
                    Revive.apply(this, skillUseInfo, card);
                    Sacrifice.apply(this, skillUseInfo, card, summonSkill);
                } else if (skillUseInfo.getType() == SkillType.制衡) {
                    Sacrifice.apply(this, skillUseInfo, card, summonSkill);
                    if (isSummoning) {
                        Revive.apply(this, skillUseInfo, card);
                        Revive.apply(this, skillUseInfo, card);
                    }
                }
            }
        }
    }

    public void resolveLeaveSkills(CardInfo card) {
        for (SkillUseInfo deadCardSkillUseInfo : card.getUsableNormalSkills()) {
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
            } else if (deadCardSkillUseInfo.getType() == SkillType.根源之力) {
                TogetherBuff.remove(this, deadCardSkillUseInfo, card, null);
            } else if (deadCardSkillUseInfo.getType() == SkillType.神圣守护) {
                HolyGuard.remove(this, deadCardSkillUseInfo, card);
            } else if (deadCardSkillUseInfo.getType() == SkillType.西凉铁骑) {
                GiveSideSkill.remove(this, deadCardSkillUseInfo, card, deadCardSkillUseInfo.getAttachedUseInfo1().getSkill());
            } else if (deadCardSkillUseInfo.getType() == SkillType.袈裟斩) {
                GiveSideSkill.remove(this, deadCardSkillUseInfo, card, deadCardSkillUseInfo.getAttachedUseInfo1().getSkill());
            } else if (deadCardSkillUseInfo.getType() == SkillType.致命打击) {
                GiveSideSkill.remove(this, deadCardSkillUseInfo, card, deadCardSkillUseInfo.getAttachedUseInfo1().getSkill());
            } else if (deadCardSkillUseInfo.getType() == SkillType.爱心料理) {
                GiveSideSkill.remove(this, deadCardSkillUseInfo, card, deadCardSkillUseInfo.getAttachedUseInfo1().getSkill());
            } else if (deadCardSkillUseInfo.getType() == SkillType.仁德之君) {
                GiveSideSkill.remove(this, deadCardSkillUseInfo, card, deadCardSkillUseInfo.getAttachedUseInfo1().getSkill());
            }  else if (deadCardSkillUseInfo.getType() == SkillType.剑舞) {
                GiveSideSkill.remove(this, deadCardSkillUseInfo, card, deadCardSkillUseInfo.getAttachedUseInfo1().getSkill());
            } else if (deadCardSkillUseInfo.getType() == SkillType.军团王国之力
                    || deadCardSkillUseInfo.getType() == SkillType.军团森林之力
                    || deadCardSkillUseInfo.getType() == SkillType.军团蛮荒之力
                    || deadCardSkillUseInfo.getType() == SkillType.军团地狱之力) {
                LegionBuff.remove(this, deadCardSkillUseInfo, card);
            } else if (deadCardSkillUseInfo.getSkill().getGiveSkill() == 1) {
                GiveSideSkill.removeAll(this,deadCardSkillUseInfo,card);
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

            OnDamagedResult result = this.applyDamage(card, card, item.getCause().getSkill(), item.getEffect());
            this.resolveDeathSkills(item.getCause().getOwner(), card, item.getCause().getSkill(), result);
            if (result.cardDead) {
                break;
            }
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
            if (rune.is(RuneData.鬼步) && !victim.isSilent()) {
                if (Escape.isStatusEscaped(rune.getSkill(), this, item, victim)) {
                    return new BlockStatusResult(true);
                }
            }
        }
        for (SkillUseInfo blockSkillUseInfo : victim.getUsableNormalSkills()) {
            if (blockSkillUseInfo.getType() == SkillType.脱困 ||
                    blockSkillUseInfo.getType() == SkillType.神威 ||
                    blockSkillUseInfo.getType() == SkillType.村正 ||
                    blockSkillUseInfo.getType() == SkillType.月之守望 ||
                    blockSkillUseInfo.getType() == SkillType.冰神附体 ||
                    blockSkillUseInfo.getType() == SkillType.以逸待劳 ||
                    blockSkillUseInfo.getType() == SkillType.不灭原核 ||
                    blockSkillUseInfo.getType() == SkillType.黄天当立 ||
                    blockSkillUseInfo.getType() == SkillType.神之守护) {
                if (Escape.isStatusEscaped(blockSkillUseInfo.getSkill(), this, item, victim)) {
                    return new BlockStatusResult(true);
                }
            }
        }
        return new BlockStatusResult(false);
    }

    private void setCardToField(CardInfo card) {
        Player player = card.getOwner();
        card.reset();
        this.stage.getUI().summonCard(player, card);
        // 夺魂可以从敌方卡组召唤
        if (card.getOriginalOwner().getGrave().contains(card)) {
            card.getOriginalOwner().getGrave().removeCard(card);
        }
        player.getField().addCard(card);
        player.getHand().removeCard(card);
        // 星云锁链之类可以从卡组直接召唤的情况
        player.getDeck().removeCard(card);
    }

    public void summonCard(Player player, CardInfo summonedCard, CardInfo reviver, boolean isMinion, Skill summonSkill) throws HeroDieSignal {
        Player enemy = this.getStage().getOpponent(player);
        List<CardInfo> summonedCards = new ArrayList<CardInfo>();
        summonedCards.add(summonedCard);
        setCardToField(summonedCard);
        this.resolveFirstClassSummoningSkills(summonedCard, player, enemy, isMinion);
        this.resolveSecondClassSummoningSkills(summonedCards, player.getField(), enemy.getField(), summonSkill, true);
    }

    /**
     * 1. Process racial buff skills
     * 2. Process summoning skills
     *
     * @param player
     * @param cards
     * @param reviver
     * @throws HeroDieSignal
     */
    public void summonCards(Player player, CardInfo reviver, boolean isMinion) throws HeroDieSignal {
        Player enemy = this.getStage().getOpponent(player);
        List<CardInfo> summonedCards = new ArrayList<CardInfo>();
        while (true) {
            CardInfo summonedCard = null;
            for (CardInfo card : player.getHand().toList()) {
                if (card.getSummonDelay() == 0) {
                    summonedCard = card;
                    break;
                }
            }
            if (summonedCard == null) {
                break;
            }
            summonedCards.add(summonedCard);
            setCardToField(summonedCard);
            this.resolveFirstClassSummoningSkills(summonedCard, player, enemy, isMinion);
        }

        this.resolveSecondClassSummoningSkills(summonedCards, player.getField(), enemy.getField(), null, true);
    }

    /**
     * @param cardSkill
     * @param attacker
     * @param defender
     * @return Whether block is disabled
     */
    public boolean resolveCounterBlockSkill(Skill cardSkill, CardInfo attacker, CardInfo defender) {
        for (SkillUseInfo attackerSkillUseInfo : attacker.getUsableNormalSkills()) {
            if (attackerSkillUseInfo.getType() == SkillType.弱点攻击 ||
                    attackerSkillUseInfo.getType() == SkillType.会心一击 ||
                    attackerSkillUseInfo.getType() == SkillType.三千世界 ||
                    attackerSkillUseInfo.getType() == SkillType.亮银 ||
                    attackerSkillUseInfo.getType() == SkillType.鹰眼 ||
                    attackerSkillUseInfo.getType() == SkillType.九转禁术 ||
                    attackerSkillUseInfo.getType() == SkillType.刀语 ||
                    attackerSkillUseInfo.getType() == SkillType.武圣) {
                return WeakPointAttack.isBlockSkillDisabled(this, attackerSkillUseInfo.getSkill(), cardSkill, attacker, defender);
            } else if (attackerSkillUseInfo.getType() == SkillType.斩杀 ||
                    attackerSkillUseInfo.getType() == SkillType.送葬之刃 ||
                    attackerSkillUseInfo.getType() == SkillType.无双 ||
                    attackerSkillUseInfo.getType() == SkillType.双斩) {
                return SuddenKill.isBlockSkillDisabled(this, attackerSkillUseInfo.getSkill(), cardSkill, attacker, defender);
            }
        }
        if (!attacker.isDead() && !attacker.isSilent() && !attacker.justRevived()) {
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.鹰眼);
                if (rune != null) {
                    return WeakPointAttack.isBlockSkillDisabled(this, rune.getSkill(), cardSkill, attacker, defender);
                }
            }
        }
        return false;
    }

    public boolean resolveStopBlockSkill(Skill cardSkill, CardInfo attacker, CardInfo defender) {
        for (SkillUseInfo attackerSkillUseInfo : attacker.getUsableNormalSkills()) {
            if (attackerSkillUseInfo.getType() == SkillType.破军||attackerSkillUseInfo.getType() == SkillType.原素裂变) {
                return DefeatArmy.isDefenSkillDisabled(this, attackerSkillUseInfo.getSkill(), cardSkill, attacker, defender);
            }
        }
        return false;
    }

    public boolean resolverCounterAttackBlockSkill(Skill counterAttackSkill, CardInfo attacker, CardInfo counterAttacker) {
        for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
            if (skillUseInfo.getType() == SkillType.灵巧 ||
                    skillUseInfo.getType() == SkillType.武形秘仪) {
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
                Snipe.apply(rune.getSkillUseInfo(),rune.getSkill(), this, rune, defenderHero, 1);
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
                BurningFlame.apply(rune.getSkillUseInfo(), this, rune, defenderHero, -1);
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
                Purify.apply(rune.getSkillUseInfo(), this, rune, -1);
            } else if (rune.is(RuneData.风暴)) {
                ManaErode.apply(rune.getSkill(), this, rune, defenderHero, -1);
            } else if (rune.is(RuneData.封闭)) {
                Silence.apply(this, rune.getSkillUseInfo(), rune, defenderHero, true, false);
            }
        }
    }

    public void killCard(CardInfo attacker, CardInfo victim, Skill cardSkill) throws HeroDieSignal {
        int originalDamage = victim.getHP();
        int actualDamage = victim.applyDamage(victim.getHP());
        this.cardDead(attacker, cardSkill, victim);
        OnDamagedResult onDamagedResult = new OnDamagedResult();
        onDamagedResult.actualDamage = actualDamage;
        onDamagedResult.originalDamage = originalDamage;
        onDamagedResult.cardDead = true;
        onDamagedResult.unbending = false;
        victim.removeStatus(CardStatusType.不屈);
        this.resolveDeathSkills(attacker, victim, cardSkill, onDamagedResult);
    }

    public void resolvePrecastSkills(CardInfo card, Player defenderHero,boolean flag) throws HeroDieSignal {
        for (SkillUseInfo skillUseInfo : card.getUsablePrecastSkills()) {
            if (skillUseInfo.getType() == SkillType.凋零真言) {
                WitheringWord.apply(skillUseInfo, this, card, defenderHero);
            } else if (skillUseInfo.getType() == SkillType.灵王的轰击) {
                ManaErode.apply(skillUseInfo.getSkill(), this, card, defenderHero, 1);
            } else if (skillUseInfo.getType() == SkillType.神性祈求) {
                Purify.apply(skillUseInfo, this, card, -1);
            } else if (skillUseInfo.getType() == SkillType.寒霜冲击) {
                IceMagic.apply(skillUseInfo, this, card, defenderHero, -1, 50, 45 * defenderHero.getField().getAliveCards().size());
            } else if (skillUseInfo.getType() == SkillType.全体加速) {
                AllSpeedUp.apply(skillUseInfo, this, card);
            } else if (skillUseInfo.getType() == SkillType.混乱领域) {
                Confusion.apply(skillUseInfo, this, card, defenderHero, 3);
            } else if (skillUseInfo.getType() == SkillType.镜像&&flag) {
                Summon.apply(this, skillUseInfo, card, SummonType.Summoning, 1, card.getName());
            }
        }
    }

    public void resolvePostcastSkills(CardInfo card, Player defenderHero) throws HeroDieSignal {
        for (SkillUseInfo skillUseInfo : card.getUsablePostcastSkills()) {
            if (skillUseInfo.getType() == SkillType.灵王的轰击) {
                ManaErode.apply(skillUseInfo.getSkill(), this, card, defenderHero, 1);
            } else if (skillUseInfo.getType() == SkillType.修罗地火攻) {
                SuraFire.apply(this, skillUseInfo, card, defenderHero);
            } else if (skillUseInfo.getType() == SkillType.寒霜冲击) {
                IceMagic.apply(skillUseInfo, this, card, defenderHero, -1, 50, 45 * defenderHero.getField().getAliveCards().size());
            } else if (skillUseInfo.getType() == SkillType.回魂) {
                Resurrection.apply(this, skillUseInfo, card);
            } else if (skillUseInfo.getType() == SkillType.青囊) {
                Revive.apply(this, skillUseInfo, card);
            } else if (skillUseInfo.getType() == SkillType.洛神) {
                // 镜像召唤的单位可以被连锁攻击
                Summon.apply(this, skillUseInfo, card, SummonType.Normal, 1, card.getName());
            }
        }
    }

    public static class BlockStatusResult {
        private boolean blocked;

        public BlockStatusResult(boolean blocked) {
            this.blocked = blocked;
        }

        public boolean isBlocked() {
            return blocked;
        }
    }
}
