/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;

public class BackgroundRenderer {
    private static float red;
    private static float green;
    private static float blue;
    private static int waterFogColor;
    private static int nextWaterFogColor;
    private static long lastWaterFogColorUpdateTime;

    public static void render(Camera camera, float tickDelta, ClientWorld world, int n4, float f) {
        int _snowman2;
        FluidState fluidState = camera.getSubmergedFluidState();
        if (fluidState.isIn(FluidTags.WATER)) {
            long l = Util.getMeasuringTimeMs();
            _snowman2 = world.getBiome(new BlockPos(camera.getPos())).getWaterFogColor();
            if (lastWaterFogColorUpdateTime < 0L) {
                waterFogColor = _snowman2;
                nextWaterFogColor = _snowman2;
                lastWaterFogColorUpdateTime = l;
            }
            int _snowman3 = waterFogColor >> 16 & 0xFF;
            int _snowman4 = waterFogColor >> 8 & 0xFF;
            int _snowman5 = waterFogColor & 0xFF;
            int _snowman6 = nextWaterFogColor >> 16 & 0xFF;
            int _snowman7 = nextWaterFogColor >> 8 & 0xFF;
            int _snowman8 = nextWaterFogColor & 0xFF;
            float _snowman9 = MathHelper.clamp((float)(l - lastWaterFogColorUpdateTime) / 5000.0f, 0.0f, 1.0f);
            float _snowman10 = MathHelper.lerp(_snowman9, _snowman6, _snowman3);
            float _snowman11 = MathHelper.lerp(_snowman9, _snowman7, _snowman4);
            float _snowman12 = MathHelper.lerp(_snowman9, _snowman8, _snowman5);
            red = _snowman10 / 255.0f;
            green = _snowman11 / 255.0f;
            blue = _snowman12 / 255.0f;
            if (waterFogColor != _snowman2) {
                waterFogColor = _snowman2;
                nextWaterFogColor = MathHelper.floor(_snowman10) << 16 | MathHelper.floor(_snowman11) << 8 | MathHelper.floor(_snowman12);
                lastWaterFogColorUpdateTime = l;
            }
        } else if (fluidState.isIn(FluidTags.LAVA)) {
            red = 0.6f;
            green = 0.1f;
            blue = 0.0f;
            lastWaterFogColorUpdateTime = -1L;
        } else {
            float _snowman13 = 0.25f + 0.75f * (float)n4 / 32.0f;
            _snowman13 = 1.0f - (float)Math.pow(_snowman13, 0.25);
            Vec3d _snowman14 = world.method_23777(camera.getBlockPos(), tickDelta);
            float _snowman15 = (float)_snowman14.x;
            float _snowman16 = (float)_snowman14.y;
            float _snowman17 = (float)_snowman14.z;
            float _snowman18 = MathHelper.clamp(MathHelper.cos(world.getSkyAngle(tickDelta) * ((float)Math.PI * 2)) * 2.0f + 0.5f, 0.0f, 1.0f);
            BiomeAccess _snowman19 = world.getBiomeAccess();
            Vec3d _snowman20 = camera.getPos().subtract(2.0, 2.0, 2.0).multiply(0.25);
            Vec3d _snowman21 = CubicSampler.sampleColor(_snowman20, (n, n2, n3) -> world.getSkyProperties().adjustFogColor(Vec3d.unpackRgb(_snowman19.getBiomeForNoiseGen(n, n2, n3).getFogColor()), _snowman18));
            red = (float)_snowman21.getX();
            green = (float)_snowman21.getY();
            blue = (float)_snowman21.getZ();
            if (n4 >= 4) {
                float f2 = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) > 0.0f ? -1.0f : 1.0f;
                Vector3f _snowman22 = new Vector3f(f2, 0.0f, 0.0f);
                _snowman = camera.getHorizontalPlane().dot(_snowman22);
                if (_snowman < 0.0f) {
                    _snowman = 0.0f;
                }
                if (_snowman > 0.0f && (_snowman = world.getSkyProperties().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta)) != null) {
                    red = red * (1.0f - (_snowman *= _snowman[3])) + _snowman[0] * _snowman;
                    green = green * (1.0f - _snowman) + _snowman[1] * _snowman;
                    blue = blue * (1.0f - _snowman) + _snowman[2] * _snowman;
                }
            }
            red += (_snowman15 - red) * _snowman13;
            green += (_snowman16 - green) * _snowman13;
            blue += (_snowman17 - blue) * _snowman13;
            f2 = world.getRainGradient(tickDelta);
            if (f2 > 0.0f) {
                _snowman = 1.0f - f2 * 0.5f;
                _snowman = 1.0f - f2 * 0.4f;
                red *= _snowman;
                green *= _snowman;
                blue *= _snowman;
            }
            if ((_snowman = world.getThunderGradient(tickDelta)) > 0.0f) {
                _snowman = 1.0f - _snowman * 0.5f;
                red *= _snowman;
                green *= _snowman;
                blue *= _snowman;
            }
            lastWaterFogColorUpdateTime = -1L;
        }
        double d = camera.getPos().y * world.getLevelProperties().getHorizonShadingRatio();
        if (camera.getFocusedEntity() instanceof LivingEntity && ((LivingEntity)camera.getFocusedEntity()).hasStatusEffect(StatusEffects.BLINDNESS)) {
            _snowman2 = ((LivingEntity)camera.getFocusedEntity()).getStatusEffect(StatusEffects.BLINDNESS).getDuration();
            d = _snowman2 < 20 ? (d *= (double)(1.0f - (float)_snowman2 / 20.0f)) : 0.0;
        }
        if (d < 1.0 && !fluidState.isIn(FluidTags.LAVA)) {
            if (d < 0.0) {
                d = 0.0;
            }
            d *= d;
            red = (float)((double)red * d);
            green = (float)((double)green * d);
            blue = (float)((double)blue * d);
        }
        if (f > 0.0f) {
            red = red * (1.0f - f) + red * 0.7f * f;
            green = green * (1.0f - f) + green * 0.6f * f;
            blue = blue * (1.0f - f) + blue * 0.6f * f;
        }
        if (fluidState.isIn(FluidTags.WATER)) {
            float _snowman23 = 0.0f;
            if (camera.getFocusedEntity() instanceof ClientPlayerEntity) {
                ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)camera.getFocusedEntity();
                _snowman23 = clientPlayerEntity.getUnderwaterVisibility();
            }
            float f3 = Math.min(1.0f / red, Math.min(1.0f / green, 1.0f / blue));
            red = red * (1.0f - _snowman23) + red * f3 * _snowman23;
            green = green * (1.0f - _snowman23) + green * f3 * _snowman23;
            blue = blue * (1.0f - _snowman23) + blue * f3 * _snowman23;
        } else if (camera.getFocusedEntity() instanceof LivingEntity && ((LivingEntity)camera.getFocusedEntity()).hasStatusEffect(StatusEffects.NIGHT_VISION)) {
            float f4 = GameRenderer.getNightVisionStrength((LivingEntity)camera.getFocusedEntity(), tickDelta);
            _snowman = Math.min(1.0f / red, Math.min(1.0f / green, 1.0f / blue));
            red = red * (1.0f - f4) + red * _snowman * f4;
            green = green * (1.0f - f4) + green * _snowman * f4;
            blue = blue * (1.0f - f4) + blue * _snowman * f4;
        }
        RenderSystem.clearColor(red, green, blue, 0.0f);
    }

    public static void method_23792() {
        RenderSystem.fogDensity(0.0f);
        RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
    }

    public static void applyFog(Camera camera, FogType fogType, float viewDistance, boolean thickFog) {
        FluidState fluidState = camera.getSubmergedFluidState();
        Entity _snowman2 = camera.getFocusedEntity();
        if (fluidState.isIn(FluidTags.WATER)) {
            float f = 1.0f;
            f = 0.05f;
            if (_snowman2 instanceof ClientPlayerEntity) {
                ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)_snowman2;
                f -= clientPlayerEntity.getUnderwaterVisibility() * clientPlayerEntity.getUnderwaterVisibility() * 0.03f;
                Biome _snowman3 = clientPlayerEntity.world.getBiome(clientPlayerEntity.getBlockPos());
                if (_snowman3.getCategory() == Biome.Category.SWAMP) {
                    f += 0.005f;
                }
            }
            RenderSystem.fogDensity(f);
            RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
        } else {
            float f;
            if (fluidState.isIn(FluidTags.LAVA)) {
                if (_snowman2 instanceof LivingEntity && ((LivingEntity)_snowman2).hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
                    f = 0.0f;
                    _snowman = 3.0f;
                } else {
                    f = 0.25f;
                    _snowman = 1.0f;
                }
            } else if (_snowman2 instanceof LivingEntity && ((LivingEntity)_snowman2).hasStatusEffect(StatusEffects.BLINDNESS)) {
                int n = ((LivingEntity)_snowman2).getStatusEffect(StatusEffects.BLINDNESS).getDuration();
                float _snowman4 = MathHelper.lerp(Math.min(1.0f, (float)n / 20.0f), viewDistance, 5.0f);
                if (fogType == FogType.FOG_SKY) {
                    f = 0.0f;
                    _snowman = _snowman4 * 0.8f;
                } else {
                    f = _snowman4 * 0.25f;
                    _snowman = _snowman4;
                }
            } else if (thickFog) {
                f = viewDistance * 0.05f;
                _snowman = Math.min(viewDistance, 192.0f) * 0.5f;
            } else if (fogType == FogType.FOG_SKY) {
                f = 0.0f;
                _snowman = viewDistance;
            } else {
                f = viewDistance * 0.75f;
                _snowman = viewDistance;
            }
            RenderSystem.fogStart(f);
            RenderSystem.fogEnd(_snowman);
            RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
            RenderSystem.setupNvFogDistance();
        }
    }

    public static void setFogBlack() {
        RenderSystem.fog(2918, red, green, blue, 1.0f);
    }

    static {
        waterFogColor = -1;
        nextWaterFogColor = -1;
        lastWaterFogColorUpdateTime = -1L;
    }

    public static enum FogType {
        FOG_SKY,
        FOG_TERRAIN;

    }
}

