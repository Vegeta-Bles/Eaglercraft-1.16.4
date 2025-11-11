import com.google.common.collect.ImmutableList;

public class dvp<T extends aqa> extends dur<T> {
   private final dwn a;

   public dvp() {
      this.r = 64;
      this.s = 32;
      this.a = new dwn(this);
      this.a.a(0, 0).a(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F, 0.0F);
      this.a.a(0, 10).a(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, 0.0F);
      this.a.a(20, 0).a(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, 0.0F);
      this.a.a(0.0F, 0.0F, 0.0F);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.a.e = _snowman * (float) (Math.PI / 180.0);
      this.a.d = _snowman * (float) (Math.PI / 180.0);
   }
}
