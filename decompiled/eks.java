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

public class eks {
   private final ekd a;
   private final File b;
   private final MinecraftSessionService c;
   private final LoadingCache<String, Map<Type, MinecraftProfileTexture>> d;

   public eks(ekd var1, File var2, final MinecraftSessionService var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader<String, Map<Type, MinecraftProfileTexture>>() {
         public Map<Type, MinecraftProfileTexture> a(String var1) {
            GameProfile _snowman = new GameProfile(null, "dummy_mcdummyface");
            _snowman.getProperties().put("textures", new Property("textures", _snowman, ""));

            try {
               return _snowman.getTextures(_snowman, false);
            } catch (Throwable var4) {
               return ImmutableMap.of();
            }
         }
      });
   }

   public vk a(MinecraftProfileTexture var1, Type var2) {
      return this.a(_snowman, _snowman, null);
   }

   private vk a(MinecraftProfileTexture var1, Type var2, @Nullable eks.a var3) {
      String _snowman = Hashing.sha1().hashUnencodedChars(_snowman.getHash()).toString();
      vk _snowmanx = new vk("skins/" + _snowman);
      ejq _snowmanxx = this.a.b(_snowmanx);
      if (_snowmanxx != null) {
         if (_snowman != null) {
            _snowman.onSkinTextureAvailable(_snowman, _snowmanx, _snowman);
         }
      } else {
         File _snowmanxxx = new File(this.b, _snowman.length() > 2 ? _snowman.substring(0, 2) : "xx");
         File _snowmanxxxx = new File(_snowmanxxx, _snowman);
         ejt _snowmanxxxxx = new ejt(_snowmanxxxx, _snowman.getUrl(), ekj.a(), _snowman == Type.SKIN, () -> {
            if (_snowman != null) {
               _snowman.onSkinTextureAvailable(_snowman, _snowman, _snowman);
            }
         });
         this.a.a(_snowmanx, _snowmanxxxxx);
      }

      return _snowmanx;
   }

   public void a(GameProfile var1, eks.a var2, boolean var3) {
      Runnable _snowman = () -> {
         Map<Type, MinecraftProfileTexture> _snowmanx = Maps.newHashMap();

         try {
            _snowmanx.putAll(this.c.getTextures(_snowman, _snowman));
         } catch (InsecureTextureException var7) {
         }

         if (_snowmanx.isEmpty()) {
            _snowman.getProperties().clear();
            if (_snowman.getId().equals(djz.C().J().e().getId())) {
               _snowman.getProperties().putAll(djz.C().K());
               _snowmanx.putAll(this.c.getTextures(_snowman, false));
            } else {
               this.c.fillProfileProperties(_snowman, _snowman);

               try {
                  _snowmanx.putAll(this.c.getTextures(_snowman, _snowman));
               } catch (InsecureTextureException var6) {
               }
            }
         }

         djz.C().execute(() -> RenderSystem.recordRenderCall(() -> ImmutableList.of(Type.SKIN, Type.CAPE).forEach(var3x -> {
                  if (_snowman.containsKey(var3x)) {
                     this.a(_snowman.get(var3x), var3x, _snowman);
                  }
               })));
      };
      x.f().execute(_snowman);
   }

   public Map<Type, MinecraftProfileTexture> a(GameProfile var1) {
      Property _snowman = (Property)Iterables.getFirst(_snowman.getProperties().get("textures"), null);
      return (Map<Type, MinecraftProfileTexture>)(_snowman == null ? ImmutableMap.of() : (Map)this.d.getUnchecked(_snowman.getValue()));
   }

   public interface a {
      void onSkinTextureAvailable(Type var1, vk var2, MinecraftProfileTexture var3);
   }
}
