import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class edq implements edh.a {
   private final djz a;
   private final Map<chd, Map<String, cra>> b = Maps.newIdentityHashMap();
   private final Map<chd, Map<String, cra>> c = Maps.newIdentityHashMap();
   private final Map<chd, Map<String, Boolean>> d = Maps.newIdentityHashMap();

   public edq(djz var1) {
      this.a = _snowman;
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      djk _snowman = this.a.h.k();
      bry _snowmanx = this.a.r;
      chd _snowmanxx = _snowmanx.k();
      fx _snowmanxxx = new fx(_snowman.b().b, 0.0, _snowman.b().d);
      dfq _snowmanxxxx = _snowman.getBuffer(eao.t());
      if (this.b.containsKey(_snowmanxx)) {
         for (cra _snowmanxxxxx : this.b.get(_snowmanxx).values()) {
            if (_snowmanxxx.a(_snowmanxxxxx.g(), 500.0)) {
               eae.a(
                  _snowman,
                  _snowmanxxxx,
                  (double)_snowmanxxxxx.a - _snowman,
                  (double)_snowmanxxxxx.b - _snowman,
                  (double)_snowmanxxxxx.c - _snowman,
                  (double)(_snowmanxxxxx.d + 1) - _snowman,
                  (double)(_snowmanxxxxx.e + 1) - _snowman,
                  (double)(_snowmanxxxxx.f + 1) - _snowman,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F
               );
            }
         }
      }

      if (this.c.containsKey(_snowmanxx)) {
         for (Entry<String, cra> _snowmanxxxxxx : this.c.get(_snowmanxx).entrySet()) {
            String _snowmanxxxxxxx = _snowmanxxxxxx.getKey();
            cra _snowmanxxxxxxxx = _snowmanxxxxxx.getValue();
            Boolean _snowmanxxxxxxxxx = this.d.get(_snowmanxx).get(_snowmanxxxxxxx);
            if (_snowmanxxx.a(_snowmanxxxxxxxx.g(), 500.0)) {
               if (_snowmanxxxxxxxxx) {
                  eae.a(
                     _snowman,
                     _snowmanxxxx,
                     (double)_snowmanxxxxxxxx.a - _snowman,
                     (double)_snowmanxxxxxxxx.b - _snowman,
                     (double)_snowmanxxxxxxxx.c - _snowman,
                     (double)(_snowmanxxxxxxxx.d + 1) - _snowman,
                     (double)(_snowmanxxxxxxxx.e + 1) - _snowman,
                     (double)(_snowmanxxxxxxxx.f + 1) - _snowman,
                     0.0F,
                     1.0F,
                     0.0F,
                     1.0F,
                     0.0F,
                     1.0F,
                     0.0F
                  );
               } else {
                  eae.a(
                     _snowman,
                     _snowmanxxxx,
                     (double)_snowmanxxxxxxxx.a - _snowman,
                     (double)_snowmanxxxxxxxx.b - _snowman,
                     (double)_snowmanxxxxxxxx.c - _snowman,
                     (double)(_snowmanxxxxxxxx.d + 1) - _snowman,
                     (double)(_snowmanxxxxxxxx.e + 1) - _snowman,
                     (double)(_snowmanxxxxxxxx.f + 1) - _snowman,
                     0.0F,
                     0.0F,
                     1.0F,
                     1.0F,
                     0.0F,
                     0.0F,
                     1.0F
                  );
               }
            }
         }
      }
   }

   public void a(cra var1, List<cra> var2, List<Boolean> var3, chd var4) {
      if (!this.b.containsKey(_snowman)) {
         this.b.put(_snowman, Maps.newHashMap());
      }

      if (!this.c.containsKey(_snowman)) {
         this.c.put(_snowman, Maps.newHashMap());
         this.d.put(_snowman, Maps.newHashMap());
      }

      this.b.get(_snowman).put(_snowman.toString(), _snowman);

      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         cra _snowmanx = _snowman.get(_snowman);
         Boolean _snowmanxx = _snowman.get(_snowman);
         this.c.get(_snowman).put(_snowmanx.toString(), _snowmanx);
         this.d.get(_snowman).put(_snowmanx.toString(), _snowmanxx);
      }
   }

   @Override
   public void a() {
      this.b.clear();
      this.c.clear();
      this.d.clear();
   }
}
