package net.minecraft.client.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Random;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

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
      ModelPart _snowman = new ModelPart();
      _snowman.copyPositionAndRotation(this);
      return _snowman;
   }

   public void copyPositionAndRotation(ModelPart _snowman) {
      this.pitch = _snowman.pitch;
      this.yaw = _snowman.yaw;
      this.roll = _snowman.roll;
      this.pivotX = _snowman.pivotX;
      this.pivotY = _snowman.pivotY;
      this.pivotZ = _snowman.pivotZ;
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
      int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, boolean _snowman
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
               ModelPart _snowman = (ModelPart)var9.next();
               _snowman.render(matrices, vertices, light, overlay, red, green, blue, alpha);
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
      Matrix4f _snowman = matrices.getModel();
      Matrix3f _snowmanx = matrices.getNormal();
      ObjectListIterator var11 = this.cuboids.iterator();

      while (var11.hasNext()) {
         ModelPart.Cuboid _snowmanxx = (ModelPart.Cuboid)var11.next();

         for (ModelPart.Quad _snowmanxxx : _snowmanxx.sides) {
            Vector3f _snowmanxxxx = _snowmanxxx.direction.copy();
            _snowmanxxxx.transform(_snowmanx);
            float _snowmanxxxxx = _snowmanxxxx.getX();
            float _snowmanxxxxxx = _snowmanxxxx.getY();
            float _snowmanxxxxxxx = _snowmanxxxx.getZ();

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 4; _snowmanxxxxxxxx++) {
               ModelPart.Vertex _snowmanxxxxxxxxx = _snowmanxxx.vertices[_snowmanxxxxxxxx];
               float _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.pos.getX() / 16.0F;
               float _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.pos.getY() / 16.0F;
               float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx.pos.getZ() / 16.0F;
               Vector4f _snowmanxxxxxxxxxxxxx = new Vector4f(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 1.0F);
               _snowmanxxxxxxxxxxxxx.transform(_snowman);
               vertexConsumer.vertex(
                  _snowmanxxxxxxxxxxxxx.getX(),
                  _snowmanxxxxxxxxxxxxx.getY(),
                  _snowmanxxxxxxxxxxxxx.getZ(),
                  red,
                  green,
                  blue,
                  alpha,
                  _snowmanxxxxxxxxx.u,
                  _snowmanxxxxxxxxx.v,
                  overlay,
                  light,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxx
               );
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
         float _snowman = x + sizeX;
         float _snowmanx = y + sizeY;
         float _snowmanxx = z + sizeZ;
         x -= extraX;
         y -= extraY;
         z -= extraZ;
         _snowman += extraX;
         _snowmanx += extraY;
         _snowmanxx += extraZ;
         if (mirror) {
            float _snowmanxxx = _snowman;
            _snowman = x;
            x = _snowmanxxx;
         }

         ModelPart.Vertex _snowmanxxx = new ModelPart.Vertex(x, y, z, 0.0F, 0.0F);
         ModelPart.Vertex _snowmanxxxx = new ModelPart.Vertex(_snowman, y, z, 0.0F, 8.0F);
         ModelPart.Vertex _snowmanxxxxx = new ModelPart.Vertex(_snowman, _snowmanx, z, 8.0F, 8.0F);
         ModelPart.Vertex _snowmanxxxxxx = new ModelPart.Vertex(x, _snowmanx, z, 8.0F, 0.0F);
         ModelPart.Vertex _snowmanxxxxxxx = new ModelPart.Vertex(x, y, _snowmanxx, 0.0F, 0.0F);
         ModelPart.Vertex _snowmanxxxxxxxx = new ModelPart.Vertex(_snowman, y, _snowmanxx, 0.0F, 8.0F);
         ModelPart.Vertex _snowmanxxxxxxxxx = new ModelPart.Vertex(_snowman, _snowmanx, _snowmanxx, 8.0F, 8.0F);
         ModelPart.Vertex _snowmanxxxxxxxxxx = new ModelPart.Vertex(x, _snowmanx, _snowmanxx, 8.0F, 0.0F);
         float _snowmanxxxxxxxxxxx = (float)u;
         float _snowmanxxxxxxxxxxxx = (float)u + sizeZ;
         float _snowmanxxxxxxxxxxxxx = (float)u + sizeZ + sizeX;
         float _snowmanxxxxxxxxxxxxxx = (float)u + sizeZ + sizeX + sizeX;
         float _snowmanxxxxxxxxxxxxxxx = (float)u + sizeZ + sizeX + sizeZ;
         float _snowmanxxxxxxxxxxxxxxxx = (float)u + sizeZ + sizeX + sizeZ + sizeX;
         float _snowmanxxxxxxxxxxxxxxxxx = (float)v;
         float _snowmanxxxxxxxxxxxxxxxxxx = (float)v + sizeZ;
         float _snowmanxxxxxxxxxxxxxxxxxxx = (float)v + sizeZ + sizeY;
         this.sides[2] = new ModelPart.Quad(
            new ModelPart.Vertex[]{_snowmanxxxxxxxx, _snowmanxxxxxxx, _snowmanxxx, _snowmanxxxx},
            _snowmanxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxx,
            textureWidth,
            textureHeight,
            mirror,
            Direction.DOWN
         );
         this.sides[3] = new ModelPart.Quad(
            new ModelPart.Vertex[]{_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx},
            _snowmanxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxx,
            textureWidth,
            textureHeight,
            mirror,
            Direction.UP
         );
         this.sides[1] = new ModelPart.Quad(
            new ModelPart.Vertex[]{_snowmanxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxx},
            _snowmanxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxx,
            textureWidth,
            textureHeight,
            mirror,
            Direction.WEST
         );
         this.sides[4] = new ModelPart.Quad(
            new ModelPart.Vertex[]{_snowmanxxxx, _snowmanxxx, _snowmanxxxxxx, _snowmanxxxxx},
            _snowmanxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxx,
            textureWidth,
            textureHeight,
            mirror,
            Direction.NORTH
         );
         this.sides[0] = new ModelPart.Quad(
            new ModelPart.Vertex[]{_snowmanxxxxxxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxxxx},
            _snowmanxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxx,
            textureWidth,
            textureHeight,
            mirror,
            Direction.EAST
         );
         this.sides[5] = new ModelPart.Quad(
            new ModelPart.Vertex[]{_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx},
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxx,
            textureWidth,
            textureHeight,
            mirror,
            Direction.SOUTH
         );
      }
   }

   static class Quad {
      public final ModelPart.Vertex[] vertices;
      public final Vector3f direction;

      public Quad(ModelPart.Vertex[] vertices, float u1, float v1, float u2, float v2, float squishU, float squishV, boolean flip, Direction direction) {
         this.vertices = vertices;
         float _snowman = 0.0F / squishU;
         float _snowmanx = 0.0F / squishV;
         vertices[0] = vertices[0].remap(u2 / squishU - _snowman, v1 / squishV + _snowmanx);
         vertices[1] = vertices[1].remap(u1 / squishU + _snowman, v1 / squishV + _snowmanx);
         vertices[2] = vertices[2].remap(u1 / squishU + _snowman, v2 / squishV - _snowmanx);
         vertices[3] = vertices[3].remap(u2 / squishU - _snowman, v2 / squishV - _snowmanx);
         if (flip) {
            int _snowmanxx = vertices.length;

            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx / 2; _snowmanxxx++) {
               ModelPart.Vertex _snowmanxxxx = vertices[_snowmanxxx];
               vertices[_snowmanxxx] = vertices[_snowmanxx - 1 - _snowmanxxx];
               vertices[_snowmanxx - 1 - _snowmanxxx] = _snowmanxxxx;
            }
         }

         this.direction = direction.getUnitVector();
         if (flip) {
            this.direction.multiplyComponentwise(-1.0F, 1.0F, 1.0F);
         }
      }
   }

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
