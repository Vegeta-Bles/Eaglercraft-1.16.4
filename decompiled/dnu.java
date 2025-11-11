import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class dnu extends dot {
   private static final nr a = new of("createWorld.customize.buffet.biome");
   private final dot b;
   private final Consumer<bsv> c;
   private final gs<bsv> p;
   private dnu.a q;
   private bsv r;
   private dlj s;

   public dnu(dot var1, gn var2, Consumer<bsv> var3, bsv var4) {
      super(new of("createWorld.customize.buffet.title"));
      this.b = _snowman;
      this.c = _snowman;
      this.r = _snowman;
      this.p = _snowman.b(gm.ay);
   }

   @Override
   public void at_() {
      this.i.a(this.b);
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      this.q = new dnu.a();
      this.e.add(this.q);
      this.s = this.a((dlj)(new dlj(this.k / 2 - 155, this.l - 28, 150, 20, nq.c, var1 -> {
         this.c.accept(this.r);
         this.i.a(this.b);
      })));
      this.a((dlj)(new dlj(this.k / 2 + 5, this.l - 28, 150, 20, nq.d, var1 -> this.i.a(this.b))));
      this.q.a(this.q.au_().stream().filter(var1 -> Objects.equals(var1.b, this.r)).findFirst().orElse(null));
   }

   private void h() {
      this.s.o = this.q.h() != null;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.e(0);
      this.q.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.d, this.k / 2, 8, 16777215);
      a(_snowman, this.o, a, this.k / 2, 28, 10526880);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   class a extends dlv<dnu.a.a> {
      private a() {
         super(dnu.this.i, dnu.this.k, dnu.this.l, 40, dnu.this.l - 37, 16);
         dnu.this.p.d().stream().sorted(Comparator.comparing(var0 -> var0.getKey().a().toString())).forEach(var1x -> this.b(new dnu.a.a(var1x.getValue())));
      }

      @Override
      protected boolean b() {
         return dnu.this.aw_() == this;
      }

      public void a(@Nullable dnu.a.a var1) {
         super.a(_snowman);
         if (_snowman != null) {
            dnu.this.r = _snowman.b;
            dkz.b.a(new of("narrator.select", dnu.this.p.b(_snowman.b)).getString());
         }

         dnu.this.h();
      }

      class a extends dlv.a<dnu.a.a> {
         private final bsv b;
         private final nr c;

         public a(bsv var2) {
            this.b = _snowman;
            vk _snowman = dnu.this.p.b(_snowman);
            String _snowmanx = "biome." + _snowman.b() + "." + _snowman.a();
            if (ly.a().b(_snowmanx)) {
               this.c = new of(_snowmanx);
            } else {
               this.c = new oe(_snowman.toString());
            }
         }

         @Override
         public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            dkw.b(_snowman, dnu.this.o, this.c, _snowman + 5, _snowman + 2, 16777215);
         }

         @Override
         public boolean a(double var1, double var3, int var5) {
            if (_snowman == 0) {
               a.this.a(this);
               return true;
            } else {
               return false;
            }
         }
      }
   }
}
