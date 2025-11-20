package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.WitchEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class WitchHeldItemFeatureRenderer<T extends LivingEntity> extends VillagerHeldItemFeatureRenderer<T, WitchEntityModel<T>> {
   public WitchHeldItemFeatureRenderer(FeatureRendererContext<T, WitchEntityModel<T>> _snowman) {
      super(_snowman);
   }

   @Override
   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      ItemStack _snowmanxxxxxxxxxx = _snowman.getMainHandStack();
      _snowman.push();
      if (_snowmanxxxxxxxxxx.getItem() == Items.POTION) {
         this.getContextModel().getHead().rotate(_snowman);
         this.getContextModel().getNose().rotate(_snowman);
         _snowman.translate(0.0625, 0.25, 0.0);
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(140.0F));
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(10.0F));
         _snowman.translate(0.0, -0.4F, 0.4F);
      }

      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.pop();
   }
}
