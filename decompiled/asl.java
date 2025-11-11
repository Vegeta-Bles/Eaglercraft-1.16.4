import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;

public class asl<E extends aqm> extends arv<E> {
   private final Predicate<E> b;
   private final int c;
   private final float d;

   public asl(float var1, boolean var2, int var3) {
      this(var0 -> true, _snowman, _snowman, _snowman);
   }

   public asl(Predicate<E> var1, float var2, boolean var3, int var4) {
      super(ImmutableMap.of(ayd.n, aye.c, ayd.m, _snowman ? aye.c : aye.b, ayd.J, aye.a));
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   protected boolean a(aag var1, E var2) {
      return this.b.test(_snowman) && this.a(_snowman).a(_snowman, (double)this.c);
   }

   @Override
   protected void a(aag var1, E var2, long var3) {
      arw.a(_snowman, this.a(_snowman), this.d, 0);
   }

   private bcv a(E var1) {
      return _snowman.cJ().c(ayd.J).get();
   }
}
