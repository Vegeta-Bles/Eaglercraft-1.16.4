import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class bhc extends bdr {
   protected static final us<Boolean> c = uv.a(bhc.class, uu.i);
   private static final Predicate<bcv> b = var0 -> !var0.p() && var0.aX() && bmb.b(var0.g(), bhb.s());
   @Nullable
   protected bhb d;
   private int bo;
   private boolean bp;
   private int bq;

   protected bhc(aqe<? extends bhc> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(1, new bhc.b<>(this));
      this.bk.a(3, new awq<>(this));
      this.bk.a(4, new bhc.d(this, 1.05F, 1));
      this.bk.a(5, new bhc.c(this));
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(c, false);
   }

   public abstract void a(int var1, boolean var2);

   public boolean eZ() {
      return this.bp;
   }

   public void w(boolean var1) {
      this.bp = _snowman;
   }

   @Override
   public void k() {
      if (this.l instanceof aag && this.aX()) {
         bhb _snowman = this.fa();
         if (this.eZ()) {
            if (_snowman == null) {
               if (this.l.T() % 20L == 0L) {
                  bhb _snowmanx = ((aag)this.l).b_(this.cB());
                  if (_snowmanx != null && bhd.a(this, _snowmanx)) {
                     _snowmanx.a(_snowmanx.k(), this, null, true);
                  }
               }
            } else {
               aqm _snowmanx = this.A();
               if (_snowmanx != null && (_snowmanx.X() == aqe.bc || _snowmanx.X() == aqe.K)) {
                  this.aI = 0;
               }
            }
         }
      }

      super.k();
   }

   @Override
   protected void eQ() {
      this.aI += 2;
   }

   @Override
   public void a(apk var1) {
      if (this.l instanceof aag) {
         aqa _snowman = _snowman.k();
         bhb _snowmanx = this.fa();
         if (_snowmanx != null) {
            if (this.eS()) {
               _snowmanx.c(this.fc());
            }

            if (_snowman != null && _snowman.X() == aqe.bc) {
               _snowmanx.a(_snowman);
            }

            _snowmanx.a(this, false);
         }

         if (this.eS() && _snowmanx == null && ((aag)this.l).b_(this.cB()) == null) {
            bmb _snowmanxx = this.b(aqf.f);
            bfw _snowmanxxx = null;
            if (_snowman instanceof bfw) {
               _snowmanxxx = (bfw)_snowman;
            } else if (_snowman instanceof baz) {
               baz _snowmanxxxx = (baz)_snowman;
               aqm _snowmanxxxxx = _snowmanxxxx.eN();
               if (_snowmanxxxx.eK() && _snowmanxxxxx instanceof bfw) {
                  _snowmanxxx = (bfw)_snowmanxxxxx;
               }
            }

            if (!_snowmanxx.a() && bmb.b(_snowmanxx, bhb.s()) && _snowmanxxx != null) {
               apu _snowmanxxxx = _snowmanxxx.b(apw.E);
               int _snowmanxxxxx = 1;
               if (_snowmanxxxx != null) {
                  _snowmanxxxxx += _snowmanxxxx.c();
                  _snowmanxxx.c(apw.E);
               } else {
                  _snowmanxxxxx--;
               }

               _snowmanxxxxx = afm.a(_snowmanxxxxx, 0, 4);
               apu _snowmanxxxxxx = new apu(apw.E, 120000, _snowmanxxxxx, false, false, true);
               if (!this.l.V().b(brt.x)) {
                  _snowmanxxx.c(_snowmanxxxxxx);
               }
            }
         }
      }

      super.a(_snowman);
   }

   @Override
   public boolean eT() {
      return !this.fb();
   }

   public void a(@Nullable bhb var1) {
      this.d = _snowman;
   }

   @Nullable
   public bhb fa() {
      return this.d;
   }

   public boolean fb() {
      return this.fa() != null && this.fa().v();
   }

   public void a(int var1) {
      this.bo = _snowman;
   }

   public int fc() {
      return this.bo;
   }

   public boolean fd() {
      return this.R.a(c);
   }

   public void x(boolean var1) {
      this.R.b(c, _snowman);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("Wave", this.bo);
      _snowman.a("CanJoinRaid", this.bp);
      if (this.d != null) {
         _snowman.b("RaidId", this.d.u());
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.bo = _snowman.h("Wave");
      this.bp = _snowman.q("CanJoinRaid");
      if (_snowman.c("RaidId", 3)) {
         if (this.l instanceof aag) {
            this.d = ((aag)this.l).z().a(_snowman.h("RaidId"));
         }

         if (this.d != null) {
            this.d.a(this.bo, this, false);
            if (this.eS()) {
               this.d.a(this.bo, this);
            }
         }
      }
   }

   @Override
   protected void b(bcv var1) {
      bmb _snowman = _snowman.g();
      boolean _snowmanx = this.fb() && this.fa().b(this.fc()) != null;
      if (this.fb() && !_snowmanx && bmb.b(_snowman, bhb.s())) {
         aqf _snowmanxx = aqf.f;
         bmb _snowmanxxx = this.b(_snowmanxx);
         double _snowmanxxxx = (double)this.e(_snowmanxx);
         if (!_snowmanxxx.a() && (double)Math.max(this.J.nextFloat() - 0.1F, 0.0F) < _snowmanxxxx) {
            this.a(_snowmanxxx);
         }

         this.a(_snowman);
         this.a(_snowmanxx, _snowman);
         this.a(_snowman, _snowman.E());
         _snowman.ad();
         this.fa().a(this.fc(), this);
         this.t(true);
      } else {
         super.b(_snowman);
      }
   }

   @Override
   public boolean h(double var1) {
      return this.fa() == null ? super.h(_snowman) : false;
   }

   @Override
   public boolean K() {
      return super.K() || this.fa() != null;
   }

   public int fe() {
      return this.bq;
   }

   public void b(int var1) {
      this.bq = _snowman;
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.fb()) {
         this.fa().p();
      }

      return super.a(_snowman, _snowman);
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.w(this.X() != aqe.aS || _snowman != aqp.a);
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public abstract adp eL();

   public class a extends avv {
      private final bhc c;
      private final float d;
      public final azg a = new azg().a(8.0).d().a().b().c().e();

      public a(bcy var2, float var3) {
         this.c = _snowman;
         this.d = _snowman * _snowman;
         this.a(EnumSet.of(avv.a.a, avv.a.b));
      }

      @Override
      public boolean a() {
         aqm _snowman = this.c.cZ();
         return this.c.fa() == null && this.c.eV() && this.c.A() != null && !this.c.eF() && (_snowman == null || _snowman.X() != aqe.bc);
      }

      @Override
      public void c() {
         super.c();
         this.c.x().o();

         for (bhc _snowman : this.c.l.a(bhc.class, this.a, this.c, this.c.cc().c(8.0, 8.0, 8.0))) {
            _snowman.h(this.c.A());
         }
      }

      @Override
      public void d() {
         super.d();
         aqm _snowman = this.c.A();
         if (_snowman != null) {
            for (bhc _snowmanx : this.c.l.a(bhc.class, this.a, this.c, this.c.cc().c(8.0, 8.0, 8.0))) {
               _snowmanx.h(_snowman);
               _snowmanx.s(true);
            }

            this.c.s(true);
         }
      }

      @Override
      public void e() {
         aqm _snowman = this.c.A();
         if (_snowman != null) {
            if (this.c.h((aqa)_snowman) > (double)this.d) {
               this.c.t().a(_snowman, 30.0F, 30.0F);
               if (this.c.J.nextInt(50) == 0) {
                  this.c.F();
               }
            } else {
               this.c.s(true);
            }

            super.e();
         }
      }
   }

   public class b<T extends bhc> extends avv {
      private final T b;

      public b(T var2) {
         this.b = _snowman;
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         bhb _snowman = this.b.fa();
         if (this.b.fb() && !this.b.fa().a() && this.b.eN() && !bmb.b(this.b.b(aqf.f), bhb.s())) {
            bhc _snowmanx = _snowman.b(this.b.fc());
            if (_snowmanx == null || !_snowmanx.aX()) {
               List<bcv> _snowmanxx = this.b.l.a(bcv.class, this.b.cc().c(16.0, 8.0, 16.0), bhc.b);
               if (!_snowmanxx.isEmpty()) {
                  return this.b.x().a(_snowmanxx.get(0), 1.15F);
               }
            }

            return false;
         } else {
            return false;
         }
      }

      @Override
      public void e() {
         if (this.b.x().h().a(this.b.cA(), 1.414)) {
            List<bcv> _snowman = this.b.l.a(bcv.class, this.b.cc().c(4.0, 4.0, 4.0), bhc.b);
            if (!_snowman.isEmpty()) {
               this.b.b(_snowman.get(0));
            }
         }
      }
   }

   public class c extends avv {
      private final bhc b;

      c(bhc var2) {
         this.b = _snowman;
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         bhb _snowman = this.b.fa();
         return this.b.aX() && this.b.A() == null && _snowman != null && _snowman.f();
      }

      @Override
      public void c() {
         this.b.x(true);
         super.c();
      }

      @Override
      public void d() {
         this.b.x(false);
         super.d();
      }

      @Override
      public void e() {
         if (!this.b.aA() && this.b.J.nextInt(100) == 0) {
            bhc.this.a(bhc.this.eL(), bhc.this.dG(), bhc.this.dH());
         }

         if (!this.b.br() && this.b.J.nextInt(50) == 0) {
            this.b.v().a();
         }

         super.e();
      }
   }

   static class d extends avv {
      private final bhc a;
      private final double b;
      private fx c;
      private final List<fx> d = Lists.newArrayList();
      private final int e;
      private boolean f;

      public d(bhc var1, double var2, int var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.e = _snowman;
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         this.j();
         return this.g() && this.h() && this.a.A() == null;
      }

      private boolean g() {
         return this.a.fb() && !this.a.fa().a();
      }

      private boolean h() {
         aag _snowman = (aag)this.a.l;
         fx _snowmanx = this.a.cB();
         Optional<fx> _snowmanxx = _snowman.y().a(var0 -> var0 == azr.r, this::a, azo.b.c, _snowmanx, 48, this.a.J);
         if (!_snowmanxx.isPresent()) {
            return false;
         } else {
            this.c = _snowmanxx.get().h();
            return true;
         }
      }

      @Override
      public boolean b() {
         return this.a.x().m() ? false : this.a.A() == null && !this.c.a(this.a.cA(), (double)(this.a.cy() + (float)this.e)) && !this.f;
      }

      @Override
      public void d() {
         if (this.c.a(this.a.cA(), (double)this.e)) {
            this.d.add(this.c);
         }
      }

      @Override
      public void c() {
         super.c();
         this.a.n(0);
         this.a.x().a((double)this.c.u(), (double)this.c.v(), (double)this.c.w(), this.b);
         this.f = false;
      }

      @Override
      public void e() {
         if (this.a.x().m()) {
            dcn _snowman = dcn.c(this.c);
            dcn _snowmanx = azj.a(this.a, 16, 7, _snowman, (float) (Math.PI / 10));
            if (_snowmanx == null) {
               _snowmanx = azj.b(this.a, 8, 7, _snowman);
            }

            if (_snowmanx == null) {
               this.f = true;
               return;
            }

            this.a.x().a(_snowmanx.b, _snowmanx.c, _snowmanx.d, this.b);
         }
      }

      private boolean a(fx var1) {
         for (fx _snowman : this.d) {
            if (Objects.equals(_snowman, _snowman)) {
               return false;
            }
         }

         return true;
      }

      private void j() {
         if (this.d.size() > 2) {
            this.d.remove(0);
         }
      }
   }
}
