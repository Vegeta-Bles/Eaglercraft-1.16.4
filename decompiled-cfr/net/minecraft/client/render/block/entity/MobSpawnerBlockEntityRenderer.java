/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.MobSpawnerLogic;

public class MobSpawnerBlockEntityRenderer
extends BlockEntityRenderer<MobSpawnerBlockEntity> {
    public MobSpawnerBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    public void render(MobSpawnerBlockEntity mobSpawnerBlockEntity, float f, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        MatrixStack matrixStack2;
        matrixStack2.push();
        matrixStack2.translate(0.5, 0.0, 0.5);
        MobSpawnerLogic mobSpawnerLogic = mobSpawnerBlockEntity.getLogic();
        Entity _snowman2 = mobSpawnerLogic.getRenderedEntity();
        if (_snowman2 != null) {
            float f2 = 0.53125f;
            _snowman = Math.max(_snowman2.getWidth(), _snowman2.getHeight());
            if ((double)_snowman > 1.0) {
                f2 /= _snowman;
            }
            matrixStack2.translate(0.0, 0.4f, 0.0);
            matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)MathHelper.lerp((double)f, mobSpawnerLogic.method_8279(), mobSpawnerLogic.method_8278()) * 10.0f));
            matrixStack2.translate(0.0, -0.2f, 0.0);
            matrixStack2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-30.0f));
            matrixStack2.scale(f2, f2, f2);
            MinecraftClient.getInstance().getEntityRenderDispatcher().render(_snowman2, 0.0, 0.0, 0.0, 0.0f, f, matrixStack2, vertexConsumerProvider, n);
        }
        matrixStack2.pop();
    }
}

