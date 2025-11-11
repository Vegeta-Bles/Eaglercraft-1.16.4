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

public class nf extends ByteBuf {
   private final ByteBuf a;

   public nf(ByteBuf var1) {
      this.a = _snowman;
   }

   public static int a(int var0) {
      for (int _snowman = 1; _snowman < 5; _snowman++) {
         if ((_snowman & -1 << _snowman * 7) == 0) {
            return _snowman;
         }
      }

      return 5;
   }

   public <T> T a(Codec<T> var1) throws IOException {
      md _snowman = this.m();
      DataResult<T> _snowmanx = _snowman.parse(mo.a, _snowman);
      if (_snowmanx.error().isPresent()) {
         throw new IOException("Failed to decode: " + ((PartialResult)_snowmanx.error().get()).message() + " " + _snowman);
      } else {
         return (T)_snowmanx.result().get();
      }
   }

   public <T> void a(Codec<T> var1, T var2) throws IOException {
      DataResult<mt> _snowman = _snowman.encodeStart(mo.a, _snowman);
      if (_snowman.error().isPresent()) {
         throw new IOException("Failed to encode: " + ((PartialResult)_snowman.error().get()).message() + " " + _snowman);
      } else {
         this.a((md)_snowman.result().get());
      }
   }

   public nf a(byte[] var1) {
      this.d(_snowman.length);
      this.writeBytes(_snowman);
      return this;
   }

   public byte[] a() {
      return this.b(this.readableBytes());
   }

   public byte[] b(int var1) {
      int _snowman = this.i();
      if (_snowman > _snowman) {
         throw new DecoderException("ByteArray with size " + _snowman + " is bigger than allowed " + _snowman);
      } else {
         byte[] _snowmanx = new byte[_snowman];
         this.readBytes(_snowmanx);
         return _snowmanx;
      }
   }

   public nf a(int[] var1) {
      this.d(_snowman.length);

      for (int _snowman : _snowman) {
         this.d(_snowman);
      }

      return this;
   }

   public int[] b() {
      return this.c(this.readableBytes());
   }

   public int[] c(int var1) {
      int _snowman = this.i();
      if (_snowman > _snowman) {
         throw new DecoderException("VarIntArray with size " + _snowman + " is bigger than allowed " + _snowman);
      } else {
         int[] _snowmanx = new int[_snowman];

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
            _snowmanx[_snowmanxx] = this.i();
         }

         return _snowmanx;
      }
   }

   public nf a(long[] var1) {
      this.d(_snowman.length);

      for (long _snowman : _snowman) {
         this.writeLong(_snowman);
      }

      return this;
   }

   public long[] b(@Nullable long[] var1) {
      return this.a(_snowman, this.readableBytes() / 8);
   }

   public long[] a(@Nullable long[] var1, int var2) {
      int _snowman = this.i();
      if (_snowman == null || _snowman.length != _snowman) {
         if (_snowman > _snowman) {
            throw new DecoderException("LongArray with size " + _snowman + " is bigger than allowed " + _snowman);
         }

         _snowman = new long[_snowman];
      }

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         _snowman[_snowmanx] = this.readLong();
      }

      return _snowman;
   }

   public fx e() {
      return fx.e(this.readLong());
   }

   public nf a(fx var1) {
      this.writeLong(_snowman.a());
      return this;
   }

   public gp g() {
      return gp.a(this.readLong());
   }

   public nr h() {
      return nr.a.a(this.e(262144));
   }

   public nf a(nr var1) {
      return this.a(nr.a.a(_snowman), 262144);
   }

   public <T extends Enum<T>> T a(Class<T> var1) {
      return _snowman.getEnumConstants()[this.i()];
   }

   public nf a(Enum<?> var1) {
      return this.d(_snowman.ordinal());
   }

   public int i() {
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

   public long j() {
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

   public nf a(UUID var1) {
      this.writeLong(_snowman.getMostSignificantBits());
      this.writeLong(_snowman.getLeastSignificantBits());
      return this;
   }

   public UUID k() {
      return new UUID(this.readLong(), this.readLong());
   }

   public nf d(int var1) {
      while ((_snowman & -128) != 0) {
         this.writeByte(_snowman & 127 | 128);
         _snowman >>>= 7;
      }

      this.writeByte(_snowman);
      return this;
   }

   public nf b(long var1) {
      while ((_snowman & -128L) != 0L) {
         this.writeByte((int)(_snowman & 127L) | 128);
         _snowman >>>= 7;
      }

      this.writeByte((int)_snowman);
      return this;
   }

   public nf a(@Nullable md var1) {
      if (_snowman == null) {
         this.writeByte(0);
      } else {
         try {
            mn.a(_snowman, new ByteBufOutputStream(this));
         } catch (IOException var3) {
            throw new EncoderException(var3);
         }
      }

      return this;
   }

   @Nullable
   public md l() {
      return this.a(new mm(2097152L));
   }

   @Nullable
   public md m() {
      return this.a(mm.a);
   }

   @Nullable
   public md a(mm var1) {
      int _snowman = this.readerIndex();
      byte _snowmanx = this.readByte();
      if (_snowmanx == 0) {
         return null;
      } else {
         this.readerIndex(_snowman);

         try {
            return mn.a(new ByteBufInputStream(this), _snowman);
         } catch (IOException var5) {
            throw new EncoderException(var5);
         }
      }
   }

   public nf a(bmb var1) {
      if (_snowman.a()) {
         this.writeBoolean(false);
      } else {
         this.writeBoolean(true);
         blx _snowman = _snowman.b();
         this.d(blx.a(_snowman));
         this.writeByte(_snowman.E());
         md _snowmanx = null;
         if (_snowman.k() || _snowman.n()) {
            _snowmanx = _snowman.o();
         }

         this.a(_snowmanx);
      }

      return this;
   }

   public bmb n() {
      if (!this.readBoolean()) {
         return bmb.b;
      } else {
         int _snowman = this.i();
         int _snowmanx = this.readByte();
         bmb _snowmanxx = new bmb(blx.b(_snowman), _snowmanx);
         _snowmanxx.c(this.l());
         return _snowmanxx;
      }
   }

   public String o() {
      return this.e(32767);
   }

   public String e(int var1) {
      int _snowman = this.i();
      if (_snowman > _snowman * 4) {
         throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + _snowman + " > " + _snowman * 4 + ")");
      } else if (_snowman < 0) {
         throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
      } else {
         String _snowmanx = this.toString(this.readerIndex(), _snowman, StandardCharsets.UTF_8);
         this.readerIndex(this.readerIndex() + _snowman);
         if (_snowmanx.length() > _snowman) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + _snowman + " > " + _snowman + ")");
         } else {
            return _snowmanx;
         }
      }
   }

   public nf a(String var1) {
      return this.a(_snowman, 32767);
   }

   public nf a(String var1, int var2) {
      byte[] _snowman = _snowman.getBytes(StandardCharsets.UTF_8);
      if (_snowman.length > _snowman) {
         throw new EncoderException("String too big (was " + _snowman.length + " bytes encoded, max " + _snowman + ")");
      } else {
         this.d(_snowman.length);
         this.writeBytes(_snowman);
         return this;
      }
   }

   public vk p() {
      return new vk(this.e(32767));
   }

   public nf a(vk var1) {
      this.a(_snowman.toString());
      return this;
   }

   public Date q() {
      return new Date(this.readLong());
   }

   public nf a(Date var1) {
      this.writeLong(_snowman.getTime());
      return this;
   }

   public dcj r() {
      fx _snowman = this.e();
      gc _snowmanx = this.a(gc.class);
      float _snowmanxx = this.readFloat();
      float _snowmanxxx = this.readFloat();
      float _snowmanxxxx = this.readFloat();
      boolean _snowmanxxxxx = this.readBoolean();
      return new dcj(new dcn((double)_snowman.u() + (double)_snowmanxx, (double)_snowman.v() + (double)_snowmanxxx, (double)_snowman.w() + (double)_snowmanxxxx), _snowmanx, _snowman, _snowmanxxxxx);
   }

   public void a(dcj var1) {
      fx _snowman = _snowman.a();
      this.a(_snowman);
      this.a(_snowman.b());
      dcn _snowmanx = _snowman.e();
      this.writeFloat((float)(_snowmanx.b - (double)_snowman.u()));
      this.writeFloat((float)(_snowmanx.c - (double)_snowman.v()));
      this.writeFloat((float)(_snowmanx.d - (double)_snowman.w()));
      this.writeBoolean(_snowman.d());
   }

   public int capacity() {
      return this.a.capacity();
   }

   public ByteBuf capacity(int var1) {
      return this.a.capacity(_snowman);
   }

   public int maxCapacity() {
      return this.a.maxCapacity();
   }

   public ByteBufAllocator alloc() {
      return this.a.alloc();
   }

   public ByteOrder order() {
      return this.a.order();
   }

   public ByteBuf order(ByteOrder var1) {
      return this.a.order(_snowman);
   }

   public ByteBuf unwrap() {
      return this.a.unwrap();
   }

   public boolean isDirect() {
      return this.a.isDirect();
   }

   public boolean isReadOnly() {
      return this.a.isReadOnly();
   }

   public ByteBuf asReadOnly() {
      return this.a.asReadOnly();
   }

   public int readerIndex() {
      return this.a.readerIndex();
   }

   public ByteBuf readerIndex(int var1) {
      return this.a.readerIndex(_snowman);
   }

   public int writerIndex() {
      return this.a.writerIndex();
   }

   public ByteBuf writerIndex(int var1) {
      return this.a.writerIndex(_snowman);
   }

   public ByteBuf setIndex(int var1, int var2) {
      return this.a.setIndex(_snowman, _snowman);
   }

   public int readableBytes() {
      return this.a.readableBytes();
   }

   public int writableBytes() {
      return this.a.writableBytes();
   }

   public int maxWritableBytes() {
      return this.a.maxWritableBytes();
   }

   public boolean isReadable() {
      return this.a.isReadable();
   }

   public boolean isReadable(int var1) {
      return this.a.isReadable(_snowman);
   }

   public boolean isWritable() {
      return this.a.isWritable();
   }

   public boolean isWritable(int var1) {
      return this.a.isWritable(_snowman);
   }

   public ByteBuf clear() {
      return this.a.clear();
   }

   public ByteBuf markReaderIndex() {
      return this.a.markReaderIndex();
   }

   public ByteBuf resetReaderIndex() {
      return this.a.resetReaderIndex();
   }

   public ByteBuf markWriterIndex() {
      return this.a.markWriterIndex();
   }

   public ByteBuf resetWriterIndex() {
      return this.a.resetWriterIndex();
   }

   public ByteBuf discardReadBytes() {
      return this.a.discardReadBytes();
   }

   public ByteBuf discardSomeReadBytes() {
      return this.a.discardSomeReadBytes();
   }

   public ByteBuf ensureWritable(int var1) {
      return this.a.ensureWritable(_snowman);
   }

   public int ensureWritable(int var1, boolean var2) {
      return this.a.ensureWritable(_snowman, _snowman);
   }

   public boolean getBoolean(int var1) {
      return this.a.getBoolean(_snowman);
   }

   public byte getByte(int var1) {
      return this.a.getByte(_snowman);
   }

   public short getUnsignedByte(int var1) {
      return this.a.getUnsignedByte(_snowman);
   }

   public short getShort(int var1) {
      return this.a.getShort(_snowman);
   }

   public short getShortLE(int var1) {
      return this.a.getShortLE(_snowman);
   }

   public int getUnsignedShort(int var1) {
      return this.a.getUnsignedShort(_snowman);
   }

   public int getUnsignedShortLE(int var1) {
      return this.a.getUnsignedShortLE(_snowman);
   }

   public int getMedium(int var1) {
      return this.a.getMedium(_snowman);
   }

   public int getMediumLE(int var1) {
      return this.a.getMediumLE(_snowman);
   }

   public int getUnsignedMedium(int var1) {
      return this.a.getUnsignedMedium(_snowman);
   }

   public int getUnsignedMediumLE(int var1) {
      return this.a.getUnsignedMediumLE(_snowman);
   }

   public int getInt(int var1) {
      return this.a.getInt(_snowman);
   }

   public int getIntLE(int var1) {
      return this.a.getIntLE(_snowman);
   }

   public long getUnsignedInt(int var1) {
      return this.a.getUnsignedInt(_snowman);
   }

   public long getUnsignedIntLE(int var1) {
      return this.a.getUnsignedIntLE(_snowman);
   }

   public long getLong(int var1) {
      return this.a.getLong(_snowman);
   }

   public long getLongLE(int var1) {
      return this.a.getLongLE(_snowman);
   }

   public char getChar(int var1) {
      return this.a.getChar(_snowman);
   }

   public float getFloat(int var1) {
      return this.a.getFloat(_snowman);
   }

   public double getDouble(int var1) {
      return this.a.getDouble(_snowman);
   }

   public ByteBuf getBytes(int var1, ByteBuf var2) {
      return this.a.getBytes(_snowman, _snowman);
   }

   public ByteBuf getBytes(int var1, ByteBuf var2, int var3) {
      return this.a.getBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf getBytes(int var1, ByteBuf var2, int var3, int var4) {
      return this.a.getBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public ByteBuf getBytes(int var1, byte[] var2) {
      return this.a.getBytes(_snowman, _snowman);
   }

   public ByteBuf getBytes(int var1, byte[] var2, int var3, int var4) {
      return this.a.getBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public ByteBuf getBytes(int var1, ByteBuffer var2) {
      return this.a.getBytes(_snowman, _snowman);
   }

   public ByteBuf getBytes(int var1, OutputStream var2, int var3) throws IOException {
      return this.a.getBytes(_snowman, _snowman, _snowman);
   }

   public int getBytes(int var1, GatheringByteChannel var2, int var3) throws IOException {
      return this.a.getBytes(_snowman, _snowman, _snowman);
   }

   public int getBytes(int var1, FileChannel var2, long var3, int var5) throws IOException {
      return this.a.getBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public CharSequence getCharSequence(int var1, int var2, Charset var3) {
      return this.a.getCharSequence(_snowman, _snowman, _snowman);
   }

   public ByteBuf setBoolean(int var1, boolean var2) {
      return this.a.setBoolean(_snowman, _snowman);
   }

   public ByteBuf setByte(int var1, int var2) {
      return this.a.setByte(_snowman, _snowman);
   }

   public ByteBuf setShort(int var1, int var2) {
      return this.a.setShort(_snowman, _snowman);
   }

   public ByteBuf setShortLE(int var1, int var2) {
      return this.a.setShortLE(_snowman, _snowman);
   }

   public ByteBuf setMedium(int var1, int var2) {
      return this.a.setMedium(_snowman, _snowman);
   }

   public ByteBuf setMediumLE(int var1, int var2) {
      return this.a.setMediumLE(_snowman, _snowman);
   }

   public ByteBuf setInt(int var1, int var2) {
      return this.a.setInt(_snowman, _snowman);
   }

   public ByteBuf setIntLE(int var1, int var2) {
      return this.a.setIntLE(_snowman, _snowman);
   }

   public ByteBuf setLong(int var1, long var2) {
      return this.a.setLong(_snowman, _snowman);
   }

   public ByteBuf setLongLE(int var1, long var2) {
      return this.a.setLongLE(_snowman, _snowman);
   }

   public ByteBuf setChar(int var1, int var2) {
      return this.a.setChar(_snowman, _snowman);
   }

   public ByteBuf setFloat(int var1, float var2) {
      return this.a.setFloat(_snowman, _snowman);
   }

   public ByteBuf setDouble(int var1, double var2) {
      return this.a.setDouble(_snowman, _snowman);
   }

   public ByteBuf setBytes(int var1, ByteBuf var2) {
      return this.a.setBytes(_snowman, _snowman);
   }

   public ByteBuf setBytes(int var1, ByteBuf var2, int var3) {
      return this.a.setBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf setBytes(int var1, ByteBuf var2, int var3, int var4) {
      return this.a.setBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public ByteBuf setBytes(int var1, byte[] var2) {
      return this.a.setBytes(_snowman, _snowman);
   }

   public ByteBuf setBytes(int var1, byte[] var2, int var3, int var4) {
      return this.a.setBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public ByteBuf setBytes(int var1, ByteBuffer var2) {
      return this.a.setBytes(_snowman, _snowman);
   }

   public int setBytes(int var1, InputStream var2, int var3) throws IOException {
      return this.a.setBytes(_snowman, _snowman, _snowman);
   }

   public int setBytes(int var1, ScatteringByteChannel var2, int var3) throws IOException {
      return this.a.setBytes(_snowman, _snowman, _snowman);
   }

   public int setBytes(int var1, FileChannel var2, long var3, int var5) throws IOException {
      return this.a.setBytes(_snowman, _snowman, _snowman, _snowman);
   }

   public ByteBuf setZero(int var1, int var2) {
      return this.a.setZero(_snowman, _snowman);
   }

   public int setCharSequence(int var1, CharSequence var2, Charset var3) {
      return this.a.setCharSequence(_snowman, _snowman, _snowman);
   }

   public boolean readBoolean() {
      return this.a.readBoolean();
   }

   public byte readByte() {
      return this.a.readByte();
   }

   public short readUnsignedByte() {
      return this.a.readUnsignedByte();
   }

   public short readShort() {
      return this.a.readShort();
   }

   public short readShortLE() {
      return this.a.readShortLE();
   }

   public int readUnsignedShort() {
      return this.a.readUnsignedShort();
   }

   public int readUnsignedShortLE() {
      return this.a.readUnsignedShortLE();
   }

   public int readMedium() {
      return this.a.readMedium();
   }

   public int readMediumLE() {
      return this.a.readMediumLE();
   }

   public int readUnsignedMedium() {
      return this.a.readUnsignedMedium();
   }

   public int readUnsignedMediumLE() {
      return this.a.readUnsignedMediumLE();
   }

   public int readInt() {
      return this.a.readInt();
   }

   public int readIntLE() {
      return this.a.readIntLE();
   }

   public long readUnsignedInt() {
      return this.a.readUnsignedInt();
   }

   public long readUnsignedIntLE() {
      return this.a.readUnsignedIntLE();
   }

   public long readLong() {
      return this.a.readLong();
   }

   public long readLongLE() {
      return this.a.readLongLE();
   }

   public char readChar() {
      return this.a.readChar();
   }

   public float readFloat() {
      return this.a.readFloat();
   }

   public double readDouble() {
      return this.a.readDouble();
   }

   public ByteBuf readBytes(int var1) {
      return this.a.readBytes(_snowman);
   }

   public ByteBuf readSlice(int var1) {
      return this.a.readSlice(_snowman);
   }

   public ByteBuf readRetainedSlice(int var1) {
      return this.a.readRetainedSlice(_snowman);
   }

   public ByteBuf readBytes(ByteBuf var1) {
      return this.a.readBytes(_snowman);
   }

   public ByteBuf readBytes(ByteBuf var1, int var2) {
      return this.a.readBytes(_snowman, _snowman);
   }

   public ByteBuf readBytes(ByteBuf var1, int var2, int var3) {
      return this.a.readBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf readBytes(byte[] var1) {
      return this.a.readBytes(_snowman);
   }

   public ByteBuf readBytes(byte[] var1, int var2, int var3) {
      return this.a.readBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf readBytes(ByteBuffer var1) {
      return this.a.readBytes(_snowman);
   }

   public ByteBuf readBytes(OutputStream var1, int var2) throws IOException {
      return this.a.readBytes(_snowman, _snowman);
   }

   public int readBytes(GatheringByteChannel var1, int var2) throws IOException {
      return this.a.readBytes(_snowman, _snowman);
   }

   public CharSequence readCharSequence(int var1, Charset var2) {
      return this.a.readCharSequence(_snowman, _snowman);
   }

   public int readBytes(FileChannel var1, long var2, int var4) throws IOException {
      return this.a.readBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf skipBytes(int var1) {
      return this.a.skipBytes(_snowman);
   }

   public ByteBuf writeBoolean(boolean var1) {
      return this.a.writeBoolean(_snowman);
   }

   public ByteBuf writeByte(int var1) {
      return this.a.writeByte(_snowman);
   }

   public ByteBuf writeShort(int var1) {
      return this.a.writeShort(_snowman);
   }

   public ByteBuf writeShortLE(int var1) {
      return this.a.writeShortLE(_snowman);
   }

   public ByteBuf writeMedium(int var1) {
      return this.a.writeMedium(_snowman);
   }

   public ByteBuf writeMediumLE(int var1) {
      return this.a.writeMediumLE(_snowman);
   }

   public ByteBuf writeInt(int var1) {
      return this.a.writeInt(_snowman);
   }

   public ByteBuf writeIntLE(int var1) {
      return this.a.writeIntLE(_snowman);
   }

   public ByteBuf writeLong(long var1) {
      return this.a.writeLong(_snowman);
   }

   public ByteBuf writeLongLE(long var1) {
      return this.a.writeLongLE(_snowman);
   }

   public ByteBuf writeChar(int var1) {
      return this.a.writeChar(_snowman);
   }

   public ByteBuf writeFloat(float var1) {
      return this.a.writeFloat(_snowman);
   }

   public ByteBuf writeDouble(double var1) {
      return this.a.writeDouble(_snowman);
   }

   public ByteBuf writeBytes(ByteBuf var1) {
      return this.a.writeBytes(_snowman);
   }

   public ByteBuf writeBytes(ByteBuf var1, int var2) {
      return this.a.writeBytes(_snowman, _snowman);
   }

   public ByteBuf writeBytes(ByteBuf var1, int var2, int var3) {
      return this.a.writeBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf writeBytes(byte[] var1) {
      return this.a.writeBytes(_snowman);
   }

   public ByteBuf writeBytes(byte[] var1, int var2, int var3) {
      return this.a.writeBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf writeBytes(ByteBuffer var1) {
      return this.a.writeBytes(_snowman);
   }

   public int writeBytes(InputStream var1, int var2) throws IOException {
      return this.a.writeBytes(_snowman, _snowman);
   }

   public int writeBytes(ScatteringByteChannel var1, int var2) throws IOException {
      return this.a.writeBytes(_snowman, _snowman);
   }

   public int writeBytes(FileChannel var1, long var2, int var4) throws IOException {
      return this.a.writeBytes(_snowman, _snowman, _snowman);
   }

   public ByteBuf writeZero(int var1) {
      return this.a.writeZero(_snowman);
   }

   public int writeCharSequence(CharSequence var1, Charset var2) {
      return this.a.writeCharSequence(_snowman, _snowman);
   }

   public int indexOf(int var1, int var2, byte var3) {
      return this.a.indexOf(_snowman, _snowman, _snowman);
   }

   public int bytesBefore(byte var1) {
      return this.a.bytesBefore(_snowman);
   }

   public int bytesBefore(int var1, byte var2) {
      return this.a.bytesBefore(_snowman, _snowman);
   }

   public int bytesBefore(int var1, int var2, byte var3) {
      return this.a.bytesBefore(_snowman, _snowman, _snowman);
   }

   public int forEachByte(ByteProcessor var1) {
      return this.a.forEachByte(_snowman);
   }

   public int forEachByte(int var1, int var2, ByteProcessor var3) {
      return this.a.forEachByte(_snowman, _snowman, _snowman);
   }

   public int forEachByteDesc(ByteProcessor var1) {
      return this.a.forEachByteDesc(_snowman);
   }

   public int forEachByteDesc(int var1, int var2, ByteProcessor var3) {
      return this.a.forEachByteDesc(_snowman, _snowman, _snowman);
   }

   public ByteBuf copy() {
      return this.a.copy();
   }

   public ByteBuf copy(int var1, int var2) {
      return this.a.copy(_snowman, _snowman);
   }

   public ByteBuf slice() {
      return this.a.slice();
   }

   public ByteBuf retainedSlice() {
      return this.a.retainedSlice();
   }

   public ByteBuf slice(int var1, int var2) {
      return this.a.slice(_snowman, _snowman);
   }

   public ByteBuf retainedSlice(int var1, int var2) {
      return this.a.retainedSlice(_snowman, _snowman);
   }

   public ByteBuf duplicate() {
      return this.a.duplicate();
   }

   public ByteBuf retainedDuplicate() {
      return this.a.retainedDuplicate();
   }

   public int nioBufferCount() {
      return this.a.nioBufferCount();
   }

   public ByteBuffer nioBuffer() {
      return this.a.nioBuffer();
   }

   public ByteBuffer nioBuffer(int var1, int var2) {
      return this.a.nioBuffer(_snowman, _snowman);
   }

   public ByteBuffer internalNioBuffer(int var1, int var2) {
      return this.a.internalNioBuffer(_snowman, _snowman);
   }

   public ByteBuffer[] nioBuffers() {
      return this.a.nioBuffers();
   }

   public ByteBuffer[] nioBuffers(int var1, int var2) {
      return this.a.nioBuffers(_snowman, _snowman);
   }

   public boolean hasArray() {
      return this.a.hasArray();
   }

   public byte[] array() {
      return this.a.array();
   }

   public int arrayOffset() {
      return this.a.arrayOffset();
   }

   public boolean hasMemoryAddress() {
      return this.a.hasMemoryAddress();
   }

   public long memoryAddress() {
      return this.a.memoryAddress();
   }

   public String toString(Charset var1) {
      return this.a.toString(_snowman);
   }

   public String toString(int var1, int var2, Charset var3) {
      return this.a.toString(_snowman, _snowman, _snowman);
   }

   public int hashCode() {
      return this.a.hashCode();
   }

   public boolean equals(Object var1) {
      return this.a.equals(_snowman);
   }

   public int compareTo(ByteBuf var1) {
      return this.a.compareTo(_snowman);
   }

   public String toString() {
      return this.a.toString();
   }

   public ByteBuf retain(int var1) {
      return this.a.retain(_snowman);
   }

   public ByteBuf retain() {
      return this.a.retain();
   }

   public ByteBuf touch() {
      return this.a.touch();
   }

   public ByteBuf touch(Object var1) {
      return this.a.touch(_snowman);
   }

   public int refCnt() {
      return this.a.refCnt();
   }

   public boolean release() {
      return this.a.release();
   }

   public boolean release(int var1) {
      return this.a.release(_snowman);
   }
}
