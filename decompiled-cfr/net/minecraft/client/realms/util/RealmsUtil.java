/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  com.google.common.collect.Maps
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.util.UUIDTypeAdapter
 */
package net.minecraft.client.realms.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.MinecraftClient;

public class RealmsUtil {
    private static final YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(MinecraftClient.getInstance().getNetworkProxy());
    private static final MinecraftSessionService sessionService = authenticationService.createMinecraftSessionService();
    public static LoadingCache<String, GameProfile> gameProfileCache = CacheBuilder.newBuilder().expireAfterWrite(60L, TimeUnit.MINUTES).build((CacheLoader)new CacheLoader<String, GameProfile>(){

        public GameProfile load(String string) throws Exception {
            GameProfile gameProfile = sessionService.fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString((String)string), null), false);
            if (gameProfile == null) {
                throw new Exception("Couldn't get profile");
            }
            return gameProfile;
        }

        public /* synthetic */ Object load(Object object) throws Exception {
            return this.load((String)object);
        }
    });

    public static String uuidToName(String uuid) throws Exception {
        GameProfile gameProfile = (GameProfile)gameProfileCache.get((Object)uuid);
        return gameProfile.getName();
    }

    public static Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(String uuid) {
        try {
            GameProfile gameProfile = (GameProfile)gameProfileCache.get((Object)uuid);
            return sessionService.getTextures(gameProfile, false);
        }
        catch (Exception exception) {
            return Maps.newHashMap();
        }
    }

    public static String convertToAgePresentation(long l) {
        if (l < 0L) {
            return "right now";
        }
        _snowman = l / 1000L;
        if (_snowman < 60L) {
            return (_snowman == 1L ? "1 second" : _snowman + " seconds") + " ago";
        }
        if (_snowman < 3600L) {
            _snowman = _snowman / 60L;
            return (_snowman == 1L ? "1 minute" : _snowman + " minutes") + " ago";
        }
        if (_snowman < 86400L) {
            _snowman = _snowman / 3600L;
            return (_snowman == 1L ? "1 hour" : _snowman + " hours") + " ago";
        }
        _snowman = _snowman / 86400L;
        return (_snowman == 1L ? "1 day" : _snowman + " days") + " ago";
    }

    public static String method_25282(Date date) {
        return RealmsUtil.convertToAgePresentation(System.currentTimeMillis() - date.getTime());
    }
}

