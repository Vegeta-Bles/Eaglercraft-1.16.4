package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;

@Environment(EnvType.CLIENT)
public abstract class EntityRenderer<T extends Entity> {
   protected final EntityRenderDispatcher dispatcher;
   protected float shadowRadius;
   protected float shadowOpacity = 1.0F;

   protected EntityRenderer(EntityRenderDispatcher dispatcher) {
      this.dispatcher = dispatcher;
   }

   public final int getLight(T entity, float tickDelta) {
      BlockPos lv = new BlockPos(entity.method_31166(tickDelta));
      return LightmapTextureManager.pack(this.getBlockLight(entity, lv), this.method_27950(entity, lv));
   }

   protected int method_27950(T arg, BlockPos arg2) {
      return arg.world.getLightLevel(LightType.SKY, arg2);
   }

   protected int getBlockLight(T arg, BlockPos arg2) {
      return arg.isOnFire() ? 15 : arg.world.getLightLevel(LightType.BLOCK, arg2);
   }

   public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
      if (!entity.shouldRender(x, y, z)) {
         return false;
      } else if (entity.ignoreCameraFrustum) {
         return true;
      } else {
         Box lv = entity.getVisibilityBoundingBox().expand(0.5);
         if (lv.isValid() || lv.getAverageSideLength() == 0.0) {
            lv = new Box(entity.getX() - 2.0, entity.getY() - 2.0, entity.getZ() - 2.0, entity.getX() + 2.0, entity.getY() + 2.0, entity.getZ() + 2.0);
         }

         return frustum.isVisible(lv);
      }
   }

   public Vec3d getPositionOffset(T entity, float tickDelta) {
      return Vec3d.ZERO;
   }

   public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
      if (this.hasLabel(entity)) {
         this.renderLabelIfPresent(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
      }
   }

   protected boolean hasLabel(T entity) {
      return entity.shouldRenderName() && entity.hasCustomName();
   }

   public abstract Identifier getTexture(T entity);

   public TextRenderer getFontRenderer() {
      return this.dispatcher.getTextRenderer();
   }

   protected void renderLabelIfPresent(T entity, Text arg2, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
      double d = this.dispatcher.getSquaredDistanceToCamera(entity);
      if (!(d > 4096.0)) {
         boolean bl = !entity.isSneaky();
         float f = entity.getHeight() + 0.5F;
         int j = "deadmau5".equals(arg2.getString()) ? -10 : 0;
         matrices.push();
         matrices.translate(0.0, (double)f, 0.0);
         matrices.multiply(this.dispatcher.getRotation());
         matrices.scale(-0.025F, -0.025F, 0.025F);
         Matrix4f lv = matrices.peek().getModel();
         float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
         int k = (int)(g * 255.0F) << 24;
         TextRenderer lv2 = this.getFontRenderer();
         float h = (float)(-lv2.getWidth(arg2) / 2);
         lv2.draw(arg2, h, (float)j, 553648127, false, lv, vertexConsumers, bl, k, light);
         if (bl) {
            lv2.draw(arg2, h, (float)j, -1, false, lv, vertexConsumers, false, 0, light);
         }

         matrices.pop();
      }
   }

   public EntityRenderDispatcher getRenderManager() {
      return this.dispatcher;
   }
}
