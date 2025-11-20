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
      this.updateThread.setUncaughtExceptionHandler((_snowman, _snowmanx) -> {
         LOGGER.error("Error upgrading world", _snowmanx);
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
      Builder<RegistryKey<World>, ListIterator<ChunkPos>> _snowman = ImmutableMap.builder();
      UnmodifiableIterator var2 = this.worlds.iterator();

      while (var2.hasNext()) {
         RegistryKey<World> _snowmanx = (RegistryKey<World>)var2.next();
         List<ChunkPos> _snowmanxx = this.getChunkPositions(_snowmanx);
         _snowman.put(_snowmanx, _snowmanxx.listIterator());
         this.totalChunkCount = this.totalChunkCount + _snowmanxx.size();
      }

      if (this.totalChunkCount == 0) {
         this.done = true;
      } else {
         float _snowmanx = (float)this.totalChunkCount;
         ImmutableMap<RegistryKey<World>, ListIterator<ChunkPos>> _snowmanxx = _snowman.build();
         Builder<RegistryKey<World>, VersionedChunkStorage> _snowmanxxx = ImmutableMap.builder();
         UnmodifiableIterator var5 = this.worlds.iterator();

         while (var5.hasNext()) {
            RegistryKey<World> _snowmanxxxx = (RegistryKey<World>)var5.next();
            File _snowmanxxxxx = this.session.getWorldDirectory(_snowmanxxxx);
            _snowmanxxx.put(_snowmanxxxx, new VersionedChunkStorage(new File(_snowmanxxxxx, "region"), this.dataFixer, true));
         }

         ImmutableMap<RegistryKey<World>, VersionedChunkStorage> _snowmanxxxx = _snowmanxxx.build();
         long _snowmanxxxxx = Util.getMeasuringTimeMs();
         this.status = new TranslatableText("optimizeWorld.stage.upgrading");

         while (this.keepUpgradingChunks) {
            boolean _snowmanxxxxxx = false;
            float _snowmanxxxxxxx = 0.0F;
            UnmodifiableIterator var10 = this.worlds.iterator();

            while (var10.hasNext()) {
               RegistryKey<World> _snowmanxxxxxxxx = (RegistryKey<World>)var10.next();
               ListIterator<ChunkPos> _snowmanxxxxxxxxx = (ListIterator<ChunkPos>)_snowmanxx.get(_snowmanxxxxxxxx);
               VersionedChunkStorage _snowmanxxxxxxxxxx = (VersionedChunkStorage)_snowmanxxxx.get(_snowmanxxxxxxxx);
               if (_snowmanxxxxxxxxx.hasNext()) {
                  ChunkPos _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.next();
                  boolean _snowmanxxxxxxxxxxxx = false;

                  try {
                     CompoundTag _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.getNbt(_snowmanxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxx != null) {
                        int _snowmanxxxxxxxxxxxxxx = VersionedChunkStorage.getDataVersion(_snowmanxxxxxxxxxxxxx);
                        CompoundTag _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.updateChunkTag(_snowmanxxxxxxxx, () -> this.persistentStateManager, _snowmanxxxxxxxxxxxxx);
                        CompoundTag _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.getCompound("Level");
                        ChunkPos _snowmanxxxxxxxxxxxxxxxxx = new ChunkPos(_snowmanxxxxxxxxxxxxxxxx.getInt("xPos"), _snowmanxxxxxxxxxxxxxxxx.getInt("zPos"));
                        if (!_snowmanxxxxxxxxxxxxxxxxx.equals(_snowmanxxxxxxxxxxx)) {
                           LOGGER.warn("Chunk {} has invalid position {}", _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
                        }

                        boolean _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx < SharedConstants.getGameVersion().getWorldVersion();
                        if (this.eraseCache) {
                           _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxxxx.contains("Heightmaps");
                           _snowmanxxxxxxxxxxxxxxxx.remove("Heightmaps");
                           _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxxxx.contains("isLightOn");
                           _snowmanxxxxxxxxxxxxxxxx.remove("isLightOn");
                        }

                        if (_snowmanxxxxxxxxxxxxxxxxxx) {
                           _snowmanxxxxxxxxxx.setTagAt(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                           _snowmanxxxxxxxxxxxx = true;
                        }
                     }
                  } catch (CrashException var23) {
                     Throwable _snowmanxxxxxxxxxxxxxxxxxxx = var23.getCause();
                     if (!(_snowmanxxxxxxxxxxxxxxxxxxx instanceof IOException)) {
                        throw var23;
                     }

                     LOGGER.error("Error upgrading chunk {}", _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
                  } catch (IOException var24) {
                     LOGGER.error("Error upgrading chunk {}", _snowmanxxxxxxxxxxx, var24);
                  }

                  if (_snowmanxxxxxxxxxxxx) {
                     this.upgradedChunkCount++;
                  } else {
                     this.skippedChunkCount++;
                  }

                  _snowmanxxxxxx = true;
               }

               float _snowmanxxxxxxxxxxx = (float)_snowmanxxxxxxxxx.nextIndex() / _snowmanx;
               this.dimensionProgress.put(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx);
               _snowmanxxxxxxx += _snowmanxxxxxxxxxxx;
            }

            this.progress = _snowmanxxxxxxx;
            if (!_snowmanxxxxxx) {
               this.keepUpgradingChunks = false;
            }
         }

         this.status = new TranslatableText("optimizeWorld.stage.finished");
         UnmodifiableIterator var31 = _snowmanxxxx.values().iterator();

         while (var31.hasNext()) {
            VersionedChunkStorage _snowmanxxxxxx = (VersionedChunkStorage)var31.next();

            try {
               _snowmanxxxxxx.close();
            } catch (IOException var22) {
               LOGGER.error("Error upgrading chunk", var22);
            }
         }

         this.persistentStateManager.save();
         _snowmanxxxxx = Util.getMeasuringTimeMs() - _snowmanxxxxx;
         LOGGER.info("World optimizaton finished after {} ms", _snowmanxxxxx);
         this.done = true;
      }
   }

   private List<ChunkPos> getChunkPositions(RegistryKey<World> _snowman) {
      File _snowmanx = this.session.getWorldDirectory(_snowman);
      File _snowmanxx = new File(_snowmanx, "region");
      File[] _snowmanxxx = _snowmanxx.listFiles((_snowmanxxxx, _snowmanxxxxx) -> _snowmanxxxxx.endsWith(".mca"));
      if (_snowmanxxx == null) {
         return ImmutableList.of();
      } else {
         List<ChunkPos> _snowmanxxxx = Lists.newArrayList();

         for (File _snowmanxxxxx : _snowmanxxx) {
            Matcher _snowmanxxxxxx = REGION_FILE_PATTERN.matcher(_snowmanxxxxx.getName());
            if (_snowmanxxxxxx.matches()) {
               int _snowmanxxxxxxx = Integer.parseInt(_snowmanxxxxxx.group(1)) << 5;
               int _snowmanxxxxxxxx = Integer.parseInt(_snowmanxxxxxx.group(2)) << 5;

               try (RegionFile _snowmanxxxxxxxxx = new RegionFile(_snowmanxxxxx, _snowmanxx, true)) {
                  for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 32; _snowmanxxxxxxxxxx++) {
                     for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < 32; _snowmanxxxxxxxxxxx++) {
                        ChunkPos _snowmanxxxxxxxxxxxx = new ChunkPos(_snowmanxxxxxxxxxx + _snowmanxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxx);
                        if (_snowmanxxxxxxxxx.isChunkValid(_snowmanxxxxxxxxxxxx)) {
                           _snowmanxxxx.add(_snowmanxxxxxxxxxxxx);
                        }
                     }
                  }
               } catch (Throwable var28) {
               }
            }
         }

         return _snowmanxxxx;
      }
   }

   public boolean isDone() {
      return this.done;
   }

   public ImmutableSet<RegistryKey<World>> method_28304() {
      return this.worlds;
   }

   public float getProgress(RegistryKey<World> _snowman) {
      return this.dimensionProgress.getFloat(_snowman);
   }

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
