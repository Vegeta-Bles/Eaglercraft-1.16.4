/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
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

public class SubtitlesHud
extends DrawableHelper
implements SoundInstanceListener {
    private final MinecraftClient client;
    private final List<SubtitleEntry> entries = Lists.newArrayList();
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
        if (!this.enabled || this.entries.isEmpty()) {
            return;
        }
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        Vec3d vec3d = new Vec3d(this.client.player.getX(), this.client.player.getEyeY(), this.client.player.getZ());
        _snowman = new Vec3d(0.0, 0.0, -1.0).rotateX(-this.client.player.pitch * ((float)Math.PI / 180)).rotateY(-this.client.player.yaw * ((float)Math.PI / 180));
        _snowman = new Vec3d(0.0, 1.0, 0.0).rotateX(-this.client.player.pitch * ((float)Math.PI / 180)).rotateY(-this.client.player.yaw * ((float)Math.PI / 180));
        _snowman = _snowman.crossProduct(_snowman);
        int _snowman2 = 0;
        int _snowman3 = 0;
        Iterator<SubtitleEntry> _snowman4 = this.entries.iterator();
        while (_snowman4.hasNext()) {
            SubtitleEntry subtitleEntry = _snowman4.next();
            if (subtitleEntry.getTime() + 3000L <= Util.getMeasuringTimeMs()) {
                _snowman4.remove();
                continue;
            }
            _snowman3 = Math.max(_snowman3, this.client.textRenderer.getWidth(subtitleEntry.getText()));
        }
        _snowman3 += this.client.textRenderer.getWidth("<") + this.client.textRenderer.getWidth(" ") + this.client.textRenderer.getWidth(">") + this.client.textRenderer.getWidth(" ");
        for (SubtitleEntry subtitleEntry : this.entries) {
            int n = 255;
            Text _snowman5 = subtitleEntry.getText();
            Vec3d _snowman6 = subtitleEntry.getPosition().subtract(vec3d).normalize();
            double _snowman7 = -_snowman.dotProduct(_snowman6);
            double _snowman8 = -_snowman.dotProduct(_snowman6);
            boolean _snowman9 = _snowman8 > 0.5;
            _snowman = _snowman3 / 2;
            _snowman = this.client.textRenderer.fontHeight;
            _snowman = _snowman / 2;
            float _snowman10 = 1.0f;
            _snowman = this.client.textRenderer.getWidth(_snowman5);
            _snowman = MathHelper.floor(MathHelper.clampedLerp(255.0, 75.0, (float)(Util.getMeasuringTimeMs() - subtitleEntry.getTime()) / 3000.0f));
            _snowman = _snowman << 16 | _snowman << 8 | _snowman;
            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)this.client.getWindow().getScaledWidth() - (float)_snowman * 1.0f - 2.0f, (float)(this.client.getWindow().getScaledHeight() - 30) - (float)(_snowman2 * (_snowman + 1)) * 1.0f, 0.0f);
            RenderSystem.scalef(1.0f, 1.0f, 1.0f);
            SubtitlesHud.fill(matrices, -_snowman - 1, -_snowman - 1, _snowman + 1, _snowman + 1, this.client.options.getTextBackgroundColor(0.8f));
            RenderSystem.enableBlend();
            if (!_snowman9) {
                if (_snowman7 > 0.0) {
                    this.client.textRenderer.draw(matrices, ">", (float)(_snowman - this.client.textRenderer.getWidth(">")), (float)(-_snowman), _snowman + -16777216);
                } else if (_snowman7 < 0.0) {
                    this.client.textRenderer.draw(matrices, "<", (float)(-_snowman), (float)(-_snowman), _snowman + -16777216);
                }
            }
            this.client.textRenderer.draw(matrices, _snowman5, (float)(-_snowman / 2), (float)(-_snowman), _snowman + -16777216);
            RenderSystem.popMatrix();
            ++_snowman2;
        }
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
    }

    @Override
    public void onSoundPlayed(SoundInstance sound, WeightedSoundSet soundSet) {
        if (soundSet.getSubtitle() == null) {
            return;
        }
        Text text = soundSet.getSubtitle();
        if (!this.entries.isEmpty()) {
            for (SubtitleEntry subtitleEntry : this.entries) {
                if (!subtitleEntry.getText().equals(text)) continue;
                subtitleEntry.reset(new Vec3d(sound.getX(), sound.getY(), sound.getZ()));
                return;
            }
        }
        this.entries.add(new SubtitleEntry(text, new Vec3d(sound.getX(), sound.getY(), sound.getZ())));
    }

    public class SubtitleEntry {
        private final Text text;
        private long time;
        private Vec3d pos;

        public SubtitleEntry(Text text, Vec3d pos) {
            this.text = text;
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

