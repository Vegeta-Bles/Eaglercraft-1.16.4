import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class dqz extends dot {
   private static final nr a = new of("structure_block.structure_name");
   private static final nr b = new of("structure_block.position");
   private static final nr c = new of("structure_block.size");
   private static final nr p = new of("structure_block.integrity");
   private static final nr q = new of("structure_block.custom_data");
   private static final nr r = new of("structure_block.include_entities");
   private static final nr s = new of("structure_block.detect_size");
   private static final nr t = new of("structure_block.show_air");
   private static final nr u = new of("structure_block.show_boundingbox");
   private final cdj v;
   private byg w = byg.a;
   private bzm x = bzm.a;
   private cfo y = cfo.d;
   private boolean z;
   private boolean A;
   private boolean B;
   private dlq C;
   private dlq D;
   private dlq E;
   private dlq F;
   private dlq G;
   private dlq H;
   private dlq I;
   private dlq J;
   private dlq K;
   private dlq L;
   private dlj M;
   private dlj N;
   private dlj O;
   private dlj P;
   private dlj Q;
   private dlj R;
   private dlj S;
   private dlj T;
   private dlj U;
   private dlj V;
   private dlj W;
   private dlj X;
   private dlj Y;
   private dlj Z;
   private final DecimalFormat aa = new DecimalFormat("0.0###");

   public dqz(cdj var1) {
      super(new of(bup.mY.i()));
      this.v = _snowman;
      this.aa.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
   }

   @Override
   public void d() {
      this.C.a();
      this.D.a();
      this.E.a();
      this.F.a();
      this.G.a();
      this.H.a();
      this.I.a();
      this.J.a();
      this.K.a();
      this.L.a();
   }

   private void h() {
      if (this.a(cdj.a.a)) {
         this.i.a(null);
      }
   }

   private void i() {
      this.v.b(this.w);
      this.v.b(this.x);
      this.v.a(this.y);
      this.v.a(this.z);
      this.v.d(this.A);
      this.v.e(this.B);
      this.i.a(null);
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      this.M = this.a((dlj)(new dlj(this.k / 2 - 4 - 150, 210, 150, 20, nq.c, var1x -> this.h())));
      this.N = this.a((dlj)(new dlj(this.k / 2 + 4, 210, 150, 20, nq.d, var1x -> this.i())));
      this.O = this.a((dlj)(new dlj(this.k / 2 + 4 + 100, 185, 50, 20, new of("structure_block.button.save"), var1x -> {
         if (this.v.x() == cfo.a) {
            this.a(cdj.a.b);
            this.i.a(null);
         }
      })));
      this.P = this.a((dlj)(new dlj(this.k / 2 + 4 + 100, 185, 50, 20, new of("structure_block.button.load"), var1x -> {
         if (this.v.x() == cfo.b) {
            this.a(cdj.a.c);
            this.i.a(null);
         }
      })));
      this.U = this.a((dlj)(new dlj(this.k / 2 - 4 - 150, 185, 50, 20, new oe("MODE"), var1x -> {
         this.v.y();
         this.p();
      })));
      this.V = this.a((dlj)(new dlj(this.k / 2 + 4 + 100, 120, 50, 20, new of("structure_block.button.detect_size"), var1x -> {
         if (this.v.x() == cfo.a) {
            this.a(cdj.a.d);
            this.i.a(null);
         }
      })));
      this.W = this.a((dlj)(new dlj(this.k / 2 + 4 + 100, 160, 50, 20, new oe("ENTITIES"), var1x -> {
         this.v.a(!this.v.z());
         this.k();
      })));
      this.X = this.a((dlj)(new dlj(this.k / 2 - 20, 185, 40, 20, new oe("MIRROR"), var1x -> {
         switch (this.v.k()) {
            case a:
               this.v.b(byg.b);
               break;
            case b:
               this.v.b(byg.c);
               break;
            case c:
               this.v.b(byg.a);
         }

         this.n();
      })));
      this.Y = this.a((dlj)(new dlj(this.k / 2 + 4 + 100, 80, 50, 20, new oe("SHOWAIR"), var1x -> {
         this.v.d(!this.v.H());
         this.l();
      })));
      this.Z = this.a((dlj)(new dlj(this.k / 2 + 4 + 100, 80, 50, 20, new oe("SHOWBB"), var1x -> {
         this.v.e(!this.v.I());
         this.m();
      })));
      this.Q = this.a((dlj)(new dlj(this.k / 2 - 1 - 40 - 1 - 40 - 20, 185, 40, 20, new oe("0"), var1x -> {
         this.v.b(bzm.a);
         this.o();
      })));
      this.R = this.a((dlj)(new dlj(this.k / 2 - 1 - 40 - 20, 185, 40, 20, new oe("90"), var1x -> {
         this.v.b(bzm.b);
         this.o();
      })));
      this.S = this.a((dlj)(new dlj(this.k / 2 + 1 + 20, 185, 40, 20, new oe("180"), var1x -> {
         this.v.b(bzm.c);
         this.o();
      })));
      this.T = this.a((dlj)(new dlj(this.k / 2 + 1 + 40 + 1 + 20, 185, 40, 20, new oe("270"), var1x -> {
         this.v.b(bzm.d);
         this.o();
      })));
      this.C = new dlq(this.o, this.k / 2 - 152, 40, 300, 20, new of("structure_block.structure_name")) {
         @Override
         public boolean a(char var1, int var2) {
            return !dqz.this.a(this.b(), _snowman, this.n()) ? false : super.a(_snowman, _snowman);
         }
      };
      this.C.k(64);
      this.C.a(this.v.d());
      this.e.add(this.C);
      fx _snowman = this.v.h();
      this.D = new dlq(this.o, this.k / 2 - 152, 80, 80, 20, new of("structure_block.position.x"));
      this.D.k(15);
      this.D.a(Integer.toString(_snowman.u()));
      this.e.add(this.D);
      this.E = new dlq(this.o, this.k / 2 - 72, 80, 80, 20, new of("structure_block.position.y"));
      this.E.k(15);
      this.E.a(Integer.toString(_snowman.v()));
      this.e.add(this.E);
      this.F = new dlq(this.o, this.k / 2 + 8, 80, 80, 20, new of("structure_block.position.z"));
      this.F.k(15);
      this.F.a(Integer.toString(_snowman.w()));
      this.e.add(this.F);
      fx _snowmanx = this.v.j();
      this.G = new dlq(this.o, this.k / 2 - 152, 120, 80, 20, new of("structure_block.size.x"));
      this.G.k(15);
      this.G.a(Integer.toString(_snowmanx.u()));
      this.e.add(this.G);
      this.H = new dlq(this.o, this.k / 2 - 72, 120, 80, 20, new of("structure_block.size.y"));
      this.H.k(15);
      this.H.a(Integer.toString(_snowmanx.v()));
      this.e.add(this.H);
      this.I = new dlq(this.o, this.k / 2 + 8, 120, 80, 20, new of("structure_block.size.z"));
      this.I.k(15);
      this.I.a(Integer.toString(_snowmanx.w()));
      this.e.add(this.I);
      this.J = new dlq(this.o, this.k / 2 - 152, 120, 80, 20, new of("structure_block.integrity.integrity"));
      this.J.k(15);
      this.J.a(this.aa.format((double)this.v.A()));
      this.e.add(this.J);
      this.K = new dlq(this.o, this.k / 2 - 72, 120, 80, 20, new of("structure_block.integrity.seed"));
      this.K.k(31);
      this.K.a(Long.toString(this.v.B()));
      this.e.add(this.K);
      this.L = new dlq(this.o, this.k / 2 - 152, 120, 240, 20, new of("structure_block.custom_data"));
      this.L.k(128);
      this.L.a(this.v.m());
      this.e.add(this.L);
      this.w = this.v.k();
      this.n();
      this.x = this.v.l();
      this.o();
      this.y = this.v.x();
      this.p();
      this.z = this.v.z();
      this.k();
      this.A = this.v.H();
      this.l();
      this.B = this.v.I();
      this.m();
      this.b(this.C);
   }

   @Override
   public void a(djz var1, int var2, int var3) {
      String _snowman = this.C.b();
      String _snowmanx = this.D.b();
      String _snowmanxx = this.E.b();
      String _snowmanxxx = this.F.b();
      String _snowmanxxxx = this.G.b();
      String _snowmanxxxxx = this.H.b();
      String _snowmanxxxxxx = this.I.b();
      String _snowmanxxxxxxx = this.J.b();
      String _snowmanxxxxxxxx = this.K.b();
      String _snowmanxxxxxxxxx = this.L.b();
      this.b(_snowman, _snowman, _snowman);
      this.C.a(_snowman);
      this.D.a(_snowmanx);
      this.E.a(_snowmanxx);
      this.F.a(_snowmanxxx);
      this.G.a(_snowmanxxxx);
      this.H.a(_snowmanxxxxx);
      this.I.a(_snowmanxxxxxx);
      this.J.a(_snowmanxxxxxxx);
      this.K.a(_snowmanxxxxxxxx);
      this.L.a(_snowmanxxxxxxxxx);
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   private void k() {
      this.W.a(nq.a(!this.v.z()));
   }

   private void l() {
      this.Y.a(nq.a(this.v.H()));
   }

   private void m() {
      this.Z.a(nq.a(this.v.I()));
   }

   private void n() {
      byg _snowman = this.v.k();
      switch (_snowman) {
         case a:
            this.X.a(new oe("|"));
            break;
         case b:
            this.X.a(new oe("< >"));
            break;
         case c:
            this.X.a(new oe("^ v"));
      }
   }

   private void o() {
      this.Q.o = true;
      this.R.o = true;
      this.S.o = true;
      this.T.o = true;
      switch (this.v.l()) {
         case a:
            this.Q.o = false;
            break;
         case c:
            this.S.o = false;
            break;
         case d:
            this.T.o = false;
            break;
         case b:
            this.R.o = false;
      }
   }

   private void p() {
      this.C.i(false);
      this.D.i(false);
      this.E.i(false);
      this.F.i(false);
      this.G.i(false);
      this.H.i(false);
      this.I.i(false);
      this.J.i(false);
      this.K.i(false);
      this.L.i(false);
      this.O.p = false;
      this.P.p = false;
      this.V.p = false;
      this.W.p = false;
      this.X.p = false;
      this.Q.p = false;
      this.R.p = false;
      this.S.p = false;
      this.T.p = false;
      this.Y.p = false;
      this.Z.p = false;
      switch (this.v.x()) {
         case a:
            this.C.i(true);
            this.D.i(true);
            this.E.i(true);
            this.F.i(true);
            this.G.i(true);
            this.H.i(true);
            this.I.i(true);
            this.O.p = true;
            this.V.p = true;
            this.W.p = true;
            this.Y.p = true;
            break;
         case b:
            this.C.i(true);
            this.D.i(true);
            this.E.i(true);
            this.F.i(true);
            this.J.i(true);
            this.K.i(true);
            this.P.p = true;
            this.W.p = true;
            this.X.p = true;
            this.Q.p = true;
            this.R.p = true;
            this.S.p = true;
            this.T.p = true;
            this.Z.p = true;
            this.o();
            break;
         case c:
            this.C.i(true);
            break;
         case d:
            this.L.i(true);
      }

      this.U.a(new of("structure_block.mode." + this.v.x().a()));
   }

   private boolean a(cdj.a var1) {
      fx _snowman = new fx(this.d(this.D.b()), this.d(this.E.b()), this.d(this.F.b()));
      fx _snowmanx = new fx(this.d(this.G.b()), this.d(this.H.b()), this.d(this.I.b()));
      float _snowmanxx = this.c(this.J.b());
      long _snowmanxxx = this.b(this.K.b());
      this.i.w().a(new to(this.v.o(), _snowman, this.v.x(), this.C.b(), _snowman, _snowmanx, this.v.k(), this.v.l(), this.L.b(), this.v.z(), this.v.H(), this.v.I(), _snowmanxx, _snowmanxxx));
      return true;
   }

   private long b(String var1) {
      try {
         return Long.valueOf(_snowman);
      } catch (NumberFormatException var3) {
         return 0L;
      }
   }

   private float c(String var1) {
      try {
         return Float.valueOf(_snowman);
      } catch (NumberFormatException var3) {
         return 1.0F;
      }
   }

   private int d(String var1) {
      try {
         return Integer.parseInt(_snowman);
      } catch (NumberFormatException var3) {
         return 0;
      }
   }

   @Override
   public void at_() {
      this.i();
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (_snowman != 257 && _snowman != 335) {
         return false;
      } else {
         this.h();
         return true;
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      cfo _snowman = this.v.x();
      a(_snowman, this.o, this.d, this.k / 2, 10, 16777215);
      if (_snowman != cfo.d) {
         b(_snowman, this.o, a, this.k / 2 - 153, 30, 10526880);
         this.C.a(_snowman, _snowman, _snowman, _snowman);
      }

      if (_snowman == cfo.b || _snowman == cfo.a) {
         b(_snowman, this.o, b, this.k / 2 - 153, 70, 10526880);
         this.D.a(_snowman, _snowman, _snowman, _snowman);
         this.E.a(_snowman, _snowman, _snowman, _snowman);
         this.F.a(_snowman, _snowman, _snowman, _snowman);
         b(_snowman, this.o, r, this.k / 2 + 154 - this.o.a(r), 150, 10526880);
      }

      if (_snowman == cfo.a) {
         b(_snowman, this.o, c, this.k / 2 - 153, 110, 10526880);
         this.G.a(_snowman, _snowman, _snowman, _snowman);
         this.H.a(_snowman, _snowman, _snowman, _snowman);
         this.I.a(_snowman, _snowman, _snowman, _snowman);
         b(_snowman, this.o, s, this.k / 2 + 154 - this.o.a(s), 110, 10526880);
         b(_snowman, this.o, t, this.k / 2 + 154 - this.o.a(t), 70, 10526880);
      }

      if (_snowman == cfo.b) {
         b(_snowman, this.o, p, this.k / 2 - 153, 110, 10526880);
         this.J.a(_snowman, _snowman, _snowman, _snowman);
         this.K.a(_snowman, _snowman, _snowman, _snowman);
         b(_snowman, this.o, u, this.k / 2 + 154 - this.o.a(u), 70, 10526880);
      }

      if (_snowman == cfo.d) {
         b(_snowman, this.o, q, this.k / 2 - 153, 110, 10526880);
         this.L.a(_snowman, _snowman, _snowman, _snowman);
      }

      b(_snowman, this.o, _snowman.b(), this.k / 2 - 153, 174, 10526880);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean ay_() {
      return false;
   }
}
