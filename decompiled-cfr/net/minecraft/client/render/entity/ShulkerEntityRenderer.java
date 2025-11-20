/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ShulkerHeadFeatureRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ShulkerEntityRenderer
extends MobEntityRenderer<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> {
    public static final Identifier TEXTURE = new Identifier("textures/" + TexturedRenderLayers.SHULKER_TEXTURE_ID.getTextureId().getPath() + ".png");
    public static final Identifier[] COLORED_TEXTURES = (Identifier[])TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.stream().map(spriteIdentifier -> new Identifier("textures/" + spriteIdentifier.getTextureId().getPath() + ".png")).toArray(Identifier[]::new);

    public ShulkerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ShulkerEntityModel(), 0.0f);
        this.addFeature(new ShulkerHeadFeatureRenderer(this));
    }

    @Override
    public Vec3d getPositionOffset(ShulkerEntity shulkerEntity, float f) {
        int n = shulkerEntity.getTeleportLerpTimer();
        if (n > 0 && shulkerEntity.hasAttachedBlock()) {
            BlockPos blockPos = shulkerEntity.getAttachedBlock();
            _snowman = shulkerEntity.getPrevAttachedBlock();
            double _snowman2 = (double)((float)n - f) / 6.0;
            _snowman2 *= _snowman2;
            double _snowman3 = (double)(blockPos.getX() - _snowman.getX()) * _snowman2;
            double _snowman4 = (double)(blockPos.getY() - _snowman.getY()) * _snowman2;
            double _snowman5 = (double)(blockPos.getZ() - _snowman.getZ()) * _snowman2;
            return new Vec3d(-_snowman3, -_snowman4, -_snowman5);
        }
        return super.getPositionOffset(shulkerEntity, f);
    }

    @Override
    public boolean shouldRender(ShulkerEntity shulkerEntity, Frustum frustum, double d, double d2, double d3) {
        if (super.shouldRender(shulkerEntity, frustum, d, d2, d3)) {
            return true;
        }
        if (shulkerEntity.getTeleportLerpTimer() > 0 && shulkerEntity.hasAttachedBlock()) {
            Vec3d vec3d = Vec3d.of(shulkerEntity.getAttachedBlock());
            _snowman = Vec3d.of(shulkerEntity.getPrevAttachedBlock());
            if (frustum.isVisible(new Box(_snowman.x, _snowman.y, _snowman.z, vec3d.x, vec3d.y, vec3d.z))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Identifier getTexture(ShulkerEntity shulkerEntity) {
        if (shulkerEntity.getColor() == null) {
            return TEXTURE;
        }
        return COLORED_TEXTURES[shulkerEntity.getColor().getId()];
    }

    @Override
    protected void setupTransforms(ShulkerEntity shulkerEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.setupTransforms(shulkerEntity, matrixStack, f, f2 + 180.0f, f3);
        matrixStack.translate(0.0, 0.5, 0.0);
        matrixStack.multiply(shulkerEntity.getAttachedFace().getOpposite().getRotationQuaternion());
        matrixStack.translate(0.0, -0.5, 0.0);
    }
}

