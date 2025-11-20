/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.SalmonEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SalmonEntityRenderer
extends MobEntityRenderer<SalmonEntity, SalmonEntityModel<SalmonEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/fish/salmon.png");

    public SalmonEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new SalmonEntityModel(), 0.4f);
    }

    @Override
    public Identifier getTexture(SalmonEntity salmonEntity) {
        return TEXTURE;
    }

    @Override
    protected void setupTransforms(SalmonEntity salmonEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.setupTransforms(salmonEntity, matrixStack, f, f2, f3);
        _snowman = 1.0f;
        _snowman = 1.0f;
        if (!salmonEntity.isTouchingWater()) {
            _snowman = 1.3f;
            _snowman = 1.7f;
        }
        _snowman = _snowman * 4.3f * MathHelper.sin(_snowman * 0.6f * f);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman));
        matrixStack.translate(0.0, 0.0, -0.4f);
        if (!salmonEntity.isTouchingWater()) {
            matrixStack.translate(0.2f, 0.1f, 0.0);
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
        }
    }
}

