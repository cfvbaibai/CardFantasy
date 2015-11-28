package cfvbaibai.cardfantasy.jettyserver;

import cfvbaibai.cardfantasy.game.launcher.GameLauncherFacade;

public class MapGameJettyServlet extends GameHttpServlet {

    private static final long serialVersionUID = 3080544640654038896L;

    @Override
    protected String getResultJson(FormData formData) {

        String playerDeck = formData.getNonNullString("deck");
        int gameCount = formData.getInt("count");
        int heroLv = formData.getInt("hlv");
        String mapName = formData.getNonNullString("map");

        String resultJson = GameLauncherFacade.playMapGame(playerDeck, mapName, heroLv, gameCount);
        return resultJson;
    }
}
