import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class eaa extends ack<eaa.a> {
   private static final Logger a = LogManager.getLogger();
   private static final vk b = new vk("gpu_warnlist.json");
   private ImmutableMap<String, String> c = ImmutableMap.of();
   private boolean d;
   private boolean e;
   private boolean f;

   public eaa() {
   }

   public boolean a() {
      return !this.c.isEmpty();
   }

   public boolean b() {
      return this.a() && !this.e;
   }

   public void d() {
      this.d = true;
   }

   public void e() {
      this.e = true;
   }

   public void f() {
      this.e = true;
      this.f = true;
   }

   public boolean g() {
      return this.d && !this.e;
   }

   public boolean h() {
      return this.f;
   }

   public void i() {
      this.d = false;
      this.e = false;
      this.f = false;
   }

   @Nullable
   public String j() {
      return (String)this.c.get("renderer");
   }

   @Nullable
   public String k() {
      return (String)this.c.get("version");
   }

   @Nullable
   public String l() {
      return (String)this.c.get("vendor");
   }

   @Nullable
   public String m() {
      StringBuilder _snowman = new StringBuilder();
      this.c.forEach((var1x, var2) -> _snowman.append(var1x).append(": ").append(var2));
      return _snowman.length() == 0 ? null : _snowman.toString();
   }

   protected eaa.a a(ach var1, anw var2) {
      List<Pattern> _snowman = Lists.newArrayList();
      List<Pattern> _snowmanx = Lists.newArrayList();
      List<Pattern> _snowmanxx = Lists.newArrayList();
      _snowman.a();
      JsonObject _snowmanxxx = c(_snowman, _snowman);
      if (_snowmanxxx != null) {
         _snowman.a("compile_regex");
         a(_snowmanxxx.getAsJsonArray("renderer"), _snowman);
         a(_snowmanxxx.getAsJsonArray("version"), _snowmanx);
         a(_snowmanxxx.getAsJsonArray("vendor"), _snowmanxx);
         _snowman.c();
      }

      _snowman.b();
      return new eaa.a(_snowman, _snowmanx, _snowmanxx);
   }

   protected void a(eaa.a var1, ach var2, anw var3) {
      this.c = _snowman.a();
   }

   private static void a(JsonArray var0, List<Pattern> var1) {
      _snowman.forEach(var1x -> _snowman.add(Pattern.compile(var1x.getAsString(), 2)));
   }

   @Nullable
   private static JsonObject c(ach var0, anw var1) {
      _snowman.a("parse_json");
      JsonObject _snowman = null;

      try (
         acg _snowmanx = _snowman.a(b);
         BufferedReader _snowmanxx = new BufferedReader(new InputStreamReader(_snowmanx.b(), StandardCharsets.UTF_8));
      ) {
         _snowman = new JsonParser().parse(_snowmanxx).getAsJsonObject();
      } catch (JsonSyntaxException | IOException var35) {
         a.warn("Failed to load GPU warnlist");
      }

      _snowman.c();
      return _snowman;
   }

   public static final class a {
      private final List<Pattern> a;
      private final List<Pattern> b;
      private final List<Pattern> c;

      private a(List<Pattern> var1, List<Pattern> var2, List<Pattern> var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      private static String a(List<Pattern> var0, String var1) {
         List<String> _snowman = Lists.newArrayList();

         for (Pattern _snowmanx : _snowman) {
            Matcher _snowmanxx = _snowmanx.matcher(_snowman);

            while (_snowmanxx.find()) {
               _snowman.add(_snowmanxx.group());
            }
         }

         return String.join(", ", _snowman);
      }

      private ImmutableMap<String, String> a() {
         Builder<String, String> _snowman = new Builder();
         String _snowmanx = a(this.a, den.c());
         if (!_snowmanx.isEmpty()) {
            _snowman.put("renderer", _snowmanx);
         }

         String _snowmanxx = a(this.b, den.d());
         if (!_snowmanxx.isEmpty()) {
            _snowman.put("version", _snowmanxx);
         }

         String _snowmanxxx = a(this.c, den.a());
         if (!_snowmanxxx.isEmpty()) {
            _snowman.put("vendor", _snowmanxxx);
         }

         return _snowman.build();
      }
   }
}
