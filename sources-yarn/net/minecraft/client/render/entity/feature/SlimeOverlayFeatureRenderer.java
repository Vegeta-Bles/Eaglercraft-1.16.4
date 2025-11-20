package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class SlimeOverlayFeatureRenderer<T extends LivingEntity> extends FeatureRenderer<T, SlimeEntityModel<T>> {
   private final EntityModel<T> model = new SlimeEntityModel<>(0);

   public SlimeOverlayFeatureRenderer(FeatureRendererContext<T, SlimeEntityModel<T>> arg) {
      super(arg);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, T arg3, float f, float g, float h, float j, float k, float l) {
      if (!arg3.isInvisible()) {
         this.getContextModel().copyStateTo(this.model);
         this.model.animateModel(arg3, f, g, h);
         this.model.setAngles(arg3, f, g, j, k, l);
         VertexConsumer lv = arg2.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(arg3)));
         this.model.render(arg, lv, i, LivingEntityRenderer.getOverlay(arg3, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}
