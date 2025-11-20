/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TridentEntityRenderer
extends EntityRenderer<TridentEntity> {
    public static final Identifier TEXTURE = new Identifier("textures/entity/trident.png");
    private final TridentEntityModel model = new TridentEntityModel();

    public TridentEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(TridentEntity tridentEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        matrixStack.push();
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(f2, tridentEntity.prevYaw, tridentEntity.yaw) - 90.0f));
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(f2, tridentEntity.prevPitch, tridentEntity.pitch) + 90.0f));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(this.getTexture(tridentEntity)), false, tridentEntity.isEnchanted());
        this.model.render(matrixStack, vertexConsumer, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(tridentEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    public Identifier getTexture(TridentEntity tridentEntity) {
        return TEXTURE;
    }
}

