import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cgk<T> implements cgm<T> {
   private final gh<T> a;
   private final T[] b;
   private final cgn<T> c;
   private final Function<md, T> d;
   private final int e;
   private int f;

   public cgk(gh<T> var1, int var2, cgn<T> var3, Function<md, T> var4) {
      this.a = _snowman;
      this.b = (T[])(new Object[1 << _snowman]);
      this.e = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public int a(T var1) {
      for (int _snowman = 0; _snowman < this.f; _snowman++) {
         if (this.b[_snowman] == _snowman) {
            return _snowman;
         }
      }

      int _snowmanx = this.f;
      if (_snowmanx < this.b.length) {
         this.b[_snowmanx] = _snowman;
         this.f++;
         return _snowmanx;
      } else {
         return this.c.onResize(this.e + 1, _snowman);
      }
   }

   @Override
   public boolean a(Predicate<T> var1) {
      for (int _snowman = 0; _snowman < this.f; _snowman++) {
         if (_snowman.test(this.b[_snowman])) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   @Override
   public T a(int var1) {
      return _snowman >= 0 && _snowman < this.f ? this.b[_snowman] : null;
   }

   @Override
   public void a(nf var1) {
      this.f = _snowman.i();

      for (int _snowman = 0; _snowman < this.f; _snowman++) {
         this.b[_snowman] = this.a.a(_snowman.i());
      }
   }

   @Override
   public void b(nf var1) {
      _snowman.d(this.f);

      for (int _snowman = 0; _snowman < this.f; _snowman++) {
         _snowman.d(this.a.a(this.b[_snowman]));
      }
   }

   @Override
   public int a() {
      int _snowman = nf.a(this.b());

      for (int _snowmanx = 0; _snowmanx < this.b(); _snowmanx++) {
         _snowman += nf.a(this.a.a(this.b[_snowmanx]));
      }

      return _snowman;
   }

   public int b() {
      return this.f;
   }

   @Override
   public void a(mj var1) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         this.b[_snowman] = this.d.apply(_snowman.a(_snowman));
      }

      this.f = _snowman.size();
   }
}
