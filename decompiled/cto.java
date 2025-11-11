import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;

public class cto extends ctt<ctu> {
   private static final ceh b = bup.lb.n();
   protected long a;
   private cub c;

   public cto(Codec<ctu> var1) {
      super(_snowman);
   }

   public void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, int var11, long var12, ctu var14) {
      int _snowman = _snowman;
      int _snowmanx = _snowman & 15;
      int _snowmanxx = _snowman & 15;
      double _snowmanxxx = this.c.a((double)_snowman * 0.1, (double)_snowman, (double)_snowman * 0.1);
      boolean _snowmanxxxx = _snowmanxxx > 0.15 + _snowman.nextDouble() * 0.35;
      double _snowmanxxxxx = this.c.a((double)_snowman * 0.1, 109.0, (double)_snowman * 0.1);
      boolean _snowmanxxxxxx = _snowmanxxxxx > 0.25 + _snowman.nextDouble() * 0.9;
      int _snowmanxxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      fx.a _snowmanxxxxxxxx = new fx.a();
      int _snowmanxxxxxxxxx = -1;
      ceh _snowmanxxxxxxxxxx = _snowman.b();

      for (int _snowmanxxxxxxxxxxx = 127; _snowmanxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxx--) {
         _snowmanxxxxxxxx.d(_snowmanx, _snowmanxxxxxxxxxxx, _snowmanxx);
         ceh _snowmanxxxxxxxxxxxx = _snowman.a();
         ceh _snowmanxxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxx.g()) {
            _snowmanxxxxxxxxx = -1;
         } else if (_snowmanxxxxxxxxxxxxx.a(_snowman.b())) {
            if (_snowmanxxxxxxxxx == -1) {
               boolean _snowmanxxxxxxxxxxxxxx = false;
               if (_snowmanxxxxxxx <= 0) {
                  _snowmanxxxxxxxxxxxxxx = true;
                  _snowmanxxxxxxxxxx = _snowman.b();
               }

               if (_snowmanxxxx) {
                  _snowmanxxxxxxxxxxxx = _snowman.b();
               } else if (_snowmanxxxxxx) {
                  _snowmanxxxxxxxxxxxx = _snowman.c();
               }

               if (_snowmanxxxxxxxxxxx < _snowman && _snowmanxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxxxxx = _snowman;
               }

               _snowmanxxxxxxxxx = _snowmanxxxxxxx;
               if (_snowmanxxxxxxxxxxx >= _snowman - 1) {
                  _snowman.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx, false);
               } else {
                  _snowman.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx, false);
               }
            } else if (_snowmanxxxxxxxxx > 0) {
               _snowmanxxxxxxxxx--;
               _snowman.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx, false);
            }
         }
      }
   }

   @Override
   public void a(long var1) {
      if (this.a != _snowman || this.c == null) {
         this.c = new cub(new chx(_snowman), ImmutableList.of(0));
      }

      this.a = _snowman;
   }
}
