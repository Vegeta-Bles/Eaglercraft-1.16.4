import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

public class dnz extends dot {
   private static final nr a = new of("addServer.enterIp");
   private dlj b;
   private final dwz c;
   private dlq p;
   private final BooleanConsumer q;
   private final dot r;

   public dnz(dot var1, BooleanConsumer var2, dwz var3) {
      super(new of("selectServer.direct"));
      this.r = _snowman;
      this.c = _snowman;
      this.q = _snowman;
   }

   @Override
   public void d() {
      this.p.a();
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (this.aw_() != this.p || _snowman != 257 && _snowman != 335) {
         return super.a(_snowman, _snowman, _snowman);
      } else {
         this.h();
         return true;
      }
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      this.b = this.a(new dlj(this.k / 2 - 100, this.l / 4 + 96 + 12, 200, 20, new of("selectServer.select"), var1 -> this.h()));
      this.a(new dlj(this.k / 2 - 100, this.l / 4 + 120 + 12, 200, 20, nq.d, var1 -> this.q.accept(false)));
      this.p = new dlq(this.o, this.k / 2 - 100, 116, 200, 20, new of("addServer.enterIp"));
      this.p.k(128);
      this.p.e(true);
      this.p.a(this.i.k.aM);
      this.p.a(var1 -> this.i());
      this.e.add(this.p);
      this.b(this.p);
      this.i();
   }

   @Override
   public void a(djz var1, int var2, int var3) {
      String _snowman = this.p.b();
      this.b(_snowman, _snowman, _snowman);
      this.p.a(_snowman);
   }

   private void h() {
      this.c.b = this.p.b();
      this.q.accept(true);
   }

   @Override
   public void at_() {
      this.i.a(this.r);
   }

   @Override
   public void e() {
      this.i.m.a(false);
      this.i.k.aM = this.p.b();
      this.i.k.b();
   }

   private void i() {
      String _snowman = this.p.b();
      this.b.o = !_snowman.isEmpty() && _snowman.split(":").length > 0 && _snowman.indexOf(32) == -1;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 20, 16777215);
      b(_snowman, this.o, a, this.k / 2 - 100, 100, 10526880);
      this.p.a(_snowman, _snowman, _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
