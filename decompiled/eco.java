import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.util.Map;
import javax.annotation.Nullable;

public class eco extends ece<cdg> {
   private static final Map<bzv.a, dvt> a = x.a(Maps.newHashMap(), var0 -> {
      dvt _snowman = new dvt(0, 0, 64, 32);
      dvt _snowmanx = new dul();
      dwl _snowmanxx = new dwl(0.0F);
      var0.put(bzv.b.a, _snowman);
      var0.put(bzv.b.b, _snowman);
      var0.put(bzv.b.c, _snowmanx);
      var0.put(bzv.b.d, _snowmanx);
      var0.put(bzv.b.e, _snowman);
      var0.put(bzv.b.f, _snowmanxx);
   });
   private static final Map<bzv.a, vk> c = x.a(Maps.newHashMap(), var0 -> {
      var0.put(bzv.b.a, new vk("textures/entity/skeleton/skeleton.png"));
      var0.put(bzv.b.b, new vk("textures/entity/skeleton/wither_skeleton.png"));
      var0.put(bzv.b.d, new vk("textures/entity/zombie/zombie.png"));
      var0.put(bzv.b.e, new vk("textures/entity/creeper/creeper.png"));
      var0.put(bzv.b.f, new vk("textures/entity/enderdragon/dragon.png"));
      var0.put(bzv.b.c, ekj.a());
   });

   public eco(ecd var1) {
      super(_snowman);
   }

   public void a(cdg var1, float var2, dfm var3, eag var4, int var5, int var6) {
      float _snowman = _snowman.a(_snowman);
      ceh _snowmanx = _snowman.p();
      boolean _snowmanxx = _snowmanx.b() instanceof cbm;
      gc _snowmanxxx = _snowmanxx ? _snowmanx.c(cbm.a) : null;
      float _snowmanxxxx = 22.5F * (float)(_snowmanxx ? (2 + _snowmanxxx.d()) * 4 : _snowmanx.c(bzv.a));
      a(_snowmanxxx, _snowmanxxxx, ((btq)_snowmanx.b()).b(), _snowman.d(), _snowman, _snowman, _snowman, _snowman);
   }

   public static void a(@Nullable gc var0, float var1, bzv.a var2, @Nullable GameProfile var3, float var4, dfm var5, eag var6, int var7) {
      dvt _snowman = a.get(_snowman);
      _snowman.a();
      if (_snowman == null) {
         _snowman.a(0.5, 0.0, 0.5);
      } else {
         float _snowmanx = 0.25F;
         _snowman.a((double)(0.5F - (float)_snowman.i() * 0.25F), 0.25, (double)(0.5F - (float)_snowman.k() * 0.25F));
      }

      _snowman.a(-1.0F, -1.0F, 1.0F);
      dfq _snowmanx = _snowman.getBuffer(a(_snowman, _snowman));
      _snowman.a(_snowman, _snowman, 0.0F);
      _snowman.a(_snowman, _snowmanx, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.b();
   }

   private static eao a(bzv.a var0, @Nullable GameProfile var1) {
      vk _snowman = c.get(_snowman);
      if (_snowman == bzv.b.c && _snowman != null) {
         djz _snowmanx = djz.C();
         Map<Type, MinecraftProfileTexture> _snowmanxx = _snowmanx.Z().a(_snowman);
         return _snowmanxx.containsKey(Type.SKIN) ? eao.h(_snowmanx.Z().a(_snowmanxx.get(Type.SKIN), Type.SKIN)) : eao.d(ekj.a(bfw.a(_snowman)));
      } else {
         return eao.e(_snowman);
      }
   }
}
