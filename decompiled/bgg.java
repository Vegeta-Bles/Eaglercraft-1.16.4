public abstract class bgg extends bgb implements bgj {
   private static final us<bmb> e = uv.a(bgg.class, uu.g);

   public bgg(aqe<? extends bgg> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgg(aqe<? extends bgg> var1, double var2, double var4, double var6, double var8, double var10, double var12, brx var14) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public bgg(aqe<? extends bgg> var1, aqm var2, double var3, double var5, double var7, brx var9) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void b(bmb var1) {
      if (_snowman.b() != bmd.oS || _snowman.n()) {
         this.ab().b(e, x.a(_snowman.i(), var0 -> var0.e(1)));
      }
   }

   protected bmb k() {
      return this.ab().a(e);
   }

   @Override
   public bmb g() {
      bmb _snowman = this.k();
      return _snowman.a() ? new bmb(bmd.oS) : _snowman;
   }

   @Override
   protected void e() {
      this.ab().a(e, bmb.b);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      bmb _snowman = this.k();
      if (!_snowman.a()) {
         _snowman.a("Item", _snowman.b(new md()));
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      bmb _snowman = bmb.a(_snowman.p("Item"));
      this.b(_snowman);
   }
}
