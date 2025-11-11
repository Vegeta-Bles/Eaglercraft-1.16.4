import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bor extends acj {
   private static final Gson a = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private static final Logger b = LogManager.getLogger();
   private Map<bot<?>, Map<vk, boq<?>>> c = ImmutableMap.of();
   private boolean d;

   public bor() {
      super(a, "recipes");
   }

   protected void a(Map<vk, JsonElement> var1, ach var2, anw var3) {
      this.d = false;
      Map<bot<?>, Builder<vk, boq<?>>> _snowman = Maps.newHashMap();

      for (Entry<vk, JsonElement> _snowmanx : _snowman.entrySet()) {
         vk _snowmanxx = _snowmanx.getKey();

         try {
            boq<?> _snowmanxxx = a(_snowmanxx, afd.m(_snowmanx.getValue(), "top element"));
            _snowman.computeIfAbsent(_snowmanxxx.g(), var0 -> ImmutableMap.builder()).put(_snowmanxx, _snowmanxxx);
         } catch (IllegalArgumentException | JsonParseException var9) {
            b.error("Parsing error loading recipe {}", _snowmanxx, var9);
         }
      }

      this.c = _snowman.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> ((Builder)var0.getValue()).build()));
      b.info("Loaded {} recipes", _snowman.size());
   }

   public <C extends aon, T extends boq<C>> Optional<T> a(bot<T> var1, C var2, brx var3) {
      return this.b(_snowman).values().stream().flatMap(var3x -> x.a(_snowman.a((boq<C>)var3x, _snowman, _snowman))).findFirst();
   }

   public <C extends aon, T extends boq<C>> List<T> a(bot<T> var1) {
      return this.b(_snowman).values().stream().map(var0 -> (boq)var0).collect(Collectors.toList());
   }

   public <C extends aon, T extends boq<C>> List<T> b(bot<T> var1, C var2, brx var3) {
      return this.b(_snowman)
         .values()
         .stream()
         .flatMap(var3x -> x.a(_snowman.a((boq<C>)var3x, _snowman, _snowman)))
         .sorted(Comparator.comparing(var0 -> var0.c().j()))
         .collect(Collectors.toList());
   }

   private <C extends aon, T extends boq<C>> Map<vk, boq<C>> b(bot<T> var1) {
      return (Map<vk, boq<C>>)this.c.getOrDefault(_snowman, Collections.emptyMap());
   }

   public <C extends aon, T extends boq<C>> gj<bmb> c(bot<T> var1, C var2, brx var3) {
      Optional<T> _snowman = this.a(_snowman, _snowman, _snowman);
      if (_snowman.isPresent()) {
         return _snowman.get().b(_snowman);
      } else {
         gj<bmb> _snowmanx = gj.a(_snowman.Z_(), bmb.b);

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            _snowmanx.set(_snowmanxx, _snowman.a(_snowmanxx));
         }

         return _snowmanx;
      }
   }

   public Optional<? extends boq<?>> a(vk var1) {
      return this.c.values().stream().map(var1x -> var1x.get(_snowman)).filter(Objects::nonNull).findFirst();
   }

   public Collection<boq<?>> b() {
      return this.c.values().stream().flatMap(var0 -> var0.values().stream()).collect(Collectors.toSet());
   }

   public Stream<vk> d() {
      return this.c.values().stream().flatMap(var0 -> var0.keySet().stream());
   }

   public static boq<?> a(vk var0, JsonObject var1) {
      String _snowman = afd.h(_snowman, "type");
      return gm.ae.b(new vk(_snowman)).orElseThrow(() -> new JsonSyntaxException("Invalid or unsupported recipe type '" + _snowman + "'")).a(_snowman, _snowman);
   }

   public void a(Iterable<boq<?>> var1) {
      this.d = false;
      Map<bot<?>, Map<vk, boq<?>>> _snowman = Maps.newHashMap();
      _snowman.forEach(var1x -> {
         Map<vk, boq<?>> _snowmanx = _snowman.computeIfAbsent(var1x.g(), var0x -> Maps.newHashMap());
         boq<?> _snowmanx = _snowmanx.put(var1x.f(), (boq<?>)var1x);
         if (_snowmanx != null) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + var1x.f());
         }
      });
      this.c = ImmutableMap.copyOf(_snowman);
   }
}
