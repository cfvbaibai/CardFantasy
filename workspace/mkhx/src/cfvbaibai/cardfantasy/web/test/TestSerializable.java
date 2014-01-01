package cfvbaibai.cardfantasy.web.test;

import org.junit.Before;
import org.junit.Test;

import cfvbaibai.cardfantasy.CardInfoJsonSerializer;
import cfvbaibai.cardfantasy.NonSerializableStrategy;
import cfvbaibai.cardfantasy.PlayerJsonSerializer;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.StageInfo;
import cfvbaibai.cardfantasy.game.PlayerBuilder;
import cfvbaibai.cardfantasy.test.TestGameBuilder;
import cfvbaibai.cardfantasy.web.animation.PlayerInitInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestSerializable {

    private Gson gson;
    
    @Before
    public void setup() {
        gson = new GsonBuilder()
            .setExclusionStrategies(new NonSerializableStrategy())
            .registerTypeAdapter(Player.class, new PlayerJsonSerializer())
            .registerTypeAdapter(CardInfo.class, new CardInfoJsonSerializer())
            .setPrettyPrinting()
            .create();
    }
    
    @Test
    public void TestSerializePlayerInitInfo() {
        PlayerInfo playerInfo = PlayerBuilder.build(true, "玩家", "降临天使*4,光明之龙*1,毁灭之龙*3,灼魂,冰封,清泉,绝杀", 75);
        StageInfo stage = TestGameBuilder.build(playerInfo, playerInfo).getStage();
        Player player = stage.getPlayers().get(0);
        PlayerInitInfo pii = new PlayerInitInfo(player, 0);
        String json = gson.toJson(pii);
        System.out.println(json);
    }
    
    @Test
    public void testOverflow() {
        int damage = 3000000;
        System.out.println(String.valueOf(damage * 1000));
    }
}
