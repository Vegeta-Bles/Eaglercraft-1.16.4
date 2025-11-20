/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ComparisonChain
 *  com.google.common.collect.Ordering
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.hud;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;

public class PlayerListHud
extends DrawableHelper {
    private static final Ordering<PlayerListEntry> ENTRY_ORDERING = Ordering.from((Comparator)new EntryOrderComparator());
    private final MinecraftClient client;
    private final InGameHud inGameHud;
    private Text footer;
    private Text header;
    private long showTime;
    private boolean visible;

    public PlayerListHud(MinecraftClient client, InGameHud inGameHud) {
        this.client = client;
        this.inGameHud = inGameHud;
    }

    public Text getPlayerName(PlayerListEntry playerListEntry) {
        if (playerListEntry.getDisplayName() != null) {
            return this.method_27538(playerListEntry, playerListEntry.getDisplayName().shallowCopy());
        }
        return this.method_27538(playerListEntry, Team.modifyText(playerListEntry.getScoreboardTeam(), new LiteralText(playerListEntry.getProfile().getName())));
    }

    private Text method_27538(PlayerListEntry playerListEntry, MutableText mutableText) {
        return playerListEntry.getGameMode() == GameMode.SPECTATOR ? mutableText.formatted(Formatting.ITALIC) : mutableText;
    }

    public void tick(boolean visible) {
        if (visible && !this.visible) {
            this.showTime = Util.getMeasuringTimeMs();
        }
        this.visible = visible;
    }

    public void render(MatrixStack matrixStack2, int n, Scoreboard scoreboard, @Nullable ScoreboardObjective scoreboardObjective) {
        int n3;
        ClientPlayNetworkHandler clientPlayNetworkHandler = this.client.player.networkHandler;
        List _snowman2 = ENTRY_ORDERING.sortedCopy(clientPlayNetworkHandler.getPlayerList());
        int _snowman3 = 0;
        int _snowman4 = 0;
        for (PlayerListEntry playerListEntry : _snowman2) {
            int n4 = this.client.textRenderer.getWidth(this.getPlayerName(playerListEntry));
            _snowman3 = Math.max(_snowman3, n4);
            if (scoreboardObjective == null || scoreboardObjective.getRenderType() == ScoreboardCriterion.RenderType.HEARTS) continue;
            n4 = this.client.textRenderer.getWidth(" " + scoreboard.getPlayerScore(playerListEntry.getProfile().getName(), scoreboardObjective).getScore());
            _snowman4 = Math.max(_snowman4, n4);
        }
        _snowman2 = _snowman2.subList(0, Math.min(_snowman2.size(), 80));
        int n2 = n3 = _snowman2.size();
        _snowman = 1;
        while (n2 > 20) {
            n2 = (n3 + ++_snowman - 1) / _snowman;
        }
        boolean bl = _snowman = this.client.isInSingleplayer() || this.client.getNetworkHandler().getConnection().isEncrypted();
        _snowman = scoreboardObjective != null ? (scoreboardObjective.getRenderType() == ScoreboardCriterion.RenderType.HEARTS ? 90 : _snowman4) : 0;
        _snowman = Math.min(_snowman * ((_snowman ? 9 : 0) + _snowman3 + _snowman + 13), n - 50) / _snowman;
        _snowman = n / 2 - (_snowman * _snowman + (_snowman - 1) * 5) / 2;
        int n5 = 10;
        int n22 = _snowman * _snowman + (_snowman - 1) * 5;
        List<OrderedText> _snowman5 = null;
        if (this.header != null) {
            _snowman5 = this.client.textRenderer.wrapLines(this.header, n - 50);
            for (OrderedText orderedText : _snowman5) {
                n22 = Math.max(n22, this.client.textRenderer.getWidth(orderedText));
            }
        }
        List<OrderedText> list = null;
        if (this.footer != null) {
            list = this.client.textRenderer.wrapLines(this.footer, n - 50);
            for (OrderedText orderedText : list) {
                n22 = Math.max(n22, this.client.textRenderer.getWidth(orderedText));
            }
        }
        if (_snowman5 != null) {
            PlayerListHud.fill(matrixStack2, n / 2 - n22 / 2 - 1, n5 - 1, n / 2 + n22 / 2 + 1, n5 + _snowman5.size() * this.client.textRenderer.fontHeight, Integer.MIN_VALUE);
            for (OrderedText orderedText : _snowman5) {
                int n4 = this.client.textRenderer.getWidth(orderedText);
                this.client.textRenderer.drawWithShadow(matrixStack2, orderedText, (float)(n / 2 - n4 / 2), (float)n5, -1);
                n5 += this.client.textRenderer.fontHeight;
            }
            ++n5;
        }
        PlayerListHud.fill(matrixStack2, n / 2 - n22 / 2 - 1, n5 - 1, n / 2 + n22 / 2 + 1, n5 + n2 * 9, Integer.MIN_VALUE);
        int n6 = this.client.options.getTextBackgroundColor(0x20FFFFFF);
        for (int i = 0; i < n3; ++i) {
            int n62 = i / n2;
            int n8 = i % n2;
            _snowman = _snowman + n62 * _snowman + n62 * 5;
            _snowman = n5 + n8 * 9;
            PlayerListHud.fill(matrixStack2, _snowman, _snowman, _snowman + _snowman, _snowman + 8, n6);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            if (i >= _snowman2.size()) continue;
            PlayerListEntry playerListEntry = (PlayerListEntry)_snowman2.get(i);
            GameProfile _snowman7 = playerListEntry.getProfile();
            if (_snowman) {
                PlayerEntity playerEntity = this.client.world.getPlayerByUuid(_snowman7.getId());
                boolean _snowman8 = playerEntity != null && playerEntity.isPartVisible(PlayerModelPart.CAPE) && ("Dinnerbone".equals(_snowman7.getName()) || "Grumm".equals(_snowman7.getName()));
                this.client.getTextureManager().bindTexture(playerListEntry.getSkinTexture());
                int _snowman9 = 8 + (_snowman8 ? 8 : 0);
                int _snowman10 = 8 * (_snowman8 ? -1 : 1);
                DrawableHelper.drawTexture(matrixStack2, _snowman, _snowman, 8, 8, 8.0f, _snowman9, 8, _snowman10, 64, 64);
                if (playerEntity != null && playerEntity.isPartVisible(PlayerModelPart.HAT)) {
                    int n7 = 8 + (_snowman8 ? 8 : 0);
                    _snowman = 8 * (_snowman8 ? -1 : 1);
                    DrawableHelper.drawTexture(matrixStack2, _snowman, _snowman, 8, 8, 40.0f, n7, 8, _snowman, 64, 64);
                }
                _snowman += 9;
            }
            this.client.textRenderer.drawWithShadow(matrixStack2, this.getPlayerName(playerListEntry), (float)_snowman, (float)_snowman, playerListEntry.getGameMode() == GameMode.SPECTATOR ? -1862270977 : -1);
            if (scoreboardObjective != null && playerListEntry.getGameMode() != GameMode.SPECTATOR && (_snowman = (_snowman = _snowman + _snowman3 + 1) + _snowman) - _snowman > 5) {
                this.renderScoreboardObjective(scoreboardObjective, _snowman, _snowman7.getName(), _snowman, _snowman, playerListEntry, matrixStack2);
            }
            this.renderLatencyIcon(matrixStack2, _snowman, _snowman - (_snowman ? 9 : 0), _snowman, playerListEntry);
        }
        if (list != null) {
            PlayerListHud.fill(matrixStack2, n / 2 - n22 / 2 - 1, (n5 += n2 * 9 + 1) - 1, n / 2 + n22 / 2 + 1, n5 + list.size() * this.client.textRenderer.fontHeight, Integer.MIN_VALUE);
            for (OrderedText orderedText : list) {
                _snowman = this.client.textRenderer.getWidth(orderedText);
                this.client.textRenderer.drawWithShadow(matrixStack2, orderedText, (float)(n / 2 - _snowman / 2), (float)n5, -1);
                n5 += this.client.textRenderer.fontHeight;
            }
        }
    }

    protected void renderLatencyIcon(MatrixStack matrixStack, int n, int n2, int n3, PlayerListEntry playerListEntry) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
        boolean bl = false;
        int _snowman2 = playerListEntry.getLatency() < 0 ? 5 : (playerListEntry.getLatency() < 150 ? 0 : (playerListEntry.getLatency() < 300 ? 1 : (playerListEntry.getLatency() < 600 ? 2 : (playerListEntry.getLatency() < 1000 ? 3 : 4))));
        this.setZOffset(this.getZOffset() + 100);
        this.drawTexture(matrixStack, n2 + n - 11, n3, 0, 176 + _snowman2 * 8, 10, 8);
        this.setZOffset(this.getZOffset() - 100);
    }

    private void renderScoreboardObjective(ScoreboardObjective scoreboardObjective, int n, String string, int n2, int n3, PlayerListEntry playerListEntry, MatrixStack matrixStack) {
        int n4 = scoreboardObjective.getScoreboard().getPlayerScore(string, scoreboardObjective).getScore();
        if (scoreboardObjective.getRenderType() == ScoreboardCriterion.RenderType.HEARTS) {
            this.client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
            long l = Util.getMeasuringTimeMs();
            if (this.showTime == playerListEntry.method_2976()) {
                if (n4 < playerListEntry.method_2973()) {
                    playerListEntry.method_2978(l);
                    playerListEntry.method_2975(this.inGameHud.getTicks() + 20);
                } else if (n4 > playerListEntry.method_2973()) {
                    playerListEntry.method_2978(l);
                    playerListEntry.method_2975(this.inGameHud.getTicks() + 10);
                }
            }
            if (l - playerListEntry.method_2974() > 1000L || this.showTime != playerListEntry.method_2976()) {
                playerListEntry.method_2972(n4);
                playerListEntry.method_2965(n4);
                playerListEntry.method_2978(l);
            }
            playerListEntry.method_2964(this.showTime);
            playerListEntry.method_2972(n4);
            int _snowman2 = MathHelper.ceil((float)Math.max(n4, playerListEntry.method_2960()) / 2.0f);
            int _snowman3 = Math.max(MathHelper.ceil(n4 / 2), Math.max(MathHelper.ceil(playerListEntry.method_2960() / 2), 10));
            boolean bl = _snowman = playerListEntry.method_2961() > (long)this.inGameHud.getTicks() && (playerListEntry.method_2961() - (long)this.inGameHud.getTicks()) / 3L % 2L == 1L;
            if (_snowman2 > 0) {
                int n5 = MathHelper.floor(Math.min((float)(n3 - n2 - 4) / (float)_snowman3, 9.0f));
                if (n5 > 3) {
                    for (_snowman = _snowman2; _snowman < _snowman3; ++_snowman) {
                        this.drawTexture(matrixStack, n2 + _snowman * n5, n, _snowman ? 25 : 16, 0, 9, 9);
                    }
                    for (_snowman = 0; _snowman < _snowman2; ++_snowman) {
                        this.drawTexture(matrixStack, n2 + _snowman * n5, n, _snowman ? 25 : 16, 0, 9, 9);
                        if (_snowman) {
                            if (_snowman * 2 + 1 < playerListEntry.method_2960()) {
                                this.drawTexture(matrixStack, n2 + _snowman * n5, n, 70, 0, 9, 9);
                            }
                            if (_snowman * 2 + 1 == playerListEntry.method_2960()) {
                                this.drawTexture(matrixStack, n2 + _snowman * n5, n, 79, 0, 9, 9);
                            }
                        }
                        if (_snowman * 2 + 1 < n4) {
                            this.drawTexture(matrixStack, n2 + _snowman * n5, n, _snowman >= 10 ? 160 : 52, 0, 9, 9);
                        }
                        if (_snowman * 2 + 1 != n4) continue;
                        this.drawTexture(matrixStack, n2 + _snowman * n5, n, _snowman >= 10 ? 169 : 61, 0, 9, 9);
                    }
                } else {
                    float f = MathHelper.clamp((float)n4 / 20.0f, 0.0f, 1.0f);
                    int _snowman4 = (int)((1.0f - f) * 255.0f) << 16 | (int)(f * 255.0f) << 8;
                    String _snowman5 = "" + (float)n4 / 2.0f;
                    if (n3 - this.client.textRenderer.getWidth(_snowman5 + "hp") >= n2) {
                        _snowman5 = _snowman5 + "hp";
                    }
                    this.client.textRenderer.drawWithShadow(matrixStack, _snowman5, (float)((n3 + n2) / 2 - this.client.textRenderer.getWidth(_snowman5) / 2), (float)n, _snowman4);
                }
            }
        } else {
            String string2 = (Object)((Object)Formatting.YELLOW) + "" + n4;
            this.client.textRenderer.drawWithShadow(matrixStack, string2, (float)(n3 - this.client.textRenderer.getWidth(string2)), (float)n, 0xFFFFFF);
        }
    }

    public void setFooter(@Nullable Text footer) {
        this.footer = footer;
    }

    public void setHeader(@Nullable Text header) {
        this.header = header;
    }

    public void clear() {
        this.header = null;
        this.footer = null;
    }

    static class EntryOrderComparator
    implements Comparator<PlayerListEntry> {
        private EntryOrderComparator() {
        }

        @Override
        public int compare(PlayerListEntry playerListEntry, PlayerListEntry playerListEntry2) {
            Team team = playerListEntry.getScoreboardTeam();
            _snowman = playerListEntry2.getScoreboardTeam();
            return ComparisonChain.start().compareTrueFirst(playerListEntry.getGameMode() != GameMode.SPECTATOR, playerListEntry2.getGameMode() != GameMode.SPECTATOR).compare((Comparable)((Object)(team != null ? team.getName() : "")), (Comparable)((Object)(_snowman != null ? _snowman.getName() : ""))).compare((Object)playerListEntry.getProfile().getName(), (Object)playerListEntry2.getProfile().getName(), String::compareToIgnoreCase).result();
        }

        @Override
        public /* synthetic */ int compare(Object object, Object object2) {
            return this.compare((PlayerListEntry)object, (PlayerListEntry)object2);
        }
    }
}

