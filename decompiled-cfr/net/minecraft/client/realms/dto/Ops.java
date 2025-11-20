/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package net.minecraft.client.realms.dto;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Set;
import net.minecraft.client.realms.dto.ValueObject;

public class Ops
extends ValueObject {
    public Set<String> ops = Sets.newHashSet();

    public static Ops parse(String json) {
        Ops ops = new Ops();
        JsonParser _snowman2 = new JsonParser();
        try {
            JsonElement jsonElement = _snowman2.parse(json);
            JsonObject _snowman3 = jsonElement.getAsJsonObject();
            _snowman = _snowman3.get("ops");
            if (_snowman.isJsonArray()) {
                for (JsonElement jsonElement2 : _snowman.getAsJsonArray()) {
                    ops.ops.add(jsonElement2.getAsString());
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return ops;
    }
}

