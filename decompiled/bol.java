import com.google.common.collect.Lists;
import java.util.List;

public class bol extends boj {
   private static final bon a = bon.a(bmd.pp);

   public bol(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      boolean _snowman = false;
      boolean _snowmanx = false;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            if (_snowmanxxx.b() instanceof bky) {
               _snowman = true;
            } else {
               if (!a.a(_snowmanxxx)) {
                  return false;
               }

               if (_snowmanx) {
                  return false;
               }

               _snowmanx = true;
            }
         }
      }

      return _snowmanx && _snowman;
   }

   public bmb a(bio var1) {
      List<Integer> _snowman = Lists.newArrayList();
      bmb _snowmanx = null;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         blx _snowmanxxxx = _snowmanxxx.b();
         if (_snowmanxxxx instanceof bky) {
            _snowman.add(((bky)_snowmanxxxx).d().g());
         } else if (a.a(_snowmanxxx)) {
            _snowmanx = _snowmanxxx.i();
            _snowmanx.e(1);
         }
      }

      if (_snowmanx != null && !_snowman.isEmpty()) {
         _snowmanx.a("Explosion").b("FadeColors", _snowman);
         return _snowmanx;
      } else {
         return bmb.b;
      }
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman * _snowman >= 2;
   }

   @Override
   public bos<?> ag_() {
      return bos.i;
   }
}
