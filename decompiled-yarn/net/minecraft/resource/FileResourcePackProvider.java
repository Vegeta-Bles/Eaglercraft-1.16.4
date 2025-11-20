package net.minecraft.resource;

import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FileResourcePackProvider implements ResourcePackProvider {
   private static final FileFilter POSSIBLE_PACK = _snowman -> {
      boolean _snowmanx = _snowman.isFile() && _snowman.getName().endsWith(".zip");
      boolean _snowmanxx = _snowman.isDirectory() && new File(_snowman, "pack.mcmeta").isFile();
      return _snowmanx || _snowmanxx;
   };
   private final File packsFolder;
   private final ResourcePackSource field_25345;

   public FileResourcePackProvider(File packsFolder, ResourcePackSource _snowman) {
      this.packsFolder = packsFolder;
      this.field_25345 = _snowman;
   }

   @Override
   public void register(Consumer<ResourcePackProfile> _snowman, ResourcePackProfile.Factory factory) {
      if (!this.packsFolder.isDirectory()) {
         this.packsFolder.mkdirs();
      }

      File[] _snowmanx = this.packsFolder.listFiles(POSSIBLE_PACK);
      if (_snowmanx != null) {
         for (File _snowmanxx : _snowmanx) {
            String _snowmanxxx = "file/" + _snowmanxx.getName();
            ResourcePackProfile _snowmanxxxx = ResourcePackProfile.of(
               _snowmanxxx, false, this.createResourcePack(_snowmanxx), factory, ResourcePackProfile.InsertionPosition.TOP, this.field_25345
            );
            if (_snowmanxxxx != null) {
               _snowman.accept(_snowmanxxxx);
            }
         }
      }
   }

   private Supplier<ResourcePack> createResourcePack(File file) {
      return file.isDirectory() ? () -> new DirectoryResourcePack(file) : () -> new ZipResourcePack(file);
   }
}
