/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import java.util.List;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.CatCollarFeatureRenderer;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

public class CatEntityRenderer
extends MobEntityRenderer<CatEntity, CatEntityModel<CatEntity>> {
    public CatEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new CatEntityModel(0.0f), 0.4f);
        this.addFeature(new CatCollarFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(CatEntity catEntity) {
        return catEntity.getTexture();
    }

    @Override
    protected void scale(CatEntity catEntity, MatrixStack matrixStack, float f) {
        super.scale(catEntity, matrixStack, f);
        matrixStack.scale(0.8f, 0.8f, 0.8f);
    }

    @Override
    protected void setupTransforms(CatEntity catEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.setupTransforms(catEntity, matrixStack, f, f2, f3);
        _snowman = catEntity.getSleepAnimation(f3);
        if (_snowman > 0.0f) {
            matrixStack.translate(0.4f * _snowman, 0.15f * _snowman, 0.1f * _snowman);
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerpAngleDegrees(_snowman, 0.0f, 90.0f)));
            BlockPos blockPos = catEntity.getBlockPos();
            List<PlayerEntity> _snowman2 = catEntity.world.getNonSpectatingEntities(PlayerEntity.class, new Box(blockPos).expand(2.0, 2.0, 2.0));
            for (PlayerEntity playerEntity : _snowman2) {
                if (!playerEntity.isSleeping()) continue;
                matrixStack.translate(0.15f * _snowman, 0.0, 0.0);
                break;
            }
        }
    }
}

