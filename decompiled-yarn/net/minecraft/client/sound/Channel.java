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

public class Channel {
   private final Set<Channel.SourceManager> sources = Sets.newIdentityHashSet();
   private final SoundEngine soundEngine;
   private final Executor executor;

   public Channel(SoundEngine soundEngine, Executor executor) {
      this.soundEngine = soundEngine;
      this.executor = executor;
   }

   public CompletableFuture<Channel.SourceManager> createSource(SoundEngine.RunMode mode) {
      CompletableFuture<Channel.SourceManager> _snowman = new CompletableFuture<>();
      this.executor.execute(() -> {
         Source _snowmanx = this.soundEngine.createSource(mode);
         if (_snowmanx != null) {
            Channel.SourceManager _snowmanx = new Channel.SourceManager(_snowmanx);
            this.sources.add(_snowmanx);
            _snowman.complete(_snowmanx);
         } else {
            _snowman.complete(null);
         }
      });
      return _snowman;
   }

   public void execute(Consumer<Stream<Source>> _snowman) {
      this.executor.execute(() -> _snowman.accept(this.sources.stream().map(_snowmanxx -> _snowmanxx.source).filter(Objects::nonNull)));
   }

   public void tick() {
      this.executor.execute(() -> {
         Iterator<Channel.SourceManager> _snowman = this.sources.iterator();

         while (_snowman.hasNext()) {
            Channel.SourceManager _snowmanx = _snowman.next();
            _snowmanx.source.tick();
            if (_snowmanx.source.isStopped()) {
               _snowmanx.close();
               _snowman.remove();
            }
         }
      });
   }

   public void close() {
      this.sources.forEach(Channel.SourceManager::close);
      this.sources.clear();
   }

   public class SourceManager {
      @Nullable
      private Source source;
      private boolean stopped;

      public boolean isStopped() {
         return this.stopped;
      }

      public SourceManager(Source _snowman) {
         this.source = _snowman;
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
