package net.minecraft.client.render.entity.feature;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;

public class SnowmanPumpkinFeatureRenderer extends FeatureRenderer<SnowGolemEntity, SnowGolemEntityModel<SnowGolemEntity>> {
   public SnowmanPumpkinFeatureRenderer(FeatureRendererContext<SnowGolemEntity, SnowGolemEntityModel<SnowGolemEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, SnowGolemEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (!_snowman.isInvisible() && _snowman.hasPumpkin()) {
         _snowman.push();
         this.getContextModel().getTopSnowball().rotate(_snowman);
         float _snowmanxxxxxxxxxx = 0.625F;
         _snowman.translate(0.0, -0.34375, 0.0);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
         _snowman.scale(0.625F, -0.625F, -0.625F);
         ItemStack _snowmanxxxxxxxxxxx = new ItemStack(Blocks.CARVED_PUMPKIN);
         MinecraftClient.getInstance()
            .getItemRenderer()
            .renderItem(_snowman, _snowmanxxxxxxxxxxx, ModelTransformation.Mode.HEAD, false, _snowman, _snowman, _snowman.world, _snowman, LivingEntityRenderer.getOverlay(_snowman, 0.0F));
         _snowman.pop();
      }
   }
}
