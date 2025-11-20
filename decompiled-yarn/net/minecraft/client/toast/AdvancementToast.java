package net.minecraft.client.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.MathHelper;

public class AdvancementToast implements Toast {
   private final Advancement advancement;
   private boolean soundPlayed;

   public AdvancementToast(Advancement advancement) {
      this.advancement = advancement;
   }

   @Override
   public Toast.Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
      manager.getGame().getTextureManager().bindTexture(TEXTURE);
      RenderSystem.color3f(1.0F, 1.0F, 1.0F);
      AdvancementDisplay _snowman = this.advancement.getDisplay();
      manager.drawTexture(matrices, 0, 0, 0, 0, this.getWidth(), this.getHeight());
      if (_snowman != null) {
         List<OrderedText> _snowmanx = manager.getGame().textRenderer.wrapLines(_snowman.getTitle(), 125);
         int _snowmanxx = _snowman.getFrame() == AdvancementFrame.CHALLENGE ? 16746751 : 16776960;
         if (_snowmanx.size() == 1) {
            manager.getGame().textRenderer.draw(matrices, _snowman.getFrame().getToastText(), 30.0F, 7.0F, _snowmanxx | 0xFF000000);
            manager.getGame().textRenderer.draw(matrices, _snowmanx.get(0), 30.0F, 18.0F, -1);
         } else {
            int _snowmanxxx = 1500;
            float _snowmanxxxx = 300.0F;
            if (startTime < 1500L) {
               int _snowmanxxxxx = MathHelper.floor(MathHelper.clamp((float)(1500L - startTime) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
               manager.getGame().textRenderer.draw(matrices, _snowman.getFrame().getToastText(), 30.0F, 11.0F, _snowmanxx | _snowmanxxxxx);
            } else {
               int _snowmanxxxxx = MathHelper.floor(MathHelper.clamp((float)(startTime - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
               int _snowmanxxxxxx = this.getHeight() / 2 - _snowmanx.size() * 9 / 2;

               for (OrderedText _snowmanxxxxxxx : _snowmanx) {
                  manager.getGame().textRenderer.draw(matrices, _snowmanxxxxxxx, 30.0F, (float)_snowmanxxxxxx, 16777215 | _snowmanxxxxx);
                  _snowmanxxxxxx += 9;
               }
            }
         }

         if (!this.soundPlayed && startTime > 0L) {
            this.soundPlayed = true;
            if (_snowman.getFrame() == AdvancementFrame.CHALLENGE) {
               manager.getGame().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));
            }
         }

         manager.getGame().getItemRenderer().renderInGui(_snowman.getIcon(), 8, 8);
         return startTime >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
      } else {
         return Toast.Visibility.HIDE;
      }
   }
}
