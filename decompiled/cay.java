import java.util.Random;

public class cay extends buo {
   private static final cfg a = cex.az;

   public cay(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public void a(brx var1, ceh var2, dcj var3, bgm var4) {
      int _snowman = a((bry)_snowman, _snowman, _snowman, (aqa)_snowman);
      aqa _snowmanx = _snowman.v();
      if (_snowmanx instanceof aah) {
         aah _snowmanxx = (aah)_snowmanx;
         _snowmanxx.a(aea.aD);
         ac.L.a(_snowmanxx, _snowman, _snowman.e(), _snowman);
      }
   }

   private static int a(bry var0, ceh var1, dcj var2, aqa var3) {
      int _snowman = a(_snowman, _snowman.e());
      int _snowmanx = _snowman instanceof bga ? 20 : 8;
      if (!_snowman.J().a(_snowman.a(), _snowman.b())) {
         a(_snowman, _snowman, _snowman, _snowman.a(), _snowmanx);
      }

      return _snowman;
   }

   private static int a(dcj var0, dcn var1) {
      gc _snowman = _snowman.b();
      double _snowmanx = Math.abs(afm.h(_snowman.b) - 0.5);
      double _snowmanxx = Math.abs(afm.h(_snowman.c) - 0.5);
      double _snowmanxxx = Math.abs(afm.h(_snowman.d) - 0.5);
      gc.a _snowmanxxxx = _snowman.n();
      double _snowmanxxxxx;
      if (_snowmanxxxx == gc.a.b) {
         _snowmanxxxxx = Math.max(_snowmanx, _snowmanxxx);
      } else if (_snowmanxxxx == gc.a.c) {
         _snowmanxxxxx = Math.max(_snowmanx, _snowmanxx);
      } else {
         _snowmanxxxxx = Math.max(_snowmanxx, _snowmanxxx);
      }

      return Math.max(1, afm.f(15.0 * afm.a((0.5 - _snowmanxxxxx) / 0.5, 0.0, 1.0)));
   }

   private static void a(bry var0, ceh var1, int var2, fx var3, int var4) {
      _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowman)), 3);
      _snowman.J().a(_snowman, _snowman.b(), _snowman);
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (_snowman.c(a) != 0) {
         _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(0)), 3);
      }
   }

   @Override
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman.c(a);
   }

   @Override
   public boolean b_(ceh var1) {
      return true;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.s_() && !_snowman.a(_snowman.b())) {
         if (_snowman.c(a) > 0 && !_snowman.J().a(_snowman, this)) {
            _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(0)), 18);
         }
      }
   }
}
