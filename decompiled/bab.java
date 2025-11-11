import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

public class bab extends are {
   private static final bon br = bon.a(bmd.ml, bmd.mm);
   private static final us<Integer> bs = uv.a(bab.class, uu.b);
   private static final us<Boolean> bt = uv.a(bab.class, uu.i);
   private static final us<Boolean> bu = uv.a(bab.class, uu.i);
   private static final us<Integer> bv = uv.a(bab.class, uu.b);
   public static final Map<Integer, vk> bq = x.a(Maps.newHashMap(), var0 -> {
      var0.put(0, new vk("textures/entity/cat/tabby.png"));
      var0.put(1, new vk("textures/entity/cat/black.png"));
      var0.put(2, new vk("textures/entity/cat/red.png"));
      var0.put(3, new vk("textures/entity/cat/siamese.png"));
      var0.put(4, new vk("textures/entity/cat/british_shorthair.png"));
      var0.put(5, new vk("textures/entity/cat/calico.png"));
      var0.put(6, new vk("textures/entity/cat/persian.png"));
      var0.put(7, new vk("textures/entity/cat/ragdoll.png"));
      var0.put(8, new vk("textures/entity/cat/white.png"));
      var0.put(9, new vk("textures/entity/cat/jellie.png"));
      var0.put(10, new vk("textures/entity/cat/all_black.png"));
   });
   private bab.a<bfw> bw;
   private axf bx;
   private float by;
   private float bz;
   private float bA;
   private float bB;
   private float bC;
   private float bD;

   public bab(aqe<? extends bab> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public vk eU() {
      return bq.getOrDefault(this.eV(), bq.get(0));
   }

   @Override
   protected void o() {
      this.bx = new bab.c(this, 0.6, br, true);
      this.bk.a(1, new avp(this));
      this.bk.a(1, new axb(this));
      this.bk.a(2, new bab.b(this));
      this.bk.a(3, this.bx);
      this.bk.a(5, new avj(this, 1.1, 8));
      this.bk.a(6, new avt(this, 1.0, 10.0F, 5.0F, false));
      this.bk.a(7, new avk(this, 0.8));
      this.bk.a(8, new awb(this, 0.3F));
      this.bk.a(9, new awm(this));
      this.bk.a(10, new avi(this, 0.8));
      this.bk.a(11, new axk(this, 0.8, 1.0000001E-5F));
      this.bk.a(12, new awd(this, bfw.class, 10.0F));
      this.bl.a(1, new axt<>(this, baq.class, false, null));
      this.bl.a(1, new axt<>(this, bax.class, false, bax.bo));
   }

   public int eV() {
      return this.R.a(bs);
   }

   public void t(int var1) {
      if (_snowman < 0 || _snowman >= 11) {
         _snowman = this.J.nextInt(10);
      }

      this.R.b(bs, _snowman);
   }

   public void x(boolean var1) {
      this.R.b(bt, _snowman);
   }

   public boolean eW() {
      return this.R.a(bt);
   }

   public void y(boolean var1) {
      this.R.b(bu, _snowman);
   }

   public boolean eX() {
      return this.R.a(bu);
   }

   public bkx eY() {
      return bkx.a(this.R.a(bv));
   }

   public void a(bkx var1) {
      this.R.b(bv, _snowman.b());
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bs, 1);
      this.R.a(bt, false);
      this.R.a(bu, false);
      this.R.a(bv, bkx.o.b());
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("CatType", this.eV());
      _snowman.a("CollarColor", (byte)this.eY().b());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.t(_snowman.h("CatType"));
      if (_snowman.c("CollarColor", 99)) {
         this.a(bkx.a(_snowman.h("CollarColor")));
      }
   }

   @Override
   public void N() {
      if (this.u().b()) {
         double _snowman = this.u().c();
         if (_snowman == 0.6) {
            this.b(aqx.f);
            this.g(false);
         } else if (_snowman == 1.33) {
            this.b(aqx.a);
            this.g(true);
         } else {
            this.b(aqx.a);
            this.g(false);
         }
      } else {
         this.b(aqx.a);
         this.g(false);
      }
   }

   @Nullable
   @Override
   protected adp I() {
      if (this.eK()) {
         if (this.eS()) {
            return adq.bx;
         } else {
            return this.J.nextInt(4) == 0 ? adq.by : adq.bq;
         }
      } else {
         return adq.br;
      }
   }

   @Override
   public int D() {
      return 120;
   }

   public void eZ() {
      this.a(adq.bu, this.dG(), this.dH());
   }

   @Override
   protected adp e(apk var1) {
      return adq.bw;
   }

   @Override
   protected adp dq() {
      return adq.bs;
   }

   public static ark.a fa() {
      return aqn.p().a(arl.a, 10.0).a(arl.d, 0.3F).a(arl.f, 3.0);
   }

   @Override
   public boolean b(float var1, float var2) {
      return false;
   }

   @Override
   protected void a(bfw var1, bmb var2) {
      if (this.k(_snowman)) {
         this.a(adq.bt, 1.0F, 1.0F);
      }

      super.a(_snowman, _snowman);
   }

   private float fb() {
      return (float)this.b(arl.f);
   }

   @Override
   public boolean B(aqa var1) {
      return _snowman.a(apk.c(this), this.fb());
   }

   @Override
   public void j() {
      super.j();
      if (this.bx != null && this.bx.h() && !this.eK() && this.K % 100 == 0) {
         this.a(adq.bv, 1.0F, 1.0F);
      }

      this.fc();
   }

   private void fc() {
      if ((this.eW() || this.eX()) && this.K % 5 == 0) {
         this.a(adq.bx, 0.6F + 0.4F * (this.J.nextFloat() - this.J.nextFloat()), 1.0F);
      }

      this.fd();
      this.fe();
   }

   private void fd() {
      this.bz = this.by;
      this.bB = this.bA;
      if (this.eW()) {
         this.by = Math.min(1.0F, this.by + 0.15F);
         this.bA = Math.min(1.0F, this.bA + 0.08F);
      } else {
         this.by = Math.max(0.0F, this.by - 0.22F);
         this.bA = Math.max(0.0F, this.bA - 0.13F);
      }
   }

   private void fe() {
      this.bD = this.bC;
      if (this.eX()) {
         this.bC = Math.min(1.0F, this.bC + 0.1F);
      } else {
         this.bC = Math.max(0.0F, this.bC - 0.13F);
      }
   }

   public float y(float var1) {
      return afm.g(_snowman, this.bz, this.by);
   }

   public float z(float var1) {
      return afm.g(_snowman, this.bB, this.bA);
   }

   public float A(float var1) {
      return afm.g(_snowman, this.bD, this.bC);
   }

   public bab b(aag var1, apy var2) {
      bab _snowman = aqe.h.a(_snowman);
      if (_snowman instanceof bab) {
         if (this.J.nextBoolean()) {
            _snowman.t(this.eV());
         } else {
            _snowman.t(((bab)_snowman).eV());
         }

         if (this.eK()) {
            _snowman.b(this.A_());
            _snowman.u(true);
            if (this.J.nextBoolean()) {
               _snowman.a(this.eY());
            } else {
               _snowman.a(((bab)_snowman).eY());
            }
         }
      }

      return _snowman;
   }

   @Override
   public boolean a(azz var1) {
      if (!this.eK()) {
         return false;
      } else if (!(_snowman instanceof bab)) {
         return false;
      } else {
         bab _snowman = (bab)_snowman;
         return _snowman.eK() && super.a(_snowman);
      }
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      _snowman = super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman.af() > 0.9F) {
         this.t(this.J.nextInt(11));
      } else {
         this.t(this.J.nextInt(10));
      }

      brx _snowman = _snowman.E();
      if (_snowman instanceof aag && ((aag)_snowman).a().a(this.cB(), true, cla.j).e()) {
         this.t(10);
         this.es();
      }

      return _snowman;
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      blx _snowmanx = _snowman.b();
      if (this.l.v) {
         if (this.eK() && this.i(_snowman)) {
            return aou.a;
         } else {
            return !this.k(_snowman) || !(this.dk() < this.dx()) && this.eK() ? aou.c : aou.a;
         }
      } else {
         if (this.eK()) {
            if (this.i(_snowman)) {
               if (!(_snowmanx instanceof bky)) {
                  if (_snowmanx.s() && this.k(_snowman) && this.dk() < this.dx()) {
                     this.a(_snowman, _snowman);
                     this.b((float)_snowmanx.t().a());
                     return aou.b;
                  }

                  aou _snowmanxx = super.b(_snowman, _snowman);
                  if (!_snowmanxx.a() || this.w_()) {
                     this.w(!this.eO());
                  }

                  return _snowmanxx;
               }

               bkx _snowmanxx = ((bky)_snowmanx).d();
               if (_snowmanxx != this.eY()) {
                  this.a(_snowmanxx);
                  if (!_snowman.bC.d) {
                     _snowman.g(1);
                  }

                  this.es();
                  return aou.b;
               }
            }
         } else if (this.k(_snowman)) {
            this.a(_snowman, _snowman);
            if (this.J.nextInt(3) == 0) {
               this.f(_snowman);
               this.w(true);
               this.l.a(this, (byte)7);
            } else {
               this.l.a(this, (byte)6);
            }

            this.es();
            return aou.b;
         }

         aou _snowmanxx = super.b(_snowman, _snowman);
         if (_snowmanxx.a()) {
            this.es();
         }

         return _snowmanxx;
      }
   }

   @Override
   public boolean k(bmb var1) {
      return br.a(_snowman);
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return _snowman.b * 0.5F;
   }

   @Override
   public boolean h(double var1) {
      return !this.eK() && this.K > 2400;
   }

   @Override
   protected void eL() {
      if (this.bw == null) {
         this.bw = new bab.a<>(this, bfw.class, 16.0F, 0.8, 1.33);
      }

      this.bk.a(this.bw);
      if (!this.eK()) {
         this.bk.a(4, this.bw);
      }
   }

   static class a<T extends aqm> extends avd<T> {
      private final bab i;

      public a(bab var1, Class<T> var2, float var3, double var4, double var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, aqd.e::test);
         this.i = _snowman;
      }

      @Override
      public boolean a() {
         return !this.i.eK() && super.a();
      }

      @Override
      public boolean b() {
         return !this.i.eK() && super.b();
      }
   }

   static class b extends avv {
      private final bab a;
      private bfw b;
      private fx c;
      private int d;

      public b(bab var1) {
         this.a = _snowman;
      }

      @Override
      public boolean a() {
         if (!this.a.eK()) {
            return false;
         } else if (this.a.eO()) {
            return false;
         } else {
            aqm _snowman = this.a.eN();
            if (_snowman instanceof bfw) {
               this.b = (bfw)_snowman;
               if (!_snowman.em()) {
                  return false;
               }

               if (this.a.h(this.b) > 100.0) {
                  return false;
               }

               fx _snowmanx = this.b.cB();
               ceh _snowmanxx = this.a.l.d_(_snowmanx);
               if (_snowmanxx.b().a(aed.L)) {
                  this.c = _snowmanxx.d(buj.aq).map(var1x -> _snowman.a(var1x.f())).orElseGet(() -> new fx(_snowman));
                  return !this.g();
               }
            }

            return false;
         }
      }

      private boolean g() {
         for (bab _snowman : this.a.l.a(bab.class, new dci(this.c).g(2.0))) {
            if (_snowman != this.a && (_snowman.eW() || _snowman.eX())) {
               return true;
            }
         }

         return false;
      }

      @Override
      public boolean b() {
         return this.a.eK() && !this.a.eO() && this.b != null && this.b.em() && this.c != null && !this.g();
      }

      @Override
      public void c() {
         if (this.c != null) {
            this.a.v(false);
            this.a.x().a((double)this.c.u(), (double)this.c.v(), (double)this.c.w(), 1.1F);
         }
      }

      @Override
      public void d() {
         this.a.x(false);
         float _snowman = this.a.l.f(1.0F);
         if (this.b.eC() >= 100 && (double)_snowman > 0.77 && (double)_snowman < 0.8 && (double)this.a.l.u_().nextFloat() < 0.7) {
            this.h();
         }

         this.d = 0;
         this.a.y(false);
         this.a.x().o();
      }

      private void h() {
         Random _snowman = this.a.cY();
         fx.a _snowmanx = new fx.a();
         _snowmanx.g(this.a.cB());
         this.a.a((double)(_snowmanx.u() + _snowman.nextInt(11) - 5), (double)(_snowmanx.v() + _snowman.nextInt(5) - 2), (double)(_snowmanx.w() + _snowman.nextInt(11) - 5), false);
         _snowmanx.g(this.a.cB());
         cyy _snowmanxx = this.a.l.l().aJ().a(cyq.ak);
         cyv.a _snowmanxxx = new cyv.a((aag)this.a.l).a(dbc.f, this.a.cA()).a(dbc.a, this.a).a(_snowman);

         for (bmb _snowmanxxxx : _snowmanxx.a(_snowmanxxx.a(dbb.g))) {
            this.a
               .l
               .c(
                  new bcv(
                     this.a.l,
                     (double)_snowmanx.u() - (double)afm.a(this.a.aA * (float) (Math.PI / 180.0)),
                     (double)_snowmanx.v(),
                     (double)_snowmanx.w() + (double)afm.b(this.a.aA * (float) (Math.PI / 180.0)),
                     _snowmanxxxx
                  )
               );
         }
      }

      @Override
      public void e() {
         if (this.b != null && this.c != null) {
            this.a.v(false);
            this.a.x().a((double)this.c.u(), (double)this.c.v(), (double)this.c.w(), 1.1F);
            if (this.a.h(this.b) < 2.5) {
               this.d++;
               if (this.d > 16) {
                  this.a.x(true);
                  this.a.y(false);
               } else {
                  this.a.a(this.b, 45.0F, 45.0F);
                  this.a.y(true);
               }
            } else {
               this.a.x(false);
            }
         }
      }
   }

   static class c extends axf {
      @Nullable
      private bfw c;
      private final bab d;

      public c(bab var1, double var2, bon var4, boolean var5) {
         super(_snowman, _snowman, _snowman, _snowman);
         this.d = _snowman;
      }

      @Override
      public void e() {
         super.e();
         if (this.c == null && this.a.cY().nextInt(600) == 0) {
            this.c = this.b;
         } else if (this.a.cY().nextInt(500) == 0) {
            this.c = null;
         }
      }

      @Override
      protected boolean g() {
         return this.c != null && this.c.equals(this.b) ? false : super.g();
      }

      @Override
      public boolean a() {
         return super.a() && !this.d.eK();
      }
   }
}
