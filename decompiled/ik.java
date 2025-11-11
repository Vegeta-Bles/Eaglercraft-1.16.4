import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ik implements hm {
   private static final Logger b = LogManager.getLogger();
   private static final Gson c = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private final hl d;

   public ik(hl var1) {
      this.d = _snowman;
   }

   @Override
   public void a(hn var1) {
      Path _snowman = this.d.b();
      Map<buo, il> _snowmanx = Maps.newHashMap();
      Consumer<il> _snowmanxx = var1x -> {
         buo _snowmanxxx = var1x.a();
         il _snowmanx = _snowman.put(_snowmanxxx, var1x);
         if (_snowmanx != null) {
            throw new IllegalStateException("Duplicate blockstate definition for " + _snowmanxxx);
         }
      };
      Map<vk, Supplier<JsonElement>> _snowmanxxx = Maps.newHashMap();
      Set<blx> _snowmanxxxx = Sets.newHashSet();
      BiConsumer<vk, Supplier<JsonElement>> _snowmanxxxxx = (var1x, var2x) -> {
         Supplier<JsonElement> _snowmanxxxxxx = _snowman.put(var1x, var2x);
         if (_snowmanxxxxxx != null) {
            throw new IllegalStateException("Duplicate model definition for " + var1x);
         }
      };
      Consumer<blx> _snowmanxxxxxx = _snowmanxxxx::add;
      new ii(_snowmanxx, _snowmanxxxxx, _snowmanxxxxxx).a();
      new ij(_snowmanxxxxx).a();
      List<buo> _snowmanxxxxxxx = gm.Q.g().filter(var1x -> !_snowman.containsKey(var1x)).collect(Collectors.toList());
      if (!_snowmanxxxxxxx.isEmpty()) {
         throw new IllegalStateException("Missing blockstate definitions for: " + _snowmanxxxxxxx);
      } else {
         gm.Q.forEach(var2x -> {
            blx _snowmanxxxxxxxx = blx.e.get(var2x);
            if (_snowmanxxxxxxxx != null) {
               if (_snowman.contains(_snowmanxxxxxxxx)) {
                  return;
               }

               vk _snowmanx = iw.a(_snowmanxxxxxxxx);
               if (!_snowman.containsKey(_snowmanx)) {
                  _snowman.put(_snowmanx, new iv(iw.a(var2x)));
               }
            }
         });
         this.a(_snowman, _snowman, _snowmanx, ik::a);
         this.a(_snowman, _snowman, _snowmanxxx, ik::a);
      }
   }

   private <T> void a(hn var1, Path var2, Map<T, ? extends Supplier<JsonElement>> var3, BiFunction<Path, T, Path> var4) {
      _snowman.forEach((var3x, var4x) -> {
         Path _snowman = _snowman.apply(_snowman, (T)var3x);

         try {
            hm.a(c, _snowman, var4x.get(), _snowman);
         } catch (Exception var7) {
            b.error("Couldn't save {}", _snowman, var7);
         }
      });
   }

   private static Path a(Path var0, buo var1) {
      vk _snowman = gm.Q.b(_snowman);
      return _snowman.resolve("assets/" + _snowman.b() + "/blockstates/" + _snowman.a() + ".json");
   }

   private static Path a(Path var0, vk var1) {
      return _snowman.resolve("assets/" + _snowman.b() + "/models/" + _snowman.a() + ".json");
   }

   @Override
   public String a() {
      return "Block State Definitions";
   }
}
