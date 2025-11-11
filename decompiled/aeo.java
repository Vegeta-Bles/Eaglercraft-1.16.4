import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aeo<T> {
   private static final Logger a = LogManager.getLogger();
   private static final Gson b = new Gson();
   private static final int c = ".json".length();
   private final Function<vk, Optional<T>> d;
   private final String e;
   private final String f;

   public aeo(Function<vk, Optional<T>> var1, String var2, String var3) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public CompletableFuture<Map<vk, ael.a>> a(ach var1, Executor var2) {
      return CompletableFuture.supplyAsync(() -> {
         Map<vk, ael.a> _snowman = Maps.newHashMap();

         for (vk _snowmanx : _snowman.a(this.e, var0 -> var0.endsWith(".json"))) {
            String _snowmanxx = _snowmanx.a();
            vk _snowmanxxx = new vk(_snowmanx.b(), _snowmanxx.substring(this.e.length() + 1, _snowmanxx.length() - c));

            try {
               for (acg _snowmanxxxx : _snowman.c(_snowmanx)) {
                  try (
                     InputStream _snowmanxxxxx = _snowmanxxxx.b();
                     Reader _snowmanxxxxxx = new BufferedReader(new InputStreamReader(_snowmanxxxxx, StandardCharsets.UTF_8));
                  ) {
                     JsonObject _snowmanxxxxxxx = afd.a(b, _snowmanxxxxxx, JsonObject.class);
                     if (_snowmanxxxxxxx == null) {
                        a.error("Couldn't load {} tag list {} from {} in data pack {} as it is empty or null", this.f, _snowmanxxx, _snowmanx, _snowmanxxxx.d());
                     } else {
                        _snowman.computeIfAbsent(_snowmanxxx, var0 -> ael.a.a()).a(_snowmanxxxxxxx, _snowmanxxxx.d());
                     }
                  } catch (RuntimeException | IOException var57) {
                     a.error("Couldn't read {} tag list {} from {} in data pack {}", this.f, _snowmanxxx, _snowmanx, _snowmanxxxx.d(), var57);
                  } finally {
                     IOUtils.closeQuietly(_snowmanxxxx);
                  }
               }
            } catch (IOException var59) {
               a.error("Couldn't read {} tag list {} from {}", this.f, _snowmanxxx, _snowmanx, var59);
            }
         }

         return _snowman;
      }, _snowman);
   }

   public aem<T> a(Map<vk, ael.a> var1) {
      Map<vk, ael<T>> _snowman = Maps.newHashMap();
      Function<vk, ael<T>> _snowmanx = _snowman::get;
      Function<vk, T> _snowmanxx = var1x -> this.d.apply(var1x).orElse(null);

      while (!_snowman.isEmpty()) {
         boolean _snowmanxxx = false;
         Iterator<Entry<vk, ael.a>> _snowmanxxxx = _snowman.entrySet().iterator();

         while (_snowmanxxxx.hasNext()) {
            Entry<vk, ael.a> _snowmanxxxxx = _snowmanxxxx.next();
            Optional<ael<T>> _snowmanxxxxxx = _snowmanxxxxx.getValue().a(_snowmanx, _snowmanxx);
            if (_snowmanxxxxxx.isPresent()) {
               _snowman.put(_snowmanxxxxx.getKey(), _snowmanxxxxxx.get());
               _snowmanxxxx.remove();
               _snowmanxxx = true;
            }
         }

         if (!_snowmanxxx) {
            break;
         }
      }

      _snowman.forEach(
         (var3x, var4x) -> a.error(
               "Couldn't load {} tag {} as it is missing following references: {}",
               this.f,
               var3x,
               var4x.b(_snowman, _snowman).map(Objects::toString).collect(Collectors.joining(","))
            )
      );
      return aem.a(_snowman);
   }
}
