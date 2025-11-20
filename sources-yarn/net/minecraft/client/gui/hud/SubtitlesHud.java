package net.minecraft.client.gui.hud;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
         Vec3d lv = new Vec3d(this.client.player.getX(), this.client.player.getEyeY(), this.client.player.getZ());
         Vec3d lv2 = new Vec3d(0.0, 0.0, -1.0)
            .rotateX(-this.client.player.pitch * (float) (Math.PI / 180.0))
            .rotateY(-this.client.player.yaw * (float) (Math.PI / 180.0));
         Vec3d lv3 = new Vec3d(0.0, 1.0, 0.0)
            .rotateX(-this.client.player.pitch * (float) (Math.PI / 180.0))
            .rotateY(-this.client.player.yaw * (float) (Math.PI / 180.0));
         Vec3d lv4 = lv2.crossProduct(lv3);
         int i = 0;
         int j = 0;
         Iterator<SubtitlesHud.SubtitleEntry> iterator = this.entries.iterator();

         while (iterator.hasNext()) {
            SubtitlesHud.SubtitleEntry lv5 = iterator.next();
            if (lv5.getTime() + 3000L <= Util.getMeasuringTimeMs()) {
               iterator.remove();
            } else {
               j = Math.max(j, this.client.textRenderer.getWidth(lv5.getText()));
            }
         }

         j += this.client.textRenderer.getWidth("<")
            + this.client.textRenderer.getWidth(" ")
            + this.client.textRenderer.getWidth(">")
            + this.client.textRenderer.getWidth(" ");

         for (SubtitlesHud.SubtitleEntry lv6 : this.entries) {
            int k = 255;
            Text lv7 = lv6.getText();
            Vec3d lv8 = lv6.getPosition().subtract(lv).normalize();
            double d = -lv4.dotProduct(lv8);
            double e = -lv2.dotProduct(lv8);
            boolean bl = e > 0.5;
            int l = j / 2;
            int m = 9;
            int n = m / 2;
            float f = 1.0F;
            int o = this.client.textRenderer.getWidth(lv7);
            int p = MathHelper.floor(MathHelper.clampedLerp(255.0, 75.0, (double)((float)(Util.getMeasuringTimeMs() - lv6.getTime()) / 3000.0F)));
            int q = p << 16 | p << 8 | p;
            RenderSystem.pushMatrix();
            RenderSystem.translatef(
               (float)this.client.getWindow().getScaledWidth() - (float)l * 1.0F - 2.0F,
               (float)(this.client.getWindow().getScaledHeight() - 30) - (float)(i * (m + 1)) * 1.0F,
               0.0F
            );
            RenderSystem.scalef(1.0F, 1.0F, 1.0F);
            fill(matrices, -l - 1, -n - 1, l + 1, n + 1, this.client.options.getTextBackgroundColor(0.8F));
            RenderSystem.enableBlend();
            if (!bl) {
               if (d > 0.0) {
                  this.client.textRenderer.draw(matrices, ">", (float)(l - this.client.textRenderer.getWidth(">")), (float)(-n), q + -16777216);
               } else if (d < 0.0) {
                  this.client.textRenderer.draw(matrices, "<", (float)(-l), (float)(-n), q + -16777216);
               }
            }

            this.client.textRenderer.draw(matrices, lv7, (float)(-o / 2), (float)(-n), q + -16777216);
            RenderSystem.popMatrix();
            i++;
         }

         RenderSystem.disableBlend();
         RenderSystem.popMatrix();
      }
   }

   @Override
   public void onSoundPlayed(SoundInstance sound, WeightedSoundSet soundSet) {
      if (soundSet.getSubtitle() != null) {
         Text lv = soundSet.getSubtitle();
         if (!this.entries.isEmpty()) {
            for (SubtitlesHud.SubtitleEntry lv2 : this.entries) {
               if (lv2.getText().equals(lv)) {
                  lv2.reset(new Vec3d(sound.getX(), sound.getY(), sound.getZ()));
                  return;
               }
            }
         }

         this.entries.add(new SubtitlesHud.SubtitleEntry(lv, new Vec3d(sound.getX(), sound.getY(), sound.getZ())));
      }
   }

   @Environment(EnvType.CLIENT)
   public class SubtitleEntry {
      private final Text text;
      private long time;
      private Vec3d pos;

      public SubtitleEntry(Text arg2, Vec3d pos) {
         this.text = arg2;
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
