/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2IntMap
 *  it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
 */
package net.minecraft.world.chunk;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.collection.PackedIntegerArray;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.ArrayPalette;
import net.minecraft.world.chunk.BiMapPalette;
import net.minecraft.world.chunk.Palette;
import net.minecraft.world.chunk.PaletteResizeListener;

public class PalettedContainer<T>
implements PaletteResizeListener<T> {
    private final Palette<T> fallbackPalette;
    private final PaletteResizeListener<T> noOpPaletteResizeHandler = (newSize, added) -> 0;
    private final IdList<T> idList;
    private final Function<CompoundTag, T> elementDeserializer;
    private final Function<T, CompoundTag> elementSerializer;
    private final T defaultValue;
    protected PackedIntegerArray data;
    private Palette<T> palette;
    private int paletteSize;
    private final ReentrantLock writeLock = new ReentrantLock();

    public void lock() {
        if (this.writeLock.isLocked() && !this.writeLock.isHeldByCurrentThread()) {
            String string = Thread.getAllStackTraces().keySet().stream().filter(Objects::nonNull).map(thread -> thread.getName() + ": \n\tat " + Arrays.stream(thread.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n\tat "))).collect(Collectors.joining("\n"));
            CrashReport _snowman2 = new CrashReport("Writing into PalettedContainer from multiple threads", new IllegalStateException());
            CrashReportSection _snowman3 = _snowman2.addElement("Thread dumps");
            _snowman3.add("Thread dumps", string);
            throw new CrashException(_snowman2);
        }
        this.writeLock.lock();
    }

    public void unlock() {
        this.writeLock.unlock();
    }

    public PalettedContainer(Palette<T> fallbackPalette, IdList<T> idList, Function<CompoundTag, T> elementDeserializer, Function<T, CompoundTag> elementSerializer, T defaultElement) {
        this.fallbackPalette = fallbackPalette;
        this.idList = idList;
        this.elementDeserializer = elementDeserializer;
        this.elementSerializer = elementSerializer;
        this.defaultValue = defaultElement;
        this.setPaletteSize(4);
    }

    private static int toIndex(int x, int y, int z) {
        return y << 8 | z << 4 | x;
    }

    private void setPaletteSize(int size) {
        if (size == this.paletteSize) {
            return;
        }
        this.paletteSize = size;
        if (this.paletteSize <= 4) {
            this.paletteSize = 4;
            this.palette = new ArrayPalette<T>(this.idList, this.paletteSize, this, this.elementDeserializer);
        } else if (this.paletteSize < 9) {
            this.palette = new BiMapPalette<T>(this.idList, this.paletteSize, this, this.elementDeserializer, this.elementSerializer);
        } else {
            this.palette = this.fallbackPalette;
            this.paletteSize = MathHelper.log2DeBruijn(this.idList.size());
        }
        this.palette.getIndex(this.defaultValue);
        this.data = new PackedIntegerArray(this.paletteSize, 4096);
    }

    @Override
    public int onResize(int n, T t2) {
        T t2;
        int _snowman3;
        this.lock();
        PackedIntegerArray packedIntegerArray = this.data;
        Palette<T> _snowman2 = this.palette;
        this.setPaletteSize(n);
        for (_snowman3 = 0; _snowman3 < packedIntegerArray.getSize(); ++_snowman3) {
            T t3 = _snowman2.getByIndex(packedIntegerArray.get(_snowman3));
            if (t3 == null) continue;
            this.set(_snowman3, t3);
        }
        _snowman3 = this.palette.getIndex(t2);
        this.unlock();
        return _snowman3;
    }

    public T setSync(int x, int y, int z, T value) {
        this.lock();
        T t = this.setAndGetOldValue(PalettedContainer.toIndex(x, y, z), value);
        this.unlock();
        return t;
    }

    public T set(int x, int y, int z, T value) {
        return this.setAndGetOldValue(PalettedContainer.toIndex(x, y, z), value);
    }

    protected T setAndGetOldValue(int index, T value) {
        int n = this.palette.getIndex(value);
        _snowman = this.data.setAndGetOldValue(index, n);
        T _snowman2 = this.palette.getByIndex(_snowman);
        return _snowman2 == null ? this.defaultValue : _snowman2;
    }

    protected void set(int n, T t) {
        int n2 = this.palette.getIndex(t);
        this.data.set(n, n2);
    }

    public T get(int x, int y, int z) {
        return this.get(PalettedContainer.toIndex(x, y, z));
    }

    protected T get(int index) {
        T t = this.palette.getByIndex(this.data.get(index));
        return t == null ? this.defaultValue : t;
    }

    public void fromPacket(PacketByteBuf buf) {
        this.lock();
        byte by = buf.readByte();
        if (this.paletteSize != by) {
            this.setPaletteSize(by);
        }
        this.palette.fromPacket(buf);
        buf.readLongArray(this.data.getStorage());
        this.unlock();
    }

    public void toPacket(PacketByteBuf buf) {
        this.lock();
        buf.writeByte(this.paletteSize);
        this.palette.toPacket(buf);
        buf.writeLongArray(this.data.getStorage());
        this.unlock();
    }

    public void read(ListTag paletteTag, long[] data) {
        int n;
        this.lock();
        int n2 = Math.max(4, MathHelper.log2DeBruijn(paletteTag.size()));
        if (n2 != this.paletteSize) {
            this.setPaletteSize(n2);
        }
        this.palette.fromTag(paletteTag);
        n = data.length * 64 / 4096;
        if (this.palette == this.fallbackPalette) {
            BiMapPalette<T> biMapPalette = new BiMapPalette<T>(this.idList, n2, this.noOpPaletteResizeHandler, this.elementDeserializer, this.elementSerializer);
            biMapPalette.fromTag(paletteTag);
            PackedIntegerArray _snowman2 = new PackedIntegerArray(n2, 4096, data);
            for (int i = 0; i < 4096; ++i) {
                this.data.set(i, this.fallbackPalette.getIndex(biMapPalette.getByIndex(_snowman2.get(i))));
            }
        } else if (n == this.paletteSize) {
            System.arraycopy(data, 0, this.data.getStorage(), 0, data.length);
        } else {
            PackedIntegerArray packedIntegerArray = new PackedIntegerArray(n, 4096, data);
            for (int i = 0; i < 4096; ++i) {
                this.data.set(i, packedIntegerArray.get(i));
            }
        }
        this.unlock();
    }

    public void write(CompoundTag compoundTag2, String string, String string2) {
        CompoundTag compoundTag2;
        this.lock();
        BiMapPalette<T> biMapPalette = new BiMapPalette<T>(this.idList, this.paletteSize, this.noOpPaletteResizeHandler, this.elementDeserializer, this.elementSerializer);
        T _snowman2 = this.defaultValue;
        int _snowman3 = biMapPalette.getIndex(this.defaultValue);
        int[] _snowman4 = new int[4096];
        for (int i = 0; i < 4096; ++i) {
            T t = this.get(i);
            if (t != _snowman2) {
                _snowman2 = t;
                _snowman3 = biMapPalette.getIndex(t);
            }
            _snowman4[i] = _snowman3;
        }
        ListTag listTag = new ListTag();
        biMapPalette.toTag(listTag);
        compoundTag2.put(string, listTag);
        int _snowman5 = Math.max(4, MathHelper.log2DeBruijn(listTag.size()));
        PackedIntegerArray _snowman6 = new PackedIntegerArray(_snowman5, 4096);
        for (int i = 0; i < _snowman4.length; ++i) {
            _snowman6.set(i, _snowman4[i]);
        }
        compoundTag2.putLongArray(string2, _snowman6.getStorage());
        this.unlock();
    }

    public int getPacketSize() {
        return 1 + this.palette.getPacketSize() + PacketByteBuf.getVarIntSizeBytes(this.data.getSize()) + this.data.getStorage().length * 8;
    }

    public boolean hasAny(Predicate<T> predicate) {
        return this.palette.accepts(predicate);
    }

    public void count(CountConsumer<T> consumer) {
        Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap();
        this.data.forEach(arg_0 -> PalettedContainer.method_21734((Int2IntMap)int2IntOpenHashMap, arg_0));
        int2IntOpenHashMap.int2IntEntrySet().forEach(entry -> consumer.accept(this.palette.getByIndex(entry.getIntKey()), entry.getIntValue()));
    }

    private static /* synthetic */ void method_21734(Int2IntMap int2IntMap, int n) {
        int2IntMap.put(n, int2IntMap.get(n) + 1);
    }

    @FunctionalInterface
    public static interface CountConsumer<T> {
        public void accept(T var1, int var2);
    }
}

