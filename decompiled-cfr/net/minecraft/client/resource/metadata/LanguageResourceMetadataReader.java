/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.client.resource.metadata;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.metadata.LanguageResourceMetadata;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.JsonHelper;

public class LanguageResourceMetadataReader
implements ResourceMetadataReader<LanguageResourceMetadata> {
    @Override
    public LanguageResourceMetadata fromJson(JsonObject jsonObject) {
        HashSet hashSet = Sets.newHashSet();
        for (Map.Entry entry : jsonObject.entrySet()) {
            String string = (String)entry.getKey();
            if (string.length() > 16) {
                throw new JsonParseException("Invalid language->'" + string + "': language code must not be more than " + 16 + " characters long");
            }
            JsonObject _snowman2 = JsonHelper.asObject((JsonElement)entry.getValue(), "language");
            _snowman = JsonHelper.getString(_snowman2, "region");
            _snowman = JsonHelper.getString(_snowman2, "name");
            boolean _snowman3 = JsonHelper.getBoolean(_snowman2, "bidirectional", false);
            if (_snowman.isEmpty()) {
                throw new JsonParseException("Invalid language->'" + string + "'->region: empty value");
            }
            if (_snowman.isEmpty()) {
                throw new JsonParseException("Invalid language->'" + string + "'->name: empty value");
            }
            if (hashSet.add(new LanguageDefinition(string, _snowman, _snowman, _snowman3))) continue;
            throw new JsonParseException("Duplicate language->'" + string + "' defined");
        }
        return new LanguageResourceMetadata(hashSet);
    }

    @Override
    public String getKey() {
        return "language";
    }

    @Override
    public /* synthetic */ Object fromJson(JsonObject json) {
        return this.fromJson(json);
    }
}

