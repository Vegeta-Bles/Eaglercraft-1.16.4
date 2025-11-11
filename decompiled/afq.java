import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class afq<T> extends AbstractSet<T> {
   private final Comparator<T> a;
   private T[] b;
   private int c;

   private afq(int var1, Comparator<T> var2) {
      this.a = _snowman;
      if (_snowman < 0) {
         throw new IllegalArgumentException("Initial capacity (" + _snowman + ") is negative");
      } else {
         this.b = (T[])a(new Object[_snowman]);
      }
   }

   public static <T extends Comparable<T>> afq<T> a(int var0) {
      return new afq<>(_snowman, Comparator.naturalOrder());
   }

   private static <T> T[] a(Object[] var0) {
      return (T[])_snowman;
   }

   private int c(T var1) {
      return Arrays.binarySearch(this.b, 0, this.c, _snowman, this.a);
   }

   private static int b(int var0) {
      return -_snowman - 1;
   }

   @Override
   public boolean add(T var1) {
      int _snowman = this.c(_snowman);
      if (_snowman >= 0) {
         return false;
      } else {
         int _snowmanx = b(_snowman);
         this.a(_snowman, _snowmanx);
         return true;
      }
   }

   private void c(int var1) {
      if (_snowman > this.b.length) {
         if (this.b != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            _snowman = (int)Math.max(Math.min((long)this.b.length + (long)(this.b.length >> 1), 2147483639L), (long)_snowman);
         } else if (_snowman < 10) {
            _snowman = 10;
         }

         Object[] _snowman = new Object[_snowman];
         System.arraycopy(this.b, 0, _snowman, 0, this.c);
         this.b = (T[])a(_snowman);
      }
   }

   private void a(T var1, int var2) {
      this.c(this.c + 1);
      if (_snowman != this.c) {
         System.arraycopy(this.b, _snowman, this.b, _snowman + 1, this.c - _snowman);
      }

      this.b[_snowman] = _snowman;
      this.c++;
   }

   private void d(int var1) {
      this.c--;
      if (_snowman != this.c) {
         System.arraycopy(this.b, _snowman + 1, this.b, _snowman, this.c - _snowman);
      }

      this.b[this.c] = null;
   }

   private T e(int var1) {
      return this.b[_snowman];
   }

   public T a(T var1) {
      int _snowman = this.c(_snowman);
      if (_snowman >= 0) {
         return this.e(_snowman);
      } else {
         this.a(_snowman, b(_snowman));
         return _snowman;
      }
   }

   @Override
   public boolean remove(Object var1) {
      int _snowman = this.c((T)_snowman);
      if (_snowman >= 0) {
         this.d(_snowman);
         return true;
      } else {
         return false;
      }
   }

   public T b() {
      return this.e(0);
   }

   @Override
   public boolean contains(Object var1) {
      int _snowman = this.c((T)_snowman);
      return _snowman >= 0;
   }

   @Override
   public Iterator<T> iterator() {
      return new afq.a();
   }

   @Override
   public int size() {
      return this.c;
   }

   @Override
   public Object[] toArray() {
      return (Object[])this.b.clone();
   }

   @Override
   public <U> U[] toArray(U[] var1) {
      if (_snowman.length < this.c) {
         return (U[])Arrays.copyOf(this.b, this.c, (Class<? extends T[]>)_snowman.getClass());
      } else {
         System.arraycopy(this.b, 0, _snowman, 0, this.c);
         if (_snowman.length > this.c) {
            _snowman[this.c] = null;
         }

         return _snowman;
      }
   }

   @Override
   public void clear() {
      Arrays.fill(this.b, 0, this.c, null);
      this.c = 0;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else {
         if (_snowman instanceof afq) {
            afq<?> _snowman = (afq<?>)_snowman;
            if (this.a.equals(_snowman.a)) {
               return this.c == _snowman.c && Arrays.equals(this.b, _snowman.b);
            }
         }

         return super.equals(_snowman);
      }
   }

   class a implements Iterator<T> {
      private int b;
      private int c = -1;

      private a() {
      }

      @Override
      public boolean hasNext() {
         return this.b < afq.this.c;
      }

      @Override
      public T next() {
         if (this.b >= afq.this.c) {
            throw new NoSuchElementException();
         } else {
            this.c = this.b++;
            return afq.this.b[this.c];
         }
      }

      @Override
      public void remove() {
         if (this.c == -1) {
            throw new IllegalStateException();
         } else {
            afq.this.d(this.c);
            this.b--;
            this.c = -1;
         }
      }
   }
}
