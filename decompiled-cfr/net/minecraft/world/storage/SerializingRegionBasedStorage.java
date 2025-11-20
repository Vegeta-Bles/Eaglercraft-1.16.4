/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.OptionalDynamic
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.storage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.StorageIoWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SerializingRegionBasedStorage<R>
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final StorageIoWorker worker;
    private final Long2ObjectMap<Optional<R>> loadedElements = new Long2ObjectOpenHashMap();
    private final LongLinkedOpenHashSet unsavedElements = new LongLinkedOpenHashSet();
    private final Function<Runnable, Codec<R>> codecFactory;
    private final Function<Runnable, R> factory;
    private final DataFixer dataFixer;
    private final DataFixTypes dataFixType;

    public SerializingRegionBasedStorage(File directory, Function<Runnable, Codec<R>> codecFactory, Function<Runnable, R> factory, DataFixer dataFixer, DataFixTypes dataFixTypes, boolean bl) {
        this.codecFactory = codecFactory;
        this.factory = factory;
        this.dataFixer = dataFixer;
        this.dataFixType = dataFixTypes;
        this.worker = new StorageIoWorker(directory, bl, directory.getName());
    }

    protected void tick(BooleanSupplier shouldKeepTicking) {
        while (!this.unsavedElements.isEmpty() && shouldKeepTicking.getAsBoolean()) {
            ChunkPos chunkPos = ChunkSectionPos.from(this.unsavedElements.firstLong()).toChunkPos();
            this.save(chunkPos);
        }
    }

    @Nullable
    protected Optional<R> getIfLoaded(long pos) {
        return (Optional)this.loadedElements.get(pos);
    }

    protected Optional<R> get(long pos) {
        ChunkSectionPos chunkSectionPos = ChunkSectionPos.from(pos);
        if (this.isPosInvalid(chunkSectionPos)) {
            return Optional.empty();
        }
        Optional<R> _snowman2 = this.getIfLoaded(pos);
        if (_snowman2 != null) {
            return _snowman2;
        }
        this.loadDataAt(chunkSectionPos.toChunkPos());
        _snowman2 = this.getIfLoaded(pos);
        if (_snowman2 == null) {
            throw Util.throwOrPause(new IllegalStateException());
        }
        return _snowman2;
    }

    protected boolean isPosInvalid(ChunkSectionPos pos) {
        return World.isOutOfBuildLimitVertically(ChunkSectionPos.getBlockCoord(pos.getSectionY()));
    }

    protected R getOrCreate(long pos) {
        Optional<R> optional = this.get(pos);
        if (optional.isPresent()) {
            return optional.get();
        }
        R _snowman2 = this.factory.apply(() -> this.onUpdate(pos));
        this.loadedElements.put(pos, Optional.of(_snowman2));
        return _snowman2;
    }

    private void loadDataAt(ChunkPos chunkPos) {
        this.update(chunkPos, NbtOps.INSTANCE, this.loadNbt(chunkPos));
    }

    @Nullable
    private CompoundTag loadNbt(ChunkPos pos) {
        try {
            return this.worker.getNbt(pos);
        }
        catch (IOException iOException) {
            LOGGER.error("Error reading chunk {} data from disk", (Object)pos, (Object)iOException);
            return null;
        }
    }

    private <T> void update(ChunkPos pos, DynamicOps<T> dynamicOps2, @Nullable T data) {
        if (data == null) {
            for (int i = 0; i < 16; ++i) {
                this.loadedElements.put(ChunkSectionPos.from(pos, i).asLong(), Optional.empty());
            }
        } else {
            DynamicOps<T> dynamicOps2;
            Dynamic _snowman2 = new Dynamic(dynamicOps2, data);
            int _snowman3 = SerializingRegionBasedStorage.getDataVersion(_snowman2);
            boolean _snowman4 = _snowman3 != (_snowman = SharedConstants.getGameVersion().getWorldVersion());
            Dynamic _snowman5 = this.dataFixer.update(this.dataFixType.getTypeReference(), _snowman2, _snowman3, _snowman);
            OptionalDynamic _snowman6 = _snowman5.get("Sections");
            for (int i = 0; i < 16; ++i) {
                long l = ChunkSectionPos.from(pos, i).asLong();
                Optional _snowman7 = _snowman6.get(Integer.toString(i)).result().flatMap(dynamic -> this.codecFactory.apply(() -> this.onUpdate(l)).parse(dynamic).resultOrPartial(arg_0 -> ((Logger)LOGGER).error(arg_0)));
                this.loadedElements.put(l, _snowman7);
                _snowman7.ifPresent(object -> {
                    this.onLoad(l);
                    if (_snowman4) {
                        this.onUpdate(l);
                    }
                });
            }
        }
    }

    private void save(ChunkPos chunkPos) {
        Dynamic<Tag> dynamic = this.method_20367(chunkPos, NbtOps.INSTANCE);
        Tag _snowman2 = (Tag)dynamic.getValue();
        if (_snowman2 instanceof CompoundTag) {
            this.worker.setResult(chunkPos, (CompoundTag)_snowman2);
        } else {
            LOGGER.error("Expected compound tag, got {}", (Object)_snowman2);
        }
    }

    private <T> Dynamic<T> method_20367(ChunkPos chunkPos, DynamicOps<T> dynamicOps) {
        HashMap hashMap = Maps.newHashMap();
        for (int i = 0; i < 16; ++i) {
            long l = ChunkSectionPos.from(chunkPos, i).asLong();
            this.unsavedElements.remove(l);
            Optional _snowman2 = (Optional)this.loadedElements.get(l);
            if (_snowman2 == null || !_snowman2.isPresent()) continue;
            DataResult _snowman3 = this.codecFactory.apply(() -> this.onUpdate(l)).encodeStart(dynamicOps, _snowman2.get());
            String _snowman4 = Integer.toString(i);
            _snowman3.resultOrPartial(arg_0 -> ((Logger)LOGGER).error(arg_0)).ifPresent(object -> hashMap.put(dynamicOps.createString(_snowman4), object));
        }
        return new Dynamic(dynamicOps, dynamicOps.createMap((Map)ImmutableMap.of((Object)dynamicOps.createString("Sections"), (Object)dynamicOps.createMap((Map)hashMap), (Object)dynamicOps.createString("DataVersion"), (Object)dynamicOps.createInt(SharedConstants.getGameVersion().getWorldVersion()))));
    }

    protected void onLoad(long pos) {
    }

    protected void onUpdate(long pos) {
        Optional optional = (Optional)this.loadedElements.get(pos);
        if (optional == null || !optional.isPresent()) {
            LOGGER.warn("No data for position: {}", (Object)ChunkSectionPos.from(pos));
            return;
        }
        this.unsavedElements.add(pos);
    }

    private static int getDataVersion(Dynamic<?> dynamic) {
        return dynamic.get("DataVersion").asInt(1945);
    }

    public void method_20436(ChunkPos chunkPos) {
        if (!this.unsavedElements.isEmpty()) {
            for (int i = 0; i < 16; ++i) {
                long l = ChunkSectionPos.from(chunkPos, i).asLong();
                if (!this.unsavedElements.contains(l)) continue;
                this.save(chunkPos);
                return;
            }
        }
    }

    @Override
    public void close() throws IOException {
        this.worker.close();
    }
}

