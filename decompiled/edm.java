import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class edm implements edh.a {
   private final djz a;
   private final Map<Long, Map<fx, Integer>> b = Maps.newTreeMap(Ordering.natural().reverse());

   edm(djz var1) {
      this.a = _snowman;
   }

   public void a(long var1, fx var3) {
      Map<fx, Integer> _snowman = this.b.computeIfAbsent(_snowman, var0 -> Maps.newHashMap());
      int _snowmanx = _snowman.getOrDefault(_snowman, 0);
      _snowman.put(_snowman, _snowmanx + 1);
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      long _snowman = this.a.r.T();
      int _snowmanx = 200;
      double _snowmanxx = 0.0025;
      Set<fx> _snowmanxxx = Sets.newHashSet();
      Map<fx, Integer> _snowmanxxxx = Maps.newHashMap();
      dfq _snowmanxxxxx = _snowman.getBuffer(eao.t());
      Iterator<Entry<Long, Map<fx, Integer>>> _snowmanxxxxxx = this.b.entrySet().iterator();

      while (_snowmanxxxxxx.hasNext()) {
         Entry<Long, Map<fx, Integer>> _snowmanxxxxxxx = _snowmanxxxxxx.next();
         Long _snowmanxxxxxxxx = _snowmanxxxxxxx.getKey();
         Map<fx, Integer> _snowmanxxxxxxxxx = _snowmanxxxxxxx.getValue();
         long _snowmanxxxxxxxxxx = _snowman - _snowmanxxxxxxxx;
         if (_snowmanxxxxxxxxxx > 200L) {
            _snowmanxxxxxx.remove();
         } else {
            for (Entry<fx, Integer> _snowmanxxxxxxxxxxx : _snowmanxxxxxxxxx.entrySet()) {
               fx _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getKey();
               Integer _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getValue();
               if (_snowmanxxx.add(_snowmanxxxxxxxxxxxx)) {
                  dci _snowmanxxxxxxxxxxxxxx = new dci(fx.b)
                     .g(0.002)
                     .h(0.0025 * (double)_snowmanxxxxxxxxxx)
                     .d((double)_snowmanxxxxxxxxxxxx.u(), (double)_snowmanxxxxxxxxxxxx.v(), (double)_snowmanxxxxxxxxxxxx.w())
                     .d(-_snowman, -_snowman, -_snowman);
                  eae.a(
                     _snowman,
                     _snowmanxxxxx,
                     _snowmanxxxxxxxxxxxxxx.a,
                     _snowmanxxxxxxxxxxxxxx.b,
                     _snowmanxxxxxxxxxxxxxx.c,
                     _snowmanxxxxxxxxxxxxxx.d,
                     _snowmanxxxxxxxxxxxxxx.e,
                     _snowmanxxxxxxxxxxxxxx.f,
                     1.0F,
                     1.0F,
                     1.0F,
                     1.0F
                  );
                  _snowmanxxxx.put(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
               }
            }
         }
      }

      for (Entry<fx, Integer> _snowmanxxxxxxx : _snowmanxxxx.entrySet()) {
         fx _snowmanxxxxxxxx = _snowmanxxxxxxx.getKey();
         Integer _snowmanxxxxxxxxx = _snowmanxxxxxxx.getValue();
         edh.a(String.valueOf(_snowmanxxxxxxxxx), _snowmanxxxxxxxx.u(), _snowmanxxxxxxxx.v(), _snowmanxxxxxxxx.w(), -1);
      }
   }
}
