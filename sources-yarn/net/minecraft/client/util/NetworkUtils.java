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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   @Environment(EnvType.CLIENT)
   public static CompletableFuture<?> download(File file, String string, Map<String, String> map, int i, @Nullable ProgressListener arg, Proxy proxy) {
      return CompletableFuture.supplyAsync(() -> {
         HttpURLConnection httpURLConnection = null;
         InputStream inputStream = null;
         OutputStream outputStream = null;
         if (arg != null) {
            arg.method_15413(new TranslatableText("resourcepack.downloading"));
            arg.method_15414(new TranslatableText("resourcepack.requesting"));
         }

         try {
            byte[] bs = new byte[4096];
            URL uRL = new URL(string);
            httpURLConnection = (HttpURLConnection)uRL.openConnection(proxy);
            httpURLConnection.setInstanceFollowRedirects(true);
            float f = 0.0F;
            float g = (float)map.entrySet().size();

            for (Entry<String, String> entry : map.entrySet()) {
               httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
               if (arg != null) {
                  arg.progressStagePercentage((int)(++f / g * 100.0F));
               }
            }

            inputStream = httpURLConnection.getInputStream();
            g = (float)httpURLConnection.getContentLength();
            int j = httpURLConnection.getContentLength();
            if (arg != null) {
               arg.method_15414(new TranslatableText("resourcepack.progress", String.format(Locale.ROOT, "%.2f", g / 1000.0F / 1000.0F)));
            }

            if (file.exists()) {
               long l = file.length();
               if (l == (long)j) {
                  if (arg != null) {
                     arg.setDone();
                  }

                  return null;
               }

               LOGGER.warn("Deleting {} as it does not match what we currently have ({} vs our {}).", file, j, l);
               FileUtils.deleteQuietly(file);
            } else if (file.getParentFile() != null) {
               file.getParentFile().mkdirs();
            }

            outputStream = new DataOutputStream(new FileOutputStream(file));
            if (i > 0 && g > (float)i) {
               if (arg != null) {
                  arg.setDone();
               }

               throw new IOException("Filesize is bigger than maximum allowed (file is " + f + ", limit is " + i + ")");
            } else {
               int k;
               while ((k = inputStream.read(bs)) >= 0) {
                  f += (float)k;
                  if (arg != null) {
                     arg.progressStagePercentage((int)(f / g * 100.0F));
                  }

                  if (i > 0 && f > (float)i) {
                     if (arg != null) {
                        arg.setDone();
                     }

                     throw new IOException("Filesize was bigger than maximum allowed (got >= " + f + ", limit was " + i + ")");
                  }

                  if (Thread.interrupted()) {
                     LOGGER.error("INTERRUPTED");
                     if (arg != null) {
                        arg.setDone();
                     }

                     return null;
                  }

                  outputStream.write(bs, 0, k);
               }

               if (arg != null) {
                  arg.setDone();
               }

               return null;
            }
         } catch (Throwable var22) {
            var22.printStackTrace();
            if (httpURLConnection != null) {
               InputStream inputStream2 = httpURLConnection.getErrorStream();

               try {
                  LOGGER.error(IOUtils.toString(inputStream2));
               } catch (IOException var21) {
                  var21.printStackTrace();
               }
            }

            if (arg != null) {
               arg.setDone();
            }

            return null;
         } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
         }
      }, downloadExecutor);
   }

   public static int findLocalPort() {
      try (ServerSocket serverSocket = new ServerSocket(0)) {
         return serverSocket.getLocalPort();
      } catch (IOException var14) {
         return 25564;
      }
   }
}
