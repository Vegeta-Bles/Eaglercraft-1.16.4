public class dik extends eoo {
   public static final nr[] a = new nr[]{
      new of("options.difficulty.peaceful"), new of("options.difficulty.easy"), new of("options.difficulty.normal"), new of("options.difficulty.hard")
   };
   public static final nr[] b = new nr[]{
      new of("selectWorld.gameMode.survival"), new of("selectWorld.gameMode.creative"), new of("selectWorld.gameMode.adventure")
   };
   private static final nr p = new of("mco.configure.world.on");
   private static final nr q = new of("mco.configure.world.off");
   private static final nr r = new of("selectWorld.gameMode");
   private static final nr s = new of("mco.configure.world.edit.slot.name");
   private dlq t;
   protected final dhs c;
   private int u;
   private int v;
   private int w;
   private final dgw x;
   private final dgq.c y;
   private final int z;
   private int A;
   private int B;
   private Boolean C;
   private Boolean D;
   private Boolean E;
   private Boolean F;
   private Integer G;
   private Boolean H;
   private Boolean I;
   private dlj J;
   private dlj K;
   private dlj L;
   private dlj M;
   private dik.a N;
   private dlj O;
   private dlj P;
   private eom Q;
   private eom R;

   public dik(dhs var1, dgw var2, dgq.c var3, int var4) {
      this.c = _snowman;
      this.x = _snowman;
      this.y = _snowman;
      this.z = _snowman;
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public void d() {
      this.t.a();
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
   public void b() {
      this.v = 170;
      this.u = this.k / 2 - this.v;
      this.w = this.k / 2 + 10;
      this.A = this.x.h;
      this.B = this.x.i;
      if (this.y == dgq.c.a) {
         this.C = this.x.a;
         this.G = this.x.e;
         this.I = this.x.g;
         this.E = this.x.b;
         this.F = this.x.c;
         this.D = this.x.d;
         this.H = this.x.f;
      } else {
         nr _snowman;
         if (this.y == dgq.c.c) {
            _snowman = new of("mco.configure.world.edit.subscreen.adventuremap");
         } else if (this.y == dgq.c.e) {
            _snowman = new of("mco.configure.world.edit.subscreen.inspiration");
         } else {
            _snowman = new of("mco.configure.world.edit.subscreen.experience");
         }

         this.R = new eom(_snowman, this.k / 2, 26, 16711680);
         this.C = true;
         this.G = 0;
         this.I = false;
         this.E = true;
         this.F = true;
         this.D = true;
         this.H = true;
      }

      this.t = new dlq(this.i.g, this.u + 2, j(1), this.v - 4, 20, null, new of("mco.configure.world.edit.slot.name"));
      this.t.k(10);
      this.t.a(this.x.a(this.z));
      this.c(this.t);
      this.J = this.a((dlj)(new dlj(this.w, j(1), this.v, 20, this.k(), var1x -> {
         this.C = !this.C;
         var1x.a(this.k());
      })));
      this.a((dlj)(new dlj(this.u, j(3), this.v, 20, this.i(), var1x -> {
         this.B = (this.B + 1) % b.length;
         var1x.a(this.i());
      })));
      this.K = this.a((dlj)(new dlj(this.w, j(3), this.v, 20, this.l(), var1x -> {
         this.E = !this.E;
         var1x.a(this.l());
      })));
      this.a((dlj)(new dlj(this.u, j(5), this.v, 20, this.h(), var1x -> {
         this.A = (this.A + 1) % a.length;
         var1x.a(this.h());
         if (this.y == dgq.c.a) {
            this.L.o = this.A != 0;
            this.L.a(this.m());
         }
      })));
      this.L = this.a((dlj)(new dlj(this.w, j(5), this.v, 20, this.m(), var1x -> {
         this.F = !this.F;
         var1x.a(this.m());
      })));
      this.N = this.a(new dik.a(this.u, j(7), this.v, this.G, 0.0F, 16.0F));
      this.M = this.a((dlj)(new dlj(this.w, j(7), this.v, 20, this.n(), var1x -> {
         this.D = !this.D;
         var1x.a(this.n());
      })));
      this.P = this.a((dlj)(new dlj(this.u, j(9), this.v, 20, this.p(), var1x -> {
         this.I = !this.I;
         var1x.a(this.p());
      })));
      this.O = this.a((dlj)(new dlj(this.w, j(9), this.v, 20, this.o(), var1x -> {
         this.H = !this.H;
         var1x.a(this.o());
      })));
      if (this.y != dgq.c.a) {
         this.J.o = false;
         this.K.o = false;
         this.M.o = false;
         this.L.o = false;
         this.N.o = false;
         this.O.o = false;
         this.P.o = false;
      }

      if (this.A == 0) {
         this.L.o = false;
      }

      this.a((dlj)(new dlj(this.u, j(13), this.v, 20, new of("mco.configure.world.buttons.done"), var1x -> this.r())));
      this.a((dlj)(new dlj(this.w, j(13), this.v, 20, nq.d, var1x -> this.i.a(this.c))));
      this.d(this.t);
      this.Q = this.d(new eom(new of("mco.configure.world.buttons.options"), this.k / 2, 17, 16777215));
      if (this.R != null) {
         this.d(this.R);
      }

      this.A();
   }

   private nr h() {
      return new of("options.difficulty").c(": ").a(a[this.A]);
   }

   private nr i() {
      return new of("options.generic_value", r, b[this.B]);
   }

   private nr k() {
      return new of("mco.configure.world.pvp").c(": ").a(c(this.C));
   }

   private nr l() {
      return new of("mco.configure.world.spawnAnimals").c(": ").a(c(this.E));
   }

   private nr m() {
      return this.A == 0
         ? new of("mco.configure.world.spawnMonsters").c(": ").a(new of("mco.configure.world.off"))
         : new of("mco.configure.world.spawnMonsters").c(": ").a(c(this.F));
   }

   private nr n() {
      return new of("mco.configure.world.spawnNPCs").c(": ").a(c(this.D));
   }

   private nr o() {
      return new of("mco.configure.world.commandBlocks").c(": ").a(c(this.H));
   }

   private nr p() {
      return new of("mco.configure.world.forceGameMode").c(": ").a(c(this.I));
   }

   private static nr c(boolean var0) {
      return _snowman ? p : q;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.o.b(_snowman, s, (float)(this.u + this.v / 2 - this.o.a(s) / 2), (float)(j(0) - 5), 16777215);
      this.Q.a(this, _snowman);
      if (this.R != null) {
         this.R.a(this, _snowman);
      }

      this.t.a(_snowman, _snowman, _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private String q() {
      return this.t.b().equals(this.x.b(this.z)) ? "" : this.t.b();
   }

   private void r() {
      if (this.y != dgq.c.c && this.y != dgq.c.d && this.y != dgq.c.e) {
         this.c.a(new dgw(this.C, this.E, this.F, this.D, this.G, this.H, this.A, this.B, this.I, this.q()));
      } else {
         this.c.a(new dgw(this.x.a, this.x.b, this.x.c, this.x.d, this.x.e, this.x.f, this.A, this.B, this.x.g, this.q()));
      }
   }

   class a extends dlg {
      private final double c;
      private final double d;

      public a(int var2, int var3, int var4, int var5, float var6, float var7) {
         super(_snowman, _snowman, _snowman, 20, oe.d, 0.0);
         this.c = (double)_snowman;
         this.d = (double)_snowman;
         this.b = (double)((afm.a((float)_snowman, _snowman, _snowman) - _snowman) / (_snowman - _snowman));
         this.b();
      }

      @Override
      public void a() {
         if (dik.this.N.o) {
            dik.this.G = (int)afm.d(afm.a(this.b, 0.0, 1.0), this.c, this.d);
         }
      }

      @Override
      protected void b() {
         this.a(
            new of("mco.configure.world.spawnProtection")
               .c(": ")
               .a((nr)(dik.this.G == 0 ? new of("mco.configure.world.off") : new oe(String.valueOf(dik.this.G))))
         );
      }

      @Override
      public void a(double var1, double var3) {
      }

      @Override
      public void a_(double var1, double var3) {
      }
   }
}
