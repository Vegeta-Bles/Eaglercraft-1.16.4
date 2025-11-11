import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;

public abstract class bga extends bgm {
   private static final us<Byte> f = uv.a(bga.class, uu.a);
   private static final us<Byte> g = uv.a(bga.class, uu.a);
   @Nullable
   private ceh ag;
   protected boolean b;
   protected int c;
   public bga.a d = bga.a.a;
   public int e;
   private int ah;
   private double ai = 2.0;
   private int aj;
   private adp ak = this.i();
   private IntOpenHashSet al;
   private List<aqa> am;

   protected bga(aqe<? extends bga> var1, brx var2) {
      super(_snowman, _snowman);
   }

   protected bga(aqe<? extends bga> var1, double var2, double var4, double var6, brx var8) {
      this(_snowman, _snowman);
      this.d(_snowman, _snowman, _snowman);
   }

   protected bga(aqe<? extends bga> var1, aqm var2, brx var3) {
      this(_snowman, _snowman.cD(), _snowman.cG() - 0.1F, _snowman.cH(), _snowman);
      this.b(_snowman);
      if (_snowman instanceof bfw) {
         this.d = bga.a.b;
      }
   }

   public void a(adp var1) {
      this.ak = _snowman;
   }

   @Override
   public boolean a(double var1) {
      double _snowman = this.cc().a() * 10.0;
      if (Double.isNaN(_snowman)) {
         _snowman = 1.0;
      }

      _snowman *= 64.0 * bW();
      return _snowman < _snowman * _snowman;
   }

   @Override
   protected void e() {
      this.R.a(f, (byte)0);
      this.R.a(g, (byte)0);
   }

   @Override
   public void c(double var1, double var3, double var5, float var7, float var8) {
      super.c(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.ah = 0;
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.d(_snowman, _snowman, _snowman);
      this.a(_snowman, _snowman);
   }

   @Override
   public void k(double var1, double var3, double var5) {
      super.k(_snowman, _snowman, _snowman);
      this.ah = 0;
   }

   @Override
   public void j() {
      super.j();
      boolean _snowman = this.t();
      dcn _snowmanx = this.cC();
      if (this.s == 0.0F && this.r == 0.0F) {
         float _snowmanxx = afm.a(c(_snowmanx));
         this.p = (float)(afm.d(_snowmanx.b, _snowmanx.d) * 180.0F / (float)Math.PI);
         this.q = (float)(afm.d(_snowmanx.c, (double)_snowmanxx) * 180.0F / (float)Math.PI);
         this.r = this.p;
         this.s = this.q;
      }

      fx _snowmanxx = this.cB();
      ceh _snowmanxxx = this.l.d_(_snowmanxx);
      if (!_snowmanxxx.g() && !_snowman) {
         ddh _snowmanxxxx = _snowmanxxx.k(this.l, _snowmanxx);
         if (!_snowmanxxxx.b()) {
            dcn _snowmanxxxxx = this.cA();

            for (dci _snowmanxxxxxx : _snowmanxxxx.d()) {
               if (_snowmanxxxxxx.a(_snowmanxx).d(_snowmanxxxxx)) {
                  this.b = true;
                  break;
               }
            }
         }
      }

      if (this.e > 0) {
         this.e--;
      }

      if (this.aF()) {
         this.am();
      }

      if (this.b && !_snowman) {
         if (this.ag != _snowmanxxx && this.u()) {
            this.z();
         } else if (!this.l.v) {
            this.h();
         }

         this.c++;
      } else {
         this.c = 0;
         dcn _snowmanxxxx = this.cA();
         dcn _snowmanxxxxx = _snowmanxxxx.e(_snowmanx);
         dcl _snowmanxxxxxxx = this.l.a(new brf(_snowmanxxxx, _snowmanxxxxx, brf.a.a, brf.b.a, this));
         if (_snowmanxxxxxxx.c() != dcl.a.a) {
            _snowmanxxxxx = _snowmanxxxxxxx.e();
         }

         while (!this.y) {
            dck _snowmanxxxxxxxx = this.a(_snowmanxxxx, _snowmanxxxxx);
            if (_snowmanxxxxxxxx != null) {
               _snowmanxxxxxxx = _snowmanxxxxxxxx;
            }

            if (_snowmanxxxxxxx != null && _snowmanxxxxxxx.c() == dcl.a.c) {
               aqa _snowmanxxxxxxxxx = ((dck)_snowmanxxxxxxx).a();
               aqa _snowmanxxxxxxxxxx = this.v();
               if (_snowmanxxxxxxxxx instanceof bfw && _snowmanxxxxxxxxxx instanceof bfw && !((bfw)_snowmanxxxxxxxxxx).a((bfw)_snowmanxxxxxxxxx)) {
                  _snowmanxxxxxxx = null;
                  _snowmanxxxxxxxx = null;
               }
            }

            if (_snowmanxxxxxxx != null && !_snowman) {
               this.a(_snowmanxxxxxxx);
               this.Z = true;
            }

            if (_snowmanxxxxxxxx == null || this.r() <= 0) {
               break;
            }

            _snowmanxxxxxxx = null;
         }

         _snowmanx = this.cC();
         double _snowmanxxxxxxxxx = _snowmanx.b;
         double _snowmanxxxxxxxxxx = _snowmanx.c;
         double _snowmanxxxxxxxxxxx = _snowmanx.d;
         if (this.p()) {
            for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxx++) {
               this.l
                  .a(
                     hh.g,
                     this.cD() + _snowmanxxxxxxxxx * (double)_snowmanxxxxxxxxxxxx / 4.0,
                     this.cE() + _snowmanxxxxxxxxxx * (double)_snowmanxxxxxxxxxxxx / 4.0,
                     this.cH() + _snowmanxxxxxxxxxxx * (double)_snowmanxxxxxxxxxxxx / 4.0,
                     -_snowmanxxxxxxxxx,
                     -_snowmanxxxxxxxxxx + 0.2,
                     -_snowmanxxxxxxxxxxx
                  );
            }
         }

         double _snowmanxxxxxxxxxxxx = this.cD() + _snowmanxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxx = this.cE() + _snowmanxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxx = this.cH() + _snowmanxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxxx = afm.a(c(_snowmanx));
         if (_snowman) {
            this.p = (float)(afm.d(-_snowmanxxxxxxxxx, -_snowmanxxxxxxxxxxx) * 180.0F / (float)Math.PI);
         } else {
            this.p = (float)(afm.d(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx) * 180.0F / (float)Math.PI);
         }

         this.q = (float)(afm.d(_snowmanxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxx) * 180.0F / (float)Math.PI);
         this.q = e(this.s, this.q);
         this.p = e(this.r, this.p);
         float _snowmanxxxxxxxxxxxxxxxx = 0.99F;
         float _snowmanxxxxxxxxxxxxxxxxx = 0.05F;
         if (this.aE()) {
            for (int _snowmanxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxxxxxxx++) {
               float _snowmanxxxxxxxxxxxxxxxxxxx = 0.25F;
               this.l
                  .a(
                     hh.e,
                     _snowmanxxxxxxxxxxxx - _snowmanxxxxxxxxx * 0.25,
                     _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxxx * 0.25,
                     _snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxx * 0.25,
                     _snowmanxxxxxxxxx,
                     _snowmanxxxxxxxxxx,
                     _snowmanxxxxxxxxxxx
                  );
            }

            _snowmanxxxxxxxxxxxxxxxx = this.s();
         }

         this.f(_snowmanx.a((double)_snowmanxxxxxxxxxxxxxxxx));
         if (!this.aB() && !_snowman) {
            dcn _snowmanxxxxxxxxxxxxxxxxxx = this.cC();
            this.n(_snowmanxxxxxxxxxxxxxxxxxx.b, _snowmanxxxxxxxxxxxxxxxxxx.c - 0.05F, _snowmanxxxxxxxxxxxxxxxxxx.d);
         }

         this.d(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
         this.ay();
      }
   }

   private boolean u() {
      return this.b && this.l.b(new dci(this.cA(), this.cA()).g(0.06));
   }

   private void z() {
      this.b = false;
      dcn _snowman = this.cC();
      this.f(_snowman.d((double)(this.J.nextFloat() * 0.2F), (double)(this.J.nextFloat() * 0.2F), (double)(this.J.nextFloat() * 0.2F)));
      this.ah = 0;
   }

   @Override
   public void a(aqr var1, dcn var2) {
      super.a(_snowman, _snowman);
      if (_snowman != aqr.a && this.u()) {
         this.z();
      }
   }

   protected void h() {
      this.ah++;
      if (this.ah >= 1200) {
         this.ad();
      }
   }

   private void A() {
      if (this.am != null) {
         this.am.clear();
      }

      if (this.al != null) {
         this.al.clear();
      }
   }

   @Override
   protected void a(dck var1) {
      super.a(_snowman);
      aqa _snowman = _snowman.a();
      float _snowmanx = (float)this.cC().f();
      int _snowmanxx = afm.f(afm.a((double)_snowmanx * this.ai, 0.0, 2.147483647E9));
      if (this.r() > 0) {
         if (this.al == null) {
            this.al = new IntOpenHashSet(5);
         }

         if (this.am == null) {
            this.am = Lists.newArrayListWithCapacity(5);
         }

         if (this.al.size() >= this.r() + 1) {
            this.ad();
            return;
         }

         this.al.add(_snowman.Y());
      }

      if (this.p()) {
         long _snowmanxxx = (long)this.J.nextInt(_snowmanxx / 2 + 2);
         _snowmanxx = (int)Math.min(_snowmanxxx + (long)_snowmanxx, 2147483647L);
      }

      aqa _snowmanxxx = this.v();
      apk _snowmanxxxx;
      if (_snowmanxxx == null) {
         _snowmanxxxx = apk.a(this, this);
      } else {
         _snowmanxxxx = apk.a(this, _snowmanxxx);
         if (_snowmanxxx instanceof aqm) {
            ((aqm)_snowmanxxx).z(_snowman);
         }
      }

      boolean _snowmanxxxxx = _snowman.X() == aqe.u;
      int _snowmanxxxxxx = _snowman.al();
      if (this.bq() && !_snowmanxxxxx) {
         _snowman.f(5);
      }

      if (_snowman.a(_snowmanxxxx, (float)_snowmanxx)) {
         if (_snowmanxxxxx) {
            return;
         }

         if (_snowman instanceof aqm) {
            aqm _snowmanxxxxxxx = (aqm)_snowman;
            if (!this.l.v && this.r() <= 0) {
               _snowmanxxxxxxx.p(_snowmanxxxxxxx.dy() + 1);
            }

            if (this.aj > 0) {
               dcn _snowmanxxxxxxxx = this.cC().d(1.0, 0.0, 1.0).d().a((double)this.aj * 0.6);
               if (_snowmanxxxxxxxx.g() > 0.0) {
                  _snowmanxxxxxxx.i(_snowmanxxxxxxxx.b, 0.1, _snowmanxxxxxxxx.d);
               }
            }

            if (!this.l.v && _snowmanxxx instanceof aqm) {
               bpu.a(_snowmanxxxxxxx, _snowmanxxx);
               bpu.b((aqm)_snowmanxxx, _snowmanxxxxxxx);
            }

            this.a(_snowmanxxxxxxx);
            if (_snowmanxxx != null && _snowmanxxxxxxx != _snowmanxxx && _snowmanxxxxxxx instanceof bfw && _snowmanxxx instanceof aah && !this.aA()) {
               ((aah)_snowmanxxx).b.a(new pq(pq.g, 0.0F));
            }

            if (!_snowman.aX() && this.am != null) {
               this.am.add(_snowmanxxxxxxx);
            }

            if (!this.l.v && _snowmanxxx instanceof aah) {
               aah _snowmanxxxxxxxx = (aah)_snowmanxxx;
               if (this.am != null && this.q()) {
                  ac.G.a(_snowmanxxxxxxxx, this.am);
               } else if (!_snowman.aX() && this.q()) {
                  ac.G.a(_snowmanxxxxxxxx, Arrays.asList(_snowman));
               }
            }
         }

         this.a(this.ak, 1.0F, 1.2F / (this.J.nextFloat() * 0.2F + 0.9F));
         if (this.r() <= 0) {
            this.ad();
         }
      } else {
         _snowman.g(_snowmanxxxxxx);
         this.f(this.cC().a(-0.1));
         this.p += 180.0F;
         this.r += 180.0F;
         if (!this.l.v && this.cC().g() < 1.0E-7) {
            if (this.d == bga.a.b) {
               this.a(this.m(), 0.1F);
            }

            this.ad();
         }
      }
   }

   @Override
   protected void a(dcj var1) {
      this.ag = this.l.d_(_snowman.a());
      super.a(_snowman);
      dcn _snowman = _snowman.e().a(this.cD(), this.cE(), this.cH());
      this.f(_snowman);
      dcn _snowmanx = _snowman.d().a(0.05F);
      this.o(this.cD() - _snowmanx.b, this.cE() - _snowmanx.c, this.cH() - _snowmanx.d);
      this.a(this.k(), 1.0F, 1.2F / (this.J.nextFloat() * 0.2F + 0.9F));
      this.b = true;
      this.e = 7;
      this.a(false);
      this.b((byte)0);
      this.a(adq.W);
      this.p(false);
      this.A();
   }

   protected adp i() {
      return adq.W;
   }

   protected final adp k() {
      return this.ak;
   }

   protected void a(aqm var1) {
   }

   @Nullable
   protected dck a(dcn var1, dcn var2) {
      return bgn.a(this.l, this, _snowman, _snowman, this.cc().b(this.cC()).g(1.0), this::a);
   }

   @Override
   protected boolean a(aqa var1) {
      return super.a(_snowman) && (this.al == null || !this.al.contains(_snowman.Y()));
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("life", (short)this.ah);
      if (this.ag != null) {
         _snowman.a("inBlockState", mp.a(this.ag));
      }

      _snowman.a("shake", (byte)this.e);
      _snowman.a("inGround", this.b);
      _snowman.a("pickup", (byte)this.d.ordinal());
      _snowman.a("damage", this.ai);
      _snowman.a("crit", this.p());
      _snowman.a("PierceLevel", this.r());
      _snowman.a("SoundEvent", gm.N.b(this.ak).toString());
      _snowman.a("ShotFromCrossbow", this.q());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.ah = _snowman.g("life");
      if (_snowman.c("inBlockState", 10)) {
         this.ag = mp.c(_snowman.p("inBlockState"));
      }

      this.e = _snowman.f("shake") & 255;
      this.b = _snowman.q("inGround");
      if (_snowman.c("damage", 99)) {
         this.ai = _snowman.k("damage");
      }

      if (_snowman.c("pickup", 99)) {
         this.d = bga.a.a(_snowman.f("pickup"));
      } else if (_snowman.c("player", 99)) {
         this.d = _snowman.q("player") ? bga.a.b : bga.a.a;
      }

      this.a(_snowman.q("crit"));
      this.b(_snowman.f("PierceLevel"));
      if (_snowman.c("SoundEvent", 8)) {
         this.ak = gm.N.b(new vk(_snowman.l("SoundEvent"))).orElse(this.i());
      }

      this.p(_snowman.q("ShotFromCrossbow"));
   }

   @Override
   public void b(@Nullable aqa var1) {
      super.b(_snowman);
      if (_snowman instanceof bfw) {
         this.d = ((bfw)_snowman).bC.d ? bga.a.c : bga.a.b;
      }
   }

   @Override
   public void a_(bfw var1) {
      if (!this.l.v && (this.b || this.t()) && this.e <= 0) {
         boolean _snowman = this.d == bga.a.b || this.d == bga.a.c && _snowman.bC.d || this.t() && this.v().bS() == _snowman.bS();
         if (this.d == bga.a.b && !_snowman.bm.e(this.m())) {
            _snowman = false;
         }

         if (_snowman) {
            _snowman.a(this, 1);
            this.ad();
         }
      }
   }

   protected abstract bmb m();

   @Override
   protected boolean aC() {
      return false;
   }

   public void h(double var1) {
      this.ai = _snowman;
   }

   public double n() {
      return this.ai;
   }

   public void a(int var1) {
      this.aj = _snowman;
   }

   @Override
   public boolean bL() {
      return false;
   }

   @Override
   protected float a(aqx var1, aqb var2) {
      return 0.13F;
   }

   public void a(boolean var1) {
      this.a(1, _snowman);
   }

   public void b(byte var1) {
      this.R.b(g, _snowman);
   }

   private void a(int var1, boolean var2) {
      byte _snowman = this.R.a(f);
      if (_snowman) {
         this.R.b(f, (byte)(_snowman | _snowman));
      } else {
         this.R.b(f, (byte)(_snowman & ~_snowman));
      }
   }

   public boolean p() {
      byte _snowman = this.R.a(f);
      return (_snowman & 1) != 0;
   }

   public boolean q() {
      byte _snowman = this.R.a(f);
      return (_snowman & 4) != 0;
   }

   public byte r() {
      return this.R.a(g);
   }

   public void a(aqm var1, float var2) {
      int _snowman = bpu.a(bpw.x, _snowman);
      int _snowmanx = bpu.a(bpw.y, _snowman);
      this.h((double)(_snowman * 2.0F) + this.J.nextGaussian() * 0.25 + (double)((float)this.l.ad().a() * 0.11F));
      if (_snowman > 0) {
         this.h(this.n() + (double)_snowman * 0.5 + 0.5);
      }

      if (_snowmanx > 0) {
         this.a(_snowmanx);
      }

      if (bpu.a(bpw.z, _snowman) > 0) {
         this.f(100);
      }
   }

   protected float s() {
      return 0.6F;
   }

   public void o(boolean var1) {
      this.H = _snowman;
      this.a(2, _snowman);
   }

   public boolean t() {
      return !this.l.v ? this.H : (this.R.a(f) & 2) != 0;
   }

   public void p(boolean var1) {
      this.a(4, _snowman);
   }

   @Override
   public oj<?> P() {
      aqa _snowman = this.v();
      return new on(this, _snowman == null ? 0 : _snowman.Y());
   }

   public static enum a {
      a,
      b,
      c;

      private a() {
      }

      public static bga.a a(int var0) {
         if (_snowman < 0 || _snowman > values().length) {
            _snowman = 0;
         }

         return values()[_snowman];
      }
   }
}
