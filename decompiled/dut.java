import com.google.common.collect.ImmutableList;

public class dut<T extends aqa> extends dur<T> {
   private final dwn a = new dwn(this);

   public dut() {
      this(0.0F);
   }

   public dut(float var1) {
      int _snowman = 2;
      this.a.a(0, 0).a(-4.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, _snowman);
      this.a.a(0, 0).a(0.0F, -4.0F, 0.0F, 2.0F, 2.0F, 2.0F, _snowman);
      this.a.a(0, 0).a(0.0F, 0.0F, -4.0F, 2.0F, 2.0F, 2.0F, _snowman);
      this.a.a(0, 0).a(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, _snowman);
      this.a.a(0, 0).a(2.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, _snowman);
      this.a.a(0, 0).a(0.0F, 2.0F, 0.0F, 2.0F, 2.0F, 2.0F, _snowman);
      this.a.a(0, 0).a(0.0F, 0.0F, 2.0F, 2.0F, 2.0F, 2.0F, _snowman);
      this.a.a(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a);
   }
}
