package net.minecraft.client.render.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

public class PathfindingDebugRenderer implements DebugRenderer.Renderer {
   private final Map<Integer, Path> paths = Maps.newHashMap();
   private final Map<Integer, Float> field_4617 = Maps.newHashMap();
   private final Map<Integer, Long> pathTimes = Maps.newHashMap();

   public PathfindingDebugRenderer() {
   }

   public void addPath(int id, Path path, float _snowman) {
      this.paths.put(id, path);
      this.pathTimes.put(id, Util.getMeasuringTimeMs());
      this.field_4617.put(id, _snowman);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      if (!this.paths.isEmpty()) {
         long _snowman = Util.getMeasuringTimeMs();

         for (Integer _snowmanx : this.paths.keySet()) {
            Path _snowmanxx = this.paths.get(_snowmanx);
            float _snowmanxxx = this.field_4617.get(_snowmanx);
            drawPath(_snowmanxx, _snowmanxxx, true, true, cameraX, cameraY, cameraZ);
         }

         for (Integer _snowmanx : this.pathTimes.keySet().toArray(new Integer[0])) {
            if (_snowman - this.pathTimes.get(_snowmanx) > 5000L) {
               this.paths.remove(_snowmanx);
               this.pathTimes.remove(_snowmanx);
            }
         }
      }
   }

   public static void drawPath(Path path, float nodeSize, boolean _snowman, boolean drawLabels, double cameraX, double cameraY, double cameraZ) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.disableTexture();
      RenderSystem.lineWidth(6.0F);
      drawPathInternal(path, nodeSize, _snowman, drawLabels, cameraX, cameraY, cameraZ);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
   }

   private static void drawPathInternal(Path path, float nodeSize, boolean _snowman, boolean drawLabels, double cameraX, double cameraY, double cameraZ) {
      drawPathLines(path, cameraX, cameraY, cameraZ);
      BlockPos _snowmanx = path.getTarget();
      if (getManhattanDistance(_snowmanx, cameraX, cameraY, cameraZ) <= 80.0F) {
         DebugRenderer.drawBox(
            new Box(
                  (double)((float)_snowmanx.getX() + 0.25F),
                  (double)((float)_snowmanx.getY() + 0.25F),
                  (double)_snowmanx.getZ() + 0.25,
                  (double)((float)_snowmanx.getX() + 0.75F),
                  (double)((float)_snowmanx.getY() + 0.75F),
                  (double)((float)_snowmanx.getZ() + 0.75F)
               )
               .offset(-cameraX, -cameraY, -cameraZ),
            0.0F,
            1.0F,
            0.0F,
            0.5F
         );

         for (int _snowmanxx = 0; _snowmanxx < path.getLength(); _snowmanxx++) {
            PathNode _snowmanxxx = path.getNode(_snowmanxx);
            if (getManhattanDistance(_snowmanxxx.getPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
               float _snowmanxxxx = _snowmanxx == path.getCurrentNodeIndex() ? 1.0F : 0.0F;
               float _snowmanxxxxx = _snowmanxx == path.getCurrentNodeIndex() ? 0.0F : 1.0F;
               DebugRenderer.drawBox(
                  new Box(
                        (double)((float)_snowmanxxx.x + 0.5F - nodeSize),
                        (double)((float)_snowmanxxx.y + 0.01F * (float)_snowmanxx),
                        (double)((float)_snowmanxxx.z + 0.5F - nodeSize),
                        (double)((float)_snowmanxxx.x + 0.5F + nodeSize),
                        (double)((float)_snowmanxxx.y + 0.25F + 0.01F * (float)_snowmanxx),
                        (double)((float)_snowmanxxx.z + 0.5F + nodeSize)
                     )
                     .offset(-cameraX, -cameraY, -cameraZ),
                  _snowmanxxxx,
                  0.0F,
                  _snowmanxxxxx,
                  0.5F
               );
            }
         }
      }

      if (_snowman) {
         for (PathNode _snowmanxxx : path.method_22881()) {
            if (getManhattanDistance(_snowmanxxx.getPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
               DebugRenderer.drawBox(
                  new Box(
                        (double)((float)_snowmanxxx.x + 0.5F - nodeSize / 2.0F),
                        (double)((float)_snowmanxxx.y + 0.01F),
                        (double)((float)_snowmanxxx.z + 0.5F - nodeSize / 2.0F),
                        (double)((float)_snowmanxxx.x + 0.5F + nodeSize / 2.0F),
                        (double)_snowmanxxx.y + 0.1,
                        (double)((float)_snowmanxxx.z + 0.5F + nodeSize / 2.0F)
                     )
                     .offset(-cameraX, -cameraY, -cameraZ),
                  1.0F,
                  0.8F,
                  0.8F,
                  0.5F
               );
            }
         }

         for (PathNode _snowmanxxxx : path.method_22880()) {
            if (getManhattanDistance(_snowmanxxxx.getPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
               DebugRenderer.drawBox(
                  new Box(
                        (double)((float)_snowmanxxxx.x + 0.5F - nodeSize / 2.0F),
                        (double)((float)_snowmanxxxx.y + 0.01F),
                        (double)((float)_snowmanxxxx.z + 0.5F - nodeSize / 2.0F),
                        (double)((float)_snowmanxxxx.x + 0.5F + nodeSize / 2.0F),
                        (double)_snowmanxxxx.y + 0.1,
                        (double)((float)_snowmanxxxx.z + 0.5F + nodeSize / 2.0F)
                     )
                     .offset(-cameraX, -cameraY, -cameraZ),
                  0.8F,
                  1.0F,
                  1.0F,
                  0.5F
               );
            }
         }
      }

      if (drawLabels) {
         for (int _snowmanxxxxx = 0; _snowmanxxxxx < path.getLength(); _snowmanxxxxx++) {
            PathNode _snowmanxxxxxx = path.getNode(_snowmanxxxxx);
            if (getManhattanDistance(_snowmanxxxxxx.getPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
               DebugRenderer.drawString(
                  String.format("%s", _snowmanxxxxxx.type), (double)_snowmanxxxxxx.x + 0.5, (double)_snowmanxxxxxx.y + 0.75, (double)_snowmanxxxxxx.z + 0.5, -1, 0.02F, true, 0.0F, true
               );
               DebugRenderer.drawString(
                  String.format(Locale.ROOT, "%.2f", _snowmanxxxxxx.penalty),
                  (double)_snowmanxxxxxx.x + 0.5,
                  (double)_snowmanxxxxxx.y + 0.25,
                  (double)_snowmanxxxxxx.z + 0.5,
                  -1,
                  0.02F,
                  true,
                  0.0F,
                  true
               );
            }
         }
      }
   }

   public static void drawPathLines(Path path, double cameraX, double cameraY, double cameraZ) {
      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      _snowmanx.begin(3, VertexFormats.POSITION_COLOR);

      for (int _snowmanxx = 0; _snowmanxx < path.getLength(); _snowmanxx++) {
         PathNode _snowmanxxx = path.getNode(_snowmanxx);
         if (!(getManhattanDistance(_snowmanxxx.getPos(), cameraX, cameraY, cameraZ) > 80.0F)) {
            float _snowmanxxxx = (float)_snowmanxx / (float)path.getLength() * 0.33F;
            int _snowmanxxxxx = _snowmanxx == 0 ? 0 : MathHelper.hsvToRgb(_snowmanxxxx, 0.9F, 0.9F);
            int _snowmanxxxxxx = _snowmanxxxxx >> 16 & 0xFF;
            int _snowmanxxxxxxx = _snowmanxxxxx >> 8 & 0xFF;
            int _snowmanxxxxxxxx = _snowmanxxxxx & 0xFF;
            _snowmanx.vertex((double)_snowmanxxx.x - cameraX + 0.5, (double)_snowmanxxx.y - cameraY + 0.5, (double)_snowmanxxx.z - cameraZ + 0.5)
               .color(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, 255)
               .next();
         }
      }

      _snowman.draw();
   }

   private static float getManhattanDistance(BlockPos pos, double x, double y, double z) {
      return (float)(Math.abs((double)pos.getX() - x) + Math.abs((double)pos.getY() - y) + Math.abs((double)pos.getZ() - z));
   }
}
