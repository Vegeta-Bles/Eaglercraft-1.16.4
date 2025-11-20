package net.minecraft.client.render;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryStack;

public interface VertexConsumer {
   Logger LOGGER = LogManager.getLogger();

   VertexConsumer vertex(double x, double y, double z);

   VertexConsumer color(int red, int green, int blue, int alpha);

   VertexConsumer texture(float u, float v);

   VertexConsumer overlay(int u, int v);

   VertexConsumer light(int u, int v);

   VertexConsumer normal(float x, float y, float z);

   void next();

   default void vertex(
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
      this.vertex((double)x, (double)y, (double)z);
      this.color(red, green, blue, alpha);
      this.texture(u, v);
      this.overlay(overlay);
      this.light(light);
      this.normal(normalX, normalY, normalZ);
      this.next();
   }

   default VertexConsumer color(float red, float green, float blue, float alpha) {
      return this.color((int)(red * 255.0F), (int)(green * 255.0F), (int)(blue * 255.0F), (int)(alpha * 255.0F));
   }

   default VertexConsumer light(int uv) {
      return this.light(uv & 65535, uv >> 16 & 65535);
   }

   default VertexConsumer overlay(int uv) {
      return this.overlay(uv & 65535, uv >> 16 & 65535);
   }

   default void quad(MatrixStack.Entry matrixEntry, BakedQuad quad, float red, float green, float blue, int light, int overlay) {
      this.quad(matrixEntry, quad, new float[]{1.0F, 1.0F, 1.0F, 1.0F}, red, green, blue, new int[]{light, light, light, light}, overlay, false);
   }

   default void quad(
      MatrixStack.Entry matrixEntry,
      BakedQuad quad,
      float[] brightnesses,
      float red,
      float green,
      float blue,
      int[] lights,
      int overlay,
      boolean useQuadColorData
   ) {
      int[] _snowman = quad.getVertexData();
      Vec3i _snowmanx = quad.getFace().getVector();
      Vector3f _snowmanxx = new Vector3f((float)_snowmanx.getX(), (float)_snowmanx.getY(), (float)_snowmanx.getZ());
      Matrix4f _snowmanxxx = matrixEntry.getModel();
      _snowmanxx.transform(matrixEntry.getNormal());
      int _snowmanxxxx = 8;
      int _snowmanxxxxx = _snowman.length / 8;
      MemoryStack _snowmanxxxxxx = MemoryStack.stackPush();
      Throwable var17 = null;

      try {
         ByteBuffer _snowmanxxxxxxx = _snowmanxxxxxx.malloc(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL.getVertexSize());
         IntBuffer _snowmanxxxxxxxx = _snowmanxxxxxxx.asIntBuffer();

         for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxx++) {
            ((Buffer)_snowmanxxxxxxxx).clear();
            _snowmanxxxxxxxx.put(_snowman, _snowmanxxxxxxxxx * 8, 8);
            float _snowmanxxxxxxxxxx = _snowmanxxxxxxx.getFloat(0);
            float _snowmanxxxxxxxxxxx = _snowmanxxxxxxx.getFloat(4);
            float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx.getFloat(8);
            float _snowmanxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxx;
            if (useQuadColorData) {
               float _snowmanxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxx.get(12) & 255) / 255.0F;
               float _snowmanxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxx.get(13) & 255) / 255.0F;
               float _snowmanxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxx.get(14) & 255) / 255.0F;
               _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx * brightnesses[_snowmanxxxxxxxxx] * red;
               _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx * brightnesses[_snowmanxxxxxxxxx] * green;
               _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx * brightnesses[_snowmanxxxxxxxxx] * blue;
            } else {
               _snowmanxxxxxxxxxxxxx = brightnesses[_snowmanxxxxxxxxx] * red;
               _snowmanxxxxxxxxxxxxxx = brightnesses[_snowmanxxxxxxxxx] * green;
               _snowmanxxxxxxxxxxxxxxx = brightnesses[_snowmanxxxxxxxxx] * blue;
            }

            int _snowmanxxxxxxxxxxxxxxxx = lights[_snowmanxxxxxxxxx];
            float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.getFloat(16);
            float _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.getFloat(20);
            Vector4f _snowmanxxxxxxxxxxxxxxxxxxx = new Vector4f(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 1.0F);
            _snowmanxxxxxxxxxxxxxxxxxxx.transform(_snowmanxxx);
            this.vertex(
               _snowmanxxxxxxxxxxxxxxxxxxx.getX(),
               _snowmanxxxxxxxxxxxxxxxxxxx.getY(),
               _snowmanxxxxxxxxxxxxxxxxxxx.getZ(),
               _snowmanxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxx,
               1.0F,
               _snowmanxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxx,
               overlay,
               _snowmanxxxxxxxxxxxxxxxx,
               _snowmanxx.getX(),
               _snowmanxx.getY(),
               _snowmanxx.getZ()
            );
         }
      } catch (Throwable var38) {
         var17 = var38;
         throw var38;
      } finally {
         if (_snowmanxxxxxx != null) {
            if (var17 != null) {
               try {
                  _snowmanxxxxxx.close();
               } catch (Throwable var37) {
                  var17.addSuppressed(var37);
               }
            } else {
               _snowmanxxxxxx.close();
            }
         }
      }
   }

   default VertexConsumer vertex(Matrix4f matrix, float x, float y, float z) {
      Vector4f _snowman = new Vector4f(x, y, z, 1.0F);
      _snowman.transform(matrix);
      return this.vertex((double)_snowman.getX(), (double)_snowman.getY(), (double)_snowman.getZ());
   }

   default VertexConsumer normal(Matrix3f matrix, float x, float y, float z) {
      Vector3f _snowman = new Vector3f(x, y, z);
      _snowman.transform(matrix);
      return this.normal(_snowman.getX(), _snowman.getY(), _snowman.getZ());
   }
}
