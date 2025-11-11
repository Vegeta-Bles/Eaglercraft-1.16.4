import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;

public abstract class are extends azz {
   protected static final us<Byte> bo = uv.a(are.class, uu.a);
   protected static final us<Optional<UUID>> bp = uv.a(are.class, uu.o);
   private boolean bq;

   protected are(aqe<? extends are> var1, brx var2) {
      super(_snowman, _snowman);
      this.eL();
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bo, (byte)0);
      this.R.a(bp, Optional.empty());
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (this.A_() != null) {
         _snowman.a("Owner", this.A_());
      }

      _snowman.a("Sitting", this.bq);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      UUID _snowman;
      if (_snowman.b("Owner")) {
         _snowman = _snowman.a("Owner");
      } else {
         String _snowmanx = _snowman.l("Owner");
         _snowman = act.a(this.ch(), _snowmanx);
      }

      if (_snowman != null) {
         try {
            this.b(_snowman);
            this.u(true);
         } catch (Throwable var4) {
            this.u(false);
         }
      }

      this.bq = _snowman.q("Sitting");
      this.v(this.bq);
   }

   @Override
   public boolean a(bfw var1) {
      return !this.eB();
   }

   protected void t(boolean var1) {
      hf _snowman = hh.G;
      if (!_snowman) {
         _snowman = hh.S;
      }

      for (int _snowmanx = 0; _snowmanx < 7; _snowmanx++) {
         double _snowmanxx = this.J.nextGaussian() * 0.02;
         double _snowmanxxx = this.J.nextGaussian() * 0.02;
         double _snowmanxxxx = this.J.nextGaussian() * 0.02;
         this.l.a(_snowman, this.d(1.0), this.cF() + 0.5, this.g(1.0), _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 7) {
         this.t(true);
      } else if (_snowman == 6) {
         this.t(false);
      } else {
         super.a(_snowman);
      }
   }

   public boolean eK() {
      return (this.R.a(bo) & 4) != 0;
   }

   public void u(boolean var1) {
      byte _snowman = this.R.a(bo);
      if (_snowman) {
         this.R.b(bo, (byte)(_snowman | 4));
      } else {
         this.R.b(bo, (byte)(_snowman & -5));
      }

      this.eL();
   }

   protected void eL() {
   }

   public boolean eM() {
      return (this.R.a(bo) & 1) != 0;
   }

   public void v(boolean var1) {
      byte _snowman = this.R.a(bo);
      if (_snowman) {
         this.R.b(bo, (byte)(_snowman | 1));
      } else {
         this.R.b(bo, (byte)(_snowman & -2));
      }
   }

   @Nullable
   public UUID A_() {
      return this.R.a(bp).orElse(null);
   }

   public void b(@Nullable UUID var1) {
      this.R.b(bp, Optional.ofNullable(_snowman));
   }

   public void f(bfw var1) {
      this.u(true);
      this.b(_snowman.bS());
      if (_snowman instanceof aah) {
         ac.x.a((aah)_snowman, this);
      }
   }

   @Nullable
   public aqm eN() {
      try {
         UUID _snowman = this.A_();
         return _snowman == null ? null : this.l.b(_snowman);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   @Override
   public boolean c(aqm var1) {
      return this.i(_snowman) ? false : super.c(_snowman);
   }

   public boolean i(aqm var1) {
      return _snowman == this.eN();
   }

   public boolean a(aqm var1, aqm var2) {
      return true;
   }

   @Override
   public ddp bG() {
      if (this.eK()) {
         aqm _snowman = this.eN();
         if (_snowman != null) {
            return _snowman.bG();
         }
      }

      return super.bG();
   }

   @Override
   public boolean r(aqa var1) {
      if (this.eK()) {
         aqm _snowman = this.eN();
         if (_snowman == _snowman) {
            return true;
         }

         if (_snowman != null) {
            return _snowman.r(_snowman);
         }
      }

      return super.r(_snowman);
   }

   @Override
   public void a(apk var1) {
      if (!this.l.v && this.l.V().b(brt.l) && this.eN() instanceof aah) {
         this.eN().a(this.dv().b(), x.b);
      }

      super.a(_snowman);
   }

   public boolean eO() {
      return this.bq;
   }

   public void w(boolean var1) {
      this.bq = _snowman;
   }
}
