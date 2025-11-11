import java.util.function.Supplier;

public enum bnj implements bnh {
   a(0, 59, 2.0F, 0.0F, 15, () -> bon.a(aeg.c)),
   b(1, 131, 4.0F, 1.0F, 5, () -> bon.a(aeg.ab)),
   c(2, 250, 6.0F, 2.0F, 14, () -> bon.a(bmd.kh)),
   d(3, 1561, 8.0F, 3.0F, 10, () -> bon.a(bmd.kg)),
   e(0, 32, 12.0F, 0.0F, 22, () -> bon.a(bmd.ki)),
   f(4, 2031, 9.0F, 4.0F, 15, () -> bon.a(bmd.kj));

   private final int g;
   private final int h;
   private final float i;
   private final float j;
   private final int k;
   private final afi<bon> l;

   private bnj(int var3, int var4, float var5, float var6, int var7, Supplier<bon> var8) {
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = new afi<>(_snowman);
   }

   @Override
   public int a() {
      return this.h;
   }

   @Override
   public float b() {
      return this.i;
   }

   @Override
   public float c() {
      return this.j;
   }

   @Override
   public int d() {
      return this.g;
   }

   @Override
   public int e() {
      return this.k;
   }

   @Override
   public bon f() {
      return this.l.a();
   }
}
