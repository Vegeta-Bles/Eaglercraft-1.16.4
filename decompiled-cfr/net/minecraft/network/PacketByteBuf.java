/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DataResult$PartialResult
 *  com.mojang.serialization.DynamicOps
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufAllocator
 *  io.netty.buffer.ByteBufInputStream
 *  io.netty.buffer.ByteBufOutputStream
 *  io.netty.handler.codec.DecoderException
 *  io.netty.handler.codec.EncoderException
 *  io.netty.util.ByteProcessor
 *  javax.annotation.Nullable
 */
package net.minecraft.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.PositionTracker;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class PacketByteBuf
extends ByteBuf {
    private final ByteBuf parent;

    public PacketByteBuf(ByteBuf byteBuf) {
        this.parent = byteBuf;
    }

    public static int getVarIntSizeBytes(int n) {
        for (_snowman = 1; _snowman < 5; ++_snowman) {
            if ((n & -1 << _snowman * 7) != 0) continue;
            return _snowman;
        }
        return 5;
    }

    public <T> T decode(Codec<T> codec) throws IOException {
        CompoundTag compoundTag = this.method_30617();
        DataResult _snowman2 = codec.parse((DynamicOps)NbtOps.INSTANCE, (Object)compoundTag);
        if (_snowman2.error().isPresent()) {
            throw new IOException("Failed to decode: " + ((DataResult.PartialResult)_snowman2.error().get()).message() + " " + compoundTag);
        }
        return _snowman2.result().get();
    }

    public <T> void encode(Codec<T> codec, T t) throws IOException {
        DataResult dataResult = codec.encodeStart((DynamicOps)NbtOps.INSTANCE, t);
        if (dataResult.error().isPresent()) {
            throw new IOException("Failed to encode: " + ((DataResult.PartialResult)dataResult.error().get()).message() + " " + t);
        }
        this.writeCompoundTag((CompoundTag)dataResult.result().get());
    }

    public PacketByteBuf writeByteArray(byte[] byArray) {
        this.writeVarInt(byArray.length);
        this.writeBytes(byArray);
        return this;
    }

    public byte[] readByteArray() {
        return this.readByteArray(this.readableBytes());
    }

    public byte[] readByteArray(int n) {
        _snowman = this.readVarInt();
        if (_snowman > n) {
            throw new DecoderException("ByteArray with size " + _snowman + " is bigger than allowed " + n);
        }
        byte[] byArray = new byte[_snowman];
        this.readBytes(byArray);
        return byArray;
    }

    public PacketByteBuf writeIntArray(int[] nArray) {
        this.writeVarInt(nArray.length);
        for (int n : nArray) {
            this.writeVarInt(n);
        }
        return this;
    }

    public int[] readIntArray() {
        return this.readIntArray(this.readableBytes());
    }

    public int[] readIntArray(int n) {
        _snowman = this.readVarInt();
        if (_snowman > n) {
            throw new DecoderException("VarIntArray with size " + _snowman + " is bigger than allowed " + n);
        }
        int[] nArray = new int[_snowman];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = this.readVarInt();
        }
        return nArray;
    }

    public PacketByteBuf writeLongArray(long[] lArray) {
        this.writeVarInt(lArray.length);
        for (long l : lArray) {
            this.writeLong(l);
        }
        return this;
    }

    public long[] readLongArray(@Nullable long[] lArray) {
        return this.readLongArray(lArray, this.readableBytes() / 8);
    }

    public long[] readLongArray(@Nullable long[] toArray, int n) {
        _snowman = this.readVarInt();
        if (toArray == null || toArray.length != _snowman) {
            if (_snowman > n) {
                throw new DecoderException("LongArray with size " + _snowman + " is bigger than allowed " + n);
            }
            toArray = new long[_snowman];
        }
        for (_snowman = 0; _snowman < toArray.length; ++_snowman) {
            toArray[_snowman] = this.readLong();
        }
        return toArray;
    }

    public BlockPos readBlockPos() {
        return BlockPos.fromLong(this.readLong());
    }

    public PacketByteBuf writeBlockPos(BlockPos blockPos) {
        this.writeLong(blockPos.asLong());
        return this;
    }

    public ChunkSectionPos readChunkSectionPos() {
        return ChunkSectionPos.from(this.readLong());
    }

    public Text readText() {
        return Text.Serializer.fromJson(this.readString(262144));
    }

    public PacketByteBuf writeText(Text text) {
        return this.writeString(Text.Serializer.toJson(text), 262144);
    }

    public <T extends Enum<T>> T readEnumConstant(Class<T> clazz) {
        return (T)((Enum[])clazz.getEnumConstants())[this.readVarInt()];
    }

    public PacketByteBuf writeEnumConstant(Enum<?> enum_) {
        return this.writeVarInt(enum_.ordinal());
    }

    public int readVarInt() {
        int n = 0;
        _snowman = 0;
        do {
            byte by = this.readByte();
            n |= (by & 0x7F) << _snowman++ * 7;
            if (_snowman <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while ((by & 0x80) == 128);
        return n;
    }

    public long readVarLong() {
        long l = 0L;
        int _snowman2 = 0;
        do {
            byte by = this.readByte();
            l |= (long)(by & 0x7F) << _snowman2++ * 7;
            if (_snowman2 <= 10) continue;
            throw new RuntimeException("VarLong too big");
        } while ((by & 0x80) == 128);
        return l;
    }

    public PacketByteBuf writeUuid(UUID uUID) {
        this.writeLong(uUID.getMostSignificantBits());
        this.writeLong(uUID.getLeastSignificantBits());
        return this;
    }

    public UUID readUuid() {
        return new UUID(this.readLong(), this.readLong());
    }

    public PacketByteBuf writeVarInt(int n) {
        while (true) {
            if ((n & 0xFFFFFF80) == 0) {
                this.writeByte(n);
                return this;
            }
            this.writeByte(n & 0x7F | 0x80);
            n >>>= 7;
        }
    }

    public PacketByteBuf writeVarLong(long l) {
        while (true) {
            if ((l & 0xFFFFFFFFFFFFFF80L) == 0L) {
                this.writeByte((int)l);
                return this;
            }
            this.writeByte((int)(l & 0x7FL) | 0x80);
            l >>>= 7;
        }
    }

    public PacketByteBuf writeCompoundTag(@Nullable CompoundTag compoundTag) {
        if (compoundTag == null) {
            this.writeByte(0);
        } else {
            try {
                NbtIo.write(compoundTag, (DataOutput)new ByteBufOutputStream((ByteBuf)this));
            }
            catch (IOException iOException) {
                throw new EncoderException((Throwable)iOException);
            }
        }
        return this;
    }

    @Nullable
    public CompoundTag readCompoundTag() {
        return this.method_30616(new PositionTracker(0x200000L));
    }

    @Nullable
    public CompoundTag method_30617() {
        return this.method_30616(PositionTracker.DEFAULT);
    }

    @Nullable
    public CompoundTag method_30616(PositionTracker positionTracker) {
        int n = this.readerIndex();
        byte _snowman2 = this.readByte();
        if (_snowman2 == 0) {
            return null;
        }
        this.readerIndex(n);
        try {
            return NbtIo.read((DataInput)new ByteBufInputStream((ByteBuf)this), positionTracker);
        }
        catch (IOException _snowman3) {
            throw new EncoderException((Throwable)_snowman3);
        }
    }

    public PacketByteBuf writeItemStack(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            this.writeBoolean(false);
        } else {
            this.writeBoolean(true);
            Item item = itemStack.getItem();
            this.writeVarInt(Item.getRawId(item));
            this.writeByte(itemStack.getCount());
            CompoundTag _snowman2 = null;
            if (item.isDamageable() || item.shouldSyncTagToClient()) {
                _snowman2 = itemStack.getTag();
            }
            this.writeCompoundTag(_snowman2);
        }
        return this;
    }

    public ItemStack readItemStack() {
        if (!this.readBoolean()) {
            return ItemStack.EMPTY;
        }
        int n = this.readVarInt();
        byte _snowman2 = this.readByte();
        ItemStack _snowman3 = new ItemStack(Item.byRawId(n), _snowman2);
        _snowman3.setTag(this.readCompoundTag());
        return _snowman3;
    }

    public String readString() {
        return this.readString(Short.MAX_VALUE);
    }

    public String readString(int n) {
        _snowman = this.readVarInt();
        if (_snowman > n * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + _snowman + " > " + n * 4 + ")");
        }
        if (_snowman < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        String string = this.toString(this.readerIndex(), _snowman, StandardCharsets.UTF_8);
        this.readerIndex(this.readerIndex() + _snowman);
        if (string.length() > n) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + _snowman + " > " + n + ")");
        }
        return string;
    }

    public PacketByteBuf writeString(String string) {
        return this.writeString(string, Short.MAX_VALUE);
    }

    public PacketByteBuf writeString(String string, int n) {
        byte[] byArray = string.getBytes(StandardCharsets.UTF_8);
        if (byArray.length > n) {
            throw new EncoderException("String too big (was " + byArray.length + " bytes encoded, max " + n + ")");
        }
        this.writeVarInt(byArray.length);
        this.writeBytes(byArray);
        return this;
    }

    public Identifier readIdentifier() {
        return new Identifier(this.readString(Short.MAX_VALUE));
    }

    public PacketByteBuf writeIdentifier(Identifier identifier) {
        this.writeString(identifier.toString());
        return this;
    }

    public Date readDate() {
        return new Date(this.readLong());
    }

    public PacketByteBuf writeDate(Date date) {
        this.writeLong(date.getTime());
        return this;
    }

    public BlockHitResult readBlockHitResult() {
        BlockPos blockPos = this.readBlockPos();
        Direction _snowman2 = this.readEnumConstant(Direction.class);
        float _snowman3 = this.readFloat();
        float _snowman4 = this.readFloat();
        float _snowman5 = this.readFloat();
        boolean _snowman6 = this.readBoolean();
        return new BlockHitResult(new Vec3d((double)blockPos.getX() + (double)_snowman3, (double)blockPos.getY() + (double)_snowman4, (double)blockPos.getZ() + (double)_snowman5), _snowman2, blockPos, _snowman6);
    }

    public void writeBlockHitResult(BlockHitResult blockHitResult) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        this.writeBlockPos(blockPos);
        this.writeEnumConstant(blockHitResult.getSide());
        Vec3d _snowman2 = blockHitResult.getPos();
        this.writeFloat((float)(_snowman2.x - (double)blockPos.getX()));
        this.writeFloat((float)(_snowman2.y - (double)blockPos.getY()));
        this.writeFloat((float)(_snowman2.z - (double)blockPos.getZ()));
        this.writeBoolean(blockHitResult.isInsideBlock());
    }

    public int capacity() {
        return this.parent.capacity();
    }

    public ByteBuf capacity(int n) {
        return this.parent.capacity(n);
    }

    public int maxCapacity() {
        return this.parent.maxCapacity();
    }

    public ByteBufAllocator alloc() {
        return this.parent.alloc();
    }

    public ByteOrder order() {
        return this.parent.order();
    }

    public ByteBuf order(ByteOrder byteOrder) {
        return this.parent.order(byteOrder);
    }

    public ByteBuf unwrap() {
        return this.parent.unwrap();
    }

    public boolean isDirect() {
        return this.parent.isDirect();
    }

    public boolean isReadOnly() {
        return this.parent.isReadOnly();
    }

    public ByteBuf asReadOnly() {
        return this.parent.asReadOnly();
    }

    public int readerIndex() {
        return this.parent.readerIndex();
    }

    public ByteBuf readerIndex(int n) {
        return this.parent.readerIndex(n);
    }

    public int writerIndex() {
        return this.parent.writerIndex();
    }

    public ByteBuf writerIndex(int n) {
        return this.parent.writerIndex(n);
    }

    public ByteBuf setIndex(int n, int n2) {
        return this.parent.setIndex(n, n2);
    }

    public int readableBytes() {
        return this.parent.readableBytes();
    }

    public int writableBytes() {
        return this.parent.writableBytes();
    }

    public int maxWritableBytes() {
        return this.parent.maxWritableBytes();
    }

    public boolean isReadable() {
        return this.parent.isReadable();
    }

    public boolean isReadable(int n) {
        return this.parent.isReadable(n);
    }

    public boolean isWritable() {
        return this.parent.isWritable();
    }

    public boolean isWritable(int n) {
        return this.parent.isWritable(n);
    }

    public ByteBuf clear() {
        return this.parent.clear();
    }

    public ByteBuf markReaderIndex() {
        return this.parent.markReaderIndex();
    }

    public ByteBuf resetReaderIndex() {
        return this.parent.resetReaderIndex();
    }

    public ByteBuf markWriterIndex() {
        return this.parent.markWriterIndex();
    }

    public ByteBuf resetWriterIndex() {
        return this.parent.resetWriterIndex();
    }

    public ByteBuf discardReadBytes() {
        return this.parent.discardReadBytes();
    }

    public ByteBuf discardSomeReadBytes() {
        return this.parent.discardSomeReadBytes();
    }

    public ByteBuf ensureWritable(int n) {
        return this.parent.ensureWritable(n);
    }

    public int ensureWritable(int n, boolean bl) {
        return this.parent.ensureWritable(n, bl);
    }

    public boolean getBoolean(int n) {
        return this.parent.getBoolean(n);
    }

    public byte getByte(int n) {
        return this.parent.getByte(n);
    }

    public short getUnsignedByte(int n) {
        return this.parent.getUnsignedByte(n);
    }

    public short getShort(int n) {
        return this.parent.getShort(n);
    }

    public short getShortLE(int n) {
        return this.parent.getShortLE(n);
    }

    public int getUnsignedShort(int n) {
        return this.parent.getUnsignedShort(n);
    }

    public int getUnsignedShortLE(int n) {
        return this.parent.getUnsignedShortLE(n);
    }

    public int getMedium(int n) {
        return this.parent.getMedium(n);
    }

    public int getMediumLE(int n) {
        return this.parent.getMediumLE(n);
    }

    public int getUnsignedMedium(int n) {
        return this.parent.getUnsignedMedium(n);
    }

    public int getUnsignedMediumLE(int n) {
        return this.parent.getUnsignedMediumLE(n);
    }

    public int getInt(int n) {
        return this.parent.getInt(n);
    }

    public int getIntLE(int n) {
        return this.parent.getIntLE(n);
    }

    public long getUnsignedInt(int n) {
        return this.parent.getUnsignedInt(n);
    }

    public long getUnsignedIntLE(int n) {
        return this.parent.getUnsignedIntLE(n);
    }

    public long getLong(int n) {
        return this.parent.getLong(n);
    }

    public long getLongLE(int n) {
        return this.parent.getLongLE(n);
    }

    public char getChar(int n) {
        return this.parent.getChar(n);
    }

    public float getFloat(int n) {
        return this.parent.getFloat(n);
    }

    public double getDouble(int n) {
        return this.parent.getDouble(n);
    }

    public ByteBuf getBytes(int n, ByteBuf byteBuf) {
        return this.parent.getBytes(n, byteBuf);
    }

    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2) {
        return this.parent.getBytes(n, byteBuf, n2);
    }

    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        return this.parent.getBytes(n, byteBuf, n2, n3);
    }

    public ByteBuf getBytes(int n, byte[] byArray) {
        return this.parent.getBytes(n, byArray);
    }

    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        return this.parent.getBytes(n, byArray, n2, n3);
    }

    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        return this.parent.getBytes(n, byteBuffer);
    }

    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        return this.parent.getBytes(n, outputStream, n2);
    }

    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        return this.parent.getBytes(n, gatheringByteChannel, n2);
    }

    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.parent.getBytes(n, fileChannel, l, n2);
    }

    public CharSequence getCharSequence(int n, int n2, Charset charset) {
        return this.parent.getCharSequence(n, n2, charset);
    }

    public ByteBuf setBoolean(int n, boolean bl) {
        return this.parent.setBoolean(n, bl);
    }

    public ByteBuf setByte(int n, int n2) {
        return this.parent.setByte(n, n2);
    }

    public ByteBuf setShort(int n, int n2) {
        return this.parent.setShort(n, n2);
    }

    public ByteBuf setShortLE(int n, int n2) {
        return this.parent.setShortLE(n, n2);
    }

    public ByteBuf setMedium(int n, int n2) {
        return this.parent.setMedium(n, n2);
    }

    public ByteBuf setMediumLE(int n, int n2) {
        return this.parent.setMediumLE(n, n2);
    }

    public ByteBuf setInt(int n, int n2) {
        return this.parent.setInt(n, n2);
    }

    public ByteBuf setIntLE(int n, int n2) {
        return this.parent.setIntLE(n, n2);
    }

    public ByteBuf setLong(int n, long l) {
        return this.parent.setLong(n, l);
    }

    public ByteBuf setLongLE(int n, long l) {
        return this.parent.setLongLE(n, l);
    }

    public ByteBuf setChar(int n, int n2) {
        return this.parent.setChar(n, n2);
    }

    public ByteBuf setFloat(int n, float f) {
        return this.parent.setFloat(n, f);
    }

    public ByteBuf setDouble(int n, double d) {
        return this.parent.setDouble(n, d);
    }

    public ByteBuf setBytes(int n, ByteBuf byteBuf) {
        return this.parent.setBytes(n, byteBuf);
    }

    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2) {
        return this.parent.setBytes(n, byteBuf, n2);
    }

    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        return this.parent.setBytes(n, byteBuf, n2, n3);
    }

    public ByteBuf setBytes(int n, byte[] byArray) {
        return this.parent.setBytes(n, byArray);
    }

    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        return this.parent.setBytes(n, byArray, n2, n3);
    }

    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        return this.parent.setBytes(n, byteBuffer);
    }

    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        return this.parent.setBytes(n, inputStream, n2);
    }

    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        return this.parent.setBytes(n, scatteringByteChannel, n2);
    }

    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.parent.setBytes(n, fileChannel, l, n2);
    }

    public ByteBuf setZero(int n, int n2) {
        return this.parent.setZero(n, n2);
    }

    public int setCharSequence(int n, CharSequence charSequence, Charset charset) {
        return this.parent.setCharSequence(n, charSequence, charset);
    }

    public boolean readBoolean() {
        return this.parent.readBoolean();
    }

    public byte readByte() {
        return this.parent.readByte();
    }

    public short readUnsignedByte() {
        return this.parent.readUnsignedByte();
    }

    public short readShort() {
        return this.parent.readShort();
    }

    public short readShortLE() {
        return this.parent.readShortLE();
    }

    public int readUnsignedShort() {
        return this.parent.readUnsignedShort();
    }

    public int readUnsignedShortLE() {
        return this.parent.readUnsignedShortLE();
    }

    public int readMedium() {
        return this.parent.readMedium();
    }

    public int readMediumLE() {
        return this.parent.readMediumLE();
    }

    public int readUnsignedMedium() {
        return this.parent.readUnsignedMedium();
    }

    public int readUnsignedMediumLE() {
        return this.parent.readUnsignedMediumLE();
    }

    public int readInt() {
        return this.parent.readInt();
    }

    public int readIntLE() {
        return this.parent.readIntLE();
    }

    public long readUnsignedInt() {
        return this.parent.readUnsignedInt();
    }

    public long readUnsignedIntLE() {
        return this.parent.readUnsignedIntLE();
    }

    public long readLong() {
        return this.parent.readLong();
    }

    public long readLongLE() {
        return this.parent.readLongLE();
    }

    public char readChar() {
        return this.parent.readChar();
    }

    public float readFloat() {
        return this.parent.readFloat();
    }

    public double readDouble() {
        return this.parent.readDouble();
    }

    public ByteBuf readBytes(int n) {
        return this.parent.readBytes(n);
    }

    public ByteBuf readSlice(int n) {
        return this.parent.readSlice(n);
    }

    public ByteBuf readRetainedSlice(int n) {
        return this.parent.readRetainedSlice(n);
    }

    public ByteBuf readBytes(ByteBuf byteBuf) {
        return this.parent.readBytes(byteBuf);
    }

    public ByteBuf readBytes(ByteBuf byteBuf, int n) {
        return this.parent.readBytes(byteBuf, n);
    }

    public ByteBuf readBytes(ByteBuf byteBuf, int n, int n2) {
        return this.parent.readBytes(byteBuf, n, n2);
    }

    public ByteBuf readBytes(byte[] byArray) {
        return this.parent.readBytes(byArray);
    }

    public ByteBuf readBytes(byte[] byArray, int n, int n2) {
        return this.parent.readBytes(byArray, n, n2);
    }

    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        return this.parent.readBytes(byteBuffer);
    }

    public ByteBuf readBytes(OutputStream outputStream, int n) throws IOException {
        return this.parent.readBytes(outputStream, n);
    }

    public int readBytes(GatheringByteChannel gatheringByteChannel, int n) throws IOException {
        return this.parent.readBytes(gatheringByteChannel, n);
    }

    public CharSequence readCharSequence(int n, Charset charset) {
        return this.parent.readCharSequence(n, charset);
    }

    public int readBytes(FileChannel fileChannel, long l, int n) throws IOException {
        return this.parent.readBytes(fileChannel, l, n);
    }

    public ByteBuf skipBytes(int n) {
        return this.parent.skipBytes(n);
    }

    public ByteBuf writeBoolean(boolean bl) {
        return this.parent.writeBoolean(bl);
    }

    public ByteBuf writeByte(int n) {
        return this.parent.writeByte(n);
    }

    public ByteBuf writeShort(int n) {
        return this.parent.writeShort(n);
    }

    public ByteBuf writeShortLE(int n) {
        return this.parent.writeShortLE(n);
    }

    public ByteBuf writeMedium(int n) {
        return this.parent.writeMedium(n);
    }

    public ByteBuf writeMediumLE(int n) {
        return this.parent.writeMediumLE(n);
    }

    public ByteBuf writeInt(int n) {
        return this.parent.writeInt(n);
    }

    public ByteBuf writeIntLE(int n) {
        return this.parent.writeIntLE(n);
    }

    public ByteBuf writeLong(long l) {
        return this.parent.writeLong(l);
    }

    public ByteBuf writeLongLE(long l) {
        return this.parent.writeLongLE(l);
    }

    public ByteBuf writeChar(int n) {
        return this.parent.writeChar(n);
    }

    public ByteBuf writeFloat(float f) {
        return this.parent.writeFloat(f);
    }

    public ByteBuf writeDouble(double d) {
        return this.parent.writeDouble(d);
    }

    public ByteBuf writeBytes(ByteBuf byteBuf) {
        return this.parent.writeBytes(byteBuf);
    }

    public ByteBuf writeBytes(ByteBuf byteBuf, int n) {
        return this.parent.writeBytes(byteBuf, n);
    }

    public ByteBuf writeBytes(ByteBuf byteBuf, int n, int n2) {
        return this.parent.writeBytes(byteBuf, n, n2);
    }

    public ByteBuf writeBytes(byte[] byArray) {
        return this.parent.writeBytes(byArray);
    }

    public ByteBuf writeBytes(byte[] byArray, int n, int n2) {
        return this.parent.writeBytes(byArray, n, n2);
    }

    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        return this.parent.writeBytes(byteBuffer);
    }

    public int writeBytes(InputStream inputStream, int n) throws IOException {
        return this.parent.writeBytes(inputStream, n);
    }

    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int n) throws IOException {
        return this.parent.writeBytes(scatteringByteChannel, n);
    }

    public int writeBytes(FileChannel fileChannel, long l, int n) throws IOException {
        return this.parent.writeBytes(fileChannel, l, n);
    }

    public ByteBuf writeZero(int n) {
        return this.parent.writeZero(n);
    }

    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return this.parent.writeCharSequence(charSequence, charset);
    }

    public int indexOf(int n, int n2, byte by) {
        return this.parent.indexOf(n, n2, by);
    }

    public int bytesBefore(byte by) {
        return this.parent.bytesBefore(by);
    }

    public int bytesBefore(int n, byte by) {
        return this.parent.bytesBefore(n, by);
    }

    public int bytesBefore(int n, int n2, byte by) {
        return this.parent.bytesBefore(n, n2, by);
    }

    public int forEachByte(ByteProcessor byteProcessor) {
        return this.parent.forEachByte(byteProcessor);
    }

    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) {
        return this.parent.forEachByte(n, n2, byteProcessor);
    }

    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return this.parent.forEachByteDesc(byteProcessor);
    }

    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) {
        return this.parent.forEachByteDesc(n, n2, byteProcessor);
    }

    public ByteBuf copy() {
        return this.parent.copy();
    }

    public ByteBuf copy(int n, int n2) {
        return this.parent.copy(n, n2);
    }

    public ByteBuf slice() {
        return this.parent.slice();
    }

    public ByteBuf retainedSlice() {
        return this.parent.retainedSlice();
    }

    public ByteBuf slice(int n, int n2) {
        return this.parent.slice(n, n2);
    }

    public ByteBuf retainedSlice(int n, int n2) {
        return this.parent.retainedSlice(n, n2);
    }

    public ByteBuf duplicate() {
        return this.parent.duplicate();
    }

    public ByteBuf retainedDuplicate() {
        return this.parent.retainedDuplicate();
    }

    public int nioBufferCount() {
        return this.parent.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        return this.parent.nioBuffer();
    }

    public ByteBuffer nioBuffer(int n, int n2) {
        return this.parent.nioBuffer(n, n2);
    }

    public ByteBuffer internalNioBuffer(int n, int n2) {
        return this.parent.internalNioBuffer(n, n2);
    }

    public ByteBuffer[] nioBuffers() {
        return this.parent.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int n, int n2) {
        return this.parent.nioBuffers(n, n2);
    }

    public boolean hasArray() {
        return this.parent.hasArray();
    }

    public byte[] array() {
        return this.parent.array();
    }

    public int arrayOffset() {
        return this.parent.arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return this.parent.hasMemoryAddress();
    }

    public long memoryAddress() {
        return this.parent.memoryAddress();
    }

    public String toString(Charset charset) {
        return this.parent.toString(charset);
    }

    public String toString(int n, int n2, Charset charset) {
        return this.parent.toString(n, n2, charset);
    }

    public int hashCode() {
        return this.parent.hashCode();
    }

    public boolean equals(Object object) {
        return this.parent.equals(object);
    }

    public int compareTo(ByteBuf byteBuf) {
        return this.parent.compareTo(byteBuf);
    }

    public String toString() {
        return this.parent.toString();
    }

    public ByteBuf retain(int n) {
        return this.parent.retain(n);
    }

    public ByteBuf retain() {
        return this.parent.retain();
    }

    public ByteBuf touch() {
        return this.parent.touch();
    }

    public ByteBuf touch(Object object) {
        return this.parent.touch(object);
    }

    public int refCnt() {
        return this.parent.refCnt();
    }

    public boolean release() {
        return this.parent.release();
    }

    public boolean release(int n) {
        return this.parent.release(n);
    }
}

