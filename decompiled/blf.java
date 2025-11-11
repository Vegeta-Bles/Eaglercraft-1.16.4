import java.util.List;
import javax.annotation.Nullable;

public class blf extends blx {
   public blf(blx.a var1) {
      super(_snowman);
   }

   @Override
   public boolean e(bmb var1) {
      return true;
   }

   @Override
   public boolean f_(bmb var1) {
      return false;
   }

   public static mj d(bmb var0) {
      md _snowman = _snowman.o();
      return _snowman != null ? _snowman.d("StoredEnchantments", 10) : new mj();
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      bmb.a(_snowman, d(_snowman));
   }

   public static void a(bmb var0, bpv var1) {
      mj _snowman = d(_snowman);
      boolean _snowmanx = true;
      vk _snowmanxx = gm.R.b(_snowman.b);

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
         md _snowmanxxxx = _snowman.a(_snowmanxxx);
         vk _snowmanxxxxx = vk.a(_snowmanxxxx.l("id"));
         if (_snowmanxxxxx != null && _snowmanxxxxx.equals(_snowmanxx)) {
            if (_snowmanxxxx.h("lvl") < _snowman.c) {
               _snowmanxxxx.a("lvl", (short)_snowman.c);
            }

            _snowmanx = false;
            break;
         }
      }

      if (_snowmanx) {
         md _snowmanxxxx = new md();
         _snowmanxxxx.a("id", String.valueOf(_snowmanxx));
         _snowmanxxxx.a("lvl", (short)_snowman.c);
         _snowman.add(_snowmanxxxx);
      }

      _snowman.p().a("StoredEnchantments", _snowman);
   }

   public static bmb a(bpv var0) {
      bmb _snowman = new bmb(bmd.pq);
      a(_snowman, _snowman);
      return _snowman;
   }

   @Override
   public void a(bks var1, gj<bmb> var2) {
      if (_snowman == bks.g) {
         for (bps _snowman : gm.R) {
            if (_snowman.b != null) {
               for (int _snowmanx = _snowman.e(); _snowmanx <= _snowman.a(); _snowmanx++) {
                  _snowman.add(a(new bpv(_snowman, _snowmanx)));
               }
            }
         }
      } else if (_snowman.n().length != 0) {
         for (bps _snowmanx : gm.R) {
            if (_snowman.a(_snowmanx.b)) {
               _snowman.add(a(new bpv(_snowmanx, _snowmanx.a())));
            }
         }
      }
   }
}
