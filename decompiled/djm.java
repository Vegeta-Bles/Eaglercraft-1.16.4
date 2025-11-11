import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

public class djm extends adt {
   private static final Logger c = LogManager.getLogger();
   private Map<dkg, List<drt>> d = ImmutableMap.of();
   private List<drt> e = ImmutableList.of();

   public djm() {
   }

   public void a(Iterable<boq<?>> var1) {
      Map<dkg, List<List<boq<?>>>> _snowman = b(_snowman);
      Map<dkg, List<drt>> _snowmanx = Maps.newHashMap();
      Builder<drt> _snowmanxx = ImmutableList.builder();
      _snowman.forEach((var2x, var3x) -> {
         List var10000 = _snowman.put(var2x, var3x.stream().map(drt::new).peek(_snowman::add).collect(ImmutableList.toImmutableList()));
      });
      dkg.w
         .forEach(
            (var1x, var2x) -> {
               List var10000 = _snowman.put(
                  var1x, var2x.stream().flatMap(var1xx -> _snowman.getOrDefault(var1xx, ImmutableList.of()).stream()).collect(ImmutableList.toImmutableList())
               );
            }
         );
      this.d = ImmutableMap.copyOf(_snowmanx);
      this.e = _snowmanxx.build();
   }

   private static Map<dkg, List<List<boq<?>>>> b(Iterable<boq<?>> var0) {
      Map<dkg, List<List<boq<?>>>> _snowman = Maps.newHashMap();
      Table<dkg, String, List<boq<?>>> _snowmanx = HashBasedTable.create();

      for (boq<?> _snowmanxx : _snowman) {
         if (!_snowmanxx.af_()) {
            dkg _snowmanxxx = g(_snowmanxx);
            String _snowmanxxxx = _snowmanxx.d();
            if (_snowmanxxxx.isEmpty()) {
               _snowman.computeIfAbsent(_snowmanxxx, var0x -> Lists.newArrayList()).add(ImmutableList.of(_snowmanxx));
            } else {
               List<boq<?>> _snowmanxxxxx = (List<boq<?>>)_snowmanx.get(_snowmanxxx, _snowmanxxxx);
               if (_snowmanxxxxx == null) {
                  _snowmanxxxxx = Lists.newArrayList();
                  _snowmanx.put(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
                  _snowman.computeIfAbsent(_snowmanxxx, var0x -> Lists.newArrayList()).add(_snowmanxxxxx);
               }

               _snowmanxxxxx.add(_snowmanxx);
            }
         }
      }

      return _snowman;
   }

   private static dkg g(boq<?> var0) {
      bot<?> _snowman = _snowman.g();
      if (_snowman == bot.a) {
         bmb _snowmanx = _snowman.c();
         bks _snowmanxx = _snowmanx.b().q();
         if (_snowmanxx == bks.b) {
            return dkg.b;
         } else if (_snowmanxx == bks.i || _snowmanxx == bks.j) {
            return dkg.d;
         } else {
            return _snowmanxx == bks.d ? dkg.c : dkg.e;
         }
      } else if (_snowman == bot.b) {
         if (_snowman.c().b().s()) {
            return dkg.g;
         } else {
            return _snowman.c().b() instanceof bkh ? dkg.h : dkg.i;
         }
      } else if (_snowman == bot.c) {
         return _snowman.c().b() instanceof bkh ? dkg.k : dkg.l;
      } else if (_snowman == bot.d) {
         return dkg.n;
      } else if (_snowman == bot.f) {
         return dkg.o;
      } else if (_snowman == bot.e) {
         return dkg.q;
      } else if (_snowman == bot.g) {
         return dkg.p;
      } else {
         c.warn("Unknown recipe category: {}/{}", new Supplier[]{() -> gm.ad.b(_snowman.g()), _snowman::f});
         return dkg.r;
      }
   }

   public List<drt> b() {
      return this.e;
   }

   public List<drt> a(dkg var1) {
      return this.d.getOrDefault(_snowman, Collections.emptyList());
   }
}
