import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;

public class dso implements dss {
   private final GameProfile a;
   private final vk b;
   private final oe c;

   public dso(GameProfile var1) {
      this.a = _snowman;
      djz _snowman = djz.C();
      Map<Type, MinecraftProfileTexture> _snowmanx = _snowman.Z().a(_snowman);
      if (_snowmanx.containsKey(Type.SKIN)) {
         this.b = _snowman.Z().a(_snowmanx.get(Type.SKIN), Type.SKIN);
      } else {
         this.b = ekj.a(bfw.a(_snowman));
      }

      this.c = new oe(_snowman.getName());
   }

   @Override
   public void a(dsq var1) {
      djz.C().w().a(new tr(this.a.getId()));
   }

   @Override
   public nr aA_() {
      return this.c;
   }

   @Override
   public void a(dfm var1, float var2, int var3) {
      djz.C().M().a(this.b);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, (float)_snowman / 255.0F);
      dkw.a(_snowman, 2, 2, 12, 12, 8.0F, 8.0F, 8, 8, 64, 64);
      dkw.a(_snowman, 2, 2, 12, 12, 40.0F, 8.0F, 8, 8, 64, 64);
   }

   @Override
   public boolean aB_() {
      return true;
   }
}
