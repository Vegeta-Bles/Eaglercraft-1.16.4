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
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class BoatEntityRenderer
extends EntityRenderer<BoatEntity> {
    private static final Identifier[] TEXTURES = new Identifier[]{new Identifier("textures/entity/boat/oak.png"), new Identifier("textures/entity/boat/spruce.png"), new Identifier("textures/entity/boat/birch.png"), new Identifier("textures/entity/boat/jungle.png"), new Identifier("textures/entity/boat/acacia.png"), new Identifier("textures/entity/boat/dark_oak.png")};
    protected final BoatEntityModel model = new BoatEntityModel();

    public BoatEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.shadowRadius = 0.8f;
    }

    @Override
    public void render(BoatEntity boatEntity, float f, float f2, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n) {
        MatrixStack matrixStack2;
        matrixStack2.push();
        matrixStack2.translate(0.0, 0.375, 0.0);
        matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f - f));
        float f3 = (float)boatEntity.getDamageWobbleTicks() - f2;
        _snowman = boatEntity.getDamageWobbleStrength() - f2;
        if (_snowman < 0.0f) {
            _snowman = 0.0f;
        }
        if (f3 > 0.0f) {
            matrixStack2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(f3) * f3 * _snowman / 10.0f * (float)boatEntity.getDamageWobbleSide()));
        }
        if (!MathHelper.approximatelyEquals(_snowman = boatEntity.interpolateBubbleWobble(f2), 0.0f)) {
            matrixStack2.multiply(new Quaternion(new Vector3f(1.0f, 0.0f, 1.0f), boatEntity.interpolateBubbleWobble(f2), true));
        }
        matrixStack2.scale(-1.0f, -1.0f, 1.0f);
        matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0f));
        this.model.setAngles(boatEntity, f2, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer _snowman2 = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(boatEntity)));
        this.model.render(matrixStack2, _snowman2, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        if (!boatEntity.isSubmergedInWater()) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getWaterMask());
            this.model.getBottom().render(matrixStack2, vertexConsumer, n, OverlayTexture.DEFAULT_UV);
        }
        matrixStack2.pop();
        super.render(boatEntity, f, f2, matrixStack2, vertexConsumerProvider, n);
    }

    @Override
    public Identifier getTexture(BoatEntity boatEntity) {
        return TEXTURES[boatEntity.getBoatType().ordinal()];
    }
}

