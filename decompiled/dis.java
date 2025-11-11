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

public class dis {
   private static final YggdrasilAuthenticationService b = new YggdrasilAuthenticationService(djz.C().L());
   private static final MinecraftSessionService c = b.createMinecraftSessionService();
   public static LoadingCache<String, GameProfile> a = CacheBuilder.newBuilder()
      .expireAfterWrite(60L, TimeUnit.MINUTES)
      .build(new CacheLoader<String, GameProfile>() {
         public GameProfile a(String var1) throws Exception {
            GameProfile _snowman = dis.c.fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(_snowman), null), false);
            if (_snowman == null) {
               throw new Exception("Couldn't get profile");
            } else {
               return _snowman;
            }
         }
      });

   public static String a(String var0) throws Exception {
      GameProfile _snowman = (GameProfile)a.get(_snowman);
      return _snowman.getName();
   }

   public static Map<Type, MinecraftProfileTexture> b(String var0) {
      try {
         GameProfile _snowman = (GameProfile)a.get(_snowman);
         return c.getTextures(_snowman, false);
      } catch (Exception var2) {
         return Maps.newHashMap();
      }
   }

   public static String a(long var0) {
      if (_snowman < 0L) {
         return "right now";
      } else {
         long _snowman = _snowman / 1000L;
         if (_snowman < 60L) {
            return (_snowman == 1L ? "1 second" : _snowman + " seconds") + " ago";
         } else if (_snowman < 3600L) {
            long _snowmanx = _snowman / 60L;
            return (_snowmanx == 1L ? "1 minute" : _snowmanx + " minutes") + " ago";
         } else if (_snowman < 86400L) {
            long _snowmanx = _snowman / 3600L;
            return (_snowmanx == 1L ? "1 hour" : _snowmanx + " hours") + " ago";
         } else {
            long _snowmanx = _snowman / 86400L;
            return (_snowmanx == 1L ? "1 day" : _snowmanx + " days") + " ago";
         }
      }
   }

   public static String a(Date var0) {
      return a(System.currentTimeMillis() - _snowman.getTime());
   }
}
