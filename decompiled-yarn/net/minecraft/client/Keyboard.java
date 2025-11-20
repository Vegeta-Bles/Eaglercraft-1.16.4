package net.minecraft.client;

import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.screen.GameModeSelectionScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.options.NarratorOptionsScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.Clipboard;
import net.minecraft.client.util.GlfwUtil;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.ScreenshotUtils;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

public class Keyboard {
   private final MinecraftClient client;
   private boolean repeatEvents;
   private final Clipboard clipboard = new Clipboard();
   private long debugCrashStartTime = -1L;
   private long debugCrashLastLogTime = -1L;
   private long debugCrashElapsedTime = -1L;
   private boolean switchF3State;

   public Keyboard(MinecraftClient client) {
      this.client = client;
   }

   private void debugWarn(String _snowman, Object... _snowman) {
      this.client
         .inGameHud
         .getChatHud()
         .addMessage(
            new LiteralText("")
               .append(new TranslatableText("debug.prefix").formatted(new Formatting[]{Formatting.YELLOW, Formatting.BOLD}))
               .append(" ")
               .append(new TranslatableText(_snowman, _snowman))
         );
   }

   private void debugError(String _snowman, Object... _snowman) {
      this.client
         .inGameHud
         .getChatHud()
         .addMessage(
            new LiteralText("")
               .append(new TranslatableText("debug.prefix").formatted(new Formatting[]{Formatting.RED, Formatting.BOLD}))
               .append(" ")
               .append(new TranslatableText(_snowman, _snowman))
         );
   }

   private boolean processF3(int key) {
      if (this.debugCrashStartTime > 0L && this.debugCrashStartTime < Util.getMeasuringTimeMs() - 100L) {
         return true;
      } else {
         switch (key) {
            case 65:
               this.client.worldRenderer.reload();
               this.debugWarn("debug.reload_chunks.message");
               return true;
            case 66: {
               boolean _snowman = !this.client.getEntityRenderDispatcher().shouldRenderHitboxes();
               this.client.getEntityRenderDispatcher().setRenderHitboxes(_snowman);
               this.debugWarn(_snowman ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off");
               return true;
            }
            case 67:
               if (this.client.player.getReducedDebugInfo()) {
                  return false;
               } else {
                  ClientPlayNetworkHandler _snowmanx = this.client.player.networkHandler;
                  if (_snowmanx == null) {
                     return false;
                  }

                  this.debugWarn("debug.copy_location.message");
                  this.setClipboard(
                     String.format(
                        Locale.ROOT,
                        "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f",
                        this.client.player.world.getRegistryKey().getValue(),
                        this.client.player.getX(),
                        this.client.player.getY(),
                        this.client.player.getZ(),
                        this.client.player.yaw,
                        this.client.player.pitch
                     )
                  );
                  return true;
               }
            case 68:
               if (this.client.inGameHud != null) {
                  this.client.inGameHud.getChatHud().clear(false);
               }

               return true;
            case 70:
               Option.RENDER_DISTANCE
                  .set(
                     this.client.options,
                     MathHelper.clamp(
                        (double)(this.client.options.viewDistance + (Screen.hasShiftDown() ? -1 : 1)),
                        Option.RENDER_DISTANCE.getMin(),
                        Option.RENDER_DISTANCE.getMax()
                     )
                  );
               this.debugWarn("debug.cycle_renderdistance.message", this.client.options.viewDistance);
               return true;
            case 71: {
               boolean _snowman = this.client.debugRenderer.toggleShowChunkBorder();
               this.debugWarn(_snowman ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off");
               return true;
            }
            case 72:
               this.client.options.advancedItemTooltips = !this.client.options.advancedItemTooltips;
               this.debugWarn(this.client.options.advancedItemTooltips ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off");
               this.client.options.write();
               return true;
            case 73:
               if (!this.client.player.getReducedDebugInfo()) {
                  this.copyLookAt(this.client.player.hasPermissionLevel(2), !Screen.hasShiftDown());
               }

               return true;
            case 78:
               if (!this.client.player.hasPermissionLevel(2)) {
                  this.debugWarn("debug.creative_spectator.error");
               } else if (!this.client.player.isSpectator()) {
                  this.client.player.sendChatMessage("/gamemode spectator");
               } else {
                  this.client.player.sendChatMessage("/gamemode " + this.client.interactionManager.getPreviousGameMode().getName());
               }

               return true;
            case 80:
               this.client.options.pauseOnLostFocus = !this.client.options.pauseOnLostFocus;
               this.client.options.write();
               this.debugWarn(this.client.options.pauseOnLostFocus ? "debug.pause_focus.on" : "debug.pause_focus.off");
               return true;
            case 81: {
               this.debugWarn("debug.help.message");
               ChatHud _snowman = this.client.inGameHud.getChatHud();
               _snowman.addMessage(new TranslatableText("debug.reload_chunks.help"));
               _snowman.addMessage(new TranslatableText("debug.show_hitboxes.help"));
               _snowman.addMessage(new TranslatableText("debug.copy_location.help"));
               _snowman.addMessage(new TranslatableText("debug.clear_chat.help"));
               _snowman.addMessage(new TranslatableText("debug.cycle_renderdistance.help"));
               _snowman.addMessage(new TranslatableText("debug.chunk_boundaries.help"));
               _snowman.addMessage(new TranslatableText("debug.advanced_tooltips.help"));
               _snowman.addMessage(new TranslatableText("debug.inspect.help"));
               _snowman.addMessage(new TranslatableText("debug.creative_spectator.help"));
               _snowman.addMessage(new TranslatableText("debug.pause_focus.help"));
               _snowman.addMessage(new TranslatableText("debug.help.help"));
               _snowman.addMessage(new TranslatableText("debug.reload_resourcepacks.help"));
               _snowman.addMessage(new TranslatableText("debug.pause.help"));
               _snowman.addMessage(new TranslatableText("debug.gamemodes.help"));
               return true;
            }
            case 84:
               this.debugWarn("debug.reload_resourcepacks.message");
               this.client.reloadResources();
               return true;
            case 293:
               if (!this.client.player.hasPermissionLevel(2)) {
                  this.debugWarn("debug.gamemodes.error");
               } else {
                  this.client.openScreen(new GameModeSelectionScreen());
               }

               return true;
            default:
               return false;
         }
      }
   }

   private void copyLookAt(boolean _snowman, boolean _snowman) {
      HitResult _snowmanxx = this.client.crosshairTarget;
      if (_snowmanxx != null) {
         switch (_snowmanxx.getType()) {
            case BLOCK:
               BlockPos _snowmanxxxxx = ((BlockHitResult)_snowmanxx).getBlockPos();
               BlockState _snowmanxxxxxx = this.client.player.world.getBlockState(_snowmanxxxxx);
               if (_snowman) {
                  if (_snowman) {
                     this.client.player.networkHandler.getDataQueryHandler().queryBlockNbt(_snowmanxxxxx, _snowmanxxxxxxx -> {
                        this.copyBlock(_snowman, _snowman, _snowmanxxxxxxx);
                        this.debugWarn("debug.inspect.server.block");
                     });
                  } else {
                     BlockEntity _snowmanxxxxxxx = this.client.player.world.getBlockEntity(_snowmanxxxxx);
                     CompoundTag _snowmanxxxxxxxx = _snowmanxxxxxxx != null ? _snowmanxxxxxxx.toTag(new CompoundTag()) : null;
                     this.copyBlock(_snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxx);
                     this.debugWarn("debug.inspect.client.block");
                  }
               } else {
                  this.copyBlock(_snowmanxxxxxx, _snowmanxxxxx, null);
                  this.debugWarn("debug.inspect.client.block");
               }
               break;
            case ENTITY:
               Entity _snowmanxxx = ((EntityHitResult)_snowmanxx).getEntity();
               Identifier _snowmanxxxx = Registry.ENTITY_TYPE.getId(_snowmanxxx.getType());
               if (_snowman) {
                  if (_snowman) {
                     this.client.player.networkHandler.getDataQueryHandler().queryEntityNbt(_snowmanxxx.getEntityId(), _snowmanxxxxx -> {
                        this.copyEntity(_snowman, _snowman.getPos(), _snowmanxxxxx);
                        this.debugWarn("debug.inspect.server.entity");
                     });
                  } else {
                     CompoundTag _snowmanxxxxx = _snowmanxxx.toTag(new CompoundTag());
                     this.copyEntity(_snowmanxxxx, _snowmanxxx.getPos(), _snowmanxxxxx);
                     this.debugWarn("debug.inspect.client.entity");
                  }
               } else {
                  this.copyEntity(_snowmanxxxx, _snowmanxxx.getPos(), null);
                  this.debugWarn("debug.inspect.client.entity");
               }
         }
      }
   }

   private void copyBlock(BlockState state, BlockPos pos, @Nullable CompoundTag tag) {
      if (tag != null) {
         tag.remove("x");
         tag.remove("y");
         tag.remove("z");
         tag.remove("id");
      }

      StringBuilder _snowman = new StringBuilder(BlockArgumentParser.stringifyBlockState(state));
      if (tag != null) {
         _snowman.append(tag);
      }

      String _snowmanx = String.format(Locale.ROOT, "/setblock %d %d %d %s", pos.getX(), pos.getY(), pos.getZ(), _snowman);
      this.setClipboard(_snowmanx);
   }

   private void copyEntity(Identifier id, Vec3d pos, @Nullable CompoundTag tag) {
      String _snowman;
      if (tag != null) {
         tag.remove("UUID");
         tag.remove("Pos");
         tag.remove("Dimension");
         String _snowmanx = tag.toText().getString();
         _snowman = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f %s", id.toString(), pos.x, pos.y, pos.z, _snowmanx);
      } else {
         _snowman = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f", id.toString(), pos.x, pos.y, pos.z);
      }

      this.setClipboard(_snowman);
   }

   public void onKey(long window, int key, int scancode, int _snowman, int _snowman) {
      if (window == this.client.getWindow().getHandle()) {
         if (this.debugCrashStartTime > 0L) {
            if (!InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 67)
               || !InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 292)) {
               this.debugCrashStartTime = -1L;
            }
         } else if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 67)
            && InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 292)) {
            this.switchF3State = true;
            this.debugCrashStartTime = Util.getMeasuringTimeMs();
            this.debugCrashLastLogTime = Util.getMeasuringTimeMs();
            this.debugCrashElapsedTime = 0L;
         }

         ParentElement _snowmanxx = this.client.currentScreen;
         if (_snowman == 1 && (!(this.client.currentScreen instanceof ControlsOptionsScreen) || ((ControlsOptionsScreen)_snowmanxx).time <= Util.getMeasuringTimeMs() - 20L)) {
            if (this.client.options.keyFullscreen.matchesKey(key, scancode)) {
               this.client.getWindow().toggleFullscreen();
               this.client.options.fullscreen = this.client.getWindow().isFullscreen();
               this.client.options.write();
               return;
            }

            if (this.client.options.keyScreenshot.matchesKey(key, scancode)) {
               if (Screen.hasControlDown()) {
               }

               ScreenshotUtils.saveScreenshot(
                  this.client.runDirectory,
                  this.client.getWindow().getFramebufferWidth(),
                  this.client.getWindow().getFramebufferHeight(),
                  this.client.getFramebuffer(),
                  _snowmanxxx -> this.client.execute(() -> this.client.inGameHud.getChatHud().addMessage(_snowmanxx))
               );
               return;
            }
         }

         boolean _snowmanxxx = _snowmanxx == null || !(_snowmanxx.getFocused() instanceof TextFieldWidget) || !((TextFieldWidget)_snowmanxx.getFocused()).isActive();
         if (_snowman != 0 && key == 66 && Screen.hasControlDown() && _snowmanxxx) {
            Option.NARRATOR.cycle(this.client.options, 1);
            if (_snowmanxx instanceof NarratorOptionsScreen) {
               ((NarratorOptionsScreen)_snowmanxx).updateNarratorButtonText();
            }
         }

         if (_snowmanxx != null) {
            boolean[] _snowmanxxxx = new boolean[]{false};
            Screen.wrapScreenError(() -> {
               if (_snowman != 1 && (_snowman != 2 || !this.repeatEvents)) {
                  if (_snowman == 0) {
                     _snowman[0] = _snowman.keyReleased(key, scancode, _snowman);
                  }
               } else {
                  _snowman[0] = _snowman.keyPressed(key, scancode, _snowman);
               }
            }, "keyPressed event handler", _snowmanxx.getClass().getCanonicalName());
            if (_snowmanxxxx[0]) {
               return;
            }
         }

         if (this.client.currentScreen == null || this.client.currentScreen.passEvents) {
            InputUtil.Key _snowmanxxxx = InputUtil.fromKeyCode(key, scancode);
            if (_snowman == 0) {
               KeyBinding.setKeyPressed(_snowmanxxxx, false);
               if (key == 292) {
                  if (this.switchF3State) {
                     this.switchF3State = false;
                  } else {
                     this.client.options.debugEnabled = !this.client.options.debugEnabled;
                     this.client.options.debugProfilerEnabled = this.client.options.debugEnabled && Screen.hasShiftDown();
                     this.client.options.debugTpsEnabled = this.client.options.debugEnabled && Screen.hasAltDown();
                  }
               }
            } else {
               if (key == 293 && this.client.gameRenderer != null) {
                  this.client.gameRenderer.toggleShadersEnabled();
               }

               boolean _snowmanxxxxx = false;
               if (this.client.currentScreen == null) {
                  if (key == 256) {
                     boolean _snowmanxxxxxx = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 292);
                     this.client.openPauseMenu(_snowmanxxxxxx);
                  }

                  _snowmanxxxxx = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 292) && this.processF3(key);
                  this.switchF3State |= _snowmanxxxxx;
                  if (key == 290) {
                     this.client.options.hudHidden = !this.client.options.hudHidden;
                  }
               }

               if (_snowmanxxxxx) {
                  KeyBinding.setKeyPressed(_snowmanxxxx, false);
               } else {
                  KeyBinding.setKeyPressed(_snowmanxxxx, true);
                  KeyBinding.onKeyPressed(_snowmanxxxx);
               }

               if (this.client.options.debugProfilerEnabled && key >= 48 && key <= 57) {
                  this.client.handleProfilerKeyPress(key - 48);
               }
            }
         }
      }
   }

   private void onChar(long window, int _snowman, int _snowman) {
      if (window == this.client.getWindow().getHandle()) {
         Element _snowmanxx = this.client.currentScreen;
         if (_snowmanxx != null && this.client.getOverlay() == null) {
            if (Character.charCount(_snowman) == 1) {
               Screen.wrapScreenError(() -> _snowman.charTyped((char)_snowman, _snowman), "charTyped event handler", _snowmanxx.getClass().getCanonicalName());
            } else {
               for (char _snowmanxxx : Character.toChars(_snowman)) {
                  Screen.wrapScreenError(() -> _snowman.charTyped(_snowman, _snowman), "charTyped event handler", _snowmanxx.getClass().getCanonicalName());
               }
            }
         }
      }
   }

   public void setRepeatEvents(boolean repeatEvents) {
      this.repeatEvents = repeatEvents;
   }

   public void setup(long _snowman) {
      InputUtil.setKeyboardCallbacks(
         _snowman,
         (_snowmanxxxxx, _snowmanxxxx, _snowmanxxx, _snowmanxx, _snowmanx) -> this.client.execute(() -> this.onKey(_snowmanxxxxx, _snowmanxxxx, _snowmanxxx, _snowmanxx, _snowmanx)),
         (_snowmanxxx, _snowmanxx, _snowmanx) -> this.client.execute(() -> this.onChar(_snowmanxxx, _snowmanxx, _snowmanx))
      );
   }

   public String getClipboard() {
      return this.clipboard.getClipboard(this.client.getWindow().getHandle(), (error, description) -> {
         if (error != 65545) {
            this.client.getWindow().logGlError(error, description);
         }
      });
   }

   public void setClipboard(String _snowman) {
      this.clipboard.setClipboard(this.client.getWindow().getHandle(), _snowman);
   }

   public void pollDebugCrash() {
      if (this.debugCrashStartTime > 0L) {
         long _snowman = Util.getMeasuringTimeMs();
         long _snowmanx = 10000L - (_snowman - this.debugCrashStartTime);
         long _snowmanxx = _snowman - this.debugCrashLastLogTime;
         if (_snowmanx < 0L) {
            if (Screen.hasControlDown()) {
               GlfwUtil.makeJvmCrash();
            }

            throw new CrashException(new CrashReport("Manually triggered debug crash", new Throwable()));
         }

         if (_snowmanxx >= 1000L) {
            if (this.debugCrashElapsedTime == 0L) {
               this.debugWarn("debug.crash.message");
            } else {
               this.debugError("debug.crash.warning", MathHelper.ceil((float)_snowmanx / 1000.0F));
            }

            this.debugCrashLastLogTime = _snowman;
            this.debugCrashElapsedTime++;
         }
      }
   }
}
