import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class bas extends azz implements arb {
   private static final us<Byte> bo = uv.a(bas.class, uu.a);
   private static final Map<bkx, brw> bp = x.a(Maps.newEnumMap(bkx.class), var0 -> {
      var0.put(bkx.a, bup.aY);
      var0.put(bkx.b, bup.aZ);
      var0.put(bkx.c, bup.ba);
      var0.put(bkx.d, bup.bb);
      var0.put(bkx.e, bup.bc);
      var0.put(bkx.f, bup.bd);
      var0.put(bkx.g, bup.be);
      var0.put(bkx.h, bup.bf);
      var0.put(bkx.i, bup.bg);
      var0.put(bkx.j, bup.bh);
      var0.put(bkx.k, bup.bi);
      var0.put(bkx.l, bup.bj);
      var0.put(bkx.m, bup.bk);
      var0.put(bkx.n, bup.bl);
      var0.put(bkx.o, bup.bm);
      var0.put(bkx.p, bup.bn);
   });
   private static final Map<bkx, float[]> bq = Maps.newEnumMap(Arrays.stream(bkx.values()).collect(Collectors.toMap(var0 -> (bkx)var0, bas::c)));
   private int br;
   private avn bs;

   private static float[] c(bkx var0) {
      if (_snowman == bkx.a) {
         return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
      } else {
         float[] _snowman = _snowman.e();
         float _snowmanx = 0.75F;
         return new float[]{_snowman[0] * 0.75F, _snowman[1] * 0.75F, _snowman[2] * 0.75F};
      }
   }

   public static float[] a(bkx var0) {
      return bq.get(_snowman);
   }

   public bas(aqe<? extends bas> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      this.bs = new avn(this);
      this.bk.a(0, new avp(this));
      this.bk.a(1, new awp(this, 1.25));
      this.bk.a(2, new avi(this, 1.0));
      this.bk.a(3, new axf(this, 1.1, bon.a(bmd.kW), false));
      this.bk.a(4, new avu(this, 1.1));
      this.bk.a(5, this.bs);
      this.bk.a(6, new axk(this, 1.0));
      this.bk.a(7, new awd(this, bfw.class, 6.0F));
      this.bk.a(8, new aws(this));
   }

   @Override
   protected void N() {
      this.br = this.bs.g();
      super.N();
   }

   @Override
   public void k() {
      if (this.l.v) {
         this.br = Math.max(0, this.br - 1);
      }

      super.k();
   }

   public static ark.a eK() {
      return aqn.p().a(arl.a, 8.0).a(arl.d, 0.23F);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bo, (byte)0);
   }

   @Override
   public vk J() {
      if (this.eM()) {
         return this.X().i();
      } else {
         switch (this.eL()) {
            case a:
            default:
               return cyq.Q;
            case b:
               return cyq.R;
            case c:
               return cyq.S;
            case d:
               return cyq.T;
            case e:
               return cyq.U;
            case f:
               return cyq.V;
            case g:
               return cyq.W;
            case h:
               return cyq.X;
            case i:
               return cyq.Y;
            case j:
               return cyq.Z;
            case k:
               return cyq.aa;
            case l:
               return cyq.ab;
            case m:
               return cyq.ac;
            case n:
               return cyq.ad;
            case o:
               return cyq.ae;
            case p:
               return cyq.af;
         }
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 10) {
         this.br = 40;
      } else {
         super.a(_snowman);
      }
   }

   public float y(float var1) {
      if (this.br <= 0) {
         return 0.0F;
      } else if (this.br >= 4 && this.br <= 36) {
         return 1.0F;
      } else {
         return this.br < 4 ? ((float)this.br - _snowman) / 4.0F : -((float)(this.br - 40) - _snowman) / 4.0F;
      }
   }

   public float z(float var1) {
      if (this.br > 4 && this.br <= 36) {
         float _snowman = ((float)(this.br - 4) - _snowman) / 32.0F;
         return (float) (Math.PI / 5) + 0.21991149F * afm.a(_snowman * 28.7F);
      } else {
         return this.br > 0 ? (float) (Math.PI / 5) : this.q * (float) (Math.PI / 180.0);
      }
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() == bmd.ng) {
         if (!this.l.v && this.K_()) {
            this.a(adr.h);
            _snowman.a(1, _snowman, var1x -> var1x.d(_snowman));
            return aou.a;
         } else {
            return aou.b;
         }
      } else {
         return super.b(_snowman, _snowman);
      }
   }

   @Override
   public void a(adr var1) {
      this.l.a(null, this, adq.mS, _snowman, 1.0F, 1.0F);
      this.t(true);
      int _snowman = 1 + this.J.nextInt(3);

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         bcv _snowmanxx = this.a(bp.get(this.eL()), 1);
         if (_snowmanxx != null) {
            _snowmanxx.f(
               _snowmanxx.cC()
                  .b(
                     (double)((this.J.nextFloat() - this.J.nextFloat()) * 0.1F),
                     (double)(this.J.nextFloat() * 0.05F),
                     (double)((this.J.nextFloat() - this.J.nextFloat()) * 0.1F)
                  )
            );
         }
      }
   }

   @Override
   public boolean K_() {
      return this.aX() && !this.eM() && !this.w_();
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("Sheared", this.eM());
      _snowman.a("Color", (byte)this.eL().b());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.t(_snowman.q("Sheared"));
      this.b(bkx.a(_snowman.f("Color")));
   }

   @Override
   protected adp I() {
      return adq.mP;
   }

   @Override
   protected adp e(apk var1) {
      return adq.mR;
   }

   @Override
   protected adp dq() {
      return adq.mQ;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.mT, 0.15F, 1.0F);
   }

   public bkx eL() {
      return bkx.a(this.R.a(bo) & 15);
   }

   public void b(bkx var1) {
      byte _snowman = this.R.a(bo);
      this.R.b(bo, (byte)(_snowman & 240 | _snowman.b() & 15));
   }

   public boolean eM() {
      return (this.R.a(bo) & 16) != 0;
   }

   public void t(boolean var1) {
      byte _snowman = this.R.a(bo);
      if (_snowman) {
         this.R.b(bo, (byte)(_snowman | 16));
      } else {
         this.R.b(bo, (byte)(_snowman & -17));
      }
   }

   public static bkx a(Random var0) {
      int _snowman = _snowman.nextInt(100);
      if (_snowman < 5) {
         return bkx.p;
      } else if (_snowman < 10) {
         return bkx.h;
      } else if (_snowman < 15) {
         return bkx.i;
      } else if (_snowman < 18) {
         return bkx.m;
      } else {
         return _snowman.nextInt(500) == 0 ? bkx.g : bkx.a;
      }
   }

   public bas b(aag var1, apy var2) {
      bas _snowman = (bas)_snowman;
      bas _snowmanx = aqe.ar.a(_snowman);
      _snowmanx.b(this.a(this, _snowman));
      return _snowmanx;
   }

   @Override
   public void B() {
      this.t(false);
      if (this.w_()) {
         this.a(60);
      }
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.b(a(_snowman.u_()));
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private bkx a(azz var1, azz var2) {
      bkx _snowman = ((bas)_snowman).eL();
      bkx _snowmanx = ((bas)_snowman).eL();
      bio _snowmanxx = a(_snowman, _snowmanx);
      return this.l
         .o()
         .a(bot.a, _snowmanxx, this.l)
         .map(var1x -> var1x.a(_snowman))
         .map(bmb::b)
         .filter(bky.class::isInstance)
         .map(bky.class::cast)
         .map(bky::d)
         .orElseGet(() -> this.l.t.nextBoolean() ? _snowman : _snowman);
   }

   private static bio a(bkx var0, bkx var1) {
      bio _snowman = new bio(new bic(null, -1) {
         @Override
         public boolean a(bfw var1) {
            return false;
         }
      }, 2, 1);
      _snowman.a(0, new bmb(bky.a(_snowman)));
      _snowman.a(1, new bmb(bky.a(_snowman)));
      return _snowman;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 0.95F * _snowman.b;
   }
}
