package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EvokerEntityRenderer<T extends SpellcastingIllagerEntity> extends IllagerEntityRenderer<T> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/illager/evoker.png");

   public EvokerEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new IllagerEntityModel<>(0.0F, 0.0F, 64, 64), 0.5F);
      this.addFeature(new HeldItemFeatureRenderer<T, IllagerEntityModel<T>>(this) {
         public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, T arg3, float f, float g, float h, float j, float k, float l) {
            if (arg3.isSpellcasting()) {
               super.render(arg, arg2, i, arg3, f, g, h, j, k, l);
            }
         }
      });
   }

   public Identifier getTexture(T arg) {
      return TEXTURE;
   }
}
