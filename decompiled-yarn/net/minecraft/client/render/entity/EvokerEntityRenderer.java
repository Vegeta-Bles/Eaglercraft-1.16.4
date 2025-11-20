package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.util.Identifier;

public class EvokerEntityRenderer<T extends SpellcastingIllagerEntity> extends IllagerEntityRenderer<T> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/illager/evoker.png");

   public EvokerEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new IllagerEntityModel<>(0.0F, 0.0F, 64, 64), 0.5F);
      this.addFeature(new HeldItemFeatureRenderer<T, IllagerEntityModel<T>>(this) {
         public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
            if (_snowman.isSpellcasting()) {
               super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            }
         }
      });
   }

   public Identifier getTexture(T _snowman) {
      return TEXTURE;
   }
}
