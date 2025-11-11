import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cgf<T> implements cgm<T> {
   private final gh<T> a;
   private final aet<T> b;
   private final cgn<T> c;
   private final Function<md, T> d;
   private final Function<T, md> e;
   private final int f;

   public cgf(gh<T> var1, int var2, cgn<T> var3, Function<md, T> var4, Function<T, md> var5) {
      this.a = _snowman;
      this.f = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.b = new aet<>(1 << _snowman);
   }

   @Override
   public int a(T var1) {
      int _snowman = this.b.a(_snowman);
      if (_snowman == -1) {
         _snowman = this.b.c(_snowman);
         if (_snowman >= 1 << this.f) {
            _snowman = this.c.onResize(this.f + 1, _snowman);
         }
      }

      return _snowman;
   }

   @Override
   public boolean a(Predicate<T> var1) {
      for (int _snowman = 0; _snowman < this.b(); _snowman++) {
         if (_snowman.test(this.b.a(_snowman))) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   @Override
   public T a(int var1) {
      return this.b.a(_snowman);
   }

   @Override
   public void a(nf var1) {
      this.b.a();
      int _snowman = _snowman.i();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.b.c(this.a.a(_snowman.i()));
      }
   }

   @Override
   public void b(nf var1) {
      int _snowman = this.b();
      _snowman.d(_snowman);

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         _snowman.d(this.a.a(this.b.a(_snowmanx)));
      }
   }

   @Override
   public int a() {
      int _snowman = nf.a(this.b());

      for (int _snowmanx = 0; _snowmanx < this.b(); _snowmanx++) {
         _snowman += nf.a(this.a.a(this.b.a(_snowmanx)));
      }

      return _snowman;
   }

   public int b() {
      return this.b.b();
   }

   @Override
   public void a(mj var1) {
      this.b.a();

      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         this.b.c(this.d.apply(_snowman.a(_snowman)));
      }
   }

   public void b(mj var1) {
      for (int _snowman = 0; _snowman < this.b(); _snowman++) {
         _snowman.add(this.e.apply(this.b.a(_snowman)));
      }
   }
}
