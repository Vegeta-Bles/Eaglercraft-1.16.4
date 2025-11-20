package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EvokerFangsEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.util.Identifier;

public class EvokerFangsEntityRenderer extends EntityRenderer<EvokerFangsEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/illager/evoker_fangs.png");
   private final EvokerFangsEntityModel<EvokerFangsEntity> model = new EvokerFangsEntityModel<>();

   public EvokerFangsEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(EvokerFangsEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      float _snowmanxxxxxx = _snowman.getAnimationProgress(_snowman);
      if (_snowmanxxxxxx != 0.0F) {
         float _snowmanxxxxxxx = 2.0F;
         if (_snowmanxxxxxx > 0.9F) {
            _snowmanxxxxxxx = (float)((double)_snowmanxxxxxxx * ((1.0 - (double)_snowmanxxxxxx) / 0.1F));
         }

         _snowman.push();
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F - _snowman.yaw));
         _snowman.scale(-_snowmanxxxxxxx, -_snowmanxxxxxxx, _snowmanxxxxxxx);
         float _snowmanxxxxxxxx = 0.03125F;
         _snowman.translate(0.0, -0.626F, 0.0);
         _snowman.scale(0.5F, 0.5F, 0.5F);
         this.model.setAngles(_snowman, _snowmanxxxxxx, 0.0F, 0.0F, _snowman.yaw, _snowman.pitch);
         VertexConsumer _snowmanxxxxxxxxx = _snowman.getBuffer(this.model.getLayer(TEXTURE));
         this.model.render(_snowman, _snowmanxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
         _snowman.pop();
         super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public Identifier getTexture(EvokerFangsEntity _snowman) {
      return TEXTURE;
   }
}
