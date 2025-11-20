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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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

      for (MessageType lv : MessageType.values()) {
         this.listeners.put(lv, Lists.newArrayList());
      }

      ClientChatListener lv2 = NarratorManager.INSTANCE;
      this.listeners.get(MessageType.CHAT).add(new ChatHudListener(client));
      this.listeners.get(MessageType.CHAT).add(lv2);
      this.listeners.get(MessageType.SYSTEM).add(new ChatHudListener(client));
      this.listeners.get(MessageType.SYSTEM).add(lv2);
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
      TextRenderer lv = this.getFontRenderer();
      RenderSystem.enableBlend();
      if (MinecraftClient.isFancyGraphicsOrBetter()) {
         this.renderVignetteOverlay(this.client.getCameraEntity());
      } else {
         RenderSystem.enableDepthTest();
         RenderSystem.defaultBlendFunc();
      }

      ItemStack lv2 = this.client.player.inventory.getArmorStack(3);
      if (this.client.options.getPerspective().isFirstPerson() && lv2.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
         this.renderPumpkinOverlay();
      }

      float g = MathHelper.lerp(tickDelta, this.client.player.lastNauseaStrength, this.client.player.nextNauseaStrength);
      if (g > 0.0F && !this.client.player.hasStatusEffect(StatusEffects.NAUSEA)) {
         this.renderPortalOverlay(g);
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
         int i = this.scaledWidth / 2 - 91;
         if (this.client.player.hasJumpingMount()) {
            this.renderMountJumpBar(matrices, i);
         } else if (this.client.interactionManager.hasExperienceBar()) {
            this.renderExperienceBar(matrices, i);
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
         float h = (float)this.client.player.getSleepTimer();
         float j = h / 100.0F;
         if (j > 1.0F) {
            j = 1.0F - (h - 100.0F) / 10.0F;
         }

         int k = (int)(220.0F * j) << 24 | 1052704;
         fill(matrices, 0, 0, this.scaledWidth, this.scaledHeight, k);
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
            float l = (float)this.overlayRemaining - tickDelta;
            int m = (int)(l * 255.0F / 20.0F);
            if (m > 255) {
               m = 255;
            }

            if (m > 8) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.scaledWidth / 2), (float)(this.scaledHeight - 68), 0.0F);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               int n = 16777215;
               if (this.overlayTinted) {
                  n = MathHelper.hsvToRgb(l / 50.0F, 0.7F, 0.6F) & 16777215;
               }

               int o = m << 24 & 0xFF000000;
               int p = lv.getWidth(this.overlayMessage);
               this.drawTextBackground(matrices, lv, -4, p, 16777215 | o);
               lv.draw(matrices, this.overlayMessage, (float)(-p / 2), -4.0F, n | o);
               RenderSystem.disableBlend();
               RenderSystem.popMatrix();
            }

            this.client.getProfiler().pop();
         }

         if (this.title != null && this.titleTotalTicks > 0) {
            this.client.getProfiler().push("titleAndSubtitle");
            float q = (float)this.titleTotalTicks - tickDelta;
            int r = 255;
            if (this.titleTotalTicks > this.titleFadeOutTicks + this.titleRemainTicks) {
               float s = (float)(this.titleFadeInTicks + this.titleRemainTicks + this.titleFadeOutTicks) - q;
               r = (int)(s * 255.0F / (float)this.titleFadeInTicks);
            }

            if (this.titleTotalTicks <= this.titleFadeOutTicks) {
               r = (int)(q * 255.0F / (float)this.titleFadeOutTicks);
            }

            r = MathHelper.clamp(r, 0, 255);
            if (r > 8) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.scaledWidth / 2), (float)(this.scaledHeight / 2), 0.0F);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.pushMatrix();
               RenderSystem.scalef(4.0F, 4.0F, 4.0F);
               int t = r << 24 & 0xFF000000;
               int u = lv.getWidth(this.title);
               this.drawTextBackground(matrices, lv, -10, u, 16777215 | t);
               lv.drawWithShadow(matrices, this.title, (float)(-u / 2), -10.0F, 16777215 | t);
               RenderSystem.popMatrix();
               if (this.subtitle != null) {
                  RenderSystem.pushMatrix();
                  RenderSystem.scalef(2.0F, 2.0F, 2.0F);
                  int v = lv.getWidth(this.subtitle);
                  this.drawTextBackground(matrices, lv, 5, v, 16777215 | t);
                  lv.drawWithShadow(matrices, this.subtitle, (float)(-v / 2), 5.0F, 16777215 | t);
                  RenderSystem.popMatrix();
               }

               RenderSystem.disableBlend();
               RenderSystem.popMatrix();
            }

            this.client.getProfiler().pop();
         }

         this.subtitlesHud.render(matrices);
         Scoreboard lv3 = this.client.world.getScoreboard();
         ScoreboardObjective lv4 = null;
         Team lv5 = lv3.getPlayerTeam(this.client.player.getEntityName());
         if (lv5 != null) {
            int w = lv5.getColor().getColorIndex();
            if (w >= 0) {
               lv4 = lv3.getObjectiveForSlot(3 + w);
            }
         }

         ScoreboardObjective lv6 = lv4 != null ? lv4 : lv3.getObjectiveForSlot(1);
         if (lv6 != null) {
            this.renderScoreboardSidebar(matrices, lv6);
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
         lv6 = lv3.getObjectiveForSlot(0);
         if (!this.client.options.keyPlayerList.isPressed()
            || this.client.isInSingleplayer() && this.client.player.networkHandler.getPlayerList().size() <= 1 && lv6 == null) {
            this.playerListHud.tick(false);
         } else {
            this.playerListHud.tick(true);
            this.playerListHud.render(matrices, this.scaledWidth, lv3, lv6);
         }
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableAlphaTest();
   }

   private void drawTextBackground(MatrixStack matrices, TextRenderer textRenderer, int yOffset, int width, int color) {
      int l = this.client.options.getTextBackgroundColor(0.0F);
      if (l != 0) {
         int m = -width / 2;
         fill(matrices, m - 2, yOffset - 2, m + width + 2, yOffset + 9 + 2, BackgroundHelper.ColorMixer.mixColor(l, color));
      }
   }

   private void renderCrosshair(MatrixStack matrices) {
      GameOptions lv = this.client.options;
      if (lv.getPerspective().isFirstPerson()) {
         if (this.client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR || this.shouldRenderSpectatorCrosshair(this.client.crosshairTarget)) {
            if (lv.debugEnabled && !lv.hudHidden && !this.client.player.getReducedDebugInfo() && !lv.reducedDebugInfo) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.scaledWidth / 2), (float)(this.scaledHeight / 2), (float)this.getZOffset());
               Camera lv2 = this.client.gameRenderer.getCamera();
               RenderSystem.rotatef(lv2.getPitch(), -1.0F, 0.0F, 0.0F);
               RenderSystem.rotatef(lv2.getYaw(), 0.0F, 1.0F, 0.0F);
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
               int i = 15;
               this.drawTexture(matrices, (this.scaledWidth - 15) / 2, (this.scaledHeight - 15) / 2, 0, 0, 15, 15);
               if (this.client.options.attackIndicator == AttackIndicator.CROSSHAIR) {
                  float f = this.client.player.getAttackCooldownProgress(0.0F);
                  boolean bl = false;
                  if (this.client.targetedEntity != null && this.client.targetedEntity instanceof LivingEntity && f >= 1.0F) {
                     bl = this.client.player.getAttackCooldownProgressPerTick() > 5.0F;
                     bl &= this.client.targetedEntity.isAlive();
                  }

                  int j = this.scaledHeight / 2 - 7 + 16;
                  int k = this.scaledWidth / 2 - 8;
                  if (bl) {
                     this.drawTexture(matrices, k, j, 68, 94, 16, 16);
                  } else if (f < 1.0F) {
                     int l = (int)(f * 17.0F);
                     this.drawTexture(matrices, k, j, 36, 94, 16, 4);
                     this.drawTexture(matrices, k, j, 52, 94, l, 4);
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
         BlockPos lv = ((BlockHitResult)hitResult).getBlockPos();
         World lv2 = this.client.world;
         return lv2.getBlockState(lv).createScreenHandlerFactory(lv2, lv) != null;
      } else {
         return false;
      }
   }

   protected void renderStatusEffectOverlay(MatrixStack matrices) {
      Collection<StatusEffectInstance> collection = this.client.player.getStatusEffects();
      if (!collection.isEmpty()) {
         RenderSystem.enableBlend();
         int i = 0;
         int j = 0;
         StatusEffectSpriteManager lv = this.client.getStatusEffectSpriteManager();
         List<Runnable> list = Lists.newArrayListWithExpectedSize(collection.size());
         this.client.getTextureManager().bindTexture(HandledScreen.BACKGROUND_TEXTURE);

         for (StatusEffectInstance lv2 : Ordering.natural().reverse().sortedCopy(collection)) {
            StatusEffect lv3 = lv2.getEffectType();
            if (lv2.shouldShowIcon()) {
               int k = this.scaledWidth;
               int l = 1;
               if (this.client.isDemo()) {
                  l += 15;
               }

               if (lv3.isBeneficial()) {
                  i++;
                  k -= 25 * i;
               } else {
                  j++;
                  k -= 25 * j;
                  l += 26;
               }

               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               float f = 1.0F;
               if (lv2.isAmbient()) {
                  this.drawTexture(matrices, k, l, 165, 166, 24, 24);
               } else {
                  this.drawTexture(matrices, k, l, 141, 166, 24, 24);
                  if (lv2.getDuration() <= 200) {
                     int m = 10 - lv2.getDuration() / 20;
                     f = MathHelper.clamp((float)lv2.getDuration() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                        + MathHelper.cos((float)lv2.getDuration() * (float) Math.PI / 5.0F) * MathHelper.clamp((float)m / 10.0F * 0.25F, 0.0F, 0.25F);
                  }
               }

               Sprite lv4 = lv.getSprite(lv3);
               int n = k;
               int o = l;
               float g = f;
               list.add(() -> {
                  this.client.getTextureManager().bindTexture(lv4.getAtlas().getId());
                  RenderSystem.color4f(1.0F, 1.0F, 1.0F, g);
                  drawSprite(matrices, n + 3, o + 3, this.getZOffset(), 18, 18, lv4);
               });
            }
         }

         list.forEach(Runnable::run);
      }
   }

   protected void renderHotbar(float tickDelta, MatrixStack matrices) {
      PlayerEntity lv = this.getCameraPlayer();
      if (lv != null) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
         ItemStack lv2 = lv.getOffHandStack();
         Arm lv3 = lv.getMainArm().getOpposite();
         int i = this.scaledWidth / 2;
         int j = this.getZOffset();
         int k = 182;
         int l = 91;
         this.setZOffset(-90);
         this.drawTexture(matrices, i - 91, this.scaledHeight - 22, 0, 0, 182, 22);
         this.drawTexture(matrices, i - 91 - 1 + lv.inventory.selectedSlot * 20, this.scaledHeight - 22 - 1, 0, 22, 24, 22);
         if (!lv2.isEmpty()) {
            if (lv3 == Arm.LEFT) {
               this.drawTexture(matrices, i - 91 - 29, this.scaledHeight - 23, 24, 22, 29, 24);
            } else {
               this.drawTexture(matrices, i + 91, this.scaledHeight - 23, 53, 22, 29, 24);
            }
         }

         this.setZOffset(j);
         RenderSystem.enableRescaleNormal();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();

         for (int m = 0; m < 9; m++) {
            int n = i - 90 + m * 20 + 2;
            int o = this.scaledHeight - 16 - 3;
            this.renderHotbarItem(n, o, tickDelta, lv, lv.inventory.main.get(m));
         }

         if (!lv2.isEmpty()) {
            int p = this.scaledHeight - 16 - 3;
            if (lv3 == Arm.LEFT) {
               this.renderHotbarItem(i - 91 - 26, p, tickDelta, lv, lv2);
            } else {
               this.renderHotbarItem(i + 91 + 10, p, tickDelta, lv, lv2);
            }
         }

         if (this.client.options.attackIndicator == AttackIndicator.HOTBAR) {
            float g = this.client.player.getAttackCooldownProgress(0.0F);
            if (g < 1.0F) {
               int q = this.scaledHeight - 20;
               int r = i + 91 + 6;
               if (lv3 == Arm.RIGHT) {
                  r = i - 91 - 22;
               }

               this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
               int s = (int)(g * 19.0F);
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               this.drawTexture(matrices, r, q, 0, 94, 18, 18);
               this.drawTexture(matrices, r, q + 18 - s, 18, 112 - s, 18, s);
            }
         }

         RenderSystem.disableRescaleNormal();
         RenderSystem.disableBlend();
      }
   }

   public void renderMountJumpBar(MatrixStack matrices, int x) {
      this.client.getProfiler().push("jumpBar");
      this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
      float f = this.client.player.method_3151();
      int j = 182;
      int k = (int)(f * 183.0F);
      int l = this.scaledHeight - 32 + 3;
      this.drawTexture(matrices, x, l, 0, 84, 182, 5);
      if (k > 0) {
         this.drawTexture(matrices, x, l, 0, 89, k, 5);
      }

      this.client.getProfiler().pop();
   }

   public void renderExperienceBar(MatrixStack matrices, int x) {
      this.client.getProfiler().push("expBar");
      this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
      int j = this.client.player.getNextLevelExperience();
      if (j > 0) {
         int k = 182;
         int l = (int)(this.client.player.experienceProgress * 183.0F);
         int m = this.scaledHeight - 32 + 3;
         this.drawTexture(matrices, x, m, 0, 64, 182, 5);
         if (l > 0) {
            this.drawTexture(matrices, x, m, 0, 69, l, 5);
         }
      }

      this.client.getProfiler().pop();
      if (this.client.player.experienceLevel > 0) {
         this.client.getProfiler().push("expLevel");
         String string = "" + this.client.player.experienceLevel;
         int n = (this.scaledWidth - this.getFontRenderer().getWidth(string)) / 2;
         int o = this.scaledHeight - 31 - 4;
         this.getFontRenderer().draw(matrices, string, (float)(n + 1), (float)o, 0);
         this.getFontRenderer().draw(matrices, string, (float)(n - 1), (float)o, 0);
         this.getFontRenderer().draw(matrices, string, (float)n, (float)(o + 1), 0);
         this.getFontRenderer().draw(matrices, string, (float)n, (float)(o - 1), 0);
         this.getFontRenderer().draw(matrices, string, (float)n, (float)o, 8453920);
         this.client.getProfiler().pop();
      }
   }

   public void renderHeldItemTooltip(MatrixStack matrices) {
      this.client.getProfiler().push("selectedItemName");
      if (this.heldItemTooltipFade > 0 && !this.currentStack.isEmpty()) {
         MutableText lv = new LiteralText("").append(this.currentStack.getName()).formatted(this.currentStack.getRarity().formatting);
         if (this.currentStack.hasCustomName()) {
            lv.formatted(Formatting.ITALIC);
         }

         int i = this.getFontRenderer().getWidth(lv);
         int j = (this.scaledWidth - i) / 2;
         int k = this.scaledHeight - 59;
         if (!this.client.interactionManager.hasStatusBars()) {
            k += 14;
         }

         int l = (int)((float)this.heldItemTooltipFade * 256.0F / 10.0F);
         if (l > 255) {
            l = 255;
         }

         if (l > 0) {
            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            fill(matrices, j - 2, k - 2, j + i + 2, k + 9 + 2, this.client.options.getTextBackgroundColor(0));
            this.getFontRenderer().drawWithShadow(matrices, lv, (float)j, (float)k, 16777215 + (l << 24));
            RenderSystem.disableBlend();
            RenderSystem.popMatrix();
         }
      }

      this.client.getProfiler().pop();
   }

   public void renderDemoTimer(MatrixStack matrices) {
      this.client.getProfiler().push("demo");
      Text lv;
      if (this.client.world.getTime() >= 120500L) {
         lv = DEMO_EXPIRED_MESSAGE;
      } else {
         lv = new TranslatableText("demo.remainingTime", ChatUtil.ticksToString((int)(120500L - this.client.world.getTime())));
      }

      int i = this.getFontRenderer().getWidth(lv);
      this.getFontRenderer().drawWithShadow(matrices, lv, (float)(this.scaledWidth - i - 10), 5.0F, 16777215);
      this.client.getProfiler().pop();
   }

   private void renderScoreboardSidebar(MatrixStack matrices, ScoreboardObjective objective) {
      Scoreboard lv = objective.getScoreboard();
      Collection<ScoreboardPlayerScore> collection = lv.getAllPlayerScores(objective);
      List<ScoreboardPlayerScore> list = collection.stream()
         .filter(arg -> arg.getPlayerName() != null && !arg.getPlayerName().startsWith("#"))
         .collect(Collectors.toList());
      if (list.size() > 15) {
         collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
      } else {
         collection = list;
      }

      List<Pair<ScoreboardPlayerScore, Text>> list2 = Lists.newArrayListWithCapacity(collection.size());
      Text lv2 = objective.getDisplayName();
      int i = this.getFontRenderer().getWidth(lv2);
      int j = i;
      int k = this.getFontRenderer().getWidth(": ");

      for (ScoreboardPlayerScore lv3 : collection) {
         Team lv4 = lv.getPlayerTeam(lv3.getPlayerName());
         Text lv5 = Team.modifyText(lv4, new LiteralText(lv3.getPlayerName()));
         list2.add(Pair.of(lv3, lv5));
         j = Math.max(j, this.getFontRenderer().getWidth(lv5) + k + this.getFontRenderer().getWidth(Integer.toString(lv3.getScore())));
      }

      int l = collection.size() * 9;
      int m = this.scaledHeight / 2 + l / 3;
      int n = 3;
      int o = this.scaledWidth - j - 3;
      int p = 0;
      int q = this.client.options.getTextBackgroundColor(0.3F);
      int r = this.client.options.getTextBackgroundColor(0.4F);

      for (Pair<ScoreboardPlayerScore, Text> pair : list2) {
         p++;
         ScoreboardPlayerScore lv6 = (ScoreboardPlayerScore)pair.getFirst();
         Text lv7 = (Text)pair.getSecond();
         String string = Formatting.RED + "" + lv6.getScore();
         int t = m - p * 9;
         int u = this.scaledWidth - 3 + 2;
         fill(matrices, o - 2, t, u, t + 9, q);
         this.getFontRenderer().draw(matrices, lv7, (float)o, (float)t, -1);
         this.getFontRenderer().draw(matrices, string, (float)(u - this.getFontRenderer().getWidth(string)), (float)t, -1);
         if (p == collection.size()) {
            fill(matrices, o - 2, t - 9 - 1, u, t - 1, r);
            fill(matrices, o - 2, t - 1, u, t, q);
            this.getFontRenderer().draw(matrices, lv2, (float)(o + j / 2 - i / 2), (float)(t - 9), -1);
         }
      }
   }

   private PlayerEntity getCameraPlayer() {
      return !(this.client.getCameraEntity() instanceof PlayerEntity) ? null : (PlayerEntity)this.client.getCameraEntity();
   }

   private LivingEntity getRiddenEntity() {
      PlayerEntity lv = this.getCameraPlayer();
      if (lv != null) {
         Entity lv2 = lv.getVehicle();
         if (lv2 == null) {
            return null;
         }

         if (lv2 instanceof LivingEntity) {
            return (LivingEntity)lv2;
         }
      }

      return null;
   }

   private int getHeartCount(LivingEntity entity) {
      if (entity != null && entity.isLiving()) {
         float f = entity.getMaxHealth();
         int i = (int)(f + 0.5F) / 2;
         if (i > 30) {
            i = 30;
         }

         return i;
      } else {
         return 0;
      }
   }

   private int getHeartRows(int heartCount) {
      return (int)Math.ceil((double)heartCount / 10.0);
   }

   private void renderStatusBars(MatrixStack matrices) {
      PlayerEntity lv = this.getCameraPlayer();
      if (lv != null) {
         int i = MathHelper.ceil(lv.getHealth());
         boolean bl = this.heartJumpEndTick > (long)this.ticks && (this.heartJumpEndTick - (long)this.ticks) / 3L % 2L == 1L;
         long l = Util.getMeasuringTimeMs();
         if (i < this.lastHealthValue && lv.timeUntilRegen > 0) {
            this.lastHealthCheckTime = l;
            this.heartJumpEndTick = (long)(this.ticks + 20);
         } else if (i > this.lastHealthValue && lv.timeUntilRegen > 0) {
            this.lastHealthCheckTime = l;
            this.heartJumpEndTick = (long)(this.ticks + 10);
         }

         if (l - this.lastHealthCheckTime > 1000L) {
            this.lastHealthValue = i;
            this.renderHealthValue = i;
            this.lastHealthCheckTime = l;
         }

         this.lastHealthValue = i;
         int j = this.renderHealthValue;
         this.random.setSeed((long)(this.ticks * 312871));
         HungerManager lv2 = lv.getHungerManager();
         int k = lv2.getFoodLevel();
         int m = this.scaledWidth / 2 - 91;
         int n = this.scaledWidth / 2 + 91;
         int o = this.scaledHeight - 39;
         float f = (float)lv.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
         int p = MathHelper.ceil(lv.getAbsorptionAmount());
         int q = MathHelper.ceil((f + (float)p) / 2.0F / 10.0F);
         int r = Math.max(10 - (q - 2), 3);
         int s = o - (q - 1) * r - 10;
         int t = o - 10;
         int u = p;
         int v = lv.getArmor();
         int w = -1;
         if (lv.hasStatusEffect(StatusEffects.REGENERATION)) {
            w = this.ticks % MathHelper.ceil(f + 5.0F);
         }

         this.client.getProfiler().push("armor");

         for (int x = 0; x < 10; x++) {
            if (v > 0) {
               int y = m + x * 8;
               if (x * 2 + 1 < v) {
                  this.drawTexture(matrices, y, s, 34, 9, 9, 9);
               }

               if (x * 2 + 1 == v) {
                  this.drawTexture(matrices, y, s, 25, 9, 9, 9);
               }

               if (x * 2 + 1 > v) {
                  this.drawTexture(matrices, y, s, 16, 9, 9, 9);
               }
            }
         }

         this.client.getProfiler().swap("health");

         for (int z = MathHelper.ceil((f + (float)p) / 2.0F) - 1; z >= 0; z--) {
            int aa = 16;
            if (lv.hasStatusEffect(StatusEffects.POISON)) {
               aa += 36;
            } else if (lv.hasStatusEffect(StatusEffects.WITHER)) {
               aa += 72;
            }

            int ab = 0;
            if (bl) {
               ab = 1;
            }

            int ac = MathHelper.ceil((float)(z + 1) / 10.0F) - 1;
            int ad = m + z % 10 * 8;
            int ae = o - ac * r;
            if (i <= 4) {
               ae += this.random.nextInt(2);
            }

            if (u <= 0 && z == w) {
               ae -= 2;
            }

            int af = 0;
            if (lv.world.getLevelProperties().isHardcore()) {
               af = 5;
            }

            this.drawTexture(matrices, ad, ae, 16 + ab * 9, 9 * af, 9, 9);
            if (bl) {
               if (z * 2 + 1 < j) {
                  this.drawTexture(matrices, ad, ae, aa + 54, 9 * af, 9, 9);
               }

               if (z * 2 + 1 == j) {
                  this.drawTexture(matrices, ad, ae, aa + 63, 9 * af, 9, 9);
               }
            }

            if (u > 0) {
               if (u == p && p % 2 == 1) {
                  this.drawTexture(matrices, ad, ae, aa + 153, 9 * af, 9, 9);
                  u--;
               } else {
                  this.drawTexture(matrices, ad, ae, aa + 144, 9 * af, 9, 9);
                  u -= 2;
               }
            } else {
               if (z * 2 + 1 < i) {
                  this.drawTexture(matrices, ad, ae, aa + 36, 9 * af, 9, 9);
               }

               if (z * 2 + 1 == i) {
                  this.drawTexture(matrices, ad, ae, aa + 45, 9 * af, 9, 9);
               }
            }
         }

         LivingEntity lv3 = this.getRiddenEntity();
         int ag = this.getHeartCount(lv3);
         if (ag == 0) {
            this.client.getProfiler().swap("food");

            for (int ah = 0; ah < 10; ah++) {
               int ai = o;
               int aj = 16;
               int ak = 0;
               if (lv.hasStatusEffect(StatusEffects.HUNGER)) {
                  aj += 36;
                  ak = 13;
               }

               if (lv.getHungerManager().getSaturationLevel() <= 0.0F && this.ticks % (k * 3 + 1) == 0) {
                  ai = o + (this.random.nextInt(3) - 1);
               }

               int al = n - ah * 8 - 9;
               this.drawTexture(matrices, al, ai, 16 + ak * 9, 27, 9, 9);
               if (ah * 2 + 1 < k) {
                  this.drawTexture(matrices, al, ai, aj + 36, 27, 9, 9);
               }

               if (ah * 2 + 1 == k) {
                  this.drawTexture(matrices, al, ai, aj + 45, 27, 9, 9);
               }
            }

            t -= 10;
         }

         this.client.getProfiler().swap("air");
         int am = lv.getMaxAir();
         int an = Math.min(lv.getAir(), am);
         if (lv.isSubmergedIn(FluidTags.WATER) || an < am) {
            int ao = this.getHeartRows(ag) - 1;
            t -= ao * 10;
            int ap = MathHelper.ceil((double)(an - 2) * 10.0 / (double)am);
            int aq = MathHelper.ceil((double)an * 10.0 / (double)am) - ap;

            for (int ar = 0; ar < ap + aq; ar++) {
               if (ar < ap) {
                  this.drawTexture(matrices, n - ar * 8 - 9, t, 16, 18, 9, 9);
               } else {
                  this.drawTexture(matrices, n - ar * 8 - 9, t, 25, 18, 9, 9);
               }
            }
         }

         this.client.getProfiler().pop();
      }
   }

   private void renderMountHealth(MatrixStack matrices) {
      LivingEntity lv = this.getRiddenEntity();
      if (lv != null) {
         int i = this.getHeartCount(lv);
         if (i != 0) {
            int j = (int)Math.ceil((double)lv.getHealth());
            this.client.getProfiler().swap("mountHealth");
            int k = this.scaledHeight - 39;
            int l = this.scaledWidth / 2 + 91;
            int m = k;
            int n = 0;

            for (boolean bl = false; i > 0; n += 20) {
               int o = Math.min(i, 10);
               i -= o;

               for (int p = 0; p < o; p++) {
                  int q = 52;
                  int r = 0;
                  int s = l - p * 8 - 9;
                  this.drawTexture(matrices, s, m, 52 + r * 9, 9, 9, 9);
                  if (p * 2 + 1 + n < j) {
                     this.drawTexture(matrices, s, m, 88, 9, 9, 9);
                  }

                  if (p * 2 + 1 + n == j) {
                     this.drawTexture(matrices, s, m, 97, 9, 9, 9);
                  }
               }

               m -= 10;
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
      Tessellator lv = Tessellator.getInstance();
      BufferBuilder lv2 = lv.getBuffer();
      lv2.begin(7, VertexFormats.POSITION_TEXTURE);
      lv2.vertex(0.0, (double)this.scaledHeight, -90.0).texture(0.0F, 1.0F).next();
      lv2.vertex((double)this.scaledWidth, (double)this.scaledHeight, -90.0).texture(1.0F, 1.0F).next();
      lv2.vertex((double)this.scaledWidth, 0.0, -90.0).texture(1.0F, 0.0F).next();
      lv2.vertex(0.0, 0.0, -90.0).texture(0.0F, 0.0F).next();
      lv.draw();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableAlphaTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void updateVignetteDarkness(Entity entity) {
      if (entity != null) {
         float f = MathHelper.clamp(1.0F - entity.getBrightnessAtEyes(), 0.0F, 1.0F);
         this.vignetteDarkness = (float)((double)this.vignetteDarkness + (double)(f - this.vignetteDarkness) * 0.01);
      }
   }

   private void renderVignetteOverlay(Entity entity) {
      WorldBorder lv = this.client.world.getWorldBorder();
      float f = (float)lv.getDistanceInsideBorder(entity);
      double d = Math.min(lv.getShrinkingSpeed() * (double)lv.getWarningTime() * 1000.0, Math.abs(lv.getTargetSize() - lv.getSize()));
      double e = Math.max((double)lv.getWarningBlocks(), d);
      if ((double)f < e) {
         f = 1.0F - (float)((double)f / e);
      } else {
         f = 0.0F;
      }

      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.blendFuncSeparate(
         GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO
      );
      if (f > 0.0F) {
         RenderSystem.color4f(0.0F, f, f, 1.0F);
      } else {
         RenderSystem.color4f(this.vignetteDarkness, this.vignetteDarkness, this.vignetteDarkness, 1.0F);
      }

      this.client.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
      Tessellator lv2 = Tessellator.getInstance();
      BufferBuilder lv3 = lv2.getBuffer();
      lv3.begin(7, VertexFormats.POSITION_TEXTURE);
      lv3.vertex(0.0, (double)this.scaledHeight, -90.0).texture(0.0F, 1.0F).next();
      lv3.vertex((double)this.scaledWidth, (double)this.scaledHeight, -90.0).texture(1.0F, 1.0F).next();
      lv3.vertex((double)this.scaledWidth, 0.0, -90.0).texture(1.0F, 0.0F).next();
      lv3.vertex(0.0, 0.0, -90.0).texture(0.0F, 0.0F).next();
      lv2.draw();
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
      Sprite lv = this.client.getBlockRenderManager().getModels().getSprite(Blocks.NETHER_PORTAL.getDefaultState());
      float g = lv.getMinU();
      float h = lv.getMinV();
      float i = lv.getMaxU();
      float j = lv.getMaxV();
      Tessellator lv2 = Tessellator.getInstance();
      BufferBuilder lv3 = lv2.getBuffer();
      lv3.begin(7, VertexFormats.POSITION_TEXTURE);
      lv3.vertex(0.0, (double)this.scaledHeight, -90.0).texture(g, j).next();
      lv3.vertex((double)this.scaledWidth, (double)this.scaledHeight, -90.0).texture(i, j).next();
      lv3.vertex((double)this.scaledWidth, 0.0, -90.0).texture(i, h).next();
      lv3.vertex(0.0, 0.0, -90.0).texture(g, h).next();
      lv2.draw();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableAlphaTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderHotbarItem(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack) {
      if (!stack.isEmpty()) {
         float g = (float)stack.getCooldown() - tickDelta;
         if (g > 0.0F) {
            RenderSystem.pushMatrix();
            float h = 1.0F + g / 5.0F;
            RenderSystem.translatef((float)(x + 8), (float)(y + 12), 0.0F);
            RenderSystem.scalef(1.0F / h, (h + 1.0F) / 2.0F, 1.0F);
            RenderSystem.translatef((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
         }

         this.itemRenderer.renderInGuiWithOverrides(player, stack, x, y);
         if (g > 0.0F) {
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
      Entity lv = this.client.getCameraEntity();
      if (lv != null) {
         this.updateVignetteDarkness(lv);
      }

      if (this.client.player != null) {
         ItemStack lv2 = this.client.player.inventory.getMainHandStack();
         if (lv2.isEmpty()) {
            this.heldItemTooltipFade = 0;
         } else if (this.currentStack.isEmpty() || lv2.getItem() != this.currentStack.getItem() || !lv2.getName().equals(this.currentStack.getName())) {
            this.heldItemTooltipFade = 40;
         } else if (this.heldItemTooltipFade > 0) {
            this.heldItemTooltipFade--;
         }

         this.currentStack = lv2;
      }
   }

   public void setRecordPlayingOverlay(Text arg) {
      this.setOverlayMessage(new TranslatableText("record.nowPlaying", arg), true);
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

   public UUID method_31406(Text arg) {
      String string = TextVisitFactory.method_31402(arg);
      String string2 = StringUtils.substringBetween(string, "<", ">");
      return string2 == null ? Util.NIL_UUID : this.client.getSocialInteractionsManager().method_31407(string2);
   }

   public void addChatMessage(MessageType type, Text text, UUID senderUuid) {
      if (!this.client.shouldBlockMessages(senderUuid)) {
         if (!this.client.options.field_26926 || !this.client.shouldBlockMessages(this.method_31406(text))) {
            for (ClientChatListener lv : this.listeners.get(type)) {
               lv.onChatMessage(type, text, senderUuid);
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
