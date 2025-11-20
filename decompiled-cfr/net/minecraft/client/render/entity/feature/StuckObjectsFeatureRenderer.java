/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import java.util.Random;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public abstract class StuckObjectsFeatureRenderer<T extends LivingEntity, M extends PlayerEntityModel<T>>
extends FeatureRenderer<T, M> {
    public StuckObjectsFeatureRenderer(LivingEntityRenderer<T, M> entityRenderer) {
        super(entityRenderer);
    }

    protected abstract int getObjectCount(T var1);

    protected abstract void renderObject(MatrixStack var1, VertexConsumerProvider var2, int var3, Entity var4, float var5, float var6, float var7, float var8);

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        int n2 = this.getObjectCount(t);
        Random _snowman2 = new Random(((Entity)t).getEntityId());
        if (n2 <= 0) {
            return;
        }
        for (_snowman = 0; _snowman < n2; ++_snowman) {
            matrixStack.push();
            ModelPart modelPart = ((PlayerEntityModel)this.getContextModel()).getRandomPart(_snowman2);
            ModelPart.Cuboid _snowman3 = modelPart.getRandomCuboid(_snowman2);
            modelPart.rotate(matrixStack);
            float _snowman4 = _snowman2.nextFloat();
            float _snowman5 = _snowman2.nextFloat();
            float _snowman6 = _snowman2.nextFloat();
            float _snowman7 = MathHelper.lerp(_snowman4, _snowman3.minX, _snowman3.maxX) / 16.0f;
            float _snowman8 = MathHelper.lerp(_snowman5, _snowman3.minY, _snowman3.maxY) / 16.0f;
            float _snowman9 = MathHelper.lerp(_snowman6, _snowman3.minZ, _snowman3.maxZ) / 16.0f;
            matrixStack.translate(_snowman7, _snowman8, _snowman9);
            _snowman4 = -1.0f * (_snowman4 * 2.0f - 1.0f);
            _snowman5 = -1.0f * (_snowman5 * 2.0f - 1.0f);
            _snowman6 = -1.0f * (_snowman6 * 2.0f - 1.0f);
            this.renderObject(matrixStack, vertexConsumerProvider, n, (Entity)t, _snowman4, _snowman5, _snowman6, f3);
            matrixStack.pop();
        }
    }
}

