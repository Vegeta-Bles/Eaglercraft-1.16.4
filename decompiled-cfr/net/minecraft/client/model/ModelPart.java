/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 */
package net.minecraft.client.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Random;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class ModelPart {
    private float textureWidth = 64.0f;
    private float textureHeight = 32.0f;
    private int textureOffsetU;
    private int textureOffsetV;
    public float pivotX;
    public float pivotY;
    public float pivotZ;
    public float pitch;
    public float yaw;
    public float roll;
    public boolean mirror;
    public boolean visible = true;
    private final ObjectList<Cuboid> cuboids = new ObjectArrayList();
    private final ObjectList<ModelPart> children = new ObjectArrayList();

    public ModelPart(Model model) {
        model.accept(this);
        this.setTextureSize(model.textureWidth, model.textureHeight);
    }

    public ModelPart(Model model, int textureOffsetU, int textureOffsetV) {
        this(model.textureWidth, model.textureHeight, textureOffsetU, textureOffsetV);
        model.accept(this);
    }

    public ModelPart(int textureWidth, int textureHeight, int textureOffsetU, int textureOffsetV) {
        this.setTextureSize(textureWidth, textureHeight);
        this.setTextureOffset(textureOffsetU, textureOffsetV);
    }

    private ModelPart() {
    }

    public ModelPart method_29991() {
        ModelPart modelPart = new ModelPart();
        modelPart.copyPositionAndRotation(this);
        return modelPart;
    }

    public void copyPositionAndRotation(ModelPart modelPart) {
        this.pitch = modelPart.pitch;
        this.yaw = modelPart.yaw;
        this.roll = modelPart.roll;
        this.pivotX = modelPart.pivotX;
        this.pivotY = modelPart.pivotY;
        this.pivotZ = modelPart.pivotZ;
    }

    public void addChild(ModelPart part) {
        this.children.add((Object)part);
    }

    public ModelPart setTextureOffset(int textureOffsetU, int textureOffsetV) {
        this.textureOffsetU = textureOffsetU;
        this.textureOffsetV = textureOffsetV;
        return this;
    }

    public ModelPart addCuboid(String name, float x, float y, float z, int sizeX, int sizeY, int sizeZ, float extra, int textureOffsetU, int textureOffsetV) {
        this.setTextureOffset(textureOffsetU, textureOffsetV);
        this.addCuboid(this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, extra, extra, extra, this.mirror, false);
        return this;
    }

    public ModelPart addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ) {
        this.addCuboid(this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, 0.0f, 0.0f, 0.0f, this.mirror, false);
        return this;
    }

    public ModelPart addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ, boolean mirror) {
        this.addCuboid(this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, 0.0f, 0.0f, 0.0f, mirror, false);
        return this;
    }

    public void addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extra) {
        this.addCuboid(this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, extra, extra, extra, this.mirror, false);
    }

    public void addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ) {
        this.addCuboid(this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ, this.mirror, false);
    }

    public void addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extra, boolean mirror) {
        this.addCuboid(this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, extra, extra, extra, mirror, false);
    }

    private void addCuboid(int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, boolean bl) {
        this.cuboids.add((Object)new Cuboid(u, v, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ, mirror, this.textureWidth, this.textureHeight));
    }

    public void setPivot(float x, float y, float z) {
        this.pivotX = x;
        this.pivotY = y;
        this.pivotZ = z;
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.render(matrices, vertices, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (!this.visible) {
            return;
        }
        if (this.cuboids.isEmpty() && this.children.isEmpty()) {
            return;
        }
        matrices.push();
        this.rotate(matrices);
        this.renderCuboids(matrices.peek(), vertices, light, overlay, red, green, blue, alpha);
        for (ModelPart modelPart : this.children) {
            modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        }
        matrices.pop();
    }

    public void rotate(MatrixStack matrix) {
        matrix.translate(this.pivotX / 16.0f, this.pivotY / 16.0f, this.pivotZ / 16.0f);
        if (this.roll != 0.0f) {
            matrix.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(this.roll));
        }
        if (this.yaw != 0.0f) {
            matrix.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion(this.yaw));
        }
        if (this.pitch != 0.0f) {
            matrix.multiply(Vector3f.POSITIVE_X.getRadialQuaternion(this.pitch));
        }
    }

    private void renderCuboids(MatrixStack.Entry matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        Matrix4f matrix4f = matrices.getModel();
        Matrix3f _snowman2 = matrices.getNormal();
        for (Cuboid cuboid : this.cuboids) {
            for (Quad quad : cuboid.sides) {
                Vector3f vector3f = quad.direction.copy();
                vector3f.transform(_snowman2);
                float _snowman3 = vector3f.getX();
                float _snowman4 = vector3f.getY();
                float _snowman5 = vector3f.getZ();
                for (int i = 0; i < 4; ++i) {
                    Vertex vertex = quad.vertices[i];
                    float _snowman6 = vertex.pos.getX() / 16.0f;
                    float _snowman7 = vertex.pos.getY() / 16.0f;
                    float _snowman8 = vertex.pos.getZ() / 16.0f;
                    Vector4f _snowman9 = new Vector4f(_snowman6, _snowman7, _snowman8, 1.0f);
                    _snowman9.transform(matrix4f);
                    vertexConsumer.vertex(_snowman9.getX(), _snowman9.getY(), _snowman9.getZ(), red, green, blue, alpha, vertex.u, vertex.v, overlay, light, _snowman3, _snowman4, _snowman5);
                }
            }
        }
    }

    public ModelPart setTextureSize(int width, int height) {
        this.textureWidth = width;
        this.textureHeight = height;
        return this;
    }

    public Cuboid getRandomCuboid(Random random) {
        return (Cuboid)this.cuboids.get(random.nextInt(this.cuboids.size()));
    }

    static class Vertex {
        public final Vector3f pos;
        public final float u;
        public final float v;

        public Vertex(float x, float y, float z, float u, float v) {
            this(new Vector3f(x, y, z), u, v);
        }

        public Vertex remap(float u, float v) {
            return new Vertex(this.pos, u, v);
        }

        public Vertex(Vector3f pos, float u, float v) {
            this.pos = pos;
            this.u = u;
            this.v = v;
        }
    }

    static class Quad {
        public final Vertex[] vertices;
        public final Vector3f direction;

        public Quad(Vertex[] vertices, float u1, float v1, float u2, float v2, float squishU, float squishV, boolean flip, Direction direction) {
            this.vertices = vertices;
            float f = 0.0f / squishU;
            _snowman = 0.0f / squishV;
            vertices[0] = vertices[0].remap(u2 / squishU - f, v1 / squishV + _snowman);
            vertices[1] = vertices[1].remap(u1 / squishU + f, v1 / squishV + _snowman);
            vertices[2] = vertices[2].remap(u1 / squishU + f, v2 / squishV - _snowman);
            vertices[3] = vertices[3].remap(u2 / squishU - f, v2 / squishV - _snowman);
            if (flip) {
                int n = vertices.length;
                for (_snowman = 0; _snowman < n / 2; ++_snowman) {
                    Vertex vertex = vertices[_snowman];
                    vertices[_snowman] = vertices[n - 1 - _snowman];
                    vertices[n - 1 - _snowman] = vertex;
                }
            }
            this.direction = direction.getUnitVector();
            if (flip) {
                this.direction.multiplyComponentwise(-1.0f, 1.0f, 1.0f);
            }
        }
    }

    public static class Cuboid {
        private final Quad[] sides;
        public final float minX;
        public final float minY;
        public final float minZ;
        public final float maxX;
        public final float maxY;
        public final float maxZ;

        public Cuboid(int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, float textureWidth, float textureHeight) {
            this.minX = x;
            this.minY = y;
            this.minZ = z;
            this.maxX = x + sizeX;
            this.maxY = y + sizeY;
            this.maxZ = z + sizeZ;
            this.sides = new Quad[6];
            float f = x + sizeX;
            _snowman = y + sizeY;
            _snowman = z + sizeZ;
            x -= extraX;
            y -= extraY;
            z -= extraZ;
            f += extraX;
            _snowman += extraY;
            _snowman += extraZ;
            if (mirror) {
                _snowman = f;
                f = x;
                x = _snowman;
            }
            Vertex _snowman2 = new Vertex(x, y, z, 0.0f, 0.0f);
            Vertex _snowman3 = new Vertex(f, y, z, 0.0f, 8.0f);
            Vertex _snowman4 = new Vertex(f, _snowman, z, 8.0f, 8.0f);
            Vertex _snowman5 = new Vertex(x, _snowman, z, 8.0f, 0.0f);
            Vertex _snowman6 = new Vertex(x, y, _snowman, 0.0f, 0.0f);
            Vertex _snowman7 = new Vertex(f, y, _snowman, 0.0f, 8.0f);
            Vertex _snowman8 = new Vertex(f, _snowman, _snowman, 8.0f, 8.0f);
            Vertex _snowman9 = new Vertex(x, _snowman, _snowman, 8.0f, 0.0f);
            _snowman = u;
            _snowman = (float)u + sizeZ;
            _snowman = (float)u + sizeZ + sizeX;
            _snowman = (float)u + sizeZ + sizeX + sizeX;
            _snowman = (float)u + sizeZ + sizeX + sizeZ;
            _snowman = (float)u + sizeZ + sizeX + sizeZ + sizeX;
            _snowman = v;
            _snowman = (float)v + sizeZ;
            _snowman = (float)v + sizeZ + sizeY;
            this.sides[2] = new Quad(new Vertex[]{_snowman7, _snowman6, _snowman2, _snowman3}, _snowman, _snowman, _snowman, _snowman, textureWidth, textureHeight, mirror, Direction.DOWN);
            this.sides[3] = new Quad(new Vertex[]{_snowman4, _snowman5, _snowman9, _snowman8}, _snowman, _snowman, _snowman, _snowman, textureWidth, textureHeight, mirror, Direction.UP);
            this.sides[1] = new Quad(new Vertex[]{_snowman2, _snowman6, _snowman9, _snowman5}, _snowman, _snowman, _snowman, _snowman, textureWidth, textureHeight, mirror, Direction.WEST);
            this.sides[4] = new Quad(new Vertex[]{_snowman3, _snowman2, _snowman5, _snowman4}, _snowman, _snowman, _snowman, _snowman, textureWidth, textureHeight, mirror, Direction.NORTH);
            this.sides[0] = new Quad(new Vertex[]{_snowman7, _snowman3, _snowman4, _snowman8}, _snowman, _snowman, _snowman, _snowman, textureWidth, textureHeight, mirror, Direction.EAST);
            this.sides[5] = new Quad(new Vertex[]{_snowman6, _snowman7, _snowman8, _snowman9}, _snowman, _snowman, _snowman, _snowman, textureWidth, textureHeight, mirror, Direction.SOUTH);
        }
    }
}

