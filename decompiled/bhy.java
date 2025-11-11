public class bhy {
   private int a = 20;
   private float b;
   private float c;
   private int d;
   private int e = 20;

   public bhy() {
      this.b = 5.0F;
   }

   public void a(int var1, float var2) {
      this.a = Math.min(_snowman + this.a, 20);
      this.b = Math.min(this.b + (float)_snowman * _snowman * 2.0F, (float)this.a);
   }

   public void a(blx var1, bmb var2) {
      if (_snowman.s()) {
         bhz _snowman = _snowman.t();
         this.a(_snowman.a(), _snowman.b());
      }
   }

   public void a(bfw var1) {
      aor _snowman = _snowman.l.ad();
      this.e = this.a;
      if (this.c > 4.0F) {
         this.c -= 4.0F;
         if (this.b > 0.0F) {
            this.b = Math.max(this.b - 1.0F, 0.0F);
         } else if (_snowman != aor.a) {
            this.a = Math.max(this.a - 1, 0);
         }
      }

      boolean _snowmanx = _snowman.l.V().b(brt.i);
      if (_snowmanx && this.b > 0.0F && _snowman.eJ() && this.a >= 20) {
         this.d++;
         if (this.d >= 10) {
            float _snowmanxx = Math.min(this.b, 6.0F);
            _snowman.b(_snowmanxx / 6.0F);
            this.a(_snowmanxx);
            this.d = 0;
         }
      } else if (_snowmanx && this.a >= 18 && _snowman.eJ()) {
         this.d++;
         if (this.d >= 80) {
            _snowman.b(1.0F);
            this.a(6.0F);
            this.d = 0;
         }
      } else if (this.a <= 0) {
         this.d++;
         if (this.d >= 80) {
            if (_snowman.dk() > 10.0F || _snowman == aor.d || _snowman.dk() > 1.0F && _snowman == aor.c) {
               _snowman.a(apk.i, 1.0F);
            }

            this.d = 0;
         }
      } else {
         this.d = 0;
      }
   }

   public void a(md var1) {
      if (_snowman.c("foodLevel", 99)) {
         this.a = _snowman.h("foodLevel");
         this.d = _snowman.h("foodTickTimer");
         this.b = _snowman.j("foodSaturationLevel");
         this.c = _snowman.j("foodExhaustionLevel");
      }
   }

   public void b(md var1) {
      _snowman.b("foodLevel", this.a);
      _snowman.b("foodTickTimer", this.d);
      _snowman.a("foodSaturationLevel", this.b);
      _snowman.a("foodExhaustionLevel", this.c);
   }

   public int a() {
      return this.a;
   }

   public boolean c() {
      return this.a < 20;
   }

   public void a(float var1) {
      this.c = Math.min(this.c + _snowman, 40.0F);
   }

   public float e() {
      return this.b;
   }

   public void a(int var1) {
      this.a = _snowman;
   }

   public void b(float var1) {
      this.b = _snowman;
   }
}
