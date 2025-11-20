/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EvokerFangsEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.util.Identifier;

public class EvokerFangsEntityRenderer
extends EntityRenderer<EvokerFangsEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/illager/evoker_fangs.png");
    private final EvokerFangsEntityModel<EvokerFangsEntity> model = new EvokerFangsEntityModel();

    public EvokerFangsEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(EvokerFangsEntity evokerFangsEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        float f3 = evokerFangsEntity.getAnimationProgress(f2);
        if (f3 == 0.0f) {
            return;
        }
        _snowman = 2.0f;
        if (f3 > 0.9f) {
            _snowman = (float)((double)_snowman * ((1.0 - (double)f3) / (double)0.1f));
        }
        matrixStack.push();
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0f - evokerFangsEntity.yaw));
        matrixStack.scale(-_snowman, -_snowman, _snowman);
        _snowman = 0.03125f;
        matrixStack.translate(0.0, -0.626f, 0.0);
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        this.model.setAngles(evokerFangsEntity, f3, 0.0f, 0.0f, evokerFangsEntity.yaw, evokerFangsEntity.pitch);
        VertexConsumer _snowman2 = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, _snowman2, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(evokerFangsEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    public Identifier getTexture(EvokerFangsEntity evokerFangsEntity) {
        return TEXTURE;
    }
}

