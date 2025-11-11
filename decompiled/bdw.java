import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;

public class bdw extends azx implements bdi {
   private static final UUID bp = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
   private static final arj bq = new arj(bp, "Covered armor bonus", 20.0, arj.a.a);
   protected static final us<gc> b = uv.a(bdw.class, uu.n);
   protected static final us<Optional<fx>> c = uv.a(bdw.class, uu.m);
   protected static final us<Byte> d = uv.a(bdw.class, uu.a);
   protected static final us<Byte> bo = uv.a(bdw.class, uu.a);
   private float br;
   private float bs;
   private fx bt = null;
   private int bu;

   public bdw(aqe<? extends bdw> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 5;
   }

   @Override
   protected void o() {
      this.bk.a(1, new awd(this, bfw.class, 8.0F));
      this.bk.a(4, new bdw.a());
      this.bk.a(7, new bdw.e());
      this.bk.a(8, new aws(this));
      this.bl.a(1, new axp(this).a());
      this.bl.a(2, new bdw.d(this));
      this.bl.a(3, new bdw.c(this));
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   public adr cu() {
      return adr.f;
   }

   @Override
   protected adp I() {
      return adq.nc;
   }

   @Override
   public void F() {
      if (!this.eT()) {
         super.F();
      }
   }

   @Override
   protected adp dq() {
      return adq.ni;
   }

   @Override
   protected adp e(apk var1) {
      return this.eT() ? adq.nk : adq.nj;
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, gc.a);
      this.R.a(c, Optional.empty());
      this.R.a(d, (byte)0);
      this.R.a(bo, (byte)16);
   }

   public static ark.a m() {
      return aqn.p().a(arl.a, 30.0);
   }

   @Override
   protected auu r() {
      return new bdw.b(this);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.R.b(b, gc.a(_snowman.f("AttachFace")));
      this.R.b(d, _snowman.f("Peek"));
      this.R.b(bo, _snowman.f("Color"));
      if (_snowman.e("APX")) {
         int _snowman = _snowman.h("APX");
         int _snowmanx = _snowman.h("APY");
         int _snowmanxx = _snowman.h("APZ");
         this.R.b(c, Optional.of(new fx(_snowman, _snowmanx, _snowmanxx)));
      } else {
         this.R.b(c, Optional.empty());
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("AttachFace", (byte)this.R.a(b).c());
      _snowman.a("Peek", this.R.a(d));
      _snowman.a("Color", this.R.a(bo));
      fx _snowman = this.eM();
      if (_snowman != null) {
         _snowman.b("APX", _snowman.u());
         _snowman.b("APY", _snowman.v());
         _snowman.b("APZ", _snowman.w());
      }
   }

   @Override
   public void j() {
      super.j();
      fx _snowman = this.R.a(c).orElse(null);
      if (_snowman == null && !this.l.v) {
         _snowman = this.cB();
         this.R.b(c, Optional.of(_snowman));
      }

      if (this.br()) {
         _snowman = null;
         float _snowmanx = this.ct().p;
         this.p = _snowmanx;
         this.aA = _snowmanx;
         this.aB = _snowmanx;
         this.bu = 0;
      } else if (!this.l.v) {
         ceh _snowmanx = this.l.d_(_snowman);
         if (!_snowmanx.g()) {
            if (_snowmanx.a(bup.bo)) {
               gc _snowmanxx = _snowmanx.c(cea.a);
               if (this.l.w(_snowman.a(_snowmanxx))) {
                  _snowman = _snowman.a(_snowmanxx);
                  this.R.b(c, Optional.of(_snowman));
               } else {
                  this.eK();
               }
            } else if (_snowmanx.a(bup.aX)) {
               gc _snowmanxx = _snowmanx.c(ceb.a);
               if (this.l.w(_snowman.a(_snowmanxx))) {
                  _snowman = _snowman.a(_snowmanxx);
                  this.R.b(c, Optional.of(_snowman));
               } else {
                  this.eK();
               }
            } else {
               this.eK();
            }
         }

         gc _snowmanxx = this.eL();
         if (!this.a(_snowman, _snowmanxx)) {
            gc _snowmanxxx = this.g(_snowman);
            if (_snowmanxxx != null) {
               this.R.b(b, _snowmanxxx);
            } else {
               this.eK();
            }
         }
      }

      float _snowmanxx = (float)this.eN() * 0.01F;
      this.br = this.bs;
      if (this.bs > _snowmanxx) {
         this.bs = afm.a(this.bs - 0.05F, _snowmanxx, 1.0F);
      } else if (this.bs < _snowmanxx) {
         this.bs = afm.a(this.bs + 0.05F, 0.0F, _snowmanxx);
      }

      if (_snowman != null) {
         if (this.l.v) {
            if (this.bu > 0 && this.bt != null) {
               this.bu--;
            } else {
               this.bt = _snowman;
            }
         }

         this.g((double)_snowman.u() + 0.5, (double)_snowman.v(), (double)_snowman.w() + 0.5);
         double _snowmanxxx = 0.5 - (double)afm.a((0.5F + this.bs) * (float) Math.PI) * 0.5;
         double _snowmanxxxx = 0.5 - (double)afm.a((0.5F + this.br) * (float) Math.PI) * 0.5;
         gc _snowmanxxxxx = this.eL().f();
         this.a(
            new dci(this.cD() - 0.5, this.cE(), this.cH() - 0.5, this.cD() + 0.5, this.cE() + 1.0, this.cH() + 0.5)
               .b((double)_snowmanxxxxx.i() * _snowmanxxx, (double)_snowmanxxxxx.j() * _snowmanxxx, (double)_snowmanxxxxx.k() * _snowmanxxx)
         );
         double _snowmanxxxxxx = _snowmanxxx - _snowmanxxxx;
         if (_snowmanxxxxxx > 0.0) {
            List<aqa> _snowmanxxxxxxx = this.l.a(this, this.cc());
            if (!_snowmanxxxxxxx.isEmpty()) {
               for (aqa _snowmanxxxxxxxx : _snowmanxxxxxxx) {
                  if (!(_snowmanxxxxxxxx instanceof bdw) && !_snowmanxxxxxxxx.H) {
                     _snowmanxxxxxxxx.a(aqr.e, new dcn(_snowmanxxxxxx * (double)_snowmanxxxxx.i(), _snowmanxxxxxx * (double)_snowmanxxxxx.j(), _snowmanxxxxxx * (double)_snowmanxxxxx.k()));
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(aqr var1, dcn var2) {
      if (_snowman == aqr.d) {
         this.eK();
      } else {
         super.a(_snowman, _snowman);
      }
   }

   @Override
   public void d(double var1, double var3, double var5) {
      super.d(_snowman, _snowman, _snowman);
      if (this.R != null && this.K != 0) {
         Optional<fx> _snowman = this.R.a(c);
         Optional<fx> _snowmanx = Optional.of(new fx(_snowman, _snowman, _snowman));
         if (!_snowmanx.equals(_snowman)) {
            this.R.b(c, _snowmanx);
            this.R.b(d, (byte)0);
            this.Z = true;
         }
      }
   }

   @Nullable
   protected gc g(fx var1) {
      for (gc _snowman : gc.values()) {
         if (this.a(_snowman, _snowman)) {
            return _snowman;
         }
      }

      return null;
   }

   private boolean a(fx var1, gc var2) {
      return this.l.a(_snowman.a(_snowman), this, _snowman.f()) && this.l.a_(this, aoz.a(_snowman, _snowman.f()));
   }

   protected boolean eK() {
      if (!this.eD() && this.aX()) {
         fx _snowman = this.cB();

         for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
            fx _snowmanxx = _snowman.b(8 - this.J.nextInt(17), 8 - this.J.nextInt(17), 8 - this.J.nextInt(17));
            if (_snowmanxx.v() > 0 && this.l.w(_snowmanxx) && this.l.f().a(_snowmanxx) && this.l.a_(this, new dci(_snowmanxx))) {
               gc _snowmanxxx = this.g(_snowmanxx);
               if (_snowmanxxx != null) {
                  this.R.b(b, _snowmanxxx);
                  this.a(adq.nn, 1.0F, 1.0F);
                  this.R.b(c, Optional.of(_snowmanxx));
                  this.R.b(d, (byte)0);
                  this.h(null);
                  return true;
               }
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @Override
   public void k() {
      super.k();
      this.f(dcn.a);
      if (!this.eD()) {
         this.aB = 0.0F;
         this.aA = 0.0F;
      }
   }

   @Override
   public void a(us<?> var1) {
      if (c.equals(_snowman) && this.l.v && !this.br()) {
         fx _snowman = this.eM();
         if (_snowman != null) {
            if (this.bt == null) {
               this.bt = _snowman;
            } else {
               this.bu = 6;
            }

            this.g((double)_snowman.u() + 0.5, (double)_snowman.v(), (double)_snowman.w() + 0.5);
         }
      }

      super.a(_snowman);
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.aU = 0;
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.eT()) {
         aqa _snowman = _snowman.j();
         if (_snowman instanceof bga) {
            return false;
         }
      }

      if (super.a(_snowman, _snowman)) {
         if ((double)this.dk() < (double)this.dx() * 0.5 && this.J.nextInt(4) == 0) {
            this.eK();
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean eT() {
      return this.eN() == 0;
   }

   @Override
   public boolean aZ() {
      return this.aX();
   }

   public gc eL() {
      return this.R.a(b);
   }

   @Nullable
   public fx eM() {
      return this.R.a(c).orElse(null);
   }

   public void h(@Nullable fx var1) {
      this.R.b(c, Optional.ofNullable(_snowman));
   }

   public int eN() {
      return this.R.a(d);
   }

   public void a(int var1) {
      if (!this.l.v) {
         this.a(arl.i).d(bq);
         if (_snowman == 0) {
            this.a(arl.i).c(bq);
            this.a(adq.nh, 1.0F, 1.0F);
         } else {
            this.a(adq.nl, 1.0F, 1.0F);
         }
      }

      this.R.b(d, (byte)_snowman);
   }

   public float y(float var1) {
      return afm.g(_snowman, this.br, this.bs);
   }

   public int eO() {
      return this.bu;
   }

   public fx eP() {
      return this.bt;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 0.5F;
   }

   @Override
   public int O() {
      return 180;
   }

   @Override
   public int Q() {
      return 180;
   }

   @Override
   public void i(aqa var1) {
   }

   @Override
   public float bg() {
      return 0.0F;
   }

   public boolean eQ() {
      return this.bt != null && this.eM() != null;
   }

   @Nullable
   public bkx eS() {
      Byte _snowman = this.R.a(bo);
      return _snowman != 16 && _snowman <= 15 ? bkx.a(_snowman) : null;
   }

   class a extends avv {
      private int b;

      public a() {
         this.a(EnumSet.of(avv.a.a, avv.a.b));
      }

      @Override
      public boolean a() {
         aqm _snowman = bdw.this.A();
         return _snowman != null && _snowman.aX() ? bdw.this.l.ad() != aor.a : false;
      }

      @Override
      public void c() {
         this.b = 20;
         bdw.this.a(100);
      }

      @Override
      public void d() {
         bdw.this.a(0);
      }

      @Override
      public void e() {
         if (bdw.this.l.ad() != aor.a) {
            this.b--;
            aqm _snowman = bdw.this.A();
            bdw.this.t().a(_snowman, 180.0F, 180.0F);
            double _snowmanx = bdw.this.h(_snowman);
            if (_snowmanx < 400.0) {
               if (this.b <= 0) {
                  this.b = 20 + bdw.this.J.nextInt(10) * 20 / 2;
                  bdw.this.l.c(new bgo(bdw.this.l, bdw.this, _snowman, bdw.this.eL().n()));
                  bdw.this.a(adq.nm, 2.0F, (bdw.this.J.nextFloat() - bdw.this.J.nextFloat()) * 0.2F + 1.0F);
               }
            } else {
               bdw.this.h(null);
            }

            super.e();
         }
      }
   }

   class b extends auu {
      public b(aqn var2) {
         super(_snowman);
      }

      @Override
      public void a() {
      }
   }

   static class c extends axq<aqm> {
      public c(bdw var1) {
         super(_snowman, aqm.class, 10, true, false, var0 -> var0 instanceof bdi);
      }

      @Override
      public boolean a() {
         return this.e.bG() == null ? false : super.a();
      }

      @Override
      protected dci a(double var1) {
         gc _snowman = ((bdw)this.e).eL();
         if (_snowman.n() == gc.a.a) {
            return this.e.cc().c(4.0, _snowman, _snowman);
         } else {
            return _snowman.n() == gc.a.c ? this.e.cc().c(_snowman, _snowman, 4.0) : this.e.cc().c(_snowman, 4.0, _snowman);
         }
      }
   }

   class d extends axq<bfw> {
      public d(bdw var2) {
         super(_snowman, bfw.class, true);
      }

      @Override
      public boolean a() {
         return bdw.this.l.ad() == aor.a ? false : super.a();
      }

      @Override
      protected dci a(double var1) {
         gc _snowman = ((bdw)this.e).eL();
         if (_snowman.n() == gc.a.a) {
            return this.e.cc().c(4.0, _snowman, _snowman);
         } else {
            return _snowman.n() == gc.a.c ? this.e.cc().c(_snowman, _snowman, 4.0) : this.e.cc().c(_snowman, 4.0, _snowman);
         }
      }
   }

   class e extends avv {
      private int b;

      private e() {
      }

      @Override
      public boolean a() {
         return bdw.this.A() == null && bdw.this.J.nextInt(40) == 0;
      }

      @Override
      public boolean b() {
         return bdw.this.A() == null && this.b > 0;
      }

      @Override
      public void c() {
         this.b = 20 * (1 + bdw.this.J.nextInt(3));
         bdw.this.a(30);
      }

      @Override
      public void d() {
         if (bdw.this.A() == null) {
            bdw.this.a(0);
         }
      }

      @Override
      public void e() {
         this.b--;
      }
   }
}
