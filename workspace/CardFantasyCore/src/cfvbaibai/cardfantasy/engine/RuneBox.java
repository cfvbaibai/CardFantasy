package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.data.Rune;
import cfvbaibai.cardfantasy.data.RuneData;

public class RuneBox {
    private List<RuneInfo> runes;
    private Player owner;

    public RuneBox(Player owner, Collection<Rune> runes) {
        this.owner = owner;
        this.runes = new ArrayList<RuneInfo>();
        for (Rune rune : runes) {
            this.runes.add(new RuneInfo(rune, owner));
        }
    }
    
    public Player getOwner() {
        return this.owner;
    }

    public void addRune(RuneInfo rune) {
        this.runes.add(rune);
    }

    public List<RuneInfo> getRunes() {
        return new ArrayList<RuneInfo>(this.runes);
    }

    public RuneInfo getRuneOf(RuneData runeData) {
        for (RuneInfo rune : this.runes) {
            if (rune.is(runeData)) {
                return rune;
            }
        }
        return null;
    }
}
