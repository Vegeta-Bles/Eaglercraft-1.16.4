import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zs extends cgu implements zr.d {
   private static final Logger c = LogManager.getLogger();
   public static final int a = 33 + cga.b();
   private final Long2ObjectLinkedOpenHashMap<zr> d = new Long2ObjectLinkedOpenHashMap();
   private volatile Long2ObjectLinkedOpenHashMap<zr> e = this.d.clone();
   private final Long2ObjectLinkedOpenHashMap<zr> f = new Long2ObjectLinkedOpenHashMap();
   private final LongSet g = new LongOpenHashSet();
   private final aag h;
   private final aaj i;
   private final aob<Runnable> j;
   private final cfy k;
   private final Supplier<cyc> l;
   private final azo m;
   private final LongSet n = new LongOpenHashSet();
   private boolean o;
   private final zu p;
   private final aod<zu.a<Runnable>> q;
   private final aod<zu.a<Runnable>> r;
   private final aap s;
   private final zs.a t;
   private final AtomicInteger u = new AtomicInteger();
   private final csw v;
   private final File w;
   private final aaa x = new aaa();
   private final Int2ObjectMap<zs.b> y = new Int2ObjectOpenHashMap();
   private final Long2ByteMap z = new Long2ByteOpenHashMap();
   private final Queue<Runnable> A = Queues.newConcurrentLinkedQueue();
   private int B;

   public zs(
      aag var1,
      cyg.a var2,
      DataFixer var3,
      csw var4,
      Executor var5,
      aob<Runnable> var6,
      cgj var7,
      cfy var8,
      aap var9,
      Supplier<cyc> var10,
      int var11,
      boolean var12
   ) {
      super(new File(_snowman.a(_snowman.Y()), "region"), _snowman, _snowman);
      this.v = _snowman;
      this.w = _snowman.a(_snowman.Y());
      this.h = _snowman;
      this.k = _snowman;
      this.j = _snowman;
      aoe<Runnable> _snowman = aoe.a(_snowman, "worldgen");
      aod<Runnable> _snowmanx = aod.a("main", _snowman::h);
      this.s = _snowman;
      aoe<Runnable> _snowmanxx = aoe.a(_snowman, "light");
      this.p = new zu(ImmutableList.of(_snowman, _snowmanx, _snowmanxx), _snowman, Integer.MAX_VALUE);
      this.q = this.p.a(_snowman, false);
      this.r = this.p.a(_snowmanx, false);
      this.i = new aaj(_snowman, this, this.h.k().b(), _snowmanxx, this.p.a(_snowmanxx, false));
      this.t = new zs.a(_snowman, _snowman);
      this.l = _snowman;
      this.m = new azo(new File(this.w, "poi"), _snowman, _snowman);
      this.a(_snowman);
   }

   private static double a(brd var0, aqa var1) {
      double _snowman = (double)(_snowman.b * 16 + 8);
      double _snowmanx = (double)(_snowman.c * 16 + 8);
      double _snowmanxx = _snowman - _snowman.cD();
      double _snowmanxxx = _snowmanx - _snowman.cH();
      return _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx;
   }

   private static int b(brd var0, aah var1, boolean var2) {
      int _snowman;
      int _snowmanx;
      if (_snowman) {
         gp _snowmanxx = _snowman.O();
         _snowman = _snowmanxx.a();
         _snowmanx = _snowmanxx.c();
      } else {
         _snowman = afm.c(_snowman.cD() / 16.0);
         _snowmanx = afm.c(_snowman.cH() / 16.0);
      }

      return a(_snowman, _snowman, _snowmanx);
   }

   private static int a(brd var0, int var1, int var2) {
      int _snowman = _snowman.b - _snowman;
      int _snowmanx = _snowman.c - _snowman;
      return Math.max(Math.abs(_snowman), Math.abs(_snowmanx));
   }

   protected aaj a() {
      return this.i;
   }

   @Nullable
   protected zr a(long var1) {
      return (zr)this.d.get(_snowman);
   }

   @Nullable
   protected zr b(long var1) {
      return (zr)this.e.get(_snowman);
   }

   protected IntSupplier c(long var1) {
      return () -> {
         zr _snowman = this.b(_snowman);
         return _snowman == null ? zt.a - 1 : Math.min(_snowman.k(), zt.a - 1);
      };
   }

   public String a(brd var1) {
      zr _snowman = this.b(_snowman.a());
      if (_snowman == null) {
         return "null";
      } else {
         String _snowmanx = _snowman.j() + "\n";
         cga _snowmanxx = _snowman.e();
         cfw _snowmanxxx = _snowman.f();
         if (_snowmanxx != null) {
            _snowmanx = _snowmanx + "St: §" + _snowmanxx.c() + _snowmanxx + '§' + "r\n";
         }

         if (_snowmanxxx != null) {
            _snowmanx = _snowmanx + "Ch: §" + _snowmanxxx.k().c() + _snowmanxxx.k() + '§' + "r\n";
         }

         zr.b _snowmanxxxx = _snowman.h();
         _snowmanx = _snowmanx + "§" + _snowmanxxxx.ordinal() + _snowmanxxxx;
         return _snowmanx + '§' + "r";
      }
   }

   private CompletableFuture<Either<List<cfw>, zr.a>> a(brd var1, int var2, IntFunction<cga> var3) {
      List<CompletableFuture<Either<cfw, zr.a>>> _snowman = Lists.newArrayList();
      int _snowmanx = _snowman.b;
      int _snowmanxx = _snowman.c;

      for (int _snowmanxxx = -_snowman; _snowmanxxx <= _snowman; _snowmanxxx++) {
         for (int _snowmanxxxx = -_snowman; _snowmanxxxx <= _snowman; _snowmanxxxx++) {
            int _snowmanxxxxx = Math.max(Math.abs(_snowmanxxxx), Math.abs(_snowmanxxx));
            final brd _snowmanxxxxxx = new brd(_snowmanx + _snowmanxxxx, _snowmanxx + _snowmanxxx);
            long _snowmanxxxxxxx = _snowmanxxxxxx.a();
            zr _snowmanxxxxxxxx = this.a(_snowmanxxxxxxx);
            if (_snowmanxxxxxxxx == null) {
               return CompletableFuture.completedFuture(Either.right(new zr.a() {
                  @Override
                  public String toString() {
                     return "Unloaded " + _snowman.toString();
                  }
               }));
            }

            cga _snowmanxxxxxxxxx = _snowman.apply(_snowmanxxxxx);
            CompletableFuture<Either<cfw, zr.a>> _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.a(_snowmanxxxxxxxxx, this);
            _snowman.add(_snowmanxxxxxxxxxx);
         }
      }

      CompletableFuture<List<Either<cfw, zr.a>>> _snowmanxxx = x.b(_snowman);
      return _snowmanxxx.thenApply(var4x -> {
         List<cfw> _snowmanxxxx = Lists.newArrayList();
         int _snowmanx = 0;

         for (final Either<cfw, zr.a> _snowmanxx : var4x) {
            Optional<cfw> _snowmanxxx = _snowmanxx.left();
            if (!_snowmanxxx.isPresent()) {
               final int _snowmanxxxx = _snowmanx;
               return Either.right(new zr.a() {
                  @Override
                  public String toString() {
                     return "Unloaded " + new brd(_snowman + _snowman % (_snowman * 2 + 1), _snowman + _snowman / (_snowman * 2 + 1)) + " " + ((zr.a)_snowman.right().get()).toString();
                  }
               });
            }

            _snowmanxxxx.add(_snowmanxxx.get());
            _snowmanx++;
         }

         return Either.left(_snowmanxxxx);
      });
   }

   public CompletableFuture<Either<cgh, zr.a>> b(brd var1) {
      return this.a(_snowman, 2, var0 -> cga.m).thenApplyAsync(var0 -> var0.mapLeft(var0x -> (cgh)var0x.get(var0x.size() / 2)), this.j);
   }

   @Nullable
   private zr a(long var1, int var3, @Nullable zr var4, int var5) {
      if (_snowman > a && _snowman > a) {
         return _snowman;
      } else {
         if (_snowman != null) {
            _snowman.a(_snowman);
         }

         if (_snowman != null) {
            if (_snowman > a) {
               this.n.add(_snowman);
            } else {
               this.n.remove(_snowman);
            }
         }

         if (_snowman <= a && _snowman == null) {
            _snowman = (zr)this.f.remove(_snowman);
            if (_snowman != null) {
               _snowman.a(_snowman);
            } else {
               _snowman = new zr(new brd(_snowman), _snowman, this.i, this.p, this);
            }

            this.d.put(_snowman, _snowman);
            this.o = true;
         }

         return _snowman;
      }
   }

   @Override
   public void close() throws IOException {
      try {
         this.p.close();
         this.m.close();
      } finally {
         super.close();
      }
   }

   protected void a(boolean var1) {
      if (_snowman) {
         List<zr> _snowman = this.e.values().stream().filter(zr::l).peek(zr::m).collect(Collectors.toList());
         MutableBoolean _snowmanx = new MutableBoolean();

         do {
            _snowmanx.setFalse();
            _snowman.stream().map(var1x -> {
               CompletableFuture<cfw> _snowmanxx;
               do {
                  _snowmanxx = var1x.g();
                  this.j.c(_snowmanxx::isDone);
               } while (_snowmanxx != var1x.g());

               return _snowmanxx.join();
            }).filter(var0 -> var0 instanceof cgg || var0 instanceof cgh).filter(this::a).forEach(var1x -> _snowman.setTrue());
         } while (_snowmanx.isTrue());

         this.b((BooleanSupplier)(() -> true));
         this.i();
         c.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", this.w.getName());
      } else {
         this.e.values().stream().filter(zr::l).forEach(var1x -> {
            cfw _snowman = var1x.g().getNow(null);
            if (_snowman instanceof cgg || _snowman instanceof cgh) {
               this.a(_snowman);
               var1x.m();
            }
         });
      }
   }

   protected void a(BooleanSupplier var1) {
      anw _snowman = this.h.Z();
      _snowman.a("poi");
      this.m.a(_snowman);
      _snowman.b("chunk_unload");
      if (!this.h.q()) {
         this.b(_snowman);
      }

      _snowman.c();
   }

   private void b(BooleanSupplier var1) {
      LongIterator _snowman = this.n.iterator();

      for (int _snowmanx = 0; _snowman.hasNext() && (_snowman.getAsBoolean() || _snowmanx < 200 || this.n.size() > 2000); _snowman.remove()) {
         long _snowmanxx = _snowman.nextLong();
         zr _snowmanxxx = (zr)this.d.remove(_snowmanxx);
         if (_snowmanxxx != null) {
            this.f.put(_snowmanxx, _snowmanxxx);
            this.o = true;
            _snowmanx++;
            this.a(_snowmanxx, _snowmanxxx);
         }
      }

      Runnable _snowmanxx;
      while ((_snowman.getAsBoolean() || this.A.size() > 2000) && (_snowmanxx = this.A.poll()) != null) {
         _snowmanxx.run();
      }
   }

   private void a(long var1, zr var3) {
      CompletableFuture<cfw> _snowman = _snowman.g();
      _snowman.thenAcceptAsync(var5 -> {
         CompletableFuture<cfw> _snowmanx = _snowman.g();
         if (_snowmanx != _snowman) {
            this.a(_snowman, _snowman);
         } else {
            if (this.f.remove(_snowman, _snowman) && var5 != null) {
               if (var5 instanceof cgh) {
                  ((cgh)var5).c(false);
               }

               this.a(var5);
               if (this.g.remove(_snowman) && var5 instanceof cgh) {
                  cgh _snowmanx = (cgh)var5;
                  this.h.a(_snowmanx);
               }

               this.i.a(var5.g());
               this.i.z_();
               this.s.a(var5.g(), null);
            }
         }
      }, this.A::add).whenComplete((var1x, var2) -> {
         if (var2 != null) {
            c.error("Failed to save chunk " + _snowman.i(), var2);
         }
      });
   }

   protected boolean b() {
      if (!this.o) {
         return false;
      } else {
         this.e = this.d.clone();
         this.o = false;
         return true;
      }
   }

   public CompletableFuture<Either<cfw, zr.a>> a(zr var1, cga var2) {
      brd _snowman = _snowman.i();
      if (_snowman == cga.a) {
         return this.f(_snowman);
      } else {
         CompletableFuture<Either<cfw, zr.a>> _snowmanx = _snowman.a(_snowman.e(), this);
         return _snowmanx.thenComposeAsync(var4x -> {
            Optional<cfw> _snowmanxx = var4x.left();
            if (!_snowmanxx.isPresent()) {
               return CompletableFuture.completedFuture((Either<cfw, zr.a>)var4x);
            } else {
               if (_snowman == cga.j) {
                  this.t.a(aal.e, _snowman, 33 + cga.a(cga.i), _snowman);
               }

               cfw _snowmanx = _snowmanxx.get();
               if (_snowmanx.k().b(_snowman)) {
                  CompletableFuture<Either<cfw, zr.a>> _snowmanxx;
                  if (_snowman == cga.j) {
                     _snowmanxx = this.b(_snowman, _snowman);
                  } else {
                     _snowmanxx = _snowman.a(this.h, this.v, this.i, var2x -> this.c(_snowman), _snowmanx);
                  }

                  this.s.a(_snowman, _snowman);
                  return _snowmanxx;
               } else {
                  return this.b(_snowman, _snowman);
               }
            }
         }, this.j);
      }
   }

   private CompletableFuture<Either<cfw, zr.a>> f(brd var1) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            this.h.Z().c("chunkLoad");
            md _snowman = this.i(_snowman);
            if (_snowman != null) {
               boolean _snowmanx = _snowman.c("Level", 10) && _snowman.p("Level").c("Status", 8);
               if (_snowmanx) {
                  cfw _snowmanxx = cgt.a(this.h, this.v, this.m, _snowman, _snowman);
                  _snowmanxx.a(this.h.T());
                  this.a(_snowman, _snowmanxx.k().g());
                  return Either.left(_snowmanxx);
               }

               c.error("Chunk file at {} is missing level data, skipping", _snowman);
            }
         } catch (u var5) {
            Throwable _snowmanx = var5.getCause();
            if (!(_snowmanx instanceof IOException)) {
               this.g(_snowman);
               throw var5;
            }

            c.error("Couldn't load chunk {}", _snowman, _snowmanx);
         } catch (Exception var6) {
            c.error("Couldn't load chunk {}", _snowman, var6);
         }

         this.g(_snowman);
         return Either.left(new cgp(_snowman, cgr.a));
      }, this.j);
   }

   private void g(brd var1) {
      this.z.put(_snowman.a(), (byte)-1);
   }

   private byte a(brd var1, cga.a var2) {
      return this.z.put(_snowman.a(), (byte)(_snowman == cga.a.a ? -1 : 1));
   }

   private CompletableFuture<Either<cfw, zr.a>> b(zr var1, cga var2) {
      brd _snowman = _snowman.i();
      CompletableFuture<Either<List<cfw>, zr.a>> _snowmanx = this.a(_snowman, _snowman.f(), var2x -> this.a(_snowman, var2x));
      this.h.Z().c(() -> "chunkGenerate " + _snowman.d());
      return _snowmanx.thenComposeAsync(var4x -> (CompletableFuture)var4x.map(var4xx -> {
            try {
               CompletableFuture<Either<cfw, zr.a>> _snowmanxx = _snowman.a(this.h, this.k, this.v, this.i, var2x -> this.c(_snowman), var4xx);
               this.s.a(_snowman, _snowman);
               return _snowmanxx;
            } catch (Exception var8) {
               l _snowmanx = l.a(var8, "Exception generating new chunk");
               m _snowmanxx = _snowmanx.a("Chunk to be generated");
               _snowmanxx.a("Location", String.format("%d,%d", _snowman.b, _snowman.c));
               _snowmanxx.a("Position hash", brd.a(_snowman.b, _snowman.c));
               _snowmanxx.a("Generator", this.k);
               throw new u(_snowmanx);
            }
         }, var2x -> {
            this.c(_snowman);
            return CompletableFuture.completedFuture(Either.right(var2x));
         }), var2x -> this.q.a(zu.a(_snowman, var2x)));
   }

   protected void c(brd var1) {
      this.j.h(x.a(() -> this.t.b(aal.e, _snowman, 33 + cga.a(cga.i), _snowman), () -> "release light ticket " + _snowman));
   }

   private cga a(cga var1, int var2) {
      cga _snowman;
      if (_snowman == 0) {
         _snowman = _snowman.e();
      } else {
         _snowman = cga.a(cga.a(_snowman) + _snowman);
      }

      return _snowman;
   }

   private CompletableFuture<Either<cfw, zr.a>> c(zr var1) {
      CompletableFuture<Either<cfw, zr.a>> _snowman = _snowman.a(cga.m.e());
      return _snowman.thenApplyAsync(var2x -> {
         cga _snowmanx = zr.b(_snowman.j());
         return !_snowmanx.b(cga.m) ? zr.a : var2x.mapLeft(var2xx -> {
            brd _snowmanx = _snowman.i();
            cgh _snowmanx;
            if (var2xx instanceof cgg) {
               _snowmanx = ((cgg)var2xx).u();
            } else {
               _snowmanx = new cgh(this.h, (cgp)var2xx);
               _snowman.a(new cgg(_snowmanx));
            }

            _snowmanx.a(() -> zr.c(_snowman.j()));
            _snowmanx.w();
            if (this.g.add(_snowmanx.a())) {
               _snowmanx.c(true);
               this.h.a(_snowmanx.y().values());
               List<aqa> _snowmanxx = null;
               aes[] var6 = _snowmanx.z();
               int var7 = var6.length;

               for (int var8 = 0; var8 < var7; var8++) {
                  for (aqa _snowmanxxx : var6[var8]) {
                     if (!(_snowmanxxx instanceof bfw) && !this.h.f(_snowmanxxx)) {
                        if (_snowmanxx == null) {
                           _snowmanxx = Lists.newArrayList(new aqa[]{_snowmanxxx});
                        } else {
                           _snowmanxx.add(_snowmanxxx);
                        }
                     }
                  }
               }

               if (_snowmanxx != null) {
                  _snowmanxx.forEach(_snowmanx::b);
               }
            }

            return _snowmanx;
         });
      }, var2x -> this.r.a(zu.a(var2x, _snowman.i().a(), _snowman::j)));
   }

   public CompletableFuture<Either<cgh, zr.a>> a(zr var1) {
      brd _snowman = _snowman.i();
      CompletableFuture<Either<List<cfw>, zr.a>> _snowmanx = this.a(_snowman, 1, var0 -> cga.m);
      CompletableFuture<Either<cgh, zr.a>> _snowmanxx = _snowmanx.thenApplyAsync(var0 -> var0.flatMap(var0x -> {
            cgh _snowmanxxx = (cgh)var0x.get(var0x.size() / 2);
            _snowmanxxx.A();
            return Either.left(_snowmanxxx);
         }), var2x -> this.r.a(zu.a(_snowman, var2x)));
      _snowmanxx.thenAcceptAsync(var2x -> var2x.mapLeft(var2xx -> {
            this.u.getAndIncrement();
            oj<?>[] _snowmanxxx = new oj[2];
            this.a(_snowman, false).forEach(var3x -> this.a(var3x, _snowman, var2xx));
            return Either.left(var2xx);
         }), var2x -> this.r.a(zu.a(_snowman, var2x)));
      return _snowmanxx;
   }

   public CompletableFuture<Either<cgh, zr.a>> b(zr var1) {
      return _snowman.a(cga.m, this).thenApplyAsync(var0 -> var0.mapLeft(var0x -> {
            cgh _snowman = (cgh)var0x;
            _snowman.B();
            return _snowman;
         }), var2 -> this.r.a(zu.a(_snowman, var2)));
   }

   public int c() {
      return this.u.get();
   }

   private boolean a(cfw var1) {
      this.m.a(_snowman.g());
      if (!_snowman.j()) {
         return false;
      } else {
         _snowman.a(this.h.T());
         _snowman.a(false);
         brd _snowman = _snowman.g();

         try {
            cga _snowmanx = _snowman.k();
            if (_snowmanx.g() != cga.a.b) {
               if (this.h(_snowman)) {
                  return false;
               }

               if (_snowmanx == cga.a && _snowman.h().values().stream().noneMatch(crv::e)) {
                  return false;
               }
            }

            this.h.Z().c("chunkSave");
            md _snowmanxx = cgt.a(this.h, _snowman);
            this.a(_snowman, _snowmanxx);
            this.a(_snowman, _snowmanx.g());
            return true;
         } catch (Exception var5) {
            c.error("Failed to save chunk {},{}", _snowman.b, _snowman.c, var5);
            return false;
         }
      }
   }

   private boolean h(brd var1) {
      byte _snowman = this.z.get(_snowman.a());
      if (_snowman != 0) {
         return _snowman == 1;
      } else {
         md _snowmanx;
         try {
            _snowmanx = this.i(_snowman);
            if (_snowmanx == null) {
               this.g(_snowman);
               return false;
            }
         } catch (Exception var5) {
            c.error("Failed to read chunk {}", _snowman, var5);
            this.g(_snowman);
            return false;
         }

         cga.a _snowmanxx = cgt.a(_snowmanx);
         return this.a(_snowman, _snowmanxx) == 1;
      }
   }

   protected void a(int var1) {
      int _snowman = afm.a(_snowman + 1, 3, 33);
      if (_snowman != this.B) {
         int _snowmanx = this.B;
         this.B = _snowman;
         this.t.a(this.B);
         ObjectIterator var4 = this.d.values().iterator();

         while (var4.hasNext()) {
            zr _snowmanxx = (zr)var4.next();
            brd _snowmanxxx = _snowmanxx.i();
            oj<?>[] _snowmanxxxx = new oj[2];
            this.a(_snowmanxxx, false).forEach(var4x -> {
               int _snowmanxxxxx = b(_snowman, var4x, true);
               boolean _snowmanx = _snowmanxxxxx <= _snowman;
               boolean _snowmanxx = _snowmanxxxxx <= this.B;
               this.a(var4x, _snowman, _snowman, _snowmanx, _snowmanxx);
            });
         }
      }
   }

   protected void a(aah var1, brd var2, oj<?>[] var3, boolean var4, boolean var5) {
      if (_snowman.l == this.h) {
         if (_snowman && !_snowman) {
            zr _snowman = this.b(_snowman.a());
            if (_snowman != null) {
               cgh _snowmanx = _snowman.d();
               if (_snowmanx != null) {
                  this.a(_snowman, _snowman, _snowmanx);
               }

               rz.a(this.h, _snowman);
            }
         }

         if (!_snowman && _snowman) {
            _snowman.a(_snowman);
         }
      }
   }

   public int d() {
      return this.e.size();
   }

   protected zs.a e() {
      return this.t;
   }

   protected Iterable<zr> f() {
      return Iterables.unmodifiableIterable(this.e.values());
   }

   void a(Writer var1) throws IOException {
      aew _snowman = aew.a()
         .a("x")
         .a("z")
         .a("level")
         .a("in_memory")
         .a("status")
         .a("full_status")
         .a("accessible_ready")
         .a("ticking_ready")
         .a("entity_ticking_ready")
         .a("ticket")
         .a("spawning")
         .a("entity_count")
         .a("block_entity_count")
         .a(_snowman);
      ObjectBidirectionalIterator var3 = this.e.long2ObjectEntrySet().iterator();

      while (var3.hasNext()) {
         Entry<zr> _snowmanx = (Entry<zr>)var3.next();
         brd _snowmanxx = new brd(_snowmanx.getLongKey());
         zr _snowmanxxx = (zr)_snowmanx.getValue();
         Optional<cfw> _snowmanxxxx = Optional.ofNullable(_snowmanxxx.f());
         Optional<cgh> _snowmanxxxxx = _snowmanxxxx.flatMap(var0 -> var0 instanceof cgh ? Optional.of((cgh)var0) : Optional.empty());
         _snowman.a(
            _snowmanxx.b,
            _snowmanxx.c,
            _snowmanxxx.j(),
            _snowmanxxxx.isPresent(),
            _snowmanxxxx.map(cfw::k).orElse(null),
            _snowmanxxxxx.map(cgh::u).orElse(null),
            a(_snowmanxxx.c()),
            a(_snowmanxxx.a()),
            a(_snowmanxxx.b()),
            this.t.c(_snowmanx.getLongKey()),
            !this.d(_snowmanxx),
            _snowmanxxxxx.<Integer>map(var0 -> Stream.of(var0.z()).mapToInt(aes::size).sum()).orElse(0),
            _snowmanxxxxx.<Integer>map(var0 -> var0.y().size()).orElse(0)
         );
      }
   }

   private static String a(CompletableFuture<Either<cgh, zr.a>> var0) {
      try {
         Either<cgh, zr.a> _snowman = _snowman.getNow(null);
         return _snowman != null ? (String)_snowman.map(var0x -> "done", var0x -> "unloaded") : "not completed";
      } catch (CompletionException var2) {
         return "failed " + var2.getCause().getMessage();
      } catch (CancellationException var3) {
         return "cancelled";
      }
   }

   @Nullable
   private md i(brd var1) throws IOException {
      md _snowman = this.e(_snowman);
      return _snowman == null ? null : this.a(this.h.Y(), this.l, _snowman);
   }

   boolean d(brd var1) {
      long _snowman = _snowman.a();
      return !this.t.d(_snowman) ? true : this.x.a(_snowman).noneMatch(var1x -> !var1x.a_() && a(_snowman, (aqa)var1x) < 16384.0);
   }

   private boolean b(aah var1) {
      return _snowman.a_() && !this.h.V().b(brt.p);
   }

   void a(aah var1, boolean var2) {
      boolean _snowman = this.b(_snowman);
      boolean _snowmanx = this.x.c(_snowman);
      int _snowmanxx = afm.c(_snowman.cD()) >> 4;
      int _snowmanxxx = afm.c(_snowman.cH()) >> 4;
      if (_snowman) {
         this.x.a(brd.a(_snowmanxx, _snowmanxxx), _snowman, _snowman);
         this.c(_snowman);
         if (!_snowman) {
            this.t.a(gp.a(_snowman), _snowman);
         }
      } else {
         gp _snowmanxxxx = _snowman.O();
         this.x.a(_snowmanxxxx.r().a(), _snowman);
         if (!_snowmanx) {
            this.t.b(_snowmanxxxx, _snowman);
         }
      }

      for (int _snowmanxxxxx = _snowmanxx - this.B; _snowmanxxxxx <= _snowmanxx + this.B; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = _snowmanxxx - this.B; _snowmanxxxxxx <= _snowmanxxx + this.B; _snowmanxxxxxx++) {
            brd _snowmanxxxxxxx = new brd(_snowmanxxxxx, _snowmanxxxxxx);
            this.a(_snowman, _snowmanxxxxxxx, new oj[2], !_snowman, _snowman);
         }
      }
   }

   private gp c(aah var1) {
      gp _snowman = gp.a(_snowman);
      _snowman.a(_snowman);
      _snowman.b.a(new qw(_snowman.a(), _snowman.c()));
      return _snowman;
   }

   public void a(aah var1) {
      ObjectIterator var2 = this.y.values().iterator();

      while (var2.hasNext()) {
         zs.b _snowman = (zs.b)var2.next();
         if (_snowman.c == _snowman) {
            _snowman.a(this.h.x());
         } else {
            _snowman.b(_snowman);
         }
      }

      int _snowman = afm.c(_snowman.cD()) >> 4;
      int _snowmanx = afm.c(_snowman.cH()) >> 4;
      gp _snowmanxx = _snowman.O();
      gp _snowmanxxx = gp.a(_snowman);
      long _snowmanxxxx = _snowmanxx.r().a();
      long _snowmanxxxxx = _snowmanxxx.r().a();
      boolean _snowmanxxxxxx = this.x.d(_snowman);
      boolean _snowmanxxxxxxx = this.b(_snowman);
      boolean _snowmanxxxxxxxx = _snowmanxx.s() != _snowmanxxx.s();
      if (_snowmanxxxxxxxx || _snowmanxxxxxx != _snowmanxxxxxxx) {
         this.c(_snowman);
         if (!_snowmanxxxxxx) {
            this.t.b(_snowmanxx, _snowman);
         }

         if (!_snowmanxxxxxxx) {
            this.t.a(_snowmanxxx, _snowman);
         }

         if (!_snowmanxxxxxx && _snowmanxxxxxxx) {
            this.x.a(_snowman);
         }

         if (_snowmanxxxxxx && !_snowmanxxxxxxx) {
            this.x.b(_snowman);
         }

         if (_snowmanxxxx != _snowmanxxxxx) {
            this.x.a(_snowmanxxxx, _snowmanxxxxx, _snowman);
         }
      }

      int _snowmanxxxxxxxxx = _snowmanxx.a();
      int _snowmanxxxxxxxxxx = _snowmanxx.c();
      if (Math.abs(_snowmanxxxxxxxxx - _snowman) <= this.B * 2 && Math.abs(_snowmanxxxxxxxxxx - _snowmanx) <= this.B * 2) {
         int _snowmanxxxxxxxxxxx = Math.min(_snowman, _snowmanxxxxxxxxx) - this.B;
         int _snowmanxxxxxxxxxxxx = Math.min(_snowmanx, _snowmanxxxxxxxxxx) - this.B;
         int _snowmanxxxxxxxxxxxxx = Math.max(_snowman, _snowmanxxxxxxxxx) + this.B;
         int _snowmanxxxxxxxxxxxxxx = Math.max(_snowmanx, _snowmanxxxxxxxxxx) + this.B;

         for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
               brd _snowmanxxxxxxxxxxxxxxxxx = new brd(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
               boolean _snowmanxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx) <= this.B;
               boolean _snowmanxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxx, _snowman, _snowmanx) <= this.B;
               this.a(_snowman, _snowmanxxxxxxxxxxxxxxxxx, new oj[2], _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
            }
         }
      } else {
         for (int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx - this.B; _snowmanxxxxxxxxxxx <= _snowmanxxxxxxxxx + this.B; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx - this.B; _snowmanxxxxxxxxxxxx <= _snowmanxxxxxxxxxx + this.B; _snowmanxxxxxxxxxxxx++) {
               brd _snowmanxxxxxxxxxxxxx = new brd(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
               boolean _snowmanxxxxxxxxxxxxxx = true;
               boolean _snowmanxxxxxxxxxxxxxxx = false;
               this.a(_snowman, _snowmanxxxxxxxxxxxxx, new oj[2], true, false);
            }
         }

         for (int _snowmanxxxxxxxxxxx = _snowman - this.B; _snowmanxxxxxxxxxxx <= _snowman + this.B; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = _snowmanx - this.B; _snowmanxxxxxxxxxxxx <= _snowmanx + this.B; _snowmanxxxxxxxxxxxx++) {
               brd _snowmanxxxxxxxxxxxxx = new brd(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
               boolean _snowmanxxxxxxxxxxxxxx = false;
               boolean _snowmanxxxxxxxxxxxxxxx = true;
               this.a(_snowman, _snowmanxxxxxxxxxxxxx, new oj[2], false, true);
            }
         }
      }
   }

   @Override
   public Stream<aah> a(brd var1, boolean var2) {
      return this.x.a(_snowman.a()).filter(var3 -> {
         int _snowman = b(_snowman, var3, true);
         return _snowman > this.B ? false : !_snowman || _snowman == this.B;
      });
   }

   protected void a(aqa var1) {
      if (!(_snowman instanceof bbp)) {
         aqe<?> _snowman = _snowman.X();
         int _snowmanx = _snowman.m() * 16;
         int _snowmanxx = _snowman.n();
         if (this.y.containsKey(_snowman.Y())) {
            throw (IllegalStateException)x.c(new IllegalStateException("Entity is already tracked!"));
         } else {
            zs.b _snowmanxxx = new zs.b(_snowman, _snowmanx, _snowmanxx, _snowman.o());
            this.y.put(_snowman.Y(), _snowmanxxx);
            _snowmanxxx.a(this.h.x());
            if (_snowman instanceof aah) {
               aah _snowmanxxxx = (aah)_snowman;
               this.a(_snowmanxxxx, true);
               ObjectIterator var7 = this.y.values().iterator();

               while (var7.hasNext()) {
                  zs.b _snowmanxxxxx = (zs.b)var7.next();
                  if (_snowmanxxxxx.c != _snowmanxxxx) {
                     _snowmanxxxxx.b(_snowmanxxxx);
                  }
               }
            }
         }
      }
   }

   protected void b(aqa var1) {
      if (_snowman instanceof aah) {
         aah _snowman = (aah)_snowman;
         this.a(_snowman, false);
         ObjectIterator var3 = this.y.values().iterator();

         while (var3.hasNext()) {
            zs.b _snowmanx = (zs.b)var3.next();
            _snowmanx.a(_snowman);
         }
      }

      zs.b _snowman = (zs.b)this.y.remove(_snowman.Y());
      if (_snowman != null) {
         _snowman.a();
      }
   }

   protected void g() {
      List<aah> _snowman = Lists.newArrayList();
      List<aah> _snowmanx = this.h.x();
      ObjectIterator var3 = this.y.values().iterator();

      while (var3.hasNext()) {
         zs.b _snowmanxx = (zs.b)var3.next();
         gp _snowmanxxx = _snowmanxx.e;
         gp _snowmanxxxx = gp.a(_snowmanxx.c);
         if (!Objects.equals(_snowmanxxx, _snowmanxxxx)) {
            _snowmanxx.a(_snowmanx);
            aqa _snowmanxxxxx = _snowmanxx.c;
            if (_snowmanxxxxx instanceof aah) {
               _snowman.add((aah)_snowmanxxxxx);
            }

            _snowmanxx.e = _snowmanxxxx;
         }

         _snowmanxx.b.a();
      }

      if (!_snowman.isEmpty()) {
         var3 = this.y.values().iterator();

         while (var3.hasNext()) {
            zs.b _snowmanxx = (zs.b)var3.next();
            _snowmanxx.a(_snowman);
         }
      }
   }

   protected void a(aqa var1, oj<?> var2) {
      zs.b _snowman = (zs.b)this.y.get(_snowman.Y());
      if (_snowman != null) {
         _snowman.a(_snowman);
      }
   }

   protected void b(aqa var1, oj<?> var2) {
      zs.b _snowman = (zs.b)this.y.get(_snowman.Y());
      if (_snowman != null) {
         _snowman.b(_snowman);
      }
   }

   private void a(aah var1, oj<?>[] var2, cgh var3) {
      if (_snowman[0] == null) {
         _snowman[0] = new pt(_snowman, 65535);
         _snowman[1] = new pw(_snowman.g(), this.i, true);
      }

      _snowman.a(_snowman.g(), _snowman[0], _snowman[1]);
      rz.a(this.h, _snowman.g());
      List<aqa> _snowman = Lists.newArrayList();
      List<aqa> _snowmanx = Lists.newArrayList();
      ObjectIterator var6 = this.y.values().iterator();

      while (var6.hasNext()) {
         zs.b _snowmanxx = (zs.b)var6.next();
         aqa _snowmanxxx = _snowmanxx.c;
         if (_snowmanxxx != _snowman && _snowmanxxx.V == _snowman.g().b && _snowmanxxx.X == _snowman.g().c) {
            _snowmanxx.b(_snowman);
            if (_snowmanxxx instanceof aqn && ((aqn)_snowmanxxx).eC() != null) {
               _snowman.add(_snowmanxxx);
            }

            if (!_snowmanxxx.cn().isEmpty()) {
               _snowmanx.add(_snowmanxxx);
            }
         }
      }

      if (!_snowman.isEmpty()) {
         for (aqa _snowmanxx : _snowman) {
            _snowman.b.a(new rb(_snowmanxx, ((aqn)_snowmanxx).eC()));
         }
      }

      if (!_snowmanx.isEmpty()) {
         for (aqa _snowmanxx : _snowmanx) {
            _snowman.b.a(new rh(_snowmanxx));
         }
      }
   }

   protected azo h() {
      return this.m;
   }

   public CompletableFuture<Void> a(cgh var1) {
      return this.j.f(() -> _snowman.a(this.h));
   }

   class a extends zy {
      protected a(Executor var2, Executor var3) {
         super(_snowman, _snowman);
      }

      @Override
      protected boolean a(long var1) {
         return zs.this.n.contains(_snowman);
      }

      @Nullable
      @Override
      protected zr b(long var1) {
         return zs.this.a(_snowman);
      }

      @Nullable
      @Override
      protected zr a(long var1, int var3, @Nullable zr var4, int var5) {
         return zs.this.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   class b {
      private final aaf b;
      private final aqa c;
      private final int d;
      private gp e;
      private final Set<aah> f = Sets.newHashSet();

      public b(aqa var2, int var3, int var4, boolean var5) {
         this.b = new aaf(zs.this.h, _snowman, _snowman, _snowman, this::a);
         this.c = _snowman;
         this.d = _snowman;
         this.e = gp.a(_snowman);
      }

      @Override
      public boolean equals(Object var1) {
         return _snowman instanceof zs.b ? ((zs.b)_snowman).c.Y() == this.c.Y() : false;
      }

      @Override
      public int hashCode() {
         return this.c.Y();
      }

      public void a(oj<?> var1) {
         for (aah _snowman : this.f) {
            _snowman.b.a(_snowman);
         }
      }

      public void b(oj<?> var1) {
         this.a(_snowman);
         if (this.c instanceof aah) {
            ((aah)this.c).b.a(_snowman);
         }
      }

      public void a() {
         for (aah _snowman : this.f) {
            this.b.a(_snowman);
         }
      }

      public void a(aah var1) {
         if (this.f.remove(_snowman)) {
            this.b.a(_snowman);
         }
      }

      public void b(aah var1) {
         if (_snowman != this.c) {
            dcn _snowman = _snowman.cA().d(this.b.b());
            int _snowmanx = Math.min(this.b(), (zs.this.B - 1) * 16);
            boolean _snowmanxx = _snowman.b >= (double)(-_snowmanx) && _snowman.b <= (double)_snowmanx && _snowman.d >= (double)(-_snowmanx) && _snowman.d <= (double)_snowmanx && this.c.a(_snowman);
            if (_snowmanxx) {
               boolean _snowmanxxx = this.c.k;
               if (!_snowmanxxx) {
                  brd _snowmanxxxx = new brd(this.c.V, this.c.X);
                  zr _snowmanxxxxx = zs.this.b(_snowmanxxxx.a());
                  if (_snowmanxxxxx != null && _snowmanxxxxx.d() != null) {
                     _snowmanxxx = zs.b(_snowmanxxxx, _snowman, false) <= zs.this.B;
                  }
               }

               if (_snowmanxxx && this.f.add(_snowman)) {
                  this.b.b(_snowman);
               }
            } else if (this.f.remove(_snowman)) {
               this.b.a(_snowman);
            }
         }
      }

      private int a(int var1) {
         return zs.this.h.l().b(_snowman);
      }

      private int b() {
         Collection<aqa> _snowman = this.c.co();
         int _snowmanx = this.d;

         for (aqa _snowmanxx : _snowman) {
            int _snowmanxxx = _snowmanxx.X().m() * 16;
            if (_snowmanxxx > _snowmanx) {
               _snowmanx = _snowmanxxx;
            }
         }

         return this.a(_snowmanx);
      }

      public void a(List<aah> var1) {
         for (aah _snowman : _snowman) {
            this.b(_snowman);
         }
      }
   }
}
