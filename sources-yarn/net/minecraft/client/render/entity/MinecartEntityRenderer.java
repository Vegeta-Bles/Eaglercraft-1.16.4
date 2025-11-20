package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class MinecartEntityRenderer<T extends AbstractMinecartEntity> extends EntityRenderer<T> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/minecart.png");
   protected final EntityModel<T> model = new MinecartEntityModel<>();

   public MinecartEntityRenderer(EntityRenderDispatcher arg) {
      super(arg);
      this.shadowRadius = 0.7F;
   }

   public void render(T arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      super.render(arg, f, g, arg2, arg3, i);
      arg2.push();
      long l = (long)arg.getEntityId() * 493286711L;
      l = l * l * 4392167121L + l * 98761L;
      float h = (((float)(l >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float j = (((float)(l >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float k = (((float)(l >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      arg2.translate((double)h, (double)j, (double)k);
      double d = MathHelper.lerp((double)g, arg.lastRenderX, arg.getX());
      double e = MathHelper.lerp((double)g, arg.lastRenderY, arg.getY());
      double m = MathHelper.lerp((double)g, arg.lastRenderZ, arg.getZ());
      double n = 0.3F;
      Vec3d lv = arg.snapPositionToRail(d, e, m);
      float o = MathHelper.lerp(g, arg.prevPitch, arg.pitch);
      if (lv != null) {
         Vec3d lv2 = arg.snapPositionToRailWithOffset(d, e, m, 0.3F);
         Vec3d lv3 = arg.snapPositionToRailWithOffset(d, e, m, -0.3F);
         if (lv2 == null) {
            lv2 = lv;
         }

         if (lv3 == null) {
            lv3 = lv;
         }

         arg2.translate(lv.x - d, (lv2.y + lv3.y) / 2.0 - e, lv.z - m);
         Vec3d lv4 = lv3.add(-lv2.x, -lv2.y, -lv2.z);
         if (lv4.length() != 0.0) {
            lv4 = lv4.normalize();
            f = (float)(Math.atan2(lv4.z, lv4.x) * 180.0 / Math.PI);
            o = (float)(Math.atan(lv4.y) * 73.0);
         }
      }

      arg2.translate(0.0, 0.375, 0.0);
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - f));
      arg2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-o));
      float p = (float)arg.getDamageWobbleTicks() - g;
      float q = arg.getDamageWobbleStrength() - g;
      if (q < 0.0F) {
         q = 0.0F;
      }

      if (p > 0.0F) {
         arg2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(p) * p * q / 10.0F * (float)arg.getDamageWobbleSide()));
      }

      int r = arg.getBlockOffset();
      BlockState lv5 = arg.getContainedBlock();
      if (lv5.getRenderType() != BlockRenderType.INVISIBLE) {
         arg2.push();
         float s = 0.75F;
         arg2.scale(0.75F, 0.75F, 0.75F);
         arg2.translate(-0.5, (double)((float)(r - 8) / 16.0F), 0.5);
         arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
         this.renderBlock(arg, g, lv5, arg2, arg3, i);
         arg2.pop();
      }

      arg2.scale(-1.0F, -1.0F, 1.0F);
      this.model.setAngles(arg, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer lv6 = arg3.getBuffer(this.model.getLayer(this.getTexture(arg)));
      this.model.render(arg2, lv6, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      arg2.pop();
   }

   public Identifier getTexture(T arg) {
      return TEXTURE;
   }

   protected void renderBlock(T entity, float delta, BlockState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
      MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
   }
}
