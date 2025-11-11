import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class acy<K, V extends acx<K>> {
   protected static final Logger a = LogManager.getLogger();
   private static final Gson b = new GsonBuilder().setPrettyPrinting().create();
   private final File c;
   private final Map<String, V> d = Maps.newHashMap();

   public acy(File var1) {
      this.c = _snowman;
   }

   public File b() {
      return this.c;
   }

   public void a(V var1) {
      this.d.put(this.a(_snowman.g()), _snowman);

      try {
         this.e();
      } catch (IOException var3) {
         a.warn("Could not save the list after adding a user.", var3);
      }
   }

   @Nullable
   public V b(K var1) {
      this.g();
      return this.d.get(this.a(_snowman));
   }

   public void c(K var1) {
      this.d.remove(this.a(_snowman));

      try {
         this.e();
      } catch (IOException var3) {
         a.warn("Could not save the list after removing a user.", var3);
      }
   }

   public void b(acx<K> var1) {
      this.c(_snowman.g());
   }

   public String[] a() {
      return this.d.keySet().toArray(new String[this.d.size()]);
   }

   public boolean c() {
      return this.d.size() < 1;
   }

   protected String a(K var1) {
      return _snowman.toString();
   }

   protected boolean d(K var1) {
      return this.d.containsKey(this.a(_snowman));
   }

   private void g() {
      List<K> _snowman = Lists.newArrayList();

      for (V _snowmanx : this.d.values()) {
         if (_snowmanx.f()) {
            _snowman.add(_snowmanx.g());
         }
      }

      for (K _snowmanxx : _snowman) {
         this.d.remove(this.a(_snowmanxx));
      }
   }

   protected abstract acx<K> a(JsonObject var1);

   public Collection<V> d() {
      return this.d.values();
   }

   public void e() throws IOException {
      JsonArray _snowman = new JsonArray();
      this.d.values().stream().map(var0 -> x.a(new JsonObject(), var0::a)).forEach(_snowman::add);

      try (BufferedWriter _snowmanx = Files.newWriter(this.c, StandardCharsets.UTF_8)) {
         b.toJson(_snowman, _snowmanx);
      }
   }

   public void f() throws IOException {
      if (this.c.exists()) {
         try (BufferedReader _snowman = Files.newReader(this.c, StandardCharsets.UTF_8)) {
            JsonArray _snowmanx = (JsonArray)b.fromJson(_snowman, JsonArray.class);
            this.d.clear();

            for (JsonElement _snowmanxx : _snowmanx) {
               JsonObject _snowmanxxx = afd.m(_snowmanxx, "entry");
               acx<K> _snowmanxxxx = this.a(_snowmanxxx);
               if (_snowmanxxxx.g() != null) {
                  this.d.put(this.a(_snowmanxxxx.g()), (V)_snowmanxxxx);
               }
            }
         }
      }
   }
}
