package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    //添加阶段为方便技能处理
    public void setStagePhase(Phase phase) {
        this.stage.setPhase(phase);
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
                if (skillUseInfo.getType() == SkillType.神性祈求 || skillUseInfo.getType() == SkillType.神性祈祷) {
                    Purify.apply(skillUseInfo, this, card, -1);
                } else if (skillUseInfo.getType() == SkillType.净化领域) {
                    Purify.apply(skillUseInfo, this, card, -1);
                } else if (skillUseInfo.getType() == SkillType.净魂领域 || skillUseInfo.getType() == SkillType.星座能量清醒) {
                    Purify.apply(skillUseInfo, this, card, -2);
                } else if (skillUseInfo.getType() == SkillType.幻音) {
                    Purify.apply(skillUseInfo.getAttachedUseInfo2(), this, card, -2);
                } else if (skillUseInfo.getType() == SkillType.西凉铁骑 || skillUseInfo.getType() == SkillType.零度领域 || skillUseInfo.getType() == SkillType.冰肌雪骨) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.袈裟斩) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.厄运枪) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.顽石契约) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.碎冰成雪) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.致命打击) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.爱心料理) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.仁德之君) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.质能展开) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.神圣光环) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.链式防守 || skillUseInfo.getType() == SkillType.潘帕斯雄鹰) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.剑舞) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.陨星) {
                    GiveSideSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.月神的恩泽) {
                    MoonBounty.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.临风傲雪) {
                    MoonBounty.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.水榭桃盈) {
                    AddSidesSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.铁舞天衣) {
                    AddSidesSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.不死战神 || skillUseInfo.getType() == SkillType.页游不凋花) {
                    AddSkillOneself.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.荆棘守护) {
                    AddSidesSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.隐遁之术 || skillUseInfo.getType() == SkillType.神兵召唤光环
                        || skillUseInfo.getType() == SkillType.天降神兵 || skillUseInfo.getType() == SkillType.聚能立场
                        || skillUseInfo.getType() == SkillType.圣盾大光环 || skillUseInfo.getType() == SkillType.勤学苦练光环
                        || skillUseInfo.getType() == SkillType.战争狂热) {
                    AllFiledExceptSelf.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.集训) {
                    AllFiledExceptSelf.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getAttachedUseInfo1().getSkill());
                    AllFiledExceptSelf.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo2().getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.致命晶莹) {
                    AllFiledAddSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.乐不思蜀) {
                    AllFiledAddSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.御魔屏障) {
                    AllFiledAddSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.邪恶光环) {
                    AllFiledAddSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.荆棘刺盾) {
                    AllFiledAddSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.共生链接) {
                    AllFiledAddSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.圣战之歌) {
                    AllFiledAddSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.圣域屏障 || skillUseInfo.getType() == SkillType.足球风暴 || skillUseInfo.getType() == SkillType.破阵之势
                        || skillUseInfo.getType() == SkillType.蛇蜕之术) {
                    AllFiledAddSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.庇护光环 || skillUseInfo.getType() == SkillType.武形破剑光环 || skillUseInfo.getType() == SkillType.镜面光环
                        || skillUseInfo.getType() == SkillType.秘纹领域 || skillUseInfo.getType() == SkillType.圣光奏鸣曲) {
                    AddSidesSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                } else if (skillUseInfo.getType() == SkillType.骑士庇护) {
                    AddSidesSkill.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill());
                    Purify.apply(skillUseInfo, this, card, -1);
                } else if (skillUseInfo.getType() == SkillType.流光回梦) {
                    Cooperation.apply(this, skillUseInfo, card, "雪舞霓裳", true);
                } else if (skillUseInfo.getType() == SkillType.暗红魔导阵) {
                    Cooperation.apply(this, skillUseInfo, card, "幻镜魔导镜像", false);
                } else if (skillUseInfo.getType() == SkillType.暗红魔导阵) {
                    Cooperation.apply(this, skillUseInfo, card, "幻镜魔导", false);
                } else if (skillUseInfo.getType() == SkillType.暗红魔导阵) {
                    Cooperation.apply(this, skillUseInfo, card, "真幻镜魔导", false);
                } else if (skillUseInfo.getType() == SkillType.魏国英魂) {
                    Cooperation.apply(this, skillUseInfo, card, "三国英魂·孟德", true);
                } else if (skillUseInfo.getType() == SkillType.卡组保护) {
                    CooperationExceptSelf.apply(this, skillUseInfo, card, "魔卡策划X", false);
                } else if (skillUseInfo.getType() == SkillType.冥狱牢囚) {
                    Cooperation.apply(this, skillUseInfo, card, "樱蝶仙子", true);
                } else if (skillUseInfo.getType() == SkillType.重整 || skillUseInfo.getType() == SkillType.不朽岿岩 || skillUseInfo.getType() == SkillType.不息神盾 || skillUseInfo.getType() == SkillType.再生金蝉) {
                    Reforming.reset(skillUseInfo, card);
                } else if (skillUseInfo.getType() == SkillType.无刀取) {
                    HolyShield.resetApply(skillUseInfo, this, card);
                }
            }
        }
    }

    public void resolvePreAttackSkills(CardInfo attacker, Player defender, int status) throws HeroDieSignal {
        for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
            if (attacker.isDead() || attacker.getStatus().containsStatus(CardStatusType.不屈)) {
                continue;
            }
            if (skillUseInfo.getType() == SkillType.透支 || skillUseInfo.getType() == SkillType.过载 || skillUseInfo.getType() == SkillType.勤学苦练) {
                Overdraw.apply(this, skillUseInfo, attacker);
            }
            else if (skillUseInfo.getType() == SkillType.力竭) {
                Exhausted.apply(this, skillUseInfo, attacker);
            }
            else if (skillUseInfo.getType() == SkillType.未知) {
                // JUST A PLACEHOLDER
            } else if (skillUseInfo.getType() == SkillType.送还 || skillUseInfo.getType() == SkillType.突袭) {
                Return.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.流光回梦) {
                ReturnToHandAndDelay.apply(this, skillUseInfo.getSkill(), attacker, defender, 2, 1);
            } else if (skillUseInfo.getType() == SkillType.退散) {
                ReturnCard.apply(this, skillUseInfo.getSkill(), attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.弱者溃散) {
                ReturnCardAndDelay.apply(this, skillUseInfo.getSkill(), attacker, defender, 2);
            } else if (skillUseInfo.getType() == SkillType.LETITGO || skillUseInfo.getType() == SkillType.击溃 || skillUseInfo.getType() == SkillType.高位逼抢) {
                Return.apply(this, skillUseInfo.getSkill().getAttachedSkill1(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.沉默 ||
                    skillUseInfo.getType() == SkillType.觉醒沉默 && attacker.isAwaken(skillUseInfo, Race.KINGDOM, 1) ||
                    skillUseInfo.getType() == SkillType.觉醒沉默A && attacker.isAwaken(skillUseInfo, Race.FOREST, 2)) {
                Silence.apply(this, skillUseInfo, attacker, defender, false, false);
            } else if (skillUseInfo.getType() == SkillType.全体沉默) {
                Silence.apply(this, skillUseInfo, attacker, defender, true, false);
            } else if (skillUseInfo.getType() == SkillType.死亡印记 || skillUseInfo.getType() == SkillType.武形印记) {
                DeathMark.apply(this, skillUseInfo, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.霜火炸弹 || skillUseInfo.getType() == SkillType.破片手雷) {
                DeathMark.apply(this, skillUseInfo, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.煮豆燃萁) {
                DeathMark.apply(this, skillUseInfo, attacker, defender, 7);
            } else if (skillUseInfo.getType() == SkillType.关小黑屋) {
                Enprison.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.吐槽) {
                Tsukomi.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.火球) {
                FireMagic.apply(skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.八重红彼岸 || skillUseInfo.getType() == SkillType.浩劫
                    || skillUseInfo.getType() == SkillType.最终判决 || skillUseInfo.getType() == SkillType.剧毒剑刃) {
                GreatFireMagic.apply(skillUseInfo.getSkill(), this, attacker, defender, 1, false);
            } else if (skillUseInfo.getType() == SkillType.奥术湮灭) {
                GreatFireMagic.apply(skillUseInfo.getSkill(), this, attacker, defender, 2, false);
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
                    skillUseInfo.getType() == SkillType.觉醒雷神降临 && attacker.isAwaken(skillUseInfo, Race.HELL, 1)) {
                LighteningMagic.apply(skillUseInfo, this, attacker, defender, -1, 75);
            } else if (skillUseInfo.getType() == SkillType.冰弹) {
                IceMagic.apply(skillUseInfo, this, attacker, defender, 1, 45, 0);
            } else if (skillUseInfo.getType() == SkillType.圣诞雪球) {
                IceMagic.apply(skillUseInfo, this, attacker, defender, 1, 90, 0);
            } else if (skillUseInfo.getType() == SkillType.霜冻新星) {
                IceMagic.apply(skillUseInfo, this, attacker, defender, 3, 35, 0);
            } else if (skillUseInfo.getType() == SkillType.暴风雪) {
                IceMagic.apply(skillUseInfo, this, attacker, defender, -1, 30, 0);
            } else if (skillUseInfo.getType() == SkillType.冰封禁制) {
                IceMagic.apply(skillUseInfo, this, attacker, defender, -1, 90, 0);
            } else if (skillUseInfo.getType() == SkillType.寒霜冲击) {
                IceMagic.apply(skillUseInfo, this, attacker, defender, -1, 50, (5 + skillUseInfo.getSkill().getLevel() * 5) * defender.getField().getAliveCards().size());
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
            } else if (skillUseInfo.getType() == SkillType.甘霖 || skillUseInfo.getType() == SkillType.甘露) {
                Rainfall.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.月神的护佑 || skillUseInfo.getType() == SkillType.月之守护 || skillUseInfo.getType() == SkillType.月之守望 || skillUseInfo.getType() == SkillType.紫气东来) {
                LunaBless.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.月神的触碰 || skillUseInfo.getType() == SkillType.月神的恩赐 || skillUseInfo.getType() == SkillType.救死扶伤) {
                LunaTouch.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.治疗之雾) {
                HealingMist.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.祈祷) {
                Pray.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.复活) {
                Revive.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.新生) {
                NewBorn.apply(this, skillUseInfo, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.夺魂) {
                SoulControl.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.鬼才) {
                SoulControl.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.背刺 || skillUseInfo.getType() == SkillType.大背刺 || skillUseInfo.getType() == SkillType.突击
                    || skillUseInfo.getType() == SkillType.闪耀突击) {
                BackStab.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.群体削弱 || skillUseInfo.getType() == SkillType.霸王之姿) {
                WeakenAll.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.回魂) {
                Resurrection.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.祈愿 || skillUseInfo.getType() == SkillType.神恩) {
                Supplication.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.号角 || skillUseInfo.getType() == SkillType.集结旗帜) {
                Horn.apply(skillUseInfo, this, attacker);
            } else if (skillUseInfo.getType() == SkillType.觉醒风之祈愿) {
                if (skillUseInfo.getOwner().getOwner().getHand().isFull()) {
                    Horn.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker);
                }
                if (!skillUseInfo.getOwner().getOwner().getHand().isFull()) {
                    Supplication.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, defender);
                }
            } else if (skillUseInfo.getType() == SkillType.归魂) {
                RegressionSoul.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.太平要术) {
                RegressionSoul.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, defender);
                LunaBless.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.狙击 || skillUseInfo.getType() == SkillType.射门) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.魔神之刃) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.武形秘箭 || skillUseInfo.getType() == SkillType.骤雨) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.星座能量思考) {
                Snipe.apply(skillUseInfo.getAttachedUseInfo2(), skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.寒心恨雪) {
                Snipe.apply(skillUseInfo.getAttachedUseInfo2(), skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.二重狙击 || skillUseInfo.getType() == SkillType.处罚) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 2);
            } else if (skillUseInfo.getType() == SkillType.神箭三重奏) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.寒莹触碰 || skillUseInfo.getType() == SkillType.猎杀时刻) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.武形神箭) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, -1);
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.穿云箭 || skillUseInfo.getType() == SkillType.完美狙击 || skillUseInfo.getType() == SkillType.精准狙击) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.弹无虚发) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.破阵弧光) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 1);
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 1);
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.三连狙击) {
                Snipe.apply(skillUseInfo.getAttachedUseInfo1(), skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, 1);
                Snipe.apply(skillUseInfo.getAttachedUseInfo1(), skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, 1);
                if (defender.getField().getAliveCards().size() >= 3) {
                    Snipe.apply(skillUseInfo.getAttachedUseInfo1(), skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, 1);
                } else {
                    Snipe.apply(skillUseInfo.getAttachedUseInfo2(), skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 1);
                }
                Snipe.apply(skillUseInfo.getAttachedUseInfo1(), skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.绯弹) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.左轮射击) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.百步穿杨) {
                Snipe.apply(skillUseInfo, skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, -1);
                Snipe.apply(skillUseInfo, skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.枪林弹雨) {
                Snipe.apply(skillUseInfo, skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, -1);
                Snipe.apply(skillUseInfo, skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.迷魂) {
                Confusion.apply(skillUseInfo, this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.混乱领域 || skillUseInfo.getType() == SkillType.连奏) {
                Confusion.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.国色 || skillUseInfo.getType() == SkillType.千娇百媚) {
                Confusion.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.魅惑之舞) {
                Confusion.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.蝶语 || skillUseInfo.getType() == SkillType.倾城之舞) {
                Confusion.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.幻音) {
                Confusion.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.无我境界) {
                Confusion.apply(skillUseInfo, this, attacker, defender, 3);
                Insane.apply(skillUseInfo, this, attacker, defender, 1, 100);
            } else if (skillUseInfo.getType() == SkillType.烈火焚神 || skillUseInfo.getType() == SkillType.天火 || skillUseInfo.getType() == SkillType.全体灼烧) {
                BurningFlame.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.诅咒) {
                Curse.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.魔神之咒) {
                Curse.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.摧毁) {
                Destroy.apply(this, skillUseInfo.getSkill(), attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.冥府之召) {
                UnderworldCall.apply(this, skillUseInfo.getSkill(), attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.药桶爆弹) {
                UnderworldCall.apply(this, skillUseInfo.getSkill(), attacker, defender, 5);
            } else if (skillUseInfo.getType() == SkillType.死亡宣告) {
                UnderworldCall.apply(this, skillUseInfo.getSkill(), attacker, defender, 2);
            } else if (skillUseInfo.getType() == SkillType.烈焰审判) {
                UnderworldCall.apply(this, skillUseInfo.getSkill(), attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.炼金失败 || skillUseInfo.getType() == SkillType.凤凰涅盘 || skillUseInfo.getType() == SkillType.海滨危机 || skillUseInfo.getType() == SkillType.战术性撤退) {
                AlchemyFailure.apply(this, skillUseInfo, skillUseInfo.getSkill(), attacker);
            } else if (skillUseInfo.getType() == SkillType.瘟疫) {
                Plague.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.凋零真言 || skillUseInfo.getType() == SkillType.暗之凋零 || skillUseInfo.getType() == SkillType.花刺) {
                WitheringWord.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.血炼 || skillUseInfo.getType() == SkillType.生命吸取) {
                BloodPaint.apply(skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.鲜血盛宴 || skillUseInfo.getType() == SkillType.歃血魔咒 ||
                    skillUseInfo.getType() == SkillType.猎杀之夜) {
                BloodPaint.apply(skillUseInfo.getSkill(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.天谴 || skillUseInfo.getType() == SkillType.末世术 || skillUseInfo.getType() == SkillType.以逸待劳) {
                HeavenWrath.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.莫测) {
                HeavenWrath.apply(this, skillUseInfo.getAttachedUseInfo1().getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.封印 || skillUseInfo.getType() == SkillType.封锁) {
                Seal.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.圣炎) {
                HolyFire.apply(skillUseInfo.getSkill(), this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.法力侵蚀 || skillUseInfo.getType() == SkillType.灵王的轰击 || skillUseInfo.getType() == SkillType.灵能冲击 ||
                    skillUseInfo.getType() == SkillType.觉醒灵王的轰击 && attacker.isAwaken(skillUseInfo, Race.FOREST, 2)) {
                ManaErode.apply(skillUseInfo.getSkill(), this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.破魔手) {
                ManaErode.apply(skillUseInfo.getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.法力风暴 || skillUseInfo.getType() == SkillType.魔法毁灭 || skillUseInfo.getType() == SkillType.片翼天使) {
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
            } else if (skillUseInfo.getType() == SkillType.战争怒吼 || skillUseInfo.getType() == SkillType.常夏日光 || skillUseInfo.getType() == SkillType.碎裂怒吼) {
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
            } else if (skillUseInfo.getType() == SkillType.原召唤花族守卫) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "黄金金属巨龙", "原处女座");
            } else if (skillUseInfo.getType() == SkillType.召唤花族守卫) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "黄金金属巨龙", "处女座");
            } else if (skillUseInfo.getType() == SkillType.召唤花族侍卫) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "时光女神", "雷雕之魂");
            } else if (skillUseInfo.getType() == SkillType.原召唤花族侍卫) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "原时光女神", "雷雕之魂");
            } else if (skillUseInfo.getType() == SkillType.七十二变) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "齐天美猴王", "齐天美猴王");
            } else if (skillUseInfo.getType() == SkillType.仙子召唤) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "蝶语仙子", "蝶语仙子");
            } else if (skillUseInfo.getType() == SkillType.召唤炮灰) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "炮灰", "炮灰");
            } else if (skillUseInfo.getType() == SkillType.召唤孔明) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 1, "三国英魂孔明");
            } else if (skillUseInfo.getType() == SkillType.英灵降临) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "圣剑持有者", "银河圣剑使", "精灵游骑兵", "爱神", "蝗虫公爵", "战场女武神", "龙角将军", "断罪之镰");
            } else if (skillUseInfo.getType() == SkillType.禁术召唤) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "网页版禁术无尽华尔兹", "网页版禁术全领域沉默", "网页版禁术救赎", "网页版禁术末世降临", "网页版禁术全体阻碍");
            } else if (skillUseInfo.getType() == SkillType.进攻号令) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 2,
                        "网页版原素曜灵", "网页版原素猎手", "网页版原素狂暴者");
            } else if (skillUseInfo.getType() == SkillType.进攻号令) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 2,
                        "网页版原素侍卫", "网页版原素将军");
            } else if (skillUseInfo.getType() == SkillType.页游纯洁) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2,
                        "降临天使", "大主教");
            } else if (skillUseInfo.getType() == SkillType.万华镜) {
                Summon.apply(this, skillUseInfo.getAttachedUseInfo1().getAttachedUseInfo1(), attacker, SummonType.Normal, 1,
                        "幻镜魔导镜像");
                Summon.apply(this, skillUseInfo.getAttachedUseInfo1().getAttachedUseInfo2(), attacker, SummonType.Normal, 1,
                        "幻镜魔导镜像");
                Summon.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, SummonType.Normal, 1,
                        "幻镜魔导镜像");
            } else if (skillUseInfo.getType() == SkillType.页游万华镜) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "网页版幻镜魔导·屠戮", "网页版幻镜魔导·归源", "网页版幻镜魔导·暗炎");
            } else if (skillUseInfo.getType() == SkillType.圣诞大狂欢) {
                SummonOpponent.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, SummonType.Normal, 4, "圣诞雪人", "圣诞雪人", "圣诞雪人", "圣诞雪人");
                Summon.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, SummonType.Normal, 4, "圣诞老人", "圣诞树人", "圣诞麋鹿", "圣诞麋鹿");
            } else if (skillUseInfo.getType() == SkillType.大地召唤) {
                SummonOpponent.apply(this, skillUseInfo, attacker, SummonType.Normal, 1, "网页版大地之影");
            } else if (skillUseInfo.getType() == SkillType.樱色轮舞) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "月樱公主", "风之樱女", "春樱斗魂");
            } else if (skillUseInfo.getType() == SkillType.魏国英魂) {
                AddCard.apply(this, skillUseInfo, attacker, SummonType.Summoning, 1,
                        "三国英魂孟德", "三国英魂仲达", "三国樱魂文远", "三国英魂元让", "三国英魂甄姬", "三国英魂文若");
            } else if (skillUseInfo.getType() == SkillType.蜀国英魂) {
                AddCard.apply(this, skillUseInfo, attacker, SummonType.Summoning, 1,
                        "三国英魂子龙", "三国英魂翼德", "三国英魂卧龙", "三国英魂孔明", "三国英魂孟起", "三国英魂云长", "三国英魂汉升", "三国英魂玄德", "三国英魂星彩");
            } else if (skillUseInfo.getType() == SkillType.吴国英魂) {
                AddCard.apply(this, skillUseInfo, attacker, SummonType.Summoning, 1,
                        "三国英魂大乔", "三国英魂仲谋", "三国英魂子敬", "三国英魂伯言", "三国英魂子义");
            } else if (skillUseInfo.getType() == SkillType.繁星) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "月亮女神", "复活节兔女郎", "银河圣剑使", "世界树之灵");
            } else if (skillUseInfo.getType() == SkillType.育龙者) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "光明之龙", "金属巨龙", "黄金金属巨龙", "元素灵龙", "暴怒霸龙", "毁灭之龙", "幽灵巨龙",
                        "水晶巨龙", "毒雾羽龙", "黄金毒龙", "地魔龙", "邪狱魔龙", "混沌之龙", "地狱雷龙");
            } else if (skillUseInfo.getType() == SkillType.寒霜召唤) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1,
                        "陨星魔法使", "怒雪咆哮", "圣诞老人", "寒霜冰灵使", "白羊座", "霜狼酋长", "雪月花", "梦魇猎手·霜");
            } else if (skillUseInfo.getType() == SkillType.原寒霜召唤) {
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
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 1, "巅峰兵长");
            } else if (skillUseInfo.getType() == SkillType.突击军势) {
                Summon.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, SummonType.Normal, 1, "巅峰伍长");
                Summon.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, SummonType.Normal, 1, "巅峰兵长");
            } else if (skillUseInfo.getType() == SkillType.突击军阵) {
                Summon.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, SummonType.Normal, 1, "巅峰伍长·合金");
                Summon.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, SummonType.Normal, 1, "巅峰兵长·合金");
            } else if (skillUseInfo.getType() == SkillType.灵龙轰咆) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 2,
                        "光明之龙", "金属巨龙", "黄金金属巨龙", "元素灵龙", "暴怒霸龙", "毁灭之龙", "幽灵巨龙",
                        "水晶巨龙", "毒雾羽龙", "黄金毒龙", "地魔龙", "邪狱魔龙", "混沌之龙");
            } else if (skillUseInfo.getType() == SkillType.法师契约) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 2,
                        "魔导师", "暴雪召唤士");
            } else if (skillUseInfo.getType() == SkillType.圣堂召唤) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2,
                        "圣堂刑律官", "圣堂执政官");
            } else if (skillUseInfo.getType() == SkillType.圣德同伴) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2,
                        "大主教", "幻术舞姬");
            } else if (skillUseInfo.getType() == SkillType.乱世红颜) {
                SummonMultiple.apply(this, skillUseInfo, attacker, SummonType.Normal, 5,
                        "肉林");
            } else if (skillUseInfo.getType() == SkillType.召唤小黑) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 1,
                        "暗黑游侠");
            } else if (skillUseInfo.getType() == SkillType.连营) {
                Summon.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, SummonType.Normal, 2, "炮灰", "炮灰");
                MagicMark.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.魔力法阵) {
                MagicMark.apply(this, skillUseInfo, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.荣耀降临) {
                MagicMark.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.魔力印记) {
                MagicMark.apply(this, skillUseInfo, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.东风 || skillUseInfo.getType() == SkillType.酩酊 || skillUseInfo.getType() == SkillType.灵力魔阵) {
                MagicMark.apply(this, skillUseInfo, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.腐蚀术) {
                MagicMark.apply(this, skillUseInfo, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.致盲) {
                Blind.apply(this, skillUseInfo, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.闪光弹) {
                Blind.apply(this, skillUseInfo, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.爆震弹) {
                FireMagic.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, -1);
                Blind.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.动能追加 || skillUseInfo.getType() == SkillType.彼岸冥灵) {
                EnergyIncrement.apply(skillUseInfo, this, attacker);
            } else if (skillUseInfo.getType() == SkillType.祈福 || skillUseInfo.getType() == SkillType.王母挥袂
                    || skillUseInfo.getType() == SkillType.真理导言 || skillUseInfo.getType() == SkillType.神性祈祷) {
                Bless.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.闭月) {
                Bless.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.修罗地火攻 || skillUseInfo.getType() == SkillType.火攻 || skillUseInfo.getType() == SkillType.地狱烈火) {
                SuraFire.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.炎敷) {
                SuraFire.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, defender);
                Trap.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.天照) {
                SuraFire.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, defender);
                BurningFlame.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.淬毒手里剑) {
                HandSword.apply(this, skillUseInfo, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.望月杀阵) {
                HandSword.apply(this, skillUseInfo, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.熔魂之刃) {
                HandSword.apply(this, skillUseInfo, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.火烧连营 || skillUseInfo.getType() == SkillType.彻骨霜火) {
                ContinuousFire.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.化学风暴 || skillUseInfo.getType() == SkillType.生化风暴) {
                ChemicalRage.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.精神狂乱) {
                Insane.apply(skillUseInfo, this, attacker, defender, 1, 100);
            } else if (skillUseInfo.getType() == SkillType.离间) {
                Insane.apply(skillUseInfo, this, attacker, defender, 3, 100);
            } else if (skillUseInfo.getType() == SkillType.骚乱) {
                Insane.apply(skillUseInfo, this, attacker, defender, 3, 150);
            } else if (skillUseInfo.getType() == SkillType.癫狂之舞) {
                Insane.apply(skillUseInfo, this, attacker, defender, 1, 150);
            } else if (skillUseInfo.getType() == SkillType.怨魂附身) {
                Insane.apply(skillUseInfo, this, attacker, defender, 2, 200);
            } else if (skillUseInfo.getType() == SkillType.精神污染) {
                Insane.apply(skillUseInfo, this, attacker, defender, 3, 0);
            } else if (skillUseInfo.getType() == SkillType.原素之舞) {
                Insane.apply(skillUseInfo, this, attacker, defender, 5, 70);
            } else if (skillUseInfo.getType() == SkillType.醉酒狂暴) {
                Insane.apply(skillUseInfo, this, attacker, defender, 5, 200);
            } else if (skillUseInfo.getType() == SkillType.无尽华尔兹) {
                Insane.apply(skillUseInfo, this, attacker, defender, -1, 100);
            } else if (skillUseInfo.getType() == SkillType.圣洁魅惑) {
                Insane.apply(skillUseInfo, this, attacker, defender, 3, 50);
            } else if (skillUseInfo.getType() == SkillType.霓裳羽衣舞) {
                Insane.apply(skillUseInfo, this, attacker, defender, 7, 150);
            } else if (skillUseInfo.getType() == SkillType.天怒) {
                FireMagic.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, -1);
                BurningFlame.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.纯质流火 || skillUseInfo.getType() == SkillType.凤凰业火 || skillUseInfo.getType() == SkillType.烈火冲击
                    || skillUseInfo.getType() == SkillType.火焰呼吸) {
                GreatFireMagic.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, -1, true);
                BurningFlame.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.浴火) {
                GreatFireMagic.apply(skillUseInfo.getAttachedUseInfo1().getAttachedUseInfo1().getSkill(), this, attacker, defender, -1, true);
                BurningFlame.apply(skillUseInfo.getAttachedUseInfo1().getAttachedUseInfo2(), this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.业火) {
                HellFire.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.魔龙吐息) {
                HellFire.apply(skillUseInfo, this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.流星火球) {
                HellFire.apply(skillUseInfo, this, attacker, defender, -1);
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
            } else if (skillUseInfo.getType() == SkillType.觉醒极寒) {
                if (defender.getField().getAliveCards().size() >= 3) {
                    IceMagic.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender, -1, 50, 45 * defender.getField().getAliveCards().size());
                }
                if (defender.getField().getAliveCards().size() < 3) {
                    IceTouch.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender, 3);
                }
            } else if (skillUseInfo.getType() == SkillType.页游极寒冲击) {
                IceTouch.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.王牌狙击) {
                if (attacker.getOwner().getHP() >= attacker.getOwner().getMaxHP() * 0.7) {
                    Snipe.apply(skillUseInfo.getAttachedUseInfo1(), skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, 1);
                }
                if (attacker.getOwner().getHP() < attacker.getOwner().getMaxHP() * 0.7) {
                    Snipe.apply(skillUseInfo.getAttachedUseInfo2(), skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 1);
                }
            } else if (skillUseInfo.getType() == SkillType.觉醒继志) {
                if (defender.getField().getAliveCards().size() >= 5) {
                    Destroy.apply(this, skillUseInfo.getAttachedUseInfo1().getSkill(), attacker, defender, 1);
                }
                if (defender.getField().getAliveCards().size() < 5) {
                    Soften.apply(skillUseInfo, this, attacker, defender, -1);
                }
            } else if (skillUseInfo.getType() == SkillType.觉醒狼顾 || skillUseInfo.getType() == SkillType.觉醒雷狱) {
                if (defender.getField().getAliveCards().size() >= 5) {
                    LighteningMagic.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender, -1, 75);
                }
                if (defender.getField().getAliveCards().size() < 5) {
                    ThunderStrike.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender, 3);
                }
            } else if (skillUseInfo.getType() == SkillType.觉醒圣光惩戒) {
                if (defender.getField().getAliveCards().size() >= 5) {
                    LighteningMagic.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender, -1, 75);
                }
                if (defender.getField().getAliveCards().size() < 5) {
                    Bless.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker);
                }
            } else if (skillUseInfo.getType() == SkillType.觉醒异端审判) {
                if (defender.getField().getAliveCards().size() >= 5) {
                    SoulCrash.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender);
                }
                if (defender.getField().getAliveCards().size() < 5) {
                    Snipe.apply(skillUseInfo, skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 1);
                }
            } else if (skillUseInfo.getType() == SkillType.觉醒原素之舞) {
                if (defender.getField().getAliveCards().size() >= 5) {
                    ThunderStrike.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender, -1);
                }
                if (defender.getField().getAliveCards().size() < 5) {
                    Snipe.apply(skillUseInfo, skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 3);
                }
            } else if (skillUseInfo.getType() == SkillType.原素共鸣) {
                ResonantElements.apply(this, skillUseInfo, attacker, "原素曜灵");
            } else if (skillUseInfo.getType() == SkillType.生物进化) {
                Evolution.apply(this, skillUseInfo, attacker, "进化材料", "科学家变异");
            } else if (skillUseInfo.getType() == SkillType.精神补完) {
                Evolution.apply(this, skillUseInfo, attacker, "进化材料", "科学家进化");
            } else if (skillUseInfo.getType() == SkillType.暗之献祭) {
                Deformation.apply(this, skillUseInfo, attacker, "真幻镜魔导");
            } else if (skillUseInfo.getType() == SkillType.暗之归还) {
                Deformation.apply(this, skillUseInfo, attacker, "幻镜魔导");
            } else if (skillUseInfo.getType() == SkillType.银色之棘毁灭) {
                Deformation.apply(this, skillUseInfo, attacker, "银色之棘");
            } else if (skillUseInfo.getType() == SkillType.银色之棘守护) {
                Deformation.apply(this, skillUseInfo, attacker, "毁灭之棘");
            } else if (skillUseInfo.getType() == SkillType.地裂) {
                GiantEarthquakesLandslides.apply(this, skillUseInfo.getSkill(), attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.觉醒天崩地裂) {
                GiantEarthquakesLandslides.apply(this, skillUseInfo.getAttachedUseInfo1().getSkill(), attacker, defender, 3);
                ManaErode.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.寒冰触碰) {
                IceTouch.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.风暴汇集) {
                IceTouch.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.冰之魔枪) {
                IceTouch.apply(skillUseInfo, this, attacker, defender, 5);
            } else if (skillUseInfo.getType() == SkillType.寒霜拳) {
                IceTouch.apply(skillUseInfo, this, attacker, defender, 1);
            } else if (skillUseInfo.getType() == SkillType.魔力碎片) {
                IceTouch.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.漫天风雪) {
                IceMagic.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender, -1, 50, 45 * defender.getField().getAliveCards().size());
                IceTouch.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.雷霆一击) {
                ThunderStrike.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.妖力侵蚀) {
                ThunderStrike.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.雷公助我) {
                ThunderStrike.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.薜荔之怒) {
                ThunderStrike.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.雷霆之怒) {
                ThunderStrike.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.赤之魔枪 || skillUseInfo.getType() == SkillType.灵能启迪) {
                RedGun.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.灵能爆发) {
                RedGun.apply(skillUseInfo, this, attacker, defender, -1);
            } else if (skillUseInfo.getType() == SkillType.全垒打) {
                RedGun.apply(skillUseInfo, this, attacker, defender, 4);
            } else if (skillUseInfo.getType() == SkillType.雷切) {
                ThunderStrike.apply(skillUseInfo, this, attacker, defender, 3);
            } else if (skillUseInfo.getType() == SkillType.麻痹药剂) {
                ThunderStrike.apply(skillUseInfo, this, attacker, defender, 2);
            } else if (skillUseInfo.getType() == SkillType.王佐之才) {
                HandCardAddTwoSkill.apply(this, skillUseInfo, attacker, skillUseInfo.getAttachedUseInfo1().getSkill());
            } else if (skillUseInfo.getType() == SkillType.你们来啊 || skillUseInfo.getType() == SkillType.你们上啊 || skillUseInfo.getType() == SkillType.还有谁) {
                HandCardAddTwoSkillOpponent.apply(this, skillUseInfo, attacker, skillUseInfo.getAttachedUseInfo1().getSkill(), defender);
            } else if (skillUseInfo.getType() == SkillType.抗魔石肤) {
                HandCardAddOneSkill.apply(this, skillUseInfo, attacker, skillUseInfo.getAttachedUseInfo1().getSkill());
            } else if (skillUseInfo.getType() == SkillType.肾上腺素) {
                HandCardAddOneSkill.apply(this, skillUseInfo, attacker, skillUseInfo.getAttachedUseInfo1().getSkill());
            } else if (skillUseInfo.getType() == SkillType.愈音) {
                HandCardAddSkillNormal.apply(this, skillUseInfo, attacker, skillUseInfo.getAttachedUseInfo1().getSkill(), 1);
            } else if (skillUseInfo.getType() == SkillType.敏助) {
                HandCardAddSkillNormal.apply(this, skillUseInfo, attacker, skillUseInfo.getAttachedUseInfo1().getSkill(), 1);
            } else if (skillUseInfo.getType() == SkillType.亚平宁之蓝 || skillUseInfo.getType() == SkillType.荣誉之地 || skillUseInfo.getType() == SkillType.花酿
                    || skillUseInfo.getType() == SkillType.页游生命之杯 || skillUseInfo.getType() == SkillType.复苏药剂 || skillUseInfo.getType() == SkillType.无上荣耀) {
                HandCardAddSkillNormal.apply(this, skillUseInfo, attacker, skillUseInfo.getAttachedUseInfo1().getSkill(), 1);
            } else if (skillUseInfo.getType() == SkillType.偷偷削弱) {
                HandCardBuff.apply(this, skillUseInfo, attacker, SkillEffectType.MAXHP_CHANGE, 1);
            } else if (skillUseInfo.getType() == SkillType.新卡作成 || skillUseInfo.getType() == SkillType.卡牌作废) {
                AddSkillOpponent.apply(this, skillUseInfo, attacker, skillUseInfo.getAttachedUseInfo1().getSkill(), 1, defender);
            } else if (skillUseInfo.getType() == SkillType.冰巨人吞噬) {
                Erode.apply(this, skillUseInfo, attacker, defender, null,false);
            } else if (skillUseInfo.getType() == SkillType.天召) {
                DivineSummon.apply(skillUseInfo, this, attacker);
            } else if (skillUseInfo.getType() == SkillType.绝对压制) {
                Polymorph.apply(this, skillUseInfo, attacker, defender, 1, 1);
            } else if (skillUseInfo.getType() == SkillType.末日降临) {
                Polymorph.apply(this, skillUseInfo, attacker, defender, -1, 1);
            } else if (skillUseInfo.getType() == SkillType.终极天谴) {
                Curse.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.终极祈祷) {
                Pray.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.再生) {
                Bless.apply(skillUseInfo.getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.终焉时刻) {
                Curse.apply(this, skillUseInfo.getSkill(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.弑主) {
                CounterBite.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker);
                TheSword.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker);
            } else if (skillUseInfo.getType() == SkillType.士气振奋) {
                TheSword.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.魂之枷锁) {
                SoulChains.apply(this, skillUseInfo, attacker, defender, 5, 4);
            } else if (skillUseInfo.getType() == SkillType.血魂之咒) {
                SoulChains.apply(this, skillUseInfo, attacker, defender, 3, 3);
            } else if (skillUseInfo.getType() == SkillType.离魂芳印) {
                Rapture.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.天使降临) {
                if (defender.getField().getAliveCards().size() >= 4) {
                    SoulCrash.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender);
                }
                if (defender.getField().getAliveCards().size() < 4) {
                    ReturnToHandAndDelay.apply(this, skillUseInfo.getAttachedUseInfo2().getSkill(), attacker, defender, 1, 1);
                }
            } else if (skillUseInfo.getType() == SkillType.神圣放逐) {
                ReturnToHandAndDelay.apply(this, skillUseInfo.getSkill(), attacker, defender, 1, 1);
            } else if (skillUseInfo.getType() == SkillType.逆羽罡风) {
                RegressionSoul.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.原素召唤) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 2, "网页版原素侍卫", "网页版原素将军");
            } else if (skillUseInfo.getType() == SkillType.小飞侠) {
                Supplication.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, defender);
                AllSpeedUp.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.生命之杯) {
                Bless.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker);
                LunaTouch.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.吞噬) {
                DevourMultiple.apply(this, skillUseInfo, attacker, defender, 2);
            } else if (skillUseInfo.getType() == SkillType.山崩) {
                Crumbling.apply(this, skillUseInfo.getSkill(), attacker, defender, 1, 1);
            } else if (skillUseInfo.getType() == SkillType.咒怨) {
                Grudge.apply(this, skillUseInfo, attacker, defender, 2);
            } else if (skillUseInfo.getType() == SkillType.帝国光辉) {
                Bless.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker);
                Rainfall.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.混乱之源) {
                SummonOpponent.apply(this, skillUseInfo, attacker, SummonType.Normal, 3, "混沌体", "混沌体", "混沌体");
            } else if (skillUseInfo.getType() == SkillType.地狱烈焰) {
                HeavenWrath.apply(this, skillUseInfo.getAttachedUseInfo1().getSkill(), attacker, defender);
                SuraFire.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.星座能量控制) {
                SuraFire.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, defender);
                IceMagic.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender, -1, 90, 0);
            } else if (skillUseInfo.getType() == SkillType.星座能量神秘) {
                EnergyIncrement.apply(skillUseInfo, this, attacker);
                SoulCrash.apply(skillUseInfo, this, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.半数沉默 || skillUseInfo.getType() == SkillType.地煞倾覆) {
                HalfSilence.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.锁魂) {
                SoulChains.apply(this, skillUseInfo, attacker, defender, 5, 1);
            } else if (skillUseInfo.getType() == SkillType.二重大灵轰) {
                ManaErode.apply(skillUseInfo.getSkill(), this, attacker, defender, 2);
            } else if (skillUseInfo.getType() == SkillType.海滨大作战) {
                SoulChains.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, defender, 5, 1);
                ManaErode.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker, defender, 2);
            } else if (skillUseInfo.getType() == SkillType.冲浪集结) {
                Horn.apply(skillUseInfo, this, attacker);
            } else if (skillUseInfo.getType() == SkillType.同调) {
                Homology.apply(this, skillUseInfo, attacker,"八岐大蛇");
            } else if (skillUseInfo.getType() == SkillType.月神祈福) {
                Bless.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker);
                LunaBless.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.终焉之兆) {
                ReturnToHandAndDelay.apply(this, skillUseInfo.getSkill(), attacker, defender, 2, 1);
                Summon.apply(this, skillUseInfo, attacker, SummonType.Normal, 1, "终焉使魔");
            } else if (skillUseInfo.getType() == SkillType.月之召唤) {
                SummonWhenAttack.apply(this, skillUseInfo, attacker, 1,false, "暗月");
            } else if (skillUseInfo.getType() == SkillType.逐光 || skillUseInfo.getType() == SkillType.杀手回梦) {
                ReturnToHandAndDelay.apply(this, skillUseInfo.getSkill(), attacker, defender, 2, 2);
            } else if (skillUseInfo.getType() == SkillType.特殊体质) {
                Summon.apply(this, skillUseInfo, attacker, SummonType.Random, 1, "真·预言之神","谎言之神","炎魔",
                        "大毒汁怪","大毒汁之王","巨大毒汁怪","死亡天使");
            } else if (skillUseInfo.getType() == SkillType.星座能量热情) {
                Bless.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker);
            } else if (skillUseInfo.getType() == SkillType.觉醒月神降临) {
                if (attacker.getOwner().getField().getAliveCards().size() >= 5) {
                    LunaBless.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker);
                }
                if (attacker.getOwner().getField().getAliveCards().size() < 5) {
                    Bless.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, attacker);
                }
            } else if (skillUseInfo.getType() == SkillType.月之暗面) {
                SummonWhenAttack.apply(this, skillUseInfo, attacker, 1,false, "网页版暗之月蚀");
            } else if (skillUseInfo.getType() == SkillType.魔王降临) {
                RaceChangeSelf.apply(this, skillUseInfo, attacker);
            } else if (skillUseInfo.getType() == SkillType.嗜血潜能) {
                Erode.apply(this, skillUseInfo.getAttachedUseInfo1(), attacker, defender, null,false);
            }
        }
        if ((attacker.containsAllSkill(SkillType.连续魔法) || attacker.containsAllSkill(SkillType.黄天当立) || attacker.containsAllSkill(SkillType.连奏) || attacker.containsAllSkill(SkillType.神性爆发) || attacker.containsAllSkill(SkillType.时光迁跃) || attacker.containsAllSkill(SkillType.我们生命中的时光)) && !attacker.isDead() && status == 0) {
            for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
                if (skillUseInfo.getType() == SkillType.连续魔法 || skillUseInfo.getType() == SkillType.黄天当立 || skillUseInfo.getType() == SkillType.连奏 || skillUseInfo.getType() == SkillType.神性爆发 || skillUseInfo.getType() == SkillType.时光迁跃) {
                    ContinuousMagic.apply(this, skillUseInfo, attacker, defender);
                    break;
                }
            }
        }
        if ((attacker.containsAllSkill(SkillType.莫测)) && !attacker.isDead() && status == 0) {
            for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
                if (skillUseInfo.getType() == SkillType.莫测) {
                    ContinuousMagic.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, defender);
                    break;
                }
            }
        }
        if (!attacker.isDead() && !attacker.isSilent() && !attacker.justRevived()) {
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.飞岩);
                if (rune != null) {
                    Snipe.apply(rune.getSkillUseInfo(), rune.getSkill(), this, attacker, defender, 1);
                }
            }
        }
        resolveDebuff(attacker, CardStatusType.咒怨);
        resolveAddATDebuff(attacker, CardStatusType.咒怨);
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
                } else if (skillUseInfo.getType() == SkillType.大地之盾 || skillUseInfo.getType() == SkillType.寒冰之盾 || skillUseInfo.getType() == SkillType.禁区之王
                        || skillUseInfo.getType() == SkillType.清泉之盾 || skillUseInfo.getType() == SkillType.大地庇护) {
                    EarthShield.apply(skillUseInfo, this, attacker, defender);
                } else if (skillUseInfo.getType() == SkillType.物理反弹 || skillUseInfo.getType() == SkillType.武形破剑击 || skillUseInfo.getType() == SkillType.反击屏障
                        || skillUseInfo.getType() == SkillType.星座能量直感 || skillUseInfo.getType() == SkillType.应激) {
                    PhysicalReflection.apply(skillUseInfo.getSkill(), this, attacker, defender, damagedResult.actualDamage);
                } else if (skillUseInfo.getType() == SkillType.天丛云) {
                    PhysicalReflection.apply(skillUseInfo.getAttachedUseInfo1().getSkill(), this, attacker, defender, damagedResult.actualDamage);
                } else if (skillUseInfo.getType() == SkillType.一闪) {
                    EarthShield.apply(skillUseInfo, this, attacker, defender);
                    PhysicalReflection.apply(skillUseInfo.getSkill(), this, attacker, defender, damagedResult.actualDamage);
                } else if (skillUseInfo.getType() == SkillType.魔神之甲) {
                    Spike.apply(skillUseInfo.getSkill(), this, attacker, defender, attackSkill, result.getDamage());
                } else if (skillUseInfo.getType() == SkillType.燃烧) {
                    Burning.apply(skillUseInfo, this, attacker, defender);
                } else if (skillUseInfo.getType() == SkillType.护身烈焰) {
                    Burning.apply(skillUseInfo, this, attacker, defender);
                } else if (skillUseInfo.getType() == SkillType.烈焰之肤) {
                    Burning.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender);
                } else if (skillUseInfo.getType() == SkillType.邪灵汲取) {
                    EnergyDrain.apply(skillUseInfo, this, attacker, defender, result, damagedResult);
                } else if (skillUseInfo.getType() == SkillType.恶灵汲取) {
                    LifeDrain.apply(skillUseInfo, this, attacker, defender, result, damagedResult);
                } else if (skillUseInfo.getType() == SkillType.星座能量坚韧) {
                    EnergyDrain.apply(skillUseInfo.getAttachedUseInfo1(), this, attacker, defender, result, damagedResult);
                    LifeDrain.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender, result, damagedResult);
                } else if (skillUseInfo.getType() == SkillType.肉食者) {
                    LifeDrain.apply(skillUseInfo.getAttachedUseInfo2(), this, attacker, defender, result, damagedResult);
                } else if (skillUseInfo.getType() == SkillType.不灭原核) {
                    EnergyDrain.apply(skillUseInfo, this, attacker, defender, result, damagedResult);
                } else if (skillUseInfo.getType() == SkillType.被插出五星) {
                    CounterSummon.apply(this, defender, skillUseInfo.getSkill(), 5);
                } else if (skillUseInfo.getType() == SkillType.反射装甲 || skillUseInfo.getType() == SkillType.魔神加护) {
                    ReflectionArmor.apply(skillUseInfo.getSkill(), this, attacker, defender, attackSkill, damagedResult.actualDamage);
                } else if (skillUseInfo.getType() == SkillType.LETITGO || skillUseInfo.getType() == SkillType.击溃 || skillUseInfo.getType() == SkillType.高位逼抢) {
                    ReflectionArmor.apply(skillUseInfo.getSkill().getAttachedSkill2(), this, attacker, defender, attackSkill, damagedResult.actualDamage);
                }
            }
            if (!defender.isSilent() && !defender.justRevived()) {
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.雷盾);
                    if (rune != null && defender.getRuneActive()) {
                        Spike.apply(rune.getSkill(), this, attacker, defender, attackSkill, result.getDamage());
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.漩涡);
                    if (rune != null && defender.getRuneActive()) {
                        CounterAttack.apply(rune.getSkill(), this, attacker, defender, result.getDamage());
                    }
                }
                {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.升阳);
                    if (rune != null && !defender.justRevived()) {
                        ReflectionArmor.apply(rune.getSkill(), this, attacker, defender, attackSkill, damagedResult.actualDamage);
                    }
                }
                if (!defender.isDead()) {
                    for (SkillUseInfo skillUseInfo : defender.getUsableNormalSkills()) {
                        if (skillUseInfo.getType() == SkillType.狂热 || skillUseInfo.getType() == SkillType.狂热之血 || skillUseInfo.getType() == SkillType.兽人之血) {
                            Zealot.apply(skillUseInfo, this, attacker, defender, result);
                        }
                    }
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.怒涛);
                    if (rune != null && defender.getRuneActive()) {
                        Zealot.apply(rune.getSkillUseInfo(), this, attacker, defender, result);
                    }
                }
            }
            for (SkillUseInfo skillUseInfo : defender.getUsableNormalSkills()) {
                if (skillUseInfo.getType() == SkillType.逃跑 || skillUseInfo.getType() == SkillType.强链原核 || skillUseInfo.getType() == SkillType.撤退) {
                    Flee.apply(skillUseInfo.getSkill(), this, attacker, defender, damagedResult.actualDamage);
                }
            }
        }
    }

    //返回int类型，0表示不反弹，1表述反弹并且不受伤害，2表示反弹受伤害
    public int resolveMagicEchoSkill(EntityInfo attacter, CardInfo defender, Skill cardSkill) {
        if (defender.containsAllSkill(SkillType.奥术回声)) {
            if (attacter instanceof CardInfo) {
                CardInfo attacterCard = (CardInfo) attacter;
                if (attacterCard.containsAllSkill(SkillType.奥术回声)) {
                    return 0;
                }
                //魔族不在结算
//                if (attacterCard.isDeman() || attacterCard.isBoss()) {
//                    for (SkillUseInfo defenderSkillInfo : defender.getAllUsableSkillsInvalidSilence()) {
//                        if (defenderSkillInfo.getType() == SkillType.奥术回声) {
//                            stage.getUI().useSkill(defender, defenderSkillInfo.getSkill(), true);
//                            break;
//                        }
//                    }
//                    return 2;
//                }
                //雷系灵王不再结算
//                if (cardSkill.getType().containsTag(SkillTag.雷系灵轰)) {
//                    //暂时的处理雷系和魔族为2的处理
//                    for (SkillUseInfo defenderSkillInfo : defender.getAllUsableSkillsInvalidSilence()) {
//                        if (defenderSkillInfo.getType() == SkillType.奥术回声) {
//                            stage.getUI().useSkill(defender, defenderSkillInfo.getSkill(), true);
//                            break;
//                        }
//                    }
//                    return 2;
//                }
                for (SkillUseInfo defenderSkillInfo : defender.getAllUsableSkillsInvalidSilence()) {
                    if (defenderSkillInfo.getType() == SkillType.奥术回声) {
                        stage.getUI().useSkill(defender, defenderSkillInfo.getSkill(), true);
                        break;
                    }
                }
                return 1;
            } else {
                for (SkillUseInfo defenderSkillInfo : defender.getAllUsableSkillsInvalidSilence()) {
                    if (defenderSkillInfo.getType() == SkillType.奥术回声) {
                        stage.getUI().useSkill(defender, defenderSkillInfo.getSkill(), true);
                        break;
                    }
                }
                return 1;
            }
        }
        return 0;
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
                    if (blockSkillUseInfo.getType() == SkillType.闪避 || blockSkillUseInfo.getType() == SkillType.龙胆 || blockSkillUseInfo.getType() == SkillType.直感 || blockSkillUseInfo.getType() == SkillType.敏捷 || blockSkillUseInfo.getType() == SkillType.隐蔽 || blockSkillUseInfo.getType() == SkillType.页游屏息) {
                        result.setAttackable(!Dodge.apply(blockSkillUseInfo.getSkill(), this, cardAttacker, defender, result.getDamage()));
                        if (!result.isAttackable()) {
                            return result;
                        }
                    } else if (blockSkillUseInfo.getType() == SkillType.隐匿) {
                        result.setAttackable(!Dodge.apply(blockSkillUseInfo.getAttachedUseInfo1().getSkill(), this, cardAttacker, defender, result.getDamage()));
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
                        if (blockSkillUseInfo.getType() == SkillType.圣盾 || blockSkillUseInfo.getType() == SkillType.光之守护) {
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
                    } else if (
                            blockSkillUseInfo.getType() == SkillType.酒意) {
                        result.setDamage(IceArmor.apply(blockSkillUseInfo.getAttachedUseInfo1().getSkill(), this, cardAttacker, defender,
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
                if (blockSkillUseInfo.getType() == SkillType.骑士守护 || blockSkillUseInfo.getType() == SkillType.骑士荣耀 || blockSkillUseInfo.getType() == SkillType.骑士信仰) {
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
            boolean isAttackerDisabled = (status.containsStatus(CardStatusType.冰冻)
                    || status.containsStatus(CardStatusType.锁定)
                    || status.containsStatus(CardStatusType.复活));
            if (!attackSkill.isDeathSkill() && isAttackerDisabled && attackSkill.getType() != SkillType.邪灵汲取) {
                // BUGBUG: Why we need go here? Hack 邪灵汲取 here temporarily.
                result.setAttackable(false);
                //死亡的卡牌魔法技能正常发动
                if (attacker instanceof CardInfo) {
                    if (((CardInfo) attacker).isDead()) {
                        result.setAttackable(true);
                    } else if (((CardInfo) attacker).getIsSummon()) {
                        result.setAttackable(true);
                    }
                }
                if (!result.isAttackable()) {
                    stage.getUI().attackBlocked(attacker, defender, attackSkill, null);
                }
            }
            if (result.isAttackable()) {
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
                    if (blockSkillUseInfo.getType() == SkillType.免疫 || blockSkillUseInfo.getType() == SkillType.结界立场
                            || blockSkillUseInfo.getType() == SkillType.影青龙 || blockSkillUseInfo.getType() == SkillType.龙战于野 || blockSkillUseInfo.getType() == SkillType.魔力泳圈
                            || blockSkillUseInfo.getType() == SkillType.禁区之王 || blockSkillUseInfo.getType() == SkillType.恶龙血脉 || blockSkillUseInfo.getType() == SkillType.不息神盾
                            || blockSkillUseInfo.getType() == SkillType.彻骨之寒 || blockSkillUseInfo.getType() == SkillType.灵能冲击 || blockSkillUseInfo.getType() == SkillType.嗜魔之体
                            || blockSkillUseInfo.getType() == SkillType.魔力抗性 || blockSkillUseInfo.getType() == SkillType.轮回渡厄 || blockSkillUseInfo.getType() == SkillType.明月渡我
                            || blockSkillUseInfo.getType() == SkillType.免疫风行 || blockSkillUseInfo.getType() == SkillType.优雅之姿) {
                        if (Immue.isSkillBlocked(this, blockSkillUseInfo.getSkill(), attackSkill, attacker, defender)) {
                            result.setAttackable(false);
                            return result;
                        }
                    }
                    // 神威既包含脱困又包含不动，还有技能既包含不动又抗沉默的，所以需要将if分开
                    if (blockSkillUseInfo.getType() == SkillType.脱困 ||
                            blockSkillUseInfo.getType() == SkillType.神威 ||
                            blockSkillUseInfo.getType() == SkillType.村正 ||
                            blockSkillUseInfo.getType() == SkillType.敏捷 ||
                            blockSkillUseInfo.getType() == SkillType.月之守望 ||
                            blockSkillUseInfo.getType() == SkillType.紫气东来 ||
                            blockSkillUseInfo.getType() == SkillType.冰神附体 ||
                            blockSkillUseInfo.getType() == SkillType.以逸待劳 ||
                            blockSkillUseInfo.getType() == SkillType.不灭原核 ||
                            blockSkillUseInfo.getType() == SkillType.黄天当立 ||
                            blockSkillUseInfo.getType() == SkillType.时光迁跃 ||
                            blockSkillUseInfo.getType() == SkillType.骑士信仰 ||
                            blockSkillUseInfo.getType() == SkillType.灵力魔阵 ||
                            blockSkillUseInfo.getType() == SkillType.破阵弧光 ||
                            blockSkillUseInfo.getType() == SkillType.隐蔽 ||
                            blockSkillUseInfo.getType() == SkillType.女武神之辉 ||
                            blockSkillUseInfo.getType() == SkillType.无冕之王 ||
                            blockSkillUseInfo.getType() == SkillType.再生金蝉 ||
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
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.明镜);
                    if (blockSkillUseInfo.getType().containsTag(SkillTag.抗沉默) || rune != null && !defender.isSilent()) {
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
                        if (rune != null && !defender.justRevived() && defender.isAlive()) {
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
                        if (extraDamage > maxDamage) {
                            maxDamage = extraDamage;
                        }
                    }
                    result.setDamage(damage + maxDamage);
                }
                for (SkillUseInfo blockSkillUseInfo : defender.getUsableNormalSkills()) {
                    if (blockSkillUseInfo.getType() == SkillType.魔甲 ||
                            blockSkillUseInfo.getType() == SkillType.铁骨衣 ||
                            blockSkillUseInfo.getType() == SkillType.神魔之甲 ||
                            blockSkillUseInfo.getType() == SkillType.体态丰盈 ||
                            blockSkillUseInfo.getType() == SkillType.却魔装甲 ||
                            blockSkillUseInfo.getType() == SkillType.魔力抗性) {
                        result.setDamage(MagicShield.apply(this, blockSkillUseInfo.getSkill(), attacker, defender,
                                attackSkill, result.getDamage()));
                    } else if (blockSkillUseInfo.getType() == SkillType.护体石肤 || blockSkillUseInfo.getType() == SkillType.波涛护甲) {
                        result.setDamage(MagicShield.apply(this, blockSkillUseInfo.getAttachedUseInfo1().getSkill(), attacker, defender,
                                attackSkill, result.getDamage()));
                    } else if (blockSkillUseInfo.getType() == SkillType.骑士守护 || blockSkillUseInfo.getType() == SkillType.骑士荣耀 || blockSkillUseInfo.getType() == SkillType.骑士信仰) {
                        result.setDamage(KnightGuardian.apply(this, blockSkillUseInfo.getSkill(), attacker, defender,
                                attackSkill, result.getDamage()));
                    } else if (blockSkillUseInfo.getType() == SkillType.魔法装甲 || blockSkillUseInfo.getType() == SkillType.金魔装甲) {
                        result.setDamage(MagicArmor.apply(this, blockSkillUseInfo.getSkill(), attacker, defender,
                                attackSkill, result.getDamage()));
                    }
                    if (!result.isAttackable()) {
                        return result;
                    }
                }
                if (!defender.isSilent()) {
                    RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.炎甲);
                    if (rune != null && defender.getRuneActive() && !defender.isSilent()) {
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
            if (rune != null && defender.getRuneActive()) {
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
                } else if (
                        blockSkillUseInfo.getType() == SkillType.酒意) {
                    result.setDamage(Block.apply(blockSkillUseInfo.getAttachedUseInfo1().getSkill(), this, cardAttacker, defender,
                            defender, result.getDamage()));
                } else if (blockSkillUseInfo.getType() == SkillType.神亭酣战 || blockSkillUseInfo.getType() == SkillType.烈焰之肤) {
                    result.setDamage(Block.apply(blockSkillUseInfo.getAttachedUseInfo2().getSkill(), this, cardAttacker, defender,
                            defender, result.getDamage()));
                } else if (blockSkillUseInfo.getType() == SkillType.金属装甲 || blockSkillUseInfo.getType() == SkillType.酒池肉林 || blockSkillUseInfo.getType() == SkillType.物理免疫
                        || blockSkillUseInfo.getType() == SkillType.兽人之肤 || blockSkillUseInfo.getType() == SkillType.金魔装甲 || blockSkillUseInfo.getType() == SkillType.金刚护体
                        || blockSkillUseInfo.getType() == SkillType.大地庇护) {
                    result.setDamage(PhysicalArmor.apply(blockSkillUseInfo.getSkill(), this, cardAttacker, defender,
                            result.getDamage()));
                } else if (blockSkillUseInfo.getType() == SkillType.水流护甲 || blockSkillUseInfo.getType() == SkillType.真夏通雨 || blockSkillUseInfo.getType() == SkillType.水流壁
                        || blockSkillUseInfo.getType() == SkillType.传承黯影 || blockSkillUseInfo.getType() == SkillType.回光返照 || blockSkillUseInfo.getType() == SkillType.圣泉护身
                        || blockSkillUseInfo.getType() == SkillType.清泉之盾 || blockSkillUseInfo.getType() == SkillType.魔力泳圈 || blockSkillUseInfo.getType() == SkillType.铁骨衣
                        || blockSkillUseInfo.getType() == SkillType.优雅之姿 ) {
                    result.setDamage(WaterArmor.apply(blockSkillUseInfo.getSkill(), this, cardAttacker, defender, result.getDamage()));
                } else if (blockSkillUseInfo.getType() == SkillType.波涛护甲) {
                    result.setDamage(WaterArmor.apply(blockSkillUseInfo.getAttachedUseInfo2().getSkill(), this, cardAttacker, defender, result.getDamage()));
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
        if (deadCard.isDead()) {
            deadCard.setIsDeathNow(true);
        }

        Player opponent = this.getStage().getOpponent(deadCard.getOwner());
        //位置調整,处理复合型结算时结算错误

        //处理羽扇虎拳
        if (deadCard.isDead()) {
            for (CardInfo attackFiled : opponent.getField().getAliveCards()) {
                if (attackFiled.containsUsableSkill(SkillType.羽扇虎拳)) {
                    for (SkillUseInfo skillUseInfo : attackFiled.getUsableNormalSkills()) {
                        if (skillUseInfo.getType() == SkillType.羽扇虎拳) {
                            PercentagAttackHero.apply(this, skillUseInfo.getSkill(), attackFiled, deadCard);
                        }
                    }
                }
            }
        }

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
                Rainfall.apply(deadCardSkillUseInfo.getSkill(), this, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.月神的护佑) {
                LunaBless.apply(deadCardSkillUseInfo.getSkill(), this, deadCard);
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
                RegressionSoul.apply(this, deadCardSkillUseInfo, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.时光倒流) {
                TimeBack.apply(deadCardSkillUseInfo, this, deadCard.getOwner(), opponent);
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
            } else if (deadCardSkillUseInfo.getType() == SkillType.万兽奔腾) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Random, 2,
                        "麒麟兽", "凤凰", "浮云青鸟", "九头妖蛇", "雷兽", "羽翼化蛇", "神谕火狐",
                        "齐天美猴王", "羽蛇神", "月蚀兽", "逐月恶狼", "逐日凶狼", "月之神兽", "山地兽", "逐月恶狼", "圣翼白虎",
                        "炙阳麒麟", "霜月麒麟", "雪峰飞狐", "九尾妖狐", "魔卡策划", "深海怪鱼", "冰霜巨蛙", "冰焰狼"
                );
            } else if (deadCardSkillUseInfo.getType() == SkillType.狂野之怒) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Random, 2,
                        "凤凰", "浮云青鸟", "九头妖蛇", "雷兽", "羽翼化蛇", "神谕火狐",
                        "齐天美猴王", "羽蛇神", "月蚀兽", "逐月恶狼", "逐日凶狼", "月之神兽", "山地兽");
            } else if (deadCardSkillUseInfo.getType() == SkillType.武形降临) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Random, 1,
                        "武形火焰尊者", "武形神射尊者", "武形破拳尊者", "武形剑圣", "武形斗圣");
            } else if (deadCardSkillUseInfo.getType() == SkillType.镜像) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, deadCard.getName());
            } else if (deadCardSkillUseInfo.getType() == SkillType.常夏日光 || deadCardSkillUseInfo.getType() == SkillType.碎裂怒吼) {
                Soften.apply(deadCardSkillUseInfo, this, deadCard, opponent, -1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.灵魂消散) {
                SoulCrash.apply(deadCardSkillUseInfo, this, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.夺魂) {
                SoulControl.apply(this, deadCardSkillUseInfo, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.再生) {
                Bless.apply(deadCardSkillUseInfo.getSkill(), this, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.弱者溃散) {
                ReturnCardAndDelay.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, opponent, 2);
            } else if (deadCardSkillUseInfo.getType() == SkillType.地裂) {
                GiantEarthquakesLandslides.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, opponent, 1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.死无尽华尔兹) {
                Insane.apply(deadCardSkillUseInfo, this, deadCard, opponent, -1, 100);
            } else if (deadCardSkillUseInfo.getType() == SkillType.死全领域沉默) {
                Silence.apply(this, deadCardSkillUseInfo, deadCard, opponent, true, false);
            } else if (deadCardSkillUseInfo.getType() == SkillType.山崩) {
                Crumbling.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, opponent, 1, 1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.全体复活) {
                ReviveMultiple.apply(this, deadCardSkillUseInfo, deadCard);
            }
        }
        for (SkillUseInfo deadCardSkillUseInfo : deadCard.getAllUsableSkills()) {
            // IMPORTANT: Unbending card cannot trigger 自爆
            if (deadCardSkillUseInfo.getType() == SkillType.自爆 && !result.unbending) {
                Explode.apply(this, deadCardSkillUseInfo.getSkill(), killerCard, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.燕返 || deadCardSkillUseInfo.getType() == SkillType.上层精灵的挽歌 || deadCardSkillUseInfo.getType() == SkillType.海滨危机) {
                if (cardSkill != null && (cardSkill.getType() == SkillType.侵蚀 || cardSkill.getType() == SkillType.吞噬
                        || cardSkill.getType() == SkillType.冰巨人吞噬|| cardSkill.getType() == SkillType.嗜血潜能
                        || cardSkill.getType() == SkillType.鬼才 || cardSkill.getType() == SkillType.驱虎吞狼)) {
                    continue;
                }
                TsubameGaeshi.apply(deadCardSkillUseInfo, deadCardSkillUseInfo.getSkill(), this, opponent, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.袈裟斩燕返 && deadCard.isDead()) {
                TsubameGaeshi.apply(deadCardSkillUseInfo, deadCardSkillUseInfo.getSkill(), this, opponent, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.厄运枪狙击 && deadCard.isDead()) {
                Snipe.apply(deadCardSkillUseInfo, deadCardSkillUseInfo.getSkill(), this, deadCard, opponent, 1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.学园骚乱) {
                Insane.apply(deadCardSkillUseInfo, this, deadCard, opponent, -1, 50);
            } else if (deadCardSkillUseInfo.getType() == SkillType.逆命华舞) {
                HandCardAddThreeSkill.apply(this, deadCardSkillUseInfo, deadCard, deadCardSkillUseInfo.getAttachedUseInfo1().getSkill());
            } else if (deadCardSkillUseInfo.getType() == SkillType.公平竞争) {
                CounterBite.apply(deadCardSkillUseInfo, this, deadCard);
                Curse.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.三振出局) {
                GiantEarthquakesLandslides.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, opponent, 1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.格式化) {
                SoulCrash.apply(deadCardSkillUseInfo, this, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.九转秘术) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, "九命猫神·幻影");
            } else if (deadCardSkillUseInfo.getType() == SkillType.九转禁术) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, deadCard.getName());
            } else if (deadCardSkillUseInfo.getType() == SkillType.北海报恩) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, deadCard.getName());
            } else if (deadCardSkillUseInfo.getType() == SkillType.樱蝶重生) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, "网页版樱蝶仙子");
            } else if (deadCardSkillUseInfo.getType() == SkillType.魔化冥蝶) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, "网页版冥蝶妖姬");
            } else if (deadCardSkillUseInfo.getType() == SkillType.月影分身) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 2, deadCard.getName(), deadCard.getName());
            } else if (deadCardSkillUseInfo.getType() == SkillType.寒心恨雪) {
                Summon.apply(this, deadCardSkillUseInfo.getAttachedUseInfo1(), deadCard, SummonType.Normal, 1, deadCard.getName());
            } else if (deadCardSkillUseInfo.getType() == SkillType.我还会回来的) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, "大毒汁之王-5");
            } else if (deadCardSkillUseInfo.getType() == SkillType.栗子军团) {
                RegressionSoul.apply(this, deadCardSkillUseInfo.getAttachedUseInfo1(), deadCard, opponent);
                Summon.apply(this, deadCardSkillUseInfo.getAttachedUseInfo2(), deadCard, SummonType.Normal, 1, deadCard.getName());
            } else if (deadCardSkillUseInfo.getType() == SkillType.蛮荒我还会回来的) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, "蛮荒大毒汁之王-5");
            } else if (deadCardSkillUseInfo.getType() == SkillType.召唤玫瑰剑士) {
                Summon.apply(this, deadCardSkillUseInfo.getAttachedUseInfo2(), deadCard, SummonType.Normal, 1,
                        "玫瑰甜心");
            } else if (deadCardSkillUseInfo.getType() == SkillType.英灵召唤 || deadCardSkillUseInfo.getType() == SkillType.英魂召集) {
                Summon.apply(this, deadCardSkillUseInfo.getAttachedUseInfo2(), deadCard, SummonType.Random, 2,
                        "堕落英魂", "苍穹碧骑", "暗黑游侠", "冥河之主", "天界女武神", "暗影猎手", "湖上骑士");
            } else if (deadCardSkillUseInfo.getType() == SkillType.白帝托孤) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Random, 1,
                        "三国英魂卧龙", "三国英魂汉升", "三国英魂子龙", "三国英魂孟起");
            } else if (deadCardSkillUseInfo.getType() == SkillType.森林的梦幻) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Random, 2,
                        "梦境治愈师", "梦境耳语者", "梦境女神");
            } else if (deadCardSkillUseInfo.getType() == SkillType.铁壁 && !deadCard.getStatus().containsStatus(CardStatusType.不屈)
                    || deadCardSkillUseInfo.getType() == SkillType.金汤 && !deadCard.getStatus().containsStatus(CardStatusType.不屈)
                    || deadCardSkillUseInfo.getType() == SkillType.铁壁方阵 && !deadCard.getStatus().containsStatus(CardStatusType.不屈)
                    || deadCardSkillUseInfo.getType() == SkillType.聚能立场 && !deadCard.getStatus().containsStatus(CardStatusType.不屈)
                    || deadCardSkillUseInfo.getType() == SkillType.光之守护 && !deadCard.getStatus().containsStatus(CardStatusType.不屈)) {
                ImpregnableDefenseHeroBuff.remove(this, deadCardSkillUseInfo, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.驱虎吞狼 && !deadCard.getStatus().containsStatus(CardStatusType.不屈)) {
                ImpregnableDefenseHeroBuff.remove(this, deadCardSkillUseInfo.getAttachedUseInfo2(), deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.魔神加护 && !deadCard.getStatus().containsStatus(CardStatusType.不屈)) {
                ImpregnableDefenseHeroBuff.remove(this, deadCardSkillUseInfo.getAttachedUseInfo2(), deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.生命湍流) {
                Revive.apply(this, deadCardSkillUseInfo, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.大江山鬼王) {
                SoulCrash.apply(deadCardSkillUseInfo, this, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.八卦阵) {
                Seal.apply(deadCardSkillUseInfo, this, deadCard, opponent);
                RegressionSoul.apply(this, deadCardSkillUseInfo, deadCard, opponent);
                HandCardAddSkillNormal.apply(this, deadCardSkillUseInfo, deadCard, deadCardSkillUseInfo.getSkill(), 1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.传响) {
                HandCardAddOneSkill.apply(this, deadCardSkillUseInfo, deadCard, deadCardSkillUseInfo.getAttachedUseInfo1().getSkill());
            } else if (deadCardSkillUseInfo.getType() == SkillType.诀隐) {
                HandCardAddOneSkill.apply(this, deadCardSkillUseInfo, deadCard, deadCardSkillUseInfo.getAttachedUseInfo1().getSkill());
            } else if (deadCardSkillUseInfo.getType() == SkillType.挽歌) {
                Bless.apply(deadCardSkillUseInfo.getSkill(), this, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.自然恩泽) {
                LunaBless.apply(deadCardSkillUseInfo.getSkill(), this, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.恶龙血脉) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, deadCard.getName());
            } else if (deadCardSkillUseInfo.getType() == SkillType.崩坏) {
                Crumbling.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, opponent, 1, 1);
            } else if (deadCardSkillUseInfo.getType() == SkillType.妖力侵蚀) {
                SoulControl.apply(this, deadCardSkillUseInfo.getAttachedUseInfo2(), deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.离魂芳印) {
                Rapture.remove(this, deadCardSkillUseInfo, deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.复仇亡灵) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Random, 1,
                        "网页版摄魂", "网页版贪魔", "网页版夺魄");
            } else if (deadCardSkillUseInfo.getType() == SkillType.化鹏) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1,
                        "网页版赤翼巨鹏");
            } else if (deadCardSkillUseInfo.getType() == SkillType.逆鳞) {
                Snipe.apply(deadCardSkillUseInfo, deadCardSkillUseInfo.getSkill().getAttachedSkill1(), this, deadCard, opponent, -1);
                Snipe.apply(deadCardSkillUseInfo, deadCardSkillUseInfo.getSkill().getAttachedSkill2(), this, deadCard, opponent, 3);
            } else if (deadCardSkillUseInfo.getType() == SkillType.幻化 || deadCardSkillUseInfo.getType() == SkillType.幻影
                    || deadCardSkillUseInfo.getType() == SkillType.日光浴) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, deadCard.getName());
            } else if (deadCardSkillUseInfo.getType() == SkillType.护主) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, deadCard.getName());
                if(!deadCard.getStatus().containsStatus(CardStatusType.不屈)) {
                    ImpregnableDefenseHeroBuff.remove(this, deadCardSkillUseInfo, deadCard);
                }
            } else if (deadCardSkillUseInfo.getType() == SkillType.安魂引) {
                RegressionSoul.apply(this, deadCardSkillUseInfo.getAttachedUseInfo2(), deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.彼岸轮回) {
                Resurrection.apply(this, deadCardSkillUseInfo, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.星座能量掌握) {
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 1, deadCard.getName());
            } else if (deadCardSkillUseInfo.getType() == SkillType.分裂) {
                AddSelfCard.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Summoning, 1,
                        "八岐大蛇");
            } else if (deadCardSkillUseInfo.getType() == SkillType.分裂术) {
                AddSelfCard.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Summoning, 1,
                        "八岐大蛇");
                Bless.apply(deadCardSkillUseInfo.getAttachedUseInfo1().getSkill(), this, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.元素分离) {
                SoulCrash.apply(deadCardSkillUseInfo, this, deadCard, opponent);
                Summon.apply(this, deadCardSkillUseInfo, deadCard, SummonType.Normal, 3, "风暴熊猫","土熊猫","火熊猫");
            } else if (deadCardSkillUseInfo.getType() == SkillType.终焉脱壳) {
                Curse.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.天丛云) {
                GreatFireMagic.apply(deadCardSkillUseInfo.getAttachedUseInfo2().getSkill(), this, deadCard, opponent, 1, false);
            } else if (deadCardSkillUseInfo.getType() == SkillType.星座能量信念) {
                Revive.apply(this, deadCardSkillUseInfo, deadCard);
            } else if (deadCardSkillUseInfo.getType() == SkillType.余香) {
                SummonWhenAttack.apply(this, deadCardSkillUseInfo, deadCard, 1,false, "网页版红玫瑰");
            } else if (deadCardSkillUseInfo.getType() == SkillType.归隐) {
                Curse.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, opponent);
            } else if (deadCardSkillUseInfo.getType() == SkillType.大地吟咏) {
                Revive.apply(this, deadCardSkillUseInfo, deadCard);
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
            if (rune != null && deadCard.getRuneActive() && !result.unbending) {
                TsubameGaeshi.apply(null, rune.getSkill(), this, opponent, deadCard);
            }
        }

        if (deadCard.getStatus().containsStatus(CardStatusType.死印) && deadCard.isDead()) {
            DeathMark.explode(this, deadCard, result);
        }
        if (deadCard.getStatus().containsStatus(CardStatusType.死咒) && deadCard.isDead()) {
            ControlGhost.explode(this, deadCard, result, "摄魂", "噬血", "贪魔", "夺魄");
        }
        if (deadCard.getStatus().containsStatus(CardStatusType.黄天) && deadCard.isDead()) {
            SkeletonArmy.explode(this, deadCard, result, "战场亡魂", "战场亡灵");
        }
        if (deadCard.getStatus().containsStatus(CardStatusType.炼成) && deadCard.isDead()) {
            HumanRefining.explode(this, deadCard, result, "进化材料");
        }
        if (deadCard.getStatus().containsStatus(CardStatusType.咒怨) && deadCard.isDead()) {
            Grudge.Infected(this, deadCard);
        }

        if (!deadCard.getStatus().containsStatus(CardStatusType.不屈)&& !deadCard.isAlive()) {
            for (SkillUseInfo deadCardSkillUseInfo : deadCard.getAllUsableSkills()) {
                if (deadCardSkillUseInfo.getGiveSkill() == 1) {
                    deadCard.removeSkill(deadCardSkillUseInfo);
                }
            }
            deadCard.setSummonNumber(0);
            deadCard.setAddDelay(0);
            deadCard.setRuneActive(false);
            resolveLeaveSkills(deadCard);
        }
        if (!result.unbending && !deadCard.isAlive()) {
            if (!deadCard.isSummonedMinion()) {
                deadCard.reset();
            }
        }
        if (!result.soulCrushed && !result.soulControlDead && !deadCard.getStatus().containsStatus(CardStatusType.魂殇)) {
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
                        deadCardSkillUseInfo.getType() == SkillType.战术性撤退 ||
                        deadCardSkillUseInfo.getType() == SkillType.凤凰涅盘 ||
                        deadCardSkillUseInfo.getType() == SkillType.诲人不倦 ||
                        deadCardSkillUseInfo.getType() == SkillType.鞠躬尽瘁 ||
                        deadCardSkillUseInfo.getType() == SkillType.心转之术 ||
                        deadCardSkillUseInfo.getType() == SkillType.天选之子 ||
                        deadCardSkillUseInfo.getType() == SkillType.轮回渡厄 ||
                        deadCardSkillUseInfo.getType() == SkillType.明月渡我 ||
                        deadCardSkillUseInfo.getType() == SkillType.生物进化 ||
                        deadCardSkillUseInfo.getType() == SkillType.精神补完 ||
                        deadCardSkillUseInfo.getType() == SkillType.武侯) {
                    if (Reincarnation.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, result.unbending, opponent)) {
                        reincarnated = true;
                        break;
                    }
                } else if (deadCardSkillUseInfo.getType() == SkillType.蝶语) {
                    if (Reincarnation.apply(this, deadCardSkillUseInfo.getAttachedUseInfo2().getSkill(), deadCard, result.unbending, opponent)) {
                        reincarnated = true;
                        break;
                    }
                } else if (deadCardSkillUseInfo.getType() == SkillType.司命 || deadCardSkillUseInfo.getType() == SkillType.不灭定律 || deadCardSkillUseInfo.getType() == SkillType.不灭 || deadCardSkillUseInfo.getType() == SkillType.顽强 || deadCardSkillUseInfo.getType() == SkillType.我又回来了) {
                    if (Reborn.apply(this, deadCardSkillUseInfo, deadCard, result.unbending)) {
                        reincarnated = true;
                        break;
                    }
                } else if (deadCardSkillUseInfo.getType() == SkillType.不灭原质) {
                    Bless.apply(deadCardSkillUseInfo.getAttachedUseInfo1().getSkill(), this, deadCard);
                    LunaBless.apply(deadCardSkillUseInfo.getAttachedUseInfo2().getSkill(), this, deadCard);
                    if (Reborn.apply(this, deadCardSkillUseInfo, deadCard, result.unbending)) {
                        reincarnated = true;
                        break;
                    }
                } else if (deadCardSkillUseInfo.getType() == SkillType.雪幕) {
                    if (Reborn.apply(this, deadCardSkillUseInfo.getAttachedUseInfo1(), deadCard, result.unbending)) {
                        reincarnated = true;
                        break;
                    }
                } else if (deadCardSkillUseInfo.getType() == SkillType.回生 || deadCardSkillUseInfo.getType() == SkillType.不凋花) {
                    if (Retrogradation.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, result.unbending)) {
                        reincarnated = true;
                        break;
                    }
                } else if (deadCardSkillUseInfo.getType() == SkillType.灵魂脱壳) {
                    if (Reborn.apply(this, deadCardSkillUseInfo, deadCard, result.unbending)) {
                        reincarnated = true;
                        break;
                    } else {
                        Retrogradation.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, result.unbending);
                        reincarnated = true;
                        break;
                    }
                } else if (deadCardSkillUseInfo.getType() == SkillType.还魂) {
                    if (Reincarnation.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, result.unbending, opponent)) {
                        reincarnated = true;
                        break;
                    } else {
                        Retrogradation.apply(this, deadCardSkillUseInfo.getSkill(), deadCard, result.unbending);
                        reincarnated = true;
                        break;
                    }
                } else if (deadCardSkillUseInfo.getType() == SkillType.安魂引) {
                    if (Reincarnation.apply(this, deadCardSkillUseInfo.getAttachedUseInfo1().getSkill(), deadCard, result.unbending, opponent)) {
                        reincarnated = true;
                        break;
                    } else {
                        Retrogradation.apply(this, deadCardSkillUseInfo.getAttachedUseInfo1().getSkill(), deadCard, result.unbending);
                        reincarnated = true;
                        break;
                    }
                } else if (deadCardSkillUseInfo.getType() == SkillType.真龙九现) {
                    if (MultipleReincarnation.apply(this, deadCardSkillUseInfo, deadCard, result.unbending, opponent, 65, 15, 19)) {
                        reincarnated = true;
                        break;
                    }
                } else if (deadCardSkillUseInfo.getType() == SkillType.终焉脱壳) {
                    if (MultipleReincarnation.apply(this, deadCardSkillUseInfo, deadCard, result.unbending, opponent, 50, 0, 50)) {
                        reincarnated = true;
                        break;
                    }
                } else if (deadCardSkillUseInfo.getType() == SkillType.飞天揽月) {
                    if (MultipleReincarnation.apply(this, deadCardSkillUseInfo, deadCard, result.unbending, opponent, 40, 40, 0)) {
                        reincarnated = true;
                        break;
                    }
                }
            }
            if (!reincarnated && !deadCard.isSilent()) {
                RuneInfo rune = deadCard.getOwner().getActiveRuneOf(RuneData.秽土);
                if (rune != null && !deadCard.justRevived()) {
                    Reincarnation.apply(this, rune.getSkill(), deadCard, result.unbending, opponent);
                }
            }
        }

        // HACKHACK: Cannot find better way to handle 不屈/
        //改变不屈的去掉buff位置，为GiveSideSkill做的处理
        if (result.soulControlDead) {
            deadCard.restoreOwner();
        }
        if (deadCard.getOwner().getBeforeDeath().contains(deadCard)) {
            deadCard.getOwner().getBeforeDeath().removeCard(deadCard);
            deadCard.getOwner().getGrave().addCard(deadCard);
        }
        deadCard.setIsDeathNow(false);
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
                if (skillUseInfo.getType() == SkillType.樱魂 || skillUseInfo.getType() == SkillType.神亭酣战 || skillUseInfo.getType() == SkillType.肉食者) {
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

    public void resolveExtraAttackSkills(CardInfo attacker, CardInfo defender, Player defenderHero, Skill attackSkill, OnDamagedResult damageResult, boolean firstSkill) throws HeroDieSignal {
        int normalAttackDamage = damageResult.actualDamage;
        for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
            if (!attacker.isDead()) {
                if (skillUseInfo.getType() == SkillType.穿刺 || skillUseInfo.getType() == SkillType.英雄之敌 || skillUseInfo.getType() == SkillType.头槌破门) {
                    Penetration.apply(skillUseInfo.getSkill(), this, attacker, defenderHero, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.精准打击 || skillUseInfo.getType() == SkillType.精准射击) {
                    Penetration.apply(skillUseInfo.getSkill(), this, attacker, defenderHero, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.削弱 || skillUseInfo.getType() == SkillType.缴械) {
                    Weaken.apply(this, skillUseInfo, attacker, defender, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.裂伤) {
                    Wound.apply(this, skillUseInfo, attackSkill, attacker, defender, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.嗜血 || skillUseInfo.getType() == SkillType.亮银 || skillUseInfo.getType() == SkillType.暴食) {
                    BloodThirsty.apply(this, skillUseInfo, attacker, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.连锁攻击 || skillUseInfo.getType() == SkillType.女武神之辉) {
                    ChainAttack.apply(this, skillUseInfo, attacker, defender, attackSkill, damageResult.originalDamage);
                } else if (skillUseInfo.getType() == SkillType.疾病) {
                    Disease.apply(skillUseInfo, this, attacker, defender, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.贪吃 || skillUseInfo.getType() == SkillType.魔龙之怒) {
                    BloodThirsty.apply(this, skillUseInfo, attacker, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.毒刃 || skillUseInfo.getType() == SkillType.毒杀) {
                    PosionBlade.apply(this, skillUseInfo, attacker, defender, normalAttackDamage);
                } else if (skillUseInfo.getType() == SkillType.高级连击) {
                    MultipleAttack.apply(this, skillUseInfo, attacker, defenderHero, attackSkill, firstSkill);
                } else if (skillUseInfo.getType() == SkillType.狂舞) {
                    MultipleAttack.apply(this, skillUseInfo, attacker, defenderHero, attackSkill, firstSkill);
                }
            }
            if (skillUseInfo.getType() == SkillType.武形天火击) {
                if (!defender.isDead() && defender.getStatus().containsStatus(CardStatusType.燃烧)) {
                    Destroy.apply(this, skillUseInfo.getSkill(), attacker, defender);
                }
            }
            else if (skillUseInfo.getType() == SkillType.幻影军团 || skillUseInfo.getType() == SkillType.幻影奇袭) {
                SummonWhenAttack.apply(this, skillUseInfo, attacker, 1,true, attacker.getName());
            }
            else if (skillUseInfo.getType() == SkillType.离魂剑) {
                SoulControlMutiple.apply(this, skillUseInfo, attacker, defenderHero);
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
            if (skillUseInfo.getType() == SkillType.英雄杀手 || skillUseInfo.getType() == SkillType.英雄之敌 || skillUseInfo.getType() == SkillType.头槌破门
                    || skillUseInfo.getType() == SkillType.龙战于野 || skillUseInfo.getType() == SkillType.超级英雄杀手 ||  skillUseInfo.getType() == SkillType.杀手回梦) {
                HeroKiller.apply(this, skillUseInfo, attacker, defenderPlayer);
            } else if (skillUseInfo.getType() == SkillType.夜袭) {
                HeroKiller.apply(this, skillUseInfo.getAttachedUseInfo2(), attacker, defenderPlayer);
            } else if (skillUseInfo.getType() == SkillType.凯撒之击) {
                CaeserAttack.apply(this, skillUseInfo, attacker, defenderPlayer);
            } else if (skillUseInfo.getType() == SkillType.厨具召唤) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defenderPlayer, 1, 500);
            } else if (skillUseInfo.getType() == SkillType.神兵召唤 ||
                    skillUseInfo.getType() == SkillType.混铁棍 ||
                    skillUseInfo.getType() == SkillType.天降神兵 ||
                    skillUseInfo.getType() == SkillType.神兵降临 ||
                    skillUseInfo.getType() == SkillType.觉醒神兵召唤 && attacker.isAwaken(skillUseInfo, Race.SAVAGE, 2) ||
                    skillUseInfo.getType() == SkillType.星座能量清醒 ||
                    skillUseInfo.getType() == SkillType.阿拉希血统) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defenderPlayer, 500, 1700);
            } else if (skillUseInfo.getType() == SkillType.觉醒青龙偃月 && attacker.isAwaken(skillUseInfo, Race.FOREST, 3)) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defenderPlayer, 1300, 1780);
            } else if (skillUseInfo.getType() == SkillType.圣器召唤 || skillUseInfo.getType() == SkillType.王牌飞刀 || skillUseInfo.getType() == SkillType.突袭) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defenderPlayer, 300, 1300);
            } else if (skillUseInfo.getType() == SkillType.极寒裂伤) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defenderPlayer, 600, 1200);
            } else if (skillUseInfo.getType() == SkillType.陨星攻击) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defenderPlayer, 700, 1600);
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
            } else if (skillUseInfo.getType() == SkillType.暴击 || skillUseInfo.getType() == SkillType.鹰眼 || skillUseInfo.getType() == SkillType.致命一击) {
                CriticalAttack.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.神兵召唤 ||
                    skillUseInfo.getType() == SkillType.混铁棍 ||
                    skillUseInfo.getType() == SkillType.天降神兵 ||
                    skillUseInfo.getType() == SkillType.神兵降临 ||
                    skillUseInfo.getType() == SkillType.觉醒神兵召唤 && attacker.isAwaken(skillUseInfo, Race.SAVAGE, 2) ||
                    skillUseInfo.getType() == SkillType.星座能量清醒 ||
                    skillUseInfo.getType() == SkillType.阿拉希血统) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defender, 500, 1700);
            } else if (skillUseInfo.getType() == SkillType.觉醒青龙偃月 && attacker.isAwaken(skillUseInfo, Race.FOREST, 3)) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defender, 1300, 1780);
            } else if (skillUseInfo.getType() == SkillType.厨具召唤) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defender, 1, 500);
            } else if (skillUseInfo.getType() == SkillType.圣器召唤 || skillUseInfo.getType() == SkillType.王牌飞刀 || skillUseInfo.getType() == SkillType.突袭) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defender, 300, 1300);
            } else if (skillUseInfo.getType() == SkillType.极寒裂伤) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defender, 600, 1200);
            } else if (skillUseInfo.getType() == SkillType.陨星攻击) {
                WeaponSummon.apply(this, skillUseInfo, attacker, defender, 700, 1600);
            } else if (skillUseInfo.getType() == SkillType.穷追猛打 || skillUseInfo.getType() == SkillType.灵击) {
                Pursuit.apply(this, skillUseInfo, attacker, defender);
            } else if (skillUseInfo.getType() == SkillType.战意 || skillUseInfo.getType() == SkillType.鬼王之怒 || skillUseInfo.getType() == SkillType.大江山鬼王 || skillUseInfo.getType() == SkillType.正义追击 || skillUseInfo.getType() == SkillType.战神) {
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
            } else if (type == SkillType.暴击 || type == SkillType.鹰眼 || type == SkillType.致命一击) {
                CriticalAttack.remove(this, effect.getCause(), card);
            } else if (type == SkillType.穷追猛打 || type == SkillType.灵击) {
                Pursuit.remove(this, effect.getCause(), card);
            } else if (type == SkillType.背刺 || type == SkillType.大背刺) {
                BackStab.remove(this, effect.getCause(), card);
            } else if (type == SkillType.战意 || type == SkillType.鬼王之怒 || type == SkillType.大江山鬼王 || type == SkillType.正义追击 || type == SkillType.战神) {
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
            } else if (type == SkillType.英雄杀手 || type == SkillType.英雄之敌 || type == SkillType.头槌破门
                    || type == SkillType.龙战于野 || type == SkillType.超级英雄杀手 || type == SkillType.杀手回梦) {
                HeroKiller.remove(this, effect.getCause(), card);
            } else if (type == SkillType.夜袭) {
                HeroKiller.remove(this, effect.getCause(), card);
            } else if (type == SkillType.凯撒之击) {
                CaeserAttack.remove(this, effect.getCause(), card);
            } else if (type == SkillType.神兵召唤 || type == SkillType.天降神兵 || type == SkillType.神兵降临 || type == SkillType.厨具召唤 || type == SkillType.觉醒神兵召唤 || type == SkillType.觉醒青龙偃月 ||
                    type == SkillType.圣器召唤 || type == SkillType.突袭 || type == SkillType.极寒裂伤 || type == SkillType.陨星攻击 || type == SkillType.王牌飞刀 || type == SkillType.混铁棍
                    || type == SkillType.阿拉希血统 || type == SkillType.星座能量清醒) {
                WeaponSummon.remove(this, effect.getCause(), card);
            }
        }
    }

    public OnDamagedResult applyDamage(EntityInfo attacker, CardInfo defender, Skill skill, int damage) throws HeroDieSignal {
        OnDamagedResult result = new OnDamagedResult();
        List<CardStatusItem> unbendingStatusItems = defender.getStatus().getStatusOf(CardStatusType.不屈);
        if ((defender.containsUsableSkill(SkillType.魔族之血) || defender.containsUsableSkill(SkillType.邪甲术) || defender.containsUsableSkill(SkillType.不朽原核)
                || defender.containsUsableSkill(SkillType.白袍银甲) || defender.containsUsableSkill(SkillType.魔王之血) || defender.containsUsableSkill(SkillType.魔神加护)
                || defender.containsUsableSkill(SkillType.嗜血潜能))) {
            for (SkillUseInfo skillUseInfo : defender.getUsableNormalSkills()) {
                if (skillUseInfo.getType() == SkillType.魔族之血 || skillUseInfo.getType() == SkillType.邪甲术 || skillUseInfo.getType() == SkillType.不朽原核
                        || skillUseInfo.getType() == SkillType.白袍银甲 || skillUseInfo.getType() == SkillType.魔王之血 || skillUseInfo.getType() == SkillType.魔神加护
                        || skillUseInfo.getType() == SkillType.嗜血潜能) {
                    if (attacker instanceof CardInfo) {
                        if (resolveStopBlockSkill(skillUseInfo.getSkill(), (CardInfo) attacker, defender)) {
                            break;
                        }
                    }
                    damage = (damage - skillUseInfo.getSkill().getImpact()) > 0 ? (damage - skillUseInfo.getSkill().getImpact()) : 0;
                    stage.getUI().useSkill(defender, skillUseInfo.getSkill(), true);
                    break;
                }
            }
        }

        if (!unbendingStatusItems.isEmpty()) {
            if (skill != null && skill.getType().containsTag(SkillTag.法术扼杀) && damage > 0) {
                this.removeStatus(defender, CardStatusType.不屈);
            }
            //攻击不屈卡不能吸血 2018-07-08 add
//            else if (skill != null && (skill.getType() == SkillType.吸血 || skill.getType() == SkillType.蛇吻 || skill.getType() == SkillType.鬼彻
//                    || skill.getType() == SkillType.武圣 || skill.getType() == SkillType.村正 || skill.getType() == SkillType.樱魂 || skill.getType() == SkillType.狂暴|| skill.getType() == SkillType.神亭酣战|| skill.getType() == SkillType.肉食者)) {
//                // 不屈状态下可以吸血
//            }
            else {
                this.getStage().getUI().unbend(defender, unbendingStatusItems.get(0));
                damage = 0;
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
                        skillUseInfo.getType() == SkillType.暗之献祭 ||
                        skillUseInfo.getType() == SkillType.暗之归还 ||
                        skillUseInfo.getType() == SkillType.武形秘法 ||
                        skillUseInfo.getType() == SkillType.蝶息 ||
                        skillUseInfo.getType() == SkillType.逆鳞 ||
                        skillUseInfo.getType() == SkillType.嗜魔之体 ||
                        skillUseInfo.getType() == SkillType.坚韧 ||
                        skillUseInfo.getType() == SkillType.坚毅) {
                    // BUGBUG: The original game does not set cardDead to false
                    // result.cardDead = false
                    result.unbending = Unbending.apply(skillUseInfo, this, defender);
                } else if (skillUseInfo.getType() == SkillType.怨起) {
                    result.unbending = UnbendingAwaken.apply(skillUseInfo, this, defender);
                } else if (skillUseInfo.getType() == SkillType.赤焰战场) {
                    Player opponent = this.getStage().getOpponent(defender.getOwner());
                    result.unbending = UnbendingAwaken.applyLess(skillUseInfo, this, defender, opponent);
                }
            }
            if (!result.unbending) {
                DeadType deadType = cardDead(attacker, skill, defender);
                if (deadType == DeadType.SoulCrushed) {
                    result.soulCrushed = true;
                } else if (deadType == DeadType.SoulControlDead) {
                    result.soulControlDead = true;
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
                if (killingSkill != null && killingSkill.getType().containsTag(SkillTag.法术扼杀) && deadCard.getRace() != Race.BOSS && deadCard.getRace() != Race.DEMON) {
                    this.getStage().getUI().useSkill(attacker, killingSkill, true);
                    card.restoreOwner();
                    owner.getOutField().addCard(card);
                    return DeadType.SoulCrushed;
                }
                if (attacker instanceof CardInfo && this.isPhysicalAttackSkill(killingSkill)) {
                    CardInfo attackCard = (CardInfo) attacker;
                    if (deadCard.getRace() != Race.BOSS && deadCard.getRace() != Race.DEMON) {
                        //逆流符文
                        RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.逆流);
                        if (rune != null && attackCard.getRuneActive()) {
                            this.getStage().getUI().useSkill(attacker, rune.getSkill(), true);
                            card.restoreOwner();
                            owner.getOutField().addCard(card);
                            return DeadType.SoulCrushed;
                        }
                        for (SkillUseInfo skillUseInfo : attackCard.getUsableNormalSkills()) {
                            if (skillUseInfo.getType() == SkillType.扼杀 || skillUseInfo.getType() == SkillType.无双 || skillUseInfo.getType() == SkillType.双斩 || skillUseInfo.getType() == SkillType.淘汰 || skillUseInfo.getType() == SkillType.溶骨的毒酒) {
                                if (deadCard.getRace() == Race.BOSS || deadCard.getRace() == Race.DEMON) {
                                    break;
                                }
                                this.getStage().getUI().useSkill(attacker, skillUseInfo.getSkill(), true);
                                card.restoreOwner();
                                owner.getOutField().addCard(card);
                                return DeadType.SoulCrushed;
                            }
                        }
                    }
                }
                if (card.getOriginalOwner() != null && card.getOriginalOwner() != card.getOwner()) {
                    card.restoreOwner();
                    //    card.getOwner().getGrave().addCard(card);
                    card.getOwner().getBeforeDeath().addCard(card);
                    card.switchOwner(attacker.getOwner());
                    return DeadType.SoulControlDead;
                }
                card.restoreOwner();
                // card.getOwner().getGrave().addCard(card);
                card.getOwner().getBeforeDeath().addCard(card);
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
                    if (!(cardSkill != null && (cardSkill.getType() == SkillType.自动扣血 || cardSkill.getType() == SkillType.羽扇虎拳))) {
                        remainingDamage = remainingDamage * defenderPlayer.getCoefficient() / 100;
                    }
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

    public void resolveExtraAttackHeroSkills(CardInfo attacker, Player defenderHero, Boolean firstSkill) throws HeroDieSignal {
        if (!(attacker.containsUsableSkill(SkillType.幻影军团) || attacker.containsUsableSkill(SkillType.高级连击) || attacker.containsUsableSkill(SkillType.狂舞)
                || attacker.containsUsableSkill(SkillType.离魂剑))) {
            return;
        }
        for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
            if (skillUseInfo.getType() == SkillType.幻影军团 || skillUseInfo.getType() == SkillType.幻影奇袭) {
                SummonWhenAttack.apply(this, skillUseInfo, attacker, 1,true, attacker.getName());
            } else if (skillUseInfo.getType() == SkillType.高级连击) {
                MultipleAttack.apply(this, skillUseInfo, attacker, defenderHero, null, firstSkill);
            } else if (skillUseInfo.getType() == SkillType.狂舞) {
                MultipleAttack.apply(this, skillUseInfo, attacker, defenderHero, null, firstSkill);
            } else if (skillUseInfo.getType() == SkillType.离魂剑) {
                SoulControlMutiple.apply(this, skillUseInfo, attacker, defenderHero);
            }
        }
    }

    private int resolveAttackHeroBlockingSkills(EntityInfo attacker, Player defenderPlayer, Skill cardSkill,
                                                int damage) throws HeroDieSignal {
        int remainingDamage = damage;
        if (cardSkill == null) {
            return remainingDamage;
        } else if (cardSkill.getType() == SkillType.自动扣血 || cardSkill.getType() == SkillType.羽扇虎拳) {
            return remainingDamage;
        }
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

    public void resolveCardRoundEndingSkills(CardInfo card, Player defender) throws HeroDieSignal {
        if (card == null) {
            return;
        }
        CardStatus status = card.getStatus();
        if (status.containsStatus(CardStatusType.锁定)) {
            return;
        }
        for (SkillUseInfo cardSkillUseInfo : card.getUsableNormalSkills()) {
            if (cardSkillUseInfo.getType() == SkillType.回春 ||
                    cardSkillUseInfo.getType() == SkillType.自愈 ||
                    cardSkillUseInfo.getType() == SkillType.月恩术 ||
                    cardSkillUseInfo.getType() == SkillType.圣母回声 ||
                    cardSkillUseInfo.getType() == SkillType.大地吟咏 ||
                    cardSkillUseInfo.getType() == SkillType.圣光奏鸣曲 ||
                    cardSkillUseInfo.getType() == SkillType.亚平宁之蓝 ||
                    cardSkillUseInfo.getType() == SkillType.圣母咏叹调) {
                Rejuvenate.apply(cardSkillUseInfo.getSkill(), this, card);
            } else if (cardSkillUseInfo.getType() == SkillType.闭月 || cardSkillUseInfo.getType() == SkillType.浴火 || cardSkillUseInfo.getType() == SkillType.护体石肤 || cardSkillUseInfo.getType() == SkillType.酒意 || cardSkillUseInfo.getType() == SkillType.隐匿) {
                Rejuvenate.apply(cardSkillUseInfo.getAttachedUseInfo2().getSkill(), this, card);
            } else if (cardSkillUseInfo.getType() == SkillType.圣母吟咏) {
                PercentGetHp.apply(cardSkillUseInfo.getSkill(), this, card);
            } else if (cardSkillUseInfo.getType() == SkillType.重整 || cardSkillUseInfo.getType() == SkillType.不朽岿岩 || cardSkillUseInfo.getType() == SkillType.不息神盾 || cardSkillUseInfo.getType() == SkillType.再生金蝉) {
                Reforming.apply(this, cardSkillUseInfo, card, defender);
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
        return attackCard(attacker, defender, skillUseInfo, attacker.getCurrentAT(), true);
    }

    public OnDamagedResult attackCard(CardInfo attacker, CardInfo defender, SkillUseInfo skillUseInfo, int damage, boolean firstSkill) throws HeroDieSignal {
        Skill skill = skillUseInfo == null ? null : skillUseInfo.getSkill();
        boolean bingo = !attacker.getStatus().containsStatus(CardStatusType.麻痹);
        this.stage.getUI().useSkill(attacker, defender, null, bingo);

        OnAttackBlockingResult blockingResult = stage.getResolver().resolveAttackBlockingSkills(
                attacker, defender, skill, damage);
        if (!blockingResult.isAttackable()) {
            //   this.removeStatus(attacker, CardStatusType.不屈);
            return null;
        }
        if (skill == null) {
            for (SkillUseInfo cardSkillUseInfo : attacker.getAllUsableSkills()) {
                if (cardSkillUseInfo.getType() == SkillType.斩杀 || cardSkillUseInfo.getType() == SkillType.送葬之刃 || cardSkillUseInfo.getType() == SkillType.页游击溃
                        || cardSkillUseInfo.getType() == SkillType.无双 || cardSkillUseInfo.getType() == SkillType.双斩 || cardSkillUseInfo.getType() == SkillType.屏息 || cardSkillUseInfo.getType() == SkillType.淘汰) {
                    SuddenKill.apply(this, cardSkillUseInfo, attacker, defender, blockingResult);
                }
            }
        }
        this.stage.getUI().attackCard(attacker, defender, skill, blockingResult.getDamage());
        OnDamagedResult damagedResult = stage.getResolver().applyDamage(attacker, defender, skill, blockingResult.getDamage());
        // this.removeStatus(attacker, CardStatusType.不屈);

        resolvePostAttackSkills(attacker, defender, defender.getOwner(), skill, damagedResult.actualDamage);
        stage.getResolver().resolveDeathSkills(attacker, defender, skill, damagedResult);

        resolveExtraAttackSkills(attacker, defender, defender.getOwner(), skill, damagedResult, firstSkill);
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
                } else if (skillUseInfo.getType() == SkillType.森之星河) {
                    RacialBuff.apply(this, skillUseInfo.getAttachedUseInfo1(), fieldCard, Race.FOREST, SkillEffectType.ATTACK_CHANGE);
                    RacialBuff.apply(this, skillUseInfo.getAttachedUseInfo2(), fieldCard, Race.FOREST, SkillEffectType.MAXHP_CHANGE);
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
                } else if (skillUseInfo.getType() == SkillType.根源之力 || skillUseInfo.getType() == SkillType.战争狂热) {
                    TogetherBuff.apply(this, skillUseInfo, fieldCard, null);
                } else if (skillUseInfo.getType() == SkillType.生命符文) {
                    CoefficientBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.MAXHP_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.倾城之舞) {
                    CoefficientBuff.apply(this, skillUseInfo.getAttachedUseInfo2(), fieldCard, card, null, SkillEffectType.MAXHP_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.魏之勇) {
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.MAXHP_CHANGE, "三国英魂孟德", "三国英魂仲达", "三国樱魂文远", "三国英魂元让", "三国英魂甄姬", "三国英魂文若");
                } else if (skillUseInfo.getType() == SkillType.魏之力) {
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.ATTACK_CHANGE, "三国英魂孟德", "三国英魂仲达", "三国樱魂文远", "三国英魂元让", "三国英魂甄姬", "三国英魂文若");
                } else if (skillUseInfo.getType() == SkillType.曹魏无双) {
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.MAXHP_CHANGE, "三国英魂孟德", "三国英魂仲达", "三国樱魂文远", "三国英魂元让", "三国英魂甄姬", "三国英魂文若");
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.ATTACK_CHANGE, "三国英魂孟德", "三国英魂仲达", "三国樱魂文远", "三国英魂元让", "三国英魂甄姬", "三国英魂文若");
                } else if (skillUseInfo.getType() == SkillType.蜀之勇) {
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.MAXHP_CHANGE, "三国英魂子龙", "三国英魂翼德", "三国英魂卧龙", "三国英魂孔明", "三国英魂孟起", "三国英魂云长", "三国英魂汉升", "三国英魂玄德", "三国英魂星彩");
                } else if (skillUseInfo.getType() == SkillType.蜀之力) {
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.ATTACK_CHANGE, "三国英魂子龙", "三国英魂翼德", "三国英魂卧龙", "三国英魂孔明", "三国英魂孟起", "三国英魂云长", "三国英魂汉升", "三国英魂玄德", "三国英魂星彩");
                } else if (skillUseInfo.getType() == SkillType.蜀汉无双) {
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.MAXHP_CHANGE, "三国英魂子龙", "三国英魂翼德", "三国英魂卧龙", "三国英魂孔明", "三国英魂孟起", "三国英魂云长", "三国英魂汉升", "三国英魂玄德", "三国英魂星彩");
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.ATTACK_CHANGE, "三国英魂子龙", "三国英魂翼德", "三国英魂卧龙", "三国英魂孔明", "三国英魂孟起", "三国英魂云长", "三国英魂汉升", "三国英魂玄德", "三国英魂星彩");
                } else if (skillUseInfo.getType() == SkillType.吴之勇) {
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.MAXHP_CHANGE, "三国英魂大乔", "三国英魂仲谋", "三国英魂子敬", "三国英魂伯言", "三国英魂子义");
                } else if (skillUseInfo.getType() == SkillType.吴之力) {
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.ATTACK_CHANGE, "三国英魂大乔", "三国英魂仲谋", "三国英魂子敬", "三国英魂伯言", "三国英魂子义");
                } else if (skillUseInfo.getType() == SkillType.江东无双) {
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.MAXHP_CHANGE, "三国英魂大乔", "三国英魂仲谋", "三国英魂子敬", "三国英魂伯言", "三国英魂子义");
                    CountryBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.ATTACK_CHANGE, "三国英魂大乔", "三国英魂仲谋", "三国英魂子敬", "三国英魂伯言", "三国英魂子义");
                } else if (skillUseInfo.getType() == SkillType.战歌之鼓) {
                    CoefficientBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.ATTACK_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.神圣守护) {
                    HolyGuard.apply(this, skillUseInfo, fieldCard);
                } else if (skillUseInfo.getType() == SkillType.坚壁) {
                    CoefficientThreeBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.MAXHP_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.剑域) {
                    CoefficientThreeBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.ATTACK_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.北海报恩) {
                    CoefficientThreeBuff.apply(this, skillUseInfo, fieldCard, card, null, SkillEffectType.ATTACK_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.王国同调) {
                    Synchrome.apply(this, skillUseInfo, fieldCard, card, Race.KINGDOM);
                } else if (skillUseInfo.getType() == SkillType.森林同调) {
                    Synchrome.apply(this, skillUseInfo, fieldCard, card, Race.FOREST);
                } else if (skillUseInfo.getType() == SkillType.蛮荒同调) {
                    Synchrome.apply(this, skillUseInfo, fieldCard, card, Race.SAVAGE);
                } else if (skillUseInfo.getType() == SkillType.地狱同调) {
                    Synchrome.apply(this, skillUseInfo, fieldCard, card, Race.HELL);
                } else if (skillUseInfo.getType() == SkillType.森之助) {
                    CoefficientBuffExcludeSummon.apply(this, skillUseInfo.getAttachedUseInfo1(), fieldCard, card, Race.FOREST, SkillEffectType.ATTACK_CHANGE);
                    CoefficientBuffExcludeSummon.apply(this, skillUseInfo.getAttachedUseInfo2(), fieldCard, card, Race.FOREST, SkillEffectType.MAXHP_CHANGE);
                } else if (skillUseInfo.getType() == SkillType.羽扇虎拳) {
                    Bless.apply(skillUseInfo.getSkill(), this, fieldCard);
                } else if (skillUseInfo.getType() == SkillType.王之军阵) {
                    CoefficientThreeBuff.apply(this, skillUseInfo.getAttachedUseInfo1(), fieldCard, card, null, SkillEffectType.MAXHP_CHANGE);
                    CoefficientThreeBuff.apply(this, skillUseInfo.getAttachedUseInfo2(), fieldCard, card, null, SkillEffectType.ATTACK_CHANGE);
                }
            }
        }

        // 降临系技能（除了降临复活之外）
        int position = card.getPosition();
        if (position < 0 || player.getField().getCard(position) == null) {
            // Killed or returned by other summoning skills
            card.setIsSummon(false);
            return;
        }
        for (SkillUseInfo skillUseInfo : card.getAllUsableSkills()) {
            if (!card.isAlive()) {
                //card is dead or return hand or deck
                break;
            }
            if (skillUseInfo.getSkill().isSummonSkill()) {
                if (skillUseInfo.getType() == SkillType.烈焰风暴) {
                    FireMagic.apply(skillUseInfo.getSkill(), this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.雷暴) {
                    LighteningMagic.apply(skillUseInfo, this, card, enemy, -1, 35);
                } else if (skillUseInfo.getType() == SkillType.暴风雪) {
                    IceMagic.apply(skillUseInfo, this, card, enemy, -1, 30, 0);
                } else if (skillUseInfo.getType() == SkillType.寒霜冲击) {
                    IceMagic.apply(skillUseInfo, this, card, enemy, -1, 50, (5 + skillUseInfo.getSkill().getLevel() * 5) * enemy.getField().getAliveCards().size());
                } else if (skillUseInfo.getType() == SkillType.极寒冲击) {
                    IceMagic.apply(skillUseInfo, this, card, enemy, -1, 50, (40 + skillUseInfo.getSkill().getLevel() * 20) * enemy.getField().getAliveCards().size());
                } else if (skillUseInfo.getType() == SkillType.霜焰) {
                    IceMagic.apply(skillUseInfo, this, card, enemy, -1, 50, 120 * enemy.getField().getAliveCards().size());
                } else if (skillUseInfo.getType() == SkillType.寒冰触碰) {
                    IceTouch.apply(skillUseInfo, this, card, enemy, 3);
                } else if (skillUseInfo.getType() == SkillType.审判之剑) {
                    IceTouch.apply(skillUseInfo, this, card, enemy, 3);
                } else if (skillUseInfo.getType() == SkillType.圣炎 || skillUseInfo.getType() == SkillType.热血战士) {
                    HolyFire.apply(skillUseInfo.getSkill(), this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.魔力碎片) {
                    IceTouch.apply(skillUseInfo, this, card, enemy, 3);
                } else if (skillUseInfo.getType() == SkillType.法力风暴 || skillUseInfo.getType() == SkillType.魔法毁灭 || skillUseInfo.getType() == SkillType.屠戮) {
                    ManaErode.apply(skillUseInfo.getSkill(), this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.毒云) {
                    PoisonMagic.apply(skillUseInfo, this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.剧毒新星) {
                    PoisonMagic.apply(skillUseInfo, this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.瘟疫) {
                    Plague.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.灵魂消散) {
                    SoulCrash.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.凋零真言) {
                    WitheringWord.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.治疗) {
                    Heal.apply(skillUseInfo.getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.甘霖) {
                    Rainfall.apply(skillUseInfo.getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.月神的护佑 || skillUseInfo.getType() == SkillType.月之守护 || skillUseInfo.getType() == SkillType.月之守望) {
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
                    RegressionSoul.apply(this, skillUseInfo, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.号角) {
                    Horn.apply(skillUseInfo, this, card);
                } else if (skillUseInfo.getType() == SkillType.祈愿) {
                    Supplication.apply(this, skillUseInfo, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.封印) {
                    Seal.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.业火) {
                    HellFire.apply(skillUseInfo, this, card, enemy, 3);
                } else if (skillUseInfo.getType() == SkillType.关小黑屋) {
                    Enprison.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.净化) {
                    Purify.apply(skillUseInfo, this, card, -1);
                } else if (skillUseInfo.getType() == SkillType.战争怒吼 || skillUseInfo.getType() == SkillType.常夏日光 || skillUseInfo.getType() == SkillType.碎裂怒吼) {
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
                } else if (skillUseInfo.getType() == SkillType.原星之所在) {
                    Summon.apply(this, skillUseInfo, card, SummonType.RandomSummoning, 2,
                            "原白羊座", "原金牛座", "原双子座", "原巨蟹座", "原狮子座", "原处女座",
                            "原天秤座", "原射手座", "原天蝎座", "原摩羯座", "原水瓶座", "原双鱼座");
                } else if (skillUseInfo.getType() == SkillType.页游星之所在) {
                    Summon.apply(this, skillUseInfo, card, SummonType.RandomSummoning, 2,
                            "网页版白羊座", "网页版金牛座", "网页版双子座", "网页版巨蟹座", "网页版狮子座", "网页版处女座",
                            "天秤座", "射手座", "天蝎座", "摩羯座", "网页版水瓶座", "网页版双鱼座");
                } else if (skillUseInfo.getType() == SkillType.灵龙轰咆) {
                    Summon.apply(this, skillUseInfo, card, SummonType.RandomSummoning, 2,
                            "光明之龙", "金属巨龙", "黄金金属巨龙", "元素灵龙", "暴怒霸龙", "毁灭之龙", "幽灵巨龙",
                            "水晶巨龙", "毒雾羽龙", "黄金毒龙", "地魔龙", "邪狱魔龙", "混沌之龙", "地狱雷龙");
                } else if (skillUseInfo.getType() == SkillType.万兽奔腾) {
                    Summon.apply(this, skillUseInfo, card, SummonType.RandomSummoning, 2,
                            "麒麟兽", "凤凰", "浮云青鸟", "九头妖蛇", "雷兽", "羽翼化蛇", "神谕火狐",
                            "齐天美猴王", "羽蛇神", "月蚀兽", "逐月恶狼", "逐日凶狼", "月之神兽", "山地兽");
                } else if (skillUseInfo.getType() == SkillType.狂野之怒) {
                    Summon.apply(this, skillUseInfo, card, SummonType.RandomSummoning, 2,
                            "凤凰", "浮云青鸟", "九头妖蛇", "雷兽", "羽翼化蛇", "神谕火狐",
                            "齐天美猴王", "羽蛇神", "月蚀兽", "逐月恶狼", "逐日凶狼", "月之神兽", "山地兽");
                } else if (skillUseInfo.getType() == SkillType.三国英才) {
                    Summon.apply(this, skillUseInfo, card, SummonType.RandomSummoning, 1,
                            "三国英魂卧龙", "三国英魂仲达", "三国英魂孔明", "三国英魂子敬", "三国英魂伯言", "三国英魂文若");
                } else if (skillUseInfo.getType() == SkillType.三国武魂) {
                    Summon.apply(this, skillUseInfo, card, SummonType.RandomSummoning, 1,
                            "三国英魂子龙", "三国英魂翼德", "三国英魂奉先", "三国英魂孟起", "三国樱魂文远", "三国英魂云长", "三国英魂元让", "三国英魂汉升", "三国英魂子义");
                } else if (skillUseInfo.getType() == SkillType.星河召唤) {
                    Summon.apply(this, skillUseInfo, card, SummonType.RandomSummoning, 2,
                            "精灵游骑兵", "蝶语仙子", "人马大贤者", "洞察之鹰", "森林弹唱者", "森林女神");
                } else if (skillUseInfo.getType() == SkillType.祈福) {
                    Bless.apply(skillUseInfo.getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.山崩) {
                    Crumbling.apply(this, skillUseInfo.getSkill(), card, enemy, 1, 1);
                } else if (skillUseInfo.getType() == SkillType.烈焰审判) {
                    UnderworldCall.apply(this, skillUseInfo.getSkill(), card, enemy, 3);
                } else if (skillUseInfo.getType() == SkillType.神性祈求) {
                    Purify.apply(skillUseInfo, this, card, -1);
                } else if (skillUseInfo.getType() == SkillType.夺魂) {
                    SoulControl.apply(this, skillUseInfo, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.封锁) {
                    WeakenAll.apply(this, skillUseInfo, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.远古召唤) {
                    AddCard.apply(this, skillUseInfo, card, SummonType.Summoning, 1,
                            "元素巨人");
                } else if (skillUseInfo.getType() == SkillType.冰天雪地) {
                    IceMagic.apply(skillUseInfo, this, card, enemy, -1, 0, 160 * enemy.getField().getAliveCards().size());
                } else if (skillUseInfo.getType() == SkillType.擒拿) {
                    Curse.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.天谴 || skillUseInfo.getType() == SkillType.末世术) {
                    HeavenWrath.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.退散) {
                    ReturnCard.apply(this, skillUseInfo.getSkill(), card, enemy, 1);
                }
            } else if (!skillUseInfo.getSkill().isDeathSkill()) {
                if (skillUseInfo.getType() == SkillType.反噬) {
                    CounterBite.apply(skillUseInfo, this, card);
                } else if (skillUseInfo.getType() == SkillType.星云锁链 || skillUseInfo.getType() == SkillType.星团锁链) {
                    NebulaChain.apply(skillUseInfo, this, card);
                } else if (skillUseInfo.getType() == SkillType.先锋突袭) {
                    Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, card, enemy, 1);
                } else if (skillUseInfo.getType() == SkillType.邪灵退散) {
                    SoulCrash.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.进军之令) {
                    Supplication.apply(this, skillUseInfo, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.救赎) {
                    Bless.apply(skillUseInfo.getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.掠影) {
                    Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, card, enemy, 1);
                } else if (skillUseInfo.getType() == SkillType.天下桃李) {
                    NebulaChain.apply(skillUseInfo, this, card);
                    HandCardAddSkillLong.apply(this, skillUseInfo, card, skillUseInfo.getSkill());
                } else if (skillUseInfo.getType() == SkillType.分解反应) {
                    Destroy.apply(this, skillUseInfo.getAttachedUseInfo1().getSkill(), card, enemy, 1);
                    AllDelay.apply(skillUseInfo.getAttachedUseInfo2(), this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.王牌飞刀) {
                    Seal.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.咆哮 || skillUseInfo.getType() == SkillType.瓦解) {
                    Destroy.apply(this, skillUseInfo.getSkill(), card, enemy, 1);
                    Transport.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.制裁之拳) {
                    Destroy.apply(this, skillUseInfo.getSkill(), card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.火力压制) {
                    RedGun.apply(skillUseInfo.getAttachedUseInfo1(), this, card, enemy, 3);
                    AllDelay.apply(skillUseInfo.getAttachedUseInfo2(), this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.虚梦) {
                    Transport.apply(this, skillUseInfo.getAttachedUseInfo2().getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.影青龙) {
                    Summon.apply(this, skillUseInfo, card, SummonType.Normal, 1, card.getName());
                } else if (skillUseInfo.getType() == SkillType.上层精灵的挽歌) {
                    Resurrection.apply(this, skillUseInfo, card);
                } else if (!isMinion && skillUseInfo.getType() == SkillType.镜魔) {
                    // 镜像召唤的单位可以被连锁攻击
                    Summon.apply(this, skillUseInfo.getAttachedUseInfo2(), card, SummonType.Summoning, 1, card.getName());
                } else if (skillUseInfo.getType() == SkillType.全领域沉默) {
                    Silence.apply(this, skillUseInfo, card, enemy, true, false);
                } else if (skillUseInfo.getType() == SkillType.召唤玫瑰剑士) {
                    Summon.apply(this, skillUseInfo.getAttachedUseInfo1(), card, SummonType.Summoning, 1,
                            "花舞剑士");
                } else if (skillUseInfo.getType() == SkillType.英灵召唤 || skillUseInfo.getType() == SkillType.英魂唤醒) {
                    Summon.apply(this, skillUseInfo.getAttachedUseInfo1(), card, SummonType.RandomSummoning, 2,
                            "三国英魂玄德", "三国英魂子龙", "三国英魂汉升", "三国英魂张角", "三国英魂仲颖", "三国英魂貂蝉", "三国英魂奉先");
                } else if (skillUseInfo.getType() == SkillType.剑道) {
                    Summon.apply(this, skillUseInfo, card, SummonType.Normal, 2,
                            "武形剑圣", "武形剑圣");
                } else if (skillUseInfo.getType() == SkillType.伎町迷影) {
                    Summon.apply(this, skillUseInfo, card, SummonType.Normal, 2,
                            card.getName(), card.getName());
                } else if (skillUseInfo.getType() == SkillType.猫神的低语) {
                    Summon.apply(this, skillUseInfo, card, SummonType.Normal, 2,
                            "九命猫神", "九命猫神");
                } else if (skillUseInfo.getType() == SkillType.桃园结义) {
                    Summon.apply(this, skillUseInfo, card, SummonType.Summoning, 2,
                            "三国英魂云长", "三国英魂翼德");
                } else if (skillUseInfo.getType() == SkillType.灵龟羁绊) {
                    Summon.apply(this, skillUseInfo, card, SummonType.Summoning, 1,
                            "巨岛龟幼崽");
                } else if (skillUseInfo.getType() == SkillType.舌战群儒) {
                    Insane.apply(skillUseInfo, this, card, enemy, -1, 70);
                } else if (skillUseInfo.getType() == SkillType.无尽华尔兹) {
                    Insane.apply(skillUseInfo, this, card, enemy, -1, 100);
                } else if (skillUseInfo.getType() == SkillType.纷争乱境) {
                    Insane.apply(skillUseInfo.getAttachedUseInfo1(), this, card, enemy, -1, 100);
                    Erode.apply(this, skillUseInfo.getAttachedUseInfo2(), card, enemy, null,true);
                } else if (skillUseInfo.getType() == SkillType.合纵连横) {
                    GiantEarthquakesLandslides.apply(this, skillUseInfo.getSkill(), card, enemy, 1);
                } else if (skillUseInfo.getType() == SkillType.铁壁 || skillUseInfo.getType() == SkillType.金汤 || skillUseInfo.getType() == SkillType.铁壁方阵
                        || skillUseInfo.getType() == SkillType.光之守护 || skillUseInfo.getType() == SkillType.聚能立场 || skillUseInfo.getType() == SkillType.护主) {
                    ImpregnableDefenseHeroBuff.apply(this, skillUseInfo, card);
                } else if (skillUseInfo.getType() == SkillType.驱虎吞狼) {
                    ImpregnableDefenseHeroBuff.apply(this, skillUseInfo.getAttachedUseInfo2(), card);
                    Erode.apply(this, skillUseInfo.getAttachedUseInfo1(), card, enemy, null,true);
                } else if (skillUseInfo.getType() == SkillType.魔神加护) {
                    ImpregnableDefenseHeroBuff.apply(this, skillUseInfo.getAttachedUseInfo2(), card);
                } else if (skillUseInfo.getType() == SkillType.侵蚀 || skillUseInfo.getType() == SkillType.吞噬) {
                    Erode.apply(this, skillUseInfo, card, enemy, null,true);
                } else if (skillUseInfo.getType() == SkillType.鬼才) {
                    Erode.apply(this, skillUseInfo.getAttachedUseInfo1(), card, enemy, null,true);
                } else if (skillUseInfo.getType() == SkillType.突突突) {
                    AddSkillOpponent.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill(), 1, enemy);
                } else if (skillUseInfo.getType() == SkillType.雀之引) {
                    RegressionSoul.apply(this, skillUseInfo, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.修罗道) {
                    Genie.apply(this, skillUseInfo, card);
                } else if (skillUseInfo.getType() == SkillType.决胜时刻) {
                    TimeTravel.apply(skillUseInfo, this, card.getOwner(), enemy);
                } else if (skillUseInfo.getType() == SkillType.误人子弟) {
                    Confusion.apply(skillUseInfo.getAttachedUseInfo1(), this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.支配亡灵) {
                    ControlGhost.apply(this, skillUseInfo, card, enemy, -1, 3);
                } else if (skillUseInfo.getType() == SkillType.人体炼成) {
                    HumanRefining.apply(this, skillUseInfo, card, enemy, -1, 3);
                } else if (skillUseInfo.getType() == SkillType.涤罪神启) {
                    SoulCrash.apply(skillUseInfo.getAttachedUseInfo1(), this, card, enemy);
                    Bless.apply(skillUseInfo.getAttachedUseInfo2().getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.噬血狂袭) {
                    Curse.apply(this, skillUseInfo.getSkill(), card, enemy);
                    Pray.apply(skillUseInfo.getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.降归魂 || skillUseInfo.getType() == SkillType.彼岸轮回) {
                    RegressionSoul.apply(this, skillUseInfo, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.诀隐) {
                    RegressionSoul.apply(this, skillUseInfo, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.禁术全体阻碍) {
                    AllDelay.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.禁术无尽华尔兹) {
                    Insane.apply(skillUseInfo, this, card, enemy, -1, 100);
                } else if (skillUseInfo.getType() == SkillType.禁术全领域沉默) {
                    Silence.apply(this, skillUseInfo, card, enemy, true, false);
                } else if (skillUseInfo.getType() == SkillType.禁术救赎) {
                    Bless.apply(skillUseInfo.getSkill(), this, card);
                } else if (skillUseInfo.getType() == SkillType.禁术末世降临) {
                    HeavenWrath.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.招魂术) {
                    Resurrection.apply(this, skillUseInfo, card);
                } else if (skillUseInfo.getType() == SkillType.消逝) {
                    AlchemyFailure.apply(this, skillUseInfo, skillUseInfo.getSkill(), card);
                } else if (skillUseInfo.getType() == SkillType.噩梦马戏团) {
                    Summon.apply(this, skillUseInfo, card, SummonType.Normal, 3,
                            "镜魔", "镜魔", "镜魔");
                } else if (skillUseInfo.getType() == SkillType.逆羽罡风) {
                    UnderworldCall.apply(this, skillUseInfo.getAttachedUseInfo1().getSkill(), card, enemy, 3);
                } else if (skillUseInfo.getType() == SkillType.万里追魂) {
                    Transport.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.潜摧) {
                    Destroy.apply(this, skillUseInfo.getSkill(), card, enemy, 1);
                } else if (skillUseInfo.getType() == SkillType.盘球大师) {
                    Seal.apply(skillUseInfo, this, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.桑巴之舞) {
                    Insane.apply(skillUseInfo, this, card, enemy, -1, 100);
                } else if (skillUseInfo.getType() == SkillType.亡魂咒印) {
                    AddSkillOpponent.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill(), 3, enemy);
                } else if (skillUseInfo.getType() == SkillType.星座能量力量) {
                    Crumbling.apply(this, skillUseInfo.getSkill(), card, enemy, 1, 1);
                } else if (skillUseInfo.getType() == SkillType.雪幕) {
                    IceMagic.apply(skillUseInfo.getAttachedUseInfo2(), this, card, enemy, -1, 70, 160 * enemy.getField().getAliveCards().size());
                } else if (skillUseInfo.getType() == SkillType.猫神的低语) {
                    Summon.apply(this, skillUseInfo, card, SummonType.Summoning, 1,
                            "帝国审判者");
                } else if (skillUseInfo.getType() == SkillType.彻骨之寒) {
                    IceTouch.apply(skillUseInfo, this, card, enemy, 3);
                } else if (skillUseInfo.getType() == SkillType.凛冬将至) {
                    IceTouch.apply(skillUseInfo, this, card, enemy, -1);
                } else if (skillUseInfo.getType() == SkillType.舍身) {
                    Curse.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.海滨骚乱) {
                    AddSkillOpponent.apply(this, skillUseInfo, card, skillUseInfo.getAttachedUseInfo1().getSkill(), 1, enemy);
                    Insane.apply(skillUseInfo, this, card, enemy, 3, 150);
                } else if (skillUseInfo.getType() == SkillType.闪耀突击) {
                    Horn.apply(skillUseInfo, this, card);
                } else if (skillUseInfo.getType() == SkillType.逐光追梦) {
                    ReturnCard.apply(this, skillUseInfo.getSkill(), card, enemy, 5);
                } else if (skillUseInfo.getType() == SkillType.反间情报) {
                    Insane.apply(skillUseInfo, this, card, enemy, 3, 0);
                } else if (skillUseInfo.getType() == SkillType.放飞自我) {
                    Supplication.apply(this, skillUseInfo, card, enemy);
                } else if (skillUseInfo.getType() == SkillType.星座能量思考) {
                    AllSpeedUp.apply(skillUseInfo.getAttachedUseInfo1(), this, card);
                } else if (skillUseInfo.getType() == SkillType.星座能量智慧) {
                    Curse.apply(this, skillUseInfo.getSkill(), card, enemy);
                } else if (skillUseInfo.getType() == SkillType.星座能量热情) {
                    AddSkillOpponent.apply(this, skillUseInfo.getAttachedUseInfo2(), card, skillUseInfo.getAttachedUseInfo2().getAttachedUseInfo1().getSkill(), 1, enemy);
                } else if (skillUseInfo.getType() == SkillType.解惑) {
                    Purify.apply(skillUseInfo, this, card, -1);
                    HandCardAddOneSkill.apply(this, skillUseInfo, card, skillUseInfo.getSkill());
                } else if (skillUseInfo.getType() == SkillType.选课代表) {
                    HandCardAddOneSkill.apply(this, skillUseInfo, card, skillUseInfo.getSkill());
                } else if (skillUseInfo.getType() == SkillType.离魂剑) {
                    SoulControlMutiple.applySetNumber(this,skillUseInfo, card);
                } else if (skillUseInfo.getType() == SkillType.骸骨大军) {
                    SkeletonArmy.apply(this, skillUseInfo, card, enemy, -1, 4);
                } else if (skillUseInfo.getType() == SkillType.戾气诅咒) {
                    Curse.apply(this, skillUseInfo.getSkill(), card, enemy);
                    Pray.apply(skillUseInfo.getSkill(), this, card);
                }
            }
        }
        card.setIsSummon(false);
    }

    // reviver: for most of the cases, it should be null.
    // It is only set when the summoning skill performer is revived by another card.
    public void resolveSecondClassSummoningSkills(List<CardInfo> summonedCards, Field myField, Field opField, Skill summonSkill, boolean isSummoning) throws HeroDieSignal {
//        if (summonSkill != null && summonSkill.getType() == SkillType.星云锁链) {
//            // 木盒的特殊BUG，星云锁链召唤的卡无法发动第二阶降临技能//这个作废可以发动二段降临技能
//            return;
//        }
        for (CardInfo card : summonedCards) {
            if (null == card) {
                continue;
            }
            CardStatus status = card.getStatus();
            if (status.containsStatus(CardStatusType.冰冻) || status.containsStatus(CardStatusType.锁定)) {
                continue;
            }
            int position = card.getPosition();
            if (position < 0 || myField.getCard(position) == null) {
                // Killed or returned by other summoning skills
                continue;
            }
            for (SkillUseInfo skillUseInfo : card.getAllUsableSkills()) {
                if (skillUseInfo.getType() == SkillType.时光倒流 && !skillUseInfo.getSkill().isDeathSkill() || skillUseInfo.getType() == SkillType.星座能量平衡 && !skillUseInfo.getSkill().isDeathSkill()) {
                    TimeBack.apply(skillUseInfo, this, myField.getOwner(), opField.getOwner());
                } else if (skillUseInfo.getType() == SkillType.献祭) {
                    Sacrifice.apply(this, skillUseInfo, card, summonSkill);
                }
                //调整侵蚀一段降临发动
//                else if (skillUseInfo.getType() == SkillType.侵蚀) {
//                    Erode.apply(this, skillUseInfo, card, opField.getOwner(), summonSkill);
//                } else if (skillUseInfo.getType() == SkillType.鬼才) {
//                    Erode.apply(this, skillUseInfo.getAttachedUseInfo1(), card, opField.getOwner(), summonSkill);
//                } else if (skillUseInfo.getType() == SkillType.驱虎吞狼) {
//                    Erode.apply(this, skillUseInfo.getAttachedUseInfo1(), card, opField.getOwner(), summonSkill);
//                }
                else if (skillUseInfo.getType() == SkillType.复活 && skillUseInfo.getSkill().isSummonSkill() && isSummoning) {
                    if (!card.hasUsed(skillUseInfo)) {
                        Revive.apply(this, skillUseInfo, card);
                        card.setUsed(skillUseInfo);
                    }
                } else if (skillUseInfo.getType() == SkillType.荣耀降临 && isSummoning) {
                    if (!card.hasUsed(skillUseInfo)) {
                        Revive.apply(this, skillUseInfo.getAttachedUseInfo1(), card);
                        card.setUsed(skillUseInfo);
                    }
                } else if (skillUseInfo.getType() == SkillType.返生术 && isSummoning) {
                    if (!card.hasUsed(skillUseInfo)) {
                        Revive.apply(this, skillUseInfo, card);
                        card.setUsed(skillUseInfo);
                    }
                } else if (skillUseInfo.getType() == SkillType.荆棘守护 && isSummoning) {
                    if (!card.hasUsed(skillUseInfo)) {
                        Revive.apply(this, skillUseInfo, card);
                        card.setUsed(skillUseInfo);
                    }
                } else if (skillUseInfo.getType() == SkillType.灵魂献祭 && isSummoning) {
                    if (!card.hasUsed(skillUseInfo)) {
                        Revive.apply(this, skillUseInfo, card);
                        Sacrifice.apply(this, skillUseInfo, card, summonSkill);
                    }
                } else if (skillUseInfo.getType() == SkillType.制衡) {
                    if (!card.hasUsed(skillUseInfo)) {
                        Sacrifice.apply(this, skillUseInfo, card, summonSkill);
                        if (isSummoning) {
                            Revive.apply(this, skillUseInfo, card);
                            Revive.apply(this, skillUseInfo, card);
                        }
                    }
                } else if (skillUseInfo.getType() == SkillType.新生 && skillUseInfo.getSkill().isSummonSkill() && isSummoning) {
                    NewBorn.apply(this, skillUseInfo, card, opField.getOwner(), 1);
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
            } else if (deadCardSkillUseInfo.getType() == SkillType.森林之力) {
                RacialBuff.remove(this, deadCardSkillUseInfo.getAttachedUseInfo1(), card, Race.FOREST);
                RacialBuff.remove(this, deadCardSkillUseInfo.getAttachedUseInfo2(), card, Race.FOREST);
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
            } else if (deadCardSkillUseInfo.getType() == SkillType.根源之力 || deadCardSkillUseInfo.getType() == SkillType.战争狂热) {
                TogetherBuff.remove(this, deadCardSkillUseInfo, card, null);
            } else if (deadCardSkillUseInfo.getType() == SkillType.神圣守护) {
                HolyGuard.remove(this, deadCardSkillUseInfo, card);
            } else if (deadCardSkillUseInfo.getType() == SkillType.生命符文) {
                CoefficientBuff.remove(this, deadCardSkillUseInfo, card, null);
            } else if (deadCardSkillUseInfo.getType() == SkillType.倾城之舞) {
                CoefficientBuff.remove(this, deadCardSkillUseInfo.getAttachedUseInfo2(), card, null);
            } else if (deadCardSkillUseInfo.getType() == SkillType.战歌之鼓) {
                CoefficientBuff.remove(this, deadCardSkillUseInfo, card, null);
            } else if (deadCardSkillUseInfo.getType() == SkillType.坚壁) {
                CoefficientThreeBuff.remove(this, deadCardSkillUseInfo, card);
            } else if (deadCardSkillUseInfo.getType() == SkillType.剑域) {
                CoefficientThreeBuff.remove(this, deadCardSkillUseInfo, card);
            } else if (deadCardSkillUseInfo.getType() == SkillType.王之军阵) {
                CoefficientThreeBuff.remove(this, deadCardSkillUseInfo.getAttachedUseInfo1(), card);
                CoefficientThreeBuff.remove(this, deadCardSkillUseInfo.getAttachedUseInfo2(), card);
            } else if (deadCardSkillUseInfo.getType() == SkillType.魏之勇) {
                CountryBuff.remove(this, deadCardSkillUseInfo, card, null);
            } else if (deadCardSkillUseInfo.getType() == SkillType.魏之力) {
                CountryBuff.remove(this, deadCardSkillUseInfo, card, null);
            } else if (deadCardSkillUseInfo.getType() == SkillType.曹魏无双) {
                CountryBuff.remove(this, deadCardSkillUseInfo, card, null);
            } else if (deadCardSkillUseInfo.getType() == SkillType.军团王国之力
                    || deadCardSkillUseInfo.getType() == SkillType.军团森林之力
                    || deadCardSkillUseInfo.getType() == SkillType.军团蛮荒之力
                    || deadCardSkillUseInfo.getType() == SkillType.军团地狱之力) {
                LegionBuff.remove(this, deadCardSkillUseInfo, card);
            } else if (deadCardSkillUseInfo.getGiveSkill() == 1 || deadCardSkillUseInfo.getGiveSkill() == 2) {
                GiveSideSkill.removeAll(this, deadCardSkillUseInfo, card);
            } else if (deadCardSkillUseInfo.getType() == SkillType.森之助) {
                CoefficientBuffExcludeSummon.remove(this, deadCardSkillUseInfo.getAttachedUseInfo1(), card, Race.FOREST);
                CoefficientBuffExcludeSummon.remove(this, deadCardSkillUseInfo.getAttachedUseInfo2(), card, Race.FOREST);
            }
        }
    }

    public void removeGiveSkills(CardInfo card) {
        for (SkillUseInfo cardSkillUseInfo : card.getUsableNormalSkills()) {
            if (cardSkillUseInfo.getGiveSkill() == 1) {
                card.removeGiveSkill();
                break;
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

    public void resolveAddATDebuff(CardInfo card, CardStatusType debuffType) throws HeroDieSignal {
        if (card == null) {
            return;
        }
        List<CardStatusItem> items = card.getStatus().getStatusOf(debuffType);
        for (CardStatusItem item : items) {
            SkillUseInfo skillUseInfo = item.getCause();
            this.stage.getUI().adjustAT(skillUseInfo.getOwner(), card, -item.getEffect(), skillUseInfo.getSkill());
            card.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, -item.getEffect(), true));
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
                    blockSkillUseInfo.getType() == SkillType.敏捷 ||
                    blockSkillUseInfo.getType() == SkillType.灵力魔阵 ||
                    blockSkillUseInfo.getType() == SkillType.月之守望 ||
                    blockSkillUseInfo.getType() == SkillType.冰神附体 ||
                    blockSkillUseInfo.getType() == SkillType.以逸待劳 ||
                    blockSkillUseInfo.getType() == SkillType.不灭原核 ||
                    blockSkillUseInfo.getType() == SkillType.黄天当立 ||
                    blockSkillUseInfo.getType() == SkillType.破阵弧光 ||
                    blockSkillUseInfo.getType() == SkillType.时光迁跃 ||
                    blockSkillUseInfo.getType() == SkillType.骑士信仰 ||
                    blockSkillUseInfo.getType() == SkillType.隐蔽 ||
                    blockSkillUseInfo.getType() == SkillType.女武神之辉 ||
                    blockSkillUseInfo.getType() == SkillType.无冕之王 ||
                    blockSkillUseInfo.getType() == SkillType.再生金蝉 ||
                    blockSkillUseInfo.getType() == SkillType.神之守护) {
                if (Escape.isStatusEscaped(blockSkillUseInfo.getSkill(), this, item, victim)) {
                    return new BlockStatusResult(true);
                }
            }
        }
        return new BlockStatusResult(false);
    }

    private void setCardToField(CardInfo card, int flag) {
        Player player = card.getOwner();
        //添加一个flag，召唤的卡牌不会重置状态，为了解决召唤卡牌降临死亡和限定技能
        if (flag == 0) {
            card.resetStart();
        }
        card.setIsSummon(true);
        this.stage.getUI().summonCard(player, card);
        // 夺魂可以从敌方卡组召唤
        if (card.getOriginalOwner().getGrave().contains(card)) {
            card.getOriginalOwner().getGrave().removeCard(card);
        }
        player.getField().addCard(card);
        card.setSummonNumber(1);
        card.setAddDelay(0);
        player.getHand().removeCard(card);
        // 星云锁链之类可以从卡组直接召唤的情况
        player.getDeck().removeCard(card);
    }

    public void summonCard(Player player, CardInfo summonedCard, CardInfo reviver, boolean isMinion, Skill summonSkill, int flag) throws HeroDieSignal {
        Player enemy = this.getStage().getOpponent(player);
        setCardToField(summonedCard, flag);
        this.resolveFirstClassSummoningSkills(summonedCard, player, enemy, isMinion);
        // this.resolveSecondClassSummoningSkills(summonedCards, player.getField(), enemy.getField(), summonSkill, true);
        //取消召唤类技能直接发动二段技能。
    }

    /**
     * 1. Process racial buff skills
     * 2. Process summoning skills
     *
     * @param player
     * @param isMinion
     * @param reviver
     * @throws HeroDieSignal
     */
    public void summonCards(Player player, CardInfo reviver, boolean isMinion) throws HeroDieSignal {
        Player enemy = this.getStage().getOpponent(player);
        List<CardInfo> summonedCards = new ArrayList<CardInfo>();
//        while (true) {
//            CardInfo summonedCard = null;
//            for (CardInfo card : player.getHand().toList()) {
//                if (card.getSummonDelay() == 0) {
//                    summonedCard = card;
//                    break;
//                }
//            }
//            if (summonedCard == null) {
//                break;
//            }
//            summonedCards.add(summonedCard);
//            setCardToField(summonedCard, 0);
//            this.resolveFirstClassSummoningSkills(summonedCard, player, enemy, isMinion);
//        }
        for (CardInfo card : player.getHand().toList()) {
            summonedCards.add(card);
        }
        for (CardInfo summonedCard : summonedCards) {
            if (summonedCard.getSummonDelay() == 0) {
                if (player.getHand().contains(summonedCard)) {
                    setCardToField(summonedCard, 0);
                    this.resolveFirstClassSummoningSkills(summonedCard, player, enemy, isMinion);
                }
            }
        }
        List<CardInfo> fieldCards = player.getField().toList();

        for (CardInfo card : fieldCards) {
            this.stage.getResolver().removeStatus(card, CardStatusType.复活);
        }
//      改变发动技能是所有卡牌不是当回合召唤卡牌
//        this.resolveSecondClassSummoningSkills(summonedCards, player.getField(), enemy.getField(), null, true);
        this.resolveSecondClassSummoningSkills(fieldCards, player.getField(), enemy.getField(), null, true);
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
                    attackerSkillUseInfo.getType() == SkillType.一文字 ||
                    attackerSkillUseInfo.getType() == SkillType.武圣) {
                return WeakPointAttack.isBlockSkillDisabled(this, attackerSkillUseInfo.getSkill(), cardSkill, attacker, defender);
            } else if (attackerSkillUseInfo.getType() == SkillType.斩杀 ||
                    attackerSkillUseInfo.getType() == SkillType.送葬之刃 ||
                    attackerSkillUseInfo.getType() == SkillType.页游击溃 ||
                    attackerSkillUseInfo.getType() == SkillType.无双 ||
                    attackerSkillUseInfo.getType() == SkillType.双斩 ||
                    attackerSkillUseInfo.getType() == SkillType.屏息 ||
                    attackerSkillUseInfo.getType() == SkillType.淘汰) {
                return SuddenKill.isBlockSkillDisabled(this, attackerSkillUseInfo.getSkill(), cardSkill, attacker, defender);
            }
        }
        if (!attacker.isDead() && !attacker.isSilent() && !attacker.justRevived()) {
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.鹰眼);
                if (rune != null && !attacker.justRevived()) {
                    return WeakPointAttack.isBlockSkillDisabled(this, rune.getSkill(), cardSkill, attacker, defender);
                }
            }
        }
        return false;
    }

    public boolean resolveStopBlockSkill(Skill cardSkill, CardInfo attacker, CardInfo defender) {
        for (SkillUseInfo attackerSkillUseInfo : attacker.getUsableNormalSkills()) {
            if (attackerSkillUseInfo.getType() == SkillType.破军 || attackerSkillUseInfo.getType() == SkillType.原素裂变 || attackerSkillUseInfo.getType() == SkillType.溶骨的毒酒
                    || attackerSkillUseInfo.getType() == SkillType.死亡收割 || attackerSkillUseInfo.getType() == SkillType.地裂劲) {
                return DefeatArmy.isDefenSkillDisabled(this, attackerSkillUseInfo.getSkill(), cardSkill, attacker, defender);
            } else if (attackerSkillUseInfo.getType() == SkillType.夜袭) {
                return DefeatArmy.isDefenSkillDisabled(this, attackerSkillUseInfo.getAttachedUseInfo1().getSkill(), cardSkill, attacker, defender);
            }
        }
        if (!attacker.isDead() && !attacker.isSilent() && !attacker.justRevived()) {
            {
                RuneInfo rune = attacker.getOwner().getActiveRuneOf(RuneData.破军);
                if (rune != null && !attacker.justRevived()) {
                    return DefeatArmy.isDefenSkillDisabled(this, rune.getSkill(), cardSkill, attacker, defender);
                }
            }
        }
        return false;
    }

    public boolean resolveStopHolyFire(Player defender) {
        for (CardInfo defenderCard : defender.getField().getAliveCards()) {
            for (SkillUseInfo defenderSkillUseInfo : defenderCard.getUsableNormalSkills()) {
                if (defenderSkillUseInfo.getType() == SkillType.庇护 || defenderSkillUseInfo.getType() == SkillType.浴火 || defenderSkillUseInfo.getType() == SkillType.圣骸) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean resolveStopDelay(Player defender) {
        for (CardInfo defenderCard : defender.getField().getAliveCards()) {
            if(defenderCard.containsUsableSkill(SkillType.稳定))
            {
                return true;
            }
        }
        return false;
    }

    public boolean resolveStopCardDelay(CardInfo defenderCard) {
        if(defenderCard.containsUsableSkill(SkillType.风行) || defenderCard.containsUsableSkill(SkillType.免疫风行))
        {
            return true;
        }
        return false;
    }

    public boolean resolveIsImmune(CardInfo defender, int type) {
        //0包括法反，1只有免疫
        if (type == 0) {
            if (defender.containsAllSkill(SkillType.免疫) || defender.containsAllSkill(SkillType.结界立场) || defender.containsAllSkill(SkillType.影青龙)
                    || defender.containsAllSkill(SkillType.禁区之王) || defender.containsAllSkill(SkillType.彻骨之寒) || defender.containsAllSkill(SkillType.龙战于野)
                    || defender.containsAllSkill(SkillType.恶龙血脉) || defender.containsAllSkill(SkillType.魔力抗性) || defender.containsAllSkill(SkillType.灵能冲击)
                    || defender.containsAllSkill(SkillType.轮回渡厄) || defender.containsAllSkill(SkillType.明月渡我) || defender.containsAllSkill(SkillType.嗜魔之体)
                    || defender.containsAllSkill(SkillType.免疫风行) || defender.containsAllSkill(SkillType.不息神盾) || defender.containsAllSkill(SkillType.魔力泳圈)
                    || defender.containsAllSkill(SkillType.优雅之姿)
                    || CounterMagic.getBlockSkill(defender) != null) {
                return true;
            }
        } else if (type == 1) {
            if (defender.containsAllSkill(SkillType.免疫) || defender.containsAllSkill(SkillType.结界立场) || defender.containsAllSkill(SkillType.影青龙)
                    | defender.containsAllSkill(SkillType.彻骨之寒) || defender.containsAllSkill(SkillType.龙战于野)
                    || defender.containsAllSkill(SkillType.禁区之王) || defender.containsAllSkill(SkillType.恶龙血脉)
                    || defender.containsAllSkill(SkillType.魔力抗性) || defender.containsAllSkill(SkillType.灵能冲击)
                    || defender.containsAllSkill(SkillType.轮回渡厄) || defender.containsAllSkill(SkillType.明月渡我)
                    || defender.containsAllSkill(SkillType.嗜魔之体) || defender.containsAllSkill(SkillType.不息神盾)
                    || defender.containsAllSkill(SkillType.免疫风行) || defender.containsAllSkill(SkillType.魔力泳圈)
                    || defender.containsAllSkill(SkillType.优雅之姿)) {
                return true;
            }
        }
        return false;
    }

    public boolean resolverCounterAttackBlockSkill(Skill counterAttackSkill, CardInfo attacker, CardInfo counterAttacker) {
        for (SkillUseInfo skillUseInfo : attacker.getUsableNormalSkills()) {
            if (skillUseInfo.getType() == SkillType.灵巧 ||
                    skillUseInfo.getType() == SkillType.武形秘仪 ||
                    skillUseInfo.getType() == SkillType.修罗道 ||
                    skillUseInfo.getType() == SkillType.页游屏息 ||
                    skillUseInfo.getType() == SkillType.直感) {
                return Agile.isCounterAttackSkillDisabled(this, skillUseInfo.getSkill(), counterAttackSkill, attacker, counterAttacker);
            }
        }
        return false;
    }

    public void activateRunes(Player player, Player enemy) {
        activateCardRunes(player);//设置卡牌是否能激活符文
        for (RuneInfo rune : player.getRuneBox().getRunes()) {
            if (rune.getEnergy() <= 0) {
                continue;
            }
            if (rune.getName().equals("背水") && player.getField().size() == 0) {
                continue;
            }
            if (rune.getName().equals("逆流") && player.getField().size() == 0) {
                continue;
            }
            if (rune.getName().equals("止水") && player.getField().size() == 0) {
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

    public void activateCardRunes(Player player) {
        for (CardInfo card : player.getField().getAliveCards()) {
            card.setRuneActive(true);
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
                Snipe.apply(rune.getSkillUseInfo(), rune.getSkill(), this, rune, defenderHero, 1);
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
            } else if (rune.is(RuneData.冥途)) {
                HolyFire.apply(rune.getSkillUseInfo().getSkill(), this, rune, defenderHero);
            }
        }
    }

    public void killCard(CardInfo attacker, CardInfo victim, Skill cardSkill) throws HeroDieSignal {
        int originalDamage = victim.getHP();
        int actualDamage = victim.applyDamage(victim.getHP());
        DeadType deadType = this.cardDead(attacker, cardSkill, victim);
        OnDamagedResult onDamagedResult = new OnDamagedResult();
        if (deadType == DeadType.SoulCrushed) {
            onDamagedResult.soulCrushed = true;
        } else if (deadType == DeadType.SoulControlDead) {
            onDamagedResult.soulControlDead = true;
        }
        onDamagedResult.cardDead = true;
        onDamagedResult.actualDamage = actualDamage;
        onDamagedResult.originalDamage = originalDamage;
        onDamagedResult.unbending = false;
        victim.removeStatus(CardStatusType.不屈);
        this.resolveDeathSkills(attacker, victim, cardSkill, onDamagedResult);
    }

    public void resolvePrecastSkills(CardInfo card, Player defenderHero, boolean flag) throws HeroDieSignal {
        for (SkillUseInfo skillUseInfo : card.getUsablePrecastSkills()) {
            if (skillUseInfo.getType() == SkillType.凋零真言) {
                WitheringWord.apply(skillUseInfo, this, card, defenderHero);
            } else if (skillUseInfo.getType() == SkillType.灵王的轰击 || skillUseInfo.getType() == SkillType.法力侵蚀 || skillUseInfo.getType() == SkillType.核弹头) {
                ManaErode.apply(skillUseInfo.getSkill(), this, card, defenderHero, 1);
            } else if (skillUseInfo.getType() == SkillType.神性祈求) {
                Purify.apply(skillUseInfo, this, card, -1);
            } else if (skillUseInfo.getType() == SkillType.寒霜冲击) {
                IceMagic.apply(skillUseInfo, this, card, defenderHero, -1, 50, (5 + skillUseInfo.getSkill().getLevel() * 5) * defenderHero.getField().getAliveCards().size());
            } else if (skillUseInfo.getType() == SkillType.全体加速) {
                AllSpeedUp.apply(skillUseInfo, this, card);
            } else if (skillUseInfo.getType() == SkillType.神行术) {
                AllSpeedUp.apply(skillUseInfo, this, card);
            } else if (skillUseInfo.getType() == SkillType.混乱领域) {
                Confusion.apply(skillUseInfo, this, card, defenderHero, 3);
            } else if (skillUseInfo.getType() == SkillType.拔刀术) {
                TheSword.apply(this, skillUseInfo, card);
            } else if (skillUseInfo.getType() == SkillType.镜像 && flag) {
                Summon.apply(this, skillUseInfo, card, SummonType.Normal, 1, card.getName());
            } else if (skillUseInfo.getType() == SkillType.误人子弟) {
                Confusion.apply(skillUseInfo.getAttachedUseInfo2(), this, card, defenderHero, -1);
            } else if (skillUseInfo.getType() == SkillType.鬼神乱舞) {
                MultipleSnipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, card, defenderHero, 1);
            } else if (skillUseInfo.getType() == SkillType.一刀斩) {
                Snipe.apply(skillUseInfo, skillUseInfo.getSkill(), this, card, defenderHero, 1);
            } else if (skillUseInfo.getType() == SkillType.幻影 && flag) {
                Summon.apply(this, skillUseInfo, card, SummonType.Normal, 1, card.getName());
            } else if (skillUseInfo.getType() == SkillType.请帮帮我) {
                Supplication.apply(this, skillUseInfo, card, defenderHero);
            } else if (skillUseInfo.getType() == SkillType.全体阻碍) {
                AllDelay.apply(skillUseInfo, this, card, defenderHero);
            } else if (skillUseInfo.getType() == SkillType.胜者为王) {
                Transport.apply(this, skillUseInfo.getSkill(), card, defenderHero);
            } else if (skillUseInfo.getType() == SkillType.无上荣耀) {
                Bless.apply(skillUseInfo.getSkill(), this, card);
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
                IceMagic.apply(skillUseInfo, this, card, defenderHero, -1, 50, (5 + skillUseInfo.getSkill().getLevel() * 5) * defenderHero.getField().getAliveCards().size());
            } else if (skillUseInfo.getType() == SkillType.回魂) {
                Resurrection.apply(this, skillUseInfo, card);
            } else if (skillUseInfo.getType() == SkillType.青囊) {
                Revive.apply(this, skillUseInfo, card);
            } else if (skillUseInfo.getType() == SkillType.洛神) {
                // 镜像召唤的单位可以被连锁攻击
                Summon.apply(this, skillUseInfo, card, SummonType.Normal, 1, card.getName());
            } else if (skillUseInfo.getType() == SkillType.镜像) {
                // 镜像召唤的单位可以被连锁攻击
                Summon.apply(this, skillUseInfo, card, SummonType.Normal, 1, card.getName());
            } else if (skillUseInfo.getType() == SkillType.归魂) {
                RegressionSoul.apply(this, skillUseInfo, card, defenderHero);
            } else if (skillUseInfo.getType() == SkillType.生命湍流) {
                RegressionSoul.apply(this, skillUseInfo, card, defenderHero);
            } else if (skillUseInfo.getType() == SkillType.霜焰) {
                IceMagic.apply(skillUseInfo, this, card, defenderHero, -1, 50, 120 * defenderHero.getField().getAliveCards().size());
            }
        }
    }

    //移除卡牌结算
    public void endOutField(Player player) throws HeroDieSignal {
        for (CardInfo outCard : player.getOutField().getAllCards()) {
            removeStatus(outCard, CardStatusType.离魂);
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
