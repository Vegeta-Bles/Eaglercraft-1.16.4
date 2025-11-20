/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class ConduitBlockEntityRenderer
extends BlockEntityRenderer<ConduitBlockEntity> {
    public static final SpriteIdentifier BASE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/base"));
    public static final SpriteIdentifier CAGE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/cage"));
    public static final SpriteIdentifier WIND_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/wind"));
    public static final SpriteIdentifier WIND_VERTICAL_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/wind_vertical"));
    public static final SpriteIdentifier OPEN_EYE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/open_eye"));
    public static final SpriteIdentifier CLOSED_EYE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/conduit/closed_eye"));
    private final ModelPart field_20823 = new ModelPart(16, 16, 0, 0);
    private final ModelPart field_20824;
    private final ModelPart field_20825;
    private final ModelPart field_20826;

    public ConduitBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
        this.field_20823.addCuboid(-4.0f, -4.0f, 0.0f, 8.0f, 8.0f, 0.0f, 0.01f);
        this.field_20824 = new ModelPart(64, 32, 0, 0);
        this.field_20824.addCuboid(-8.0f, -8.0f, -8.0f, 16.0f, 16.0f, 16.0f);
        this.field_20825 = new ModelPart(32, 16, 0, 0);
        this.field_20825.addCuboid(-3.0f, -3.0f, -3.0f, 6.0f, 6.0f, 6.0f);
        this.field_20826 = new ModelPart(32, 16, 0, 0);
        this.field_20826.addCuboid(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f);
    }

    @Override
    public void render(ConduitBlockEntity conduitBlockEntity2, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        ConduitBlockEntity conduitBlockEntity2;
        float f2 = (float)conduitBlockEntity2.ticks + f;
        if (!conduitBlockEntity2.isActive()) {
            _snowman = conduitBlockEntity2.getRotation(0.0f);
            VertexConsumer vertexConsumer = BASE_TEXTURE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman));
            this.field_20825.render(matrixStack, vertexConsumer, n, n2);
            matrixStack.pop();
            return;
        }
        float _snowman2 = conduitBlockEntity2.getRotation(f) * 57.295776f;
        float _snowman3 = MathHelper.sin(f2 * 0.1f) / 2.0f + 0.5f;
        _snowman3 = _snowman3 * _snowman3 + _snowman3;
        matrixStack.push();
        matrixStack.translate(0.5, 0.3f + _snowman3 * 0.2f, 0.5);
        Vector3f _snowman4 = new Vector3f(0.5f, 1.0f, 0.5f);
        _snowman4.normalize();
        matrixStack.multiply(new Quaternion(_snowman4, _snowman2, true));
        this.field_20826.render(matrixStack, CAGE_TEXTURE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutoutNoCull), n, n2);
        matrixStack.pop();
        int _snowman5 = conduitBlockEntity2.ticks / 66 % 3;
        matrixStack.push();
        matrixStack.translate(0.5, 0.5, 0.5);
        if (_snowman5 == 1) {
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0f));
        } else if (_snowman5 == 2) {
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
        }
        VertexConsumer _snowman6 = (_snowman5 == 1 ? WIND_VERTICAL_TEXTURE : WIND_TEXTURE).getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutoutNoCull);
        this.field_20824.render(matrixStack, _snowman6, n, n2);
        matrixStack.pop();
        matrixStack.push();
        matrixStack.translate(0.5, 0.5, 0.5);
        matrixStack.scale(0.875f, 0.875f, 0.875f);
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0f));
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0f));
        this.field_20824.render(matrixStack, _snowman6, n, n2);
        matrixStack.pop();
        Camera _snowman7 = this.dispatcher.camera;
        matrixStack.push();
        matrixStack.translate(0.5, 0.3f + _snowman3 * 0.2f, 0.5);
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        float _snowman8 = -_snowman7.getYaw();
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman8));
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman7.getPitch()));
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0f));
        float _snowman9 = 1.3333334f;
        matrixStack.scale(1.3333334f, 1.3333334f, 1.3333334f);
        this.field_20823.render(matrixStack, (conduitBlockEntity2.isEyeOpen() ? OPEN_EYE_TEXTURE : CLOSED_EYE_TEXTURE).getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutoutNoCull), n, n2);
        matrixStack.pop();
    }
}

