import java.util.UUID;
import javax.annotation.Nullable;

public class bbd extends bbb {
   private static final UUID bw = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
   private static final us<Integer> bx = uv.a(bbd.class, uu.b);

   public bbd(aqe<? extends bbd> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void eK() {
      this.a(arl.a).a((double)this.fp());
      this.a(arl.d).a(this.fr());
      this.a(arl.m).a(this.fq());
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bx, 0);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("Variant", this.eU());
      if (!this.br.a(1).a()) {
         _snowman.a("ArmorItem", this.br.a(1).b(new md()));
      }
   }

   public bmb eL() {
      return this.b(aqf.e);
   }

   private void m(bmb var1) {
      this.a(aqf.e, _snowman);
      this.a(aqf.e, 0.0F);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.w(_snowman.h("Variant"));
      if (_snowman.c("ArmorItem", 10)) {
         bmb _snowman = bmb.a(_snowman.p("ArmorItem"));
         if (!_snowman.a() && this.l(_snowman)) {
            this.br.a(1, _snowman);
         }
      }

      this.fe();
   }

   private void w(int var1) {
      this.R.b(bx, _snowman);
   }

   private int eU() {
      return this.R.a(bx);
   }

   private void a(bbk var1, bbf var2) {
      this.w(_snowman.a() & 0xFF | _snowman.a() << 8 & 0xFF00);
   }

   public bbk eM() {
      return bbk.a(this.eU() & 0xFF);
   }

   public bbf eO() {
      return bbf.a((this.eU() & 0xFF00) >> 8);
   }

   @Override
   protected void fe() {
      if (!this.l.v) {
         super.fe();
         this.n(this.br.a(1));
         this.a(aqf.e, 0.0F);
      }
   }

   private void n(bmb var1) {
      this.m(_snowman);
      if (!this.l.v) {
         this.a(arl.i).b(bw);
         if (this.l(_snowman)) {
            int _snowman = ((blw)_snowman.b()).g();
            if (_snowman != 0) {
               this.a(arl.i).b(new arj(bw, "Horse armor bonus", (double)_snowman, arj.a.a));
            }
         }
      }
   }

   @Override
   public void a(aon var1) {
      bmb _snowman = this.eL();
      super.a(_snowman);
      bmb _snowmanx = this.eL();
      if (this.K > 20 && this.l(_snowmanx) && _snowman != _snowmanx) {
         this.a(adq.fS, 0.5F, 1.0F);
      }
   }

   @Override
   protected void a(cae var1) {
      super.a(_snowman);
      if (this.J.nextInt(10) == 0) {
         this.a(adq.fT, _snowman.a() * 0.6F, _snowman.b());
      }
   }

   @Override
   protected adp I() {
      super.I();
      return adq.fQ;
   }

   @Override
   protected adp dq() {
      super.dq();
      return adq.fU;
   }

   @Nullable
   @Override
   protected adp fg() {
      return adq.fV;
   }

   @Override
   protected adp e(apk var1) {
      super.e(_snowman);
      return adq.fX;
   }

   @Override
   protected adp fh() {
      super.fh();
      return adq.fR;
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (!this.w_()) {
         if (this.eW() && _snowman.eq()) {
            this.f(_snowman);
            return aou.a(this.l.v);
         }

         if (this.bs()) {
            return super.b(_snowman, _snowman);
         }
      }

      if (!_snowman.a()) {
         if (this.k(_snowman)) {
            return this.b(_snowman, _snowman);
         }

         aou _snowmanx = _snowman.a(_snowman, this, _snowman);
         if (_snowmanx.a()) {
            return _snowmanx;
         }

         if (!this.eW()) {
            this.fm();
            return aou.a(this.l.v);
         }

         boolean _snowmanxx = !this.w_() && !this.M_() && _snowman.b() == bmd.lO;
         if (this.l(_snowman) || _snowmanxx) {
            this.f(_snowman);
            return aou.a(this.l.v);
         }
      }

      if (this.w_()) {
         return super.b(_snowman, _snowman);
      } else {
         this.h(_snowman);
         return aou.a(this.l.v);
      }
   }

   @Override
   public boolean a(azz var1) {
      if (_snowman == this) {
         return false;
      } else {
         return !(_snowman instanceof bbc) && !(_snowman instanceof bbd) ? false : this.fo() && ((bbb)_snowman).fo();
      }
   }

   @Override
   public apy a(aag var1, apy var2) {
      bbb _snowman;
      if (_snowman instanceof bbc) {
         _snowman = aqe.aa.a(_snowman);
      } else {
         bbd _snowmanx = (bbd)_snowman;
         _snowman = aqe.H.a(_snowman);
         int _snowmanxx = this.J.nextInt(9);
         bbk _snowmanxxx;
         if (_snowmanxx < 4) {
            _snowmanxxx = this.eM();
         } else if (_snowmanxx < 8) {
            _snowmanxxx = _snowmanx.eM();
         } else {
            _snowmanxxx = x.a(bbk.values(), this.J);
         }

         int _snowmanxxxx = this.J.nextInt(5);
         bbf _snowmanxxxxx;
         if (_snowmanxxxx < 2) {
            _snowmanxxxxx = this.eO();
         } else if (_snowmanxxxx < 4) {
            _snowmanxxxxx = _snowmanx.eO();
         } else {
            _snowmanxxxxx = x.a(bbf.values(), this.J);
         }

         ((bbd)_snowman).a(_snowmanxxx, _snowmanxxxxx);
      }

      this.a(_snowman, _snowman);
      return _snowman;
   }

   @Override
   public boolean fs() {
      return true;
   }

   @Override
   public boolean l(bmb var1) {
      return _snowman.b() instanceof blw;
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      bbk _snowman;
      if (_snowman instanceof bbd.a) {
         _snowman = ((bbd.a)_snowman).a;
      } else {
         _snowman = x.a(bbk.values(), this.J);
         _snowman = new bbd.a(_snowman);
      }

      this.a(_snowman, x.a(bbf.values(), this.J));
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static class a extends apy.a {
      public final bbk a;

      public a(bbk var1) {
         super(true);
         this.a = _snowman;
      }
   }
}
