/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.render.block.entity;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;

public class EndPortalBlockEntityRenderer<T extends EndPortalBlockEntity>
extends BlockEntityRenderer<T> {
    public static final Identifier SKY_TEXTURE = new Identifier("textures/environment/end_sky.png");
    public static final Identifier PORTAL_TEXTURE = new Identifier("textures/entity/end_portal.png");
    private static final Random RANDOM = new Random(31100L);
    private static final List<RenderLayer> field_21732 = (List)IntStream.range(0, 16).mapToObj(n -> RenderLayer.getEndPortal(n + 1)).collect(ImmutableList.toImmutableList());

    public EndPortalBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    public void render(T t, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        RANDOM.setSeed(31100L);
        double d = ((BlockEntity)t).getPos().getSquaredDistance(this.dispatcher.camera.getPos(), true);
        int _snowman2 = this.method_3592(d);
        float _snowman3 = this.method_3594();
        Matrix4f _snowman4 = matrixStack.peek().getModel();
        this.method_23084(t, _snowman3, 0.15f, _snowman4, vertexConsumerProvider.getBuffer(field_21732.get(0)));
        for (int i = 1; i < _snowman2; ++i) {
            this.method_23084(t, _snowman3, 2.0f / (float)(18 - i), _snowman4, vertexConsumerProvider.getBuffer(field_21732.get(i)));
        }
    }

    private void method_23084(T t, float f, float f2, Matrix4f matrix4f, VertexConsumer vertexConsumer) {
        float f3 = (RANDOM.nextFloat() * 0.5f + 0.1f) * f2;
        _snowman = (RANDOM.nextFloat() * 0.5f + 0.4f) * f2;
        _snowman = (RANDOM.nextFloat() * 0.5f + 0.5f) * f2;
        this.method_23085(t, matrix4f, vertexConsumer, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, f3, _snowman, _snowman, Direction.SOUTH);
        this.method_23085(t, matrix4f, vertexConsumer, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f3, _snowman, _snowman, Direction.NORTH);
        this.method_23085(t, matrix4f, vertexConsumer, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, f3, _snowman, _snowman, Direction.EAST);
        this.method_23085(t, matrix4f, vertexConsumer, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, f3, _snowman, _snowman, Direction.WEST);
        this.method_23085(t, matrix4f, vertexConsumer, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f3, _snowman, _snowman, Direction.DOWN);
        this.method_23085(t, matrix4f, vertexConsumer, 0.0f, 1.0f, f, f, 1.0f, 1.0f, 0.0f, 0.0f, f3, _snowman, _snowman, Direction.UP);
    }

    private void method_23085(T t, Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, Direction direction) {
        if (((EndPortalBlockEntity)t).shouldDrawSide(direction)) {
            vertexConsumer.vertex(matrix4f, f, f3, f5).color(f9, f10, f11, 1.0f).next();
            vertexConsumer.vertex(matrix4f, f2, f3, f6).color(f9, f10, f11, 1.0f).next();
            vertexConsumer.vertex(matrix4f, f2, f4, f7).color(f9, f10, f11, 1.0f).next();
            vertexConsumer.vertex(matrix4f, f, f4, f8).color(f9, f10, f11, 1.0f).next();
        }
    }

    protected int method_3592(double d) {
        if (d > 36864.0) {
            return 1;
        }
        if (d > 25600.0) {
            return 3;
        }
        if (d > 16384.0) {
            return 5;
        }
        if (d > 9216.0) {
            return 7;
        }
        if (d > 4096.0) {
            return 9;
        }
        if (d > 1024.0) {
            return 11;
        }
        if (d > 576.0) {
            return 13;
        }
        if (d > 256.0) {
            return 14;
        }
        return 15;
    }

    protected float method_3594() {
        return 0.75f;
    }
}

