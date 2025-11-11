import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dfz {
   private static final Logger a = LogManager.getLogger();
   private final File b;
   private final long c;
   private final int d;
   private final dhb e;
   private final String f;
   private final String g;
   private final String h;
   private final dgf i;
   private final AtomicBoolean j = new AtomicBoolean(false);
   private CompletableFuture<dio> k;
   private final RequestConfig l = RequestConfig.custom()
      .setSocketTimeout((int)TimeUnit.MINUTES.toMillis(10L))
      .setConnectTimeout((int)TimeUnit.SECONDS.toMillis(15L))
      .build();

   public dfz(File var1, long var2, int var4, dhb var5, dkm var6, String var7, dgf var8) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman.a();
      this.g = _snowman.c();
      this.h = _snowman;
      this.i = _snowman;
   }

   public void a(Consumer<dio> var1) {
      if (this.k == null) {
         this.k = CompletableFuture.supplyAsync(() -> this.a(0));
         this.k.thenAccept(_snowman);
      }
   }

   public void a() {
      this.j.set(true);
      if (this.k != null) {
         this.k.cancel(false);
         this.k = null;
      }
   }

   private dio a(int var1) {
      dio.a _snowman = new dio.a();
      if (this.j.get()) {
         return _snowman.a();
      } else {
         this.i.b = this.b.length();
         HttpPost _snowmanx = new HttpPost(this.e.b().resolve("/upload/" + this.c + "/" + this.d));
         CloseableHttpClient _snowmanxx = HttpClientBuilder.create().setDefaultRequestConfig(this.l).build();

         dio var8;
         try {
            this.a(_snowmanx);
            HttpResponse _snowmanxxx = _snowmanxx.execute(_snowmanx);
            long _snowmanxxxx = this.a(_snowmanxxx);
            if (!this.a(_snowmanxxxx, _snowman)) {
               this.a(_snowmanxxx, _snowman);
               return _snowman.a();
            }

            var8 = this.b(_snowmanxxxx, _snowman);
         } catch (Exception var12) {
            if (!this.j.get()) {
               a.error("Caught exception while uploading: ", var12);
            }

            return _snowman.a();
         } finally {
            this.a(_snowmanx, _snowmanxx);
         }

         return var8;
      }
   }

   private void a(HttpPost var1, CloseableHttpClient var2) {
      _snowman.releaseConnection();
      if (_snowman != null) {
         try {
            _snowman.close();
         } catch (IOException var4) {
            a.error("Failed to close Realms upload client");
         }
      }
   }

   private void a(HttpPost var1) throws FileNotFoundException {
      _snowman.setHeader("Cookie", "sid=" + this.f + ";token=" + this.e.a() + ";user=" + this.g + ";version=" + this.h);
      dfz.a _snowman = new dfz.a(new FileInputStream(this.b), this.b.length(), this.i);
      _snowman.setContentType("application/octet-stream");
      _snowman.setEntity(_snowman);
   }

   private void a(HttpResponse var1, dio.a var2) throws IOException {
      int _snowman = _snowman.getStatusLine().getStatusCode();
      if (_snowman == 401) {
         a.debug("Realms server returned 401: " + _snowman.getFirstHeader("WWW-Authenticate"));
      }

      _snowman.a(_snowman);
      if (_snowman.getEntity() != null) {
         String _snowmanx = EntityUtils.toString(_snowman.getEntity(), "UTF-8");
         if (_snowmanx != null) {
            try {
               JsonParser _snowmanxx = new JsonParser();
               JsonElement _snowmanxxx = _snowmanxx.parse(_snowmanx).getAsJsonObject().get("errorMsg");
               Optional<String> _snowmanxxxx = Optional.ofNullable(_snowmanxxx).map(JsonElement::getAsString);
               _snowman.a(_snowmanxxxx.orElse(null));
            } catch (Exception var8) {
            }
         }
      }
   }

   private boolean a(long var1, int var3) {
      return _snowman > 0L && _snowman + 1 < 5;
   }

   private dio b(long var1, int var3) throws InterruptedException {
      Thread.sleep(Duration.ofSeconds(_snowman).toMillis());
      return this.a(_snowman + 1);
   }

   private long a(HttpResponse var1) {
      return Optional.ofNullable(_snowman.getFirstHeader("Retry-After")).<String>map(Header::getValue).map(Long::valueOf).orElse(0L);
   }

   public boolean b() {
      return this.k.isDone() || this.k.isCancelled();
   }

   static class a extends InputStreamEntity {
      private final long a;
      private final InputStream b;
      private final dgf c;

      public a(InputStream var1, long var2, dgf var4) {
         super(_snowman);
         this.b = _snowman;
         this.a = _snowman;
         this.c = _snowman;
      }

      public void writeTo(OutputStream var1) throws IOException {
         Args.notNull(_snowman, "Output stream");
         InputStream _snowman = this.b;

         try {
            byte[] _snowmanx = new byte[4096];
            int _snowmanxx;
            if (this.a < 0L) {
               while ((_snowmanxx = _snowman.read(_snowmanx)) != -1) {
                  _snowman.write(_snowmanx, 0, _snowmanxx);
                  this.c.a += (long)_snowmanxx;
               }
            } else {
               long _snowmanxxx = this.a;

               while (_snowmanxxx > 0L) {
                  _snowmanxx = _snowman.read(_snowmanx, 0, (int)Math.min(4096L, _snowmanxxx));
                  if (_snowmanxx == -1) {
                     break;
                  }

                  _snowman.write(_snowmanx, 0, _snowmanxx);
                  this.c.a += (long)_snowmanxx;
                  _snowmanxxx -= (long)_snowmanxx;
                  _snowman.flush();
               }
            }
         } finally {
            _snowman.close();
         }
      }
   }
}
