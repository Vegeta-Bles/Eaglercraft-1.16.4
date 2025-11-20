package net.minecraft.client.sound;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class SoundLoader {
   private final ResourceManager resourceManager;
   private final Map<Identifier, CompletableFuture<StaticSound>> loadedSounds = Maps.newHashMap();

   public SoundLoader(ResourceManager resourceManager) {
      this.resourceManager = resourceManager;
   }

   public CompletableFuture<StaticSound> loadStatic(Identifier id) {
      return this.loadedSounds.computeIfAbsent(id, _snowman -> CompletableFuture.supplyAsync(() -> {
            try (
               Resource _snowmanx = this.resourceManager.getResource(_snowman);
               InputStream _snowmanxx = _snowmanx.getInputStream();
               OggAudioStream _snowmanxxx = new OggAudioStream(_snowmanxx);
            ) {
               ByteBuffer _snowmanxxxx = _snowmanxxx.getBuffer();
               return new StaticSound(_snowmanxxxx, _snowmanxxx.getFormat());
            } catch (IOException var62) {
               throw new CompletionException(var62);
            }
         }, Util.getMainWorkerExecutor()));
   }

   public CompletableFuture<AudioStream> loadStreamed(Identifier id, boolean repeatInstantly) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            Resource _snowmanxx = this.resourceManager.getResource(id);
            InputStream _snowmanx = _snowmanxx.getInputStream();
            return (AudioStream)(repeatInstantly ? new RepeatingAudioStream(OggAudioStream::new, _snowmanx) : new OggAudioStream(_snowmanx));
         } catch (IOException var5) {
            throw new CompletionException(var5);
         }
      }, Util.getMainWorkerExecutor());
   }

   public void close() {
      this.loadedSounds.values().forEach(_snowman -> _snowman.thenAccept(StaticSound::close));
      this.loadedSounds.clear();
   }

   public CompletableFuture<?> loadStatic(Collection<Sound> sounds) {
      return CompletableFuture.allOf(sounds.stream().map(_snowman -> this.loadStatic(_snowman.getLocation())).toArray(CompletableFuture[]::new));
   }
}
