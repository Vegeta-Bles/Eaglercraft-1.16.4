package net.minecraft.client.sound;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Channel {
   private final Set<Channel.SourceManager> sources = Sets.newIdentityHashSet();
   private final SoundEngine soundEngine;
   private final Executor executor;

   public Channel(SoundEngine soundEngine, Executor executor) {
      this.soundEngine = soundEngine;
      this.executor = executor;
   }

   public CompletableFuture<Channel.SourceManager> createSource(SoundEngine.RunMode mode) {
      CompletableFuture<Channel.SourceManager> completableFuture = new CompletableFuture<>();
      this.executor.execute(() -> {
         Source lv = this.soundEngine.createSource(mode);
         if (lv != null) {
            Channel.SourceManager lv2 = new Channel.SourceManager(lv);
            this.sources.add(lv2);
            completableFuture.complete(lv2);
         } else {
            completableFuture.complete(null);
         }
      });
      return completableFuture;
   }

   public void execute(Consumer<Stream<Source>> consumer) {
      this.executor.execute(() -> consumer.accept(this.sources.stream().map(arg -> arg.source).filter(Objects::nonNull)));
   }

   public void tick() {
      this.executor.execute(() -> {
         Iterator<Channel.SourceManager> iterator = this.sources.iterator();

         while (iterator.hasNext()) {
            Channel.SourceManager lv = iterator.next();
            lv.source.tick();
            if (lv.source.isStopped()) {
               lv.close();
               iterator.remove();
            }
         }
      });
   }

   public void close() {
      this.sources.forEach(Channel.SourceManager::close);
      this.sources.clear();
   }

   @Environment(EnvType.CLIENT)
   public class SourceManager {
      @Nullable
      private Source source;
      private boolean stopped;

      public boolean isStopped() {
         return this.stopped;
      }

      public SourceManager(Source arg2) {
         this.source = arg2;
      }

      public void run(Consumer<Source> action) {
         Channel.this.executor.execute(() -> {
            if (this.source != null) {
               action.accept(this.source);
            }
         });
      }

      public void close() {
         this.stopped = true;
         Channel.this.soundEngine.release(this.source);
         this.source = null;
      }
   }
}
