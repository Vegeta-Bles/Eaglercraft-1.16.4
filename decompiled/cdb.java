import javax.annotation.Nullable;

public class cdb extends ccj implements aol, aox {
   private final aon a = new aon() {
      @Override
      public int Z_() {
         return 1;
      }

      @Override
      public boolean c() {
         return cdb.this.c.a();
      }

      @Override
      public bmb a(int var1) {
         return _snowman == 0 ? cdb.this.c : bmb.b;
      }

      @Override
      public bmb a(int var1, int var2) {
         if (_snowman == 0) {
            bmb _snowman = cdb.this.c.a(_snowman);
            if (cdb.this.c.a()) {
               cdb.this.k();
            }

            return _snowman;
         } else {
            return bmb.b;
         }
      }

      @Override
      public bmb b(int var1) {
         if (_snowman == 0) {
            bmb _snowman = cdb.this.c;
            cdb.this.c = bmb.b;
            cdb.this.k();
            return _snowman;
         } else {
            return bmb.b;
         }
      }

      @Override
      public void a(int var1, bmb var2) {
      }

      @Override
      public int V_() {
         return 1;
      }

      @Override
      public void X_() {
         cdb.this.X_();
      }

      @Override
      public boolean a(bfw var1) {
         if (cdb.this.d.c(cdb.this.e) != cdb.this) {
            return false;
         } else {
            return _snowman.h((double)cdb.this.e.u() + 0.5, (double)cdb.this.e.v() + 0.5, (double)cdb.this.e.w() + 0.5) > 64.0 ? false : cdb.this.g();
         }
      }

      @Override
      public boolean b(int var1, bmb var2) {
         return false;
      }

      @Override
      public void Y_() {
      }
   };
   private final bil b = new bil() {
      @Override
      public int a(int var1) {
         return _snowman == 0 ? cdb.this.g : 0;
      }

      @Override
      public void a(int var1, int var2) {
         if (_snowman == 0) {
            cdb.this.a(_snowman);
         }
      }

      @Override
      public int a() {
         return 1;
      }
   };
   private bmb c = bmb.b;
   private int g;
   private int h;

   public cdb() {
      super(cck.C);
   }

   public bmb f() {
      return this.c;
   }

   public boolean g() {
      blx _snowman = this.c.b();
      return _snowman == bmd.oT || _snowman == bmd.oU;
   }

   public void a(bmb var1) {
      this.a(_snowman, null);
   }

   private void k() {
      this.g = 0;
      this.h = 0;
      bxy.a(this.v(), this.o(), this.p(), false);
   }

   public void a(bmb var1, @Nullable bfw var2) {
      this.c = this.b(_snowman, _snowman);
      this.g = 0;
      this.h = bns.g(this.c);
      this.X_();
   }

   private void a(int var1) {
      int _snowman = afm.a(_snowman, 0, this.h - 1);
      if (_snowman != this.g) {
         this.g = _snowman;
         this.X_();
         bxy.a(this.v(), this.o(), this.p());
      }
   }

   public int h() {
      return this.g;
   }

   public int j() {
      float _snowman = this.h > 1 ? (float)this.h() / ((float)this.h - 1.0F) : 1.0F;
      return afm.d(_snowman * 14.0F) + (this.g() ? 1 : 0);
   }

   private bmb b(bmb var1, @Nullable bfw var2) {
      if (this.d instanceof aag && _snowman.b() == bmd.oU) {
         bns.a(_snowman, this.a(_snowman), _snowman);
      }

      return _snowman;
   }

   private db a(@Nullable bfw var1) {
      String _snowman;
      nr _snowmanx;
      if (_snowman == null) {
         _snowman = "Lectern";
         _snowmanx = new oe("Lectern");
      } else {
         _snowman = _snowman.R().getString();
         _snowmanx = _snowman.d();
      }

      dcn _snowmanxx = dcn.a(this.e);
      return new db(da.a_, _snowmanxx, dcm.a, (aag)this.d, 2, _snowman, _snowmanx, this.d.l(), _snowman);
   }

   @Override
   public boolean t() {
      return true;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      if (_snowman.c("Book", 10)) {
         this.c = this.b(bmb.a(_snowman.p("Book")), null);
      } else {
         this.c = bmb.b;
      }

      this.h = bns.g(this.c);
      this.g = afm.a(_snowman.h("Page"), 0, this.h - 1);
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      if (!this.f().a()) {
         _snowman.a("Book", this.f().b(new md()));
         _snowman.b("Page", this.g);
      }

      return _snowman;
   }

   @Override
   public void Y_() {
      this.a(bmb.b);
   }

   @Override
   public bic createMenu(int var1, bfv var2, bfw var3) {
      return new bjb(_snowman, this.a, this.b);
   }

   @Override
   public nr d() {
      return new of("container.lectern");
   }
}
