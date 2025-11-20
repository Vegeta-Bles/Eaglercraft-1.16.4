/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
 */
package net.minecraft.client.sound;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Optional;
import java.util.Random;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;

public class BiomeEffectSoundPlayer
implements ClientPlayerTickable {
    private final ClientPlayerEntity player;
    private final SoundManager soundManager;
    private final BiomeAccess biomeAccess;
    private final Random random;
    private Object2ObjectArrayMap<Biome, MusicLoop> soundLoops = new Object2ObjectArrayMap();
    private Optional<BiomeMoodSound> moodSound = Optional.empty();
    private Optional<BiomeAdditionsSound> additionsSound = Optional.empty();
    private float moodPercentage;
    private Biome activeBiome;

    public BiomeEffectSoundPlayer(ClientPlayerEntity player, SoundManager soundManager, BiomeAccess biomeAccess) {
        this.random = player.world.getRandom();
        this.player = player;
        this.soundManager = soundManager;
        this.biomeAccess = biomeAccess;
    }

    public float getMoodPercentage() {
        return this.moodPercentage;
    }

    @Override
    public void tick() {
        this.soundLoops.values().removeIf(MovingSoundInstance::isDone);
        Biome biome = this.biomeAccess.getBiome(this.player.getX(), this.player.getY(), this.player.getZ());
        if (biome != this.activeBiome) {
            this.activeBiome = biome;
            this.moodSound = biome.getMoodSound();
            this.additionsSound = biome.getAdditionsSound();
            this.soundLoops.values().forEach(MusicLoop::fadeOut);
            biome.getLoopSound().ifPresent(soundEvent -> {
                MusicLoop cfr_ignored_0 = (MusicLoop)this.soundLoops.compute((Object)biome, (biome, musicLoop) -> {
                    if (musicLoop == null) {
                        musicLoop = new MusicLoop((SoundEvent)soundEvent);
                        this.soundManager.play((SoundInstance)musicLoop);
                    }
                    musicLoop.fadeIn();
                    return musicLoop;
                });
            });
        }
        this.additionsSound.ifPresent(biomeAdditionsSound -> {
            if (this.random.nextDouble() < biomeAdditionsSound.getChance()) {
                this.soundManager.play(PositionedSoundInstance.ambient(biomeAdditionsSound.getSound()));
            }
        });
        this.moodSound.ifPresent(biomeMoodSound -> {
            World world = this.player.world;
            int _snowman2 = biomeMoodSound.getSpawnRange() * 2 + 1;
            BlockPos _snowman3 = new BlockPos(this.player.getX() + (double)this.random.nextInt(_snowman2) - (double)biomeMoodSound.getSpawnRange(), this.player.getEyeY() + (double)this.random.nextInt(_snowman2) - (double)biomeMoodSound.getSpawnRange(), this.player.getZ() + (double)this.random.nextInt(_snowman2) - (double)biomeMoodSound.getSpawnRange());
            int _snowman4 = world.getLightLevel(LightType.SKY, _snowman3);
            this.moodPercentage = _snowman4 > 0 ? (this.moodPercentage -= (float)_snowman4 / (float)world.getMaxLightLevel() * 0.001f) : (this.moodPercentage -= (float)(world.getLightLevel(LightType.BLOCK, _snowman3) - 1) / (float)biomeMoodSound.getCultivationTicks());
            if (this.moodPercentage >= 1.0f) {
                double d = (double)_snowman3.getX() + 0.5;
                _snowman = (double)_snowman3.getY() + 0.5;
                _snowman = (double)_snowman3.getZ() + 0.5;
                _snowman = d - this.player.getX();
                _snowman = _snowman - this.player.getEyeY();
                _snowman = _snowman - this.player.getZ();
                _snowman = MathHelper.sqrt(_snowman * _snowman + _snowman * _snowman + _snowman * _snowman);
                _snowman = _snowman + biomeMoodSound.getExtraDistance();
                PositionedSoundInstance _snowman5 = PositionedSoundInstance.ambient(biomeMoodSound.getSound(), this.player.getX() + _snowman / _snowman * _snowman, this.player.getEyeY() + _snowman / _snowman * _snowman, this.player.getZ() + _snowman / _snowman * _snowman);
                this.soundManager.play(_snowman5);
                this.moodPercentage = 0.0f;
            } else {
                this.moodPercentage = Math.max(this.moodPercentage, 0.0f);
            }
        });
    }

    public static class MusicLoop
    extends MovingSoundInstance {
        private int delta;
        private int strength;

        public MusicLoop(SoundEvent sound) {
            super(sound, SoundCategory.AMBIENT);
            this.repeat = true;
            this.repeatDelay = 0;
            this.volume = 1.0f;
            this.looping = true;
        }

        @Override
        public void tick() {
            if (this.strength < 0) {
                this.setDone();
            }
            this.strength += this.delta;
            this.volume = MathHelper.clamp((float)this.strength / 40.0f, 0.0f, 1.0f);
        }

        public void fadeOut() {
            this.strength = Math.min(this.strength, 40);
            this.delta = -1;
        }

        public void fadeIn() {
            this.strength = Math.max(0, this.strength);
            this.delta = 1;
        }
    }
}

