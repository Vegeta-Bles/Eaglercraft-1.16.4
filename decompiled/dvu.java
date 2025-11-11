import com.google.common.collect.ImmutableList;

public class dvu<T extends aqa> extends dur<T> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;

   public dvu(int var1) {
      this.a = new dwn(this, 0, _snowman);
      this.b = new dwn(this, 32, 0);
      this.f = new dwn(this, 32, 4);
      this.g = new dwn(this, 32, 8);
      if (_snowman > 0) {
         this.a.a(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F);
         this.b.a(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
         this.f.a(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
         this.g.a(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F);
      } else {
         this.a.a(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      }
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.b, this.f, this.g);
   }
}
