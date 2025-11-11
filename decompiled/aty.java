import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Predicate;

public class aty<E extends aqn> extends arv<E> {
   private final Predicate<aqm> b;

   public aty(Predicate<aqm> var1) {
      super(ImmutableMap.of(ayd.o, aye.a, ayd.D, aye.c));
      this.b = _snowman;
   }

   public aty() {
      this(var0 -> false);
   }

   protected void a(aag var1, E var2, long var3) {
      if (a(_snowman)) {
         this.d(_snowman);
      } else if (this.c(_snowman)) {
         this.d(_snowman);
      } else if (this.a(_snowman)) {
         this.d(_snowman);
      } else if (!aqd.f.test(this.b(_snowman))) {
         this.d(_snowman);
      } else if (this.b.test(this.b(_snowman))) {
         this.d(_snowman);
      }
   }

   private boolean a(E var1) {
      return this.b(_snowman).l != _snowman.l;
   }

   private aqm b(E var1) {
      return _snowman.cJ().c(ayd.o).get();
   }

   private static <E extends aqm> boolean a(E var0) {
      Optional<Long> _snowman = _snowman.cJ().c(ayd.D);
      return _snowman.isPresent() && _snowman.l.T() - _snowman.get() > 200L;
   }

   private boolean c(E var1) {
      Optional<aqm> _snowman = _snowman.cJ().c(ayd.o);
      return _snowman.isPresent() && !_snowman.get().aX();
   }

   private void d(E var1) {
      _snowman.cJ().b(ayd.o);
   }
}
