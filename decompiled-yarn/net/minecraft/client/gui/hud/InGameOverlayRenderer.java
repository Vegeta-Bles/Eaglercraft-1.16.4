package net.minecraft.client.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;

public class InGameOverlayRenderer {
   private static final Identifier UNDERWATER_TEXTURE = new Identifier("textures/misc/underwater.png");

   public static void renderOverlays(MinecraftClient _snowman, MatrixStack _snowman) {
      RenderSystem.disableAlphaTest();
      PlayerEntity _snowmanxx = _snowman.player;
      if (!_snowmanxx.noClip) {
         BlockState _snowmanxxx = getInWallBlockState(_snowmanxx);
         if (_snowmanxxx != null) {
            renderInWallOverlay(_snowman, _snowman.getBlockRenderManager().getModels().getSprite(_snowmanxxx), _snowman);
         }
      }

      if (!_snowman.player.isSpectator()) {
         if (_snowman.player.isSubmergedIn(FluidTags.WATER)) {
            renderUnderwaterOverlay(_snowman, _snowman);
         }

         if (_snowman.player.isOnFire()) {
            renderFireOverlay(_snowman, _snowman);
         }
      }

      RenderSystem.enableAlphaTest();
   }

   @Nullable
   private static BlockState getInWallBlockState(PlayerEntity _snowman) {
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();

      for (int _snowmanxx = 0; _snowmanxx < 8; _snowmanxx++) {
         double _snowmanxxx = _snowman.getX() + (double)(((float)((_snowmanxx >> 0) % 2) - 0.5F) * _snowman.getWidth() * 0.8F);
         double _snowmanxxxx = _snowman.getEyeY() + (double)(((float)((_snowmanxx >> 1) % 2) - 0.5F) * 0.1F);
         double _snowmanxxxxx = _snowman.getZ() + (double)(((float)((_snowmanxx >> 2) % 2) - 0.5F) * _snowman.getWidth() * 0.8F);
         _snowmanx.set(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
         BlockState _snowmanxxxxxx = _snowman.world.getBlockState(_snowmanx);
         if (_snowmanxxxxxx.getRenderType() != BlockRenderType.INVISIBLE && _snowmanxxxxxx.shouldBlockVision(_snowman.world, _snowmanx)) {
            return _snowmanxxxxxx;
         }
      }

      return null;
   }

   private static void renderInWallOverlay(MinecraftClient _snowman, Sprite _snowman, MatrixStack _snowman) {
      _snowman.getTextureManager().bindTexture(_snowman.getAtlas().getId());
      BufferBuilder _snowmanxxx = Tessellator.getInstance().getBuffer();
      float _snowmanxxxx = 0.1F;
      float _snowmanxxxxx = -1.0F;
      float _snowmanxxxxxx = 1.0F;
      float _snowmanxxxxxxx = -1.0F;
      float _snowmanxxxxxxxx = 1.0F;
      float _snowmanxxxxxxxxx = -0.5F;
      float _snowmanxxxxxxxxxx = _snowman.getMinU();
      float _snowmanxxxxxxxxxxx = _snowman.getMaxU();
      float _snowmanxxxxxxxxxxxx = _snowman.getMinV();
      float _snowmanxxxxxxxxxxxxx = _snowman.getMaxV();
      Matrix4f _snowmanxxxxxxxxxxxxxx = _snowman.peek().getModel();
      _snowmanxxx.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
      _snowmanxxx.vertex(_snowmanxxxxxxxxxxxxxx, -1.0F, -1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).texture(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx).next();
      _snowmanxxx.vertex(_snowmanxxxxxxxxxxxxxx, 1.0F, -1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).texture(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxx).next();
      _snowmanxxx.vertex(_snowmanxxxxxxxxxxxxxx, 1.0F, 1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).texture(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxx).next();
      _snowmanxxx.vertex(_snowmanxxxxxxxxxxxxxx, -1.0F, 1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).texture(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx).next();
      _snowmanxxx.end();
      BufferRenderer.draw(_snowmanxxx);
   }

   private static void renderUnderwaterOverlay(MinecraftClient _snowman, MatrixStack _snowman) {
      RenderSystem.enableTexture();
      _snowman.getTextureManager().bindTexture(UNDERWATER_TEXTURE);
      BufferBuilder _snowmanxx = Tessellator.getInstance().getBuffer();
      float _snowmanxxx = _snowman.player.getBrightnessAtEyes();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      float _snowmanxxxx = 4.0F;
      float _snowmanxxxxx = -1.0F;
      float _snowmanxxxxxx = 1.0F;
      float _snowmanxxxxxxx = -1.0F;
      float _snowmanxxxxxxxx = 1.0F;
      float _snowmanxxxxxxxxx = -0.5F;
      float _snowmanxxxxxxxxxx = -_snowman.player.yaw / 64.0F;
      float _snowmanxxxxxxxxxxx = _snowman.player.pitch / 64.0F;
      Matrix4f _snowmanxxxxxxxxxxxx = _snowman.peek().getModel();
      _snowmanxx.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
      _snowmanxx.vertex(_snowmanxxxxxxxxxxxx, -1.0F, -1.0F, -0.5F).color(_snowmanxxx, _snowmanxxx, _snowmanxxx, 0.1F).texture(4.0F + _snowmanxxxxxxxxxx, 4.0F + _snowmanxxxxxxxxxxx).next();
      _snowmanxx.vertex(_snowmanxxxxxxxxxxxx, 1.0F, -1.0F, -0.5F).color(_snowmanxxx, _snowmanxxx, _snowmanxxx, 0.1F).texture(0.0F + _snowmanxxxxxxxxxx, 4.0F + _snowmanxxxxxxxxxxx).next();
      _snowmanxx.vertex(_snowmanxxxxxxxxxxxx, 1.0F, 1.0F, -0.5F).color(_snowmanxxx, _snowmanxxx, _snowmanxxx, 0.1F).texture(0.0F + _snowmanxxxxxxxxxx, 0.0F + _snowmanxxxxxxxxxxx).next();
      _snowmanxx.vertex(_snowmanxxxxxxxxxxxx, -1.0F, 1.0F, -0.5F).color(_snowmanxxx, _snowmanxxx, _snowmanxxx, 0.1F).texture(4.0F + _snowmanxxxxxxxxxx, 0.0F + _snowmanxxxxxxxxxxx).next();
      _snowmanxx.end();
      BufferRenderer.draw(_snowmanxx);
      RenderSystem.disableBlend();
   }

   private static void renderFireOverlay(MinecraftClient _snowman, MatrixStack _snowman) {
      BufferBuilder _snowmanxx = Tessellator.getInstance().getBuffer();
      RenderSystem.depthFunc(519);
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableTexture();
      Sprite _snowmanxxx = ModelLoader.FIRE_1.getSprite();
      _snowman.getTextureManager().bindTexture(_snowmanxxx.getAtlas().getId());
      float _snowmanxxxx = _snowmanxxx.getMinU();
      float _snowmanxxxxx = _snowmanxxx.getMaxU();
      float _snowmanxxxxxx = (_snowmanxxxx + _snowmanxxxxx) / 2.0F;
      float _snowmanxxxxxxx = _snowmanxxx.getMinV();
      float _snowmanxxxxxxxx = _snowmanxxx.getMaxV();
      float _snowmanxxxxxxxxx = (_snowmanxxxxxxx + _snowmanxxxxxxxx) / 2.0F;
      float _snowmanxxxxxxxxxx = _snowmanxxx.getAnimationFrameDelta();
      float _snowmanxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxx, _snowmanxxxx, _snowmanxxxxxx);
      float _snowmanxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      float _snowmanxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx);
      float _snowmanxxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
      float _snowmanxxxxxxxxxxxxxxx = 1.0F;

      for (int _snowmanxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxxxxxx++) {
         _snowman.push();
         float _snowmanxxxxxxxxxxxxxxxxx = -0.5F;
         float _snowmanxxxxxxxxxxxxxxxxxx = 0.5F;
         float _snowmanxxxxxxxxxxxxxxxxxxx = -0.5F;
         float _snowmanxxxxxxxxxxxxxxxxxxxx = 0.5F;
         float _snowmanxxxxxxxxxxxxxxxxxxxxx = -0.5F;
         _snowman.translate((double)((float)(-(_snowmanxxxxxxxxxxxxxxxx * 2 - 1)) * 0.24F), -0.3F, 0.0);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)(_snowmanxxxxxxxxxxxxxxxx * 2 - 1) * 10.0F));
         Matrix4f _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.peek().getModel();
         _snowmanxx.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
         _snowmanxx.vertex(_snowmanxxxxxxxxxxxxxxxxxxxxxx, -0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx).next();
         _snowmanxx.vertex(_snowmanxxxxxxxxxxxxxxxxxxxxxx, 0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx).next();
         _snowmanxx.vertex(_snowmanxxxxxxxxxxxxxxxxxxxxxx, 0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx).next();
         _snowmanxx.vertex(_snowmanxxxxxxxxxxxxxxxxxxxxxx, -0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx).next();
         _snowmanxx.end();
         BufferRenderer.draw(_snowmanxx);
         _snowman.pop();
      }

      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.depthFunc(515);
   }
}
