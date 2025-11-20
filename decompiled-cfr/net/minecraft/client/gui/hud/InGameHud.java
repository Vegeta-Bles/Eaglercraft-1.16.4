/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Ordering
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.client.gui.hud;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.font.TextVisitFactory;
import net.minecraft.client.gui.ClientChatListener;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.GameInfoChatListener;
import net.minecraft.client.gui.hud.BackgroundHelper;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudListener;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.gui.hud.SpectatorHud;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.options.AttackIndicator;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.MessageType;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Arm;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;
import net.minecraft.world.border.WorldBorder;
import org.apache.commons.lang3.StringUtils;

public class InGameHud
extends DrawableHelper {
    private static final Identifier VIGNETTE_TEXTURE = new Identifier("textures/misc/vignette.png");
    private static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/widgets.png");
    private static final Identifier PUMPKIN_BLUR = new Identifier("textures/misc/pumpkinblur.png");
    private static final Text DEMO_EXPIRED_MESSAGE = new TranslatableText("demo.demoExpired");
    private final Random random = new Random();
    private final MinecraftClient client;
    private final ItemRenderer itemRenderer;
    private final ChatHud chatHud;
    private int ticks;
    @Nullable
    private Text overlayMessage;
    private int overlayRemaining;
    private boolean overlayTinted;
    public float vignetteDarkness = 1.0f;
    private int heldItemTooltipFade;
    private ItemStack currentStack = ItemStack.EMPTY;
    private final DebugHud debugHud;
    private final SubtitlesHud subtitlesHud;
    private final SpectatorHud spectatorHud;
    private final PlayerListHud playerListHud;
    private final BossBarHud bossBarHud;
    private int titleTotalTicks;
    @Nullable
    private Text title;
    @Nullable
    private Text subtitle;
    private int titleFadeInTicks;
    private int titleRemainTicks;
    private int titleFadeOutTicks;
    private int lastHealthValue;
    private int renderHealthValue;
    private long lastHealthCheckTime;
    private long heartJumpEndTick;
    private int scaledWidth;
    private int scaledHeight;
    private final Map<MessageType, List<ClientChatListener>> listeners = Maps.newHashMap();

    public InGameHud(MinecraftClient client) {
        this.client = client;
        this.itemRenderer = client.getItemRenderer();
        this.debugHud = new DebugHud(client);
        this.spectatorHud = new SpectatorHud(client);
        this.chatHud = new ChatHud(client);
        this.playerListHud = new PlayerListHud(client, this);
        this.bossBarHud = new BossBarHud(client);
        this.subtitlesHud = new SubtitlesHud(client);
        for (MessageType messageType : MessageType.values()) {
            this.listeners.put(messageType, Lists.newArrayList());
        }
        NarratorManager narratorManager = NarratorManager.INSTANCE;
        this.listeners.get((Object)MessageType.CHAT).add(new ChatHudListener(client));
        this.listeners.get((Object)MessageType.CHAT).add(narratorManager);
        this.listeners.get((Object)MessageType.SYSTEM).add(new ChatHudListener(client));
        this.listeners.get((Object)MessageType.SYSTEM).add(narratorManager);
        this.listeners.get((Object)MessageType.GAME_INFO).add(new GameInfoChatListener(client));
        this.setDefaultTitleFade();
    }

    public void setDefaultTitleFade() {
        this.titleFadeInTicks = 10;
        this.titleRemainTicks = 70;
        this.titleFadeOutTicks = 20;
    }

    public void render(MatrixStack matrices, float tickDelta) {
        int _snowman3;
        this.scaledWidth = this.client.getWindow().getScaledWidth();
        this.scaledHeight = this.client.getWindow().getScaledHeight();
        TextRenderer textRenderer = this.getFontRenderer();
        RenderSystem.enableBlend();
        if (MinecraftClient.isFancyGraphicsOrBetter()) {
            this.renderVignetteOverlay(this.client.getCameraEntity());
        } else {
            RenderSystem.enableDepthTest();
            RenderSystem.defaultBlendFunc();
        }
        ItemStack _snowman2 = this.client.player.inventory.getArmorStack(3);
        if (this.client.options.getPerspective().isFirstPerson() && _snowman2.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
            this.renderPumpkinOverlay();
        }
        if ((_snowman = MathHelper.lerp(tickDelta, this.client.player.lastNauseaStrength, this.client.player.nextNauseaStrength)) > 0.0f && !this.client.player.hasStatusEffect(StatusEffects.NAUSEA)) {
            this.renderPortalOverlay(_snowman);
        }
        if (this.client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
            this.spectatorHud.render(matrices, tickDelta);
        } else if (!this.client.options.hudHidden) {
            this.renderHotbar(tickDelta, matrices);
        }
        if (!this.client.options.hudHidden) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
            RenderSystem.enableBlend();
            RenderSystem.enableAlphaTest();
            this.renderCrosshair(matrices);
            RenderSystem.defaultBlendFunc();
            this.client.getProfiler().push("bossHealth");
            this.bossBarHud.render(matrices);
            this.client.getProfiler().pop();
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
            if (this.client.interactionManager.hasStatusBars()) {
                this.renderStatusBars(matrices);
            }
            this.renderMountHealth(matrices);
            RenderSystem.disableBlend();
            int n = this.scaledWidth / 2 - 91;
            if (this.client.player.hasJumpingMount()) {
                this.renderMountJumpBar(matrices, n);
            } else if (this.client.interactionManager.hasExperienceBar()) {
                this.renderExperienceBar(matrices, n);
            }
            if (this.client.options.heldItemTooltips && this.client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR) {
                this.renderHeldItemTooltip(matrices);
            } else if (this.client.player.isSpectator()) {
                this.spectatorHud.render(matrices);
            }
        }
        if (this.client.player.getSleepTimer() > 0) {
            this.client.getProfiler().push("sleep");
            RenderSystem.disableDepthTest();
            RenderSystem.disableAlphaTest();
            float f = this.client.player.getSleepTimer();
            _snowman = f / 100.0f;
            if (_snowman > 1.0f) {
                _snowman = 1.0f - (f - 100.0f) / 10.0f;
            }
            _snowman3 = (int)(220.0f * _snowman) << 24 | 0x101020;
            InGameHud.fill(matrices, 0, 0, this.scaledWidth, this.scaledHeight, _snowman3);
            RenderSystem.enableAlphaTest();
            RenderSystem.enableDepthTest();
            this.client.getProfiler().pop();
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (this.client.isDemo()) {
            this.renderDemoTimer(matrices);
        }
        this.renderStatusEffectOverlay(matrices);
        if (this.client.options.debugEnabled) {
            this.debugHud.render(matrices);
        }
        if (!this.client.options.hudHidden) {
            if (this.overlayMessage != null && this.overlayRemaining > 0) {
                this.client.getProfiler().push("overlayMessage");
                _snowman = (float)this.overlayRemaining - tickDelta;
                int n = (int)(_snowman * 255.0f / 20.0f);
                if (n > 255) {
                    n = 255;
                }
                if (n > 8) {
                    RenderSystem.pushMatrix();
                    RenderSystem.translatef(this.scaledWidth / 2, this.scaledHeight - 68, 0.0f);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    _snowman3 = 0xFFFFFF;
                    if (this.overlayTinted) {
                        _snowman3 = MathHelper.hsvToRgb(_snowman / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                    }
                    _snowman = n << 24 & 0xFF000000;
                    _snowman = textRenderer.getWidth(this.overlayMessage);
                    this.drawTextBackground(matrices, textRenderer, -4, _snowman, 0xFFFFFF | _snowman);
                    textRenderer.draw(matrices, this.overlayMessage, (float)(-_snowman / 2), -4.0f, _snowman3 | _snowman);
                    RenderSystem.disableBlend();
                    RenderSystem.popMatrix();
                }
                this.client.getProfiler().pop();
            }
            if (this.title != null && this.titleTotalTicks > 0) {
                this.client.getProfiler().push("titleAndSubtitle");
                float f = (float)this.titleTotalTicks - tickDelta;
                int _snowman4 = 255;
                if (this.titleTotalTicks > this.titleFadeOutTicks + this.titleRemainTicks) {
                    _snowman = (float)(this.titleFadeInTicks + this.titleRemainTicks + this.titleFadeOutTicks) - f;
                    _snowman4 = (int)(_snowman * 255.0f / (float)this.titleFadeInTicks);
                }
                if (this.titleTotalTicks <= this.titleFadeOutTicks) {
                    _snowman4 = (int)(f * 255.0f / (float)this.titleFadeOutTicks);
                }
                if ((_snowman4 = MathHelper.clamp(_snowman4, 0, 255)) > 8) {
                    RenderSystem.pushMatrix();
                    RenderSystem.translatef(this.scaledWidth / 2, this.scaledHeight / 2, 0.0f);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.pushMatrix();
                    RenderSystem.scalef(4.0f, 4.0f, 4.0f);
                    int n = _snowman4 << 24 & 0xFF000000;
                    _snowman = textRenderer.getWidth(this.title);
                    this.drawTextBackground(matrices, textRenderer, -10, _snowman, 0xFFFFFF | n);
                    textRenderer.drawWithShadow(matrices, this.title, (float)(-_snowman / 2), -10.0f, 0xFFFFFF | n);
                    RenderSystem.popMatrix();
                    if (this.subtitle != null) {
                        RenderSystem.pushMatrix();
                        RenderSystem.scalef(2.0f, 2.0f, 2.0f);
                        _snowman = textRenderer.getWidth(this.subtitle);
                        this.drawTextBackground(matrices, textRenderer, 5, _snowman, 0xFFFFFF | n);
                        textRenderer.drawWithShadow(matrices, this.subtitle, (float)(-_snowman / 2), 5.0f, 0xFFFFFF | n);
                        RenderSystem.popMatrix();
                    }
                    RenderSystem.disableBlend();
                    RenderSystem.popMatrix();
                }
                this.client.getProfiler().pop();
            }
            this.subtitlesHud.render(matrices);
            Scoreboard scoreboard = this.client.world.getScoreboard();
            ScoreboardObjective _snowman5 = null;
            Team _snowman6 = scoreboard.getPlayerTeam(this.client.player.getEntityName());
            if (_snowman6 != null && (_snowman = _snowman6.getColor().getColorIndex()) >= 0) {
                _snowman5 = scoreboard.getObjectiveForSlot(3 + _snowman);
            }
            ScoreboardObjective scoreboardObjective = _snowman7 = _snowman5 != null ? _snowman5 : scoreboard.getObjectiveForSlot(1);
            if (_snowman7 != null) {
                this.renderScoreboardSidebar(matrices, _snowman7);
            }
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableAlphaTest();
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0f, this.scaledHeight - 48, 0.0f);
            this.client.getProfiler().push("chat");
            this.chatHud.render(matrices, this.ticks);
            this.client.getProfiler().pop();
            RenderSystem.popMatrix();
            ScoreboardObjective _snowman7 = scoreboard.getObjectiveForSlot(0);
            if (this.client.options.keyPlayerList.isPressed() && (!this.client.isInSingleplayer() || this.client.player.networkHandler.getPlayerList().size() > 1 || _snowman7 != null)) {
                this.playerListHud.tick(true);
                this.playerListHud.render(matrices, this.scaledWidth, scoreboard, _snowman7);
            } else {
                this.playerListHud.tick(false);
            }
        }
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableAlphaTest();
    }

    private void drawTextBackground(MatrixStack matrices, TextRenderer textRenderer, int yOffset, int width, int color) {
        int n = this.client.options.getTextBackgroundColor(0.0f);
        if (n != 0) {
            _snowman = -width / 2;
            InGameHud.fill(matrices, _snowman - 2, yOffset - 2, _snowman + width + 2, yOffset + textRenderer.fontHeight + 2, BackgroundHelper.ColorMixer.mixColor(n, color));
        }
    }

    private void renderCrosshair(MatrixStack matrices) {
        GameOptions gameOptions = this.client.options;
        if (!gameOptions.getPerspective().isFirstPerson()) {
            return;
        }
        if (this.client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR && !this.shouldRenderSpectatorCrosshair(this.client.crosshairTarget)) {
            return;
        }
        if (gameOptions.debugEnabled && !gameOptions.hudHidden && !this.client.player.getReducedDebugInfo() && !gameOptions.reducedDebugInfo) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(this.scaledWidth / 2, this.scaledHeight / 2, this.getZOffset());
            Camera camera = this.client.gameRenderer.getCamera();
            RenderSystem.rotatef(camera.getPitch(), -1.0f, 0.0f, 0.0f);
            RenderSystem.rotatef(camera.getYaw(), 0.0f, 1.0f, 0.0f);
            RenderSystem.scalef(-1.0f, -1.0f, -1.0f);
            RenderSystem.renderCrosshair(10);
            RenderSystem.popMatrix();
        } else {
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
            int n = 15;
            this.drawTexture(matrices, (this.scaledWidth - 15) / 2, (this.scaledHeight - 15) / 2, 0, 0, 15, 15);
            if (this.client.options.attackIndicator == AttackIndicator.CROSSHAIR) {
                float f = this.client.player.getAttackCooldownProgress(0.0f);
                boolean _snowman2 = false;
                if (this.client.targetedEntity != null && this.client.targetedEntity instanceof LivingEntity && f >= 1.0f) {
                    _snowman2 = this.client.player.getAttackCooldownProgressPerTick() > 5.0f;
                    _snowman2 &= this.client.targetedEntity.isAlive();
                }
                int _snowman3 = this.scaledHeight / 2 - 7 + 16;
                int _snowman4 = this.scaledWidth / 2 - 8;
                if (_snowman2) {
                    this.drawTexture(matrices, _snowman4, _snowman3, 68, 94, 16, 16);
                } else if (f < 1.0f) {
                    int n2 = (int)(f * 17.0f);
                    this.drawTexture(matrices, _snowman4, _snowman3, 36, 94, 16, 4);
                    this.drawTexture(matrices, _snowman4, _snowman3, 52, 94, n2, 4);
                }
            }
        }
    }

    private boolean shouldRenderSpectatorCrosshair(HitResult hitResult) {
        if (hitResult == null) {
            return false;
        }
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            return ((EntityHitResult)hitResult).getEntity() instanceof NamedScreenHandlerFactory;
        }
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            ClientWorld clientWorld = this.client.world;
            BlockPos _snowman2 = ((BlockHitResult)hitResult).getBlockPos();
            return clientWorld.getBlockState(_snowman2).createScreenHandlerFactory(clientWorld, _snowman2) != null;
        }
        return false;
    }

    protected void renderStatusEffectOverlay(MatrixStack matrices) {
        Collection<StatusEffectInstance> collection = this.client.player.getStatusEffects();
        if (collection.isEmpty()) {
            return;
        }
        RenderSystem.enableBlend();
        int _snowman2 = 0;
        int _snowman3 = 0;
        StatusEffectSpriteManager _snowman4 = this.client.getStatusEffectSpriteManager();
        ArrayList _snowman5 = Lists.newArrayListWithExpectedSize((int)collection.size());
        this.client.getTextureManager().bindTexture(HandledScreen.BACKGROUND_TEXTURE);
        for (StatusEffectInstance statusEffectInstance : Ordering.natural().reverse().sortedCopy(collection)) {
            StatusEffect statusEffect = statusEffectInstance.getEffectType();
            if (!statusEffectInstance.shouldShowIcon()) continue;
            int _snowman6 = this.scaledWidth;
            int _snowman7 = 1;
            if (this.client.isDemo()) {
                _snowman7 += 15;
            }
            if (statusEffect.isBeneficial()) {
                _snowman6 -= 25 * ++_snowman2;
            } else {
                _snowman6 -= 25 * ++_snowman3;
                _snowman7 += 26;
            }
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            float _snowman8 = 1.0f;
            if (statusEffectInstance.isAmbient()) {
                this.drawTexture(matrices, _snowman6, _snowman7, 165, 166, 24, 24);
            } else {
                this.drawTexture(matrices, _snowman6, _snowman7, 141, 166, 24, 24);
                if (statusEffectInstance.getDuration() <= 200) {
                    int n = 10 - statusEffectInstance.getDuration() / 20;
                    _snowman8 = MathHelper.clamp((float)statusEffectInstance.getDuration() / 10.0f / 5.0f * 0.5f, 0.0f, 0.5f) + MathHelper.cos((float)statusEffectInstance.getDuration() * (float)Math.PI / 5.0f) * MathHelper.clamp((float)n / 10.0f * 0.25f, 0.0f, 0.25f);
                }
            }
            Sprite _snowman9 = _snowman4.getSprite(statusEffect);
            int _snowman10 = _snowman6;
            int _snowman11 = _snowman7;
            float _snowman12 = _snowman8;
            _snowman5.add(() -> {
                this.client.getTextureManager().bindTexture(_snowman9.getAtlas().getId());
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, _snowman12);
                InGameHud.drawSprite(matrices, _snowman10 + 3, _snowman11 + 3, this.getZOffset(), 18, 18, _snowman9);
            });
        }
        _snowman5.forEach(Runnable::run);
    }

    protected void renderHotbar(float tickDelta, MatrixStack matrices) {
        float f;
        int n;
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity == null) {
            return;
        }
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
        ItemStack _snowman2 = playerEntity.getOffHandStack();
        Arm _snowman3 = playerEntity.getMainArm().getOpposite();
        int _snowman4 = this.scaledWidth / 2;
        int _snowman5 = this.getZOffset();
        int _snowman6 = 182;
        int _snowman7 = 91;
        this.setZOffset(-90);
        this.drawTexture(matrices, _snowman4 - 91, this.scaledHeight - 22, 0, 0, 182, 22);
        this.drawTexture(matrices, _snowman4 - 91 - 1 + playerEntity.inventory.selectedSlot * 20, this.scaledHeight - 22 - 1, 0, 22, 24, 22);
        if (!_snowman2.isEmpty()) {
            if (_snowman3 == Arm.LEFT) {
                this.drawTexture(matrices, _snowman4 - 91 - 29, this.scaledHeight - 23, 24, 22, 29, 24);
            } else {
                this.drawTexture(matrices, _snowman4 + 91, this.scaledHeight - 23, 53, 22, 29, 24);
            }
        }
        this.setZOffset(_snowman5);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        for (n = 0; n < 9; ++n) {
            n2 = _snowman4 - 90 + n * 20 + 2;
            _snowman = this.scaledHeight - 16 - 3;
            this.renderHotbarItem(n2, _snowman, tickDelta, playerEntity, playerEntity.inventory.main.get(n));
        }
        if (!_snowman2.isEmpty()) {
            n = this.scaledHeight - 16 - 3;
            if (_snowman3 == Arm.LEFT) {
                this.renderHotbarItem(_snowman4 - 91 - 26, n, tickDelta, playerEntity, _snowman2);
            } else {
                this.renderHotbarItem(_snowman4 + 91 + 10, n, tickDelta, playerEntity, _snowman2);
            }
        }
        if (this.client.options.attackIndicator == AttackIndicator.HOTBAR && (f = this.client.player.getAttackCooldownProgress(0.0f)) < 1.0f) {
            int n2 = this.scaledHeight - 20;
            _snowman = _snowman4 + 91 + 6;
            if (_snowman3 == Arm.RIGHT) {
                _snowman = _snowman4 - 91 - 22;
            }
            this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
            _snowman = (int)(f * 19.0f);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexture(matrices, _snowman, n2, 0, 94, 18, 18);
            this.drawTexture(matrices, _snowman, n2 + 18 - _snowman, 18, 112 - _snowman, 18, _snowman);
        }
        RenderSystem.disableRescaleNormal();
        RenderSystem.disableBlend();
    }

    public void renderMountJumpBar(MatrixStack matrices, int x) {
        this.client.getProfiler().push("jumpBar");
        this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
        float f = this.client.player.method_3151();
        int _snowman2 = 182;
        int _snowman3 = (int)(f * 183.0f);
        int _snowman4 = this.scaledHeight - 32 + 3;
        this.drawTexture(matrices, x, _snowman4, 0, 84, 182, 5);
        if (_snowman3 > 0) {
            this.drawTexture(matrices, x, _snowman4, 0, 89, _snowman3, 5);
        }
        this.client.getProfiler().pop();
    }

    public void renderExperienceBar(MatrixStack matrices, int x) {
        this.client.getProfiler().push("expBar");
        this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
        int n = this.client.player.getNextLevelExperience();
        if (n > 0) {
            _snowman = 182;
            _snowman2 = (int)(this.client.player.experienceProgress * 183.0f);
            _snowman3 = this.scaledHeight - 32 + 3;
            this.drawTexture(matrices, x, _snowman3, 0, 64, 182, 5);
            if (_snowman2 > 0) {
                this.drawTexture(matrices, x, _snowman3, 0, 69, _snowman2, 5);
            }
        }
        this.client.getProfiler().pop();
        if (this.client.player.experienceLevel > 0) {
            this.client.getProfiler().push("expLevel");
            String string = "" + this.client.player.experienceLevel;
            int _snowman2 = (this.scaledWidth - this.getFontRenderer().getWidth(string)) / 2;
            int _snowman3 = this.scaledHeight - 31 - 4;
            this.getFontRenderer().draw(matrices, string, (float)(_snowman2 + 1), (float)_snowman3, 0);
            this.getFontRenderer().draw(matrices, string, (float)(_snowman2 - 1), (float)_snowman3, 0);
            this.getFontRenderer().draw(matrices, string, (float)_snowman2, (float)(_snowman3 + 1), 0);
            this.getFontRenderer().draw(matrices, string, (float)_snowman2, (float)(_snowman3 - 1), 0);
            this.getFontRenderer().draw(matrices, string, (float)_snowman2, (float)_snowman3, 8453920);
            this.client.getProfiler().pop();
        }
    }

    public void renderHeldItemTooltip(MatrixStack matrices) {
        this.client.getProfiler().push("selectedItemName");
        if (this.heldItemTooltipFade > 0 && !this.currentStack.isEmpty()) {
            int n;
            MutableText mutableText = new LiteralText("").append(this.currentStack.getName()).formatted(this.currentStack.getRarity().formatting);
            if (this.currentStack.hasCustomName()) {
                mutableText.formatted(Formatting.ITALIC);
            }
            int _snowman2 = this.getFontRenderer().getWidth(mutableText);
            int _snowman3 = (this.scaledWidth - _snowman2) / 2;
            int _snowman4 = this.scaledHeight - 59;
            if (!this.client.interactionManager.hasStatusBars()) {
                _snowman4 += 14;
            }
            if ((n = (int)((float)this.heldItemTooltipFade * 256.0f / 10.0f)) > 255) {
                n = 255;
            }
            if (n > 0) {
                RenderSystem.pushMatrix();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                InGameHud.fill(matrices, _snowman3 - 2, _snowman4 - 2, _snowman3 + _snowman2 + 2, _snowman4 + this.getFontRenderer().fontHeight + 2, this.client.options.getTextBackgroundColor(0));
                this.getFontRenderer().drawWithShadow(matrices, mutableText, (float)_snowman3, (float)_snowman4, 0xFFFFFF + (n << 24));
                RenderSystem.disableBlend();
                RenderSystem.popMatrix();
            }
        }
        this.client.getProfiler().pop();
    }

    public void renderDemoTimer(MatrixStack matrices) {
        this.client.getProfiler().push("demo");
        Text text = this.client.world.getTime() >= 120500L ? DEMO_EXPIRED_MESSAGE : new TranslatableText("demo.remainingTime", ChatUtil.ticksToString((int)(120500L - this.client.world.getTime())));
        int _snowman2 = this.getFontRenderer().getWidth(text);
        this.getFontRenderer().drawWithShadow(matrices, text, (float)(this.scaledWidth - _snowman2 - 10), 5.0f, 0xFFFFFF);
        this.client.getProfiler().pop();
    }

    private void renderScoreboardSidebar(MatrixStack matrices, ScoreboardObjective objective) {
        Scoreboard scoreboard = objective.getScoreboard();
        List<Object> _snowman2 = scoreboard.getAllPlayerScores(objective);
        List _snowman3 = _snowman2.stream().filter(scoreboardPlayerScore -> scoreboardPlayerScore.getPlayerName() != null && !scoreboardPlayerScore.getPlayerName().startsWith("#")).collect(Collectors.toList());
        _snowman2 = _snowman3.size() > 15 ? Lists.newArrayList((Iterable)Iterables.skip(_snowman3, (int)(_snowman2.size() - 15))) : _snowman3;
        ArrayList _snowman4 = Lists.newArrayListWithCapacity((int)_snowman2.size());
        Text _snowman5 = objective.getDisplayName();
        int _snowman6 = _snowman = this.getFontRenderer().getWidth(_snowman5);
        int _snowman7 = this.getFontRenderer().getWidth(": ");
        for (ScoreboardPlayerScore scoreboardPlayerScore2 : _snowman2) {
            Team team = scoreboard.getPlayerTeam(scoreboardPlayerScore2.getPlayerName());
            MutableText _snowman8 = Team.modifyText(team, new LiteralText(scoreboardPlayerScore2.getPlayerName()));
            _snowman4.add(Pair.of((Object)scoreboardPlayerScore2, (Object)_snowman8));
            _snowman6 = Math.max(_snowman6, this.getFontRenderer().getWidth(_snowman8) + _snowman7 + this.getFontRenderer().getWidth(Integer.toString(scoreboardPlayerScore2.getScore())));
        }
        int n = _snowman2.size() * this.getFontRenderer().fontHeight;
        _snowman = this.scaledHeight / 2 + n / 3;
        _snowman = 3;
        _snowman = this.scaledWidth - _snowman6 - 3;
        _snowman = 0;
        _snowman = this.client.options.getTextBackgroundColor(0.3f);
        _snowman = this.client.options.getTextBackgroundColor(0.4f);
        for (Pair pair : _snowman4) {
            ScoreboardPlayerScore scoreboardPlayerScore3 = (ScoreboardPlayerScore)pair.getFirst();
            Text _snowman9 = (Text)pair.getSecond();
            String _snowman10 = (Object)((Object)Formatting.RED) + "" + scoreboardPlayerScore3.getScore();
            int _snowman11 = _snowman;
            int _snowman12 = _snowman - ++_snowman * this.getFontRenderer().fontHeight;
            int _snowman13 = this.scaledWidth - 3 + 2;
            InGameHud.fill(matrices, _snowman11 - 2, _snowman12, _snowman13, _snowman12 + this.getFontRenderer().fontHeight, _snowman);
            this.getFontRenderer().draw(matrices, _snowman9, (float)_snowman11, (float)_snowman12, -1);
            this.getFontRenderer().draw(matrices, _snowman10, (float)(_snowman13 - this.getFontRenderer().getWidth(_snowman10)), (float)_snowman12, -1);
            if (_snowman != _snowman2.size()) continue;
            InGameHud.fill(matrices, _snowman11 - 2, _snowman12 - this.getFontRenderer().fontHeight - 1, _snowman13, _snowman12 - 1, _snowman);
            InGameHud.fill(matrices, _snowman11 - 2, _snowman12 - 1, _snowman13, _snowman12, _snowman);
            this.getFontRenderer().draw(matrices, _snowman5, (float)(_snowman11 + _snowman6 / 2 - _snowman / 2), (float)(_snowman12 - this.getFontRenderer().fontHeight), -1);
        }
    }

    private PlayerEntity getCameraPlayer() {
        if (!(this.client.getCameraEntity() instanceof PlayerEntity)) {
            return null;
        }
        return (PlayerEntity)this.client.getCameraEntity();
    }

    private LivingEntity getRiddenEntity() {
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity != null) {
            Entity entity = playerEntity.getVehicle();
            if (entity == null) {
                return null;
            }
            if (entity instanceof LivingEntity) {
                return (LivingEntity)entity;
            }
        }
        return null;
    }

    private int getHeartCount(LivingEntity entity) {
        if (entity == null || !entity.isLiving()) {
            return 0;
        }
        float f = entity.getMaxHealth();
        int _snowman2 = (int)(f + 0.5f) / 2;
        if (_snowman2 > 30) {
            _snowman2 = 30;
        }
        return _snowman2;
    }

    private int getHeartRows(int heartCount) {
        return (int)Math.ceil((double)heartCount / 10.0);
    }

    private void renderStatusBars(MatrixStack matrices) {
        int _snowman21;
        int n;
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity == null) {
            return;
        }
        int _snowman2 = MathHelper.ceil(playerEntity.getHealth());
        boolean _snowman3 = this.heartJumpEndTick > (long)this.ticks && (this.heartJumpEndTick - (long)this.ticks) / 3L % 2L == 1L;
        long _snowman4 = Util.getMeasuringTimeMs();
        if (_snowman2 < this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
            this.lastHealthCheckTime = _snowman4;
            this.heartJumpEndTick = this.ticks + 20;
        } else if (_snowman2 > this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
            this.lastHealthCheckTime = _snowman4;
            this.heartJumpEndTick = this.ticks + 10;
        }
        if (_snowman4 - this.lastHealthCheckTime > 1000L) {
            this.lastHealthValue = _snowman2;
            this.renderHealthValue = _snowman2;
            this.lastHealthCheckTime = _snowman4;
        }
        this.lastHealthValue = _snowman2;
        int _snowman5 = this.renderHealthValue;
        this.random.setSeed(this.ticks * 312871);
        HungerManager _snowman6 = playerEntity.getHungerManager();
        int _snowman7 = _snowman6.getFoodLevel();
        int _snowman8 = this.scaledWidth / 2 - 91;
        int _snowman9 = this.scaledWidth / 2 + 91;
        int _snowman10 = this.scaledHeight - 39;
        float _snowman11 = (float)playerEntity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
        int _snowman12 = MathHelper.ceil(playerEntity.getAbsorptionAmount());
        int _snowman13 = MathHelper.ceil((_snowman11 + (float)_snowman12) / 2.0f / 10.0f);
        int _snowman14 = Math.max(10 - (_snowman13 - 2), 3);
        int _snowman15 = _snowman10 - (_snowman13 - 1) * _snowman14 - 10;
        int _snowman16 = _snowman10 - 10;
        int _snowman17 = _snowman12;
        int _snowman18 = playerEntity.getArmor();
        int _snowman19 = -1;
        if (playerEntity.hasStatusEffect(StatusEffects.REGENERATION)) {
            _snowman19 = this.ticks % MathHelper.ceil(_snowman11 + 5.0f);
        }
        this.client.getProfiler().push("armor");
        for (n = 0; n < 10; ++n) {
            if (_snowman18 <= 0) continue;
            _snowman20 = _snowman8 + n * 8;
            if (n * 2 + 1 < _snowman18) {
                this.drawTexture(matrices, _snowman20, _snowman15, 34, 9, 9, 9);
            }
            if (n * 2 + 1 == _snowman18) {
                this.drawTexture(matrices, _snowman20, _snowman15, 25, 9, 9, 9);
            }
            if (n * 2 + 1 <= _snowman18) continue;
            this.drawTexture(matrices, _snowman20, _snowman15, 16, 9, 9, 9);
        }
        this.client.getProfiler().swap("health");
        for (n = MathHelper.ceil((_snowman11 + (float)_snowman12) / 2.0f) - 1; n >= 0; --n) {
            _snowman20 = 16;
            if (playerEntity.hasStatusEffect(StatusEffects.POISON)) {
                _snowman20 += 36;
            } else if (playerEntity.hasStatusEffect(StatusEffects.WITHER)) {
                _snowman20 += 72;
            }
            _snowman21 = 0;
            if (_snowman3) {
                _snowman21 = 1;
            }
            _snowman22 = MathHelper.ceil((float)(n + 1) / 10.0f) - 1;
            n2 = _snowman8 + n % 10 * 8;
            _snowman = _snowman10 - _snowman22 * _snowman14;
            if (_snowman2 <= 4) {
                _snowman += this.random.nextInt(2);
            }
            if (_snowman17 <= 0 && n == _snowman19) {
                _snowman -= 2;
            }
            _snowman = 0;
            if (playerEntity.world.getLevelProperties().isHardcore()) {
                _snowman = 5;
            }
            this.drawTexture(matrices, n2, _snowman, 16 + _snowman21 * 9, 9 * _snowman, 9, 9);
            if (_snowman3) {
                if (n * 2 + 1 < _snowman5) {
                    this.drawTexture(matrices, n2, _snowman, _snowman20 + 54, 9 * _snowman, 9, 9);
                }
                if (n * 2 + 1 == _snowman5) {
                    this.drawTexture(matrices, n2, _snowman, _snowman20 + 63, 9 * _snowman, 9, 9);
                }
            }
            if (_snowman17 > 0) {
                if (_snowman17 == _snowman12 && _snowman12 % 2 == 1) {
                    this.drawTexture(matrices, n2, _snowman, _snowman20 + 153, 9 * _snowman, 9, 9);
                    --_snowman17;
                    continue;
                }
                this.drawTexture(matrices, n2, _snowman, _snowman20 + 144, 9 * _snowman, 9, 9);
                _snowman17 -= 2;
                continue;
            }
            if (n * 2 + 1 < _snowman2) {
                this.drawTexture(matrices, n2, _snowman, _snowman20 + 36, 9 * _snowman, 9, 9);
            }
            if (n * 2 + 1 != _snowman2) continue;
            this.drawTexture(matrices, n2, _snowman, _snowman20 + 45, 9 * _snowman, 9, 9);
        }
        LivingEntity livingEntity = this.getRiddenEntity();
        int _snowman20 = this.getHeartCount(livingEntity);
        if (_snowman20 == 0) {
            this.client.getProfiler().swap("food");
            for (_snowman21 = 0; _snowman21 < 10; ++_snowman21) {
                _snowman22 = _snowman10;
                n2 = 16;
                _snowman = 0;
                if (playerEntity.hasStatusEffect(StatusEffects.HUNGER)) {
                    n2 += 36;
                    _snowman = 13;
                }
                if (playerEntity.getHungerManager().getSaturationLevel() <= 0.0f && this.ticks % (_snowman7 * 3 + 1) == 0) {
                    _snowman22 += this.random.nextInt(3) - 1;
                }
                _snowman = _snowman9 - _snowman21 * 8 - 9;
                this.drawTexture(matrices, _snowman, _snowman22, 16 + _snowman * 9, 27, 9, 9);
                if (_snowman21 * 2 + 1 < _snowman7) {
                    this.drawTexture(matrices, _snowman, _snowman22, n2 + 36, 27, 9, 9);
                }
                if (_snowman21 * 2 + 1 != _snowman7) continue;
                this.drawTexture(matrices, _snowman, _snowman22, n2 + 45, 27, 9, 9);
            }
            _snowman16 -= 10;
        }
        this.client.getProfiler().swap("air");
        _snowman21 = playerEntity.getMaxAir();
        int _snowman22 = Math.min(playerEntity.getAir(), _snowman21);
        if (playerEntity.isSubmergedIn(FluidTags.WATER) || _snowman22 < _snowman21) {
            int n2 = this.getHeartRows(_snowman20) - 1;
            _snowman16 -= n2 * 10;
            _snowman = MathHelper.ceil((double)(_snowman22 - 2) * 10.0 / (double)_snowman21);
            _snowman = MathHelper.ceil((double)_snowman22 * 10.0 / (double)_snowman21) - _snowman;
            for (_snowman = 0; _snowman < _snowman + _snowman; ++_snowman) {
                if (_snowman < _snowman) {
                    this.drawTexture(matrices, _snowman9 - _snowman * 8 - 9, _snowman16, 16, 18, 9, 9);
                    continue;
                }
                this.drawTexture(matrices, _snowman9 - _snowman * 8 - 9, _snowman16, 25, 18, 9, 9);
            }
        }
        this.client.getProfiler().pop();
    }

    private void renderMountHealth(MatrixStack matrices) {
        LivingEntity livingEntity = this.getRiddenEntity();
        if (livingEntity == null) {
            return;
        }
        int _snowman2 = this.getHeartCount(livingEntity);
        if (_snowman2 == 0) {
            return;
        }
        int _snowman3 = (int)Math.ceil(livingEntity.getHealth());
        this.client.getProfiler().swap("mountHealth");
        int _snowman4 = this.scaledHeight - 39;
        int _snowman5 = this.scaledWidth / 2 + 91;
        int _snowman6 = _snowman4;
        int _snowman7 = 0;
        boolean _snowman8 = false;
        while (_snowman2 > 0) {
            int n = Math.min(_snowman2, 10);
            _snowman2 -= n;
            for (_snowman = 0; _snowman < n; ++_snowman) {
                _snowman = 52;
                _snowman = 0;
                _snowman = _snowman5 - _snowman * 8 - 9;
                this.drawTexture(matrices, _snowman, _snowman6, 52 + _snowman * 9, 9, 9, 9);
                if (_snowman * 2 + 1 + _snowman7 < _snowman3) {
                    this.drawTexture(matrices, _snowman, _snowman6, 88, 9, 9, 9);
                }
                if (_snowman * 2 + 1 + _snowman7 != _snowman3) continue;
                this.drawTexture(matrices, _snowman, _snowman6, 97, 9, 9, 9);
            }
            _snowman6 -= 10;
            _snowman7 += 20;
        }
    }

    private void renderPumpkinOverlay() {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableAlphaTest();
        this.client.getTextureManager().bindTexture(PUMPKIN_BLUR);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        _snowman2.begin(7, VertexFormats.POSITION_TEXTURE);
        _snowman2.vertex(0.0, this.scaledHeight, -90.0).texture(0.0f, 1.0f).next();
        _snowman2.vertex(this.scaledWidth, this.scaledHeight, -90.0).texture(1.0f, 1.0f).next();
        _snowman2.vertex(this.scaledWidth, 0.0, -90.0).texture(1.0f, 0.0f).next();
        _snowman2.vertex(0.0, 0.0, -90.0).texture(0.0f, 0.0f).next();
        tessellator.draw();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void updateVignetteDarkness(Entity entity) {
        if (entity == null) {
            return;
        }
        float f = MathHelper.clamp(1.0f - entity.getBrightnessAtEyes(), 0.0f, 1.0f);
        this.vignetteDarkness = (float)((double)this.vignetteDarkness + (double)(f - this.vignetteDarkness) * 0.01);
    }

    private void renderVignetteOverlay(Entity entity) {
        WorldBorder worldBorder = this.client.world.getWorldBorder();
        float _snowman2 = (float)worldBorder.getDistanceInsideBorder(entity);
        double _snowman3 = Math.min(worldBorder.getShrinkingSpeed() * (double)worldBorder.getWarningTime() * 1000.0, Math.abs(worldBorder.getTargetSize() - worldBorder.getSize()));
        double _snowman4 = Math.max((double)worldBorder.getWarningBlocks(), _snowman3);
        _snowman2 = (double)_snowman2 < _snowman4 ? 1.0f - (float)((double)_snowman2 / _snowman4) : 0.0f;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        if (_snowman2 > 0.0f) {
            RenderSystem.color4f(0.0f, _snowman2, _snowman2, 1.0f);
        } else {
            RenderSystem.color4f(this.vignetteDarkness, this.vignetteDarkness, this.vignetteDarkness, 1.0f);
        }
        this.client.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
        Tessellator _snowman5 = Tessellator.getInstance();
        BufferBuilder _snowman6 = _snowman5.getBuffer();
        _snowman6.begin(7, VertexFormats.POSITION_TEXTURE);
        _snowman6.vertex(0.0, this.scaledHeight, -90.0).texture(0.0f, 1.0f).next();
        _snowman6.vertex(this.scaledWidth, this.scaledHeight, -90.0).texture(1.0f, 1.0f).next();
        _snowman6.vertex(this.scaledWidth, 0.0, -90.0).texture(1.0f, 0.0f).next();
        _snowman6.vertex(0.0, 0.0, -90.0).texture(0.0f, 0.0f).next();
        _snowman5.draw();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
    }

    private void renderPortalOverlay(float nauseaStrength) {
        if (nauseaStrength < 1.0f) {
            nauseaStrength *= nauseaStrength;
            nauseaStrength *= nauseaStrength;
            nauseaStrength = nauseaStrength * 0.8f + 0.2f;
        }
        RenderSystem.disableAlphaTest();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, nauseaStrength);
        this.client.getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
        Sprite sprite = this.client.getBlockRenderManager().getModels().getSprite(Blocks.NETHER_PORTAL.getDefaultState());
        float _snowman2 = sprite.getMinU();
        float _snowman3 = sprite.getMinV();
        float _snowman4 = sprite.getMaxU();
        float _snowman5 = sprite.getMaxV();
        Tessellator _snowman6 = Tessellator.getInstance();
        BufferBuilder _snowman7 = _snowman6.getBuffer();
        _snowman7.begin(7, VertexFormats.POSITION_TEXTURE);
        _snowman7.vertex(0.0, this.scaledHeight, -90.0).texture(_snowman2, _snowman5).next();
        _snowman7.vertex(this.scaledWidth, this.scaledHeight, -90.0).texture(_snowman4, _snowman5).next();
        _snowman7.vertex(this.scaledWidth, 0.0, -90.0).texture(_snowman4, _snowman3).next();
        _snowman7.vertex(0.0, 0.0, -90.0).texture(_snowman2, _snowman3).next();
        _snowman6.draw();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderHotbarItem(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }
        float f = (float)stack.getCooldown() - tickDelta;
        if (f > 0.0f) {
            RenderSystem.pushMatrix();
            _snowman = 1.0f + f / 5.0f;
            RenderSystem.translatef(x + 8, y + 12, 0.0f);
            RenderSystem.scalef(1.0f / _snowman, (_snowman + 1.0f) / 2.0f, 1.0f);
            RenderSystem.translatef(-(x + 8), -(y + 12), 0.0f);
        }
        this.itemRenderer.renderInGuiWithOverrides(player, stack, x, y);
        if (f > 0.0f) {
            RenderSystem.popMatrix();
        }
        this.itemRenderer.renderGuiItemOverlay(this.client.textRenderer, stack, x, y);
    }

    public void tick() {
        if (this.overlayRemaining > 0) {
            --this.overlayRemaining;
        }
        if (this.titleTotalTicks > 0) {
            --this.titleTotalTicks;
            if (this.titleTotalTicks <= 0) {
                this.title = null;
                this.subtitle = null;
            }
        }
        ++this.ticks;
        Entity entity = this.client.getCameraEntity();
        if (entity != null) {
            this.updateVignetteDarkness(entity);
        }
        if (this.client.player != null) {
            ItemStack itemStack = this.client.player.inventory.getMainHandStack();
            if (itemStack.isEmpty()) {
                this.heldItemTooltipFade = 0;
            } else if (this.currentStack.isEmpty() || itemStack.getItem() != this.currentStack.getItem() || !itemStack.getName().equals(this.currentStack.getName())) {
                this.heldItemTooltipFade = 40;
            } else if (this.heldItemTooltipFade > 0) {
                --this.heldItemTooltipFade;
            }
            this.currentStack = itemStack;
        }
    }

    public void setRecordPlayingOverlay(Text text) {
        this.setOverlayMessage(new TranslatableText("record.nowPlaying", text), true);
    }

    public void setOverlayMessage(Text message, boolean tinted) {
        this.overlayMessage = message;
        this.overlayRemaining = 60;
        this.overlayTinted = tinted;
    }

    public void setTitles(@Nullable Text title, @Nullable Text subtitle, int titleFadeInTicks, int titleRemainTicks, int titleFadeOutTicks) {
        if (title == null && subtitle == null && titleFadeInTicks < 0 && titleRemainTicks < 0 && titleFadeOutTicks < 0) {
            this.title = null;
            this.subtitle = null;
            this.titleTotalTicks = 0;
            return;
        }
        if (title != null) {
            this.title = title;
            this.titleTotalTicks = this.titleFadeInTicks + this.titleRemainTicks + this.titleFadeOutTicks;
            return;
        }
        if (subtitle != null) {
            this.subtitle = subtitle;
            return;
        }
        if (titleFadeInTicks >= 0) {
            this.titleFadeInTicks = titleFadeInTicks;
        }
        if (titleRemainTicks >= 0) {
            this.titleRemainTicks = titleRemainTicks;
        }
        if (titleFadeOutTicks >= 0) {
            this.titleFadeOutTicks = titleFadeOutTicks;
        }
        if (this.titleTotalTicks > 0) {
            this.titleTotalTicks = this.titleFadeInTicks + this.titleRemainTicks + this.titleFadeOutTicks;
        }
    }

    public UUID method_31406(Text text) {
        String string = TextVisitFactory.method_31402(text);
        _snowman = StringUtils.substringBetween((String)string, (String)"<", (String)">");
        if (_snowman == null) {
            return Util.NIL_UUID;
        }
        return this.client.getSocialInteractionsManager().method_31407(_snowman);
    }

    public void addChatMessage(MessageType type, Text text, UUID senderUuid) {
        if (this.client.shouldBlockMessages(senderUuid)) {
            return;
        }
        if (this.client.options.field_26926 && this.client.shouldBlockMessages(this.method_31406(text))) {
            return;
        }
        for (ClientChatListener clientChatListener : this.listeners.get((Object)type)) {
            clientChatListener.onChatMessage(type, text, senderUuid);
        }
    }

    public ChatHud getChatHud() {
        return this.chatHud;
    }

    public int getTicks() {
        return this.ticks;
    }

    public TextRenderer getFontRenderer() {
        return this.client.textRenderer;
    }

    public SpectatorHud getSpectatorHud() {
        return this.spectatorHud;
    }

    public PlayerListHud getPlayerListWidget() {
        return this.playerListHud;
    }

    public void clear() {
        this.playerListHud.clear();
        this.bossBarHud.clear();
        this.client.getToastManager().clear();
    }

    public BossBarHud getBossBarHud() {
        return this.bossBarHud;
    }

    public void resetDebugHudChunk() {
        this.debugHud.resetChunk();
    }
}

