/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

public class CampfireBlockEntityRenderer
extends BlockEntityRenderer<CampfireBlockEntity> {
    public CampfireBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    public void render(CampfireBlockEntity campfireBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        Direction direction = campfireBlockEntity.getCachedState().get(CampfireBlock.FACING);
        DefaultedList<ItemStack> _snowman2 = campfireBlockEntity.getItemsBeingCooked();
        for (int i = 0; i < _snowman2.size(); ++i) {
            ItemStack itemStack = _snowman2.get(i);
            if (itemStack == ItemStack.EMPTY) continue;
            matrixStack.push();
            matrixStack.translate(0.5, 0.44921875, 0.5);
            Direction _snowman3 = Direction.fromHorizontal((i + direction.getHorizontal()) % 4);
            float _snowman4 = -_snowman3.asRotation();
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman4));
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0f));
            matrixStack.translate(-0.3125, -0.3125, 0.0);
            matrixStack.scale(0.375f, 0.375f, 0.375f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.FIXED, n, n2, matrixStack, vertexConsumerProvider);
            matrixStack.pop();
        }
    }
}

