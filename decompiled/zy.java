import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class zy {
   private static final Logger a = LogManager.getLogger();
   private static final int b = 33 + cga.a(cga.m) - 2;
   private final Long2ObjectMap<ObjectSet<aah>> c = new Long2ObjectOpenHashMap();
   private final Long2ObjectOpenHashMap<afq<aak<?>>> d = new Long2ObjectOpenHashMap();
   private final zy.a e = new zy.a();
   private final zy.b f = new zy.b(8);
   private final zy.c g = new zy.c(33);
   private final Set<zr> h = Sets.newHashSet();
   private final zu i;
   private final aod<zu.a<Runnable>> j;
   private final aod<zu.b> k;
   private final LongSet l = new LongOpenHashSet();
   private final Executor m;
   private long n;

   protected zy(Executor var1, Executor var2) {
      aod<Runnable> _snowman = aod.a("player ticket throttler", _snowman::execute);
      zu _snowmanx = new zu(ImmutableList.of(_snowman), _snowman, 4);
      this.i = _snowmanx;
      this.j = _snowmanx.a(_snowman, true);
      this.k = _snowmanx.a(_snowman);
      this.m = _snowman;
   }

   protected void a() {
      this.n++;
      ObjectIterator<Entry<afq<aak<?>>>> _snowman = this.d.long2ObjectEntrySet().fastIterator();

      while (_snowman.hasNext()) {
         Entry<afq<aak<?>>> _snowmanx = (Entry<afq<aak<?>>>)_snowman.next();
         if (((afq)_snowmanx.getValue()).removeIf(var1x -> var1x.b(this.n))) {
            this.e.b(_snowmanx.getLongKey(), a((afq<aak<?>>)_snowmanx.getValue()), false);
         }

         if (((afq)_snowmanx.getValue()).isEmpty()) {
            _snowman.remove();
         }
      }
   }

   private static int a(afq<aak<?>> var0) {
      return !_snowman.isEmpty() ? _snowman.b().b() : zs.a + 1;
   }

   protected abstract boolean a(long var1);

   @Nullable
   protected abstract zr b(long var1);

   @Nullable
   protected abstract zr a(long var1, int var3, @Nullable zr var4, int var5);

   public boolean a(zs var1) {
      this.f.a();
      this.g.a();
      int _snowman = Integer.MAX_VALUE - this.e.a(Integer.MAX_VALUE);
      boolean _snowmanx = _snowman != 0;
      if (_snowmanx) {
      }

      if (!this.h.isEmpty()) {
         this.h.forEach(var1x -> var1x.a(_snowman));
         this.h.clear();
         return true;
      } else {
         if (!this.l.isEmpty()) {
            LongIterator _snowmanxx = this.l.iterator();

            while (_snowmanxx.hasNext()) {
               long _snowmanxxx = _snowmanxx.nextLong();
               if (this.e(_snowmanxxx).stream().anyMatch(var0 -> var0.a() == aal.c)) {
                  zr _snowmanxxxx = _snowman.a(_snowmanxxx);
                  if (_snowmanxxxx == null) {
                     throw new IllegalStateException();
                  }

                  CompletableFuture<Either<cgh, zr.a>> _snowmanxxxxx = _snowmanxxxx.b();
                  _snowmanxxxxx.thenAccept(var3x -> this.m.execute(() -> this.k.a(zu.a(() -> {
                        }, _snowman, false))));
               }
            }

            this.l.clear();
         }

         return _snowmanx;
      }
   }

   private void a(long var1, aak<?> var3) {
      afq<aak<?>> _snowman = this.e(_snowman);
      int _snowmanx = a(_snowman);
      aak<?> _snowmanxx = _snowman.a(_snowman);
      _snowmanxx.a(this.n);
      if (_snowman.b() < _snowmanx) {
         this.e.b(_snowman, _snowman.b(), true);
      }
   }

   private void b(long var1, aak<?> var3) {
      afq<aak<?>> _snowman = this.e(_snowman);
      if (_snowman.remove(_snowman)) {
      }

      if (_snowman.isEmpty()) {
         this.d.remove(_snowman);
      }

      this.e.b(_snowman, a(_snowman), false);
   }

   public <T> void a(aal<T> var1, brd var2, int var3, T var4) {
      this.a(_snowman.a(), new aak<>(_snowman, _snowman, _snowman));
   }

   public <T> void b(aal<T> var1, brd var2, int var3, T var4) {
      aak<T> _snowman = new aak<>(_snowman, _snowman, _snowman);
      this.b(_snowman.a(), _snowman);
   }

   public <T> void c(aal<T> var1, brd var2, int var3, T var4) {
      this.a(_snowman.a(), new aak<>(_snowman, 33 - _snowman, _snowman));
   }

   public <T> void d(aal<T> var1, brd var2, int var3, T var4) {
      aak<T> _snowman = new aak<>(_snowman, 33 - _snowman, _snowman);
      this.b(_snowman.a(), _snowman);
   }

   private afq<aak<?>> e(long var1) {
      return (afq<aak<?>>)this.d.computeIfAbsent(_snowman, var0 -> afq.a(4));
   }

   protected void a(brd var1, boolean var2) {
      aak<brd> _snowman = new aak<>(aal.d, 31, _snowman);
      if (_snowman) {
         this.a(_snowman.a(), _snowman);
      } else {
         this.b(_snowman.a(), _snowman);
      }
   }

   public void a(gp var1, aah var2) {
      long _snowman = _snowman.r().a();
      ((ObjectSet)this.c.computeIfAbsent(_snowman, var0 -> new ObjectOpenHashSet())).add(_snowman);
      this.f.b(_snowman, 0, true);
      this.g.b(_snowman, 0, true);
   }

   public void b(gp var1, aah var2) {
      long _snowman = _snowman.r().a();
      ObjectSet<aah> _snowmanx = (ObjectSet<aah>)this.c.get(_snowman);
      _snowmanx.remove(_snowman);
      if (_snowmanx.isEmpty()) {
         this.c.remove(_snowman);
         this.f.b(_snowman, Integer.MAX_VALUE, false);
         this.g.b(_snowman, Integer.MAX_VALUE, false);
      }
   }

   protected String c(long var1) {
      afq<aak<?>> _snowman = (afq<aak<?>>)this.d.get(_snowman);
      String _snowmanx;
      if (_snowman != null && !_snowman.isEmpty()) {
         _snowmanx = _snowman.b().toString();
      } else {
         _snowmanx = "no_ticket";
      }

      return _snowmanx;
   }

   protected void a(int var1) {
      this.g.a(_snowman);
   }

   public int b() {
      this.f.a();
      return this.f.a.size();
   }

   public boolean d(long var1) {
      this.f.a();
      return this.f.a.containsKey(_snowman);
   }

   public String c() {
      return this.i.a();
   }

   class a extends zv {
      public a() {
         super(zs.a + 2, 16, 256);
      }

      @Override
      protected int b(long var1) {
         afq<aak<?>> _snowman = (afq<aak<?>>)zy.this.d.get(_snowman);
         if (_snowman == null) {
            return Integer.MAX_VALUE;
         } else {
            return _snowman.isEmpty() ? Integer.MAX_VALUE : _snowman.b().b();
         }
      }

      @Override
      protected int c(long var1) {
         if (!zy.this.a(_snowman)) {
            zr _snowman = zy.this.b(_snowman);
            if (_snowman != null) {
               return _snowman.j();
            }
         }

         return zs.a + 1;
      }

      @Override
      protected void a(long var1, int var3) {
         zr _snowman = zy.this.b(_snowman);
         int _snowmanx = _snowman == null ? zs.a + 1 : _snowman.j();
         if (_snowmanx != _snowman) {
            _snowman = zy.this.a(_snowman, _snowman, _snowman, _snowmanx);
            if (_snowman != null) {
               zy.this.h.add(_snowman);
            }
         }
      }

      public int a(int var1) {
         return this.b(_snowman);
      }
   }

   class b extends zv {
      protected final Long2ByteMap a = new Long2ByteOpenHashMap();
      protected final int b;

      protected b(int var2) {
         super(_snowman + 2, 16, 256);
         this.b = _snowman;
         this.a.defaultReturnValue((byte)(_snowman + 2));
      }

      @Override
      protected int c(long var1) {
         return this.a.get(_snowman);
      }

      @Override
      protected void a(long var1, int var3) {
         byte _snowman;
         if (_snowman > this.b) {
            _snowman = this.a.remove(_snowman);
         } else {
            _snowman = this.a.put(_snowman, (byte)_snowman);
         }

         this.a(_snowman, _snowman, _snowman);
      }

      @Override
      protected void a(long var1, int var3, int var4) {
      }

      @Override
      protected int b(long var1) {
         return this.d(_snowman) ? 0 : Integer.MAX_VALUE;
      }

      private boolean d(long var1) {
         ObjectSet<aah> _snowman = (ObjectSet<aah>)zy.this.c.get(_snowman);
         return _snowman != null && !_snowman.isEmpty();
      }

      public void a() {
         this.b(Integer.MAX_VALUE);
      }
   }

   class c extends zy.b {
      private int e;
      private final Long2IntMap f = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
      private final LongSet g = new LongOpenHashSet();

      protected c(int var2) {
         super(_snowman);
         this.e = 0;
         this.f.defaultReturnValue(_snowman + 2);
      }

      @Override
      protected void a(long var1, int var3, int var4) {
         this.g.add(_snowman);
      }

      @Override
      public void a(int var1) {
         ObjectIterator var2 = this.a.long2ByteEntrySet().iterator();

         while (var2.hasNext()) {
            it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry _snowman = (it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry)var2.next();
            byte _snowmanx = _snowman.getByteValue();
            long _snowmanxx = _snowman.getLongKey();
            this.a(_snowmanxx, _snowmanx, this.c(_snowmanx), _snowmanx <= _snowman - 2);
         }

         this.e = _snowman;
      }

      private void a(long var1, int var3, boolean var4, boolean var5) {
         if (_snowman != _snowman) {
            aak<?> _snowman = new aak<>(aal.c, zy.b, new brd(_snowman));
            if (_snowman) {
               zy.this.j.a(zu.a(() -> zy.this.m.execute(() -> {
                     if (this.c(this.c(_snowman))) {
                        zy.this.a(_snowman, _snowman);
                        zy.this.l.add(_snowman);
                     } else {
                        zy.this.k.a(zu.a(() -> {
                        }, _snowman, false));
                     }
                  }), _snowman, () -> _snowman));
            } else {
               zy.this.k.a(zu.a(() -> zy.this.m.execute(() -> zy.this.b(_snowman, _snowman)), _snowman, true));
            }
         }
      }

      @Override
      public void a() {
         super.a();
         if (!this.g.isEmpty()) {
            LongIterator _snowman = this.g.iterator();

            while (_snowman.hasNext()) {
               long _snowmanx = _snowman.nextLong();
               int _snowmanxx = this.f.get(_snowmanx);
               int _snowmanxxx = this.c(_snowmanx);
               if (_snowmanxx != _snowmanxxx) {
                  zy.this.i.a(new brd(_snowmanx), () -> this.f.get(_snowman), _snowmanxxx, var3 -> {
                     if (var3 >= this.f.defaultReturnValue()) {
                        this.f.remove(_snowman);
                     } else {
                        this.f.put(_snowman, var3);
                     }
                  });
                  this.a(_snowmanx, _snowmanxxx, this.c(_snowmanxx), this.c(_snowmanxxx));
               }
            }

            this.g.clear();
         }
      }

      private boolean c(int var1) {
         return _snowman <= this.e - 2;
      }
   }
}
