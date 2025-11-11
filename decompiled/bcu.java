import com.google.common.collect.Lists;
import java.util.List;

public class bcu extends aqa {
   private ceh f = bup.C.n();
   public int b;
   public boolean c = true;
   private boolean g;
   private boolean ag;
   private int ah = 40;
   private float ai = 2.0F;
   public md d;
   protected static final us<fx> e = uv.a(bcu.class, uu.l);

   public bcu(aqe<? extends bcu> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bcu(brx var1, double var2, double var4, double var6, ceh var8) {
      this(aqe.A, _snowman);
      this.f = _snowman;
      this.i = true;
      this.d(_snowman, _snowman + (double)((1.0F - this.cz()) / 2.0F), _snowman);
      this.f(dcn.a);
      this.m = _snowman;
      this.n = _snowman;
      this.o = _snowman;
      this.a(this.cB());
   }

   @Override
   public boolean bL() {
      return false;
   }

   public void a(fx var1) {
      this.R.b(e, _snowman);
   }

   public fx g() {
      return this.R.a(e);
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   protected void e() {
      this.R.a(e, fx.b);
   }

   @Override
   public boolean aT() {
      return !this.y;
   }

   @Override
   public void j() {
      if (this.f.g()) {
         this.ad();
      } else {
         buo _snowman = this.f.b();
         if (this.b++ == 0) {
            fx _snowmanx = this.cB();
            if (this.l.d_(_snowmanx).a(_snowman)) {
               this.l.a(_snowmanx, false);
            } else if (!this.l.v) {
               this.ad();
               return;
            }
         }

         if (!this.aB()) {
            this.f(this.cC().b(0.0, -0.04, 0.0));
         }

         this.a(aqr.a, this.cC());
         if (!this.l.v) {
            fx _snowmanx = this.cB();
            boolean _snowmanxx = this.f.b() instanceof bvl;
            boolean _snowmanxxx = _snowmanxx && this.l.b(_snowmanx).a(aef.b);
            double _snowmanxxxx = this.cC().g();
            if (_snowmanxx && _snowmanxxxx > 1.0) {
               dcj _snowmanxxxxx = this.l.a(new brf(new dcn(this.m, this.n, this.o), this.cA(), brf.a.a, brf.b.b, this));
               if (_snowmanxxxxx.c() != dcl.a.a && this.l.b(_snowmanxxxxx.a()).a(aef.b)) {
                  _snowmanx = _snowmanxxxxx.a();
                  _snowmanxxx = true;
               }
            }

            if (this.t || _snowmanxxx) {
               ceh _snowmanxxxxx = this.l.d_(_snowmanx);
               this.f(this.cC().d(0.7, -0.5, 0.7));
               if (!_snowmanxxxxx.a(bup.bo)) {
                  this.ad();
                  if (!this.g) {
                     boolean _snowmanxxxxxx = _snowmanxxxxx.a(new bnz(this.l, _snowmanx, gc.a, bmb.b, gc.b));
                     boolean _snowmanxxxxxxx = bwo.h(this.l.d_(_snowmanx.c())) && (!_snowmanxx || !_snowmanxxx);
                     boolean _snowmanxxxxxxxx = this.f.a((brz)this.l, _snowmanx) && !_snowmanxxxxxxx;
                     if (_snowmanxxxxxx && _snowmanxxxxxxxx) {
                        if (this.f.b(cex.C) && this.l.b(_snowmanx).a() == cuy.c) {
                           this.f = this.f.a(cex.C, Boolean.valueOf(true));
                        }

                        if (this.l.a(_snowmanx, this.f, 3)) {
                           if (_snowman instanceof bwo) {
                              ((bwo)_snowman).a(this.l, _snowmanx, this.f, _snowmanxxxxx, this);
                           }

                           if (this.d != null && _snowman instanceof bwm) {
                              ccj _snowmanxxxxxxxxx = this.l.c(_snowmanx);
                              if (_snowmanxxxxxxxxx != null) {
                                 md _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.a(new md());

                                 for (String _snowmanxxxxxxxxxxx : this.d.d()) {
                                    mt _snowmanxxxxxxxxxxxx = this.d.c(_snowmanxxxxxxxxxxx);
                                    if (!"x".equals(_snowmanxxxxxxxxxxx) && !"y".equals(_snowmanxxxxxxxxxxx) && !"z".equals(_snowmanxxxxxxxxxxx)) {
                                       _snowmanxxxxxxxxxx.a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx.c());
                                    }
                                 }

                                 _snowmanxxxxxxxxx.a(this.f, _snowmanxxxxxxxxxx);
                                 _snowmanxxxxxxxxx.X_();
                              }
                           }
                        } else if (this.c && this.l.V().b(brt.g)) {
                           this.a(_snowman);
                        }
                     } else if (this.c && this.l.V().b(brt.g)) {
                        this.a(_snowman);
                     }
                  } else if (_snowman instanceof bwo) {
                     ((bwo)_snowman).a(this.l, _snowmanx, this);
                  }
               }
            } else if (!this.l.v && (this.b > 100 && (_snowmanx.v() < 1 || _snowmanx.v() > 256) || this.b > 600)) {
               if (this.c && this.l.V().b(brt.g)) {
                  this.a(_snowman);
               }

               this.ad();
            }
         }

         this.f(this.cC().a(0.98));
      }
   }

   @Override
   public boolean b(float var1, float var2) {
      if (this.ag) {
         int _snowman = afm.f(_snowman - 1.0F);
         if (_snowman > 0) {
            List<aqa> _snowmanx = Lists.newArrayList(this.l.a(this, this.cc()));
            boolean _snowmanxx = this.f.a(aed.G);
            apk _snowmanxxx = _snowmanxx ? apk.q : apk.r;

            for (aqa _snowmanxxxx : _snowmanx) {
               _snowmanxxxx.a(_snowmanxxx, (float)Math.min(afm.d((float)_snowman * this.ai), this.ah));
            }

            if (_snowmanxx && (double)this.J.nextFloat() < 0.05F + (double)_snowman * 0.05) {
               ceh _snowmanxxxx = bts.c(this.f);
               if (_snowmanxxxx == null) {
                  this.g = true;
               } else {
                  this.f = _snowmanxxxx;
               }
            }
         }
      }

      return false;
   }

   @Override
   protected void b(md var1) {
      _snowman.a("BlockState", mp.a(this.f));
      _snowman.b("Time", this.b);
      _snowman.a("DropItem", this.c);
      _snowman.a("HurtEntities", this.ag);
      _snowman.a("FallHurtAmount", this.ai);
      _snowman.b("FallHurtMax", this.ah);
      if (this.d != null) {
         _snowman.a("TileEntityData", this.d);
      }
   }

   @Override
   protected void a(md var1) {
      this.f = mp.c(_snowman.p("BlockState"));
      this.b = _snowman.h("Time");
      if (_snowman.c("HurtEntities", 99)) {
         this.ag = _snowman.q("HurtEntities");
         this.ai = _snowman.j("FallHurtAmount");
         this.ah = _snowman.h("FallHurtMax");
      } else if (this.f.a(aed.G)) {
         this.ag = true;
      }

      if (_snowman.c("DropItem", 99)) {
         this.c = _snowman.q("DropItem");
      }

      if (_snowman.c("TileEntityData", 10)) {
         this.d = _snowman.p("TileEntityData");
      }

      if (this.f.g()) {
         this.f = bup.C.n();
      }
   }

   public brx h() {
      return this.l;
   }

   public void a(boolean var1) {
      this.ag = _snowman;
   }

   @Override
   public boolean bR() {
      return false;
   }

   @Override
   public void a(m var1) {
      super.a(_snowman);
      _snowman.a("Immitating BlockState", this.f.toString());
   }

   public ceh i() {
      return this.f;
   }

   @Override
   public boolean cj() {
      return true;
   }

   @Override
   public oj<?> P() {
      return new on(this, buo.i(this.i()));
   }
}
