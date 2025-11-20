package net.minecraft.client.util.math;

import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import org.apache.commons.lang3.tuple.Triple;

public final class AffineTransformation {
   private final Matrix4f matrix;
   private boolean initialized;
   @Nullable
   private Vector3f translation;
   @Nullable
   private Quaternion rotation2;
   @Nullable
   private Vector3f scale;
   @Nullable
   private Quaternion rotation1;
   private static final AffineTransformation IDENTITY = Util.make(() -> {
      Matrix4f _snowman = new Matrix4f();
      _snowman.loadIdentity();
      AffineTransformation _snowmanx = new AffineTransformation(_snowman);
      _snowmanx.getRotation2();
      return _snowmanx;
   });

   public AffineTransformation(@Nullable Matrix4f matrix) {
      if (matrix == null) {
         this.matrix = IDENTITY.matrix;
      } else {
         this.matrix = matrix;
      }
   }

   public AffineTransformation(@Nullable Vector3f translation, @Nullable Quaternion rotation2, @Nullable Vector3f scale, @Nullable Quaternion rotation1) {
      this.matrix = setup(translation, rotation2, scale, rotation1);
      this.translation = translation != null ? translation : new Vector3f();
      this.rotation2 = rotation2 != null ? rotation2 : Quaternion.IDENTITY.copy();
      this.scale = scale != null ? scale : new Vector3f(1.0F, 1.0F, 1.0F);
      this.rotation1 = rotation1 != null ? rotation1 : Quaternion.IDENTITY.copy();
      this.initialized = true;
   }

   public static AffineTransformation identity() {
      return IDENTITY;
   }

   public AffineTransformation multiply(AffineTransformation other) {
      Matrix4f _snowman = this.getMatrix();
      _snowman.multiply(other.getMatrix());
      return new AffineTransformation(_snowman);
   }

   @Nullable
   public AffineTransformation invert() {
      if (this == IDENTITY) {
         return this;
      } else {
         Matrix4f _snowman = this.getMatrix();
         return _snowman.invert() ? new AffineTransformation(_snowman) : null;
      }
   }

   private void init() {
      if (!this.initialized) {
         Pair<Matrix3f, Vector3f> _snowman = getLinearTransformationAndTranslationFromAffine(this.matrix);
         Triple<Quaternion, Vector3f, Quaternion> _snowmanx = ((Matrix3f)_snowman.getFirst()).decomposeLinearTransformation();
         this.translation = (Vector3f)_snowman.getSecond();
         this.rotation2 = (Quaternion)_snowmanx.getLeft();
         this.scale = (Vector3f)_snowmanx.getMiddle();
         this.rotation1 = (Quaternion)_snowmanx.getRight();
         this.initialized = true;
      }
   }

   private static Matrix4f setup(@Nullable Vector3f translation, @Nullable Quaternion rotation2, @Nullable Vector3f scale, @Nullable Quaternion rotation1) {
      Matrix4f _snowman = new Matrix4f();
      _snowman.loadIdentity();
      if (rotation2 != null) {
         _snowman.multiply(new Matrix4f(rotation2));
      }

      if (scale != null) {
         _snowman.multiply(Matrix4f.scale(scale.getX(), scale.getY(), scale.getZ()));
      }

      if (rotation1 != null) {
         _snowman.multiply(new Matrix4f(rotation1));
      }

      if (translation != null) {
         _snowman.a03 = translation.getX();
         _snowman.a13 = translation.getY();
         _snowman.a23 = translation.getZ();
      }

      return _snowman;
   }

   public static Pair<Matrix3f, Vector3f> getLinearTransformationAndTranslationFromAffine(Matrix4f affineTransform) {
      affineTransform.multiply(1.0F / affineTransform.a33);
      Vector3f _snowman = new Vector3f(affineTransform.a03, affineTransform.a13, affineTransform.a23);
      Matrix3f _snowmanx = new Matrix3f(affineTransform);
      return Pair.of(_snowmanx, _snowman);
   }

   public Matrix4f getMatrix() {
      return this.matrix.copy();
   }

   public Quaternion getRotation2() {
      this.init();
      return this.rotation2.copy();
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         AffineTransformation _snowmanx = (AffineTransformation)_snowman;
         return Objects.equals(this.matrix, _snowmanx.matrix);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.matrix);
   }
}
