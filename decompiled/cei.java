import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class cei<O, S extends cej<O, S>> {
   private static final Pattern a = Pattern.compile("^[a-z0-9_]+$");
   private final O b;
   private final ImmutableSortedMap<String, cfj<?>> c;
   private final ImmutableList<S> d;

   protected cei(Function<O, S> var1, O var2, cei.b<O, S> var3, Map<String, cfj<?>> var4) {
      this.b = _snowman;
      this.c = ImmutableSortedMap.copyOf(_snowman);
      Supplier<S> _snowman = () -> _snowman.apply(_snowman);
      MapCodec<S> _snowmanx = MapCodec.of(Encoder.empty(), Decoder.unit(_snowman));
      UnmodifiableIterator var7 = this.c.entrySet().iterator();

      while (var7.hasNext()) {
         Entry<String, cfj<?>> _snowmanxx = (Entry<String, cfj<?>>)var7.next();
         _snowmanx = a(_snowmanx, _snowman, _snowmanxx.getKey(), _snowmanxx.getValue());
      }

      MapCodec<S> _snowmanxx = _snowmanx;
      Map<Map<cfj<?>, Comparable<?>>, S> _snowmanxxx = Maps.newLinkedHashMap();
      List<S> _snowmanxxxx = Lists.newArrayList();
      Stream<List<Pair<cfj<?>, Comparable<?>>>> _snowmanxxxxx = Stream.of(Collections.emptyList());
      UnmodifiableIterator var11 = this.c.values().iterator();

      while (var11.hasNext()) {
         cfj<?> _snowmanxxxxxx = (cfj<?>)var11.next();
         _snowmanxxxxx = _snowmanxxxxx.flatMap(var1x -> _snowman.a().stream().map(var2x -> {
               List<Pair<cfj<?>, Comparable<?>>> _snowmanxxxxxxx = Lists.newArrayList(var1x);
               _snowmanxxxxxxx.add(Pair.of(_snowman, var2x));
               return _snowmanxxxxxxx;
            }));
      }

      _snowmanxxxxx.forEach(var5x -> {
         ImmutableMap<cfj<?>, Comparable<?>> _snowmanxxxxxx = var5x.stream().collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
         S _snowmanx = _snowman.create(_snowman, _snowmanxxxxxx, _snowman);
         _snowman.put(_snowmanxxxxxx, _snowmanx);
         _snowman.add(_snowmanx);
      });

      for (S _snowmanxxxxxx : _snowmanxxxx) {
         _snowmanxxxxxx.a(_snowmanxxx);
      }

      this.d = ImmutableList.copyOf(_snowmanxxxx);
   }

   private static <S extends cej<?, S>, T extends Comparable<T>> MapCodec<S> a(MapCodec<S> var0, Supplier<S> var1, String var2, cfj<T> var3) {
      return Codec.mapPair(_snowman, _snowman.e().fieldOf(_snowman).setPartial(() -> _snowman.a(_snowman.get())))
         .xmap(var1x -> (cej)((cej)var1x.getFirst()).a(_snowman, ((cfj.a)var1x.getSecond()).b()), var1x -> Pair.of(var1x, _snowman.a(var1x)));
   }

   public ImmutableList<S> a() {
      return this.d;
   }

   public S b() {
      return (S)this.d.get(0);
   }

   public O c() {
      return this.b;
   }

   public Collection<cfj<?>> d() {
      return this.c.values();
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
         .add("block", this.b)
         .add("properties", this.c.values().stream().map(cfj::f).collect(Collectors.toList()))
         .toString();
   }

   @Nullable
   public cfj<?> a(String var1) {
      return (cfj<?>)this.c.get(_snowman);
   }

   public static class a<O, S extends cej<O, S>> {
      private final O a;
      private final Map<String, cfj<?>> b = Maps.newHashMap();

      public a(O var1) {
         this.a = _snowman;
      }

      public cei.a<O, S> a(cfj<?>... var1) {
         for (cfj<?> _snowman : _snowman) {
            this.a(_snowman);
            this.b.put(_snowman.f(), _snowman);
         }

         return this;
      }

      private <T extends Comparable<T>> void a(cfj<T> var1) {
         String _snowman = _snowman.f();
         if (!cei.a.matcher(_snowman).matches()) {
            throw new IllegalArgumentException(this.a + " has invalidly named property: " + _snowman);
         } else {
            Collection<T> _snowmanx = _snowman.a();
            if (_snowmanx.size() <= 1) {
               throw new IllegalArgumentException(this.a + " attempted use property " + _snowman + " with <= 1 possible values");
            } else {
               for (T _snowmanxx : _snowmanx) {
                  String _snowmanxxx = _snowman.a(_snowmanxx);
                  if (!cei.a.matcher(_snowmanxxx).matches()) {
                     throw new IllegalArgumentException(this.a + " has property: " + _snowman + " with invalidly named value: " + _snowmanxxx);
                  }
               }

               if (this.b.containsKey(_snowman)) {
                  throw new IllegalArgumentException(this.a + " has duplicate property: " + _snowman);
               }
            }
         }
      }

      public cei<O, S> a(Function<O, S> var1, cei.b<O, S> var2) {
         return new cei<>(_snowman, this.a, _snowman, this.b);
      }
   }

   public interface b<O, S> {
      S create(O var1, ImmutableMap<cfj<?>, Comparable<?>> var2, MapCodec<S> var3);
   }
}
