/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client;

import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
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
import net.minecraft.text.Text;
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

    private void debugWarn(String string, Object ... objectArray) {
        this.client.inGameHud.getChatHud().addMessage(new LiteralText("").append(new TranslatableText("debug.prefix").formatted(Formatting.YELLOW, Formatting.BOLD)).append(" ").append(new TranslatableText(string, objectArray)));
    }

    private void debugError(String string, Object ... objectArray) {
        this.client.inGameHud.getChatHud().addMessage(new LiteralText("").append(new TranslatableText("debug.prefix").formatted(Formatting.RED, Formatting.BOLD)).append(" ").append(new TranslatableText(string, objectArray)));
    }

    private boolean processF3(int key) {
        if (this.debugCrashStartTime > 0L && this.debugCrashStartTime < Util.getMeasuringTimeMs() - 100L) {
            return true;
        }
        switch (key) {
            case 65: {
                this.client.worldRenderer.reload();
                this.debugWarn("debug.reload_chunks.message", new Object[0]);
                return true;
            }
            case 66: {
                boolean bl = !this.client.getEntityRenderDispatcher().shouldRenderHitboxes();
                this.client.getEntityRenderDispatcher().setRenderHitboxes(bl);
                this.debugWarn(bl ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off", new Object[0]);
                return true;
            }
            case 68: {
                if (this.client.inGameHud != null) {
                    this.client.inGameHud.getChatHud().clear(false);
                }
                return true;
            }
            case 70: {
                Option.RENDER_DISTANCE.set(this.client.options, MathHelper.clamp((double)(this.client.options.viewDistance + (Screen.hasShiftDown() ? -1 : 1)), Option.RENDER_DISTANCE.getMin(), Option.RENDER_DISTANCE.getMax()));
                this.debugWarn("debug.cycle_renderdistance.message", this.client.options.viewDistance);
                return true;
            }
            case 71: {
                boolean bl = this.client.debugRenderer.toggleShowChunkBorder();
                this.debugWarn(bl ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off", new Object[0]);
                return true;
            }
            case 72: {
                this.client.options.advancedItemTooltips = !this.client.options.advancedItemTooltips;
                this.debugWarn(this.client.options.advancedItemTooltips ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off", new Object[0]);
                this.client.options.write();
                return true;
            }
            case 73: {
                if (!this.client.player.getReducedDebugInfo()) {
                    this.copyLookAt(this.client.player.hasPermissionLevel(2), !Screen.hasShiftDown());
                }
                return true;
            }
            case 78: {
                if (!this.client.player.hasPermissionLevel(2)) {
                    this.debugWarn("debug.creative_spectator.error", new Object[0]);
                } else if (!this.client.player.isSpectator()) {
                    this.client.player.sendChatMessage("/gamemode spectator");
                } else {
                    this.client.player.sendChatMessage("/gamemode " + this.client.interactionManager.getPreviousGameMode().getName());
                }
                return true;
            }
            case 293: {
                if (!this.client.player.hasPermissionLevel(2)) {
                    this.debugWarn("debug.gamemodes.error", new Object[0]);
                } else {
                    this.client.openScreen(new GameModeSelectionScreen());
                }
                return true;
            }
            case 80: {
                this.client.options.pauseOnLostFocus = !this.client.options.pauseOnLostFocus;
                this.client.options.write();
                this.debugWarn(this.client.options.pauseOnLostFocus ? "debug.pause_focus.on" : "debug.pause_focus.off", new Object[0]);
                return true;
            }
            case 81: {
                this.debugWarn("debug.help.message", new Object[0]);
                ChatHud chatHud = this.client.inGameHud.getChatHud();
                chatHud.addMessage(new TranslatableText("debug.reload_chunks.help"));
                chatHud.addMessage(new TranslatableText("debug.show_hitboxes.help"));
                chatHud.addMessage(new TranslatableText("debug.copy_location.help"));
                chatHud.addMessage(new TranslatableText("debug.clear_chat.help"));
                chatHud.addMessage(new TranslatableText("debug.cycle_renderdistance.help"));
                chatHud.addMessage(new TranslatableText("debug.chunk_boundaries.help"));
                chatHud.addMessage(new TranslatableText("debug.advanced_tooltips.help"));
                chatHud.addMessage(new TranslatableText("debug.inspect.help"));
                chatHud.addMessage(new TranslatableText("debug.creative_spectator.help"));
                chatHud.addMessage(new TranslatableText("debug.pause_focus.help"));
                chatHud.addMessage(new TranslatableText("debug.help.help"));
                chatHud.addMessage(new TranslatableText("debug.reload_resourcepacks.help"));
                chatHud.addMessage(new TranslatableText("debug.pause.help"));
                chatHud.addMessage(new TranslatableText("debug.gamemodes.help"));
                return true;
            }
            case 84: {
                this.debugWarn("debug.reload_resourcepacks.message", new Object[0]);
                this.client.reloadResources();
                return true;
            }
            case 67: {
                if (this.client.player.getReducedDebugInfo()) {
                    return false;
                }
                ClientPlayNetworkHandler clientPlayNetworkHandler = this.client.player.networkHandler;
                if (clientPlayNetworkHandler == null) {
                    return false;
                }
                this.debugWarn("debug.copy_location.message", new Object[0]);
                this.setClipboard(String.format(Locale.ROOT, "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f", this.client.player.world.getRegistryKey().getValue(), this.client.player.getX(), this.client.player.getY(), this.client.player.getZ(), Float.valueOf(this.client.player.yaw), Float.valueOf(this.client.player.pitch)));
                return true;
            }
        }
        return false;
    }

    private void copyLookAt(boolean bl, boolean bl2) {
        HitResult hitResult = this.client.crosshairTarget;
        if (hitResult == null) {
            return;
        }
        switch (hitResult.getType()) {
            case BLOCK: {
                BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
                BlockState _snowman2 = this.client.player.world.getBlockState(blockPos);
                if (bl) {
                    if (bl2) {
                        this.client.player.networkHandler.getDataQueryHandler().queryBlockNbt(blockPos, compoundTag -> {
                            this.copyBlock(_snowman2, blockPos, (CompoundTag)compoundTag);
                            this.debugWarn("debug.inspect.server.block", new Object[0]);
                        });
                        break;
                    }
                    BlockEntity blockEntity = this.client.player.world.getBlockEntity(blockPos);
                    CompoundTag _snowman3 = blockEntity != null ? blockEntity.toTag(new CompoundTag()) : null;
                    this.copyBlock(_snowman2, blockPos, _snowman3);
                    this.debugWarn("debug.inspect.client.block", new Object[0]);
                    break;
                }
                this.copyBlock(_snowman2, blockPos, null);
                this.debugWarn("debug.inspect.client.block", new Object[0]);
                break;
            }
            case ENTITY: {
                Entity entity = ((EntityHitResult)hitResult).getEntity();
                Identifier _snowman4 = Registry.ENTITY_TYPE.getId(entity.getType());
                if (bl) {
                    if (bl2) {
                        this.client.player.networkHandler.getDataQueryHandler().queryEntityNbt(entity.getEntityId(), compoundTag -> {
                            this.copyEntity(_snowman4, entity.getPos(), (CompoundTag)compoundTag);
                            this.debugWarn("debug.inspect.server.entity", new Object[0]);
                        });
                        break;
                    }
                    CompoundTag compoundTag2 = entity.toTag(new CompoundTag());
                    this.copyEntity(_snowman4, entity.getPos(), compoundTag2);
                    this.debugWarn("debug.inspect.client.entity", new Object[0]);
                    break;
                }
                this.copyEntity(_snowman4, entity.getPos(), null);
                this.debugWarn("debug.inspect.client.entity", new Object[0]);
                break;
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
        StringBuilder stringBuilder = new StringBuilder(BlockArgumentParser.stringifyBlockState(state));
        if (tag != null) {
            stringBuilder.append(tag);
        }
        String _snowman2 = String.format(Locale.ROOT, "/setblock %d %d %d %s", pos.getX(), pos.getY(), pos.getZ(), stringBuilder);
        this.setClipboard(_snowman2);
    }

    private void copyEntity(Identifier id, Vec3d pos, @Nullable CompoundTag tag) {
        String string;
        if (tag != null) {
            tag.remove("UUID");
            tag.remove("Pos");
            tag.remove("Dimension");
            String string2 = tag.toText().getString();
            string = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f %s", id.toString(), pos.x, pos.y, pos.z, string2);
        } else {
            string = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f", id.toString(), pos.x, pos.y, pos.z);
        }
        this.setClipboard(string);
    }

    public void onKey(long window, int key, int scancode, int n, int n2) {
        Object object;
        if (window != this.client.getWindow().getHandle()) {
            return;
        }
        if (this.debugCrashStartTime > 0L) {
            if (!InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 67) || !InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 292)) {
                this.debugCrashStartTime = -1L;
            }
        } else if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 67) && InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 292)) {
            this.switchF3State = true;
            this.debugCrashStartTime = Util.getMeasuringTimeMs();
            this.debugCrashLastLogTime = Util.getMeasuringTimeMs();
            this.debugCrashElapsedTime = 0L;
        }
        Screen screen = this.client.currentScreen;
        if (!(n != 1 || this.client.currentScreen instanceof ControlsOptionsScreen && ((ControlsOptionsScreen)screen).time > Util.getMeasuringTimeMs() - 20L)) {
            if (this.client.options.keyFullscreen.matchesKey(key, scancode)) {
                this.client.getWindow().toggleFullscreen();
                this.client.options.fullscreen = this.client.getWindow().isFullscreen();
                this.client.options.write();
                return;
            }
            if (this.client.options.keyScreenshot.matchesKey(key, scancode)) {
                if (Screen.hasControlDown()) {
                    // empty if block
                }
                ScreenshotUtils.saveScreenshot(this.client.runDirectory, this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight(), this.client.getFramebuffer(), text -> this.client.execute(() -> this.client.inGameHud.getChatHud().addMessage((Text)text)));
                return;
            }
        }
        boolean bl = _snowman = screen == null || !(screen.getFocused() instanceof TextFieldWidget) || !((TextFieldWidget)screen.getFocused()).isActive();
        if (n != 0 && key == 66 && Screen.hasControlDown() && _snowman) {
            Option.NARRATOR.cycle(this.client.options, 1);
            if (screen instanceof NarratorOptionsScreen) {
                ((NarratorOptionsScreen)screen).updateNarratorButtonText();
            }
        }
        if (screen != null) {
            object = new boolean[]{false};
            Screen.wrapScreenError(() -> this.method_1454(n, (boolean[])object, screen, key, scancode, n2), "keyPressed event handler", screen.getClass().getCanonicalName());
            if (object[0]) {
                return;
            }
        }
        if (this.client.currentScreen == null || this.client.currentScreen.passEvents) {
            object = InputUtil.fromKeyCode(key, scancode);
            if (n == 0) {
                KeyBinding.setKeyPressed((InputUtil.Key)object, false);
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
                boolean bl2 = false;
                if (this.client.currentScreen == null) {
                    if (key == 256) {
                        _snowman = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 292);
                        this.client.openPauseMenu(_snowman);
                    }
                    bl2 = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 292) && this.processF3(key);
                    this.switchF3State |= bl2;
                    if (key == 290) {
                        boolean bl3 = this.client.options.hudHidden = !this.client.options.hudHidden;
                    }
                }
                if (bl2) {
                    KeyBinding.setKeyPressed((InputUtil.Key)object, false);
                } else {
                    KeyBinding.setKeyPressed((InputUtil.Key)object, true);
                    KeyBinding.onKeyPressed((InputUtil.Key)object);
                }
                if (this.client.options.debugProfilerEnabled && key >= 48 && key <= 57) {
                    this.client.handleProfilerKeyPress(key - 48);
                }
            }
        }
    }

    private void onChar(long window, int n, int n2) {
        if (window != this.client.getWindow().getHandle()) {
            return;
        }
        Screen screen = this.client.currentScreen;
        if (screen == null || this.client.getOverlay() != null) {
            return;
        }
        if (Character.charCount(n) == 1) {
            Screen.wrapScreenError(() -> screen.charTyped((char)n, n2), "charTyped event handler", screen.getClass().getCanonicalName());
        } else {
            for (char c : Character.toChars(n)) {
                Screen.wrapScreenError(() -> screen.charTyped(c, n2), "charTyped event handler", screen.getClass().getCanonicalName());
            }
        }
    }

    public void setRepeatEvents(boolean repeatEvents) {
        this.repeatEvents = repeatEvents;
    }

    public void setup(long l2) {
        InputUtil.setKeyboardCallbacks(l2, (l, n, n2, n3, n4) -> this.client.execute(() -> this.onKey(l, n, n2, n3, n4)), (l, n, n2) -> this.client.execute(() -> this.onChar(l, n, n2)));
    }

    public String getClipboard() {
        return this.clipboard.getClipboard(this.client.getWindow().getHandle(), (error, description) -> {
            if (error != 65545) {
                this.client.getWindow().logGlError(error, description);
            }
        });
    }

    public void setClipboard(String string) {
        this.clipboard.setClipboard(this.client.getWindow().getHandle(), string);
    }

    public void pollDebugCrash() {
        if (this.debugCrashStartTime > 0L) {
            long l = Util.getMeasuringTimeMs();
            _snowman = 10000L - (l - this.debugCrashStartTime);
            _snowman = l - this.debugCrashLastLogTime;
            if (_snowman < 0L) {
                if (Screen.hasControlDown()) {
                    GlfwUtil.makeJvmCrash();
                }
                throw new CrashException(new CrashReport("Manually triggered debug crash", new Throwable()));
            }
            if (_snowman >= 1000L) {
                if (this.debugCrashElapsedTime == 0L) {
                    this.debugWarn("debug.crash.message", new Object[0]);
                } else {
                    this.debugError("debug.crash.warning", MathHelper.ceil((float)_snowman / 1000.0f));
                }
                this.debugCrashLastLogTime = l;
                ++this.debugCrashElapsedTime;
            }
        }
    }

    private /* synthetic */ void method_1454(int n, boolean[] blArray, ParentElement parentElement, int n2, int n3, int n4) {
        if (n == 1 || n == 2 && this.repeatEvents) {
            blArray[0] = parentElement.keyPressed(n2, n3, n4);
        } else if (n == 0) {
            blArray[0] = parentElement.keyReleased(n2, n3, n4);
        }
    }
}

