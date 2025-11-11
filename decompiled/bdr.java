import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public abstract class bdr extends bdq {
   private fx b;
   private boolean c;
   private boolean d;

   protected bdr(aqe<? extends bdr> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(4, new bdr.a<>(this, 0.7, 0.595));
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (this.b != null) {
         _snowman.a("PatrolTarget", mp.a(this.b));
      }

      _snowman.a("PatrolLeader", this.c);
      _snowman.a("Patrolling", this.d);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.e("PatrolTarget")) {
         this.b = mp.b(_snowman.p("PatrolTarget"));
      }

      this.c = _snowman.q("PatrolLeader");
      this.d = _snowman.q("Patrolling");
   }

   @Override
   public double bb() {
      return -0.45;
   }

   public boolean eN() {
      return true;
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      if (_snowman != aqp.p && _snowman != aqp.h && _snowman != aqp.d && this.J.nextFloat() < 0.06F && this.eN()) {
         this.c = true;
      }

      if (this.eS()) {
         this.a(aqf.f, bhb.s());
         this.a(aqf.f, 2.0F);
      }

      if (_snowman == aqp.p) {
         this.d = true;
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static boolean b(aqe<? extends bdr> var0, bry var1, aqp var2, fx var3, Random var4) {
      return _snowman.a(bsf.b, _snowman) > 8 ? false : c(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean h(double var1) {
      return !this.d || _snowman > 16384.0;
   }

   public void g(fx var1) {
      this.b = _snowman;
      this.d = true;
   }

   public fx eO() {
      return this.b;
   }

   public boolean eP() {
      return this.b != null;
   }

   public void t(boolean var1) {
      this.c = _snowman;
      this.d = true;
   }

   public boolean eS() {
      return this.c;
   }

   public boolean eT() {
      return true;
   }

   public void eU() {
      this.b = this.cB().b(-500 + this.J.nextInt(1000), 0, -500 + this.J.nextInt(1000));
      this.d = true;
   }

   protected boolean eV() {
      return this.d;
   }

   protected void u(boolean var1) {
      this.d = _snowman;
   }

   public static class a<T extends bdr> extends avv {
      private final T a;
      private final double b;
      private final double c;
      private long d;

      public a(T var1, double var2, double var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = -1L;
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         boolean _snowman = this.a.l.T() < this.d;
         return this.a.eV() && this.a.A() == null && !this.a.bs() && this.a.eP() && !_snowman;
      }

      @Override
      public void c() {
      }

      @Override
      public void d() {
      }

      @Override
      public void e() {
         boolean _snowman = this.a.eS();
         ayj _snowmanx = this.a.x();
         if (_snowmanx.m()) {
            List<bdr> _snowmanxx = this.g();
            if (this.a.eV() && _snowmanxx.isEmpty()) {
               this.a.u(false);
            } else if (_snowman && this.a.eO().a(this.a.cA(), 10.0)) {
               this.a.eU();
            } else {
               dcn _snowmanxxx = dcn.c(this.a.eO());
               dcn _snowmanxxxx = this.a.cA();
               dcn _snowmanxxxxx = _snowmanxxxx.d(_snowmanxxx);
               _snowmanxxx = _snowmanxxxxx.b(90.0F).a(0.4).e(_snowmanxxx);
               dcn _snowmanxxxxxx = _snowmanxxx.d(_snowmanxxxx).d().a(10.0).e(_snowmanxxxx);
               fx _snowmanxxxxxxx = new fx(_snowmanxxxxxx);
               _snowmanxxxxxxx = this.a.l.a(chn.a.f, _snowmanxxxxxxx);
               if (!_snowmanx.a((double)_snowmanxxxxxxx.u(), (double)_snowmanxxxxxxx.v(), (double)_snowmanxxxxxxx.w(), _snowman ? this.c : this.b)) {
                  this.h();
                  this.d = this.a.l.T() + 200L;
               } else if (_snowman) {
                  for (bdr _snowmanxxxxxxxx : _snowmanxx) {
                     _snowmanxxxxxxxx.g(_snowmanxxxxxxx);
                  }
               }
            }
         }
      }

      private List<bdr> g() {
         return this.a.l.a(bdr.class, this.a.cc().g(16.0), var1 -> var1.eT() && !var1.s(this.a));
      }

      private boolean h() {
         Random _snowman = this.a.cY();
         fx _snowmanx = this.a.l.a(chn.a.f, this.a.cB().b(-8 + _snowman.nextInt(16), 0, -8 + _snowman.nextInt(16)));
         return this.a.x().a((double)_snowmanx.u(), (double)_snowmanx.v(), (double)_snowmanx.w(), this.b);
      }
   }
}
