import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;

public class arz<E extends aqn, T> extends arv<E> {
   private final Predicate<E> b;
   private final ayd<? extends T> c;
   private final ayd<T> d;
   private final afh e;

   public arz(Predicate<E> var1, ayd<? extends T> var2, ayd<T> var3, afh var4) {
      super(ImmutableMap.of(_snowman, aye.a, _snowman, aye.b));
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   protected boolean a(aag var1, E var2) {
      return this.b.test(_snowman);
   }

   protected void a(aag var1, E var2, long var3) {
      arf<?> _snowman = _snowman.cJ();
      _snowman.a(this.d, (T)_snowman.c(this.c).get(), (long)this.e.a(_snowman.t));
   }
}
