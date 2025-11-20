/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.system.MemoryStack
 */
package net.minecraft.client.render;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.render.VertexFormats;
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
    public static final Logger LOGGER = LogManager.getLogger();

    public VertexConsumer vertex(double var1, double var3, double var5);

    public VertexConsumer color(int var1, int var2, int var3, int var4);

    public VertexConsumer texture(float var1, float var2);

    public VertexConsumer overlay(int var1, int var2);

    public VertexConsumer light(int var1, int var2);

    public VertexConsumer normal(float var1, float var2, float var3);

    public void next();

    default public void vertex(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ) {
        this.vertex(x, y, z);
        this.color(red, green, blue, alpha);
        this.texture(u, v);
        this.overlay(overlay);
        this.light(light);
        this.normal(normalX, normalY, normalZ);
        this.next();
    }

    default public VertexConsumer color(float red, float green, float blue, float alpha) {
        return this.color((int)(red * 255.0f), (int)(green * 255.0f), (int)(blue * 255.0f), (int)(alpha * 255.0f));
    }

    default public VertexConsumer light(int uv) {
        return this.light(uv & 0xFFFF, uv >> 16 & 0xFFFF);
    }

    default public VertexConsumer overlay(int uv) {
        return this.overlay(uv & 0xFFFF, uv >> 16 & 0xFFFF);
    }

    default public void quad(MatrixStack.Entry matrixEntry, BakedQuad quad, float red, float green, float blue, int light, int overlay) {
        this.quad(matrixEntry, quad, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, red, green, blue, new int[]{light, light, light, light}, overlay, false);
    }

    default public void quad(MatrixStack.Entry matrixEntry, BakedQuad quad, float[] brightnesses, float red, float green, float blue, int[] lights, int overlay, boolean useQuadColorData) {
        int[] nArray = quad.getVertexData();
        Vec3i _snowman2 = quad.getFace().getVector();
        Vector3f _snowman3 = new Vector3f(_snowman2.getX(), _snowman2.getY(), _snowman2.getZ());
        Matrix4f _snowman4 = matrixEntry.getModel();
        _snowman3.transform(matrixEntry.getNormal());
        int _snowman5 = 8;
        int _snowman6 = nArray.length / 8;
        try (MemoryStack _snowman7 = MemoryStack.stackPush();){
            ByteBuffer byteBuffer = _snowman7.malloc(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL.getVertexSize());
            IntBuffer _snowman8 = byteBuffer.asIntBuffer();
            for (int i = 0; i < _snowman6; ++i) {
                _snowman8.clear();
                _snowman8.put(nArray, i * 8, 8);
                float f = byteBuffer.getFloat(0);
                _snowman = byteBuffer.getFloat(4);
                _snowman = byteBuffer.getFloat(8);
                if (useQuadColorData) {
                    _snowman = (float)(byteBuffer.get(12) & 0xFF) / 255.0f;
                    _snowman = (float)(byteBuffer.get(13) & 0xFF) / 255.0f;
                    _snowman = (float)(byteBuffer.get(14) & 0xFF) / 255.0f;
                    _snowman = _snowman * brightnesses[i] * red;
                    _snowman = _snowman * brightnesses[i] * green;
                    _snowman = _snowman * brightnesses[i] * blue;
                } else {
                    _snowman = brightnesses[i] * red;
                    _snowman = brightnesses[i] * green;
                    _snowman = brightnesses[i] * blue;
                }
                int _snowman9 = lights[i];
                _snowman = byteBuffer.getFloat(16);
                _snowman = byteBuffer.getFloat(20);
                Vector4f _snowman10 = new Vector4f(f, _snowman, _snowman, 1.0f);
                _snowman10.transform(_snowman4);
                this.vertex(_snowman10.getX(), _snowman10.getY(), _snowman10.getZ(), _snowman, _snowman, _snowman, 1.0f, _snowman, _snowman, overlay, _snowman9, _snowman3.getX(), _snowman3.getY(), _snowman3.getZ());
            }
        }
    }

    default public VertexConsumer vertex(Matrix4f matrix, float x, float y, float z) {
        Vector4f vector4f = new Vector4f(x, y, z, 1.0f);
        vector4f.transform(matrix);
        return this.vertex(vector4f.getX(), vector4f.getY(), vector4f.getZ());
    }

    default public VertexConsumer normal(Matrix3f matrix, float x, float y, float z) {
        Vector3f vector3f = new Vector3f(x, y, z);
        vector3f.transform(matrix);
        return this.normal(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }
}

