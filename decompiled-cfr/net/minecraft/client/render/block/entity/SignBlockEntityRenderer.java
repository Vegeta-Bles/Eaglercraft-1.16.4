/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import java.util.List;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.util.SignType;

public class SignBlockEntityRenderer
extends BlockEntityRenderer<SignBlockEntity> {
    private final SignModel model = new SignModel();

    public SignBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    public void render(SignBlockEntity signBlockEntity, float f, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        float _snowman3;
        BlockState blockState = signBlockEntity.getCachedState();
        matrixStack2.push();
        float _snowman2 = 0.6666667f;
        if (blockState.getBlock() instanceof SignBlock) {
            matrixStack2.translate(0.5, 0.5, 0.5);
            _snowman3 = -((float)(blockState.get(SignBlock.ROTATION) * 360) / 16.0f);
            matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman3));
            this.model.foot.visible = true;
        } else {
            MatrixStack matrixStack2;
            matrixStack2.translate(0.5, 0.5, 0.5);
            _snowman3 = -blockState.get(WallSignBlock.FACING).asRotation();
            matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman3));
            matrixStack2.translate(0.0, -0.3125, -0.4375);
            this.model.foot.visible = false;
        }
        matrixStack2.push();
        matrixStack2.scale(0.6666667f, -0.6666667f, -0.6666667f);
        SpriteIdentifier spriteIdentifier = SignBlockEntityRenderer.getModelTexture(blockState.getBlock());
        VertexConsumer _snowman4 = spriteIdentifier.getVertexConsumer(vertexConsumerProvider, this.model::getLayer);
        this.model.field.render(matrixStack2, _snowman4, n, n2);
        this.model.foot.render(matrixStack2, _snowman4, n, n2);
        matrixStack2.pop();
        TextRenderer _snowman5 = this.dispatcher.getTextRenderer();
        float _snowman6 = 0.010416667f;
        matrixStack2.translate(0.0, 0.3333333432674408, 0.046666666865348816);
        matrixStack2.scale(0.010416667f, -0.010416667f, 0.010416667f);
        int _snowman7 = signBlockEntity.getTextColor().getSignColor();
        double _snowman8 = 0.4;
        int _snowman9 = (int)((double)NativeImage.getRed(_snowman7) * 0.4);
        int _snowman10 = (int)((double)NativeImage.getGreen(_snowman7) * 0.4);
        int _snowman11 = (int)((double)NativeImage.getBlue(_snowman7) * 0.4);
        int _snowman12 = NativeImage.getAbgrColor(0, _snowman11, _snowman10, _snowman9);
        int _snowman13 = 20;
        for (int i = 0; i < 4; ++i) {
            OrderedText orderedText = signBlockEntity.getTextBeingEditedOnRow(i, text -> {
                List<OrderedText> list = _snowman5.wrapLines((StringVisitable)text, 90);
                return list.isEmpty() ? OrderedText.EMPTY : list.get(0);
            });
            if (orderedText == null) continue;
            float _snowman14 = -_snowman5.getWidth(orderedText) / 2;
            _snowman5.draw(orderedText, _snowman14, (float)(i * 10 - 20), _snowman12, false, matrixStack2.peek().getModel(), vertexConsumerProvider, false, 0, n);
        }
        matrixStack2.pop();
    }

    public static SpriteIdentifier getModelTexture(Block block) {
        SignType signType = block instanceof AbstractSignBlock ? ((AbstractSignBlock)block).getSignType() : SignType.OAK;
        return TexturedRenderLayers.getSignTextureId(signType);
    }

    public static final class SignModel
    extends Model {
        public final ModelPart field = new ModelPart(64, 32, 0, 0);
        public final ModelPart foot;

        public SignModel() {
            super(RenderLayer::getEntityCutoutNoCull);
            this.field.addCuboid(-12.0f, -14.0f, -1.0f, 24.0f, 12.0f, 2.0f, 0.0f);
            this.foot = new ModelPart(64, 32, 0, 14);
            this.foot.addCuboid(-1.0f, -2.0f, -1.0f, 2.0f, 14.0f, 2.0f, 0.0f);
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
            this.field.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            this.foot.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        }
    }
}

