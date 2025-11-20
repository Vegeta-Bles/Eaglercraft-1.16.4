package net.minecraft.client.render;

import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Matrix4f;

public class Frustum {
   private final Vector4f[] homogeneousCoordinates = new Vector4f[6];
   private double x;
   private double y;
   private double z;

   public Frustum(Matrix4f _snowman, Matrix4f _snowman) {
      this.init(_snowman, _snowman);
   }

   public void setPosition(double cameraX, double cameraY, double cameraZ) {
      this.x = cameraX;
      this.y = cameraY;
      this.z = cameraZ;
   }

   private void init(Matrix4f _snowman, Matrix4f _snowman) {
      Matrix4f _snowmanxx = _snowman.copy();
      _snowmanxx.multiply(_snowman);
      _snowmanxx.transpose();
      this.transform(_snowmanxx, -1, 0, 0, 0);
      this.transform(_snowmanxx, 1, 0, 0, 1);
      this.transform(_snowmanxx, 0, -1, 0, 2);
      this.transform(_snowmanxx, 0, 1, 0, 3);
      this.transform(_snowmanxx, 0, 0, -1, 4);
      this.transform(_snowmanxx, 0, 0, 1, 5);
   }

   private void transform(Matrix4f function, int x, int y, int z, int index) {
      Vector4f _snowman = new Vector4f((float)x, (float)y, (float)z, 1.0F);
      _snowman.transform(function);
      _snowman.normalize();
      this.homogeneousCoordinates[index] = _snowman;
   }

   public boolean isVisible(Box box) {
      return this.isVisible(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
   }

   private boolean isVisible(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      float _snowman = (float)(minX - this.x);
      float _snowmanx = (float)(minY - this.y);
      float _snowmanxx = (float)(minZ - this.z);
      float _snowmanxxx = (float)(maxX - this.x);
      float _snowmanxxxx = (float)(maxY - this.y);
      float _snowmanxxxxx = (float)(maxZ - this.z);
      return this.isAnyCornerVisible(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   private boolean isAnyCornerVisible(float x1, float y1, float z1, float x2, float y2, float z2) {
      for (int _snowman = 0; _snowman < 6; _snowman++) {
         Vector4f _snowmanx = this.homogeneousCoordinates[_snowman];
         if (!(_snowmanx.dotProduct(new Vector4f(x1, y1, z1, 1.0F)) > 0.0F)
            && !(_snowmanx.dotProduct(new Vector4f(x2, y1, z1, 1.0F)) > 0.0F)
            && !(_snowmanx.dotProduct(new Vector4f(x1, y2, z1, 1.0F)) > 0.0F)
            && !(_snowmanx.dotProduct(new Vector4f(x2, y2, z1, 1.0F)) > 0.0F)
            && !(_snowmanx.dotProduct(new Vector4f(x1, y1, z2, 1.0F)) > 0.0F)
            && !(_snowmanx.dotProduct(new Vector4f(x2, y1, z2, 1.0F)) > 0.0F)
            && !(_snowmanx.dotProduct(new Vector4f(x1, y2, z2, 1.0F)) > 0.0F)
            && !(_snowmanx.dotProduct(new Vector4f(x2, y2, z2, 1.0F)) > 0.0F)) {
            return false;
         }
      }

      return true;
   }
}
