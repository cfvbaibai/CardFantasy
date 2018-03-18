package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.*;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Rune;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;

public class BattleEngine {

    private StageInfo stage;

    public StageInfo getStage() {
        return stage;
    }

    public BattleEngine(GameUI ui, Rule rule) {
        this.stage = new StageInfo(new Board(), ui, rule);
    }

    public static GameResult play1v1(GameUI ui, Rule rule, PlayerInfo p1, PlayerInfo p2) {
        BattleEngine engine = new BattleEngine(ui, rule);
        engine.registerPlayers(p1, p2);
        return engine.playGame();
    }

    public List<CardInfo> exportSurvivers(int playerIndex) {
        Player player = this.getStage().getPlayers().get(playerIndex);
        List<CardInfo> result = new ArrayList<CardInfo>();
        for (CardInfo card : player.getField().getAliveCards()) {
            result.add(card);
            card.carveEternalWound();
        }
        for (CardInfo card : player.getDeck().toList()) {
            result.add(card);
        }
        for (CardInfo card : player.getHand().toList()) {
            result.add(card);
        }
        return result;
    }

    /**
     * This method should be called after the user is registered.
     * This method will tag survivors with remaining HP in deck.
     *
     * @param playerIndex
     * @param survivors
     */
    public void importSurvivers(int playerIndex, List<CardInfo> survivors) {
        Player player = this.getStage().getPlayers().get(playerIndex);
        if (player == null) {
            throw new CardFantasyRuntimeException("Cannot find user with index " + playerIndex);
        }
        Deck deck = player.getDeck();
        deck.clear();
        for (CardInfo survivor : survivors) {
            deck.addCard(survivor);
            // Switch to new player.
            survivor.setOwner(player);
        }
    }

    private Player getActivePlayer() {
        return this.stage.getActivePlayer();
    }

    private Player getInactivePlayer() {
        return this.stage.getInactivePlayers().get(0);
    }

    public static void validateDeck(PlayerInfo playerInfo) {
        if (playerInfo.isNormalPlayer() && playerInfo.getLevel() > 150) {
            throw new CardFantasyUserRuntimeException(String.format(
                    "%s 的等级过高：%d！玩家等级不得超过150级。",
                    playerInfo.getId(), playerInfo.getLevel()));
        }
        Collection<Card> cards = playerInfo.getCards();
        Collection<Rune> runes = playerInfo.getRunes();
        if (playerInfo.isNormalPlayer() && cards.size() > playerInfo.getCardSlot()) {
            throw new CardFantasyUserRuntimeException(String.format(
                    "%s 的卡牌槽不足！%s 卡牌槽数：%d, 卡组卡牌数：%d",
                    playerInfo.getId(), playerInfo.getId(), playerInfo.getCardSlot(), cards.size()));
        }
        /*
        if (cards.size() == 0) {
            throw new CardFantasyUserRuntimeException(String.format(
                    "没有为 %s 配置任何卡牌！", playerInfo.getId()));
        }
        */
        if (playerInfo.isNormalPlayer() && runes.size() > playerInfo.getRuneSlot()) {
            throw new CardFantasyUserRuntimeException(String.format(
                    "%s 的符文槽不足！%s 符文槽数：%d, 卡组符文数：%d",
                    playerInfo.getId(), playerInfo.getId(), playerInfo.getRuneSlot(), runes.size()));
        }
        int cost = 0;
        for (Card card : playerInfo.getCards()) {
            cost += card.getCost();
        }
        if (playerInfo.isNormalPlayer() && cost > playerInfo.getMaxCost()) {
            throw new CardFantasyUserRuntimeException(String.format(
                    "%s 的COST不足！%s 的最大COST：%d, 卡组COST: %d",
                    playerInfo.getId(), playerInfo.getId(), playerInfo.getMaxCost(), cost));
        }
    }

    public void registerPlayers(PlayerInfo player1Info, PlayerInfo player2Info) {
        stage.addPlayer(player1Info);
        stage.addPlayer(player2Info);
    }

    public GameResult playGame() {
        this.stage.gameStarted();
        this.stage.setRound(1);
        GameResult result = proceedGame(GameMode.Normal);
        this.stage.getUI().gameEnded(result);
        return result;
    }

    public GameResult proceedOneRound() {
        return proceedGame(GameMode.OneRound);
    }

    private GameResult proceedGame(GameMode gameMode) {
        if (!this.getStage().isStarted()) {
            throw new CardFantasyRuntimeException("战斗未开始");
        }
        if (this.getStage().isEnded()) {
            throw new CardFantasyRuntimeException("战斗已结束");
        }
        Phase phase = Phase.开始;
        Phase nextPhase = Phase.未知;
        try {
            while (true) {
                if (phase == Phase.开始) {
                    nextPhase = roundStart();
                } else if (phase == Phase.抽卡) {
                    nextPhase = drawCard();
                } else if (phase == Phase.召唤) {
                    nextPhase = summonCards();
                } else if (phase == Phase.准备) {
                    nextPhase = standby();
                } else if (phase == Phase.战斗) {
                    nextPhase = battle();
                } else if (phase == Phase.结束) {
                    nextPhase = roundEnd();
                    if (gameMode == GameMode.OneRound) {
                        return stage.result(this.stage.getPlayers().get(0), GameEndCause.一时中断);
                    }
                } else {
                    throw new CardFantasyRuntimeException(String.format("Unknown phase encountered: %s", phase));
                }
                stage.getUI().phaseChanged(getActivePlayer(), phase, nextPhase);
                phase = nextPhase;
                nextPhase = Phase.未知;
            }
        } catch (GameOverSignal signal) {
            this.getStage().setEnded(true);
            return stage.result(this.stage.getPlayers().get(0), GameEndCause.战斗超时);
        } catch (HeroDieSignal signal) {
            this.getStage().setEnded(true);
            return stage.result(getOpponent(signal.getDeadPlayer()), GameEndCause.英雄死亡);
        } catch (AllCardsDieSignal signal) {
            this.getStage().setEnded(true);
            return stage.result(getOpponent(signal.getDeadPlayer()), GameEndCause.卡片全灭);
        } catch (CardFantasyRuntimeException e) {
            throw new CardFantasyRuntimeException(e.getMessage() + System.lineSeparator() + this.getStage().getBoard().getBoardInText(), e);
        }
    }

    private Player getOpponent(Player player) {
        return this.getActivePlayer() == player ? this.getInactivePlayer() : this.getActivePlayer();
    }

    private Phase summonCards() throws HeroDieSignal {
        Player player = this.getActivePlayer();
        this.stage.getResolver().setStagePhase(Phase.召唤);
        for (CardInfo card : this.getActivePlayer().getField().getAliveCards()) {
            this.stage.getResolver().removeStatus(card, CardStatusType.复活);
        }
        this.stage.getResolver().summonCards(player, null, false);

        player.getField().compact();
        this.getInactivePlayer().getField().compact();


        return Phase.准备;
    }

    private Phase standby() throws HeroDieSignal {
        this.stage.getResolver().activateRunes(this.getActivePlayer(), this.getInactivePlayer());
        this.stage.getResolver().resolvePreAttackRune(this.getActivePlayer(), this.getInactivePlayer());
        this.stage.getResolver().resolvePreAttackSkills(this.getActivePlayer(), this.getInactivePlayer());
        for (CardInfo card : this.getActivePlayer().getHand().toList()) {
            this.stage.getResolver().resolvePrecastSkills(card, this.getInactivePlayer(),true);
        }
        for (CardInfo card : this.getActivePlayer().getField().getAliveCards()) {
            this.stage.getResolver().removeStatus(card, CardStatusType.复活);
        }
        return Phase.战斗;
    }

    private Phase roundEnd() throws HeroDieSignal {
        this.stage.getResolver().setStagePhase(Phase.结束);
        for (CardInfo card : this.getActivePlayer().getGrave().toList()) {
            this.stage.getResolver().resolvePostcastSkills(card, this.getInactivePlayer());
        }
        for(CardInfo defenderCard : this.getInactivePlayer().getField().getAliveCards())
        {
            this.stage.getResolver().removeGiveSkills(defenderCard);
        }
        Collection<CardInfo> allHandCards = this.stage.getAllHandCards();
        for (CardInfo card : allHandCards) {
            int summonDelay = card.getSummonDelay();
            if (summonDelay > 0) {
                card.setSummonDelay(summonDelay - 1);
            }
        }

        Player previousPlayer = getActivePlayer();
        int thisRound = stage.getRound();
        this.stage.setRound(thisRound + 1);
        this.stage.getUI().roundEnded(previousPlayer, thisRound);

        if (Global.isDebugging()) {
            this.getStage().getBoard().validate();
        }

        int nextPlayerNumber = (this.stage.getActivePlayerNumber() + 1) % stage.getPlayerCount();
        this.stage.setActivePlayerNumber(nextPlayerNumber);
        Player nextPlayer = this.getActivePlayer();
        stage.getUI().playerChanged(previousPlayer, nextPlayer);
        return Phase.开始;
    }

    private Phase battle() throws HeroDieSignal {
        /***
         * Algorithm: For each card in field of active user: Check whether
         * target player has a card in field in the same position - Yes: Attack.
         * Card died? - Yes: Move card to grave. Leave an empty position. - No:
         * Go on. - No: Attack Hero. Trigger hero HP check. Remove all empty
         * position in fields.
         */

        SkillResolver resolver = stage.getResolver();
        this.stage.getResolver().setStagePhase(Phase.战斗);
        GameUI ui = stage.getUI();

        ui.battleBegins();
        Field myField = getActivePlayer().getField();
        Field opField = getInactivePlayer().getField();

        this.stage.getUI().compactField(myField);
        myField.compact();
        this.stage.getUI().compactField(opField);
        opField.compact();

        for (int i = 0; i < opField.size(); ++i) {
            if (opField.getCard(i) != null) {
                resolver.removeStatus(opField.getCard(i), CardStatusType.魔印);
            }
        }
        for (int i = 0; i < myField.size(); ++i) {
            if (myField.getCard(i) == null) {
                continue;
            }

            CardInfo card = myField.getCard(i);
            resolver.removeStatus(card, CardStatusType.死印);
            ui.cardActionBegins(card);
            CardStatus status = myField.getCard(i).getStatus();
            boolean underControl = false;
            List<CardStatusItem> softenedStatusItems = status.getStatusOf(CardStatusType.弱化);
            if (!softenedStatusItems.isEmpty()) {
                CardInfo myCard = myField.getCard(i);
                int currentBaseAT = myCard.getLevel1AT();
                // 允许多重弱化
                for (int j = 0; j < softenedStatusItems.size(); ++j) {
                    SkillUseInfo skillUseInfo = softenedStatusItems.get(j).getCause();
                    ui.softened(myCard);
                    int adjAT = -currentBaseAT / 2;
                    if(skillUseInfo.getType() == SkillType.常夏日光||skillUseInfo.getType() == SkillType.碎裂怒吼)
                    {
                        adjAT = -currentBaseAT;
                        currentBaseAT = 0;
                    }
                    ui.adjustAT(skillUseInfo.getOwner(), myCard, adjAT, skillUseInfo.getSkill());
                    myField.getCard(i).addEffect(
                            new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
                    currentBaseAT /= 2;
                }
            }
            if (status.containsStatus(CardStatusType.迷惑) ||
                    status.containsStatus(CardStatusType.冰冻) ||
                    status.containsStatus(CardStatusType.锁定) ||
                    status.containsStatus(CardStatusType.复活) ||
                    status.containsStatus(CardStatusType.晕眩)) {
                underControl = true;

                if (status.containsStatus(CardStatusType.迷惑)) {
                    ui.confused(myField.getCard(i));
                    resolver.resolvePreAttackHeroSkills(myField.getCard(i), getActivePlayer());
                    resolver.attackHero(myField.getCard(i), getActivePlayer(), null, myField.getCard(i).getCurrentAT());
                } else {
                    ui.cannotAction(myField.getCard(i));
                }

                resolver.removeStatus(myField.getCard(i), CardStatusType.迷惑);
                resolver.removeStatus(myField.getCard(i), CardStatusType.冰冻);
                resolver.removeStatus(myField.getCard(i), CardStatusType.锁定);
                resolver.removeStatus(myField.getCard(i), CardStatusType.麻痹);
                resolver.removeStatus(myField.getCard(i), CardStatusType.晕眩);
                resolver.removeStatus(myField.getCard(i), CardStatusType.致盲);
                resolver.removeStatus(myField.getCard(i), CardStatusType.不屈);
            } else {
                tryAttackEnemy(myField, opField, i);
            }

            // Remove AdjustAT effects caused by 虚弱 or 战争怒吼
            if (myField.getCard(i) != null) {
                CardInfo myCard = myField.getCard(i);
                List<SkillEffect> effects = myCard.getEffectsCausedBy(SkillType.虚弱);
                effects.addAll(myCard.getEffectsCausedBy(SkillType.战争怒吼));
                effects.addAll(myCard.getEffectsCausedBy(SkillType.碎裂怒吼));
                effects.addAll(myCard.getEffectsCausedBy(SkillType.常夏日光));
                for (SkillEffect effect : effects) {
                    resolver.getStage().getUI().loseAdjustATEffect(myCard, effect);
                    myCard.removeEffect(effect);
                }
            }

            // TODO: This is an ugly hack. We should revisit the logic of revival.
            if (!status.containsStatus(CardStatusType.复活)) {
                resolver.removeStatus(myField.getCard(i), CardStatusType.麻痹);
            }
            resolver.removeStatus(myField.getCard(i), CardStatusType.弱化);
            resolver.removeStatus(myField.getCard(i), CardStatusType.王国);
            resolver.removeStatus(myField.getCard(i), CardStatusType.森林);
            resolver.removeStatus(myField.getCard(i), CardStatusType.蛮荒);
            resolver.removeStatus(myField.getCard(i), CardStatusType.地狱);
            resolver.removeStatus(myField.getCard(i), CardStatusType.致盲);
            resolver.resolveDebuff(myField.getCard(i), CardStatusType.中毒);
            resolver.resolveDebuff(myField.getCard(i), CardStatusType.燃烧);
            resolver.removeStatus(myField.getCard(i), CardStatusType.沉默);
            resolver.removeStatus(myField.getCard(i), CardStatusType.死咒);
            if (status.containsStatus(CardStatusType.变羊)) {
                //变羊类技能恢复原状
                List<CardStatusItem>  sheepStatus= status.getStatusOf(CardStatusType.变羊);
                {
                    for(CardStatusItem statusItem:sheepStatus)
                    {
                        if(myField.getCard(i)==null)
                        {
                            break;
                        }
                        for (SkillEffect effect : myField.getCard(i).getEffectsCausedBy(statusItem.getCause())) {
                            if (effect.getType() == SkillEffectType.ATTACK_CHANGE) {
                                resolver.getStage().getUI().loseAdjustATEffect(myField.getCard(i), effect);
                            } else if (effect.getType() == SkillEffectType.MAXHP_CHANGE) {
                                myField.getCard(i).setBasicHP(statusItem.getCause().getSkill().getImpact2());
                                resolver.getStage().getUI().loseAdjustHPEffect(myField.getCard(i), effect);
                            } else {
                                throw new CardFantasyRuntimeException("Invalid effect type: " + effect.getType().name());
                            }
                            myField.getCard(i).removeEffect(effect);
                        }
                    }
                }
                resolver.removeStatus(myField.getCard(i), CardStatusType.变羊);
            }

            if (!underControl) {
                // 回春
                resolver.resolveCardRoundEndingSkills(myField.getCard(i));
            }

            // 解除状态
            resolver.removeStatus(myField.getCard(i), CardStatusType.中毒);
            ui.cardActionEnds(card);
        }

        this.stage.getUI().compactField(myField);
        myField.compact();
        this.stage.getUI().compactField(opField);
        opField.compact();

        return Phase.结束;
    }

    private void tryAttackEnemy(Field myField, Field opField, int i) throws HeroDieSignal {
        SkillResolver resolver = this.stage.getResolver();
        if (myField.getCard(i) == null) {
            return;
        }
        List<CardInfo> cards = new ArrayList<CardInfo>();
        cards.add(myField.getCard(i));
        //二段技能发动时机改变
//        resolver.resolveSecondClassSummoningSkills(cards, myField, opField, null, false);
        if (myField.getCard(i) == null) {
            return;
        }
        resolver.resolvePreAttackSkills(myField.getCard(i), getInactivePlayer(),0);
        if (myField.getCard(i) == null) {
            return;
        }
        if (opField.getCard(i) == null) {
            resolver.resolvePreAttackHeroSkills(myField.getCard(i), getInactivePlayer());
            resolver.attackHero(myField.getCard(i), getInactivePlayer(), null, myField.getCard(i).getCurrentAT());
            if (myField.getCard(i)!=null&&(myField.getCard(i).containsUsableSkill(SkillType.连斩)||myField.getCard(i).containsUsableSkill(SkillType.原素裂变)||myField.getCard(i).containsUsableSkill(SkillType.死亡收割))) {
                boolean killCard = true;
                for(;killCard;) {
                    killCard = randomAttackCard(myField, opField, i);
                    if (myField.getCard(i) == null || myField.getCard(i).isDead()){
                        killCard = false;
                    }
                }
            }
            resolver.removeStatus(myField.getCard(i), CardStatusType.不屈);
        } else {
            tryAttackCard(myField, opField, i);
        }

        // Remove lasting effects
        resolver.removeTempEffects(myField.getCard(i));
        //
        resolver.resolvePostAttackSkills(myField.getCard(i), getInactivePlayer());
    }

    private void tryAttackCard(Field myField, Field opField, int i) throws HeroDieSignal {
        SkillResolver resolver = this.stage.getResolver();
        resolver.resolvePreAttackCardSkills(myField.getCard(i), opField.getCard(i));
        if (opField.getCard(i) == null) {
            resolver.resolvePreAttackHeroSkills(myField.getCard(i), getInactivePlayer());
            resolver.attackHero(myField.getCard(i), getInactivePlayer(), null, myField.getCard(i).getCurrentAT());
            resolver.removeStatus(myField.getCard(i), CardStatusType.不屈);
        } else {
            processAttackCard(myField, opField, i,true);
            if (myField.getCard(i) != null && !myField.getCard(i).isDead() &&
                    opField.getCard(i) != null && !opField.getCard(i).isDead()) {
                if (myField.getCard(i)!=null&&myField.getCard(i).containsUsableSkill(SkillType.连击) || myField.getCard(i).containsUsableSkill(SkillType.刀语)|| myField.getCard(i).containsUsableSkill(SkillType.正义追击)) {
                    processAttackCard(myField, opField, i,false);
                }
            }
            if (myField.getCard(i) != null && !myField.getCard(i).isDead() &&
                        (opField.getCard(i) == null || opField.getCard(i).isDead())) {
                if (myField.getCard(i)!=null&&(myField.getCard(i).containsUsableSkill(SkillType.连斩)||myField.getCard(i).containsUsableSkill(SkillType.原素裂变)||myField.getCard(i).containsUsableSkill(SkillType.死亡收割))) {
                    boolean killCard = true;
                    for(;killCard;) {
                        killCard = randomAttackCard(myField, opField, i);
                        if (myField.getCard(i) == null || myField.getCard(i).isDead()){
                            killCard = false;
                        }
                    }
               }
            }
        }
    }

    private void processAttackCard(Field myField, Field opField, int i,boolean attackflag) throws HeroDieSignal {
        CardInfo defender = opField.getCard(i);
        SkillResolver resolver = this.stage.getResolver();
        GameUI ui = this.stage.getUI();
        if (myField.getCard(i).getStatus().containsStatus(CardStatusType.麻痹)) {
            resolver.removeStatus(myField.getCard(i), CardStatusType.麻痹);
            resolver.removeStatus(myField.getCard(i), CardStatusType.不屈);
            return;
        }
        for (SkillUseInfo skillUseInfo : myField.getCard(i).getUsableNormalSkills()) {
            if (skillUseInfo.getType() == SkillType.横扫 ||
                    skillUseInfo.getType() == SkillType.灵击 ||
                    skillUseInfo.getType() == SkillType.三千世界 ||
                    skillUseInfo.getType() == SkillType.魔龙之怒 ||
                    skillUseInfo.getType() == SkillType.鬼彻 ||
                    skillUseInfo.getType() == SkillType.毒杀) {
                ui.useSkill(myField.getCard(i), defender, skillUseInfo.getSkill(), true);
            }
            else if (skillUseInfo.getType() == SkillType.一文字) {
                ui.useSkill(myField.getCard(i), defender, skillUseInfo.getSkill(), true);
            }
        }
        CardInfo taunt = tauntCard(opField);
        OnDamagedResult damagedResult =null;
        if (taunt!=null&&attackflag)
        {
            Skill skill = null;
            for(SkillUseInfo skillUseInfo: taunt.getUsableNormalSkills())
            {
                if(skillUseInfo.getType() == SkillType.嘲讽 || skillUseInfo.getType() == SkillType.酒池肉林)
                {
                    skill = skillUseInfo.getSkill();
                }
            }
            ui.useSkill(taunt,defender,skill,true);
            damagedResult = resolver.attackCard(myField.getCard(i), taunt, null);
        }
        else {
             damagedResult = resolver.attackCard(myField.getCard(i), defender, null);
        }
        if (damagedResult != null && damagedResult.originalDamage > 0 && myField.getCard(i) != null&&attackflag) {
            for (SkillUseInfo skillUseInfo : myField.getCard(i).getUsableNormalSkills()) {
                if (skillUseInfo.getType() == SkillType.横扫 ||
                        skillUseInfo.getType() == SkillType.三千世界 ||
                        skillUseInfo.getType() == SkillType.魔龙之怒 ||
                        skillUseInfo.getType() == SkillType.鬼彻 ||
                        skillUseInfo.getType() == SkillType.灵击 ||
                        skillUseInfo.getType() == SkillType.毒杀) {
                    List<CardInfo> sweepDefenders = new ArrayList<CardInfo>();
                    if (i > 0 && opField.getCard(i - 1) != null) {
                        sweepDefenders.add(opField.getCard(i - 1));
                    }
                    if (opField.getCard(i + 1) != null) {
                        sweepDefenders.add(opField.getCard(i + 1));
                    }

                    for (CardInfo sweepDefender : sweepDefenders) {
                        if(!sweepDefender.isAlive())
                        {
                            continue;
                        }
                        //木盒修改嘲讽卡牌对横扫不生效。
//                        CardInfo tauntTwo = tauntCard(opField);
//                        if (tauntTwo!=null)
//                        {
//                            Skill skill = null;
//                            for(SkillUseInfo skillUseInfoTaunt: tauntTwo.getUsableNormalSkills())
//                            {
//                                if(skillUseInfoTaunt.getType() == SkillType.嘲讽 || skillUseInfo.getType() == SkillType.酒池肉林)
//                                {
//                                    skill = skillUseInfoTaunt.getSkill();
//                                }
//                            }
//                            ui.useSkill(tauntTwo,defender,skill,true);
//                            ui.useSkill(myField.getCard(i), tauntTwo, skillUseInfo.getSkill(), true);
//                            resolver.attackCard(myField.getCard(i), tauntTwo, skillUseInfo, damagedResult.originalDamage);
//                        }
//                        else {
                            ui.useSkill(myField.getCard(i), sweepDefender, skillUseInfo.getSkill(), true);
                            resolver.attackCard(myField.getCard(i), sweepDefender, skillUseInfo, damagedResult.originalDamage);
//                        }
                        // Physical attack cannot proceed if attacker is killed by counter attack skills.
                        if (myField.getCard(i) == null) {
                            break;
                        }
                    }
                    break;
                }
            }
        }
        if (damagedResult != null && damagedResult.originalDamage > 0 && myField.getCard(i) != null&&attackflag) {
            for (SkillUseInfo skillUseInfo : myField.getCard(i).getUsableNormalSkills()) {
                if (skillUseInfo.getType() == SkillType.一文字) {
                    for (CardInfo sweepDefender : opField.getAliveCards()) {
                        //一文字可以攻击自己。
//                        if(sweepDefender == opField.getCard(i))
//                        {
//                            continue;
//                        }
                        if(!sweepDefender.isAlive())
                        {
                            continue;
                        }
                        ui.useSkill(myField.getCard(i), sweepDefender, skillUseInfo.getSkill(), true);
                        resolver.attackCard(myField.getCard(i), sweepDefender, skillUseInfo, damagedResult.originalDamage);
                        if (myField.getCard(i) == null ||myField.getCard(i).isDead()) {
                            break;
                        }
                    }
                }
            }
        }

    }

    private boolean randomAttackCard(Field myField, Field opField, int i)throws HeroDieSignal {
        Randomizer random = stage.getRandomizer();
        SkillResolver resolver = this.stage.getResolver();
        if (myField.getCard(i).getStatus().containsStatus(CardStatusType.麻痹)) {
            resolver.removeStatus(myField.getCard(i), CardStatusType.麻痹);
            return false;
        }
        if(opField.size()>0)
        {
            List<CardInfo> victims = random.pickRandom(opField.toList(), 1, true, null);
            for (CardInfo victim : victims) {
                CardInfo defender = victim;
                resolver.attackCard(myField.getCard(i), defender, null);
                if (defender.isDead()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private Phase roundStart() throws GameOverSignal, AllCardsDieSignal, HeroDieSignal {
        if (this.stage.getRound() > stage.getRule().getMaxRound()) {
            throw new GameOverSignal();
        }
        Player player = this.getActivePlayer();
        if (player.getHP() <= 0) {
            throw new HeroDieSignal(player);
        }
        if (player.getDeck().size() == 0 && player.getField().size() == 0 && player.getHand().size() == 0) {
            throw new AllCardsDieSignal(player);
        }

        this.stage.getResolver().deactivateRunes(player);
        this.stage.getResolver().setStagePhase(Phase.开始);
        this.stage.getUI().roundStarted(player, this.stage.getRound());
        int thresholdRound = 51;

        if (this.stage.getRound() >= thresholdRound) {
            int extraRound = this.stage.getRound() - thresholdRound;
            int heroDamage = 50 + extraRound * 30;
            Skill skill = Skill.自动扣血();
            this.stage.getResolver().attackHero(player, player, skill, heroDamage);
        }
        if (player.getHP() <= 0) {
            throw new HeroDieSignal(player);
        }
        return Phase.抽卡;
    }

    private Phase drawCard() {
        Player activePlayer = this.getActivePlayer();
        this.stage.getResolver().setStagePhase(Phase.抽卡);
        Hand hand = activePlayer.getHand();
        if (hand.size() >= this.stage.getRule().getMaxHandCards()) {
            stage.getUI().cantDrawHandFull(activePlayer);
            return Phase.召唤;
        }
        Deck deck = activePlayer.getDeck();
        if (deck.isEmpty()) {
            stage.getUI().cantDrawDeckEmpty(activePlayer);
            return Phase.召唤;
        }
        CardInfo newCard = deck.draw();
        hand.addCard(newCard);
        stage.getUI().cardDrawed(activePlayer, newCard);
        return Phase.召唤;
    }

    private CardInfo tauntCard(Field opField){
        if(opField.size()>0) {
            for (CardInfo card : opField.getAliveCards()) {
                if(card.containsUsableSkill(SkillType.嘲讽)||card.containsUsableSkill(SkillType.酒池肉林))
                {
                    return card;
                }
            }
        }
        return  null;

    }
}