package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;

@Environment(EnvType.CLIENT)
public abstract class MobEntityRenderer<T extends MobEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {
   public MobEntityRenderer(EntityRenderDispatcher arg, M arg2, float f) {
      super(arg, arg2, f);
   }

   protected boolean hasLabel(T arg) {
      return super.hasLabel(arg) && (arg.shouldRenderName() || arg.hasCustomName() && arg == this.dispatcher.targetedEntity);
   }

   public boolean shouldRender(T arg, Frustum arg2, double d, double e, double f) {
      if (super.shouldRender(arg, arg2, d, e, f)) {
         return true;
      } else {
         Entity lv = arg.getHoldingEntity();
         return lv != null ? arg2.isVisible(lv.getVisibilityBoundingBox()) : false;
      }
   }

   public void render(T arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      super.render(arg, f, g, arg2, arg3, i);
      Entity lv = arg.getHoldingEntity();
      if (lv != null) {
         this.method_4073(arg, g, arg2, arg3, lv);
      }
   }

   private <E extends Entity> void method_4073(T arg, float f, MatrixStack arg2, VertexConsumerProvider arg3, E arg4) {
      arg2.push();
      Vec3d lv = arg4.method_30951(f);
      double d = (double)(MathHelper.lerp(f, arg.bodyYaw, arg.prevBodyYaw) * (float) (Math.PI / 180.0)) + (Math.PI / 2);
      Vec3d lv2 = arg.method_29919();
      double e = Math.cos(d) * lv2.z + Math.sin(d) * lv2.x;
      double g = Math.sin(d) * lv2.z - Math.cos(d) * lv2.x;
      double h = MathHelper.lerp((double)f, arg.prevX, arg.getX()) + e;
      double i = MathHelper.lerp((double)f, arg.prevY, arg.getY()) + lv2.y;
      double j = MathHelper.lerp((double)f, arg.prevZ, arg.getZ()) + g;
      arg2.translate(e, lv2.y, g);
      float k = (float)(lv.x - h);
      float l = (float)(lv.y - i);
      float m = (float)(lv.z - j);
      float n = 0.025F;
      VertexConsumer lv3 = arg3.getBuffer(RenderLayer.getLeash());
      Matrix4f lv4 = arg2.peek().getModel();
      float o = MathHelper.fastInverseSqrt(k * k + m * m) * 0.025F / 2.0F;
      float p = m * o;
      float q = k * o;
      BlockPos lv5 = new BlockPos(arg.getCameraPosVec(f));
      BlockPos lv6 = new BlockPos(arg4.getCameraPosVec(f));
      int r = this.getBlockLight(arg, lv5);
      int s = this.dispatcher.getRenderer(arg4).getBlockLight(arg4, lv6);
      int t = arg.world.getLightLevel(LightType.SKY, lv5);
      int u = arg.world.getLightLevel(LightType.SKY, lv6);
      method_23186(lv3, lv4, k, l, m, r, s, t, u, 0.025F, 0.025F, p, q);
      method_23186(lv3, lv4, k, l, m, r, s, t, u, 0.025F, 0.0F, p, q);
      arg2.pop();
   }

   public static void method_23186(VertexConsumer arg, Matrix4f arg2, float f, float g, float h, int i, int j, int k, int l, float m, float n, float o, float p) {
      int q = 24;

      for (int r = 0; r < 24; r++) {
         float s = (float)r / 23.0F;
         int t = (int)MathHelper.lerp(s, (float)i, (float)j);
         int u = (int)MathHelper.lerp(s, (float)k, (float)l);
         int v = LightmapTextureManager.pack(t, u);
         method_23187(arg, arg2, v, f, g, h, m, n, 24, r, false, o, p);
         method_23187(arg, arg2, v, f, g, h, m, n, 24, r + 1, true, o, p);
      }
   }

   public static void method_23187(
      VertexConsumer arg, Matrix4f arg2, int i, float f, float g, float h, float j, float k, int l, int m, boolean bl, float n, float o
   ) {
      float p = 0.5F;
      float q = 0.4F;
      float r = 0.3F;
      if (m % 2 == 0) {
         p *= 0.7F;
         q *= 0.7F;
         r *= 0.7F;
      }

      float s = (float)m / (float)l;
      float t = f * s;
      float u = g > 0.0F ? g * s * s : g - g * (1.0F - s) * (1.0F - s);
      float v = h * s;
      if (!bl) {
         arg.vertex(arg2, t + n, u + j - k, v - o).color(p, q, r, 1.0F).light(i).next();
      }

      arg.vertex(arg2, t - n, u + k, v + o).color(p, q, r, 1.0F).light(i).next();
      if (bl) {
         arg.vertex(arg2, t + n, u + j - k, v - o).color(p, q, r, 1.0F).light(i).next();
      }
   }
}
