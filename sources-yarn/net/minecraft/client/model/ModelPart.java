package net.minecraft.client.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class ModelPart {
   private float textureWidth = 64.0F;
   private float textureHeight = 32.0F;
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
   private final ObjectList<ModelPart.Cuboid> cuboids = new ObjectArrayList();
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
      ModelPart lv = new ModelPart();
      lv.copyPositionAndRotation(this);
      return lv;
   }

   public void copyPositionAndRotation(ModelPart arg) {
      this.pitch = arg.pitch;
      this.yaw = arg.yaw;
      this.roll = arg.roll;
      this.pivotX = arg.pivotX;
      this.pivotY = arg.pivotY;
      this.pivotZ = arg.pivotZ;
   }

   public void addChild(ModelPart part) {
      this.children.add(part);
   }

   public ModelPart setTextureOffset(int textureOffsetU, int textureOffsetV) {
      this.textureOffsetU = textureOffsetU;
      this.textureOffsetV = textureOffsetV;
      return this;
   }

   public ModelPart addCuboid(String name, float x, float y, float z, int sizeX, int sizeY, int sizeZ, float extra, int textureOffsetU, int textureOffsetV) {
      this.setTextureOffset(textureOffsetU, textureOffsetV);
      this.addCuboid(this.textureOffsetU, this.textureOffsetV, x, y, z, (float)sizeX, (float)sizeY, (float)sizeZ, extra, extra, extra, this.mirror, false);
      return this;
   }

   public ModelPart addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ) {
      this.addCuboid(this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, 0.0F, 0.0F, 0.0F, this.mirror, false);
      return this;
   }

   public ModelPart addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ, boolean mirror) {
      this.addCuboid(this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, 0.0F, 0.0F, 0.0F, mirror, false);
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

   private void addCuboid(
      int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, boolean bl2
   ) {
      this.cuboids.add(new ModelPart.Cuboid(u, v, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ, mirror, this.textureWidth, this.textureHeight));
   }

   public void setPivot(float x, float y, float z) {
      this.pivotX = x;
      this.pivotY = y;
      this.pivotZ = z;
   }

   public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
      this.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
      if (this.visible) {
         if (!this.cuboids.isEmpty() || !this.children.isEmpty()) {
            matrices.push();
            this.rotate(matrices);
            this.renderCuboids(matrices.peek(), vertices, light, overlay, red, green, blue, alpha);
            ObjectListIterator var9 = this.children.iterator();

            while (var9.hasNext()) {
               ModelPart lv = (ModelPart)var9.next();
               lv.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            }

            matrices.pop();
         }
      }
   }

   public void rotate(MatrixStack matrix) {
      matrix.translate((double)(this.pivotX / 16.0F), (double)(this.pivotY / 16.0F), (double)(this.pivotZ / 16.0F));
      if (this.roll != 0.0F) {
         matrix.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(this.roll));
      }

      if (this.yaw != 0.0F) {
         matrix.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion(this.yaw));
      }

      if (this.pitch != 0.0F) {
         matrix.multiply(Vector3f.POSITIVE_X.getRadialQuaternion(this.pitch));
      }
   }

   private void renderCuboids(
      MatrixStack.Entry matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha
   ) {
      Matrix4f lv = matrices.getModel();
      Matrix3f lv2 = matrices.getNormal();
      ObjectListIterator var11 = this.cuboids.iterator();

      while (var11.hasNext()) {
         ModelPart.Cuboid lv3 = (ModelPart.Cuboid)var11.next();

         for (ModelPart.Quad lv4 : lv3.sides) {
            Vector3f lv5 = lv4.direction.copy();
            lv5.transform(lv2);
            float l = lv5.getX();
            float m = lv5.getY();
            float n = lv5.getZ();

            for (int o = 0; o < 4; o++) {
               ModelPart.Vertex lv6 = lv4.vertices[o];
               float p = lv6.pos.getX() / 16.0F;
               float q = lv6.pos.getY() / 16.0F;
               float r = lv6.pos.getZ() / 16.0F;
               Vector4f lv7 = new Vector4f(p, q, r, 1.0F);
               lv7.transform(lv);
               vertexConsumer.vertex(lv7.getX(), lv7.getY(), lv7.getZ(), red, green, blue, alpha, lv6.u, lv6.v, overlay, light, l, m, n);
            }
         }
      }
   }

   public ModelPart setTextureSize(int width, int height) {
      this.textureWidth = (float)width;
      this.textureHeight = (float)height;
      return this;
   }

   public ModelPart.Cuboid getRandomCuboid(Random random) {
      return (ModelPart.Cuboid)this.cuboids.get(random.nextInt(this.cuboids.size()));
   }

   @Environment(EnvType.CLIENT)
   public static class Cuboid {
      private final ModelPart.Quad[] sides;
      public final float minX;
      public final float minY;
      public final float minZ;
      public final float maxX;
      public final float maxY;
      public final float maxZ;

      public Cuboid(
         int u,
         int v,
         float x,
         float y,
         float z,
         float sizeX,
         float sizeY,
         float sizeZ,
         float extraX,
         float extraY,
         float extraZ,
         boolean mirror,
         float textureWidth,
         float textureHeight
      ) {
         this.minX = x;
         this.minY = y;
         this.minZ = z;
         this.maxX = x + sizeX;
         this.maxY = y + sizeY;
         this.maxZ = z + sizeZ;
         this.sides = new ModelPart.Quad[6];
         float s = x + sizeX;
         float t = y + sizeY;
         float ux = z + sizeZ;
         x -= extraX;
         y -= extraY;
         z -= extraZ;
         s += extraX;
         t += extraY;
         ux += extraZ;
         if (mirror) {
            float vx = s;
            s = x;
            x = vx;
         }

         ModelPart.Vertex lv = new ModelPart.Vertex(x, y, z, 0.0F, 0.0F);
         ModelPart.Vertex lv2 = new ModelPart.Vertex(s, y, z, 0.0F, 8.0F);
         ModelPart.Vertex lv3 = new ModelPart.Vertex(s, t, z, 8.0F, 8.0F);
         ModelPart.Vertex lv4 = new ModelPart.Vertex(x, t, z, 8.0F, 0.0F);
         ModelPart.Vertex lv5 = new ModelPart.Vertex(x, y, ux, 0.0F, 0.0F);
         ModelPart.Vertex lv6 = new ModelPart.Vertex(s, y, ux, 0.0F, 8.0F);
         ModelPart.Vertex lv7 = new ModelPart.Vertex(s, t, ux, 8.0F, 8.0F);
         ModelPart.Vertex lv8 = new ModelPart.Vertex(x, t, ux, 8.0F, 0.0F);
         float w = (float)u;
         float xx = (float)u + sizeZ;
         float yx = (float)u + sizeZ + sizeX;
         float zx = (float)u + sizeZ + sizeX + sizeX;
         float aa = (float)u + sizeZ + sizeX + sizeZ;
         float ab = (float)u + sizeZ + sizeX + sizeZ + sizeX;
         float ac = (float)v;
         float ad = (float)v + sizeZ;
         float ae = (float)v + sizeZ + sizeY;
         this.sides[2] = new ModelPart.Quad(new ModelPart.Vertex[]{lv6, lv5, lv, lv2}, xx, ac, yx, ad, textureWidth, textureHeight, mirror, Direction.DOWN);
         this.sides[3] = new ModelPart.Quad(new ModelPart.Vertex[]{lv3, lv4, lv8, lv7}, yx, ad, zx, ac, textureWidth, textureHeight, mirror, Direction.UP);
         this.sides[1] = new ModelPart.Quad(new ModelPart.Vertex[]{lv, lv5, lv8, lv4}, w, ad, xx, ae, textureWidth, textureHeight, mirror, Direction.WEST);
         this.sides[4] = new ModelPart.Quad(new ModelPart.Vertex[]{lv2, lv, lv4, lv3}, xx, ad, yx, ae, textureWidth, textureHeight, mirror, Direction.NORTH);
         this.sides[0] = new ModelPart.Quad(new ModelPart.Vertex[]{lv6, lv2, lv3, lv7}, yx, ad, aa, ae, textureWidth, textureHeight, mirror, Direction.EAST);
         this.sides[5] = new ModelPart.Quad(new ModelPart.Vertex[]{lv5, lv6, lv7, lv8}, aa, ad, ab, ae, textureWidth, textureHeight, mirror, Direction.SOUTH);
      }
   }

   @Environment(EnvType.CLIENT)
   static class Quad {
      public final ModelPart.Vertex[] vertices;
      public final Vector3f direction;

      public Quad(ModelPart.Vertex[] vertices, float u1, float v1, float u2, float v2, float squishU, float squishV, boolean flip, Direction direction) {
         this.vertices = vertices;
         float l = 0.0F / squishU;
         float m = 0.0F / squishV;
         vertices[0] = vertices[0].remap(u2 / squishU - l, v1 / squishV + m);
         vertices[1] = vertices[1].remap(u1 / squishU + l, v1 / squishV + m);
         vertices[2] = vertices[2].remap(u1 / squishU + l, v2 / squishV - m);
         vertices[3] = vertices[3].remap(u2 / squishU - l, v2 / squishV - m);
         if (flip) {
            int n = vertices.length;

            for (int o = 0; o < n / 2; o++) {
               ModelPart.Vertex lv = vertices[o];
               vertices[o] = vertices[n - 1 - o];
               vertices[n - 1 - o] = lv;
            }
         }

         this.direction = direction.getUnitVector();
         if (flip) {
            this.direction.multiplyComponentwise(-1.0F, 1.0F, 1.0F);
         }
      }
   }

   @Environment(EnvType.CLIENT)
   static class Vertex {
      public final Vector3f pos;
      public final float u;
      public final float v;

      public Vertex(float x, float y, float z, float u, float v) {
         this(new Vector3f(x, y, z), u, v);
      }

      public ModelPart.Vertex remap(float u, float v) {
         return new ModelPart.Vertex(this.pos, u, v);
      }

      public Vertex(Vector3f pos, float u, float v) {
         this.pos = pos;
         this.u = u;
         this.v = v;
      }
   }
}
