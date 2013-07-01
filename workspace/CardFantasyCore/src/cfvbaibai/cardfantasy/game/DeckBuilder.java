package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.Rune;
import cfvbaibai.cardfantasy.data.RuneData;

public final class DeckBuilder {

    private static CardDataStore store;

    public static DeckStartupInfo build(String... descs) {
        if (store == null) {
            store = CardDataStore.loadDefault();
        }
        DeckStartupInfo deck = new DeckStartupInfo();
        for (String desc : descs) {
            if (desc == null) {
                throw new CardFantasyRuntimeException("item description should not be null");
            }
            if (desc.length() < 2) {
                throw new CardFantasyRuntimeException("item description must be longer than 1 characeter, but is "
                        + desc);
            }
            if (desc.charAt(0) == 'C') {
                parseCardDesc(deck, desc);
            } else if (desc.charAt(0) == 'R') {
                parseRuneDesc(deck, desc);
            } else {
                throw new CardFantasyRuntimeException("Invalid description prefix! " + desc);
            }
        }
        return deck;
    }

    private static void parseRuneDesc(DeckStartupInfo deck, String desc) {
        String runeDesc = desc.substring(1);
        int iDash = runeDesc.indexOf('-');
        String runeName = runeDesc.substring(0, iDash);
        int runeLevel = Integer.parseInt(runeDesc.substring(iDash + 1));

        RuneData runeData = RuneData.valueOf(runeName);
        Rune rune = new Rune(runeData, 0);
        rune.growToLevel(runeLevel);
        deck.addRune(rune);
    }

    private static void parseCardDesc(DeckStartupInfo deck, String desc) {
        String cardDesc = desc.substring(1);
        int iStar = cardDesc.indexOf("*");
        int count = 1;
        if (iStar > 0) {
            String countText = cardDesc.substring(iStar + 1);
            count = Integer.parseInt(countText);
            cardDesc = cardDesc.substring(0, iStar);
        }
        int iDash = cardDesc.indexOf("-");
        String cardName = cardDesc.substring(0, iDash);
        int cardLevel = Integer.parseInt(cardDesc.substring(iDash + 1));

        CardData data = store.getCardInfo(cardName);
        if (data == null) {
            throw new RuntimeException("Invalid card name: " + cardName);
        }
        for (int j = 0; j < count; ++j) {
            char suffix = 'A';
            while (true) {
                boolean suffixUsed = false;
                for (Card card : deck.getCards()) {
                    if (card.getId().equals(cardName + suffix)) {
                        suffixUsed = true;
                        break;
                    }
                }
                if (suffixUsed) {
                    ++suffix;
                } else {
                    break;
                }
            }
            deck.addCard(new Card(data, cardLevel, String.valueOf(suffix)));
        }
    }
}
