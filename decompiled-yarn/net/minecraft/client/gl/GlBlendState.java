package net.minecraft.client.gl;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Locale;

public class GlBlendState {
   private static GlBlendState activeBlendState;
   private final int srcRgb;
   private final int srcAlpha;
   private final int dstRgb;
   private final int dstAlpha;
   private final int func;
   private final boolean separateBlend;
   private final boolean blendDisabled;

   private GlBlendState(boolean separateBlend, boolean blendDisabled, int srcRgb, int dstRgb, int srcAlpha, int dstAlpha, int func) {
      this.separateBlend = separateBlend;
      this.srcRgb = srcRgb;
      this.dstRgb = dstRgb;
      this.srcAlpha = srcAlpha;
      this.dstAlpha = dstAlpha;
      this.blendDisabled = blendDisabled;
      this.func = func;
   }

   public GlBlendState() {
      this(false, true, 1, 0, 1, 0, 32774);
   }

   public GlBlendState(int srcRgb, int dstRgb, int func) {
      this(false, false, srcRgb, dstRgb, srcRgb, dstRgb, func);
   }

   public GlBlendState(int srcRgb, int dstRgb, int srcAlpha, int dstAlpha, int func) {
      this(true, false, srcRgb, dstRgb, srcAlpha, dstAlpha, func);
   }

   public void enable() {
      if (!this.equals(activeBlendState)) {
         if (activeBlendState == null || this.blendDisabled != activeBlendState.isBlendDisabled()) {
            activeBlendState = this;
            if (this.blendDisabled) {
               RenderSystem.disableBlend();
               return;
            }

            RenderSystem.enableBlend();
         }

         RenderSystem.blendEquation(this.func);
         if (this.separateBlend) {
            RenderSystem.blendFuncSeparate(this.srcRgb, this.dstRgb, this.srcAlpha, this.dstAlpha);
         } else {
            RenderSystem.blendFunc(this.srcRgb, this.dstRgb);
         }
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof GlBlendState)) {
         return false;
      } else {
         GlBlendState _snowman = (GlBlendState)o;
         if (this.func != _snowman.func) {
            return false;
         } else if (this.dstAlpha != _snowman.dstAlpha) {
            return false;
         } else if (this.dstRgb != _snowman.dstRgb) {
            return false;
         } else if (this.blendDisabled != _snowman.blendDisabled) {
            return false;
         } else if (this.separateBlend != _snowman.separateBlend) {
            return false;
         } else {
            return this.srcAlpha != _snowman.srcAlpha ? false : this.srcRgb == _snowman.srcRgb;
         }
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.srcRgb;
      _snowman = 31 * _snowman + this.srcAlpha;
      _snowman = 31 * _snowman + this.dstRgb;
      _snowman = 31 * _snowman + this.dstAlpha;
      _snowman = 31 * _snowman + this.func;
      _snowman = 31 * _snowman + (this.separateBlend ? 1 : 0);
      return 31 * _snowman + (this.blendDisabled ? 1 : 0);
   }

   public boolean isBlendDisabled() {
      return this.blendDisabled;
   }

   public static int getFuncFromString(String _snowman) {
      String _snowmanx = _snowman.trim().toLowerCase(Locale.ROOT);
      if ("add".equals(_snowmanx)) {
         return 32774;
      } else if ("subtract".equals(_snowmanx)) {
         return 32778;
      } else if ("reversesubtract".equals(_snowmanx)) {
         return 32779;
      } else if ("reverse_subtract".equals(_snowmanx)) {
         return 32779;
      } else if ("min".equals(_snowmanx)) {
         return 32775;
      } else {
         return "max".equals(_snowmanx) ? 32776 : 32774;
      }
   }

   public static int getComponentFromString(String _snowman) {
      String _snowmanx = _snowman.trim().toLowerCase(Locale.ROOT);
      _snowmanx = _snowmanx.replaceAll("_", "");
      _snowmanx = _snowmanx.replaceAll("one", "1");
      _snowmanx = _snowmanx.replaceAll("zero", "0");
      _snowmanx = _snowmanx.replaceAll("minus", "-");
      if ("0".equals(_snowmanx)) {
         return 0;
      } else if ("1".equals(_snowmanx)) {
         return 1;
      } else if ("srccolor".equals(_snowmanx)) {
         return 768;
      } else if ("1-srccolor".equals(_snowmanx)) {
         return 769;
      } else if ("dstcolor".equals(_snowmanx)) {
         return 774;
      } else if ("1-dstcolor".equals(_snowmanx)) {
         return 775;
      } else if ("srcalpha".equals(_snowmanx)) {
         return 770;
      } else if ("1-srcalpha".equals(_snowmanx)) {
         return 771;
      } else if ("dstalpha".equals(_snowmanx)) {
         return 772;
      } else {
         return "1-dstalpha".equals(_snowmanx) ? 773 : -1;
      }
   }
}
