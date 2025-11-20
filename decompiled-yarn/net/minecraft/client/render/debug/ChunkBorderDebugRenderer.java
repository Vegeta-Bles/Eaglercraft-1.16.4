package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class ChunkBorderDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;

   public ChunkBorderDebugRenderer(MinecraftClient client) {
      this.client = client;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      RenderSystem.enableDepthTest();
      RenderSystem.shadeModel(7425);
      RenderSystem.enableAlphaTest();
      RenderSystem.defaultAlphaFunc();
      Entity _snowman = this.client.gameRenderer.getCamera().getFocusedEntity();
      Tessellator _snowmanx = Tessellator.getInstance();
      BufferBuilder _snowmanxx = _snowmanx.getBuffer();
      double _snowmanxxx = 0.0 - cameraY;
      double _snowmanxxxx = 256.0 - cameraY;
      RenderSystem.disableTexture();
      RenderSystem.disableBlend();
      double _snowmanxxxxx = (double)(_snowman.chunkX << 4) - cameraX;
      double _snowmanxxxxxx = (double)(_snowman.chunkZ << 4) - cameraZ;
      RenderSystem.lineWidth(1.0F);
      _snowmanxx.begin(3, VertexFormats.POSITION_COLOR);

      for (int _snowmanxxxxxxx = -16; _snowmanxxxxxxx <= 32; _snowmanxxxxxxx += 16) {
         for (int _snowmanxxxxxxxx = -16; _snowmanxxxxxxxx <= 32; _snowmanxxxxxxxx += 16) {
            _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.0F).next();
            _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
            _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).next();
            _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.0F).next();
         }
      }

      for (int _snowmanxxxxxxx = 2; _snowmanxxxxxxx < 16; _snowmanxxxxxxx += 2) {
         _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 0.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 0.0F).next();
      }

      for (int _snowmanxxxxxxx = 2; _snowmanxxxxxxx < 16; _snowmanxxxxxxx += 2) {
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + 16.0, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + 16.0, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + 16.0, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + 16.0, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).next();
      }

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= 256; _snowmanxxxxxxx += 2) {
         double _snowmanxxxxxxxx = (double)_snowmanxxxxxxx - cameraY;
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + 16.0, _snowmanxxxxxxxx, _snowmanxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + 16.0, _snowmanxxxxxxxx, _snowmanxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).next();
      }

      _snowmanx.draw();
      RenderSystem.lineWidth(2.0F);
      _snowmanxx.begin(3, VertexFormats.POSITION_COLOR);

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= 16; _snowmanxxxxxxx += 16) {
         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx <= 16; _snowmanxxxxxxxx += 16) {
            _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).color(0.25F, 0.25F, 1.0F, 0.0F).next();
            _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).color(0.25F, 0.25F, 1.0F, 1.0F).next();
            _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).color(0.25F, 0.25F, 1.0F, 1.0F).next();
            _snowmanxx.vertex(_snowmanxxxxx + (double)_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx + (double)_snowmanxxxxxxxx).color(0.25F, 0.25F, 1.0F, 0.0F).next();
         }
      }

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= 256; _snowmanxxxxxxx += 16) {
         double _snowmanxxxxxxxx = (double)_snowmanxxxxxxx - cameraY;
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).color(0.25F, 0.25F, 1.0F, 0.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).color(0.25F, 0.25F, 1.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx + 16.0).color(0.25F, 0.25F, 1.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + 16.0, _snowmanxxxxxxxx, _snowmanxxxxxx + 16.0).color(0.25F, 0.25F, 1.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx + 16.0, _snowmanxxxxxxxx, _snowmanxxxxxx).color(0.25F, 0.25F, 1.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).color(0.25F, 0.25F, 1.0F, 1.0F).next();
         _snowmanxx.vertex(_snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx).color(0.25F, 0.25F, 1.0F, 0.0F).next();
      }

      _snowmanx.draw();
      RenderSystem.lineWidth(1.0F);
      RenderSystem.enableBlend();
      RenderSystem.enableTexture();
      RenderSystem.shadeModel(7424);
   }
}
