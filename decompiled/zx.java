public class zx extends aai {
   private boolean c;
   private boolean d;
   private int e;
   private int f;

   public zx(aag var1) {
      super(_snowman);
   }

   @Override
   public void a() {
      super.a();
      this.f++;
      long _snowman = this.a.T();
      long _snowmanx = _snowman / 24000L + 1L;
      if (!this.c && this.f > 20) {
         this.c = true;
         this.b.b.a(new pq(pq.f, 0.0F));
      }

      this.d = _snowman > 120500L;
      if (this.d) {
         this.e++;
      }

      if (_snowman % 24000L == 500L) {
         if (_snowmanx <= 6L) {
            if (_snowmanx == 6L) {
               this.b.b.a(new pq(pq.f, 104.0F));
            } else {
               this.b.a(new of("demo.day." + _snowmanx), x.b);
            }
         }
      } else if (_snowmanx == 1L) {
         if (_snowman == 100L) {
            this.b.b.a(new pq(pq.f, 101.0F));
         } else if (_snowman == 175L) {
            this.b.b.a(new pq(pq.f, 102.0F));
         } else if (_snowman == 250L) {
            this.b.b.a(new pq(pq.f, 103.0F));
         }
      } else if (_snowmanx == 5L && _snowman % 24000L == 22000L) {
         this.b.a(new of("demo.day.warning"), x.b);
      }
   }

   private void f() {
      if (this.e > 100) {
         this.b.a(new of("demo.reminder"), x.b);
         this.e = 0;
      }
   }

   @Override
   public void a(fx var1, sz.a var2, gc var3, int var4) {
      if (this.d) {
         this.f();
      } else {
         super.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public aou a(aah var1, brx var2, bmb var3, aot var4) {
      if (this.d) {
         this.f();
         return aou.c;
      } else {
         return super.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public aou a(aah var1, brx var2, bmb var3, aot var4, dcj var5) {
      if (this.d) {
         this.f();
         return aou.c;
      } else {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
