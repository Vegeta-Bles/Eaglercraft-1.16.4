import java.util.Arrays;

public class duu<T extends aqa> extends dur<T> {
   private final dwn[] a = new dwn[6];

   public duu() {
      this.a[0] = new dwn(this, 0, 10);
      this.a[1] = new dwn(this, 0, 0);
      this.a[2] = new dwn(this, 0, 0);
      this.a[3] = new dwn(this, 0, 0);
      this.a[4] = new dwn(this, 0, 0);
      this.a[5] = new dwn(this, 44, 10);
      int _snowman = 20;
      int _snowmanx = 8;
      int _snowmanxx = 16;
      int _snowmanxxx = 4;
      this.a[0].a(-10.0F, -8.0F, -1.0F, 20.0F, 16.0F, 2.0F, 0.0F);
      this.a[0].a(0.0F, 4.0F, 0.0F);
      this.a[5].a(-9.0F, -7.0F, -1.0F, 18.0F, 14.0F, 1.0F, 0.0F);
      this.a[5].a(0.0F, 4.0F, 0.0F);
      this.a[1].a(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F, 0.0F);
      this.a[1].a(-9.0F, 4.0F, 0.0F);
      this.a[2].a(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F, 0.0F);
      this.a[2].a(9.0F, 4.0F, 0.0F);
      this.a[3].a(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F, 0.0F);
      this.a[3].a(0.0F, 4.0F, -7.0F);
      this.a[4].a(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F, 0.0F);
      this.a[4].a(0.0F, 4.0F, 7.0F);
      this.a[0].d = (float) (Math.PI / 2);
      this.a[1].e = (float) (Math.PI * 3.0 / 2.0);
      this.a[2].e = (float) (Math.PI / 2);
      this.a[3].e = (float) Math.PI;
      this.a[5].d = (float) (-Math.PI / 2);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.a[5].b = 4.0F - _snowman;
   }

   @Override
   public Iterable<dwn> a() {
      return Arrays.asList(this.a);
   }
}
