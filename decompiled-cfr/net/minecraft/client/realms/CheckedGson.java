/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 */
package net.minecraft.client.realms;

import com.google.gson.Gson;
import net.minecraft.client.realms.RealmsSerializable;

public class CheckedGson {
    private final Gson GSON = new Gson();

    public String toJson(RealmsSerializable serializable) {
        return this.GSON.toJson((Object)serializable);
    }

    public <T extends RealmsSerializable> T fromJson(String json, Class<T> type) {
        return (T)((RealmsSerializable)this.GSON.fromJson(json, type));
    }
}

