public class die extends eoo {
   private static final nr a = new of("mco.reset.world.seed");
   private static final nr[] b = new nr[]{
      new of("generator.default"), new of("generator.flat"), new of("generator.large_biomes"), new of("generator.amplified")
   };
   private final dif c;
   private eom p;
   private dlq q;
   private Boolean r = true;
   private Integer s = 0;
   private nr t;

   public die(dif var1, nr var2) {
      this.c = _snowman;
      this.t = _snowman;
   }

   @Override
   public void d() {
      this.q.a();
      super.d();
   }

   @Override
   public void b() {
      this.i.m.a(true);
      this.p = new eom(new of("mco.reset.world.generate"), this.k / 2, 17, 16777215);
      this.d(this.p);
      this.q = new dlq(this.i.g, this.k / 2 - 100, j(2), 200, 20, null, new of("mco.reset.world.seed"));
      this.q.k(32);
      this.d(this.q);
      this.b(this.q);
      this.a(new dlj(this.k / 2 - 102, j(4), 205, 20, this.h(), var1 -> {
         this.s = (this.s + 1) % b.length;
         var1.a(this.h());
      }));
      this.a(new dlj(this.k / 2 - 102, j(6) - 2, 205, 20, this.i(), var1 -> {
         this.r = !this.r;
         var1.a(this.i());
      }));
      this.a(new dlj(this.k / 2 - 102, j(12), 97, 20, this.t, var1 -> this.c.a(new dif.c(this.q.b(), this.s, this.r))));
      this.a(new dlj(this.k / 2 + 8, j(12), 97, 20, nq.h, var1 -> this.i.a(this.c)));
      this.A();
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.a(this.c);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.p.a(this, _snowman);
      this.o.b(_snowman, a, (float)(this.k / 2 - 100), (float)j(1), 10526880);
      this.q.a(_snowman, _snowman, _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private nr h() {
      return new of("selectWorld.mapType").c(" ").a(b[this.s]);
   }

   private nr i() {
      return nq.a(new of("selectWorld.mapFeatures"), this.r);
   }
}
