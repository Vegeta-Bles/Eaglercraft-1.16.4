import com.google.common.collect.ImmutableList;

public class duq<T extends aqa> extends dur<T> {
   private final dwn a;

   public duq() {
      this.r = 32;
      this.s = 32;
      this.a = new dwn(this, 0, 0);
      this.a.a(-3.0F, -6.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F);
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
