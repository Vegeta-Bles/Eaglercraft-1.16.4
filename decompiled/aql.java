import java.util.List;
import javax.annotation.Nullable;

public class aql extends aqa {
   private int c;
   public long b;
   private int d;
   private boolean e;
   @Nullable
   private aah f;

   public aql(aqe<? extends aql> var1, brx var2) {
      super(_snowman, _snowman);
      this.Y = true;
      this.c = 2;
      this.b = this.J.nextLong();
      this.d = this.J.nextInt(3) + 1;
   }

   public void a(boolean var1) {
      this.e = _snowman;
   }

   @Override
   public adr cu() {
      return adr.d;
   }

   public void d(@Nullable aah var1) {
      this.f = _snowman;
   }

   @Override
   public void j() {
      super.j();
      if (this.c == 2) {
         aor _snowman = this.l.ad();
         if (_snowman == aor.c || _snowman == aor.d) {
            this.a(4);
         }

         this.l.a(null, this.cD(), this.cE(), this.cH(), adq.hd, adr.d, 10000.0F, 0.8F + this.J.nextFloat() * 0.2F);
         this.l.a(null, this.cD(), this.cE(), this.cH(), adq.hc, adr.d, 2.0F, 0.5F + this.J.nextFloat() * 0.2F);
      }

      this.c--;
      if (this.c < 0) {
         if (this.d == 0) {
            this.ad();
         } else if (this.c < -this.J.nextInt(10)) {
            this.d--;
            this.c = 1;
            this.b = this.J.nextLong();
            this.a(0);
         }
      }

      if (this.c >= 0) {
         if (!(this.l instanceof aag)) {
            this.l.c(2);
         } else if (!this.e) {
            double _snowman = 3.0;
            List<aqa> _snowmanx = this.l
               .a(this, new dci(this.cD() - 3.0, this.cE() - 3.0, this.cH() - 3.0, this.cD() + 3.0, this.cE() + 6.0 + 3.0, this.cH() + 3.0), aqa::aX);

            for (aqa _snowmanxx : _snowmanx) {
               _snowmanxx.a((aag)this.l, this);
            }

            if (this.f != null) {
               ac.E.a(this.f, _snowmanx);
            }
         }
      }
   }

   private void a(int var1) {
      if (!this.e && !this.l.v && this.l.V().b(brt.a)) {
         fx _snowman = this.cB();
         ceh _snowmanx = bue.a(this.l, _snowman);
         if (this.l.d_(_snowman).g() && _snowmanx.a((brz)this.l, _snowman)) {
            this.l.a(_snowman, _snowmanx);
         }

         for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
            fx _snowmanxxx = _snowman.b(this.J.nextInt(3) - 1, this.J.nextInt(3) - 1, this.J.nextInt(3) - 1);
            _snowmanx = bue.a(this.l, _snowmanxxx);
            if (this.l.d_(_snowmanxxx).g() && _snowmanx.a((brz)this.l, _snowmanxxx)) {
               this.l.a(_snowmanxxx, _snowmanx);
            }
         }
      }
   }

   @Override
   public boolean a(double var1) {
      double _snowman = 64.0 * bW();
      return _snowman < _snowman * _snowman;
   }

   @Override
   protected void e() {
   }

   @Override
   protected void a(md var1) {
   }

   @Override
   protected void b(md var1) {
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }
}
