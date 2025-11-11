import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ly {
   private static final Logger a = LogManager.getLogger();
   private static final Gson b = new Gson();
   private static final Pattern c = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
   private static volatile ly d = c();

   public ly() {
   }

   private static ly c() {
      Builder<String, String> _snowman = ImmutableMap.builder();
      BiConsumer<String, String> _snowmanx = _snowman::put;

      try (InputStream _snowmanxx = ly.class.getResourceAsStream("/assets/minecraft/lang/en_us.json")) {
         a(_snowmanxx, _snowmanx);
      } catch (JsonParseException | IOException var15) {
         a.error("Couldn't read strings from /assets/minecraft/lang/en_us.json", var15);
      }

      final Map<String, String> _snowmanxx = _snowman.build();
      return new ly() {
         @Override
         public String a(String var1) {
            return _snowman.getOrDefault(_snowman, _snowman);
         }

         @Override
         public boolean b(String var1) {
            return _snowman.containsKey(_snowman);
         }

         @Override
         public boolean b() {
            return false;
         }

         @Override
         public afa a(nu var1) {
            return var1x -> _snowman.a((var1xx, var2) -> afr.c(var2, var1xx, var1x) ? Optional.empty() : nu.b, ob.a).isPresent();
         }
      };
   }

   public static void a(InputStream var0, BiConsumer<String, String> var1) {
      JsonObject _snowman = (JsonObject)b.fromJson(new InputStreamReader(_snowman, StandardCharsets.UTF_8), JsonObject.class);

      for (Entry<String, JsonElement> _snowmanx : _snowman.entrySet()) {
         String _snowmanxx = c.matcher(afd.a(_snowmanx.getValue(), _snowmanx.getKey())).replaceAll("%$1s");
         _snowman.accept(_snowmanx.getKey(), _snowmanxx);
      }
   }

   public static ly a() {
      return d;
   }

   public static void a(ly var0) {
      d = _snowman;
   }

   public abstract String a(String var1);

   public abstract boolean b(String var1);

   public abstract boolean b();

   public abstract afa a(nu var1);

   public List<afa> a(List<nu> var1) {
      return _snowman.stream().map(a()::a).collect(ImmutableList.toImmutableList());
   }
}
