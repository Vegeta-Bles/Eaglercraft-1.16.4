package net.minecraft.client.render.block.entity;

import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

public class CampfireBlockEntityRenderer extends BlockEntityRenderer<CampfireBlockEntity> {
   public CampfireBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(CampfireBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      Direction _snowmanxxxxxx = _snowman.getCachedState().get(CampfireBlock.FACING);
      DefaultedList<ItemStack> _snowmanxxxxxxx = _snowman.getItemsBeingCooked();

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxxx.size(); _snowmanxxxxxxxx++) {
         ItemStack _snowmanxxxxxxxxx = _snowmanxxxxxxx.get(_snowmanxxxxxxxx);
         if (_snowmanxxxxxxxxx != ItemStack.EMPTY) {
            _snowman.push();
            _snowman.translate(0.5, 0.44921875, 0.5);
            Direction _snowmanxxxxxxxxxx = Direction.fromHorizontal((_snowmanxxxxxxxx + _snowmanxxxxxx.getHorizontal()) % 4);
            float _snowmanxxxxxxxxxxx = -_snowmanxxxxxxxxxx.asRotation();
            _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxxxxxx));
            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
            _snowman.translate(-0.3125, -0.3125, 0.0);
            _snowman.scale(0.375F, 0.375F, 0.375F);
            MinecraftClient.getInstance().getItemRenderer().renderItem(_snowmanxxxxxxxxx, ModelTransformation.Mode.FIXED, _snowman, _snowman, _snowman, _snowman);
            _snowman.pop();
         }
      }
   }
}
