import java.util.function.Supplier;

public enum bka implements bjz {
   a("leather", 5, new int[]{1, 2, 3, 1}, 15, adq.P, 0.0F, 0.0F, () -> bon.a(bmd.lS)),
   b("chainmail", 15, new int[]{1, 4, 5, 2}, 12, adq.J, 0.0F, 0.0F, () -> bon.a(bmd.kh)),
   c("iron", 15, new int[]{2, 5, 6, 2}, 9, adq.O, 0.0F, 0.0F, () -> bon.a(bmd.kh)),
   d("gold", 7, new int[]{1, 3, 5, 2}, 25, adq.N, 0.0F, 0.0F, () -> bon.a(bmd.ki)),
   e("diamond", 33, new int[]{3, 6, 8, 3}, 10, adq.K, 2.0F, 0.0F, () -> bon.a(bmd.kg)),
   f("turtle", 25, new int[]{2, 5, 6, 2}, 9, adq.R, 0.0F, 0.0F, () -> bon.a(bmd.jZ)),
   g("netherite", 37, new int[]{3, 6, 8, 3}, 15, adq.Q, 3.0F, 0.1F, () -> bon.a(bmd.kj));

   private static final int[] h = new int[]{13, 15, 16, 11};
   private final String i;
   private final int j;
   private final int[] k;
   private final int l;
   private final adp m;
   private final float n;
   private final float o;
   private final afi<bon> p;

   private bka(String var3, int var4, int[] var5, int var6, adp var7, float var8, float var9, Supplier<bon> var10) {
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.m = _snowman;
      this.n = _snowman;
      this.o = _snowman;
      this.p = new afi<>(_snowman);
   }

   @Override
   public int a(aqf var1) {
      return h[_snowman.b()] * this.j;
   }

   @Override
   public int b(aqf var1) {
      return this.k[_snowman.b()];
   }

   @Override
   public int a() {
      return this.l;
   }

   @Override
   public adp b() {
      return this.m;
   }

   @Override
   public bon c() {
      return this.p.a();
   }

   @Override
   public String d() {
      return this.i;
   }

   @Override
   public float e() {
      return this.n;
   }

   @Override
   public float f() {
      return this.o;
   }
}
