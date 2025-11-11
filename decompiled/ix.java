import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ix {
   private final Optional<vk> a;
   private final Set<ja> b;
   private Optional<String> c;

   public ix(Optional<vk> var1, Optional<String> var2, ja... var3) {
      this.a = _snowman;
      this.c = _snowman;
      this.b = ImmutableSet.copyOf(_snowman);
   }

   public vk a(buo var1, iz var2, BiConsumer<vk, Supplier<JsonElement>> var3) {
      return this.a(iw.a(_snowman, this.c.orElse("")), _snowman, _snowman);
   }

   public vk a(buo var1, String var2, iz var3, BiConsumer<vk, Supplier<JsonElement>> var4) {
      return this.a(iw.a(_snowman, _snowman + this.c.orElse("")), _snowman, _snowman);
   }

   public vk b(buo var1, String var2, iz var3, BiConsumer<vk, Supplier<JsonElement>> var4) {
      return this.a(iw.a(_snowman, _snowman), _snowman, _snowman);
   }

   public vk a(vk var1, iz var2, BiConsumer<vk, Supplier<JsonElement>> var3) {
      Map<ja, vk> _snowman = this.a(_snowman);
      _snowman.accept(_snowman, () -> {
         JsonObject _snowmanx = new JsonObject();
         this.a.ifPresent(var1x -> _snowman.addProperty("parent", var1x.toString()));
         if (!_snowman.isEmpty()) {
            JsonObject _snowmanx = new JsonObject();
            _snowman.forEach((var1x, var2x) -> _snowman.addProperty(var1x.a(), var2x.toString()));
            _snowmanx.add("textures", _snowmanx);
         }

         return _snowmanx;
      });
      return _snowman;
   }

   private Map<ja, vk> a(iz var1) {
      return Streams.concat(new Stream[]{this.b.stream(), _snowman.a()}).collect(ImmutableMap.toImmutableMap(Function.identity(), _snowman::a));
   }
}
