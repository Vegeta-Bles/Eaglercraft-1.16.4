package net.minecraft.client.render.block;

import java.util.Random;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

public class BlockRenderManager implements SynchronousResourceReloadListener {
   private final BlockModels models;
   private final BlockModelRenderer blockModelRenderer;
   private final FluidRenderer fluidRenderer;
   private final Random random = new Random();
   private final BlockColors blockColors;

   public BlockRenderManager(BlockModels models, BlockColors blockColors) {
      this.models = models;
      this.blockColors = blockColors;
      this.blockModelRenderer = new BlockModelRenderer(this.blockColors);
      this.fluidRenderer = new FluidRenderer();
   }

   public BlockModels getModels() {
      return this.models;
   }

   public void renderDamage(BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrix, VertexConsumer vertexConsumer) {
      if (state.getRenderType() == BlockRenderType.MODEL) {
         BakedModel _snowman = this.models.getModel(state);
         long _snowmanx = state.getRenderingSeed(pos);
         this.blockModelRenderer.render(world, _snowman, state, pos, matrix, vertexConsumer, true, this.random, _snowmanx, OverlayTexture.DEFAULT_UV);
      }
   }

   public boolean renderBlock(
      BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrix, VertexConsumer vertexConsumer, boolean cull, Random random
   ) {
      try {
         BlockRenderType _snowman = state.getRenderType();
         return _snowman != BlockRenderType.MODEL
            ? false
            : this.blockModelRenderer
               .render(world, this.getModel(state), state, pos, matrix, vertexConsumer, cull, random, state.getRenderingSeed(pos), OverlayTexture.DEFAULT_UV);
      } catch (Throwable var11) {
         CrashReport _snowmanx = CrashReport.create(var11, "Tesselating block in world");
         CrashReportSection _snowmanxx = _snowmanx.addElement("Block being tesselated");
         CrashReportSection.addBlockInfo(_snowmanxx, pos, state);
         throw new CrashException(_snowmanx);
      }
   }

   public boolean renderFluid(BlockPos pos, BlockRenderView _snowman, VertexConsumer _snowman, FluidState _snowman) {
      try {
         return this.fluidRenderer.render(_snowman, pos, _snowman, _snowman);
      } catch (Throwable var8) {
         CrashReport _snowmanxxx = CrashReport.create(var8, "Tesselating liquid in world");
         CrashReportSection _snowmanxxxx = _snowmanxxx.addElement("Block being tesselated");
         CrashReportSection.addBlockInfo(_snowmanxxxx, pos, null);
         throw new CrashException(_snowmanxxx);
      }
   }

   public BlockModelRenderer getModelRenderer() {
      return this.blockModelRenderer;
   }

   public BakedModel getModel(BlockState state) {
      return this.models.getModel(state);
   }

   public void renderBlockAsEntity(BlockState state, MatrixStack matrices, VertexConsumerProvider vertexConsumer, int light, int overlay) {
      BlockRenderType _snowman = state.getRenderType();
      if (_snowman != BlockRenderType.INVISIBLE) {
         switch (_snowman) {
            case MODEL:
               BakedModel _snowmanx = this.getModel(state);
               int _snowmanxx = this.blockColors.getColor(state, null, null, 0);
               float _snowmanxxx = (float)(_snowmanxx >> 16 & 0xFF) / 255.0F;
               float _snowmanxxxx = (float)(_snowmanxx >> 8 & 0xFF) / 255.0F;
               float _snowmanxxxxx = (float)(_snowmanxx & 0xFF) / 255.0F;
               this.blockModelRenderer
                  .render(
                     matrices.peek(), vertexConsumer.getBuffer(RenderLayers.getEntityBlockLayer(state, false)), state, _snowmanx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, light, overlay
                  );
               break;
            case ENTITYBLOCK_ANIMATED:
               BuiltinModelItemRenderer.INSTANCE
                  .render(new ItemStack(state.getBlock()), ModelTransformation.Mode.NONE, matrices, vertexConsumer, light, overlay);
         }
      }
   }

   @Override
   public void apply(ResourceManager manager) {
      this.fluidRenderer.onResourceReload();
   }
}
