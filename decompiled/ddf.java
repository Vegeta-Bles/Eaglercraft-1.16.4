import it.unimi.dsi.fastutil.doubles.DoubleList;

public class ddf extends ddh {
   private final ddh b;
   private final gc.a c;
   private static final DoubleList d = new dct(1);

   public ddf(ddh var1, gc.a var2, int var3) {
      super(a(_snowman.a, _snowman, _snowman));
      this.b = _snowman;
      this.c = _snowman;
   }

   private static dcw a(dcw var0, gc.a var1, int var2) {
      return new ddg(_snowman, _snowman.a(_snowman, 0, 0), _snowman.a(0, _snowman, 0), _snowman.a(0, 0, _snowman), _snowman.a(_snowman + 1, _snowman.a, _snowman.a), _snowman.a(_snowman.b, _snowman + 1, _snowman.b), _snowman.a(_snowman.c, _snowman.c, _snowman + 1));
   }

   @Override
   protected DoubleList a(gc.a var1) {
      return _snowman == this.c ? d : this.b.a(_snowman);
   }
}
