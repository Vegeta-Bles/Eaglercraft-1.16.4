/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.client.render.block.entity;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.state.State;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class BannerBlockEntityRenderer
extends BlockEntityRenderer<BannerBlockEntity> {
    private final ModelPart banner = BannerBlockEntityRenderer.createBanner();
    private final ModelPart pillar = new ModelPart(64, 64, 44, 0);
    private final ModelPart crossbar;

    public BannerBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
        this.pillar.addCuboid(-1.0f, -30.0f, -1.0f, 2.0f, 42.0f, 2.0f, 0.0f);
        this.crossbar = new ModelPart(64, 64, 0, 42);
        this.crossbar.addCuboid(-10.0f, -32.0f, -1.0f, 20.0f, 2.0f, 2.0f, 0.0f);
    }

    public static ModelPart createBanner() {
        ModelPart modelPart = new ModelPart(64, 64, 0, 0);
        modelPart.addCuboid(-10.0f, 0.0f, -2.0f, 20.0f, 40.0f, 1.0f, 0.0f);
        return modelPart;
    }

    @Override
    public void render(BannerBlockEntity bannerBlockEntity2, float f, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        Object _snowman5;
        long _snowman4;
        List<Pair<BannerPattern, DyeColor>> list = bannerBlockEntity2.getPatterns();
        if (list == null) {
            return;
        }
        float _snowman2 = 0.6666667f;
        boolean _snowman3 = bannerBlockEntity2.getWorld() == null;
        matrixStack2.push();
        if (_snowman3) {
            _snowman4 = 0L;
            matrixStack2.translate(0.5, 0.5, 0.5);
            this.pillar.visible = true;
        } else {
            float _snowman6;
            BannerBlockEntity bannerBlockEntity2;
            _snowman4 = bannerBlockEntity2.getWorld().getTime();
            _snowman5 = bannerBlockEntity2.getCachedState();
            if (((AbstractBlock.AbstractBlockState)_snowman5).getBlock() instanceof BannerBlock) {
                matrixStack2.translate(0.5, 0.5, 0.5);
                _snowman6 = (float)(-((State)_snowman5).get(BannerBlock.ROTATION).intValue() * 360) / 16.0f;
                matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman6));
                this.pillar.visible = true;
            } else {
                MatrixStack matrixStack2;
                matrixStack2.translate(0.5, -0.1666666716337204, 0.5);
                _snowman6 = -((State)_snowman5).get(WallBannerBlock.FACING).asRotation();
                matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman6));
                matrixStack2.translate(0.0, -0.3125, -0.4375);
                this.pillar.visible = false;
            }
        }
        matrixStack2.push();
        matrixStack2.scale(0.6666667f, -0.6666667f, -0.6666667f);
        _snowman5 = ModelLoader.BANNER_BASE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        this.pillar.render(matrixStack2, (VertexConsumer)_snowman5, n, n2);
        this.crossbar.render(matrixStack2, (VertexConsumer)_snowman5, n, n2);
        BlockPos blockPos = bannerBlockEntity2.getPos();
        float _snowman7 = ((float)Math.floorMod((long)(blockPos.getX() * 7 + blockPos.getY() * 9 + blockPos.getZ() * 13) + _snowman4, 100L) + f) / 100.0f;
        this.banner.pitch = (-0.0125f + 0.01f * MathHelper.cos((float)Math.PI * 2 * _snowman7)) * (float)Math.PI;
        this.banner.pivotY = -32.0f;
        BannerBlockEntityRenderer.method_29999(matrixStack2, vertexConsumerProvider, n, n2, this.banner, ModelLoader.BANNER_BASE, true, list);
        matrixStack2.pop();
        matrixStack2.pop();
    }

    public static void method_29999(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2, ModelPart modelPart, SpriteIdentifier spriteIdentifier, boolean bl, List<Pair<BannerPattern, DyeColor>> list) {
        BannerBlockEntityRenderer.renderCanvas(matrixStack, vertexConsumerProvider, n, n2, modelPart, spriteIdentifier, bl, list, false);
    }

    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, List<Pair<BannerPattern, DyeColor>> patterns, boolean bl) {
        canvas.render(matrices, baseSprite.method_30001(vertexConsumers, RenderLayer::getEntitySolid, bl), light, overlay);
        for (int i = 0; i < 17 && i < patterns.size(); ++i) {
            Pair<BannerPattern, DyeColor> pair = patterns.get(i);
            float[] _snowman2 = ((DyeColor)pair.getSecond()).getColorComponents();
            SpriteIdentifier _snowman3 = new SpriteIdentifier(isBanner ? TexturedRenderLayers.BANNER_PATTERNS_ATLAS_TEXTURE : TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE, ((BannerPattern)((Object)pair.getFirst())).getSpriteId(isBanner));
            canvas.render(matrices, _snowman3.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay, _snowman2[0], _snowman2[1], _snowman2[2], 1.0f);
        }
    }
}

