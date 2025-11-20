package net.minecraft.client.render.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;

public class GameTestDebugRenderer implements DebugRenderer.Renderer {
   private final Map<BlockPos, GameTestDebugRenderer.Marker> markers = Maps.newHashMap();

   public GameTestDebugRenderer() {
   }

   public void addMarker(BlockPos pos, int color, String message, int duration) {
      this.markers.put(pos, new GameTestDebugRenderer.Marker(color, message, Util.getMeasuringTimeMs() + (long)duration));
   }

   @Override
   public void clear() {
      this.markers.clear();
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      long _snowman = Util.getMeasuringTimeMs();
      this.markers.entrySet().removeIf(_snowmanx -> _snowman > _snowmanx.getValue().removalTime);
      this.markers.forEach(this::method_23111);
   }

   private void method_23111(BlockPos _snowman, GameTestDebugRenderer.Marker _snowman) {
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(
         GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO
      );
      RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.disableTexture();
      DebugRenderer.drawBox(_snowman, 0.02F, _snowman.method_23112(), _snowman.method_23113(), _snowman.method_23114(), _snowman.method_23115());
      if (!_snowman.message.isEmpty()) {
         double _snowmanxx = (double)_snowman.getX() + 0.5;
         double _snowmanxxx = (double)_snowman.getY() + 1.2;
         double _snowmanxxxx = (double)_snowman.getZ() + 0.5;
         DebugRenderer.drawString(_snowman.message, _snowmanxx, _snowmanxxx, _snowmanxxxx, -1, 0.01F, true, 0.0F, true);
      }

      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
   }

   static class Marker {
      public int color;
      public String message;
      public long removalTime;

      public Marker(int color, String message, long removalTime) {
         this.color = color;
         this.message = message;
         this.removalTime = removalTime;
      }

      public float method_23112() {
         return (float)(this.color >> 16 & 0xFF) / 255.0F;
      }

      public float method_23113() {
         return (float)(this.color >> 8 & 0xFF) / 255.0F;
      }

      public float method_23114() {
         return (float)(this.color & 0xFF) / 255.0F;
      }

      public float method_23115() {
         return (float)(this.color >> 24 & 0xFF) / 255.0F;
      }
   }
}
