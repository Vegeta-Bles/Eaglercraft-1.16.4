package net.minecraft.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BellBlockEntityRenderer extends BlockEntityRenderer<BellBlockEntity> {
   public static final SpriteIdentifier BELL_BODY_TEXTURE = new SpriteIdentifier(
      SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/bell/bell_body")
   );
   private final ModelPart field_20816 = new ModelPart(32, 32, 0, 0);

   public BellBlockEntityRenderer(BlockEntityRenderDispatcher arg) {
      super(arg);
      this.field_20816.addCuboid(-3.0F, -6.0F, -3.0F, 6.0F, 7.0F, 6.0F);
      this.field_20816.setPivot(8.0F, 12.0F, 8.0F);
      ModelPart lv = new ModelPart(32, 32, 0, 13);
      lv.addCuboid(4.0F, 4.0F, 4.0F, 8.0F, 2.0F, 8.0F);
      lv.setPivot(-8.0F, -12.0F, -8.0F);
      this.field_20816.addChild(lv);
   }

   public void render(BellBlockEntity arg, float f, MatrixStack arg2, VertexConsumerProvider arg3, int i, int j) {
      float g = (float)arg.ringTicks + f;
      float h = 0.0F;
      float k = 0.0F;
      if (arg.ringing) {
         float l = MathHelper.sin(g / (float) Math.PI) / (4.0F + g / 3.0F);
         if (arg.lastSideHit == Direction.NORTH) {
            h = -l;
         } else if (arg.lastSideHit == Direction.SOUTH) {
            h = l;
         } else if (arg.lastSideHit == Direction.EAST) {
            k = -l;
         } else if (arg.lastSideHit == Direction.WEST) {
            k = l;
         }
      }

      this.field_20816.pitch = h;
      this.field_20816.roll = k;
      VertexConsumer lv = BELL_BODY_TEXTURE.getVertexConsumer(arg3, RenderLayer::getEntitySolid);
      this.field_20816.render(arg2, lv, i, j);
   }
}
