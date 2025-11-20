/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EndermanBlockFeatureRenderer;
import net.minecraft.client.render.entity.feature.EndermanEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class EndermanEntityRenderer
extends MobEntityRenderer<EndermanEntity, EndermanEntityModel<EndermanEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/enderman/enderman.png");
    private final Random random = new Random();

    public EndermanEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new EndermanEntityModel(0.0f), 0.5f);
        this.addFeature(new EndermanEyesFeatureRenderer<EndermanEntity>(this));
        this.addFeature(new EndermanBlockFeatureRenderer(this));
    }

    @Override
    public void render(EndermanEntity endermanEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        BlockState blockState = endermanEntity.getCarriedBlock();
        EndermanEntityModel _snowman2 = (EndermanEntityModel)this.getModel();
        _snowman2.carryingBlock = blockState != null;
        _snowman2.angry = endermanEntity.isAngry();
        super.render(endermanEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    public Vec3d getPositionOffset(EndermanEntity endermanEntity2, float f) {
        EndermanEntity endermanEntity2;
        if (endermanEntity2.isAngry()) {
            double d = 0.02;
            return new Vec3d(this.random.nextGaussian() * 0.02, 0.0, this.random.nextGaussian() * 0.02);
        }
        return super.getPositionOffset(endermanEntity2, f);
    }

    @Override
    public Identifier getTexture(EndermanEntity endermanEntity) {
        return TEXTURE;
    }
}

