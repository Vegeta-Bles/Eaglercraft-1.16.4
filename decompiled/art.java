import com.google.common.collect.ImmutableMap;

public class art<E extends aqn> extends arv<E> {
   private final int b;
   private final float c;

   public art(int var1, float var2) {
      super(ImmutableMap.of(ayd.m, aye.b, ayd.n, aye.c, ayd.o, aye.a, ayd.h, aye.a));
      this.b = _snowman;
      this.c = _snowman;
   }

   protected boolean a(aag var1, E var2) {
      return this.a(_snowman) && this.b(_snowman);
   }

   protected void a(aag var1, E var2, long var3) {
      _snowman.cJ().a(ayd.n, new asd(this.c(_snowman), true));
      _snowman.u().a(-this.c, 0.0F);
      _snowman.p = afm.b(_snowman.p, _snowman.aC, 0.0F);
   }

   private boolean a(E var1) {
      return _snowman.cJ().c(ayd.h).get().contains(this.c(_snowman));
   }

   private boolean b(E var1) {
      return this.c(_snowman).a(_snowman, (double)this.b);
   }

   private aqm c(E var1) {
      return _snowman.cJ().c(ayd.o).get();
   }
}
