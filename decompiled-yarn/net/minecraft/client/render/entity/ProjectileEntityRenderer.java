package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public abstract class ProjectileEntityRenderer<T extends PersistentProjectileEntity> extends EntityRenderer<T> {
   public ProjectileEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(T _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(_snowman, _snowman.prevYaw, _snowman.yaw) - 90.0F));
      _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(_snowman, _snowman.prevPitch, _snowman.pitch)));
      int _snowmanxxxxxx = 0;
      float _snowmanxxxxxxx = 0.0F;
      float _snowmanxxxxxxxx = 0.5F;
      float _snowmanxxxxxxxxx = 0.0F;
      float _snowmanxxxxxxxxxx = 0.15625F;
      float _snowmanxxxxxxxxxxx = 0.0F;
      float _snowmanxxxxxxxxxxxx = 0.15625F;
      float _snowmanxxxxxxxxxxxxx = 0.15625F;
      float _snowmanxxxxxxxxxxxxxx = 0.3125F;
      float _snowmanxxxxxxxxxxxxxxx = 0.05625F;
      float _snowmanxxxxxxxxxxxxxxxx = (float)_snowman.shake - _snowman;
      if (_snowmanxxxxxxxxxxxxxxxx > 0.0F) {
         float _snowmanxxxxxxxxxxxxxxxxx = -MathHelper.sin(_snowmanxxxxxxxxxxxxxxxx * 3.0F) * _snowmanxxxxxxxxxxxxxxxx;
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowmanxxxxxxxxxxxxxxxxx));
      }

      _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(45.0F));
      _snowman.scale(0.05625F, 0.05625F, 0.05625F);
      _snowman.translate(-4.0, 0.0, 0.0);
      VertexConsumer _snowmanxxxxxxxxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getEntityCutout(this.getTexture(_snowman)));
      MatrixStack.Entry _snowmanxxxxxxxxxxxxxxxxxx = _snowman.peek();
      Matrix4f _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx.getModel();
      Matrix3f _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx.getNormal();
      this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, _snowman);
      this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, _snowman);
      this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, _snowman);
      this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, _snowman);
      this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, _snowman);
      this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, _snowman);
      this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, _snowman);
      this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, _snowman);

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxxxxxxxxxx++) {
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
         this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, _snowman);
         this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, _snowman);
         this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, _snowman);
         this.method_23153(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, _snowman);
      }

      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void method_23153(Matrix4f _snowman, Matrix3f _snowman, VertexConsumer _snowman, int _snowman, int _snowman, int _snowman, float _snowman, float _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      _snowman.vertex(_snowman, (float)_snowman, (float)_snowman, (float)_snowman)
         .color(255, 255, 255, 255)
         .texture(_snowman, _snowman)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(_snowman)
         .normal(_snowman, (float)_snowman, (float)_snowman, (float)_snowman)
         .next();
   }
}
