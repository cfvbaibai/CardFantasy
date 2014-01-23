package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.PlayerInfo;

public class StageInfo {
    private Board board;
    private int activePlayerNumber;
    private Phase phase;
    private int round;
    private GameUI ui;
    private Rule rule;
    private FeatureResolver resolver;
    private Randomizer randomizer;

    public StageInfo(Board board, GameUI ui, Rule rule) {
        this.board = board;
        this.activePlayerNumber = -1;
        this.phase = Phase.未知;
        this.round = 1;
        this.ui = ui;
        this.rule = rule;
        this.resolver = new FeatureResolver(this);
        this.randomizer = new Randomizer(ui);
        
        this.ui.stageCreated();
    }

    public FeatureResolver getResolver() {
        return this.resolver;
    }

    public int getActivePlayerNumber() {
        return activePlayerNumber;
    }

    public void setActivePlayerNumber(int activePlayerNumber) {
        this.activePlayerNumber = activePlayerNumber;
    }

    public Player getActivePlayer() {
        return this.getBoard().getPlayer(this.getActivePlayerNumber());
    }

    public List<Player> getInactivePlayers() {
        List<Player> inactivePlayers = new ArrayList<Player>(this.getBoard().getPlayerCount() - 1);
        for (int i = 0; i < this.getBoard().getPlayerCount(); ++i) {
            if (i != this.getActivePlayerNumber()) {
                inactivePlayers.add(this.getBoard().getPlayer(i));
            }
        }
        return inactivePlayers;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public GameUI getUI() {
        return this.ui;
    }

    public void setUI(GameUI ui) {
        this.ui = ui;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    private Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void addPlayer(PlayerInfo playerInfo) {
        Player player = new Player(playerInfo, this);
        int playerNumber = this.board.addPlayer(player);
        this.ui.playerAdded(player, playerNumber);
    }

    public int getPlayerCount() {
        return this.board.getPlayerCount();
    }

    public List<Player> getPlayers() {
        return this.board.getPlayers();
    }

    public void gameStarted() {
        int firstPlayer = determineFirstPlayer();
        this.ui.showMessage(this.getPlayers().get(firstPlayer).getShortDesc() + "先攻");
        this.ui.gameStarted(this.getBoard(), this.getRule());
        this.activePlayerNumber = firstPlayer;
    }
    
    private int determineFirstPlayer() {
        int firstPlayer = this.getRule().getFirstPlayer();
        if (firstPlayer != -1) {
            return firstPlayer;
        }
        List<Player> players = this.board.getPlayers();
        List<Integer> possibleFirstAttackers = new ArrayList<Integer>();
        int maxScore = 0;
        for (int i = 0; i < players.size(); ++i) {
            Player player = players.get(i);
            int sumCardAT = 0;
            int sumCardHP = 0;
            List<CardInfo> cards = player.getDeck().getCards();
            for (CardInfo card : cards) {
                sumCardAT += card.getLevel0AT();
                sumCardHP += card.getOriginalMaxHP();
            }
            int score = player.getMaxHP() + sumCardAT + sumCardHP;
            if (score > maxScore) {
                possibleFirstAttackers.clear();
                possibleFirstAttackers.add(i);
                maxScore = score;
            } else if (score == maxScore) {
                possibleFirstAttackers.add(i);
            }
        }
        int firstAttackerIndex = randomizer.next(0, possibleFirstAttackers.size());
        return possibleFirstAttackers.get(firstAttackerIndex);
    }

    public GameResult result(Player winner, GameEndCause cause) {
        int damageToBoss = -1;
        if (this.getRule().isBossBattle()) {
            Player boss = this.getPlayers().get(0);
            CardInfo bossCard = boss.getField().getCard(0);
            if (bossCard == null) {
                // boss is killed!
                bossCard = boss.getGrave().getFirst();
                if (bossCard == null) {
                    damageToBoss = 99999999;
                } else {
                    damageToBoss = bossCard.getOriginalMaxHP();
                }
            } else {
                damageToBoss = bossCard.getOriginalMaxHP() - bossCard.getHP();
            }
        }
        return new GameResult(this.getBoard(), winner, this.getRound(), cause, damageToBoss, null);
    }

    public Collection<CardInfo> getAllHandCards() {
        return this.board.getAllHandCards();
    }

    public Randomizer getRandomizer() {
        return randomizer;
    }

    public Player getOpponent(Player currentPlayer) {
        if (this.getPlayerCount() != 2) {
            throw new CardFantasyRuntimeException("Invalid operation. Player count is " + this.getPlayerCount());
        }
        for (Player player : this.getPlayers()) {
            if (!player.getId().equals(currentPlayer.getId())) {
                return player;
            }
        }
        throw new CardFantasyRuntimeException("Should not reach here!");
    }
}