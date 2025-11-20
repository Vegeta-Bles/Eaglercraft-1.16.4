/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
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
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.storage.ChunkStreamVersion;
import net.minecraft.world.storage.SectorMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegionFile
implements AutoCloseable {
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
        if (!Files.isDirectory(directory, new LinkOption[0])) {
            throw new IllegalArgumentException("Expected directory, got " + directory.toAbsolutePath());
        }
        this.directory = directory;
        this.sectorData = this.header.asIntBuffer();
        this.sectorData.limit(1024);
        this.header.position(4096);
        this.saveTimes = this.header.asIntBuffer();
        this.channel = dsync ? FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC) : FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
        this.sectors.allocate(0, 2);
        this.header.position(0);
        int n = this.channel.read(this.header, 0L);
        if (n != -1) {
            if (n != 8192) {
                LOGGER.warn("Region file {} has truncated header: {}", (Object)file, (Object)n);
            }
            long l = Files.size(file);
            for (int i = 0; i < 1024; ++i) {
                _snowman = this.sectorData.get(i);
                if (_snowman == 0) continue;
                _snowman = RegionFile.getOffset(_snowman);
                _snowman = RegionFile.getSize(_snowman);
                if (_snowman < 2) {
                    LOGGER.warn("Region file {} has invalid sector at index: {}; sector {} overlaps with header", (Object)file, (Object)i, (Object)_snowman);
                    this.sectorData.put(i, 0);
                    continue;
                }
                if (_snowman == 0) {
                    LOGGER.warn("Region file {} has an invalid sector at index: {}; size has to be > 0", (Object)file, (Object)i);
                    this.sectorData.put(i, 0);
                    continue;
                }
                if ((long)_snowman * 4096L > l) {
                    LOGGER.warn("Region file {} has an invalid sector at index: {}; sector {} is out of bounds", (Object)file, (Object)i, (Object)_snowman);
                    this.sectorData.put(i, 0);
                    continue;
                }
                this.sectors.allocate(_snowman, _snowman);
            }
        }
    }

    private Path getExternalChunkPath(ChunkPos chunkPos) {
        String string = "c." + chunkPos.x + "." + chunkPos.z + ".mcc";
        return this.directory.resolve(string);
    }

    @Nullable
    public synchronized DataInputStream getChunkInputStream(ChunkPos pos) throws IOException {
        int n = this.getSectorData(pos);
        if (n == 0) {
            return null;
        }
        _snowman = RegionFile.getOffset(n);
        _snowman = RegionFile.getSize(n);
        _snowman = _snowman * 4096;
        ByteBuffer _snowman2 = ByteBuffer.allocate(_snowman);
        this.channel.read(_snowman2, _snowman * 4096);
        _snowman2.flip();
        if (_snowman2.remaining() < 5) {
            LOGGER.error("Chunk {} header is truncated: expected {} but read {}", (Object)pos, (Object)_snowman, (Object)_snowman2.remaining());
            return null;
        }
        _snowman = _snowman2.getInt();
        byte _snowman3 = _snowman2.get();
        if (_snowman == 0) {
            LOGGER.warn("Chunk {} is allocated, but stream is missing", (Object)pos);
            return null;
        }
        _snowman = _snowman - 1;
        if (RegionFile.hasChunkStreamVersionId(_snowman3)) {
            if (_snowman != 0) {
                LOGGER.warn("Chunk has both internal and external streams");
            }
            return this.method_22408(pos, RegionFile.getChunkStreamVersionId(_snowman3));
        }
        if (_snowman > _snowman2.remaining()) {
            LOGGER.error("Chunk {} stream is truncated: expected {} but read {}", (Object)pos, (Object)_snowman, (Object)_snowman2.remaining());
            return null;
        }
        if (_snowman < 0) {
            LOGGER.error("Declared size {} of chunk {} is negative", (Object)_snowman, (Object)pos);
            return null;
        }
        return this.method_22409(pos, _snowman3, RegionFile.getInputStream(_snowman2, _snowman));
    }

    private static boolean hasChunkStreamVersionId(byte by) {
        return (by & 0x80) != 0;
    }

    private static byte getChunkStreamVersionId(byte by) {
        return (byte)(by & 0xFFFFFF7F);
    }

    @Nullable
    private DataInputStream method_22409(ChunkPos chunkPos, byte by, InputStream inputStream) throws IOException {
        ChunkStreamVersion chunkStreamVersion = ChunkStreamVersion.get(by);
        if (chunkStreamVersion == null) {
            LOGGER.error("Chunk {} has invalid chunk stream version {}", (Object)chunkPos, (Object)by);
            return null;
        }
        return new DataInputStream(new BufferedInputStream(chunkStreamVersion.wrap(inputStream)));
    }

    @Nullable
    private DataInputStream method_22408(ChunkPos chunkPos, byte by) throws IOException {
        Path path = this.getExternalChunkPath(chunkPos);
        if (!Files.isRegularFile(path, new LinkOption[0])) {
            LOGGER.error("External chunk path {} is not file", (Object)path);
            return null;
        }
        return this.method_22409(chunkPos, by, Files.newInputStream(path, new OpenOption[0]));
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
        return sectorData >> 8 & 0xFFFFFF;
    }

    private static int getSectorCount(int byteCount) {
        return (byteCount + 4096 - 1) / 4096;
    }

    public boolean isChunkValid(ChunkPos pos) {
        int n = this.getSectorData(pos);
        if (n == 0) {
            return false;
        }
        _snowman = RegionFile.getOffset(n);
        _snowman = RegionFile.getSize(n);
        ByteBuffer _snowman2 = ByteBuffer.allocate(5);
        try {
            this.channel.read(_snowman2, _snowman * 4096);
            _snowman2.flip();
            if (_snowman2.remaining() != 5) {
                return false;
            }
            _snowman = _snowman2.getInt();
            byte by = _snowman2.get();
            if (RegionFile.hasChunkStreamVersionId(by)) {
                if (!ChunkStreamVersion.exists(RegionFile.getChunkStreamVersionId(by))) {
                    return false;
                }
                if (!Files.isRegularFile(this.getExternalChunkPath(pos), new LinkOption[0])) {
                    return false;
                }
            } else {
                if (!ChunkStreamVersion.exists(by)) {
                    return false;
                }
                if (_snowman == 0) {
                    return false;
                }
                int n2 = _snowman - 1;
                if (n2 < 0 || n2 > 4096 * _snowman) {
                    return false;
                }
            }
        }
        catch (IOException iOException) {
            return false;
        }
        return true;
    }

    public DataOutputStream getChunkOutputStream(ChunkPos pos) throws IOException {
        return new DataOutputStream(new BufferedOutputStream(this.outputChunkStreamVersion.wrap(new ChunkBuffer(pos))));
    }

    public void method_26981() throws IOException {
        this.channel.force(true);
    }

    protected synchronized void writeChunk(ChunkPos pos, ByteBuffer byteBuffer) throws IOException {
        OutputAction _snowman4;
        int _snowman3;
        int _snowman2;
        int n = RegionFile.getIndex(pos);
        _snowman = this.sectorData.get(n);
        _snowman = RegionFile.getOffset(_snowman);
        _snowman = RegionFile.getSize(_snowman);
        _snowman = byteBuffer.remaining();
        _snowman2 = RegionFile.getSectorCount(_snowman);
        if (_snowman2 >= 256) {
            Path path = this.getExternalChunkPath(pos);
            LOGGER.warn("Saving oversized chunk {} ({} bytes} to external file {}", (Object)pos, (Object)_snowman, (Object)path);
            _snowman2 = 1;
            _snowman3 = this.sectors.allocate(_snowman2);
            _snowman4 = this.writeSafely(path, byteBuffer);
            ByteBuffer _snowman5 = this.method_22406();
            this.channel.write(_snowman5, _snowman3 * 4096);
        } else {
            _snowman3 = this.sectors.allocate(_snowman2);
            _snowman4 = () -> Files.deleteIfExists(this.getExternalChunkPath(pos));
            this.channel.write(byteBuffer, _snowman3 * 4096);
        }
        int n2 = (int)(Util.getEpochTimeMs() / 1000L);
        this.sectorData.put(n, this.packSectorData(_snowman3, _snowman2));
        this.saveTimes.put(n, n2);
        this.writeHeader();
        _snowman4.run();
        if (_snowman != 0) {
            this.sectors.free(_snowman, _snowman);
        }
    }

    private ByteBuffer method_22406() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        byteBuffer.putInt(1);
        byteBuffer.put((byte)(this.outputChunkStreamVersion.getId() | 0x80));
        byteBuffer.flip();
        return byteBuffer;
    }

    private OutputAction writeSafely(Path path, ByteBuffer byteBuffer) throws IOException {
        Path path2 = Files.createTempFile(this.directory, "tmp", null, new FileAttribute[0]);
        try (FileChannel _snowman2 = FileChannel.open(path2, StandardOpenOption.CREATE, StandardOpenOption.WRITE);){
            byteBuffer.position(5);
            _snowman2.write(byteBuffer);
        }
        return () -> Files.move(path2, path, StandardCopyOption.REPLACE_EXISTING);
    }

    private void writeHeader() throws IOException {
        this.header.position(0);
        this.channel.write(this.header, 0L);
    }

    private int getSectorData(ChunkPos pos) {
        return this.sectorData.get(RegionFile.getIndex(pos));
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
        }
        finally {
            try {
                this.channel.force(true);
            }
            finally {
                this.channel.close();
            }
        }
    }

    private void fillLastSector() throws IOException {
        int n = (int)this.channel.size();
        if (n != (_snowman = RegionFile.getSectorCount(n) * 4096)) {
            ByteBuffer byteBuffer = ZERO.duplicate();
            byteBuffer.position(0);
            this.channel.write(byteBuffer, _snowman - 1);
        }
    }

    static interface OutputAction {
        public void run() throws IOException;
    }

    class ChunkBuffer
    extends ByteArrayOutputStream {
        private final ChunkPos pos;

        public ChunkBuffer(ChunkPos chunkPos) {
            super(8096);
            super.write(0);
            super.write(0);
            super.write(0);
            super.write(0);
            super.write(RegionFile.this.outputChunkStreamVersion.getId());
            this.pos = chunkPos;
        }

        @Override
        public void close() throws IOException {
            ByteBuffer byteBuffer = ByteBuffer.wrap(this.buf, 0, this.count);
            byteBuffer.putInt(0, this.count - 5 + 1);
            RegionFile.this.writeChunk(this.pos, byteBuffer);
        }
    }
}

