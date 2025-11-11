import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntStack;
import java.util.function.Predicate;

public class i {
   public static i.a a(fx var0, gc.a var1, int var2, gc.a var3, int var4, Predicate<fx> var5) {
      fx.a _snowman = _snowman.i();
      gc _snowmanx = gc.a(gc.b.b, _snowman);
      gc _snowmanxx = _snowmanx.f();
      gc _snowmanxxx = gc.a(gc.b.b, _snowman);
      gc _snowmanxxxx = _snowmanxxx.f();
      int _snowmanxxxxx = a(_snowman, _snowman.g(_snowman), _snowmanx, _snowman);
      int _snowmanxxxxxx = a(_snowman, _snowman.g(_snowman), _snowmanxx, _snowman);
      int _snowmanxxxxxxx = _snowmanxxxxx;
      i.b[] _snowmanxxxxxxxx = new i.b[_snowmanxxxxx + 1 + _snowmanxxxxxx];
      _snowmanxxxxxxxx[_snowmanxxxxx] = new i.b(a(_snowman, _snowman.g(_snowman), _snowmanxxx, _snowman), a(_snowman, _snowman.g(_snowman), _snowmanxxxx, _snowman));
      int _snowmanxxxxxxxxx = _snowmanxxxxxxxx[_snowmanxxxxx].a;

      for (int _snowmanxxxxxxxxxx = 1; _snowmanxxxxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxxxxx++) {
         i.b _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx[_snowmanxxxxxxx - (_snowmanxxxxxxxxxx - 1)];
         _snowmanxxxxxxxx[_snowmanxxxxxxx - _snowmanxxxxxxxxxx] = new i.b(
            a(_snowman, _snowman.g(_snowman).c(_snowmanx, _snowmanxxxxxxxxxx), _snowmanxxx, _snowmanxxxxxxxxxxx.a), a(_snowman, _snowman.g(_snowman).c(_snowmanx, _snowmanxxxxxxxxxx), _snowmanxxxx, _snowmanxxxxxxxxxxx.b)
         );
      }

      for (int _snowmanxxxxxxxxxx = 1; _snowmanxxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxxx++) {
         i.b _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx[_snowmanxxxxxxx + _snowmanxxxxxxxxxx - 1];
         _snowmanxxxxxxxx[_snowmanxxxxxxx + _snowmanxxxxxxxxxx] = new i.b(
            a(_snowman, _snowman.g(_snowman).c(_snowmanxx, _snowmanxxxxxxxxxx), _snowmanxxx, _snowmanxxxxxxxxxxx.a), a(_snowman, _snowman.g(_snowman).c(_snowmanxx, _snowmanxxxxxxxxxx), _snowmanxxxx, _snowmanxxxxxxxxxxx.b)
         );
      }

      int _snowmanxxxxxxxxxx = 0;
      int _snowmanxxxxxxxxxxx = 0;
      int _snowmanxxxxxxxxxxxx = 0;
      int _snowmanxxxxxxxxxxxxx = 0;
      int[] _snowmanxxxxxxxxxxxxxx = new int[_snowmanxxxxxxxx.length];

      for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxx--) {
         for (int _snowmanxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxxx.length; _snowmanxxxxxxxxxxxxxxxx++) {
            i.b _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxxxx];
            int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxx.a;
            int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx.b;
            _snowmanxxxxxxxxxxxxxx[_snowmanxxxxxxxxxxxxxxxx] = _snowmanxxxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxxxxxxxx && _snowmanxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxxxxxx
               ? _snowmanxxxxxxxxxxxxxxxxxxx + 1 - _snowmanxxxxxxxxxxxxxxx
               : 0;
         }

         Pair<i.b, Integer> _snowmanxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxx);
         i.b _snowmanxxxxxxxxxxxxxxxxx = (i.b)_snowmanxxxxxxxxxxxxxxxx.getFirst();
         int _snowmanxxxxxxxxxxxxxxxxxx = 1 + _snowmanxxxxxxxxxxxxxxxxx.b - _snowmanxxxxxxxxxxxxxxxxx.a;
         int _snowmanxxxxxxxxxxxxxxxxxxx = (Integer)_snowmanxxxxxxxxxxxxxxxx.getSecond();
         if (_snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx > _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.a;
            _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx;
         }
      }

      return new i.a(_snowman.a(_snowman, _snowmanxxxxxxxxxx - _snowmanxxxxxxx).a(_snowman, _snowmanxxxxxxxxxxx - _snowmanxxxxxxxxx), _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
   }

   private static int a(Predicate<fx> var0, fx.a var1, gc var2, int var3) {
      int _snowman = 0;

      while (_snowman < _snowman && _snowman.test(_snowman.c(_snowman))) {
         _snowman++;
      }

      return _snowman;
   }

   @VisibleForTesting
   static Pair<i.b, Integer> a(int[] var0) {
      int _snowman = 0;
      int _snowmanx = 0;
      int _snowmanxx = 0;
      IntStack _snowmanxxx = new IntArrayList();
      _snowmanxxx.push(0);

      for (int _snowmanxxxx = 1; _snowmanxxxx <= _snowman.length; _snowmanxxxx++) {
         int _snowmanxxxxx = _snowmanxxxx == _snowman.length ? 0 : _snowman[_snowmanxxxx];

         while (!_snowmanxxx.isEmpty()) {
            int _snowmanxxxxxx = _snowman[_snowmanxxx.topInt()];
            if (_snowmanxxxxx >= _snowmanxxxxxx) {
               _snowmanxxx.push(_snowmanxxxx);
               break;
            }

            _snowmanxxx.popInt();
            int _snowmanxxxxxxx = _snowmanxxx.isEmpty() ? 0 : _snowmanxxx.topInt() + 1;
            if (_snowmanxxxxxx * (_snowmanxxxx - _snowmanxxxxxxx) > _snowmanxx * (_snowmanx - _snowman)) {
               _snowmanx = _snowmanxxxx;
               _snowman = _snowmanxxxxxxx;
               _snowmanxx = _snowmanxxxxxx;
            }
         }

         if (_snowmanxxx.isEmpty()) {
            _snowmanxxx.push(_snowmanxxxx);
         }
      }

      return new Pair(new i.b(_snowman, _snowmanx - 1), _snowmanxx);
   }

   public static class a {
      public final fx a;
      public final int b;
      public final int c;

      public a(fx var1, int var2, int var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }

   public static class b {
      public final int a;
      public final int b;

      public b(int var1, int var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      public String toString() {
         return "IntBounds{min=" + this.a + ", max=" + this.b + '}';
      }
   }
}
