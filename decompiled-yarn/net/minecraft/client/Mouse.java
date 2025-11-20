package net.minecraft.client;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.Element;
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
      if (window == this.client.getWindow().getHandle()) {
         boolean _snowman = action == 1;
         if (MinecraftClient.IS_SYSTEM_MAC && button == 0) {
            if (_snowman) {
               if ((mods & 2) == 2) {
                  button = 1;
                  this.controlLeftTicks++;
               }
            } else if (this.controlLeftTicks > 0) {
               button = 1;
               this.controlLeftTicks--;
            }
         }

         int _snowmanx = button;
         if (_snowman) {
            if (this.client.options.touchscreen && this.field_1796++ > 0) {
               return;
            }

            this.activeButton = _snowmanx;
            this.glfwTime = GlfwUtil.getTime();
         } else if (this.activeButton != -1) {
            if (this.client.options.touchscreen && --this.field_1796 > 0) {
               return;
            }

            this.activeButton = -1;
         }

         boolean[] _snowmanxx = new boolean[]{false};
         if (this.client.overlay == null) {
            if (this.client.currentScreen == null) {
               if (!this.cursorLocked && _snowman) {
                  this.lockCursor();
               }
            } else {
               double _snowmanxxx = this.x * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
               double _snowmanxxxx = this.y * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
               if (_snowman) {
                  Screen.wrapScreenError(
                     () -> _snowman[0] = this.client.currentScreen.mouseClicked(_snowman, _snowman, _snowman),
                     "mouseClicked event handler",
                     this.client.currentScreen.getClass().getCanonicalName()
                  );
               } else {
                  Screen.wrapScreenError(
                     () -> _snowman[0] = this.client.currentScreen.mouseReleased(_snowman, _snowman, _snowman),
                     "mouseReleased event handler",
                     this.client.currentScreen.getClass().getCanonicalName()
                  );
               }
            }
         }

         if (!_snowmanxx[0] && (this.client.currentScreen == null || this.client.currentScreen.passEvents) && this.client.overlay == null) {
            if (_snowmanx == 0) {
               this.leftButtonClicked = _snowman;
            } else if (_snowmanx == 2) {
               this.middleButtonClicked = _snowman;
            } else if (_snowmanx == 1) {
               this.rightButtonClicked = _snowman;
            }

            KeyBinding.setKeyPressed(InputUtil.Type.MOUSE.createFromCode(_snowmanx), _snowman);
            if (_snowman) {
               if (this.client.player.isSpectator() && _snowmanx == 2) {
                  this.client.inGameHud.getSpectatorHud().useSelectedCommand();
               } else {
                  KeyBinding.onKeyPressed(InputUtil.Type.MOUSE.createFromCode(_snowmanx));
               }
            }
         }
      }
   }

   private void onMouseScroll(long window, double horizontal, double vertical) {
      if (window == MinecraftClient.getInstance().getWindow().getHandle()) {
         double _snowman = (this.client.options.discreteMouseScroll ? Math.signum(vertical) : vertical) * this.client.options.mouseWheelSensitivity;
         if (this.client.overlay == null) {
            if (this.client.currentScreen != null) {
               double _snowmanx = this.x * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
               double _snowmanxx = this.y * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
               this.client.currentScreen.mouseScrolled(_snowmanx, _snowmanxx, _snowman);
            } else if (this.client.player != null) {
               if (this.eventDeltaWheel != 0.0 && Math.signum(_snowman) != Math.signum(this.eventDeltaWheel)) {
                  this.eventDeltaWheel = 0.0;
               }

               this.eventDeltaWheel += _snowman;
               float _snowmanx = (float)((int)this.eventDeltaWheel);
               if (_snowmanx == 0.0F) {
                  return;
               }

               this.eventDeltaWheel -= (double)_snowmanx;
               if (this.client.player.isSpectator()) {
                  if (this.client.inGameHud.getSpectatorHud().isOpen()) {
                     this.client.inGameHud.getSpectatorHud().cycleSlot((double)(-_snowmanx));
                  } else {
                     float _snowmanxx = MathHelper.clamp(this.client.player.abilities.getFlySpeed() + _snowmanx * 0.005F, 0.0F, 0.2F);
                     this.client.player.abilities.setFlySpeed(_snowmanxx);
                  }
               } else {
                  this.client.player.inventory.scrollInHotbar((double)_snowmanx);
               }
            }
         }
      }
   }

   private void method_29616(long _snowman, List<Path> _snowman) {
      if (this.client.currentScreen != null) {
         this.client.currentScreen.filesDragged(_snowman);
      }
   }

   public void setup(long _snowman) {
      InputUtil.setMouseCallbacks(
         _snowman,
         (_snowmanxxx, _snowmanxx, _snowmanx) -> this.client.execute(() -> this.onCursorPos(_snowmanxxx, _snowmanxx, _snowmanx)),
         (_snowmanxxxx, _snowmanxxx, _snowmanxx, _snowmanx) -> this.client.execute(() -> this.onMouseButton(_snowmanxxxx, _snowmanxxx, _snowmanxx, _snowmanx)),
         (_snowmanxxx, _snowmanxx, _snowmanx) -> this.client.execute(() -> this.onMouseScroll(_snowmanxxx, _snowmanxx, _snowmanx)),
         (_snowmanxxxxx, _snowmanxxxx, _snowmanxxx) -> {
            Path[] _snowmanxxx = new Path[_snowmanxxxx];

            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxxx; _snowmanxxxx++) {
               _snowmanxxx[_snowmanxxxx] = Paths.get(GLFWDropCallback.getName(_snowmanxxx, _snowmanxxxx));
            }

            this.client.execute(() -> this.method_29616(_snowmanxxxxx, Arrays.asList(_snowmanxx)));
         }
      );
   }

   private void onCursorPos(long window, double x, double y) {
      if (window == MinecraftClient.getInstance().getWindow().getHandle()) {
         if (this.hasResolutionChanged) {
            this.x = x;
            this.y = y;
            this.hasResolutionChanged = false;
         }

         Element _snowman = this.client.currentScreen;
         if (_snowman != null && this.client.overlay == null) {
            double _snowmanx = x * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
            double _snowmanxx = y * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
            Screen.wrapScreenError(() -> _snowman.mouseMoved(_snowman, _snowman), "mouseMoved event handler", _snowman.getClass().getCanonicalName());
            if (this.activeButton != -1 && this.glfwTime > 0.0) {
               double _snowmanxxx = (x - this.x) * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
               double _snowmanxxxx = (y - this.y) * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
               Screen.wrapScreenError(() -> _snowman.mouseDragged(_snowman, _snowman, this.activeButton, _snowman, _snowman), "mouseDragged event handler", _snowman.getClass().getCanonicalName());
            }
         }

         this.client.getProfiler().push("mouse");
         if (this.isCursorLocked() && this.client.isWindowFocused()) {
            this.cursorDeltaX = this.cursorDeltaX + (x - this.x);
            this.cursorDeltaY = this.cursorDeltaY + (y - this.y);
         }

         this.updateMouse();
         this.x = x;
         this.y = y;
         this.client.getProfiler().pop();
      }
   }

   public void updateMouse() {
      double _snowman = GlfwUtil.getTime();
      double _snowmanx = _snowman - this.lastMouseUpdateTime;
      this.lastMouseUpdateTime = _snowman;
      if (this.isCursorLocked() && this.client.isWindowFocused()) {
         double _snowmanxx = this.client.options.mouseSensitivity * 0.6F + 0.2F;
         double _snowmanxxx = _snowmanxx * _snowmanxx * _snowmanxx * 8.0;
         double _snowmanxxxx;
         double _snowmanxxxxx;
         if (this.client.options.smoothCameraEnabled) {
            double _snowmanxxxxxx = this.cursorXSmoother.smooth(this.cursorDeltaX * _snowmanxxx, _snowmanx * _snowmanxxx);
            double _snowmanxxxxxxx = this.cursorYSmoother.smooth(this.cursorDeltaY * _snowmanxxx, _snowmanx * _snowmanxxx);
            _snowmanxxxx = _snowmanxxxxxx;
            _snowmanxxxxx = _snowmanxxxxxxx;
         } else {
            this.cursorXSmoother.clear();
            this.cursorYSmoother.clear();
            _snowmanxxxx = this.cursorDeltaX * _snowmanxxx;
            _snowmanxxxxx = this.cursorDeltaY * _snowmanxxx;
         }

         this.cursorDeltaX = 0.0;
         this.cursorDeltaY = 0.0;
         int _snowmanxxxxxx = 1;
         if (this.client.options.invertYMouse) {
            _snowmanxxxxxx = -1;
         }

         this.client.getTutorialManager().onUpdateMouse(_snowmanxxxx, _snowmanxxxxx);
         if (this.client.player != null) {
            this.client.player.changeLookDirection(_snowmanxxxx, _snowmanxxxxx * (double)_snowmanxxxxxx);
         }
      } else {
         this.cursorDeltaX = 0.0;
         this.cursorDeltaY = 0.0;
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
      if (this.client.isWindowFocused()) {
         if (!this.cursorLocked) {
            if (!MinecraftClient.IS_SYSTEM_MAC) {
               KeyBinding.updatePressedStates();
            }

            this.cursorLocked = true;
            this.x = (double)(this.client.getWindow().getWidth() / 2);
            this.y = (double)(this.client.getWindow().getHeight() / 2);
            InputUtil.setCursorParameters(this.client.getWindow().getHandle(), 212995, this.x, this.y);
            this.client.openScreen(null);
            this.client.attackCooldown = 10000;
            this.hasResolutionChanged = true;
         }
      }
   }

   public void unlockCursor() {
      if (this.cursorLocked) {
         this.cursorLocked = false;
         this.x = (double)(this.client.getWindow().getWidth() / 2);
         this.y = (double)(this.client.getWindow().getHeight() / 2);
         InputUtil.setCursorParameters(this.client.getWindow().getHandle(), 212993, this.x, this.y);
      }
   }

   public void method_30134() {
      this.hasResolutionChanged = true;
   }
}
