import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;

public class aet<K> implements gg<K> {
   private static final Object a = null;
   private K[] b;
   private int[] c;
   private K[] d;
   private int e;
   private int f;

   public aet(int var1) {
      _snowman = (int)((float)_snowman / 0.8F);
      this.b = (K[])(new Object[_snowman]);
      this.c = new int[_snowman];
      this.d = (K[])(new Object[_snowman]);
   }

   @Override
   public int a(@Nullable K var1) {
      return this.c(this.b(_snowman, this.d(_snowman)));
   }

   @Nullable
   @Override
   public K a(int var1) {
      return _snowman >= 0 && _snowman < this.d.length ? this.d[_snowman] : null;
   }

   private int c(int var1) {
      return _snowman == -1 ? -1 : this.c[_snowman];
   }

   public int c(K var1) {
      int _snowman = this.c();
      this.a(_snowman, _snowman);
      return _snowman;
   }

   private int c() {
      while (this.e < this.d.length && this.d[this.e] != null) {
         this.e++;
      }

      return this.e;
   }

   private void d(int var1) {
      K[] _snowman = this.b;
      int[] _snowmanx = this.c;
      this.b = (K[])(new Object[_snowman]);
      this.c = new int[_snowman];
      this.d = (K[])(new Object[_snowman]);
      this.e = 0;
      this.f = 0;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.length; _snowmanxx++) {
         if (_snowman[_snowmanxx] != null) {
            this.a(_snowman[_snowmanxx], _snowmanx[_snowmanxx]);
         }
      }
   }

   public void a(K var1, int var2) {
      int _snowman = Math.max(_snowman, this.f + 1);
      if ((float)_snowman >= (float)this.b.length * 0.8F) {
         int _snowmanx = this.b.length << 1;

         while (_snowmanx < _snowman) {
            _snowmanx <<= 1;
         }

         this.d(_snowmanx);
      }

      int _snowmanx = this.e(this.d(_snowman));
      this.b[_snowmanx] = _snowman;
      this.c[_snowmanx] = _snowman;
      this.d[_snowman] = _snowman;
      this.f++;
      if (_snowman == this.e) {
         this.e++;
      }
   }

   private int d(@Nullable K var1) {
      return (afm.g(System.identityHashCode(_snowman)) & 2147483647) % this.b.length;
   }

   private int b(@Nullable K var1, int var2) {
      for (int _snowman = _snowman; _snowman < this.b.length; _snowman++) {
         if (this.b[_snowman] == _snowman) {
            return _snowman;
         }

         if (this.b[_snowman] == a) {
            return -1;
         }
      }

      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         if (this.b[_snowman] == _snowman) {
            return _snowman;
         }

         if (this.b[_snowman] == a) {
            return -1;
         }
      }

      return -1;
   }

   private int e(int var1) {
      for (int _snowman = _snowman; _snowman < this.b.length; _snowman++) {
         if (this.b[_snowman] == a) {
            return _snowman;
         }
      }

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         if (this.b[_snowmanx] == a) {
            return _snowmanx;
         }
      }

      throw new RuntimeException("Overflowed :(");
   }

   @Override
   public Iterator<K> iterator() {
      return Iterators.filter(Iterators.forArray(this.d), Predicates.notNull());
   }

   public void a() {
      Arrays.fill(this.b, null);
      Arrays.fill(this.d, null);
      this.e = 0;
      this.f = 0;
   }

   public int b() {
      return this.f;
   }
}
