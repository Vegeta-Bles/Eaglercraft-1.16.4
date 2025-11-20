package net.minecraft.client.util.math;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;

public class Vector4f {
   private float x;
   private float y;
   private float z;
   private float w;

   public Vector4f() {
   }

   public Vector4f(float x, float y, float z, float w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
   }

   public Vector4f(Vector3f vector) {
      this(vector.getX(), vector.getY(), vector.getZ(), 1.0F);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Vector4f _snowman = (Vector4f)o;
         if (Float.compare(_snowman.x, this.x) != 0) {
            return false;
         } else if (Float.compare(_snowman.y, this.y) != 0) {
            return false;
         } else {
            return Float.compare(_snowman.z, this.z) != 0 ? false : Float.compare(_snowman.w, this.w) == 0;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = Float.floatToIntBits(this.x);
      _snowman = 31 * _snowman + Float.floatToIntBits(this.y);
      _snowman = 31 * _snowman + Float.floatToIntBits(this.z);
      return 31 * _snowman + Float.floatToIntBits(this.w);
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getZ() {
      return this.z;
   }

   public float getW() {
      return this.w;
   }

   public void multiplyComponentwise(Vector3f vector) {
      this.x = this.x * vector.getX();
      this.y = this.y * vector.getY();
      this.z = this.z * vector.getZ();
   }

   public void set(float x, float y, float z, float w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
   }

   public float dotProduct(Vector4f other) {
      return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
   }

   public boolean normalize() {
      float _snowman = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
      if ((double)_snowman < 1.0E-5) {
         return false;
      } else {
         float _snowmanx = MathHelper.fastInverseSqrt(_snowman);
         this.x *= _snowmanx;
         this.y *= _snowmanx;
         this.z *= _snowmanx;
         this.w *= _snowmanx;
         return true;
      }
   }

   public void transform(Matrix4f matrix) {
      float _snowman = this.x;
      float _snowmanx = this.y;
      float _snowmanxx = this.z;
      float _snowmanxxx = this.w;
      this.x = matrix.a00 * _snowman + matrix.a01 * _snowmanx + matrix.a02 * _snowmanxx + matrix.a03 * _snowmanxxx;
      this.y = matrix.a10 * _snowman + matrix.a11 * _snowmanx + matrix.a12 * _snowmanxx + matrix.a13 * _snowmanxxx;
      this.z = matrix.a20 * _snowman + matrix.a21 * _snowmanx + matrix.a22 * _snowmanxx + matrix.a23 * _snowmanxxx;
      this.w = matrix.a30 * _snowman + matrix.a31 * _snowmanx + matrix.a32 * _snowmanxx + matrix.a33 * _snowmanxxx;
   }

   public void rotate(Quaternion rotation) {
      Quaternion _snowman = new Quaternion(rotation);
      _snowman.hamiltonProduct(new Quaternion(this.getX(), this.getY(), this.getZ(), 0.0F));
      Quaternion _snowmanx = new Quaternion(rotation);
      _snowmanx.conjugate();
      _snowman.hamiltonProduct(_snowmanx);
      this.set(_snowman.getX(), _snowman.getY(), _snowman.getZ(), this.getW());
   }

   public void normalizeProjectiveCoordinates() {
      this.x = this.x / this.w;
      this.y = this.y / this.w;
      this.z = this.z / this.w;
      this.w = 1.0F;
   }

   @Override
   public String toString() {
      return "[" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + "]";
   }
}
