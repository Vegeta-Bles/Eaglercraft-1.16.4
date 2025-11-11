import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;

public class dmp implements dmq {
   private final dmp.a c;
   private nr d;
   private List<afa> e;
   private long f;
   private boolean g;
   private final int h;

   public dmp(dmp.a var1, nr var2, @Nullable nr var3) {
      this(_snowman, _snowman, a(_snowman), 160);
   }

   public static dmp a(djz var0, dmp.a var1, nr var2, nr var3) {
      dku _snowman = _snowman.g;
      List<afa> _snowmanx = _snowman.b(_snowman, 200);
      int _snowmanxx = Math.max(200, _snowmanx.stream().mapToInt(_snowman::a).max().orElse(200));
      return new dmp(_snowman, _snowman, _snowmanx, _snowmanxx + 30);
   }

   private dmp(dmp.a var1, nr var2, List<afa> var3, int var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.h = _snowman;
   }

   private static ImmutableList<afa> a(@Nullable nr var0) {
      return _snowman == null ? ImmutableList.of() : ImmutableList.of(_snowman.f());
   }

   @Override
   public int a() {
      return this.h;
   }

   @Override
   public dmq.a a(dfm var1, dmr var2, long var3) {
      if (this.g) {
         this.f = _snowman;
         this.g = false;
      }

      _snowman.b().M().a(a);
      RenderSystem.color3f(1.0F, 1.0F, 1.0F);
      int _snowman = this.a();
      int _snowmanx = 12;
      if (_snowman == 160 && this.e.size() <= 1) {
         _snowman.b(_snowman, 0, 0, 0, 64, _snowman, this.d());
      } else {
         int _snowmanxx = this.d() + Math.max(0, this.e.size() - 1) * 12;
         int _snowmanxxx = 28;
         int _snowmanxxxx = Math.min(4, _snowmanxx - 28);
         this.a(_snowman, _snowman, _snowman, 0, 0, 28);

         for (int _snowmanxxxxx = 28; _snowmanxxxxx < _snowmanxx - _snowmanxxxx; _snowmanxxxxx += 10) {
            this.a(_snowman, _snowman, _snowman, 16, _snowmanxxxxx, Math.min(16, _snowmanxx - _snowmanxxxxx - _snowmanxxxx));
         }

         this.a(_snowman, _snowman, _snowman, 32 - _snowmanxxxx, _snowmanxx - _snowmanxxxx, _snowmanxxxx);
      }

      if (this.e == null) {
         _snowman.b().g.b(_snowman, this.d, 18.0F, 12.0F, -256);
      } else {
         _snowman.b().g.b(_snowman, this.d, 18.0F, 7.0F, -256);

         for (int _snowmanxx = 0; _snowmanxx < this.e.size(); _snowmanxx++) {
            _snowman.b().g.b(_snowman, this.e.get(_snowmanxx), 18.0F, (float)(18 + _snowmanxx * 12), -1);
         }
      }

      return _snowman - this.f < 5000L ? dmq.a.a : dmq.a.b;
   }

   private void a(dfm var1, dmr var2, int var3, int var4, int var5, int var6) {
      int _snowman = _snowman == 0 ? 20 : 5;
      int _snowmanx = Math.min(60, _snowman - _snowman);
      _snowman.b(_snowman, 0, _snowman, 0, 64 + _snowman, _snowman, _snowman);

      for (int _snowmanxx = _snowman; _snowmanxx < _snowman - _snowmanx; _snowmanxx += 64) {
         _snowman.b(_snowman, _snowmanxx, _snowman, 32, 64 + _snowman, Math.min(64, _snowman - _snowmanxx - _snowmanx), _snowman);
      }

      _snowman.b(_snowman, _snowman - _snowmanx, _snowman, 160 - _snowmanx, 64 + _snowman, _snowmanx, _snowman);
   }

   public void a(nr var1, @Nullable nr var2) {
      this.d = _snowman;
      this.e = a(_snowman);
      this.g = true;
   }

   public dmp.a b() {
      return this.c;
   }

   public static void a(dmr var0, dmp.a var1, nr var2, @Nullable nr var3) {
      _snowman.a(new dmp(_snowman, _snowman, _snowman));
   }

   public static void b(dmr var0, dmp.a var1, nr var2, @Nullable nr var3) {
      dmp _snowman = _snowman.a(dmp.class, _snowman);
      if (_snowman == null) {
         a(_snowman, _snowman, _snowman, _snowman);
      } else {
         _snowman.a(_snowman, _snowman);
      }
   }

   public static void a(djz var0, String var1) {
      a(_snowman.an(), dmp.a.f, new of("selectWorld.access_failure"), new oe(_snowman));
   }

   public static void b(djz var0, String var1) {
      a(_snowman.an(), dmp.a.f, new of("selectWorld.delete_failure"), new oe(_snowman));
   }

   public static void c(djz var0, String var1) {
      a(_snowman.an(), dmp.a.g, new of("pack.copyFailure"), new oe(_snowman));
   }

   public static enum a {
      a,
      b,
      c,
      d,
      e,
      f,
      g;

      private a() {
      }
   }
}
