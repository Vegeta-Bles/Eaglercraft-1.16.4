package net.minecraft.util.math;

import java.nio.FloatBuffer;
import net.minecraft.client.util.math.Vector3f;

public final class Matrix4f {
   protected float a00;
   protected float a01;
   protected float a02;
   protected float a03;
   protected float a10;
   protected float a11;
   protected float a12;
   protected float a13;
   protected float a20;
   protected float a21;
   protected float a22;
   protected float a23;
   protected float a30;
   protected float a31;
   protected float a32;
   protected float a33;

   public Matrix4f() {
   }

   public Matrix4f(Matrix4f source) {
      this.a00 = source.a00;
      this.a01 = source.a01;
      this.a02 = source.a02;
      this.a03 = source.a03;
      this.a10 = source.a10;
      this.a11 = source.a11;
      this.a12 = source.a12;
      this.a13 = source.a13;
      this.a20 = source.a20;
      this.a21 = source.a21;
      this.a22 = source.a22;
      this.a23 = source.a23;
      this.a30 = source.a30;
      this.a31 = source.a31;
      this.a32 = source.a32;
      this.a33 = source.a33;
   }

   public Matrix4f(Quaternion quaternion) {
      float _snowman = quaternion.getX();
      float _snowmanx = quaternion.getY();
      float _snowmanxx = quaternion.getZ();
      float _snowmanxxx = quaternion.getW();
      float _snowmanxxxx = 2.0F * _snowman * _snowman;
      float _snowmanxxxxx = 2.0F * _snowmanx * _snowmanx;
      float _snowmanxxxxxx = 2.0F * _snowmanxx * _snowmanxx;
      this.a00 = 1.0F - _snowmanxxxxx - _snowmanxxxxxx;
      this.a11 = 1.0F - _snowmanxxxxxx - _snowmanxxxx;
      this.a22 = 1.0F - _snowmanxxxx - _snowmanxxxxx;
      this.a33 = 1.0F;
      float _snowmanxxxxxxx = _snowman * _snowmanx;
      float _snowmanxxxxxxxx = _snowmanx * _snowmanxx;
      float _snowmanxxxxxxxxx = _snowmanxx * _snowman;
      float _snowmanxxxxxxxxxx = _snowman * _snowmanxxx;
      float _snowmanxxxxxxxxxxx = _snowmanx * _snowmanxxx;
      float _snowmanxxxxxxxxxxxx = _snowmanxx * _snowmanxxx;
      this.a10 = 2.0F * (_snowmanxxxxxxx + _snowmanxxxxxxxxxxxx);
      this.a01 = 2.0F * (_snowmanxxxxxxx - _snowmanxxxxxxxxxxxx);
      this.a20 = 2.0F * (_snowmanxxxxxxxxx - _snowmanxxxxxxxxxxx);
      this.a02 = 2.0F * (_snowmanxxxxxxxxx + _snowmanxxxxxxxxxxx);
      this.a21 = 2.0F * (_snowmanxxxxxxxx + _snowmanxxxxxxxxxx);
      this.a12 = 2.0F * (_snowmanxxxxxxxx - _snowmanxxxxxxxxxx);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Matrix4f _snowman = (Matrix4f)o;
         return Float.compare(_snowman.a00, this.a00) == 0
            && Float.compare(_snowman.a01, this.a01) == 0
            && Float.compare(_snowman.a02, this.a02) == 0
            && Float.compare(_snowman.a03, this.a03) == 0
            && Float.compare(_snowman.a10, this.a10) == 0
            && Float.compare(_snowman.a11, this.a11) == 0
            && Float.compare(_snowman.a12, this.a12) == 0
            && Float.compare(_snowman.a13, this.a13) == 0
            && Float.compare(_snowman.a20, this.a20) == 0
            && Float.compare(_snowman.a21, this.a21) == 0
            && Float.compare(_snowman.a22, this.a22) == 0
            && Float.compare(_snowman.a23, this.a23) == 0
            && Float.compare(_snowman.a30, this.a30) == 0
            && Float.compare(_snowman.a31, this.a31) == 0
            && Float.compare(_snowman.a32, this.a32) == 0
            && Float.compare(_snowman.a33, this.a33) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.a00 != 0.0F ? Float.floatToIntBits(this.a00) : 0;
      _snowman = 31 * _snowman + (this.a01 != 0.0F ? Float.floatToIntBits(this.a01) : 0);
      _snowman = 31 * _snowman + (this.a02 != 0.0F ? Float.floatToIntBits(this.a02) : 0);
      _snowman = 31 * _snowman + (this.a03 != 0.0F ? Float.floatToIntBits(this.a03) : 0);
      _snowman = 31 * _snowman + (this.a10 != 0.0F ? Float.floatToIntBits(this.a10) : 0);
      _snowman = 31 * _snowman + (this.a11 != 0.0F ? Float.floatToIntBits(this.a11) : 0);
      _snowman = 31 * _snowman + (this.a12 != 0.0F ? Float.floatToIntBits(this.a12) : 0);
      _snowman = 31 * _snowman + (this.a13 != 0.0F ? Float.floatToIntBits(this.a13) : 0);
      _snowman = 31 * _snowman + (this.a20 != 0.0F ? Float.floatToIntBits(this.a20) : 0);
      _snowman = 31 * _snowman + (this.a21 != 0.0F ? Float.floatToIntBits(this.a21) : 0);
      _snowman = 31 * _snowman + (this.a22 != 0.0F ? Float.floatToIntBits(this.a22) : 0);
      _snowman = 31 * _snowman + (this.a23 != 0.0F ? Float.floatToIntBits(this.a23) : 0);
      _snowman = 31 * _snowman + (this.a30 != 0.0F ? Float.floatToIntBits(this.a30) : 0);
      _snowman = 31 * _snowman + (this.a31 != 0.0F ? Float.floatToIntBits(this.a31) : 0);
      _snowman = 31 * _snowman + (this.a32 != 0.0F ? Float.floatToIntBits(this.a32) : 0);
      return 31 * _snowman + (this.a33 != 0.0F ? Float.floatToIntBits(this.a33) : 0);
   }

   private static int pack(int x, int y) {
      return y * 4 + x;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append("Matrix4f:\n");
      _snowman.append(this.a00);
      _snowman.append(" ");
      _snowman.append(this.a01);
      _snowman.append(" ");
      _snowman.append(this.a02);
      _snowman.append(" ");
      _snowman.append(this.a03);
      _snowman.append("\n");
      _snowman.append(this.a10);
      _snowman.append(" ");
      _snowman.append(this.a11);
      _snowman.append(" ");
      _snowman.append(this.a12);
      _snowman.append(" ");
      _snowman.append(this.a13);
      _snowman.append("\n");
      _snowman.append(this.a20);
      _snowman.append(" ");
      _snowman.append(this.a21);
      _snowman.append(" ");
      _snowman.append(this.a22);
      _snowman.append(" ");
      _snowman.append(this.a23);
      _snowman.append("\n");
      _snowman.append(this.a30);
      _snowman.append(" ");
      _snowman.append(this.a31);
      _snowman.append(" ");
      _snowman.append(this.a32);
      _snowman.append(" ");
      _snowman.append(this.a33);
      _snowman.append("\n");
      return _snowman.toString();
   }

   public void writeToBuffer(FloatBuffer _snowman) {
      _snowman.put(pack(0, 0), this.a00);
      _snowman.put(pack(0, 1), this.a01);
      _snowman.put(pack(0, 2), this.a02);
      _snowman.put(pack(0, 3), this.a03);
      _snowman.put(pack(1, 0), this.a10);
      _snowman.put(pack(1, 1), this.a11);
      _snowman.put(pack(1, 2), this.a12);
      _snowman.put(pack(1, 3), this.a13);
      _snowman.put(pack(2, 0), this.a20);
      _snowman.put(pack(2, 1), this.a21);
      _snowman.put(pack(2, 2), this.a22);
      _snowman.put(pack(2, 3), this.a23);
      _snowman.put(pack(3, 0), this.a30);
      _snowman.put(pack(3, 1), this.a31);
      _snowman.put(pack(3, 2), this.a32);
      _snowman.put(pack(3, 3), this.a33);
   }

   public void loadIdentity() {
      this.a00 = 1.0F;
      this.a01 = 0.0F;
      this.a02 = 0.0F;
      this.a03 = 0.0F;
      this.a10 = 0.0F;
      this.a11 = 1.0F;
      this.a12 = 0.0F;
      this.a13 = 0.0F;
      this.a20 = 0.0F;
      this.a21 = 0.0F;
      this.a22 = 1.0F;
      this.a23 = 0.0F;
      this.a30 = 0.0F;
      this.a31 = 0.0F;
      this.a32 = 0.0F;
      this.a33 = 1.0F;
   }

   public float determinantAndAdjugate() {
      float _snowman = this.a00 * this.a11 - this.a01 * this.a10;
      float _snowmanx = this.a00 * this.a12 - this.a02 * this.a10;
      float _snowmanxx = this.a00 * this.a13 - this.a03 * this.a10;
      float _snowmanxxx = this.a01 * this.a12 - this.a02 * this.a11;
      float _snowmanxxxx = this.a01 * this.a13 - this.a03 * this.a11;
      float _snowmanxxxxx = this.a02 * this.a13 - this.a03 * this.a12;
      float _snowmanxxxxxx = this.a20 * this.a31 - this.a21 * this.a30;
      float _snowmanxxxxxxx = this.a20 * this.a32 - this.a22 * this.a30;
      float _snowmanxxxxxxxx = this.a20 * this.a33 - this.a23 * this.a30;
      float _snowmanxxxxxxxxx = this.a21 * this.a32 - this.a22 * this.a31;
      float _snowmanxxxxxxxxxx = this.a21 * this.a33 - this.a23 * this.a31;
      float _snowmanxxxxxxxxxxx = this.a22 * this.a33 - this.a23 * this.a32;
      float _snowmanxxxxxxxxxxxx = this.a11 * _snowmanxxxxxxxxxxx - this.a12 * _snowmanxxxxxxxxxx + this.a13 * _snowmanxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxx = -this.a10 * _snowmanxxxxxxxxxxx + this.a12 * _snowmanxxxxxxxx - this.a13 * _snowmanxxxxxxx;
      float _snowmanxxxxxxxxxxxxxx = this.a10 * _snowmanxxxxxxxxxx - this.a11 * _snowmanxxxxxxxx + this.a13 * _snowmanxxxxxx;
      float _snowmanxxxxxxxxxxxxxxx = -this.a10 * _snowmanxxxxxxxxx + this.a11 * _snowmanxxxxxxx - this.a12 * _snowmanxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxx = -this.a01 * _snowmanxxxxxxxxxxx + this.a02 * _snowmanxxxxxxxxxx - this.a03 * _snowmanxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxx = this.a00 * _snowmanxxxxxxxxxxx - this.a02 * _snowmanxxxxxxxx + this.a03 * _snowmanxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxx = -this.a00 * _snowmanxxxxxxxxxx + this.a01 * _snowmanxxxxxxxx - this.a03 * _snowmanxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxx = this.a00 * _snowmanxxxxxxxxx - this.a01 * _snowmanxxxxxxx + this.a02 * _snowmanxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxx = this.a31 * _snowmanxxxxx - this.a32 * _snowmanxxxx + this.a33 * _snowmanxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxx = -this.a30 * _snowmanxxxxx + this.a32 * _snowmanxx - this.a33 * _snowmanx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxx = this.a30 * _snowmanxxxx - this.a31 * _snowmanxx + this.a33 * _snowman;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = -this.a30 * _snowmanxxx + this.a31 * _snowmanx - this.a32 * _snowman;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = -this.a21 * _snowmanxxxxx + this.a22 * _snowmanxxxx - this.a23 * _snowmanxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = this.a20 * _snowmanxxxxx - this.a22 * _snowmanxx + this.a23 * _snowmanx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = -this.a20 * _snowmanxxxx + this.a21 * _snowmanxx - this.a23 * _snowman;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a20 * _snowmanxxx - this.a21 * _snowmanx + this.a22 * _snowman;
      this.a00 = _snowmanxxxxxxxxxxxx;
      this.a10 = _snowmanxxxxxxxxxxxxx;
      this.a20 = _snowmanxxxxxxxxxxxxxx;
      this.a30 = _snowmanxxxxxxxxxxxxxxx;
      this.a01 = _snowmanxxxxxxxxxxxxxxxx;
      this.a11 = _snowmanxxxxxxxxxxxxxxxxx;
      this.a21 = _snowmanxxxxxxxxxxxxxxxxxx;
      this.a31 = _snowmanxxxxxxxxxxxxxxxxxxx;
      this.a02 = _snowmanxxxxxxxxxxxxxxxxxxxx;
      this.a12 = _snowmanxxxxxxxxxxxxxxxxxxxxx;
      this.a22 = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
      this.a32 = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      this.a03 = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
      this.a13 = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx;
      this.a23 = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
      this.a33 = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      return _snowman * _snowmanxxxxxxxxxxx - _snowmanx * _snowmanxxxxxxxxxx + _snowmanxx * _snowmanxxxxxxxxx + _snowmanxxx * _snowmanxxxxxxxx - _snowmanxxxx * _snowmanxxxxxxx + _snowmanxxxxx * _snowmanxxxxxx;
   }

   public void transpose() {
      float _snowman = this.a10;
      this.a10 = this.a01;
      this.a01 = _snowman;
      _snowman = this.a20;
      this.a20 = this.a02;
      this.a02 = _snowman;
      _snowman = this.a21;
      this.a21 = this.a12;
      this.a12 = _snowman;
      _snowman = this.a30;
      this.a30 = this.a03;
      this.a03 = _snowman;
      _snowman = this.a31;
      this.a31 = this.a13;
      this.a13 = _snowman;
      _snowman = this.a32;
      this.a32 = this.a23;
      this.a23 = _snowman;
   }

   public boolean invert() {
      float _snowman = this.determinantAndAdjugate();
      if (Math.abs(_snowman) > 1.0E-6F) {
         this.multiply(_snowman);
         return true;
      } else {
         return false;
      }
   }

   public void multiply(Matrix4f matrix) {
      float _snowman = this.a00 * matrix.a00 + this.a01 * matrix.a10 + this.a02 * matrix.a20 + this.a03 * matrix.a30;
      float _snowmanx = this.a00 * matrix.a01 + this.a01 * matrix.a11 + this.a02 * matrix.a21 + this.a03 * matrix.a31;
      float _snowmanxx = this.a00 * matrix.a02 + this.a01 * matrix.a12 + this.a02 * matrix.a22 + this.a03 * matrix.a32;
      float _snowmanxxx = this.a00 * matrix.a03 + this.a01 * matrix.a13 + this.a02 * matrix.a23 + this.a03 * matrix.a33;
      float _snowmanxxxx = this.a10 * matrix.a00 + this.a11 * matrix.a10 + this.a12 * matrix.a20 + this.a13 * matrix.a30;
      float _snowmanxxxxx = this.a10 * matrix.a01 + this.a11 * matrix.a11 + this.a12 * matrix.a21 + this.a13 * matrix.a31;
      float _snowmanxxxxxx = this.a10 * matrix.a02 + this.a11 * matrix.a12 + this.a12 * matrix.a22 + this.a13 * matrix.a32;
      float _snowmanxxxxxxx = this.a10 * matrix.a03 + this.a11 * matrix.a13 + this.a12 * matrix.a23 + this.a13 * matrix.a33;
      float _snowmanxxxxxxxx = this.a20 * matrix.a00 + this.a21 * matrix.a10 + this.a22 * matrix.a20 + this.a23 * matrix.a30;
      float _snowmanxxxxxxxxx = this.a20 * matrix.a01 + this.a21 * matrix.a11 + this.a22 * matrix.a21 + this.a23 * matrix.a31;
      float _snowmanxxxxxxxxxx = this.a20 * matrix.a02 + this.a21 * matrix.a12 + this.a22 * matrix.a22 + this.a23 * matrix.a32;
      float _snowmanxxxxxxxxxxx = this.a20 * matrix.a03 + this.a21 * matrix.a13 + this.a22 * matrix.a23 + this.a23 * matrix.a33;
      float _snowmanxxxxxxxxxxxx = this.a30 * matrix.a00 + this.a31 * matrix.a10 + this.a32 * matrix.a20 + this.a33 * matrix.a30;
      float _snowmanxxxxxxxxxxxxx = this.a30 * matrix.a01 + this.a31 * matrix.a11 + this.a32 * matrix.a21 + this.a33 * matrix.a31;
      float _snowmanxxxxxxxxxxxxxx = this.a30 * matrix.a02 + this.a31 * matrix.a12 + this.a32 * matrix.a22 + this.a33 * matrix.a32;
      float _snowmanxxxxxxxxxxxxxxx = this.a30 * matrix.a03 + this.a31 * matrix.a13 + this.a32 * matrix.a23 + this.a33 * matrix.a33;
      this.a00 = _snowman;
      this.a01 = _snowmanx;
      this.a02 = _snowmanxx;
      this.a03 = _snowmanxxx;
      this.a10 = _snowmanxxxx;
      this.a11 = _snowmanxxxxx;
      this.a12 = _snowmanxxxxxx;
      this.a13 = _snowmanxxxxxxx;
      this.a20 = _snowmanxxxxxxxx;
      this.a21 = _snowmanxxxxxxxxx;
      this.a22 = _snowmanxxxxxxxxxx;
      this.a23 = _snowmanxxxxxxxxxxx;
      this.a30 = _snowmanxxxxxxxxxxxx;
      this.a31 = _snowmanxxxxxxxxxxxxx;
      this.a32 = _snowmanxxxxxxxxxxxxxx;
      this.a33 = _snowmanxxxxxxxxxxxxxxx;
   }

   public void multiply(Quaternion quaternion) {
      this.multiply(new Matrix4f(quaternion));
   }

   public void multiply(float scalar) {
      this.a00 *= scalar;
      this.a01 *= scalar;
      this.a02 *= scalar;
      this.a03 *= scalar;
      this.a10 *= scalar;
      this.a11 *= scalar;
      this.a12 *= scalar;
      this.a13 *= scalar;
      this.a20 *= scalar;
      this.a21 *= scalar;
      this.a22 *= scalar;
      this.a23 *= scalar;
      this.a30 *= scalar;
      this.a31 *= scalar;
      this.a32 *= scalar;
      this.a33 *= scalar;
   }

   public static Matrix4f viewboxMatrix(double fov, float aspectRatio, float cameraDepth, float viewDistance) {
      float _snowman = (float)(1.0 / Math.tan(fov * (float) (Math.PI / 180.0) / 2.0));
      Matrix4f _snowmanx = new Matrix4f();
      _snowmanx.a00 = _snowman / aspectRatio;
      _snowmanx.a11 = _snowman;
      _snowmanx.a22 = (viewDistance + cameraDepth) / (cameraDepth - viewDistance);
      _snowmanx.a32 = -1.0F;
      _snowmanx.a23 = 2.0F * viewDistance * cameraDepth / (cameraDepth - viewDistance);
      return _snowmanx;
   }

   public static Matrix4f projectionMatrix(float width, float height, float nearPlane, float farPlane) {
      Matrix4f _snowman = new Matrix4f();
      _snowman.a00 = 2.0F / width;
      _snowman.a11 = 2.0F / height;
      float _snowmanx = farPlane - nearPlane;
      _snowman.a22 = -2.0F / _snowmanx;
      _snowman.a33 = 1.0F;
      _snowman.a03 = -1.0F;
      _snowman.a13 = -1.0F;
      _snowman.a23 = -(farPlane + nearPlane) / _snowmanx;
      return _snowman;
   }

   public void addToLastColumn(Vector3f vector) {
      this.a03 = this.a03 + vector.getX();
      this.a13 = this.a13 + vector.getY();
      this.a23 = this.a23 + vector.getZ();
   }

   public Matrix4f copy() {
      return new Matrix4f(this);
   }

   public static Matrix4f scale(float x, float y, float z) {
      Matrix4f _snowman = new Matrix4f();
      _snowman.a00 = x;
      _snowman.a11 = y;
      _snowman.a22 = z;
      _snowman.a33 = 1.0F;
      return _snowman;
   }

   public static Matrix4f translate(float x, float y, float z) {
      Matrix4f _snowman = new Matrix4f();
      _snowman.a00 = 1.0F;
      _snowman.a11 = 1.0F;
      _snowman.a22 = 1.0F;
      _snowman.a33 = 1.0F;
      _snowman.a03 = x;
      _snowman.a13 = y;
      _snowman.a23 = z;
      return _snowman;
   }
}
