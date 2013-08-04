package cfvbaibai.cardfantasy;

import cfvbaibai.cardfantasy.NonSerializable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class NonSerializableStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(NonSerializable.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
