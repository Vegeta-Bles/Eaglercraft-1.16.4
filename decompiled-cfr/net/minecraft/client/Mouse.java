/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.glfw.GLFWDropCallback
 */
package net.minecraft.client;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.GlfwUtil;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.SmoothUtil;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFWDropCallback;

public class Mouse {
    private final MinecraftClient client;
    private boolean leftButtonClicked;
    private boolean middleButtonClicked;
    private boolean rightButtonClicked;
    private double x;
    private double y;
    private int controlLeftTicks;
    private int activeButton = -1;
    private boolean hasResolutionChanged = true;
    private int field_1796;
    private double glfwTime;
    private final SmoothUtil cursorXSmoother = new SmoothUtil();
    private final SmoothUtil cursorYSmoother = new SmoothUtil();
    private double cursorDeltaX;
    private double cursorDeltaY;
    private double eventDeltaWheel;
    private double lastMouseUpdateTime = Double.MIN_VALUE;
    private boolean cursorLocked;

    public Mouse(MinecraftClient client) {
        this.client = client;
    }

    private void onMouseButton(long window, int button, int action, int mods) {
        boolean bl;
        if (window != this.client.getWindow().getHandle()) {
            return;
        }
        boolean bl2 = bl = action == 1;
        if (MinecraftClient.IS_SYSTEM_MAC && button == 0) {
            if (bl) {
                if ((mods & 2) == 2) {
                    button = 1;
                    ++this.controlLeftTicks;
                }
            } else if (this.controlLeftTicks > 0) {
                button = 1;
                --this.controlLeftTicks;
            }
        }
        int _snowman2 = button;
        if (bl) {
            if (this.client.options.touchscreen && this.field_1796++ > 0) {
                return;
            }
            this.activeButton = _snowman2;
            this.glfwTime = GlfwUtil.getTime();
        } else if (this.activeButton != -1) {
            if (this.client.options.touchscreen && --this.field_1796 > 0) {
                return;
            }
            this.activeButton = -1;
        }
        boolean[] _snowman3 = new boolean[]{false};
        if (this.client.overlay == null) {
            if (this.client.currentScreen == null) {
                if (!this.cursorLocked && bl) {
                    this.lockCursor();
                }
            } else {
                double d = this.x * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
                _snowman = this.y * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
                if (bl) {
                    Screen.wrapScreenError(() -> {
                        blArray[0] = this.client.currentScreen.mouseClicked(d, _snowman, _snowman2);
                    }, "mouseClicked event handler", this.client.currentScreen.getClass().getCanonicalName());
                } else {
                    Screen.wrapScreenError(() -> {
                        blArray[0] = this.client.currentScreen.mouseReleased(d, _snowman, _snowman2);
                    }, "mouseReleased event handler", this.client.currentScreen.getClass().getCanonicalName());
                }
            }
        }
        if (!_snowman3[0] && (this.client.currentScreen == null || this.client.currentScreen.passEvents) && this.client.overlay == null) {
            if (_snowman2 == 0) {
                this.leftButtonClicked = bl;
            } else if (_snowman2 == 2) {
                this.middleButtonClicked = bl;
            } else if (_snowman2 == 1) {
                this.rightButtonClicked = bl;
            }
            KeyBinding.setKeyPressed(InputUtil.Type.MOUSE.createFromCode(_snowman2), bl);
            if (bl) {
                if (this.client.player.isSpectator() && _snowman2 == 2) {
                    this.client.inGameHud.getSpectatorHud().useSelectedCommand();
                } else {
                    KeyBinding.onKeyPressed(InputUtil.Type.MOUSE.createFromCode(_snowman2));
                }
            }
        }
    }

    private void onMouseScroll(long window, double horizontal, double vertical) {
        if (window == MinecraftClient.getInstance().getWindow().getHandle()) {
            double d = (this.client.options.discreteMouseScroll ? Math.signum(vertical) : vertical) * this.client.options.mouseWheelSensitivity;
            if (this.client.overlay == null) {
                if (this.client.currentScreen != null) {
                    _snowman = this.x * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
                    _snowman = this.y * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
                    this.client.currentScreen.mouseScrolled(_snowman, _snowman, d);
                } else if (this.client.player != null) {
                    if (this.eventDeltaWheel != 0.0 && Math.signum(d) != Math.signum(this.eventDeltaWheel)) {
                        this.eventDeltaWheel = 0.0;
                    }
                    this.eventDeltaWheel += d;
                    float f = (int)this.eventDeltaWheel;
                    if (f == 0.0f) {
                        return;
                    }
                    this.eventDeltaWheel -= (double)f;
                    if (this.client.player.isSpectator()) {
                        if (this.client.inGameHud.getSpectatorHud().isOpen()) {
                            this.client.inGameHud.getSpectatorHud().cycleSlot(-f);
                        } else {
                            _snowman = MathHelper.clamp(this.client.player.abilities.getFlySpeed() + f * 0.005f, 0.0f, 0.2f);
                            this.client.player.abilities.setFlySpeed(_snowman);
                        }
                    } else {
                        this.client.player.inventory.scrollInHotbar(f);
                    }
                }
            }
        }
    }

    private void method_29616(long l, List<Path> list) {
        if (this.client.currentScreen != null) {
            this.client.currentScreen.filesDragged(list);
        }
    }

    public void setup(long l4) {
        InputUtil.setMouseCallbacks(l4, (l, d, d2) -> this.client.execute(() -> this.onCursorPos(l, d, d2)), (l, n, n2, n3) -> this.client.execute(() -> this.onMouseButton(l, n, n2, n3)), (l, d, d2) -> this.client.execute(() -> this.onMouseScroll(l, d, d2)), (l3, n, l2) -> {
            long l3;
            Path[] pathArray = new Path[n];
            for (int i = 0; i < n; ++i) {
                pathArray[i] = Paths.get(GLFWDropCallback.getName((long)l2, (int)i), new String[0]);
            }
            this.client.execute(() -> this.method_29616(l3, Arrays.asList(pathArray)));
        });
    }

    private void onCursorPos(long window, double x, double y) {
        Screen screen;
        if (window != MinecraftClient.getInstance().getWindow().getHandle()) {
            return;
        }
        if (this.hasResolutionChanged) {
            this.x = x;
            this.y = y;
            this.hasResolutionChanged = false;
        }
        if ((screen = this.client.currentScreen) != null && this.client.overlay == null) {
            double d = x * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
            _snowman = y * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
            Screen.wrapScreenError(() -> screen.mouseMoved(d, _snowman), "mouseMoved event handler", screen.getClass().getCanonicalName());
            if (this.activeButton != -1 && this.glfwTime > 0.0) {
                _snowman = (x - this.x) * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
                _snowman = (y - this.y) * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
                Screen.wrapScreenError(() -> screen.mouseDragged(d, _snowman, this.activeButton, _snowman, _snowman), "mouseDragged event handler", screen.getClass().getCanonicalName());
            }
        }
        this.client.getProfiler().push("mouse");
        if (this.isCursorLocked() && this.client.isWindowFocused()) {
            this.cursorDeltaX += x - this.x;
            this.cursorDeltaY += y - this.y;
        }
        this.updateMouse();
        this.x = x;
        this.y = y;
        this.client.getProfiler().pop();
    }

    public void updateMouse() {
        double d = GlfwUtil.getTime();
        _snowman = d - this.lastMouseUpdateTime;
        this.lastMouseUpdateTime = d;
        if (!this.isCursorLocked() || !this.client.isWindowFocused()) {
            this.cursorDeltaX = 0.0;
            this.cursorDeltaY = 0.0;
            return;
        }
        _snowman = this.client.options.mouseSensitivity * (double)0.6f + (double)0.2f;
        _snowman = _snowman * _snowman * _snowman * 8.0;
        if (this.client.options.smoothCameraEnabled) {
            _snowman = this.cursorXSmoother.smooth(this.cursorDeltaX * _snowman, _snowman * _snowman);
            _snowman = this.cursorYSmoother.smooth(this.cursorDeltaY * _snowman, _snowman * _snowman);
            _snowman = _snowman;
            _snowman = _snowman;
        } else {
            this.cursorXSmoother.clear();
            this.cursorYSmoother.clear();
            _snowman = this.cursorDeltaX * _snowman;
            _snowman = this.cursorDeltaY * _snowman;
        }
        this.cursorDeltaX = 0.0;
        this.cursorDeltaY = 0.0;
        int _snowman2 = 1;
        if (this.client.options.invertYMouse) {
            _snowman2 = -1;
        }
        this.client.getTutorialManager().onUpdateMouse(_snowman, _snowman);
        if (this.client.player != null) {
            this.client.player.changeLookDirection(_snowman, _snowman * (double)_snowman2);
        }
    }

    public boolean wasLeftButtonClicked() {
        return this.leftButtonClicked;
    }

    public boolean wasRightButtonClicked() {
        return this.rightButtonClicked;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void onResolutionChanged() {
        this.hasResolutionChanged = true;
    }

    public boolean isCursorLocked() {
        return this.cursorLocked;
    }

    public void lockCursor() {
        if (!this.client.isWindowFocused()) {
            return;
        }
        if (this.cursorLocked) {
            return;
        }
        if (!MinecraftClient.IS_SYSTEM_MAC) {
            KeyBinding.updatePressedStates();
        }
        this.cursorLocked = true;
        this.x = this.client.getWindow().getWidth() / 2;
        this.y = this.client.getWindow().getHeight() / 2;
        InputUtil.setCursorParameters(this.client.getWindow().getHandle(), 212995, this.x, this.y);
        this.client.openScreen(null);
        this.client.attackCooldown = 10000;
        this.hasResolutionChanged = true;
    }

    public void unlockCursor() {
        if (!this.cursorLocked) {
            return;
        }
        this.cursorLocked = false;
        this.x = this.client.getWindow().getWidth() / 2;
        this.y = this.client.getWindow().getHeight() / 2;
        InputUtil.setCursorParameters(this.client.getWindow().getHandle(), 212993, this.x, this.y);
    }

    public void method_30134() {
        this.hasResolutionChanged = true;
    }
}

