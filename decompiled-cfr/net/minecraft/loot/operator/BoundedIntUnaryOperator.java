/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  javax.annotation.Nullable
 */
package net.minecraft.loot.operator;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.function.IntUnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;

public class BoundedIntUnaryOperator
implements IntUnaryOperator {
    private final Integer min;
    private final Integer max;
    private final IntUnaryOperator operator;

    private BoundedIntUnaryOperator(@Nullable Integer min, @Nullable Integer max) {
        this.min = min;
        this.max = max;
        if (min == null) {
            if (max == null) {
                this.operator = n -> n;
            } else {
                int n4 = max;
                this.operator = n2 -> Math.min(n4, n2);
            }
        } else {
            int n5 = min;
            if (max == null) {
                this.operator = n2 -> Math.max(n5, n2);
            } else {
                _snowman = max;
                this.operator = n3 -> MathHelper.clamp(n3, n5, _snowman);
            }
        }
    }

    public static BoundedIntUnaryOperator create(int min, int max) {
        return new BoundedIntUnaryOperator(min, max);
    }

    public static BoundedIntUnaryOperator createMin(int min) {
        return new BoundedIntUnaryOperator(min, null);
    }

    public static BoundedIntUnaryOperator createMax(int max) {
        return new BoundedIntUnaryOperator(null, max);
    }

    @Override
    public int applyAsInt(int value) {
        return this.operator.applyAsInt(value);
    }

    public static class Serializer
    implements JsonDeserializer<BoundedIntUnaryOperator>,
    JsonSerializer<BoundedIntUnaryOperator> {
        public BoundedIntUnaryOperator deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = JsonHelper.asObject(jsonElement, "value");
            Integer _snowman2 = jsonObject.has("min") ? Integer.valueOf(JsonHelper.getInt(jsonObject, "min")) : null;
            Integer _snowman3 = jsonObject.has("max") ? Integer.valueOf(JsonHelper.getInt(jsonObject, "max")) : null;
            return new BoundedIntUnaryOperator(_snowman2, _snowman3);
        }

        public JsonElement serialize(BoundedIntUnaryOperator boundedIntUnaryOperator, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            if (boundedIntUnaryOperator.max != null) {
                jsonObject.addProperty("max", (Number)boundedIntUnaryOperator.max);
            }
            if (boundedIntUnaryOperator.min != null) {
                jsonObject.addProperty("min", (Number)boundedIntUnaryOperator.min);
            }
            return jsonObject;
        }

        public /* synthetic */ JsonElement serialize(Object op, Type type, JsonSerializationContext context) {
            return this.serialize((BoundedIntUnaryOperator)op, type, context);
        }

        public /* synthetic */ Object deserialize(JsonElement functionJson, Type unused, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(functionJson, unused, context);
        }
    }
}

