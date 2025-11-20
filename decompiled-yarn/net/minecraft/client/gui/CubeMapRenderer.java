package net.minecraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class CubeMapRenderer {
   private final Identifier[] faces = new Identifier[6];

   public CubeMapRenderer(Identifier faces) {
      for (int _snowman = 0; _snowman < 6; _snowman++) {
         this.faces[_snowman] = new Identifier(faces.getNamespace(), faces.getPath() + '_' + _snowman + ".png");
      }
   }

   public void draw(MinecraftClient client, float x, float y, float alpha) {
      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      RenderSystem.matrixMode(5889);
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      RenderSystem.multMatrix(
         Matrix4f.viewboxMatrix(85.0, (float)client.getWindow().getFramebufferWidth() / (float)client.getWindow().getFramebufferHeight(), 0.05F, 10.0F)
      );
      RenderSystem.matrixMode(5888);
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
      RenderSystem.enableBlend();
      RenderSystem.disableAlphaTest();
      RenderSystem.disableCull();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      int _snowmanxx = 2;

      for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
         RenderSystem.pushMatrix();
         float _snowmanxxxx = ((float)(_snowmanxxx % 2) / 2.0F - 0.5F) / 256.0F;
         float _snowmanxxxxx = ((float)(_snowmanxxx / 2) / 2.0F - 0.5F) / 256.0F;
         float _snowmanxxxxxx = 0.0F;
         RenderSystem.translatef(_snowmanxxxx, _snowmanxxxxx, 0.0F);
         RenderSystem.rotatef(x, 1.0F, 0.0F, 0.0F);
         RenderSystem.rotatef(y, 0.0F, 1.0F, 0.0F);

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 6; _snowmanxxxxxxx++) {
            client.getTextureManager().bindTexture(this.faces[_snowmanxxxxxxx]);
            _snowmanx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
            int _snowmanxxxxxxxx = Math.round(255.0F * alpha) / (_snowmanxxx + 1);
            if (_snowmanxxxxxxx == 0) {
               _snowmanx.vertex(-1.0, -1.0, 1.0).texture(0.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(-1.0, 1.0, 1.0).texture(0.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(1.0, 1.0, 1.0).texture(1.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(1.0, -1.0, 1.0).texture(1.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
            }

            if (_snowmanxxxxxxx == 1) {
               _snowmanx.vertex(1.0, -1.0, 1.0).texture(0.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(1.0, 1.0, 1.0).texture(0.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(1.0, 1.0, -1.0).texture(1.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(1.0, -1.0, -1.0).texture(1.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
            }

            if (_snowmanxxxxxxx == 2) {
               _snowmanx.vertex(1.0, -1.0, -1.0).texture(0.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(1.0, 1.0, -1.0).texture(0.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(-1.0, 1.0, -1.0).texture(1.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(-1.0, -1.0, -1.0).texture(1.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
            }

            if (_snowmanxxxxxxx == 3) {
               _snowmanx.vertex(-1.0, -1.0, -1.0).texture(0.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(-1.0, 1.0, -1.0).texture(0.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(-1.0, 1.0, 1.0).texture(1.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(-1.0, -1.0, 1.0).texture(1.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
            }

            if (_snowmanxxxxxxx == 4) {
               _snowmanx.vertex(-1.0, -1.0, -1.0).texture(0.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(-1.0, -1.0, 1.0).texture(0.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(1.0, -1.0, 1.0).texture(1.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(1.0, -1.0, -1.0).texture(1.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
            }

            if (_snowmanxxxxxxx == 5) {
               _snowmanx.vertex(-1.0, 1.0, 1.0).texture(0.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(-1.0, 1.0, -1.0).texture(0.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(1.0, 1.0, -1.0).texture(1.0F, 1.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
               _snowmanx.vertex(1.0, 1.0, 1.0).texture(1.0F, 0.0F).color(255, 255, 255, _snowmanxxxxxxxx).next();
            }

            _snowman.draw();
         }

         RenderSystem.popMatrix();
         RenderSystem.colorMask(true, true, true, false);
      }

      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.matrixMode(5889);
      RenderSystem.popMatrix();
      RenderSystem.matrixMode(5888);
      RenderSystem.popMatrix();
      RenderSystem.depthMask(true);
      RenderSystem.enableCull();
      RenderSystem.enableDepthTest();
   }

   public CompletableFuture<Void> loadTexturesAsync(TextureManager _snowman, Executor _snowman) {
      CompletableFuture<?>[] _snowmanxx = new CompletableFuture[6];

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.length; _snowmanxxx++) {
         _snowmanxx[_snowmanxxx] = _snowman.loadTextureAsync(this.faces[_snowmanxxx], _snowman);
      }

      return CompletableFuture.allOf(_snowmanxx);
   }
}
