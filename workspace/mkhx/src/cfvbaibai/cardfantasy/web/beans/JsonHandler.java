package cfvbaibai.cardfantasy.web.beans;

import cfvbaibai.cardfantasy.NonSerializableStrategy;
import cfvbaibai.cardfantasy.PlayerJsonSerializer;
import cfvbaibai.cardfantasy.engine.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHandler {
    private Gson gson;
    public JsonHandler() {
        this.gson = new GsonBuilder()
        .setExclusionStrategies(new NonSerializableStrategy())
        .registerTypeAdapter(Player.class, new PlayerJsonSerializer())
        .setPrettyPrinting()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create();
    }

    public String toJson(Object obj) {
        return this.gson.toJson(obj);
    }
    
    public <T> T fromJson(String json, Class <T> clazz) {
        return this.gson.fromJson(json, clazz);
    }
}
