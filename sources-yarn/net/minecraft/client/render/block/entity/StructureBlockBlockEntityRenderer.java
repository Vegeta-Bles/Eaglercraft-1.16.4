package net.minecraft.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Environment(EnvType.CLIENT)
public class StructureBlockBlockEntityRenderer extends BlockEntityRenderer<StructureBlockBlockEntity> {
   public StructureBlockBlockEntityRenderer(BlockEntityRenderDispatcher arg) {
      super(arg);
   }

   public void render(StructureBlockBlockEntity arg, float f, MatrixStack arg2, VertexConsumerProvider arg3, int i, int j) {
      if (MinecraftClient.getInstance().player.isCreativeLevelTwoOp() || MinecraftClient.getInstance().player.isSpectator()) {
         BlockPos lv = arg.getOffset();
         BlockPos lv2 = arg.getSize();
         if (lv2.getX() >= 1 && lv2.getY() >= 1 && lv2.getZ() >= 1) {
            if (arg.getMode() == StructureBlockMode.SAVE || arg.getMode() == StructureBlockMode.LOAD) {
               double d = (double)lv.getX();
               double e = (double)lv.getZ();
               double g = (double)lv.getY();
               double h = g + (double)lv2.getY();
               double k;
               double l;
               switch (arg.getMirror()) {
                  case LEFT_RIGHT:
                     k = (double)lv2.getX();
                     l = (double)(-lv2.getZ());
                     break;
                  case FRONT_BACK:
                     k = (double)(-lv2.getX());
                     l = (double)lv2.getZ();
                     break;
                  default:
                     k = (double)lv2.getX();
                     l = (double)lv2.getZ();
               }

               double ac;
               double ad;
               double ae;
               double af;
               switch (arg.getRotation()) {
                  case CLOCKWISE_90:
                     ac = l < 0.0 ? d : d + 1.0;
                     ad = k < 0.0 ? e + 1.0 : e;
                     ae = ac - l;
                     af = ad + k;
                     break;
                  case CLOCKWISE_180:
                     ac = k < 0.0 ? d : d + 1.0;
                     ad = l < 0.0 ? e : e + 1.0;
                     ae = ac - k;
                     af = ad - l;
                     break;
                  case COUNTERCLOCKWISE_90:
                     ac = l < 0.0 ? d + 1.0 : d;
                     ad = k < 0.0 ? e : e + 1.0;
                     ae = ac + l;
                     af = ad - k;
                     break;
                  default:
                     ac = k < 0.0 ? d + 1.0 : d;
                     ad = l < 0.0 ? e + 1.0 : e;
                     ae = ac + k;
                     af = ad + l;
               }

               float ag = 1.0F;
               float ah = 0.9F;
               float ai = 0.5F;
               VertexConsumer lv3 = arg3.getBuffer(RenderLayer.getLines());
               if (arg.getMode() == StructureBlockMode.SAVE || arg.shouldShowBoundingBox()) {
                  WorldRenderer.drawBox(arg2, lv3, ac, g, ad, ae, h, af, 0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
               }

               if (arg.getMode() == StructureBlockMode.SAVE && arg.shouldShowAir()) {
                  this.method_3585(arg, lv3, lv, true, arg2);
                  this.method_3585(arg, lv3, lv, false, arg2);
               }
            }
         }
      }
   }

   private void method_3585(StructureBlockBlockEntity arg, VertexConsumer arg2, BlockPos arg3, boolean bl, MatrixStack arg4) {
      BlockView lv = arg.getWorld();
      BlockPos lv2 = arg.getPos();
      BlockPos lv3 = lv2.add(arg3);

      for (BlockPos lv4 : BlockPos.iterate(lv3, lv3.add(arg.getSize()).add(-1, -1, -1))) {
         BlockState lv5 = lv.getBlockState(lv4);
         boolean bl2 = lv5.isAir();
         boolean bl3 = lv5.isOf(Blocks.STRUCTURE_VOID);
         if (bl2 || bl3) {
            float f = bl2 ? 0.05F : 0.0F;
            double d = (double)((float)(lv4.getX() - lv2.getX()) + 0.45F - f);
            double e = (double)((float)(lv4.getY() - lv2.getY()) + 0.45F - f);
            double g = (double)((float)(lv4.getZ() - lv2.getZ()) + 0.45F - f);
            double h = (double)((float)(lv4.getX() - lv2.getX()) + 0.55F + f);
            double i = (double)((float)(lv4.getY() - lv2.getY()) + 0.55F + f);
            double j = (double)((float)(lv4.getZ() - lv2.getZ()) + 0.55F + f);
            if (bl) {
               WorldRenderer.drawBox(arg4, arg2, d, e, g, h, i, j, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F);
            } else if (bl2) {
               WorldRenderer.drawBox(arg4, arg2, d, e, g, h, i, j, 0.5F, 0.5F, 1.0F, 1.0F, 0.5F, 0.5F, 1.0F);
            } else {
               WorldRenderer.drawBox(arg4, arg2, d, e, g, h, i, j, 1.0F, 0.25F, 0.25F, 1.0F, 1.0F, 0.25F, 0.25F);
            }
         }
      }
   }

   public boolean rendersOutsideBoundingBox(StructureBlockBlockEntity arg) {
      return true;
   }
}
