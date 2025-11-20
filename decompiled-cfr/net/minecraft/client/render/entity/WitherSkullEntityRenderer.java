/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.SkullEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class WitherSkullEntityRenderer
extends EntityRenderer<WitherSkullEntity> {
    private static final Identifier INVULNERABLE_TEXTURE = new Identifier("textures/entity/wither/wither_invulnerable.png");
    private static final Identifier TEXTURE = new Identifier("textures/entity/wither/wither.png");
    private final SkullEntityModel model = new SkullEntityModel();

    public WitherSkullEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    protected int getBlockLight(WitherSkullEntity witherSkullEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void render(WitherSkullEntity witherSkullEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        matrixStack.push();
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        float f3 = MathHelper.lerpAngle(witherSkullEntity.prevYaw, witherSkullEntity.yaw, f2);
        _snowman = MathHelper.lerp(f2, witherSkullEntity.prevPitch, witherSkullEntity.pitch);
        VertexConsumer _snowman2 = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(witherSkullEntity)));
        this.model.method_2821(0.0f, f3, _snowman);
        this.model.render(matrixStack, _snowman2, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(witherSkullEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    public Identifier getTexture(WitherSkullEntity witherSkullEntity) {
        return witherSkullEntity.isCharged() ? INVULNERABLE_TEXTURE : TEXTURE;
    }
}

