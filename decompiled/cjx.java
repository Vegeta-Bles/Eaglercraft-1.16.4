import com.mojang.serialization.Codec;

public class cjx extends cla<cmc> {
   private final int u;
   private final boolean v;
   private final boolean w;

   public cjx(Codec<cmc> var1, int var2, boolean var3, boolean var4) {
      super(_snowman);
      this.u = _snowman;
      this.v = _snowman;
      this.w = _snowman;
   }

   @Override
   public cla.a<cmc> a() {
      return (var1, var2, var3, var4, var5, var6) -> new cjx.a(this, var2, var3, var4, var5, var6);
   }

   public static class a extends cqz<cmc> {
      private final cjx e;

      public a(cjx var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         this.e = _snowman;
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cmc var7) {
         fx _snowman = new fx(_snowman * 16, this.e.u, _snowman * 16);
         kk.a();
         coe.a(_snowman, _snowman, cro::new, _snowman, _snowman, _snowman, this.b, this.d, this.e.v, this.e.w);
         this.b();
      }
   }
}
