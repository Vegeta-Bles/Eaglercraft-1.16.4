import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class adw extends aeb {
   private static final Logger b = LogManager.getLogger();
   private final MinecraftServer c;
   private final File d;
   private final Set<adx<?>> e = Sets.newHashSet();
   private int f = -300;

   public adw(MinecraftServer var1, File var2) {
      this.c = _snowman;
      this.d = _snowman;
      if (_snowman.isFile()) {
         try {
            this.a(_snowman.az(), FileUtils.readFileToString(_snowman));
         } catch (IOException var4) {
            b.error("Couldn't read statistics file {}", _snowman, var4);
         } catch (JsonParseException var5) {
            b.error("Couldn't parse statistics file {}", _snowman, var5);
         }
      }
   }

   public void a() {
      try {
         FileUtils.writeStringToFile(this.d, this.b());
      } catch (IOException var2) {
         b.error("Couldn't save stats", var2);
      }
   }

   @Override
   public void a(bfw var1, adx<?> var2, int var3) {
      super.a(_snowman, _snowman, _snowman);
      this.e.add(_snowman);
   }

   private Set<adx<?>> d() {
      Set<adx<?>> _snowman = Sets.newHashSet(this.e);
      this.e.clear();
      return _snowman;
   }

   public void a(DataFixer var1, String var2) {
      try {
         JsonReader _snowman = new JsonReader(new StringReader(_snowman));
         Throwable var4 = null;

         try {
            _snowman.setLenient(false);
            JsonElement _snowmanx = Streams.parse(_snowman);
            if (!_snowmanx.isJsonNull()) {
               md _snowmanxx = a(_snowmanx.getAsJsonObject());
               if (!_snowmanxx.c("DataVersion", 99)) {
                  _snowmanxx.b("DataVersion", 1343);
               }

               _snowmanxx = mp.a(_snowman, aga.g, _snowmanxx, _snowmanxx.h("DataVersion"));
               if (_snowmanxx.c("stats", 10)) {
                  md _snowmanxxx = _snowmanxx.p("stats");

                  for (String _snowmanxxxx : _snowmanxxx.d()) {
                     if (_snowmanxxx.c(_snowmanxxxx, 10)) {
                        x.a(
                           gm.ag.b(new vk(_snowmanxxxx)),
                           var3x -> {
                              md _snowmanxxxxx = _snowman.p(_snowman);

                              for (String _snowmanx : _snowmanxxxxx.d()) {
                                 if (_snowmanxxxxx.c(_snowmanx, 99)) {
                                    x.a(
                                       this.a(var3x, _snowmanx),
                                       var3xx -> this.a.put(var3xx, _snowman.h(_snowman)),
                                       () -> b.warn("Invalid statistic in {}: Don't know what {} is", this.d, _snowman)
                                    );
                                 } else {
                                    b.warn("Invalid statistic value in {}: Don't know what {} is for key {}", this.d, _snowmanxxxxx.c(_snowmanx), _snowmanx);
                                 }
                              }
                           },
                           () -> b.warn("Invalid statistic type in {}: Don't know what {} is", this.d, _snowman)
                        );
                     }
                  }
               }

               return;
            }

            b.error("Unable to parse Stat data from {}", this.d);
         } catch (Throwable var19) {
            var4 = var19;
            throw var19;
         } finally {
            if (_snowman != null) {
               if (var4 != null) {
                  try {
                     _snowman.close();
                  } catch (Throwable var18) {
                     var4.addSuppressed(var18);
                  }
               } else {
                  _snowman.close();
               }
            }
         }
      } catch (IOException | JsonParseException var21) {
         b.error("Unable to parse Stat data from {}", this.d, var21);
      }
   }

   private <T> Optional<adx<T>> a(adz<T> var1, String var2) {
      return Optional.ofNullable(vk.a(_snowman)).flatMap(_snowman.a()::b).map(_snowman::b);
   }

   private static md a(JsonObject var0) {
      md _snowman = new md();

      for (Entry<String, JsonElement> _snowmanx : _snowman.entrySet()) {
         JsonElement _snowmanxx = _snowmanx.getValue();
         if (_snowmanxx.isJsonObject()) {
            _snowman.a(_snowmanx.getKey(), a(_snowmanxx.getAsJsonObject()));
         } else if (_snowmanxx.isJsonPrimitive()) {
            JsonPrimitive _snowmanxxx = _snowmanxx.getAsJsonPrimitive();
            if (_snowmanxxx.isNumber()) {
               _snowman.b(_snowmanx.getKey(), _snowmanxxx.getAsInt());
            }
         }
      }

      return _snowman;
   }

   protected String b() {
      Map<adz<?>, JsonObject> _snowman = Maps.newHashMap();
      ObjectIterator var2 = this.a.object2IntEntrySet().iterator();

      while (var2.hasNext()) {
         it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<adx<?>> _snowmanx = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<adx<?>>)var2.next();
         adx<?> _snowmanxx = (adx<?>)_snowmanx.getKey();
         _snowman.computeIfAbsent(_snowmanxx.a(), var0 -> new JsonObject()).addProperty(b(_snowmanxx).toString(), _snowmanx.getIntValue());
      }

      JsonObject _snowmanx = new JsonObject();

      for (Entry<adz<?>, JsonObject> _snowmanxx : _snowman.entrySet()) {
         _snowmanx.add(gm.ag.b(_snowmanxx.getKey()).toString(), (JsonElement)_snowmanxx.getValue());
      }

      JsonObject _snowmanxx = new JsonObject();
      _snowmanxx.add("stats", _snowmanx);
      _snowmanxx.addProperty("DataVersion", w.a().getWorldVersion());
      return _snowmanxx.toString();
   }

   private static <T> vk b(adx<T> var0) {
      return _snowman.a().a().b(_snowman.b());
   }

   public void c() {
      this.e.addAll(this.a.keySet());
   }

   public void a(aah var1) {
      int _snowman = this.c.ai();
      Object2IntMap<adx<?>> _snowmanx = new Object2IntOpenHashMap();
      if (_snowman - this.f > 300) {
         this.f = _snowman;

         for (adx<?> _snowmanxx : this.d()) {
            _snowmanx.put(_snowmanxx, this.a(_snowmanxx));
         }
      }

      _snowman.b.a(new ot(_snowmanx));
   }
}
