/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.primitives.Floats
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.ints.IntArrays
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.render;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.render.BufferVertexConsumer;
import net.minecraft.client.render.FixedColorVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.GlAllocationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BufferBuilder
extends FixedColorVertexConsumer
implements BufferVertexConsumer {
    private static final Logger LOGGER = LogManager.getLogger();
    private ByteBuffer buffer;
    private final List<DrawArrayParameters> parameters = Lists.newArrayList();
    private int lastParameterIndex = 0;
    private int buildStart = 0;
    private int elementOffset = 0;
    private int nextDrawStart = 0;
    private int vertexCount;
    @Nullable
    private VertexFormatElement currentElement;
    private int currentElementId;
    private int drawMode;
    private VertexFormat format;
    private boolean field_21594;
    private boolean field_21595;
    private boolean building;

    public BufferBuilder(int initialCapacity) {
        this.buffer = GlAllocationUtils.allocateByteBuffer(initialCapacity * 4);
    }

    protected void grow() {
        this.grow(this.format.getVertexSize());
    }

    private void grow(int size) {
        if (this.elementOffset + size <= this.buffer.capacity()) {
            return;
        }
        int n = this.buffer.capacity();
        _snowman = n + BufferBuilder.roundBufferSize(size);
        LOGGER.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", (Object)n, (Object)_snowman);
        ByteBuffer _snowman2 = GlAllocationUtils.allocateByteBuffer(_snowman);
        this.buffer.position(0);
        _snowman2.put(this.buffer);
        _snowman2.rewind();
        this.buffer = _snowman2;
    }

    private static int roundBufferSize(int amount) {
        int n = 0x200000;
        if (amount == 0) {
            return n;
        }
        if (amount < 0) {
            n *= -1;
        }
        if ((_snowman = amount % n) == 0) {
            return amount;
        }
        return amount + n - _snowman;
    }

    public void sortQuads(float cameraX, float cameraY, float cameraZ) {
        this.buffer.clear();
        FloatBuffer floatBuffer = this.buffer.asFloatBuffer();
        int _snowman2 = this.vertexCount / 4;
        float[] _snowman3 = new float[_snowman2];
        for (int i = 0; i < _snowman2; ++i) {
            _snowman3[i] = BufferBuilder.getDistanceSq(floatBuffer, cameraX, cameraY, cameraZ, this.format.getVertexSizeInteger(), this.buildStart / 4 + i * this.format.getVertexSize());
        }
        int[] nArray = new int[_snowman2];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = i;
        }
        IntArrays.mergeSort((int[])nArray, (n, n2) -> Floats.compare((float)_snowman3[n2], (float)_snowman3[n]));
        BitSet _snowman4 = new BitSet();
        FloatBuffer _snowman5 = GlAllocationUtils.allocateFloatBuffer(this.format.getVertexSizeInteger() * 4);
        int _snowman6 = _snowman4.nextClearBit(0);
        while (_snowman6 < nArray.length) {
            int n3 = nArray[_snowman6];
            if (n3 != _snowman6) {
                this.method_22628(floatBuffer, n3);
                _snowman5.clear();
                _snowman5.put(floatBuffer);
                _snowman7 = n3;
                _snowman8 = nArray[_snowman7];
                while (_snowman7 != _snowman6) {
                    this.method_22628(floatBuffer, _snowman8);
                    FloatBuffer floatBuffer2 = floatBuffer.slice();
                    this.method_22628(floatBuffer, _snowman7);
                    floatBuffer.put(floatBuffer2);
                    _snowman4.set(_snowman7);
                    int _snowman7 = _snowman8;
                    int _snowman8 = nArray[_snowman7];
                }
                this.method_22628(floatBuffer, _snowman6);
                _snowman5.flip();
                floatBuffer.put(_snowman5);
            }
            _snowman4.set(_snowman6);
            _snowman6 = _snowman4.nextClearBit(_snowman6 + 1);
        }
    }

    private void method_22628(FloatBuffer floatBuffer, int n) {
        _snowman = this.format.getVertexSizeInteger() * 4;
        floatBuffer.limit(this.buildStart / 4 + (n + 1) * _snowman);
        floatBuffer.position(this.buildStart / 4 + n * _snowman);
    }

    public State popState() {
        this.buffer.limit(this.elementOffset);
        this.buffer.position(this.buildStart);
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.vertexCount * this.format.getVertexSize());
        byteBuffer.put(this.buffer);
        this.buffer.clear();
        return new State(byteBuffer, this.format);
    }

    private static float getDistanceSq(FloatBuffer buffer, float x, float y, float z, int n, int n2) {
        float f = buffer.get(n2 + n * 0 + 0);
        _snowman = buffer.get(n2 + n * 0 + 1);
        _snowman = buffer.get(n2 + n * 0 + 2);
        _snowman = buffer.get(n2 + n * 1 + 0);
        _snowman = buffer.get(n2 + n * 1 + 1);
        _snowman = buffer.get(n2 + n * 1 + 2);
        _snowman = buffer.get(n2 + n * 2 + 0);
        _snowman = buffer.get(n2 + n * 2 + 1);
        _snowman = buffer.get(n2 + n * 2 + 2);
        _snowman = buffer.get(n2 + n * 3 + 0);
        _snowman = buffer.get(n2 + n * 3 + 1);
        _snowman = buffer.get(n2 + n * 3 + 2);
        _snowman = (f + _snowman + _snowman + _snowman) * 0.25f - x;
        _snowman = (_snowman + _snowman + _snowman + _snowman) * 0.25f - y;
        _snowman = (_snowman + _snowman + _snowman + _snowman) * 0.25f - z;
        return _snowman * _snowman + _snowman * _snowman + _snowman * _snowman;
    }

    public void restoreState(State state) {
        state.buffer.clear();
        int n = state.buffer.capacity();
        this.grow(n);
        this.buffer.limit(this.buffer.capacity());
        this.buffer.position(this.buildStart);
        this.buffer.put(state.buffer);
        this.buffer.clear();
        VertexFormat _snowman2 = state.format;
        this.method_23918(_snowman2);
        this.vertexCount = n / _snowman2.getVertexSize();
        this.elementOffset = this.buildStart + this.vertexCount * _snowman2.getVertexSize();
    }

    public void begin(int drawMode, VertexFormat format) {
        if (this.building) {
            throw new IllegalStateException("Already building!");
        }
        this.building = true;
        this.drawMode = drawMode;
        this.method_23918(format);
        this.currentElement = (VertexFormatElement)format.getElements().get(0);
        this.currentElementId = 0;
        this.buffer.clear();
    }

    private void method_23918(VertexFormat vertexFormat) {
        if (this.format == vertexFormat) {
            return;
        }
        this.format = vertexFormat;
        boolean bl = vertexFormat == VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL;
        _snowman = vertexFormat == VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL;
        this.field_21594 = bl || _snowman;
        this.field_21595 = bl;
    }

    public void end() {
        if (!this.building) {
            throw new IllegalStateException("Not building!");
        }
        this.building = false;
        this.parameters.add(new DrawArrayParameters(this.format, this.vertexCount, this.drawMode));
        this.buildStart += this.vertexCount * this.format.getVertexSize();
        this.vertexCount = 0;
        this.currentElement = null;
        this.currentElementId = 0;
    }

    @Override
    public void putByte(int index, byte value) {
        this.buffer.put(this.elementOffset + index, value);
    }

    @Override
    public void putShort(int index, short value) {
        this.buffer.putShort(this.elementOffset + index, value);
    }

    @Override
    public void putFloat(int index, float value) {
        this.buffer.putFloat(this.elementOffset + index, value);
    }

    @Override
    public void next() {
        if (this.currentElementId != 0) {
            throw new IllegalStateException("Not filled all elements of the vertex");
        }
        ++this.vertexCount;
        this.grow();
    }

    @Override
    public void nextElement() {
        ImmutableList<VertexFormatElement> immutableList = this.format.getElements();
        this.currentElementId = (this.currentElementId + 1) % immutableList.size();
        this.elementOffset += this.currentElement.getSize();
        this.currentElement = _snowman = (VertexFormatElement)immutableList.get(this.currentElementId);
        if (_snowman.getType() == VertexFormatElement.Type.PADDING) {
            this.nextElement();
        }
        if (this.colorFixed && this.currentElement.getType() == VertexFormatElement.Type.COLOR) {
            BufferVertexConsumer.super.color(this.fixedRed, this.fixedGreen, this.fixedBlue, this.fixedAlpha);
        }
    }

    @Override
    public VertexConsumer color(int red, int green, int blue, int alpha) {
        if (this.colorFixed) {
            throw new IllegalStateException();
        }
        return BufferVertexConsumer.super.color(red, green, blue, alpha);
    }

    @Override
    public void vertex(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ) {
        if (this.colorFixed) {
            throw new IllegalStateException();
        }
        if (this.field_21594) {
            int n;
            this.putFloat(0, x);
            this.putFloat(4, y);
            this.putFloat(8, z);
            this.putByte(12, (byte)(red * 255.0f));
            this.putByte(13, (byte)(green * 255.0f));
            this.putByte(14, (byte)(blue * 255.0f));
            this.putByte(15, (byte)(alpha * 255.0f));
            this.putFloat(16, u);
            this.putFloat(20, v);
            if (this.field_21595) {
                this.putShort(24, (short)(overlay & 0xFFFF));
                this.putShort(26, (short)(overlay >> 16 & 0xFFFF));
                n = 28;
            } else {
                n = 24;
            }
            this.putShort(n + 0, (short)(light & 0xFFFF));
            this.putShort(n + 2, (short)(light >> 16 & 0xFFFF));
            this.putByte(n + 4, BufferVertexConsumer.method_24212(normalX));
            this.putByte(n + 5, BufferVertexConsumer.method_24212(normalY));
            this.putByte(n + 6, BufferVertexConsumer.method_24212(normalZ));
            this.elementOffset += n + 8;
            this.next();
            return;
        }
        super.vertex(x, y, z, red, green, blue, alpha, u, v, overlay, light, normalX, normalY, normalZ);
    }

    public Pair<DrawArrayParameters, ByteBuffer> popData() {
        DrawArrayParameters drawArrayParameters = this.parameters.get(this.lastParameterIndex++);
        this.buffer.position(this.nextDrawStart);
        this.nextDrawStart += drawArrayParameters.getCount() * drawArrayParameters.getVertexFormat().getVertexSize();
        this.buffer.limit(this.nextDrawStart);
        if (this.lastParameterIndex == this.parameters.size() && this.vertexCount == 0) {
            this.clear();
        }
        ByteBuffer _snowman2 = this.buffer.slice();
        this.buffer.clear();
        return Pair.of((Object)drawArrayParameters, (Object)_snowman2);
    }

    public void clear() {
        if (this.buildStart != this.nextDrawStart) {
            LOGGER.warn("Bytes mismatch " + this.buildStart + " " + this.nextDrawStart);
        }
        this.reset();
    }

    public void reset() {
        this.buildStart = 0;
        this.nextDrawStart = 0;
        this.elementOffset = 0;
        this.parameters.clear();
        this.lastParameterIndex = 0;
    }

    @Override
    public VertexFormatElement getCurrentElement() {
        if (this.currentElement == null) {
            throw new IllegalStateException("BufferBuilder not started");
        }
        return this.currentElement;
    }

    public boolean isBuilding() {
        return this.building;
    }

    public static final class DrawArrayParameters {
        private final VertexFormat vertexFormat;
        private final int count;
        private final int mode;

        private DrawArrayParameters(VertexFormat vertexFormat, int count, int mode) {
            this.vertexFormat = vertexFormat;
            this.count = count;
            this.mode = mode;
        }

        public VertexFormat getVertexFormat() {
            return this.vertexFormat;
        }

        public int getCount() {
            return this.count;
        }

        public int getMode() {
            return this.mode;
        }
    }

    public static class State {
        private final ByteBuffer buffer;
        private final VertexFormat format;

        private State(ByteBuffer buffer, VertexFormat format) {
            this.buffer = buffer;
            this.format = format;
        }
    }
}

