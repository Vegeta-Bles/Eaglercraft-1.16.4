package net.minecraft.client.render.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;

public class LecternBlockEntityRenderer extends BlockEntityRenderer<LecternBlockEntity> {
   private final BookModel book = new BookModel();

   public LecternBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(LecternBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      BlockState _snowmanxxxxxx = _snowman.getCachedState();
      if (_snowmanxxxxxx.get(LecternBlock.HAS_BOOK)) {
         _snowman.push();
         _snowman.translate(0.5, 1.0625, 0.5);
         float _snowmanxxxxxxx = _snowmanxxxxxx.get(LecternBlock.FACING).rotateYClockwise().asRotation();
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-_snowmanxxxxxxx));
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(67.5F));
         _snowman.translate(0.0, -0.125, 0.0);
         this.book.setPageAngles(0.0F, 0.1F, 0.9F, 1.2F);
         VertexConsumer _snowmanxxxxxxxx = EnchantingTableBlockEntityRenderer.BOOK_TEXTURE.getVertexConsumer(_snowman, RenderLayer::getEntitySolid);
         this.book.method_24184(_snowman, _snowmanxxxxxxxx, _snowman, _snowman, 1.0F, 1.0F, 1.0F, 1.0F);
         _snowman.pop();
      }
   }
}
