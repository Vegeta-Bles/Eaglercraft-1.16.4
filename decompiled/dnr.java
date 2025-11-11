import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

public class dnr extends dns {
   private final nr p;
   private final nr q;
   private final String r;
   private final boolean s;

   public dnr(BooleanConsumer var1, String var2, boolean var3) {
      super(_snowman, new of(_snowman ? "chat.link.confirmTrusted" : "chat.link.confirm"), new oe(_snowman));
      this.a = (nr)(_snowman ? new of("chat.link.open") : nq.e);
      this.b = _snowman ? nq.d : nq.f;
      this.q = new of("chat.copy");
      this.p = new of("chat.link.warning");
      this.s = !_snowman;
      this.r = _snowman;
   }

   @Override
   protected void b() {
      super.b();
      this.m.clear();
      this.e.clear();
      this.a(new dlj(this.k / 2 - 50 - 105, this.l / 6 + 96, 100, 20, this.a, var1 -> this.c.accept(true)));
      this.a(new dlj(this.k / 2 - 50, this.l / 6 + 96, 100, 20, this.q, var1 -> {
         this.g();
         this.c.accept(false);
      }));
      this.a(new dlj(this.k / 2 - 50 + 105, this.l / 6 + 96, 100, 20, this.b, var1 -> this.c.accept(false)));
   }

   public void g() {
      this.i.m.a(this.r);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (this.s) {
         a(_snowman, this.o, this.p, this.k / 2, 110, 16764108);
      }
   }
}
