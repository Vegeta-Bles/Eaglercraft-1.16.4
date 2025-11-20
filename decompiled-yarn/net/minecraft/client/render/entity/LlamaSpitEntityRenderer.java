package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LlamaSpitEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class LlamaSpitEntityRenderer extends EntityRenderer<LlamaSpitEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/llama/spit.png");
   private final LlamaSpitEntityModel<LlamaSpitEntity> model = new LlamaSpitEntityModel<>();

   public LlamaSpitEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(LlamaSpitEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      _snowman.translate(0.0, 0.15F, 0.0);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(_snowman, _snowman.prevYaw, _snowman.yaw) - 90.0F));
      _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(_snowman, _snowman.prevPitch, _snowman.pitch)));
      this.model.setAngles(_snowman, _snowman, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer _snowmanxxxxxx = _snowman.getBuffer(this.model.getLayer(TEXTURE));
      this.model.render(_snowman, _snowmanxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(LlamaSpitEntity _snowman) {
      return TEXTURE;
   }
}
