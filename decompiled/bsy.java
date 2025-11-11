import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public abstract class bsy implements bsx.a {
   public static final Codec<bsy> a = gm.bb.dispatchStable(bsy::a, Function.identity());
   protected final Map<cla<?>, Boolean> b = Maps.newHashMap();
   protected final Set<ceh> c = Sets.newHashSet();
   protected final List<bsv> d;

   protected bsy(Stream<Supplier<bsv>> var1) {
      this(_snowman.map(Supplier::get).collect(ImmutableList.toImmutableList()));
   }

   protected bsy(List<bsv> var1) {
      this.d = _snowman;
   }

   protected abstract Codec<? extends bsy> a();

   public abstract bsy a(long var1);

   public List<bsv> b() {
      return this.d;
   }

   public Set<bsv> a(int var1, int var2, int var3, int var4) {
      int _snowman = _snowman - _snowman >> 2;
      int _snowmanx = _snowman - _snowman >> 2;
      int _snowmanxx = _snowman - _snowman >> 2;
      int _snowmanxxx = _snowman + _snowman >> 2;
      int _snowmanxxxx = _snowman + _snowman >> 2;
      int _snowmanxxxxx = _snowman + _snowman >> 2;
      int _snowmanxxxxxx = _snowmanxxx - _snowman + 1;
      int _snowmanxxxxxxx = _snowmanxxxx - _snowmanx + 1;
      int _snowmanxxxxxxxx = _snowmanxxxxx - _snowmanxx + 1;
      Set<bsv> _snowmanxxxxxxxxx = Sets.newHashSet();

      for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxx = _snowman + _snowmanxxxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxx = _snowmanx + _snowmanxxxxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxxx = _snowmanxx + _snowmanxxxxxxxxxx;
               _snowmanxxxxxxxxx.add(this.b(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx));
            }
         }
      }

      return _snowmanxxxxxxxxx;
   }

   @Nullable
   public fx a(int var1, int var2, int var3, int var4, Predicate<bsv> var5, Random var6) {
      return this.a(_snowman, _snowman, _snowman, _snowman, 1, _snowman, _snowman, false);
   }

   @Nullable
   public fx a(int var1, int var2, int var3, int var4, int var5, Predicate<bsv> var6, Random var7, boolean var8) {
      int _snowman = _snowman >> 2;
      int _snowmanx = _snowman >> 2;
      int _snowmanxx = _snowman >> 2;
      int _snowmanxxx = _snowman >> 2;
      fx _snowmanxxxx = null;
      int _snowmanxxxxx = 0;
      int _snowmanxxxxxx = _snowman ? 0 : _snowmanxx;
      int _snowmanxxxxxxx = _snowmanxxxxxx;

      while (_snowmanxxxxxxx <= _snowmanxx) {
         for (int _snowmanxxxxxxxx = -_snowmanxxxxxxx; _snowmanxxxxxxxx <= _snowmanxxxxxxx; _snowmanxxxxxxxx += _snowman) {
            boolean _snowmanxxxxxxxxx = Math.abs(_snowmanxxxxxxxx) == _snowmanxxxxxxx;

            for (int _snowmanxxxxxxxxxx = -_snowmanxxxxxxx; _snowmanxxxxxxxxxx <= _snowmanxxxxxxx; _snowmanxxxxxxxxxx += _snowman) {
               if (_snowman) {
                  boolean _snowmanxxxxxxxxxxx = Math.abs(_snowmanxxxxxxxxxx) == _snowmanxxxxxxx;
                  if (!_snowmanxxxxxxxxxxx && !_snowmanxxxxxxxxx) {
                     continue;
                  }
               }

               int _snowmanxxxxxxxxxxx = _snowman + _snowmanxxxxxxxxxx;
               int _snowmanxxxxxxxxxxxx = _snowmanx + _snowmanxxxxxxxx;
               if (_snowman.test(this.b(_snowmanxxxxxxxxxxx, _snowmanxxx, _snowmanxxxxxxxxxxxx))) {
                  if (_snowmanxxxx == null || _snowman.nextInt(_snowmanxxxxx + 1) == 0) {
                     _snowmanxxxx = new fx(_snowmanxxxxxxxxxxx << 2, _snowman, _snowmanxxxxxxxxxxxx << 2);
                     if (_snowman) {
                        return _snowmanxxxx;
                     }
                  }

                  _snowmanxxxxx++;
               }
            }
         }

         _snowmanxxxxxxx += _snowman;
      }

      return _snowmanxxxx;
   }

   public boolean a(cla<?> var1) {
      return this.b.computeIfAbsent(_snowman, var1x -> this.d.stream().anyMatch(var1xx -> var1xx.e().a(var1x)));
   }

   public Set<ceh> c() {
      if (this.c.isEmpty()) {
         for (bsv _snowman : this.d) {
            this.c.add(_snowman.e().e().a());
         }
      }

      return this.c;
   }

   static {
      gm.a(gm.bb, "fixed", btd.e);
      gm.a(gm.bb, "multi_noise", bth.f);
      gm.a(gm.bb, "checkerboard", btc.e);
      gm.a(gm.bb, "vanilla_layered", btj.e);
      gm.a(gm.bb, "the_end", btk.e);
   }
}
