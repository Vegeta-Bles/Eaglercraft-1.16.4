import java.util.Arrays;
import java.util.stream.Stream;

public class doj extends dol {
   private dlx c;
   private static final dkc[] p = new dkc[]{dkc.s, dkc.O, dkc.o, dkc.K, dkc.V};

   public doj(dot var1, dkd var2) {
      super(_snowman, _snowman, new of("options.mouse_settings.title"));
   }

   @Override
   protected void b() {
      this.c = new dlx(this.i, this.k, this.l, 32, this.l - 32, 25);
      if (deo.a()) {
         this.c.a(Stream.concat(Arrays.stream(p), Stream.of(dkc.p)).toArray(dkc[]::new));
      } else {
         this.c.a(p);
      }

      this.e.add(this.c);
      this.a(new dlj(this.k / 2 - 100, this.l - 27, 200, 20, nq.c, var1 -> {
         this.b.b();
         this.i.a(this.a);
      }));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.c.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.d, this.k / 2, 5, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
