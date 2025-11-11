import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

public class dns extends dot {
   private final nr p;
   private dlu q = dlu.a;
   protected nr a;
   protected nr b;
   private int r;
   protected final BooleanConsumer c;

   public dns(BooleanConsumer var1, nr var2, nr var3) {
      this(_snowman, _snowman, _snowman, nq.e, nq.f);
   }

   public dns(BooleanConsumer var1, nr var2, nr var3, nr var4, nr var5) {
      super(_snowman);
      this.c = _snowman;
      this.p = _snowman;
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public String ax_() {
      return super.ax_() + ". " + this.p.getString();
   }

   @Override
   protected void b() {
      super.b();
      this.a(new dlj(this.k / 2 - 155, this.l / 6 + 96, 150, 20, this.a, var1 -> this.c.accept(true)));
      this.a(new dlj(this.k / 2 - 155 + 160, this.l / 6 + 96, 150, 20, this.b, var1 -> this.c.accept(false)));
      this.q = dlu.a(this.o, this.p, this.k - 50);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 70, 16777215);
      this.q.a(_snowman, this.k / 2, 90);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   public void a(int var1) {
      this.r = _snowman;

      for (dlh _snowman : this.m) {
         _snowman.o = false;
      }
   }

   @Override
   public void d() {
      super.d();
      if (--this.r == 0) {
         for (dlh _snowman : this.m) {
            _snowman.o = true;
         }
      }
   }

   @Override
   public boolean as_() {
      return false;
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.c.accept(false);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }
}
