package net.minecraft.client.gui.hud;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
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
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import org.apache.commons.lang3.StringUtils;

public class InGameHud extends DrawableHelper {
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
   public float vignetteDarkness = 1.0F;
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

      for (MessageType _snowman : MessageType.values()) {
         this.listeners.put(_snowman, Lists.newArrayList());
      }

      ClientChatListener _snowman = NarratorManager.INSTANCE;
      this.listeners.get(MessageType.CHAT).add(new ChatHudListener(client));
      this.listeners.get(MessageType.CHAT).add(_snowman);
      this.listeners.get(MessageType.SYSTEM).add(new ChatHudListener(client));
      this.listeners.get(MessageType.SYSTEM).add(_snowman);
      this.listeners.get(MessageType.GAME_INFO).add(new GameInfoChatListener(client));
      this.setDefaultTitleFade();
   }

   public void setDefaultTitleFade() {
      this.titleFadeInTicks = 10;
      this.titleRemainTicks = 70;
      this.titleFadeOutTicks = 20;
   }

   public void render(MatrixStack matrices, float tickDelta) {
      this.scaledWidth = this.client.getWindow().getScaledWidth();
      this.scaledHeight = this.client.getWindow().getScaledHeight();
      TextRenderer _snowman = this.getFontRenderer();
      RenderSystem.enableBlend();
      if (MinecraftClient.isFancyGraphicsOrBetter()) {
         this.renderVignetteOverlay(this.client.getCameraEntity());
      } else {
         RenderSystem.enableDepthTest();
         RenderSystem.defaultBlendFunc();
      }

      ItemStack _snowmanx = this.client.player.inventory.getArmorStack(3);
      if (this.client.options.getPerspective().isFirstPerson() && _snowmanx.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
         this.renderPumpkinOverlay();
      }

      float _snowmanxx = MathHelper.lerp(tickDelta, this.client.player.lastNauseaStrength, this.client.player.nextNauseaStrength);
      if (_snowmanxx > 0.0F && !this.client.player.hasStatusEffect(StatusEffects.NAUSEA)) {
         this.renderPortalOverlay(_snowmanxx);
      }

      if (this.client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
         this.spectatorHud.render(matrices, tickDelta);
      } else if (!this.client.options.hudHidden) {
         this.renderHotbar(tickDelta, matrices);
      }

      if (!this.client.options.hudHidden) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
         RenderSystem.enableBlend();
         RenderSystem.enableAlphaTest();
         this.renderCrosshair(matrices);
         RenderSystem.defaultBlendFunc();
         this.client.getProfiler().push("bossHealth");
         this.bossBarHud.render(matrices);
         this.client.getProfiler().pop();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
         if (this.client.interactionManager.hasStatusBars()) {
            this.renderStatusBars(matrices);
         }

         this.renderMountHealth(matrices);
         RenderSystem.disableBlend();
         int _snowmanxxx = this.scaledWidth / 2 - 91;
         if (this.client.player.hasJumpingMount()) {
            this.renderMountJumpBar(matrices, _snowmanxxx);
         } else if (this.client.interactionManager.hasExperienceBar()) {
            this.renderExperienceBar(matrices, _snowmanxxx);
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
         float _snowmanxxxx = (float)this.client.player.getSleepTimer();
         float _snowmanxxxxx = _snowmanxxxx / 100.0F;
         if (_snowmanxxxxx > 1.0F) {
            _snowmanxxxxx = 1.0F - (_snowmanxxxx - 100.0F) / 10.0F;
         }

         int _snowmanxxxxxx = (int)(220.0F * _snowmanxxxxx) << 24 | 1052704;
         fill(matrices, 0, 0, this.scaledWidth, this.scaledHeight, _snowmanxxxxxx);
         RenderSystem.enableAlphaTest();
         RenderSystem.enableDepthTest();
         this.client.getProfiler().pop();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
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
            float _snowmanxxxx = (float)this.overlayRemaining - tickDelta;
            int _snowmanxxxxx = (int)(_snowmanxxxx * 255.0F / 20.0F);
            if (_snowmanxxxxx > 255) {
               _snowmanxxxxx = 255;
            }

            if (_snowmanxxxxx > 8) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.scaledWidth / 2), (float)(this.scaledHeight - 68), 0.0F);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               int _snowmanxxxxxx = 16777215;
               if (this.overlayTinted) {
                  _snowmanxxxxxx = MathHelper.hsvToRgb(_snowmanxxxx / 50.0F, 0.7F, 0.6F) & 16777215;
               }

               int _snowmanxxxxxxx = _snowmanxxxxx << 24 & 0xFF000000;
               int _snowmanxxxxxxxx = _snowman.getWidth(this.overlayMessage);
               this.drawTextBackground(matrices, _snowman, -4, _snowmanxxxxxxxx, 16777215 | _snowmanxxxxxxx);
               _snowman.draw(matrices, this.overlayMessage, (float)(-_snowmanxxxxxxxx / 2), -4.0F, _snowmanxxxxxx | _snowmanxxxxxxx);
               RenderSystem.disableBlend();
               RenderSystem.popMatrix();
            }

            this.client.getProfiler().pop();
         }

         if (this.title != null && this.titleTotalTicks > 0) {
            this.client.getProfiler().push("titleAndSubtitle");
            float _snowmanxxxxxx = (float)this.titleTotalTicks - tickDelta;
            int _snowmanxxxxxxx = 255;
            if (this.titleTotalTicks > this.titleFadeOutTicks + this.titleRemainTicks) {
               float _snowmanxxxxxxxx = (float)(this.titleFadeInTicks + this.titleRemainTicks + this.titleFadeOutTicks) - _snowmanxxxxxx;
               _snowmanxxxxxxx = (int)(_snowmanxxxxxxxx * 255.0F / (float)this.titleFadeInTicks);
            }

            if (this.titleTotalTicks <= this.titleFadeOutTicks) {
               _snowmanxxxxxxx = (int)(_snowmanxxxxxx * 255.0F / (float)this.titleFadeOutTicks);
            }

            _snowmanxxxxxxx = MathHelper.clamp(_snowmanxxxxxxx, 0, 255);
            if (_snowmanxxxxxxx > 8) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.scaledWidth / 2), (float)(this.scaledHeight / 2), 0.0F);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.pushMatrix();
               RenderSystem.scalef(4.0F, 4.0F, 4.0F);
               int _snowmanxxxxxxxx = _snowmanxxxxxxx << 24 & 0xFF000000;
               int _snowmanxxxxxxxxx = _snowman.getWidth(this.title);
               this.drawTextBackground(matrices, _snowman, -10, _snowmanxxxxxxxxx, 16777215 | _snowmanxxxxxxxx);
               _snowman.drawWithShadow(matrices, this.title, (float)(-_snowmanxxxxxxxxx / 2), -10.0F, 16777215 | _snowmanxxxxxxxx);
               RenderSystem.popMatrix();
               if (this.subtitle != null) {
                  RenderSystem.pushMatrix();
                  RenderSystem.scalef(2.0F, 2.0F, 2.0F);
                  int _snowmanxxxxxxxxxx = _snowman.getWidth(this.subtitle);
                  this.drawTextBackground(matrices, _snowman, 5, _snowmanxxxxxxxxxx, 16777215 | _snowmanxxxxxxxx);
                  _snowman.drawWithShadow(matrices, this.subtitle, (float)(-_snowmanxxxxxxxxxx / 2), 5.0F, 16777215 | _snowmanxxxxxxxx);
                  RenderSystem.popMatrix();
               }

               RenderSystem.disableBlend();
               RenderSystem.popMatrix();
            }

            this.client.getProfiler().pop();
         }

         this.subtitlesHud.render(matrices);
         Scoreboard _snowmanxxxxxxxx = this.client.world.getScoreboard();
         ScoreboardObjective _snowmanxxxxxxxxx = null;
         Team _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getPlayerTeam(this.client.player.getEntityName());
         if (_snowmanxxxxxxxxxx != null) {
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getColor().getColorIndex();
            if (_snowmanxxxxxxxxxxx >= 0) {
               _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getObjectiveForSlot(3 + _snowmanxxxxxxxxxxx);
            }
         }

         ScoreboardObjective _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx != null ? _snowmanxxxxxxxxx : _snowmanxxxxxxxx.getObjectiveForSlot(1);
         if (_snowmanxxxxxxxxxxx != null) {
            this.renderScoreboardSidebar(matrices, _snowmanxxxxxxxxxxx);
         }

         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableAlphaTest();
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, (float)(this.scaledHeight - 48), 0.0F);
         this.client.getProfiler().push("chat");
         this.chatHud.render(matrices, this.ticks);
         this.client.getProfiler().pop();
         RenderSystem.popMatrix();
         _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx.getObjectiveForSlot(0);
         if (!this.client.options.keyPlayerList.isPressed()
            || this.client.isInSingleplayer() && this.client.player.networkHandler.getPlayerList().size() <= 1 && _snowmanxxxxxxxxxxx == null) {
            this.playerListHud.tick(false);
         } else {
            this.playerListHud.tick(true);
            this.playerListHud.render(matrices, this.scaledWidth, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx);
         }
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableAlphaTest();
   }

   private void drawTextBackground(MatrixStack matrices, TextRenderer textRenderer, int yOffset, int width, int color) {
      int _snowman = this.client.options.getTextBackgroundColor(0.0F);
      if (_snowman != 0) {
         int _snowmanx = -width / 2;
         fill(matrices, _snowmanx - 2, yOffset - 2, _snowmanx + width + 2, yOffset + 9 + 2, BackgroundHelper.ColorMixer.mixColor(_snowman, color));
      }
   }

   private void renderCrosshair(MatrixStack matrices) {
      GameOptions _snowman = this.client.options;
      if (_snowman.getPerspective().isFirstPerson()) {
         if (this.client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR || this.shouldRenderSpectatorCrosshair(this.client.crosshairTarget)) {
            if (_snowman.debugEnabled && !_snowman.hudHidden && !this.client.player.getReducedDebugInfo() && !_snowman.reducedDebugInfo) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.scaledWidth / 2), (float)(this.scaledHeight / 2), (float)this.getZOffset());
               Camera _snowmanx = this.client.gameRenderer.getCamera();
               RenderSystem.rotatef(_snowmanx.getPitch(), -1.0F, 0.0F, 0.0F);
               RenderSystem.rotatef(_snowmanx.getYaw(), 0.0F, 1.0F, 0.0F);
               RenderSystem.scalef(-1.0F, -1.0F, -1.0F);
               RenderSystem.renderCrosshair(10);
               RenderSystem.popMatrix();
            } else {
               RenderSystem.blendFuncSeparate(
                  GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR,
                  GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR,
                  GlStateManager.SrcFactor.ONE,
                  GlStateManager.DstFactor.ZERO
               );
               int _snowmanx = 15;
               this.drawTexture(matrices, (this.scaledWidth - 15) / 2, (this.scaledHeight - 15) / 2, 0, 0, 15, 15);
               if (this.client.options.attackIndicator == AttackIndicator.CROSSHAIR) {
                  float _snowmanxx = this.client.player.getAttackCooldownProgress(0.0F);
                  boolean _snowmanxxx = false;
                  if (this.client.targetedEntity != null && this.client.targetedEntity instanceof LivingEntity && _snowmanxx >= 1.0F) {
                     _snowmanxxx = this.client.player.getAttackCooldownProgressPerTick() > 5.0F;
                     _snowmanxxx &= this.client.targetedEntity.isAlive();
                  }

                  int _snowmanxxxx = this.scaledHeight / 2 - 7 + 16;
                  int _snowmanxxxxx = this.scaledWidth / 2 - 8;
                  if (_snowmanxxx) {
                     this.drawTexture(matrices, _snowmanxxxxx, _snowmanxxxx, 68, 94, 16, 16);
                  } else if (_snowmanxx < 1.0F) {
                     int _snowmanxxxxxx = (int)(_snowmanxx * 17.0F);
                     this.drawTexture(matrices, _snowmanxxxxx, _snowmanxxxx, 36, 94, 16, 4);
                     this.drawTexture(matrices, _snowmanxxxxx, _snowmanxxxx, 52, 94, _snowmanxxxxxx, 4);
                  }
               }
            }
         }
      }
   }

   private boolean shouldRenderSpectatorCrosshair(HitResult hitResult) {
      if (hitResult == null) {
         return false;
      } else if (hitResult.getType() == HitResult.Type.ENTITY) {
         return ((EntityHitResult)hitResult).getEntity() instanceof NamedScreenHandlerFactory;
      } else if (hitResult.getType() == HitResult.Type.BLOCK) {
         BlockPos _snowman = ((BlockHitResult)hitResult).getBlockPos();
         World _snowmanx = this.client.world;
         return _snowmanx.getBlockState(_snowman).createScreenHandlerFactory(_snowmanx, _snowman) != null;
      } else {
         return false;
      }
   }

   protected void renderStatusEffectOverlay(MatrixStack matrices) {
      Collection<StatusEffectInstance> _snowman = this.client.player.getStatusEffects();
      if (!_snowman.isEmpty()) {
         RenderSystem.enableBlend();
         int _snowmanx = 0;
         int _snowmanxx = 0;
         StatusEffectSpriteManager _snowmanxxx = this.client.getStatusEffectSpriteManager();
         List<Runnable> _snowmanxxxx = Lists.newArrayListWithExpectedSize(_snowman.size());
         this.client.getTextureManager().bindTexture(HandledScreen.BACKGROUND_TEXTURE);

         for (StatusEffectInstance _snowmanxxxxx : Ordering.natural().reverse().sortedCopy(_snowman)) {
            StatusEffect _snowmanxxxxxx = _snowmanxxxxx.getEffectType();
            if (_snowmanxxxxx.shouldShowIcon()) {
               int _snowmanxxxxxxx = this.scaledWidth;
               int _snowmanxxxxxxxx = 1;
               if (this.client.isDemo()) {
                  _snowmanxxxxxxxx += 15;
               }

               if (_snowmanxxxxxx.isBeneficial()) {
                  _snowmanx++;
                  _snowmanxxxxxxx -= 25 * _snowmanx;
               } else {
                  _snowmanxx++;
                  _snowmanxxxxxxx -= 25 * _snowmanxx;
                  _snowmanxxxxxxxx += 26;
               }

               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               float _snowmanxxxxxxxxx = 1.0F;
               if (_snowmanxxxxx.isAmbient()) {
                  this.drawTexture(matrices, _snowmanxxxxxxx, _snowmanxxxxxxxx, 165, 166, 24, 24);
               } else {
                  this.drawTexture(matrices, _snowmanxxxxxxx, _snowmanxxxxxxxx, 141, 166, 24, 24);
                  if (_snowmanxxxxx.getDuration() <= 200) {
                     int _snowmanxxxxxxxxxx = 10 - _snowmanxxxxx.getDuration() / 20;
                     _snowmanxxxxxxxxx = MathHelper.clamp((float)_snowmanxxxxx.getDuration() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                        + MathHelper.cos((float)_snowmanxxxxx.getDuration() * (float) Math.PI / 5.0F)
                           * MathHelper.clamp((float)_snowmanxxxxxxxxxx / 10.0F * 0.25F, 0.0F, 0.25F);
                  }
               }

               Sprite _snowmanxxxxxxxxxx = _snowmanxxx.getSprite(_snowmanxxxxxx);
               int _snowmanxxxxxxxxxxx = _snowmanxxxxxxx;
               int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxx;
               float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx;
               _snowmanxxxx.add(() -> {
                  this.client.getTextureManager().bindTexture(_snowman.getAtlas().getId());
                  RenderSystem.color4f(1.0F, 1.0F, 1.0F, _snowman);
                  drawSprite(matrices, _snowman + 3, _snowman + 3, this.getZOffset(), 18, 18, _snowman);
               });
            }
         }

         _snowmanxxxx.forEach(Runnable::run);
      }
   }

   protected void renderHotbar(float tickDelta, MatrixStack matrices) {
      PlayerEntity _snowman = this.getCameraPlayer();
      if (_snowman != null) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
         ItemStack _snowmanx = _snowman.getOffHandStack();
         Arm _snowmanxx = _snowman.getMainArm().getOpposite();
         int _snowmanxxx = this.scaledWidth / 2;
         int _snowmanxxxx = this.getZOffset();
         int _snowmanxxxxx = 182;
         int _snowmanxxxxxx = 91;
         this.setZOffset(-90);
         this.drawTexture(matrices, _snowmanxxx - 91, this.scaledHeight - 22, 0, 0, 182, 22);
         this.drawTexture(matrices, _snowmanxxx - 91 - 1 + _snowman.inventory.selectedSlot * 20, this.scaledHeight - 22 - 1, 0, 22, 24, 22);
         if (!_snowmanx.isEmpty()) {
            if (_snowmanxx == Arm.LEFT) {
               this.drawTexture(matrices, _snowmanxxx - 91 - 29, this.scaledHeight - 23, 24, 22, 29, 24);
            } else {
               this.drawTexture(matrices, _snowmanxxx + 91, this.scaledHeight - 23, 53, 22, 29, 24);
            }
         }

         this.setZOffset(_snowmanxxxx);
         RenderSystem.enableRescaleNormal();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 9; _snowmanxxxxxxx++) {
            int _snowmanxxxxxxxx = _snowmanxxx - 90 + _snowmanxxxxxxx * 20 + 2;
            int _snowmanxxxxxxxxx = this.scaledHeight - 16 - 3;
            this.renderHotbarItem(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, tickDelta, _snowman, _snowman.inventory.main.get(_snowmanxxxxxxx));
         }

         if (!_snowmanx.isEmpty()) {
            int _snowmanxxxxxxx = this.scaledHeight - 16 - 3;
            if (_snowmanxx == Arm.LEFT) {
               this.renderHotbarItem(_snowmanxxx - 91 - 26, _snowmanxxxxxxx, tickDelta, _snowman, _snowmanx);
            } else {
               this.renderHotbarItem(_snowmanxxx + 91 + 10, _snowmanxxxxxxx, tickDelta, _snowman, _snowmanx);
            }
         }

         if (this.client.options.attackIndicator == AttackIndicator.HOTBAR) {
            float _snowmanxxxxxxx = this.client.player.getAttackCooldownProgress(0.0F);
            if (_snowmanxxxxxxx < 1.0F) {
               int _snowmanxxxxxxxx = this.scaledHeight - 20;
               int _snowmanxxxxxxxxx = _snowmanxxx + 91 + 6;
               if (_snowmanxx == Arm.RIGHT) {
                  _snowmanxxxxxxxxx = _snowmanxxx - 91 - 22;
               }

               this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
               int _snowmanxxxxxxxxxx = (int)(_snowmanxxxxxxx * 19.0F);
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               this.drawTexture(matrices, _snowmanxxxxxxxxx, _snowmanxxxxxxxx, 0, 94, 18, 18);
               this.drawTexture(matrices, _snowmanxxxxxxxxx, _snowmanxxxxxxxx + 18 - _snowmanxxxxxxxxxx, 18, 112 - _snowmanxxxxxxxxxx, 18, _snowmanxxxxxxxxxx);
            }
         }

         RenderSystem.disableRescaleNormal();
         RenderSystem.disableBlend();
      }
   }

   public void renderMountJumpBar(MatrixStack matrices, int x) {
      this.client.getProfiler().push("jumpBar");
      this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
      float _snowman = this.client.player.method_3151();
      int _snowmanx = 182;
      int _snowmanxx = (int)(_snowman * 183.0F);
      int _snowmanxxx = this.scaledHeight - 32 + 3;
      this.drawTexture(matrices, x, _snowmanxxx, 0, 84, 182, 5);
      if (_snowmanxx > 0) {
         this.drawTexture(matrices, x, _snowmanxxx, 0, 89, _snowmanxx, 5);
      }

      this.client.getProfiler().pop();
   }

   public void renderExperienceBar(MatrixStack matrices, int x) {
      this.client.getProfiler().push("expBar");
      this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
      int _snowman = this.client.player.getNextLevelExperience();
      if (_snowman > 0) {
         int _snowmanx = 182;
         int _snowmanxx = (int)(this.client.player.experienceProgress * 183.0F);
         int _snowmanxxx = this.scaledHeight - 32 + 3;
         this.drawTexture(matrices, x, _snowmanxxx, 0, 64, 182, 5);
         if (_snowmanxx > 0) {
            this.drawTexture(matrices, x, _snowmanxxx, 0, 69, _snowmanxx, 5);
         }
      }

      this.client.getProfiler().pop();
      if (this.client.player.experienceLevel > 0) {
         this.client.getProfiler().push("expLevel");
         String _snowmanx = "" + this.client.player.experienceLevel;
         int _snowmanxx = (this.scaledWidth - this.getFontRenderer().getWidth(_snowmanx)) / 2;
         int _snowmanxxx = this.scaledHeight - 31 - 4;
         this.getFontRenderer().draw(matrices, _snowmanx, (float)(_snowmanxx + 1), (float)_snowmanxxx, 0);
         this.getFontRenderer().draw(matrices, _snowmanx, (float)(_snowmanxx - 1), (float)_snowmanxxx, 0);
         this.getFontRenderer().draw(matrices, _snowmanx, (float)_snowmanxx, (float)(_snowmanxxx + 1), 0);
         this.getFontRenderer().draw(matrices, _snowmanx, (float)_snowmanxx, (float)(_snowmanxxx - 1), 0);
         this.getFontRenderer().draw(matrices, _snowmanx, (float)_snowmanxx, (float)_snowmanxxx, 8453920);
         this.client.getProfiler().pop();
      }
   }

   public void renderHeldItemTooltip(MatrixStack matrices) {
      this.client.getProfiler().push("selectedItemName");
      if (this.heldItemTooltipFade > 0 && !this.currentStack.isEmpty()) {
         MutableText _snowman = new LiteralText("").append(this.currentStack.getName()).formatted(this.currentStack.getRarity().formatting);
         if (this.currentStack.hasCustomName()) {
            _snowman.formatted(Formatting.ITALIC);
         }

         int _snowmanx = this.getFontRenderer().getWidth(_snowman);
         int _snowmanxx = (this.scaledWidth - _snowmanx) / 2;
         int _snowmanxxx = this.scaledHeight - 59;
         if (!this.client.interactionManager.hasStatusBars()) {
            _snowmanxxx += 14;
         }

         int _snowmanxxxx = (int)((float)this.heldItemTooltipFade * 256.0F / 10.0F);
         if (_snowmanxxxx > 255) {
            _snowmanxxxx = 255;
         }

         if (_snowmanxxxx > 0) {
            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            fill(matrices, _snowmanxx - 2, _snowmanxxx - 2, _snowmanxx + _snowmanx + 2, _snowmanxxx + 9 + 2, this.client.options.getTextBackgroundColor(0));
            this.getFontRenderer().drawWithShadow(matrices, _snowman, (float)_snowmanxx, (float)_snowmanxxx, 16777215 + (_snowmanxxxx << 24));
            RenderSystem.disableBlend();
            RenderSystem.popMatrix();
         }
      }

      this.client.getProfiler().pop();
   }

   public void renderDemoTimer(MatrixStack matrices) {
      this.client.getProfiler().push("demo");
      Text _snowman;
      if (this.client.world.getTime() >= 120500L) {
         _snowman = DEMO_EXPIRED_MESSAGE;
      } else {
         _snowman = new TranslatableText("demo.remainingTime", ChatUtil.ticksToString((int)(120500L - this.client.world.getTime())));
      }

      int _snowmanx = this.getFontRenderer().getWidth(_snowman);
      this.getFontRenderer().drawWithShadow(matrices, _snowman, (float)(this.scaledWidth - _snowmanx - 10), 5.0F, 16777215);
      this.client.getProfiler().pop();
   }

   private void renderScoreboardSidebar(MatrixStack matrices, ScoreboardObjective objective) {
      Scoreboard _snowman = objective.getScoreboard();
      Collection<ScoreboardPlayerScore> _snowmanx = _snowman.getAllPlayerScores(objective);
      List<ScoreboardPlayerScore> _snowmanxx = _snowmanx.stream()
         .filter(_snowmanxxx -> _snowmanxxx.getPlayerName() != null && !_snowmanxxx.getPlayerName().startsWith("#"))
         .collect(Collectors.toList());
      if (_snowmanxx.size() > 15) {
         _snowmanx = Lists.newArrayList(Iterables.skip(_snowmanxx, _snowmanx.size() - 15));
      } else {
         _snowmanx = _snowmanxx;
      }

      List<Pair<ScoreboardPlayerScore, Text>> _snowmanxxx = Lists.newArrayListWithCapacity(_snowmanx.size());
      Text _snowmanxxxx = objective.getDisplayName();
      int _snowmanxxxxx = this.getFontRenderer().getWidth(_snowmanxxxx);
      int _snowmanxxxxxx = _snowmanxxxxx;
      int _snowmanxxxxxxx = this.getFontRenderer().getWidth(": ");

      for (ScoreboardPlayerScore _snowmanxxxxxxxx : _snowmanx) {
         Team _snowmanxxxxxxxxx = _snowman.getPlayerTeam(_snowmanxxxxxxxx.getPlayerName());
         Text _snowmanxxxxxxxxxx = Team.modifyText(_snowmanxxxxxxxxx, new LiteralText(_snowmanxxxxxxxx.getPlayerName()));
         _snowmanxxx.add(Pair.of(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx));
         _snowmanxxxxxx = Math.max(
            _snowmanxxxxxx, this.getFontRenderer().getWidth(_snowmanxxxxxxxxxx) + _snowmanxxxxxxx + this.getFontRenderer().getWidth(Integer.toString(_snowmanxxxxxxxx.getScore()))
         );
      }

      int _snowmanxxxxxxxx = _snowmanx.size() * 9;
      int _snowmanxxxxxxxxx = this.scaledHeight / 2 + _snowmanxxxxxxxx / 3;
      int _snowmanxxxxxxxxxx = 3;
      int _snowmanxxxxxxxxxxx = this.scaledWidth - _snowmanxxxxxx - 3;
      int _snowmanxxxxxxxxxxxx = 0;
      int _snowmanxxxxxxxxxxxxx = this.client.options.getTextBackgroundColor(0.3F);
      int _snowmanxxxxxxxxxxxxxx = this.client.options.getTextBackgroundColor(0.4F);

      for (Pair<ScoreboardPlayerScore, Text> _snowmanxxxxxxxxxxxxxxx : _snowmanxxx) {
         _snowmanxxxxxxxxxxxx++;
         ScoreboardPlayerScore _snowmanxxxxxxxxxxxxxxxx = (ScoreboardPlayerScore)_snowmanxxxxxxxxxxxxxxx.getFirst();
         Text _snowmanxxxxxxxxxxxxxxxxx = (Text)_snowmanxxxxxxxxxxxxxxx.getSecond();
         String _snowmanxxxxxxxxxxxxxxxxxx = Formatting.RED + "" + _snowmanxxxxxxxxxxxxxxxx.getScore();
         int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxx * 9;
         int _snowmanxxxxxxxxxxxxxxxxxxxx = this.scaledWidth - 3 + 2;
         fill(matrices, _snowmanxxxxxxxxxxx - 2, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx + 9, _snowmanxxxxxxxxxxxxx);
         this.getFontRenderer().draw(matrices, _snowmanxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxx, -1);
         this.getFontRenderer()
            .draw(
               matrices,
               _snowmanxxxxxxxxxxxxxxxxxx,
               (float)(_snowmanxxxxxxxxxxxxxxxxxxxx - this.getFontRenderer().getWidth(_snowmanxxxxxxxxxxxxxxxxxx)),
               (float)_snowmanxxxxxxxxxxxxxxxxxxx,
               -1
            );
         if (_snowmanxxxxxxxxxxxx == _snowmanx.size()) {
            fill(matrices, _snowmanxxxxxxxxxxx - 2, _snowmanxxxxxxxxxxxxxxxxxxx - 9 - 1, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxx);
            fill(matrices, _snowmanxxxxxxxxxxx - 2, _snowmanxxxxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
            this.getFontRenderer().draw(matrices, _snowmanxxxx, (float)(_snowmanxxxxxxxxxxx + _snowmanxxxxxx / 2 - _snowmanxxxxx / 2), (float)(_snowmanxxxxxxxxxxxxxxxxxxx - 9), -1);
         }
      }
   }

   private PlayerEntity getCameraPlayer() {
      return !(this.client.getCameraEntity() instanceof PlayerEntity) ? null : (PlayerEntity)this.client.getCameraEntity();
   }

   private LivingEntity getRiddenEntity() {
      PlayerEntity _snowman = this.getCameraPlayer();
      if (_snowman != null) {
         Entity _snowmanx = _snowman.getVehicle();
         if (_snowmanx == null) {
            return null;
         }

         if (_snowmanx instanceof LivingEntity) {
            return (LivingEntity)_snowmanx;
         }
      }

      return null;
   }

   private int getHeartCount(LivingEntity entity) {
      if (entity != null && entity.isLiving()) {
         float _snowman = entity.getMaxHealth();
         int _snowmanx = (int)(_snowman + 0.5F) / 2;
         if (_snowmanx > 30) {
            _snowmanx = 30;
         }

         return _snowmanx;
      } else {
         return 0;
      }
   }

   private int getHeartRows(int heartCount) {
      return (int)Math.ceil((double)heartCount / 10.0);
   }

   private void renderStatusBars(MatrixStack matrices) {
      PlayerEntity _snowman = this.getCameraPlayer();
      if (_snowman != null) {
         int _snowmanx = MathHelper.ceil(_snowman.getHealth());
         boolean _snowmanxx = this.heartJumpEndTick > (long)this.ticks && (this.heartJumpEndTick - (long)this.ticks) / 3L % 2L == 1L;
         long _snowmanxxx = Util.getMeasuringTimeMs();
         if (_snowmanx < this.lastHealthValue && _snowman.timeUntilRegen > 0) {
            this.lastHealthCheckTime = _snowmanxxx;
            this.heartJumpEndTick = (long)(this.ticks + 20);
         } else if (_snowmanx > this.lastHealthValue && _snowman.timeUntilRegen > 0) {
            this.lastHealthCheckTime = _snowmanxxx;
            this.heartJumpEndTick = (long)(this.ticks + 10);
         }

         if (_snowmanxxx - this.lastHealthCheckTime > 1000L) {
            this.lastHealthValue = _snowmanx;
            this.renderHealthValue = _snowmanx;
            this.lastHealthCheckTime = _snowmanxxx;
         }

         this.lastHealthValue = _snowmanx;
         int _snowmanxxxx = this.renderHealthValue;
         this.random.setSeed((long)(this.ticks * 312871));
         HungerManager _snowmanxxxxx = _snowman.getHungerManager();
         int _snowmanxxxxxx = _snowmanxxxxx.getFoodLevel();
         int _snowmanxxxxxxx = this.scaledWidth / 2 - 91;
         int _snowmanxxxxxxxx = this.scaledWidth / 2 + 91;
         int _snowmanxxxxxxxxx = this.scaledHeight - 39;
         float _snowmanxxxxxxxxxx = (float)_snowman.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
         int _snowmanxxxxxxxxxxx = MathHelper.ceil(_snowman.getAbsorptionAmount());
         int _snowmanxxxxxxxxxxxx = MathHelper.ceil((_snowmanxxxxxxxxxx + (float)_snowmanxxxxxxxxxxx) / 2.0F / 10.0F);
         int _snowmanxxxxxxxxxxxxx = Math.max(10 - (_snowmanxxxxxxxxxxxx - 2), 3);
         int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxx - (_snowmanxxxxxxxxxxxx - 1) * _snowmanxxxxxxxxxxxxx - 10;
         int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx - 10;
         int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxx = _snowman.getArmor();
         int _snowmanxxxxxxxxxxxxxxxxxx = -1;
         if (_snowman.hasStatusEffect(StatusEffects.REGENERATION)) {
            _snowmanxxxxxxxxxxxxxxxxxx = this.ticks % MathHelper.ceil(_snowmanxxxxxxxxxx + 5.0F);
         }

         this.client.getProfiler().push("armor");

         for (int _snowmanxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxx < 10; _snowmanxxxxxxxxxxxxxxxxxxx++) {
            if (_snowmanxxxxxxxxxxxxxxxxx > 0) {
               int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx * 8;
               if (_snowmanxxxxxxxxxxxxxxxxxxx * 2 + 1 < _snowmanxxxxxxxxxxxxxxxxx) {
                  this.drawTexture(matrices, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 34, 9, 9, 9);
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxx * 2 + 1 == _snowmanxxxxxxxxxxxxxxxxx) {
                  this.drawTexture(matrices, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 25, 9, 9, 9);
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxx * 2 + 1 > _snowmanxxxxxxxxxxxxxxxxx) {
                  this.drawTexture(matrices, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 16, 9, 9, 9);
               }
            }
         }

         this.client.getProfiler().swap("health");

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = MathHelper.ceil((_snowmanxxxxxxxxxx + (float)_snowmanxxxxxxxxxxx) / 2.0F) - 1;
            _snowmanxxxxxxxxxxxxxxxxxxxxx >= 0;
            _snowmanxxxxxxxxxxxxxxxxxxxxx--
         ) {
            int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 16;
            if (_snowman.hasStatusEffect(StatusEffects.POISON)) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxx += 36;
            } else if (_snowman.hasStatusEffect(StatusEffects.WITHER)) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxx += 72;
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 0;
            if (_snowmanxx) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 1;
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.ceil((float)(_snowmanxxxxxxxxxxxxxxxxxxxxx + 1) / 10.0F) - 1;
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx % 10 * 8;
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx;
            if (_snowmanx <= 4) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx += this.random.nextInt(2);
            }

            if (_snowmanxxxxxxxxxxxxxxxx <= 0 && _snowmanxxxxxxxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx -= 2;
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            if (_snowman.world.getLevelProperties().isHardcore()) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = 5;
            }

            this.drawTexture(
               matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, 16 + _snowmanxxxxxxxxxxxxxxxxxxxxxxx * 9, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9
            );
            if (_snowmanxx) {
               if (_snowmanxxxxxxxxxxxxxxxxxxxxx * 2 + 1 < _snowmanxxxx) {
                  this.drawTexture(
                     matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 54, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9
                  );
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxx * 2 + 1 == _snowmanxxxx) {
                  this.drawTexture(
                     matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 63, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9
                  );
               }
            }

            if (_snowmanxxxxxxxxxxxxxxxx > 0) {
               if (_snowmanxxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxx && _snowmanxxxxxxxxxxx % 2 == 1) {
                  this.drawTexture(
                     matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 153, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9
                  );
                  _snowmanxxxxxxxxxxxxxxxx--;
               } else {
                  this.drawTexture(
                     matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 144, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9
                  );
                  _snowmanxxxxxxxxxxxxxxxx -= 2;
               }
            } else {
               if (_snowmanxxxxxxxxxxxxxxxxxxxxx * 2 + 1 < _snowmanx) {
                  this.drawTexture(
                     matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 36, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9
                  );
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxx * 2 + 1 == _snowmanx) {
                  this.drawTexture(
                     matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx + 45, 9 * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 9, 9
                  );
               }
            }
         }

         LivingEntity _snowmanxxxxxxxxxxxxxxxxxxxxx = this.getRiddenEntity();
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getHeartCount(_snowmanxxxxxxxxxxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx == 0) {
            this.client.getProfiler().swap("food");

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < 10; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 16;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
               if (_snowman.hasStatusEffect(StatusEffects.HUNGER)) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx += 36;
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 13;
               }

               if (_snowman.getHungerManager().getSaturationLevel() <= 0.0F && this.ticks % (_snowmanxxxxxx * 3 + 1) == 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx + (this.random.nextInt(3) - 1);
               }

               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 8 - 9;
               this.drawTexture(
                  matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 16 + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 9, 27, 9, 9
               );
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 2 + 1 < _snowmanxxxxxx) {
                  this.drawTexture(
                     matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 36, 27, 9, 9
                  );
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 2 + 1 == _snowmanxxxxxx) {
                  this.drawTexture(
                     matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 45, 27, 9, 9
                  );
               }
            }

            _snowmanxxxxxxxxxxxxxxx -= 10;
         }

         this.client.getProfiler().swap("air");
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getMaxAir();
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.min(_snowman.getAir(), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         if (_snowman.isSubmergedIn(FluidTags.WATER) || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getHeartRows(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx) - 1;
            _snowmanxxxxxxxxxxxxxxx -= _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 10;
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.ceil(
               (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - 2) * 10.0 / (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.ceil(
                  (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 10.0 / (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               )
               - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
            ) {
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                  this.drawTexture(matrices, _snowmanxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 8 - 9, _snowmanxxxxxxxxxxxxxxx, 16, 18, 9, 9);
               } else {
                  this.drawTexture(matrices, _snowmanxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 8 - 9, _snowmanxxxxxxxxxxxxxxx, 25, 18, 9, 9);
               }
            }
         }

         this.client.getProfiler().pop();
      }
   }

   private void renderMountHealth(MatrixStack matrices) {
      LivingEntity _snowman = this.getRiddenEntity();
      if (_snowman != null) {
         int _snowmanx = this.getHeartCount(_snowman);
         if (_snowmanx != 0) {
            int _snowmanxx = (int)Math.ceil((double)_snowman.getHealth());
            this.client.getProfiler().swap("mountHealth");
            int _snowmanxxx = this.scaledHeight - 39;
            int _snowmanxxxx = this.scaledWidth / 2 + 91;
            int _snowmanxxxxx = _snowmanxxx;
            int _snowmanxxxxxx = 0;

            for (boolean _snowmanxxxxxxx = false; _snowmanx > 0; _snowmanxxxxxx += 20) {
               int _snowmanxxxxxxxx = Math.min(_snowmanx, 10);
               _snowmanx -= _snowmanxxxxxxxx;

               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxx = 52;
                  int _snowmanxxxxxxxxxxx = 0;
                  int _snowmanxxxxxxxxxxxx = _snowmanxxxx - _snowmanxxxxxxxxx * 8 - 9;
                  this.drawTexture(matrices, _snowmanxxxxxxxxxxxx, _snowmanxxxxx, 52 + _snowmanxxxxxxxxxxx * 9, 9, 9, 9);
                  if (_snowmanxxxxxxxxx * 2 + 1 + _snowmanxxxxxx < _snowmanxx) {
                     this.drawTexture(matrices, _snowmanxxxxxxxxxxxx, _snowmanxxxxx, 88, 9, 9, 9);
                  }

                  if (_snowmanxxxxxxxxx * 2 + 1 + _snowmanxxxxxx == _snowmanxx) {
                     this.drawTexture(matrices, _snowmanxxxxxxxxxxxx, _snowmanxxxxx, 97, 9, 9, 9);
                  }
               }

               _snowmanxxxxx -= 10;
            }
         }
      }
   }

   private void renderPumpkinOverlay() {
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableAlphaTest();
      this.client.getTextureManager().bindTexture(PUMPKIN_BLUR);
      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      _snowmanx.begin(7, VertexFormats.POSITION_TEXTURE);
      _snowmanx.vertex(0.0, (double)this.scaledHeight, -90.0).texture(0.0F, 1.0F).next();
      _snowmanx.vertex((double)this.scaledWidth, (double)this.scaledHeight, -90.0).texture(1.0F, 1.0F).next();
      _snowmanx.vertex((double)this.scaledWidth, 0.0, -90.0).texture(1.0F, 0.0F).next();
      _snowmanx.vertex(0.0, 0.0, -90.0).texture(0.0F, 0.0F).next();
      _snowman.draw();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableAlphaTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void updateVignetteDarkness(Entity entity) {
      if (entity != null) {
         float _snowman = MathHelper.clamp(1.0F - entity.getBrightnessAtEyes(), 0.0F, 1.0F);
         this.vignetteDarkness = (float)((double)this.vignetteDarkness + (double)(_snowman - this.vignetteDarkness) * 0.01);
      }
   }

   private void renderVignetteOverlay(Entity entity) {
      WorldBorder _snowman = this.client.world.getWorldBorder();
      float _snowmanx = (float)_snowman.getDistanceInsideBorder(entity);
      double _snowmanxx = Math.min(_snowman.getShrinkingSpeed() * (double)_snowman.getWarningTime() * 1000.0, Math.abs(_snowman.getTargetSize() - _snowman.getSize()));
      double _snowmanxxx = Math.max((double)_snowman.getWarningBlocks(), _snowmanxx);
      if ((double)_snowmanx < _snowmanxxx) {
         _snowmanx = 1.0F - (float)((double)_snowmanx / _snowmanxxx);
      } else {
         _snowmanx = 0.0F;
      }

      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.blendFuncSeparate(
         GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO
      );
      if (_snowmanx > 0.0F) {
         RenderSystem.color4f(0.0F, _snowmanx, _snowmanx, 1.0F);
      } else {
         RenderSystem.color4f(this.vignetteDarkness, this.vignetteDarkness, this.vignetteDarkness, 1.0F);
      }

      this.client.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
      Tessellator _snowmanxxxx = Tessellator.getInstance();
      BufferBuilder _snowmanxxxxx = _snowmanxxxx.getBuffer();
      _snowmanxxxxx.begin(7, VertexFormats.POSITION_TEXTURE);
      _snowmanxxxxx.vertex(0.0, (double)this.scaledHeight, -90.0).texture(0.0F, 1.0F).next();
      _snowmanxxxxx.vertex((double)this.scaledWidth, (double)this.scaledHeight, -90.0).texture(1.0F, 1.0F).next();
      _snowmanxxxxx.vertex((double)this.scaledWidth, 0.0, -90.0).texture(1.0F, 0.0F).next();
      _snowmanxxxxx.vertex(0.0, 0.0, -90.0).texture(0.0F, 0.0F).next();
      _snowmanxxxx.draw();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
   }

   private void renderPortalOverlay(float nauseaStrength) {
      if (nauseaStrength < 1.0F) {
         nauseaStrength *= nauseaStrength;
         nauseaStrength *= nauseaStrength;
         nauseaStrength = nauseaStrength * 0.8F + 0.2F;
      }

      RenderSystem.disableAlphaTest();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, nauseaStrength);
      this.client.getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
      Sprite _snowman = this.client.getBlockRenderManager().getModels().getSprite(Blocks.NETHER_PORTAL.getDefaultState());
      float _snowmanx = _snowman.getMinU();
      float _snowmanxx = _snowman.getMinV();
      float _snowmanxxx = _snowman.getMaxU();
      float _snowmanxxxx = _snowman.getMaxV();
      Tessellator _snowmanxxxxx = Tessellator.getInstance();
      BufferBuilder _snowmanxxxxxx = _snowmanxxxxx.getBuffer();
      _snowmanxxxxxx.begin(7, VertexFormats.POSITION_TEXTURE);
      _snowmanxxxxxx.vertex(0.0, (double)this.scaledHeight, -90.0).texture(_snowmanx, _snowmanxxxx).next();
      _snowmanxxxxxx.vertex((double)this.scaledWidth, (double)this.scaledHeight, -90.0).texture(_snowmanxxx, _snowmanxxxx).next();
      _snowmanxxxxxx.vertex((double)this.scaledWidth, 0.0, -90.0).texture(_snowmanxxx, _snowmanxx).next();
      _snowmanxxxxxx.vertex(0.0, 0.0, -90.0).texture(_snowmanx, _snowmanxx).next();
      _snowmanxxxxx.draw();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableAlphaTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderHotbarItem(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack) {
      if (!stack.isEmpty()) {
         float _snowman = (float)stack.getCooldown() - tickDelta;
         if (_snowman > 0.0F) {
            RenderSystem.pushMatrix();
            float _snowmanx = 1.0F + _snowman / 5.0F;
            RenderSystem.translatef((float)(x + 8), (float)(y + 12), 0.0F);
            RenderSystem.scalef(1.0F / _snowmanx, (_snowmanx + 1.0F) / 2.0F, 1.0F);
            RenderSystem.translatef((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
         }

         this.itemRenderer.renderInGuiWithOverrides(player, stack, x, y);
         if (_snowman > 0.0F) {
            RenderSystem.popMatrix();
         }

         this.itemRenderer.renderGuiItemOverlay(this.client.textRenderer, stack, x, y);
      }
   }

   public void tick() {
      if (this.overlayRemaining > 0) {
         this.overlayRemaining--;
      }

      if (this.titleTotalTicks > 0) {
         this.titleTotalTicks--;
         if (this.titleTotalTicks <= 0) {
            this.title = null;
            this.subtitle = null;
         }
      }

      this.ticks++;
      Entity _snowman = this.client.getCameraEntity();
      if (_snowman != null) {
         this.updateVignetteDarkness(_snowman);
      }

      if (this.client.player != null) {
         ItemStack _snowmanx = this.client.player.inventory.getMainHandStack();
         if (_snowmanx.isEmpty()) {
            this.heldItemTooltipFade = 0;
         } else if (this.currentStack.isEmpty() || _snowmanx.getItem() != this.currentStack.getItem() || !_snowmanx.getName().equals(this.currentStack.getName())) {
            this.heldItemTooltipFade = 40;
         } else if (this.heldItemTooltipFade > 0) {
            this.heldItemTooltipFade--;
         }

         this.currentStack = _snowmanx;
      }
   }

   public void setRecordPlayingOverlay(Text _snowman) {
      this.setOverlayMessage(new TranslatableText("record.nowPlaying", _snowman), true);
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
      } else if (title != null) {
         this.title = title;
         this.titleTotalTicks = this.titleFadeInTicks + this.titleRemainTicks + this.titleFadeOutTicks;
      } else if (subtitle != null) {
         this.subtitle = subtitle;
      } else {
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
   }

   public UUID method_31406(Text _snowman) {
      String _snowmanx = TextVisitFactory.method_31402(_snowman);
      String _snowmanxx = StringUtils.substringBetween(_snowmanx, "<", ">");
      return _snowmanxx == null ? Util.NIL_UUID : this.client.getSocialInteractionsManager().method_31407(_snowmanxx);
   }

   public void addChatMessage(MessageType type, Text text, UUID senderUuid) {
      if (!this.client.shouldBlockMessages(senderUuid)) {
         if (!this.client.options.field_26926 || !this.client.shouldBlockMessages(this.method_31406(text))) {
            for (ClientChatListener _snowman : this.listeners.get(type)) {
               _snowman.onChatMessage(type, text, senderUuid);
            }
         }
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
