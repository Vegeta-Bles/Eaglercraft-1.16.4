import java.util.UUID;
import javax.annotation.Nullable;

public class bge extends aqa {
   private int b;
   private boolean c;
   private int d = 22;
   private boolean e;
   private aqm f;
   private UUID g;

   public bge(aqe<? extends bge> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bge(brx var1, double var2, double var4, double var6, float var8, int var9, aqm var10) {
      this(aqe.x, _snowman);
      this.b = _snowman;
      this.a(_snowman);
      this.p = _snowman * (180.0F / (float)Math.PI);
      this.d(_snowman, _snowman, _snowman);
   }

   @Override
   protected void e() {
   }

   public void a(@Nullable aqm var1) {
      this.f = _snowman;
      this.g = _snowman == null ? null : _snowman.bS();
   }

   @Nullable
   public aqm g() {
      if (this.f == null && this.g != null && this.l instanceof aag) {
         aqa _snowman = ((aag)this.l).a(this.g);
         if (_snowman instanceof aqm) {
            this.f = (aqm)_snowman;
         }
      }

      return this.f;
   }

   @Override
   protected void a(md var1) {
      this.b = _snowman.h("Warmup");
      if (_snowman.b("Owner")) {
         this.g = _snowman.a("Owner");
      }
   }

   @Override
   protected void b(md var1) {
      _snowman.b("Warmup", this.b);
      if (this.g != null) {
         _snowman.a("Owner", this.g);
      }
   }

   @Override
   public void j() {
      super.j();
      if (this.l.v) {
         if (this.e) {
            this.d--;
            if (this.d == 14) {
               for (int _snowman = 0; _snowman < 12; _snowman++) {
                  double _snowmanx = this.cD() + (this.J.nextDouble() * 2.0 - 1.0) * (double)this.cy() * 0.5;
                  double _snowmanxx = this.cE() + 0.05 + this.J.nextDouble();
                  double _snowmanxxx = this.cH() + (this.J.nextDouble() * 2.0 - 1.0) * (double)this.cy() * 0.5;
                  double _snowmanxxxx = (this.J.nextDouble() * 2.0 - 1.0) * 0.3;
                  double _snowmanxxxxx = 0.3 + this.J.nextDouble() * 0.3;
                  double _snowmanxxxxxx = (this.J.nextDouble() * 2.0 - 1.0) * 0.3;
                  this.l.a(hh.g, _snowmanx, _snowmanxx + 1.0, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
               }
            }
         }
      } else if (--this.b < 0) {
         if (this.b == -8) {
            for (aqm _snowman : this.l.a(aqm.class, this.cc().c(0.2, 0.0, 0.2))) {
               this.c(_snowman);
            }
         }

         if (!this.c) {
            this.l.a(this, (byte)4);
            this.c = true;
         }

         if (--this.d < 0) {
            this.ad();
         }
      }
   }

   private void c(aqm var1) {
      aqm _snowman = this.g();
      if (_snowman.aX() && !_snowman.bM() && _snowman != _snowman) {
         if (_snowman == null) {
            _snowman.a(apk.o, 6.0F);
         } else {
            if (_snowman.r(_snowman)) {
               return;
            }

            _snowman.a(apk.c(this, _snowman), 6.0F);
         }
      }
   }

   @Override
   public void a(byte var1) {
      super.a(_snowman);
      if (_snowman == 4) {
         this.e = true;
         if (!this.aA()) {
            this.l.a(this.cD(), this.cE(), this.cH(), adq.dQ, this.cu(), 1.0F, this.J.nextFloat() * 0.2F + 0.85F, false);
         }
      }
   }

   public float a(float var1) {
      if (!this.e) {
         return 0.0F;
      } else {
         int _snowman = this.d - 2;
         return _snowman <= 0 ? 1.0F : 1.0F - ((float)_snowman - _snowman) / 20.0F;
      }
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }
}
