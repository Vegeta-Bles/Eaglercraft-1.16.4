package net.minecraft.client.sound;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

@Environment(EnvType.CLIENT)
public class SoundLoader {
   private final ResourceManager resourceManager;
   private final Map<Identifier, CompletableFuture<StaticSound>> loadedSounds = Maps.newHashMap();

   public SoundLoader(ResourceManager resourceManager) {
      this.resourceManager = resourceManager;
   }

   public CompletableFuture<StaticSound> loadStatic(Identifier id) {
      return this.loadedSounds.computeIfAbsent(id, arg -> CompletableFuture.supplyAsync(() -> {
            try (
               Resource lv = this.resourceManager.getResource(arg);
               InputStream inputStream = lv.getInputStream();
               OggAudioStream lv2 = new OggAudioStream(inputStream);
            ) {
               ByteBuffer byteBuffer = lv2.getBuffer();
               return new StaticSound(byteBuffer, lv2.getFormat());
            } catch (IOException var62) {
               throw new CompletionException(var62);
            }
         }, Util.getMainWorkerExecutor()));
   }

   public CompletableFuture<AudioStream> loadStreamed(Identifier id, boolean repeatInstantly) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            Resource lv = this.resourceManager.getResource(id);
            InputStream inputStream = lv.getInputStream();
            return (AudioStream)(repeatInstantly ? new RepeatingAudioStream(OggAudioStream::new, inputStream) : new OggAudioStream(inputStream));
         } catch (IOException var5) {
            throw new CompletionException(var5);
         }
      }, Util.getMainWorkerExecutor());
   }

   public void close() {
      this.loadedSounds.values().forEach(completableFuture -> completableFuture.thenAccept(StaticSound::close));
      this.loadedSounds.clear();
   }

   public CompletableFuture<?> loadStatic(Collection<Sound> sounds) {
      return CompletableFuture.allOf(sounds.stream().map(arg -> this.loadStatic(arg.getLocation())).toArray(CompletableFuture[]::new));
   }
}
