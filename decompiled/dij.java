public class dij extends eoo {
   private static final nr a = new of("mco.configure.world.name");
   private static final nr b = new of("mco.configure.world.description");
   private final dhs c;
   private final dgq p;
   private dlj q;
   private dlq r;
   private dlq s;
   private eom t;

   public dij(dhs var1, dgq var2) {
      this.c = _snowman;
      this.p = _snowman;
   }

   @Override
   public void d() {
      this.s.a();
      this.r.a();
      this.q.o = !this.s.b().trim().isEmpty();
   }

   @Override
   public void b() {
      this.i.m.a(true);
      int _snowman = this.k / 2 - 106;
      this.q = this.a(new dlj(_snowman - 2, j(12), 106, 20, new of("mco.configure.world.buttons.done"), var1x -> this.a()));
      this.a(new dlj(this.k / 2 + 2, j(12), 106, 20, nq.d, var1x -> this.i.a(this.c)));
      String _snowmanx = this.p.e == dgq.b.b ? "mco.configure.world.buttons.close" : "mco.configure.world.buttons.open";
      dlj _snowmanxx = new dlj(this.k / 2 - 53, j(0), 106, 20, new of(_snowmanx), var1x -> {
         if (this.p.e == dgq.b.b) {
            nr _snowmanxxx = new of("mco.configure.world.close.question.line1");
            nr _snowmanx = new of("mco.configure.world.close.question.line2");
            this.i.a(new dhy(var1xx -> {
               if (var1xx) {
                  this.c.a(this);
               } else {
                  this.i.a(this);
               }
            }, dhy.a.b, _snowmanxxx, _snowmanx, true));
         } else {
            this.c.a(false, this);
         }
      });
      this.a(_snowmanxx);
      this.s = new dlq(this.i.g, _snowman, j(4), 212, 20, null, new of("mco.configure.world.name"));
      this.s.k(32);
      this.s.a(this.p.b());
      this.d(this.s);
      this.c(this.s);
      this.r = new dlq(this.i.g, _snowman, j(8), 212, 20, null, new of("mco.configure.world.description"));
      this.r.k(32);
      this.r.a(this.p.a());
      this.d(this.r);
      this.t = this.d(new eom(new of("mco.configure.world.settings.title"), this.k / 2, 17, 16777215));
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
      this.t.a(this, _snowman);
      this.o.b(_snowman, a, (float)(this.k / 2 - 106), (float)j(3), 10526880);
      this.o.b(_snowman, b, (float)(this.k / 2 - 106), (float)j(7), 10526880);
      this.s.a(_snowman, _snowman, _snowman, _snowman);
      this.r.a(_snowman, _snowman, _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   public void a() {
      this.c.a(this.s.b(), this.r.b());
   }
}
