public class dvn<T extends bas> extends dvi<T> {
   private float j;

   public dvn() {
      super(12, 0.0F, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
      this.a = new dwn(this, 0, 0);
      this.a.a(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F, 0.0F);
      this.a.a(0.0F, 6.0F, -8.0F);
      this.b = new dwn(this, 28, 8);
      this.b.a(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, 0.0F);
      this.b.a(0.0F, 5.0F, 2.0F);
   }

   public void a(T var1, float var2, float var3, float var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.a.b = 6.0F + _snowman.y(_snowman) * 9.0F;
      this.j = _snowman.z(_snowman);
   }

   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.a.d = this.j;
   }
}
