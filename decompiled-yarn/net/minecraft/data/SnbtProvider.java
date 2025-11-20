package net.minecraft.data;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SnbtProvider implements DataProvider {
   @Nullable
   private static final Path field_24615 = null;
   private static final Logger LOGGER = LogManager.getLogger();
   private final DataGenerator root;
   private final List<SnbtProvider.Tweaker> write = Lists.newArrayList();

   public SnbtProvider(DataGenerator _snowman) {
      this.root = _snowman;
   }

   public SnbtProvider addWriter(SnbtProvider.Tweaker _snowman) {
      this.write.add(_snowman);
      return this;
   }

   private CompoundTag write(String _snowman, CompoundTag _snowman) {
      CompoundTag _snowmanxx = _snowman;

      for (SnbtProvider.Tweaker _snowmanxxx : this.write) {
         _snowmanxx = _snowmanxxx.write(_snowman, _snowmanxx);
      }

      return _snowmanxx;
   }

   @Override
   public void run(DataCache cache) throws IOException {
      Path _snowman = this.root.getOutput();
      List<CompletableFuture<SnbtProvider.CompressedData>> _snowmanx = Lists.newArrayList();

      for (Path _snowmanxx : this.root.getInputs()) {
         Files.walk(_snowmanxx)
            .filter(_snowmanxxx -> _snowmanxxx.toString().endsWith(".snbt"))
            .forEach(_snowmanxxx -> _snowman.add(CompletableFuture.supplyAsync(() -> this.toCompressedNbt(_snowmanxx, this.getFileName(_snowman, _snowmanxx)), Util.getMainWorkerExecutor())));
      }

      Util.combine(_snowmanx).join().stream().filter(Objects::nonNull).forEach(_snowmanxx -> this.write(cache, _snowmanxx, _snowman));
   }

   @Override
   public String getName() {
      return "SNBT -> NBT";
   }

   private String getFileName(Path root, Path file) {
      String _snowman = root.relativize(file).toString().replaceAll("\\\\", "/");
      return _snowman.substring(0, _snowman.length() - ".snbt".length());
   }

   @Nullable
   private SnbtProvider.CompressedData toCompressedNbt(Path _snowman, String name) {
      try (BufferedReader _snowmanx = Files.newBufferedReader(_snowman)) {
         String _snowmanxx = IOUtils.toString(_snowmanx);
         CompoundTag _snowmanxxx = this.write(name, StringNbtReader.parse(_snowmanxx));
         ByteArrayOutputStream _snowmanxxxx = new ByteArrayOutputStream();
         NbtIo.writeCompressed(_snowmanxxx, _snowmanxxxx);
         byte[] _snowmanxxxxx = _snowmanxxxx.toByteArray();
         String _snowmanxxxxxx = SHA1.hashBytes(_snowmanxxxxx).toString();
         String _snowmanxxxxxxx;
         if (field_24615 != null) {
            _snowmanxxxxxxx = _snowmanxxx.toText("    ", 0).getString() + "\n";
         } else {
            _snowmanxxxxxxx = null;
         }

         return new SnbtProvider.CompressedData(name, _snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx);
      } catch (CommandSyntaxException var24) {
         LOGGER.error("Couldn't convert {} from SNBT to NBT at {} as it's invalid SNBT", name, _snowman, var24);
      } catch (IOException var25) {
         LOGGER.error("Couldn't convert {} from SNBT to NBT at {}", name, _snowman, var25);
      }

      return null;
   }

   private void write(DataCache _snowman, SnbtProvider.CompressedData _snowman, Path _snowman) {
      if (_snowman.field_24616 != null) {
         Path _snowmanxxx = field_24615.resolve(_snowman.name + ".snbt");

         try {
            FileUtils.write(_snowmanxxx.toFile(), _snowman.field_24616, StandardCharsets.UTF_8);
         } catch (IOException var18) {
            LOGGER.error("Couldn't write structure SNBT {} at {}", _snowman.name, _snowmanxxx, var18);
         }
      }

      Path _snowmanxxx = _snowman.resolve(_snowman.name + ".nbt");

      try {
         if (!Objects.equals(_snowman.getOldSha1(_snowmanxxx), _snowman.sha1) || !Files.exists(_snowmanxxx)) {
            Files.createDirectories(_snowmanxxx.getParent());

            try (OutputStream _snowmanxxxx = Files.newOutputStream(_snowmanxxx)) {
               _snowmanxxxx.write(_snowman.bytes);
            }
         }

         _snowman.updateSha1(_snowmanxxx, _snowman.sha1);
      } catch (IOException var20) {
         LOGGER.error("Couldn't write structure {} at {}", _snowman.name, _snowmanxxx, var20);
      }
   }

   static class CompressedData {
      private final String name;
      private final byte[] bytes;
      @Nullable
      private final String field_24616;
      private final String sha1;

      public CompressedData(String name, byte[] bytes, @Nullable String sha1, String _snowman) {
         this.name = name;
         this.bytes = bytes;
         this.field_24616 = sha1;
         this.sha1 = _snowman;
      }
   }

   @FunctionalInterface
   public interface Tweaker {
      CompoundTag write(String name, CompoundTag nbt);
   }
}
