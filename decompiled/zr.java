import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.shorts.ShortArraySet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class zr {
   public static final Either<cfw, zr.a> a = Either.right(zr.a.b);
   public static final CompletableFuture<Either<cfw, zr.a>> b = CompletableFuture.completedFuture(a);
   public static final Either<cgh, zr.a> c = Either.right(zr.a.b);
   private static final CompletableFuture<Either<cgh, zr.a>> d = CompletableFuture.completedFuture(c);
   private static final List<cga> e = cga.a();
   private static final zr.b[] f = zr.b.values();
   private final AtomicReferenceArray<CompletableFuture<Either<cfw, zr.a>>> g = new AtomicReferenceArray<>(e.size());
   private volatile CompletableFuture<Either<cgh, zr.a>> h = d;
   private volatile CompletableFuture<Either<cgh, zr.a>> i = d;
   private volatile CompletableFuture<Either<cgh, zr.a>> j = d;
   private CompletableFuture<cfw> k = CompletableFuture.completedFuture(null);
   private int l;
   private int m;
   private int n;
   private final brd o;
   private boolean p;
   private final ShortSet[] q = new ShortSet[16];
   private int r;
   private int s;
   private final cuo t;
   private final zr.c u;
   private final zr.d v;
   private boolean w;
   private boolean x;

   public zr(brd var1, int var2, cuo var3, zr.c var4, zr.d var5) {
      this.o = _snowman;
      this.t = _snowman;
      this.u = _snowman;
      this.v = _snowman;
      this.l = zs.a + 1;
      this.m = this.l;
      this.n = this.l;
      this.a(_snowman);
   }

   public CompletableFuture<Either<cfw, zr.a>> a(cga var1) {
      CompletableFuture<Either<cfw, zr.a>> _snowman = this.g.get(_snowman.c());
      return _snowman == null ? b : _snowman;
   }

   public CompletableFuture<Either<cfw, zr.a>> b(cga var1) {
      return b(this.m).b(_snowman) ? this.a(_snowman) : b;
   }

   public CompletableFuture<Either<cgh, zr.a>> a() {
      return this.i;
   }

   public CompletableFuture<Either<cgh, zr.a>> b() {
      return this.j;
   }

   public CompletableFuture<Either<cgh, zr.a>> c() {
      return this.h;
   }

   @Nullable
   public cgh d() {
      CompletableFuture<Either<cgh, zr.a>> _snowman = this.a();
      Either<cgh, zr.a> _snowmanx = _snowman.getNow(null);
      return _snowmanx == null ? null : (cgh)_snowmanx.left().orElse(null);
   }

   @Nullable
   public cga e() {
      for (int _snowman = e.size() - 1; _snowman >= 0; _snowman--) {
         cga _snowmanx = e.get(_snowman);
         CompletableFuture<Either<cfw, zr.a>> _snowmanxx = this.a(_snowmanx);
         if (_snowmanxx.getNow(a).left().isPresent()) {
            return _snowmanx;
         }
      }

      return null;
   }

   @Nullable
   public cfw f() {
      for (int _snowman = e.size() - 1; _snowman >= 0; _snowman--) {
         cga _snowmanx = e.get(_snowman);
         CompletableFuture<Either<cfw, zr.a>> _snowmanxx = this.a(_snowmanx);
         if (!_snowmanxx.isCompletedExceptionally()) {
            Optional<cfw> _snowmanxxx = _snowmanxx.getNow(a).left();
            if (_snowmanxxx.isPresent()) {
               return _snowmanxxx.get();
            }
         }
      }

      return null;
   }

   public CompletableFuture<cfw> g() {
      return this.k;
   }

   public void a(fx var1) {
      cgh _snowman = this.d();
      if (_snowman != null) {
         byte _snowmanx = (byte)gp.a(_snowman.v());
         if (this.q[_snowmanx] == null) {
            this.p = true;
            this.q[_snowmanx] = new ShortArraySet();
         }

         this.q[_snowmanx].add(gp.b(_snowman));
      }
   }

   public void a(bsf var1, int var2) {
      cgh _snowman = this.d();
      if (_snowman != null) {
         _snowman.a(true);
         if (_snowman == bsf.a) {
            this.s |= 1 << _snowman - -1;
         } else {
            this.r |= 1 << _snowman - -1;
         }
      }
   }

   public void a(cgh var1) {
      if (this.p || this.s != 0 || this.r != 0) {
         brx _snowman = _snowman.x();
         int _snowmanx = 0;

         for (int _snowmanxx = 0; _snowmanxx < this.q.length; _snowmanxx++) {
            _snowmanx += this.q[_snowmanxx] != null ? this.q[_snowmanxx].size() : 0;
         }

         this.x |= _snowmanx >= 64;
         if (this.s != 0 || this.r != 0) {
            this.a(new pw(_snowman.g(), this.t, this.s, this.r, true), !this.x);
            this.s = 0;
            this.r = 0;
         }

         for (int _snowmanxx = 0; _snowmanxx < this.q.length; _snowmanxx++) {
            ShortSet _snowmanxxx = this.q[_snowmanxx];
            if (_snowmanxxx != null) {
               gp _snowmanxxxx = gp.a(_snowman.g(), _snowmanxx);
               if (_snowmanxxx.size() == 1) {
                  fx _snowmanxxxxx = _snowmanxxxx.g(_snowmanxxx.iterator().nextShort());
                  ceh _snowmanxxxxxx = _snowman.d_(_snowmanxxxxx);
                  this.a(new oy(_snowmanxxxxx, _snowmanxxxxxx), false);
                  this.a(_snowman, _snowmanxxxxx, _snowmanxxxxxx);
               } else {
                  cgi _snowmanxxxxx = _snowman.d()[_snowmanxxxx.v()];
                  qr _snowmanxxxxxx = new qr(_snowmanxxxx, _snowmanxxx, _snowmanxxxxx, this.x);
                  this.a(_snowmanxxxxxx, false);
                  _snowmanxxxxxx.a((var2x, var3x) -> this.a(_snowman, var2x, var3x));
               }

               this.q[_snowmanxx] = null;
            }
         }

         this.p = false;
      }
   }

   private void a(brx var1, fx var2, ceh var3) {
      if (_snowman.b().q()) {
         this.a(_snowman, _snowman);
      }
   }

   private void a(brx var1, fx var2) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman != null) {
         ow _snowmanx = _snowman.a();
         if (_snowmanx != null) {
            this.a(_snowmanx, false);
         }
      }
   }

   private void a(oj<?> var1, boolean var2) {
      this.v.a(this.o, _snowman).forEach(var1x -> var1x.b.a(_snowman));
   }

   public CompletableFuture<Either<cfw, zr.a>> a(cga var1, zs var2) {
      int _snowman = _snowman.c();
      CompletableFuture<Either<cfw, zr.a>> _snowmanx = this.g.get(_snowman);
      if (_snowmanx != null) {
         Either<cfw, zr.a> _snowmanxx = _snowmanx.getNow(null);
         if (_snowmanxx == null || _snowmanxx.left().isPresent()) {
            return _snowmanx;
         }
      }

      if (b(this.m).b(_snowman)) {
         CompletableFuture<Either<cfw, zr.a>> _snowmanxx = _snowman.a(this, _snowman);
         this.a(_snowmanxx);
         this.g.set(_snowman, _snowmanxx);
         return _snowmanxx;
      } else {
         return _snowmanx == null ? b : _snowmanx;
      }
   }

   private void a(CompletableFuture<? extends Either<? extends cfw, zr.a>> var1) {
      this.k = this.k.thenCombine(_snowman, (var0, var1x) -> (cfw)var1x.map(var0x -> var0x, var1xx -> var0));
   }

   public zr.b h() {
      return c(this.m);
   }

   public brd i() {
      return this.o;
   }

   public int j() {
      return this.m;
   }

   public int k() {
      return this.n;
   }

   private void d(int var1) {
      this.n = _snowman;
   }

   public void a(int var1) {
      this.m = _snowman;
   }

   protected void a(zs var1) {
      cga _snowman = b(this.l);
      cga _snowmanx = b(this.m);
      boolean _snowmanxx = this.l <= zs.a;
      boolean _snowmanxxx = this.m <= zs.a;
      zr.b _snowmanxxxx = c(this.l);
      zr.b _snowmanxxxxx = c(this.m);
      if (_snowmanxx) {
         Either<cfw, zr.a> _snowmanxxxxxx = Either.right(new zr.a() {
            @Override
            public String toString() {
               return "Unloaded ticket level " + zr.this.o.toString();
            }
         });

         for (int _snowmanxxxxxxx = _snowmanxxx ? _snowmanx.c() + 1 : 0; _snowmanxxxxxxx <= _snowman.c(); _snowmanxxxxxxx++) {
            CompletableFuture<Either<cfw, zr.a>> _snowmanxxxxxxxx = this.g.get(_snowmanxxxxxxx);
            if (_snowmanxxxxxxxx != null) {
               _snowmanxxxxxxxx.complete(_snowmanxxxxxx);
            } else {
               this.g.set(_snowmanxxxxxxx, CompletableFuture.completedFuture(_snowmanxxxxxx));
            }
         }
      }

      boolean _snowmanxxxxxx = _snowmanxxxx.a(zr.b.b);
      boolean _snowmanxxxxxxxx = _snowmanxxxxx.a(zr.b.b);
      this.w |= _snowmanxxxxxxxx;
      if (!_snowmanxxxxxx && _snowmanxxxxxxxx) {
         this.h = _snowman.b(this);
         this.a(this.h);
      }

      if (_snowmanxxxxxx && !_snowmanxxxxxxxx) {
         CompletableFuture<Either<cgh, zr.a>> _snowmanxxxxxxxxx = this.h;
         this.h = d;
         this.a(_snowmanxxxxxxxxx.thenApply(var1x -> var1x.ifLeft(_snowman::a)));
      }

      boolean _snowmanxxxxxxxxx = _snowmanxxxx.a(zr.b.c);
      boolean _snowmanxxxxxxxxxx = _snowmanxxxxx.a(zr.b.c);
      if (!_snowmanxxxxxxxxx && _snowmanxxxxxxxxxx) {
         this.i = _snowman.a(this);
         this.a(this.i);
      }

      if (_snowmanxxxxxxxxx && !_snowmanxxxxxxxxxx) {
         this.i.complete(c);
         this.i = d;
      }

      boolean _snowmanxxxxxxxxxxx = _snowmanxxxx.a(zr.b.d);
      boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxx.a(zr.b.d);
      if (!_snowmanxxxxxxxxxxx && _snowmanxxxxxxxxxxxx) {
         if (this.j != d) {
            throw (IllegalStateException)x.c(new IllegalStateException());
         }

         this.j = _snowman.b(this.o);
         this.a(this.j);
      }

      if (_snowmanxxxxxxxxxxx && !_snowmanxxxxxxxxxxxx) {
         this.j.complete(c);
         this.j = d;
      }

      this.u.a(this.o, this::k, this.m, this::d);
      this.l = this.m;
   }

   public static cga b(int var0) {
      return _snowman < 33 ? cga.m : cga.a(_snowman - 33);
   }

   public static zr.b c(int var0) {
      return f[afm.a(33 - _snowman + 1, 0, f.length - 1)];
   }

   public boolean l() {
      return this.w;
   }

   public void m() {
      this.w = c(this.m).a(zr.b.b);
   }

   public void a(cgg var1) {
      for (int _snowman = 0; _snowman < this.g.length(); _snowman++) {
         CompletableFuture<Either<cfw, zr.a>> _snowmanx = this.g.get(_snowman);
         if (_snowmanx != null) {
            Optional<cfw> _snowmanxx = _snowmanx.getNow(a).left();
            if (_snowmanxx.isPresent() && _snowmanxx.get() instanceof cgp) {
               this.g.set(_snowman, CompletableFuture.completedFuture(Either.left(_snowman)));
            }
         }
      }

      this.a(CompletableFuture.completedFuture(Either.left(_snowman.u())));
   }

   public interface a {
      zr.a b = new zr.a() {
         @Override
         public String toString() {
            return "UNLOADED";
         }
      };
   }

   public static enum b {
      a,
      b,
      c,
      d;

      private b() {
      }

      public boolean a(zr.b var1) {
         return this.ordinal() >= _snowman.ordinal();
      }
   }

   public interface c {
      void a(brd var1, IntSupplier var2, int var3, IntConsumer var4);
   }

   public interface d {
      Stream<aah> a(brd var1, boolean var2);
   }
}
