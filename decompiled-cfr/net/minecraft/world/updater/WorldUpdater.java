/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  com.mojang.datafixers.DataFixer
 *  it.unimi.dsi.fastutil.objects.Object2FloatMap
 *  it.unimi.dsi.fastutil.objects.Object2FloatMaps
 *  it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.updater;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private final Object2FloatMap<RegistryKey<World>> dimensionProgress = Object2FloatMaps.synchronize((Object2FloatMap)new Object2FloatOpenCustomHashMap(Util.identityHashStrategy()));
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
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    private void updateWorld() {
        ImmutableMap.Builder _snowman3;
        RegistryKey _snowman22;
        this.totalChunkCount = 0;
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (RegistryKey _snowman22 : this.worlds) {
            _snowman3 = this.getChunkPositions(_snowman22);
            builder.put((Object)_snowman22, _snowman3.listIterator());
            this.totalChunkCount += _snowman3.size();
        }
        if (this.totalChunkCount == 0) {
            this.done = true;
            return;
        }
        float f = this.totalChunkCount;
        _snowman22 = builder.build();
        _snowman3 = ImmutableMap.builder();
        for (RegistryKey registryKey : this.worlds) {
            File file = this.session.getWorldDirectory(registryKey);
            _snowman3.put((Object)registryKey, (Object)new VersionedChunkStorage(new File(file, "region"), this.dataFixer, true));
        }
        ImmutableMap immutableMap = _snowman3.build();
        long _snowman4 = Util.getMeasuringTimeMs();
        this.status = new TranslatableText("optimizeWorld.stage.upgrading");
        while (this.keepUpgradingChunks) {
            boolean bl = false;
            float _snowman5 = 0.0f;
            for (RegistryKey registryKey : this.worlds) {
                ListIterator listIterator = (ListIterator)_snowman22.get(registryKey);
                VersionedChunkStorage _snowman6 = (VersionedChunkStorage)immutableMap.get((Object)registryKey);
                if (listIterator.hasNext()) {
                    ChunkPos chunkPos = (ChunkPos)listIterator.next();
                    boolean _snowman7 = false;
                    try {
                        CompoundTag compoundTag = _snowman6.getNbt(chunkPos);
                        if (compoundTag != null) {
                            boolean bl2;
                            int n = VersionedChunkStorage.getDataVersion(compoundTag);
                            CompoundTag _snowman8 = _snowman6.updateChunkTag(registryKey, () -> this.persistentStateManager, compoundTag);
                            CompoundTag _snowman9 = _snowman8.getCompound("Level");
                            ChunkPos _snowman10 = new ChunkPos(_snowman9.getInt("xPos"), _snowman9.getInt("zPos"));
                            if (!_snowman10.equals(chunkPos)) {
                                LOGGER.warn("Chunk {} has invalid position {}", (Object)chunkPos, (Object)_snowman10);
                            }
                            boolean bl3 = bl2 = n < SharedConstants.getGameVersion().getWorldVersion();
                            if (this.eraseCache) {
                                bl2 = bl2 || _snowman9.contains("Heightmaps");
                                _snowman9.remove("Heightmaps");
                                bl2 = bl2 || _snowman9.contains("isLightOn");
                                _snowman9.remove("isLightOn");
                            }
                            if (bl2) {
                                _snowman6.setTagAt(chunkPos, _snowman8);
                                _snowman7 = true;
                            }
                        }
                    }
                    catch (CrashException crashException) {
                        Throwable throwable = crashException.getCause();
                        if (throwable instanceof IOException) {
                            LOGGER.error("Error upgrading chunk {}", (Object)chunkPos, (Object)throwable);
                        }
                        throw crashException;
                    }
                    catch (IOException iOException) {
                        LOGGER.error("Error upgrading chunk {}", (Object)chunkPos, (Object)iOException);
                    }
                    if (_snowman7) {
                        ++this.upgradedChunkCount;
                    } else {
                        ++this.skippedChunkCount;
                    }
                    bl = true;
                }
                float _snowman11 = (float)listIterator.nextIndex() / f;
                this.dimensionProgress.put((Object)registryKey, _snowman11);
                _snowman5 += _snowman11;
            }
            this.progress = _snowman5;
            if (bl) continue;
            this.keepUpgradingChunks = false;
        }
        this.status = new TranslatableText("optimizeWorld.stage.finished");
        for (VersionedChunkStorage versionedChunkStorage : immutableMap.values()) {
            try {
                versionedChunkStorage.close();
            }
            catch (IOException iOException) {
                LOGGER.error("Error upgrading chunk", (Throwable)iOException);
            }
        }
        this.persistentStateManager.save();
        _snowman4 = Util.getMeasuringTimeMs() - _snowman4;
        LOGGER.info("World optimizaton finished after {} ms", (Object)_snowman4);
        this.done = true;
    }

    private List<ChunkPos> getChunkPositions(RegistryKey<World> registryKey) {
        File file2 = this.session.getWorldDirectory(registryKey);
        _snowman = new File(file2, "region");
        File[] _snowman2 = _snowman.listFiles((file, string) -> string.endsWith(".mca"));
        if (_snowman2 == null) {
            return ImmutableList.of();
        }
        ArrayList _snowman3 = Lists.newArrayList();
        for (File file3 : _snowman2) {
            Matcher matcher = REGION_FILE_PATTERN.matcher(file3.getName());
            if (!matcher.matches()) continue;
            int _snowman4 = Integer.parseInt(matcher.group(1)) << 5;
            int _snowman5 = Integer.parseInt(matcher.group(2)) << 5;
            try (RegionFile regionFile = new RegionFile(file3, _snowman, true);){
                for (int i = 0; i < 32; ++i) {
                    for (_snowman = 0; _snowman < 32; ++_snowman) {
                        ChunkPos chunkPos = new ChunkPos(i + _snowman4, _snowman + _snowman5);
                        if (!regionFile.isChunkValid(chunkPos)) continue;
                        _snowman3.add(chunkPos);
                    }
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return _snowman3;
    }

    public boolean isDone() {
        return this.done;
    }

    public ImmutableSet<RegistryKey<World>> method_28304() {
        return this.worlds;
    }

    public float getProgress(RegistryKey<World> registryKey) {
        return this.dimensionProgress.getFloat(registryKey);
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

