public class dqn extends dot {
   private static final nr a = new of("jigsaw_block.joint_label");
   private static final nr b = new of("jigsaw_block.pool");
   private static final nr c = new of("jigsaw_block.name");
   private static final nr p = new of("jigsaw_block.target");
   private static final nr q = new of("jigsaw_block.final_state");
   private final ccz r;
   private dlq s;
   private dlq t;
   private dlq u;
   private dlq v;
   private int w;
   private boolean x = true;
   private dlj y;
   private dlj z;
   private ccz.a A;

   public dqn(ccz var1) {
      super(dkz.a);
      this.r = _snowman;
   }

   @Override
   public void d() {
      this.s.a();
      this.t.a();
      this.u.a();
      this.v.a();
   }

   private void h() {
      this.k();
      this.i.a(null);
   }

   private void i() {
      this.i.a(null);
   }

   private void k() {
      this.i.w().a(new tn(this.r.o(), new vk(this.s.b()), new vk(this.t.b()), new vk(this.u.b()), this.v.b(), this.A));
   }

   private void l() {
      this.i.w().a(new sq(this.r.o(), this.w, this.x));
   }

   @Override
   public void at_() {
      this.i();
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      this.u = new dlq(this.o, this.k / 2 - 152, 20, 300, 20, new of("jigsaw_block.pool"));
      this.u.k(128);
      this.u.a(this.r.g().toString());
      this.u.a(var1x -> this.m());
      this.e.add(this.u);
      this.s = new dlq(this.o, this.k / 2 - 152, 55, 300, 20, new of("jigsaw_block.name"));
      this.s.k(128);
      this.s.a(this.r.d().toString());
      this.s.a(var1x -> this.m());
      this.e.add(this.s);
      this.t = new dlq(this.o, this.k / 2 - 152, 90, 300, 20, new of("jigsaw_block.target"));
      this.t.k(128);
      this.t.a(this.r.f().toString());
      this.t.a(var1x -> this.m());
      this.e.add(this.t);
      this.v = new dlq(this.o, this.k / 2 - 152, 125, 300, 20, new of("jigsaw_block.final_state"));
      this.v.k(256);
      this.v.a(this.r.h());
      this.e.add(this.v);
      this.A = this.r.j();
      int _snowman = this.o.a(a) + 10;
      this.y = this.a((dlj)(new dlj(this.k / 2 - 152 + _snowman, 150, 300 - _snowman, 20, this.n(), var1x -> {
         ccz.a[] _snowmanx = ccz.a.values();
         int _snowmanx = (this.A.ordinal() + 1) % _snowmanx.length;
         this.A = _snowmanx[_snowmanx];
         var1x.a(this.n());
      })));
      boolean _snowmanx = bxr.h(this.r.p()).n().c();
      this.y.o = _snowmanx;
      this.y.p = _snowmanx;
      this.a(new dlg(this.k / 2 - 154, 180, 100, 20, oe.d, 0.0) {
         {
            this.b();
         }

         @Override
         protected void b() {
            this.a(new of("jigsaw_block.levels", dqn.this.w));
         }

         @Override
         protected void a() {
            dqn.this.w = afm.c(afm.b(0.0, 7.0, this.b));
         }
      });
      this.a((dlj)(new dlj(this.k / 2 - 50, 180, 100, 20, new of("jigsaw_block.keep_jigsaws"), var1x -> {
         this.x = !this.x;
         var1x.c(250);
      }) {
         @Override
         public nr i() {
            return nq.a(super.i(), dqn.this.x);
         }
      }));
      this.a((dlj)(new dlj(this.k / 2 + 54, 180, 100, 20, new of("jigsaw_block.generate"), var1x -> {
         this.h();
         this.l();
      })));
      this.z = this.a((dlj)(new dlj(this.k / 2 - 4 - 150, 210, 150, 20, nq.c, var1x -> this.h())));
      this.a((dlj)(new dlj(this.k / 2 + 4, 210, 150, 20, nq.d, var1x -> this.i())));
      this.b(this.u);
      this.m();
   }

   private void m() {
      this.z.o = vk.b(this.s.b()) && vk.b(this.t.b()) && vk.b(this.u.b());
   }

   @Override
   public void a(djz var1, int var2, int var3) {
      String _snowman = this.s.b();
      String _snowmanx = this.t.b();
      String _snowmanxx = this.u.b();
      String _snowmanxxx = this.v.b();
      int _snowmanxxxx = this.w;
      ccz.a _snowmanxxxxx = this.A;
      this.b(_snowman, _snowman, _snowman);
      this.s.a(_snowman);
      this.t.a(_snowmanx);
      this.u.a(_snowmanxx);
      this.v.a(_snowmanxxx);
      this.w = _snowmanxxxx;
      this.A = _snowmanxxxxx;
      this.y.a(this.n());
   }

   private nr n() {
      return new of("jigsaw_block.joint." + this.A.a());
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (!this.z.o || _snowman != 257 && _snowman != 335) {
         return false;
      } else {
         this.h();
         return true;
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      b(_snowman, this.o, b, this.k / 2 - 153, 10, 10526880);
      this.u.a(_snowman, _snowman, _snowman, _snowman);
      b(_snowman, this.o, c, this.k / 2 - 153, 45, 10526880);
      this.s.a(_snowman, _snowman, _snowman, _snowman);
      b(_snowman, this.o, p, this.k / 2 - 153, 80, 10526880);
      this.t.a(_snowman, _snowman, _snowman, _snowman);
      b(_snowman, this.o, q, this.k / 2 - 153, 115, 10526880);
      this.v.a(_snowman, _snowman, _snowman, _snowman);
      if (bxr.h(this.r.p()).n().c()) {
         b(_snowman, this.o, a, this.k / 2 - 153, 156, 16777215);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
