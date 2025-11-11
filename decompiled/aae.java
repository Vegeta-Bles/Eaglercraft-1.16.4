import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class aae extends cfz {
   private static final List<cga> b = cga.a();
   private final zy c;
   private final cfy d;
   private final aag e;
   private final Thread f;
   private final aaj g;
   private final aae.a h;
   public final zs a;
   private final cyc i;
   private long j;
   private boolean k = true;
   private boolean l = true;
   private final long[] m = new long[4];
   private final cga[] n = new cga[4];
   private final cfw[] o = new cfw[4];
   @Nullable
   private bsg.d p;

   public aae(aag var1, cyg.a var2, DataFixer var3, csw var4, Executor var5, cfy var6, int var7, boolean var8, aap var9, Supplier<cyc> var10) {
      this.e = _snowman;
      this.h = new aae.a(_snowman);
      this.d = _snowman;
      this.f = Thread.currentThread();
      File _snowman = _snowman.a(_snowman.Y());
      File _snowmanx = new File(_snowman, "data");
      _snowmanx.mkdirs();
      this.i = new cyc(_snowmanx, _snowman);
      this.a = new zs(_snowman, _snowman, _snowman, _snowman, _snowman, this.h, this, this.g(), _snowman, _snowman, _snowman, _snowman);
      this.g = this.a.a();
      this.c = this.a.e();
      this.n();
   }

   public aaj a() {
      return this.g;
   }

   @Nullable
   private zr a(long var1) {
      return this.a.b(_snowman);
   }

   public int b() {
      return this.a.c();
   }

   private void a(long var1, cfw var3, cga var4) {
      for (int _snowman = 3; _snowman > 0; _snowman--) {
         this.m[_snowman] = this.m[_snowman - 1];
         this.n[_snowman] = this.n[_snowman - 1];
         this.o[_snowman] = this.o[_snowman - 1];
      }

      this.m[0] = _snowman;
      this.n[0] = _snowman;
      this.o[0] = _snowman;
   }

   @Nullable
   @Override
   public cfw a(int var1, int var2, cga var3, boolean var4) {
      if (Thread.currentThread() != this.f) {
         return CompletableFuture.<cfw>supplyAsync(() -> this.a(_snowman, _snowman, _snowman, _snowman), this.h).join();
      } else {
         anw _snowman = this.e.Z();
         _snowman.c("getChunk");
         long _snowmanx = brd.a(_snowman, _snowman);

         for (int _snowmanxx = 0; _snowmanxx < 4; _snowmanxx++) {
            if (_snowmanx == this.m[_snowmanxx] && _snowman == this.n[_snowmanxx]) {
               cfw _snowmanxxx = this.o[_snowmanxx];
               if (_snowmanxxx != null || !_snowman) {
                  return _snowmanxxx;
               }
            }
         }

         _snowman.c("getChunkCacheMiss");
         CompletableFuture<Either<cfw, zr.a>> _snowmanxxx = this.c(_snowman, _snowman, _snowman, _snowman);
         this.h.c(_snowmanxxx::isDone);
         cfw _snowmanxxxx = (cfw)_snowmanxxx.join().map(var0 -> var0, var1x -> {
            if (_snowman) {
               throw (IllegalStateException)x.c(new IllegalStateException("Chunk not there when requested: " + var1x));
            } else {
               return null;
            }
         });
         this.a(_snowmanx, _snowmanxxxx, _snowman);
         return _snowmanxxxx;
      }
   }

   @Nullable
   @Override
   public cgh a(int var1, int var2) {
      if (Thread.currentThread() != this.f) {
         return null;
      } else {
         this.e.Z().c("getChunkNow");
         long _snowman = brd.a(_snowman, _snowman);

         for (int _snowmanx = 0; _snowmanx < 4; _snowmanx++) {
            if (_snowman == this.m[_snowmanx] && this.n[_snowmanx] == cga.m) {
               cfw _snowmanxx = this.o[_snowmanx];
               return _snowmanxx instanceof cgh ? (cgh)_snowmanxx : null;
            }
         }

         zr _snowmanxx = this.a(_snowman);
         if (_snowmanxx == null) {
            return null;
         } else {
            Either<cfw, zr.a> _snowmanxxx = _snowmanxx.b(cga.m).getNow(null);
            if (_snowmanxxx == null) {
               return null;
            } else {
               cfw _snowmanxxxx = (cfw)_snowmanxxx.left().orElse(null);
               if (_snowmanxxxx != null) {
                  this.a(_snowman, _snowmanxxxx, cga.m);
                  if (_snowmanxxxx instanceof cgh) {
                     return (cgh)_snowmanxxxx;
                  }
               }

               return null;
            }
         }
      }
   }

   private void n() {
      Arrays.fill(this.m, brd.a);
      Arrays.fill(this.n, null);
      Arrays.fill(this.o, null);
   }

   public CompletableFuture<Either<cfw, zr.a>> b(int var1, int var2, cga var3, boolean var4) {
      boolean _snowman = Thread.currentThread() == this.f;
      CompletableFuture<Either<cfw, zr.a>> _snowmanx;
      if (_snowman) {
         _snowmanx = this.c(_snowman, _snowman, _snowman, _snowman);
         this.h.c(_snowmanx::isDone);
      } else {
         _snowmanx = CompletableFuture.<CompletableFuture<Either<cfw, zr.a>>>supplyAsync(() -> this.c(_snowman, _snowman, _snowman, _snowman), this.h)
            .thenCompose(var0 -> (CompletionStage<Either<cfw, zr.a>>)var0);
      }

      return _snowmanx;
   }

   private CompletableFuture<Either<cfw, zr.a>> c(int var1, int var2, cga var3, boolean var4) {
      brd _snowman = new brd(_snowman, _snowman);
      long _snowmanx = _snowman.a();
      int _snowmanxx = 33 + cga.a(_snowman);
      zr _snowmanxxx = this.a(_snowmanx);
      if (_snowman) {
         this.c.a(aal.h, _snowman, _snowmanxx, _snowman);
         if (this.a(_snowmanxxx, _snowmanxx)) {
            anw _snowmanxxxx = this.e.Z();
            _snowmanxxxx.a("chunkLoad");
            this.o();
            _snowmanxxx = this.a(_snowmanx);
            _snowmanxxxx.c();
            if (this.a(_snowmanxxx, _snowmanxx)) {
               throw (IllegalStateException)x.c(new IllegalStateException("No chunk holder after ticket has been added"));
            }
         }
      }

      return this.a(_snowmanxxx, _snowmanxx) ? zr.b : _snowmanxxx.a(_snowman, this.a);
   }

   private boolean a(@Nullable zr var1, int var2) {
      return _snowman == null || _snowman.j() > _snowman;
   }

   @Override
   public boolean b(int var1, int var2) {
      zr _snowman = this.a(new brd(_snowman, _snowman).a());
      int _snowmanx = 33 + cga.a(cga.m);
      return !this.a(_snowman, _snowmanx);
   }

   @Override
   public brc c(int var1, int var2) {
      long _snowman = brd.a(_snowman, _snowman);
      zr _snowmanx = this.a(_snowman);
      if (_snowmanx == null) {
         return null;
      } else {
         int _snowmanxx = b.size() - 1;

         while (true) {
            cga _snowmanxxx = b.get(_snowmanxx);
            Optional<cfw> _snowmanxxxx = _snowmanx.a(_snowmanxxx).getNow(zr.a).left();
            if (_snowmanxxxx.isPresent()) {
               return _snowmanxxxx.get();
            }

            if (_snowmanxxx == cga.j.e()) {
               return null;
            }

            _snowmanxx--;
         }
      }
   }

   public brx c() {
      return this.e;
   }

   public boolean d() {
      return this.h.y();
   }

   private boolean o() {
      boolean _snowman = this.c.a(this.a);
      boolean _snowmanx = this.a.b();
      if (!_snowman && !_snowmanx) {
         return false;
      } else {
         this.n();
         return true;
      }
   }

   @Override
   public boolean a(aqa var1) {
      long _snowman = brd.a(afm.c(_snowman.cD()) >> 4, afm.c(_snowman.cH()) >> 4);
      return this.a(_snowman, zr::b);
   }

   @Override
   public boolean a(brd var1) {
      return this.a(_snowman.a(), zr::b);
   }

   @Override
   public boolean a(fx var1) {
      long _snowman = brd.a(_snowman.u() >> 4, _snowman.w() >> 4);
      return this.a(_snowman, zr::a);
   }

   private boolean a(long var1, Function<zr, CompletableFuture<Either<cgh, zr.a>>> var3) {
      zr _snowman = this.a(_snowman);
      if (_snowman == null) {
         return false;
      } else {
         Either<cgh, zr.a> _snowmanx = _snowman.apply(_snowman).getNow(zr.c);
         return _snowmanx.left().isPresent();
      }
   }

   public void a(boolean var1) {
      this.o();
      this.a.a(_snowman);
   }

   @Override
   public void close() throws IOException {
      this.a(true);
      this.g.close();
      this.a.close();
   }

   public void a(BooleanSupplier var1) {
      this.e.Z().a("purge");
      this.c.a();
      this.o();
      this.e.Z().b("chunks");
      this.p();
      this.e.Z().b("unload");
      this.a.a(_snowman);
      this.e.Z().c();
      this.n();
   }

   private void p() {
      long _snowman = this.e.T();
      long _snowmanx = _snowman - this.j;
      this.j = _snowman;
      cyd _snowmanxx = this.e.h();
      boolean _snowmanxxx = this.e.ab();
      boolean _snowmanxxxx = this.e.V().b(brt.d);
      if (!_snowmanxxx) {
         this.e.Z().a("pollingChunks");
         int _snowmanxxxxx = this.e.V().c(brt.m);
         boolean _snowmanxxxxxx = _snowmanxx.e() % 400L == 0L;
         this.e.Z().a("naturalSpawnCount");
         int _snowmanxxxxxxx = this.c.b();
         bsg.d _snowmanxxxxxxxx = bsg.a(_snowmanxxxxxxx, this.e.A(), this::a);
         this.p = _snowmanxxxxxxxx;
         this.e.Z().c();
         List<zr> _snowmanxxxxxxxxx = Lists.newArrayList(this.a.f());
         Collections.shuffle(_snowmanxxxxxxxxx);
         _snowmanxxxxxxxxx.forEach(var7x -> {
            Optional<cgh> _snowmanxxxxxxxxxx = var7x.a().getNow(zr.c).left();
            if (_snowmanxxxxxxxxxx.isPresent()) {
               this.e.Z().a("broadcast");
               var7x.a(_snowmanxxxxxxxxxx.get());
               this.e.Z().c();
               Optional<cgh> _snowmanx = var7x.b().getNow(zr.c).left();
               if (_snowmanx.isPresent()) {
                  cgh _snowmanxx = _snowmanx.get();
                  brd _snowmanxxx = var7x.i();
                  if (!this.a.d(_snowmanxxx)) {
                     _snowmanxx.b(_snowmanxx.q() + _snowman);
                     if (_snowman && (this.k || this.l) && this.e.f().a(_snowmanxx.g())) {
                        bsg.a(this.e, _snowmanxx, _snowman, this.l, this.k, _snowman);
                     }

                     this.e.a(_snowmanxx, _snowman);
                  }
               }
            }
         });
         this.e.Z().a("customSpawners");
         if (_snowmanxxxx) {
            this.e.a(this.k, this.l);
         }

         this.e.Z().c();
         this.e.Z().c();
      }

      this.a.g();
   }

   private void a(long var1, Consumer<cgh> var3) {
      zr _snowman = this.a(_snowman);
      if (_snowman != null) {
         _snowman.c().getNow(zr.c).left().ifPresent(_snowman);
      }
   }

   @Override
   public String e() {
      return "ServerChunkCache: " + this.h();
   }

   @VisibleForTesting
   public int f() {
      return this.h.bi();
   }

   public cfy g() {
      return this.d;
   }

   public int h() {
      return this.a.d();
   }

   public void b(fx var1) {
      int _snowman = _snowman.u() >> 4;
      int _snowmanx = _snowman.w() >> 4;
      zr _snowmanxx = this.a(brd.a(_snowman, _snowmanx));
      if (_snowmanxx != null) {
         _snowmanxx.a(_snowman);
      }
   }

   @Override
   public void a(bsf var1, gp var2) {
      this.h.execute(() -> {
         zr _snowman = this.a(_snowman.r().a());
         if (_snowman != null) {
            _snowman.a(_snowman, _snowman.b());
         }
      });
   }

   public <T> void a(aal<T> var1, brd var2, int var3, T var4) {
      this.c.c(_snowman, _snowman, _snowman, _snowman);
   }

   public <T> void b(aal<T> var1, brd var2, int var3, T var4) {
      this.c.d(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(brd var1, boolean var2) {
      this.c.a(_snowman, _snowman);
   }

   public void a(aah var1) {
      this.a.a(_snowman);
   }

   public void b(aqa var1) {
      this.a.b(_snowman);
   }

   public void c(aqa var1) {
      this.a.a(_snowman);
   }

   public void a(aqa var1, oj<?> var2) {
      this.a.b(_snowman, _snowman);
   }

   public void b(aqa var1, oj<?> var2) {
      this.a.a(_snowman, _snowman);
   }

   public void a(int var1) {
      this.a.a(_snowman);
   }

   @Override
   public void a(boolean var1, boolean var2) {
      this.k = _snowman;
      this.l = _snowman;
   }

   public String b(brd var1) {
      return this.a.a(_snowman);
   }

   public cyc i() {
      return this.i;
   }

   public azo j() {
      return this.a.h();
   }

   @Nullable
   public bsg.d k() {
      return this.p;
   }

   final class a extends aob<Runnable> {
      private a(brx var2) {
         super("Chunk source main thread executor for " + _snowman.Y().a());
      }

      @Override
      protected Runnable e(Runnable var1) {
         return _snowman;
      }

      @Override
      protected boolean d(Runnable var1) {
         return true;
      }

      @Override
      protected boolean av() {
         return true;
      }

      @Override
      protected Thread aw() {
         return aae.this.f;
      }

      @Override
      protected void c(Runnable var1) {
         aae.this.e.Z().c("runTask");
         super.c(_snowman);
      }

      @Override
      protected boolean y() {
         if (aae.this.o()) {
            return true;
         } else {
            aae.this.g.z_();
            return super.y();
         }
      }
   }
}
