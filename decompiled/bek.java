import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.UUID;
import javax.annotation.Nullable;

public class bek extends bej implements bfl {
   private static final us<Boolean> b = uv.a(bek.class, uu.i);
   private static final us<bfk> c = uv.a(bek.class, uu.q);
   private int d;
   private UUID bo;
   private mt bp;
   private md bq;
   private int br;

   public bek(aqe<? extends bek> var1, brx var2) {
      super(_snowman, _snowman);
      this.a(this.eX().a(gm.ai.a(this.J)));
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, false);
      this.R.a(c, new bfk(bfo.c, bfm.a, 1));
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      bfk.a.encodeStart(mo.a, this.eX()).resultOrPartial(h::error).ifPresent(var1x -> _snowman.a("VillagerData", var1x));
      if (this.bq != null) {
         _snowman.a("Offers", this.bq);
      }

      if (this.bp != null) {
         _snowman.a("Gossips", this.bp);
      }

      _snowman.b("ConversionTime", this.eW() ? this.d : -1);
      if (this.bo != null) {
         _snowman.a("ConversionPlayer", this.bo);
      }

      _snowman.b("Xp", this.br);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("VillagerData", 10)) {
         DataResult<bfk> _snowman = bfk.a.parse(new Dynamic(mo.a, _snowman.c("VillagerData")));
         _snowman.resultOrPartial(h::error).ifPresent(this::a);
      }

      if (_snowman.c("Offers", 10)) {
         this.bq = _snowman.p("Offers");
      }

      if (_snowman.c("Gossips", 10)) {
         this.bp = _snowman.d("Gossips", 10);
      }

      if (_snowman.c("ConversionTime", 99) && _snowman.h("ConversionTime") > -1) {
         this.a(_snowman.b("ConversionPlayer") ? _snowman.a("ConversionPlayer") : null, _snowman.h("ConversionTime"));
      }

      if (_snowman.c("Xp", 3)) {
         this.br = _snowman.h("Xp");
      }
   }

   @Override
   public void j() {
      if (!this.l.v && this.aX() && this.eW()) {
         int _snowman = this.eZ();
         this.d -= _snowman;
         if (this.d <= 0) {
            this.c((aag)this.l);
         }
      }

      super.j();
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() == bmd.lA) {
         if (this.a(apw.r)) {
            if (!_snowman.bC.d) {
               _snowman.g(1);
            }

            if (!this.l.v) {
               this.a(_snowman.bS(), this.J.nextInt(2401) + 3600);
            }

            return aou.a;
         } else {
            return aou.b;
         }
      } else {
         return super.b(_snowman, _snowman);
      }
   }

   @Override
   protected boolean eN() {
      return false;
   }

   @Override
   public boolean h(double var1) {
      return !this.eW() && this.br == 0;
   }

   public boolean eW() {
      return this.ab().a(b);
   }

   private void a(@Nullable UUID var1, int var2) {
      this.bo = _snowman;
      this.d = _snowman;
      this.ab().b(b, true);
      this.d(apw.r);
      this.c(new apu(apw.e, _snowman, Math.min(this.l.ad().a() - 1, 0)));
      this.l.a(this, (byte)16);
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 16) {
         if (!this.aA()) {
            this.l.a(this.cD(), this.cG(), this.cH(), adq.sa, this.cu(), 1.0F + this.J.nextFloat(), this.J.nextFloat() * 0.7F + 0.3F, false);
         }
      } else {
         super.a(_snowman);
      }
   }

   private void c(aag var1) {
      bfj _snowman = this.a(aqe.aP, false);

      for (aqf _snowmanx : aqf.values()) {
         bmb _snowmanxx = this.b(_snowmanx);
         if (!_snowmanxx.a()) {
            if (bpu.d(_snowmanxx)) {
               _snowman.a_(_snowmanx.b() + 300, _snowmanxx);
            } else {
               double _snowmanxxx = (double)this.e(_snowmanx);
               if (_snowmanxxx > 1.0) {
                  this.a(_snowmanxx);
               }
            }
         }
      }

      _snowman.a(this.eX());
      if (this.bp != null) {
         _snowman.a(this.bp);
      }

      if (this.bq != null) {
         _snowman.b(new bqw(this.bq));
      }

      _snowman.u(this.br);
      _snowman.a(_snowman, _snowman.d(_snowman.cB()), aqp.i, null, null);
      if (this.bo != null) {
         bfw _snowmanxx = _snowman.b(this.bo);
         if (_snowmanxx instanceof aah) {
            ac.r.a((aah)_snowmanxx, this, _snowman);
            _snowman.a(azl.a, _snowmanxx, _snowman);
         }
      }

      _snowman.c(new apu(apw.i, 200, 0));
      if (!this.aA()) {
         _snowman.a(null, 1027, this.cB(), 0);
      }
   }

   private int eZ() {
      int _snowman = 1;
      if (this.J.nextFloat() < 0.01F) {
         int _snowmanx = 0;
         fx.a _snowmanxx = new fx.a();

         for (int _snowmanxxx = (int)this.cD() - 4; _snowmanxxx < (int)this.cD() + 4 && _snowmanx < 14; _snowmanxxx++) {
            for (int _snowmanxxxx = (int)this.cE() - 4; _snowmanxxxx < (int)this.cE() + 4 && _snowmanx < 14; _snowmanxxxx++) {
               for (int _snowmanxxxxx = (int)this.cH() - 4; _snowmanxxxxx < (int)this.cH() + 4 && _snowmanx < 14; _snowmanxxxxx++) {
                  buo _snowmanxxxxxx = this.l.d_(_snowmanxx.d(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx)).b();
                  if (_snowmanxxxxxx == bup.dH || _snowmanxxxxxx instanceof buj) {
                     if (this.J.nextFloat() < 0.3F) {
                        _snowman++;
                     }

                     _snowmanx++;
                  }
               }
            }
         }
      }

      return _snowman;
   }

   @Override
   protected float dH() {
      return this.w_() ? (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 2.0F : (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F;
   }

   @Override
   public adp I() {
      return adq.rY;
   }

   @Override
   public adp e(apk var1) {
      return adq.sc;
   }

   @Override
   public adp dq() {
      return adq.sb;
   }

   @Override
   public adp eL() {
      return adq.sd;
   }

   @Override
   protected bmb eM() {
      return bmb.b;
   }

   public void g(md var1) {
      this.bq = _snowman;
   }

   public void a(mt var1) {
      this.bp = _snowman;
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.a(this.eX().a(bfo.a(_snowman.i(this.cB()))));
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void a(bfk var1) {
      bfk _snowman = this.eX();
      if (_snowman.b() != _snowman.b()) {
         this.bq = null;
      }

      this.R.b(c, _snowman);
   }

   @Override
   public bfk eX() {
      return this.R.a(c);
   }

   @Override
   public void a(int var1) {
      this.br = _snowman;
   }
}
