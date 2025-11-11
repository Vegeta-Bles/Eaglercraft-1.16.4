public class drd extends dot {
   private final dot a;
   private static final nr b = new of("multiplayerWarning.header").a(k.r);
   private static final nr c = new of("multiplayerWarning.message");
   private static final nr p = new of("multiplayerWarning.check");
   private static final nr q = b.e().c("\n").a(c);
   private dll r;
   private dlu s = dlu.a;

   public drd(dot var1) {
      super(dkz.a);
      this.a = _snowman;
   }

   @Override
   protected void b() {
      super.b();
      this.s = dlu.a(this.o, c, this.k - 50);
      int _snowman = (this.s.a() + 1) * 9 * 2;
      this.a(new dlj(this.k / 2 - 155, 100 + _snowman, 150, 20, nq.g, var1x -> {
         if (this.r.a()) {
            this.i.k.ad = true;
            this.i.k.b();
         }

         this.i.a(new drc(this.a));
      }));
      this.a(new dlj(this.k / 2 - 155 + 160, 100 + _snowman, 150, 20, nq.h, var1x -> this.i.a(this.a)));
      this.r = new dll(this.k / 2 - 155 + 80, 76 + _snowman, 150, 20, p, false);
      this.a(this.r);
   }

   @Override
   public String ax_() {
      return q.getString();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.e(0);
      b(_snowman, this.o, b, 25, 30, 16777215);
      this.s.b(_snowman, 25, 70, 9 * 2, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
