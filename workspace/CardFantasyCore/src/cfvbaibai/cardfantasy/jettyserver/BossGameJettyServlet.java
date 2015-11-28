package cfvbaibai.cardfantasy.jettyserver;

import cfvbaibai.cardfantasy.game.launcher.GameLauncherFacade;

public class BossGameJettyServlet extends GameHttpServlet {

    private static final long serialVersionUID = 4793952639239459059L;

    @Override
    protected String getResultJson(FormData formData) {

        String playerDeck = formData.getNonNullString("deck");
        int gameCount = formData.getInt("count");
        int guardType = formData.getInt("gt");
        int heroLv = formData.getInt("hlv");
        String bossName = formData.getNonNullString("bn");
        int kingdomBuff = formData.getInt("bk");
        int forestBuff = formData.getInt("bf");
        int savageBuff = formData.getInt("bs");
        int hellBuff = formData.getInt("bh");

        String resultJson = GameLauncherFacade.playBossGame(
                playerDeck, bossName, heroLv, 
                kingdomBuff, forestBuff, savageBuff, hellBuff, guardType, gameCount);
        return resultJson;
    }
}
