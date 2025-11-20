package net.minecraft.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DataResult.PartialResult;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
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
import net.minecraft.nbt.Tag;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class PacketByteBuf extends ByteBuf {
   private final ByteBuf parent;

   public PacketByteBuf(ByteBuf _snowman) {
      this.parent = _snowman;
   }

   public static int getVarIntSizeBytes(int _snowman) {
      for (int _snowmanx = 1; _snowmanx < 5; _snowmanx++) {
         if ((_snowman & -1 << _snowmanx * 7) == 0) {
            return _snowmanx;
         }
      }

      return 5;
   }

   public <T> T decode(Codec<T> _snowman) throws IOException {
      CompoundTag _snowmanx = this.method_30617();
      DataResult<T> _snowmanxx = _snowman.parse(NbtOps.INSTANCE, _snowmanx);
      if (_snowmanxx.error().isPresent()) {
         throw new IOException("Failed to decode: " + ((PartialResult)_snowmanxx.error().get()).message() + " " + _snowmanx);
      } else {
         return (T)_snowmanxx.result().get();
      }
   }

   public <T> void encode(Codec<T> _snowman, T _snowman) throws IOException {
      DataResult<Tag> _snowmanxx = _snowman.encodeStart(NbtOps.INSTANCE, _snowman);
      if (_snowmanxx.error().isPresent()) {
         throw new IOException("Failed to encode: " + ((PartialResult)_snowmanxx.error().get()).message() + " " + _snowman);
      } else {
         this.writeCompoundTag((CompoundTag)_snowmanxx.result().get());
      }
   }

   public PacketByteBuf writeByteArray(byte[] _snowman) {
      this.writeVarInt(_snowman.length);
      this.writeBytes(_snowman);
      return this;
   }

   public byte[] readByteArray() {
      return this.readByteArray(this.readableBytes());
   }

   public byte[] readByteArray(int _snowman) {
      int _snowmanx = this.readVarInt();
      if (_snowmanx > _snowman) {
         throw new DecoderException("ByteArray with size " + _snowmanx + " is bigger than allowed " + _snowman);
      } else {
         byte[] _snowmanxx = new byte[_snowmanx];
         this.readBytes(_snowmanxx);
         return _snowmanxx;
      }
   }

   public PacketByteBuf writeIntArray(int[] _snowman) {
      this.writeVarInt(_snowman.length);

      for (int _snowmanx : _snowman) {
         this.writeVarInt(_snowmanx);
      }

      return this;
   }

   public int[] readIntArray() {
      return this.readIntArray(this.readableBytes());
   }

   public int[] readIntArray(int _snowman) {
      int _snowmanx = this.readVarInt();
      if (_snowmanx > _snowman) {
         throw new DecoderException("VarIntArray with size " + _snowmanx + " is bigger than allowed " + _snowman);
      } else {
         int[] _snowmanxx = new int[_snowmanx];

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.length; _snowmanxxx++) {
            _snowmanxx[_snowmanxxx] = this.readVarInt();
         }

         return _snowmanxx;
      }
   }

   public PacketByteBuf writeLongArray(long[] _snowman) {
      this.writeVarInt(_snowman.length);

      for (long _snowmanx : _snowman) {
         this.writeLong(_snowmanx);
      }

      return this;
   }

   public long[] readLongArray(@Nullable long[] _snowman) {
      return this.readLongArray(_snowman, this.readableBytes() / 8);
   }

   public long[] readLongArray(@Nullable long[] toArray, int _snowman) {
      int _snowmanx = this.readVarInt();
      if (toArray == null || toArray.length != _snowmanx) {
         if (_snowmanx > _snowman) {
            throw new DecoderException("LongArray with size " + _snowmanx + " is bigger than allowed " + _snowman);
         }

         toArray = new long[_snowmanx];
      }

      for (int _snowmanxx = 0; _snowmanxx < toArray.length; _snowmanxx++) {
         toArray[_snowmanxx] = this.readLong();
      }

      return toArray;
   }

   public BlockPos readBlockPos() {
      return BlockPos.fromLong(this.readLong());
   }

   public PacketByteBuf writeBlockPos(BlockPos _snowman) {
      this.writeLong(_snowman.asLong());
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

   public <T extends Enum<T>> T readEnumConstant(Class<T> _snowman) {
      return _snowman.getEnumConstants()[this.readVarInt()];
   }

   public PacketByteBuf writeEnumConstant(Enum<?> _snowman) {
      return this.writeVarInt(_snowman.ordinal());
   }

   public int readVarInt() {
      int _snowman = 0;
      int _snowmanx = 0;

      byte _snowmanxx;
      do {
         _snowmanxx = this.readByte();
         _snowman |= (_snowmanxx & 127) << _snowmanx++ * 7;
         if (_snowmanx > 5) {
            throw new RuntimeException("VarInt too big");
         }
      } while ((_snowmanxx & 128) == 128);

      return _snowman;
   }

   public long readVarLong() {
      long _snowman = 0L;
      int _snowmanx = 0;

      byte _snowmanxx;
      do {
         _snowmanxx = this.readByte();
         _snowman |= (long)(_snowmanxx & 127) << _snowmanx++ * 7;
         if (_snowmanx > 10) {
            throw new RuntimeException("VarLong too big");
         }
      } while ((_snowmanxx & 128) == 128);

      return _snowman;
   }

   public PacketByteBuf writeUuid(UUID _snowman) {
      this.writeLong(_snowman.getMostSignificantBits());
      this.writeLong(_snowman.getLeastSignificantBits());
      return this;
   }

   public UUID readUuid() {
      return new UUID(this.readLong(), this.readLong());
   }

   public PacketByteBuf writeVarInt(int _snowman) {
      while ((_snowman & -128) != 0) {
         this.writeByte(_snowman & 127 | 128);
         _snowman >>>= 7;
      }

      this.writeByte(_snowman);
      return this;
   }

   public PacketByteBuf writeVarLong(long _snowman) {
      while ((_snowman & -128L) != 0L) {
         this.writeByte((int)(_snowman & 127L) | 128);
         _snowman >>>= 7;
      }

      this.writeByte((int)_snowman);
      return this;
   }

   public PacketByteBuf writeCompoundTag(@Nullable CompoundTag _snowman) {
      if (_snowman == null) {
         this.writeByte(0);
      } else {
         try {
            NbtIo.write(_snowman, new ByteBufOutputStream(this));
         } catch (IOException var3) {
            throw new EncoderException(var3);
         }
      }

      return this;
   }

   @Nullable
   public CompoundTag readCompoundTag() {
      return this.method_30616(new PositionTracker(2097152L));
   }

   @Nullable
   public CompoundTag method_30617() {
      return this.method_30616(PositionTracker.DEFAULT);
   }

   @Nullable
   public CompoundTag method_30616(PositionTracker _snowman) {
      int _snowmanx = this.readerIndex();
      byte _snowmanxx = this.readByte();
      if (_snowmanxx == 0) {
         return null;
      } else {
         this.readerIndex(_snowmanx);

         try {
            return NbtIo.read(new ByteBufInputStream(this), _snowman);
         } catch (IOException var5) {
            throw new EncoderException(var5);
         }
      }
   }

   public PacketByteBuf writeItemStack(ItemStack _snowman) {
      if (_snowman.isEmpty()) {
         this.writeBoolean(false);
      } else {
         this.writeBoolean(true);
         Item _snowmanx = _snowman.getItem();
         this.writeVarInt(Item.getRawId(_snowmanx));
         this.writeByte(_snowman.getCount());
         CompoundTag _snowmanxx = null;
         if (_snowmanx.isDamageable() || _snowmanx.shouldSyncTagToClient()) {
            _snowmanxx = _snowman.getTag();
         }

         this.writeCompoundTag(_snowmanxx);
      }

      return this;
   }

   public ItemStack readItemStack() {
      if (!this.readBoolean()) {
         return ItemStack.EMPTY;
      } else {
         int _snowman = this.readVarInt();
         int _snowmanx = this.readByte();
         ItemStack _snowmanxx = new ItemStack(Item.byRawId(_snowman), _snowmanx);
         _snowmanxx.setTag(this.readCompoundTag());
         return _snowmanxx;
      }
   }

   public String readString() {
      return this.readString(32767);
   }

   public String readString(int _snowman) {
      int _snowmanx = this.readVarInt();
      if (_snowmanx > _snowman * 4) {
         throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + _snowmanx + " > " + _snowman * 4 + ")");
      } else if (_snowmanx < 0) {
         throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
      } else {
         String _snowmanxx = this.toString(this.readerIndex(), _snowmanx, StandardCharsets.UTF_8);
         this.readerIndex(this.readerIndex() + _snowmanx);
         if (_snowmanxx.length() > _snowman) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + _snowmanx + " > " + _snowman + ")");
         } else {
            return _snowmanxx;
         }
      }
   }

   public PacketByteBuf writeString(String _snowman) {
      return this.writeString(_snowman, 32767);
   }

   public PacketByteBuf writeString(String _snowman, int _snowman) {
      byte[] _snowmanxx = _snowman.getBytes(StandardCharsets.UTF_8);
      if (_snowmanxx.length > _snowman) {
         throw new EncoderException("String too big (was " + _snowmanxx.length + " bytes encoded, max " + _snowman + ")");
      } else {
         this.writeVarInt(_snowmanxx.length);
         this.writeBytes(_snowmanxx);
         return this;
      }
   }

   public Identifier readIdentifier() {
      return new Identifier(this.readString(32767));
   }

   public PacketByteBuf writeIdentifier(Identifier _snowman) {
      this.writeString(_snowman.toString());
      return this;
   }

   public Date readDate() {
      return new Date(this.readLong());
   }

   public PacketByteBuf writeDate(Date _snowman) {
      this.writeLong(_snowman.getTime());
      return this;
   }

   public BlockHitResult readBlockHitResult() {
      BlockPos _snowman = this.readBlockPos();
      Direction _snowmanx = this.readEnumConstant(Direction.class);
      float _snowmanxx = this.readFloat();
      float _snowmanxxx = this.readFloat();
      float _snowmanxxxx = this.readFloat();
      boolean _snowmanxxxxx = this.readBoolean();
      return new BlockHitResult(new Vec3d((double)_snowman.getX() + (double)_snowmanxx, (double)_snowman.getY() + (double)_snowmanxxx, (double)_snowman.getZ() + (double)_snowmanxxxx), _snowmanx, _snowman, _snowmanxxxxx);
   }

   public void writeBlockHitResult(BlockHitResult _snowman) {
      BlockPos _snowmanx = _snowman.getBlockPos();
      this.writeBlockPos(_snowmanx);
      this.writeEnumConstant(_snowman.getSide());
      Vec3d _snowmanxx = _snowman.getPos();
      this.writeFloat((float)(_snowmanxx.x - (double)_snowmanx.getX()));
      this.writeFloat((float)(_snowmanxx.y - (double)_snowmanx.getY()));
      this.writeFloat((float)(_snowmanxx.z - (double)_snowmanx.getZ()));
      this.writeBoolean(_snowman.isInsideBlock());
   }

   public int capacity() {
      return this.parent.capacity();
   }

   public ByteBuf capacity(int _snowman) {
      return this.parent.capacity(_snowman);
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

   public ByteBuf order(ByteOrder _snowman) {
      return this.parent.order(_snowman);
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

   public ByteBuf readerIndex(int _snowman) {
      return this.parent.readerIndex(_snowman);
   }

   public int writerIndex() {
      return this.parent.writerIndex();
   }

   public ByteBuf writerIndex(int _snowman) {
      return this.parent.writerIndex(_snowman);
   }

   public ByteBuf setIndex(int _snowman, int _snowman) {
      return this.parent.setIndex(_snowman, _snowman);
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

   public boolean isReadable(int _snowman) {
      return this.parent.isReadable(_snowman);
   }

   public boolean isWritable() {
      return this.parent.isWritable();
   }

   public boolean isWritable(int _snowman) {
      return this.parent.isWritable(_snowman);
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

   public ByteBuf ensureWritable(int _snowman) {
      return this.parent.ensureWritable(_snowman);
   }

   public int ensureWritable(int _snowman, boolean _snowman) {
      return this.parent.ensureWritable(_snowman, _snowman);
   }

   public boolean getBoolean(int _snowman) {
      return this.parent.getBoolean(_snowman);
   }

   public byte getByte(int _snowman) {
      return this.parent.getByte(_snowman);
   }

   public short getUnsignedByte(int _snowman) {
      return this.parent.getUnsignedByte(_snowman);
   }

   public short getShort(int _snowman) {
      return this.parent.getShort(_snowman);
   }

   public short getShortLE(int _snowman) {
      return this.parent.getShortLE(_snowman);
   }

   public int getUnsignedShort(int _snowman) {
      return this.parent.getUnsignedShort(_snowman);
   }

   public int getUnsignedShortLE(int _snowman) {
      return this.parent.getUnsignedShortLE(_snowman);
   }

   public int getMedium(int _snowman) {
      return this.parent.getMedium(_snowman);
   }

   public int getMediumLE(int _snowman) {
      return this.parent.getMediumLE(_snowman);
   }

   public int getUnsignedMedium(int _snowman) {
      return this.parent.getUnsignedMedium(_snowman);
   }

   public int getUnsignedMediumLE(int _snowman) {
      return this.parent.getUnsignedMediumLE(_snowman);
   }

   public int getInt(int _snowman) {
      return this.parent.getInt(_snowman);
   }

   public int getIntLE(int _snowman) {
      return this.parent.getIntLE(_snowman);
   }

   public long getUnsignedInt(int _snowman) {
      return this.parent.getUnsignedInt(_snowman);
   }

   public long getUnsignedIntLE(int _snowman) {
      return this.parent.getUnsignedIntLE(_snowman);
   }

   public long getLong(int _snowman) {
      return this.parent.getLong(_snowman);
   }

   public long getLongLE(int _snowman) {
      return this.parent.getLongLE(_snowman);
   }

   public char getChar(int _snowman) {
      return this.parent.getChar(_snowman);
   }

   public float getFloat(int _snowman) {
      return this.parent.getFloat(_snowman);
   }

   public double getDouble(int _snowman) {
      return this.parent.getDouble(_snowman);
   }

   public ByteBuf getBytes(int _snowman, ByteBuf _snowman) {
      return this.parent.getBytes(_snowman, _snowman);
   }

   public ByteBuf getBytes(int _snowman, ByteBuf _snowman, int _snowman) {
      return this.parent.getBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf getBytes(int _snowman, ByteBuf _snowman, int _snowman, int _snowman) {
      return this.parent.getBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public ByteBuf getBytes(int _snowman, byte[] _snowman) {
      return this.parent.getBytes(_snowman, _snowman);
   }

   public ByteBuf getBytes(int _snowman, byte[] _snowman, int _snowman, int _snowman) {
      return this.parent.getBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public ByteBuf getBytes(int _snowman, ByteBuffer _snowman) {
      return this.parent.getBytes(_snowman, _snowman);
   }

   public ByteBuf getBytes(int _snowman, OutputStream _snowman, int _snowman) throws IOException {
      return this.parent.getBytes(_snowman, _snowman, _snowman);
   }

   public int getBytes(int _snowman, GatheringByteChannel _snowman, int _snowman) throws IOException {
      return this.parent.getBytes(_snowman, _snowman, _snowman);
   }

   public int getBytes(int _snowman, FileChannel _snowman, long _snowman, int _snowman) throws IOException {
      return this.parent.getBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public CharSequence getCharSequence(int _snowman, int _snowman, Charset _snowman) {
      return this.parent.getCharSequence(_snowman, _snowman, _snowman);
   }

   public ByteBuf setBoolean(int _snowman, boolean _snowman) {
      return this.parent.setBoolean(_snowman, _snowman);
   }

   public ByteBuf setByte(int _snowman, int _snowman) {
      return this.parent.setByte(_snowman, _snowman);
   }

   public ByteBuf setShort(int _snowman, int _snowman) {
      return this.parent.setShort(_snowman, _snowman);
   }

   public ByteBuf setShortLE(int _snowman, int _snowman) {
      return this.parent.setShortLE(_snowman, _snowman);
   }

   public ByteBuf setMedium(int _snowman, int _snowman) {
      return this.parent.setMedium(_snowman, _snowman);
   }

   public ByteBuf setMediumLE(int _snowman, int _snowman) {
      return this.parent.setMediumLE(_snowman, _snowman);
   }

   public ByteBuf setInt(int _snowman, int _snowman) {
      return this.parent.setInt(_snowman, _snowman);
   }

   public ByteBuf setIntLE(int _snowman, int _snowman) {
      return this.parent.setIntLE(_snowman, _snowman);
   }

   public ByteBuf setLong(int _snowman, long _snowman) {
      return this.parent.setLong(_snowman, _snowman);
   }

   public ByteBuf setLongLE(int _snowman, long _snowman) {
      return this.parent.setLongLE(_snowman, _snowman);
   }

   public ByteBuf setChar(int _snowman, int _snowman) {
      return this.parent.setChar(_snowman, _snowman);
   }

   public ByteBuf setFloat(int _snowman, float _snowman) {
      return this.parent.setFloat(_snowman, _snowman);
   }

   public ByteBuf setDouble(int _snowman, double _snowman) {
      return this.parent.setDouble(_snowman, _snowman);
   }

   public ByteBuf setBytes(int _snowman, ByteBuf _snowman) {
      return this.parent.setBytes(_snowman, _snowman);
   }

   public ByteBuf setBytes(int _snowman, ByteBuf _snowman, int _snowman) {
      return this.parent.setBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf setBytes(int _snowman, ByteBuf _snowman, int _snowman, int _snowman) {
      return this.parent.setBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public ByteBuf setBytes(int _snowman, byte[] _snowman) {
      return this.parent.setBytes(_snowman, _snowman);
   }

   public ByteBuf setBytes(int _snowman, byte[] _snowman, int _snowman, int _snowman) {
      return this.parent.setBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public ByteBuf setBytes(int _snowman, ByteBuffer _snowman) {
      return this.parent.setBytes(_snowman, _snowman);
   }

   public int setBytes(int _snowman, InputStream _snowman, int _snowman) throws IOException {
      return this.parent.setBytes(_snowman, _snowman, _snowman);
   }

   public int setBytes(int _snowman, ScatteringByteChannel _snowman, int _snowman) throws IOException {
      return this.parent.setBytes(_snowman, _snowman, _snowman);
   }

   public int setBytes(int _snowman, FileChannel _snowman, long _snowman, int _snowman) throws IOException {
      return this.parent.setBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public ByteBuf setZero(int _snowman, int _snowman) {
      return this.parent.setZero(_snowman, _snowman);
   }

   public int setCharSequence(int _snowman, CharSequence _snowman, Charset _snowman) {
      return this.parent.setCharSequence(_snowman, _snowman, _snowman);
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

   public ByteBuf readBytes(int _snowman) {
      return this.parent.readBytes(_snowman);
   }

   public ByteBuf readSlice(int _snowman) {
      return this.parent.readSlice(_snowman);
   }

   public ByteBuf readRetainedSlice(int _snowman) {
      return this.parent.readRetainedSlice(_snowman);
   }

   public ByteBuf readBytes(ByteBuf _snowman) {
      return this.parent.readBytes(_snowman);
   }

   public ByteBuf readBytes(ByteBuf _snowman, int _snowman) {
      return this.parent.readBytes(_snowman, _snowman);
   }

   public ByteBuf readBytes(ByteBuf _snowman, int _snowman, int _snowman) {
      return this.parent.readBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf readBytes(byte[] _snowman) {
      return this.parent.readBytes(_snowman);
   }

   public ByteBuf readBytes(byte[] _snowman, int _snowman, int _snowman) {
      return this.parent.readBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf readBytes(ByteBuffer _snowman) {
      return this.parent.readBytes(_snowman);
   }

   public ByteBuf readBytes(OutputStream _snowman, int _snowman) throws IOException {
      return this.parent.readBytes(_snowman, _snowman);
   }

   public int readBytes(GatheringByteChannel _snowman, int _snowman) throws IOException {
      return this.parent.readBytes(_snowman, _snowman);
   }

   public CharSequence readCharSequence(int _snowman, Charset _snowman) {
      return this.parent.readCharSequence(_snowman, _snowman);
   }

   public int readBytes(FileChannel _snowman, long _snowman, int _snowman) throws IOException {
      return this.parent.readBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf skipBytes(int _snowman) {
      return this.parent.skipBytes(_snowman);
   }

   public ByteBuf writeBoolean(boolean _snowman) {
      return this.parent.writeBoolean(_snowman);
   }

   public ByteBuf writeByte(int _snowman) {
      return this.parent.writeByte(_snowman);
   }

   public ByteBuf writeShort(int _snowman) {
      return this.parent.writeShort(_snowman);
   }

   public ByteBuf writeShortLE(int _snowman) {
      return this.parent.writeShortLE(_snowman);
   }

   public ByteBuf writeMedium(int _snowman) {
      return this.parent.writeMedium(_snowman);
   }

   public ByteBuf writeMediumLE(int _snowman) {
      return this.parent.writeMediumLE(_snowman);
   }

   public ByteBuf writeInt(int _snowman) {
      return this.parent.writeInt(_snowman);
   }

   public ByteBuf writeIntLE(int _snowman) {
      return this.parent.writeIntLE(_snowman);
   }

   public ByteBuf writeLong(long _snowman) {
      return this.parent.writeLong(_snowman);
   }

   public ByteBuf writeLongLE(long _snowman) {
      return this.parent.writeLongLE(_snowman);
   }

   public ByteBuf writeChar(int _snowman) {
      return this.parent.writeChar(_snowman);
   }

   public ByteBuf writeFloat(float _snowman) {
      return this.parent.writeFloat(_snowman);
   }

   public ByteBuf writeDouble(double _snowman) {
      return this.parent.writeDouble(_snowman);
   }

   public ByteBuf writeBytes(ByteBuf _snowman) {
      return this.parent.writeBytes(_snowman);
   }

   public ByteBuf writeBytes(ByteBuf _snowman, int _snowman) {
      return this.parent.writeBytes(_snowman, _snowman);
   }

   public ByteBuf writeBytes(ByteBuf _snowman, int _snowman, int _snowman) {
      return this.parent.writeBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf writeBytes(byte[] _snowman) {
      return this.parent.writeBytes(_snowman);
   }

   public ByteBuf writeBytes(byte[] _snowman, int _snowman, int _snowman) {
      return this.parent.writeBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf writeBytes(ByteBuffer _snowman) {
      return this.parent.writeBytes(_snowman);
   }

   public int writeBytes(InputStream _snowman, int _snowman) throws IOException {
      return this.parent.writeBytes(_snowman, _snowman);
   }

   public int writeBytes(ScatteringByteChannel _snowman, int _snowman) throws IOException {
      return this.parent.writeBytes(_snowman, _snowman);
   }

   public int writeBytes(FileChannel _snowman, long _snowman, int _snowman) throws IOException {
      return this.parent.writeBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf writeZero(int _snowman) {
      return this.parent.writeZero(_snowman);
   }

   public int writeCharSequence(CharSequence _snowman, Charset _snowman) {
      return this.parent.writeCharSequence(_snowman, _snowman);
   }

   public int indexOf(int _snowman, int _snowman, byte _snowman) {
      return this.parent.indexOf(_snowman, _snowman, _snowman);
   }

   public int bytesBefore(byte _snowman) {
      return this.parent.bytesBefore(_snowman);
   }

   public int bytesBefore(int _snowman, byte _snowman) {
      return this.parent.bytesBefore(_snowman, _snowman);
   }

   public int bytesBefore(int _snowman, int _snowman, byte _snowman) {
      return this.parent.bytesBefore(_snowman, _snowman, _snowman);
   }

   public int forEachByte(ByteProcessor _snowman) {
      return this.parent.forEachByte(_snowman);
   }

   public int forEachByte(int _snowman, int _snowman, ByteProcessor _snowman) {
      return this.parent.forEachByte(_snowman, _snowman, _snowman);
   }

   public int forEachByteDesc(ByteProcessor _snowman) {
      return this.parent.forEachByteDesc(_snowman);
   }

   public int forEachByteDesc(int _snowman, int _snowman, ByteProcessor _snowman) {
      return this.parent.forEachByteDesc(_snowman, _snowman, _snowman);
   }

   public ByteBuf copy() {
      return this.parent.copy();
   }

   public ByteBuf copy(int _snowman, int _snowman) {
      return this.parent.copy(_snowman, _snowman);
   }

   public ByteBuf slice() {
      return this.parent.slice();
   }

   public ByteBuf retainedSlice() {
      return this.parent.retainedSlice();
   }

   public ByteBuf slice(int _snowman, int _snowman) {
      return this.parent.slice(_snowman, _snowman);
   }

   public ByteBuf retainedSlice(int _snowman, int _snowman) {
      return this.parent.retainedSlice(_snowman, _snowman);
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

   public ByteBuffer nioBuffer(int _snowman, int _snowman) {
      return this.parent.nioBuffer(_snowman, _snowman);
   }

   public ByteBuffer internalNioBuffer(int _snowman, int _snowman) {
      return this.parent.internalNioBuffer(_snowman, _snowman);
   }

   public ByteBuffer[] nioBuffers() {
      return this.parent.nioBuffers();
   }

   public ByteBuffer[] nioBuffers(int _snowman, int _snowman) {
      return this.parent.nioBuffers(_snowman, _snowman);
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

   public String toString(Charset _snowman) {
      return this.parent.toString(_snowman);
   }

   public String toString(int _snowman, int _snowman, Charset _snowman) {
      return this.parent.toString(_snowman, _snowman, _snowman);
   }

   public int hashCode() {
      return this.parent.hashCode();
   }

   public boolean equals(Object _snowman) {
      return this.parent.equals(_snowman);
   }

   public int compareTo(ByteBuf _snowman) {
      return this.parent.compareTo(_snowman);
   }

   public String toString() {
      return this.parent.toString();
   }

   public ByteBuf retain(int _snowman) {
      return this.parent.retain(_snowman);
   }

   public ByteBuf retain() {
      return this.parent.retain();
   }

   public ByteBuf touch() {
      return this.parent.touch();
   }

   public ByteBuf touch(Object _snowman) {
      return this.parent.touch(_snowman);
   }

   public int refCnt() {
      return this.parent.refCnt();
   }

   public boolean release() {
      return this.parent.release();
   }

   public boolean release(int _snowman) {
      return this.parent.release(_snowman);
   }
}
