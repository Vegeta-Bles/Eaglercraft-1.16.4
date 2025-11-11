import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

public class dhy extends eoo {
   private final dhy.a b;
   private final nr c;
   private final nr p;
   protected final BooleanConsumer a;
   private final boolean q;

   public dhy(BooleanConsumer var1, dhy.a var2, nr var3, nr var4, boolean var5) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.p = _snowman;
      this.q = _snowman;
   }

   @Override
   public void b() {
      eoj.a(this.b.d, this.c.getString(), this.p.getString());
      if (this.q) {
         this.a(new dlj(this.k / 2 - 105, j(8), 100, 20, nq.e, var1 -> this.a.accept(true)));
         this.a(new dlj(this.k / 2 + 5, j(8), 100, 20, nq.f, var1 -> this.a.accept(false)));
      } else {
         this.a(new dlj(this.k / 2 - 50, j(8), 100, 20, new of("mco.gui.ok"), var1 -> this.a.accept(true)));
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.a.accept(false);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.b.d, this.k / 2, j(2), this.b.c);
      a(_snowman, this.o, this.c, this.k / 2, j(4), 16777215);
      a(_snowman, this.o, this.p, this.k / 2, j(6), 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   public static enum a {
      a("Warning!", 16711680),
      b("Info!", 8226750);

      public final int c;
      public final String d;

      private a(String var3, int var4) {
         this.d = _snowman;
         this.c = _snowman;
      }
   }
}
