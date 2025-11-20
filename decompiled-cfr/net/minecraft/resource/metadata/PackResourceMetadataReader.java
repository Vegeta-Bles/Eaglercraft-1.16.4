/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.resource.metadata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resource.metadata.PackResourceMetadata;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;

public class PackResourceMetadataReader
implements ResourceMetadataReader<PackResourceMetadata> {
    @Override
    public PackResourceMetadata fromJson(JsonObject jsonObject) {
        MutableText mutableText = Text.Serializer.fromJson(jsonObject.get("description"));
        if (mutableText == null) {
            throw new JsonParseException("Invalid/missing description!");
        }
        int _snowman2 = JsonHelper.getInt(jsonObject, "pack_format");
        return new PackResourceMetadata(mutableText, _snowman2);
    }

    @Override
    public String getKey() {
        return "pack";
    }

    @Override
    public /* synthetic */ Object fromJson(JsonObject json) {
        return this.fromJson(json);
    }
}

