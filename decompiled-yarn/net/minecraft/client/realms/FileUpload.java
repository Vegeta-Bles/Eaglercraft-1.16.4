package net.minecraft.client.realms;

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
import net.minecraft.client.realms.dto.UploadInfo;
import net.minecraft.client.realms.gui.screen.UploadResult;
import net.minecraft.client.util.Session;
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

public class FileUpload {
   private static final Logger LOGGER = LogManager.getLogger();
   private final File file;
   private final long worldId;
   private final int slotId;
   private final UploadInfo uploadInfo;
   private final String sessionId;
   private final String username;
   private final String clientVersion;
   private final UploadStatus uploadStatus;
   private final AtomicBoolean cancelled = new AtomicBoolean(false);
   private CompletableFuture<UploadResult> uploadTask;
   private final RequestConfig requestConfig = RequestConfig.custom()
      .setSocketTimeout((int)TimeUnit.MINUTES.toMillis(10L))
      .setConnectTimeout((int)TimeUnit.SECONDS.toMillis(15L))
      .build();

   public FileUpload(File file, long worldId, int slotId, UploadInfo uploadInfo, Session _snowman, String clientVersion, UploadStatus _snowman) {
      this.file = file;
      this.worldId = worldId;
      this.slotId = slotId;
      this.uploadInfo = uploadInfo;
      this.sessionId = _snowman.getSessionId();
      this.username = _snowman.getUsername();
      this.clientVersion = clientVersion;
      this.uploadStatus = _snowman;
   }

   public void upload(Consumer<UploadResult> callback) {
      if (this.uploadTask == null) {
         this.uploadTask = CompletableFuture.supplyAsync(() -> this.requestUpload(0));
         this.uploadTask.thenAccept(callback);
      }
   }

   public void cancel() {
      this.cancelled.set(true);
      if (this.uploadTask != null) {
         this.uploadTask.cancel(false);
         this.uploadTask = null;
      }
   }

   private UploadResult requestUpload(int currentAttempt) {
      UploadResult.Builder _snowman = new UploadResult.Builder();
      if (this.cancelled.get()) {
         return _snowman.build();
      } else {
         this.uploadStatus.totalBytes = this.file.length();
         HttpPost _snowmanx = new HttpPost(this.uploadInfo.getUploadEndpoint().resolve("/upload/" + this.worldId + "/" + this.slotId));
         CloseableHttpClient _snowmanxx = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();

         UploadResult var8;
         try {
            this.setupRequest(_snowmanx);
            HttpResponse _snowmanxxx = _snowmanxx.execute(_snowmanx);
            long _snowmanxxxx = this.getRetryDelaySeconds(_snowmanxxx);
            if (!this.shouldRetry(_snowmanxxxx, currentAttempt)) {
               this.handleResponse(_snowmanxxx, _snowman);
               return _snowman.build();
            }

            var8 = this.retryUploadAfter(_snowmanxxxx, currentAttempt);
         } catch (Exception var12) {
            if (!this.cancelled.get()) {
               LOGGER.error("Caught exception while uploading: ", var12);
            }

            return _snowman.build();
         } finally {
            this.cleanup(_snowmanx, _snowmanxx);
         }

         return var8;
      }
   }

   private void cleanup(HttpPost request, CloseableHttpClient client) {
      request.releaseConnection();
      if (client != null) {
         try {
            client.close();
         } catch (IOException var4) {
            LOGGER.error("Failed to close Realms upload client");
         }
      }
   }

   private void setupRequest(HttpPost request) throws FileNotFoundException {
      request.setHeader(
         "Cookie", "sid=" + this.sessionId + ";token=" + this.uploadInfo.getToken() + ";user=" + this.username + ";version=" + this.clientVersion
      );
      FileUpload.CustomInputStreamEntity _snowman = new FileUpload.CustomInputStreamEntity(new FileInputStream(this.file), this.file.length(), this.uploadStatus);
      _snowman.setContentType("application/octet-stream");
      request.setEntity(_snowman);
   }

   private void handleResponse(HttpResponse response, UploadResult.Builder uploadResultBuilder) throws IOException {
      int _snowman = response.getStatusLine().getStatusCode();
      if (_snowman == 401) {
         LOGGER.debug("Realms server returned 401: " + response.getFirstHeader("WWW-Authenticate"));
      }

      uploadResultBuilder.withStatusCode(_snowman);
      if (response.getEntity() != null) {
         String _snowmanx = EntityUtils.toString(response.getEntity(), "UTF-8");
         if (_snowmanx != null) {
            try {
               JsonParser _snowmanxx = new JsonParser();
               JsonElement _snowmanxxx = _snowmanxx.parse(_snowmanx).getAsJsonObject().get("errorMsg");
               Optional<String> _snowmanxxxx = Optional.ofNullable(_snowmanxxx).map(JsonElement::getAsString);
               uploadResultBuilder.withErrorMessage(_snowmanxxxx.orElse(null));
            } catch (Exception var8) {
            }
         }
      }
   }

   private boolean shouldRetry(long retryDelaySeconds, int currentAttempt) {
      return retryDelaySeconds > 0L && currentAttempt + 1 < 5;
   }

   private UploadResult retryUploadAfter(long retryDelaySeconds, int currentAttempt) throws InterruptedException {
      Thread.sleep(Duration.ofSeconds(retryDelaySeconds).toMillis());
      return this.requestUpload(currentAttempt + 1);
   }

   private long getRetryDelaySeconds(HttpResponse response) {
      return Optional.ofNullable(response.getFirstHeader("Retry-After")).<String>map(Header::getValue).map(Long::valueOf).orElse(0L);
   }

   public boolean isFinished() {
      return this.uploadTask.isDone() || this.uploadTask.isCancelled();
   }

   static class CustomInputStreamEntity extends InputStreamEntity {
      private final long length;
      private final InputStream content;
      private final UploadStatus uploadStatus;

      public CustomInputStreamEntity(InputStream content, long length, UploadStatus uploadStatus) {
         super(content);
         this.content = content;
         this.length = length;
         this.uploadStatus = uploadStatus;
      }

      public void writeTo(OutputStream outstream) throws IOException {
         Args.notNull(outstream, "Output stream");
         InputStream _snowman = this.content;

         try {
            byte[] _snowmanx = new byte[4096];
            int _snowmanxx;
            if (this.length < 0L) {
               while ((_snowmanxx = _snowman.read(_snowmanx)) != -1) {
                  outstream.write(_snowmanx, 0, _snowmanxx);
                  this.uploadStatus.bytesWritten += (long)_snowmanxx;
               }
            } else {
               long _snowmanxxx = this.length;

               while (_snowmanxxx > 0L) {
                  _snowmanxx = _snowman.read(_snowmanx, 0, (int)Math.min(4096L, _snowmanxxx));
                  if (_snowmanxx == -1) {
                     break;
                  }

                  outstream.write(_snowmanx, 0, _snowmanxx);
                  this.uploadStatus.bytesWritten += (long)_snowmanxx;
                  _snowmanxxx -= (long)_snowmanxx;
                  outstream.flush();
               }
            }
         } finally {
            _snowman.close();
         }
      }
   }
}
