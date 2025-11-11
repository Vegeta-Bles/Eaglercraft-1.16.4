import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class bom extends boj {
   private static final bon a = bon.a(bmd.oS, bmd.kT, bmd.nt, bmd.pe, bmd.pf, bmd.pi, bmd.pg, bmd.pj, bmd.ph);
   private static final bon b = bon.a(bmd.kg);
   private static final bon c = bon.a(bmd.mk);
   private static final Map<blx, blm.a> d = x.a(Maps.newHashMap(), var0 -> {
      var0.put(bmd.oS, blm.a.b);
      var0.put(bmd.kT, blm.a.e);
      var0.put(bmd.nt, blm.a.c);
      var0.put(bmd.pe, blm.a.d);
      var0.put(bmd.pf, blm.a.d);
      var0.put(bmd.pi, blm.a.d);
      var0.put(bmd.pg, blm.a.d);
      var0.put(bmd.pj, blm.a.d);
      var0.put(bmd.ph, blm.a.d);
   });
   private static final bon e = bon.a(bmd.kU);

   public bom(vk var1) {
      super(_snowman);
   }

   public boolean a(bio var1, brx var2) {
      boolean _snowman = false;
      boolean _snowmanx = false;
      boolean _snowmanxx = false;
      boolean _snowmanxxx = false;
      boolean _snowmanxxxx = false;

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman.Z_(); _snowmanxxxxx++) {
         bmb _snowmanxxxxxx = _snowman.a(_snowmanxxxxx);
         if (!_snowmanxxxxxx.a()) {
            if (a.a(_snowmanxxxxxx)) {
               if (_snowmanxx) {
                  return false;
               }

               _snowmanxx = true;
            } else if (c.a(_snowmanxxxxxx)) {
               if (_snowmanxxxx) {
                  return false;
               }

               _snowmanxxxx = true;
            } else if (b.a(_snowmanxxxxxx)) {
               if (_snowmanxxx) {
                  return false;
               }

               _snowmanxxx = true;
            } else if (e.a(_snowmanxxxxxx)) {
               if (_snowman) {
                  return false;
               }

               _snowman = true;
            } else {
               if (!(_snowmanxxxxxx.b() instanceof bky)) {
                  return false;
               }

               _snowmanx = true;
            }
         }
      }

      return _snowman && _snowmanx;
   }

   public bmb a(bio var1) {
      bmb _snowman = new bmb(bmd.pp);
      md _snowmanx = _snowman.a("Explosion");
      blm.a _snowmanxx = blm.a.a;
      List<Integer> _snowmanxxx = Lists.newArrayList();

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.Z_(); _snowmanxxxx++) {
         bmb _snowmanxxxxx = _snowman.a(_snowmanxxxx);
         if (!_snowmanxxxxx.a()) {
            if (a.a(_snowmanxxxxx)) {
               _snowmanxx = d.get(_snowmanxxxxx.b());
            } else if (c.a(_snowmanxxxxx)) {
               _snowmanx.a("Flicker", true);
            } else if (b.a(_snowmanxxxxx)) {
               _snowmanx.a("Trail", true);
            } else if (_snowmanxxxxx.b() instanceof bky) {
               _snowmanxxx.add(((bky)_snowmanxxxxx.b()).d().g());
            }
         }
      }

      _snowmanx.b("Colors", _snowmanxxx);
      _snowmanx.a("Type", (byte)_snowmanxx.a());
      return _snowman;
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman * _snowman >= 2;
   }

   @Override
   public bmb c() {
      return new bmb(bmd.pp);
   }

   @Override
   public bos<?> ag_() {
      return bos.h;
   }
}
