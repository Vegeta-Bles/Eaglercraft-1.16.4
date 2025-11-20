package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class HorseArmorFeatureRenderer extends FeatureRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {
   private final HorseEntityModel<HorseEntity> model = new HorseEntityModel<>(0.1F);

   public HorseArmorFeatureRenderer(FeatureRendererContext<HorseEntity, HorseEntityModel<HorseEntity>> arg) {
      super(arg);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, HorseEntity arg3, float f, float g, float h, float j, float k, float l) {
      ItemStack lv = arg3.getArmorType();
      if (lv.getItem() instanceof HorseArmorItem) {
         HorseArmorItem lv2 = (HorseArmorItem)lv.getItem();
         this.getContextModel().copyStateTo(this.model);
         this.model.animateModel(arg3, f, g, h);
         this.model.setAngles(arg3, f, g, j, k, l);
         float n;
         float o;
         float p;
         if (lv2 instanceof DyeableHorseArmorItem) {
            int m = ((DyeableHorseArmorItem)lv2).getColor(lv);
            n = (float)(m >> 16 & 0xFF) / 255.0F;
            o = (float)(m >> 8 & 0xFF) / 255.0F;
            p = (float)(m & 0xFF) / 255.0F;
         } else {
            n = 1.0F;
            o = 1.0F;
            p = 1.0F;
         }

         VertexConsumer lv3 = arg2.getBuffer(RenderLayer.getEntityCutoutNoCull(lv2.getEntityTexture()));
         this.model.render(arg, lv3, i, OverlayTexture.DEFAULT_UV, n, o, p, 1.0F);
      }
   }
}
