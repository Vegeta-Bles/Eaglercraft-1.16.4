package net.minecraft.client.realms.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.MinecraftClient;

public class RealmsUtil {
   private static final YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(
      MinecraftClient.getInstance().getNetworkProxy()
   );
   private static final MinecraftSessionService sessionService = authenticationService.createMinecraftSessionService();
   public static LoadingCache<String, GameProfile> gameProfileCache = CacheBuilder.newBuilder()
      .expireAfterWrite(60L, TimeUnit.MINUTES)
      .build(new CacheLoader<String, GameProfile>() {
         public GameProfile load(String _snowman) throws Exception {
            GameProfile _snowmanx = RealmsUtil.sessionService.fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(_snowman), null), false);
            if (_snowmanx == null) {
               throw new Exception("Couldn't get profile");
            } else {
               return _snowmanx;
            }
         }
      });

   public static String uuidToName(String uuid) throws Exception {
      GameProfile _snowman = (GameProfile)gameProfileCache.get(uuid);
      return _snowman.getName();
   }

   public static Map<Type, MinecraftProfileTexture> getTextures(String uuid) {
      try {
         GameProfile _snowman = (GameProfile)gameProfileCache.get(uuid);
         return sessionService.getTextures(_snowman, false);
      } catch (Exception var2) {
         return Maps.newHashMap();
      }
   }

   public static String convertToAgePresentation(long _snowman) {
      if (_snowman < 0L) {
         return "right now";
      } else {
         long _snowmanx = _snowman / 1000L;
         if (_snowmanx < 60L) {
            return (_snowmanx == 1L ? "1 second" : _snowmanx + " seconds") + " ago";
         } else if (_snowmanx < 3600L) {
            long _snowmanxx = _snowmanx / 60L;
            return (_snowmanxx == 1L ? "1 minute" : _snowmanxx + " minutes") + " ago";
         } else if (_snowmanx < 86400L) {
            long _snowmanxx = _snowmanx / 3600L;
            return (_snowmanxx == 1L ? "1 hour" : _snowmanxx + " hours") + " ago";
         } else {
            long _snowmanxx = _snowmanx / 86400L;
            return (_snowmanxx == 1L ? "1 day" : _snowmanxx + " days") + " ago";
         }
      }
   }

   public static String method_25282(Date _snowman) {
      return convertToAgePresentation(System.currentTimeMillis() - _snowman.getTime());
   }
}
