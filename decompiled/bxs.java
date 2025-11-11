import javax.annotation.Nullable;

public class bxs extends bud {
   public static final cey a = cex.n;

   protected bxs(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Boolean.valueOf(false)));
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, @Nullable aqm var4, bmb var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      md _snowman = _snowman.p();
      if (_snowman.e("BlockEntityTag")) {
         md _snowmanx = _snowman.p("BlockEntityTag");
         if (_snowmanx.e("RecordItem")) {
            _snowman.a(_snowman, _snowman.a(a, Boolean.valueOf(true)), 2);
         }
      }
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.c(a)) {
         this.a(_snowman, _snowman);
         _snowman = _snowman.a(a, Boolean.valueOf(false));
         _snowman.a(_snowman, _snowman, 2);
         return aou.a(_snowman.v);
      } else {
         return aou.c;
      }
   }

   public void a(bry var1, fx var2, ceh var3, bmb var4) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cda) {
         ((cda)_snowman).a(_snowman.i());
         _snowman.a(_snowman, _snowman.a(a, Boolean.valueOf(true)), 2);
      }
   }

   private void a(brx var1, fx var2) {
      if (!_snowman.v) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof cda) {
            cda _snowmanx = (cda)_snowman;
            bmb _snowmanxx = _snowmanx.d();
            if (!_snowmanxx.a()) {
               _snowman.c(1010, _snowman, 0);
               _snowmanx.Y_();
               float _snowmanxxx = 0.7F;
               double _snowmanxxxx = (double)(_snowman.t.nextFloat() * 0.7F) + 0.15F;
               double _snowmanxxxxx = (double)(_snowman.t.nextFloat() * 0.7F) + 0.060000002F + 0.6;
               double _snowmanxxxxxx = (double)(_snowman.t.nextFloat() * 0.7F) + 0.15F;
               bmb _snowmanxxxxxxx = _snowmanxx.i();
               bcv _snowmanxxxxxxxx = new bcv(_snowman, (double)_snowman.u() + _snowmanxxxx, (double)_snowman.v() + _snowmanxxxxx, (double)_snowman.w() + _snowmanxxxxxx, _snowmanxxxxxxx);
               _snowmanxxxxxxxx.m();
               _snowman.c(_snowmanxxxxxxxx);
            }
         }
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         this.a(_snowman, _snowman);
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public ccj a(brc var1) {
      return new cda();
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cda) {
         blx _snowmanx = ((cda)_snowman).d().b();
         if (_snowmanx instanceof bmq) {
            return ((bmq)_snowmanx).f();
         }
      }

      return 0;
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.c;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }
}
