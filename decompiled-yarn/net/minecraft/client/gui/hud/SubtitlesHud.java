package net.minecraft.client.gui.hud;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SubtitlesHud extends DrawableHelper implements SoundInstanceListener {
   private final MinecraftClient client;
   private final List<SubtitlesHud.SubtitleEntry> entries = Lists.newArrayList();
   private boolean enabled;

   public SubtitlesHud(MinecraftClient client) {
      this.client = client;
   }

   public void render(MatrixStack matrices) {
      if (!this.enabled && this.client.options.showSubtitles) {
         this.client.getSoundManager().registerListener(this);
         this.enabled = true;
      } else if (this.enabled && !this.client.options.showSubtitles) {
         this.client.getSoundManager().unregisterListener(this);
         this.enabled = false;
      }

      if (this.enabled && !this.entries.isEmpty()) {
         RenderSystem.pushMatrix();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         Vec3d _snowman = new Vec3d(this.client.player.getX(), this.client.player.getEyeY(), this.client.player.getZ());
         Vec3d _snowmanx = new Vec3d(0.0, 0.0, -1.0)
            .rotateX(-this.client.player.pitch * (float) (Math.PI / 180.0))
            .rotateY(-this.client.player.yaw * (float) (Math.PI / 180.0));
         Vec3d _snowmanxx = new Vec3d(0.0, 1.0, 0.0)
            .rotateX(-this.client.player.pitch * (float) (Math.PI / 180.0))
            .rotateY(-this.client.player.yaw * (float) (Math.PI / 180.0));
         Vec3d _snowmanxxx = _snowmanx.crossProduct(_snowmanxx);
         int _snowmanxxxx = 0;
         int _snowmanxxxxx = 0;
         Iterator<SubtitlesHud.SubtitleEntry> _snowmanxxxxxx = this.entries.iterator();

         while (_snowmanxxxxxx.hasNext()) {
            SubtitlesHud.SubtitleEntry _snowmanxxxxxxx = _snowmanxxxxxx.next();
            if (_snowmanxxxxxxx.getTime() + 3000L <= Util.getMeasuringTimeMs()) {
               _snowmanxxxxxx.remove();
            } else {
               _snowmanxxxxx = Math.max(_snowmanxxxxx, this.client.textRenderer.getWidth(_snowmanxxxxxxx.getText()));
            }
         }

         _snowmanxxxxx += this.client.textRenderer.getWidth("<")
            + this.client.textRenderer.getWidth(" ")
            + this.client.textRenderer.getWidth(">")
            + this.client.textRenderer.getWidth(" ");

         for (SubtitlesHud.SubtitleEntry _snowmanxxxxxxx : this.entries) {
            int _snowmanxxxxxxxx = 255;
            Text _snowmanxxxxxxxxx = _snowmanxxxxxxx.getText();
            Vec3d _snowmanxxxxxxxxxx = _snowmanxxxxxxx.getPosition().subtract(_snowman).normalize();
            double _snowmanxxxxxxxxxxx = -_snowmanxxx.dotProduct(_snowmanxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxx = -_snowmanx.dotProduct(_snowmanxxxxxxxxxx);
            boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx > 0.5;
            int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxx / 2;
            int _snowmanxxxxxxxxxxxxxxx = 9;
            int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx / 2;
            float _snowmanxxxxxxxxxxxxxxxxx = 1.0F;
            int _snowmanxxxxxxxxxxxxxxxxxx = this.client.textRenderer.getWidth(_snowmanxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxxx = MathHelper.floor(
               MathHelper.clampedLerp(255.0, 75.0, (double)((float)(Util.getMeasuringTimeMs() - _snowmanxxxxxxx.getTime()) / 3000.0F))
            );
            int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx << 16 | _snowmanxxxxxxxxxxxxxxxxxxx << 8 | _snowmanxxxxxxxxxxxxxxxxxxx;
            RenderSystem.pushMatrix();
            RenderSystem.translatef(
               (float)this.client.getWindow().getScaledWidth() - (float)_snowmanxxxxxxxxxxxxxx * 1.0F - 2.0F,
               (float)(this.client.getWindow().getScaledHeight() - 30) - (float)(_snowmanxxxx * (_snowmanxxxxxxxxxxxxxxx + 1)) * 1.0F,
               0.0F
            );
            RenderSystem.scalef(1.0F, 1.0F, 1.0F);
            fill(
               matrices,
               -_snowmanxxxxxxxxxxxxxx - 1,
               -_snowmanxxxxxxxxxxxxxxxx - 1,
               _snowmanxxxxxxxxxxxxxx + 1,
               _snowmanxxxxxxxxxxxxxxxx + 1,
               this.client.options.getTextBackgroundColor(0.8F)
            );
            RenderSystem.enableBlend();
            if (!_snowmanxxxxxxxxxxxxx) {
               if (_snowmanxxxxxxxxxxx > 0.0) {
                  this.client
                     .textRenderer
                     .draw(
                        matrices,
                        ">",
                        (float)(_snowmanxxxxxxxxxxxxxx - this.client.textRenderer.getWidth(">")),
                        (float)(-_snowmanxxxxxxxxxxxxxxxx),
                        _snowmanxxxxxxxxxxxxxxxxxxxx + -16777216
                     );
               } else if (_snowmanxxxxxxxxxxx < 0.0) {
                  this.client.textRenderer.draw(matrices, "<", (float)(-_snowmanxxxxxxxxxxxxxx), (float)(-_snowmanxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxxxx + -16777216);
               }
            }

            this.client
               .textRenderer
               .draw(matrices, _snowmanxxxxxxxxx, (float)(-_snowmanxxxxxxxxxxxxxxxxxx / 2), (float)(-_snowmanxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxxxx + -16777216);
            RenderSystem.popMatrix();
            _snowmanxxxx++;
         }

         RenderSystem.disableBlend();
         RenderSystem.popMatrix();
      }
   }

   @Override
   public void onSoundPlayed(SoundInstance sound, WeightedSoundSet soundSet) {
      if (soundSet.getSubtitle() != null) {
         Text _snowman = soundSet.getSubtitle();
         if (!this.entries.isEmpty()) {
            for (SubtitlesHud.SubtitleEntry _snowmanx : this.entries) {
               if (_snowmanx.getText().equals(_snowman)) {
                  _snowmanx.reset(new Vec3d(sound.getX(), sound.getY(), sound.getZ()));
                  return;
               }
            }
         }

         this.entries.add(new SubtitlesHud.SubtitleEntry(_snowman, new Vec3d(sound.getX(), sound.getY(), sound.getZ())));
      }
   }

   public class SubtitleEntry {
      private final Text text;
      private long time;
      private Vec3d pos;

      public SubtitleEntry(Text _snowman, Vec3d pos) {
         this.text = _snowman;
         this.pos = pos;
         this.time = Util.getMeasuringTimeMs();
      }

      public Text getText() {
         return this.text;
      }

      public long getTime() {
         return this.time;
      }

      public Vec3d getPosition() {
         return this.pos;
      }

      public void reset(Vec3d pos) {
         this.pos = pos;
         this.time = Util.getMeasuringTimeMs();
      }
   }
}
