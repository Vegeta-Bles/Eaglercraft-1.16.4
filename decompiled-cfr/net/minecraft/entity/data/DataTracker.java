/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  io.netty.handler.codec.DecoderException
 *  io.netty.handler.codec.EncoderException
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataTracker {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<Class<? extends Entity>, Integer> TRACKED_ENTITIES = Maps.newHashMap();
    private final Entity trackedEntity;
    private final Map<Integer, Entry<?>> entries = Maps.newHashMap();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private boolean empty = true;
    private boolean dirty;

    public DataTracker(Entity trackedEntity) {
        this.trackedEntity = trackedEntity;
    }

    public static <T> TrackedData<T> registerData(Class<? extends Entity> entityClass, TrackedDataHandler<T> dataHandler) {
        if (LOGGER.isDebugEnabled()) {
            try {
                Class<?> clazz = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
                if (!clazz.equals(entityClass)) {
                    LOGGER.debug("defineId called for: {} from {}", entityClass, clazz, (Object)new RuntimeException());
                }
            }
            catch (ClassNotFoundException clazz) {
                // empty catch block
            }
        }
        if (TRACKED_ENTITIES.containsKey(entityClass)) {
            int n = TRACKED_ENTITIES.get(entityClass) + 1;
        } else {
            int n = 0;
            Class<? extends Entity> _snowman2 = entityClass;
            while (_snowman2 != Entity.class) {
                if (!TRACKED_ENTITIES.containsKey(_snowman2 = _snowman2.getSuperclass())) continue;
                n = TRACKED_ENTITIES.get(_snowman2) + 1;
                break;
            }
            n = n;
        }
        if (n > 254) {
            throw new IllegalArgumentException("Data value id is too big with " + n + "! (Max is " + 254 + ")");
        }
        TRACKED_ENTITIES.put(entityClass, n);
        return dataHandler.create(n);
    }

    public <T> void startTracking(TrackedData<T> key, T initialValue) {
        int n = key.getId();
        if (n > 254) {
            throw new IllegalArgumentException("Data value id is too big with " + n + "! (Max is " + 254 + ")");
        }
        if (this.entries.containsKey(n)) {
            throw new IllegalArgumentException("Duplicate id value for " + n + "!");
        }
        if (TrackedDataHandlerRegistry.getId(key.getType()) < 0) {
            throw new IllegalArgumentException("Unregistered serializer " + key.getType() + " for " + n + "!");
        }
        this.addTrackedData(key, initialValue);
    }

    private <T> void addTrackedData(TrackedData<T> trackedData, T t) {
        Entry<T> entry = new Entry<T>(trackedData, t);
        this.lock.writeLock().lock();
        this.entries.put(trackedData.getId(), entry);
        this.empty = false;
        this.lock.writeLock().unlock();
    }

    private <T> Entry<T> getEntry(TrackedData<T> trackedData) {
        this.lock.readLock().lock();
        try {
            Entry<?> entry = this.entries.get(trackedData.getId());
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Getting synched entity data");
            CrashReportSection _snowman2 = crashReport.addElement("Synched entity data");
            _snowman2.add("Data ID", trackedData);
            throw new CrashException(crashReport);
        }
        finally {
            this.lock.readLock().unlock();
        }
        return entry;
    }

    public <T> T get(TrackedData<T> trackedData) {
        return this.getEntry(trackedData).get();
    }

    public <T> void set(TrackedData<T> key, T t) {
        Entry<T> entry = this.getEntry(key);
        if (ObjectUtils.notEqual(t, entry.get())) {
            entry.set(t);
            this.trackedEntity.onTrackedDataSet(key);
            entry.setDirty(true);
            this.dirty = true;
        }
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public static void entriesToPacket(List<Entry<?>> list, PacketByteBuf packetByteBuf2) throws IOException {
        PacketByteBuf packetByteBuf2;
        if (list != null) {
            int n = list.size();
            for (_snowman = 0; _snowman < n; ++_snowman) {
                DataTracker.writeEntryToPacket(packetByteBuf2, list.get(_snowman));
            }
        }
        packetByteBuf2.writeByte(255);
    }

    @Nullable
    public List<Entry<?>> getDirtyEntries() {
        ArrayList arrayList = null;
        if (this.dirty) {
            this.lock.readLock().lock();
            for (Entry<?> entry : this.entries.values()) {
                if (!entry.isDirty()) continue;
                entry.setDirty(false);
                if (arrayList == null) {
                    arrayList = Lists.newArrayList();
                }
                arrayList.add(entry.copy());
            }
            this.lock.readLock().unlock();
        }
        this.dirty = false;
        return arrayList;
    }

    @Nullable
    public List<Entry<?>> getAllEntries() {
        ArrayList arrayList = null;
        this.lock.readLock().lock();
        for (Entry<?> entry : this.entries.values()) {
            if (arrayList == null) {
                arrayList = Lists.newArrayList();
            }
            arrayList.add(entry.copy());
        }
        this.lock.readLock().unlock();
        return arrayList;
    }

    private static <T> void writeEntryToPacket(PacketByteBuf packetByteBuf, Entry<T> entry) throws IOException {
        TrackedData<T> trackedData = entry.getData();
        int _snowman2 = TrackedDataHandlerRegistry.getId(trackedData.getType());
        if (_snowman2 < 0) {
            throw new EncoderException("Unknown serializer type " + trackedData.getType());
        }
        packetByteBuf.writeByte(trackedData.getId());
        packetByteBuf.writeVarInt(_snowman2);
        trackedData.getType().write(packetByteBuf, entry.get());
    }

    @Nullable
    public static List<Entry<?>> deserializePacket(PacketByteBuf packetByteBuf) throws IOException {
        ArrayList arrayList = null;
        while ((_snowman = packetByteBuf.readUnsignedByte()) != 255) {
            if (arrayList == null) {
                arrayList = Lists.newArrayList();
            }
            if ((_snowman = TrackedDataHandlerRegistry.get(_snowman = packetByteBuf.readVarInt())) == null) {
                throw new DecoderException("Unknown serializer type " + _snowman);
            }
            arrayList.add(DataTracker.entryFromPacket(packetByteBuf, _snowman, _snowman));
        }
        return arrayList;
    }

    private static <T> Entry<T> entryFromPacket(PacketByteBuf packetByteBuf, int n, TrackedDataHandler<T> trackedDataHandler) {
        return new Entry<T>(trackedDataHandler.create(n), trackedDataHandler.read(packetByteBuf));
    }

    public void writeUpdatedEntries(List<Entry<?>> list) {
        this.lock.writeLock().lock();
        for (Entry<?> entry : list) {
            _snowman = this.entries.get(entry.getData().getId());
            if (_snowman == null) continue;
            this.copyToFrom(_snowman, entry);
            this.trackedEntity.onTrackedDataSet(entry.getData());
        }
        this.lock.writeLock().unlock();
        this.dirty = true;
    }

    private <T> void copyToFrom(Entry<T> entry, Entry<?> entry2) {
        if (!Objects.equals(((Entry)entry2).data.getType(), ((Entry)entry).data.getType())) {
            throw new IllegalStateException(String.format("Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)", ((Entry)entry).data.getId(), this.trackedEntity, ((Entry)entry).value, ((Entry)entry).value.getClass(), ((Entry)entry2).value, ((Entry)entry2).value.getClass()));
        }
        entry.set(entry2.get());
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void clearDirty() {
        this.dirty = false;
        this.lock.readLock().lock();
        for (Entry<?> entry : this.entries.values()) {
            entry.setDirty(false);
        }
        this.lock.readLock().unlock();
    }

    public static class Entry<T> {
        private final TrackedData<T> data;
        private T value;
        private boolean dirty;

        public Entry(TrackedData<T> data, T value) {
            this.data = data;
            this.value = value;
            this.dirty = true;
        }

        public TrackedData<T> getData() {
            return this.data;
        }

        public void set(T value) {
            this.value = value;
        }

        public T get() {
            return this.value;
        }

        public boolean isDirty() {
            return this.dirty;
        }

        public void setDirty(boolean dirty) {
            this.dirty = dirty;
        }

        public Entry<T> copy() {
            return new Entry<T>(this.data, this.data.getType().copy(this.value));
        }
    }
}

