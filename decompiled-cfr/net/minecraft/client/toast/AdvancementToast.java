/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.MathHelper;

public class AdvancementToast
implements Toast {
    private final Advancement advancement;
    private boolean soundPlayed;

    public AdvancementToast(Advancement advancement) {
        this.advancement = advancement;
    }

    @Override
    public Toast.Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        manager.getGame().getTextureManager().bindTexture(TEXTURE);
        RenderSystem.color3f(1.0f, 1.0f, 1.0f);
        AdvancementDisplay advancementDisplay = this.advancement.getDisplay();
        manager.drawTexture(matrices, 0, 0, 0, 0, this.getWidth(), this.getHeight());
        if (advancementDisplay != null) {
            List<OrderedText> list = manager.getGame().textRenderer.wrapLines(advancementDisplay.getTitle(), 125);
            int n = _snowman = advancementDisplay.getFrame() == AdvancementFrame.CHALLENGE ? 0xFF88FF : 0xFFFF00;
            if (list.size() == 1) {
                manager.getGame().textRenderer.draw(matrices, advancementDisplay.getFrame().getToastText(), 30.0f, 7.0f, _snowman | 0xFF000000);
                manager.getGame().textRenderer.draw(matrices, list.get(0), 30.0f, 18.0f, -1);
            } else {
                int n2 = 1500;
                float _snowman2 = 300.0f;
                if (startTime < 1500L) {
                    _snowman = MathHelper.floor(MathHelper.clamp((float)(1500L - startTime) / 300.0f, 0.0f, 1.0f) * 255.0f) << 24 | 0x4000000;
                    manager.getGame().textRenderer.draw(matrices, advancementDisplay.getFrame().getToastText(), 30.0f, 11.0f, _snowman | _snowman);
                } else {
                    _snowman = MathHelper.floor(MathHelper.clamp((float)(startTime - 1500L) / 300.0f, 0.0f, 1.0f) * 252.0f) << 24 | 0x4000000;
                    _snowman = this.getHeight() / 2 - list.size() * manager.getGame().textRenderer.fontHeight / 2;
                    for (OrderedText orderedText : list) {
                        manager.getGame().textRenderer.draw(matrices, orderedText, 30.0f, (float)_snowman, 0xFFFFFF | _snowman);
                        _snowman += manager.getGame().textRenderer.fontHeight;
                    }
                }
            }
            if (!this.soundPlayed && startTime > 0L) {
                this.soundPlayed = true;
                if (advancementDisplay.getFrame() == AdvancementFrame.CHALLENGE) {
                    manager.getGame().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f));
                }
            }
            manager.getGame().getItemRenderer().renderInGui(advancementDisplay.getIcon(), 8, 8);
            return startTime >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
        }
        return Toast.Visibility.HIDE;
    }
}

