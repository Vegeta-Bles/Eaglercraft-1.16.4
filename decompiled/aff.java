import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import javax.annotation.Nullable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aff {
   private static final Logger b = LogManager.getLogger();
   public static final ListeningExecutorService a = MoreExecutors.listeningDecorator(
      Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(true).setUncaughtExceptionHandler(new o(b)).setNameFormat("Downloader %d").build())
   );

   public static CompletableFuture<?> a(File var0, String var1, Map<String, String> var2, int var3, @Nullable afn var4, Proxy var5) {
      return CompletableFuture.supplyAsync(() -> {
         HttpURLConnection _snowman = null;
         InputStream _snowmanx = null;
         OutputStream _snowmanxx = null;
         if (_snowman != null) {
            _snowman.b(new of("resourcepack.downloading"));
            _snowman.c(new of("resourcepack.requesting"));
         }

         try {
            byte[] _snowmanxxx = new byte[4096];
            URL _snowmanxxxx = new URL(_snowman);
            _snowman = (HttpURLConnection)_snowmanxxxx.openConnection(_snowman);
            _snowman.setInstanceFollowRedirects(true);
            float _snowmanxxxxx = 0.0F;
            float _snowmanxxxxxx = (float)_snowman.entrySet().size();

            for (Entry<String, String> _snowmanxxxxxxx : _snowman.entrySet()) {
               _snowman.setRequestProperty(_snowmanxxxxxxx.getKey(), _snowmanxxxxxxx.getValue());
               if (_snowman != null) {
                  _snowman.a((int)(++_snowmanxxxxx / _snowmanxxxxxx * 100.0F));
               }
            }

            _snowmanx = _snowman.getInputStream();
            _snowmanxxxxxx = (float)_snowman.getContentLength();
            int _snowmanxxxxxxxx = _snowman.getContentLength();
            if (_snowman != null) {
               _snowman.c(new of("resourcepack.progress", String.format(Locale.ROOT, "%.2f", _snowmanxxxxxx / 1000.0F / 1000.0F)));
            }

            if (_snowman.exists()) {
               long _snowmanxxxxxxxxx = _snowman.length();
               if (_snowmanxxxxxxxxx == (long)_snowmanxxxxxxxx) {
                  if (_snowman != null) {
                     _snowman.a();
                  }

                  return null;
               }

               b.warn("Deleting {} as it does not match what we currently have ({} vs our {}).", _snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
               FileUtils.deleteQuietly(_snowman);
            } else if (_snowman.getParentFile() != null) {
               _snowman.getParentFile().mkdirs();
            }

            _snowmanxx = new DataOutputStream(new FileOutputStream(_snowman));
            if (_snowman > 0 && _snowmanxxxxxx > (float)_snowman) {
               if (_snowman != null) {
                  _snowman.a();
               }

               throw new IOException("Filesize is bigger than maximum allowed (file is " + _snowmanxxxxx + ", limit is " + _snowman + ")");
            } else {
               int _snowmanxxxxxxxxx;
               while ((_snowmanxxxxxxxxx = _snowmanx.read(_snowmanxxx)) >= 0) {
                  _snowmanxxxxx += (float)_snowmanxxxxxxxxx;
                  if (_snowman != null) {
                     _snowman.a((int)(_snowmanxxxxx / _snowmanxxxxxx * 100.0F));
                  }

                  if (_snowman > 0 && _snowmanxxxxx > (float)_snowman) {
                     if (_snowman != null) {
                        _snowman.a();
                     }

                     throw new IOException("Filesize was bigger than maximum allowed (got >= " + _snowmanxxxxx + ", limit was " + _snowman + ")");
                  }

                  if (Thread.interrupted()) {
                     b.error("INTERRUPTED");
                     if (_snowman != null) {
                        _snowman.a();
                     }

                     return null;
                  }

                  _snowmanxx.write(_snowmanxxx, 0, _snowmanxxxxxxxxx);
               }

               if (_snowman != null) {
                  _snowman.a();
               }

               return null;
            }
         } catch (Throwable var22) {
            var22.printStackTrace();
            if (_snowman != null) {
               InputStream _snowmanxxx = _snowman.getErrorStream();

               try {
                  b.error(IOUtils.toString(_snowmanxxx));
               } catch (IOException var21) {
                  var21.printStackTrace();
               }
            }

            if (_snowman != null) {
               _snowman.a();
            }

            return null;
         } finally {
            IOUtils.closeQuietly(_snowmanx);
            IOUtils.closeQuietly(_snowmanxx);
         }
      }, a);
   }

   public static int a() {
      try (ServerSocket _snowman = new ServerSocket(0)) {
         return _snowman.getLocalPort();
      } catch (IOException var14) {
         return 25564;
      }
   }
}
