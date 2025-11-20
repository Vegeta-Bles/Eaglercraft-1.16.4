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

   public SnbtProvider(DataGenerator arg) {
      this.root = arg;
   }

   public SnbtProvider addWriter(SnbtProvider.Tweaker arg) {
      this.write.add(arg);
      return this;
   }

   private CompoundTag write(String string, CompoundTag arg) {
      CompoundTag lv = arg;

      for (SnbtProvider.Tweaker lv2 : this.write) {
         lv = lv2.write(string, lv);
      }

      return lv;
   }

   @Override
   public void run(DataCache cache) throws IOException {
      Path path = this.root.getOutput();
      List<CompletableFuture<SnbtProvider.CompressedData>> list = Lists.newArrayList();

      for (Path path2 : this.root.getInputs()) {
         Files.walk(path2)
            .filter(pathx -> pathx.toString().endsWith(".snbt"))
            .forEach(
               path2x -> list.add(
                     CompletableFuture.supplyAsync(() -> this.toCompressedNbt(path2x, this.getFileName(path2, path2x)), Util.getMainWorkerExecutor())
                  )
            );
      }

      Util.combine(list).join().stream().filter(Objects::nonNull).forEach(arg2 -> this.write(cache, arg2, path));
   }

   @Override
   public String getName() {
      return "SNBT -> NBT";
   }

   private String getFileName(Path root, Path file) {
      String string = root.relativize(file).toString().replaceAll("\\\\", "/");
      return string.substring(0, string.length() - ".snbt".length());
   }

   @Nullable
   private SnbtProvider.CompressedData toCompressedNbt(Path path, String name) {
      try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
         String string2 = IOUtils.toString(bufferedReader);
         CompoundTag lv = this.write(name, StringNbtReader.parse(string2));
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         NbtIo.writeCompressed(lv, byteArrayOutputStream);
         byte[] bs = byteArrayOutputStream.toByteArray();
         String string3 = SHA1.hashBytes(bs).toString();
         String string4;
         if (field_24615 != null) {
            string4 = lv.toText("    ", 0).getString() + "\n";
         } else {
            string4 = null;
         }

         return new SnbtProvider.CompressedData(name, bs, string4, string3);
      } catch (CommandSyntaxException var24) {
         LOGGER.error("Couldn't convert {} from SNBT to NBT at {} as it's invalid SNBT", name, path, var24);
      } catch (IOException var25) {
         LOGGER.error("Couldn't convert {} from SNBT to NBT at {}", name, path, var25);
      }

      return null;
   }

   private void write(DataCache arg, SnbtProvider.CompressedData arg2, Path path) {
      if (arg2.field_24616 != null) {
         Path path2 = field_24615.resolve(arg2.name + ".snbt");

         try {
            FileUtils.write(path2.toFile(), arg2.field_24616, StandardCharsets.UTF_8);
         } catch (IOException var18) {
            LOGGER.error("Couldn't write structure SNBT {} at {}", arg2.name, path2, var18);
         }
      }

      Path path3 = path.resolve(arg2.name + ".nbt");

      try {
         if (!Objects.equals(arg.getOldSha1(path3), arg2.sha1) || !Files.exists(path3)) {
            Files.createDirectories(path3.getParent());

            try (OutputStream outputStream = Files.newOutputStream(path3)) {
               outputStream.write(arg2.bytes);
            }
         }

         arg.updateSha1(path3, arg2.sha1);
      } catch (IOException var20) {
         LOGGER.error("Couldn't write structure {} at {}", arg2.name, path3, var20);
      }
   }

   static class CompressedData {
      private final String name;
      private final byte[] bytes;
      @Nullable
      private final String field_24616;
      private final String sha1;

      public CompressedData(String name, byte[] bytes, @Nullable String sha1, String string3) {
         this.name = name;
         this.bytes = bytes;
         this.field_24616 = sha1;
         this.sha1 = string3;
      }
   }

   @FunctionalInterface
   public interface Tweaker {
      CompoundTag write(String name, CompoundTag nbt);
   }
}
