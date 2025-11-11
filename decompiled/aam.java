import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aam implements bsr {
   private static final Logger a = LogManager.getLogger();
   private final List<cfw> b;
   private final int c;
   private final int d;
   private final int e;
   private final aag f;
   private final long g;
   private final cyd h;
   private final Random i;
   private final chd j;
   private final bso<buo> k = new aan<>(var1x -> this.z(var1x).n());
   private final bso<cuw> l = new aan<>(var1x -> this.z(var1x).o());
   private final bsx m;
   private final brd n;
   private final brd o;
   private final bsn p;

   public aam(aag var1, List<cfw> var2) {
      int _snowman = afm.c(Math.sqrt((double)_snowman.size()));
      if (_snowman * _snowman != _snowman.size()) {
         throw (IllegalStateException)x.c(new IllegalStateException("Cache size is not a square."));
      } else {
         brd _snowmanx = _snowman.get(_snowman.size() / 2).g();
         this.b = _snowman;
         this.c = _snowmanx.b;
         this.d = _snowmanx.c;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman.C();
         this.h = _snowman.h();
         this.i = _snowman.u_();
         this.j = _snowman.k();
         this.m = new bsx(this, bsx.a(this.g), _snowman.k().m());
         this.n = _snowman.get(0).g();
         this.o = _snowman.get(_snowman.size() - 1).g();
         this.p = _snowman.a().a(this);
      }
   }

   public int a() {
      return this.c;
   }

   public int b() {
      return this.d;
   }

   @Override
   public cfw a(int var1, int var2) {
      return this.a(_snowman, _snowman, cga.a);
   }

   @Nullable
   @Override
   public cfw a(int var1, int var2, cga var3, boolean var4) {
      cfw _snowman;
      if (this.b(_snowman, _snowman)) {
         int _snowmanx = _snowman - this.n.b;
         int _snowmanxx = _snowman - this.n.c;
         _snowman = this.b.get(_snowmanx + _snowmanxx * this.e);
         if (_snowman.k().b(_snowman)) {
            return _snowman;
         }
      } else {
         _snowman = null;
      }

      if (!_snowman) {
         return null;
      } else {
         a.error("Requested chunk : {} {}", _snowman, _snowman);
         a.error("Region bounds : {} {} | {} {}", this.n.b, this.n.c, this.o.b, this.o.c);
         if (_snowman != null) {
            throw (RuntimeException)x.c(new RuntimeException(String.format("Chunk is not of correct status. Expecting %s, got %s | %s %s", _snowman, _snowman.k(), _snowman, _snowman)));
         } else {
            throw (RuntimeException)x.c(new RuntimeException(String.format("We are asking a region for a chunk out of bound | %s %s", _snowman, _snowman)));
         }
      }
   }

   @Override
   public boolean b(int var1, int var2) {
      return _snowman >= this.n.b && _snowman <= this.o.b && _snowman >= this.n.c && _snowman <= this.o.c;
   }

   @Override
   public ceh d_(fx var1) {
      return this.a(_snowman.u() >> 4, _snowman.w() >> 4).d_(_snowman);
   }

   @Override
   public cux b(fx var1) {
      return this.z(_snowman).b(_snowman);
   }

   @Nullable
   @Override
   public bfw a(double var1, double var3, double var5, double var7, Predicate<aqa> var9) {
      return null;
   }

   @Override
   public int c() {
      return 0;
   }

   @Override
   public bsx d() {
      return this.m;
   }

   @Override
   public bsv a(int var1, int var2, int var3) {
      return this.f.a(_snowman, _snowman, _snowman);
   }

   @Override
   public float a(gc var1, boolean var2) {
      return 1.0F;
   }

   @Override
   public cuo e() {
      return this.f.e();
   }

   @Override
   public boolean a(fx var1, boolean var2, @Nullable aqa var3, int var4) {
      ceh _snowman = this.d_(_snowman);
      if (_snowman.g()) {
         return false;
      } else {
         if (_snowman) {
            ccj _snowmanx = _snowman.b().q() ? this.c(_snowman) : null;
            buo.a(_snowman, (brx)this.f, _snowman, _snowmanx, _snowman, bmb.b);
         }

         return this.a(_snowman, bup.a.n(), 3, _snowman);
      }
   }

   @Nullable
   @Override
   public ccj c(fx var1) {
      cfw _snowman = this.z(_snowman);
      ccj _snowmanx = _snowman.c(_snowman);
      if (_snowmanx != null) {
         return _snowmanx;
      } else {
         md _snowmanxx = _snowman.i(_snowman);
         ceh _snowmanxxx = _snowman.d_(_snowman);
         if (_snowmanxx != null) {
            if ("DUMMY".equals(_snowmanxx.l("id"))) {
               buo _snowmanxxxx = _snowmanxxx.b();
               if (!(_snowmanxxxx instanceof bwm)) {
                  return null;
               }

               _snowmanx = ((bwm)_snowmanxxxx).a(this.f);
            } else {
               _snowmanx = ccj.b(_snowmanxxx, _snowmanxx);
            }

            if (_snowmanx != null) {
               _snowman.a(_snowman, _snowmanx);
               return _snowmanx;
            }
         }

         if (_snowmanxxx.b() instanceof bwm) {
            a.warn("Tried to access a block entity before it was created. {}", _snowman);
         }

         return null;
      }
   }

   @Override
   public boolean a(fx var1, ceh var2, int var3, int var4) {
      cfw _snowman = this.z(_snowman);
      ceh _snowmanx = _snowman.a(_snowman, _snowman, false);
      if (_snowmanx != null) {
         this.f.a(_snowman, _snowmanx, _snowman);
      }

      buo _snowmanxx = _snowman.b();
      if (_snowmanxx.q()) {
         if (_snowman.k().g() == cga.a.b) {
            _snowman.a(_snowman, ((bwm)_snowmanxx).a(this));
         } else {
            md _snowmanxxx = new md();
            _snowmanxxx.b("x", _snowman.u());
            _snowmanxxx.b("y", _snowman.v());
            _snowmanxxx.b("z", _snowman.w());
            _snowmanxxx.a("id", "DUMMY");
            _snowman.a(_snowmanxxx);
         }
      } else if (_snowmanx != null && _snowmanx.b().q()) {
         _snowman.d(_snowman);
      }

      if (_snowman.q(this, _snowman)) {
         this.j(_snowman);
      }

      return true;
   }

   private void j(fx var1) {
      this.z(_snowman).e(_snowman);
   }

   @Override
   public boolean c(aqa var1) {
      int _snowman = afm.c(_snowman.cD() / 16.0);
      int _snowmanx = afm.c(_snowman.cH() / 16.0);
      this.a(_snowman, _snowmanx).a(_snowman);
      return true;
   }

   @Override
   public boolean a(fx var1, boolean var2) {
      return this.a(_snowman, bup.a.n(), 3);
   }

   @Override
   public cfu f() {
      return this.f.f();
   }

   @Override
   public boolean s_() {
      return false;
   }

   @Deprecated
   @Override
   public aag E() {
      return this.f;
   }

   @Override
   public gn r() {
      return this.f.r();
   }

   @Override
   public cyd h() {
      return this.h;
   }

   @Override
   public aos d(fx var1) {
      if (!this.b(_snowman.u() >> 4, _snowman.w() >> 4)) {
         throw new RuntimeException("We are asking a region for a chunk out of bound");
      } else {
         return new aos(this.f.ad(), this.f.U(), 0L, this.f.af());
      }
   }

   @Override
   public cfz H() {
      return this.f.i();
   }

   @Override
   public long C() {
      return this.g;
   }

   @Override
   public bso<buo> J() {
      return this.k;
   }

   @Override
   public bso<cuw> I() {
      return this.l;
   }

   @Override
   public int t_() {
      return this.f.t_();
   }

   @Override
   public Random u_() {
      return this.i;
   }

   @Override
   public int a(chn.a var1, int var2, int var3) {
      return this.a(_snowman >> 4, _snowman >> 4).a(_snowman, _snowman & 15, _snowman & 15) + 1;
   }

   @Override
   public void a(@Nullable bfw var1, fx var2, adp var3, adr var4, float var5, float var6) {
   }

   @Override
   public void a(hf var1, double var2, double var4, double var6, double var8, double var10, double var12) {
   }

   @Override
   public void a(@Nullable bfw var1, int var2, fx var3, int var4) {
   }

   @Override
   public chd k() {
      return this.j;
   }

   @Override
   public boolean a(fx var1, Predicate<ceh> var2) {
      return _snowman.test(this.d_(_snowman));
   }

   @Override
   public <T extends aqa> List<T> a(Class<? extends T> var1, dci var2, @Nullable Predicate<? super T> var3) {
      return Collections.emptyList();
   }

   @Override
   public List<aqa> a(@Nullable aqa var1, dci var2, @Nullable Predicate<? super aqa> var3) {
      return Collections.emptyList();
   }

   @Override
   public List<bfw> x() {
      return Collections.emptyList();
   }

   @Override
   public Stream<? extends crv<?>> a(gp var1, cla<?> var2) {
      return this.p.a(_snowman, _snowman);
   }
}
