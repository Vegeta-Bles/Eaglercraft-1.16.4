package net.minecraft.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;

@Environment(EnvType.CLIENT)
public class LecternBlockEntityRenderer extends BlockEntityRenderer<LecternBlockEntity> {
   private final BookModel book = new BookModel();

   public LecternBlockEntityRenderer(BlockEntityRenderDispatcher arg) {
      super(arg);
   }

   public void render(LecternBlockEntity arg, float f, MatrixStack arg2, VertexConsumerProvider arg3, int i, int j) {
      BlockState lv = arg.getCachedState();
      if (lv.get(LecternBlock.HAS_BOOK)) {
         arg2.push();
         arg2.translate(0.5, 1.0625, 0.5);
         float g = lv.get(LecternBlock.FACING).rotateYClockwise().asRotation();
         arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-g));
         arg2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(67.5F));
         arg2.translate(0.0, -0.125, 0.0);
         this.book.setPageAngles(0.0F, 0.1F, 0.9F, 1.2F);
         VertexConsumer lv2 = EnchantingTableBlockEntityRenderer.BOOK_TEXTURE.getVertexConsumer(arg3, RenderLayer::getEntitySolid);
         this.book.method_24184(arg2, lv2, i, j, 1.0F, 1.0F, 1.0F, 1.0F);
         arg2.pop();
      }
   }
}
