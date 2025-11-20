package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.WolfCollarFeatureRenderer;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

public class WolfEntityRenderer extends MobEntityRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {
   private static final Identifier WILD_TEXTURE = new Identifier("textures/entity/wolf/wolf.png");
   private static final Identifier TAMED_TEXTURE = new Identifier("textures/entity/wolf/wolf_tame.png");
   private static final Identifier ANGRY_TEXTURE = new Identifier("textures/entity/wolf/wolf_angry.png");

   public WolfEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new WolfEntityModel<>(), 0.5F);
      this.addFeature(new WolfCollarFeatureRenderer(this));
   }

   protected float getAnimationProgress(WolfEntity _snowman, float _snowman) {
      return _snowman.getTailAngle();
   }

   public void render(WolfEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      if (_snowman.isFurWet()) {
         float _snowmanxxxxxx = _snowman.getFurWetBrightnessMultiplier(_snowman);
         this.model.setColorMultiplier(_snowmanxxxxxx, _snowmanxxxxxx, _snowmanxxxxxx);
      }

      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman.isFurWet()) {
         this.model.setColorMultiplier(1.0F, 1.0F, 1.0F);
      }
   }

   public Identifier getTexture(WolfEntity _snowman) {
      if (_snowman.isTamed()) {
         return TAMED_TEXTURE;
      } else {
         return _snowman.hasAngerTime() ? ANGRY_TEXTURE : WILD_TEXTURE;
      }
   }
}
