/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class EnchantingTableBlockEntityRenderer
extends BlockEntityRenderer<EnchantingTableBlockEntity> {
    public static final SpriteIdentifier BOOK_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/enchanting_table_book"));
    private final BookModel book = new BookModel();

    public EnchantingTableBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    public void render(EnchantingTableBlockEntity enchantingTableBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        matrixStack.push();
        matrixStack.translate(0.5, 0.75, 0.5);
        float f2 = (float)enchantingTableBlockEntity.ticks + f;
        matrixStack.translate(0.0, 0.1f + MathHelper.sin(f2 * 0.1f) * 0.01f, 0.0);
        for (_snowman = enchantingTableBlockEntity.field_11964 - enchantingTableBlockEntity.field_11963; _snowman >= (float)Math.PI; _snowman -= (float)Math.PI * 2) {
        }
        while (_snowman < (float)(-Math.PI)) {
            _snowman += (float)Math.PI * 2;
        }
        _snowman = enchantingTableBlockEntity.field_11963 + _snowman * f;
        matrixStack.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion(-_snowman));
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(80.0f));
        _snowman = MathHelper.lerp(f, enchantingTableBlockEntity.pageAngle, enchantingTableBlockEntity.nextPageAngle);
        _snowman = MathHelper.fractionalPart(_snowman + 0.25f) * 1.6f - 0.3f;
        _snowman = MathHelper.fractionalPart(_snowman + 0.75f) * 1.6f - 0.3f;
        _snowman = MathHelper.lerp(f, enchantingTableBlockEntity.pageTurningSpeed, enchantingTableBlockEntity.nextPageTurningSpeed);
        this.book.setPageAngles(f2, MathHelper.clamp(_snowman, 0.0f, 1.0f), MathHelper.clamp(_snowman, 0.0f, 1.0f), _snowman);
        VertexConsumer _snowman2 = BOOK_TEXTURE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        this.book.method_24184(matrixStack, _snowman2, n, n2, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }
}

