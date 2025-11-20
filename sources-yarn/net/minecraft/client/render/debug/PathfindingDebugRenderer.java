package net.minecraft.client.render.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Locale;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class PathfindingDebugRenderer implements DebugRenderer.Renderer {
   private final Map<Integer, Path> paths = Maps.newHashMap();
   private final Map<Integer, Float> field_4617 = Maps.newHashMap();
   private final Map<Integer, Long> pathTimes = Maps.newHashMap();

   public PathfindingDebugRenderer() {
   }

   public void addPath(int id, Path path, float f) {
      this.paths.put(id, path);
      this.pathTimes.put(id, Util.getMeasuringTimeMs());
      this.field_4617.put(id, f);
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      if (!this.paths.isEmpty()) {
         long l = Util.getMeasuringTimeMs();

         for (Integer integer : this.paths.keySet()) {
            Path lv = this.paths.get(integer);
            float g = this.field_4617.get(integer);
            drawPath(lv, g, true, true, cameraX, cameraY, cameraZ);
         }

         for (Integer integer2 : this.pathTimes.keySet().toArray(new Integer[0])) {
            if (l - this.pathTimes.get(integer2) > 5000L) {
               this.paths.remove(integer2);
               this.pathTimes.remove(integer2);
            }
         }
      }
   }

   public static void drawPath(Path path, float nodeSize, boolean bl, boolean drawLabels, double cameraX, double cameraY, double cameraZ) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.disableTexture();
      RenderSystem.lineWidth(6.0F);
      drawPathInternal(path, nodeSize, bl, drawLabels, cameraX, cameraY, cameraZ);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
   }

   private static void drawPathInternal(Path path, float nodeSize, boolean bl, boolean drawLabels, double cameraX, double cameraY, double cameraZ) {
      drawPathLines(path, cameraX, cameraY, cameraZ);
      BlockPos lv = path.getTarget();
      if (getManhattanDistance(lv, cameraX, cameraY, cameraZ) <= 80.0F) {
         DebugRenderer.drawBox(
            new Box(
                  (double)((float)lv.getX() + 0.25F),
                  (double)((float)lv.getY() + 0.25F),
                  (double)lv.getZ() + 0.25,
                  (double)((float)lv.getX() + 0.75F),
                  (double)((float)lv.getY() + 0.75F),
                  (double)((float)lv.getZ() + 0.75F)
               )
               .offset(-cameraX, -cameraY, -cameraZ),
            0.0F,
            1.0F,
            0.0F,
            0.5F
         );

         for (int i = 0; i < path.getLength(); i++) {
            PathNode lv2 = path.getNode(i);
            if (getManhattanDistance(lv2.getPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
               float h = i == path.getCurrentNodeIndex() ? 1.0F : 0.0F;
               float j = i == path.getCurrentNodeIndex() ? 0.0F : 1.0F;
               DebugRenderer.drawBox(
                  new Box(
                        (double)((float)lv2.x + 0.5F - nodeSize),
                        (double)((float)lv2.y + 0.01F * (float)i),
                        (double)((float)lv2.z + 0.5F - nodeSize),
                        (double)((float)lv2.x + 0.5F + nodeSize),
                        (double)((float)lv2.y + 0.25F + 0.01F * (float)i),
                        (double)((float)lv2.z + 0.5F + nodeSize)
                     )
                     .offset(-cameraX, -cameraY, -cameraZ),
                  h,
                  0.0F,
                  j,
                  0.5F
               );
            }
         }
      }

      if (bl) {
         for (PathNode lv3 : path.method_22881()) {
            if (getManhattanDistance(lv3.getPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
               DebugRenderer.drawBox(
                  new Box(
                        (double)((float)lv3.x + 0.5F - nodeSize / 2.0F),
                        (double)((float)lv3.y + 0.01F),
                        (double)((float)lv3.z + 0.5F - nodeSize / 2.0F),
                        (double)((float)lv3.x + 0.5F + nodeSize / 2.0F),
                        (double)lv3.y + 0.1,
                        (double)((float)lv3.z + 0.5F + nodeSize / 2.0F)
                     )
                     .offset(-cameraX, -cameraY, -cameraZ),
                  1.0F,
                  0.8F,
                  0.8F,
                  0.5F
               );
            }
         }

         for (PathNode lv4 : path.method_22880()) {
            if (getManhattanDistance(lv4.getPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
               DebugRenderer.drawBox(
                  new Box(
                        (double)((float)lv4.x + 0.5F - nodeSize / 2.0F),
                        (double)((float)lv4.y + 0.01F),
                        (double)((float)lv4.z + 0.5F - nodeSize / 2.0F),
                        (double)((float)lv4.x + 0.5F + nodeSize / 2.0F),
                        (double)lv4.y + 0.1,
                        (double)((float)lv4.z + 0.5F + nodeSize / 2.0F)
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
         for (int k = 0; k < path.getLength(); k++) {
            PathNode lv5 = path.getNode(k);
            if (getManhattanDistance(lv5.getPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
               DebugRenderer.drawString(
                  String.format("%s", lv5.type), (double)lv5.x + 0.5, (double)lv5.y + 0.75, (double)lv5.z + 0.5, -1, 0.02F, true, 0.0F, true
               );
               DebugRenderer.drawString(
                  String.format(Locale.ROOT, "%.2f", lv5.penalty), (double)lv5.x + 0.5, (double)lv5.y + 0.25, (double)lv5.z + 0.5, -1, 0.02F, true, 0.0F, true
               );
            }
         }
      }
   }

   public static void drawPathLines(Path path, double cameraX, double cameraY, double cameraZ) {
      Tessellator lv = Tessellator.getInstance();
      BufferBuilder lv2 = lv.getBuffer();
      lv2.begin(3, VertexFormats.POSITION_COLOR);

      for (int i = 0; i < path.getLength(); i++) {
         PathNode lv3 = path.getNode(i);
         if (!(getManhattanDistance(lv3.getPos(), cameraX, cameraY, cameraZ) > 80.0F)) {
            float g = (float)i / (float)path.getLength() * 0.33F;
            int j = i == 0 ? 0 : MathHelper.hsvToRgb(g, 0.9F, 0.9F);
            int k = j >> 16 & 0xFF;
            int l = j >> 8 & 0xFF;
            int m = j & 0xFF;
            lv2.vertex((double)lv3.x - cameraX + 0.5, (double)lv3.y - cameraY + 0.5, (double)lv3.z - cameraZ + 0.5).color(k, l, m, 255).next();
         }
      }

      lv.draw();
   }

   private static float getManhattanDistance(BlockPos pos, double x, double y, double z) {
      return (float)(Math.abs((double)pos.getX() - x) + Math.abs((double)pos.getY() - y) + Math.abs((double)pos.getZ() - z));
   }
}
