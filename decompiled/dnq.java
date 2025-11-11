public class dnq extends dot {
   private String b = "";
   private int c = -1;
   protected dlq a;
   private String p = "";
   private dlm q;

   public dnq(String var1) {
      super(dkz.a);
      this.p = _snowman;
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      this.c = this.i.j.c().b().size();
      this.a = new dlq(this.o, 4, this.l - 12, this.k - 4, 12, new of("chat.editBox")) {
         @Override
         protected nx c() {
            return super.c().c(dnq.this.q.b());
         }
      };
      this.a.k(256);
      this.a.f(false);
      this.a.a(this.p);
      this.a.a(this::b);
      this.e.add(this.a);
      this.q = new dlm(this.i, this, this.a, this.o, false, false, 1, 10, true, -805306368);
      this.q.a();
      this.b(this.a);
   }

   @Override
   public void a(djz var1, int var2, int var3) {
      String _snowman = this.a.b();
      this.b(_snowman, _snowman, _snowman);
      this.c(_snowman);
      this.q.a();
   }

   @Override
   public void e() {
      this.i.m.a(false);
      this.i.j.c().c();
   }

   @Override
   public void d() {
      this.a.a();
   }

   private void b(String var1) {
      String _snowman = this.a.b();
      this.q.a(!_snowman.equals(this.p));
      this.q.a();
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (this.q.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (_snowman == 256) {
         this.i.a(null);
         return true;
      } else if (_snowman == 257 || _snowman == 335) {
         String _snowman = this.a.b().trim();
         if (!_snowman.isEmpty()) {
            this.b_(_snowman);
         }

         this.i.a(null);
         return true;
      } else if (_snowman == 265) {
         this.a(-1);
         return true;
      } else if (_snowman == 264) {
         this.a(1);
         return true;
      } else if (_snowman == 266) {
         this.i.j.c().a((double)(this.i.j.c().g() - 1));
         return true;
      } else if (_snowman == 267) {
         this.i.j.c().a((double)(-this.i.j.c().g() + 1));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean a(double var1, double var3, double var5) {
      if (_snowman > 1.0) {
         _snowman = 1.0;
      }

      if (_snowman < -1.0) {
         _snowman = -1.0;
      }

      if (this.q.a(_snowman)) {
         return true;
      } else {
         if (!y()) {
            _snowman *= 7.0;
         }

         this.i.j.c().a(_snowman);
         return true;
      }
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.q.a((double)((int)_snowman), (double)((int)_snowman), _snowman)) {
         return true;
      } else {
         if (_snowman == 0) {
            dlk _snowman = this.i.j.c();
            if (_snowman.a(_snowman, _snowman)) {
               return true;
            }

            ob _snowmanx = _snowman.b(_snowman, _snowman);
            if (_snowmanx != null && this.a(_snowmanx)) {
               return true;
            }
         }

         return this.a.a(_snowman, _snowman, _snowman) ? true : super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   protected void a(String var1, boolean var2) {
      if (_snowman) {
         this.a.a(_snowman);
      } else {
         this.a.b(_snowman);
      }
   }

   public void a(int var1) {
      int _snowman = this.c + _snowman;
      int _snowmanx = this.i.j.c().b().size();
      _snowman = afm.a(_snowman, 0, _snowmanx);
      if (_snowman != this.c) {
         if (_snowman == _snowmanx) {
            this.c = _snowmanx;
            this.a.a(this.b);
         } else {
            if (this.c == _snowmanx) {
               this.b = this.a.b();
            }

            this.a.a(this.i.j.c().b().get(_snowman));
            this.q.a(false);
            this.c = _snowman;
         }
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(this.a);
      this.a.e(true);
      a(_snowman, 2, this.l - 14, this.k - 2, this.l - 2, this.i.k.a(Integer.MIN_VALUE));
      this.a.a(_snowman, _snowman, _snowman, _snowman);
      this.q.a(_snowman, _snowman, _snowman);
      ob _snowman = this.i.j.c().b((double)_snowman, (double)_snowman);
      if (_snowman != null && _snowman.i() != null) {
         this.a(_snowman, _snowman, _snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean ay_() {
      return false;
   }

   private void c(String var1) {
      this.a.a(_snowman);
   }
}
