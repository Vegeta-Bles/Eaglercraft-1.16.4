import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cel {
   private final brz a;
   private final fx b;
   private final boolean c;
   private ceh d;
   private ccj e;
   private boolean f;

   public cel(brz var1, fx var2, boolean var3) {
      this.a = _snowman;
      this.b = _snowman.h();
      this.c = _snowman;
   }

   public ceh a() {
      if (this.d == null && (this.c || this.a.C(this.b))) {
         this.d = this.a.d_(this.b);
      }

      return this.d;
   }

   @Nullable
   public ccj b() {
      if (this.e == null && !this.f) {
         this.e = this.a.c(this.b);
         this.f = true;
      }

      return this.e;
   }

   public brz c() {
      return this.a;
   }

   public fx d() {
      return this.b;
   }

   public static Predicate<cel> a(Predicate<ceh> var0) {
      return var1 -> var1 != null && _snowman.test(var1.a());
   }
}
