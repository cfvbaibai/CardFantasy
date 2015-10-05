package cfvbaibai.cardfantasy.web.beans;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialStageDetail;
import cfvbaibai.cardfantasy.officialdata.OfficialStageLevel;

public class OfficialStageDetailInfo {
    private OfficialStageDetail detail;
    private OfficialDataStore store;
    public OfficialStageDetailInfo(OfficialStageDetail detail, OfficialDataStore store) {
        this.detail = detail;
        this.store = store;
    }
    public String getName() {
        return detail.Name;
    }
    public int getRank() {
        return detail.Rank;
    }
    public String getRankName() {
        if (detail.Type == 2) {
            return "隐藏";
        } else {
            return String.valueOf(detail.Rank + 1);
        }
    }
    public String getFullName() {
        return detail.MapStageId + "-" + this.getRankName() + "-" + this.getName();
    }
    public int getId() {
        return detail.MapStageDetailId;
    }
    public int getStageId() {
        return detail.MapStageId;
    }
    public int getType() {
        return detail.Type;
    }
    public List<OfficialStageLevelInfo> getLevelInfos() {
        List<OfficialStageLevelInfo> result = new ArrayList<OfficialStageLevelInfo>();
        if (this.detail.Levels != null) {
            for (OfficialStageLevel level : this.detail.Levels) {
                result.add(new OfficialStageLevelInfo(level, this.store));
            }
        }
        return result;
    }
}
