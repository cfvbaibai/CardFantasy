package cfvbaibai.cardfantasy;

import java.lang.reflect.Type;

import cfvbaibai.cardfantasy.engine.Player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PlayerJsonSerializer implements JsonSerializer<Player>{

    @Override
    public JsonElement serialize(Player player, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", player.getId());
        obj.addProperty("level", player.getLevel());
        obj.addProperty("max-hp", player.getMaxHP());
        obj.add("deck", context.serialize(player.getDeck()));
        obj.add("grave", context.serialize(player.getGrave()));
        return obj;
    }

}
