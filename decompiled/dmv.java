import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dmv implements AutoCloseable {
   private static final Logger b = LogManager.getLogger();
   public static final vk a = new vk("minecraft", "missing");
   private final dmw c;
   private final Map<vk, dmw> d = Maps.newHashMap();
   private final ekd e;
   private Map<vk, vk> f = ImmutableMap.of();
   private final acc g = new ack<Map<vk, List<deb>>>() {
      protected Map<vk, List<deb>> a(ach var1, anw var2) {
         _snowman.a();
         Gson _snowman = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
         Map<vk, List<deb>> _snowmanx = Maps.newHashMap();

         for (vk _snowmanxx : _snowman.a("font", var0 -> var0.endsWith(".json"))) {
            String _snowmanxxx = _snowmanxx.a();
            vk _snowmanxxxx = new vk(_snowmanxx.b(), _snowmanxxx.substring("font/".length(), _snowmanxxx.length() - ".json".length()));
            List<deb> _snowmanxxxxx = _snowmanx.computeIfAbsent(_snowmanxxxx, var0 -> Lists.newArrayList(new deb[]{new dmu()}));
            _snowman.a(_snowmanxxxx::toString);

            try {
               for (acg _snowmanxxxxxx : _snowman.c(_snowmanxx)) {
                  _snowman.a(_snowmanxxxxxx::d);

                  try (
                     InputStream _snowmanxxxxxxx = _snowmanxxxxxx.b();
                     Reader _snowmanxxxxxxxx = new BufferedReader(new InputStreamReader(_snowmanxxxxxxx, StandardCharsets.UTF_8));
                  ) {
                     _snowman.a("reading");
                     JsonArray _snowmanxxxxxxxxx = afd.u(afd.a(_snowman, _snowmanxxxxxxxx, JsonObject.class), "providers");
                     _snowman.b("parsing");

                     for (int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.size() - 1; _snowmanxxxxxxxxxx >= 0; _snowmanxxxxxxxxxx--) {
                        JsonObject _snowmanxxxxxxxxxxx = afd.m(_snowmanxxxxxxxxx.get(_snowmanxxxxxxxxxx), "providers[" + _snowmanxxxxxxxxxx + "]");

                        try {
                           String _snowmanxxxxxxxxxxxx = afd.h(_snowmanxxxxxxxxxxx, "type");
                           dnh _snowmanxxxxxxxxxxxxx = dnh.a(_snowmanxxxxxxxxxxxx);
                           _snowman.a(_snowmanxxxxxxxxxxxx);
                           deb _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxx).a(_snowman);
                           if (_snowmanxxxxxxxxxxxxxx != null) {
                              _snowmanxxxxx.add(_snowmanxxxxxxxxxxxxxx);
                           }

                           _snowman.c();
                        } catch (RuntimeException var49) {
                           dmv.b.warn("Unable to read definition '{}' in fonts.json in resourcepack: '{}': {}", _snowmanxxxx, _snowmanxxxxxx.d(), var49.getMessage());
                        }
                     }

                     _snowman.c();
                  } catch (RuntimeException var54) {
                     dmv.b.warn("Unable to load font '{}' in fonts.json in resourcepack: '{}': {}", _snowmanxxxx, _snowmanxxxxxx.d(), var54.getMessage());
                  }

                  _snowman.c();
               }
            } catch (IOException var55) {
               dmv.b.warn("Unable to load font '{}' in fonts.json: {}", _snowmanxxxx, var55.getMessage());
            }

            _snowman.a("caching");
            IntSet _snowmanxxxxxx = new IntOpenHashSet();

            for (deb _snowmanxxxxxxx : _snowmanxxxxx) {
               _snowmanxxxxxx.addAll(_snowmanxxxxxxx.a());
            }

            _snowmanxxxxxx.forEach(var1x -> {
               if (var1x != 32) {
                  for (deb _snowmanxxxxxxx : Lists.reverse(_snowman)) {
                     if (_snowmanxxxxxxx.a(var1x) != null) {
                        break;
                     }
                  }
               }
            });
            _snowman.c();
            _snowman.c();
         }

         _snowman.b();
         return _snowmanx;
      }

      protected void a(Map<vk, List<deb>> var1, ach var2, anw var3) {
         _snowman.a();
         _snowman.a("closing");
         dmv.this.d.values().forEach(dmw::close);
         dmv.this.d.clear();
         _snowman.b("reloading");
         _snowman.forEach((var1x, var2x) -> {
            dmw _snowman = new dmw(dmv.this.e, var1x);
            _snowman.a(Lists.reverse(var2x));
            dmv.this.d.put(var1x, _snowman);
         });
         _snowman.c();
         _snowman.b();
      }

      @Override
      public String c() {
         return "FontManager";
      }
   };

   public dmv(ekd var1) {
      this.e = _snowman;
      this.c = x.a(new dmw(_snowman, a), var0 -> var0.a(Lists.newArrayList(new deb[]{new dmu()})));
   }

   public void a(Map<vk, vk> var1) {
      this.f = _snowman;
   }

   public dku a() {
      return new dku(var1 -> this.d.getOrDefault(this.f.getOrDefault(var1, var1), this.c));
   }

   public acc b() {
      return this.g;
   }

   @Override
   public void close() {
      this.d.values().forEach(dmw::close);
      this.c.close();
   }
}
