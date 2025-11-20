/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.feature.DrownedOverlayFeatureRenderer;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class DrownedEntityRenderer
extends ZombieBaseEntityRenderer<DrownedEntity, DrownedEntityModel<DrownedEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/zombie/drowned.png");

    public DrownedEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new DrownedEntityModel(0.0f, 0.0f, 64, 64), new DrownedEntityModel(0.5f, true), new DrownedEntityModel(1.0f, true));
        this.addFeature(new DrownedOverlayFeatureRenderer<DrownedEntity>(this));
    }

    @Override
    public Identifier getTexture(ZombieEntity zombieEntity) {
        return TEXTURE;
    }

    @Override
    protected void setupTransforms(DrownedEntity drownedEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.setupTransforms(drownedEntity, matrixStack, f, f2, f3);
        _snowman = drownedEntity.getLeaningPitch(f3);
        if (_snowman > 0.0f) {
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(_snowman, drownedEntity.pitch, -10.0f - drownedEntity.pitch)));
        }
    }
}

