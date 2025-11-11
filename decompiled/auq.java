import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;

public class auq extends aur {
   private static final List<blx> b = ImmutableList.of(bmd.kV, bmd.qg);

   public auq() {
   }

   @Override
   protected void a(aag var1, bfj var2) {
      Optional<gf> _snowman = _snowman.cJ().c(ayd.c);
      if (_snowman.isPresent()) {
         gf _snowmanx = _snowman.get();
         ceh _snowmanxx = _snowman.d_(_snowmanx.b());
         if (_snowmanxx.a(bup.na)) {
            this.a(_snowman);
            this.a(_snowman, _snowman, _snowmanx, _snowmanxx);
         }
      }
   }

   private void a(aag var1, bfj var2, gf var3, ceh var4) {
      fx _snowman = _snowman.b();
      if (_snowman.c(bvk.a) == 8) {
         _snowman = bvk.d(_snowman, _snowman, _snowman);
      }

      int _snowmanx = 20;
      int _snowmanxx = 10;
      int[] _snowmanxxx = new int[b.size()];
      apa _snowmanxxxx = _snowman.eU();
      int _snowmanxxxxx = _snowmanxxxx.Z_();
      ceh _snowmanxxxxxx = _snowman;

      for (int _snowmanxxxxxxx = _snowmanxxxxx - 1; _snowmanxxxxxxx >= 0 && _snowmanx > 0; _snowmanxxxxxxx--) {
         bmb _snowmanxxxxxxxx = _snowmanxxxx.a(_snowmanxxxxxxx);
         int _snowmanxxxxxxxxx = b.indexOf(_snowmanxxxxxxxx.b());
         if (_snowmanxxxxxxxxx != -1) {
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.E();
            int _snowmanxxxxxxxxxxx = _snowmanxxx[_snowmanxxxxxxxxx] + _snowmanxxxxxxxxxx;
            _snowmanxxx[_snowmanxxxxxxxxx] = _snowmanxxxxxxxxxxx;
            int _snowmanxxxxxxxxxxxx = Math.min(Math.min(_snowmanxxxxxxxxxxx - 10, _snowmanx), _snowmanxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxx > 0) {
               _snowmanx -= _snowmanxxxxxxxxxxxx;

               for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
                  _snowmanxxxxxx = bvk.a(_snowmanxxxxxx, _snowman, _snowmanxxxxxxxx, _snowman);
                  if (_snowmanxxxxxx.c(bvk.a) == 7) {
                     this.a(_snowman, _snowman, _snowman, _snowmanxxxxxx);
                     return;
                  }
               }
            }
         }
      }

      this.a(_snowman, _snowman, _snowman, _snowmanxxxxxx);
   }

   private void a(aag var1, ceh var2, fx var3, ceh var4) {
      _snowman.c(1500, _snowman, _snowman != _snowman ? 1 : 0);
   }

   private void a(bfj var1) {
      apa _snowman = _snowman.eU();
      if (_snowman.a(bmd.kX) <= 36) {
         int _snowmanx = _snowman.a(bmd.kW);
         int _snowmanxx = 3;
         int _snowmanxxx = 3;
         int _snowmanxxxx = Math.min(3, _snowmanx / 3);
         if (_snowmanxxxx != 0) {
            int _snowmanxxxxx = _snowmanxxxx * 3;
            _snowman.a(bmd.kW, _snowmanxxxxx);
            bmb _snowmanxxxxxx = _snowman.a(new bmb(bmd.kX, _snowmanxxxx));
            if (!_snowmanxxxxxx.a()) {
               _snowman.a(_snowmanxxxxxx, 0.5F);
            }
         }
      }
   }
}
