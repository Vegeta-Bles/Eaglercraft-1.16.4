package net.minecraft.client.render;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      int[] js = quad.getVertexData();
      Vec3i lv = quad.getFace().getVector();
      Vector3f lv2 = new Vector3f((float)lv.getX(), (float)lv.getY(), (float)lv.getZ());
      Matrix4f lv3 = matrixEntry.getModel();
      lv2.transform(matrixEntry.getNormal());
      int j = 8;
      int k = js.length / 8;
      MemoryStack memoryStack = MemoryStack.stackPush();
      Throwable var17 = null;

      try {
         ByteBuffer byteBuffer = memoryStack.malloc(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL.getVertexSize());
         IntBuffer intBuffer = byteBuffer.asIntBuffer();

         for (int l = 0; l < k; l++) {
            ((Buffer)intBuffer).clear();
            intBuffer.put(js, l * 8, 8);
            float m = byteBuffer.getFloat(0);
            float n = byteBuffer.getFloat(4);
            float o = byteBuffer.getFloat(8);
            float s;
            float t;
            float u;
            if (useQuadColorData) {
               float p = (float)(byteBuffer.get(12) & 255) / 255.0F;
               float q = (float)(byteBuffer.get(13) & 255) / 255.0F;
               float r = (float)(byteBuffer.get(14) & 255) / 255.0F;
               s = p * brightnesses[l] * red;
               t = q * brightnesses[l] * green;
               u = r * brightnesses[l] * blue;
            } else {
               s = brightnesses[l] * red;
               t = brightnesses[l] * green;
               u = brightnesses[l] * blue;
            }

            int y = lights[l];
            float z = byteBuffer.getFloat(16);
            float aa = byteBuffer.getFloat(20);
            Vector4f lv4 = new Vector4f(m, n, o, 1.0F);
            lv4.transform(lv3);
            this.vertex(lv4.getX(), lv4.getY(), lv4.getZ(), s, t, u, 1.0F, z, aa, overlay, y, lv2.getX(), lv2.getY(), lv2.getZ());
         }
      } catch (Throwable var38) {
         var17 = var38;
         throw var38;
      } finally {
         if (memoryStack != null) {
            if (var17 != null) {
               try {
                  memoryStack.close();
               } catch (Throwable var37) {
                  var17.addSuppressed(var37);
               }
            } else {
               memoryStack.close();
            }
         }
      }
   }

   default VertexConsumer vertex(Matrix4f matrix, float x, float y, float z) {
      Vector4f lv = new Vector4f(x, y, z, 1.0F);
      lv.transform(matrix);
      return this.vertex((double)lv.getX(), (double)lv.getY(), (double)lv.getZ());
   }

   default VertexConsumer normal(Matrix3f matrix, float x, float y, float z) {
      Vector3f lv = new Vector3f(x, y, z);
      lv.transform(matrix);
      return this.normal(lv.getX(), lv.getY(), lv.getZ());
   }
}
