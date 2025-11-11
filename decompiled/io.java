import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

public class io implements il {
   private final buo a;
   private final List<ir> b;
   private final Set<cfj<?>> c = Sets.newHashSet();
   private final List<ip> d = Lists.newArrayList();

   private io(buo var1, List<ir> var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public io a(ip var1) {
      _snowman.b().forEach(var1x -> {
         if (this.a.m().a(var1x.f()) != var1x) {
            throw new IllegalStateException("Property " + var1x + " is not defined for block " + this.a);
         } else if (!this.c.add((cfj<?>)var1x)) {
            throw new IllegalStateException("Values of property " + var1x + " already defined for block " + this.a);
         }
      });
      this.d.add(_snowman);
      return this;
   }

   public JsonElement b() {
      Stream<Pair<iq, List<ir>>> _snowman = Stream.of(Pair.of(iq.a(), this.b));

      for (ip _snowmanx : this.d) {
         Map<iq, List<ir>> _snowmanxx = _snowmanx.a();
         _snowman = _snowman.flatMap(var1x -> _snowman.entrySet().stream().map(var1xx -> {
               iq _snowmanxxx = ((iq)var1x.getFirst()).a(var1xx.getKey());
               List<ir> _snowmanx = a((List<ir>)var1x.getSecond(), var1xx.getValue());
               return Pair.of(_snowmanxxx, _snowmanx);
            }));
      }

      Map<String, JsonElement> _snowmanx = new TreeMap<>();
      _snowman.forEach(var1x -> {
         JsonElement var10000 = _snowman.put(((iq)var1x.getFirst()).b(), ir.a((List<ir>)var1x.getSecond()));
      });
      JsonObject _snowmanxx = new JsonObject();
      _snowmanxx.add("variants", x.a(new JsonObject(), var1x -> _snowman.forEach(var1x::add)));
      return _snowmanxx;
   }

   private static List<ir> a(List<ir> var0, List<ir> var1) {
      Builder<ir> _snowman = ImmutableList.builder();
      _snowman.forEach(var2x -> _snowman.forEach(var2xx -> _snowman.add(ir.a(var2x, var2xx))));
      return _snowman.build();
   }

   @Override
   public buo a() {
      return this.a;
   }

   public static io a(buo var0) {
      return new io(_snowman, ImmutableList.of(ir.a()));
   }

   public static io a(buo var0, ir var1) {
      return new io(_snowman, ImmutableList.of(_snowman));
   }

   public static io a(buo var0, ir... var1) {
      return new io(_snowman, ImmutableList.copyOf(_snowman));
   }
}
