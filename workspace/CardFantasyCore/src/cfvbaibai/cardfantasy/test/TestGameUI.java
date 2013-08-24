package cfvbaibai.cardfantasy.test;

import cfvbaibai.cardfantasy.TextGameUI;
import cfvbaibai.cardfantasy.engine.CardInfo;

public class TestGameUI extends TextGameUI {

    public TestGameUI() {
    }

    @Override
    protected void say(String obj) {
        System.out.println(obj.toString());
    }
}
