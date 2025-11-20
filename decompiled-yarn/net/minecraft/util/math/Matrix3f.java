package net.minecraft.util.math;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.util.math.Vector3f;
import org.apache.commons.lang3.tuple.Triple;

public final class Matrix3f {
   private static final float THREE_PLUS_TWO_SQRT_TWO = 3.0F + 2.0F * (float)Math.sqrt(2.0);
   private static final float COS_PI_OVER_EIGHT = (float)Math.cos(Math.PI / 8);
   private static final float SIN_PI_OVER_EIGHT = (float)Math.sin(Math.PI / 8);
   private static final float SQRT_HALF = 1.0F / (float)Math.sqrt(2.0);
   protected float a00;
   protected float a01;
   protected float a02;
   protected float a10;
   protected float a11;
   protected float a12;
   protected float a20;
   protected float a21;
   protected float a22;

   public Matrix3f() {
   }

   public Matrix3f(Quaternion source) {
      float _snowman = source.getX();
      float _snowmanx = source.getY();
      float _snowmanxx = source.getZ();
      float _snowmanxxx = source.getW();
      float _snowmanxxxx = 2.0F * _snowman * _snowman;
      float _snowmanxxxxx = 2.0F * _snowmanx * _snowmanx;
      float _snowmanxxxxxx = 2.0F * _snowmanxx * _snowmanxx;
      this.a00 = 1.0F - _snowmanxxxxx - _snowmanxxxxxx;
      this.a11 = 1.0F - _snowmanxxxxxx - _snowmanxxxx;
      this.a22 = 1.0F - _snowmanxxxx - _snowmanxxxxx;
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

   public static Matrix3f scale(float x, float y, float z) {
      Matrix3f _snowman = new Matrix3f();
      _snowman.a00 = x;
      _snowman.a11 = y;
      _snowman.a22 = z;
      return _snowman;
   }

   public Matrix3f(Matrix4f source) {
      this.a00 = source.a00;
      this.a01 = source.a01;
      this.a02 = source.a02;
      this.a10 = source.a10;
      this.a11 = source.a11;
      this.a12 = source.a12;
      this.a20 = source.a20;
      this.a21 = source.a21;
      this.a22 = source.a22;
   }

   public Matrix3f(Matrix3f source) {
      this.a00 = source.a00;
      this.a01 = source.a01;
      this.a02 = source.a02;
      this.a10 = source.a10;
      this.a11 = source.a11;
      this.a12 = source.a12;
      this.a20 = source.a20;
      this.a21 = source.a21;
      this.a22 = source.a22;
   }

   private static Pair<Float, Float> getSinAndCosOfRotation(float upperLeft, float diagonalAverage, float lowerRight) {
      float _snowman = 2.0F * (upperLeft - lowerRight);
      if (THREE_PLUS_TWO_SQRT_TWO * diagonalAverage * diagonalAverage < _snowman * _snowman) {
         float _snowmanx = MathHelper.fastInverseSqrt(diagonalAverage * diagonalAverage + _snowman * _snowman);
         return Pair.of(_snowmanx * diagonalAverage, _snowmanx * _snowman);
      } else {
         return Pair.of(SIN_PI_OVER_EIGHT, COS_PI_OVER_EIGHT);
      }
   }

   private static Pair<Float, Float> method_22848(float _snowman, float _snowmanAlt) {
      float _snowmanxx = (float)Math.hypot((double)_snowman, (double)_snowmanAlt);
      float _snowmanxxx = _snowmanxx > 1.0E-6F ? _snowman : 0.0F;
      float _snowmanxxxx = Math.abs(_snowmanAlt) + Math.max(_snowmanxx, 1.0E-6F);
      if (_snowmanAlt < 0.0F) {
         float _snowmanxxxxx = _snowmanxxx;
         _snowmanxxx = _snowmanxxxx;
         _snowmanxxxx = _snowmanxxxxx;
      }

      float _snowmanxxxxx = MathHelper.fastInverseSqrt(_snowmanxxxx * _snowmanxxxx + _snowmanxxx * _snowmanxxx);
      _snowmanxxxx *= _snowmanxxxxx;
      _snowmanxxx *= _snowmanxxxxx;
      return Pair.of(_snowmanxxx, _snowmanxxxx);
   }

   private static Quaternion method_22857(Matrix3f _snowman) {
      Matrix3f _snowmanx = new Matrix3f();
      Quaternion _snowmanxx = Quaternion.IDENTITY.copy();
      if (_snowman.a01 * _snowman.a01 + _snowman.a10 * _snowman.a10 > 1.0E-6F) {
         Pair<Float, Float> _snowmanxxx = getSinAndCosOfRotation(_snowman.a00, 0.5F * (_snowman.a01 + _snowman.a10), _snowman.a11);
         Float _snowmanxxxx = (Float)_snowmanxxx.getFirst();
         Float _snowmanxxxxx = (Float)_snowmanxxx.getSecond();
         Quaternion _snowmanxxxxxx = new Quaternion(0.0F, 0.0F, _snowmanxxxx, _snowmanxxxxx);
         float _snowmanxxxxxxx = _snowmanxxxxx * _snowmanxxxxx - _snowmanxxxx * _snowmanxxxx;
         float _snowmanxxxxxxxx = -2.0F * _snowmanxxxx * _snowmanxxxxx;
         float _snowmanxxxxxxxxx = _snowmanxxxxx * _snowmanxxxxx + _snowmanxxxx * _snowmanxxxx;
         _snowmanxx.hamiltonProduct(_snowmanxxxxxx);
         _snowmanx.loadIdentity();
         _snowmanx.a00 = _snowmanxxxxxxx;
         _snowmanx.a11 = _snowmanxxxxxxx;
         _snowmanx.a10 = -_snowmanxxxxxxxx;
         _snowmanx.a01 = _snowmanxxxxxxxx;
         _snowmanx.a22 = _snowmanxxxxxxxxx;
         _snowman.multiply(_snowmanx);
         _snowmanx.transpose();
         _snowmanx.multiply(_snowman);
         _snowman.load(_snowmanx);
      }

      if (_snowman.a02 * _snowman.a02 + _snowman.a20 * _snowman.a20 > 1.0E-6F) {
         Pair<Float, Float> _snowmanxxx = getSinAndCosOfRotation(_snowman.a00, 0.5F * (_snowman.a02 + _snowman.a20), _snowman.a22);
         float _snowmanxxxx = -(Float)_snowmanxxx.getFirst();
         Float _snowmanxxxxx = (Float)_snowmanxxx.getSecond();
         Quaternion _snowmanxxxxxx = new Quaternion(0.0F, _snowmanxxxx, 0.0F, _snowmanxxxxx);
         float _snowmanxxxxxxx = _snowmanxxxxx * _snowmanxxxxx - _snowmanxxxx * _snowmanxxxx;
         float _snowmanxxxxxxxx = -2.0F * _snowmanxxxx * _snowmanxxxxx;
         float _snowmanxxxxxxxxx = _snowmanxxxxx * _snowmanxxxxx + _snowmanxxxx * _snowmanxxxx;
         _snowmanxx.hamiltonProduct(_snowmanxxxxxx);
         _snowmanx.loadIdentity();
         _snowmanx.a00 = _snowmanxxxxxxx;
         _snowmanx.a22 = _snowmanxxxxxxx;
         _snowmanx.a20 = _snowmanxxxxxxxx;
         _snowmanx.a02 = -_snowmanxxxxxxxx;
         _snowmanx.a11 = _snowmanxxxxxxxxx;
         _snowman.multiply(_snowmanx);
         _snowmanx.transpose();
         _snowmanx.multiply(_snowman);
         _snowman.load(_snowmanx);
      }

      if (_snowman.a12 * _snowman.a12 + _snowman.a21 * _snowman.a21 > 1.0E-6F) {
         Pair<Float, Float> _snowmanxxx = getSinAndCosOfRotation(_snowman.a11, 0.5F * (_snowman.a12 + _snowman.a21), _snowman.a22);
         Float _snowmanxxxx = (Float)_snowmanxxx.getFirst();
         Float _snowmanxxxxx = (Float)_snowmanxxx.getSecond();
         Quaternion _snowmanxxxxxx = new Quaternion(_snowmanxxxx, 0.0F, 0.0F, _snowmanxxxxx);
         float _snowmanxxxxxxx = _snowmanxxxxx * _snowmanxxxxx - _snowmanxxxx * _snowmanxxxx;
         float _snowmanxxxxxxxx = -2.0F * _snowmanxxxx * _snowmanxxxxx;
         float _snowmanxxxxxxxxx = _snowmanxxxxx * _snowmanxxxxx + _snowmanxxxx * _snowmanxxxx;
         _snowmanxx.hamiltonProduct(_snowmanxxxxxx);
         _snowmanx.loadIdentity();
         _snowmanx.a11 = _snowmanxxxxxxx;
         _snowmanx.a22 = _snowmanxxxxxxx;
         _snowmanx.a21 = -_snowmanxxxxxxxx;
         _snowmanx.a12 = _snowmanxxxxxxxx;
         _snowmanx.a00 = _snowmanxxxxxxxxx;
         _snowman.multiply(_snowmanx);
         _snowmanx.transpose();
         _snowmanx.multiply(_snowman);
         _snowman.load(_snowmanx);
      }

      return _snowmanxx;
   }

   public void transpose() {
      float _snowman = this.a01;
      this.a01 = this.a10;
      this.a10 = _snowman;
      _snowman = this.a02;
      this.a02 = this.a20;
      this.a20 = _snowman;
      _snowman = this.a12;
      this.a12 = this.a21;
      this.a21 = _snowman;
   }

   public Triple<Quaternion, Vector3f, Quaternion> decomposeLinearTransformation() {
      Quaternion _snowman = Quaternion.IDENTITY.copy();
      Quaternion _snowmanx = Quaternion.IDENTITY.copy();
      Matrix3f _snowmanxx = this.copy();
      _snowmanxx.transpose();
      _snowmanxx.multiply(this);

      for (int _snowmanxxx = 0; _snowmanxxx < 5; _snowmanxxx++) {
         _snowmanx.hamiltonProduct(method_22857(_snowmanxx));
      }

      _snowmanx.normalize();
      Matrix3f _snowmanxxx = new Matrix3f(this);
      _snowmanxxx.multiply(new Matrix3f(_snowmanx));
      float _snowmanxxxx = 1.0F;
      Pair<Float, Float> _snowmanxxxxx = method_22848(_snowmanxxx.a00, _snowmanxxx.a10);
      Float _snowmanxxxxxx = (Float)_snowmanxxxxx.getFirst();
      Float _snowmanxxxxxxx = (Float)_snowmanxxxxx.getSecond();
      float _snowmanxxxxxxxx = _snowmanxxxxxxx * _snowmanxxxxxxx - _snowmanxxxxxx * _snowmanxxxxxx;
      float _snowmanxxxxxxxxx = -2.0F * _snowmanxxxxxx * _snowmanxxxxxxx;
      float _snowmanxxxxxxxxxx = _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxx * _snowmanxxxxxx;
      Quaternion _snowmanxxxxxxxxxxx = new Quaternion(0.0F, 0.0F, _snowmanxxxxxx, _snowmanxxxxxxx);
      _snowman.hamiltonProduct(_snowmanxxxxxxxxxxx);
      Matrix3f _snowmanxxxxxxxxxxxx = new Matrix3f();
      _snowmanxxxxxxxxxxxx.loadIdentity();
      _snowmanxxxxxxxxxxxx.a00 = _snowmanxxxxxxxx;
      _snowmanxxxxxxxxxxxx.a11 = _snowmanxxxxxxxx;
      _snowmanxxxxxxxxxxxx.a10 = _snowmanxxxxxxxxx;
      _snowmanxxxxxxxxxxxx.a01 = -_snowmanxxxxxxxxx;
      _snowmanxxxxxxxxxxxx.a22 = _snowmanxxxxxxxxxx;
      _snowmanxxxx *= _snowmanxxxxxxxxxx;
      _snowmanxxxxxxxxxxxx.multiply(_snowmanxxx);
      _snowmanxxxxx = method_22848(_snowmanxxxxxxxxxxxx.a00, _snowmanxxxxxxxxxxxx.a20);
      float _snowmanxxxxxxxxxxxxx = -(Float)_snowmanxxxxx.getFirst();
      Float _snowmanxxxxxxxxxxxxxx = (Float)_snowmanxxxxx.getSecond();
      float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxx = -2.0F * _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx;
      Quaternion _snowmanxxxxxxxxxxxxxxxxxx = new Quaternion(0.0F, _snowmanxxxxxxxxxxxxx, 0.0F, _snowmanxxxxxxxxxxxxxx);
      _snowman.hamiltonProduct(_snowmanxxxxxxxxxxxxxxxxxx);
      Matrix3f _snowmanxxxxxxxxxxxxxxxxxxx = new Matrix3f();
      _snowmanxxxxxxxxxxxxxxxxxxx.loadIdentity();
      _snowmanxxxxxxxxxxxxxxxxxxx.a00 = _snowmanxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxx.a22 = _snowmanxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxx.a20 = -_snowmanxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxx.a02 = _snowmanxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxx.a11 = _snowmanxxxxxxxxxxxxxxxxx;
      _snowmanxxxx *= _snowmanxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxx.multiply(_snowmanxxxxxxxxxxxx);
      _snowmanxxxxx = method_22848(_snowmanxxxxxxxxxxxxxxxxxxx.a11, _snowmanxxxxxxxxxxxxxxxxxxx.a21);
      Float _snowmanxxxxxxxxxxxxxxxxxxxx = (Float)_snowmanxxxxx.getFirst();
      Float _snowmanxxxxxxxxxxxxxxxxxxxxx = (Float)_snowmanxxxxx.getSecond();
      float _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = -2.0F * _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx;
      Quaternion _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = new Quaternion(_snowmanxxxxxxxxxxxxxxxxxxxx, 0.0F, 0.0F, _snowmanxxxxxxxxxxxxxxxxxxxxx);
      _snowman.hamiltonProduct(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
      Matrix3f _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = new Matrix3f();
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.loadIdentity();
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a11 = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a22 = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a21 = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a12 = -_snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a00 = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxx *= _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.multiply(_snowmanxxxxxxxxxxxxxxxxxxx);
      _snowmanxxxx = 1.0F / _snowmanxxxx;
      _snowman.scale((float)Math.sqrt((double)_snowmanxxxx));
      Vector3f _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Vector3f(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a00 * _snowmanxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a11 * _snowmanxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a22 * _snowmanxxxx
      );
      return Triple.of(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanx);
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         Matrix3f _snowmanx = (Matrix3f)_snowman;
         return Float.compare(_snowmanx.a00, this.a00) == 0
            && Float.compare(_snowmanx.a01, this.a01) == 0
            && Float.compare(_snowmanx.a02, this.a02) == 0
            && Float.compare(_snowmanx.a10, this.a10) == 0
            && Float.compare(_snowmanx.a11, this.a11) == 0
            && Float.compare(_snowmanx.a12, this.a12) == 0
            && Float.compare(_snowmanx.a20, this.a20) == 0
            && Float.compare(_snowmanx.a21, this.a21) == 0
            && Float.compare(_snowmanx.a22, this.a22) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.a00 != 0.0F ? Float.floatToIntBits(this.a00) : 0;
      _snowman = 31 * _snowman + (this.a01 != 0.0F ? Float.floatToIntBits(this.a01) : 0);
      _snowman = 31 * _snowman + (this.a02 != 0.0F ? Float.floatToIntBits(this.a02) : 0);
      _snowman = 31 * _snowman + (this.a10 != 0.0F ? Float.floatToIntBits(this.a10) : 0);
      _snowman = 31 * _snowman + (this.a11 != 0.0F ? Float.floatToIntBits(this.a11) : 0);
      _snowman = 31 * _snowman + (this.a12 != 0.0F ? Float.floatToIntBits(this.a12) : 0);
      _snowman = 31 * _snowman + (this.a20 != 0.0F ? Float.floatToIntBits(this.a20) : 0);
      _snowman = 31 * _snowman + (this.a21 != 0.0F ? Float.floatToIntBits(this.a21) : 0);
      return 31 * _snowman + (this.a22 != 0.0F ? Float.floatToIntBits(this.a22) : 0);
   }

   public void load(Matrix3f source) {
      this.a00 = source.a00;
      this.a01 = source.a01;
      this.a02 = source.a02;
      this.a10 = source.a10;
      this.a11 = source.a11;
      this.a12 = source.a12;
      this.a20 = source.a20;
      this.a21 = source.a21;
      this.a22 = source.a22;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append("Matrix3f:\n");
      _snowman.append(this.a00);
      _snowman.append(" ");
      _snowman.append(this.a01);
      _snowman.append(" ");
      _snowman.append(this.a02);
      _snowman.append("\n");
      _snowman.append(this.a10);
      _snowman.append(" ");
      _snowman.append(this.a11);
      _snowman.append(" ");
      _snowman.append(this.a12);
      _snowman.append("\n");
      _snowman.append(this.a20);
      _snowman.append(" ");
      _snowman.append(this.a21);
      _snowman.append(" ");
      _snowman.append(this.a22);
      _snowman.append("\n");
      return _snowman.toString();
   }

   public void loadIdentity() {
      this.a00 = 1.0F;
      this.a01 = 0.0F;
      this.a02 = 0.0F;
      this.a10 = 0.0F;
      this.a11 = 1.0F;
      this.a12 = 0.0F;
      this.a20 = 0.0F;
      this.a21 = 0.0F;
      this.a22 = 1.0F;
   }

   public float determinantAndAdjugate() {
      float _snowman = this.a11 * this.a22 - this.a12 * this.a21;
      float _snowmanx = -(this.a10 * this.a22 - this.a12 * this.a20);
      float _snowmanxx = this.a10 * this.a21 - this.a11 * this.a20;
      float _snowmanxxx = -(this.a01 * this.a22 - this.a02 * this.a21);
      float _snowmanxxxx = this.a00 * this.a22 - this.a02 * this.a20;
      float _snowmanxxxxx = -(this.a00 * this.a21 - this.a01 * this.a20);
      float _snowmanxxxxxx = this.a01 * this.a12 - this.a02 * this.a11;
      float _snowmanxxxxxxx = -(this.a00 * this.a12 - this.a02 * this.a10);
      float _snowmanxxxxxxxx = this.a00 * this.a11 - this.a01 * this.a10;
      float _snowmanxxxxxxxxx = this.a00 * _snowman + this.a01 * _snowmanx + this.a02 * _snowmanxx;
      this.a00 = _snowman;
      this.a10 = _snowmanx;
      this.a20 = _snowmanxx;
      this.a01 = _snowmanxxx;
      this.a11 = _snowmanxxxx;
      this.a21 = _snowmanxxxxx;
      this.a02 = _snowmanxxxxxx;
      this.a12 = _snowmanxxxxxxx;
      this.a22 = _snowmanxxxxxxxx;
      return _snowmanxxxxxxxxx;
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

   public void set(int x, int y, float value) {
      if (x == 0) {
         if (y == 0) {
            this.a00 = value;
         } else if (y == 1) {
            this.a01 = value;
         } else {
            this.a02 = value;
         }
      } else if (x == 1) {
         if (y == 0) {
            this.a10 = value;
         } else if (y == 1) {
            this.a11 = value;
         } else {
            this.a12 = value;
         }
      } else if (y == 0) {
         this.a20 = value;
      } else if (y == 1) {
         this.a21 = value;
      } else {
         this.a22 = value;
      }
   }

   public void multiply(Matrix3f other) {
      float _snowman = this.a00 * other.a00 + this.a01 * other.a10 + this.a02 * other.a20;
      float _snowmanx = this.a00 * other.a01 + this.a01 * other.a11 + this.a02 * other.a21;
      float _snowmanxx = this.a00 * other.a02 + this.a01 * other.a12 + this.a02 * other.a22;
      float _snowmanxxx = this.a10 * other.a00 + this.a11 * other.a10 + this.a12 * other.a20;
      float _snowmanxxxx = this.a10 * other.a01 + this.a11 * other.a11 + this.a12 * other.a21;
      float _snowmanxxxxx = this.a10 * other.a02 + this.a11 * other.a12 + this.a12 * other.a22;
      float _snowmanxxxxxx = this.a20 * other.a00 + this.a21 * other.a10 + this.a22 * other.a20;
      float _snowmanxxxxxxx = this.a20 * other.a01 + this.a21 * other.a11 + this.a22 * other.a21;
      float _snowmanxxxxxxxx = this.a20 * other.a02 + this.a21 * other.a12 + this.a22 * other.a22;
      this.a00 = _snowman;
      this.a01 = _snowmanx;
      this.a02 = _snowmanxx;
      this.a10 = _snowmanxxx;
      this.a11 = _snowmanxxxx;
      this.a12 = _snowmanxxxxx;
      this.a20 = _snowmanxxxxxx;
      this.a21 = _snowmanxxxxxxx;
      this.a22 = _snowmanxxxxxxxx;
   }

   public void multiply(Quaternion _snowman) {
      this.multiply(new Matrix3f(_snowman));
   }

   public void multiply(float scalar) {
      this.a00 *= scalar;
      this.a01 *= scalar;
      this.a02 *= scalar;
      this.a10 *= scalar;
      this.a11 *= scalar;
      this.a12 *= scalar;
      this.a20 *= scalar;
      this.a21 *= scalar;
      this.a22 *= scalar;
   }

   public Matrix3f copy() {
      return new Matrix3f(this);
   }
}
