import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class nw extends nn {
   private static Function<String, Supplier<nr>> d = var0 -> () -> new oe(var0);
   private final String e;
   private Supplier<nr> f;

   public nw(String var1) {
      this.e = _snowman;
   }

   public static void a(Function<String, Supplier<nr>> var0) {
      d = _snowman;
   }

   private nr j() {
      if (this.f == null) {
         this.f = d.apply(this.e);
      }

      return this.f.get();
   }

   @Override
   public <T> Optional<T> b(nu.a<T> var1) {
      return this.j().a(_snowman);
   }

   @Override
   public <T> Optional<T> b(nu.b<T> var1, ob var2) {
      return this.j().a(_snowman, _snowman);
   }

   public nw h() {
      return new nw(this.e);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof nw)) {
         return false;
      } else {
         nw _snowman = (nw)_snowman;
         return this.e.equals(_snowman.e) && super.equals(_snowman);
      }
   }

   @Override
   public String toString() {
      return "KeybindComponent{keybind='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
   }

   public String i() {
      return this.e;
   }
}
