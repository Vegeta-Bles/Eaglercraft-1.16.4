package net.minecraft.client.texture;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.properties.Property;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class PlayerSkinProvider {
   private final TextureManager textureManager;
   private final File skinCacheDir;
   private final MinecraftSessionService sessionService;
   private final LoadingCache<String, Map<Type, MinecraftProfileTexture>> skinCache;

   public PlayerSkinProvider(TextureManager textureManager, File skinCacheDir, MinecraftSessionService sessionService) {
      this.textureManager = textureManager;
      this.skinCacheDir = skinCacheDir;
      this.sessionService = sessionService;
      this.skinCache = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader<String, Map<Type, MinecraftProfileTexture>>() {
         public Map<Type, MinecraftProfileTexture> load(String _snowman) {
            GameProfile _snowmanx = new GameProfile(null, "dummy_mcdummyface");
            _snowmanx.getProperties().put("textures", new Property("textures", _snowman, ""));

            try {
               return sessionService.getTextures(_snowmanx, false);
            } catch (Throwable var4) {
               return ImmutableMap.of();
            }
         }
      });
   }

   public Identifier loadSkin(MinecraftProfileTexture profileTexture, Type type) {
      return this.loadSkin(profileTexture, type, null);
   }

   private Identifier loadSkin(MinecraftProfileTexture profileTexture, Type type, @Nullable PlayerSkinProvider.SkinTextureAvailableCallback callback) {
      String _snowman = Hashing.sha1().hashUnencodedChars(profileTexture.getHash()).toString();
      Identifier _snowmanx = new Identifier("skins/" + _snowman);
      AbstractTexture _snowmanxx = this.textureManager.getTexture(_snowmanx);
      if (_snowmanxx != null) {
         if (callback != null) {
            callback.onSkinTextureAvailable(type, _snowmanx, profileTexture);
         }
      } else {
         File _snowmanxxx = new File(this.skinCacheDir, _snowman.length() > 2 ? _snowman.substring(0, 2) : "xx");
         File _snowmanxxxx = new File(_snowmanxxx, _snowman);
         PlayerSkinTexture _snowmanxxxxx = new PlayerSkinTexture(_snowmanxxxx, profileTexture.getUrl(), DefaultSkinHelper.getTexture(), type == Type.SKIN, () -> {
            if (callback != null) {
               callback.onSkinTextureAvailable(type, _snowman, profileTexture);
            }
         });
         this.textureManager.registerTexture(_snowmanx, _snowmanxxxxx);
      }

      return _snowmanx;
   }

   public void loadSkin(GameProfile profile, PlayerSkinProvider.SkinTextureAvailableCallback callback, boolean requireSecure) {
      Runnable _snowman = () -> {
         Map<Type, MinecraftProfileTexture> _snowmanx = Maps.newHashMap();

         try {
            _snowmanx.putAll(this.sessionService.getTextures(profile, requireSecure));
         } catch (InsecureTextureException var7) {
         }

         if (_snowmanx.isEmpty()) {
            profile.getProperties().clear();
            if (profile.getId().equals(MinecraftClient.getInstance().getSession().getProfile().getId())) {
               profile.getProperties().putAll(MinecraftClient.getInstance().getSessionProperties());
               _snowmanx.putAll(this.sessionService.getTextures(profile, false));
            } else {
               this.sessionService.fillProfileProperties(profile, requireSecure);

               try {
                  _snowmanx.putAll(this.sessionService.getTextures(profile, requireSecure));
               } catch (InsecureTextureException var6) {
               }
            }
         }

         MinecraftClient.getInstance().execute(() -> RenderSystem.recordRenderCall(() -> ImmutableList.of(Type.SKIN, Type.CAPE).forEach(_snowmanxxxxxxx -> {
                  if (_snowman.containsKey(_snowmanxxxxxxx)) {
                     this.loadSkin(_snowman.get(_snowmanxxxxxxx), _snowmanxxxxxxx, callback);
                  }
               })));
      };
      Util.getMainWorkerExecutor().execute(_snowman);
   }

   public Map<Type, MinecraftProfileTexture> getTextures(GameProfile _snowman) {
      Property _snowmanx = (Property)Iterables.getFirst(_snowman.getProperties().get("textures"), null);
      return (Map<Type, MinecraftProfileTexture>)(_snowmanx == null ? ImmutableMap.of() : (Map)this.skinCache.getUnchecked(_snowmanx.getValue()));
   }

   public interface SkinTextureAvailableCallback {
      void onSkinTextureAvailable(Type var1, Identifier var2, MinecraftProfileTexture texture);
   }
}
