package cfvbaibai.cardfantasy;

import java.lang.reflect.Type;

import cfvbaibai.cardfantasy.engine.CardInfo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CardInfoJsonSerializer implements JsonSerializer<CardInfo>{

    @Override
    public JsonElement serialize(CardInfo card, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", card.getName());
        obj.addProperty("at", card.getCurrentAT());
        obj.addProperty("hp", card.getMaxHP());
        return obj;
    }

}
