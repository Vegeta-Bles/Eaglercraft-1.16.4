package net.minecraft.data.dev;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NbtProvider implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private final DataGenerator root;

   public NbtProvider(DataGenerator _snowman) {
      this.root = _snowman;
   }

   @Override
   public void run(DataCache cache) throws IOException {
      Path _snowman = this.root.getOutput();

      for (Path _snowmanx : this.root.getInputs()) {
         Files.walk(_snowmanx).filter(_snowmanxx -> _snowmanxx.toString().endsWith(".nbt")).forEach(_snowmanxx -> convertNbtToSnbt(_snowmanxx, this.getLocation(_snowman, _snowmanxx), _snowman));
      }
   }

   @Override
   public String getName() {
      return "NBT to SNBT";
   }

   private String getLocation(Path targetPath, Path rootPath) {
      String _snowman = targetPath.relativize(rootPath).toString().replaceAll("\\\\", "/");
      return _snowman.substring(0, _snowman.length() - ".nbt".length());
   }

   @Nullable
   public static Path convertNbtToSnbt(Path inputPath, String location, Path outputPath) {
      try {
         CompoundTag _snowman = NbtIo.readCompressed(Files.newInputStream(inputPath));
         Text _snowmanx = _snowman.toText("    ", 0);
         String _snowmanxx = _snowmanx.getString() + "\n";
         Path _snowmanxxx = outputPath.resolve(location + ".snbt");
         Files.createDirectories(_snowmanxxx.getParent());

         try (BufferedWriter _snowmanxxxx = Files.newBufferedWriter(_snowmanxxx)) {
            _snowmanxxxx.write(_snowmanxx);
         }

         LOGGER.info("Converted {} from NBT to SNBT", location);
         return _snowmanxxx;
      } catch (IOException var20) {
         LOGGER.error("Couldn't convert {} from NBT to SNBT at {}", location, inputPath, var20);
         return null;
      }
   }
}
