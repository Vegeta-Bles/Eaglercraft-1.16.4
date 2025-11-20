/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.gui.hud;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BossBarHud
extends DrawableHelper {
    private static final Identifier BARS_TEXTURE = new Identifier("textures/gui/bars.png");
    private final MinecraftClient client;
    private final Map<UUID, ClientBossBar> bossBars = Maps.newLinkedHashMap();

    public BossBarHud(MinecraftClient client) {
        this.client = client;
    }

    public void render(MatrixStack matrices) {
        if (this.bossBars.isEmpty()) {
            return;
        }
        int n = this.client.getWindow().getScaledWidth();
        _snowman = 12;
        for (ClientBossBar clientBossBar : this.bossBars.values()) {
            int n2 = n / 2 - 91;
            _snowman = _snowman;
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.client.getTextureManager().bindTexture(BARS_TEXTURE);
            this.renderBossBar(matrices, n2, _snowman, clientBossBar);
            Text _snowman2 = clientBossBar.getName();
            _snowman = this.client.textRenderer.getWidth(_snowman2);
            _snowman = n / 2 - _snowman / 2;
            _snowman = _snowman - 9;
            this.client.textRenderer.drawWithShadow(matrices, _snowman2, (float)_snowman, (float)_snowman, 0xFFFFFF);
            if ((_snowman += 10 + this.client.textRenderer.fontHeight) < this.client.getWindow().getScaledHeight() / 3) continue;
            break;
        }
    }

    private void renderBossBar(MatrixStack matrices, int x, int y, BossBar bossBar) {
        int n;
        this.drawTexture(matrices, x, y, 0, bossBar.getColor().ordinal() * 5 * 2, 182, 5);
        if (bossBar.getOverlay() != BossBar.Style.PROGRESS) {
            this.drawTexture(matrices, x, y, 0, 80 + (bossBar.getOverlay().ordinal() - 1) * 5 * 2, 182, 5);
        }
        if ((n = (int)(bossBar.getPercent() * 183.0f)) > 0) {
            this.drawTexture(matrices, x, y, 0, bossBar.getColor().ordinal() * 5 * 2 + 5, n, 5);
            if (bossBar.getOverlay() != BossBar.Style.PROGRESS) {
                this.drawTexture(matrices, x, y, 0, 80 + (bossBar.getOverlay().ordinal() - 1) * 5 * 2 + 5, n, 5);
            }
        }
    }

    public void handlePacket(BossBarS2CPacket packet) {
        if (packet.getType() == BossBarS2CPacket.Type.ADD) {
            this.bossBars.put(packet.getUuid(), new ClientBossBar(packet));
        } else if (packet.getType() == BossBarS2CPacket.Type.REMOVE) {
            this.bossBars.remove(packet.getUuid());
        } else {
            this.bossBars.get(packet.getUuid()).handlePacket(packet);
        }
    }

    public void clear() {
        this.bossBars.clear();
    }

    public boolean shouldPlayDragonMusic() {
        if (!this.bossBars.isEmpty()) {
            for (BossBar bossBar : this.bossBars.values()) {
                if (!bossBar.hasDragonMusic()) continue;
                return true;
            }
        }
        return false;
    }

    public boolean shouldDarkenSky() {
        if (!this.bossBars.isEmpty()) {
            for (BossBar bossBar : this.bossBars.values()) {
                if (!bossBar.getDarkenSky()) continue;
                return true;
            }
        }
        return false;
    }

    public boolean shouldThickenFog() {
        if (!this.bossBars.isEmpty()) {
            for (BossBar bossBar : this.bossBars.values()) {
                if (!bossBar.getThickenFog()) continue;
                return true;
            }
        }
        return false;
    }
}

