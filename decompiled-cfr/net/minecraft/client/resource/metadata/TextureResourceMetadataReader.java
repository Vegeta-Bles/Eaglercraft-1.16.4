/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.client.resource.metadata;

import com.google.gson.JsonObject;
import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.JsonHelper;

public class TextureResourceMetadataReader
implements ResourceMetadataReader<TextureResourceMetadata> {
    @Override
    public TextureResourceMetadata fromJson(JsonObject jsonObject) {
        boolean bl = JsonHelper.getBoolean(jsonObject, "blur", false);
        _snowman = JsonHelper.getBoolean(jsonObject, "clamp", false);
        return new TextureResourceMetadata(bl, _snowman);
    }

    @Override
    public String getKey() {
        return "texture";
    }

    @Override
    public /* synthetic */ Object fromJson(JsonObject json) {
        return this.fromJson(json);
    }
}

