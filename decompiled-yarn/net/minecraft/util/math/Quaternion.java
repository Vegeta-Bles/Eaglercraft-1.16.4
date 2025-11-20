package net.minecraft.util.math;

import net.minecraft.client.util.math.Vector3f;

public final class Quaternion {
   public static final Quaternion IDENTITY = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
   private float x;
   private float y;
   private float z;
   private float w;

   public Quaternion(float x, float y, float z, float w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
   }

   public Quaternion(Vector3f axis, float rotationAngle, boolean degrees) {
      if (degrees) {
         rotationAngle *= (float) (Math.PI / 180.0);
      }

      float _snowman = sin(rotationAngle / 2.0F);
      this.x = axis.getX() * _snowman;
      this.y = axis.getY() * _snowman;
      this.z = axis.getZ() * _snowman;
      this.w = cos(rotationAngle / 2.0F);
   }

   public Quaternion(float x, float y, float z, boolean degrees) {
      if (degrees) {
         x *= (float) (Math.PI / 180.0);
         y *= (float) (Math.PI / 180.0);
         z *= (float) (Math.PI / 180.0);
      }

      float _snowman = sin(0.5F * x);
      float _snowmanx = cos(0.5F * x);
      float _snowmanxx = sin(0.5F * y);
      float _snowmanxxx = cos(0.5F * y);
      float _snowmanxxxx = sin(0.5F * z);
      float _snowmanxxxxx = cos(0.5F * z);
      this.x = _snowman * _snowmanxxx * _snowmanxxxxx + _snowmanx * _snowmanxx * _snowmanxxxx;
      this.y = _snowmanx * _snowmanxx * _snowmanxxxxx - _snowman * _snowmanxxx * _snowmanxxxx;
      this.z = _snowman * _snowmanxx * _snowmanxxxxx + _snowmanx * _snowmanxxx * _snowmanxxxx;
      this.w = _snowmanx * _snowmanxxx * _snowmanxxxxx - _snowman * _snowmanxx * _snowmanxxxx;
   }

   public Quaternion(Quaternion other) {
      this.x = other.x;
      this.y = other.y;
      this.z = other.z;
      this.w = other.w;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Quaternion _snowman = (Quaternion)o;
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

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append("Quaternion[").append(this.getW()).append(" + ");
      _snowman.append(this.getX()).append("i + ");
      _snowman.append(this.getY()).append("j + ");
      _snowman.append(this.getZ()).append("k]");
      return _snowman.toString();
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

   public void hamiltonProduct(Quaternion other) {
      float _snowman = this.getX();
      float _snowmanx = this.getY();
      float _snowmanxx = this.getZ();
      float _snowmanxxx = this.getW();
      float _snowmanxxxx = other.getX();
      float _snowmanxxxxx = other.getY();
      float _snowmanxxxxxx = other.getZ();
      float _snowmanxxxxxxx = other.getW();
      this.x = _snowmanxxx * _snowmanxxxx + _snowman * _snowmanxxxxxxx + _snowmanx * _snowmanxxxxxx - _snowmanxx * _snowmanxxxxx;
      this.y = _snowmanxxx * _snowmanxxxxx - _snowman * _snowmanxxxxxx + _snowmanx * _snowmanxxxxxxx + _snowmanxx * _snowmanxxxx;
      this.z = _snowmanxxx * _snowmanxxxxxx + _snowman * _snowmanxxxxx - _snowmanx * _snowmanxxxx + _snowmanxx * _snowmanxxxxxxx;
      this.w = _snowmanxxx * _snowmanxxxxxxx - _snowman * _snowmanxxxx - _snowmanx * _snowmanxxxxx - _snowmanxx * _snowmanxxxxxx;
   }

   public void scale(float scale) {
      this.x *= scale;
      this.y *= scale;
      this.z *= scale;
      this.w *= scale;
   }

   public void conjugate() {
      this.x = -this.x;
      this.y = -this.y;
      this.z = -this.z;
   }

   public void set(float x, float y, float z, float w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
   }

   private static float cos(float value) {
      return (float)Math.cos((double)value);
   }

   private static float sin(float value) {
      return (float)Math.sin((double)value);
   }

   public void normalize() {
      float _snowman = this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ() + this.getW() * this.getW();
      if (_snowman > 1.0E-6F) {
         float _snowmanx = MathHelper.fastInverseSqrt(_snowman);
         this.x *= _snowmanx;
         this.y *= _snowmanx;
         this.z *= _snowmanx;
         this.w *= _snowmanx;
      } else {
         this.x = 0.0F;
         this.y = 0.0F;
         this.z = 0.0F;
         this.w = 0.0F;
      }
   }

   public Quaternion copy() {
      return new Quaternion(this);
   }
}
