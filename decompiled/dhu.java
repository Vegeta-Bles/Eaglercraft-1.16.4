public class dhu extends eoo {
   private static final nr a = new of("mco.configure.world.name");
   private static final nr b = new of("mco.configure.world.description");
   private final dgq c;
   private final dfw p;
   private dlq q;
   private dlq r;
   private dlj s;
   private eom t;

   public dhu(dgq var1, dfw var2) {
      this.c = _snowman;
      this.p = _snowman;
   }

   @Override
   public void d() {
      if (this.q != null) {
         this.q.a();
      }

      if (this.r != null) {
         this.r.a();
      }
   }

   @Override
   public void b() {
      this.i.m.a(true);
      this.s = this.a(new dlj(this.k / 2 - 100, this.l / 4 + 120 + 17, 97, 20, new of("mco.create.world"), var1 -> this.h()));
      this.a(new dlj(this.k / 2 + 5, this.l / 4 + 120 + 17, 95, 20, nq.d, var1 -> this.i.a(this.p)));
      this.s.o = false;
      this.q = new dlq(this.i.g, this.k / 2 - 100, 65, 200, 20, null, new of("mco.configure.world.name"));
      this.d(this.q);
      this.b(this.q);
      this.r = new dlq(this.i.g, this.k / 2 - 100, 115, 200, 20, null, new of("mco.configure.world.description"));
      this.d(this.r);
      this.t = new eom(new of("mco.selectServer.create"), this.k / 2, 11, 16777215);
      this.d(this.t);
      this.A();
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public boolean a(char var1, int var2) {
      boolean _snowman = super.a(_snowman, _snowman);
      this.s.o = this.i();
      return _snowman;
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.a(this.p);
         return true;
      } else {
         boolean _snowman = super.a(_snowman, _snowman, _snowman);
         this.s.o = this.i();
         return _snowman;
      }
   }

   private void h() {
      if (this.i()) {
         dif _snowman = new dif(
            this.p,
            this.c,
            new of("mco.selectServer.create"),
            new of("mco.create.world.subtitle"),
            10526880,
            new of("mco.create.world.skip"),
            () -> this.i.a(this.p.g()),
            () -> this.i.a(this.p.g())
         );
         _snowman.a(new of("mco.create.world.reset.title"));
         this.i.a(new dhz(this.p, new djg(this.c.a, this.q.b(), this.r.b(), _snowman)));
      }
   }

   private boolean i() {
      return !this.q.b().trim().isEmpty();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.t.a(this, _snowman);
      this.o.b(_snowman, a, (float)(this.k / 2 - 100), 52.0F, 10526880);
      this.o.b(_snowman, b, (float)(this.k / 2 - 100), 102.0F, 10526880);
      if (this.q != null) {
         this.q.a(_snowman, _snowman, _snowman, _snowman);
      }

      if (this.r != null) {
         this.r.a(_snowman, _snowman, _snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
