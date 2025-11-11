import com.google.common.collect.Lists;
import java.util.Queue;

public class cag extends buo {
   protected cag(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         this.a(_snowman, _snowman);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      this.a(_snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   protected void a(brx var1, fx var2) {
      if (this.b(_snowman, _snowman)) {
         _snowman.a(_snowman, bup.ao.n(), 2);
         _snowman.c(2001, _snowman, buo.i(bup.A.n()));
      }
   }

   private boolean b(brx var1, fx var2) {
      Queue<afv<fx, Integer>> _snowman = Lists.newLinkedList();
      _snowman.add(new afv<>(_snowman, 0));
      int _snowmanx = 0;

      while (!_snowman.isEmpty()) {
         afv<fx, Integer> _snowmanxx = _snowman.poll();
         fx _snowmanxxx = _snowmanxx.a();
         int _snowmanxxxx = _snowmanxx.b();

         for (gc _snowmanxxxxx : gc.values()) {
            fx _snowmanxxxxxx = _snowmanxxx.a(_snowmanxxxxx);
            ceh _snowmanxxxxxxx = _snowman.d_(_snowmanxxxxxx);
            cux _snowmanxxxxxxxx = _snowman.b(_snowmanxxxxxx);
            cva _snowmanxxxxxxxxx = _snowmanxxxxxxx.c();
            if (_snowmanxxxxxxxx.a(aef.b)) {
               if (_snowmanxxxxxxx.b() instanceof but && ((but)_snowmanxxxxxxx.b()).b(_snowman, _snowmanxxxxxx, _snowmanxxxxxxx) != cuy.a) {
                  _snowmanx++;
                  if (_snowmanxxxx < 6) {
                     _snowman.add(new afv<>(_snowmanxxxxxx, _snowmanxxxx + 1));
                  }
               } else if (_snowmanxxxxxxx.b() instanceof byb) {
                  _snowman.a(_snowmanxxxxxx, bup.a.n(), 3);
                  _snowmanx++;
                  if (_snowmanxxxx < 6) {
                     _snowman.add(new afv<>(_snowmanxxxxxx, _snowmanxxxx + 1));
                  }
               } else if (_snowmanxxxxxxxxx == cva.f || _snowmanxxxxxxxxx == cva.i) {
                  ccj _snowmanxxxxxxxxxx = _snowmanxxxxxxx.b().q() ? _snowman.c(_snowmanxxxxxx) : null;
                  a(_snowmanxxxxxxx, _snowman, _snowmanxxxxxx, _snowmanxxxxxxxxxx);
                  _snowman.a(_snowmanxxxxxx, bup.a.n(), 3);
                  _snowmanx++;
                  if (_snowmanxxxx < 6) {
                     _snowman.add(new afv<>(_snowmanxxxxxx, _snowmanxxxx + 1));
                  }
               }
            }
         }

         if (_snowmanx > 64) {
            break;
         }
      }

      return _snowmanx > 0;
   }
}
