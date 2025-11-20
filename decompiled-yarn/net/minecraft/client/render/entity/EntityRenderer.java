package net.minecraft.client.render.entity;

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

public abstract class EntityRenderer<T extends Entity> {
   protected final EntityRenderDispatcher dispatcher;
   protected float shadowRadius;
   protected float shadowOpacity = 1.0F;

   protected EntityRenderer(EntityRenderDispatcher dispatcher) {
      this.dispatcher = dispatcher;
   }

   public final int getLight(T entity, float tickDelta) {
      BlockPos _snowman = new BlockPos(entity.method_31166(tickDelta));
      return LightmapTextureManager.pack(this.getBlockLight(entity, _snowman), this.method_27950(entity, _snowman));
   }

   protected int method_27950(T _snowman, BlockPos _snowman) {
      return _snowman.world.getLightLevel(LightType.SKY, _snowman);
   }

   protected int getBlockLight(T _snowman, BlockPos _snowman) {
      return _snowman.isOnFire() ? 15 : _snowman.world.getLightLevel(LightType.BLOCK, _snowman);
   }

   public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
      if (!entity.shouldRender(x, y, z)) {
         return false;
      } else if (entity.ignoreCameraFrustum) {
         return true;
      } else {
         Box _snowman = entity.getVisibilityBoundingBox().expand(0.5);
         if (_snowman.isValid() || _snowman.getAverageSideLength() == 0.0) {
            _snowman = new Box(entity.getX() - 2.0, entity.getY() - 2.0, entity.getZ() - 2.0, entity.getX() + 2.0, entity.getY() + 2.0, entity.getZ() + 2.0);
         }

         return frustum.isVisible(_snowman);
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

   protected void renderLabelIfPresent(T entity, Text _snowman, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
      double _snowmanx = this.dispatcher.getSquaredDistanceToCamera(entity);
      if (!(_snowmanx > 4096.0)) {
         boolean _snowmanxx = !entity.isSneaky();
         float _snowmanxxx = entity.getHeight() + 0.5F;
         int _snowmanxxxx = "deadmau5".equals(_snowman.getString()) ? -10 : 0;
         matrices.push();
         matrices.translate(0.0, (double)_snowmanxxx, 0.0);
         matrices.multiply(this.dispatcher.getRotation());
         matrices.scale(-0.025F, -0.025F, 0.025F);
         Matrix4f _snowmanxxxxx = matrices.peek().getModel();
         float _snowmanxxxxxx = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
         int _snowmanxxxxxxx = (int)(_snowmanxxxxxx * 255.0F) << 24;
         TextRenderer _snowmanxxxxxxxx = this.getFontRenderer();
         float _snowmanxxxxxxxxx = (float)(-_snowmanxxxxxxxx.getWidth(_snowman) / 2);
         _snowmanxxxxxxxx.draw(_snowman, _snowmanxxxxxxxxx, (float)_snowmanxxxx, 553648127, false, _snowmanxxxxx, vertexConsumers, _snowmanxx, _snowmanxxxxxxx, light);
         if (_snowmanxx) {
            _snowmanxxxxxxxx.draw(_snowman, _snowmanxxxxxxxxx, (float)_snowmanxxxx, -1, false, _snowmanxxxxx, vertexConsumers, false, 0, light);
         }

         matrices.pop();
      }
   }

   public EntityRenderDispatcher getRenderManager() {
      return this.dispatcher;
   }
}
