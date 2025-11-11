import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class abd implements AutoCloseable {
   private static final Logger a = LogManager.getLogger();
   private static final AtomicInteger b = new AtomicInteger(1);
   private static final ThreadFactory c = var0 -> {
      Thread _snowman = new Thread(var0);
      _snowman.setName("Chat-Filter-Worker-" + b.getAndIncrement());
      return _snowman;
   };
   private final URL d;
   private final URL e;
   private final URL f;
   private final String g;
   private final int h;
   private final String i;
   private final abd.a j;
   private final ExecutorService k;

   private void a(GameProfile var1, URL var2, Executor var3) {
      JsonObject _snowman = new JsonObject();
      _snowman.addProperty("server", this.i);
      _snowman.addProperty("room", "Chat");
      _snowman.addProperty("user_id", _snowman.getId().toString());
      _snowman.addProperty("user_display_name", _snowman.getName());
      _snowman.execute(() -> {
         try {
            this.b(_snowman, _snowman);
         } catch (Exception var5) {
            a.warn("Failed to send join/leave packet to {} for player {}", _snowman, _snowman, var5);
         }
      });
   }

   private CompletableFuture<Optional<String>> a(GameProfile var1, String var2, abd.a var3, Executor var4) {
      if (_snowman.isEmpty()) {
         return CompletableFuture.completedFuture(Optional.of(""));
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("rule", this.h);
         _snowman.addProperty("server", this.i);
         _snowman.addProperty("room", "Chat");
         _snowman.addProperty("player", _snowman.getId().toString());
         _snowman.addProperty("player_display_name", _snowman.getName());
         _snowman.addProperty("text", _snowman);
         return CompletableFuture.supplyAsync(() -> {
            try {
               JsonObject _snowmanx = this.a(_snowman, this.d);
               boolean _snowmanx = afd.a(_snowmanx, "response", false);
               if (_snowmanx) {
                  return Optional.of(_snowman);
               } else {
                  String _snowmanxx = afd.a(_snowmanx, "hashed", null);
                  if (_snowmanxx == null) {
                     return Optional.empty();
                  } else {
                     int _snowmanxxx = afd.u(_snowmanx, "hashes").size();
                     return _snowman.shouldIgnore(_snowmanxx, _snowmanxxx) ? Optional.empty() : Optional.of(_snowmanxx);
                  }
               }
            } catch (Exception var8) {
               a.warn("Failed to validate message '{}'", _snowman, var8);
               return Optional.empty();
            }
         }, _snowman);
      }
   }

   @Override
   public void close() {
      this.k.shutdownNow();
   }

   private void a(InputStream var1) throws IOException {
      byte[] _snowman = new byte[1024];

      while (_snowman.read(_snowman) != -1) {
      }
   }

   private JsonObject a(JsonObject var1, URL var2) throws IOException {
      HttpURLConnection _snowman = this.c(_snowman, _snowman);

      JsonObject var6;
      try (InputStream _snowmanx = _snowman.getInputStream()) {
         if (_snowman.getResponseCode() != 204) {
            try {
               return Streams.parse(new JsonReader(new InputStreamReader(_snowmanx))).getAsJsonObject();
            } finally {
               this.a(_snowmanx);
            }
         }

         var6 = new JsonObject();
      }

      return var6;
   }

   private void b(JsonObject var1, URL var2) throws IOException {
      HttpURLConnection _snowman = this.c(_snowman, _snowman);

      try (InputStream _snowmanx = _snowman.getInputStream()) {
         this.a(_snowmanx);
      }
   }

   private HttpURLConnection c(JsonObject var1, URL var2) throws IOException {
      HttpURLConnection _snowman = (HttpURLConnection)_snowman.openConnection();
      _snowman.setConnectTimeout(15000);
      _snowman.setReadTimeout(2000);
      _snowman.setUseCaches(false);
      _snowman.setDoOutput(true);
      _snowman.setDoInput(true);
      _snowman.setRequestMethod("POST");
      _snowman.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      _snowman.setRequestProperty("Accept", "application/json");
      _snowman.setRequestProperty("Authorization", "Basic " + this.g);
      _snowman.setRequestProperty("User-Agent", "Minecraft server" + w.a().getName());

      try (OutputStreamWriter _snowmanx = new OutputStreamWriter(_snowman.getOutputStream(), StandardCharsets.UTF_8)) {
         JsonWriter _snowmanxx = new JsonWriter(_snowmanx);
         Throwable var7 = null;

         try {
            Streams.write(_snowman, _snowmanxx);
         } catch (Throwable var30) {
            var7 = var30;
            throw var30;
         } finally {
            if (_snowmanxx != null) {
               if (var7 != null) {
                  try {
                     _snowmanxx.close();
                  } catch (Throwable var29) {
                     var7.addSuppressed(var29);
                  }
               } else {
                  _snowmanxx.close();
               }
            }
         }
      }

      int _snowmanx = _snowman.getResponseCode();
      if (_snowmanx >= 200 && _snowmanx < 300) {
         return _snowman;
      } else {
         throw new abd.c(_snowmanx + " " + _snowman.getResponseMessage());
      }
   }

   public abc a(GameProfile var1) {
      return new abd.b(_snowman);
   }

   @FunctionalInterface
   public interface a {
      abd.a a = (var0, var1) -> false;
      abd.a b = (var0, var1) -> var0.length() == var1;

      boolean shouldIgnore(String var1, int var2);
   }

   class b implements abc {
      private final GameProfile b;
      private final Executor c;

      private b(GameProfile var2) {
         this.b = _snowman;
         aoe<Runnable> _snowman = aoe.a(abd.this.k, "chat stream for " + _snowman.getName());
         this.c = _snowman::a;
      }

      @Override
      public void a() {
         abd.this.a(this.b, abd.this.e, this.c);
      }

      @Override
      public void b() {
         abd.this.a(this.b, abd.this.f, this.c);
      }

      @Override
      public CompletableFuture<Optional<List<String>>> a(List<String> var1) {
         List<CompletableFuture<Optional<String>>> _snowman = _snowman.stream()
            .map(var1x -> abd.this.a(this.b, var1x, abd.this.j, this.c))
            .collect(ImmutableList.toImmutableList());
         return x.b(_snowman)
            .thenApply(var0 -> Optional.of(var0.stream().map(var0x -> var0x.orElse("")).collect(ImmutableList.toImmutableList())))
            .exceptionally(var0 -> Optional.empty());
      }

      @Override
      public CompletableFuture<Optional<String>> a(String var1) {
         return abd.this.a(this.b, _snowman, abd.this.j, this.c);
      }
   }

   public static class c extends RuntimeException {
      private c(String var1) {
         super(_snowman);
      }
   }
}
