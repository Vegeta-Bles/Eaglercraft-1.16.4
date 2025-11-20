/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.block.entity;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.DragonHeadEntityModel;
import net.minecraft.client.render.entity.model.SkullEntityModel;
import net.minecraft.client.render.entity.model.SkullOverlayEntityModel;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;

public class SkullBlockEntityRenderer
extends BlockEntityRenderer<SkullBlockEntity> {
    private static final Map<SkullBlock.SkullType, SkullEntityModel> MODELS = Util.make(Maps.newHashMap(), hashMap -> {
        SkullEntityModel skullEntityModel = new SkullEntityModel(0, 0, 64, 32);
        SkullOverlayEntityModel _snowman2 = new SkullOverlayEntityModel();
        DragonHeadEntityModel _snowman3 = new DragonHeadEntityModel(0.0f);
        hashMap.put(SkullBlock.Type.SKELETON, skullEntityModel);
        hashMap.put(SkullBlock.Type.WITHER_SKELETON, skullEntityModel);
        hashMap.put(SkullBlock.Type.PLAYER, _snowman2);
        hashMap.put(SkullBlock.Type.ZOMBIE, _snowman2);
        hashMap.put(SkullBlock.Type.CREEPER, skullEntityModel);
        hashMap.put(SkullBlock.Type.DRAGON, _snowman3);
    });
    private static final Map<SkullBlock.SkullType, Identifier> TEXTURES = Util.make(Maps.newHashMap(), hashMap -> {
        hashMap.put(SkullBlock.Type.SKELETON, new Identifier("textures/entity/skeleton/skeleton.png"));
        hashMap.put(SkullBlock.Type.WITHER_SKELETON, new Identifier("textures/entity/skeleton/wither_skeleton.png"));
        hashMap.put(SkullBlock.Type.ZOMBIE, new Identifier("textures/entity/zombie/zombie.png"));
        hashMap.put(SkullBlock.Type.CREEPER, new Identifier("textures/entity/creeper/creeper.png"));
        hashMap.put(SkullBlock.Type.DRAGON, new Identifier("textures/entity/enderdragon/dragon.png"));
        hashMap.put(SkullBlock.Type.PLAYER, DefaultSkinHelper.getTexture());
    });

    public SkullBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    public void render(SkullBlockEntity skullBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        float f2 = skullBlockEntity.getTicksPowered(f);
        BlockState _snowman2 = skullBlockEntity.getCachedState();
        boolean _snowman3 = _snowman2.getBlock() instanceof WallSkullBlock;
        Direction _snowman4 = _snowman3 ? _snowman2.get(WallSkullBlock.FACING) : null;
        _snowman = 22.5f * (float)(_snowman3 ? (2 + _snowman4.getHorizontal()) * 4 : _snowman2.get(SkullBlock.ROTATION));
        SkullBlockEntityRenderer.render(_snowman4, _snowman, ((AbstractSkullBlock)_snowman2.getBlock()).getSkullType(), skullBlockEntity.getOwner(), f2, matrixStack, vertexConsumerProvider, n);
    }

    public static void render(@Nullable Direction direction, float f, SkullBlock.SkullType skullType, @Nullable GameProfile gameProfile, float f2, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n) {
        MatrixStack matrixStack2;
        SkullEntityModel skullEntityModel = MODELS.get(skullType);
        matrixStack2.push();
        if (direction == null) {
            matrixStack2.translate(0.5, 0.0, 0.5);
        } else {
            float f3 = 0.25f;
            matrixStack2.translate(0.5f - (float)direction.getOffsetX() * 0.25f, 0.25, 0.5f - (float)direction.getOffsetZ() * 0.25f);
        }
        matrixStack2.scale(-1.0f, -1.0f, 1.0f);
        VertexConsumer _snowman2 = vertexConsumerProvider.getBuffer(SkullBlockEntityRenderer.method_3578(skullType, gameProfile));
        skullEntityModel.method_2821(f2, f, 0.0f);
        skullEntityModel.render(matrixStack2, _snowman2, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack2.pop();
    }

    private static RenderLayer method_3578(SkullBlock.SkullType skullType, @Nullable GameProfile gameProfile) {
        Identifier identifier = TEXTURES.get(skullType);
        if (skullType != SkullBlock.Type.PLAYER || gameProfile == null) {
            return RenderLayer.getEntityCutoutNoCullZOffset(identifier);
        }
        MinecraftClient _snowman2 = MinecraftClient.getInstance();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> _snowman3 = _snowman2.getSkinProvider().getTextures(gameProfile);
        if (_snowman3.containsKey(MinecraftProfileTexture.Type.SKIN)) {
            return RenderLayer.getEntityTranslucent(_snowman2.getSkinProvider().loadSkin(_snowman3.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN));
        }
        return RenderLayer.getEntityCutoutNoCull(DefaultSkinHelper.getTexture(PlayerEntity.getUuidFromProfile(gameProfile)));
    }
}

