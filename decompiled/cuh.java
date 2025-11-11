import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

public class cuh extends cun<cuh.a> {
   protected cuh(cgj var1) {
      super(bsf.b, _snowman, new cuh.a(new Long2ObjectOpenHashMap()));
   }

   @Override
   protected int d(long var1) {
      long _snowman = gp.e(_snowman);
      cgb _snowmanx = this.a(_snowman, false);
      return _snowmanx == null ? 0 : _snowmanx.a(gp.b(fx.b(_snowman)), gp.b(fx.c(_snowman)), gp.b(fx.d(_snowman)));
   }

   public static final class a extends cui<cuh.a> {
      public a(Long2ObjectOpenHashMap<cgb> var1) {
         super(_snowman);
      }

      public cuh.a a() {
         return new cuh.a(this.a.clone());
      }
   }
}
