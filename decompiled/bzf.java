import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

public class bzf extends cba {
   public static final cey a = cex.r;
   private static final Map<brc, List<bzf.a>> b = new WeakHashMap<>();

   protected bzf(ceg.c var1) {
      super(_snowman, hd.a);
      this.j(this.n.b().a(a, Boolean.valueOf(true)));
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      for (gc _snowman : gc.values()) {
         _snowman.b(_snowman.a(_snowman), this);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman) {
         for (gc _snowman : gc.values()) {
            _snowman.b(_snowman.a(_snowman), this);
         }
      }
   }

   @Override
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman.c(a) && gc.b != _snowman ? 15 : 0;
   }

   protected boolean a(brx var1, fx var2, ceh var3) {
      return _snowman.a(_snowman.c(), gc.a);
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      boolean _snowman = this.a(_snowman, _snowman, _snowman);
      List<bzf.a> _snowmanx = b.get(_snowman);

      while (_snowmanx != null && !_snowmanx.isEmpty() && _snowman.T() - _snowmanx.get(0).b > 60L) {
         _snowmanx.remove(0);
      }

      if (_snowman.c(a)) {
         if (_snowman) {
            _snowman.a(_snowman, _snowman.a(a, Boolean.valueOf(false)), 3);
            if (a(_snowman, _snowman, true)) {
               _snowman.c(1502, _snowman, 0);
               _snowman.j().a(_snowman, _snowman.d_(_snowman).b(), 160);
            }
         }
      } else if (!_snowman && !a(_snowman, _snowman, false)) {
         _snowman.a(_snowman, _snowman.a(a, Boolean.valueOf(true)), 3);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (_snowman.c(a) == this.a(_snowman, _snowman, _snowman) && !_snowman.J().b(_snowman, this)) {
         _snowman.J().a(_snowman, this, 2);
      }
   }

   @Override
   public int b(ceh var1, brc var2, fx var3, gc var4) {
      return _snowman == gc.a ? _snowman.b(_snowman, _snowman, _snowman) : 0;
   }

   @Override
   public boolean b_(ceh var1) {
      return true;
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.c(a)) {
         double _snowman = (double)_snowman.u() + 0.5 + (_snowman.nextDouble() - 0.5) * 0.2;
         double _snowmanx = (double)_snowman.v() + 0.7 + (_snowman.nextDouble() - 0.5) * 0.2;
         double _snowmanxx = (double)_snowman.w() + 0.5 + (_snowman.nextDouble() - 0.5) * 0.2;
         _snowman.a(this.e, _snowman, _snowmanx, _snowmanxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   private static boolean a(brx var0, fx var1, boolean var2) {
      List<bzf.a> _snowman = b.computeIfAbsent(_snowman, var0x -> Lists.newArrayList());
      if (_snowman) {
         _snowman.add(new bzf.a(_snowman.h(), _snowman.T()));
      }

      int _snowmanx = 0;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         bzf.a _snowmanxxx = _snowman.get(_snowmanxx);
         if (_snowmanxxx.a.equals(_snowman)) {
            if (++_snowmanx >= 8) {
               return true;
            }
         }
      }

      return false;
   }

   public static class a {
      private final fx a;
      private final long b;

      public a(fx var1, long var2) {
         this.a = _snowman;
         this.b = _snowman;
      }
   }
}
