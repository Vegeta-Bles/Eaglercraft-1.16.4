package net.minecraft.client.render.entity;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EndermanBlockFeatureRenderer;
import net.minecraft.client.render.entity.feature.EndermanEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class EndermanEntityRenderer extends MobEntityRenderer<EndermanEntity, EndermanEntityModel<EndermanEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/enderman/enderman.png");
   private final Random random = new Random();

   public EndermanEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new EndermanEntityModel<>(0.0F), 0.5F);
      this.addFeature(new EndermanEyesFeatureRenderer<>(this));
      this.addFeature(new EndermanBlockFeatureRenderer(this));
   }

   public void render(EndermanEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      BlockState _snowmanxxxxxx = _snowman.getCarriedBlock();
      EndermanEntityModel<EndermanEntity> _snowmanxxxxxxx = this.getModel();
      _snowmanxxxxxxx.carryingBlock = _snowmanxxxxxx != null;
      _snowmanxxxxxxx.angry = _snowman.isAngry();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Vec3d getPositionOffset(EndermanEntity _snowman, float _snowman) {
      if (_snowman.isAngry()) {
         double _snowmanxx = 0.02;
         return new Vec3d(this.random.nextGaussian() * 0.02, 0.0, this.random.nextGaussian() * 0.02);
      } else {
         return super.getPositionOffset(_snowman, _snowman);
      }
   }

   public Identifier getTexture(EndermanEntity _snowman) {
      return TEXTURE;
   }
}
