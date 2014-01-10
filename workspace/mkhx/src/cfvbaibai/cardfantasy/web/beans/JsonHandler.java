package cfvbaibai.cardfantasy.web.beans;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cfvbaibai.cardfantasy.NonSerializableStrategy;
import cfvbaibai.cardfantasy.PlayerJsonSerializer;
import cfvbaibai.cardfantasy.engine.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonHandler {
    private class UtcDateSerializer implements JsonSerializer<Date>{
        private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @Override
        public JsonElement serialize(Date utcDate, Type type, JsonSerializationContext context) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(utcDate);
            calendar.add(Calendar.HOUR_OF_DAY, 8);
            Date localDate = calendar.getTime();
            String localTimeText = this.formatter.format(localDate);
            JsonPrimitive result = new JsonPrimitive(localTimeText);
            return result;
        }
    }

    private Gson gson;
    public JsonHandler() {
        this.gson = new GsonBuilder()
        .setExclusionStrategies(new NonSerializableStrategy())
        .registerTypeAdapter(Player.class, new PlayerJsonSerializer())
        .registerTypeAdapter(Date.class, new UtcDateSerializer())
        .setPrettyPrinting()
        .create();
    }

    public String toJson(Object obj) {
        return this.gson.toJson(obj);
    }
    
    public <T> T fromJson(String json, Class <T> clazz) {
        return this.gson.fromJson(json, clazz);
    }
}
