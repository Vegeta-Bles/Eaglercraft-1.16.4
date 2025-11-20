package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.SlimeOverlayFeatureRenderer;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SlimeEntityRenderer extends MobEntityRenderer<SlimeEntity, SlimeEntityModel<SlimeEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/slime/slime.png");

   public SlimeEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new SlimeEntityModel<>(16), 0.25F);
      this.addFeature(new SlimeOverlayFeatureRenderer<>(this));
   }

   public void render(SlimeEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      this.shadowRadius = 0.25F * (float)_snowman.getSize();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   protected void scale(SlimeEntity _snowman, MatrixStack _snowman, float _snowman) {
      float _snowmanxxx = 0.999F;
      _snowman.scale(0.999F, 0.999F, 0.999F);
      _snowman.translate(0.0, 0.001F, 0.0);
      float _snowmanxxxx = (float)_snowman.getSize();
      float _snowmanxxxxx = MathHelper.lerp(_snowman, _snowman.lastStretch, _snowman.stretch) / (_snowmanxxxx * 0.5F + 1.0F);
      float _snowmanxxxxxx = 1.0F / (_snowmanxxxxx + 1.0F);
      _snowman.scale(_snowmanxxxxxx * _snowmanxxxx, 1.0F / _snowmanxxxxxx * _snowmanxxxx, _snowmanxxxxxx * _snowmanxxxx);
   }

   public Identifier getTexture(SlimeEntity _snowman) {
      return TEXTURE;
   }
}
