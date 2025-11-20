/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.class_677;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.FireworkItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class FireworksSparkParticle {

    public static class ExplosionFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public ExplosionFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Explosion explosion = new Explosion(clientWorld, d, d2, d3, d4, d5, d6, MinecraftClient.getInstance().particleManager, this.spriteProvider);
            explosion.setColorAlpha(0.99f);
            return explosion;
        }
    }

    public static class FlashFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public FlashFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Flash flash = new Flash(clientWorld, d, d2, d3);
            flash.setSprite(this.spriteProvider);
            return flash;
        }
    }

    public static class Flash
    extends SpriteBillboardParticle {
        private Flash(ClientWorld world, double x, double y, double z) {
            super(world, x, y, z);
            this.maxAge = 4;
        }

        @Override
        public ParticleTextureSheet getType() {
            return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
        }

        @Override
        public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
            this.setColorAlpha(0.6f - ((float)this.age + tickDelta - 1.0f) * 0.25f * 0.5f);
            super.buildGeometry(vertexConsumer, camera, tickDelta);
        }

        @Override
        public float getSize(float tickDelta) {
            return 7.1f * MathHelper.sin(((float)this.age + tickDelta - 1.0f) * 0.25f * (float)Math.PI);
        }
    }

    static class Explosion
    extends AnimatedParticle {
        private boolean trail;
        private boolean flicker;
        private final ParticleManager particleManager;
        private float field_3801;
        private float field_3800;
        private float field_3799;
        private boolean field_3802;

        private Explosion(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, ParticleManager particleManager, SpriteProvider spriteProvider) {
            super(world, x, y, z, spriteProvider, -0.004f);
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.velocityZ = velocityZ;
            this.particleManager = particleManager;
            this.scale *= 0.75f;
            this.maxAge = 48 + this.random.nextInt(12);
            this.setSpriteForAge(spriteProvider);
        }

        public void setTrail(boolean trail) {
            this.trail = trail;
        }

        public void setFlicker(boolean flicker) {
            this.flicker = flicker;
        }

        @Override
        public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
            if (!this.flicker || this.age < this.maxAge / 3 || (this.age + this.maxAge) / 3 % 2 == 0) {
                super.buildGeometry(vertexConsumer, camera, tickDelta);
            }
        }

        @Override
        public void tick() {
            super.tick();
            if (this.trail && this.age < this.maxAge / 2 && (this.age + this.maxAge) % 2 == 0) {
                Explosion explosion = new Explosion(this.world, this.x, this.y, this.z, 0.0, 0.0, 0.0, this.particleManager, this.spriteProvider);
                explosion.setColorAlpha(0.99f);
                explosion.setColor(this.colorRed, this.colorGreen, this.colorBlue);
                explosion.age = explosion.maxAge / 2;
                if (this.field_3802) {
                    explosion.field_3802 = true;
                    explosion.field_3801 = this.field_3801;
                    explosion.field_3800 = this.field_3800;
                    explosion.field_3799 = this.field_3799;
                }
                explosion.flicker = this.flicker;
                this.particleManager.addParticle(explosion);
            }
        }
    }

    public static class FireworkParticle
    extends NoRenderParticle {
        private int age;
        private final ParticleManager particleManager;
        private ListTag explosions;
        private boolean flicker;

        public FireworkParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, ParticleManager particleManager, @Nullable CompoundTag tag) {
            super(world, x, y, z);
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.velocityZ = velocityZ;
            this.particleManager = particleManager;
            this.maxAge = 8;
            if (tag != null) {
                this.explosions = tag.getList("Explosions", 10);
                if (this.explosions.isEmpty()) {
                    this.explosions = null;
                } else {
                    this.maxAge = this.explosions.size() * 2 - 1;
                    for (int i = 0; i < this.explosions.size(); ++i) {
                        CompoundTag compoundTag = this.explosions.getCompound(i);
                        if (!compoundTag.getBoolean("Flicker")) continue;
                        this.flicker = true;
                        this.maxAge += 15;
                        break;
                    }
                }
            }
        }

        @Override
        public void tick() {
            Object _snowman3;
            if (this.age == 0 && this.explosions != null) {
                int n = this.isFar();
                boolean _snowman2 = false;
                if (this.explosions.size() >= 3) {
                    _snowman2 = true;
                } else {
                    for (_snowman = 0; _snowman < this.explosions.size(); ++_snowman) {
                        CompoundTag compoundTag = this.explosions.getCompound(_snowman);
                        if (FireworkItem.Type.byId(compoundTag.getByte("Type")) != FireworkItem.Type.LARGE_BALL) continue;
                        _snowman2 = true;
                        break;
                    }
                }
                _snowman3 = _snowman2 ? (n != 0 ? SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST) : (n != 0 ? SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST);
                this.world.playSound(this.x, this.y, this.z, (SoundEvent)_snowman3, SoundCategory.AMBIENT, 20.0f, 0.95f + this.random.nextFloat() * 0.1f, true);
            }
            if (this.age % 2 == 0 && this.explosions != null && this.age / 2 < this.explosions.size()) {
                n = this.age / 2;
                CompoundTag _snowman4 = this.explosions.getCompound(n);
                _snowman3 = FireworkItem.Type.byId(_snowman4.getByte("Type"));
                boolean _snowman5 = _snowman4.getBoolean("Trail");
                boolean _snowman6 = _snowman4.getBoolean("Flicker");
                int[] _snowman7 = _snowman4.getIntArray("Colors");
                int[] _snowman8 = _snowman4.getIntArray("FadeColors");
                if (_snowman7.length == 0) {
                    _snowman7 = new int[]{DyeColor.BLACK.getFireworkColor()};
                }
                switch (class_677.1.field_3797[((Enum)_snowman3).ordinal()]) {
                    default: {
                        this.explodeBall(0.25, 2, _snowman7, _snowman8, _snowman5, _snowman6);
                        break;
                    }
                    case 2: {
                        this.explodeBall(0.5, 4, _snowman7, _snowman8, _snowman5, _snowman6);
                        break;
                    }
                    case 3: {
                        this.explodeStar(0.5, new double[][]{{0.0, 1.0}, {0.3455, 0.309}, {0.9511, 0.309}, {0.3795918367346939, -0.12653061224489795}, {0.6122448979591837, -0.8040816326530612}, {0.0, -0.35918367346938773}}, _snowman7, _snowman8, _snowman5, _snowman6, false);
                        break;
                    }
                    case 4: {
                        this.explodeStar(0.5, new double[][]{{0.0, 0.2}, {0.2, 0.2}, {0.2, 0.6}, {0.6, 0.6}, {0.6, 0.2}, {0.2, 0.2}, {0.2, 0.0}, {0.4, 0.0}, {0.4, -0.6}, {0.2, -0.6}, {0.2, -0.4}, {0.0, -0.4}}, _snowman7, _snowman8, _snowman5, _snowman6, true);
                        break;
                    }
                    case 5: {
                        this.explodeBurst(_snowman7, _snowman8, _snowman5, _snowman6);
                    }
                }
                _snowman = _snowman7[0];
                float _snowman9 = (float)((_snowman & 0xFF0000) >> 16) / 255.0f;
                float _snowman10 = (float)((_snowman & 0xFF00) >> 8) / 255.0f;
                float _snowman11 = (float)((_snowman & 0xFF) >> 0) / 255.0f;
                Particle _snowman12 = this.particleManager.addParticle(ParticleTypes.FLASH, this.x, this.y, this.z, 0.0, 0.0, 0.0);
                _snowman12.setColor(_snowman9, _snowman10, _snowman11);
            }
            ++this.age;
            if (this.age > this.maxAge) {
                if (this.flicker) {
                    n = this.isFar() ? 1 : 0;
                    SoundEvent soundEvent = n != 0 ? SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR : SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE;
                    this.world.playSound(this.x, this.y, this.z, soundEvent, SoundCategory.AMBIENT, 20.0f, 0.9f + this.random.nextFloat() * 0.15f, true);
                }
                this.markDead();
            }
        }

        private boolean isFar() {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            return minecraftClient.gameRenderer.getCamera().getPos().squaredDistanceTo(this.x, this.y, this.z) >= 256.0;
        }

        private void addExplosionParticle(double x, double y, double z, double velocityX, double velocityY, double velocityZ, int[] colors, int[] fadeColors, boolean trail, boolean flicker) {
            Explosion explosion = (Explosion)this.particleManager.addParticle(ParticleTypes.FIREWORK, x, y, z, velocityX, velocityY, velocityZ);
            explosion.setTrail(trail);
            explosion.setFlicker(flicker);
            explosion.setColorAlpha(0.99f);
            int _snowman2 = this.random.nextInt(colors.length);
            explosion.setColor(colors[_snowman2]);
            if (fadeColors.length > 0) {
                explosion.setTargetColor(Util.getRandom(fadeColors, this.random));
            }
        }

        private void explodeBall(double size, int amount, int[] colors, int[] fadeColors, boolean trail, boolean flicker) {
            double d = this.x;
            _snowman = this.y;
            _snowman = this.z;
            for (int i = -amount; i <= amount; ++i) {
                for (_snowman = -amount; _snowman <= amount; ++_snowman) {
                    for (_snowman = -amount; _snowman <= amount; ++_snowman) {
                        double d2 = (double)_snowman + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                        _snowman = (double)i + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                        _snowman = (double)_snowman + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                        _snowman = (double)MathHelper.sqrt(d2 * d2 + _snowman * _snowman + _snowman * _snowman) / size + this.random.nextGaussian() * 0.05;
                        this.addExplosionParticle(d, _snowman, _snowman, d2 / _snowman, _snowman / _snowman, _snowman / _snowman, colors, fadeColors, trail, flicker);
                        if (i == -amount || i == amount || _snowman == -amount || _snowman == amount) continue;
                        _snowman += amount * 2 - 1;
                    }
                }
            }
        }

        private void explodeStar(double size, double[][] pattern, int[] colors, int[] fadeColors, boolean trail, boolean flicker, boolean keepShape) {
            double d = pattern[0][0];
            _snowman = pattern[0][1];
            this.addExplosionParticle(this.x, this.y, this.z, d * size, _snowman * size, 0.0, colors, fadeColors, trail, flicker);
            float _snowman2 = this.random.nextFloat() * (float)Math.PI;
            _snowman = keepShape ? 0.034 : 0.34;
            for (int i = 0; i < 3; ++i) {
                double d2 = (double)_snowman2 + (double)((float)i * (float)Math.PI) * _snowman;
                _snowman = d;
                _snowman = _snowman;
                for (int j = 1; j < pattern.length; ++j) {
                    double d3 = pattern[j][0];
                    _snowman = pattern[j][1];
                    for (_snowman = 0.25; _snowman <= 1.0; _snowman += 0.25) {
                        _snowman = MathHelper.lerp(_snowman, _snowman, d3) * size;
                        _snowman = MathHelper.lerp(_snowman, _snowman, _snowman) * size;
                        _snowman = _snowman * Math.sin(d2);
                        _snowman *= Math.cos(d2);
                        for (_snowman = -1.0; _snowman <= 1.0; _snowman += 2.0) {
                            this.addExplosionParticle(this.x, this.y, this.z, _snowman * _snowman, _snowman, _snowman * _snowman, colors, fadeColors, trail, flicker);
                        }
                    }
                    _snowman = d3;
                    _snowman = _snowman;
                }
            }
        }

        private void explodeBurst(int[] colors, int[] fadeColors, boolean trail, boolean flocker) {
            double d = this.random.nextGaussian() * 0.05;
            _snowman = this.random.nextGaussian() * 0.05;
            for (int i = 0; i < 70; ++i) {
                double d2 = this.velocityX * 0.5 + this.random.nextGaussian() * 0.15 + d;
                _snowman = this.velocityZ * 0.5 + this.random.nextGaussian() * 0.15 + _snowman;
                _snowman = this.velocityY * 0.5 + this.random.nextDouble() * 0.5;
                this.addExplosionParticle(this.x, this.y, this.z, d2, _snowman, _snowman, colors, fadeColors, trail, flocker);
            }
        }
    }
}

