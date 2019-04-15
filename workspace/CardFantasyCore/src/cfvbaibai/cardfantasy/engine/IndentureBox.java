package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.NonSerializable;
import cfvbaibai.cardfantasy.data.Indenture;
import cfvbaibai.cardfantasy.data.IndentureData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IndentureBox {
    private List<IndentureInfo> indentureInfos;
    @NonSerializable
    private Player owner;

    public IndentureBox(Player owner, Collection<Indenture> indentures) {
        this.owner = owner;
        this.indentureInfos = new ArrayList<IndentureInfo>();
        for (Indenture indenture : indentures) {
            this.indentureInfos.add(new IndentureInfo(indenture, owner));
        }
    }
    
    public Player getOwner() {
        return this.owner;
    }

    public IndentureInfo addIndentureInfo(IndentureInfo indentureInfo) {
        this.indentureInfos.add(indentureInfo);
        return indentureInfo;
    }

    public List<IndentureInfo> getIndentureInfos() {
        return new ArrayList<IndentureInfo>(this.indentureInfos);
    }

    public IndentureInfo getIndentureInfoOf(IndentureData indentureData) {
        for (IndentureInfo indentureInfo : this.indentureInfos) {
            if (indentureInfo.is(indentureData)) {
                return indentureInfo;
            }
        }
        return null;
    }
}
