/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import net.minecraft.server.ServerConfigEntry;
import net.minecraft.server.ServerConfigList;
import net.minecraft.server.WhitelistEntry;

public class Whitelist
extends ServerConfigList<GameProfile, WhitelistEntry> {
    public Whitelist(File file) {
        super(file);
    }

    @Override
    protected ServerConfigEntry<GameProfile> fromJson(JsonObject json) {
        return new WhitelistEntry(json);
    }

    public boolean isAllowed(GameProfile profile) {
        return this.contains(profile);
    }

    @Override
    public String[] getNames() {
        String[] stringArray = new String[this.values().size()];
        int _snowman2 = 0;
        for (ServerConfigEntry serverConfigEntry : this.values()) {
            stringArray[_snowman2++] = ((GameProfile)serverConfigEntry.getKey()).getName();
        }
        return stringArray;
    }

    @Override
    protected String toString(GameProfile gameProfile) {
        return gameProfile.getId().toString();
    }

    @Override
    protected /* synthetic */ String toString(Object profile) {
        return this.toString((GameProfile)profile);
    }
}

