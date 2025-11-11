import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Predicate;

public class ath<E extends aqm> extends arv<E> {
   private final Predicate<E> b;
   private final arv<? super E> c;
   private final boolean d;

   public ath(Map<ayd<?>, aye> var1, Predicate<E> var2, arv<? super E> var3, boolean var4) {
      super(a(_snowman, _snowman.a));
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   private static Map<ayd<?>, aye> a(Map<ayd<?>, aye> var0, Map<ayd<?>, aye> var1) {
      Map<ayd<?>, aye> _snowman = Maps.newHashMap();
      _snowman.putAll(_snowman);
      _snowman.putAll(_snowman);
      return _snowman;
   }

   public ath(Predicate<E> var1, arv<? super E> var2) {
      this(ImmutableMap.of(), _snowman, _snowman, false);
   }

   @Override
   protected boolean a(aag var1, E var2) {
      return this.b.test(_snowman) && this.c.a(_snowman, _snowman);
   }

   @Override
   protected boolean b(aag var1, E var2, long var3) {
      return this.d && this.b.test(_snowman) && this.c.b(_snowman, _snowman, _snowman);
   }

   @Override
   protected boolean a(long var1) {
      return false;
   }

   @Override
   protected void a(aag var1, E var2, long var3) {
      this.c.a(_snowman, _snowman, _snowman);
   }

   @Override
   protected void d(aag var1, E var2, long var3) {
      this.c.d(_snowman, _snowman, _snowman);
   }

   @Override
   protected void c(aag var1, E var2, long var3) {
      this.c.c(_snowman, _snowman, _snowman);
   }

   @Override
   public String toString() {
      return "RunIf: " + this.c;
   }
}
