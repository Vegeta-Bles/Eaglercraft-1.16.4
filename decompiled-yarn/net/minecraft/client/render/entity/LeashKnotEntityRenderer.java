package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LeashKnotEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.util.Identifier;

public class LeashKnotEntityRenderer extends EntityRenderer<LeashKnotEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/lead_knot.png");
   private final LeashKnotEntityModel<LeashKnotEntity> model = new LeashKnotEntityModel<>();

   public LeashKnotEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(LeashKnotEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      _snowman.scale(-1.0F, -1.0F, 1.0F);
      this.model.setAngles(_snowman, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      VertexConsumer _snowmanxxxxxx = _snowman.getBuffer(this.model.getLayer(TEXTURE));
      this.model.render(_snowman, _snowmanxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(LeashKnotEntity _snowman) {
      return TEXTURE;
   }
}
