import javax.annotation.Nullable;

public abstract class bhm extends bhl implements aon, aox {
   private gj<bmb> b = gj.a(36, bmb.b);
   private boolean c = true;
   @Nullable
   private vk d;
   private long e;

   protected bhm(aqe<?> var1, brx var2) {
      super(_snowman, _snowman);
   }

   protected bhm(aqe<?> var1, double var2, double var4, double var6, brx var8) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(apk var1) {
      super.a(_snowman);
      if (this.l.V().b(brt.g)) {
         aoq.a(this.l, this, this);
         if (!this.l.v) {
            aqa _snowman = _snowman.j();
            if (_snowman != null && _snowman.X() == aqe.bc) {
               bet.a((bfw)_snowman, true);
            }
         }
      }
   }

   @Override
   public boolean c() {
      for (bmb _snowman : this.b) {
         if (!_snowman.a()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public bmb a(int var1) {
      this.d(null);
      return this.b.get(_snowman);
   }

   @Override
   public bmb a(int var1, int var2) {
      this.d(null);
      return aoo.a(this.b, _snowman, _snowman);
   }

   @Override
   public bmb b(int var1) {
      this.d(null);
      bmb _snowman = this.b.get(_snowman);
      if (_snowman.a()) {
         return bmb.b;
      } else {
         this.b.set(_snowman, bmb.b);
         return _snowman;
      }
   }

   @Override
   public void a(int var1, bmb var2) {
      this.d(null);
      this.b.set(_snowman, _snowman);
      if (!_snowman.a() && _snowman.E() > this.V_()) {
         _snowman.e(this.V_());
      }
   }

   @Override
   public boolean a_(int var1, bmb var2) {
      if (_snowman >= 0 && _snowman < this.Z_()) {
         this.a(_snowman, _snowman);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void X_() {
   }

   @Override
   public boolean a(bfw var1) {
      return this.y ? false : !(_snowman.h(this) > 64.0);
   }

   @Nullable
   @Override
   public aqa b(aag var1) {
      this.c = false;
      return super.b(_snowman);
   }

   @Override
   public void ad() {
      if (!this.l.v && this.c) {
         aoq.a(this.l, this, this);
      }

      super.ad();
   }

   @Override
   protected void b(md var1) {
      super.b(_snowman);
      if (this.d != null) {
         _snowman.a("LootTable", this.d.toString());
         if (this.e != 0L) {
            _snowman.a("LootTableSeed", this.e);
         }
      } else {
         aoo.a(_snowman, this.b);
      }
   }

   @Override
   protected void a(md var1) {
      super.a(_snowman);
      this.b = gj.a(this.Z_(), bmb.b);
      if (_snowman.c("LootTable", 8)) {
         this.d = new vk(_snowman.l("LootTable"));
         this.e = _snowman.i("LootTableSeed");
      } else {
         aoo.b(_snowman, this.b);
      }
   }

   @Override
   public aou a(bfw var1, aot var2) {
      _snowman.a((aox)this);
      if (!_snowman.l.v) {
         bet.a(_snowman, true);
         return aou.b;
      } else {
         return aou.a;
      }
   }

   @Override
   protected void i() {
      float _snowman = 0.98F;
      if (this.d == null) {
         int _snowmanx = 15 - bic.b(this);
         _snowman += (float)_snowmanx * 0.001F;
      }

      this.f(this.cC().d((double)_snowman, 0.0, (double)_snowman));
   }

   public void d(@Nullable bfw var1) {
      if (this.d != null && this.l.l() != null) {
         cyy _snowman = this.l.l().aJ().a(this.d);
         if (_snowman instanceof aah) {
            ac.N.a((aah)_snowman, this.d);
         }

         this.d = null;
         cyv.a _snowmanx = new cyv.a((aag)this.l).a(dbc.f, this.cA()).a(this.e);
         if (_snowman != null) {
            _snowmanx.a(_snowman.eU()).a(dbc.a, _snowman);
         }

         _snowman.a(this, _snowmanx.a(dbb.b));
      }
   }

   @Override
   public void Y_() {
      this.d(null);
      this.b.clear();
   }

   public void a(vk var1, long var2) {
      this.d = _snowman;
      this.e = _snowman;
   }

   @Nullable
   @Override
   public bic createMenu(int var1, bfv var2, bfw var3) {
      if (this.d != null && _snowman.a_()) {
         return null;
      } else {
         this.d(_snowman.e);
         return this.a(_snowman, _snowman);
      }
   }

   protected abstract bic a(int var1, bfv var2);
}
