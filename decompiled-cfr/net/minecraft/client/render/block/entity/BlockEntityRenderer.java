/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;

public abstract class BlockEntityRenderer<T extends BlockEntity> {
    protected final BlockEntityRenderDispatcher dispatcher;

    public BlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public abstract void render(T var1, float var2, MatrixStack var3, VertexConsumerProvider var4, int var5, int var6);

    public boolean rendersOutsideBoundingBox(T blockEntity) {
        return false;
    }
}

