import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class drz extends dlo.a<drz> {
   private final djz f;
   private final List<dmi> g;
   private final UUID h;
   private final String i;
   private final Supplier<vk> j;
   private boolean k;
   @Nullable
   private dlj l;
   @Nullable
   private dlj m;
   private final List<afa> n;
   private final List<afa> o;
   private float p;
   private static final nr q = new of("gui.socialInteractions.status_hidden").a(k.u);
   private static final nr r = new of("gui.socialInteractions.status_blocked").a(k.u);
   private static final nr s = new of("gui.socialInteractions.status_offline").a(k.u);
   private static final nr t = new of("gui.socialInteractions.status_hidden_offline").a(k.u);
   private static final nr u = new of("gui.socialInteractions.status_blocked_offline").a(k.u);
   public static final int a = aez.a.a(190, 0, 0, 0);
   public static final int b = aez.a.a(255, 74, 74, 74);
   public static final int c = aez.a.a(255, 48, 48, 48);
   public static final int d = aez.a.a(255, 255, 255, 255);
   public static final int e = aez.a.a(140, 255, 255, 255);

   public drz(djz var1, dsc var2, UUID var3, String var4, Supplier<vk> var5) {
      this.f = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.n = _snowman.g.b(new of("gui.socialInteractions.tooltip.hide", _snowman), 150);
      this.o = _snowman.g.b(new of("gui.socialInteractions.tooltip.show", _snowman), 150);
      dsa _snowman = _snowman.aB();
      if (!_snowman.s.eA().getId().equals(_snowman) && !_snowman.e(_snowman)) {
         this.l = new dlr(0, 0, 20, 20, 0, 38, 20, dsc.a, 256, 256, var4x -> {
            _snowman.a(_snowman);
            this.a(true, new of("gui.socialInteractions.hidden_in_chat", _snowman));
         }, (var3x, var4x, var5x, var6x) -> {
            this.p = this.p + _snowman.ak();
            if (this.p >= 10.0F) {
               _snowman.a(() -> a(_snowman, var4x, this.n, var5x, var6x));
            }
         }, new of("gui.socialInteractions.hide")) {
            @Override
            protected nx c() {
               return drz.this.a(super.c());
            }
         };
         this.m = new dlr(0, 0, 20, 20, 20, 38, 20, dsc.a, 256, 256, var4x -> {
            _snowman.b(_snowman);
            this.a(false, new of("gui.socialInteractions.shown_in_chat", _snowman));
         }, (var3x, var4x, var5x, var6x) -> {
            this.p = this.p + _snowman.ak();
            if (this.p >= 10.0F) {
               _snowman.a(() -> a(_snowman, var4x, this.o, var5x, var6x));
            }
         }, new of("gui.socialInteractions.show")) {
            @Override
            protected nx c() {
               return drz.this.a(super.c());
            }
         };
         this.m.p = _snowman.d(_snowman);
         this.l.p = !this.m.p;
         this.g = ImmutableList.of(this.l, this.m);
      } else {
         this.g = ImmutableList.of();
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
      int _snowman = _snowman + 4;
      int _snowmanx = _snowman + (_snowman - 24) / 2;
      int _snowmanxx = _snowman + 24 + 4;
      nr _snowmanxxx = this.d();
      int _snowmanxxxx;
      if (_snowmanxxx == oe.d) {
         dkw.a(_snowman, _snowman, _snowman, _snowman + _snowman, _snowman + _snowman, b);
         _snowmanxxxx = _snowman + (_snowman - 9) / 2;
      } else {
         dkw.a(_snowman, _snowman, _snowman, _snowman + _snowman, _snowman + _snowman, c);
         _snowmanxxxx = _snowman + (_snowman - (9 + 9)) / 2;
         this.f.g.b(_snowman, _snowmanxxx, (float)_snowmanxx, (float)(_snowmanxxxx + 12), e);
      }

      this.f.M().a(this.j.get());
      dkw.a(_snowman, _snowman, _snowmanx, 24, 24, 8.0F, 8.0F, 8, 8, 64, 64);
      RenderSystem.enableBlend();
      dkw.a(_snowman, _snowman, _snowmanx, 24, 24, 40.0F, 8.0F, 8, 8, 64, 64);
      RenderSystem.disableBlend();
      this.f.g.b(_snowman, this.i, (float)_snowmanxx, (float)_snowmanxxxx, d);
      if (this.k) {
         dkw.a(_snowman, _snowman, _snowmanx, _snowman + 24, _snowmanx + 24, a);
      }

      if (this.l != null && this.m != null) {
         float _snowmanxxxxx = this.p;
         this.l.l = _snowman + (_snowman - this.l.h() - 4);
         this.l.m = _snowman + (_snowman - this.l.e()) / 2;
         this.l.a(_snowman, _snowman, _snowman, _snowman);
         this.m.l = _snowman + (_snowman - this.m.h() - 4);
         this.m.m = _snowman + (_snowman - this.m.e()) / 2;
         this.m.a(_snowman, _snowman, _snowman, _snowman);
         if (_snowmanxxxxx == this.p) {
            this.p = 0.0F;
         }
      }
   }

   @Override
   public List<? extends dmi> au_() {
      return this.g;
   }

   public String b() {
      return this.i;
   }

   public UUID c() {
      return this.h;
   }

   public void c(boolean var1) {
      this.k = _snowman;
   }

   private void a(boolean var1, nr var2) {
      this.m.p = _snowman;
      this.l.p = !_snowman;
      this.f.j.c().a(_snowman);
      dkz.b.a(_snowman.getString());
   }

   private nx a(nx var1) {
      nr _snowman = this.d();
      return _snowman == oe.d ? new oe(this.i).c(", ").a(_snowman) : new oe(this.i).c(", ").a(_snowman).c(", ").a(_snowman);
   }

   private nr d() {
      boolean _snowman = this.f.aB().d(this.h);
      boolean _snowmanx = this.f.aB().e(this.h);
      if (_snowmanx && this.k) {
         return u;
      } else if (_snowman && this.k) {
         return t;
      } else if (_snowmanx) {
         return r;
      } else if (_snowman) {
         return q;
      } else {
         return this.k ? s : oe.d;
      }
   }

   private static void a(dsc var0, dfm var1, List<afa> var2, int var3, int var4) {
      _snowman.c(_snowman, _snowman, _snowman, _snowman);
      _snowman.a(null);
   }
}
