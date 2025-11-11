import com.google.common.collect.ImmutableMap;

public class asw<E extends aqm> extends arv<E> {
   private final float b;

   public asw(float var1) {
      super(ImmutableMap.of(ayd.n, aye.c, ayd.m, aye.b, ayd.s, aye.a));
      this.b = _snowman;
   }

   @Override
   protected boolean a(aag var1, E var2) {
      return !_snowman.br();
   }

   @Override
   protected void a(aag var1, E var2, long var3) {
      if (this.a(_snowman)) {
         _snowman.m(this.b(_snowman));
      } else {
         arw.a(_snowman, this.b(_snowman), this.b, 1);
      }
   }

   private boolean a(E var1) {
      return this.b(_snowman).a(_snowman, 1.0);
   }

   private aqa b(E var1) {
      return _snowman.cJ().c(ayd.s).get();
   }
}
