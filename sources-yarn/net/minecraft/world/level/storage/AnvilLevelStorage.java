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

   static boolean convertLevel(LevelStorage.Session arg, ProgressListener arg2) {
      arg2.progressStagePercentage(0);
      List<File> list = Lists.newArrayList();
      List<File> list2 = Lists.newArrayList();
      List<File> list3 = Lists.newArrayList();
      File file = arg.getWorldDirectory(World.OVERWORLD);
      File file2 = arg.getWorldDirectory(World.NETHER);
      File file3 = arg.getWorldDirectory(World.END);
      LOGGER.info("Scanning folders...");
      addRegionFiles(file, list);
      if (file2.exists()) {
         addRegionFiles(file2, list2);
      }

      if (file3.exists()) {
         addRegionFiles(file3, list3);
      }

      int i = list.size() + list2.size() + list3.size();
      LOGGER.info("Total conversion count is {}", i);
      DynamicRegistryManager.Impl lv = DynamicRegistryManager.create();
      RegistryOps<Tag> lv2 = RegistryOps.of(NbtOps.INSTANCE, ResourceManager.Empty.INSTANCE, lv);
      SaveProperties lv3 = arg.readLevelProperties(lv2, DataPackSettings.SAFE_MODE);
      long l = lv3 != null ? lv3.getGeneratorOptions().getSeed() : 0L;
      Registry<Biome> lv4 = lv.get(Registry.BIOME_KEY);
      BiomeSource lv5;
      if (lv3 != null && lv3.getGeneratorOptions().isFlatWorld()) {
         lv5 = new FixedBiomeSource(lv4.getOrThrow(BiomeKeys.PLAINS));
      } else {
         lv5 = new VanillaLayeredBiomeSource(l, false, false, lv4);
      }

      convertRegions(lv, new File(file, "region"), list, lv5, 0, i, arg2);
      convertRegions(lv, new File(file2, "region"), list2, new FixedBiomeSource(lv4.getOrThrow(BiomeKeys.NETHER_WASTES)), list.size(), i, arg2);
      convertRegions(lv, new File(file3, "region"), list3, new FixedBiomeSource(lv4.getOrThrow(BiomeKeys.THE_END)), list.size() + list2.size(), i, arg2);
      makeMcrLevelDatBackup(arg);
      arg.backupLevelDataFile(lv, lv3);
      return true;
   }

   private static void makeMcrLevelDatBackup(LevelStorage.Session arg) {
      File file = arg.getDirectory(WorldSavePath.LEVEL_DAT).toFile();
      if (!file.exists()) {
         LOGGER.warn("Unable to create level.dat_mcr backup");
      } else {
         File file2 = new File(file.getParent(), "level.dat_mcr");
         if (!file.renameTo(file2)) {
            LOGGER.warn("Unable to create level.dat_mcr backup");
         }
      }
   }

   private static void convertRegions(
      DynamicRegistryManager.Impl arg, File file, Iterable<File> iterable, BiomeSource arg2, int i, int j, ProgressListener arg3
   ) {
      for (File file2 : iterable) {
         convertRegion(arg, file, file2, arg2, i, j, arg3);
         i++;
         int k = (int)Math.round(100.0 * (double)i / (double)j);
         arg3.progressStagePercentage(k);
      }
   }

   private static void convertRegion(DynamicRegistryManager.Impl arg, File file, File file2, BiomeSource arg2, int i, int j, ProgressListener arg3) {
      String string = file2.getName();

      try (
         RegionFile lv = new RegionFile(file2, file, true);
         RegionFile lv2 = new RegionFile(new File(file, string.substring(0, string.length() - ".mcr".length()) + ".mca"), file, true);
      ) {
         for (int k = 0; k < 32; k++) {
            for (int l = 0; l < 32; l++) {
               ChunkPos lv3 = new ChunkPos(k, l);
               if (lv.hasChunk(lv3) && !lv2.hasChunk(lv3)) {
                  CompoundTag lv4;
                  try (DataInputStream dataInputStream = lv.getChunkInputStream(lv3)) {
                     if (dataInputStream == null) {
                        LOGGER.warn("Failed to fetch input stream for chunk {}", lv3);
                        continue;
                     }

                     lv4 = NbtIo.read(dataInputStream);
                  } catch (IOException var107) {
                     LOGGER.warn("Failed to read data for chunk {}", lv3, var107);
                     continue;
                  }

                  CompoundTag lv7 = lv4.getCompound("Level");
                  AlphaChunkIo.AlphaChunk lv8 = AlphaChunkIo.readAlphaChunk(lv7);
                  CompoundTag lv9 = new CompoundTag();
                  CompoundTag lv10 = new CompoundTag();
                  lv9.put("Level", lv10);
                  AlphaChunkIo.convertAlphaChunk(arg, lv8, lv10, arg2);

                  try (DataOutputStream dataOutputStream = lv2.getChunkOutputStream(lv3)) {
                     NbtIo.write(lv9, dataOutputStream);
                  }
               }
            }

            int m = (int)Math.round(100.0 * (double)(i * 1024) / (double)(j * 1024));
            int n = (int)Math.round(100.0 * (double)((k + 1) * 32 + i * 1024) / (double)(j * 1024));
            if (n > m) {
               arg3.progressStagePercentage(n);
            }
         }
      } catch (IOException var112) {
         LOGGER.error("Failed to upgrade region file {}", file2, var112);
      }
   }

   private static void addRegionFiles(File file, Collection<File> collection) {
      File file2 = new File(file, "region");
      File[] files = file2.listFiles((filex, string) -> string.endsWith(".mcr"));
      if (files != null) {
         Collections.addAll(collection, files);
      }
   }
}
