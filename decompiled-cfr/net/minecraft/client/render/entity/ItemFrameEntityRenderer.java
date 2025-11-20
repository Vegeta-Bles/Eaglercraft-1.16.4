/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class ItemFrameEntityRenderer
extends EntityRenderer<ItemFrameEntity> {
    private static final ModelIdentifier NORMAL_FRAME = new ModelIdentifier("item_frame", "map=false");
    private static final ModelIdentifier MAP_FRAME = new ModelIdentifier("item_frame", "map=true");
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final ItemRenderer itemRenderer;

    public ItemFrameEntityRenderer(EntityRenderDispatcher dispatcher, ItemRenderer itemRenderer) {
        super(dispatcher);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(ItemFrameEntity itemFrameEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        super.render(itemFrameEntity, f, f2, matrixStack, vertexConsumerProvider, n);
        matrixStack.push();
        Direction direction = itemFrameEntity.getHorizontalFacing();
        Vec3d _snowman2 = this.getPositionOffset(itemFrameEntity, f2);
        matrixStack.translate(-_snowman2.getX(), -_snowman2.getY(), -_snowman2.getZ());
        double _snowman3 = 0.46875;
        matrixStack.translate((double)direction.getOffsetX() * 0.46875, (double)direction.getOffsetY() * 0.46875, (double)direction.getOffsetZ() * 0.46875);
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(itemFrameEntity.pitch));
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f - itemFrameEntity.yaw));
        boolean _snowman4 = itemFrameEntity.isInvisible();
        if (!_snowman4) {
            Object object = this.client.getBlockRenderManager();
            BakedModelManager _snowman5 = ((BlockRenderManager)object).getModels().getModelManager();
            ModelIdentifier _snowman6 = itemFrameEntity.getHeldItemStack().getItem() == Items.FILLED_MAP ? MAP_FRAME : NORMAL_FRAME;
            matrixStack.push();
            matrixStack.translate(-0.5, -0.5, -0.5);
            ((BlockRenderManager)object).getModelRenderer().render(matrixStack.peek(), vertexConsumerProvider.getBuffer(TexturedRenderLayers.getEntitySolid()), null, _snowman5.getModel(_snowman6), 1.0f, 1.0f, 1.0f, n, OverlayTexture.DEFAULT_UV);
            matrixStack.pop();
        }
        if (!((ItemStack)(object = itemFrameEntity.getHeldItemStack())).isEmpty()) {
            boolean bl = _snowman = ((ItemStack)object).getItem() == Items.FILLED_MAP;
            if (_snowman4) {
                matrixStack.translate(0.0, 0.0, 0.5);
            } else {
                matrixStack.translate(0.0, 0.0, 0.4375);
            }
            int _snowman7 = _snowman ? itemFrameEntity.getRotation() % 4 * 2 : itemFrameEntity.getRotation();
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)_snowman7 * 360.0f / 8.0f));
            if (_snowman) {
                matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0f));
                float f3 = 0.0078125f;
                matrixStack.scale(0.0078125f, 0.0078125f, 0.0078125f);
                matrixStack.translate(-64.0, -64.0, 0.0);
                MapState _snowman8 = FilledMapItem.getOrCreateMapState((ItemStack)object, itemFrameEntity.world);
                matrixStack.translate(0.0, 0.0, -1.0);
                if (_snowman8 != null) {
                    this.client.gameRenderer.getMapRenderer().draw(matrixStack, vertexConsumerProvider, _snowman8, true, n);
                }
            } else {
                matrixStack.scale(0.5f, 0.5f, 0.5f);
                this.itemRenderer.renderItem((ItemStack)object, ModelTransformation.Mode.FIXED, n, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider);
            }
        }
        matrixStack.pop();
    }

    @Override
    public Vec3d getPositionOffset(ItemFrameEntity itemFrameEntity, float f) {
        return new Vec3d((float)itemFrameEntity.getHorizontalFacing().getOffsetX() * 0.3f, -0.25, (float)itemFrameEntity.getHorizontalFacing().getOffsetZ() * 0.3f);
    }

    @Override
    public Identifier getTexture(ItemFrameEntity itemFrameEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }

    @Override
    protected boolean hasLabel(ItemFrameEntity itemFrameEntity) {
        if (!MinecraftClient.isHudEnabled() || itemFrameEntity.getHeldItemStack().isEmpty() || !itemFrameEntity.getHeldItemStack().hasCustomName() || this.dispatcher.targetedEntity != itemFrameEntity) {
            return false;
        }
        double d = this.dispatcher.getSquaredDistanceToCamera(itemFrameEntity);
        float _snowman2 = itemFrameEntity.isSneaky() ? 32.0f : 64.0f;
        return d < (double)(_snowman2 * _snowman2);
    }

    @Override
    protected void renderLabelIfPresent(ItemFrameEntity itemFrameEntity, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        super.renderLabelIfPresent(itemFrameEntity, itemFrameEntity.getHeldItemStack().getName(), matrixStack, vertexConsumerProvider, n);
    }
}

