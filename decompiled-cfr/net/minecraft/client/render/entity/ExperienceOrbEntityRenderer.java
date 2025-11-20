/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class ExperienceOrbEntityRenderer
extends EntityRenderer<ExperienceOrbEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/experience_orb.png");
    private static final RenderLayer LAYER = RenderLayer.getItemEntityTranslucentCull(TEXTURE);

    public ExperienceOrbEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.shadowRadius = 0.15f;
        this.shadowOpacity = 0.75f;
    }

    @Override
    protected int getBlockLight(ExperienceOrbEntity experienceOrbEntity, BlockPos blockPos) {
        return MathHelper.clamp(super.getBlockLight(experienceOrbEntity, blockPos) + 7, 0, 15);
    }

    @Override
    public void render(ExperienceOrbEntity experienceOrbEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        matrixStack.push();
        _snowman = experienceOrbEntity.getOrbSize();
        float f3 = (float)(_snowman % 4 * 16 + 0) / 64.0f;
        _snowman = (float)(_snowman % 4 * 16 + 16) / 64.0f;
        _snowman = (float)(_snowman / 4 * 16 + 0) / 64.0f;
        _snowman = (float)(_snowman / 4 * 16 + 16) / 64.0f;
        _snowman = 1.0f;
        _snowman = 0.5f;
        _snowman = 0.25f;
        _snowman = 255.0f;
        _snowman = ((float)experienceOrbEntity.renderTicks + f2) / 2.0f;
        int _snowman2 = (int)((MathHelper.sin(_snowman + 0.0f) + 1.0f) * 0.5f * 255.0f);
        int _snowman3 = 255;
        int _snowman4 = (int)((MathHelper.sin(_snowman + 4.1887903f) + 1.0f) * 0.1f * 255.0f);
        matrixStack.translate(0.0, 0.1f, 0.0);
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f));
        _snowman = 0.3f;
        matrixStack.scale(0.3f, 0.3f, 0.3f);
        VertexConsumer _snowman5 = vertexConsumerProvider.getBuffer(LAYER);
        MatrixStack.Entry _snowman6 = matrixStack.peek();
        Matrix4f _snowman7 = _snowman6.getModel();
        Matrix3f _snowman8 = _snowman6.getNormal();
        ExperienceOrbEntityRenderer.method_23171(_snowman5, _snowman7, _snowman8, -0.5f, -0.25f, _snowman2, 255, _snowman4, f3, _snowman, n);
        ExperienceOrbEntityRenderer.method_23171(_snowman5, _snowman7, _snowman8, 0.5f, -0.25f, _snowman2, 255, _snowman4, _snowman, _snowman, n);
        ExperienceOrbEntityRenderer.method_23171(_snowman5, _snowman7, _snowman8, 0.5f, 0.75f, _snowman2, 255, _snowman4, _snowman, _snowman, n);
        ExperienceOrbEntityRenderer.method_23171(_snowman5, _snowman7, _snowman8, -0.5f, 0.75f, _snowman2, 255, _snowman4, f3, _snowman, n);
        matrixStack.pop();
        super.render(experienceOrbEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    private static void method_23171(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, float f, float f2, int n, int n2, int n3, float f3, float f4, int n4) {
        vertexConsumer.vertex(matrix4f, f, f2, 0.0f).color(n, n2, n3, 128).texture(f3, f4).overlay(OverlayTexture.DEFAULT_UV).light(n4).normal(matrix3f, 0.0f, 1.0f, 0.0f).next();
    }

    @Override
    public Identifier getTexture(ExperienceOrbEntity experienceOrbEntity) {
        return TEXTURE;
    }
}

