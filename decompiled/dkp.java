import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.IntSupplier;

public class dkp {
   private final ThreadLocal<dkp.a> a = ThreadLocal.withInitial(() -> new dkp.a());
   private final Long2ObjectLinkedOpenHashMap<int[]> b = new Long2ObjectLinkedOpenHashMap(256, 0.25F);
   private final ReentrantReadWriteLock c = new ReentrantReadWriteLock();

   public dkp() {
   }

   public int a(fx var1, IntSupplier var2) {
      int _snowman = _snowman.u() >> 4;
      int _snowmanx = _snowman.w() >> 4;
      dkp.a _snowmanxx = this.a.get();
      if (_snowmanxx.a != _snowman || _snowmanxx.b != _snowmanx) {
         _snowmanxx.a = _snowman;
         _snowmanxx.b = _snowmanx;
         _snowmanxx.c = this.b(_snowman, _snowmanx);
      }

      int _snowmanxxx = _snowman.u() & 15;
      int _snowmanxxxx = _snowman.w() & 15;
      int _snowmanxxxxx = _snowmanxxxx << 4 | _snowmanxxx;
      int _snowmanxxxxxx = _snowmanxx.c[_snowmanxxxxx];
      if (_snowmanxxxxxx != -1) {
         return _snowmanxxxxxx;
      } else {
         int _snowmanxxxxxxx = _snowman.getAsInt();
         _snowmanxx.c[_snowmanxxxxx] = _snowmanxxxxxxx;
         return _snowmanxxxxxxx;
      }
   }

   public void a(int var1, int var2) {
      try {
         this.c.writeLock().lock();

         for (int _snowman = -1; _snowman <= 1; _snowman++) {
            for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
               long _snowmanxx = brd.a(_snowman + _snowman, _snowman + _snowmanx);
               this.b.remove(_snowmanxx);
            }
         }
      } finally {
         this.c.writeLock().unlock();
      }
   }

   public void a() {
      try {
         this.c.writeLock().lock();
         this.b.clear();
      } finally {
         this.c.writeLock().unlock();
      }
   }

   private int[] b(int var1, int var2) {
      long _snowman = brd.a(_snowman, _snowman);
      this.c.readLock().lock();

      int[] _snowmanx;
      try {
         _snowmanx = (int[])this.b.get(_snowman);
      } finally {
         this.c.readLock().unlock();
      }

      if (_snowmanx != null) {
         return _snowmanx;
      } else {
         int[] _snowmanxx = new int[256];
         Arrays.fill(_snowmanxx, -1);

         try {
            this.c.writeLock().lock();
            if (this.b.size() >= 256) {
               this.b.removeFirst();
            }

            this.b.put(_snowman, _snowmanxx);
         } finally {
            this.c.writeLock().unlock();
         }

         return _snowmanxx;
      }
   }

   static class a {
      public int a = Integer.MIN_VALUE;
      public int b = Integer.MIN_VALUE;
      public int[] c;

      private a() {
      }
   }
}
