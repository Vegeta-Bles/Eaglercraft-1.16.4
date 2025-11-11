import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;

public class dqp extends dpp<bjc> {
   private static final vk A = new vk("textures/gui/container/loom.png");
   private static final int B = (ccb.P - ccb.Q - 1 + 4 - 1) / 4;
   private final dwn C;
   @Nullable
   private List<Pair<ccb, bkx>> D;
   private bmb E = bmb.b;
   private bmb F = bmb.b;
   private bmb G = bmb.b;
   private boolean H;
   private boolean I;
   private boolean J;
   private float K;
   private boolean L;
   private int M = 1;

   public dqp(bjc var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
      this.C = ebz.a();
      _snowman.a(this::i);
      this.q -= 2;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman);
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      this.a(_snowman);
      this.i.M().a(A);
      int _snowman = this.w;
      int _snowmanx = this.x;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.c);
      bjr _snowmanxx = this.t.f();
      bjr _snowmanxxx = this.t.g();
      bjr _snowmanxxxx = this.t.h();
      bjr _snowmanxxxxx = this.t.i();
      if (!_snowmanxx.f()) {
         this.b(_snowman, _snowman + _snowmanxx.e, _snowmanx + _snowmanxx.f, this.b, 0, 16, 16);
      }

      if (!_snowmanxxx.f()) {
         this.b(_snowman, _snowman + _snowmanxxx.e, _snowmanx + _snowmanxxx.f, this.b + 16, 0, 16, 16);
      }

      if (!_snowmanxxxx.f()) {
         this.b(_snowman, _snowman + _snowmanxxxx.e, _snowmanx + _snowmanxxxx.f, this.b + 32, 0, 16, 16);
      }

      int _snowmanxxxxxx = (int)(41.0F * this.K);
      this.b(_snowman, _snowman + 119, _snowmanx + 13 + _snowmanxxxxxx, 232 + (this.H ? 0 : 12), 0, 12, 15);
      dep.c();
      if (this.D != null && !this.J) {
         eag.a _snowmanxxxxxxx = this.i.aE().b();
         _snowman.a();
         _snowman.a((double)(_snowman + 139), (double)(_snowmanx + 52), 0.0);
         _snowman.a(24.0F, -24.0F, 1.0F);
         _snowman.a(0.5, 0.5, 0.5);
         float _snowmanxxxxxxxx = 0.6666667F;
         _snowman.a(0.6666667F, -0.6666667F, -0.6666667F);
         this.C.d = 0.0F;
         this.C.b = -32.0F;
         ebz.a(_snowman, _snowmanxxxxxxx, 15728880, ejw.a, this.C, els.f, true, this.D);
         _snowman.b();
         _snowmanxxxxxxx.a();
      } else if (this.J) {
         this.b(_snowman, _snowman + _snowmanxxxxx.e - 2, _snowmanx + _snowmanxxxxx.f - 2, this.b, 17, 17, 16);
      }

      if (this.H) {
         int _snowmanxxxxxxx = _snowman + 60;
         int _snowmanxxxxxxxx = _snowmanx + 13;
         int _snowmanxxxxxxxxx = this.M + 16;

         for (int _snowmanxxxxxxxxxx = this.M; _snowmanxxxxxxxxxx < _snowmanxxxxxxxxx && _snowmanxxxxxxxxxx < ccb.P - ccb.Q; _snowmanxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx - this.M;
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx + _snowmanxxxxxxxxxxx % 4 * 14;
            int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx + _snowmanxxxxxxxxxxx / 4 * 14;
            this.i.M().a(A);
            int _snowmanxxxxxxxxxxxxxx = this.c;
            if (_snowmanxxxxxxxxxx == this.t.e()) {
               _snowmanxxxxxxxxxxxxxx += 14;
            } else if (_snowman >= _snowmanxxxxxxxxxxxx && _snowman >= _snowmanxxxxxxxxxxxxx && _snowman < _snowmanxxxxxxxxxxxx + 14 && _snowman < _snowmanxxxxxxxxxxxxx + 14) {
               _snowmanxxxxxxxxxxxxxx += 28;
            }

            this.b(_snowman, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 0, _snowmanxxxxxxxxxxxxxx, 14, 14);
            this.c(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
         }
      } else if (this.I) {
         int _snowmanxxxxxxx = _snowman + 60;
         int _snowmanxxxxxxxx = _snowmanx + 13;
         this.i.M().a(A);
         this.b(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx, 0, this.c, 14, 14);
         int _snowmanxxxxxxxxx = this.t.e();
         this.c(_snowmanxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
      }

      dep.d();
   }

   private void c(int var1, int var2, int var3) {
      bmb _snowman = new bmb(bmd.pT);
      md _snowmanx = _snowman.a("BlockEntityTag");
      mj _snowmanxx = new ccb.a().a(ccb.a, bkx.h).a(ccb.values()[_snowman], bkx.a).a();
      _snowmanx.a("Patterns", _snowmanxx);
      dfm _snowmanxxx = new dfm();
      _snowmanxxx.a();
      _snowmanxxx.a((double)((float)_snowman + 0.5F), (double)(_snowman + 16), 0.0);
      _snowmanxxx.a(6.0F, -6.0F, 1.0F);
      _snowmanxxx.a(0.5, 0.5, 0.0);
      _snowmanxxx.a(0.5, 0.5, 0.5);
      float _snowmanxxxx = 0.6666667F;
      _snowmanxxx.a(0.6666667F, -0.6666667F, -0.6666667F);
      eag.a _snowmanxxxxx = this.i.aE().b();
      this.C.d = 0.0F;
      this.C.b = -32.0F;
      List<Pair<ccb, bkx>> _snowmanxxxxxx = cca.a(bkx.h, cca.a(_snowman));
      ebz.a(_snowmanxxx, _snowmanxxxxx, 15728880, ejw.a, this.C, els.f, true, _snowmanxxxxxx);
      _snowmanxxx.b();
      _snowmanxxxxx.a();
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      this.L = false;
      if (this.H) {
         int _snowman = this.w + 60;
         int _snowmanx = this.x + 13;
         int _snowmanxx = this.M + 16;

         for (int _snowmanxxx = this.M; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
            int _snowmanxxxx = _snowmanxxx - this.M;
            double _snowmanxxxxx = _snowman - (double)(_snowman + _snowmanxxxx % 4 * 14);
            double _snowmanxxxxxx = _snowman - (double)(_snowmanx + _snowmanxxxx / 4 * 14);
            if (_snowmanxxxxx >= 0.0 && _snowmanxxxxxx >= 0.0 && _snowmanxxxxx < 14.0 && _snowmanxxxxxx < 14.0 && this.t.a(this.i.s, _snowmanxxx)) {
               djz.C().W().a(emp.a(adq.pG, 1.0F));
               this.i.q.a(this.t.b, _snowmanxxx);
               return true;
            }
         }

         _snowman = this.w + 119;
         _snowmanx = this.x + 9;
         if (_snowman >= (double)_snowman && _snowman < (double)(_snowman + 12) && _snowman >= (double)_snowmanx && _snowman < (double)(_snowmanx + 56)) {
            this.L = true;
         }
      }

      return super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(double var1, double var3, int var5, double var6, double var8) {
      if (this.L && this.H) {
         int _snowman = this.x + 13;
         int _snowmanx = _snowman + 56;
         this.K = ((float)_snowman - (float)_snowman - 7.5F) / ((float)(_snowmanx - _snowman) - 15.0F);
         this.K = afm.a(this.K, 0.0F, 1.0F);
         int _snowmanxx = B - 4;
         int _snowmanxxx = (int)((double)(this.K * (float)_snowmanxx) + 0.5);
         if (_snowmanxxx < 0) {
            _snowmanxxx = 0;
         }

         this.M = 1 + _snowmanxxx * 4;
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean a(double var1, double var3, double var5) {
      if (this.H) {
         int _snowman = B - 4;
         this.K = (float)((double)this.K - _snowman / (double)_snowman);
         this.K = afm.a(this.K, 0.0F, 1.0F);
         this.M = 1 + (int)((double)(this.K * (float)_snowman) + 0.5) * 4;
      }

      return true;
   }

   @Override
   protected boolean a(double var1, double var3, int var5, int var6, int var7) {
      return _snowman < (double)_snowman || _snowman < (double)_snowman || _snowman >= (double)(_snowman + this.b) || _snowman >= (double)(_snowman + this.c);
   }

   private void i() {
      bmb _snowman = this.t.i().e();
      if (_snowman.a()) {
         this.D = null;
      } else {
         this.D = cca.a(((bke)_snowman.b()).b(), cca.a(_snowman));
      }

      bmb _snowmanx = this.t.f().e();
      bmb _snowmanxx = this.t.g().e();
      bmb _snowmanxxx = this.t.h().e();
      md _snowmanxxxx = _snowmanx.a("BlockEntityTag");
      this.J = _snowmanxxxx.c("Patterns", 9) && !_snowmanx.a() && _snowmanxxxx.d("Patterns", 10).size() >= 6;
      if (this.J) {
         this.D = null;
      }

      if (!bmb.b(_snowmanx, this.E) || !bmb.b(_snowmanxx, this.F) || !bmb.b(_snowmanxxx, this.G)) {
         this.H = !_snowmanx.a() && !_snowmanxx.a() && _snowmanxxx.a() && !this.J;
         this.I = !this.J && !_snowmanxxx.a() && !_snowmanx.a() && !_snowmanxx.a();
      }

      this.E = _snowmanx.i();
      this.F = _snowmanxx.i();
      this.G = _snowmanxxx.i();
   }
}
