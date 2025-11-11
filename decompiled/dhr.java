public class dhr extends eoo {
   private static final nr a = new of("mco.client.outdated.title");
   private static final nr[] b = new nr[]{new of("mco.client.outdated.msg.line1"), new of("mco.client.outdated.msg.line2")};
   private static final nr c = new of("mco.client.incompatible.title");
   private static final nr[] p = new nr[]{
      new of("mco.client.incompatible.msg.line1"), new of("mco.client.incompatible.msg.line2"), new of("mco.client.incompatible.msg.line3")
   };
   private final dot q;
   private final boolean r;

   public dhr(dot var1, boolean var2) {
      this.q = _snowman;
      this.r = _snowman;
   }

   @Override
   public void b() {
      this.a(new dlj(this.k / 2 - 100, j(12), 200, 20, nq.h, var1 -> this.i.a(this.q)));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      nr _snowman;
      nr[] _snowmanx;
      if (this.r) {
         _snowman = c;
         _snowmanx = p;
      } else {
         _snowman = a;
         _snowmanx = b;
      }

      a(_snowman, this.o, _snowman, this.k / 2, j(3), 16711680);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
         a(_snowman, this.o, _snowmanx[_snowmanxx], this.k / 2, j(5) + _snowmanxx * 12, 16777215);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman != 257 && _snowman != 335 && _snowman != 256) {
         return super.a(_snowman, _snowman, _snowman);
      } else {
         this.i.a(this.q);
         return true;
      }
   }
}
