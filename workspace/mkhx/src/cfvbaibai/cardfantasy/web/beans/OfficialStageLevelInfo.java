package cfvbaibai.cardfantasy.web.beans;

import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.officialdata.OfficialCard;
import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialReadyCard;
import cfvbaibai.cardfantasy.officialdata.OfficialReadyRune;
import cfvbaibai.cardfantasy.officialdata.OfficialRune;
import cfvbaibai.cardfantasy.officialdata.OfficialSkill;
import cfvbaibai.cardfantasy.officialdata.OfficialStageLevel;

public class OfficialStageLevelInfo {
    private OfficialStageLevel level;
    private OfficialDataStore store;
    public OfficialStageLevelInfo(OfficialStageLevel level, OfficialDataStore store) {
        this.level = level;
        this.store = store;
    }
    public String getName() {
        switch (level.Level) {
        case 1: return "简单";
        case 2: return "普通";
        case 3: return "困难";
        default: return "未知";
        }
    }
    public int getWinBonusGold() {
        return Integer.parseInt(level.BonusWin.split(",")[1].split("_")[1]);
    }
    public int getWinBonusExp() {
        return Integer.parseInt(level.BonusWin.split(",")[0].split("_")[1]);
    }
    public int getLoseBonusGold() {
        return Integer.parseInt(level.BonusLose.split(",")[1].split("_")[1]);
    }
    public int getLoseBonusExp() {
        return Integer.parseInt(level.BonusLose.split(",")[0].split("_")[1]);
    }
    public int getExploreBonusGold() {
        return Integer.parseInt(level.BonusExplore.split(",")[1].split("_")[1]);
    }
    public int getExploreBonusExp() {
        return Integer.parseInt(level.BonusExplore.split(",")[0].split("_")[1]);
    }
    public String getWinConditionText() {
        return level.AchievementText;
    }
    public int getHeroHp() {
        return PlayerInfo.getHpAtLevel(level.HeroLevel);
    }

    public OfficialStageDeckInfo getDeckInfo() {
        OfficialStageDeckInfo deckInfo = new OfficialStageDeckInfo();
        String[] cardDescParts = level.CardList.split(",");
        for (String cardDesc : cardDescParts) {
            if (cardDesc.equals("")) {
                continue;
            }
            String[] cardParts = cardDesc.split("_");
            int cardId = Integer.parseInt(cardParts[0]);
            OfficialCard card = this.store.getCardById(cardId);
            int cardLevel = Integer.parseInt(cardParts[1]);
            OfficialSkill extraSkill = null;
            if (cardParts.length > 2) {
                String extraSkillId = cardParts[2];
                extraSkill = this.store.getSkillById(extraSkillId);
            }
            deckInfo.addCard(new OfficialReadyCard(card, cardLevel, extraSkill));
        }
        String[] runeDescParts = level.RuneList.split(",");
        for (String runeDesc : runeDescParts) {
            if (runeDesc.equals("")) {
                continue;
            }
            String[] runeParts = runeDesc.split("_");
            int runeId = Integer.parseInt(runeParts[0]);
            OfficialRune rune = this.store.getRuneById(runeId);
            int runeLevel = Integer.parseInt(runeParts[1]);
            deckInfo.addRune(new OfficialReadyRune(rune, runeLevel));
        }
        return deckInfo;
    }

    public FirstWinBonus getFirstWinBonus() {
        if (level.FirstBonusWin == null || level.FirstBonusWin.isEmpty()) {
            return null;
        }
        String[] parts = level.FirstBonusWin.split("_");
        FirstWinBonus firstWinBonus = new FirstWinBonus();
        firstWinBonus.setId(Integer.parseInt(parts[1]));
        firstWinBonus.setTypeName(parts[0]);
        if (parts[0].equalsIgnoreCase("Card")) {
            OfficialCard card = store.getCardById(firstWinBonus.getId());
            if (card != null) {
                firstWinBonus.setObject(card);
                firstWinBonus.setName(card.getCardName());
            }
        } else if (parts[0].equalsIgnoreCase("Rune")) {
            OfficialRune rune = store.getRuneById(firstWinBonus.getId());
            if (rune != null) {
                firstWinBonus.setObject(rune);
                firstWinBonus.setName(rune.getRuneName());
            }
        }
        return firstWinBonus;
    }
}
