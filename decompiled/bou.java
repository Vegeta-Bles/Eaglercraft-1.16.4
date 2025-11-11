import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class bou extends boj {
   public bou(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      List<bmb> _snowman = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx < _snowman.Z_(); _snowmanx++) {
         bmb _snowmanxx = _snowman.a(_snowmanx);
         if (!_snowmanxx.a()) {
            _snowman.add(_snowmanxx);
            if (_snowman.size() > 1) {
               bmb _snowmanxxx = _snowman.get(0);
               if (_snowmanxx.b() != _snowmanxxx.b() || _snowmanxxx.E() != 1 || _snowmanxx.E() != 1 || !_snowmanxxx.b().k()) {
                  return false;
               }
            }
         }
      }

      return _snowman.size() == 2;
   }

   public bmb a(bio var1) {
      List<bmb> _snowman = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx < _snowman.Z_(); _snowmanx++) {
         bmb _snowmanxx = _snowman.a(_snowmanx);
         if (!_snowmanxx.a()) {
            _snowman.add(_snowmanxx);
            if (_snowman.size() > 1) {
               bmb _snowmanxxx = _snowman.get(0);
               if (_snowmanxx.b() != _snowmanxxx.b() || _snowmanxxx.E() != 1 || _snowmanxx.E() != 1 || !_snowmanxxx.b().k()) {
                  return bmb.b;
               }
            }
         }
      }

      if (_snowman.size() == 2) {
         bmb _snowmanxx = _snowman.get(0);
         bmb _snowmanxxx = _snowman.get(1);
         if (_snowmanxx.b() == _snowmanxxx.b() && _snowmanxx.E() == 1 && _snowmanxxx.E() == 1 && _snowmanxx.b().k()) {
            blx _snowmanxxxx = _snowmanxx.b();
            int _snowmanxxxxx = _snowmanxxxx.j() - _snowmanxx.g();
            int _snowmanxxxxxx = _snowmanxxxx.j() - _snowmanxxx.g();
            int _snowmanxxxxxxx = _snowmanxxxxx + _snowmanxxxxxx + _snowmanxxxx.j() * 5 / 100;
            int _snowmanxxxxxxxx = _snowmanxxxx.j() - _snowmanxxxxxxx;
            if (_snowmanxxxxxxxx < 0) {
               _snowmanxxxxxxxx = 0;
            }

            bmb _snowmanxxxxxxxxx = new bmb(_snowmanxx.b());
            _snowmanxxxxxxxxx.b(_snowmanxxxxxxxx);
            Map<bps, Integer> _snowmanxxxxxxxxxx = Maps.newHashMap();
            Map<bps, Integer> _snowmanxxxxxxxxxxx = bpu.a(_snowmanxx);
            Map<bps, Integer> _snowmanxxxxxxxxxxxx = bpu.a(_snowmanxxx);
            gm.R.g().filter(bps::c).forEach(var3x -> {
               int _snowmanxxxxxxxxxxxxx = Math.max(_snowman.getOrDefault(var3x, 0), _snowman.getOrDefault(var3x, 0));
               if (_snowmanxxxxxxxxxxxxx > 0) {
                  _snowman.put(var3x, _snowmanxxxxxxxxxxxxx);
               }
            });
            if (!_snowmanxxxxxxxxxx.isEmpty()) {
               bpu.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx);
            }

            return _snowmanxxxxxxxxx;
         }
      }

      return bmb.b;
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman * _snowman >= 2;
   }

   @Override
   public bos<?> ag_() {
      return bos.o;
   }
}
