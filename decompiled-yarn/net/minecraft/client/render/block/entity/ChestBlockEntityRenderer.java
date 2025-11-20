package net.minecraft.client.render.block.entity;

import java.util.Calendar;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ChestBlockEntityRenderer<T extends BlockEntity & ChestAnimationProgress> extends BlockEntityRenderer<T> {
   private final ModelPart singleChestLid;
   private final ModelPart singleChestBase;
   private final ModelPart singleChestLatch;
   private final ModelPart doubleChestRightLid;
   private final ModelPart doubleChestRightBase;
   private final ModelPart doubleChestRightLatch;
   private final ModelPart doubleChestLeftLid;
   private final ModelPart doubleChestLeftBase;
   private final ModelPart doubleChestLeftLatch;
   private boolean christmas;

   public ChestBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
      Calendar _snowmanx = Calendar.getInstance();
      if (_snowmanx.get(2) + 1 == 12 && _snowmanx.get(5) >= 24 && _snowmanx.get(5) <= 26) {
         this.christmas = true;
      }

      this.singleChestBase = new ModelPart(64, 64, 0, 19);
      this.singleChestBase.addCuboid(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
      this.singleChestLid = new ModelPart(64, 64, 0, 0);
      this.singleChestLid.addCuboid(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
      this.singleChestLid.pivotY = 9.0F;
      this.singleChestLid.pivotZ = 1.0F;
      this.singleChestLatch = new ModelPart(64, 64, 0, 0);
      this.singleChestLatch.addCuboid(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
      this.singleChestLatch.pivotY = 8.0F;
      this.doubleChestRightBase = new ModelPart(64, 64, 0, 19);
      this.doubleChestRightBase.addCuboid(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
      this.doubleChestRightLid = new ModelPart(64, 64, 0, 0);
      this.doubleChestRightLid.addCuboid(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
      this.doubleChestRightLid.pivotY = 9.0F;
      this.doubleChestRightLid.pivotZ = 1.0F;
      this.doubleChestRightLatch = new ModelPart(64, 64, 0, 0);
      this.doubleChestRightLatch.addCuboid(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
      this.doubleChestRightLatch.pivotY = 8.0F;
      this.doubleChestLeftBase = new ModelPart(64, 64, 0, 19);
      this.doubleChestLeftBase.addCuboid(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
      this.doubleChestLeftLid = new ModelPart(64, 64, 0, 0);
      this.doubleChestLeftLid.addCuboid(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
      this.doubleChestLeftLid.pivotY = 9.0F;
      this.doubleChestLeftLid.pivotZ = 1.0F;
      this.doubleChestLeftLatch = new ModelPart(64, 64, 0, 0);
      this.doubleChestLeftLatch.addCuboid(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
      this.doubleChestLeftLatch.pivotY = 8.0F;
   }

   @Override
   public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
      World _snowman = entity.getWorld();
      boolean _snowmanx = _snowman != null;
      BlockState _snowmanxx = _snowmanx ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
      ChestType _snowmanxxx = _snowmanxx.contains(ChestBlock.CHEST_TYPE) ? _snowmanxx.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
      Block _snowmanxxxx = _snowmanxx.getBlock();
      if (_snowmanxxxx instanceof AbstractChestBlock) {
         AbstractChestBlock<?> _snowmanxxxxx = (AbstractChestBlock<?>)_snowmanxxxx;
         boolean _snowmanxxxxxx = _snowmanxxx != ChestType.SINGLE;
         matrices.push();
         float _snowmanxxxxxxx = _snowmanxx.get(ChestBlock.FACING).asRotation();
         matrices.translate(0.5, 0.5, 0.5);
         matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-_snowmanxxxxxxx));
         matrices.translate(-0.5, -0.5, -0.5);
         DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> _snowmanxxxxxxxx;
         if (_snowmanx) {
            _snowmanxxxxxxxx = _snowmanxxxxx.getBlockEntitySource(_snowmanxx, _snowman, entity.getPos(), true);
         } else {
            _snowmanxxxxxxxx = DoubleBlockProperties.PropertyRetriever::getFallback;
         }

         float _snowmanxxxxxxxxx = _snowmanxxxxxxxx.apply(ChestBlock.getAnimationProgressRetriever(entity)).get(tickDelta);
         _snowmanxxxxxxxxx = 1.0F - _snowmanxxxxxxxxx;
         _snowmanxxxxxxxxx = 1.0F - _snowmanxxxxxxxxx * _snowmanxxxxxxxxx * _snowmanxxxxxxxxx;
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.apply(new LightmapCoordinatesRetriever<>()).applyAsInt(light);
         SpriteIdentifier _snowmanxxxxxxxxxxx = TexturedRenderLayers.getChestTexture(entity, _snowmanxxx, this.christmas);
         VertexConsumer _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
         if (_snowmanxxxxxx) {
            if (_snowmanxxx == ChestType.LEFT) {
               this.render(
                  matrices, _snowmanxxxxxxxxxxxx, this.doubleChestLeftLid, this.doubleChestLeftLatch, this.doubleChestLeftBase, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, overlay
               );
            } else {
               this.render(
                  matrices, _snowmanxxxxxxxxxxxx, this.doubleChestRightLid, this.doubleChestRightLatch, this.doubleChestRightBase, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, overlay
               );
            }
         } else {
            this.render(matrices, _snowmanxxxxxxxxxxxx, this.singleChestLid, this.singleChestLatch, this.singleChestBase, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, overlay);
         }

         matrices.pop();
      }
   }

   private void render(MatrixStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart latch, ModelPart base, float openFactor, int light, int overlay) {
      lid.pitch = -(openFactor * (float) (Math.PI / 2));
      latch.pitch = lid.pitch;
      lid.render(matrices, vertices, light, overlay);
      latch.render(matrices, vertices, light, overlay);
      base.render(matrices, vertices, light, overlay);
   }
}
