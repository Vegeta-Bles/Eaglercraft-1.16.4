/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 */
package net.minecraft.client.gui.hud.spectator;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.spectator.SpectatorMenu;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCommand;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.SpectatorTeleportC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TeleportToSpecificPlayerSpectatorCommand
implements SpectatorMenuCommand {
    private final GameProfile gameProfile;
    private final Identifier skinId;
    private final LiteralText name;

    public TeleportToSpecificPlayerSpectatorCommand(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> _snowman2 = minecraftClient.getSkinProvider().getTextures(gameProfile);
        this.skinId = _snowman2.containsKey(MinecraftProfileTexture.Type.SKIN) ? minecraftClient.getSkinProvider().loadSkin(_snowman2.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN) : DefaultSkinHelper.getTexture(PlayerEntity.getUuidFromProfile(gameProfile));
        this.name = new LiteralText(gameProfile.getName());
    }

    @Override
    public void use(SpectatorMenu menu) {
        MinecraftClient.getInstance().getNetworkHandler().sendPacket(new SpectatorTeleportC2SPacket(this.gameProfile.getId()));
    }

    @Override
    public Text getName() {
        return this.name;
    }

    @Override
    public void renderIcon(MatrixStack matrixStack, float f, int n) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(this.skinId);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, (float)n / 255.0f);
        DrawableHelper.drawTexture(matrixStack, 2, 2, 12, 12, 8.0f, 8.0f, 8, 8, 64, 64);
        DrawableHelper.drawTexture(matrixStack, 2, 2, 12, 12, 40.0f, 8.0f, 8, 8, 64, 64);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

