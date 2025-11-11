import java.util.Locale;

public class dho extends eoo {
   private final dot a;
   private final dgg b;
   private dho.a c;

   public dho(dot var1, dgg var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void d() {
   }

   @Override
   public void b() {
      this.i.m.a(true);
      this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 120 + 24, 200, 20, nq.h, var1 -> this.i.a(this.a))));
      this.c = new dho.a(this.i);
      this.d(this.c);
      this.c(this.c);
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.a(this.a);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, "Changes from last backup", this.k / 2, 10, 16777215);
      this.c.a(_snowman, _snowman, _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private nr a(String var1, String var2) {
      String _snowman = _snowman.toLowerCase(Locale.ROOT);
      if (_snowman.contains("game") && _snowman.contains("mode")) {
         return this.c(_snowman);
      } else {
         return (nr)(_snowman.contains("game") && _snowman.contains("difficulty") ? this.b(_snowman) : new oe(_snowman));
      }
   }

   private nr b(String var1) {
      try {
         return dik.a[Integer.parseInt(_snowman)];
      } catch (Exception var3) {
         return new oe("UNKNOWN");
      }
   }

   private nr c(String var1) {
      try {
         return dik.b[Integer.parseInt(_snowman)];
      } catch (Exception var3) {
         return new oe("UNKNOWN");
      }
   }

   class a extends dlv<dho.b> {
      public a(djz var2) {
         super(_snowman, dho.this.k, dho.this.l, 32, dho.this.l - 64, 36);
         this.a(false);
         if (dho.this.b.e != null) {
            dho.this.b.e.forEach((var1x, var2x) -> this.b(dho.this.new b(var1x, var2x)));
         }
      }
   }

   class b extends dlv.a<dho.b> {
      private final String b;
      private final String c;

      public b(String var2, String var3) {
         this.b = _snowman;
         this.c = _snowman;
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         dku _snowman = dho.this.i.g;
         dkw.b(_snowman, _snowman, this.b, _snowman, _snowman, 10526880);
         dkw.b(_snowman, _snowman, dho.this.a(this.b, this.c), _snowman, _snowman + 12, 16777215);
      }
   }
}
