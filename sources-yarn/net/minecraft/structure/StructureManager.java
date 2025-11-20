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

   public StructureManager(ResourceManager arg, LevelStorage.Session arg2, DataFixer dataFixer) {
      this.field_25189 = arg;
      this.dataFixer = dataFixer;
      this.generatedPath = arg2.getDirectory(WorldSavePath.GENERATED).normalize();
   }

   public Structure getStructureOrBlank(Identifier id) {
      Structure lv = this.getStructure(id);
      if (lv == null) {
         lv = new Structure();
         this.structures.put(id, lv);
      }

      return lv;
   }

   @Nullable
   public Structure getStructure(Identifier identifier) {
      return this.structures.computeIfAbsent(identifier, arg -> {
         Structure lv = this.loadStructureFromFile(arg);
         return lv != null ? lv : this.loadStructureFromResource(arg);
      });
   }

   public void method_29300(ResourceManager arg) {
      this.field_25189 = arg;
      this.structures.clear();
   }

   @Nullable
   private Structure loadStructureFromResource(Identifier id) {
      Identifier lv = new Identifier(id.getNamespace(), "structures/" + id.getPath() + ".nbt");

      try (Resource lv2 = this.field_25189.getResource(lv)) {
         return this.readStructure(lv2.getInputStream());
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
         Path path = this.getAndCheckStructurePath(id, ".nbt");

         try (InputStream inputStream = new FileInputStream(path.toFile())) {
            return this.readStructure(inputStream);
         } catch (FileNotFoundException var18) {
            return null;
         } catch (IOException var19) {
            LOGGER.error("Couldn't load structure from {}", path, var19);
            return null;
         }
      }
   }

   private Structure readStructure(InputStream structureInputStream) throws IOException {
      CompoundTag lv = NbtIo.readCompressed(structureInputStream);
      return this.createStructure(lv);
   }

   public Structure createStructure(CompoundTag tag) {
      if (!tag.contains("DataVersion", 99)) {
         tag.putInt("DataVersion", 500);
      }

      Structure lv = new Structure();
      lv.fromTag(NbtHelper.update(this.dataFixer, DataFixTypes.STRUCTURE, tag, tag.getInt("DataVersion")));
      return lv;
   }

   public boolean saveStructure(Identifier id) {
      Structure lv = this.structures.get(id);
      if (lv == null) {
         return false;
      } else {
         Path path = this.getAndCheckStructurePath(id, ".nbt");
         Path path2 = path.getParent();
         if (path2 == null) {
            return false;
         } else {
            try {
               Files.createDirectories(Files.exists(path2) ? path2.toRealPath() : path2);
            } catch (IOException var19) {
               LOGGER.error("Failed to create parent directory: {}", path2);
               return false;
            }

            CompoundTag lv2 = lv.toTag(new CompoundTag());

            try (OutputStream outputStream = new FileOutputStream(path.toFile())) {
               NbtIo.writeCompressed(lv2, outputStream);
               return true;
            } catch (Throwable var21) {
               return false;
            }
         }
      }
   }

   public Path getStructurePath(Identifier id, String extension) {
      try {
         Path path = this.generatedPath.resolve(id.getNamespace());
         Path path2 = path.resolve("structures");
         return FileNameUtil.getResourcePath(path2, id.getPath(), extension);
      } catch (InvalidPathException var5) {
         throw new InvalidIdentifierException("Invalid resource path: " + id, var5);
      }
   }

   private Path getAndCheckStructurePath(Identifier id, String extension) {
      if (id.getPath().contains("//")) {
         throw new InvalidIdentifierException("Invalid resource path: " + id);
      } else {
         Path path = this.getStructurePath(id, extension);
         if (path.startsWith(this.generatedPath) && FileNameUtil.isNormal(path) && FileNameUtil.isAllowedName(path)) {
            return path;
         } else {
            throw new InvalidIdentifierException("Invalid resource path: " + path);
         }
      }
   }

   public void unloadStructure(Identifier id) {
      this.structures.remove(id);
   }
}
