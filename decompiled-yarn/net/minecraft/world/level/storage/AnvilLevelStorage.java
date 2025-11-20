package net.minecraft.world.level.storage;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.storage.RegionFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilLevelStorage {
   private static final Logger LOGGER = LogManager.getLogger();

   static boolean convertLevel(LevelStorage.Session _snowman, ProgressListener _snowman) {
      _snowman.progressStagePercentage(0);
      List<File> _snowmanxx = Lists.newArrayList();
      List<File> _snowmanxxx = Lists.newArrayList();
      List<File> _snowmanxxxx = Lists.newArrayList();
      File _snowmanxxxxx = _snowman.getWorldDirectory(World.OVERWORLD);
      File _snowmanxxxxxx = _snowman.getWorldDirectory(World.NETHER);
      File _snowmanxxxxxxx = _snowman.getWorldDirectory(World.END);
      LOGGER.info("Scanning folders...");
      addRegionFiles(_snowmanxxxxx, _snowmanxx);
      if (_snowmanxxxxxx.exists()) {
         addRegionFiles(_snowmanxxxxxx, _snowmanxxx);
      }

      if (_snowmanxxxxxxx.exists()) {
         addRegionFiles(_snowmanxxxxxxx, _snowmanxxxx);
      }

      int _snowmanxxxxxxxx = _snowmanxx.size() + _snowmanxxx.size() + _snowmanxxxx.size();
      LOGGER.info("Total conversion count is {}", _snowmanxxxxxxxx);
      DynamicRegistryManager.Impl _snowmanxxxxxxxxx = DynamicRegistryManager.create();
      RegistryOps<Tag> _snowmanxxxxxxxxxx = RegistryOps.of(NbtOps.INSTANCE, ResourceManager.Empty.INSTANCE, _snowmanxxxxxxxxx);
      SaveProperties _snowmanxxxxxxxxxxx = _snowman.readLevelProperties(_snowmanxxxxxxxxxx, DataPackSettings.SAFE_MODE);
      long _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx != null ? _snowmanxxxxxxxxxxx.getGeneratorOptions().getSeed() : 0L;
      Registry<Biome> _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx.get(Registry.BIOME_KEY);
      BiomeSource _snowmanxxxxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxxx != null && _snowmanxxxxxxxxxxx.getGeneratorOptions().isFlatWorld()) {
         _snowmanxxxxxxxxxxxxxx = new FixedBiomeSource(_snowmanxxxxxxxxxxxxx.getOrThrow(BiomeKeys.PLAINS));
      } else {
         _snowmanxxxxxxxxxxxxxx = new VanillaLayeredBiomeSource(_snowmanxxxxxxxxxxxx, false, false, _snowmanxxxxxxxxxxxxx);
      }

      convertRegions(_snowmanxxxxxxxxx, new File(_snowmanxxxxx, "region"), _snowmanxx, _snowmanxxxxxxxxxxxxxx, 0, _snowmanxxxxxxxx, _snowman);
      convertRegions(
         _snowmanxxxxxxxxx, new File(_snowmanxxxxxx, "region"), _snowmanxxx, new FixedBiomeSource(_snowmanxxxxxxxxxxxxx.getOrThrow(BiomeKeys.NETHER_WASTES)), _snowmanxx.size(), _snowmanxxxxxxxx, _snowman
      );
      convertRegions(
         _snowmanxxxxxxxxx,
         new File(_snowmanxxxxxxx, "region"),
         _snowmanxxxx,
         new FixedBiomeSource(_snowmanxxxxxxxxxxxxx.getOrThrow(BiomeKeys.THE_END)),
         _snowmanxx.size() + _snowmanxxx.size(),
         _snowmanxxxxxxxx,
         _snowman
      );
      makeMcrLevelDatBackup(_snowman);
      _snowman.backupLevelDataFile(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx);
      return true;
   }

   private static void makeMcrLevelDatBackup(LevelStorage.Session _snowman) {
      File _snowmanx = _snowman.getDirectory(WorldSavePath.LEVEL_DAT).toFile();
      if (!_snowmanx.exists()) {
         LOGGER.warn("Unable to create level.dat_mcr backup");
      } else {
         File _snowmanxx = new File(_snowmanx.getParent(), "level.dat_mcr");
         if (!_snowmanx.renameTo(_snowmanxx)) {
            LOGGER.warn("Unable to create level.dat_mcr backup");
         }
      }
   }

   private static void convertRegions(DynamicRegistryManager.Impl _snowman, File _snowman, Iterable<File> _snowman, BiomeSource _snowman, int _snowman, int _snowman, ProgressListener _snowman) {
      for (File _snowmanxxxxxxx : _snowman) {
         convertRegion(_snowman, _snowman, _snowmanxxxxxxx, _snowman, _snowman, _snowman, _snowman);
         _snowman++;
         int _snowmanxxxxxxxx = (int)Math.round(100.0 * (double)_snowman / (double)_snowman);
         _snowman.progressStagePercentage(_snowmanxxxxxxxx);
      }
   }

   private static void convertRegion(DynamicRegistryManager.Impl _snowman, File _snowman, File _snowman, BiomeSource _snowman, int _snowman, int _snowman, ProgressListener _snowman) {
      String _snowmanxxxxxxx = _snowman.getName();

      try (
         RegionFile _snowmanxxxxxxxx = new RegionFile(_snowman, _snowman, true);
         RegionFile _snowmanxxxxxxxxx = new RegionFile(new File(_snowman, _snowmanxxxxxxx.substring(0, _snowmanxxxxxxx.length() - ".mcr".length()) + ".mca"), _snowman, true);
      ) {
         for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 32; _snowmanxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < 32; _snowmanxxxxxxxxxxx++) {
               ChunkPos _snowmanxxxxxxxxxxxx = new ChunkPos(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
               if (_snowmanxxxxxxxx.hasChunk(_snowmanxxxxxxxxxxxx) && !_snowmanxxxxxxxxx.hasChunk(_snowmanxxxxxxxxxxxx)) {
                  CompoundTag _snowmanxxxxxxxxxxxxx;
                  try (DataInputStream _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxx.getChunkInputStream(_snowmanxxxxxxxxxxxx)) {
                     if (_snowmanxxxxxxxxxxxxxx == null) {
                        LOGGER.warn("Failed to fetch input stream for chunk {}", _snowmanxxxxxxxxxxxx);
                        continue;
                     }

                     _snowmanxxxxxxxxxxxxx = NbtIo.read(_snowmanxxxxxxxxxxxxxx);
                  } catch (IOException var107) {
                     LOGGER.warn("Failed to read data for chunk {}", _snowmanxxxxxxxxxxxx, var107);
                     continue;
                  }

                  CompoundTag _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getCompound("Level");
                  AlphaChunkIo.AlphaChunk _snowmanxxxxxxxxxxxxxxx = AlphaChunkIo.readAlphaChunk(_snowmanxxxxxxxxxxxxxx);
                  CompoundTag _snowmanxxxxxxxxxxxxxxxx = new CompoundTag();
                  CompoundTag _snowmanxxxxxxxxxxxxxxxxx = new CompoundTag();
                  _snowmanxxxxxxxxxxxxxxxx.put("Level", _snowmanxxxxxxxxxxxxxxxxx);
                  AlphaChunkIo.convertAlphaChunk(_snowman, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowman);

                  try (DataOutputStream _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx.getChunkOutputStream(_snowmanxxxxxxxxxxxx)) {
                     NbtIo.write(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
                  }
               }
            }

            int _snowmanxxxxxxxxxxxx = (int)Math.round(100.0 * (double)(_snowman * 1024) / (double)(_snowman * 1024));
            int _snowmanxxxxxxxxxxxxx = (int)Math.round(100.0 * (double)((_snowmanxxxxxxxxxx + 1) * 32 + _snowman * 1024) / (double)(_snowman * 1024));
            if (_snowmanxxxxxxxxxxxxx > _snowmanxxxxxxxxxxxx) {
               _snowman.progressStagePercentage(_snowmanxxxxxxxxxxxxx);
            }
         }
      } catch (IOException var112) {
         LOGGER.error("Failed to upgrade region file {}", _snowman, var112);
      }
   }

   private static void addRegionFiles(File _snowman, Collection<File> _snowman) {
      File _snowmanxx = new File(_snowman, "region");
      File[] _snowmanxxx = _snowmanxx.listFiles((_snowmanxxxx, _snowmanxxxxx) -> _snowmanxxxxx.endsWith(".mcr"));
      if (_snowmanxxx != null) {
         Collections.addAll(_snowman, _snowmanxxx);
      }
   }
}
