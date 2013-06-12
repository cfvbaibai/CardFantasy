package cfvbaibai.cardfantasy.test;

import java.util.ArrayList;
import java.util.Collection;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Rune;
import cfvbaibai.cardfantasy.data.RuneData;

public class PlayerBuilder {

    private static CardDataStore store;

    public static PlayerInfo build(String id, int hp, String... descs) {
        if (store == null) {
            store = CardDataStore.loadDefault();
        }
        Collection<Card> cards = new ArrayList<Card>();
        Collection<Rune> runes = new ArrayList<Rune>();
        for (int i = 0; i < descs.length; ++i) {
            String desc = descs[i];
            if (desc == null || desc.length() < 2) {
                throw new CardFantasyRuntimeException("Invalid description: " + desc);
            }
            if (desc.charAt(0) == 'C') {
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
                        for (Card card : cards) {
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
                    cards.add(new Card(data, cardLevel, String.valueOf(suffix)));
                }
            } else if (desc.charAt(0) == 'R') {
                String runeDesc = desc.substring(1);
                int iDash = runeDesc.indexOf('-');
                String runeName = runeDesc.substring(0, iDash);
                int runeLevel = Integer.parseInt(runeDesc.substring(iDash + 1));

                RuneData runeData = RuneData.valueOf(runeName);
                Rune rune = new Rune(runeData, 0);
                rune.growToLevel(runeLevel);
                runes.add(rune);
            } else {
                throw new CardFantasyRuntimeException("Invalid description prefix! " + desc);
            }
        }
        Card[] cardArray = cards.toArray(new Card[cards.size()]);
        return new PlayerInfo(id, hp, runes, cardArray);
    }
}