import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface bri extends bro, brz, bsb {
   @Override
   default Stream<ddh> c(@Nullable aqa var1, dci var2, Predicate<aqa> var3) {
      return bro.super.c(_snowman, _snowman, _snowman);
   }

   @Override
   default boolean a(@Nullable aqa var1, ddh var2) {
      return bro.super.a(_snowman, _snowman);
   }

   @Override
   default fx a(chn.a var1, fx var2) {
      return brz.super.a(_snowman, _snowman);
   }

   gn r();

   default Optional<vj<bsv>> i(fx var1) {
      return this.r().b(gm.ay).c(this.v(_snowman));
   }
}
