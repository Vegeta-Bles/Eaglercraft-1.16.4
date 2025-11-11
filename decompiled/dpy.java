public class dpy extends dpo {
   private final cco s;
   private dlj t;
   private dlj u;
   private dlj v;
   private cco.a w = cco.a.c;
   private boolean x;
   private boolean y;

   public dpy(cco var1) {
      this.s = _snowman;
   }

   @Override
   bqy h() {
      return this.s.d();
   }

   @Override
   int i() {
      return 135;
   }

   @Override
   protected void b() {
      super.b();
      this.t = this.a((dlj)(new dlj(this.k / 2 - 50 - 100 - 4, 165, 100, 20, new of("advMode.mode.sequence"), var1 -> {
         this.o();
         this.n();
      })));
      this.u = this.a((dlj)(new dlj(this.k / 2 - 50, 165, 100, 20, new of("advMode.mode.unconditional"), var1 -> {
         this.x = !this.x;
         this.p();
      })));
      this.v = this.a((dlj)(new dlj(this.k / 2 + 50 + 4, 165, 100, 20, new of("advMode.mode.redstoneTriggered"), var1 -> {
         this.y = !this.y;
         this.q();
      })));
      this.c.o = false;
      this.q.o = false;
      this.t.o = false;
      this.u.o = false;
      this.v.o = false;
   }

   public void m() {
      bqy _snowman = this.s.d();
      this.a.a(_snowman.k());
      this.r = _snowman.m();
      this.w = this.s.m();
      this.x = this.s.x();
      this.y = this.s.g();
      this.k();
      this.n();
      this.p();
      this.q();
      this.c.o = true;
      this.q.o = true;
      this.t.o = true;
      this.u.o = true;
      this.v.o = true;
   }

   @Override
   public void a(djz var1, int var2, int var3) {
      super.a(_snowman, _snowman, _snowman);
      this.k();
      this.n();
      this.p();
      this.q();
      this.c.o = true;
      this.q.o = true;
      this.t.o = true;
      this.u.o = true;
      this.v.o = true;
   }

   @Override
   protected void a(bqy var1) {
      this.i.w().a(new tk(new fx(_snowman.f()), this.a.b(), this.w, _snowman.m(), this.x, this.y));
   }

   private void n() {
      switch (this.w) {
         case a:
            this.t.a(new of("advMode.mode.sequence"));
            break;
         case b:
            this.t.a(new of("advMode.mode.auto"));
            break;
         case c:
            this.t.a(new of("advMode.mode.redstone"));
      }
   }

   private void o() {
      switch (this.w) {
         case a:
            this.w = cco.a.b;
            break;
         case b:
            this.w = cco.a.c;
            break;
         case c:
            this.w = cco.a.a;
      }
   }

   private void p() {
      if (this.x) {
         this.u.a(new of("advMode.mode.conditional"));
      } else {
         this.u.a(new of("advMode.mode.unconditional"));
      }
   }

   private void q() {
      if (this.y) {
         this.v.a(new of("advMode.mode.autoexec.bat"));
      } else {
         this.v.a(new of("advMode.mode.redstoneTriggered"));
      }
   }
}
