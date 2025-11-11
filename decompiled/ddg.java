public final class ddg extends dcw {
   private final dcw d;
   private final int e;
   private final int f;
   private final int g;
   private final int h;
   private final int i;
   private final int j;

   protected ddg(dcw var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(_snowman - _snowman, _snowman - _snowman, _snowman - _snowman);
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
   }

   @Override
   public boolean b(int var1, int var2, int var3) {
      return this.d.b(this.e + _snowman, this.f + _snowman, this.g + _snowman);
   }

   @Override
   public void a(int var1, int var2, int var3, boolean var4, boolean var5) {
      this.d.a(this.e + _snowman, this.f + _snowman, this.g + _snowman, _snowman, _snowman);
   }

   @Override
   public int a(gc.a var1) {
      return Math.max(0, this.d.a(_snowman) - _snowman.a(this.e, this.f, this.g));
   }

   @Override
   public int b(gc.a var1) {
      return Math.min(_snowman.a(this.h, this.i, this.j), this.d.b(_snowman) - _snowman.a(this.e, this.f, this.g));
   }
}
