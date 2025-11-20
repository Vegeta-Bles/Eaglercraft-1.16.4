/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.client.resource.metadata;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import net.minecraft.client.resource.metadata.AnimationFrameResourceMetadata;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.Validate;

public class AnimationResourceMetadataReader
implements ResourceMetadataReader<AnimationResourceMetadata> {
    @Override
    public AnimationResourceMetadata fromJson(JsonObject jsonObject2) {
        JsonObject jsonObject2;
        int _snowman5;
        ArrayList arrayList = Lists.newArrayList();
        int _snowman2 = JsonHelper.getInt(jsonObject2, "frametime", 1);
        if (_snowman2 != 1) {
            Validate.inclusiveBetween((long)1L, (long)Integer.MAX_VALUE, (long)_snowman2, (String)"Invalid default frame time");
        }
        if (jsonObject2.has("frames")) {
            try {
                JsonArray jsonArray = JsonHelper.getArray(jsonObject2, "frames");
                for (_snowman5 = 0; _snowman5 < jsonArray.size(); ++_snowman5) {
                    JsonElement jsonElement = jsonArray.get(_snowman5);
                    AnimationFrameResourceMetadata _snowman3 = this.readFrameMetadata(_snowman5, jsonElement);
                    if (_snowman3 == null) continue;
                    arrayList.add(_snowman3);
                }
            }
            catch (ClassCastException classCastException) {
                throw new JsonParseException("Invalid animation->frames: expected array, was " + jsonObject2.get("frames"), (Throwable)classCastException);
            }
        }
        int _snowman4 = JsonHelper.getInt(jsonObject2, "width", -1);
        _snowman5 = JsonHelper.getInt(jsonObject2, "height", -1);
        if (_snowman4 != -1) {
            Validate.inclusiveBetween((long)1L, (long)Integer.MAX_VALUE, (long)_snowman4, (String)"Invalid width");
        }
        if (_snowman5 != -1) {
            Validate.inclusiveBetween((long)1L, (long)Integer.MAX_VALUE, (long)_snowman5, (String)"Invalid height");
        }
        boolean _snowman6 = JsonHelper.getBoolean(jsonObject2, "interpolate", false);
        return new AnimationResourceMetadata(arrayList, _snowman4, _snowman5, _snowman2, _snowman6);
    }

    private AnimationFrameResourceMetadata readFrameMetadata(int frame, JsonElement json) {
        if (json.isJsonPrimitive()) {
            return new AnimationFrameResourceMetadata(JsonHelper.asInt(json, "frames[" + frame + "]"));
        }
        if (json.isJsonObject()) {
            JsonObject jsonObject = JsonHelper.asObject(json, "frames[" + frame + "]");
            int _snowman2 = JsonHelper.getInt(jsonObject, "time", -1);
            if (jsonObject.has("time")) {
                Validate.inclusiveBetween((long)1L, (long)Integer.MAX_VALUE, (long)_snowman2, (String)"Invalid frame time");
            }
            int _snowman3 = JsonHelper.getInt(jsonObject, "index");
            Validate.inclusiveBetween((long)0L, (long)Integer.MAX_VALUE, (long)_snowman3, (String)"Invalid frame index");
            return new AnimationFrameResourceMetadata(_snowman3, _snowman2);
        }
        return null;
    }

    @Override
    public String getKey() {
        return "animation";
    }

    @Override
    public /* synthetic */ Object fromJson(JsonObject json) {
        return this.fromJson(json);
    }
}

