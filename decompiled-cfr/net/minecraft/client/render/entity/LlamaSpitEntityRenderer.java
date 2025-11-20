/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.LlamaSpitEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class LlamaSpitEntityRenderer
extends EntityRenderer<LlamaSpitEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/llama/spit.png");
    private final LlamaSpitEntityModel<LlamaSpitEntity> model = new LlamaSpitEntityModel();

    public LlamaSpitEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(LlamaSpitEntity llamaSpitEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        matrixStack.push();
        matrixStack.translate(0.0, 0.15f, 0.0);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(f2, llamaSpitEntity.prevYaw, llamaSpitEntity.yaw) - 90.0f));
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(f2, llamaSpitEntity.prevPitch, llamaSpitEntity.pitch)));
        this.model.setAngles(llamaSpitEntity, f2, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(llamaSpitEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    public Identifier getTexture(LlamaSpitEntity llamaSpitEntity) {
        return TEXTURE;
    }
}

