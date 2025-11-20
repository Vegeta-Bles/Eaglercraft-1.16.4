package net.minecraft.client.util.math;

import com.google.common.collect.Queues;
import java.util.Deque;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;

public class MatrixStack {
   private final Deque<MatrixStack.Entry> stack = Util.make(Queues.newArrayDeque(), _snowman -> {
      Matrix4f _snowmanx = new Matrix4f();
      _snowmanx.loadIdentity();
      Matrix3f _snowmanxx = new Matrix3f();
      _snowmanxx.loadIdentity();
      _snowman.add(new MatrixStack.Entry(_snowmanx, _snowmanxx));
   });

   public MatrixStack() {
   }

   public void translate(double x, double y, double z) {
      MatrixStack.Entry _snowman = this.stack.getLast();
      _snowman.modelMatrix.multiply(Matrix4f.translate((float)x, (float)y, (float)z));
   }

   public void scale(float x, float y, float z) {
      MatrixStack.Entry _snowman = this.stack.getLast();
      _snowman.modelMatrix.multiply(Matrix4f.scale(x, y, z));
      if (x == y && y == z) {
         if (x > 0.0F) {
            return;
         }

         _snowman.normalMatrix.multiply(-1.0F);
      }

      float _snowmanx = 1.0F / x;
      float _snowmanxx = 1.0F / y;
      float _snowmanxxx = 1.0F / z;
      float _snowmanxxxx = MathHelper.fastInverseCbrt(_snowmanx * _snowmanxx * _snowmanxxx);
      _snowman.normalMatrix.multiply(Matrix3f.scale(_snowmanxxxx * _snowmanx, _snowmanxxxx * _snowmanxx, _snowmanxxxx * _snowmanxxx));
   }

   public void multiply(Quaternion quaternion) {
      MatrixStack.Entry _snowman = this.stack.getLast();
      _snowman.modelMatrix.multiply(quaternion);
      _snowman.normalMatrix.multiply(quaternion);
   }

   public void push() {
      MatrixStack.Entry _snowman = this.stack.getLast();
      this.stack.addLast(new MatrixStack.Entry(_snowman.modelMatrix.copy(), _snowman.normalMatrix.copy()));
   }

   public void pop() {
      this.stack.removeLast();
   }

   public MatrixStack.Entry peek() {
      return this.stack.getLast();
   }

   public boolean isEmpty() {
      return this.stack.size() == 1;
   }

   public static final class Entry {
      private final Matrix4f modelMatrix;
      private final Matrix3f normalMatrix;

      private Entry(Matrix4f _snowman, Matrix3f _snowman) {
         this.modelMatrix = _snowman;
         this.normalMatrix = _snowman;
      }

      public Matrix4f getModel() {
         return this.modelMatrix;
      }

      public Matrix3f getNormal() {
         return this.normalMatrix;
      }
   }
}
