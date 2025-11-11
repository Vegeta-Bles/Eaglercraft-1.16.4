import com.mojang.blaze3d.systems.RenderSystem;
import java.util.stream.IntStream;

public class dqv extends dot {
   private final ecn.a a = new ecn.a();
   private final cdf b;
   private int c;
   private int p;
   private dmy q;
   private final String[] r;

   public dqv(cdf var1) {
      super(new of("sign.edit"));
      this.r = IntStream.range(0, 4).mapToObj(_snowman::a).map(nr::getString).toArray(String[]::new);
      this.b = _snowman;
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      this.a(new dlj(this.k / 2 - 100, this.l / 4 + 120, 200, 20, nq.c, var1 -> this.h()));
      this.b.a(false);
      this.q = new dmy(() -> this.r[this.p], var1 -> {
         this.r[this.p] = var1;
         this.b.a(this.p, new oe(var1));
      }, dmy.a(this.i), dmy.c(this.i), var1 -> this.i.g.b(var1) <= 90);
   }

   @Override
   public void e() {
      this.i.m.a(false);
      dwu _snowman = this.i.w();
      if (_snowman != null) {
         _snowman.a(new tp(this.b.o(), this.r[0], this.r[1], this.r[2], this.r[3]));
      }

      this.b.a(true);
   }

   @Override
   public void d() {
      this.c++;
      if (!this.b.u().a(this.b.p().b())) {
         this.h();
      }
   }

   private void h() {
      this.b.X_();
      this.i.a(null);
   }

   @Override
   public boolean a(char var1, int var2) {
      this.q.a(_snowman);
      return true;
   }

   @Override
   public void at_() {
      this.h();
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 265) {
         this.p = this.p - 1 & 3;
         this.q.f();
         return true;
      } else if (_snowman == 264 || _snowman == 257 || _snowman == 335) {
         this.p = this.p + 1 & 3;
         this.q.f();
         return true;
      } else {
         return this.q.a(_snowman) ? true : super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      dep.c();
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 40, 16777215);
      _snowman.a();
      _snowman.a((double)(this.k / 2), 0.0, 50.0);
      float _snowman = 93.75F;
      _snowman.a(93.75F, -93.75F, 93.75F);
      _snowman.a(0.0, -1.3125, 0.0);
      ceh _snowmanx = this.b.p();
      boolean _snowmanxx = _snowmanx.b() instanceof cal;
      if (!_snowmanxx) {
         _snowman.a(0.0, -0.3125, 0.0);
      }

      boolean _snowmanxxx = this.c / 6 % 2 == 0;
      float _snowmanxxxx = 0.6666667F;
      _snowman.a();
      _snowman.a(0.6666667F, -0.6666667F, -0.6666667F);
      eag.a _snowmanxxxxx = this.i.aE().b();
      elr _snowmanxxxxxx = ecn.a(_snowmanx.b());
      dfq _snowmanxxxxxxx = _snowmanxxxxxx.a(_snowmanxxxxx, this.a::a);
      this.a.a.a(_snowman, _snowmanxxxxxxx, 15728880, ejw.a);
      if (_snowmanxx) {
         this.a.b.a(_snowman, _snowmanxxxxxxx, 15728880, ejw.a);
      }

      _snowman.b();
      float _snowmanxxxxxxxx = 0.010416667F;
      _snowman.a(0.0, 0.33333334F, 0.046666667F);
      _snowman.a(0.010416667F, -0.010416667F, 0.010416667F);
      int _snowmanxxxxxxxxx = this.b.g().h();
      int _snowmanxxxxxxxxxx = this.q.g();
      int _snowmanxxxxxxxxxxx = this.q.h();
      int _snowmanxxxxxxxxxxxx = this.p * 10 - this.r.length * 5;
      b _snowmanxxxxxxxxxxxxx = _snowman.c().a();

      for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < this.r.length; _snowmanxxxxxxxxxxxxxx++) {
         String _snowmanxxxxxxxxxxxxxxx = this.r[_snowmanxxxxxxxxxxxxxx];
         if (_snowmanxxxxxxxxxxxxxxx != null) {
            if (this.o.a()) {
               _snowmanxxxxxxxxxxxxxxx = this.o.a(_snowmanxxxxxxxxxxxxxxx);
            }

            float _snowmanxxxxxxxxxxxxxxxx = (float)(-this.i.g.b(_snowmanxxxxxxxxxxxxxxx) / 2);
            this.i
               .g
               .a(
                  _snowmanxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxx,
                  (float)(_snowmanxxxxxxxxxxxxxx * 10 - this.r.length * 5),
                  _snowmanxxxxxxxxx,
                  false,
                  _snowmanxxxxxxxxxxxxx,
                  _snowmanxxxxx,
                  false,
                  0,
                  15728880,
                  false
               );
            if (_snowmanxxxxxxxxxxxxxx == this.p && _snowmanxxxxxxxxxx >= 0 && _snowmanxxx) {
               int _snowmanxxxxxxxxxxxxxxxxx = this.i.g.b(_snowmanxxxxxxxxxxxxxxx.substring(0, Math.max(Math.min(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx.length()), 0)));
               int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx - this.i.g.b(_snowmanxxxxxxxxxxxxxxx) / 2;
               if (_snowmanxxxxxxxxxx >= _snowmanxxxxxxxxxxxxxxx.length()) {
                  this.i.g.a("_", (float)_snowmanxxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxx, false, _snowmanxxxxxxxxxxxxx, _snowmanxxxxx, false, 0, 15728880, false);
               }
            }
         }
      }

      _snowmanxxxxx.a();

      for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < this.r.length; _snowmanxxxxxxxxxxxxxxx++) {
         String _snowmanxxxxxxxxxxxxxxxx = this.r[_snowmanxxxxxxxxxxxxxxx];
         if (_snowmanxxxxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxxxx == this.p && _snowmanxxxxxxxxxx >= 0) {
            int _snowmanxxxxxxxxxxxxxxxxx = this.i.g.b(_snowmanxxxxxxxxxxxxxxxx.substring(0, Math.max(Math.min(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx.length()), 0)));
            int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx - this.i.g.b(_snowmanxxxxxxxxxxxxxxxx) / 2;
            if (_snowmanxxx && _snowmanxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx.length()) {
               a(_snowman, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxxx + 9, 0xFF000000 | _snowmanxxxxxxxxx);
            }

            if (_snowmanxxxxxxxxxxx != _snowmanxxxxxxxxxx) {
               int _snowmanxxxxxxxxxxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
               int _snowmanxxxxxxxxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
               int _snowmanxxxxxxxxxxxxxxxxxxxxx = this.i.g.b(_snowmanxxxxxxxxxxxxxxxx.substring(0, _snowmanxxxxxxxxxxxxxxxxxxx)) - this.i.g.b(_snowmanxxxxxxxxxxxxxxxx) / 2;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxx = this.i.g.b(_snowmanxxxxxxxxxxxxxxxx.substring(0, _snowmanxxxxxxxxxxxxxxxxxxxx)) - this.i.g.b(_snowmanxxxxxxxxxxxxxxxx) / 2;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
               dfo _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = dfo.a();
               dfh _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.c();
               RenderSystem.disableTexture();
               RenderSystem.enableColorLogicOp();
               RenderSystem.logicOp(dem.o.n);
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a(7, dfk.l);
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxx, (float)(_snowmanxxxxxxxxxxxx + 9), 0.0F).a(0, 0, 255, 255).d();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, (float)(_snowmanxxxxxxxxxxxx + 9), 0.0F).a(0, 0, 255, 255).d();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxx, 0.0F).a(0, 0, 255, 255).d();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxx, 0.0F).a(0, 0, 255, 255).d();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.c();
               dfi.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx);
               RenderSystem.disableColorLogicOp();
               RenderSystem.enableTexture();
            }
         }
      }

      _snowman.b();
      dep.d();
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
