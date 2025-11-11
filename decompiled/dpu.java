import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class dpu extends dot {
   private static final nr a = new of("book.editTitle");
   private static final nr b = new of("book.finalizeWarning");
   private static final afa c = afa.a("_", ob.a.a(k.a));
   private static final afa p = afa.a("_", ob.a.a(k.h));
   private final bfw q;
   private final bmb r;
   private boolean s;
   private boolean t;
   private int u;
   private int v;
   private final List<String> w = Lists.newArrayList();
   private String x = "";
   private final dmy y = new dmy(this::u, this::c, this::h, this::b, var1x -> var1x.length() < 1024 && this.o.b(var1x, 114) <= 128);
   private final dmy z = new dmy(() -> this.x, var1x -> this.x = var1x, this::h, this::b, var0 -> var0.length() < 16);
   private long A;
   private int B = -1;
   private dqt C;
   private dqt D;
   private dlj E;
   private dlj F;
   private dlj G;
   private dlj H;
   private final aot I;
   @Nullable
   private dpu.a J = dpu.a.a;
   private nr K = oe.d;
   private final nr L;

   public dpu(bfw var1, bmb var2, aot var3) {
      super(dkz.a);
      this.q = _snowman;
      this.r = _snowman;
      this.I = _snowman;
      md _snowman = _snowman.o();
      if (_snowman != null) {
         mj _snowmanx = _snowman.d("pages", 8).d();

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            this.w.add(_snowmanx.j(_snowmanxx));
         }
      }

      if (this.w.isEmpty()) {
         this.w.add("");
      }

      this.L = new of("book.byAuthor", _snowman.R()).a(k.i);
   }

   private void b(String var1) {
      if (this.i != null) {
         dmy.a(this.i, _snowman);
      }
   }

   private String h() {
      return this.i != null ? dmy.b(this.i) : "";
   }

   private int i() {
      return this.w.size();
   }

   @Override
   public void d() {
      super.d();
      this.u++;
   }

   @Override
   protected void b() {
      this.B();
      this.i.m.a(true);
      this.F = this.a((dlj)(new dlj(this.k / 2 - 100, 196, 98, 20, new of("book.signButton"), var1x -> {
         this.t = true;
         this.m();
      })));
      this.E = this.a((dlj)(new dlj(this.k / 2 + 2, 196, 98, 20, nq.c, var1x -> {
         this.i.a(null);
         this.c(false);
      })));
      this.G = this.a((dlj)(new dlj(this.k / 2 - 100, 196, 98, 20, new of("book.finalizeButton"), var1x -> {
         if (this.t) {
            this.c(true);
            this.i.a(null);
         }
      })));
      this.H = this.a((dlj)(new dlj(this.k / 2 + 2, 196, 98, 20, nq.d, var1x -> {
         if (this.t) {
            this.t = false;
         }

         this.m();
      })));
      int _snowman = (this.k - 192) / 2;
      int _snowmanx = 2;
      this.C = this.a(new dqt(_snowman + 116, 159, true, var1x -> this.l(), true));
      this.D = this.a(new dqt(_snowman + 43, 159, false, var1x -> this.k(), true));
      this.m();
   }

   private void k() {
      if (this.v > 0) {
         this.v--;
      }

      this.m();
      this.C();
   }

   private void l() {
      if (this.v < this.i() - 1) {
         this.v++;
      } else {
         this.o();
         if (this.v < this.i() - 1) {
            this.v++;
         }
      }

      this.m();
      this.C();
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   private void m() {
      this.D.p = !this.t && this.v > 0;
      this.C.p = !this.t;
      this.E.p = !this.t;
      this.F.p = !this.t;
      this.H.p = this.t;
      this.G.p = this.t;
      this.G.o = !this.x.trim().isEmpty();
   }

   private void n() {
      ListIterator<String> _snowman = this.w.listIterator(this.w.size());

      while (_snowman.hasPrevious() && _snowman.previous().isEmpty()) {
         _snowman.remove();
      }
   }

   private void c(boolean var1) {
      if (this.s) {
         this.n();
         mj _snowman = new mj();
         this.w.stream().map(ms::a).forEach(_snowman::add);
         if (!this.w.isEmpty()) {
            this.r.a("pages", _snowman);
         }

         if (_snowman) {
            this.r.a("author", ms.a(this.q.eA().getName()));
            this.r.a("title", ms.a(this.x.trim()));
         }

         int _snowmanx = this.I == aot.a ? this.q.bm.d : 40;
         this.i.w().a(new sn(this.r, _snowman, _snowmanx));
      }
   }

   private void o() {
      if (this.i() < 100) {
         this.w.add("");
         this.s = true;
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (this.t) {
         return this.d(_snowman, _snowman, _snowman);
      } else {
         boolean _snowman = this.c(_snowman, _snowman, _snowman);
         if (_snowman) {
            this.B();
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean a(char var1, int var2) {
      if (super.a(_snowman, _snowman)) {
         return true;
      } else if (this.t) {
         boolean _snowman = this.z.a(_snowman);
         if (_snowman) {
            this.m();
            this.s = true;
            return true;
         } else {
            return false;
         }
      } else if (w.a(_snowman)) {
         this.y.a(Character.toString(_snowman));
         this.B();
         return true;
      } else {
         return false;
      }
   }

   private boolean c(int var1, int var2, int var3) {
      if (dot.i(_snowman)) {
         this.y.d();
         return true;
      } else if (dot.h(_snowman)) {
         this.y.c();
         return true;
      } else if (dot.g(_snowman)) {
         this.y.b();
         return true;
      } else if (dot.f(_snowman)) {
         this.y.a();
         return true;
      } else {
         switch (_snowman) {
            case 257:
            case 335:
               this.y.a("\n");
               return true;
            case 259:
               this.y.d(-1);
               return true;
            case 261:
               this.y.d(1);
               return true;
            case 262:
               this.y.a(1, dot.y());
               return true;
            case 263:
               this.y.a(-1, dot.y());
               return true;
            case 264:
               this.q();
               return true;
            case 265:
               this.p();
               return true;
            case 266:
               this.D.b();
               return true;
            case 267:
               this.C.b();
               return true;
            case 268:
               this.r();
               return true;
            case 269:
               this.t();
               return true;
            default:
               return false;
         }
      }
   }

   private void p() {
      this.a(-1);
   }

   private void q() {
      this.a(1);
   }

   private void a(int var1) {
      int _snowman = this.y.g();
      int _snowmanx = this.A().a(_snowman, _snowman);
      this.y.c(_snowmanx, dot.y());
   }

   private void r() {
      int _snowman = this.y.g();
      int _snowmanx = this.A().a(_snowman);
      this.y.c(_snowmanx, dot.y());
   }

   private void t() {
      dpu.a _snowman = this.A();
      int _snowmanx = this.y.g();
      int _snowmanxx = _snowman.b(_snowmanx);
      this.y.c(_snowmanxx, dot.y());
   }

   private boolean d(int var1, int var2, int var3) {
      switch (_snowman) {
         case 257:
         case 335:
            if (!this.x.isEmpty()) {
               this.c(true);
               this.i.a(null);
            }

            return true;
         case 259:
            this.z.d(-1);
            this.m();
            this.s = true;
            return true;
         default:
            return false;
      }
   }

   private String u() {
      return this.v >= 0 && this.v < this.w.size() ? this.w.get(this.v) : "";
   }

   private void c(String var1) {
      if (this.v >= 0 && this.v < this.w.size()) {
         this.w.set(this.v, _snowman);
         this.s = true;
         this.B();
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.a(null);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(dpv.b);
      int _snowman = (this.k - 192) / 2;
      int _snowmanx = 2;
      this.b(_snowman, _snowman, 2, 0, 0, 192, 192);
      if (this.t) {
         boolean _snowmanxx = this.u / 6 % 2 == 0;
         afa _snowmanxxx = afa.a(afa.a(this.x, ob.a), _snowmanxx ? c : p);
         int _snowmanxxxx = this.o.a(a);
         this.o.b(_snowman, a, (float)(_snowman + 36 + (114 - _snowmanxxxx) / 2), 34.0F, 0);
         int _snowmanxxxxx = this.o.a(_snowmanxxx);
         this.o.b(_snowman, _snowmanxxx, (float)(_snowman + 36 + (114 - _snowmanxxxxx) / 2), 50.0F, 0);
         int _snowmanxxxxxx = this.o.a(this.L);
         this.o.b(_snowman, this.L, (float)(_snowman + 36 + (114 - _snowmanxxxxxx) / 2), 60.0F, 0);
         this.o.a(b, _snowman + 36, 82, 114, 0);
      } else {
         int _snowmanxx = this.o.a(this.K);
         this.o.b(_snowman, this.K, (float)(_snowman - _snowmanxx + 192 - 44), 18.0F, 0);
         dpu.a _snowmanxxx = this.A();

         for (dpu.b _snowmanxxxx : _snowmanxxx.f) {
            this.o.b(_snowman, _snowmanxxxx.c, (float)_snowmanxxxx.d, (float)_snowmanxxxx.e, -16777216);
         }

         this.a(_snowmanxxx.g);
         this.a(_snowman, _snowmanxxx.c, _snowmanxxx.d);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private void a(dfm var1, dpu.c var2, boolean var3) {
      if (this.u / 6 % 2 == 0) {
         _snowman = this.b(_snowman);
         if (!_snowman) {
            dkw.a(_snowman, _snowman.a, _snowman.b - 1, _snowman.a + 1, _snowman.b + 9, -16777216);
         } else {
            this.o.b(_snowman, "_", (float)_snowman.a, (float)_snowman.b, 0);
         }
      }
   }

   private void a(eal[] var1) {
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      RenderSystem.color4f(0.0F, 0.0F, 255.0F, 255.0F);
      RenderSystem.disableTexture();
      RenderSystem.enableColorLogicOp();
      RenderSystem.logicOp(dem.o.n);
      _snowmanx.a(7, dfk.k);

      for (eal _snowmanxx : _snowman) {
         int _snowmanxxx = _snowmanxx.a();
         int _snowmanxxxx = _snowmanxx.b();
         int _snowmanxxxxx = _snowmanxxx + _snowmanxx.c();
         int _snowmanxxxxxx = _snowmanxxxx + _snowmanxx.d();
         _snowmanx.a((double)_snowmanxxx, (double)_snowmanxxxxxx, 0.0).d();
         _snowmanx.a((double)_snowmanxxxxx, (double)_snowmanxxxxxx, 0.0).d();
         _snowmanx.a((double)_snowmanxxxxx, (double)_snowmanxxxx, 0.0).d();
         _snowmanx.a((double)_snowmanxxx, (double)_snowmanxxxx, 0.0).d();
      }

      _snowman.b();
      RenderSystem.disableColorLogicOp();
      RenderSystem.enableTexture();
   }

   private dpu.c a(dpu.c var1) {
      return new dpu.c(_snowman.a - (this.k - 192) / 2 - 36, _snowman.b - 32);
   }

   private dpu.c b(dpu.c var1) {
      return new dpu.c(_snowman.a + (this.k - 192) / 2 + 36, _snowman.b + 32);
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else {
         if (_snowman == 0) {
            long _snowman = x.b();
            dpu.a _snowmanx = this.A();
            int _snowmanxx = _snowmanx.a(this.o, this.a(new dpu.c((int)_snowman, (int)_snowman)));
            if (_snowmanxx >= 0) {
               if (_snowmanxx != this.B || _snowman - this.A >= 250L) {
                  this.y.c(_snowmanxx, dot.y());
               } else if (!this.y.i()) {
                  this.b(_snowmanxx);
               } else {
                  this.y.d();
               }

               this.B();
            }

            this.B = _snowmanxx;
            this.A = _snowman;
         }

         return true;
      }
   }

   private void b(int var1) {
      String _snowman = this.u();
      this.y.a(dkj.a(_snowman, -1, _snowman, false), dkj.a(_snowman, 1, _snowman, false));
   }

   @Override
   public boolean a(double var1, double var3, int var5, double var6, double var8) {
      if (super.a(_snowman, _snowman, _snowman, _snowman, _snowman)) {
         return true;
      } else {
         if (_snowman == 0) {
            dpu.a _snowman = this.A();
            int _snowmanx = _snowman.a(this.o, this.a(new dpu.c((int)_snowman, (int)_snowman)));
            this.y.c(_snowmanx, true);
            this.B();
         }

         return true;
      }
   }

   private dpu.a A() {
      if (this.J == null) {
         this.J = this.D();
         this.K = new of("book.pageIndicator", this.v + 1, this.i());
      }

      return this.J;
   }

   private void B() {
      this.J = null;
   }

   private void C() {
      this.y.f();
      this.B();
   }

   private dpu.a D() {
      String _snowman = this.u();
      if (_snowman.isEmpty()) {
         return dpu.a.a;
      } else {
         int _snowmanx = this.y.g();
         int _snowmanxx = this.y.h();
         IntList _snowmanxxx = new IntArrayList();
         List<dpu.b> _snowmanxxxx = Lists.newArrayList();
         MutableInt _snowmanxxxxx = new MutableInt();
         MutableBoolean _snowmanxxxxxx = new MutableBoolean();
         dkj _snowmanxxxxxxx = this.o.b();
         _snowmanxxxxxxx.a(_snowman, 114, ob.a, true, (var6x, var7x, var8x) -> {
            int _snowmanxxxxxxxx = _snowman.getAndIncrement();
            String _snowmanx = _snowman.substring(var7x, var8x);
            _snowman.setValue(_snowmanx.endsWith("\n"));
            String _snowmanxx = StringUtils.stripEnd(_snowmanx, " \n");
            int _snowmanxxx = _snowmanxxxxxxxx * 9;
            dpu.c _snowmanxxxx = this.b(new dpu.c(0, _snowmanxxx));
            _snowman.add(var7x);
            _snowman.add(new dpu.b(var6x, _snowmanxx, _snowmanxxxx.a, _snowmanxxxx.b));
         });
         int[] _snowmanxxxxxxxx = _snowmanxxx.toIntArray();
         boolean _snowmanxxxxxxxxx = _snowmanx == _snowman.length();
         dpu.c _snowmanxxxxxxxxxx;
         if (_snowmanxxxxxxxxx && _snowmanxxxxxx.isTrue()) {
            _snowmanxxxxxxxxxx = new dpu.c(0, _snowmanxxxx.size() * 9);
         } else {
            int _snowmanxxxxxxxxxxx = b(_snowmanxxxxxxxx, _snowmanx);
            int _snowmanxxxxxxxxxxxx = this.o.b(_snowman.substring(_snowmanxxxxxxxx[_snowmanxxxxxxxxxxx], _snowmanx));
            _snowmanxxxxxxxxxx = new dpu.c(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx * 9);
         }

         List<eal> _snowmanxxxxxxxxxxx = Lists.newArrayList();
         if (_snowmanx != _snowmanxx) {
            int _snowmanxxxxxxxxxxxx = Math.min(_snowmanx, _snowmanxx);
            int _snowmanxxxxxxxxxxxxx = Math.max(_snowmanx, _snowmanxx);
            int _snowmanxxxxxxxxxxxxxx = b(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxx = b(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxxxx) {
               int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * 9;
               int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxx];
               _snowmanxxxxxxxxxxx.add(this.a(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx));
            } else {
               int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx + 1 > _snowmanxxxxxxxx.length ? _snowman.length() : _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxx + 1];
               _snowmanxxxxxxxxxxx.add(this.a(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx * 9, _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxx]));

               for (int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx + 1; _snowmanxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx * 9;
                  String _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.substring(_snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxxxxx], _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxxxxx + 1]);
                  int _snowmanxxxxxxxxxxxxxxxxxxxx = (int)_snowmanxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxx);
                  _snowmanxxxxxxxxxxx.add(this.a(new dpu.c(0, _snowmanxxxxxxxxxxxxxxxxxx), new dpu.c(_snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx + 9)));
               }

               _snowmanxxxxxxxxxxx.add(this.a(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxxx], _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx * 9, _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxxx]));
            }
         }

         return new dpu.a(_snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxx.toArray(new dpu.b[0]), _snowmanxxxxxxxxxxx.toArray(new eal[0]));
      }
   }

   private static int b(int[] var0, int var1) {
      int _snowman = Arrays.binarySearch(_snowman, _snowman);
      return _snowman < 0 ? -(_snowman + 2) : _snowman;
   }

   private eal a(String var1, dkj var2, int var3, int var4, int var5, int var6) {
      String _snowman = _snowman.substring(_snowman, _snowman);
      String _snowmanx = _snowman.substring(_snowman, _snowman);
      dpu.c _snowmanxx = new dpu.c((int)_snowman.a(_snowman), _snowman);
      dpu.c _snowmanxxx = new dpu.c((int)_snowman.a(_snowmanx), _snowman + 9);
      return this.a(_snowmanxx, _snowmanxxx);
   }

   private eal a(dpu.c var1, dpu.c var2) {
      dpu.c _snowman = this.b(_snowman);
      dpu.c _snowmanx = this.b(_snowman);
      int _snowmanxx = Math.min(_snowman.a, _snowmanx.a);
      int _snowmanxxx = Math.max(_snowman.a, _snowmanx.a);
      int _snowmanxxxx = Math.min(_snowman.b, _snowmanx.b);
      int _snowmanxxxxx = Math.max(_snowman.b, _snowmanx.b);
      return new eal(_snowmanxx, _snowmanxxxx, _snowmanxxx - _snowmanxx, _snowmanxxxxx - _snowmanxxxx);
   }

   static class a {
      private static final dpu.a a = new dpu.a("", new dpu.c(0, 0), true, new int[]{0}, new dpu.b[]{new dpu.b(ob.a, "", 0, 0)}, new eal[0]);
      private final String b;
      private final dpu.c c;
      private final boolean d;
      private final int[] e;
      private final dpu.b[] f;
      private final eal[] g;

      public a(String var1, dpu.c var2, boolean var3, int[] var4, dpu.b[] var5, eal[] var6) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
      }

      public int a(dku var1, dpu.c var2) {
         int _snowman = _snowman.b / 9;
         if (_snowman < 0) {
            return 0;
         } else if (_snowman >= this.f.length) {
            return this.b.length();
         } else {
            dpu.b _snowmanx = this.f[_snowman];
            return this.e[_snowman] + _snowman.b().a(_snowmanx.b, _snowman.a, _snowmanx.a);
         }
      }

      public int a(int var1, int var2) {
         int _snowman = dpu.b(this.e, _snowman);
         int _snowmanx = _snowman + _snowman;
         int _snowmanxx;
         if (0 <= _snowmanx && _snowmanx < this.e.length) {
            int _snowmanxxx = _snowman - this.e[_snowman];
            int _snowmanxxxx = this.f[_snowmanx].b.length();
            _snowmanxx = this.e[_snowmanx] + Math.min(_snowmanxxx, _snowmanxxxx);
         } else {
            _snowmanxx = _snowman;
         }

         return _snowmanxx;
      }

      public int a(int var1) {
         int _snowman = dpu.b(this.e, _snowman);
         return this.e[_snowman];
      }

      public int b(int var1) {
         int _snowman = dpu.b(this.e, _snowman);
         return this.e[_snowman] + this.f[_snowman].b.length();
      }
   }

   static class b {
      private final ob a;
      private final String b;
      private final nr c;
      private final int d;
      private final int e;

      public b(ob var1, String var2, int var3, int var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.c = new oe(_snowman).a(_snowman);
      }
   }

   static class c {
      public final int a;
      public final int b;

      c(int var1, int var2) {
         this.a = _snowman;
         this.b = _snowman;
      }
   }
}
