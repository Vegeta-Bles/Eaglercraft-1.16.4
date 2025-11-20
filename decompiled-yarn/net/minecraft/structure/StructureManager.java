package net.minecraft.structure;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.FileNameUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<Identifier, Structure> structures = Maps.newHashMap();
   private final DataFixer dataFixer;
   private ResourceManager field_25189;
   private final Path generatedPath;

   public StructureManager(ResourceManager _snowman, LevelStorage.Session _snowman, DataFixer dataFixer) {
      this.field_25189 = _snowman;
      this.dataFixer = dataFixer;
      this.generatedPath = _snowman.getDirectory(WorldSavePath.GENERATED).normalize();
   }

   public Structure getStructureOrBlank(Identifier id) {
      Structure _snowman = this.getStructure(id);
      if (_snowman == null) {
         _snowman = new Structure();
         this.structures.put(id, _snowman);
      }

      return _snowman;
   }

   @Nullable
   public Structure getStructure(Identifier identifier) {
      return this.structures.computeIfAbsent(identifier, _snowman -> {
         Structure _snowmanx = this.loadStructureFromFile(_snowman);
         return _snowmanx != null ? _snowmanx : this.loadStructureFromResource(_snowman);
      });
   }

   public void method_29300(ResourceManager _snowman) {
      this.field_25189 = _snowman;
      this.structures.clear();
   }

   @Nullable
   private Structure loadStructureFromResource(Identifier id) {
      Identifier _snowman = new Identifier(id.getNamespace(), "structures/" + id.getPath() + ".nbt");

      try (Resource _snowmanx = this.field_25189.getResource(_snowman)) {
         return this.readStructure(_snowmanx.getInputStream());
      } catch (FileNotFoundException var18) {
         return null;
      } catch (Throwable var19) {
         LOGGER.error("Couldn't load structure {}: {}", id, var19.toString());
         return null;
      }
   }

   @Nullable
   private Structure loadStructureFromFile(Identifier id) {
      if (!this.generatedPath.toFile().isDirectory()) {
         return null;
      } else {
         Path _snowman = this.getAndCheckStructurePath(id, ".nbt");

         try (InputStream _snowmanx = new FileInputStream(_snowman.toFile())) {
            return this.readStructure(_snowmanx);
         } catch (FileNotFoundException var18) {
            return null;
         } catch (IOException var19) {
            LOGGER.error("Couldn't load structure from {}", _snowman, var19);
            return null;
         }
      }
   }

   private Structure readStructure(InputStream structureInputStream) throws IOException {
      CompoundTag _snowman = NbtIo.readCompressed(structureInputStream);
      return this.createStructure(_snowman);
   }

   public Structure createStructure(CompoundTag tag) {
      if (!tag.contains("DataVersion", 99)) {
         tag.putInt("DataVersion", 500);
      }

      Structure _snowman = new Structure();
      _snowman.fromTag(NbtHelper.update(this.dataFixer, DataFixTypes.STRUCTURE, tag, tag.getInt("DataVersion")));
      return _snowman;
   }

   public boolean saveStructure(Identifier id) {
      Structure _snowman = this.structures.get(id);
      if (_snowman == null) {
         return false;
      } else {
         Path _snowmanx = this.getAndCheckStructurePath(id, ".nbt");
         Path _snowmanxx = _snowmanx.getParent();
         if (_snowmanxx == null) {
            return false;
         } else {
            try {
               Files.createDirectories(Files.exists(_snowmanxx) ? _snowmanxx.toRealPath() : _snowmanxx);
            } catch (IOException var19) {
               LOGGER.error("Failed to create parent directory: {}", _snowmanxx);
               return false;
            }

            CompoundTag _snowmanxxx = _snowman.toTag(new CompoundTag());

            try (OutputStream _snowmanxxxx = new FileOutputStream(_snowmanx.toFile())) {
               NbtIo.writeCompressed(_snowmanxxx, _snowmanxxxx);
               return true;
            } catch (Throwable var21) {
               return false;
            }
         }
      }
   }

   public Path getStructurePath(Identifier id, String extension) {
      try {
         Path _snowman = this.generatedPath.resolve(id.getNamespace());
         Path _snowmanx = _snowman.resolve("structures");
         return FileNameUtil.getResourcePath(_snowmanx, id.getPath(), extension);
      } catch (InvalidPathException var5) {
         throw new InvalidIdentifierException("Invalid resource path: " + id, var5);
      }
   }

   private Path getAndCheckStructurePath(Identifier id, String extension) {
      if (id.getPath().contains("//")) {
         throw new InvalidIdentifierException("Invalid resource path: " + id);
      } else {
         Path _snowman = this.getStructurePath(id, extension);
         if (_snowman.startsWith(this.generatedPath) && FileNameUtil.isNormal(_snowman) && FileNameUtil.isAllowedName(_snowman)) {
            return _snowman;
         } else {
            throw new InvalidIdentifierException("Invalid resource path: " + _snowman);
         }
      }
   }

   public void unloadStructure(Identifier id) {
      this.structures.remove(id);
   }
}
