import java.util.function.Predicate;
import javax.annotation.Nullable;

public class axi<T extends aqn> extends avv {
   private final T a;
   private final bmb b;
   private final Predicate<? super T> c;
   private final adp d;

   public axi(T var1, bmb var2, @Nullable adp var3, Predicate<? super T> var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.d = _snowman;
      this.c = _snowman;
   }

   @Override
   public boolean a() {
      return this.c.test(this.a);
   }

   @Override
   public boolean b() {
      return this.a.dW();
   }

   @Override
   public void c() {
      this.a.a(aqf.a, this.b.i());
      this.a.c(aot.a);
   }

   @Override
   public void d() {
      this.a.a(aqf.a, bmb.b);
      if (this.d != null) {
         this.a.a(this.d, 1.0F, this.a.cY().nextFloat() * 0.2F + 0.9F);
      }
   }
}
