package net.minecraft.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import java.util.function.IntConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement {
   private static final Logger LOGGER = LogManager.getLogger();
   private final VertexFormatElement.Format format;
   private final VertexFormatElement.Type type;
   private final int index;
   private final int count;
   private final int size;

   public VertexFormatElement(int index, VertexFormatElement.Format format, VertexFormatElement.Type type, int count) {
      if (this.isValidType(index, type)) {
         this.type = type;
      } else {
         LOGGER.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
         this.type = VertexFormatElement.Type.UV;
      }

      this.format = format;
      this.index = index;
      this.count = count;
      this.size = format.getSize() * this.count;
   }

   private boolean isValidType(int index, VertexFormatElement.Type _snowman) {
      return index == 0 || _snowman == VertexFormatElement.Type.UV;
   }

   public final VertexFormatElement.Format getFormat() {
      return this.format;
   }

   public final VertexFormatElement.Type getType() {
      return this.type;
   }

   public final int getIndex() {
      return this.index;
   }

   @Override
   public String toString() {
      return this.count + "," + this.type.getName() + "," + this.format.getName();
   }

   public final int getSize() {
      return this.size;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         VertexFormatElement _snowman = (VertexFormatElement)o;
         if (this.count != _snowman.count) {
            return false;
         } else if (this.index != _snowman.index) {
            return false;
         } else {
            return this.format != _snowman.format ? false : this.type == _snowman.type;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.format.hashCode();
      _snowman = 31 * _snowman + this.type.hashCode();
      _snowman = 31 * _snowman + this.index;
      return 31 * _snowman + this.count;
   }

   public void startDrawing(long pointer, int stride) {
      this.type.startDrawing(this.count, this.format.getGlId(), stride, pointer, this.index);
   }

   public void endDrawing() {
      this.type.endDrawing(this.index);
   }

   public static enum Format {
      FLOAT(4, "Float", 5126),
      UBYTE(1, "Unsigned Byte", 5121),
      BYTE(1, "Byte", 5120),
      USHORT(2, "Unsigned Short", 5123),
      SHORT(2, "Short", 5122),
      UINT(4, "Unsigned Int", 5125),
      INT(4, "Int", 5124);

      private final int size;
      private final String name;
      private final int glId;

      private Format(int size, String name, int glId) {
         this.size = size;
         this.name = name;
         this.glId = glId;
      }

      public int getSize() {
         return this.size;
      }

      public String getName() {
         return this.name;
      }

      public int getGlId() {
         return this.glId;
      }
   }

   public static enum Type {
      POSITION("Position", (_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx) -> {
         GlStateManager.vertexPointer(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
         GlStateManager.enableClientState(32884);
      }, _snowman -> GlStateManager.disableClientState(32884)),
      NORMAL("Normal", (_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx) -> {
         GlStateManager.normalPointer(_snowmanx, _snowmanxx, _snowmanxxx);
         GlStateManager.enableClientState(32885);
      }, _snowman -> GlStateManager.disableClientState(32885)),
      COLOR("Vertex Color", (_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx) -> {
         GlStateManager.colorPointer(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
         GlStateManager.enableClientState(32886);
      }, _snowman -> {
         GlStateManager.disableClientState(32886);
         GlStateManager.clearCurrentColor();
      }),
      UV("UV", (_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx) -> {
         GlStateManager.clientActiveTexture(33984 + _snowmanxxxx);
         GlStateManager.texCoordPointer(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
         GlStateManager.enableClientState(32888);
         GlStateManager.clientActiveTexture(33984);
      }, _snowman -> {
         GlStateManager.clientActiveTexture(33984 + _snowman);
         GlStateManager.disableClientState(32888);
         GlStateManager.clientActiveTexture(33984);
      }),
      PADDING("Padding", (_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx) -> {
      }, _snowman -> {
      }),
      GENERIC("Generic", (_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx) -> {
         GlStateManager.enableVertexAttribArray(_snowmanxxxx);
         GlStateManager.vertexAttribPointer(_snowmanxxxx, _snowman, _snowmanx, false, _snowmanxx, _snowmanxxx);
      }, GlStateManager::method_22607);

      private final String name;
      private final VertexFormatElement.Type.Starter starter;
      private final IntConsumer finisher;

      private Type(String name, VertexFormatElement.Type.Starter var4, IntConsumer var5) {
         this.name = name;
         this.starter = _snowman;
         this.finisher = _snowman;
      }

      private void startDrawing(int count, int glId, int stride, long pointer, int elementIndex) {
         this.starter.setupBufferState(count, glId, stride, pointer, elementIndex);
      }

      public void endDrawing(int elementIndex) {
         this.finisher.accept(elementIndex);
      }

      public String getName() {
         return this.name;
      }

      interface Starter {
         void setupBufferState(int count, int glId, int stride, long pointer, int elementIndex);
      }
   }
}
