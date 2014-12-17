package cfvbaibai.cardfantasy.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import cfvbaibai.cardfantasy.DeckBuildRuntimeException;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.CardFeature;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Rune;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Zht2Zhs;

public final class DeckBuilder {

    private static CardDataStore store = CardDataStore.loadDefault();

    /**
     * C卡片名-等级+技能名技能等级*数量
     */
    private final static String CARD_REGEX = 
        "^" +
        "(?<CardName>[^\\-+SD*]+)" +
        "(\\+(?<SummonFlag>(S|降临)?)(?<DeathFlag>(D|死契)?)" +
        "(?<ExtraFeatureName>[^\\d\\-*]+)(?<ExtraFeatureLevel>\\d+)?)?" +
        "(\\-(?<CardLevel>\\d+))?" +
        "(\\*(?<Count>\\d+))?" +
        "$";

    private final static Pattern CARD_PATTERN = Pattern.compile(CARD_REGEX);

    public static DeckStartupInfo multiBuild(String descsText) {
        descsText = descsText.replace(' ', ',').replace('　', ',').replace('，', ',');
        descsText = descsText.replace("\r\n", ",").replace("\n", ",");
        descsText = descsText.replace('＋', '+').replace('＊', '*').replace('－', '-');
        descsText = Zht2Zhs.getInstance().convert(descsText);
        String[] descs = descsText.split(",");
        return build(descs);
    }

    public static DeckStartupInfo build(String ... descs) {
        DeckStartupInfo deck = new DeckStartupInfo();
        for (String desc : descs) {
            if (desc == null || desc.length() == 0) {
                continue;
            }
            desc = desc.trim();
            if (desc.length() > 1 && desc.charAt(0) == 'C') {
            	parseAndAddCard(deck, desc.substring(1));
            } else if (desc.length() > 1 && desc.charAt(0) == 'R') {
            	parseAndAddRune(deck, desc.substring(1));
            } else {
                if (!parseAndAddCard(deck, desc)) {
                    if (!parseAndAddRune(deck, desc)) {
                        throw new DeckBuildRuntimeException("无效的卡牌或符文: " + desc);
                    }
                }
            }
        }
        return deck;
    }
    
    private static boolean parseAndAddRune(DeckStartupInfo deck, String desc) {
        Rune rune = parseRuneDesc(desc);

        if (rune == null)
            return false;

        deck.addRune(rune);
        return true;
    }

    public static Rune parseRuneDesc(String desc) {
        String runeDesc = desc;
        int iDash = runeDesc.indexOf('-');
        int runeLevel = 4;
        String runeName = runeDesc;
        if (iDash >= 0) {
            try {
                runeLevel = Integer.parseInt(runeDesc.substring(iDash + 1));
            } catch (NumberFormatException e) {
                throw new DeckBuildRuntimeException("无效的符文: " + runeDesc, e);
            }
            runeName = runeDesc.substring(0, iDash);
        }
        
        RuneData runeData = null;
        try {
            runeData = RuneData.valueOf(runeName);
        } catch (IllegalArgumentException e) {
            return null;
        }

        Rune rune = new Rune(runeData, 0);
        rune.growToLevel(runeLevel);
        return rune;
    }
    
    private static long cardSuffixId = 0;
    
    private static boolean parseAndAddCard(DeckStartupInfo deck, String desc) {
        List<Card> cards = parseCardDesc(desc);

        if (cards == null || cards.isEmpty())
            return false;

        for (Card c : cards)
            deck.addCard(c);

        return true;
    }

    /**
     * Card description text pattern:
     * C卡片名-等级+技能名技能等级*数量
     * Example: C金属巨龙-10+暴风雪1*5
     * @param deck
     * @param desc
     */
    public static List<Card> parseCardDesc(String desc) {
        List<Card> ret = new ArrayList<Card>();

        String cardDesc = desc;
        Matcher matcher = CARD_PATTERN.matcher(cardDesc);
        if (!matcher.matches()) {
            throw new DeckBuildRuntimeException("无效的卡牌: " + desc);
        }
        String cardName = matcher.group("CardName");
        String cardLevelText = matcher.group("CardLevel");
        int cardLevel = 10;
        if (cardLevelText != null) {
            try {
                cardLevel = Integer.parseInt(cardLevelText);
            } catch (NumberFormatException e) {
                throw new DeckBuildRuntimeException("无效的卡牌: " + desc, e);
            }
        }
        String extraFeatureName = matcher.group("ExtraFeatureName");
        FeatureType extraFeatureType = null;
        if (extraFeatureName != null) {
            try {
                extraFeatureType = FeatureType.valueOf(extraFeatureName);
            } catch (IllegalArgumentException e) {
                throw new DeckBuildRuntimeException("无效的卡牌: " + desc, e);
            }
        }
        if (extraFeatureType != null && cardLevelText == null) {
            cardLevel = 15;
        }
        String extraFeatureLevelText = matcher.group("ExtraFeatureLevel");
        int extraFeatureLevel = 0;
        if (extraFeatureLevelText != null) {
            try {
                extraFeatureLevel = Integer.parseInt(extraFeatureLevelText);
            } catch (NumberFormatException e) {
                throw new DeckBuildRuntimeException("无效的卡牌: " + desc, e);
            }
        }
        if (extraFeatureLevel < 0 || extraFeatureLevel > 10) {
            throw new DeckBuildRuntimeException("无效的卡牌：" + desc + "，洗炼技能等级不得大于10");
        }
       
        boolean summonFeature = !StringUtils.isBlank(matcher.group("SummonFlag"));
        boolean deathFeature = !StringUtils.isBlank(matcher.group("DeathFlag"));
        String countText = matcher.group("Count");
        int count = 1;
        if (countText != null) {
            try {
                count = Integer.parseInt(countText);
            } catch (NumberFormatException e) {
                throw new DeckBuildRuntimeException("无效的卡牌: " + desc, e);
            }
        }

        CardData data = store.getCardInfo(cardName);
        if (data == null) {
            return null;
        }
        
        String prefix = "";
        CardFeature extraFeature = null;
        if (extraFeatureType != null) {
            extraFeature = new CardFeature(extraFeatureType, extraFeatureLevel, 15, summonFeature, deathFeature);
            prefix = extraFeatureName;
            if (extraFeatureLevel != 0) {
                prefix += extraFeatureLevel;
            }
        }
        
        for (int j = 0; j < count; ++j) {
            Card card = new Card(data, cardLevel, extraFeature, prefix, String.valueOf(cardSuffixId));
            cardSuffixId++;
            
            ret.add(card);
        }
        
        return ret;
    }

    public static String getDeckDesc(Card card) {
        StringBuffer sb = new StringBuffer();
        sb.append(card.getName());
        Feature extraFeature = card.getExtraFeature();
        if (extraFeature != null) {
            sb.append('+');
            sb.append(getDeckDesc(extraFeature));
        }
        sb.append('-');
        sb.append(card.getLevel());
        return sb.toString();
    }
    
    public static String getDeckDesc(Rune rune) {
        return rune.getName() + rune.getLevel();
    }
    
    public static String getDeckDesc(Feature feature) {
        String desc = feature.getType().name();
        if (feature.getLevel() != 0) {
            desc += feature.getLevel();
        }
        if (feature.isDeathFeature()) {
            return "死契" + desc;
        }
        if (feature.isSummonFeature()) {
            return "降临" + desc;
        }
        return desc;
    }

    public static String getSortedDeckDesc(PlayerInfo player) {
        return getSortedDeckDesc(player.getCards(), player.getRunes());
    }

    public static String getSortedDeckDesc(String descsText) {
        DeckStartupInfo dsi = DeckBuilder.multiBuild(descsText);
        return getSortedDeckDesc(dsi.getCards(), dsi.getRunes());
    }
    
    private static String getSortedDeckDesc(Collection <Card> cards, Collection <Rune> runes) {
        List<Card> cardList = new ArrayList<Card>(cards);
        Collections.sort(cardList);
        List<Rune> runeList = new ArrayList<Rune>(runes);
        Collections.sort(runeList);
        StringBuffer sb = new StringBuffer();
        for (Card card : cardList) {
            sb.append(DeckBuilder.getDeckDesc(card));
            sb.append(',');
        }
        for (Rune rune : runeList) {
            sb.append(DeckBuilder.getDeckDesc(rune));
            sb.append(',');
        }
        return sb.toString();
    }
}