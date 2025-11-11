import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;

public class ase<E extends aqm> extends arv<E> {
   private final Predicate<E> b;
   private final ayd<?> c;

   public ase(Predicate<E> var1, ayd<?> var2) {
      super(ImmutableMap.of(_snowman, aye.a));
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   protected boolean a(aag var1, E var2) {
      return this.b.test(_snowman);
   }

   @Override
   protected void a(aag var1, E var2, long var3) {
      _snowman.cJ().b(this.c);
   }
}
