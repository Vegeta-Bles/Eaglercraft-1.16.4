package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.WitchEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Environment(EnvType.CLIENT)
public class WitchHeldItemFeatureRenderer<T extends LivingEntity> extends VillagerHeldItemFeatureRenderer<T, WitchEntityModel<T>> {
   public WitchHeldItemFeatureRenderer(FeatureRendererContext<T, WitchEntityModel<T>> arg) {
      super(arg);
   }

   @Override
   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, T arg3, float f, float g, float h, float j, float k, float l) {
      ItemStack lv = arg3.getMainHandStack();
      arg.push();
      if (lv.getItem() == Items.POTION) {
         this.getContextModel().getHead().rotate(arg);
         this.getContextModel().getNose().rotate(arg);
         arg.translate(0.0625, 0.25, 0.0);
         arg.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
         arg.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(140.0F));
         arg.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(10.0F));
         arg.translate(0.0, -0.4F, 0.4F);
      }

      super.render(arg, arg2, i, arg3, f, g, h, j, k, l);
      arg.pop();
   }
}
