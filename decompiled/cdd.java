import java.util.Random;
import javax.annotation.Nullable;

public abstract class cdd extends ccd {
   @Nullable
   protected vk g;
   protected long h;

   protected cdd(cck<?> var1) {
      super(_snowman);
   }

   public static void a(brc var0, Random var1, fx var2, vk var3) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cdd) {
         ((cdd)_snowman).a(_snowman, _snowman.nextLong());
      }
   }

   protected boolean b(md var1) {
      if (_snowman.c("LootTable", 8)) {
         this.g = new vk(_snowman.l("LootTable"));
         this.h = _snowman.i("LootTableSeed");
         return true;
      } else {
         return false;
      }
   }

   protected boolean c(md var1) {
      if (this.g == null) {
         return false;
      } else {
         _snowman.a("LootTable", this.g.toString());
         if (this.h != 0L) {
            _snowman.a("LootTableSeed", this.h);
         }

         return true;
      }
   }

   public void d(@Nullable bfw var1) {
      if (this.g != null && this.d.l() != null) {
         cyy _snowman = this.d.l().aJ().a(this.g);
         if (_snowman instanceof aah) {
            ac.N.a((aah)_snowman, this.g);
         }

         this.g = null;
         cyv.a _snowmanx = new cyv.a((aag)this.d).a(dbc.f, dcn.a(this.e)).a(this.h);
         if (_snowman != null) {
            _snowmanx.a(_snowman.eU()).a(dbc.a, _snowman);
         }

         _snowman.a(this, _snowmanx.a(dbb.b));
      }
   }

   public void a(vk var1, long var2) {
      this.g = _snowman;
      this.h = _snowman;
   }

   @Override
   public boolean c() {
      this.d(null);
      return this.f().stream().allMatch(bmb::a);
   }

   @Override
   public bmb a(int var1) {
      this.d(null);
      return this.f().get(_snowman);
   }

   @Override
   public bmb a(int var1, int var2) {
      this.d(null);
      bmb _snowman = aoo.a(this.f(), _snowman, _snowman);
      if (!_snowman.a()) {
         this.X_();
      }

      return _snowman;
   }

   @Override
   public bmb b(int var1) {
      this.d(null);
      return aoo.a(this.f(), _snowman);
   }

   @Override
   public void a(int var1, bmb var2) {
      this.d(null);
      this.f().set(_snowman, _snowman);
      if (_snowman.E() > this.V_()) {
         _snowman.e(this.V_());
      }

      this.X_();
   }

   @Override
   public boolean a(bfw var1) {
      return this.d.c(this.e) != this ? false : !(_snowman.h((double)this.e.u() + 0.5, (double)this.e.v() + 0.5, (double)this.e.w() + 0.5) > 64.0);
   }

   @Override
   public void Y_() {
      this.f().clear();
   }

   protected abstract gj<bmb> f();

   protected abstract void a(gj<bmb> var1);

   @Override
   public boolean e(bfw var1) {
      return super.e(_snowman) && (this.g == null || !_snowman.a_());
   }

   @Nullable
   @Override
   public bic createMenu(int var1, bfv var2, bfw var3) {
      if (this.e(_snowman)) {
         this.d(_snowman.e);
         return this.a(_snowman, _snowman);
      } else {
         return null;
      }
   }
}
