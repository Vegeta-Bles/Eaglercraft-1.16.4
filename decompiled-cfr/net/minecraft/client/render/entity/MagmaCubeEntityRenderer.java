/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.MagmaCubeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class MagmaCubeEntityRenderer
extends MobEntityRenderer<MagmaCubeEntity, MagmaCubeEntityModel<MagmaCubeEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/slime/magmacube.png");

    public MagmaCubeEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new MagmaCubeEntityModel(), 0.25f);
    }

    @Override
    protected int getBlockLight(MagmaCubeEntity magmaCubeEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public Identifier getTexture(MagmaCubeEntity magmaCubeEntity) {
        return TEXTURE;
    }

    @Override
    protected void scale(MagmaCubeEntity magmaCubeEntity, MatrixStack matrixStack, float f) {
        int n = magmaCubeEntity.getSize();
        float _snowman2 = MathHelper.lerp(f, magmaCubeEntity.lastStretch, magmaCubeEntity.stretch) / ((float)n * 0.5f + 1.0f);
        float _snowman3 = 1.0f / (_snowman2 + 1.0f);
        matrixStack.scale(_snowman3 * (float)n, 1.0f / _snowman3 * (float)n, _snowman3 * (float)n);
    }
}

