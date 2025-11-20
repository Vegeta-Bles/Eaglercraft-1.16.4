package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public abstract class ProjectileEntityRenderer<T extends PersistentProjectileEntity> extends EntityRenderer<T> {
   public ProjectileEntityRenderer(EntityRenderDispatcher arg) {
      super(arg);
   }

   public void render(T arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      arg2.push();
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, arg.prevYaw, arg.yaw) - 90.0F));
      arg2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, arg.prevPitch, arg.pitch)));
      int j = 0;
      float h = 0.0F;
      float k = 0.5F;
      float l = 0.0F;
      float m = 0.15625F;
      float n = 0.0F;
      float o = 0.15625F;
      float p = 0.15625F;
      float q = 0.3125F;
      float r = 0.05625F;
      float s = (float)arg.shake - g;
      if (s > 0.0F) {
         float t = -MathHelper.sin(s * 3.0F) * s;
         arg2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(t));
      }

      arg2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(45.0F));
      arg2.scale(0.05625F, 0.05625F, 0.05625F);
      arg2.translate(-4.0, 0.0, 0.0);
      VertexConsumer lv = arg3.getBuffer(RenderLayer.getEntityCutout(this.getTexture(arg)));
      MatrixStack.Entry lv2 = arg2.peek();
      Matrix4f lv3 = lv2.getModel();
      Matrix3f lv4 = lv2.getNormal();
      this.method_23153(lv3, lv4, lv, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, i);
      this.method_23153(lv3, lv4, lv, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, i);
      this.method_23153(lv3, lv4, lv, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, i);
      this.method_23153(lv3, lv4, lv, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, i);
      this.method_23153(lv3, lv4, lv, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, i);
      this.method_23153(lv3, lv4, lv, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, i);
      this.method_23153(lv3, lv4, lv, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, i);
      this.method_23153(lv3, lv4, lv, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, i);

      for (int u = 0; u < 4; u++) {
         arg2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
         this.method_23153(lv3, lv4, lv, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, i);
         this.method_23153(lv3, lv4, lv, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, i);
         this.method_23153(lv3, lv4, lv, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, i);
         this.method_23153(lv3, lv4, lv, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, i);
      }

      arg2.pop();
      super.render(arg, f, g, arg2, arg3, i);
   }

   public void method_23153(Matrix4f arg, Matrix3f arg2, VertexConsumer arg3, int i, int j, int k, float f, float g, int l, int m, int n, int o) {
      arg3.vertex(arg, (float)i, (float)j, (float)k)
         .color(255, 255, 255, 255)
         .texture(f, g)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(o)
         .normal(arg2, (float)l, (float)n, (float)m)
         .next();
   }
}
