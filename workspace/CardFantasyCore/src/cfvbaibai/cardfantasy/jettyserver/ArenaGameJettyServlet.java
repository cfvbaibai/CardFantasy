package cfvbaibai.cardfantasy.jettyserver;

import cfvbaibai.cardfantasy.game.launcher.GameLauncherFacade;

public class ArenaGameJettyServlet extends GameHttpServlet {

    private static final long serialVersionUID = 6269202984625938487L;

    @Override
    protected String getResultJson(FormData formData) {

        String deck1 = formData.getNonNullString("deck1");
        String deck2 = formData.getNonNullString("deck2");
        int heroLv1 = formData.getInt("hlv1");
        int heroLv2 = formData.getInt("hlv2");
        int p1CardAtBuff = formData.getInt("p1catb");
        int p1CardHpBuff = formData.getInt("p1chpb");
        int p1HeroHpBuff = formData.getInt("p1hhpb");
        int p2CardAtBuff = formData.getInt("p2catb");
        int p2CardHpBuff = formData.getInt("p2chpb");
        int p2HeroHpBuff = formData.getInt("p2hhpb");
        int firstAttack = formData.getInt("fa");
        int deckOrder = formData.getInt("do");
        String vc1Text = formData.getNonNullString("vc1");
        int gameCount = formData.getInt("count");

        String resultJson =  GameLauncherFacade.playArenaGame(
            deck1, deck2, heroLv1, heroLv2,
            p1CardAtBuff, p1CardHpBuff, p1HeroHpBuff, p2CardAtBuff, p2CardHpBuff, p2HeroHpBuff,
            firstAttack, deckOrder, vc1Text, gameCount);
        return resultJson;
    }
}
