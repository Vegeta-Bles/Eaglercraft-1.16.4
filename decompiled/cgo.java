import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class cgo<T> implements cgn<T> {
   private final cgm<T> b;
   private final cgn<T> c = (var0, var1x) -> 0;
   private final gh<T> d;
   private final Function<md, T> e;
   private final Function<T, md> f;
   private final T g;
   protected aer a;
   private cgm<T> h;
   private int i;
   private final ReentrantLock j = new ReentrantLock();

   public void a() {
      if (this.j.isLocked() && !this.j.isHeldByCurrentThread()) {
         String _snowman = Thread.getAllStackTraces()
            .keySet()
            .stream()
            .filter(Objects::nonNull)
            .map(var0 -> var0.getName() + ": \n\tat " + Arrays.stream(var0.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n\tat ")))
            .collect(Collectors.joining("\n"));
         l _snowmanx = new l("Writing into PalettedContainer from multiple threads", new IllegalStateException());
         m _snowmanxx = _snowmanx.a("Thread dumps");
         _snowmanxx.a("Thread dumps", _snowman);
         throw new u(_snowmanx);
      } else {
         this.j.lock();
      }
   }

   public void b() {
      this.j.unlock();
   }

   public cgo(cgm<T> var1, gh<T> var2, Function<md, T> var3, Function<T, md> var4, T var5) {
      this.b = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.b(4);
   }

   private static int b(int var0, int var1, int var2) {
      return _snowman << 8 | _snowman << 4 | _snowman;
   }

   private void b(int var1) {
      if (_snowman != this.i) {
         this.i = _snowman;
         if (this.i <= 4) {
            this.i = 4;
            this.h = new cgk<>(this.d, this.i, this, this.e);
         } else if (this.i < 9) {
            this.h = new cgf<>(this.d, this.i, this, this.e, this.f);
         } else {
            this.h = this.b;
            this.i = afm.e(this.d.a());
         }

         this.h.a(this.g);
         this.a = new aer(this.i, 4096);
      }
   }

   @Override
   public int onResize(int var1, T var2) {
      this.a();
      aer _snowman = this.a;
      cgm<T> _snowmanx = this.h;
      this.b(_snowman);

      for (int _snowmanxx = 0; _snowmanxx < _snowman.b(); _snowmanxx++) {
         T _snowmanxxx = _snowmanx.a(_snowman.a(_snowmanxx));
         if (_snowmanxxx != null) {
            this.b(_snowmanxx, _snowmanxxx);
         }
      }

      int _snowmanxxx = this.h.a(_snowman);
      this.b();
      return _snowmanxxx;
   }

   public T a(int var1, int var2, int var3, T var4) {
      this.a();
      T _snowman = this.a(b(_snowman, _snowman, _snowman), _snowman);
      this.b();
      return _snowman;
   }

   public T b(int var1, int var2, int var3, T var4) {
      return this.a(b(_snowman, _snowman, _snowman), _snowman);
   }

   protected T a(int var1, T var2) {
      int _snowman = this.h.a(_snowman);
      int _snowmanx = this.a.a(_snowman, _snowman);
      T _snowmanxx = this.h.a(_snowmanx);
      return _snowmanxx == null ? this.g : _snowmanxx;
   }

   protected void b(int var1, T var2) {
      int _snowman = this.h.a(_snowman);
      this.a.b(_snowman, _snowman);
   }

   public T a(int var1, int var2, int var3) {
      return this.a(b(_snowman, _snowman, _snowman));
   }

   protected T a(int var1) {
      T _snowman = this.h.a(this.a.a(_snowman));
      return _snowman == null ? this.g : _snowman;
   }

   public void a(nf var1) {
      this.a();
      int _snowman = _snowman.readByte();
      if (this.i != _snowman) {
         this.b(_snowman);
      }

      this.h.a(_snowman);
      _snowman.b(this.a.a());
      this.b();
   }

   public void b(nf var1) {
      this.a();
      _snowman.writeByte(this.i);
      this.h.b(_snowman);
      _snowman.a(this.a.a());
      this.b();
   }

   public void a(mj var1, long[] var2) {
      this.a();
      int _snowman = Math.max(4, afm.e(_snowman.size()));
      if (_snowman != this.i) {
         this.b(_snowman);
      }

      this.h.a(_snowman);
      int _snowmanx = _snowman.length * 64 / 4096;
      if (this.h == this.b) {
         cgm<T> _snowmanxx = new cgf<>(this.d, _snowman, this.c, this.e, this.f);
         _snowmanxx.a(_snowman);
         aer _snowmanxxx = new aer(_snowman, 4096, _snowman);

         for (int _snowmanxxxx = 0; _snowmanxxxx < 4096; _snowmanxxxx++) {
            this.a.b(_snowmanxxxx, this.b.a(_snowmanxx.a(_snowmanxxx.a(_snowmanxxxx))));
         }
      } else if (_snowmanx == this.i) {
         System.arraycopy(_snowman, 0, this.a.a(), 0, _snowman.length);
      } else {
         aer _snowmanxx = new aer(_snowmanx, 4096, _snowman);

         for (int _snowmanxxx = 0; _snowmanxxx < 4096; _snowmanxxx++) {
            this.a.b(_snowmanxxx, _snowmanxx.a(_snowmanxxx));
         }
      }

      this.b();
   }

   public void a(md var1, String var2, String var3) {
      this.a();
      cgf<T> _snowman = new cgf<>(this.d, this.i, this.c, this.e, this.f);
      T _snowmanx = this.g;
      int _snowmanxx = _snowman.a(this.g);
      int[] _snowmanxxx = new int[4096];

      for (int _snowmanxxxx = 0; _snowmanxxxx < 4096; _snowmanxxxx++) {
         T _snowmanxxxxx = this.a(_snowmanxxxx);
         if (_snowmanxxxxx != _snowmanx) {
            _snowmanx = _snowmanxxxxx;
            _snowmanxx = _snowman.a(_snowmanxxxxx);
         }

         _snowmanxxx[_snowmanxxxx] = _snowmanxx;
      }

      mj _snowmanxxxx = new mj();
      _snowman.b(_snowmanxxxx);
      _snowman.a(_snowman, _snowmanxxxx);
      int _snowmanxxxxx = Math.max(4, afm.e(_snowmanxxxx.size()));
      aer _snowmanxxxxxx = new aer(_snowmanxxxxx, 4096);

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxx.length; _snowmanxxxxxxx++) {
         _snowmanxxxxxx.b(_snowmanxxxxxxx, _snowmanxxx[_snowmanxxxxxxx]);
      }

      _snowman.a(_snowman, _snowmanxxxxxx.a());
      this.b();
   }

   public int c() {
      return 1 + this.h.a() + nf.a(this.a.b()) + this.a.a().length * 8;
   }

   public boolean a(Predicate<T> var1) {
      return this.h.a(_snowman);
   }

   public void a(cgo.a<T> var1) {
      Int2IntMap _snowman = new Int2IntOpenHashMap();
      this.a.a(var1x -> _snowman.put(var1x, _snowman.get(var1x) + 1));
      _snowman.int2IntEntrySet().forEach(var2x -> _snowman.accept(this.h.a(var2x.getIntKey()), var2x.getIntValue()));
   }

   @FunctionalInterface
   public interface a<T> {
      void accept(T var1, int var2);
   }
}
