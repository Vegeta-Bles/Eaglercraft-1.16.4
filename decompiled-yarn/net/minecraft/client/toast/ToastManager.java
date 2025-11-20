package net.minecraft.client.toast;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.Deque;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class ToastManager extends DrawableHelper {
   private final MinecraftClient client;
   private final ToastManager.Entry<?>[] visibleEntries = new ToastManager.Entry[5];
   private final Deque<Toast> toastQueue = Queues.newArrayDeque();

   public ToastManager(MinecraftClient client) {
      this.client = client;
   }

   public void draw(MatrixStack matrices) {
      if (!this.client.options.hudHidden) {
         for (int _snowman = 0; _snowman < this.visibleEntries.length; _snowman++) {
            ToastManager.Entry<?> _snowmanx = this.visibleEntries[_snowman];
            if (_snowmanx != null && _snowmanx.draw(this.client.getWindow().getScaledWidth(), _snowman, matrices)) {
               this.visibleEntries[_snowman] = null;
            }

            if (this.visibleEntries[_snowman] == null && !this.toastQueue.isEmpty()) {
               this.visibleEntries[_snowman] = new ToastManager.Entry(this.toastQueue.removeFirst());
            }
         }
      }
   }

   @Nullable
   public <T extends Toast> T getToast(Class<? extends T> toastClass, Object type) {
      for (ToastManager.Entry<?> _snowman : this.visibleEntries) {
         if (_snowman != null && toastClass.isAssignableFrom(_snowman.getInstance().getClass()) && _snowman.getInstance().getType().equals(type)) {
            return (T)_snowman.getInstance();
         }
      }

      for (Toast _snowmanx : this.toastQueue) {
         if (toastClass.isAssignableFrom(_snowmanx.getClass()) && _snowmanx.getType().equals(type)) {
            return (T)_snowmanx;
         }
      }

      return null;
   }

   public void clear() {
      Arrays.fill(this.visibleEntries, null);
      this.toastQueue.clear();
   }

   public void add(Toast toast) {
      this.toastQueue.add(toast);
   }

   public MinecraftClient getGame() {
      return this.client;
   }

   class Entry<T extends Toast> {
      private final T instance;
      private long field_2243 = -1L;
      private long field_2242 = -1L;
      private Toast.Visibility visibility = Toast.Visibility.SHOW;

      private Entry(T toast) {
         this.instance = toast;
      }

      public T getInstance() {
         return this.instance;
      }

      private float getDisappearProgress(long time) {
         float _snowman = MathHelper.clamp((float)(time - this.field_2243) / 600.0F, 0.0F, 1.0F);
         _snowman *= _snowman;
         return this.visibility == Toast.Visibility.HIDE ? 1.0F - _snowman : _snowman;
      }

      public boolean draw(int x, int y, MatrixStack matrices) {
         long _snowman = Util.getMeasuringTimeMs();
         if (this.field_2243 == -1L) {
            this.field_2243 = _snowman;
            this.visibility.playSound(ToastManager.this.client.getSoundManager());
         }

         if (this.visibility == Toast.Visibility.SHOW && _snowman - this.field_2243 <= 600L) {
            this.field_2242 = _snowman;
         }

         RenderSystem.pushMatrix();
         RenderSystem.translatef(
            (float)x - (float)this.instance.getWidth() * this.getDisappearProgress(_snowman), (float)(y * this.instance.getHeight()), (float)(800 + y)
         );
         Toast.Visibility _snowmanx = this.instance.draw(matrices, ToastManager.this, _snowman - this.field_2242);
         RenderSystem.popMatrix();
         if (_snowmanx != this.visibility) {
            this.field_2243 = _snowman - (long)((int)((1.0F - this.getDisappearProgress(_snowman)) * 600.0F));
            this.visibility = _snowmanx;
            this.visibility.playSound(ToastManager.this.client.getSoundManager());
         }

         return this.visibility == Toast.Visibility.HIDE && _snowman - this.field_2243 > 600L;
      }
   }
}
