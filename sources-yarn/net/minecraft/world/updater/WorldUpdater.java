package net.minecraft.world.updater;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.storage.RegionFile;
import net.minecraft.world.storage.VersionedChunkStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldUpdater {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ThreadFactory UPDATE_THREAD_FACTORY = new ThreadFactoryBuilder().setDaemon(true).build();
   private final ImmutableSet<RegistryKey<World>> worlds;
   private final boolean eraseCache;
   private final LevelStorage.Session session;
   private final Thread updateThread;
   private final DataFixer dataFixer;
   private volatile boolean keepUpgradingChunks = true;
   private volatile boolean done;
   private volatile float progress;
   private volatile int totalChunkCount;
   private volatile int upgradedChunkCount;
   private volatile int skippedChunkCount;
   private final Object2FloatMap<RegistryKey<World>> dimensionProgress = Object2FloatMaps.synchronize(
      new Object2FloatOpenCustomHashMap(Util.identityHashStrategy())
   );
   private volatile Text status = new TranslatableText("optimizeWorld.stage.counting");
   private static final Pattern REGION_FILE_PATTERN = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
   private final PersistentStateManager persistentStateManager;

   public WorldUpdater(LevelStorage.Session session, DataFixer dataFixer, ImmutableSet<RegistryKey<World>> worlds, boolean eraseCache) {
      this.worlds = worlds;
      this.eraseCache = eraseCache;
      this.dataFixer = dataFixer;
      this.session = session;
      this.persistentStateManager = new PersistentStateManager(new File(this.session.getWorldDirectory(World.OVERWORLD), "data"), dataFixer);
      this.updateThread = UPDATE_THREAD_FACTORY.newThread(this::updateWorld);
      this.updateThread.setUncaughtExceptionHandler((thread, throwable) -> {
         LOGGER.error("Error upgrading world", throwable);
         this.status = new TranslatableText("optimizeWorld.stage.failed");
         this.done = true;
      });
      this.updateThread.start();
   }

   public void cancel() {
      this.keepUpgradingChunks = false;

      try {
         this.updateThread.join();
      } catch (InterruptedException var2) {
      }
   }

   private void updateWorld() {
      this.totalChunkCount = 0;
      Builder<RegistryKey<World>, ListIterator<ChunkPos>> builder = ImmutableMap.builder();
      UnmodifiableIterator f = this.worlds.iterator();

      while (f.hasNext()) {
         RegistryKey<World> lv = (RegistryKey<World>)f.next();
         List<ChunkPos> list = this.getChunkPositions(lv);
         builder.put(lv, list.listIterator());
         this.totalChunkCount = this.totalChunkCount + list.size();
      }

      if (this.totalChunkCount == 0) {
         this.done = true;
      } else {
         float fx = (float)this.totalChunkCount;
         ImmutableMap<RegistryKey<World>, ListIterator<ChunkPos>> immutableMap = builder.build();
         Builder<RegistryKey<World>, VersionedChunkStorage> builder2 = ImmutableMap.builder();
         UnmodifiableIterator immutableMap2 = this.worlds.iterator();

         while (immutableMap2.hasNext()) {
            RegistryKey<World> lv2 = (RegistryKey<World>)immutableMap2.next();
            File file = this.session.getWorldDirectory(lv2);
            builder2.put(lv2, new VersionedChunkStorage(new File(file, "region"), this.dataFixer, true));
         }

         ImmutableMap<RegistryKey<World>, VersionedChunkStorage> immutableMap2x = builder2.build();
         long l = Util.getMeasuringTimeMs();
         this.status = new TranslatableText("optimizeWorld.stage.upgrading");

         while (this.keepUpgradingChunks) {
            boolean bl = false;
            float g = 0.0F;
            UnmodifiableIterator iOException2 = this.worlds.iterator();

            while (iOException2.hasNext()) {
               RegistryKey<World> lv3 = (RegistryKey<World>)iOException2.next();
               ListIterator<ChunkPos> listIterator = (ListIterator<ChunkPos>)immutableMap.get(lv3);
               VersionedChunkStorage lv4 = (VersionedChunkStorage)immutableMap2x.get(lv3);
               if (listIterator.hasNext()) {
                  ChunkPos lv5 = listIterator.next();
                  boolean bl2 = false;

                  try {
                     CompoundTag lv6 = lv4.getNbt(lv5);
                     if (lv6 != null) {
                        int i = VersionedChunkStorage.getDataVersion(lv6);
                        CompoundTag lv7 = lv4.updateChunkTag(lv3, () -> this.persistentStateManager, lv6);
                        CompoundTag lv8 = lv7.getCompound("Level");
                        ChunkPos lv9 = new ChunkPos(lv8.getInt("xPos"), lv8.getInt("zPos"));
                        if (!lv9.equals(lv5)) {
                           LOGGER.warn("Chunk {} has invalid position {}", lv5, lv9);
                        }

                        boolean bl3 = i < SharedConstants.getGameVersion().getWorldVersion();
                        if (this.eraseCache) {
                           bl3 = bl3 || lv8.contains("Heightmaps");
                           lv8.remove("Heightmaps");
                           bl3 = bl3 || lv8.contains("isLightOn");
                           lv8.remove("isLightOn");
                        }

                        if (bl3) {
                           lv4.setTagAt(lv5, lv7);
                           bl2 = true;
                        }
                     }
                  } catch (CrashException var23) {
                     Throwable throwable = var23.getCause();
                     if (!(throwable instanceof IOException)) {
                        throw var23;
                     }

                     LOGGER.error("Error upgrading chunk {}", lv5, throwable);
                  } catch (IOException var24) {
                     LOGGER.error("Error upgrading chunk {}", lv5, var24);
                  }

                  if (bl2) {
                     this.upgradedChunkCount++;
                  } else {
                     this.skippedChunkCount++;
                  }

                  bl = true;
               }

               float h = (float)listIterator.nextIndex() / fx;
               this.dimensionProgress.put(lv3, h);
               g += h;
            }

            this.progress = g;
            if (!bl) {
               this.keepUpgradingChunks = false;
            }
         }

         this.status = new TranslatableText("optimizeWorld.stage.finished");
         UnmodifiableIterator var31 = immutableMap2x.values().iterator();

         while (var31.hasNext()) {
            VersionedChunkStorage lv11 = (VersionedChunkStorage)var31.next();

            try {
               lv11.close();
            } catch (IOException var22) {
               LOGGER.error("Error upgrading chunk", var22);
            }
         }

         this.persistentStateManager.save();
         l = Util.getMeasuringTimeMs() - l;
         LOGGER.info("World optimizaton finished after {} ms", l);
         this.done = true;
      }
   }

   private List<ChunkPos> getChunkPositions(RegistryKey<World> arg) {
      File file = this.session.getWorldDirectory(arg);
      File file2 = new File(file, "region");
      File[] files = file2.listFiles((filex, string) -> string.endsWith(".mca"));
      if (files == null) {
         return ImmutableList.of();
      } else {
         List<ChunkPos> list = Lists.newArrayList();

         for (File file3 : files) {
            Matcher matcher = REGION_FILE_PATTERN.matcher(file3.getName());
            if (matcher.matches()) {
               int i = Integer.parseInt(matcher.group(1)) << 5;
               int j = Integer.parseInt(matcher.group(2)) << 5;

               try (RegionFile lv = new RegionFile(file3, file2, true)) {
                  for (int k = 0; k < 32; k++) {
                     for (int l = 0; l < 32; l++) {
                        ChunkPos lv2 = new ChunkPos(k + i, l + j);
                        if (lv.isChunkValid(lv2)) {
                           list.add(lv2);
                        }
                     }
                  }
               } catch (Throwable var28) {
               }
            }
         }

         return list;
      }
   }

   public boolean isDone() {
      return this.done;
   }

   @Environment(EnvType.CLIENT)
   public ImmutableSet<RegistryKey<World>> method_28304() {
      return this.worlds;
   }

   @Environment(EnvType.CLIENT)
   public float getProgress(RegistryKey<World> arg) {
      return this.dimensionProgress.getFloat(arg);
   }

   @Environment(EnvType.CLIENT)
   public float getProgress() {
      return this.progress;
   }

   public int getTotalChunkCount() {
      return this.totalChunkCount;
   }

   public int getUpgradedChunkCount() {
      return this.upgradedChunkCount;
   }

   public int getSkippedChunkCount() {
      return this.skippedChunkCount;
   }

   public Text getStatus() {
      return this.status;
   }
}
