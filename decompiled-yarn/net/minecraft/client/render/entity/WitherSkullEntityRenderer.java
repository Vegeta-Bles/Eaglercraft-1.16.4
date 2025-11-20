package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.SkullEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class WitherSkullEntityRenderer extends EntityRenderer<WitherSkullEntity> {
   private static final Identifier INVULNERABLE_TEXTURE = new Identifier("textures/entity/wither/wither_invulnerable.png");
   private static final Identifier TEXTURE = new Identifier("textures/entity/wither/wither.png");
   private final SkullEntityModel model = new SkullEntityModel();

   public WitherSkullEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   protected int getBlockLight(WitherSkullEntity _snowman, BlockPos _snowman) {
      return 15;
   }

   public void render(WitherSkullEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      _snowman.scale(-1.0F, -1.0F, 1.0F);
      float _snowmanxxxxxx = MathHelper.lerpAngle(_snowman.prevYaw, _snowman.yaw, _snowman);
      float _snowmanxxxxxxx = MathHelper.lerp(_snowman, _snowman.prevPitch, _snowman.pitch);
      VertexConsumer _snowmanxxxxxxxx = _snowman.getBuffer(this.model.getLayer(this.getTexture(_snowman)));
      this.model.method_2821(0.0F, _snowmanxxxxxx, _snowmanxxxxxxx);
      this.model.render(_snowman, _snowmanxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(WitherSkullEntity _snowman) {
      return _snowman.isCharged() ? INVULNERABLE_TEXTURE : TEXTURE;
   }
}
