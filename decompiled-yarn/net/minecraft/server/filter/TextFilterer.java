package net.minecraft.server.filter;

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
import net.minecraft.SharedConstants;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import net.minecraft.util.thread.TaskExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextFilterer implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final AtomicInteger NEXT_WORKER_ID = new AtomicInteger(1);
   private static final ThreadFactory THREAD_FACTORY = _snowman -> {
      Thread _snowmanx = new Thread(_snowman);
      _snowmanx.setName("Chat-Filter-Worker-" + NEXT_WORKER_ID.getAndIncrement());
      return _snowmanx;
   };
   private final URL chatEndpoint;
   private final URL joinEndpoint;
   private final URL leaveEndpoint;
   private final String apiKey;
   private final int ruleId;
   private final String serverId;
   private final TextFilterer.HashIgnorer ignorer;
   private final ExecutorService executor;

   private void sendJoinOrLeaveRequest(GameProfile gameProfile, URL endpoint, Executor executor) {
      JsonObject _snowman = new JsonObject();
      _snowman.addProperty("server", this.serverId);
      _snowman.addProperty("room", "Chat");
      _snowman.addProperty("user_id", gameProfile.getId().toString());
      _snowman.addProperty("user_display_name", gameProfile.getName());
      executor.execute(() -> {
         try {
            this.sendRequest(_snowman, endpoint);
         } catch (Exception var5) {
            LOGGER.warn("Failed to send join/leave packet to {} for player {}", endpoint, gameProfile, var5);
         }
      });
   }

   private CompletableFuture<Optional<String>> filterMessage(GameProfile gameProfile, String message, TextFilterer.HashIgnorer ignorer, Executor executor) {
      if (message.isEmpty()) {
         return CompletableFuture.completedFuture(Optional.of(""));
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("rule", this.ruleId);
         _snowman.addProperty("server", this.serverId);
         _snowman.addProperty("room", "Chat");
         _snowman.addProperty("player", gameProfile.getId().toString());
         _snowman.addProperty("player_display_name", gameProfile.getName());
         _snowman.addProperty("text", message);
         return CompletableFuture.supplyAsync(() -> {
            try {
               JsonObject _snowmanx = this.sendJsonRequest(_snowman, this.chatEndpoint);
               boolean _snowmanx = JsonHelper.getBoolean(_snowmanx, "response", false);
               if (_snowmanx) {
                  return Optional.of(message);
               } else {
                  String _snowmanxx = JsonHelper.getString(_snowmanx, "hashed", null);
                  if (_snowmanxx == null) {
                     return Optional.empty();
                  } else {
                     int _snowmanxxx = JsonHelper.getArray(_snowmanx, "hashes").size();
                     return ignorer.shouldIgnore(_snowmanxx, _snowmanxxx) ? Optional.empty() : Optional.of(_snowmanxx);
                  }
               }
            } catch (Exception var8) {
               LOGGER.warn("Failed to validate message '{}'", message, var8);
               return Optional.empty();
            }
         }, executor);
      }
   }

   @Override
   public void close() {
      this.executor.shutdownNow();
   }

   private void consumeFully(InputStream inputStream) throws IOException {
      byte[] _snowman = new byte[1024];

      while (inputStream.read(_snowman) != -1) {
      }
   }

   private JsonObject sendJsonRequest(JsonObject payload, URL endpoint) throws IOException {
      HttpURLConnection _snowman = this.createConnection(payload, endpoint);

      JsonObject var6;
      try (InputStream _snowmanx = _snowman.getInputStream()) {
         if (_snowman.getResponseCode() != 204) {
            try {
               return Streams.parse(new JsonReader(new InputStreamReader(_snowmanx))).getAsJsonObject();
            } finally {
               this.consumeFully(_snowmanx);
            }
         }

         var6 = new JsonObject();
      }

      return var6;
   }

   private void sendRequest(JsonObject payload, URL endpoint) throws IOException {
      HttpURLConnection _snowman = this.createConnection(payload, endpoint);

      try (InputStream _snowmanx = _snowman.getInputStream()) {
         this.consumeFully(_snowmanx);
      }
   }

   private HttpURLConnection createConnection(JsonObject payload, URL endpoint) throws IOException {
      HttpURLConnection _snowman = (HttpURLConnection)endpoint.openConnection();
      _snowman.setConnectTimeout(15000);
      _snowman.setReadTimeout(2000);
      _snowman.setUseCaches(false);
      _snowman.setDoOutput(true);
      _snowman.setDoInput(true);
      _snowman.setRequestMethod("POST");
      _snowman.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      _snowman.setRequestProperty("Accept", "application/json");
      _snowman.setRequestProperty("Authorization", "Basic " + this.apiKey);
      _snowman.setRequestProperty("User-Agent", "Minecraft server" + SharedConstants.getGameVersion().getName());

      try (OutputStreamWriter _snowmanx = new OutputStreamWriter(_snowman.getOutputStream(), StandardCharsets.UTF_8)) {
         JsonWriter _snowmanxx = new JsonWriter(_snowmanx);
         Throwable var7 = null;

         try {
            Streams.write(payload, _snowmanxx);
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
         throw new TextFilterer.FailedHttpRequestException(_snowmanx + " " + _snowman.getResponseMessage());
      }
   }

   public TextStream createFilterer(GameProfile gameProfile) {
      return new TextFilterer.Impl(gameProfile);
   }

   public static class FailedHttpRequestException extends RuntimeException {
      private FailedHttpRequestException(String message) {
         super(message);
      }
   }

   @FunctionalInterface
   public interface HashIgnorer {
      TextFilterer.HashIgnorer NEVER_IGNORE = (_snowman, _snowmanx) -> false;
      TextFilterer.HashIgnorer IGNORE_IF_MATCHES_ALL = (_snowman, _snowmanx) -> _snowman.length() == _snowmanx;

      boolean shouldIgnore(String hashes, int hashesSize);
   }

   class Impl implements TextStream {
      private final GameProfile gameProfile;
      private final Executor executor;

      private Impl(GameProfile var2) {
         this.gameProfile = _snowman;
         TaskExecutor<Runnable> _snowman = TaskExecutor.create(TextFilterer.this.executor, "chat stream for " + _snowman.getName());
         this.executor = _snowman::send;
      }

      @Override
      public void onConnect() {
         TextFilterer.this.sendJoinOrLeaveRequest(this.gameProfile, TextFilterer.this.joinEndpoint, this.executor);
      }

      @Override
      public void onDisconnect() {
         TextFilterer.this.sendJoinOrLeaveRequest(this.gameProfile, TextFilterer.this.leaveEndpoint, this.executor);
      }

      @Override
      public CompletableFuture<Optional<List<String>>> filterTexts(List<String> texts) {
         List<CompletableFuture<Optional<String>>> _snowman = texts.stream()
            .map(_snowmanx -> TextFilterer.this.filterMessage(this.gameProfile, _snowmanx, TextFilterer.this.ignorer, this.executor))
            .collect(ImmutableList.toImmutableList());
         return Util.combine(_snowman)
            .thenApply(_snowmanx -> Optional.of(_snowmanx.stream().map(_snowmanxx -> _snowmanxx.orElse("")).collect(ImmutableList.toImmutableList())))
            .exceptionally(_snowmanx -> Optional.empty());
      }

      @Override
      public CompletableFuture<Optional<String>> filterText(String text) {
         return TextFilterer.this.filterMessage(this.gameProfile, text, TextFilterer.this.ignorer, this.executor);
      }
   }
}
