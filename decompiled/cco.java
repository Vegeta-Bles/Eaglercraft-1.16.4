import javax.annotation.Nullable;

public class cco extends ccj {
   private boolean a;
   private boolean b;
   private boolean c;
   private boolean g;
   private final bqy h = new bqy() {
      @Override
      public void a(String var1) {
         super.a(_snowman);
         cco.this.X_();
      }

      @Override
      public aag d() {
         return (aag)cco.this.d;
      }

      @Override
      public void e() {
         ceh _snowman = cco.this.d.d_(cco.this.e);
         this.d().a(cco.this.e, _snowman, _snowman, 3);
      }

      @Override
      public dcn f() {
         return dcn.a(cco.this.e);
      }

      @Override
      public db h() {
         return new db(this, dcn.a(cco.this.e), dcm.a, this.d(), 2, this.l().getString(), this.l(), this.d().l(), null);
      }
   };

   public cco() {
      super(cck.v);
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      this.h.a(_snowman);
      _snowman.a("powered", this.f());
      _snowman.a("conditionMet", this.j());
      _snowman.a("auto", this.g());
      return _snowman;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.h.b(_snowman);
      this.a = _snowman.q("powered");
      this.c = _snowman.q("conditionMet");
      this.b(_snowman.q("auto"));
   }

   @Nullable
   @Override
   public ow a() {
      if (this.l()) {
         this.c(false);
         md _snowman = this.a(new md());
         return new ow(this.e, 2, _snowman);
      } else {
         return null;
      }
   }

   @Override
   public boolean t() {
      return true;
   }

   public bqy d() {
      return this.h;
   }

   public void a(boolean var1) {
      this.a = _snowman;
   }

   public boolean f() {
      return this.a;
   }

   public boolean g() {
      return this.b;
   }

   public void b(boolean var1) {
      boolean _snowman = this.b;
      this.b = _snowman;
      if (!_snowman && _snowman && !this.a && this.d != null && this.m() != cco.a.a) {
         this.y();
      }
   }

   public void h() {
      cco.a _snowman = this.m();
      if (_snowman == cco.a.b && (this.a || this.b) && this.d != null) {
         this.y();
      }
   }

   private void y() {
      buo _snowman = this.p().b();
      if (_snowman instanceof bvi) {
         this.k();
         this.d.J().a(this.e, _snowman, 1);
      }
   }

   public boolean j() {
      return this.c;
   }

   public boolean k() {
      this.c = true;
      if (this.x()) {
         fx _snowman = this.e.a(this.d.d_(this.e).c(bvi.a).f());
         if (this.d.d_(_snowman).b() instanceof bvi) {
            ccj _snowmanx = this.d.c(_snowman);
            this.c = _snowmanx instanceof cco && ((cco)_snowmanx).d().i() > 0;
         } else {
            this.c = false;
         }
      }

      return this.c;
   }

   public boolean l() {
      return this.g;
   }

   public void c(boolean var1) {
      this.g = _snowman;
   }

   public cco.a m() {
      ceh _snowman = this.p();
      if (_snowman.a(bup.er)) {
         return cco.a.c;
      } else if (_snowman.a(bup.iG)) {
         return cco.a.b;
      } else {
         return _snowman.a(bup.iH) ? cco.a.a : cco.a.c;
      }
   }

   public boolean x() {
      ceh _snowman = this.d.d_(this.o());
      return _snowman.b() instanceof bvi ? _snowman.c(bvi.b) : false;
   }

   @Override
   public void r() {
      this.s();
      super.r();
   }

   public static enum a {
      a,
      b,
      c;

      private a() {
      }
   }
}
