package net.minecraft.util.math;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.Vector3f;
import org.apache.commons.lang3.tuple.Triple;

public final class Matrix3f {
   private static final float THREE_PLUS_TWO_SQRT_TWO = 3.0F + 2.0F * (float)Math.sqrt(2.0);
   private static final float COS_PI_OVER_EIGHT = (float)Math.cos(Math.PI / 8);
   private static final float SIN_PI_OVER_EIGHT = (float)Math.sin(Math.PI / 8);
   private static final float SQRT_HALF = 1.0F / (float)Math.sqrt(2.0);
   public float a00;
   public float a01;
   public float a02;
   public float a10;
   public float a11;
   public float a12;
   public float a20;
   public float a21;
   public float a22;

   public Matrix3f() {
   }

   public Matrix3f(Quaternion source) {
      float f = source.getX();
      float g = source.getY();
      float h = source.getZ();
      float i = source.getW();
      float j = 2.0F * f * f;
      float k = 2.0F * g * g;
      float l = 2.0F * h * h;
      this.a00 = 1.0F - k - l;
      this.a11 = 1.0F - l - j;
      this.a22 = 1.0F - j - k;
      float m = f * g;
      float n = g * h;
      float o = h * f;
      float p = f * i;
      float q = g * i;
      float r = h * i;
      this.a10 = 2.0F * (m + r);
      this.a01 = 2.0F * (m - r);
      this.a20 = 2.0F * (o - q);
      this.a02 = 2.0F * (o + q);
      this.a21 = 2.0F * (n + p);
      this.a12 = 2.0F * (n - p);
   }

   @Environment(EnvType.CLIENT)
   public static Matrix3f scale(float x, float y, float z) {
      Matrix3f lv = new Matrix3f();
      lv.a00 = x;
      lv.a11 = y;
      lv.a22 = z;
      return lv;
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

   @Environment(EnvType.CLIENT)
   private static Pair<Float, Float> getSinAndCosOfRotation(float upperLeft, float diagonalAverage, float lowerRight) {
      float i = 2.0F * (upperLeft - lowerRight);
      if (THREE_PLUS_TWO_SQRT_TWO * diagonalAverage * diagonalAverage < i * i) {
         float k = MathHelper.fastInverseSqrt(diagonalAverage * diagonalAverage + i * i);
         return Pair.of(k * diagonalAverage, k * i);
      } else {
         return Pair.of(SIN_PI_OVER_EIGHT, COS_PI_OVER_EIGHT);
      }
   }

   @Environment(EnvType.CLIENT)
   private static Pair<Float, Float> method_22848(float f, float g) {
      float h = (float)Math.hypot((double)f, (double)g);
      float i = h > 1.0E-6F ? g : 0.0F;
      float j = Math.abs(f) + Math.max(h, 1.0E-6F);
      if (f < 0.0F) {
         float k = i;
         i = j;
         j = k;
      }

      float l = MathHelper.fastInverseSqrt(j * j + i * i);
      j *= l;
      i *= l;
      return Pair.of(i, j);
   }

   @Environment(EnvType.CLIENT)
   private static Quaternion method_22857(Matrix3f arg) {
      Matrix3f lv = new Matrix3f();
      Quaternion lv2 = Quaternion.IDENTITY.copy();
      if (arg.a01 * arg.a01 + arg.a10 * arg.a10 > 1.0E-6F) {
         Pair<Float, Float> pair = getSinAndCosOfRotation(arg.a00, 0.5F * (arg.a01 + arg.a10), arg.a11);
         Float float_ = (Float)pair.getFirst();
         Float float2 = (Float)pair.getSecond();
         Quaternion lv3 = new Quaternion(0.0F, 0.0F, float_, float2);
         float f = float2 * float2 - float_ * float_;
         float g = -2.0F * float_ * float2;
         float h = float2 * float2 + float_ * float_;
         lv2.hamiltonProduct(lv3);
         lv.loadIdentity();
         lv.a00 = f;
         lv.a11 = f;
         lv.a10 = -g;
         lv.a01 = g;
         lv.a22 = h;
         arg.multiply(lv);
         lv.transpose();
         lv.multiply(arg);
         arg.load(lv);
      }

      if (arg.a02 * arg.a02 + arg.a20 * arg.a20 > 1.0E-6F) {
         Pair<Float, Float> pair2 = getSinAndCosOfRotation(arg.a00, 0.5F * (arg.a02 + arg.a20), arg.a22);
         float i = -(Float)pair2.getFirst();
         Float float3 = (Float)pair2.getSecond();
         Quaternion lv4 = new Quaternion(0.0F, i, 0.0F, float3);
         float j = float3 * float3 - i * i;
         float k = -2.0F * i * float3;
         float l = float3 * float3 + i * i;
         lv2.hamiltonProduct(lv4);
         lv.loadIdentity();
         lv.a00 = j;
         lv.a22 = j;
         lv.a20 = k;
         lv.a02 = -k;
         lv.a11 = l;
         arg.multiply(lv);
         lv.transpose();
         lv.multiply(arg);
         arg.load(lv);
      }

      if (arg.a12 * arg.a12 + arg.a21 * arg.a21 > 1.0E-6F) {
         Pair<Float, Float> pair3 = getSinAndCosOfRotation(arg.a11, 0.5F * (arg.a12 + arg.a21), arg.a22);
         Float float4 = (Float)pair3.getFirst();
         Float float5 = (Float)pair3.getSecond();
         Quaternion lv5 = new Quaternion(float4, 0.0F, 0.0F, float5);
         float m = float5 * float5 - float4 * float4;
         float n = -2.0F * float4 * float5;
         float o = float5 * float5 + float4 * float4;
         lv2.hamiltonProduct(lv5);
         lv.loadIdentity();
         lv.a11 = m;
         lv.a22 = m;
         lv.a21 = -n;
         lv.a12 = n;
         lv.a00 = o;
         arg.multiply(lv);
         lv.transpose();
         lv.multiply(arg);
         arg.load(lv);
      }

      return lv2;
   }

   @Environment(EnvType.CLIENT)
   public void transpose() {
      float f = this.a01;
      this.a01 = this.a10;
      this.a10 = f;
      f = this.a02;
      this.a02 = this.a20;
      this.a20 = f;
      f = this.a12;
      this.a12 = this.a21;
      this.a21 = f;
   }

   @Environment(EnvType.CLIENT)
   public Triple<Quaternion, Vector3f, Quaternion> decomposeLinearTransformation() {
      Quaternion lv = Quaternion.IDENTITY.copy();
      Quaternion lv2 = Quaternion.IDENTITY.copy();
      Matrix3f lv3 = this.copy();
      lv3.transpose();
      lv3.multiply(this);

      for (int i = 0; i < 5; i++) {
         lv2.hamiltonProduct(method_22857(lv3));
      }

      lv2.normalize();
      Matrix3f lv4 = new Matrix3f(this);
      lv4.multiply(new Matrix3f(lv2));
      float f = 1.0F;
      Pair<Float, Float> pair = method_22848(lv4.a00, lv4.a10);
      Float float_ = (Float)pair.getFirst();
      Float float2 = (Float)pair.getSecond();
      float g = float2 * float2 - float_ * float_;
      float h = -2.0F * float_ * float2;
      float j = float2 * float2 + float_ * float_;
      Quaternion lv5 = new Quaternion(0.0F, 0.0F, float_, float2);
      lv.hamiltonProduct(lv5);
      Matrix3f lv6 = new Matrix3f();
      lv6.loadIdentity();
      lv6.a00 = g;
      lv6.a11 = g;
      lv6.a10 = h;
      lv6.a01 = -h;
      lv6.a22 = j;
      f *= j;
      lv6.multiply(lv4);
      pair = method_22848(lv6.a00, lv6.a20);
      float k = -(Float)pair.getFirst();
      Float float3 = (Float)pair.getSecond();
      float l = float3 * float3 - k * k;
      float m = -2.0F * k * float3;
      float n = float3 * float3 + k * k;
      Quaternion lv7 = new Quaternion(0.0F, k, 0.0F, float3);
      lv.hamiltonProduct(lv7);
      Matrix3f lv8 = new Matrix3f();
      lv8.loadIdentity();
      lv8.a00 = l;
      lv8.a22 = l;
      lv8.a20 = -m;
      lv8.a02 = m;
      lv8.a11 = n;
      f *= n;
      lv8.multiply(lv6);
      pair = method_22848(lv8.a11, lv8.a21);
      Float float4 = (Float)pair.getFirst();
      Float float5 = (Float)pair.getSecond();
      float o = float5 * float5 - float4 * float4;
      float p = -2.0F * float4 * float5;
      float q = float5 * float5 + float4 * float4;
      Quaternion lv9 = new Quaternion(float4, 0.0F, 0.0F, float5);
      lv.hamiltonProduct(lv9);
      Matrix3f lv10 = new Matrix3f();
      lv10.loadIdentity();
      lv10.a11 = o;
      lv10.a22 = o;
      lv10.a21 = p;
      lv10.a12 = -p;
      lv10.a00 = q;
      f *= q;
      lv10.multiply(lv8);
      f = 1.0F / f;
      lv.scale((float)Math.sqrt((double)f));
      Vector3f lv11 = new Vector3f(lv10.a00 * f, lv10.a11 * f, lv10.a22 * f);
      return Triple.of(lv, lv11, lv2);
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         Matrix3f lv = (Matrix3f)object;
         return Float.compare(lv.a00, this.a00) == 0
            && Float.compare(lv.a01, this.a01) == 0
            && Float.compare(lv.a02, this.a02) == 0
            && Float.compare(lv.a10, this.a10) == 0
            && Float.compare(lv.a11, this.a11) == 0
            && Float.compare(lv.a12, this.a12) == 0
            && Float.compare(lv.a20, this.a20) == 0
            && Float.compare(lv.a21, this.a21) == 0
            && Float.compare(lv.a22, this.a22) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int i = this.a00 != 0.0F ? Float.floatToIntBits(this.a00) : 0;
      i = 31 * i + (this.a01 != 0.0F ? Float.floatToIntBits(this.a01) : 0);
      i = 31 * i + (this.a02 != 0.0F ? Float.floatToIntBits(this.a02) : 0);
      i = 31 * i + (this.a10 != 0.0F ? Float.floatToIntBits(this.a10) : 0);
      i = 31 * i + (this.a11 != 0.0F ? Float.floatToIntBits(this.a11) : 0);
      i = 31 * i + (this.a12 != 0.0F ? Float.floatToIntBits(this.a12) : 0);
      i = 31 * i + (this.a20 != 0.0F ? Float.floatToIntBits(this.a20) : 0);
      i = 31 * i + (this.a21 != 0.0F ? Float.floatToIntBits(this.a21) : 0);
      return 31 * i + (this.a22 != 0.0F ? Float.floatToIntBits(this.a22) : 0);
   }

   @Environment(EnvType.CLIENT)
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
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Matrix3f:\n");
      stringBuilder.append(this.a00);
      stringBuilder.append(" ");
      stringBuilder.append(this.a01);
      stringBuilder.append(" ");
      stringBuilder.append(this.a02);
      stringBuilder.append("\n");
      stringBuilder.append(this.a10);
      stringBuilder.append(" ");
      stringBuilder.append(this.a11);
      stringBuilder.append(" ");
      stringBuilder.append(this.a12);
      stringBuilder.append("\n");
      stringBuilder.append(this.a20);
      stringBuilder.append(" ");
      stringBuilder.append(this.a21);
      stringBuilder.append(" ");
      stringBuilder.append(this.a22);
      stringBuilder.append("\n");
      return stringBuilder.toString();
   }

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
   public float determinantAndAdjugate() {
      float f = this.a11 * this.a22 - this.a12 * this.a21;
      float g = -(this.a10 * this.a22 - this.a12 * this.a20);
      float h = this.a10 * this.a21 - this.a11 * this.a20;
      float i = -(this.a01 * this.a22 - this.a02 * this.a21);
      float j = this.a00 * this.a22 - this.a02 * this.a20;
      float k = -(this.a00 * this.a21 - this.a01 * this.a20);
      float l = this.a01 * this.a12 - this.a02 * this.a11;
      float m = -(this.a00 * this.a12 - this.a02 * this.a10);
      float n = this.a00 * this.a11 - this.a01 * this.a10;
      float o = this.a00 * f + this.a01 * g + this.a02 * h;
      this.a00 = f;
      this.a10 = g;
      this.a20 = h;
      this.a01 = i;
      this.a11 = j;
      this.a21 = k;
      this.a02 = l;
      this.a12 = m;
      this.a22 = n;
      return o;
   }

   @Environment(EnvType.CLIENT)
   public boolean invert() {
      float f = this.determinantAndAdjugate();
      if (Math.abs(f) > 1.0E-6F) {
         this.multiply(f);
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
      float f = this.a00 * other.a00 + this.a01 * other.a10 + this.a02 * other.a20;
      float g = this.a00 * other.a01 + this.a01 * other.a11 + this.a02 * other.a21;
      float h = this.a00 * other.a02 + this.a01 * other.a12 + this.a02 * other.a22;
      float i = this.a10 * other.a00 + this.a11 * other.a10 + this.a12 * other.a20;
      float j = this.a10 * other.a01 + this.a11 * other.a11 + this.a12 * other.a21;
      float k = this.a10 * other.a02 + this.a11 * other.a12 + this.a12 * other.a22;
      float l = this.a20 * other.a00 + this.a21 * other.a10 + this.a22 * other.a20;
      float m = this.a20 * other.a01 + this.a21 * other.a11 + this.a22 * other.a21;
      float n = this.a20 * other.a02 + this.a21 * other.a12 + this.a22 * other.a22;
      this.a00 = f;
      this.a01 = g;
      this.a02 = h;
      this.a10 = i;
      this.a11 = j;
      this.a12 = k;
      this.a20 = l;
      this.a21 = m;
      this.a22 = n;
   }

   @Environment(EnvType.CLIENT)
   public void multiply(Quaternion arg) {
      this.multiply(new Matrix3f(arg));
   }

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
   public Matrix3f copy() {
      return new Matrix3f(this);
   }
}
