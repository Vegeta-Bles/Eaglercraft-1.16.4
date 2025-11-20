/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import java.util.Random;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ItemEntityRenderer
extends EntityRenderer<ItemEntity> {
    private final ItemRenderer itemRenderer;
    private final Random random = new Random();

    public ItemEntityRenderer(EntityRenderDispatcher dispatcher, ItemRenderer itemRenderer) {
        super(dispatcher);
        this.itemRenderer = itemRenderer;
        this.shadowRadius = 0.15f;
        this.shadowOpacity = 0.75f;
    }

    private int getRenderedAmount(ItemStack stack) {
        int n = 1;
        if (stack.getCount() > 48) {
            n = 5;
        } else if (stack.getCount() > 32) {
            n = 4;
        } else if (stack.getCount() > 16) {
            n = 3;
        } else if (stack.getCount() > 1) {
            n = 2;
        }
        return n;
    }

    @Override
    public void render(ItemEntity itemEntity, float f, float f2, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n) {
        MatrixStack matrixStack2;
        matrixStack2.push();
        ItemStack itemStack = itemEntity.getStack();
        int _snowman2 = itemStack.isEmpty() ? 187 : Item.getRawId(itemStack.getItem()) + itemStack.getDamage();
        this.random.setSeed(_snowman2);
        BakedModel _snowman3 = this.itemRenderer.getHeldItemModel(itemStack, itemEntity.world, null);
        boolean _snowman4 = _snowman3.hasDepth();
        int _snowman5 = this.getRenderedAmount(itemStack);
        float _snowman6 = 0.25f;
        float _snowman7 = MathHelper.sin(((float)itemEntity.getAge() + f2) / 10.0f + itemEntity.hoverHeight) * 0.1f + 0.1f;
        float _snowman8 = _snowman3.getTransformation().getTransformation((ModelTransformation.Mode)ModelTransformation.Mode.GROUND).scale.getY();
        matrixStack2.translate(0.0, _snowman7 + 0.25f * _snowman8, 0.0);
        float _snowman9 = itemEntity.method_27314(f2);
        matrixStack2.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion(_snowman9));
        float _snowman10 = _snowman3.getTransformation().ground.scale.getX();
        float _snowman11 = _snowman3.getTransformation().ground.scale.getY();
        float _snowman12 = _snowman3.getTransformation().ground.scale.getZ();
        if (!_snowman4) {
            float f3 = -0.0f * (float)(_snowman5 - 1) * 0.5f * _snowman10;
            f4 = -0.0f * (float)(_snowman5 - 1) * 0.5f * _snowman11;
            _snowman = -0.09375f * (float)(_snowman5 - 1) * 0.5f * _snowman12;
            matrixStack2.translate(f3, f4, _snowman);
        }
        for (int i = 0; i < _snowman5; ++i) {
            matrixStack2.push();
            if (i > 0) {
                float f4;
                if (_snowman4) {
                    f4 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    _snowman = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    _snowman = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    matrixStack2.translate(f4, _snowman, _snowman);
                } else {
                    f4 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    _snowman = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    matrixStack2.translate(f4, _snowman, 0.0);
                }
            }
            this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.GROUND, false, matrixStack2, vertexConsumerProvider, n, OverlayTexture.DEFAULT_UV, _snowman3);
            matrixStack2.pop();
            if (_snowman4) continue;
            matrixStack2.translate(0.0f * _snowman10, 0.0f * _snowman11, 0.09375f * _snowman12);
        }
        matrixStack2.pop();
        super.render(itemEntity, f, f2, matrixStack2, vertexConsumerProvider, n);
    }

    @Override
    public Identifier getTexture(ItemEntity itemEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}

