public abstract class bgs extends bgt implements bgj {
   private static final us<bmb> b = uv.a(bgs.class, uu.g);

   public bgs(aqe<? extends bgs> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgs(aqe<? extends bgs> var1, double var2, double var4, double var6, brx var8) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public bgs(aqe<? extends bgs> var1, aqm var2, brx var3) {
      super(_snowman, _snowman, _snowman);
   }

   public void b(bmb var1) {
      if (_snowman.b() != this.h() || _snowman.n()) {
         this.ab().b(b, x.a(_snowman.i(), var0 -> var0.e(1)));
      }
   }

   protected abstract blx h();

   protected bmb i() {
      return this.ab().a(b);
   }

   @Override
   public bmb g() {
      bmb _snowman = this.i();
      return _snowman.a() ? new bmb(this.h()) : _snowman;
   }

   @Override
   protected void e() {
      this.ab().a(b, bmb.b);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      bmb _snowman = this.i();
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
