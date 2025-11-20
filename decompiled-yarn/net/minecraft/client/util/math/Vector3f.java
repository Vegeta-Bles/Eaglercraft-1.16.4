package net.minecraft.client.util.math;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

public final class Vector3f {
   public static Vector3f NEGATIVE_X = new Vector3f(-1.0F, 0.0F, 0.0F);
   public static Vector3f POSITIVE_X = new Vector3f(1.0F, 0.0F, 0.0F);
   public static Vector3f NEGATIVE_Y = new Vector3f(0.0F, -1.0F, 0.0F);
   public static Vector3f POSITIVE_Y = new Vector3f(0.0F, 1.0F, 0.0F);
   public static Vector3f NEGATIVE_Z = new Vector3f(0.0F, 0.0F, -1.0F);
   public static Vector3f POSITIVE_Z = new Vector3f(0.0F, 0.0F, 1.0F);
   private float x;
   private float y;
   private float z;

   public Vector3f() {
   }

   public Vector3f(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vector3f(Vec3d other) {
      this((float)other.x, (float)other.y, (float)other.z);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Vector3f _snowman = (Vector3f)o;
         if (Float.compare(_snowman.x, this.x) != 0) {
            return false;
         } else {
            return Float.compare(_snowman.y, this.y) != 0 ? false : Float.compare(_snowman.z, this.z) == 0;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = Float.floatToIntBits(this.x);
      _snowman = 31 * _snowman + Float.floatToIntBits(this.y);
      return 31 * _snowman + Float.floatToIntBits(this.z);
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

   public void scale(float scale) {
      this.x *= scale;
      this.y *= scale;
      this.z *= scale;
   }

   public void multiplyComponentwise(float x, float y, float z) {
      this.x *= x;
      this.y *= y;
      this.z *= z;
   }

   public void clamp(float min, float max) {
      this.x = MathHelper.clamp(this.x, min, max);
      this.y = MathHelper.clamp(this.y, min, max);
      this.z = MathHelper.clamp(this.z, min, max);
   }

   public void set(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public void add(float x, float y, float z) {
      this.x += x;
      this.y += y;
      this.z += z;
   }

   public void add(Vector3f vector) {
      this.x = this.x + vector.x;
      this.y = this.y + vector.y;
      this.z = this.z + vector.z;
   }

   public void subtract(Vector3f other) {
      this.x = this.x - other.x;
      this.y = this.y - other.y;
      this.z = this.z - other.z;
   }

   public float dot(Vector3f other) {
      return this.x * other.x + this.y * other.y + this.z * other.z;
   }

   public boolean normalize() {
      float _snowman = this.x * this.x + this.y * this.y + this.z * this.z;
      if ((double)_snowman < 1.0E-5) {
         return false;
      } else {
         float _snowmanx = MathHelper.fastInverseSqrt(_snowman);
         this.x *= _snowmanx;
         this.y *= _snowmanx;
         this.z *= _snowmanx;
         return true;
      }
   }

   public void cross(Vector3f vector) {
      float _snowman = this.x;
      float _snowmanx = this.y;
      float _snowmanxx = this.z;
      float _snowmanxxx = vector.getX();
      float _snowmanxxxx = vector.getY();
      float _snowmanxxxxx = vector.getZ();
      this.x = _snowmanx * _snowmanxxxxx - _snowmanxx * _snowmanxxxx;
      this.y = _snowmanxx * _snowmanxxx - _snowman * _snowmanxxxxx;
      this.z = _snowman * _snowmanxxxx - _snowmanx * _snowmanxxx;
   }

   public void transform(Matrix3f _snowman) {
      float _snowmanx = this.x;
      float _snowmanxx = this.y;
      float _snowmanxxx = this.z;
      this.x = _snowman.a00 * _snowmanx + _snowman.a01 * _snowmanxx + _snowman.a02 * _snowmanxxx;
      this.y = _snowman.a10 * _snowmanx + _snowman.a11 * _snowmanxx + _snowman.a12 * _snowmanxxx;
      this.z = _snowman.a20 * _snowmanx + _snowman.a21 * _snowmanxx + _snowman.a22 * _snowmanxxx;
   }

   public void rotate(Quaternion rotation) {
      Quaternion _snowman = new Quaternion(rotation);
      _snowman.hamiltonProduct(new Quaternion(this.getX(), this.getY(), this.getZ(), 0.0F));
      Quaternion _snowmanx = new Quaternion(rotation);
      _snowmanx.conjugate();
      _snowman.hamiltonProduct(_snowmanx);
      this.set(_snowman.getX(), _snowman.getY(), _snowman.getZ());
   }

   public void lerp(Vector3f vector, float delta) {
      float _snowman = 1.0F - delta;
      this.x = this.x * _snowman + vector.x * delta;
      this.y = this.y * _snowman + vector.y * delta;
      this.z = this.z * _snowman + vector.z * delta;
   }

   public Quaternion getRadialQuaternion(float angle) {
      return new Quaternion(this, angle, false);
   }

   public Quaternion getDegreesQuaternion(float angle) {
      return new Quaternion(this, angle, true);
   }

   public Vector3f copy() {
      return new Vector3f(this.x, this.y, this.z);
   }

   public void modify(Float2FloatFunction function) {
      this.x = function.get(this.x);
      this.y = function.get(this.y);
      this.z = function.get(this.z);
   }

   @Override
   public String toString() {
      return "[" + this.x + ", " + this.y + ", " + this.z + "]";
   }
}
