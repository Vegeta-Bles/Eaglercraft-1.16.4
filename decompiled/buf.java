import java.util.Random;

public abstract class buf extends buo {
   protected static final ddh a = buo.a(1.0, 0.0, 1.0, 15.0, 0.5, 15.0);
   protected static final ddh b = buo.a(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);
   protected static final dci c = new dci(0.125, 0.0, 0.125, 0.875, 0.25, 0.875);

   protected buf(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return this.g(_snowman) > 0 ? a : b;
   }

   protected int c() {
      return 20;
   }

   @Override
   public boolean ai_() {
      return true;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman == gc.a && !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      fx _snowman = _snowman.c();
      return c(_snowman, _snowman) || a(_snowman, _snowman, gc.b);
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      int _snowman = this.g(_snowman);
      if (_snowman > 0) {
         this.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (!_snowman.v) {
         int _snowman = this.g(_snowman);
         if (_snowman == 0) {
            this.a(_snowman, _snowman, _snowman, _snowman);
         }
      }
   }

   protected void a(brx var1, fx var2, ceh var3, int var4) {
      int _snowman = this.b(_snowman, _snowman);
      boolean _snowmanx = _snowman > 0;
      boolean _snowmanxx = _snowman > 0;
      if (_snowman != _snowman) {
         ceh _snowmanxxx = this.a(_snowman, _snowman);
         _snowman.a(_snowman, _snowmanxxx, 2);
         this.a(_snowman, _snowman);
         _snowman.b(_snowman, _snowman, _snowmanxxx);
      }

      if (!_snowmanxx && _snowmanx) {
         this.b((bry)_snowman, _snowman);
      } else if (_snowmanxx && !_snowmanx) {
         this.a((bry)_snowman, _snowman);
      }

      if (_snowmanxx) {
         _snowman.J().a(new fx(_snowman), this, this.c());
      }
   }

   protected abstract void a(bry var1, fx var2);

   protected abstract void b(bry var1, fx var2);

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman && !_snowman.a(_snowman.b())) {
         if (this.g(_snowman) > 0) {
            this.a(_snowman, _snowman);
         }

         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   protected void a(brx var1, fx var2) {
      _snowman.b(_snowman, this);
      _snowman.b(_snowman.c(), this);
   }

   @Override
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      return this.g(_snowman);
   }

   @Override
   public int b(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman == gc.b ? this.g(_snowman) : 0;
   }

   @Override
   public boolean b_(ceh var1) {
      return true;
   }

   @Override
   public cvc f(ceh var1) {
      return cvc.b;
   }

   protected abstract int b(brx var1, fx var2);

   protected abstract int g(ceh var1);

   protected abstract ceh a(ceh var1, int var2);
}
