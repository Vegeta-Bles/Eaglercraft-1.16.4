package net.minecraft.client.util;

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
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkUtils {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final ListeningExecutorService downloadExecutor = MoreExecutors.listeningDecorator(
      Executors.newCachedThreadPool(
         new ThreadFactoryBuilder().setDaemon(true).setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER)).setNameFormat("Downloader %d").build()
      )
   );

   public static CompletableFuture<?> download(File _snowman, String _snowman, Map<String, String> _snowman, int _snowman, @Nullable ProgressListener _snowman, Proxy _snowman) {
      return CompletableFuture.supplyAsync(() -> {
         HttpURLConnection _snowmanxxxxxx = null;
         InputStream _snowmanxxxxxxx = null;
         OutputStream _snowmanxxxxxxxx = null;
         if (_snowman != null) {
            _snowman.method_15413(new TranslatableText("resourcepack.downloading"));
            _snowman.method_15414(new TranslatableText("resourcepack.requesting"));
         }

         try {
            byte[] _snowmanxxxxxxxxx = new byte[4096];
            URL _snowmanxxxxxxxxxx = new URL(_snowman);
            _snowmanxxxxxx = (HttpURLConnection)_snowmanxxxxxxxxxx.openConnection(_snowman);
            _snowmanxxxxxx.setInstanceFollowRedirects(true);
            float _snowmanxxxxxxxxxxx = 0.0F;
            float _snowmanxxxxxxxxxxxx = (float)_snowman.entrySet().size();

            for (Entry<String, String> _snowmanxxxxxxxxxxxxx : _snowman.entrySet()) {
               _snowmanxxxxxx.setRequestProperty(_snowmanxxxxxxxxxxxxx.getKey(), _snowmanxxxxxxxxxxxxx.getValue());
               if (_snowman != null) {
                  _snowman.progressStagePercentage((int)(++_snowmanxxxxxxxxxxx / _snowmanxxxxxxxxxxxx * 100.0F));
               }
            }

            _snowmanxxxxxxx = _snowmanxxxxxx.getInputStream();
            _snowmanxxxxxxxxxxxx = (float)_snowmanxxxxxx.getContentLength();
            int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxx.getContentLength();
            if (_snowman != null) {
               _snowman.method_15414(new TranslatableText("resourcepack.progress", String.format(Locale.ROOT, "%.2f", _snowmanxxxxxxxxxxxx / 1000.0F / 1000.0F)));
            }

            if (_snowman.exists()) {
               long _snowmanxxxxxxxxxxxxxxx = _snowman.length();
               if (_snowmanxxxxxxxxxxxxxxx == (long)_snowmanxxxxxxxxxxxxxx) {
                  if (_snowman != null) {
                     _snowman.setDone();
                  }

                  return null;
               }

               LOGGER.warn("Deleting {} as it does not match what we currently have ({} vs our {}).", _snowman, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
               FileUtils.deleteQuietly(_snowman);
            } else if (_snowman.getParentFile() != null) {
               _snowman.getParentFile().mkdirs();
            }

            _snowmanxxxxxxxx = new DataOutputStream(new FileOutputStream(_snowman));
            if (_snowman > 0 && _snowmanxxxxxxxxxxxx > (float)_snowman) {
               if (_snowman != null) {
                  _snowman.setDone();
               }

               throw new IOException("Filesize is bigger than maximum allowed (file is " + _snowmanxxxxxxxxxxx + ", limit is " + _snowman + ")");
            } else {
               int _snowmanxxxxxx;
               while ((_snowmanxxxxxx = _snowmanxxxxxxx.read(_snowmanxxxxxxxxx)) >= 0) {
                  _snowmanxxxxxxxxxxx += (float)_snowmanxxxxxx;
                  if (_snowman != null) {
                     _snowman.progressStagePercentage((int)(_snowmanxxxxxxxxxxx / _snowmanxxxxxxxxxxxx * 100.0F));
                  }

                  if (_snowman > 0 && _snowmanxxxxxxxxxxx > (float)_snowman) {
                     if (_snowman != null) {
                        _snowman.setDone();
                     }

                     throw new IOException("Filesize was bigger than maximum allowed (got >= " + _snowmanxxxxxxxxxxx + ", limit was " + _snowman + ")");
                  }

                  if (Thread.interrupted()) {
                     LOGGER.error("INTERRUPTED");
                     if (_snowman != null) {
                        _snowman.setDone();
                     }

                     return null;
                  }

                  _snowmanxxxxxxxx.write(_snowmanxxxxxxxxx, 0, _snowmanxxxxxx);
               }

               if (_snowman != null) {
                  _snowman.setDone();
               }

               return null;
            }
         } catch (Throwable var22) {
            var22.printStackTrace();
            if (_snowmanxxxxxx != null) {
               InputStream _snowmanxxxxxxxxx = _snowmanxxxxxx.getErrorStream();

               try {
                  LOGGER.error(IOUtils.toString(_snowmanxxxxxxxxx));
               } catch (IOException var21) {
                  var21.printStackTrace();
               }
            }

            if (_snowman != null) {
               _snowman.setDone();
            }

            return null;
         } finally {
            IOUtils.closeQuietly(_snowmanxxxxxxx);
            IOUtils.closeQuietly(_snowmanxxxxxxxx);
         }
      }, downloadExecutor);
   }

   public static int findLocalPort() {
      try (ServerSocket _snowman = new ServerSocket(0)) {
         return _snowman.getLocalPort();
      } catch (IOException var14) {
         return 25564;
      }
   }
}
