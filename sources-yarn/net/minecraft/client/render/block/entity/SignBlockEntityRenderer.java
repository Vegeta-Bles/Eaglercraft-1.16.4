package net.minecraft.client.render.block.entity;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.text.OrderedText;
import net.minecraft.util.SignType;

@Environment(EnvType.CLIENT)
public class SignBlockEntityRenderer extends BlockEntityRenderer<SignBlockEntity> {
   private final SignBlockEntityRenderer.SignModel model = new SignBlockEntityRenderer.SignModel();

   public SignBlockEntityRenderer(BlockEntityRenderDispatcher arg) {
      super(arg);
   }

   public void render(SignBlockEntity arg, float f, MatrixStack arg2, VertexConsumerProvider arg3, int i, int j) {
      BlockState lv = arg.getCachedState();
      arg2.push();
      float g = 0.6666667F;
      if (lv.getBlock() instanceof SignBlock) {
         arg2.translate(0.5, 0.5, 0.5);
         float h = -((float)(lv.get(SignBlock.ROTATION) * 360) / 16.0F);
         arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(h));
         this.model.foot.visible = true;
      } else {
         arg2.translate(0.5, 0.5, 0.5);
         float k = -lv.get(WallSignBlock.FACING).asRotation();
         arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(k));
         arg2.translate(0.0, -0.3125, -0.4375);
         this.model.foot.visible = false;
      }

      arg2.push();
      arg2.scale(0.6666667F, -0.6666667F, -0.6666667F);
      SpriteIdentifier lv2 = getModelTexture(lv.getBlock());
      VertexConsumer lv3 = lv2.getVertexConsumer(arg3, this.model::getLayer);
      this.model.field.render(arg2, lv3, i, j);
      this.model.foot.render(arg2, lv3, i, j);
      arg2.pop();
      TextRenderer lv4 = this.dispatcher.getTextRenderer();
      float l = 0.010416667F;
      arg2.translate(0.0, 0.33333334F, 0.046666667F);
      arg2.scale(0.010416667F, -0.010416667F, 0.010416667F);
      int m = arg.getTextColor().getSignColor();
      double d = 0.4;
      int n = (int)((double)NativeImage.getRed(m) * 0.4);
      int o = (int)((double)NativeImage.getGreen(m) * 0.4);
      int p = (int)((double)NativeImage.getBlue(m) * 0.4);
      int q = NativeImage.getAbgrColor(0, p, o, n);
      int r = 20;

      for (int s = 0; s < 4; s++) {
         OrderedText lv5 = arg.getTextBeingEditedOnRow(s, arg2x -> {
            List<OrderedText> list = lv4.wrapLines(arg2x, 90);
            return list.isEmpty() ? OrderedText.EMPTY : list.get(0);
         });
         if (lv5 != null) {
            float t = (float)(-lv4.getWidth(lv5) / 2);
            lv4.draw(lv5, t, (float)(s * 10 - 20), q, false, arg2.peek().getModel(), arg3, false, 0, i);
         }
      }

      arg2.pop();
   }

   public static SpriteIdentifier getModelTexture(Block arg) {
      SignType lv;
      if (arg instanceof AbstractSignBlock) {
         lv = ((AbstractSignBlock)arg).getSignType();
      } else {
         lv = SignType.OAK;
      }

      return TexturedRenderLayers.getSignTextureId(lv);
   }

   @Environment(EnvType.CLIENT)
   public static final class SignModel extends Model {
      public final ModelPart field = new ModelPart(64, 32, 0, 0);
      public final ModelPart foot;

      public SignModel() {
         super(RenderLayer::getEntityCutoutNoCull);
         this.field.addCuboid(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F, 0.0F);
         this.foot = new ModelPart(64, 32, 0, 14);
         this.foot.addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F);
      }

      @Override
      public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
         this.field.render(matrices, vertices, light, overlay, red, green, blue, alpha);
         this.foot.render(matrices, vertices, light, overlay, red, green, blue, alpha);
      }
   }
}
