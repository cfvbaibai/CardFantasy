package cfvbaibai.cardfantasy.officialdata;

import java.util.List;

/*
{
    Next: 18,
    MazeCount: 0,
    MapStageId: 17,
    MapStageDetails: ...
    NeedStar: 0,
    Name: 星河之森,
    Count: 12,
    EverydayReward: 37800,
    Prev: 16,
    Rank: 16
}
 */
public class OfficialStage {
    public int Next;
    public int MazeCount;
    public int MapStageId;
    public List<OfficialStageDetail> MapStageDetails;
    public int NeedStar;
    public String Name;
    public int Count;
    public int EverydayReward;
    public int Prev;
    public int Rank;
}
