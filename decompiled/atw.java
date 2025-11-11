import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class atw<E extends aqn> extends arv<E> {
   private final Predicate<E> b;
   private final Function<E, Optional<? extends aqm>> c;

   public atw(Predicate<E> var1, Function<E, Optional<? extends aqm>> var2) {
      super(ImmutableMap.of(ayd.o, aye.b, ayd.D, aye.c));
      this.b = _snowman;
      this.c = _snowman;
   }

   public atw(Function<E, Optional<? extends aqm>> var1) {
      this(var0 -> true, _snowman);
   }

   protected boolean a(aag var1, E var2) {
      if (!this.b.test(_snowman)) {
         return false;
      } else {
         Optional<? extends aqm> _snowman = this.c.apply(_snowman);
         return _snowman.isPresent() && _snowman.get().aX();
      }
   }

   protected void a(aag var1, E var2, long var3) {
      this.c.apply(_snowman).ifPresent(var2x -> this.a(_snowman, var2x));
   }

   private void a(E var1, aqm var2) {
      _snowman.cJ().a(ayd.o, _snowman);
      _snowman.cJ().b(ayd.D);
   }
}
