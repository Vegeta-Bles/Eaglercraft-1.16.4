import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;

public class ctp extends ctt<ctu> {
   private static final ceh c = bup.lb.n();
   private static final ceh d = bup.E.n();
   private static final ceh e = bup.cM.n();
   protected long a;
   protected cub b;

   public ctp(Codec<ctu> var1) {
      super(_snowman);
   }

   public void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, int var11, long var12, ctu var14) {
      int _snowman = _snowman;
      int _snowmanx = _snowman & 15;
      int _snowmanxx = _snowman & 15;
      double _snowmanxxx = 0.03125;
      boolean _snowmanxxxx = this.b.a((double)_snowman * 0.03125, (double)_snowman * 0.03125, 0.0) * 75.0 + _snowman.nextDouble() > 0.0;
      boolean _snowmanxxxxx = this.b.a((double)_snowman * 0.03125, 109.0, (double)_snowman * 0.03125) * 75.0 + _snowman.nextDouble() > 0.0;
      int _snowmanxxxxxx = (int)(_snowman / 3.0 + 3.0 + _snowman.nextDouble() * 0.25);
      fx.a _snowmanxxxxxxx = new fx.a();
      int _snowmanxxxxxxxx = -1;
      ceh _snowmanxxxxxxxxx = _snowman.a();
      ceh _snowmanxxxxxxxxxx = _snowman.b();

      for (int _snowmanxxxxxxxxxxx = 127; _snowmanxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxx--) {
         _snowmanxxxxxxx.d(_snowmanx, _snowmanxxxxxxxxxxx, _snowmanxx);
         ceh _snowmanxxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxx);
         if (_snowmanxxxxxxxxxxxx.g()) {
            _snowmanxxxxxxxx = -1;
         } else if (_snowmanxxxxxxxxxxxx.a(_snowman.b())) {
            if (_snowmanxxxxxxxx == -1) {
               boolean _snowmanxxxxxxxxxxxxx = false;
               if (_snowmanxxxxxx <= 0) {
                  _snowmanxxxxxxxxxxxxx = true;
                  _snowmanxxxxxxxxxx = _snowman.b();
               } else if (_snowmanxxxxxxxxxxx >= _snowman - 4 && _snowmanxxxxxxxxxxx <= _snowman + 1) {
                  _snowmanxxxxxxxxx = _snowman.a();
                  _snowmanxxxxxxxxxx = _snowman.b();
                  if (_snowmanxxxxx) {
                     _snowmanxxxxxxxxx = d;
                     _snowmanxxxxxxxxxx = _snowman.b();
                  }

                  if (_snowmanxxxx) {
                     _snowmanxxxxxxxxx = e;
                     _snowmanxxxxxxxxxx = e;
                  }
               }

               if (_snowmanxxxxxxxxxxx < _snowman && _snowmanxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxxx = _snowman;
               }

               _snowmanxxxxxxxx = _snowmanxxxxxx;
               if (_snowmanxxxxxxxxxxx >= _snowman - 1) {
                  _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxxxxx, false);
               } else {
                  _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxxxxxx, false);
               }
            } else if (_snowmanxxxxxxxx > 0) {
               _snowmanxxxxxxxx--;
               _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxxxxxx, false);
            }
         }
      }
   }

   @Override
   public void a(long var1) {
      if (this.a != _snowman || this.b == null) {
         this.b = new cub(new chx(_snowman), IntStream.rangeClosed(-3, 0));
      }

      this.a = _snowman;
   }
}
