package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BlockOutlineDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;

   public BlockOutlineDebugRenderer(MinecraftClient _snowman) {
      this.client = _snowman;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      BlockView _snowman = this.client.player.world;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.lineWidth(2.0F);
      RenderSystem.disableTexture();
      RenderSystem.depthMask(false);
      BlockPos _snowmanx = new BlockPos(cameraX, cameraY, cameraZ);

      for (BlockPos _snowmanxx : BlockPos.iterate(_snowmanx.add(-6, -6, -6), _snowmanx.add(6, 6, 6))) {
         BlockState _snowmanxxx = _snowman.getBlockState(_snowmanxx);
         if (!_snowmanxxx.isOf(Blocks.AIR)) {
            VoxelShape _snowmanxxxx = _snowmanxxx.getOutlineShape(_snowman, _snowmanxx);

            for (Box _snowmanxxxxx : _snowmanxxxx.getBoundingBoxes()) {
               Box _snowmanxxxxxx = _snowmanxxxxx.offset(_snowmanxx).expand(0.002).offset(-cameraX, -cameraY, -cameraZ);
               double _snowmanxxxxxxx = _snowmanxxxxxx.minX;
               double _snowmanxxxxxxxx = _snowmanxxxxxx.minY;
               double _snowmanxxxxxxxxx = _snowmanxxxxxx.minZ;
               double _snowmanxxxxxxxxxx = _snowmanxxxxxx.maxX;
               double _snowmanxxxxxxxxxxx = _snowmanxxxxxx.maxY;
               double _snowmanxxxxxxxxxxxx = _snowmanxxxxxx.maxZ;
               float _snowmanxxxxxxxxxxxxx = 1.0F;
               float _snowmanxxxxxxxxxxxxxx = 0.0F;
               float _snowmanxxxxxxxxxxxxxxx = 0.0F;
               float _snowmanxxxxxxxxxxxxxxxx = 0.5F;
               if (_snowmanxxx.isSideSolidFullSquare(_snowman, _snowmanxx, Direction.WEST)) {
                  Tessellator _snowmanxxxxxxxxxxxxxxxxx = Tessellator.getInstance();
                  BufferBuilder _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getBuffer();
                  _snowmanxxxxxxxxxxxxxxxxxx.begin(5, VertexFormats.POSITION_COLOR);
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxx.draw();
               }

               if (_snowmanxxx.isSideSolidFullSquare(_snowman, _snowmanxx, Direction.SOUTH)) {
                  Tessellator _snowmanxxxxxxxxxxxxxxxxx = Tessellator.getInstance();
                  BufferBuilder _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getBuffer();
                  _snowmanxxxxxxxxxxxxxxxxxx.begin(5, VertexFormats.POSITION_COLOR);
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxx.draw();
               }

               if (_snowmanxxx.isSideSolidFullSquare(_snowman, _snowmanxx, Direction.EAST)) {
                  Tessellator _snowmanxxxxxxxxxxxxxxxxx = Tessellator.getInstance();
                  BufferBuilder _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getBuffer();
                  _snowmanxxxxxxxxxxxxxxxxxx.begin(5, VertexFormats.POSITION_COLOR);
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxx.draw();
               }

               if (_snowmanxxx.isSideSolidFullSquare(_snowman, _snowmanxx, Direction.NORTH)) {
                  Tessellator _snowmanxxxxxxxxxxxxxxxxx = Tessellator.getInstance();
                  BufferBuilder _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getBuffer();
                  _snowmanxxxxxxxxxxxxxxxxxx.begin(5, VertexFormats.POSITION_COLOR);
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxx.draw();
               }

               if (_snowmanxxx.isSideSolidFullSquare(_snowman, _snowmanxx, Direction.DOWN)) {
                  Tessellator _snowmanxxxxxxxxxxxxxxxxx = Tessellator.getInstance();
                  BufferBuilder _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getBuffer();
                  _snowmanxxxxxxxxxxxxxxxxxx.begin(5, VertexFormats.POSITION_COLOR);
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxx.draw();
               }

               if (_snowmanxxx.isSideSolidFullSquare(_snowman, _snowmanxx, Direction.UP)) {
                  Tessellator _snowmanxxxxxxxxxxxxxxxxx = Tessellator.getInstance();
                  BufferBuilder _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getBuffer();
                  _snowmanxxxxxxxxxxxxxxxxxx.begin(5, VertexFormats.POSITION_COLOR);
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
                  _snowmanxxxxxxxxxxxxxxxxx.draw();
               }
            }
         }
      }

      RenderSystem.depthMask(true);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }
}
