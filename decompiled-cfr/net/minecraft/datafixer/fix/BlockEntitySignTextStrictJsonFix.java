/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.datafixer.fix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.lang.reflect.Type;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ChoiceFix;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.StringUtils;

public class BlockEntitySignTextStrictJsonFix
extends ChoiceFix {
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(Text.class, (Object)new JsonDeserializer<Text>(){

        public MutableText deserialize(JsonElement jsonElement2, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonElement jsonElement2;
            if (jsonElement2.isJsonPrimitive()) {
                return new LiteralText(jsonElement2.getAsString());
            }
            if (jsonElement2.isJsonArray()) {
                JsonArray jsonArray = jsonElement2.getAsJsonArray();
                MutableText _snowman2 = null;
                for (JsonElement jsonElement3 : jsonArray) {
                    MutableText mutableText = this.deserialize(jsonElement3, jsonElement3.getClass(), jsonDeserializationContext);
                    if (_snowman2 == null) {
                        _snowman2 = mutableText;
                        continue;
                    }
                    _snowman2.append(mutableText);
                }
                return _snowman2;
            }
            throw new JsonParseException("Don't know how to turn " + jsonElement2 + " into a Component");
        }

        public /* synthetic */ Object deserialize(JsonElement functionJson, Type unused, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(functionJson, unused, context);
        }
    }).create();

    public BlockEntitySignTextStrictJsonFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType, "BlockEntitySignTextStrictJsonFix", TypeReferences.BLOCK_ENTITY, "Sign");
    }

    private Dynamic<?> fix(Dynamic<?> dynamic, String lineName) {
        String string = dynamic.get(lineName).asString("");
        Text _snowman2 = null;
        if ("null".equals(string) || StringUtils.isEmpty((CharSequence)string)) {
            _snowman2 = LiteralText.EMPTY;
        } else if (string.charAt(0) == '\"' && string.charAt(string.length() - 1) == '\"' || string.charAt(0) == '{' && string.charAt(string.length() - 1) == '}') {
            try {
                _snowman2 = JsonHelper.deserialize(GSON, string, Text.class, true);
                if (_snowman2 == null) {
                    _snowman2 = LiteralText.EMPTY;
                }
            }
            catch (JsonParseException jsonParseException) {
                // empty catch block
            }
            if (_snowman2 == null) {
                try {
                    _snowman2 = Text.Serializer.fromJson(string);
                }
                catch (JsonParseException jsonParseException) {
                    // empty catch block
                }
            }
            if (_snowman2 == null) {
                try {
                    _snowman2 = Text.Serializer.fromLenientJson(string);
                }
                catch (JsonParseException jsonParseException) {
                    // empty catch block
                }
            }
            if (_snowman2 == null) {
                _snowman2 = new LiteralText(string);
            }
        } else {
            _snowman2 = new LiteralText(string);
        }
        return dynamic.set(lineName, dynamic.createString(Text.Serializer.toJson(_snowman2)));
    }

    @Override
    protected Typed<?> transform(Typed<?> inputType) {
        return inputType.update(DSL.remainderFinder(), dynamic -> {
            dynamic = this.fix((Dynamic<?>)dynamic, "Text1");
            dynamic = this.fix((Dynamic<?>)dynamic, "Text2");
            dynamic = this.fix((Dynamic<?>)dynamic, "Text3");
            dynamic = this.fix((Dynamic<?>)dynamic, "Text4");
            return dynamic;
        });
    }
}

