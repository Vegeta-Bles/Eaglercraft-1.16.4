import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public interface brg extends brc {
   cfu f();

   @Nullable
   brc c(int var1, int var2);

   default boolean a(@Nullable aqa var1, ddh var2) {
      return true;
   }

   default boolean a(ceh var1, fx var2, dcs var3) {
      ddh _snowman = _snowman.b(this, _snowman, _snowman);
      return _snowman.b() || this.a(null, _snowman.a((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w()));
   }

   default boolean j(aqa var1) {
      return this.a(_snowman, dde.a(_snowman.cc()));
   }

   default boolean b(dci var1) {
      return this.b(null, _snowman, var0 -> true);
   }

   default boolean k(aqa var1) {
      return this.b(_snowman, _snowman.cc(), var0 -> true);
   }

   default boolean a_(aqa var1, dci var2) {
      return this.b(_snowman, _snowman, var0 -> true);
   }

   default boolean b(@Nullable aqa var1, dci var2, Predicate<aqa> var3) {
      return this.d(_snowman, _snowman, _snowman).allMatch(ddh::b);
   }

   Stream<ddh> c(@Nullable aqa var1, dci var2, Predicate<aqa> var3);

   default Stream<ddh> d(@Nullable aqa var1, dci var2, Predicate<aqa> var3) {
      return Stream.concat(this.b(_snowman, _snowman), this.c(_snowman, _snowman, _snowman));
   }

   default Stream<ddh> b(@Nullable aqa var1, dci var2) {
      return StreamSupport.stream(new brh(this, _snowman, _snowman), false);
   }

   default boolean a(@Nullable aqa var1, dci var2, BiPredicate<ceh, fx> var3) {
      return this.b(_snowman, _snowman, _snowman).allMatch(ddh::b);
   }

   default Stream<ddh> b(@Nullable aqa var1, dci var2, BiPredicate<ceh, fx> var3) {
      return StreamSupport.stream(new brh(this, _snowman, _snowman, _snowman), false);
   }
}
