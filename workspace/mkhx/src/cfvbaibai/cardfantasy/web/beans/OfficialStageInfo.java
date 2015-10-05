package cfvbaibai.cardfantasy.web.beans;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialStage;
import cfvbaibai.cardfantasy.officialdata.OfficialStageDetail;

public class OfficialStageInfo {
    private OfficialStage stage;
    private OfficialDataStore store;
    public OfficialStageInfo(OfficialStage stage, OfficialDataStore store) {
        this.stage = stage;
        this.store = store;
    }
    public int getMapStageId() {
        return this.stage.MapStageId;
    }
    public String getName() {
        return this.stage.Name;
    }
    public String getFullName() {
        return this.getMapStageId() + "-" + this.getName();
    }
    public int getDetailCount() {
        return this.stage.Count;
    }
    public boolean getMazeExists() {
        return this.stage.MazeCount > 0;
    }
    public int getEverydayReward() {
        return this.stage.EverydayReward;
    }
    public List<OfficialStageDetailInfo> getDetailInfos() {
        List<OfficialStageDetailInfo> detailInfos = new ArrayList<OfficialStageDetailInfo>();
        for (OfficialStageDetail detail: stage.MapStageDetails) {
            detailInfos.add(new OfficialStageDetailInfo(detail, store));
        }
        return detailInfos;
    }
}
