/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.stb.STBVorbis
 *  org.lwjgl.stb.STBVorbisInfo
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 */
package net.minecraft.client.sound;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.sound.AudioStream;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class OggAudioStream
implements AudioStream {
    private long pointer;
    private final AudioFormat format;
    private final InputStream inputStream;
    private ByteBuffer buffer = MemoryUtil.memAlloc((int)8192);

    public OggAudioStream(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        this.buffer.limit(0);
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            _snowman = memoryStack.mallocInt(1);
            while (this.pointer == 0L) {
                if (!this.readHeader()) {
                    throw new IOException("Failed to find Ogg header");
                }
                int n = this.buffer.position();
                this.buffer.position(0);
                this.pointer = STBVorbis.stb_vorbis_open_pushdata((ByteBuffer)this.buffer, (IntBuffer)intBuffer, (IntBuffer)_snowman, null);
                this.buffer.position(n);
                _snowman = _snowman.get(0);
                if (_snowman == 1) {
                    this.increaseBufferSize();
                    continue;
                }
                if (_snowman == 0) continue;
                throw new IOException("Failed to read Ogg file " + _snowman);
            }
            this.buffer.position(this.buffer.position() + intBuffer.get(0));
            STBVorbisInfo _snowman2 = STBVorbisInfo.mallocStack((MemoryStack)memoryStack);
            STBVorbis.stb_vorbis_get_info((long)this.pointer, (STBVorbisInfo)_snowman2);
            this.format = new AudioFormat(_snowman2.sample_rate(), 16, _snowman2.channels(), true, false);
        }
    }

    private boolean readHeader() throws IOException {
        int n = this.buffer.limit();
        _snowman = this.buffer.capacity() - n;
        if (_snowman == 0) {
            return true;
        }
        byte[] _snowman2 = new byte[_snowman];
        _snowman = this.inputStream.read(_snowman2);
        if (_snowman == -1) {
            return false;
        }
        _snowman = this.buffer.position();
        this.buffer.limit(n + _snowman);
        this.buffer.position(n);
        this.buffer.put(_snowman2, 0, _snowman);
        this.buffer.position(_snowman);
        return true;
    }

    private void increaseBufferSize() {
        boolean bl = this.buffer.position() == 0;
        boolean bl2 = _snowman = this.buffer.position() == this.buffer.limit();
        if (_snowman && !bl) {
            this.buffer.position(0);
            this.buffer.limit(0);
        } else {
            ByteBuffer byteBuffer = MemoryUtil.memAlloc((int)(bl ? 2 * this.buffer.capacity() : this.buffer.capacity()));
            byteBuffer.put(this.buffer);
            MemoryUtil.memFree((Buffer)this.buffer);
            byteBuffer.flip();
            this.buffer = byteBuffer;
        }
    }

    private boolean readOggFile(ChannelList channelList) throws IOException {
        if (this.pointer == 0L) {
            return false;
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            block24: {
                PointerBuffer pointerBuffer = memoryStack.mallocPointer(1);
                IntBuffer _snowman2 = memoryStack.mallocInt(1);
                IntBuffer _snowman3 = memoryStack.mallocInt(1);
                while (true) {
                    int n = STBVorbis.stb_vorbis_decode_frame_pushdata((long)this.pointer, (ByteBuffer)this.buffer, (IntBuffer)_snowman2, (PointerBuffer)pointerBuffer, (IntBuffer)_snowman3);
                    this.buffer.position(this.buffer.position() + n);
                    _snowman = STBVorbis.stb_vorbis_get_error((long)this.pointer);
                    if (_snowman == 1) {
                        this.increaseBufferSize();
                        if (this.readHeader()) continue;
                        break block24;
                    }
                    if (_snowman != 0) {
                        throw new IOException("Failed to read Ogg file " + _snowman);
                    }
                    _snowman = _snowman3.get(0);
                    if (_snowman != 0) break;
                }
                int n = _snowman2.get(0);
                PointerBuffer _snowman4 = pointerBuffer.getPointerBuffer(n);
                if (n == 1) {
                    this.readChannels(_snowman4.getFloatBuffer(0, _snowman), channelList);
                    boolean bl = true;
                    return bl;
                }
                if (n == 2) {
                    this.readChannels(_snowman4.getFloatBuffer(0, _snowman), _snowman4.getFloatBuffer(1, _snowman), channelList);
                    boolean bl = true;
                    return bl;
                }
                throw new IllegalStateException("Invalid number of channels: " + n);
            }
            boolean bl = false;
            return bl;
        }
    }

    private void readChannels(FloatBuffer floatBuffer, ChannelList channelList) {
        while (floatBuffer.hasRemaining()) {
            channelList.addChannel(floatBuffer.get());
        }
    }

    private void readChannels(FloatBuffer floatBuffer, FloatBuffer floatBuffer2, ChannelList channelList) {
        while (floatBuffer.hasRemaining() && floatBuffer2.hasRemaining()) {
            channelList.addChannel(floatBuffer.get());
            channelList.addChannel(floatBuffer2.get());
        }
    }

    @Override
    public void close() throws IOException {
        if (this.pointer != 0L) {
            STBVorbis.stb_vorbis_close((long)this.pointer);
            this.pointer = 0L;
        }
        MemoryUtil.memFree((Buffer)this.buffer);
        this.inputStream.close();
    }

    @Override
    public AudioFormat getFormat() {
        return this.format;
    }

    @Override
    public ByteBuffer getBuffer(int size) throws IOException {
        ChannelList channelList = new ChannelList(size + 8192);
        while (this.readOggFile(channelList) && channelList.currentBufferSize < size) {
        }
        return channelList.getBuffer();
    }

    public ByteBuffer getBuffer() throws IOException {
        ChannelList channelList = new ChannelList(16384);
        while (this.readOggFile(channelList)) {
        }
        return channelList.getBuffer();
    }

    static class ChannelList {
        private final List<ByteBuffer> buffers = Lists.newArrayList();
        private final int size;
        private int currentBufferSize;
        private ByteBuffer buffer;

        public ChannelList(int size) {
            this.size = size + 1 & 0xFFFFFFFE;
            this.init();
        }

        private void init() {
            this.buffer = BufferUtils.createByteBuffer((int)this.size);
        }

        public void addChannel(float f) {
            if (this.buffer.remaining() == 0) {
                this.buffer.flip();
                this.buffers.add(this.buffer);
                this.init();
            }
            int n = MathHelper.clamp((int)(f * 32767.5f - 0.5f), Short.MIN_VALUE, Short.MAX_VALUE);
            this.buffer.putShort((short)n);
            this.currentBufferSize += 2;
        }

        public ByteBuffer getBuffer() {
            this.buffer.flip();
            if (this.buffers.isEmpty()) {
                return this.buffer;
            }
            ByteBuffer byteBuffer = BufferUtils.createByteBuffer((int)this.currentBufferSize);
            this.buffers.forEach(byteBuffer::put);
            byteBuffer.put(this.buffer);
            byteBuffer.flip();
            return byteBuffer;
        }
    }
}

