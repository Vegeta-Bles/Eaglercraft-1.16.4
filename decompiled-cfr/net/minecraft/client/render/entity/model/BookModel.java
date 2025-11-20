/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class BookModel
extends Model {
    private final ModelPart leftCover = new ModelPart(64, 32, 0, 0).addCuboid(-6.0f, -5.0f, -0.005f, 6.0f, 10.0f, 0.005f);
    private final ModelPart rightCover = new ModelPart(64, 32, 16, 0).addCuboid(0.0f, -5.0f, -0.005f, 6.0f, 10.0f, 0.005f);
    private final ModelPart leftBlock;
    private final ModelPart rightBlock;
    private final ModelPart leftPage;
    private final ModelPart rightPage;
    private final ModelPart spine = new ModelPart(64, 32, 12, 0).addCuboid(-1.0f, -5.0f, 0.0f, 2.0f, 10.0f, 0.005f);
    private final List<ModelPart> parts;

    public BookModel() {
        super(RenderLayer::getEntitySolid);
        this.leftBlock = new ModelPart(64, 32, 0, 10).addCuboid(0.0f, -4.0f, -0.99f, 5.0f, 8.0f, 1.0f);
        this.rightBlock = new ModelPart(64, 32, 12, 10).addCuboid(0.0f, -4.0f, -0.01f, 5.0f, 8.0f, 1.0f);
        this.leftPage = new ModelPart(64, 32, 24, 10).addCuboid(0.0f, -4.0f, 0.0f, 5.0f, 8.0f, 0.005f);
        this.rightPage = new ModelPart(64, 32, 24, 10).addCuboid(0.0f, -4.0f, 0.0f, 5.0f, 8.0f, 0.005f);
        this.parts = ImmutableList.of((Object)this.leftCover, (Object)this.rightCover, (Object)this.spine, (Object)this.leftBlock, (Object)this.rightBlock, (Object)this.leftPage, (Object)this.rightPage);
        this.leftCover.setPivot(0.0f, 0.0f, -1.0f);
        this.rightCover.setPivot(0.0f, 0.0f, 1.0f);
        this.spine.yaw = 1.5707964f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.method_24184(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public void method_24184(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.parts.forEach(modelPart -> modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
    }

    public void setPageAngles(float f, float f2, float f3, float f4) {
        _snowman = (MathHelper.sin(f * 0.02f) * 0.1f + 1.25f) * f4;
        this.leftCover.yaw = (float)Math.PI + _snowman;
        this.rightCover.yaw = -_snowman;
        this.leftBlock.yaw = _snowman;
        this.rightBlock.yaw = -_snowman;
        this.leftPage.yaw = _snowman - _snowman * 2.0f * f2;
        this.rightPage.yaw = _snowman - _snowman * 2.0f * f3;
        this.leftBlock.pivotX = MathHelper.sin(_snowman);
        this.rightBlock.pivotX = MathHelper.sin(_snowman);
        this.leftPage.pivotX = MathHelper.sin(_snowman);
        this.rightPage.pivotX = MathHelper.sin(_snowman);
    }
}

