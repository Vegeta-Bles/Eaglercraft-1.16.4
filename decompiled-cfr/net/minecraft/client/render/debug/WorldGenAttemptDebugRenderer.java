/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.render.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public class WorldGenAttemptDebugRenderer
implements DebugRenderer.Renderer {
    private final List<BlockPos> field_4640 = Lists.newArrayList();
    private final List<Float> field_4635 = Lists.newArrayList();
    private final List<Float> field_4637 = Lists.newArrayList();
    private final List<Float> field_4639 = Lists.newArrayList();
    private final List<Float> field_4636 = Lists.newArrayList();
    private final List<Float> field_4638 = Lists.newArrayList();

    public void method_3872(BlockPos blockPos, float f, float f2, float f3, float f4, float f5) {
        this.field_4640.add(blockPos);
        this.field_4635.add(Float.valueOf(f));
        this.field_4637.add(Float.valueOf(f5));
        this.field_4639.add(Float.valueOf(f2));
        this.field_4636.add(Float.valueOf(f3));
        this.field_4638.add(Float.valueOf(f4));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        _snowman2.begin(5, VertexFormats.POSITION_COLOR);
        for (int i = 0; i < this.field_4640.size(); ++i) {
            BlockPos blockPos = this.field_4640.get(i);
            Float _snowman3 = this.field_4635.get(i);
            float _snowman4 = _snowman3.floatValue() / 2.0f;
            WorldRenderer.drawBox(_snowman2, (double)((float)blockPos.getX() + 0.5f - _snowman4) - cameraX, (double)((float)blockPos.getY() + 0.5f - _snowman4) - cameraY, (double)((float)blockPos.getZ() + 0.5f - _snowman4) - cameraZ, (double)((float)blockPos.getX() + 0.5f + _snowman4) - cameraX, (double)((float)blockPos.getY() + 0.5f + _snowman4) - cameraY, (double)((float)blockPos.getZ() + 0.5f + _snowman4) - cameraZ, this.field_4639.get(i).floatValue(), this.field_4636.get(i).floatValue(), this.field_4638.get(i).floatValue(), this.field_4637.get(i).floatValue());
        }
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.popMatrix();
    }
}

