package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class BoatEntityRenderer extends EntityRenderer<BoatEntity> {
   private static final Identifier[] TEXTURES = new Identifier[]{
      new Identifier("textures/entity/boat/oak.png"),
      new Identifier("textures/entity/boat/spruce.png"),
      new Identifier("textures/entity/boat/birch.png"),
      new Identifier("textures/entity/boat/jungle.png"),
      new Identifier("textures/entity/boat/acacia.png"),
      new Identifier("textures/entity/boat/dark_oak.png")
   };
   protected final BoatEntityModel model = new BoatEntityModel();

   public BoatEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
      this.shadowRadius = 0.8F;
   }

   public void render(BoatEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      _snowman.translate(0.0, 0.375, 0.0);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - _snowman));
      float _snowmanxxxxxx = (float)_snowman.getDamageWobbleTicks() - _snowman;
      float _snowmanxxxxxxx = _snowman.getDamageWobbleStrength() - _snowman;
      if (_snowmanxxxxxxx < 0.0F) {
         _snowmanxxxxxxx = 0.0F;
      }

      if (_snowmanxxxxxx > 0.0F) {
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(_snowmanxxxxxx) * _snowmanxxxxxx * _snowmanxxxxxxx / 10.0F * (float)_snowman.getDamageWobbleSide()));
      }

      float _snowmanxxxxxxxx = _snowman.interpolateBubbleWobble(_snowman);
      if (!MathHelper.approximatelyEquals(_snowmanxxxxxxxx, 0.0F)) {
         _snowman.multiply(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), _snowman.interpolateBubbleWobble(_snowman), true));
      }

      _snowman.scale(-1.0F, -1.0F, 1.0F);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
      this.model.setAngles(_snowman, _snowman, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer _snowmanxxxxxxxxx = _snowman.getBuffer(this.model.getLayer(this.getTexture(_snowman)));
      this.model.render(_snowman, _snowmanxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      if (!_snowman.isSubmergedInWater()) {
         VertexConsumer _snowmanxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getWaterMask());
         this.model.getBottom().render(_snowman, _snowmanxxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV);
      }

      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(BoatEntity _snowman) {
      return TEXTURES[_snowman.getBoatType().ordinal()];
   }
}
