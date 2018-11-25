package cfvbaibai.cardfantasy.engine;

public enum CardStatusType {
    燃烧(true, "燃"),
    麻痹(false, "麻"),
    冰冻(false, "冻"),
    中毒(true, "毒"),
    锁定(false, "锁"),
    裂伤(false, "裂"),
    复活(false, "复"),
    迷惑(false, "惑"),
    弱化(false, "弱"),
    召唤(false, "召"),
    晕眩(false, "晕"),
    // 不屈在触发后一直持续到下一次攻击阶段结束（中毒、燃烧和物理反击的结算是在攻击阶段之后）
    不屈(false, "倔"),
    死印(false, "死"),
    魔印(false, "魔"),
    致盲(false, "盲"),
    沉默(false, "默"),
    变羊(false, "羊"),
    死咒(false, "咒"),
    献祭(false, "祭"),
    炼成(false, "炼"),
    魂殇(false, "殇"),
    黄天(false, "黄"),
    祭奠(false, "奠"),
    离魂(false, "离"),
    咒怨(false, "怨"),
    链接(false, "链"),
    虚化(false, "虚"),
    王国(false, "王"),
    森林(false, "森"),
    蛮荒(false, "蛮"),
    地狱(false, "地"),
    魔族(false, "族");
    

    private boolean quantitive;
    public boolean isQuantitive() {
        return this.quantitive;
    }
    
    private String abbrev;
    public String getAbbrev() {
        return this.abbrev;
    }
    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }
    
    CardStatusType(boolean quantitive, String abbrev) {
        this.quantitive = quantitive;
        this.abbrev = abbrev;
    }
}
