import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;

public class dsw implements dsr, dss {
   private static final nr a = new of("spectatorMenu.team_teleport");
   private static final nr b = new of("spectatorMenu.team_teleport.prompt");
   private final List<dss> c = Lists.newArrayList();

   public dsw() {
      djz _snowman = djz.C();

      for (ddl _snowmanx : _snowman.r.G().g()) {
         this.c.add(new dsw.a(_snowmanx));
      }
   }

   @Override
   public List<dss> a() {
      return this.c;
   }

   @Override
   public nr b() {
      return b;
   }

   @Override
   public void a(dsq var1) {
      _snowman.a(this);
   }

   @Override
   public nr aA_() {
      return a;
   }

   @Override
   public void a(dfm var1, float var2, int var3) {
      djz.C().M().a(dml.a);
      dkw.a(_snowman, 0, 0, 16.0F, 0.0F, 16, 16, 256, 256);
   }

   @Override
   public boolean aB_() {
      for (dss _snowman : this.c) {
         if (_snowman.aB_()) {
            return true;
         }
      }

      return false;
   }

   class a implements dss {
      private final ddl b;
      private final vk c;
      private final List<dwx> d;

      public a(ddl var2) {
         this.b = _snowman;
         this.d = Lists.newArrayList();

         for (String _snowman : _snowman.g()) {
            dwx _snowmanx = djz.C().w().a(_snowman);
            if (_snowmanx != null) {
               this.d.add(_snowmanx);
            }
         }

         if (this.d.isEmpty()) {
            this.c = ekj.a();
         } else {
            String _snowmanx = this.d.get(new Random().nextInt(this.d.size())).a().getName();
            this.c = dzj.d(_snowmanx);
            dzj.a(this.c, _snowmanx);
         }
      }

      @Override
      public void a(dsq var1) {
         _snowman.a(new dsv(this.d));
      }

      @Override
      public nr aA_() {
         return this.b.c();
      }

      @Override
      public void a(dfm var1, float var2, int var3) {
         Integer _snowman = this.b.n().e();
         if (_snowman != null) {
            float _snowmanx = (float)(_snowman >> 16 & 0xFF) / 255.0F;
            float _snowmanxx = (float)(_snowman >> 8 & 0xFF) / 255.0F;
            float _snowmanxxx = (float)(_snowman & 0xFF) / 255.0F;
            dkw.a(_snowman, 1, 1, 15, 15, afm.e(_snowmanx * _snowman, _snowmanxx * _snowman, _snowmanxxx * _snowman) | _snowman << 24);
         }

         djz.C().M().a(this.c);
         RenderSystem.color4f(_snowman, _snowman, _snowman, (float)_snowman / 255.0F);
         dkw.a(_snowman, 2, 2, 12, 12, 8.0F, 8.0F, 8, 8, 64, 64);
         dkw.a(_snowman, 2, 2, 12, 12, 40.0F, 8.0F, 8, 8, 64, 64);
      }

      @Override
      public boolean aB_() {
         return !this.d.isEmpty();
      }
   }
}
