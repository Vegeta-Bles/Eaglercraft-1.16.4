package net.minecraft.client.network;

import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
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

public abstract class AbstractClientPlayerEntity extends PlayerEntity {
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
      PlayerListEntry _snowman = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(this.getGameProfile().getId());
      return _snowman != null && _snowman.getGameMode() == GameMode.SPECTATOR;
   }

   @Override
   public boolean isCreative() {
      PlayerListEntry _snowman = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(this.getGameProfile().getId());
      return _snowman != null && _snowman.getGameMode() == GameMode.CREATIVE;
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
      PlayerListEntry _snowman = this.getPlayerListEntry();
      return _snowman != null && _snowman.hasSkinTexture();
   }

   public Identifier getSkinTexture() {
      PlayerListEntry _snowman = this.getPlayerListEntry();
      return _snowman == null ? DefaultSkinHelper.getTexture(this.getUuid()) : _snowman.getSkinTexture();
   }

   @Nullable
   public Identifier getCapeTexture() {
      PlayerListEntry _snowman = this.getPlayerListEntry();
      return _snowman == null ? null : _snowman.getCapeTexture();
   }

   public boolean canRenderElytraTexture() {
      return this.getPlayerListEntry() != null;
   }

   @Nullable
   public Identifier getElytraTexture() {
      PlayerListEntry _snowman = this.getPlayerListEntry();
      return _snowman == null ? null : _snowman.getElytraTexture();
   }

   public static PlayerSkinTexture loadSkin(Identifier id, String playerName) {
      TextureManager _snowman = MinecraftClient.getInstance().getTextureManager();
      AbstractTexture _snowmanx = _snowman.getTexture(id);
      if (_snowmanx == null) {
         _snowmanx = new PlayerSkinTexture(
            null,
            String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", ChatUtil.stripTextFormat(playerName)),
            DefaultSkinHelper.getTexture(getOfflinePlayerUuid(playerName)),
            true,
            null
         );
         _snowman.registerTexture(id, _snowmanx);
      }

      return (PlayerSkinTexture)_snowmanx;
   }

   public static Identifier getSkinId(String playerName) {
      return new Identifier("skins/" + Hashing.sha1().hashUnencodedChars(ChatUtil.stripTextFormat(playerName)));
   }

   public String getModel() {
      PlayerListEntry _snowman = this.getPlayerListEntry();
      return _snowman == null ? DefaultSkinHelper.getModel(this.getUuid()) : _snowman.getModel();
   }

   public float getSpeed() {
      float _snowman = 1.0F;
      if (this.abilities.flying) {
         _snowman *= 1.1F;
      }

      _snowman = (float)((double)_snowman * ((this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / (double)this.abilities.getWalkSpeed() + 1.0) / 2.0));
      if (this.abilities.getWalkSpeed() == 0.0F || Float.isNaN(_snowman) || Float.isInfinite(_snowman)) {
         _snowman = 1.0F;
      }

      if (this.isUsingItem() && this.getActiveItem().getItem() == Items.BOW) {
         int _snowmanx = this.getItemUseTime();
         float _snowmanxx = (float)_snowmanx / 20.0F;
         if (_snowmanxx > 1.0F) {
            _snowmanxx = 1.0F;
         } else {
            _snowmanxx *= _snowmanxx;
         }

         _snowman *= 1.0F - _snowmanxx * 0.15F;
      }

      return MathHelper.lerp(MinecraftClient.getInstance().options.fovEffectScale, 1.0F, _snowman);
   }
}
