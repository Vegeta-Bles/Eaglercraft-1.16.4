/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
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

    public static void renderOverlays(MinecraftClient minecraftClient, MatrixStack matrixStack) {
        RenderSystem.disableAlphaTest();
        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
        if (!clientPlayerEntity.noClip && (_snowman = InGameOverlayRenderer.getInWallBlockState(clientPlayerEntity)) != null) {
            InGameOverlayRenderer.renderInWallOverlay(minecraftClient, minecraftClient.getBlockRenderManager().getModels().getSprite(_snowman), matrixStack);
        }
        if (!minecraftClient.player.isSpectator()) {
            if (minecraftClient.player.isSubmergedIn(FluidTags.WATER)) {
                InGameOverlayRenderer.renderUnderwaterOverlay(minecraftClient, matrixStack);
            }
            if (minecraftClient.player.isOnFire()) {
                InGameOverlayRenderer.renderFireOverlay(minecraftClient, matrixStack);
            }
        }
        RenderSystem.enableAlphaTest();
    }

    @Nullable
    private static BlockState getInWallBlockState(PlayerEntity playerEntity) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < 8; ++i) {
            double d = playerEntity.getX() + (double)(((float)((i >> 0) % 2) - 0.5f) * playerEntity.getWidth() * 0.8f);
            _snowman = playerEntity.getEyeY() + (double)(((float)((i >> 1) % 2) - 0.5f) * 0.1f);
            _snowman = playerEntity.getZ() + (double)(((float)((i >> 2) % 2) - 0.5f) * playerEntity.getWidth() * 0.8f);
            mutable.set(d, _snowman, _snowman);
            BlockState _snowman2 = playerEntity.world.getBlockState(mutable);
            if (_snowman2.getRenderType() == BlockRenderType.INVISIBLE || !_snowman2.shouldBlockVision(playerEntity.world, mutable)) continue;
            return _snowman2;
        }
        return null;
    }

    private static void renderInWallOverlay(MinecraftClient minecraftClient, Sprite sprite, MatrixStack matrixStack) {
        minecraftClient.getTextureManager().bindTexture(sprite.getAtlas().getId());
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        float _snowman2 = 0.1f;
        float _snowman3 = -1.0f;
        float _snowman4 = 1.0f;
        float _snowman5 = -1.0f;
        float _snowman6 = 1.0f;
        float _snowman7 = -0.5f;
        float _snowman8 = sprite.getMinU();
        float _snowman9 = sprite.getMaxU();
        float _snowman10 = sprite.getMinV();
        float _snowman11 = sprite.getMaxV();
        Matrix4f _snowman12 = matrixStack.peek().getModel();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(_snowman12, -1.0f, -1.0f, -0.5f).color(0.1f, 0.1f, 0.1f, 1.0f).texture(_snowman9, _snowman11).next();
        bufferBuilder.vertex(_snowman12, 1.0f, -1.0f, -0.5f).color(0.1f, 0.1f, 0.1f, 1.0f).texture(_snowman8, _snowman11).next();
        bufferBuilder.vertex(_snowman12, 1.0f, 1.0f, -0.5f).color(0.1f, 0.1f, 0.1f, 1.0f).texture(_snowman8, _snowman10).next();
        bufferBuilder.vertex(_snowman12, -1.0f, 1.0f, -0.5f).color(0.1f, 0.1f, 0.1f, 1.0f).texture(_snowman9, _snowman10).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
    }

    private static void renderUnderwaterOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack) {
        RenderSystem.enableTexture();
        minecraftClient.getTextureManager().bindTexture(UNDERWATER_TEXTURE);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        float _snowman2 = minecraftClient.player.getBrightnessAtEyes();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        float _snowman3 = 4.0f;
        float _snowman4 = -1.0f;
        float _snowman5 = 1.0f;
        float _snowman6 = -1.0f;
        float _snowman7 = 1.0f;
        float _snowman8 = -0.5f;
        float _snowman9 = -minecraftClient.player.yaw / 64.0f;
        float _snowman10 = minecraftClient.player.pitch / 64.0f;
        Matrix4f _snowman11 = matrixStack.peek().getModel();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(_snowman11, -1.0f, -1.0f, -0.5f).color(_snowman2, _snowman2, _snowman2, 0.1f).texture(4.0f + _snowman9, 4.0f + _snowman10).next();
        bufferBuilder.vertex(_snowman11, 1.0f, -1.0f, -0.5f).color(_snowman2, _snowman2, _snowman2, 0.1f).texture(0.0f + _snowman9, 4.0f + _snowman10).next();
        bufferBuilder.vertex(_snowman11, 1.0f, 1.0f, -0.5f).color(_snowman2, _snowman2, _snowman2, 0.1f).texture(0.0f + _snowman9, 0.0f + _snowman10).next();
        bufferBuilder.vertex(_snowman11, -1.0f, 1.0f, -0.5f).color(_snowman2, _snowman2, _snowman2, 0.1f).texture(4.0f + _snowman9, 0.0f + _snowman10).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.disableBlend();
    }

    private static void renderFireOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.depthFunc(519);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableTexture();
        Sprite _snowman2 = ModelLoader.FIRE_1.getSprite();
        minecraftClient.getTextureManager().bindTexture(_snowman2.getAtlas().getId());
        float _snowman3 = _snowman2.getMinU();
        float _snowman4 = _snowman2.getMaxU();
        float _snowman5 = (_snowman3 + _snowman4) / 2.0f;
        float _snowman6 = _snowman2.getMinV();
        float _snowman7 = _snowman2.getMaxV();
        float _snowman8 = (_snowman6 + _snowman7) / 2.0f;
        float _snowman9 = _snowman2.getAnimationFrameDelta();
        float _snowman10 = MathHelper.lerp(_snowman9, _snowman3, _snowman5);
        float _snowman11 = MathHelper.lerp(_snowman9, _snowman4, _snowman5);
        float _snowman12 = MathHelper.lerp(_snowman9, _snowman6, _snowman8);
        float _snowman13 = MathHelper.lerp(_snowman9, _snowman7, _snowman8);
        float _snowman14 = 1.0f;
        for (int i = 0; i < 2; ++i) {
            matrixStack.push();
            float f = -0.5f;
            _snowman = 0.5f;
            _snowman = -0.5f;
            _snowman = 0.5f;
            _snowman = -0.5f;
            matrixStack.translate((float)(-(i * 2 - 1)) * 0.24f, -0.3f, 0.0);
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)(i * 2 - 1) * 10.0f));
            Matrix4f _snowman15 = matrixStack.peek().getModel();
            bufferBuilder.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
            bufferBuilder.vertex(_snowman15, -0.5f, -0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(_snowman11, _snowman13).next();
            bufferBuilder.vertex(_snowman15, 0.5f, -0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(_snowman10, _snowman13).next();
            bufferBuilder.vertex(_snowman15, 0.5f, 0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(_snowman10, _snowman12).next();
            bufferBuilder.vertex(_snowman15, -0.5f, 0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(_snowman11, _snowman12).next();
            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
            matrixStack.pop();
        }
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(515);
    }
}

