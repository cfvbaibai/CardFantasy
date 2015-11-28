package cfvbaibai.cardfantasy.jettyserver;

import cfvbaibai.cardfantasy.game.launcher.GameLauncherFacade;

public class LilithGameJettyServlet extends GameHttpServlet {

    private static final long serialVersionUID = 2502588865292207232L;

    @Override
    protected String getResultJson(FormData formData) {

        String playerDeck = formData.getNonNullString("deck");
        int gameCount = formData.getInt("count");
        int heroLv = formData.getInt("hlv");
        String lilithName = formData.getNonNullString("ln");
        int gameType = formData.getInt("gt");
        int targetRemainingGuardCount = formData.getInt("tc");
        int remainingHp = formData.getInt("rhp");
        String eventCardNames = formData.getString("ec");

        String resultJson = GameLauncherFacade.playLilithGame(
                playerDeck, lilithName, heroLv, 
                gameType, targetRemainingGuardCount, remainingHp, eventCardNames, gameCount);
        return resultJson;
    }
}
