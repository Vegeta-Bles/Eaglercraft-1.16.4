/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.client.sound;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.Validate;

public class SoundEntryDeserializer
implements JsonDeserializer<SoundEntry> {
    public SoundEntry deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = JsonHelper.asObject(jsonElement, "entry");
        boolean _snowman2 = JsonHelper.getBoolean(jsonObject, "replace", false);
        String _snowman3 = JsonHelper.getString(jsonObject, "subtitle", null);
        List<Sound> _snowman4 = this.deserializeSounds(jsonObject);
        return new SoundEntry(_snowman4, _snowman2, _snowman3);
    }

    private List<Sound> deserializeSounds(JsonObject json) {
        ArrayList arrayList = Lists.newArrayList();
        if (json.has("sounds")) {
            JsonArray jsonArray = JsonHelper.getArray(json, "sounds");
            for (int i = 0; i < jsonArray.size(); ++i) {
                JsonElement jsonElement = jsonArray.get(i);
                if (JsonHelper.isString(jsonElement)) {
                    String string = JsonHelper.asString(jsonElement, "sound");
                    arrayList.add(new Sound(string, 1.0f, 1.0f, 1, Sound.RegistrationType.FILE, false, false, 16));
                    continue;
                }
                arrayList.add(this.deserializeSound(JsonHelper.asObject(jsonElement, "sound")));
            }
        }
        return arrayList;
    }

    private Sound deserializeSound(JsonObject json) {
        String string = JsonHelper.getString(json, "name");
        Sound.RegistrationType _snowman2 = this.deserializeType(json, Sound.RegistrationType.FILE);
        float _snowman3 = JsonHelper.getFloat(json, "volume", 1.0f);
        Validate.isTrue((_snowman3 > 0.0f ? 1 : 0) != 0, (String)"Invalid volume", (Object[])new Object[0]);
        float _snowman4 = JsonHelper.getFloat(json, "pitch", 1.0f);
        Validate.isTrue((_snowman4 > 0.0f ? 1 : 0) != 0, (String)"Invalid pitch", (Object[])new Object[0]);
        int _snowman5 = JsonHelper.getInt(json, "weight", 1);
        Validate.isTrue((_snowman5 > 0 ? 1 : 0) != 0, (String)"Invalid weight", (Object[])new Object[0]);
        boolean _snowman6 = JsonHelper.getBoolean(json, "preload", false);
        boolean _snowman7 = JsonHelper.getBoolean(json, "stream", false);
        int _snowman8 = JsonHelper.getInt(json, "attenuation_distance", 16);
        return new Sound(string, _snowman3, _snowman4, _snowman5, _snowman2, _snowman7, _snowman6, _snowman8);
    }

    private Sound.RegistrationType deserializeType(JsonObject json, Sound.RegistrationType fallback) {
        Sound.RegistrationType registrationType = fallback;
        if (json.has("type")) {
            registrationType = Sound.RegistrationType.getByName(JsonHelper.getString(json, "type"));
            Validate.notNull((Object)((Object)registrationType), (String)"Invalid type", (Object[])new Object[0]);
        }
        return registrationType;
    }

    public /* synthetic */ Object deserialize(JsonElement functionJson, Type unused, JsonDeserializationContext context) throws JsonParseException {
        return this.deserialize(functionJson, unused, context);
    }
}

