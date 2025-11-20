package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.ShulkerBulletEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class ShulkerBulletEntityRenderer extends EntityRenderer<ShulkerBulletEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/shulker/spark.png");
   private static final RenderLayer LAYER = RenderLayer.getEntityTranslucent(TEXTURE);
   private final ShulkerBulletEntityModel<ShulkerBulletEntity> model = new ShulkerBulletEntityModel<>();

   public ShulkerBulletEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   protected int getBlockLight(ShulkerBulletEntity _snowman, BlockPos _snowman) {
      return 15;
   }

   public void render(ShulkerBulletEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      float _snowmanxxxxxx = MathHelper.lerpAngle(_snowman.prevYaw, _snowman.yaw, _snowman);
      float _snowmanxxxxxxx = MathHelper.lerp(_snowman, _snowman.prevPitch, _snowman.pitch);
      float _snowmanxxxxxxxx = (float)_snowman.age + _snowman;
      _snowman.translate(0.0, 0.15F, 0.0);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.sin(_snowmanxxxxxxxx * 0.1F) * 180.0F));
      _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.cos(_snowmanxxxxxxxx * 0.1F) * 180.0F));
      _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.sin(_snowmanxxxxxxxx * 0.15F) * 360.0F));
      _snowman.scale(-0.5F, -0.5F, 0.5F);
      this.model.setAngles(_snowman, 0.0F, 0.0F, 0.0F, _snowmanxxxxxx, _snowmanxxxxxxx);
      VertexConsumer _snowmanxxxxxxxxx = _snowman.getBuffer(this.model.getLayer(TEXTURE));
      this.model.render(_snowman, _snowmanxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.scale(1.5F, 1.5F, 1.5F);
      VertexConsumer _snowmanxxxxxxxxxx = _snowman.getBuffer(LAYER);
      this.model.render(_snowman, _snowmanxxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 0.15F);
      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(ShulkerBulletEntity _snowman) {
      return TEXTURE;
   }
}
