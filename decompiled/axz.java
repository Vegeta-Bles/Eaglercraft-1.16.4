import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class axz {
   private final Map<UUID, axz.a> a = Maps.newHashMap();

   public axz() {
   }

   public void b() {
      Iterator<axz.a> _snowman = this.a.values().iterator();

      while (_snowman.hasNext()) {
         axz.a _snowmanx = _snowman.next();
         _snowmanx.a();
         if (_snowmanx.b()) {
            _snowman.remove();
         }
      }
   }

   private Stream<axz.b> c() {
      return this.a.entrySet().stream().flatMap(var0 -> var0.getValue().a(var0.getKey()));
   }

   private Collection<axz.b> a(Random var1, int var2) {
      List<axz.b> _snowman = this.c().collect(Collectors.toList());
      if (_snowman.isEmpty()) {
         return Collections.emptyList();
      } else {
         int[] _snowmanx = new int[_snowman.size()];
         int _snowmanxx = 0;

         for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
            axz.b _snowmanxxxx = _snowman.get(_snowmanxxx);
            _snowmanxx += Math.abs(_snowmanxxxx.a());
            _snowmanx[_snowmanxxx] = _snowmanxx - 1;
         }

         Set<axz.b> _snowmanxxx = Sets.newIdentityHashSet();

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman; _snowmanxxxx++) {
            int _snowmanxxxxx = _snowman.nextInt(_snowmanxx);
            int _snowmanxxxxxx = Arrays.binarySearch(_snowmanx, _snowmanxxxxx);
            _snowmanxxx.add(_snowman.get(_snowmanxxxxxx < 0 ? -_snowmanxxxxxx - 1 : _snowmanxxxxxx));
         }

         return _snowmanxxx;
      }
   }

   private axz.a a(UUID var1) {
      return this.a.computeIfAbsent(_snowman, var0 -> new axz.a());
   }

   public void a(axz var1, Random var2, int var3) {
      Collection<axz.b> _snowman = _snowman.a(_snowman, _snowman);
      _snowman.forEach(var1x -> {
         int _snowmanx = var1x.c - var1x.b.j;
         if (_snowmanx >= 2) {
            this.a(var1x.a).a.mergeInt(var1x.b, _snowmanx, axz::a);
         }
      });
   }

   public int a(UUID var1, Predicate<aya> var2) {
      axz.a _snowman = this.a.get(_snowman);
      return _snowman != null ? _snowman.a(_snowman) : 0;
   }

   public void a(UUID var1, aya var2, int var3) {
      axz.a _snowman = this.a(_snowman);
      _snowman.a.mergeInt(_snowman, _snowman, (var2x, var3x) -> this.a(_snowman, var2x.intValue(), var3x.intValue()));
      _snowman.a(_snowman);
      if (_snowman.b()) {
         this.a.remove(_snowman);
      }
   }

   public <T> Dynamic<T> a(DynamicOps<T> var1) {
      return new Dynamic(_snowman, _snowman.createList(this.c().map(var1x -> var1x.a(_snowman)).map(Dynamic::getValue)));
   }

   public void a(Dynamic<?> var1) {
      _snowman.asStream().map(axz.b::a).flatMap(var0 -> x.a(var0.result())).forEach(var1x -> this.a(var1x.a).a.put(var1x.b, var1x.c));
   }

   private static int a(int var0, int var1) {
      return Math.max(_snowman, _snowman);
   }

   private int a(aya var1, int var2, int var3) {
      int _snowman = _snowman + _snowman;
      return _snowman > _snowman.h ? Math.max(_snowman.h, _snowman) : _snowman;
   }

   static class a {
      private final Object2IntMap<aya> a = new Object2IntOpenHashMap();

      private a() {
      }

      public int a(Predicate<aya> var1) {
         return this.a
            .object2IntEntrySet()
            .stream()
            .filter(var1x -> _snowman.test((aya)var1x.getKey()))
            .mapToInt(var0 -> var0.getIntValue() * ((aya)var0.getKey()).g)
            .sum();
      }

      public Stream<axz.b> a(UUID var1) {
         return this.a.object2IntEntrySet().stream().map(var1x -> new axz.b(_snowman, (aya)var1x.getKey(), var1x.getIntValue()));
      }

      public void a() {
         ObjectIterator<Entry<aya>> _snowman = this.a.object2IntEntrySet().iterator();

         while (_snowman.hasNext()) {
            Entry<aya> _snowmanx = (Entry<aya>)_snowman.next();
            int _snowmanxx = _snowmanx.getIntValue() - ((aya)_snowmanx.getKey()).i;
            if (_snowmanxx < 2) {
               _snowman.remove();
            } else {
               _snowmanx.setValue(_snowmanxx);
            }
         }
      }

      public boolean b() {
         return this.a.isEmpty();
      }

      public void a(aya var1) {
         int _snowman = this.a.getInt(_snowman);
         if (_snowman > _snowman.h) {
            this.a.put(_snowman, _snowman.h);
         }

         if (_snowman < 2) {
            this.b(_snowman);
         }
      }

      public void b(aya var1) {
         this.a.removeInt(_snowman);
      }
   }

   static class b {
      public final UUID a;
      public final aya b;
      public final int c;

      public b(UUID var1, aya var2, int var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public int a() {
         return this.c * this.b.g;
      }

      @Override
      public String toString() {
         return "GossipEntry{target=" + this.a + ", type=" + this.b + ", value=" + this.c + '}';
      }

      public <T> Dynamic<T> a(DynamicOps<T> var1) {
         return new Dynamic(
            _snowman,
            _snowman.createMap(
               ImmutableMap.of(
                  _snowman.createString("Target"),
                  gq.a.encodeStart(_snowman, this.a).result().orElseThrow(RuntimeException::new),
                  _snowman.createString("Type"),
                  _snowman.createString(this.b.f),
                  _snowman.createString("Value"),
                  _snowman.createInt(this.c)
               )
            )
         );
      }

      public static DataResult<axz.b> a(Dynamic<?> var0) {
         return DataResult.unbox(
            DataResult.instance()
               .group(_snowman.get("Target").read(gq.a), _snowman.get("Type").asString().map(aya::a), _snowman.get("Value").asNumber().map(Number::intValue))
               .apply(DataResult.instance(), axz.b::new)
         );
      }
   }
}
