import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class cxf {
   private final cxb[] a = new cxb[32];
   private final int b;
   private final cxc c;
   private final cwy d = new cwy();

   public cxf(cxc var1, int var2) {
      this.c = _snowman;
      this.b = _snowman;
   }

   @Nullable
   public cxd a(bsi var1, aqn var2, Set<fx> var3, float var4, int var5, float var6) {
      this.d.a();
      this.c.a(_snowman, _snowman);
      cxb _snowman = this.c.b();
      Map<cxh, fx> _snowmanx = _snowman.stream().collect(Collectors.toMap(var1x -> this.c.a((double)var1x.u(), (double)var1x.v(), (double)var1x.w()), Function.identity()));
      cxd _snowmanxx = this.a(_snowman, _snowmanx, _snowman, _snowman, _snowman);
      this.c.a();
      return _snowmanxx;
   }

   @Nullable
   private cxd a(cxb var1, Map<cxh, fx> var2, float var3, int var4, float var5) {
      Set<cxh> _snowman = _snowman.keySet();
      _snowman.e = 0.0F;
      _snowman.f = this.a(_snowman, _snowman);
      _snowman.g = _snowman.f;
      this.d.a();
      this.d.a(_snowman);
      Set<cxb> _snowmanx = ImmutableSet.of();
      int _snowmanxx = 0;
      Set<cxh> _snowmanxxx = Sets.newHashSetWithExpectedSize(_snowman.size());
      int _snowmanxxxx = (int)((float)this.b * _snowman);

      while (!this.d.e()) {
         if (++_snowmanxx >= _snowmanxxxx) {
            break;
         }

         cxb _snowmanxxxxx = this.d.c();
         _snowmanxxxxx.i = true;

         for (cxh _snowmanxxxxxx : _snowman) {
            if (_snowmanxxxxx.c(_snowmanxxxxxx) <= (float)_snowman) {
               _snowmanxxxxxx.e();
               _snowmanxxx.add(_snowmanxxxxxx);
            }
         }

         if (!_snowmanxxx.isEmpty()) {
            break;
         }

         if (!(_snowmanxxxxx.a(_snowman) >= _snowman)) {
            int _snowmanxxxxxxx = this.c.a(this.a, _snowmanxxxxx);

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxx++) {
               cxb _snowmanxxxxxxxxx = this.a[_snowmanxxxxxxxx];
               float _snowmanxxxxxxxxxx = _snowmanxxxxx.a(_snowmanxxxxxxxxx);
               _snowmanxxxxxxxxx.j = _snowmanxxxxx.j + _snowmanxxxxxxxxxx;
               float _snowmanxxxxxxxxxxx = _snowmanxxxxx.e + _snowmanxxxxxxxxxx + _snowmanxxxxxxxxx.k;
               if (_snowmanxxxxxxxxx.j < _snowman && (!_snowmanxxxxxxxxx.c() || _snowmanxxxxxxxxxxx < _snowmanxxxxxxxxx.e)) {
                  _snowmanxxxxxxxxx.h = _snowmanxxxxx;
                  _snowmanxxxxxxxxx.e = _snowmanxxxxxxxxxxx;
                  _snowmanxxxxxxxxx.f = this.a(_snowmanxxxxxxxxx, _snowman) * 1.5F;
                  if (_snowmanxxxxxxxxx.c()) {
                     this.d.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxx.e + _snowmanxxxxxxxxx.f);
                  } else {
                     _snowmanxxxxxxxxx.g = _snowmanxxxxxxxxx.e + _snowmanxxxxxxxxx.f;
                     this.d.a(_snowmanxxxxxxxxx);
                  }
               }
            }
         }
      }

      Optional<cxd> _snowmanxxxxx = !_snowmanxxx.isEmpty()
         ? _snowmanxxx.stream().map(var2x -> this.a(var2x.d(), _snowman.get(var2x), true)).min(Comparator.comparingInt(cxd::e))
         : _snowman.stream().map(var2x -> this.a(var2x.d(), _snowman.get(var2x), false)).min(Comparator.comparingDouble(cxd::n).thenComparingInt(cxd::e));
      return !_snowmanxxxxx.isPresent() ? null : _snowmanxxxxx.get();
   }

   private float a(cxb var1, Set<cxh> var2) {
      float _snowman = Float.MAX_VALUE;

      for (cxh _snowmanx : _snowman) {
         float _snowmanxx = _snowman.a(_snowmanx);
         _snowmanx.a(_snowmanxx, _snowman);
         _snowman = Math.min(_snowmanxx, _snowman);
      }

      return _snowman;
   }

   private cxd a(cxb var1, fx var2, boolean var3) {
      List<cxb> _snowman = Lists.newArrayList();
      cxb _snowmanx = _snowman;
      _snowman.add(0, _snowman);

      while (_snowmanx.h != null) {
         _snowmanx = _snowmanx.h;
         _snowman.add(0, _snowmanx);
      }

      return new cxd(_snowman, _snowman, _snowman);
   }
}
