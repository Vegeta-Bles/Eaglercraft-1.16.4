/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public class BellBlockEntityRenderer
extends BlockEntityRenderer<BellBlockEntity> {
    public static final SpriteIdentifier BELL_BODY_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/bell/bell_body"));
    private final ModelPart field_20816 = new ModelPart(32, 32, 0, 0);

    public BellBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
        this.field_20816.addCuboid(-3.0f, -6.0f, -3.0f, 6.0f, 7.0f, 6.0f);
        this.field_20816.setPivot(8.0f, 12.0f, 8.0f);
        ModelPart modelPart = new ModelPart(32, 32, 0, 13);
        modelPart.addCuboid(4.0f, 4.0f, 4.0f, 8.0f, 2.0f, 8.0f);
        modelPart.setPivot(-8.0f, -12.0f, -8.0f);
        this.field_20816.addChild(modelPart);
    }

    @Override
    public void render(BellBlockEntity bellBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        float f2 = (float)bellBlockEntity.ringTicks + f;
        _snowman = 0.0f;
        _snowman = 0.0f;
        if (bellBlockEntity.ringing) {
            _snowman = MathHelper.sin(f2 / (float)Math.PI) / (4.0f + f2 / 3.0f);
            if (bellBlockEntity.lastSideHit == Direction.NORTH) {
                _snowman = -_snowman;
            } else if (bellBlockEntity.lastSideHit == Direction.SOUTH) {
                _snowman = _snowman;
            } else if (bellBlockEntity.lastSideHit == Direction.EAST) {
                _snowman = -_snowman;
            } else if (bellBlockEntity.lastSideHit == Direction.WEST) {
                _snowman = _snowman;
            }
        }
        this.field_20816.pitch = _snowman;
        this.field_20816.roll = _snowman;
        VertexConsumer _snowman2 = BELL_BODY_TEXTURE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        this.field_20816.render(matrixStack, _snowman2, n, n2);
    }
}

