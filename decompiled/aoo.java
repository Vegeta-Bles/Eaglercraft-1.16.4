import java.util.List;
import java.util.function.Predicate;

public class aoo {
   public static bmb a(List<bmb> var0, int var1, int var2) {
      return _snowman >= 0 && _snowman < _snowman.size() && !_snowman.get(_snowman).a() && _snowman > 0 ? _snowman.get(_snowman).a(_snowman) : bmb.b;
   }

   public static bmb a(List<bmb> var0, int var1) {
      return _snowman >= 0 && _snowman < _snowman.size() ? _snowman.set(_snowman, bmb.b) : bmb.b;
   }

   public static md a(md var0, gj<bmb> var1) {
      return a(_snowman, _snowman, true);
   }

   public static md a(md var0, gj<bmb> var1, boolean var2) {
      mj _snowman = new mj();

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         bmb _snowmanxx = _snowman.get(_snowmanx);
         if (!_snowmanxx.a()) {
            md _snowmanxxx = new md();
            _snowmanxxx.a("Slot", (byte)_snowmanx);
            _snowmanxx.b(_snowmanxxx);
            _snowman.add(_snowmanxxx);
         }
      }

      if (!_snowman.isEmpty() || _snowman) {
         _snowman.a("Items", _snowman);
      }

      return _snowman;
   }

   public static void b(md var0, gj<bmb> var1) {
      mj _snowman = _snowman.d("Items", 10);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         md _snowmanxx = _snowman.a(_snowmanx);
         int _snowmanxxx = _snowmanxx.f("Slot") & 255;
         if (_snowmanxxx >= 0 && _snowmanxxx < _snowman.size()) {
            _snowman.set(_snowmanxxx, bmb.a(_snowmanxx));
         }
      }
   }

   public static int a(aon var0, Predicate<bmb> var1, int var2, boolean var3) {
      int _snowman = 0;

      for (int _snowmanx = 0; _snowmanx < _snowman.Z_(); _snowmanx++) {
         bmb _snowmanxx = _snowman.a(_snowmanx);
         int _snowmanxxx = a(_snowmanxx, _snowman, _snowman - _snowman, _snowman);
         if (_snowmanxxx > 0 && !_snowman && _snowmanxx.a()) {
            _snowman.a(_snowmanx, bmb.b);
         }

         _snowman += _snowmanxxx;
      }

      return _snowman;
   }

   public static int a(bmb var0, Predicate<bmb> var1, int var2, boolean var3) {
      if (_snowman.a() || !_snowman.test(_snowman)) {
         return 0;
      } else if (_snowman) {
         return _snowman.E();
      } else {
         int _snowman = _snowman < 0 ? _snowman.E() : Math.min(_snowman, _snowman.E());
         _snowman.g(_snowman);
         return _snowman;
      }
   }
}
