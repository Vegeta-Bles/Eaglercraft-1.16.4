/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EnderDragonEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class EndCrystalEntityRenderer
extends EntityRenderer<EndCrystalEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/end_crystal/end_crystal.png");
    private static final RenderLayer END_CRYSTAL = RenderLayer.getEntityCutoutNoCull(TEXTURE);
    private static final float SINE_45_DEGREES = (float)Math.sin(0.7853981633974483);
    private final ModelPart core;
    private final ModelPart frame;
    private final ModelPart bottom;

    public EndCrystalEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.shadowRadius = 0.5f;
        this.frame = new ModelPart(64, 32, 0, 0);
        this.frame.addCuboid(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f);
        this.core = new ModelPart(64, 32, 32, 0);
        this.core.addCuboid(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f);
        this.bottom = new ModelPart(64, 32, 0, 16);
        this.bottom.addCuboid(-6.0f, 0.0f, -6.0f, 12.0f, 4.0f, 12.0f);
    }

    @Override
    public void render(EndCrystalEntity endCrystalEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        matrixStack.push();
        float f3 = EndCrystalEntityRenderer.getYOffset(endCrystalEntity, f2);
        _snowman = ((float)endCrystalEntity.endCrystalAge + f2) * 3.0f;
        VertexConsumer _snowman2 = vertexConsumerProvider.getBuffer(END_CRYSTAL);
        matrixStack.push();
        matrixStack.scale(2.0f, 2.0f, 2.0f);
        matrixStack.translate(0.0, -0.5, 0.0);
        int _snowman3 = OverlayTexture.DEFAULT_UV;
        if (endCrystalEntity.getShowBottom()) {
            this.bottom.render(matrixStack, _snowman2, n, _snowman3);
        }
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman));
        matrixStack.translate(0.0, 1.5f + f3 / 2.0f, 0.0);
        matrixStack.multiply(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0f, SINE_45_DEGREES), 60.0f, true));
        this.frame.render(matrixStack, _snowman2, n, _snowman3);
        _snowman = 0.875f;
        matrixStack.scale(0.875f, 0.875f, 0.875f);
        matrixStack.multiply(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0f, SINE_45_DEGREES), 60.0f, true));
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman));
        this.frame.render(matrixStack, _snowman2, n, _snowman3);
        matrixStack.scale(0.875f, 0.875f, 0.875f);
        matrixStack.multiply(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0f, SINE_45_DEGREES), 60.0f, true));
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman));
        this.core.render(matrixStack, _snowman2, n, _snowman3);
        matrixStack.pop();
        matrixStack.pop();
        BlockPos _snowman4 = endCrystalEntity.getBeamTarget();
        if (_snowman4 != null) {
            _snowman = (float)_snowman4.getX() + 0.5f;
            _snowman = (float)_snowman4.getY() + 0.5f;
            _snowman = (float)_snowman4.getZ() + 0.5f;
            _snowman = (float)((double)_snowman - endCrystalEntity.getX());
            _snowman = (float)((double)_snowman - endCrystalEntity.getY());
            _snowman = (float)((double)_snowman - endCrystalEntity.getZ());
            matrixStack.translate(_snowman, _snowman, _snowman);
            EnderDragonEntityRenderer.renderCrystalBeam(-_snowman, -_snowman + f3, -_snowman, f2, endCrystalEntity.endCrystalAge, matrixStack, vertexConsumerProvider, n);
        }
        super.render(endCrystalEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    public static float getYOffset(EndCrystalEntity crystal, float tickDelta) {
        float f = (float)crystal.endCrystalAge + tickDelta;
        _snowman = MathHelper.sin(f * 0.2f) / 2.0f + 0.5f;
        _snowman = (_snowman * _snowman + _snowman) * 0.4f;
        return _snowman - 1.4f;
    }

    @Override
    public Identifier getTexture(EndCrystalEntity endCrystalEntity) {
        return TEXTURE;
    }

    @Override
    public boolean shouldRender(EndCrystalEntity endCrystalEntity, Frustum frustum, double d, double d2, double d3) {
        return super.shouldRender(endCrystalEntity, frustum, d, d2, d3) || endCrystalEntity.getBeamTarget() != null;
    }
}

