/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render;

import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Matrix4f;

public class Frustum {
    private final Vector4f[] homogeneousCoordinates = new Vector4f[6];
    private double x;
    private double y;
    private double z;

    public Frustum(Matrix4f matrix4f, Matrix4f matrix4f2) {
        this.init(matrix4f, matrix4f2);
    }

    public void setPosition(double cameraX, double cameraY, double cameraZ) {
        this.x = cameraX;
        this.y = cameraY;
        this.z = cameraZ;
    }

    private void init(Matrix4f matrix4f, Matrix4f matrix4f2) {
        _snowman = matrix4f2.copy();
        _snowman.multiply(matrix4f);
        _snowman.transpose();
        this.transform(_snowman, -1, 0, 0, 0);
        this.transform(_snowman, 1, 0, 0, 1);
        this.transform(_snowman, 0, -1, 0, 2);
        this.transform(_snowman, 0, 1, 0, 3);
        this.transform(_snowman, 0, 0, -1, 4);
        this.transform(_snowman, 0, 0, 1, 5);
    }

    private void transform(Matrix4f function, int x, int y, int z, int index) {
        Vector4f vector4f = new Vector4f(x, y, z, 1.0f);
        vector4f.transform(function);
        vector4f.normalize();
        this.homogeneousCoordinates[index] = vector4f;
    }

    public boolean isVisible(Box box) {
        return this.isVisible(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    private boolean isVisible(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        float f = (float)(minX - this.x);
        _snowman = (float)(minY - this.y);
        _snowman = (float)(minZ - this.z);
        _snowman = (float)(maxX - this.x);
        _snowman = (float)(maxY - this.y);
        _snowman = (float)(maxZ - this.z);
        return this.isAnyCornerVisible(f, _snowman, _snowman, _snowman, _snowman, _snowman);
    }

    private boolean isAnyCornerVisible(float x1, float y1, float z1, float x2, float y2, float z2) {
        for (int i = 0; i < 6; ++i) {
            Vector4f vector4f = this.homogeneousCoordinates[i];
            if (vector4f.dotProduct(new Vector4f(x1, y1, z1, 1.0f)) > 0.0f) continue;
            if (vector4f.dotProduct(new Vector4f(x2, y1, z1, 1.0f)) > 0.0f) continue;
            if (vector4f.dotProduct(new Vector4f(x1, y2, z1, 1.0f)) > 0.0f) continue;
            if (vector4f.dotProduct(new Vector4f(x2, y2, z1, 1.0f)) > 0.0f) continue;
            if (vector4f.dotProduct(new Vector4f(x1, y1, z2, 1.0f)) > 0.0f) continue;
            if (vector4f.dotProduct(new Vector4f(x2, y1, z2, 1.0f)) > 0.0f) continue;
            if (vector4f.dotProduct(new Vector4f(x1, y2, z2, 1.0f)) > 0.0f) continue;
            if (vector4f.dotProduct(new Vector4f(x2, y2, z2, 1.0f)) > 0.0f) continue;
            return false;
        }
        return true;
    }
}

