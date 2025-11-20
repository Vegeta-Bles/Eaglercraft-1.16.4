/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.Hashing
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 */
package net.minecraft.client.network;

import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.PlayerSkinTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;

public abstract class AbstractClientPlayerEntity
extends PlayerEntity {
    private PlayerListEntry cachedScoreboardEntry;
    public float elytraPitch;
    public float elytraYaw;
    public float elytraRoll;
    public final ClientWorld clientWorld;

    public AbstractClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, world.getSpawnPos(), world.method_30671(), profile);
        this.clientWorld = world;
    }

    @Override
    public boolean isSpectator() {
        PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(this.getGameProfile().getId());
        return playerListEntry != null && playerListEntry.getGameMode() == GameMode.SPECTATOR;
    }

    @Override
    public boolean isCreative() {
        PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(this.getGameProfile().getId());
        return playerListEntry != null && playerListEntry.getGameMode() == GameMode.CREATIVE;
    }

    public boolean canRenderCapeTexture() {
        return this.getPlayerListEntry() != null;
    }

    @Nullable
    protected PlayerListEntry getPlayerListEntry() {
        if (this.cachedScoreboardEntry == null) {
            this.cachedScoreboardEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(this.getUuid());
        }
        return this.cachedScoreboardEntry;
    }

    public boolean hasSkinTexture() {
        PlayerListEntry playerListEntry = this.getPlayerListEntry();
        return playerListEntry != null && playerListEntry.hasSkinTexture();
    }

    public Identifier getSkinTexture() {
        PlayerListEntry playerListEntry = this.getPlayerListEntry();
        return playerListEntry == null ? DefaultSkinHelper.getTexture(this.getUuid()) : playerListEntry.getSkinTexture();
    }

    @Nullable
    public Identifier getCapeTexture() {
        PlayerListEntry playerListEntry = this.getPlayerListEntry();
        return playerListEntry == null ? null : playerListEntry.getCapeTexture();
    }

    public boolean canRenderElytraTexture() {
        return this.getPlayerListEntry() != null;
    }

    @Nullable
    public Identifier getElytraTexture() {
        PlayerListEntry playerListEntry = this.getPlayerListEntry();
        return playerListEntry == null ? null : playerListEntry.getElytraTexture();
    }

    public static PlayerSkinTexture loadSkin(Identifier id, String playerName) {
        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
        AbstractTexture _snowman2 = textureManager.getTexture(id);
        if (_snowman2 == null) {
            _snowman2 = new PlayerSkinTexture(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", ChatUtil.stripTextFormat(playerName)), DefaultSkinHelper.getTexture(AbstractClientPlayerEntity.getOfflinePlayerUuid(playerName)), true, null);
            textureManager.registerTexture(id, _snowman2);
        }
        return (PlayerSkinTexture)_snowman2;
    }

    public static Identifier getSkinId(String playerName) {
        return new Identifier("skins/" + Hashing.sha1().hashUnencodedChars((CharSequence)ChatUtil.stripTextFormat(playerName)));
    }

    public String getModel() {
        PlayerListEntry playerListEntry = this.getPlayerListEntry();
        return playerListEntry == null ? DefaultSkinHelper.getModel(this.getUuid()) : playerListEntry.getModel();
    }

    public float getSpeed() {
        float f = 1.0f;
        if (this.abilities.flying) {
            f *= 1.1f;
        }
        f = (float)((double)f * ((this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / (double)this.abilities.getWalkSpeed() + 1.0) / 2.0));
        if (this.abilities.getWalkSpeed() == 0.0f || Float.isNaN(f) || Float.isInfinite(f)) {
            f = 1.0f;
        }
        if (this.isUsingItem() && this.getActiveItem().getItem() == Items.BOW) {
            int n = this.getItemUseTime();
            float _snowman2 = (float)n / 20.0f;
            _snowman2 = _snowman2 > 1.0f ? 1.0f : (_snowman2 *= _snowman2);
            f *= 1.0f - _snowman2 * 0.15f;
        }
        return MathHelper.lerp(MinecraftClient.getInstance().options.fovEffectScale, 1.0f, f);
    }
}

