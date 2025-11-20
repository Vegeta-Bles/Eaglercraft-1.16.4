/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SlimeOverlayFeatureRenderer;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SlimeEntityRenderer
extends MobEntityRenderer<SlimeEntity, SlimeEntityModel<SlimeEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/slime/slime.png");

    public SlimeEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new SlimeEntityModel(16), 0.25f);
        this.addFeature(new SlimeOverlayFeatureRenderer<SlimeEntity>(this));
    }

    @Override
    public void render(SlimeEntity slimeEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        this.shadowRadius = 0.25f * (float)slimeEntity.getSize();
        super.render(slimeEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    protected void scale(SlimeEntity slimeEntity, MatrixStack matrixStack, float f) {
        _snowman = 0.999f;
        matrixStack.scale(0.999f, 0.999f, 0.999f);
        matrixStack.translate(0.0, 0.001f, 0.0);
        _snowman = slimeEntity.getSize();
        _snowman = MathHelper.lerp(f, slimeEntity.lastStretch, slimeEntity.stretch) / (_snowman * 0.5f + 1.0f);
        _snowman = 1.0f / (_snowman + 1.0f);
        matrixStack.scale(_snowman * _snowman, 1.0f / _snowman * _snowman, _snowman * _snowman);
    }

    @Override
    public Identifier getTexture(SlimeEntity slimeEntity) {
        return TEXTURE;
    }
}

