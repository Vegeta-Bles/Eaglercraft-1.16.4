import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class bvj extends bvy implements bwm {
   public static final cfe<cfa> a = cex.aG;

   public bvj(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(aq, gc.c).a(c, Boolean.valueOf(false)).a(a, cfa.a));
   }

   @Override
   protected int g(ceh var1) {
      return 2;
   }

   @Override
   protected int b(brc var1, fx var2, ceh var3) {
      ccj _snowman = _snowman.c(_snowman);
      return _snowman instanceof ccp ? ((ccp)_snowman).d() : 0;
   }

   private int e(brx var1, fx var2, ceh var3) {
      return _snowman.c(a) == cfa.b ? Math.max(this.b(_snowman, _snowman, _snowman) - this.b((brz)_snowman, _snowman, _snowman), 0) : this.b(_snowman, _snowman, _snowman);
   }

   @Override
   protected boolean a(brx var1, fx var2, ceh var3) {
      int _snowman = this.b(_snowman, _snowman, _snowman);
      if (_snowman == 0) {
         return false;
      } else {
         int _snowmanx = this.b((brz)_snowman, _snowman, _snowman);
         return _snowman > _snowmanx ? true : _snowman == _snowmanx && _snowman.c(a) == cfa.a;
      }
   }

   @Override
   protected int b(brx var1, fx var2, ceh var3) {
      int _snowman = super.b(_snowman, _snowman, _snowman);
      gc _snowmanx = _snowman.c(aq);
      fx _snowmanxx = _snowman.a(_snowmanx);
      ceh _snowmanxxx = _snowman.d_(_snowmanxx);
      if (_snowmanxxx.j()) {
         _snowman = _snowmanxxx.a(_snowman, _snowmanxx);
      } else if (_snowman < 15 && _snowmanxxx.g(_snowman, _snowmanxx)) {
         _snowmanxx = _snowmanxx.a(_snowmanx);
         _snowmanxxx = _snowman.d_(_snowmanxx);
         bcp _snowmanxxxx = this.a(_snowman, _snowmanx, _snowmanxx);
         int _snowmanxxxxx = Math.max(_snowmanxxxx == null ? Integer.MIN_VALUE : _snowmanxxxx.q(), _snowmanxxx.j() ? _snowmanxxx.a(_snowman, _snowmanxx) : Integer.MIN_VALUE);
         if (_snowmanxxxxx != Integer.MIN_VALUE) {
            _snowman = _snowmanxxxxx;
         }
      }

      return _snowman;
   }

   @Nullable
   private bcp a(brx var1, gc var2, fx var3) {
      List<bcp> _snowman = _snowman.a(
         bcp.class,
         new dci((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), (double)(_snowman.u() + 1), (double)(_snowman.v() + 1), (double)(_snowman.w() + 1)),
         var1x -> var1x != null && var1x.bZ() == _snowman
      );
      return _snowman.size() == 1 ? _snowman.get(0) : null;
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (!_snowman.bC.e) {
         return aou.c;
      } else {
         _snowman = _snowman.a(a);
         float _snowman = _snowman.c(a) == cfa.b ? 0.55F : 0.5F;
         _snowman.a(_snowman, _snowman, adq.bT, adr.e, 0.3F, _snowman);
         _snowman.a(_snowman, _snowman, 2);
         this.f(_snowman, _snowman, _snowman);
         return aou.a(_snowman.v);
      }
   }

   @Override
   protected void c(brx var1, fx var2, ceh var3) {
      if (!_snowman.J().b(_snowman, this)) {
         int _snowman = this.e(_snowman, _snowman, _snowman);
         ccj _snowmanx = _snowman.c(_snowman);
         int _snowmanxx = _snowmanx instanceof ccp ? ((ccp)_snowmanx).d() : 0;
         if (_snowman != _snowmanxx || _snowman.c(c) != this.a(_snowman, _snowman, _snowman)) {
            bsq _snowmanxxx = this.c(_snowman, _snowman, _snowman) ? bsq.c : bsq.d;
            _snowman.J().a(_snowman, this, 2, _snowmanxxx);
         }
      }
   }

   private void f(brx var1, fx var2, ceh var3) {
      int _snowman = this.e(_snowman, _snowman, _snowman);
      ccj _snowmanx = _snowman.c(_snowman);
      int _snowmanxx = 0;
      if (_snowmanx instanceof ccp) {
         ccp _snowmanxxx = (ccp)_snowmanx;
         _snowmanxx = _snowmanxxx.d();
         _snowmanxxx.a(_snowman);
      }

      if (_snowmanxx != _snowman || _snowman.c(a) == cfa.a) {
         boolean _snowmanxxx = this.a(_snowman, _snowman, _snowman);
         boolean _snowmanxxxx = _snowman.c(c);
         if (_snowmanxxxx && !_snowmanxxx) {
            _snowman.a(_snowman, _snowman.a(c, Boolean.valueOf(false)), 2);
         } else if (!_snowmanxxxx && _snowmanxxx) {
            _snowman.a(_snowman, _snowman.a(c, Boolean.valueOf(true)), 2);
         }

         this.d(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      this.f(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brx var2, fx var3, int var4, int var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      ccj _snowman = _snowman.c(_snowman);
      return _snowman != null && _snowman.a_(_snowman, _snowman);
   }

   @Override
   public ccj a(brc var1) {
      return new ccp();
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(aq, a, c);
   }
}
