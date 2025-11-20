package net.minecraft.client.render;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.GlAllocationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BufferBuilder extends FixedColorVertexConsumer implements BufferVertexConsumer {
   private static final Logger LOGGER = LogManager.getLogger();
   private ByteBuffer buffer;
   private final List<BufferBuilder.DrawArrayParameters> parameters = Lists.newArrayList();
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
      if (this.elementOffset + size > this.buffer.capacity()) {
         int _snowman = this.buffer.capacity();
         int _snowmanx = _snowman + roundBufferSize(size);
         LOGGER.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", _snowman, _snowmanx);
         ByteBuffer _snowmanxx = GlAllocationUtils.allocateByteBuffer(_snowmanx);
         ((Buffer)this.buffer).position(0);
         _snowmanxx.put(this.buffer);
         ((Buffer)_snowmanxx).rewind();
         this.buffer = _snowmanxx;
      }
   }

   private static int roundBufferSize(int amount) {
      int _snowman = 2097152;
      if (amount == 0) {
         return _snowman;
      } else {
         if (amount < 0) {
            _snowman *= -1;
         }

         int _snowmanx = amount % _snowman;
         return _snowmanx == 0 ? amount : amount + _snowman - _snowmanx;
      }
   }

   public void sortQuads(float cameraX, float cameraY, float cameraZ) {
      ((Buffer)this.buffer).clear();
      FloatBuffer _snowman = this.buffer.asFloatBuffer();
      int _snowmanx = this.vertexCount / 4;
      float[] _snowmanxx = new float[_snowmanx];

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx; _snowmanxxx++) {
         _snowmanxx[_snowmanxxx] = getDistanceSq(_snowman, cameraX, cameraY, cameraZ, this.format.getVertexSizeInteger(), this.buildStart / 4 + _snowmanxxx * this.format.getVertexSize());
      }

      int[] _snowmanxxx = new int[_snowmanx];
      int _snowmanxxxx = 0;

      while (_snowmanxxxx < _snowmanxxx.length) {
         _snowmanxxx[_snowmanxxxx] = _snowmanxxxx++;
      }

      IntArrays.mergeSort(_snowmanxxx, (_snowmanxxxxx, _snowmanxxxxxx) -> Floats.compare(_snowman[_snowmanxxxxxx], _snowman[_snowmanxxxxx]));
      BitSet _snowmanxxxxx = new BitSet();
      FloatBuffer _snowmanxxxxxx = GlAllocationUtils.allocateFloatBuffer(this.format.getVertexSizeInteger() * 4);

      for (int _snowmanxxxxxxx = _snowmanxxxxx.nextClearBit(0); _snowmanxxxxxxx < _snowmanxxx.length; _snowmanxxxxxxx = _snowmanxxxxx.nextClearBit(_snowmanxxxxxxx + 1)) {
         int _snowmanxxxxxxxx = _snowmanxxx[_snowmanxxxxxxx];
         if (_snowmanxxxxxxxx != _snowmanxxxxxxx) {
            this.method_22628(_snowman, _snowmanxxxxxxxx);
            ((Buffer)_snowmanxxxxxx).clear();
            _snowmanxxxxxx.put(_snowman);
            int _snowmanxxxxxxxxx = _snowmanxxxxxxxx;

            for (int _snowmanxxxxxxxxxx = _snowmanxxx[_snowmanxxxxxxxx]; _snowmanxxxxxxxxx != _snowmanxxxxxxx; _snowmanxxxxxxxxxx = _snowmanxxx[_snowmanxxxxxxxxxx]) {
               this.method_22628(_snowman, _snowmanxxxxxxxxxx);
               FloatBuffer _snowmanxxxxxxxxxxx = _snowman.slice();
               this.method_22628(_snowman, _snowmanxxxxxxxxx);
               _snowman.put(_snowmanxxxxxxxxxxx);
               _snowmanxxxxx.set(_snowmanxxxxxxxxx);
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxxx;
            }

            this.method_22628(_snowman, _snowmanxxxxxxx);
            ((Buffer)_snowmanxxxxxx).flip();
            _snowman.put(_snowmanxxxxxx);
         }

         _snowmanxxxxx.set(_snowmanxxxxxxx);
      }
   }

   private void method_22628(FloatBuffer _snowman, int _snowman) {
      int _snowmanxx = this.format.getVertexSizeInteger() * 4;
      ((Buffer)_snowman).limit(this.buildStart / 4 + (_snowman + 1) * _snowmanxx);
      ((Buffer)_snowman).position(this.buildStart / 4 + _snowman * _snowmanxx);
   }

   public BufferBuilder.State popState() {
      ((Buffer)this.buffer).limit(this.elementOffset);
      ((Buffer)this.buffer).position(this.buildStart);
      ByteBuffer _snowman = ByteBuffer.allocate(this.vertexCount * this.format.getVertexSize());
      _snowman.put(this.buffer);
      ((Buffer)this.buffer).clear();
      return new BufferBuilder.State(_snowman, this.format);
   }

   private static float getDistanceSq(FloatBuffer buffer, float x, float y, float z, int _snowman, int _snowman) {
      float _snowmanxx = buffer.get(_snowman + _snowman * 0 + 0);
      float _snowmanxxx = buffer.get(_snowman + _snowman * 0 + 1);
      float _snowmanxxxx = buffer.get(_snowman + _snowman * 0 + 2);
      float _snowmanxxxxx = buffer.get(_snowman + _snowman * 1 + 0);
      float _snowmanxxxxxx = buffer.get(_snowman + _snowman * 1 + 1);
      float _snowmanxxxxxxx = buffer.get(_snowman + _snowman * 1 + 2);
      float _snowmanxxxxxxxx = buffer.get(_snowman + _snowman * 2 + 0);
      float _snowmanxxxxxxxxx = buffer.get(_snowman + _snowman * 2 + 1);
      float _snowmanxxxxxxxxxx = buffer.get(_snowman + _snowman * 2 + 2);
      float _snowmanxxxxxxxxxxx = buffer.get(_snowman + _snowman * 3 + 0);
      float _snowmanxxxxxxxxxxxx = buffer.get(_snowman + _snowman * 3 + 1);
      float _snowmanxxxxxxxxxxxxx = buffer.get(_snowman + _snowman * 3 + 2);
      float _snowmanxxxxxxxxxxxxxx = (_snowmanxx + _snowmanxxxxx + _snowmanxxxxxxxx + _snowmanxxxxxxxxxxx) * 0.25F - x;
      float _snowmanxxxxxxxxxxxxxxx = (_snowmanxxx + _snowmanxxxxxx + _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxx) * 0.25F - y;
      float _snowmanxxxxxxxxxxxxxxxx = (_snowmanxxxx + _snowmanxxxxxxx + _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxx) * 0.25F - z;
      return _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx;
   }

   public void restoreState(BufferBuilder.State _snowman) {
      ((Buffer)_snowman.buffer).clear();
      int _snowmanx = _snowman.buffer.capacity();
      this.grow(_snowmanx);
      ((Buffer)this.buffer).limit(this.buffer.capacity());
      ((Buffer)this.buffer).position(this.buildStart);
      this.buffer.put(_snowman.buffer);
      ((Buffer)this.buffer).clear();
      VertexFormat _snowmanxx = _snowman.format;
      this.method_23918(_snowmanxx);
      this.vertexCount = _snowmanx / _snowmanxx.getVertexSize();
      this.elementOffset = this.buildStart + this.vertexCount * _snowmanxx.getVertexSize();
   }

   public void begin(int drawMode, VertexFormat format) {
      if (this.building) {
         throw new IllegalStateException("Already building!");
      } else {
         this.building = true;
         this.drawMode = drawMode;
         this.method_23918(format);
         this.currentElement = (VertexFormatElement)format.getElements().get(0);
         this.currentElementId = 0;
         ((Buffer)this.buffer).clear();
      }
   }

   private void method_23918(VertexFormat _snowman) {
      if (this.format != _snowman) {
         this.format = _snowman;
         boolean _snowmanx = _snowman == VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL;
         boolean _snowmanxx = _snowman == VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL;
         this.field_21594 = _snowmanx || _snowmanxx;
         this.field_21595 = _snowmanx;
      }
   }

   public void end() {
      if (!this.building) {
         throw new IllegalStateException("Not building!");
      } else {
         this.building = false;
         this.parameters.add(new BufferBuilder.DrawArrayParameters(this.format, this.vertexCount, this.drawMode));
         this.buildStart = this.buildStart + this.vertexCount * this.format.getVertexSize();
         this.vertexCount = 0;
         this.currentElement = null;
         this.currentElementId = 0;
      }
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
      } else {
         this.vertexCount++;
         this.grow();
      }
   }

   @Override
   public void nextElement() {
      ImmutableList<VertexFormatElement> _snowman = this.format.getElements();
      this.currentElementId = (this.currentElementId + 1) % _snowman.size();
      this.elementOffset = this.elementOffset + this.currentElement.getSize();
      VertexFormatElement _snowmanx = (VertexFormatElement)_snowman.get(this.currentElementId);
      this.currentElement = _snowmanx;
      if (_snowmanx.getType() == VertexFormatElement.Type.PADDING) {
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
      } else {
         return BufferVertexConsumer.super.color(red, green, blue, alpha);
      }
   }

   @Override
   public void vertex(
      float x,
      float y,
      float z,
      float red,
      float green,
      float blue,
      float alpha,
      float u,
      float v,
      int overlay,
      int light,
      float normalX,
      float normalY,
      float normalZ
   ) {
      if (this.colorFixed) {
         throw new IllegalStateException();
      } else if (this.field_21594) {
         this.putFloat(0, x);
         this.putFloat(4, y);
         this.putFloat(8, z);
         this.putByte(12, (byte)((int)(red * 255.0F)));
         this.putByte(13, (byte)((int)(green * 255.0F)));
         this.putByte(14, (byte)((int)(blue * 255.0F)));
         this.putByte(15, (byte)((int)(alpha * 255.0F)));
         this.putFloat(16, u);
         this.putFloat(20, v);
         int _snowman;
         if (this.field_21595) {
            this.putShort(24, (short)(overlay & 65535));
            this.putShort(26, (short)(overlay >> 16 & 65535));
            _snowman = 28;
         } else {
            _snowman = 24;
         }

         this.putShort(_snowman + 0, (short)(light & 65535));
         this.putShort(_snowman + 2, (short)(light >> 16 & 65535));
         this.putByte(_snowman + 4, BufferVertexConsumer.method_24212(normalX));
         this.putByte(_snowman + 5, BufferVertexConsumer.method_24212(normalY));
         this.putByte(_snowman + 6, BufferVertexConsumer.method_24212(normalZ));
         this.elementOffset += _snowman + 8;
         this.next();
      } else {
         super.vertex(x, y, z, red, green, blue, alpha, u, v, overlay, light, normalX, normalY, normalZ);
      }
   }

   public Pair<BufferBuilder.DrawArrayParameters, ByteBuffer> popData() {
      BufferBuilder.DrawArrayParameters _snowman = this.parameters.get(this.lastParameterIndex++);
      ((Buffer)this.buffer).position(this.nextDrawStart);
      this.nextDrawStart = this.nextDrawStart + _snowman.getCount() * _snowman.getVertexFormat().getVertexSize();
      ((Buffer)this.buffer).limit(this.nextDrawStart);
      if (this.lastParameterIndex == this.parameters.size() && this.vertexCount == 0) {
         this.clear();
      }

      ByteBuffer _snowmanx = this.buffer.slice();
      ((Buffer)this.buffer).clear();
      return Pair.of(_snowman, _snowmanx);
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
      } else {
         return this.currentElement;
      }
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
