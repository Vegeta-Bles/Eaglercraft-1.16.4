import java.util.function.Supplier;

public class afi<T> {
   private Supplier<T> a;
   private T b;

   public afi(Supplier<T> var1) {
      this.a = _snowman;
   }

   public T a() {
      Supplier<T> _snowman = this.a;
      if (_snowman != null) {
         this.b = _snowman.get();
         this.a = null;
      }

      return this.b;
   }
}
