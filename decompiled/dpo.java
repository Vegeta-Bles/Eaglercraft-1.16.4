public abstract class dpo extends dot {
   private static final nr s = new of("advMode.setCommand");
   private static final nr t = new of("advMode.command");
   private static final nr u = new of("advMode.previousOutput");
   protected dlq a;
   protected dlq b;
   protected dlj c;
   protected dlj p;
   protected dlj q;
   protected boolean r;
   private dlm v;

   public dpo() {
      super(dkz.a);
   }

   @Override
   public void d() {
      this.a.a();
   }

   abstract bqy h();

   abstract int i();

   @Override
   protected void b() {
      this.i.m.a(true);
      this.c = this.a((dlj)(new dlj(this.k / 2 - 4 - 150, this.l / 4 + 120 + 12, 150, 20, nq.c, var1 -> this.l())));
      this.p = this.a((dlj)(new dlj(this.k / 2 + 4, this.l / 4 + 120 + 12, 150, 20, nq.d, var1 -> this.at_())));
      this.q = this.a((dlj)(new dlj(this.k / 2 + 150 - 20, this.i(), 20, 20, new oe("O"), var1 -> {
         bqy _snowman = this.h();
         _snowman.a(!_snowman.m());
         this.k();
      })));
      this.a = new dlq(this.o, this.k / 2 - 150, 50, 300, 20, new of("advMode.command")) {
         @Override
         protected nx c() {
            return super.c().c(dpo.this.v.b());
         }
      };
      this.a.k(32500);
      this.a.a(this::b);
      this.e.add(this.a);
      this.b = new dlq(this.o, this.k / 2 - 150, this.i(), 276, 20, new of("advMode.previousOutput"));
      this.b.k(32500);
      this.b.g(false);
      this.b.a("-");
      this.e.add(this.b);
      this.b(this.a);
      this.a.e(true);
      this.v = new dlm(this.i, this, this.a, this.o, true, true, 0, 7, false, Integer.MIN_VALUE);
      this.v.a(true);
      this.v.a();
   }

   @Override
   public void a(djz var1, int var2, int var3) {
      String _snowman = this.a.b();
      this.b(_snowman, _snowman, _snowman);
      this.a.a(_snowman);
      this.v.a();
   }

   protected void k() {
      if (this.h().m()) {
         this.q.a(new oe("O"));
         this.b.a(this.h().j().getString());
      } else {
         this.q.a(new oe("X"));
         this.b.a("-");
      }
   }

   protected void l() {
      bqy _snowman = this.h();
      this.a(_snowman);
      if (!_snowman.m()) {
         _snowman.b(null);
      }

      this.i.a(null);
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   protected abstract void a(bqy var1);

   @Override
   public void at_() {
      this.h().a(this.r);
      this.i.a(null);
   }

   private void b(String var1) {
      this.v.a();
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (this.v.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (_snowman != 257 && _snowman != 335) {
         return false;
      } else {
         this.l();
         return true;
      }
   }

   @Override
   public boolean a(double var1, double var3, double var5) {
      return this.v.a(_snowman) ? true : super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      return this.v.a(_snowman, _snowman, _snowman) ? true : super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, s, this.k / 2, 20, 16777215);
      b(_snowman, this.o, t, this.k / 2 - 150, 40, 10526880);
      this.a.a(_snowman, _snowman, _snowman, _snowman);
      int _snowman = 75;
      if (!this.b.b().isEmpty()) {
         _snowman += 5 * 9 + 1 + this.i() - 135;
         b(_snowman, this.o, u, this.k / 2 - 150, _snowman + 4, 10526880);
         this.b.a(_snowman, _snowman, _snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
      this.v.a(_snowman, _snowman, _snowman);
   }
}
