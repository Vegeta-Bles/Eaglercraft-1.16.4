import com.google.common.collect.Lists;
import java.util.List;

public class bod extends boj {
   public bod(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      bmb _snowman = bmb.b;
      List<bmb> _snowmanx = Lists.newArrayList();

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            if (_snowmanxxx.b() instanceof blb) {
               if (!_snowman.a()) {
                  return false;
               }

               _snowman = _snowmanxxx;
            } else {
               if (!(_snowmanxxx.b() instanceof bky)) {
                  return false;
               }

               _snowmanx.add(_snowmanxxx);
            }
         }
      }

      return !_snowman.a() && !_snowmanx.isEmpty();
   }

   public bmb a(bio var1) {
      List<bky> _snowman = Lists.newArrayList();
      bmb _snowmanx = bmb.b;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            blx _snowmanxxxx = _snowmanxxx.b();
            if (_snowmanxxxx instanceof blb) {
               if (!_snowmanx.a()) {
                  return bmb.b;
               }

               _snowmanx = _snowmanxxx.i();
            } else {
               if (!(_snowmanxxxx instanceof bky)) {
                  return bmb.b;
               }

               _snowman.add((bky)_snowmanxxxx);
            }
         }
      }

      return !_snowmanx.a() && !_snowman.isEmpty() ? blb.a(_snowmanx, _snowman) : bmb.b;
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman * _snowman >= 2;
   }

   @Override
   public bos<?> ag_() {
      return bos.c;
   }
}
