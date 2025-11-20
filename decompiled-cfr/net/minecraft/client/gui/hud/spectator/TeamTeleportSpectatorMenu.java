/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.hud.spectator;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.SpectatorHud;
import net.minecraft.client.gui.hud.spectator.SpectatorMenu;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCommand;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCommandGroup;
import net.minecraft.client.gui.hud.spectator.TeleportSpectatorMenu;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TeamTeleportSpectatorMenu
implements SpectatorMenuCommandGroup,
SpectatorMenuCommand {
    private static final Text field_26618 = new TranslatableText("spectatorMenu.team_teleport");
    private static final Text field_26619 = new TranslatableText("spectatorMenu.team_teleport.prompt");
    private final List<SpectatorMenuCommand> commands = Lists.newArrayList();

    public TeamTeleportSpectatorMenu() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        for (Team team : minecraftClient.world.getScoreboard().getTeams()) {
            this.commands.add(new TeleportToSpecificTeamCommand(team));
        }
    }

    @Override
    public List<SpectatorMenuCommand> getCommands() {
        return this.commands;
    }

    @Override
    public Text getPrompt() {
        return field_26619;
    }

    @Override
    public void use(SpectatorMenu menu) {
        menu.selectElement(this);
    }

    @Override
    public Text getName() {
        return field_26618;
    }

    @Override
    public void renderIcon(MatrixStack matrixStack, float f, int n) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(SpectatorHud.SPECTATOR_TEXTURE);
        DrawableHelper.drawTexture(matrixStack, 0, 0, 16.0f, 0.0f, 16, 16, 256, 256);
    }

    @Override
    public boolean isEnabled() {
        for (SpectatorMenuCommand spectatorMenuCommand : this.commands) {
            if (!spectatorMenuCommand.isEnabled()) continue;
            return true;
        }
        return false;
    }

    class TeleportToSpecificTeamCommand
    implements SpectatorMenuCommand {
        private final Team team;
        private final Identifier skinId;
        private final List<PlayerListEntry> scoreboardEntries;

        public TeleportToSpecificTeamCommand(Team team) {
            this.team = team;
            this.scoreboardEntries = Lists.newArrayList();
            for (String string : team.getPlayerList()) {
                PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(string);
                if (playerListEntry == null) continue;
                this.scoreboardEntries.add(playerListEntry);
            }
            if (this.scoreboardEntries.isEmpty()) {
                this.skinId = DefaultSkinHelper.getTexture();
            } else {
                String string = this.scoreboardEntries.get(new Random().nextInt(this.scoreboardEntries.size())).getProfile().getName();
                this.skinId = AbstractClientPlayerEntity.getSkinId(string);
                AbstractClientPlayerEntity.loadSkin(this.skinId, string);
            }
        }

        @Override
        public void use(SpectatorMenu menu) {
            menu.selectElement(new TeleportSpectatorMenu(this.scoreboardEntries));
        }

        @Override
        public Text getName() {
            return this.team.getDisplayName();
        }

        @Override
        public void renderIcon(MatrixStack matrixStack, float f2, int n) {
            float f2;
            Integer n2 = this.team.getColor().getColorValue();
            if (n2 != null) {
                float f3 = (float)(n2 >> 16 & 0xFF) / 255.0f;
                _snowman = (float)(n2 >> 8 & 0xFF) / 255.0f;
                _snowman = (float)(n2 & 0xFF) / 255.0f;
                DrawableHelper.fill(matrixStack, 1, 1, 15, 15, MathHelper.packRgb(f3 * f2, _snowman * f2, _snowman * f2) | n << 24);
            }
            MinecraftClient.getInstance().getTextureManager().bindTexture(this.skinId);
            RenderSystem.color4f(f2, f2, f2, (float)n / 255.0f);
            DrawableHelper.drawTexture(matrixStack, 2, 2, 12, 12, 8.0f, 8.0f, 8, 8, 64, 64);
            DrawableHelper.drawTexture(matrixStack, 2, 2, 12, 12, 40.0f, 8.0f, 8, 8, 64, 64);
        }

        @Override
        public boolean isEnabled() {
            return !this.scoreboardEntries.isEmpty();
        }
    }
}

