import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class dsc extends dot {
   protected static final vk a = new vk("textures/gui/social_interactions.png");
   private static final nr b = new of("gui.socialInteractions.tab_all");
   private static final nr c = new of("gui.socialInteractions.tab_hidden");
   private static final nr p = new of("gui.socialInteractions.tab_blocked");
   private static final nr q = b.g().a(k.t);
   private static final nr r = c.g().a(k.t);
   private static final nr s = p.g().a(k.t);
   private static final nr t = new of("gui.socialInteractions.search_hint").a(k.u).a(k.h);
   private static final nr u = new of("gui.socialInteractions.search_empty").a(k.h);
   private static final nr v = new of("gui.socialInteractions.empty_hidden").a(k.h);
   private static final nr w = new of("gui.socialInteractions.empty_blocked").a(k.h);
   private static final nr x = new of("gui.socialInteractions.blocking_hint");
   private dsb y;
   private dlq z;
   private String A = "";
   private dsc.a B = dsc.a.a;
   private dlj C;
   private dlj D;
   private dlj E;
   private dlj F;
   @Nullable
   private nr G;
   private int H;
   private boolean I;
   @Nullable
   private Runnable J;

   public dsc() {
      super(new of("gui.socialInteractions.title"));
      this.a(djz.C());
   }

   private int i() {
      return Math.max(52, this.l - 128 - 16);
   }

   private int k() {
      return this.i() / 16;
   }

   private int l() {
      return 80 + this.k() * 16 - 8;
   }

   private int m() {
      return (this.k - 238) / 2;
   }

   @Override
   public String ax_() {
      return super.ax_() + ". " + this.G.getString();
   }

   @Override
   public void d() {
      super.d();
      this.z.a();
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      if (this.I) {
         this.y.a(this.k, this.l, 88, this.l());
      } else {
         this.y = new dsb(this, this.i, this.k, this.l, 88, this.l(), 36);
      }

      int _snowman = this.y.d() / 3;
      int _snowmanx = this.y.q();
      int _snowmanxx = this.y.r();
      int _snowmanxxx = this.o.a(x) + 40;
      int _snowmanxxxx = 64 + 16 * this.k();
      int _snowmanxxxxx = (this.k - _snowmanxxx) / 2;
      this.C = this.a((dlj)(new dlj(_snowmanx, 45, _snowman, 20, b, var1x -> this.a(dsc.a.a))));
      this.D = this.a((dlj)(new dlj((_snowmanx + _snowmanxx - _snowman) / 2 + 1, 45, _snowman, 20, c, var1x -> this.a(dsc.a.b))));
      this.E = this.a((dlj)(new dlj(_snowmanxx - _snowman + 1, 45, _snowman, 20, p, var1x -> this.a(dsc.a.c))));
      this.F = this.a((dlj)(new dlj(_snowmanxxxxx, _snowmanxxxx, _snowmanxxx, 20, x, var1x -> this.i.a(new dnr(var1xx -> {
            if (var1xx) {
               x.i().a("https://aka.ms/javablocking");
            }

            this.i.a(this);
         }, "https://aka.ms/javablocking", true)))));
      String _snowmanxxxxxx = this.z != null ? this.z.b() : "";
      this.z = new dlq(this.o, this.m() + 28, 78, 196, 16, t) {
         @Override
         protected nx c() {
            return !dsc.this.z.b().isEmpty() && dsc.this.y.f() ? super.c().c(", ").a(dsc.u) : super.c();
         }
      };
      this.z.k(16);
      this.z.f(false);
      this.z.i(true);
      this.z.l(16777215);
      this.z.a(_snowmanxxxxxx);
      this.z.a(this::b);
      this.e.add(this.z);
      this.e.add(this.y);
      this.I = true;
      this.a(this.B);
   }

   private void a(dsc.a var1) {
      this.B = _snowman;
      this.C.a(b);
      this.D.a(c);
      this.E.a(p);
      Collection<UUID> _snowman;
      switch (_snowman) {
         case a:
            this.C.a(q);
            _snowman = this.i.s.e.f();
            break;
         case b:
            this.D.a(r);
            _snowman = this.i.aB().a();
            break;
         case c:
            this.E.a(s);
            dsa _snowmanx = this.i.aB();
            _snowman = this.i.s.e.f().stream().filter(_snowmanx::e).collect(Collectors.toSet());
            break;
         default:
            _snowman = ImmutableList.of();
      }

      this.B = _snowman;
      this.y.a(_snowman, this.y.m());
      if (!this.z.b().isEmpty() && this.y.f() && !this.z.j()) {
         dkz.b.a(u.getString());
      } else if (_snowman.isEmpty()) {
         if (_snowman == dsc.a.b) {
            dkz.b.a(v.getString());
         } else if (_snowman == dsc.a.c) {
            dkz.b.a(w.getString());
         }
      }
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public void a(dfm var1) {
      int _snowman = this.m() + 3;
      super.a(_snowman);
      this.i.M().a(a);
      this.b(_snowman, _snowman, 64, 1, 1, 236, 8);
      int _snowmanx = this.k();

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         this.b(_snowman, _snowman, 72 + 16 * _snowmanxx, 1, 10, 236, 16);
      }

      this.b(_snowman, _snowman, 72 + 16 * _snowmanx, 1, 27, 236, 8);
      this.b(_snowman, _snowman + 10, 76, 243, 1, 12, 12);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(this.i);
      this.a(_snowman);
      if (this.G != null) {
         b(_snowman, this.i.g, this.G, this.m() + 8, 35, -1);
      }

      if (!this.y.f()) {
         this.y.a(_snowman, _snowman, _snowman, _snowman);
      } else if (!this.z.b().isEmpty()) {
         a(_snowman, this.i.g, u, this.k / 2, (78 + this.l()) / 2, -1);
      } else {
         switch (this.B) {
            case b:
               a(_snowman, this.i.g, v, this.k / 2, (78 + this.l()) / 2, -1);
               break;
            case c:
               a(_snowman, this.i.g, w, this.k / 2, (78 + this.l()) / 2, -1);
         }
      }

      if (!this.z.j() && this.z.b().isEmpty()) {
         b(_snowman, this.i.g, t, this.z.l, this.z.m, -1);
      } else {
         this.z.a(_snowman, _snowman, _snowman, _snowman);
      }

      this.F.p = this.B == dsc.a.c;
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (this.J != null) {
         this.J.run();
      }
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.z.j()) {
         this.z.a(_snowman, _snowman, _snowman);
      }

      return super.a(_snowman, _snowman, _snowman) || this.y.a(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (!this.z.j() && this.i.k.av.a(_snowman, _snowman)) {
         this.i.a(null);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean ay_() {
      return false;
   }

   private void b(String var1) {
      _snowman = _snowman.toLowerCase(Locale.ROOT);
      if (!_snowman.equals(this.A)) {
         this.y.a(_snowman);
         this.A = _snowman;
         this.a(this.B);
      }
   }

   private void a(djz var1) {
      int _snowman = _snowman.w().e().size();
      if (this.H != _snowman) {
         String _snowmanx = "";
         dwz _snowmanxx = _snowman.E();
         if (_snowman.F()) {
            _snowmanx = _snowman.H().ab();
         } else if (_snowmanxx != null) {
            _snowmanx = _snowmanxx.a;
         }

         if (_snowman > 1) {
            this.G = new of("gui.socialInteractions.server_label.multiple", _snowmanx, _snowman);
         } else {
            this.G = new of("gui.socialInteractions.server_label.single", _snowmanx, _snowman);
         }

         this.H = _snowman;
      }
   }

   public void a(dwx var1) {
      this.y.a(_snowman, this.B);
   }

   public void a(UUID var1) {
      this.y.a(_snowman);
   }

   public void a(@Nullable Runnable var1) {
      this.J = _snowman;
   }

   public static enum a {
      a,
      b,
      c;

      private a() {
      }
   }
}
