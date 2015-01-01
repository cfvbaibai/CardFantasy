package cfvbaibai.cardfantasy;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.Board;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.Deck;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Grave;
import cfvbaibai.cardfantasy.engine.Hand;
import cfvbaibai.cardfantasy.engine.Phase;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.RuneBox;
import cfvbaibai.cardfantasy.engine.RuneInfo;
import cfvbaibai.cardfantasy.game.PveGameResult;

public abstract class TextGameUI extends GameUI {
    protected abstract void say(String obj);
    
    protected void sayF(String format, Object... args) {
        say(String.format(format, args));
    }
    
    @Override
    public void playerAdded(Player player, int playerNumber) {

    }

    @Override
    public void roundStarted(Player player, int round) {
        say("======================================================");
        sayF("回合 %d 开始！<%s> 行动", round, player.getId());
        say("======================================================");
    }

    @Override
    public void roundEnded(Player player, int round) {
        sayF("<%s> 的回合 %d 结束", player.getId(), round);
    }

    @Override
    public void errorHappened(CardFantasyRuntimeException e) {
        e.printStackTrace();
    }

    @Override
    public void phaseChanged(Player player, Phase previous, Phase current) {
        sayF("阶段转换: %s => %s", previous.name().toUpperCase(), current.name().toUpperCase());
    }

    @Override
    public void playerChanged(Player previousPlayer, Player nextPlayer) {
        sayF("行动玩家交换: <%s> => <%s>", previousPlayer.getId(), nextPlayer.getId());
    }

    @Override
    public void cardDrawed(Player drawer, CardInfo card) {
        sayF("<%s> 抽了一张卡: <%s (等级: %d)>", drawer.getId(), card.getUniqueName(), card.getLevel());
        showBoard();
    }

    @Override
    public void cantDrawDeckEmpty(Player drawer) {
        sayF("玩家 <%s> 牌堆已空，无法抽卡.", drawer.getId());
        this.showBoard();
    }

    @Override
    public void cantDrawHandFull(Player drawer) {
        sayF("玩家 <%s> 手牌已满，无法抽卡.", drawer.getId());
        this.showBoard();
    }

    @Override
    public void summonCard(Player player, CardInfo card) {
        sayF("<%s> 召唤: <%s (等级: %d)>", player.getId(), card.getUniqueName(), card.getLevel());
    }

    @Override
    public void attackCard(EntityInfo attacker, CardInfo defender, Skill cardFeature, int damage) {
        String featureClause = cardFeature == null ? "" : (" by " + cardFeature.getShortDesc() + "");
        int logicalRemainingHP = defender.getHP() - damage;
        if (logicalRemainingHP < 0) {
            sayF("%s 攻击 %s%s. 伤害: %d (%d 溢出). HP: %d -> 0.", attacker.getShortDesc(), defender.getShortDesc(),
                    featureClause, damage, -logicalRemainingHP, defender.getHP());
        } else {
            sayF("%s 攻击 %s%s. 伤害: %d. HP: %d -> %d", attacker.getShortDesc(), defender.getShortDesc(), featureClause,
                    damage, defender.getHP(), logicalRemainingHP);
        }
    }

    @Override
    public void cardDead(CardInfo deadCard) {
        sayF("%s 死亡!", deadCard.getShortDesc());
    }

    @Override
    public void attackHero(EntityInfo attacker, Player hero, Skill cardFeature, int damage) {
        String featureClause = cardFeature == null ? "" : (" 使用 " + cardFeature.getShortDesc() + "");
        int logicalRemainingHP = hero.getHP() - damage;
        if (logicalRemainingHP < 0) {
            sayF("%s%s 直接攻击 <%s>! 伤害: %d (%d 溢出). HP: %d -> %d", attacker.getShortDesc(), featureClause, hero.getId(),
                    damage, -logicalRemainingHP, hero.getHP(), 0);
        } else {
            sayF("%s%s 直接攻击 <%s>! 伤害: %d. HP: %d -> %d", attacker.getShortDesc(), featureClause, hero.getId(), damage,
                    hero.getHP(), hero.getHP() - damage);
        }
    }

    @Override
    public void useSkill(EntityInfo attacker, List<? extends EntityInfo> victims, Skill cardFeature, boolean bingo) {
        if (victims.isEmpty()) {
            sayF("%s 无法找到使用 %s 的合适目标.", attacker.getShortDesc(), cardFeature.getShortDesc());
        } else {
            List<String> victimTexts = new LinkedList<String>();
            for (EntityInfo victim : victims) {
                victimTexts.add(victim.getShortDesc());
            }
            String victimsText = StringUtils.join(victimTexts, ", ");
            String featureDesc = cardFeature == null ? "【普通攻击】" : cardFeature.getShortDesc();
            sayF("%s 对 { %s } 使用 %s%s!", attacker.getShortDesc(), victimsText, featureDesc, bingo ? "" : " 失败");
        }
    }

    @Override
    public void useSkillToHero(EntityInfo attacker, Player victimHero, Skill cardFeature) {
        String featureDesc = cardFeature == null ? "【普通攻击】" : cardFeature.getShortDesc();
        sayF("%s 对英雄 <%s> 使用 %s!", attacker.getShortDesc(), victimHero.getId(), featureDesc);
    }

    @Override
    public void addCardStatus(EntityInfo attacker, CardInfo victim, Skill cardFeature, CardStatusItem item) {
        sayF("%s.%s 使 %s 得到状态: 【%s】", attacker.getShortDesc(), cardFeature.getShortDesc(), victim.getShortDesc(),
                item.getShortDesc());
    }

    @Override
    public void removeCardStatus(CardInfo card, CardStatusType type) {
        sayF("%s 解除状态 【%s】", card.getShortDesc(), type.name());
    }
    
    @Override
    public void gameEnded(GameResult result) {
        String s = String.format("战斗结束. 胜利者: <%s>, 胜利方式: %s", result.getWinner().getId(), result.getCause().toString());
        if (result.getDamageToBoss() >= 0) {
            s += ", 魔神受到伤害: " + result.getDamageToBoss();
        }
        say(s);
    }
    
    @Override
    public void stageCreated() {
        
    }

    @Override
    public void gameStarted() {
        say("战斗开始!");
        sayF("规则: 最大手牌数=%d, 最大回合=%d", getRule().getMaxHandCards(), getRule().getMaxRound());
        this.showBoard();
    }

    private void showBoard() {
        Board board = this.getBoard();
        say("-----------------------------------------------------------------------------");
        Player player = board.getPlayer(0);
        sayF("玩家: %s - HP: %d", player.getId(), player.getHP());
        showGrave(player.getGrave());
        showDeck(player.getDeck());
        showHand(player.getHand());
        say("");
        showRune(player.getRuneBox());
        showField(player.getField());
        say("");

        player = board.getPlayer(1);
        say("");
        showField(player.getField());
        showRune(player.getRuneBox());
        say("");
        showHand(player.getHand());
        showDeck(player.getDeck());
        showGrave(player.getGrave());
        sayF("玩家: %s - HP: %d", player.getId(), player.getHP());
        say("-----------------------------------------------------------------------------");
    }

    private void showRune(RuneBox runeBox) {
        StringBuffer sb = new StringBuffer();
        sb.append("符文 : ");
        for (RuneInfo rune : runeBox.getRunes()) {
            sb.append(rune.getShortDesc());
            sb.append(", ");
        }
        say(sb.toString());
    }

    private void showGrave(Grave grave) {
        StringBuffer sb = new StringBuffer();
        sb.append("墓地: ");
        for (CardInfo card : grave.toList()) {
            sb.append(String.format("%s (等级=%d, 攻击=%d, HP=%d), ", card.getUniqueName(), card.getLevel(), card.getInitAT(),
                    card.getMaxHP()));
        }
        say(sb.toString());
    }

    private void showField(Field field) {
        StringBuffer sb = new StringBuffer();
        // sb.append("Field: ");
        int i = 0;
        List<CardInfo> cards = field.getAliveCards();
        for (CardInfo card : cards) {
            sb.append(String.format("[%d] %s (等级=%d, 攻击=%d/%d, HP=%d/%d/%d, 状态=%s, 效果=%s)\r\n", i, card.getUniqueName(),
                    card.getLevel(), card.getCurrentAT(), card.getInitAT(), card.getHP(), card.getMaxHP(),
                    card.getOriginalMaxHP(), card.getStatus().getShortDesc(), card.getEffectsDesc()));
            ++i;
        }
        if (cards.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        say(sb.toString());
    }

    private void showHand(Hand hand) {
        StringBuffer sb = new StringBuffer();
        sb.append("手牌: ");
        for (CardInfo card : hand.toList()) {
            sb.append(String.format("%s (等级=%d, 攻击=%d, HP=%d, 等待=%d), ", card.getUniqueName(), card.getLevel(),
                    card.getInitAT(), card.getMaxHP(), card.getSummonDelay()));
        }
        say(sb.toString());
    }

    private void showDeck(Deck deck) {
        StringBuffer sb = new StringBuffer();
        sb.append("牌堆: ");
        for (CardInfo card : deck.toList()) {
            sb.append(String.format("%s (等级=%d, 攻击=%d, HP=%d), ", card.getUniqueName(), card.getLevel(), card.getInitAT(),
                    card.getMaxHP()));
        }
        say(sb.toString());
    }

    @Override
    public void battleBegins() {
        this.showBoard();
    }

    @Override
    public void attackBlocked(EntityInfo attacker, CardInfo defender, Skill atFeature, Skill dfFeature) {
        String attackerDesc = attacker.getShortDesc();
        if (atFeature == null && dfFeature == null) {
            sayF("%s 处于状态 %s 中，无法攻击!", attackerDesc, attacker.getStatus().getShortDesc());
        } else if (atFeature == null && dfFeature != null) {
            sayF("%s 的攻击被 %s 使用 %s 化解了!", attackerDesc, defender.getShortDesc(), dfFeature.getShortDesc());
        } else if (atFeature != null && dfFeature == null) {
            sayF("%s 处于状态 %s 中，无法使用 %s!", attackerDesc, attacker.getStatus().getShortDesc(), atFeature.getShortDesc());
        } else if (atFeature != null && dfFeature != null) {
            sayF("%s 的 %s 被 %s 的 %s 化解了!", attackerDesc, atFeature.getShortDesc(), defender.getShortDesc(),
                    dfFeature.getShortDesc());
        }
    }

    @Override
    public void adjustAT(EntityInfo source, CardInfo target, int adjAT, Skill cardFeature) {
        if (adjAT == 0) {
            return;
        }
        String verb = adjAT > 0 ? "增加" : "降低";
        sayF("%s 使用 %s %s 了 %s 的 %d 点攻击! %d -> %d.", source.getShortDesc(), cardFeature.getShortDesc(), verb,
                target.getShortDesc(), Math.abs(adjAT), target.getCurrentAT(), target.getCurrentAT() + adjAT);
    }

    @Override
    public void adjustHP(EntityInfo source, List<? extends CardInfo> targets, int adjHP, Skill cardFeature) {
        if (adjHP == 0) {
            return;
        }
        String verb = adjHP > 0 ? "增加" : "降低";
        for (CardInfo target : targets) {
            sayF("%s 使用 %s %s 了 %s 的HP! %d -> %d.", source.getShortDesc(), cardFeature.getShortDesc(), verb,
                target.getShortDesc(), target.getHP(), target.getHP() + adjHP);
        }
    }

    @Override
    public void blockDamage(EntityInfo protector, EntityInfo attacker, EntityInfo defender, Skill cardFeature,
            int originalDamage, int actualDamage) {
        sayF("%s 使用 %s 为 %s 格挡了来自 %s 的攻击. 伤害: %d -> %d", protector.getShortDesc(), cardFeature.getShortDesc(),
                defender.getShortDesc(), attacker.getShortDesc(), originalDamage, actualDamage);
    }

    @Override
    public void debuffDamage(CardInfo card, CardStatusItem item, int damage) {
        sayF("%s 处在状态 %s 中受到伤害. 伤害: %d. HP: %d -> %d", card.getShortDesc(), item.getShortDesc(), damage, card.getHP(),
                Math.max(0, card.getHP() - damage));
    }

    @Override
    public void cannotAction(CardInfo card) {
        sayF("%s 处在状态 %s 中，无法行动!", card.getShortDesc(), card.getStatus().getShortDesc());
    }

    @Override
    public void recoverAT(CardInfo card, SkillType cause, int recoveredAT) {
        sayF("%s 的攻击从 【%s】 的效果中恢复. 攻击: %d -> %d", card.getShortDesc(), cause.name(),
                card.getCurrentAT(), card.getCurrentAT() - recoveredAT);
    }

    @Override
    public void healCard(EntityInfo healer, CardInfo healee, Skill cardFeature, int healHP) {
        int postHealHP = healee.getHP() + healHP;
        String healText = String.valueOf(healHP);
        if (postHealHP > healee.getMaxHP()) {
            healText += " (" + (postHealHP - healee.getMaxHP()) + " overflow)";
            postHealHP = healee.getMaxHP();
        }
        sayF("%s 使用 %s 治疗了 %s %s 点HP. HP: %d -> %d", healer.getShortDesc(), cardFeature.getShortDesc(),
                healee.getShortDesc(), healText, healee.getHP(), postHealHP);
    }

    @Override
    public void healHero(EntityInfo healer, Player healee, Skill cardFeature, int healHP) {
        int postHealHP = healee.getHP() + healHP;
        String healText = String.valueOf(healHP);
        if (postHealHP > healee.getMaxHP()) {
            healText += " (" + (postHealHP - healee.getMaxHP()) + " overflow)";
            postHealHP = healee.getMaxHP();
        }
        sayF("%s 使用 %s 治疗了 %s %s 点HP. HP: %d -> %d", healer.getShortDesc(), cardFeature.getShortDesc(),
                healee.getShortDesc(), healText, healee.getHP(), postHealHP);
    }

    @Override
    public void loseAdjustATEffect(CardInfo ally, FeatureEffect effect) {
        sayF("%s 失去由 %s 的 %s 造成的效果. 攻击: %d -> %d.", ally.getShortDesc(), effect.getSource().getShortDesc(), effect
                .getCause().getFeature().getShortDesc(), ally.getCurrentAT(), ally.getCurrentAT() - effect.getValue());
    }

    @Override
    public void loseAdjustHPEffect(CardInfo ally, FeatureEffect effect) {
        int currentHP = ally.getHP() > ally.getMaxHP() - effect.getValue() ? ally.getMaxHP() - effect.getValue() : ally
                .getHP();
        sayF("%s 失去由 %s 的 %s 造成的效果. HP: %d -> %d.", ally.getShortDesc(), effect.getSource().getShortDesc(), effect
                .getCause().getFeature().getShortDesc(), ally.getHP(), currentHP);
    }

    @Override
    public void cardToDeck(Player player, CardInfo card) {
        sayF("%s 被放回 %s 的牌堆.", card.getShortDesc(), player.getShortDesc());
    }

    @Override
    public void cardToHand(Player player, CardInfo card) {
        sayF("%s 被放回 %s 的手牌.", card.getShortDesc(), player.getShortDesc());
    }
    
    @Override
    public void cardToOutField(Player player, CardInfo card) {
        sayF("%s 从 %s 的墓地中被除外.", card.getShortDesc(), player.getShortDesc());
    }

    @Override
    public void healBlocked(EntityInfo healer, CardInfo healee, Skill cardFeature, Skill blockerFeature) {
        if (blockerFeature == null) {
            sayF("%s 处在状态 %s 中，无法被 %s 的 %s 治疗!", healee.getShortDesc(), healee.getStatus().getShortDesc(),
                    healer.getShortDesc(), cardFeature.getShortDesc());
        } else {
            throw new CardFantasyRuntimeException("blockerFeature is not null. To be implemented.");
        }
    }

    @Override
    public void blockStatus(EntityInfo attacker, EntityInfo defender, Skill cardFeature, CardStatusItem item) {
        sayF("%s 免疫 %s 造成 的状态 【%s】", defender.getShortDesc(), cardFeature.getShortDesc(), item.getShortDesc());
    }

    @Override
    public void blockFeature(EntityInfo attacker, EntityInfo defender, Skill cardFeature, Skill attackFeature) {
        sayF("%s 使用 %s 格挡了 %s", defender.getShortDesc(), cardFeature.getShortDesc(), attackFeature.getShortDesc());
    }

    @Override
    public void returnCard(CardInfo attacker, CardInfo defender, Skill cardFeature) {
        sayF("%s 使用 %s 将 %s 送还至牌堆.", attacker.getShortDesc(), cardFeature.getShortDesc(), defender.getShortDesc());
    }

    @Override
    public void cardToGrave(Player player, CardInfo card) {
        sayF("%s 被送至 %s 的墓地", card.getShortDesc(), player.getShortDesc());
    }

    @Override
    public void disableBlock(CardInfo attacker, CardInfo defender, Skill attackFeature, Skill blockFeature) {
        sayF("%s 的 %s 被 %s 的 %s 破解了.", defender.getShortDesc(), blockFeature.getShortDesc(), attacker.getShortDesc(),
                attackFeature.getShortDesc());
    }

    @Override
    public void confused(CardInfo card) {
        sayF("%s 处在状态 %s 中并且攻击了本方英雄!", card.getShortDesc(),
            card.getStatus().getShortDescOfType(CardStatusType.迷惑));
        this.attackHero(card, card.getOwner(), null, card.getCurrentAT());
    }
    
    @Override
    public void softened(CardInfo card) {
        sayF("%s 处在状态 %s 中，攻击力被弱化了一半!", card.getShortDesc(),
            card.getStatus().getShortDescOfType(CardStatusType.弱化));
    }

    @Override
    public void roll100(int dice, int rate) {
        sayF("掷骰子，命中率 %d%%。点数：%d. %s!", rate, dice, dice < rate ? "中" : "不中");
    }

    @Override
    public void useSkill(EntityInfo attacker, Skill cardFeature, boolean bingo) {
        sayF("%s 使用 %s%s", attacker.getShortDesc(), cardFeature.getShortDesc(), bingo ? "" : " 失败");
    }

    @Override
    public void killCard(EntityInfo attacker, CardInfo victim, Skill cardFeature) {
        sayF("%s 使用 %s 直接杀死 %s!", attacker.getShortDesc(), cardFeature.getShortDesc(), victim.getShortDesc());
    }

    @Override
    public void activateRune(RuneInfo rune) {
        sayF("%s 被激活! 剩余发动次数: %d -> %d", rune.getShortDesc(), rune.getEnergy(), rune.getEnergy() - 1);
    }

    @Override
    public void deactivateRune(RuneInfo rune) {
        sayF("%s 被熄灭!", rune.getShortDesc());
    }

    @Override
    public void compactField(Field field) {
        int originalSize = field.size();
        int aliveCardCount = field.getAliveCards().size();
        sayF("整理 %s 的场上卡片... 坑: %d -> %d", field.getOwner().getShortDesc(), originalSize, aliveCardCount);
    }

    @Override
    public void protect(EntityInfo protector, EntityInfo attacker, EntityInfo protectee, Skill attackFeature,
            Skill protectFeature) {
        String attackFeatureText = attackFeature == null ? "【普通攻击】" : attackFeature.getShortDesc();
        sayF("%s 使用 %s 保护 %s 不受来自 %s 的 %s 的侵害", protector.getShortDesc(), protectFeature.getShortDesc(),
                protectee.getShortDesc(), attacker.getShortDesc(), attackFeatureText);
    }

    @Override
    public void showMessage(String text) {
        sayF("【系统】" + text);
    }
    
    @Override
    public void cardActionBegins(CardInfo card) {
        sayF("%s 开始行动...", card.getShortDesc());
    }
    
    @Override
    public void cardActionEnds(CardInfo card) {
        sayF("%s 结束行动.", card.getShortDesc());
    }
    
    @Override
    public void mapStageResult(PveGameResult result) {
        sayF("战斗结果：%s", result.getDescription());
    }
    
    @Override
    public void increaseSummonDelay(CardInfo card, int offset) {
        String verb = offset > 0 ? "增加" : "减少";
        sayF("%s 的等待时间%s %d: %d -> %d", card.getShortDesc(), verb, Math.abs(offset), card.getSummonDelay(), card.getSummonDelay() + offset);
    }
}