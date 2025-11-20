package net.minecraft.world.storage;

import com.google.common.annotations.VisibleForTesting;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegionFile implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ByteBuffer ZERO = ByteBuffer.allocateDirect(1);
   private final FileChannel channel;
   private final Path directory;
   private final ChunkStreamVersion outputChunkStreamVersion;
   private final ByteBuffer header = ByteBuffer.allocateDirect(8192);
   private final IntBuffer sectorData;
   private final IntBuffer saveTimes;
   @VisibleForTesting
   protected final SectorMap sectors = new SectorMap();

   public RegionFile(File file, File directory, boolean dsync) throws IOException {
      this(file.toPath(), directory.toPath(), ChunkStreamVersion.DEFLATE, dsync);
   }

   public RegionFile(Path file, Path directory, ChunkStreamVersion outputChunkStreamVersion, boolean dsync) throws IOException {
      this.outputChunkStreamVersion = outputChunkStreamVersion;
      if (!Files.isDirectory(directory)) {
         throw new IllegalArgumentException("Expected directory, got " + directory.toAbsolutePath());
      } else {
         this.directory = directory;
         this.sectorData = this.header.asIntBuffer();
         ((Buffer)this.sectorData).limit(1024);
         ((Buffer)this.header).position(4096);
         this.saveTimes = this.header.asIntBuffer();
         if (dsync) {
            this.channel = FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC);
         } else {
            this.channel = FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
         }

         this.sectors.allocate(0, 2);
         ((Buffer)this.header).position(0);
         int _snowman = this.channel.read(this.header, 0L);
         if (_snowman != -1) {
            if (_snowman != 8192) {
               LOGGER.warn("Region file {} has truncated header: {}", file, _snowman);
            }

            long _snowmanx = Files.size(file);

            for (int _snowmanxx = 0; _snowmanxx < 1024; _snowmanxx++) {
               int _snowmanxxx = this.sectorData.get(_snowmanxx);
               if (_snowmanxxx != 0) {
                  int _snowmanxxxx = getOffset(_snowmanxxx);
                  int _snowmanxxxxx = getSize(_snowmanxxx);
                  if (_snowmanxxxx < 2) {
                     LOGGER.warn("Region file {} has invalid sector at index: {}; sector {} overlaps with header", file, _snowmanxx, _snowmanxxxx);
                     this.sectorData.put(_snowmanxx, 0);
                  } else if (_snowmanxxxxx == 0) {
                     LOGGER.warn("Region file {} has an invalid sector at index: {}; size has to be > 0", file, _snowmanxx);
                     this.sectorData.put(_snowmanxx, 0);
                  } else if ((long)_snowmanxxxx * 4096L > _snowmanx) {
                     LOGGER.warn("Region file {} has an invalid sector at index: {}; sector {} is out of bounds", file, _snowmanxx, _snowmanxxxx);
                     this.sectorData.put(_snowmanxx, 0);
                  } else {
                     this.sectors.allocate(_snowmanxxxx, _snowmanxxxxx);
                  }
               }
            }
         }
      }
   }

   private Path getExternalChunkPath(ChunkPos _snowman) {
      String _snowmanx = "c." + _snowman.x + "." + _snowman.z + ".mcc";
      return this.directory.resolve(_snowmanx);
   }

   @Nullable
   public synchronized DataInputStream getChunkInputStream(ChunkPos pos) throws IOException {
      int _snowman = this.getSectorData(pos);
      if (_snowman == 0) {
         return null;
      } else {
         int _snowmanx = getOffset(_snowman);
         int _snowmanxx = getSize(_snowman);
         int _snowmanxxx = _snowmanxx * 4096;
         ByteBuffer _snowmanxxxx = ByteBuffer.allocate(_snowmanxxx);
         this.channel.read(_snowmanxxxx, (long)(_snowmanx * 4096));
         ((Buffer)_snowmanxxxx).flip();
         if (_snowmanxxxx.remaining() < 5) {
            LOGGER.error("Chunk {} header is truncated: expected {} but read {}", pos, _snowmanxxx, _snowmanxxxx.remaining());
            return null;
         } else {
            int _snowmanxxxxx = _snowmanxxxx.getInt();
            byte _snowmanxxxxxx = _snowmanxxxx.get();
            if (_snowmanxxxxx == 0) {
               LOGGER.warn("Chunk {} is allocated, but stream is missing", pos);
               return null;
            } else {
               int _snowmanxxxxxxx = _snowmanxxxxx - 1;
               if (hasChunkStreamVersionId(_snowmanxxxxxx)) {
                  if (_snowmanxxxxxxx != 0) {
                     LOGGER.warn("Chunk has both internal and external streams");
                  }

                  return this.method_22408(pos, getChunkStreamVersionId(_snowmanxxxxxx));
               } else if (_snowmanxxxxxxx > _snowmanxxxx.remaining()) {
                  LOGGER.error("Chunk {} stream is truncated: expected {} but read {}", pos, _snowmanxxxxxxx, _snowmanxxxx.remaining());
                  return null;
               } else if (_snowmanxxxxxxx < 0) {
                  LOGGER.error("Declared size {} of chunk {} is negative", _snowmanxxxxx, pos);
                  return null;
               } else {
                  return this.method_22409(pos, _snowmanxxxxxx, getInputStream(_snowmanxxxx, _snowmanxxxxxxx));
               }
            }
         }
      }
   }

   private static boolean hasChunkStreamVersionId(byte _snowman) {
      return (_snowman & 128) != 0;
   }

   private static byte getChunkStreamVersionId(byte _snowman) {
      return (byte)(_snowman & -129);
   }

   @Nullable
   private DataInputStream method_22409(ChunkPos _snowman, byte _snowman, InputStream _snowman) throws IOException {
      ChunkStreamVersion _snowmanxxx = ChunkStreamVersion.get(_snowman);
      if (_snowmanxxx == null) {
         LOGGER.error("Chunk {} has invalid chunk stream version {}", _snowman, _snowman);
         return null;
      } else {
         return new DataInputStream(new BufferedInputStream(_snowmanxxx.wrap(_snowman)));
      }
   }

   @Nullable
   private DataInputStream method_22408(ChunkPos _snowman, byte _snowman) throws IOException {
      Path _snowmanxx = this.getExternalChunkPath(_snowman);
      if (!Files.isRegularFile(_snowmanxx)) {
         LOGGER.error("External chunk path {} is not file", _snowmanxx);
         return null;
      } else {
         return this.method_22409(_snowman, _snowman, Files.newInputStream(_snowmanxx));
      }
   }

   private static ByteArrayInputStream getInputStream(ByteBuffer buffer, int length) {
      return new ByteArrayInputStream(buffer.array(), buffer.position(), length);
   }

   private int packSectorData(int offset, int size) {
      return offset << 8 | size;
   }

   private static int getSize(int sectorData) {
      return sectorData & 0xFF;
   }

   private static int getOffset(int sectorData) {
      return sectorData >> 8 & 16777215;
   }

   private static int getSectorCount(int byteCount) {
      return (byteCount + 4096 - 1) / 4096;
   }

   public boolean isChunkValid(ChunkPos pos) {
      int _snowman = this.getSectorData(pos);
      if (_snowman == 0) {
         return false;
      } else {
         int _snowmanx = getOffset(_snowman);
         int _snowmanxx = getSize(_snowman);
         ByteBuffer _snowmanxxx = ByteBuffer.allocate(5);

         try {
            this.channel.read(_snowmanxxx, (long)(_snowmanx * 4096));
            ((Buffer)_snowmanxxx).flip();
            if (_snowmanxxx.remaining() != 5) {
               return false;
            } else {
               int _snowmanxxxx = _snowmanxxx.getInt();
               byte _snowmanxxxxx = _snowmanxxx.get();
               if (hasChunkStreamVersionId(_snowmanxxxxx)) {
                  if (!ChunkStreamVersion.exists(getChunkStreamVersionId(_snowmanxxxxx))) {
                     return false;
                  }

                  if (!Files.isRegularFile(this.getExternalChunkPath(pos))) {
                     return false;
                  }
               } else {
                  if (!ChunkStreamVersion.exists(_snowmanxxxxx)) {
                     return false;
                  }

                  if (_snowmanxxxx == 0) {
                     return false;
                  }

                  int _snowmanxxxxxx = _snowmanxxxx - 1;
                  if (_snowmanxxxxxx < 0 || _snowmanxxxxxx > 4096 * _snowmanxx) {
                     return false;
                  }
               }

               return true;
            }
         } catch (IOException var9) {
            return false;
         }
      }
   }

   public DataOutputStream getChunkOutputStream(ChunkPos pos) throws IOException {
      return new DataOutputStream(new BufferedOutputStream(this.outputChunkStreamVersion.wrap(new RegionFile.ChunkBuffer(pos))));
   }

   public void method_26981() throws IOException {
      this.channel.force(true);
   }

   protected synchronized void writeChunk(ChunkPos pos, ByteBuffer _snowman) throws IOException {
      int _snowmanx = getIndex(pos);
      int _snowmanxx = this.sectorData.get(_snowmanx);
      int _snowmanxxx = getOffset(_snowmanxx);
      int _snowmanxxxx = getSize(_snowmanxx);
      int _snowmanxxxxx = _snowman.remaining();
      int _snowmanxxxxxx = getSectorCount(_snowmanxxxxx);
      int _snowmanxxxxxxx;
      RegionFile.OutputAction _snowmanxxxxxxxx;
      if (_snowmanxxxxxx >= 256) {
         Path _snowmanxxxxxxxxx = this.getExternalChunkPath(pos);
         LOGGER.warn("Saving oversized chunk {} ({} bytes} to external file {}", pos, _snowmanxxxxx, _snowmanxxxxxxxxx);
         _snowmanxxxxxx = 1;
         _snowmanxxxxxxx = this.sectors.allocate(_snowmanxxxxxx);
         _snowmanxxxxxxxx = this.writeSafely(_snowmanxxxxxxxxx, _snowman);
         ByteBuffer _snowmanxxxxxxxxxx = this.method_22406();
         this.channel.write(_snowmanxxxxxxxxxx, (long)(_snowmanxxxxxxx * 4096));
      } else {
         _snowmanxxxxxxx = this.sectors.allocate(_snowmanxxxxxx);
         _snowmanxxxxxxxx = () -> Files.deleteIfExists(this.getExternalChunkPath(pos));
         this.channel.write(_snowman, (long)(_snowmanxxxxxxx * 4096));
      }

      int _snowmanxxxxxxxxx = (int)(Util.getEpochTimeMs() / 1000L);
      this.sectorData.put(_snowmanx, this.packSectorData(_snowmanxxxxxxx, _snowmanxxxxxx));
      this.saveTimes.put(_snowmanx, _snowmanxxxxxxxxx);
      this.writeHeader();
      _snowmanxxxxxxxx.run();
      if (_snowmanxxx != 0) {
         this.sectors.free(_snowmanxxx, _snowmanxxxx);
      }
   }

   private ByteBuffer method_22406() {
      ByteBuffer _snowman = ByteBuffer.allocate(5);
      _snowman.putInt(1);
      _snowman.put((byte)(this.outputChunkStreamVersion.getId() | 128));
      ((Buffer)_snowman).flip();
      return _snowman;
   }

   private RegionFile.OutputAction writeSafely(Path _snowman, ByteBuffer _snowman) throws IOException {
      Path _snowmanxx = Files.createTempFile(this.directory, "tmp", null);

      try (FileChannel _snowmanxxx = FileChannel.open(_snowmanxx, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
         ((Buffer)_snowman).position(5);
         _snowmanxxx.write(_snowman);
      }

      return () -> Files.move(_snowman, _snowman, StandardCopyOption.REPLACE_EXISTING);
   }

   private void writeHeader() throws IOException {
      ((Buffer)this.header).position(0);
      this.channel.write(this.header, 0L);
   }

   private int getSectorData(ChunkPos pos) {
      return this.sectorData.get(getIndex(pos));
   }

   public boolean hasChunk(ChunkPos pos) {
      return this.getSectorData(pos) != 0;
   }

   private static int getIndex(ChunkPos pos) {
      return pos.getRegionRelativeX() + pos.getRegionRelativeZ() * 32;
   }

   @Override
   public void close() throws IOException {
      try {
         this.fillLastSector();
      } finally {
         try {
            this.channel.force(true);
         } finally {
            this.channel.close();
         }
      }
   }

   private void fillLastSector() throws IOException {
      int _snowman = (int)this.channel.size();
      int _snowmanx = getSectorCount(_snowman) * 4096;
      if (_snowman != _snowmanx) {
         ByteBuffer _snowmanxx = ZERO.duplicate();
         ((Buffer)_snowmanxx).position(0);
         this.channel.write(_snowmanxx, (long)(_snowmanx - 1));
      }
   }

   class ChunkBuffer extends ByteArrayOutputStream {
      private final ChunkPos pos;

      public ChunkBuffer(ChunkPos var2) {
         super(8096);
         super.write(0);
         super.write(0);
         super.write(0);
         super.write(0);
         super.write(RegionFile.this.outputChunkStreamVersion.getId());
         this.pos = _snowman;
      }

      @Override
      public void close() throws IOException {
         ByteBuffer _snowman = ByteBuffer.wrap(this.buf, 0, this.count);
         _snowman.putInt(0, this.count - 5 + 1);
         RegionFile.this.writeChunk(this.pos, _snowman);
      }
   }

   interface OutputAction {
      void run() throws IOException;
   }
}
