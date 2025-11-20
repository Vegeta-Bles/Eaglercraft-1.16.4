/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.level.storage;

import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.level.storage.AlphaChunkIo;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.storage.RegionFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilLevelStorage {
    private static final Logger LOGGER = LogManager.getLogger();

    static boolean convertLevel(LevelStorage.Session session, ProgressListener progressListener) {
        progressListener.progressStagePercentage(0);
        ArrayList arrayList = Lists.newArrayList();
        _snowman = Lists.newArrayList();
        _snowman = Lists.newArrayList();
        File _snowman2 = session.getWorldDirectory(World.OVERWORLD);
        File _snowman3 = session.getWorldDirectory(World.NETHER);
        File _snowman4 = session.getWorldDirectory(World.END);
        LOGGER.info("Scanning folders...");
        AnvilLevelStorage.addRegionFiles(_snowman2, arrayList);
        if (_snowman3.exists()) {
            AnvilLevelStorage.addRegionFiles(_snowman3, _snowman);
        }
        if (_snowman4.exists()) {
            AnvilLevelStorage.addRegionFiles(_snowman4, _snowman);
        }
        int _snowman5 = arrayList.size() + _snowman.size() + _snowman.size();
        LOGGER.info("Total conversion count is {}", (Object)_snowman5);
        DynamicRegistryManager.Impl _snowman6 = DynamicRegistryManager.create();
        RegistryOps<Tag> _snowman7 = RegistryOps.of(NbtOps.INSTANCE, ResourceManager.Empty.INSTANCE, _snowman6);
        SaveProperties _snowman8 = session.readLevelProperties(_snowman7, DataPackSettings.SAFE_MODE);
        long _snowman9 = _snowman8 != null ? _snowman8.getGeneratorOptions().getSeed() : 0L;
        MutableRegistry<Biome> _snowman10 = _snowman6.get(Registry.BIOME_KEY);
        BiomeSource _snowman11 = _snowman8 != null && _snowman8.getGeneratorOptions().isFlatWorld() ? new FixedBiomeSource(_snowman10.getOrThrow(BiomeKeys.PLAINS)) : new VanillaLayeredBiomeSource(_snowman9, false, false, _snowman10);
        AnvilLevelStorage.convertRegions(_snowman6, new File(_snowman2, "region"), arrayList, _snowman11, 0, _snowman5, progressListener);
        AnvilLevelStorage.convertRegions(_snowman6, new File(_snowman3, "region"), _snowman, new FixedBiomeSource(_snowman10.getOrThrow(BiomeKeys.NETHER_WASTES)), arrayList.size(), _snowman5, progressListener);
        AnvilLevelStorage.convertRegions(_snowman6, new File(_snowman4, "region"), _snowman, new FixedBiomeSource(_snowman10.getOrThrow(BiomeKeys.THE_END)), arrayList.size() + _snowman.size(), _snowman5, progressListener);
        AnvilLevelStorage.makeMcrLevelDatBackup(session);
        session.backupLevelDataFile(_snowman6, _snowman8);
        return true;
    }

    private static void makeMcrLevelDatBackup(LevelStorage.Session session) {
        File file = session.getDirectory(WorldSavePath.LEVEL_DAT).toFile();
        if (!file.exists()) {
            LOGGER.warn("Unable to create level.dat_mcr backup");
            return;
        }
        _snowman = new File(file.getParent(), "level.dat_mcr");
        if (!file.renameTo(_snowman)) {
            LOGGER.warn("Unable to create level.dat_mcr backup");
        }
    }

    private static void convertRegions(DynamicRegistryManager.Impl impl, File file, Iterable<File> iterable, BiomeSource biomeSource, int n, int n2, ProgressListener progressListener) {
        for (File file2 : iterable) {
            AnvilLevelStorage.convertRegion(impl, file, file2, biomeSource, n, n2, progressListener);
            int n3 = (int)Math.round(100.0 * (double)(++n) / (double)n2);
            progressListener.progressStagePercentage(n3);
        }
    }

    private static void convertRegion(DynamicRegistryManager.Impl impl, File file, File file2, BiomeSource biomeSource, int n3, int n2, ProgressListener progressListener) {
        String string = file2.getName();
        try (RegionFile regionFile = new RegionFile(file2, file, true);){
            regionFile2 = new RegionFile(new File(file, string.substring(0, string.length() - ".mcr".length()) + ".mca"), file, true);
            Throwable throwable = null;
            try {
                for (int i = 0; i < 32; ++i) {
                    int n3;
                    for (_snowman = 0; _snowman < 32; ++_snowman) {
                        Object object;
                        ChunkPos chunkPos = new ChunkPos(i, _snowman);
                        if (!regionFile.hasChunk(chunkPos) || regionFile2.hasChunk(chunkPos)) continue;
                        try {
                            object = regionFile.getChunkInputStream(chunkPos);
                            Throwable throwable2 = null;
                            try {
                                if (object == null) {
                                    LOGGER.warn("Failed to fetch input stream for chunk {}", (Object)chunkPos);
                                    continue;
                                }
                                CompoundTag compoundTag = NbtIo.read((DataInput)object);
                            }
                            catch (Throwable throwable3) {
                                throwable2 = throwable3;
                                throw throwable3;
                            }
                            finally {
                                if (object != null) {
                                    if (throwable2 != null) {
                                        try {
                                            ((FilterInputStream)object).close();
                                        }
                                        catch (Throwable throwable4) {
                                            throwable2.addSuppressed(throwable4);
                                        }
                                    } else {
                                        ((FilterInputStream)object).close();
                                    }
                                }
                            }
                        }
                        catch (IOException iOException) {
                            LOGGER.warn("Failed to read data for chunk {}", (Object)chunkPos, (Object)iOException);
                            continue;
                        }
                        object = compoundTag.getCompound("Level");
                        AlphaChunkIo.AlphaChunk _snowman2 = AlphaChunkIo.readAlphaChunk((CompoundTag)object);
                        CompoundTag _snowman3 = new CompoundTag();
                        CompoundTag _snowman4 = new CompoundTag();
                        _snowman3.put("Level", _snowman4);
                        AlphaChunkIo.convertAlphaChunk(impl, _snowman2, _snowman4, biomeSource);
                        try (DataOutputStream _snowman5 = regionFile2.getChunkOutputStream(chunkPos);){
                            NbtIo.write(_snowman3, (DataOutput)_snowman5);
                            continue;
                        }
                    }
                    _snowman = (int)Math.round(100.0 * (double)(n3 * 1024) / (double)(n2 * 1024));
                    _snowman = (int)Math.round(100.0 * (double)((i + 1) * 32 + n3 * 1024) / (double)(n2 * 1024));
                    if (_snowman <= _snowman) continue;
                    progressListener.progressStagePercentage(_snowman);
                }
            }
            catch (Throwable throwable5) {
                throwable = throwable5;
                throw throwable5;
            }
            finally {
                RegionFile regionFile2;
                if (regionFile2 != null) {
                    if (throwable != null) {
                        try {
                            regionFile2.close();
                        }
                        catch (Throwable throwable6) {
                            throwable.addSuppressed(throwable6);
                        }
                    } else {
                        regionFile2.close();
                    }
                }
            }
        }
        catch (IOException iOException) {
            LOGGER.error("Failed to upgrade region file {}", (Object)file2, (Object)iOException);
        }
    }

    private static void addRegionFiles(File file2, Collection<File> collection) {
        File file3 = new File(file2, "region");
        File[] _snowman2 = file3.listFiles((file, string) -> string.endsWith(".mcr"));
        if (_snowman2 != null) {
            Collections.addAll(collection, _snowman2);
        }
    }
}

