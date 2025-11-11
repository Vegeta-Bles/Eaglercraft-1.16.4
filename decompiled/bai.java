import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class bai extends azx implements aqs {
   protected static final us<Byte> b = uv.a(bai.class, uu.a);
   private int c;
   private int d;
   private static final afh bo = afu.a(20, 39);
   private int bp;
   private UUID bq;

   public bai(aqe<? extends bai> var1, brx var2) {
      super(_snowman, _snowman);
      this.G = 1.0F;
   }

   @Override
   protected void o() {
      this.bk.a(1, new awf(this, 1.0, true));
      this.bk.a(2, new awl(this, 0.9, 32.0F));
      this.bk.a(2, new awg(this, 0.6, false));
      this.bk.a(4, new avx(this, 0.6));
      this.bk.a(5, new awn(this));
      this.bk.a(7, new awd(this, bfw.class, 6.0F));
      this.bk.a(8, new aws(this));
      this.bl.a(1, new axo(this));
      this.bl.a(2, new axp(this));
      this.bl.a(3, new axq<>(this, bfw.class, 10, true, false, this::a_));
      this.bl.a(3, new axq<>(this, aqn.class, 5, false, false, var0 -> var0 instanceof bdi && !(var0 instanceof bdc)));
      this.bl.a(4, new axw<>(this, false));
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, (byte)0);
   }

   public static ark.a m() {
      return aqn.p().a(arl.a, 100.0).a(arl.d, 0.25).a(arl.c, 1.0).a(arl.f, 15.0);
   }

   @Override
   protected int l(int var1) {
      return _snowman;
   }

   @Override
   protected void C(aqa var1) {
      if (_snowman instanceof bdi && !(_snowman instanceof bdc) && this.cY().nextInt(20) == 0) {
         this.h((aqm)_snowman);
      }

      super.C(_snowman);
   }

   @Override
   public void k() {
      super.k();
      if (this.c > 0) {
         this.c--;
      }

      if (this.d > 0) {
         this.d--;
      }

      if (c(this.cC()) > 2.5000003E-7F && this.J.nextInt(5) == 0) {
         int _snowman = afm.c(this.cD());
         int _snowmanx = afm.c(this.cE() - 0.2F);
         int _snowmanxx = afm.c(this.cH());
         ceh _snowmanxxx = this.l.d_(new fx(_snowman, _snowmanx, _snowmanxx));
         if (!_snowmanxxx.g()) {
            this.l
               .a(
                  new hc(hh.d, _snowmanxxx),
                  this.cD() + ((double)this.J.nextFloat() - 0.5) * (double)this.cy(),
                  this.cE() + 0.1,
                  this.cH() + ((double)this.J.nextFloat() - 0.5) * (double)this.cy(),
                  4.0 * ((double)this.J.nextFloat() - 0.5),
                  0.5,
                  ((double)this.J.nextFloat() - 0.5) * 4.0
               );
         }
      }

      if (!this.l.v) {
         this.a((aag)this.l, true);
      }
   }

   @Override
   public boolean a(aqe<?> var1) {
      if (this.eN() && _snowman == aqe.bc) {
         return false;
      } else {
         return _snowman == aqe.m ? false : super.a(_snowman);
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("PlayerCreated", this.eN());
      this.c(_snowman);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.u(_snowman.q("PlayerCreated"));
      this.a((aag)this.l, _snowman);
   }

   @Override
   public void G_() {
      this.a_(bo.a(this.J));
   }

   @Override
   public void a_(int var1) {
      this.bp = _snowman;
   }

   @Override
   public int E_() {
      return this.bp;
   }

   @Override
   public void a(@Nullable UUID var1) {
      this.bq = _snowman;
   }

   @Override
   public UUID F_() {
      return this.bq;
   }

   private float eO() {
      return (float)this.b(arl.f);
   }

   @Override
   public boolean B(aqa var1) {
      this.c = 10;
      this.l.a(this, (byte)4);
      float _snowman = this.eO();
      float _snowmanx = (int)_snowman > 0 ? _snowman / 2.0F + (float)this.J.nextInt((int)_snowman) : _snowman;
      boolean _snowmanxx = _snowman.a(apk.c(this), _snowmanx);
      if (_snowmanxx) {
         _snowman.f(_snowman.cC().b(0.0, 0.4F, 0.0));
         this.a(this, _snowman);
      }

      this.a(adq.gx, 1.0F, 1.0F);
      return _snowmanxx;
   }

   @Override
   public boolean a(apk var1, float var2) {
      bai.a _snowman = this.eK();
      boolean _snowmanx = super.a(_snowman, _snowman);
      if (_snowmanx && this.eK() != _snowman) {
         this.a(adq.gy, 1.0F, 1.0F);
      }

      return _snowmanx;
   }

   public bai.a eK() {
      return bai.a.a(this.dk() / this.dx());
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 4) {
         this.c = 10;
         this.a(adq.gx, 1.0F, 1.0F);
      } else if (_snowman == 11) {
         this.d = 400;
      } else if (_snowman == 34) {
         this.d = 0;
      } else {
         super.a(_snowman);
      }
   }

   public int eL() {
      return this.c;
   }

   public void t(boolean var1) {
      if (_snowman) {
         this.d = 400;
         this.l.a(this, (byte)11);
      } else {
         this.d = 0;
         this.l.a(this, (byte)34);
      }
   }

   @Override
   protected adp e(apk var1) {
      return adq.gA;
   }

   @Override
   protected adp dq() {
      return adq.gz;
   }

   @Override
   protected aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      blx _snowmanx = _snowman.b();
      if (_snowmanx != bmd.kh) {
         return aou.c;
      } else {
         float _snowmanxx = this.dk();
         this.b(25.0F);
         if (this.dk() == _snowmanxx) {
            return aou.c;
         } else {
            float _snowmanxxx = 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F;
            this.a(adq.gB, 1.0F, _snowmanxxx);
            if (!_snowman.bC.d) {
               _snowman.g(1);
            }

            return aou.a(this.l.v);
         }
      }
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.gC, 1.0F, 1.0F);
   }

   public int eM() {
      return this.d;
   }

   public boolean eN() {
      return (this.R.a(b) & 1) != 0;
   }

   public void u(boolean var1) {
      byte _snowman = this.R.a(b);
      if (_snowman) {
         this.R.b(b, (byte)(_snowman | 1));
      } else {
         this.R.b(b, (byte)(_snowman & -2));
      }
   }

   @Override
   public void a(apk var1) {
      super.a(_snowman);
   }

   @Override
   public boolean a(brz var1) {
      fx _snowman = this.cB();
      fx _snowmanx = _snowman.c();
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      if (!_snowmanxx.a(_snowman, _snowmanx, this)) {
         return false;
      } else {
         for (int _snowmanxxx = 1; _snowmanxxx < 3; _snowmanxxx++) {
            fx _snowmanxxxx = _snowman.b(_snowmanxxx);
            ceh _snowmanxxxxx = _snowman.d_(_snowmanxxxx);
            if (!bsg.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxx.m(), aqe.K)) {
               return false;
            }
         }

         return bsg.a(_snowman, _snowman, _snowman.d_(_snowman), cuy.a.h(), aqe.K) && _snowman.j(this);
      }
   }

   @Override
   public dcn cf() {
      return new dcn(0.0, (double)(0.875F * this.ce()), (double)(this.cy() * 0.4F));
   }

   public static enum a {
      a(1.0F),
      b(0.75F),
      c(0.5F),
      d(0.25F);

      private static final List<bai.a> e = Stream.of(values())
         .sorted(Comparator.comparingDouble(var0 -> (double)var0.f))
         .collect(ImmutableList.toImmutableList());
      private final float f;

      private a(float var3) {
         this.f = _snowman;
      }

      public static bai.a a(float var0) {
         for (bai.a _snowman : e) {
            if (_snowman < _snowman.f) {
               return _snowman;
            }
         }

         return a;
      }
   }
}
